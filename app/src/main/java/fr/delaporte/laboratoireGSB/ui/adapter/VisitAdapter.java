package fr.delaporte.laboratoireGSB.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import fr.delaporte.laboratoireGSB.R;
import fr.delaporte.laboratoireGSB.data.models.Visit;

public class VisitAdapter extends ArrayAdapter<Visit> {

    private Context context;
    private List<Visit> visits;

    /**
     * Constructeur
     *
     * @param context
     * @param visits
     */
    public VisitAdapter(Context context, List<Visit> visits) {
        super(context, 0, visits);

        this.context = context;
        this.visits = visits;
    }

    /**
     * Création de la vue.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.row_visite, parent, false);

        Visit currentVisit = visits.get(position);

        CheckBox cbDone = view.findViewById(R.id.cb_visitDone);
        cbDone.setChecked((currentVisit.getDateDone() != null));

        TextView txtName = view.findViewById(R.id.txt_visitPracticien);
        txtName.setText(currentVisit.getPractitioner().getFullname());

        TextView txtDate = view.findViewById(R.id.txt_visitDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        txtDate.setText(dateFormat.format(currentVisit.getDate().getTime()));

        return view;
    }
}
