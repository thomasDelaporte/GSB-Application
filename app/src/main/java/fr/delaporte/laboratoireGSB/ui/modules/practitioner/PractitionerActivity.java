package fr.delaporte.laboratoireGSB.ui.modules.practitioner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Practitioner;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class PractitionerActivity extends Activity {

    private String errorEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_practitioner_form);

        this.errorEmpty = getResources().getString(R.string.txt_empty);

        Practitioner practitioner = getIntent().getParcelableExtra("practitioner");
        setTitle("Modification d'un practicien");

        EditText edtName = this.findViewById(R.id.edt_practitionerFormName);
        EditText edtSurname = this.findViewById(R.id.edt_practitionerFormSurname);
        EditText edtAdress = this.findViewById(R.id.edt_practitionerFormAdress);
        EditText edtCity = this.findViewById(R.id.edt_practitionerFormCity);
        EditText edtCityCP = this.findViewById(R.id.edt_practitionerFormCityCP);

        /** METTRE CHAMPS PAR DEFAULT */
        edtName.setText(practitioner.getName());
        edtSurname.setText(practitioner.getSurname());
        edtAdress.setText(practitioner.getAdress());
        edtCity.setText(practitioner.getCity());
        edtCityCP.setText(String.valueOf(practitioner.getCityCP()));

        Button btnForm = this.findViewById(R.id.btn_practitionerForm);
        btnForm.setOnClickListener(v -> {

            String name = edtName.getText().toString();
            String surname = edtSurname.getText().toString();
            String adress = edtAdress.getText().toString();
            String city = edtCity.getText().toString();
            String cityCP = edtCityCP.getText().toString();

            if(name.isEmpty()){ edtName.setError(errorEmpty); return; }
            if(surname.isEmpty()){ edtSurname.setError(errorEmpty); return; }
            if(adress.isEmpty()){ edtAdress.setError(errorEmpty); return; }
            if(city.isEmpty()){ edtCity.setError(errorEmpty); return; }
            if(cityCP.isEmpty()){ edtCityCP.setError(errorEmpty); return; }

            practitioner.setName(name);
            practitioner.setSurname(surname);
            practitioner.setAdress(adress);
            practitioner.setCity(city);
            practitioner.setCityCP(Integer.parseInt(cityCP));
            practitioner.save();

            Intent intent = new Intent(this, PractitionersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        });
    }
}
