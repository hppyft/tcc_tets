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
    public static final Double INFINITO = -1D;

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
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
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
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.tipoSubBaseKey, subbaseButton.getId());
        editor.commit();
    }

    @Override
    public void onEspessuraClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.espessuraKey, radioButton.getId());
        editor.commit();
    }

    @Override
    public void onAcostamentoClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Keys.acostamentoKey, radioButton.getId());
        editor.commit();
    }

    @Override
    public void onTransferenciaClicked(RadioButton radioButton) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
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
                    SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
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
            calculateFadiga();
            calculateErosao();
            calculatePorcentagemTotal();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Um ou mais dados não foram preenchidos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculatePorcentagemTotal() {
        NonStaticData.calculatePorcentagemTotal(Objects.requireNonNull(getActivity()));
    }

    private void calculateErosao() {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        //TODO 25cm o qual eh a espessura chutada
        double hCM = 25;
        //Conversao para inches
        double hI = hCM / 2.54;
        double kConvertido = defineK();
        double fsc = NonStaticData.getFSC(Objects.requireNonNull(getActivity()));
        double L = Math.pow((4000000 * (Math.pow(hI, 3)) / (11.73 * kConvertido)), 0.25);

        boolean comAcostamento = false;
        switch (sharedPref.getInt(Keys.acostamentoKey, -1)) {
            case R.id.com_acostamento_button:
                comAcostamento = true;
                break;
            case R.id.sem_acostamento_button:
                comAcostamento = false;
                break;
            default:
                //TODO exception
                break;
        }

        boolean comBarras = false;
        switch (sharedPref.getInt(Keys.barrasTransferenciaKey, -1)) {
            case R.id.barras_button:
                comBarras = true;
                break;
            case R.id.entrosagem_button:
                comBarras = false;
                break;
            default:
                //TODO exception
                break;
        }

        double pcSimples = 0;
        double pcTanen = 0;
        double f6 = 0;
        double f7 = 0;
        double c2 = 0;
        if (comAcostamento && comBarras) {
            f6 = 1;
            f7 = 1;
            c2 = 0.94;
            pcSimples = 0.018 + 72.99 / L + 323.1 / Math.pow(L, 2) + 1620 / Math.pow(L, 3);
            pcTanen = 0.0345 + 146.25 / L - 2385.6 / Math.pow(L, 2) + 23848 / Math.pow(L, 3);
        } else if (comAcostamento) {
            f6 = 1.001 - Math.pow((0.26363 - kConvertido / 3034.5), 2);
            f7 = 1;
            c2 = 0.94;
            pcSimples = 0.5874 + 65.108 / L + 1130.9 / Math.pow(L, 2) - 5245.8 / Math.pow(L, 3);
            pcTanen = 1.47 + 102.2 / L - 1072 / Math.pow(L, 2) + 14451 / Math.pow(L, 3);
        } else if (comBarras) {
            f7 = 0.896;
            f6 = 1;
            c2 = 0.06;
            pcSimples = -0.3019 + 128.85 / L + 1105.8 / Math.pow(L, 2) + 3269.1 / Math.pow(L, 3);
            pcTanen = 1.258 + 97.491 / L + 1484.1 / Math.pow(L, 2) - 180 / Math.pow(L, 3);
        } else {
            f7 = 0.896;
            f6 = 0.95;
            c2 = 0.06;
            pcSimples = 1.571 + 46.127 / L + 4372.7 / Math.pow(L, 2) - 22886 / Math.pow(L, 3);
            pcTanen = 1.847 + 213.68 / L - 1260.8 / Math.pow(L, 2) + 22989 / Math.pow(L, 3);
        }

        Double[] f5Simples = new Double[10];
        //Comeca em 6 e vai ateh 15
        for (int i = 0, carga = 6; i < 10; i++, carga++) {
            double cargaConvertida = (carga * 10 * fsc) / 4.45;
            f5Simples[i] = cargaConvertida / 18;
        }

        Double[] f5Tanen = new Double[18];
        //Comeca em 13 e vai ateh 30
        for (int i = 0, carga = 13; i < 18; i++, carga++) {
            double cargaConvertida = (carga * 10 * fsc) / 4.45;
            f5Tanen[i] = cargaConvertida / 36;
        }

        double c1 = 1 - Math.pow(((kConvertido / 2000) * 4 / hI), 2);

        Double[] nRepeticoesSimples = new Double[10];
        Double[] nRepeticoesTanen = new Double[18];

        for (int i = 0; i < nRepeticoesSimples.length; i++) {
            double deflexaoSimples = pcSimples * f5Simples[i] * f6 * f7 / kConvertido;
            double pSimples = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoSimples, 2) / hI;
            double cXp = c1 * pSimples;
            if (cXp > 9) {
                nRepeticoesSimples[i] = Math.pow(10, (14.524 - 6.777 * Math.pow((cXp - 9), 0.103) - Math.log10(c2)));
            } else {
                nRepeticoesSimples[i] = INFINITO;
            }
        }

        NonStaticData.setmErosaoRepeticoesSimples(nRepeticoesSimples);

        for (int i = 0; i < nRepeticoesTanen.length; i++) {
            double deflexaoTanen = pcTanen * f5Tanen[i] * f6 * f7 / kConvertido;
            double pTanen = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoTanen, 2) / hI;
            double cXp = c1 * pTanen;
            if (cXp > 9) {
                nRepeticoesTanen[i] = Math.pow(10, (14.524 - 6.777 * Math.pow((cXp - 9), 0.103) - Math.log10(c2)));
            } else {
                nRepeticoesTanen[i] = INFINITO;
            }
        }

        NonStaticData.setmErosaoRepeticoesTanen(nRepeticoesTanen);
    }

    private void calculateFadiga() {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        //TODO 25cm o qual eh a espessura chutada
        double hCM = 25;
        //Converte a espessura para inches
        double hI = hCM / 2.54;
        //Pega K
        double kConvertido = defineK();
        //Pega o FSC
        double fsc = NonStaticData.getFSC(Objects.requireNonNull(getActivity()));
        //Pega o FCT
        double fct = sharedPref.getFloat(Keys.fctKey, 0f);
        //Converte FCT
        double fctConvertido = fct * 145.038;
        //Calcula L
        double L = Math.pow((4000000 * (Math.pow(hI, 3)) / (11.73 * kConvertido)), 0.25);
        //Define se tem ou nao acostamento
        boolean comAcostamento = false;
        switch (sharedPref.getInt(Keys.acostamentoKey, -1)) {
            case R.id.com_acostamento_button:
                comAcostamento = true;
                break;
            case R.id.sem_acostamento_button:
                comAcostamento = false;
                break;
            default:
                //TODO exception
                break;
        }

        double meSimples;
        double meTanen;
        double f2;

        if (comAcostamento) {
            meSimples = (-970.4 + 1202.6 * Math.log10(L) + 53.587 * L) * (0.8742 + 0.01088 * Math.pow(kConvertido, 0.447));
            meTanen = (2005.4 - 1980.9 * Math.log10(L) + 99.008 * L) * (0.8742 + 0.01088 * Math.pow(kConvertido, 0.447));
            f2 = 1;
        } else {
            meSimples = -1600 + 2525 * Math.log10(L) + 24.42 * L + 0.204 * Math.pow(L, 2);
            meTanen = 3029 - 2966.8 * Math.log10(L) + 133.69 * L - 0.0632 * Math.pow(L, 2);
            f2 = 0.892 + hI / 85.71 - (Math.pow(hI, 2) / 3000);
        }


        //Calcular F1 SIMPLES pra cada carga do eixo simples
        Double[] f1Simples = new Double[10];
        //Comeca em 6 e vai ateh 15
        double carga = 6;
        for (int i = 0; i < 10; i++) {
            double cargaConvertida = (carga * 10 * fsc) / 4.45;
            double f1 = Math.pow((24 / cargaConvertida), 0.06) * cargaConvertida / 18;
            f1Simples[i] = f1;
            carga++;
        }

        //Calcula F1 TANEN pra cada carga do eixo tanen
        Double[] f1Tanen = new Double[18];
        //Comeca em 13 e vai ateh o 30
        carga = 13;
        for (int i = 0; i < 18; i++) {
            double cargaConvertida = (carga * 10 * fsc) / 4.45;
            double f1 = Math.pow((48 / cargaConvertida), 0.06) * cargaConvertida / 36;
            f1Tanen[i] = f1;
            carga++;
        }

        double f3 = 0.894;
        double f4 = 0.953;

        Double[] nRepeticoesSimples = new Double[10];
        Double[] nRepeticoesTanen = new Double[18];

        //Calcula o numero de repeticoes suportadas pra cada carga
        for (int i = 0; i < nRepeticoesSimples.length; i++) {
            double tensaoSimples = 6 * meSimples * f1Simples[i] * f2 * f3 * f4 / Math.pow(hI, 2);
            double tensaoSimplesPeloFCT = tensaoSimples / fctConvertido;
            if (tensaoSimplesPeloFCT > 0.55) {
                nRepeticoesSimples[i] = Math.pow(10, (11.737 - 12.077 * tensaoSimplesPeloFCT));
            } else if (tensaoSimplesPeloFCT > 0.45 && tensaoSimplesPeloFCT < 0.55) {
                nRepeticoesSimples[i] = Math.pow((4.2577 / (tensaoSimplesPeloFCT - 0.4325)), 3.268);
            } else {
                nRepeticoesSimples[i] = INFINITO;
            }
        }

        NonStaticData.setmFadigaRepeticoesSimples(nRepeticoesSimples);

        //Calcula o numero de repeticoes suportadas pra cada carga
        for (int i = 0; i < nRepeticoesTanen.length; i++) {
            double tensaoTanen = 6 * meTanen * f1Tanen[i] * f2 * f3 * f4 / Math.pow(hI, 2);
            double tensaoTanenPeloFCT = tensaoTanen / fctConvertido;
            if (tensaoTanenPeloFCT > 0.55) {
                nRepeticoesTanen[i] = Math.pow(10, (11.737 - 12.077 * tensaoTanenPeloFCT));
            } else if (tensaoTanenPeloFCT > 0.45 && tensaoTanenPeloFCT < 0.55) {
                nRepeticoesTanen[i] = Math.pow((4.2577 / (tensaoTanenPeloFCT - 0.4325)), 3.268);
            } else {
                nRepeticoesTanen[i] = INFINITO;
            }
        }

        NonStaticData.setmFadigaRepeticoesTanen(nRepeticoesTanen);
    }

    private double defineK() {
        return NonStaticData.defineK(Objects.requireNonNull(getActivity()));
    }
}
