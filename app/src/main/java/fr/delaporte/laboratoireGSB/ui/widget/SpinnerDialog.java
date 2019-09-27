package fr.delaporte.laboratoireGSB.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fr.delaporte.laboratoireGSB.R;

public class SpinnerDialog extends Dialog {

    private String title;
    private Spinner spinner;
    private BaseAdapter adapter;
    private OnValidationListener validationListener;

    public SpinnerDialog(Context context) {
        super(context);
    }

    /**
     * Création d'une boite de dialogue avec un "Spinner".
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_dialog);

        TextView txtTitle = findViewById(R.id.txt_dialogTitle);
        if(this.title != null){
            txtTitle.setText(this.title);
        } else txtTitle.setVisibility(View.INVISIBLE);

        spinner = findViewById(R.id.spi_dialogSpinner);
        spinner.setAdapter(adapter);

        Button btnCancel = findViewById(R.id.btn_dialogSpinnerCancel);
        btnCancel.setOnClickListener(v -> this.dismiss());

        Button btnValidate = findViewById(R.id.btn_dialogSpinnerValid);
        btnValidate.setOnClickListener(v -> {

            validationListener.onValidation(spinner.getSelectedItem());
            this.dismiss();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setLayout(width, height);
    }

    /**
     * Définir le listener lorsqu'un un item est séléctionné.
     * @param listener
     */
    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(listener);
    }

    /**
     * Définir l'adapter utilisé pour le "Spinner".
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * Définir le listener lors de la validation de la boite de dialogue
     * @param validationListener
     */
    public void setOnValidationListener(OnValidationListener validationListener) {
        this.validationListener = validationListener;
    }

    /**
     * Définir le titre de la boite de dialogue.
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Action pour afficher la boite de dialogue
     */
    @Override
    public void show() {

        if(adapter.getCount() == 0){
            Toast.makeText(getContext(), "Aucune donnée à choisir", Toast.LENGTH_LONG).show(); return;
        }

        super.show();
    }

    /**
     * Interface utilisé pour permettre au créateur du dialogue d'exacuter du code
     * après la validation.
     */
    public interface OnValidationListener {

        /**
         * Methode lors de la validation de la boite de dialogue.
         * @param i Item selectionné
         */
        void onValidation(Object i);
    }
}
