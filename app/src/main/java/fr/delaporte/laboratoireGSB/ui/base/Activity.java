package fr.delaporte.laboratoireGSB.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fr.delaporte.laboratoireGSB.ui.modules.activity.ActivitesActivity;
import fr.delaporte.laboratoireGSB.ui.modules.drug.DrugsActivity;
import fr.delaporte.laboratoireGSB.ui.modules.login.LoginActivity;
import fr.delaporte.laboratoireGSB.ui.modules.personnel.PersonnelsActivity;
import fr.delaporte.laboratoireGSB.ui.modules.practitioner.PractitionersActivity;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitsActivity;
import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.helper.Session;
import fr.delaporte.laboratoireGSB.helper.enums.Role;

public abstract class Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState,int layoutResID){
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);

        this.setupToolbar();
    }

    protected void setupToolbar(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Toolbar toolbar = this.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        Role role = Session.getUserRole();

        if(role.equals(Role.VISITEURMEDICAL)){
            menuInflater.inflate(R.menu.menu_main, menu);
        } else if(role.equals(Role.DELEGUE)){
            menuInflater.inflate(R.menu.menu_delegue, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_item_visites: {

                Intent intent = new Intent(this, VisitsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;
            }
            case R.id.menu_item_activites: {

                Intent intent = new Intent(this, ActivitesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;
            }
            case android.R.id.home: {

                onBackPressed();
                return true;
            }
            case R.id.menu_item_drugs: {

                Intent intent = new Intent(this, DrugsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;
            }
            case R.id.menu_item_personnels: {

                Intent intent = new Intent(this, PersonnelsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;
            }
            case R.id.menu_item_practitioners: {

                Intent intent = new Intent(this, PractitionersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;
            }
            case R.id.menu_item_logout: {

                Session.logoutUser();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
