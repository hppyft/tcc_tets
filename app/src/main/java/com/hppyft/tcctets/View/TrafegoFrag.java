package com.hppyft.tcctets.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hppyft.tcctets.R;
import com.hppyft.tcctets.databinding.FragTrafegoBinding;

public class TrafegoFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragTrafegoBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_trafego, container, false);

        TrafegoPagerAdapter mPagerAdapter = new TrafegoPagerAdapter(getFragmentManager(), getContext());
        mBinding.trafegoPager.setAdapter(mPagerAdapter);
        mBinding.layoutTabTrafego.setupWithViewPager(mBinding.trafegoPager);
        mBinding.trafegoPager.setOffscreenPageLimit(TrafegoPagerAdapter.NUM_PAGES);

        return mBinding.getRoot();
    }
}
