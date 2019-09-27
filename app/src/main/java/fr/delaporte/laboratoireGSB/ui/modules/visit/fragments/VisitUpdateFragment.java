package fr.delaporte.laboratoireGSB.ui.modules.visit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.helper.Session;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitActivity;
import fr.delaporte.laboratoireGSB.ui.widget.SpinnerDialog;
import fr.delaporte.laboratoireGSB.data.models.Visit;

public class VisitUpdateFragment extends Fragment {

    private VisitActivity activity;

    public VisitUpdateFragment(){}

    public static VisitUpdateFragment newInstance(){
        return new VisitUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = (VisitActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_visit_update_fragment, container, false);
        Visit currentVisit = activity.currentVisit;

        Role role = Session.getUserRole();

        final EditText edtPracticien = view.findViewById(R.id.edt_practicien);
        final EditText edtPlace = view.findViewById(R.id.edt_place);
        final EditText edtRendu = view.findViewById(R.id.edt_compteRendu);
        final EditText edtDate = view.findViewById(R.id.edt_date);

        final Button btnNext = view.findViewById(R.id.btn_visitNext);

        edtPlace.setText(currentVisit.getPractitioner().getAdress());
        edtPracticien.setText(currentVisit.getPractitioner().toString());
        edtDate.setText(VisitsFragment.visitDateFormat.format(currentVisit.getDate().getTime()));
        edtRendu.setText(currentVisit.getReport());


        btnNext.setOnClickListener(v -> {

            if (edtRendu.getText().toString().isEmpty()) {

                SpinnerDialog dialog = new SpinnerDialog(getContext());
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, new String[]{"a", "b", "c"});

                dialog.setAdapter(spinnerAdapter);

                dialog.setTitle("Compte rendu prédéfini");
                dialog.setOnValidationListener(i -> {
                    edtRendu.setText(i.toString());
                    this.nextStep(edtRendu, currentVisit);
                });

                dialog.show();

            } else {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                this.nextStep(edtRendu, currentVisit);
            }
        });

        return view;
    }

    private void nextStep(EditText edtRendu, Visit currentVisit){

        activity.changePagerPosition(1);
        currentVisit.setReport(edtRendu.getText().toString());
    }

}
