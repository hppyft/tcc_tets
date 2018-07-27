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
import com.hppyft.tcctets.databinding.FragEixoSimplesBinding;

public class EixoSimplesFrag extends Fragment {

    private FragEixoSimplesBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_simples, container, false);
        return mBinding.getRoot();
    }

    public Integer[] getTrafegoEixoSimples() throws Exception {
        Integer[] trafegoES = new Integer[10];
        trafegoES[0] = Integer.parseInt(mBinding.carga6.repeticaoEdit.getText().toString());
        trafegoES[1] = Integer.parseInt(mBinding.carga7.repeticaoEdit.getText().toString());
        trafegoES[2] = Integer.parseInt(mBinding.carga8.repeticaoEdit.getText().toString());
        trafegoES[3] = Integer.parseInt(mBinding.carga9.repeticaoEdit.getText().toString());
        trafegoES[4] = Integer.parseInt(mBinding.carga10.repeticaoEdit.getText().toString());
        trafegoES[5] = Integer.parseInt(mBinding.carga11.repeticaoEdit.getText().toString());
        trafegoES[6] = Integer.parseInt(mBinding.carga12.repeticaoEdit.getText().toString());
        trafegoES[7] = Integer.parseInt(mBinding.carga13.repeticaoEdit.getText().toString());
        trafegoES[8] = Integer.parseInt(mBinding.carga14.repeticaoEdit.getText().toString());
        trafegoES[9] = Integer.parseInt(mBinding.carga15.repeticaoEdit.getText().toString());
        return trafegoES;
    }
}
