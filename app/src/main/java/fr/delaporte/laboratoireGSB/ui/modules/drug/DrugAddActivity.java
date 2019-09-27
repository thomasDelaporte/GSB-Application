package fr.delaporte.laboratoireGSB.ui.modules.drug;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Drug;
import fr.delaporte.laboratoireGSB.data.models.Drug_Table;
import fr.delaporte.laboratoireGSB.ui.base.Activity;

public class DrugAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_drugs_add);

        Button btnAdd = this.findViewById(R.id.btn_drugAdd);
        TextView txtName = this.findViewById(R.id.txt_drugAddName);
        TextView txtQuantity = this.findViewById(R.id.txt_drugAddQuantity);

        btnAdd.setOnClickListener(v -> {

            String name = txtName.getText().toString();
            Integer quantity = Integer.valueOf(txtQuantity.getText().toString());

            /** MEDICAMENT EXISTE DEJA ? */
            long count = SQLite.select()
                    .from(Drug.class)
                    .where(Drug_Table.name.eq(name))
                    .count();

            if(count > 0){
                Toast.makeText(this, "Un médicament avec le même nom existe déjà", Toast.LENGTH_SHORT).show(); return;
            }

            Drug drug = new Drug(name, quantity);
            drug.save();

            Intent intent = new Intent(this, DrugsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
