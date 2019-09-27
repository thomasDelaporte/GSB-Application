package fr.delaporte.laboratoireGSB.ui.modules.visit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.data.models.Personnel_Table;
import fr.delaporte.laboratoireGSB.data.models.Practitioner;
import fr.delaporte.laboratoireGSB.data.models.Visit;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class VisitAddActivity extends Activity {

    private Calendar visitDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_visit_add);

        Objects.requireNonNull(this.getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_clear_24px);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.visitDate = (Calendar) getIntent().getSerializableExtra("day");

        List<Practitioner> practitioners = SQLite.select()
                .from(Practitioner.class)
                .queryList();

        List<Personnel> personnels = SQLite.select()
                .from(Personnel.class)
                .where(Personnel_Table.role.eq(Role.VISITEURMEDICAL))
                .queryList();

        Spinner spiPractitioners = this.findViewById(R.id.spi_visitFormPractitioner);
        Spinner spiPersonnels = this.findViewById(R.id.spi_visitFormPersonnel);

        ArrayAdapter<Practitioner> practitionerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, practitioners);
        ArrayAdapter<Personnel> personnelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, personnels);

        spiPersonnels.setAdapter(personnelAdapter);
        spiPractitioners.setAdapter(practitionerAdapter);

        EditText edtDate = this.findViewById(R.id.edt_visitFormDate);
        edtDate.setText(visitDate.get(Calendar.DAY_OF_MONTH) + "/" + visitDate.get(Calendar.MONTH) + "/" + visitDate.get(Calendar.YEAR));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    edtDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    visitDate.set(Calendar.YEAR, year);
                    visitDate.set(Calendar.MONTH, monthOfYear);
                    visitDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                }, visitDate.get(Calendar.YEAR), visitDate.get(Calendar.MONTH), visitDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(VisitsActivity.today.getTimeInMillis());

        edtDate.setOnClickListener((v) -> {
            datePickerDialog.show();
        });

        EditText edtHour = this.findViewById(R.id.edt_visitFormDateHour);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    edtHour.setText(String.valueOf(hourOfDay) + "h" + String.valueOf(minute));

                    visitDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    visitDate.set(Calendar.MINUTE, minute);
                }, visitDate.get(Calendar.HOUR), visitDate.get(Calendar.MINUTE),true);

        edtHour.setOnClickListener((v) -> {
            timePickerDialog.show();
        });

        timePickerDialog.setOnDismissListener(dialog -> {
            edtHour.clearFocus();
        });

        Button btnAdd = this.findViewById(R.id.btn_visitAdd);
        btnAdd.setOnClickListener(v -> {

            String theoricalDate = edtDate.getText().toString();
            String theoricalHour = edtHour.getText().toString();

            if(theoricalDate.isEmpty()){ edtDate.setError("erreur"); return; }
            if(theoricalHour.isEmpty()){ edtDate.setError("erreur"); return; }

            Practitioner practitioner = (Practitioner) spiPractitioners.getSelectedItem();
            Personnel visiteurMedical = (Personnel) spiPersonnels.getSelectedItem();

            Visit visit = new Visit(practitioner, visiteurMedical, visitDate);
            visit.save();

            Intent intent = new Intent(this, VisitsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        });
    }
}
