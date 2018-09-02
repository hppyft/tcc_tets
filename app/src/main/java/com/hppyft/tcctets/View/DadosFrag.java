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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hppyft.tcctets.Data.NonStaticData;
import com.hppyft.tcctets.Listener.CalculateListener;
import com.hppyft.tcctets.Data.Keys;
import com.hppyft.tcctets.Data.StaticData;
import com.hppyft.tcctets.Data.SubBases;
import com.hppyft.tcctets.Listener.OpenDialogListener;
import com.hppyft.tcctets.Listener.SubbaseListener;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.Util;
import com.hppyft.tcctets.databinding.FragDadosBinding;

import java.nio.MappedByteBuffer;
import java.security.Key;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Objects;


public class DadosFrag extends Fragment implements OpenDialogListener, CalculateListener, SubbaseListener {

    private FragDadosBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_dados, container, false);

        mBinding.setDialogListener(this);
        mBinding.setCalculateListener(this);
        mBinding.setSubbaseListener(this);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        mBinding.fckField.setText(Float.toString(sharedPref.getFloat(Keys.fckKey, 0f)));
        mBinding.projecaoCrescimentoField.setText(Float.toString(sharedPref.getFloat(Keys.projecaoCrescimentoKey, 0f)));
        mBinding.subBaseRadioGroup.check(sharedPref.getInt(Keys.tipoSubBaseKey, -1));
        mBinding.espessuraRadioGroup.check(sharedPref.getInt(Keys.espessuraKey, -1));
        mBinding.setSelectedCarga(sharedPref.getInt(Keys.tipoCargaKey, -1));
        mBinding.cbrField.setText(Float.toString(sharedPref.getFloat(Keys.cbrKey, 0f)));
        mBinding.transferenciaCargaRadioGroup.check(sharedPref.getInt(Keys.tranferenciaCargakey, -1));
        mBinding.presencaAcostamentoRadioGroup.check(sharedPref.getInt(Keys.acostamentoKey, -1));

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
                .show();
    }

    @Override
    public void calculate() {
        try {
            saveData();
//            NonStaticData.getTrafego();
            calculateSomatorioTrafego(); //TODO mudar depois
            //TODO Verificar dados das outras telas antes de calcular e mandar pra tela que faltar dado
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Alguns dos dados não foram preenchidos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGranularSelected(boolean checked) {
        if (checked) {
            mBinding.espesssuraButton1.setText("10");
            mBinding.espesssuraButton2.setText("15");
            mBinding.espesssuraButton3.setText("20");
            mBinding.espesssuraButton4.setText("30");
            mBinding.setIsSubbaseGranular(true);
            mBinding.setIsSubbaseSelected(true);
        }
    }

    @Override
    public void onSoloCimentoSelected(boolean checked) {
        if (checked) {
            mBinding.espesssuraButton1.setText("10");
            mBinding.espesssuraButton2.setText("15");
            mBinding.espesssuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);
        }
    }

    @Override
    public void onSoloMelhoradoSelected(boolean checked) {
        if (checked) {
            mBinding.espesssuraButton1.setText("10");
            mBinding.espesssuraButton2.setText("15");
            mBinding.espesssuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);
        }
    }

    @Override
    public void onConcretoRoladoSelected(boolean checked) {
        if (checked) {
            mBinding.espesssuraButton1.setText("10");
            mBinding.espesssuraButton2.setText("12.5");
            mBinding.espesssuraButton3.setText("20");
            mBinding.setIsSubbaseGranular(false);
            mBinding.setIsSubbaseSelected(true);
        }
    }

    private void saveData() {
        //TODO Ver pq n tah salvando qd fecha o app e abre de novo
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(Keys.fckKey, Float.parseFloat(mBinding.fckField.getText().toString()));
        editor.putFloat(Keys.projecaoCrescimentoKey, Float.parseFloat(mBinding.projecaoCrescimentoField.getText().toString()));
        editor.putInt(Keys.tipoSubBaseKey, getSubBaseId());
        editor.putInt(Keys.espessuraKey, getEspessuraId());
        editor.putInt(Keys.tipoCargaKey, mBinding.getSelectedCarga());
        //TODO colocar dados novos, cbr, espessura e acostamento
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

    private int getEspessuraId() {
        return mBinding.espessuraRadioGroup.getCheckedRadioButtonId();
    }

    private void calculateSomatorioTrafego() {
        //TODO pegar valores reais
        Long[] trafegoSimples = new Long[]{150000l, 11000l, 49000l, 10000l, 14600l, 21100l, 23200l, 11100l, 14600l, 2100l};
        Long[] trafegoDuplo = new Long[]{130000l, 11000l, 26000l, 13500l, 6500l, 27200l, 4300l, 9200l, 9100l, 4400l, 3100l};
        Long[] trafegoTriplo = new Long[]{17000l, 15500l, 14100l, 8200l, 0l, 4800l, 19400l, 12600l, 6200l};

        Double projecaoCrescimento = Double.parseDouble(mBinding.projecaoCrescimentoField.getText().toString());

        Double[] somatorioSimples = new Double[10];
        Double[] somatorioDuplo = new Double[11];
        Double[] somatorioTriplo = new Double[9];

        for (int a = 0; a < 10; a++) {
            somatorioSimples[a] = Double.valueOf(trafegoSimples[a]);
            Double resultadoIteracao = somatorioSimples[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioSimples[a] += resultadoIteracao;
            }
        }

        for (int a = 0; a < 11; a++) {
            somatorioDuplo[a] = Double.valueOf(trafegoDuplo[a]);
            Double resultadoIteracao = somatorioDuplo[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioDuplo[a] += resultadoIteracao;
            }
        }

        for (int a = 0; a < 9; a++) {
            somatorioTriplo[a] = Double.valueOf(trafegoTriplo[a]);
            Double resultadoIteracao = somatorioTriplo[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioTriplo[a] += resultadoIteracao;
            }
        }

        System.out.println("SOMATORIO SIMPLES = " + Arrays.toString(somatorioSimples)); //TODO tirar sout
        System.out.println("SOMATORIO DUPLO = " + Arrays.toString(somatorioDuplo));
        System.out.println("SOMATORIO TRIPLO = " + Arrays.toString(somatorioTriplo));
    }

    private Double getCrescimentoAnual(Double trafego, Double projecao) {
        return trafego * (1 + (projecao / 100));
    }

}
