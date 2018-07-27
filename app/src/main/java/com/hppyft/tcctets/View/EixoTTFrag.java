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
import com.hppyft.tcctets.databinding.FragEixoTtBinding;

public class EixoTTFrag extends Fragment {

    private FragEixoTtBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_tt, container, false);
        return mBinding.getRoot();
    }

    public Integer[] getTrafegoEixoTT() throws Exception {
        Integer[] trafegoES = new Integer[9];
        trafegoES[0] = Integer.parseInt(mBinding.carga22.repeticaoEdit.getText().toString());
        trafegoES[1] = Integer.parseInt(mBinding.carga23.repeticaoEdit.getText().toString());
        trafegoES[2] = Integer.parseInt(mBinding.carga24.repeticaoEdit.getText().toString());
        trafegoES[3] = Integer.parseInt(mBinding.carga25.repeticaoEdit.getText().toString());
        trafegoES[4] = Integer.parseInt(mBinding.carga26.repeticaoEdit.getText().toString());
        trafegoES[5] = Integer.parseInt(mBinding.carga27.repeticaoEdit.getText().toString());
        trafegoES[6] = Integer.parseInt(mBinding.carga28.repeticaoEdit.getText().toString());
        trafegoES[7] = Integer.parseInt(mBinding.carga29.repeticaoEdit.getText().toString());
        trafegoES[8] = Integer.parseInt(mBinding.carga30.repeticaoEdit.getText().toString());
        return trafegoES;
    }
}