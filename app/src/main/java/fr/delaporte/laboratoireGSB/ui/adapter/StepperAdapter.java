package fr.delaporte.laboratoireGSB.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class StepperAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> steps = new ArrayList<>();
    private int maxSteps = 0;

    /**
     * Constructeur
     *
     * @param fm
     * @param maxSteps
     */
    public StepperAdapter(FragmentManager fm, Integer maxSteps) {
        super(fm);
        this.maxSteps = maxSteps;
    }

    /**
     * Ajouter une étape.
     * @param fragmentClass
     */
    public void addStep(Fragment fragmentClass){
        this.steps.add(fragmentClass);
    }

    /**
     * Retourne une étape à une position précise.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return steps.get(position);
    }

    @Override
    public int getCount() {
        return this.maxSteps;
    }
}

