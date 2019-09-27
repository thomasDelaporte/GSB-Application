package fr.delaporte.laboratoireGSB.ui.modules.personnel;

import android.content.Intent;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;

import fr.delaporte.laboratoireGSB.data.models.Personnel;
import fr.delaporte.laboratoireGSB.ui.base.CRUD.CRUDListActivity;

public class PersonnelsActivity extends CRUDListActivity<Personnel> {

    @Override
    public View.OnClickListener setOnAddListener() {
        return v -> {

            Intent intent = new Intent(this, PersonnelAddActivity.class);
            startActivity(intent);
        };
    }

    @Override
    public String setTextNoData() {
        return "Aucun personnel";
    }

    @Override
    public boolean onDeleteListener(Personnel item) {
        return item.delete();
    }

    @Override
    public void onUpdateListener(Personnel item) {

        Intent intent = new Intent(this, PersonnelActivity.class);
        intent.putExtra("personnel", item);
        startActivity(intent);
    }

    @Override
    public AsyncQuery<Personnel> setQuery() {
        return SQLite.select().from(Personnel.class).async();
    }
}
