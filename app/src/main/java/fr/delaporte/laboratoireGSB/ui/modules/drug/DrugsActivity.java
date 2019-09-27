package fr.delaporte.laboratoireGSB.ui.modules.drug;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;

import fr.delaporte.laboratoireGSB.data.models.Drug;
import fr.delaporte.laboratoireGSB.data.models.DrugLeft;
import fr.delaporte.laboratoireGSB.data.models.DrugLeft_Table;
import fr.delaporte.laboratoireGSB.ui.base.CRUD.CRUDListActivity;

public class DrugsActivity extends CRUDListActivity<Drug> {

    @Override
    public View.OnClickListener setOnAddListener() {
        return v -> {

            Intent intent = new Intent(this, DrugAddActivity.class);
            startActivity(intent);
        };
    }

    @Override
    public String setTextNoData() {
        return "Aucun médicament";
    }

    @Override
    public AsyncQuery<Drug> setQuery() {
        return SQLite.select().from(Drug.class).async();
    }

    @Override
    public boolean onDeleteListener(Drug item) {

        long exists = SQLite.select()
                .from(DrugLeft.class)
                .where(DrugLeft_Table.idDrugLeft.eq(item.getIdDrug()))
                .count();

        if(exists > 0){
            Toast.makeText(this, "Impossible de supprimer l'élement", Toast.LENGTH_LONG).show(); return false;
        }

        item.delete();
        return true;
    }
}
