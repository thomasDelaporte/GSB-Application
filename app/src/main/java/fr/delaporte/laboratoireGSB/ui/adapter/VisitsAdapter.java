package fr.delaporte.laboratoireGSB.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;

import fr.delaporte.laboratoireGSB.ui.modules.visit.fragments.VisitsFragment;

public class VisitsAdapter extends FragmentStatePagerAdapter {

    private Calendar today;
    private int range;

    /**
     * Constructeur
     *
     * @param fm
     * @param today
     */
    public VisitsAdapter(FragmentManager fm, Calendar today, int range) {
        super(fm);

        this.today = today;
        this.range = range;
    }

    /**
     * Retourne un Fragment avec une date précise.
     *
     * @param position
     * @return VisitsFragment
     */
    @Override
    public Fragment getItem(int position) {

        Calendar dateView = (Calendar) today.clone();
        dateView.add(Calendar.DAY_OF_YEAR, position - range - 1);

        return VisitsFragment.newInstance(dateView, position);
    }

    @Override
    public int getCount() {
        return range * 2 + 1;
    }

    /**
     * Retourne la position de la page de base.
     *
     * @return defaultPage
     */
    public int getDefaultPage() {
        return range + 1;
    }

    public long getMinDate() {

        Calendar base = (Calendar) today.clone();
        base.add(Calendar.DAY_OF_YEAR, - (range + 1));

        return base.getTimeInMillis();
    }

    public long getMaxDate() {

        Calendar base = (Calendar) today.clone();
        base.add(Calendar.DAY_OF_YEAR, range - 1);

        return base.getTimeInMillis();
    }

    public int getRange() {
        return this.range;
    }
}
