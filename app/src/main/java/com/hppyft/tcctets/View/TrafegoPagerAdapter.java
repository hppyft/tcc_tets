package com.hppyft.tcctets.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hppyft.tcctets.R;

public class TrafegoPagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 3;
    private static final int EIXO_SIMPLES_FRAG_PAGE_NUMBER = 0;
    private static final int EIXO_TD_PAGE_NUMBER = 1;
    private static final int EIXO_TT_FRAG_PAGE_NUMBER = 2;

    private Context mContext;

    private EixoSimplesFrag eixoSimplesFrag;
    private EixoTDFrag eixoTDFrag;
    private EixoTTFrag eixoTTFrag;

    public TrafegoPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case EIXO_SIMPLES_FRAG_PAGE_NUMBER:
                eixoSimplesFrag = new EixoSimplesFrag();
                return eixoSimplesFrag;
            case EIXO_TD_PAGE_NUMBER:
                eixoTDFrag = new EixoTDFrag();
                return eixoTDFrag;
            case EIXO_TT_FRAG_PAGE_NUMBER:
                eixoTTFrag = new EixoTTFrag();
                return eixoTTFrag;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case EIXO_SIMPLES_FRAG_PAGE_NUMBER:
                return mContext.getString(R.string.eixo_simples_frag_title);

            case EIXO_TD_PAGE_NUMBER:
                return mContext.getString(R.string.eixo_td_frag_title);

            case EIXO_TT_FRAG_PAGE_NUMBER:
                return mContext.getString(R.string.eixo_tt_frag_title);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public EixoSimplesFrag getEixoSimplesFrag() {
        return eixoSimplesFrag;
    }

    public EixoTDFrag getEixoTDFrag() {
        return eixoTDFrag;
    }

    public EixoTTFrag getEixoTTFrag() {
        return eixoTTFrag;
    }
}
