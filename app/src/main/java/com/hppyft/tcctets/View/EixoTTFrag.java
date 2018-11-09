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
import com.hppyft.tcctets.databinding.FragEixoTtBinding;

import java.util.Objects;

public class EixoTTFrag extends Fragment {

    private FragEixoTtBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_tt, container, false);

        loadData();
        addWatchers();

        return mBinding.getRoot();
    }

    private void addWatchers() {
        mBinding.carga22.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga22Key, Long.parseLong(mBinding.carga22.repeticaoEdit.getText().toString()))));
        mBinding.carga23.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga23Key, Long.parseLong(mBinding.carga23.repeticaoEdit.getText().toString()))));
        mBinding.carga24.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga24Key, Long.parseLong(mBinding.carga24.repeticaoEdit.getText().toString()))));
        mBinding.carga25.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga25Key, Long.parseLong(mBinding.carga25.repeticaoEdit.getText().toString()))));
        mBinding.carga26.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga26Key, Long.parseLong(mBinding.carga26.repeticaoEdit.getText().toString()))));
        mBinding.carga27.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga27Key, Long.parseLong(mBinding.carga27.repeticaoEdit.getText().toString()))));
        mBinding.carga28.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga28Key, Long.parseLong(mBinding.carga28.repeticaoEdit.getText().toString()))));
        mBinding.carga29.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga29Key, Long.parseLong(mBinding.carga29.repeticaoEdit.getText().toString()))));
        mBinding.carga30.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEttCarga30Key, Long.parseLong(mBinding.carga30.repeticaoEdit.getText().toString()))));
    }

    private void loadData() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        mBinding.carga22.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga22Key, 0)));
        mBinding.carga23.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga23Key, 0)));
        mBinding.carga24.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga24Key, 0)));
        mBinding.carga25.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga25Key, 0)));
        mBinding.carga26.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga26Key, 0)));
        mBinding.carga27.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga27Key, 0)));
        mBinding.carga28.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga28Key, 0)));
        mBinding.carga29.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga29Key, 0)));
        mBinding.carga30.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEttCarga30Key, 0)));
    }
}