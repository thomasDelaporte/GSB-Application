package fr.delaporte.laboratoireGSB.ui.modules.visit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitActivity;

public class VisitAddFragment extends Fragment {

    private VisitActivity activity;

    public VisitAddFragment(){}

    public static VisitAddFragment newInstance(){
        return new VisitAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = (VisitActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_visit_add_fragment, container, false);
        return view;
    }

}
