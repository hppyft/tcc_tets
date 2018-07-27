package com.hppyft.tcctets.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hppyft.tcctets.Listener.CalculateListener;
import com.hppyft.tcctets.Data.Keys;
import com.hppyft.tcctets.Data.StaticData;
import com.hppyft.tcctets.Data.SubBases;
import com.hppyft.tcctets.Listener.OpenDialogListener;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.Util;
import com.hppyft.tcctets.databinding.FragDadosBinding;

import java.util.Objects;


public class DadosFrag extends Fragment implements OpenDialogListener, CalculateListener {

    private FragDadosBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_dados, container, false);

        mBinding.setDialogListener(this);
        mBinding.setCalculateListener(this);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        mBinding.fckField.setText(Float.toString(sharedPref.getFloat(Keys.fckKey, 0f)));
        mBinding.espessuraField.setText(Integer.toString(sharedPref.getInt(Keys.espessuraKey, 0)));
        mBinding.projecaoCrescimentoField.setText(Float.toString(sharedPref.getFloat(Keys.projecaoCrescimentoKey, 0f)));
        mBinding.subBaseRadioGroup.check(sharedPref.getInt(Keys.tipoSubBaseKey, -1));
        mBinding.setSelectedCarga(sharedPref.getInt(Keys.tipoCargaKey, -1));

        return mBinding.getRoot();
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
                })
                .setTitle(R.string.tipo_carga_dialog_title)
                .show(); //TODO Maybe criar um adapter pra deixar a lista mais bonita, falta separador
    }

    @Override
    public void calculate() {
        try {
            saveData();
            //TODO Verificar dados das outras telas antes de calcular e mandar pra tela que faltar dado
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Alguns dos dados não foram preenchidos corretamente", Toast.LENGTH_SHORT).show();
            //TODO toast, dialog ou snackbar?
        }
    }

    private void saveData() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(Keys.fckKey, Float.parseFloat(mBinding.fckField.getText().toString()));
        editor.putInt(Keys.espessuraKey, Integer.parseInt(mBinding.espessuraField.getText().toString()));
        editor.putFloat(Keys.projecaoCrescimentoKey, Float.parseFloat(mBinding.projecaoCrescimentoField.getText().toString()));
        editor.putInt(Keys.tipoSubBaseKey, getSubBaseId());
        editor.putInt(Keys.tipoCargaKey, mBinding.getSelectedCarga());
        editor.apply();
    }

    private int getSubBaseId() {
        int id = mBinding.subBaseRadioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.granular_button:
                return SubBases.GRANULAR;

            case R.id.solo_cimento_button:
                return SubBases.SOLO_CIMENTO;

            case R.id.solo_melhorado_button:
                return SubBases.SOLO_MELHORADO;

            case R.id.concreto_rolado_button:
                return SubBases.CONCRETO_ROLADO;

            default:
                return -1;
        }
    }
}
