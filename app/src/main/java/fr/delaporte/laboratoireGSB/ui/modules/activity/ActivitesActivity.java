package fr.delaporte.laboratoireGSB.ui.modules.activity;

import android.content.Intent;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;

import fr.delaporte.laboratoireGSB.data.models.ComplementaryActivity;
import fr.delaporte.laboratoireGSB.ui.base.CRUD.CRUDListActivity;

public class ActivitesActivity extends CRUDListActivity<ComplementaryActivity> {

    @Override
    public View.OnClickListener setOnAddListener() {
        return v -> {

            Intent intent = new Intent(this, ActivityAddActivity.class);
            this.startActivity(intent);
        };
    }

    @Override
    public void onUpdateListener(ComplementaryActivity item) {

        Intent intent = new Intent(this, ActivityActivity.class);
        intent.putExtra("activity", item);
        startActivity(intent);
    }

    @Override
    public boolean onDeleteListener(ComplementaryActivity item) {

        item.delete();
        return true;
    }

    @Override
    public String setTextNoData() {
        return "Aucune activités";
    }

    @Override
    public AsyncQuery<ComplementaryActivity> setQuery() {
        return SQLite.select().from(ComplementaryActivity.class).async();
    }
}
