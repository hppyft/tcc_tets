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
import com.hppyft.tcctets.databinding.FragEixoTdBinding;

public class EixoTDFrag extends Fragment {

    private FragEixoTdBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_td, container, false);
        return mBinding.getRoot();
    }

    public Integer[] getTrafegoEixoTD() throws Exception {
        Integer[] trafegoES = new Integer[11];
        trafegoES[0] = Integer.parseInt(mBinding.carga13.repeticaoEdit.getText().toString());
        trafegoES[1] = Integer.parseInt(mBinding.carga14.repeticaoEdit.getText().toString());
        trafegoES[2] = Integer.parseInt(mBinding.carga15.repeticaoEdit.getText().toString());
        trafegoES[3] = Integer.parseInt(mBinding.carga16.repeticaoEdit.getText().toString());
        trafegoES[4] = Integer.parseInt(mBinding.carga17.repeticaoEdit.getText().toString());
        trafegoES[5] = Integer.parseInt(mBinding.carga18.repeticaoEdit.getText().toString());
        trafegoES[6] = Integer.parseInt(mBinding.carga19.repeticaoEdit.getText().toString());
        trafegoES[7] = Integer.parseInt(mBinding.carga20.repeticaoEdit.getText().toString());
        trafegoES[8] = Integer.parseInt(mBinding.carga21.repeticaoEdit.getText().toString());
        trafegoES[9] = Integer.parseInt(mBinding.carga22.repeticaoEdit.getText().toString());
        trafegoES[10] = Integer.parseInt(mBinding.carga23.repeticaoEdit.getText().toString());
        return trafegoES;
    }
}