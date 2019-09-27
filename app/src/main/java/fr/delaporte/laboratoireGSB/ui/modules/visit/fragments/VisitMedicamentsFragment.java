package fr.delaporte.laboratoireGSB.ui.modules.visit.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Objects;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Drug_Table;
import fr.delaporte.laboratoireGSB.helper.Session;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitActivity;
import fr.delaporte.laboratoireGSB.ui.adapter.MedicamentAdapter;
import fr.delaporte.laboratoireGSB.data.models.Drug;
import fr.delaporte.laboratoireGSB.data.models.DrugLeft;
import fr.delaporte.laboratoireGSB.data.models.Visit;
import fr.delaporte.laboratoireGSB.ui.widget.SpinnerDialog;

public class VisitMedicamentsFragment extends Fragment {

    private VisitActivity activity;
    private Visit currentVisit;

    public VisitMedicamentsFragment(){}

    public static VisitMedicamentsFragment newInstance(){

        VisitMedicamentsFragment fragment = new VisitMedicamentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = (VisitActivity) getActivity();
        this.currentVisit = activity.currentVisit;

        /** CHARGER LES MEDICAMENS SI DEJA FAITS */
        this.currentVisit.updateDrugsLeft();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_visit_medicaments_fragment, container, false);

        final TextView txtNoData = view.findViewById(R.id.txt_visitMedicamentsNoData);

        /** DESAFICHER LE TEXTE NO DATA SI IL Y A DEJA EU DES MEDICAMENTS ENRENGISTRE */
        if(!currentVisit.getDrugsLeft().isEmpty()){
            txtNoData.setVisibility(View.INVISIBLE);
        }

        List<Drug> drugsInStock = loadDrugsStock();

        /** CLONE VARIABLE */
        final MedicamentAdapter medicamentsAdapter = new MedicamentAdapter(getContext(), currentVisit.getDrugsLeft());
        ListView lvMedicaments = view.findViewById(R.id.lv_Medicaments);
        lvMedicaments.setAdapter(medicamentsAdapter);

        Button btnMedicamentDialog = view.findViewById(R.id.btn_medicamentDialog);
        btnMedicamentDialog.setOnClickListener(v -> {

            SpinnerDialog dialog = new SpinnerDialog(activity);
            ArrayAdapter<Drug> spinnerAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, drugsInStock);

            dialog.setAdapter(spinnerAdapter);
            dialog.setTitle("Ajouter un médicament");
            dialog.setOnValidationListener(i -> {

                Drug selectedDrug = (Drug) i;

                /** SI LE MEDICAMENT EXISTE DEJA, ON INCREMENTE SEULEMENT */
                DrugLeft findedDrug = medicamentsAdapter.containsDrug(selectedDrug);
                if(findedDrug != null){

                    if(findedDrug.getMaxQuantity() == findedDrug.getQuantity()){
                        Toast.makeText(getContext(), "Il n'y a pas assez de médicament en stock.", Toast.LENGTH_SHORT).show(); return;
                    }

                    findedDrug.addQuantity(); medicamentsAdapter.notifyDataSetChanged(); return;
                }

                /** SINON ON AJOUTE LE NOUVEAU MEDICAMENT */
                DrugLeft drugLeft = new DrugLeft(selectedDrug, currentVisit);
                medicamentsAdapter.add(drugLeft);

                if(txtNoData.getVisibility() == View.VISIBLE){
                    txtNoData.setVisibility(View.INVISIBLE);
                }
            });

            dialog.show();
        });

        final Role userRole = Session.getUserRole();

        Button btnConfirmVisitUpdate = view.findViewById(R.id.btn_confirmVisitUpdate);
        if(userRole.equals(Role.DELEGUE)) btnConfirmVisitUpdate.setText("Mettre à jour");

        btnConfirmVisitUpdate.setOnClickListener(v -> {
            activity.updateVisit(medicamentsAdapter.getItems(), true);
        });

        return view;
    }

    private List<Drug> loadDrugsStock(){
        return SQLite.select()
                .from(Drug.class)
                .where(Drug_Table.quantityAvailable.greaterThan(0))
                .queryList();
    }

}
