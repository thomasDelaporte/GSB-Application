package fr.delaporte.laboratoireGSB.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Drug;
import fr.delaporte.laboratoireGSB.data.models.DrugLeft;

    public class MedicamentAdapter extends ArrayAdapter<DrugLeft> {

    private final Context context;

    private final List<DrugLeft> drugsLeft;

    /**
     * Constructeur
     *
     * @param context
     * @param drugsLeft liste des données
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MedicamentAdapter(Context context, List<DrugLeft> drugsLeft) {
        super(context, R.layout.row_medicament, drugsLeft);

        this.context = context;
        this.drugsLeft = drugsLeft;
    }


    /**
     * Création de la vue.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.row_medicament, parent, false);

        DrugLeft drugLeft = drugsLeft.get(position);
        Drug drug = drugLeft.getDrug();

        TextView txtName = view.findViewById(R.id.txt_medicamentName);
        txtName.setText(drug.getName());

        TextView txtMedicamentCount = view.findViewById(R.id.edt_medicamentCount);
        txtMedicamentCount.setText(String.valueOf(drugLeft.getQuantity()));

        ImageButton btnAdd = view.findViewById(R.id.btn_medicamentAdd);
        btnAdd.setOnClickListener(v -> {

            if(drugLeft.getMaxQuantity() <= drugLeft.getQuantity()){
                Toast.makeText(context, "Il n'y a pas assez de médicament en stock (" + drugLeft.getMaxQuantity() + ").", Toast.LENGTH_SHORT).show(); return;
            }

            drugLeft.addQuantity();
            notifyDataSetChanged();
        });

        ImageButton btnSub = view.findViewById(R.id.btn_medicamentSub);
        btnSub.setOnClickListener(v -> {

            if(drugLeft.getQuantity() == 0) return;

            drugLeft.subQuantity();
            notifyDataSetChanged();
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DrugLeft containsDrug(Drug drug){
        return drugsLeft.stream().filter(u -> u.getDrug().getIdDrug() == drug.getIdDrug())
                .findFirst().orElse(null);
    }

    public List<DrugLeft> getItems() {
        return this.drugsLeft;
    }
}
