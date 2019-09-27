package fr.delaporte.laboratoireGSB.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import fr.delaporte.laboratoireGSB.R;

public class CRUDAdapter<T extends BaseModel> extends ArrayAdapter<T> {

    private List<T> data;
    private OnDeleteListener<T> onDeleteListener;
    private OnDeletedListener<T> onDeletedListener;

    /**
     * Constructeur
     *
     * @param context
     * @param data liste des données
     */
    public CRUDAdapter(Context context, List<T> data) {
        super(context, 0, data);

        this.data = data;
    }

    /**
     * Création de la vue
     *
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.row_crud, parent, false);

        T currentItem = data.get(position);

        TextView txtName = view.findViewById(R.id.txt_crudName);
        txtName.setText(currentItem.toString());

        ImageButton btnDelete = view.findViewById(R.id.btn_crudDelete);
        if(this.onDeleteListener != null){
            btnDelete.setOnClickListener(v -> {

                if(this.onDeleteListener.onDelete(currentItem)){

                    data.remove(currentItem); notifyDataSetChanged();
                    if(onDeletedListener != null) onDeletedListener.onDeleted(currentItem);
                }
            });
        }

        return view;
    }

    /**
     * Défnir un listener lors du clique sur le boutton de suppression d'un élément.
     * @param l
     */
    public void setOnDeleteListener(OnDeleteListener<T> l){
        this.onDeleteListener = l;
    }

    /**
     * Retourne le nombre d'élements dans la liste.
     * @return
     */
    public int getCount(){
        return this.data.size();
    }

    /**
     * Définir un listener qui s'active après la suppréssion d'un élement.
     * @param l
     */
    public void setOnDeletedListener(OnDeletedListener<T> l) {
        this.onDeletedListener = l;
    }

    public interface OnDeleteListener<B> {
        boolean onDelete(B i);
    }

    public interface OnDeletedListener<B> {
        void onDeleted(B i);
    }
}
