package fr.delaporte.laboratoireGSB.ui.modules.visit.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.PropertyFactory;

import fr.delaporte.laboratoireGSB.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.delaporte.laboratoireGSB.helper.enums.Role;
import fr.delaporte.laboratoireGSB.ui.modules.visit.VisitActivity;
import fr.delaporte.laboratoireGSB.ui.adapter.VisitAdapter;
import fr.delaporte.laboratoireGSB.data.models.Visit;
import fr.delaporte.laboratoireGSB.data.models.Visit_Table;
import fr.delaporte.laboratoireGSB.helper.Session;

public class VisitsFragment extends Fragment {

    private int idPersonnel = Session.getUserID();
    private Calendar date;
    private int pos;
    private List<Visit> visitsOfDay = new ArrayList<>();

    public static final SimpleDateFormat visitDateFormat = new SimpleDateFormat("dd/MM/YY", Locale.FRANCE);

    public static VisitsFragment newInstance(Calendar date, int pos){
        VisitsFragment fragment = new VisitsFragment();
        fragment.setDate(date);
        fragment.loadVisits();
        fragment.pos = pos;

        return fragment;
    }

    public Calendar getDate(){
        return this.date;
    }

    public void setDate(Calendar date){
        this.date = date;
    }

    public void loadVisits(){

        String dayYearFormated = String.format(Locale.FRANCE, "%03d", date.get(Calendar.DAY_OF_YEAR));

        @SuppressWarnings("unchecked")
        Where<Visit> query = SQLite.select()
                .from(Visit.class)
                .where(this.strftime("%j", Visit_Table.date.getNameAlias() + "/ 1000", "unixepoch").eq(dayYearFormated))
                .orderBy(Visit_Table.date, true);

        if(Session.getUserRole().equals(Role.VISITEURMEDICAL)){
            query = query.and(Visit_Table.visiteurMedical_idPersonnel.eq(idPersonnel));
        }

        this.visitsOfDay = query.queryList();
    }

    public Method strftime(String formatString, String timeString, String... modifiers){
        List<IProperty> propertyList = new ArrayList<>();
        propertyList.add(PropertyFactory.from(formatString));
        propertyList.add(PropertyFactory.from(new QueryBuilder<>().appendNotEmpty(timeString)));

        for (String modifier : modifiers) {
            propertyList.add(PropertyFactory.from(modifier));
        }
        return new Method("strftime", propertyList.toArray(new IProperty[propertyList.size()]));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_visits, container, false);

        if(!visitsOfDay.isEmpty()){

            TextView txtVisitNoData = view.findViewById(R.id.txt_visitsNoData);
            txtVisitNoData.setVisibility(View.GONE) ;
        }

        ListView lvVisits = view.findViewById(R.id.lv_Visites);
        VisitAdapter visitsAdapter = new VisitAdapter(getContext(), visitsOfDay);

        lvVisits.setAdapter(visitsAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.rl_Visites);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });

        lvVisits.setOnItemClickListener((parent, view1, position, id) -> {

            Visit visit = visitsAdapter.getItem(position);
            if(Session.getUserRole().equals(Role.DELEGUE) && !visit.isDone()){
                return;
            }

            Intent intent = new Intent(getContext(), VisitActivity.class);
            intent.putExtra("visit", visit);
            startActivity(intent);
        });

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
