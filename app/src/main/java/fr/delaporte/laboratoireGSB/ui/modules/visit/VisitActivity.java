package fr.delaporte.laboratoireGSB.ui.modules.visit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.ui.adapter.StepperAdapter;
import fr.delaporte.laboratoireGSB.data.models.DrugLeft;
import fr.delaporte.laboratoireGSB.ui.modules.visit.fragments.VisitAddFragment;
import fr.delaporte.laboratoireGSB.ui.modules.visit.fragments.VisitMedicamentsFragment;
import fr.delaporte.laboratoireGSB.ui.modules.visit.fragments.VisitUpdateFragment;
import fr.delaporte.laboratoireGSB.ui.widget.NonSwipeableViewPager;
import fr.delaporte.laboratoireGSB.ui.base.Activity;
import fr.delaporte.laboratoireGSB.data.models.Visit;

public class VisitActivity extends Activity {

    public Visit currentVisit;
    private StepperAdapter pagerAdapter;
    private NonSwipeableViewPager vpVisitStep;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateVisit(List<DrugLeft> drugsLeft, boolean back){

        List<DrugLeft> lastDrugs = currentVisit.getDrugsLeft();
        lastDrugs.forEach((e) -> Log.i("DB", "LastDrug : " + e.getDrug()));

        /** SI PREMIERE FOIS, METTRE A JOUR DATE D UPDATE */
        if(currentVisit.getDateDone() == null){
            currentVisit.setDateDone(Calendar.getInstance());
        }

        currentVisit.update();
        for (DrugLeft drugLeft : drugsLeft) {

            Log.i("drug", drugLeft.getDrug().toString());
            Log.i("drug", String.valueOf(drugLeft.getDrug().getIdDrug()));


            if(drugLeft.exists()) drugLeft.update();
            else drugLeft.save();
        }

        Toast.makeText(VisitActivity.this, "Modification de la visite", Toast.LENGTH_LONG).show();

        if(back){
            Intent intent = new Intent(this, VisitsActivity.class);
            intent.putExtra("day", VisitsActivity.getPositionCalculation(currentVisit.getDate()));

            startActivity(intent);
            finish();
        }
    }

    public void changePagerPosition(int position){
        vpVisitStep.setCurrentItem(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_visit);

        this.currentVisit = getIntent().getParcelableExtra("visit");

        this.setTitle(this.getTitle() + " " + currentVisit.getIdVisite());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vpVisitStep = this.findViewById(R.id.vp_Visit);

        pagerAdapter = new StepperAdapter(getSupportFragmentManager(), 3);
        pagerAdapter.addStep(VisitUpdateFragment.newInstance());
        pagerAdapter.addStep(VisitMedicamentsFragment.newInstance());
        pagerAdapter.addStep(VisitAddFragment.newInstance());

        vpVisitStep.setAdapter(pagerAdapter);
    }


}
