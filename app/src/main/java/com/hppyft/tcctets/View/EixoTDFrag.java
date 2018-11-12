package com.hppyft.tcctets.View;

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
import com.hppyft.tcctets.databinding.FragEixoTdBinding;

public class EixoTDFrag extends Fragment {

    private FragEixoTdBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_eixo_td, container, false);

        loadData();
        addWatchers();

        return mBinding.getRoot();
    }

    private void addWatchers() {
        mBinding.carga13.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga13Key, Long.parseLong(mBinding.carga13.repeticaoEdit.getText().toString()))));
        mBinding.carga14.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga14Key, Long.parseLong(mBinding.carga14.repeticaoEdit.getText().toString()))));
        mBinding.carga15.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga15Key, Long.parseLong(mBinding.carga15.repeticaoEdit.getText().toString()))));
        mBinding.carga16.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga16Key, Long.parseLong(mBinding.carga16.repeticaoEdit.getText().toString()))));
        mBinding.carga17.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga17Key, Long.parseLong(mBinding.carga17.repeticaoEdit.getText().toString()))));
        mBinding.carga18.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga18Key, Long.parseLong(mBinding.carga18.repeticaoEdit.getText().toString()))));
        mBinding.carga19.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga19Key, Long.parseLong(mBinding.carga19.repeticaoEdit.getText().toString()))));
        mBinding.carga20.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga20Key, Long.parseLong(mBinding.carga20.repeticaoEdit.getText().toString()))));
        mBinding.carga21.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga21Key, Long.parseLong(mBinding.carga21.repeticaoEdit.getText().toString()))));
        mBinding.carga22.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga22Key, Long.parseLong(mBinding.carga22.repeticaoEdit.getText().toString()))));
        mBinding.carga23.repeticaoEdit.addTextChangedListener(new SaveTextOnSharedPrefs(getActivity(),
                editor -> editor.putLong(Keys.trafegoEtdCarga23Key, Long.parseLong(mBinding.carga23.repeticaoEdit.getText().toString()))));
    }

    private void loadData() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        mBinding.carga13.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga13Key, 0)));
        mBinding.carga14.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga14Key, 0)));
        mBinding.carga15.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga15Key, 0)));
        mBinding.carga16.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga16Key, 0)));
        mBinding.carga17.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga17Key, 0)));
        mBinding.carga18.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga18Key, 0)));
        mBinding.carga19.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga19Key, 0)));
        mBinding.carga20.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga20Key, 0)));
        mBinding.carga21.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga21Key, 0)));
        mBinding.carga22.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga22Key, 0)));
        mBinding.carga23.repeticaoEdit.setText(Long.toString(sharedPref.getLong(Keys.trafegoEtdCarga23Key, 0)));
    }
}