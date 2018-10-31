package com.hppyft.tcctets.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hppyft.tcctets.R;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 3;
    private static final int DADOS_FRAG_PAGE_NUMBER = 0;
    private static final int TRAFEGO_FRAG_PAGE_NUMBER = 1;
    private static final int RESULTADOS_FRAG_PAGE_NUMBER = 2;

    private DadosFrag dadosFrag;
    private TrafegoFrag trafegoFrag;
    private ResultadosFrag resultadosFrag;

    private Context mContext;

    public PagerAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DADOS_FRAG_PAGE_NUMBER:
                dadosFrag = new DadosFrag();
                return dadosFrag;
            case TRAFEGO_FRAG_PAGE_NUMBER:
                trafegoFrag = new TrafegoFrag();
                return trafegoFrag;
            case RESULTADOS_FRAG_PAGE_NUMBER:
                resultadosFrag = new ResultadosFrag();
                return resultadosFrag;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case DADOS_FRAG_PAGE_NUMBER:
                return mContext.getString(R.string.dados_frag_title);
            case TRAFEGO_FRAG_PAGE_NUMBER:
                return mContext.getString(R.string.trafego_frag_title);
            case RESULTADOS_FRAG_PAGE_NUMBER:
                return mContext.getString(R.string.resultados_frag_title);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public DadosFrag getDadosFrag() {
        return dadosFrag;
    }

    public TrafegoFrag getTrafegoFrag() {
        return trafegoFrag;
    }

    public ResultadosFrag getResultadosFrag() {
        return resultadosFrag;
    }
}
