package fr.delaporte.laboratoireGSB.ui.modules.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.ComplementaryActivity;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.ui.base.Activity;
import fr.delaporte.laboratoireGSB.helper.Session;

public class ActivityAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_activity_add);

        Button btnAdd = this.findViewById(R.id.btn_activityAdd);
        TextView txtSalle = this.findViewById(R.id.txt_activityAddSalle);
        TextView txtBudget = this.findViewById(R.id.txt_activityAddBudget);
        TextView txtTheme = this.findViewById(R.id.txt_activityAddTheme);

        btnAdd.setOnClickListener(v -> {

            String salle = txtSalle.getText().toString();
            String budget = txtBudget.getText().toString();
            String theme = txtTheme.getText().toString();

            Personnel personnel = Session.getPersonnel();
            if(personnel == null){
                Toast.makeText(this, "Vous devez être connecté.", Toast.LENGTH_SHORT).show(); return;
            }

            ComplementaryActivity activity = new ComplementaryActivity(salle, Integer.parseInt(budget), theme, personnel);
            activity.save();

            Intent intent = new Intent(this, ActivitesActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
