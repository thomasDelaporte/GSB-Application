package fr.delaporte.laboratoireGSB.ui.modules.personnel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class PersonnelActivity extends Activity {

    private String errorEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_personnel_form);

        this.errorEmpty = getResources().getString(R.string.txt_empty);

        Personnel personnel = getIntent().getParcelableExtra("personnel");
        setTitle("Modification d'un personnel");

        EditText edtName = this.findViewById(R.id.edt_personnelAddName);
        EditText edtSurname = this.findViewById(R.id.edt_personnelAddSurname);
        EditText edtMail = this.findViewById(R.id.edt_personnelAddEmail);
        EditText edtPassword = this.findViewById(R.id.edt_personnelAddPassword);

        Spinner spiRoles = this.findViewById(R.id.spi_personnelAddRole);
        ArrayAdapter<Role> adapterRoles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Role.values());
        spiRoles.setAdapter(adapterRoles);

        spiRoles.setSelection(personnel.getRole().ordinal());

        /** METTRE CHAMPS PAR DEFAULT */
        edtName.setText(personnel.getName());
        edtSurname.setText(personnel.getSurname());
        edtMail.setText(personnel.getEmail());

        Button btnAdd = this.findViewById(R.id.btn_personnelAdd);
        btnAdd.setOnClickListener(v -> {

            String name = edtName.getText().toString();
            String surname = edtSurname.getText().toString();
            String email = edtMail.getText().toString();
            String password = edtPassword.getText().toString();

            /** GESTION DES ERREURS */
            if(TextUtils.isEmpty(name)) { edtName.setError(errorEmpty); return; }
            if(TextUtils.isEmpty(surname)) { edtSurname.setError(errorEmpty); return; }
            if(TextUtils.isEmpty(email)) { edtMail.setError(errorEmpty); return; }

            personnel.setName(name);
            personnel.setSurname(surname);
            personnel.setEmail(email);
            personnel.setRole((Role) spiRoles.getSelectedItem());

            if(!password.isEmpty()){
                personnel.setPassword(password);
            }

            personnel.update();

            Intent intent = new Intent(this, PersonnelsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        });
    }
}
