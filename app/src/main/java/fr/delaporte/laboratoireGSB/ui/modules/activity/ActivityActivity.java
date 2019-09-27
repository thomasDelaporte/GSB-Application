package fr.delaporte.laboratoireGSB.ui.modules.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.ComplementaryActivity;
import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.helper.Session;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class ActivityActivity extends Activity {

    private String errorEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_activity_add);

        this.errorEmpty = getResources().getString(R.string.txt_empty);

        ComplementaryActivity complementaryActivity = getIntent().getParcelableExtra("activity");
        Personnel personnel = Session.getPersonnel();

        if(complementaryActivity == null || personnel == null){
            onBackPressed(); finish();
        }

        TextView txtSalle = this.findViewById(R.id.txt_activityAddSalle);
        TextView txtBudget = this.findViewById(R.id.txt_activityAddBudget);
        TextView txtTheme = this.findViewById(R.id.txt_activityAddTheme);

        txtSalle.setText(complementaryActivity.getSalle());
        txtBudget.setText(String.valueOf(complementaryActivity.getBudget()));
        txtTheme.setText(complementaryActivity.getTheme());

        Button btnAdd = this.findViewById(R.id.btn_activityAdd);
        btnAdd.setOnClickListener(v -> {

            String salle = txtSalle.getText().toString();
            String budget = txtBudget.getText().toString();
            String theme = txtTheme.getText().toString();

            if(salle.isEmpty()){ txtSalle.setError(errorEmpty); return; }
            if(budget.isEmpty()){ txtBudget.setError(errorEmpty); return; }
            if(theme.isEmpty()){ txtTheme.setError(errorEmpty); return; }

            complementaryActivity.setSalle(salle);
            complementaryActivity.setBudget(Integer.parseInt(budget));
            complementaryActivity.setTheme(theme);
            complementaryActivity.update();

            Intent intent = new Intent(this, ActivitesActivity.class);
            startActivity(intent);
            finish();
        });
    }

}
