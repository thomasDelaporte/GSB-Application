package fr.delaporte.laboratoireGSB.ui.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.HashMap;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitsActivity;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.data.models.Personnel_Table;
import fr.delaporte.laboratoireGSB.helper.Encryption;
import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.helper.Session;

public class LoginActivity extends AppCompatActivity {

    public Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edtMail = findViewById(R.id.input_email);
        final EditText edtPassword = findViewById(R.id.input_password);

        if(Session.isLoggedIn()) {

            int userID = Session.getUserID();
            Role userRole = Session.getUserRole();

            Personnel personnel = SQLite.select()
                    .from(Personnel.class)
                    .where(Personnel_Table.idPersonnel.eq(userID)).querySingle();

            if(personnel != null && personnel.getRole().equals(userRole)){
                this.launchMainActivity();
            } else {

                Session.logoutUser();
                Toast.makeText(getBaseContext(), "Une erreur avec le compte utilisateur c'est produit", Toast.LENGTH_LONG).show();
            }
        }
        else if(Session.isRembered()){

            HashMap<String, Object> user = Session.getUserDetails();
            edtMail.setText((String) user.get(Session.KEY_EMAIL));
        }

        Button btnLogin = this.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> {

            String mail = edtMail.getText().toString();
            String password = edtPassword.getText().toString();

            Personnel tempPersonnel = SQLite.select()
                    .from(Personnel.class)
                    .where(Personnel_Table.email.eq(mail)).querySingle();

            if(tempPersonnel == null){
                Toast.makeText(this, "Aucun utilisateur avec cette email.", Toast.LENGTH_SHORT).show(); return;
            }

            String tempPassword = Encryption.encryptSHA512(password);
            if(!tempPersonnel.getPassword().equals(tempPassword)){
                Toast.makeText(this, "Le mot de passe ne correspond pas.", Toast.LENGTH_SHORT).show(); return;
            }

            Session.loginUser(tempPersonnel);

            if(Session.isLoggedIn()){
                this.launchMainActivity();
            }
        });
    }

    private void launchMainActivity(){

        HashMap<String, Object> user = Session.getUserDetails();
        Role role = Session.getUserRole();

        Intent intent;
        if(role.equals(Role.VISITEURMEDICAL)){
            intent = new Intent(LoginActivity.this, VisitsActivity.class);
        } else intent = new Intent(this, VisitsActivity.class);

        startActivity(intent);
        finish();
    }
}
