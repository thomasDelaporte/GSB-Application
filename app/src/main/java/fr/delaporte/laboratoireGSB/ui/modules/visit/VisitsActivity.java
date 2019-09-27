package fr.delaporte.laboratoireGSB.ui.modules.visit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.chip.Chip;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.helper.Session;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.adapter.VisitsAdapter;
import fr.delaporte.laboratoireGSB.ui.modules.visit.fragments.VisitsFragment;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class VisitsActivity extends Activity implements VisitsFragment.OnFragmentInteractionListener {

    public final static Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
    private Calendar currentDate;

    private TextView txtCurrentDate;
    private DatePickerDialog datePickerDialog;
    private static VisitsAdapter visitsAdapter;

    private SimpleDateFormat currentDateFormat = new SimpleDateFormat("d MMM", Locale.FRANCE);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_visits);

        this.currentDate = today;
        this.txtCurrentDate = this.findViewById(R.id.txt_visitCurrentDate);

        final ViewPager visitsPager = this.findViewById(R.id.mainPager);
        visitsAdapter = new VisitsAdapter(getSupportFragmentManager(), today, 20);
        visitsPager.setAdapter(visitsAdapter);

        FloatingActionButton fabAdd = this.findViewById(R.id.fab_visitsAdd);
        if(Session.getUserRole().equals(Role.VISITEURMEDICAL)) fabAdd.hide();

        fabAdd.setOnClickListener(v -> {

            Intent intent = new Intent(this, VisitAddActivity.class);
            intent.putExtra("day", currentDate);
            startActivity(intent);
        });

        visitsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {

                VisitsFragment visitsFragment = (VisitsFragment) visitsAdapter.getItem(visitsPager.getCurrentItem());
                currentDate = visitsFragment.getDate();

                updateDateWidgets();
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageScrollStateChanged(int i) {}
        });


        /** WIDGET TEXTUEL POUR VOIR ET MODIFIER LA DATE */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            this.datePickerDialog = new DatePickerDialog(VisitsActivity.this);

            datePickerDialog.getDatePicker().setMinDate(visitsAdapter.getMinDate());
            datePickerDialog.getDatePicker().setMaxDate(visitsAdapter.getMaxDate());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {

                Calendar futureDate = (Calendar) currentDate.clone();
                futureDate.set(Calendar.YEAR, year);
                futureDate.set(Calendar.MONTH, month);
                futureDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int pos = getPositionCalculation(futureDate);
                visitsPager.setCurrentItem(pos, true);

                this.currentDate = futureDate;
                updateDateWidgets();
            });

            updateDateWidgets();

            Chip chpVisitDate = this.findViewById(R.id.chp_visitDate);
            chpVisitDate.setOnClickListener(v -> {
                datePickerDialog.show();
            });
        }

        int pageDefault = getIntent().getIntExtra("day", visitsAdapter.getDefaultPage());
        visitsPager.setCurrentItem(pageDefault);
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    public void updateDateWidgets(){

        if(this.txtCurrentDate != null)
            txtCurrentDate.setText(currentDateFormat.format(currentDate.getTime()));

        if(this.datePickerDialog != null)
            datePickerDialog.updateDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
    }


    public static int getPositionCalculation(Calendar date){
        return visitsAdapter.getDefaultPage() + (date.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR));
    }
}
