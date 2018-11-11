package com.hppyft.tcctets.View;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hppyft.tcctets.Data.NonStaticData;
import com.hppyft.tcctets.Listener.CalculateListener;
import com.hppyft.tcctets.Data.Keys;
import com.hppyft.tcctets.Data.StaticData;
import com.hppyft.tcctets.Listener.OpenDialogListener;
import com.hppyft.tcctets.Listener.RadioListener;
import com.hppyft.tcctets.Listener.SubbaseListener;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.SaveTextOnSharedPrefs;
import com.hppyft.tcctets.Util.Util;
import com.hppyft.tcctets.databinding.FragDadosBinding;

import java.util.Objects;


public class DadosFrag extends Fragment implements OpenDialogListener, CalculateListener, SubbaseListener, RadioListener {

    private FragDadosBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_dados, container, false);

        mBinding.setDialogListener(this);
        mBinding.setCalculateListener(this);
        mBinding.setSubbaseListener(this);
        mBinding.setRadioListener(this);

        loadData();
        addWatchers();

        return mBinding.getRoot();
    }

    private void addWatchers() {
        mBinding.fckField.addTextChangedListener(new SaveTextOnSharedPrefs(Objects.requireNonNull(getActivity()),
                editor -> editor.putFloat(Keys.fctKey, Float.parseFloat(mBinding.fckField.getText().toString()))));

        mBinding.projecaoCrescimentoField.addTextChangedListener(new SaveTextOnSharedPrefs(Objects.requireNonNull(getActivity()),
                editor -> editor.putFloat(Keys.projecaoCrescimentoKey, Float.parseFloat(mBinding.projecaoCrescimentoField.getText().toString()))));

        mBinding.cbrField.addTextChangedListener(new SaveTextOnSharedPrefs(Objects.requireNonNull(getActivity()),
                editor -> editor.putFloat(Keys.cbrKey, Float.parseFloat(mBinding.cbrField.getText().toString()))));
    }

    private void loadData() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        mBinding.fckField.setText(Float.toString(sharedPref.getFloat(Keys.fctKey, 0f)));
        mBinding.projecaoCrescimentoField.setText(Float.toString(sharedPref.getFloat(Keys.projecaoCrescimentoKey, 0f)));
        mBinding.cbrField.setText(Float.toString(sharedPref.getFloat(Keys.cbrKey, 0f)));
        mBinding.transferenciaCargaRadioGroup.check(sharedPref.getInt(Keys.barrasTransferenciaKey, -1));
        mBinding.presencaAcostamentoRadioGroup.check(sharedPref.getInt(Keys.acostamentoKey, -1));

        int carga = sharedPref.getInt(Keys.tipoCargaKey, -1);
        mBinding.setSelectedCarga(carga);
        if (carga != -1) {
            String[] tipoCargaList = StaticData.getTipoCargaList();
            mBinding.tipoCargaEditText.setText(tipoCargaList[carga]);
        }

        int subBase = sharedPref.getInt(Keys.tipoSubBaseKey, -1);
        mBinding.subBaseRadioGroup.check(subBase);
        switch (subBase) {
            case R.id.granular_button:
                onGranularSelected(true);
                mBinding.espessuraRadioGroup.check(sharedPref.getInt(Keys.espessuraKey, -1));
                break;
            case R.id.solo_cimento_button:
                onSoloCimentoSelected(true);
                mBinding.espessuraRadioGroup.check(sharedPref.getInt(Keys.espessuraKey, -1));
                break;
            case R.id.solo_melhorado_button:
                onSoloMelhoradoSelected(true);
                mBinding.espessuraRadioGroup.check(sharedPref.getInt(Keys.espessuraKey, -1));
                break;
            case R.id.concreto_rolado_button:
                onConcretoRoladoSelected(true);
                mBinding.espessuraRadioGroup.check(sharedPref.getInt(Keys.espessuraKey, -1));
                break;
            default:
                break;
        }
    }

    //OnSelected
    @Override
    public void onGranularSelected(boolean checked) {
        if (checked) {
            mBinding.espessuraButton1.setText("10");
            mBinding.espessuraButton2.setText("15");
            mBinding.espessuraButton3.setText("20");
            mBinding.espessuraButton4.setText("30");
            mBinding.setIsSubbaseGranular(true);
            mBinding.setIsSubbaseSelected(true);

            onSubBaseSelected(mBinding.granularButton);
        }
    }

    @Override
    public void onSoloCimentoSelected(boolean checked) {
        if (checked) {
            mBinding.espessuraButton1.setText("10");
            mBinding.espessuraButton2.setText("15");
            mBinding.espessuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);

            onSubBaseSelected(mBinding.soloCimentoButton);
        }
    }

    @Override
    public void onSoloMelhoradoSelected(boolean checked) {
        if (checked) {
            mBinding.espessuraButton1.setText("10");
            mBinding.espessuraButton2.setText("15");
            mBinding.espessuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);

            onSubBaseSelected(mBinding.soloMelhoradoButton);
        }
    }

    @Override
    public void onConcretoRoladoSelected(boolean checked) {
        if (checked) {
            mBinding.espessuraButton1.setText("10");
            mBinding.espessuraButton2.setText("12.5");
            mBinding.espessuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);

            onSubBaseSelected(mBinding.concretoRoladoButton);
        }
    }

    private void onSubBaseSelected(RadioButton subbaseButton) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.tipoSubBaseKey, subbaseButton.getId());
        editor.commit();
    }

    @Override
    public void onEspessuraClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.espessuraKey, radioButton.getId());
        editor.commit();
    }

    @Override
    public void onAcostamentoClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.acostamentoKey, radioButton.getId());
        editor.commit();
    }

    @Override
    public void onTransferenciaClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.barrasTransferenciaKey, radioButton.getId());
        editor.commit();
    }

    @Override
    public void openDialog(AppCompatEditText editText) {
        String[] tipoCargaList = StaticData.getTipoCargaList();
        String[] tipoCargaListFormatted = Util.putStringsBetweenSpaces(tipoCargaList);

        new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setSingleChoiceItems(tipoCargaListFormatted, mBinding.getSelectedCarga(), null)
                .setPositiveButton(R.string.ok_button_label, (dialog, whichButton) -> {
                    dialog.dismiss();
                    mBinding.setSelectedCarga(((AlertDialog) dialog).getListView().getCheckedItemPosition());
                    mBinding.tipoCargaEditText.setText(tipoCargaList[mBinding.getSelectedCarga()]);
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(Keys.tipoCargaKey, mBinding.getSelectedCarga());
                    editor.commit();
                })
                .setTitle(R.string.tipo_carga_dialog_title)
                .show();
    }

    //CALCULOS
    @Override
    public void calculate() {
        try {
            NonStaticData.calculate(Objects.requireNonNull(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Um ou mais dados não foram preenchidos corretamente", Toast.LENGTH_SHORT).show();
        }
    }
}
