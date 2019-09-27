package fr.delaporte.laboratoireGSB.ui.modules.practitioner;

import android.content.Intent;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;

import fr.delaporte.laboratoireGSB.data.models.Practitioner;
import fr.delaporte.laboratoireGSB.ui.base.CRUD.CRUDListActivity;

public class PractitionersActivity extends CRUDListActivity<Practitioner> {

    @Override
    public View.OnClickListener setOnAddListener() {
        return v -> {
            Intent intent = new Intent(this, PractitionerAddActivity.class);
            startActivity(intent);
        };
    }

    @Override
    public void onUpdateListener(Practitioner item) {

        Intent intent = new Intent(this, PractitionerActivity.class);
        intent.putExtra("practitioner", item);
        startActivity(intent);
    }

    @Override
    public boolean onDeleteListener(Practitioner item) {
        return item.delete();
    }

    @Override
    public String setTextNoData() {
        return "Aucun practicien";
    }

    @Override
    public AsyncQuery<Practitioner> setQuery() {
        return SQLite.select()
                .from(Practitioner.class)
                .async();
    }
}
