package fr.delaporte.laboratoireGSB.ui.base.CRUD;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.ui.adapter.CRUDAdapter;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public abstract class CRUDListActivity<T extends BaseModel> extends Activity {

    protected TextView txtNoData;
    protected ListView listView;

    protected AsyncQuery<T> query;

    /**
     * Création simplifié d'une liste de données provenant
     * de la base de donnée (Liste)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_crud_list);

        this.txtNoData = this.findViewById(R.id.txt_crudListNoData);
        this.listView = this.findViewById(R.id.lv_crudList);

        this.txtNoData.setText(this.setTextNoData());
        this.query = this.setQuery();
        this.loadData();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            @SuppressWarnings("unchecked")
            T item = (T) listView.getItemAtPosition(position);

            this.onUpdateListener(item);
        });

        FloatingActionButton crudAdd = this.findViewById(R.id.btn_crudListAdd);
        crudAdd.setOnClickListener(this.setOnAddListener());

        final SwipeRefreshLayout swipeRefreshLayout = this.findViewById(R.id.rl_crudList);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            loadData(() -> {
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }

    /**
     * Action losque on clique sur un élément de la liste.
     * @param item objet type BaseModel
     */
    public void onUpdateListener(T item){}

    /**
     * Action lorsqu'on clique sur le boutton supprimé d'un élément de la liste
     *
     * @param item
     * @return Si l'élement est false, la suppression est annulé : permet de ne pas mettre à jour la liste visuellement.
     */
    public boolean onDeleteListener(T item) {
        return false;
    }

    private void loadData(){
        this.loadData(null);
    }

    /**
     * Chargement des données de la liste par rapport a :
     *  - la requête définis
     *  - le texte "Aucune données"
     *  - le listener delete (si définis)
     *
     * @param callback
     */
    private void loadData(Callback callback){

        if(this.query == null) return;

        query.queryListResultCallback((transaction, results) -> {

            if(results.isEmpty()){
                txtNoData.setVisibility(TextView.VISIBLE); return;
            } else txtNoData.setVisibility(TextView.INVISIBLE);

            CRUDAdapter<T> adapter = new CRUDAdapter<>(this, results);
            adapter.setOnDeleteListener(this::onDeleteListener);
            adapter.setOnDeletedListener(item -> {

                if(adapter.getCount() == 0){
                    txtNoData.setVisibility(TextView.VISIBLE);
                }
            });

            listView.setAdapter(adapter);
        }).success(transaction -> {
            if(callback != null) callback.done();
        }).execute();
    }

    /**
     * Définir un listener lorsque l'utilisateur appuie sur le bouton "Ajouté"
     * Sous la forme d'un return new View.onClickListener(){};
     * @return
     */
    public abstract View.OnClickListener setOnAddListener();

    /**
     * Définir le texte lorsque il n'y a pas de données dans la liste.
     * Sous la forme d'un return "Texte exemple";
     * @return
     */
    public abstract String setTextNoData();

    /**
     * Définir la requête utilisé pour charger les données de la base de donnée
     * Sous la forme d'un return new SQLite().async();
     * @return
     */
    public abstract AsyncQuery<T> setQuery();

    /**
     * Interface utilisé pour permettre d'éxecuter une action après
     * après le chargement des données.
     */
    private interface Callback {
        void done();
    }
}
