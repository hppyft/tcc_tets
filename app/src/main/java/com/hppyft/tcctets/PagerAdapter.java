package com.hppyft.tcctets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 1;

    public PagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return new DadosFrag();
        //TODO Fazer sub base como radio
        //TODO Fazer tipo de carga+fsc como dialog
        //TODO Fazer trafego como outra tab
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
