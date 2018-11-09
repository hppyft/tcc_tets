package com.hppyft.tcctets.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hppyft.tcctets.Data.Keys;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.SaveTextOnSharedPrefs;
import com.hppyft.tcctets.databinding.FragEixoSimplesBinding;

import java.util.Objects;

public class EixoSimplesFrag extends Fragment {

    private FragEixoSimplesBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_simples, container, false);

        cleanWatchers();
        loadData();
        addWatchers();

        return mBinding.getRoot();
    }

    private void cleanWatchers() {
//        mBinding.carga6.repeticaoEdit.removeTextChangedListener();
//        mBinding.carga7.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga8.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga9.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga10.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga11.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga12.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga13.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga14.repeticaoEdit.removeTextChangedListener(null);
//        mBinding.carga15.repeticaoEdit.removeTextChangedListener(null);
    }

    private void addWatchers() {
        mBinding.carga6.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga6Key, Long.parseLong(mBinding.carga6.repeticaoEdit.getText().toString()))));
        mBinding.carga7.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga7Key, Long.parseLong(mBinding.carga7.repeticaoEdit.getText().toString()))));
        mBinding.carga8.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga8Key, Long.parseLong(mBinding.carga8.repeticaoEdit.getText().toString()))));
        mBinding.carga9.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga9Key, Long.parseLong(mBinding.carga9.repeticaoEdit.getText().toString()))));
        mBinding.carga10.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga10Key, Long.parseLong(mBinding.carga10.repeticaoEdit.getText().toString()))));
        mBinding.carga11.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga11Key, Long.parseLong(mBinding.carga11.repeticaoEdit.getText().toString()))));
        mBinding.carga12.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga12Key, Long.parseLong(mBinding.carga12.repeticaoEdit.getText().toString()))));
        mBinding.carga13.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga13Key, Long.parseLong(mBinding.carga13.repeticaoEdit.getText().toString()))));
        mBinding.carga14.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga14Key, Long.parseLong(mBinding.carga14.repeticaoEdit.getText().toString()))));
        mBinding.carga15.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEsCarga15Key, Long.parseLong(mBinding.carga15.repeticaoEdit.getText().toString()))));
    }

    private void loadData() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        mBinding.carga6.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga6Key, 0)));
        mBinding.carga7.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga7Key, 0)));
        mBinding.carga8.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga8Key, 0)));
        mBinding.carga9.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga9Key, 0)));
        mBinding.carga10.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga10Key, 0)));
        mBinding.carga11.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga11Key, 0)));
        mBinding.carga12.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga12Key, 0)));
        mBinding.carga13.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga13Key, 0)));
        mBinding.carga14.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga14Key, 0)));
        mBinding.carga15.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEsCarga15Key, 0)));
    }
}
