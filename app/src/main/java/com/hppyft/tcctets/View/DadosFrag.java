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
import com.hppyft.tcctets.Listener.SubbaseListener;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.Util;
import com.hppyft.tcctets.databinding.FragDadosBinding;

import java.util.Objects;


public class DadosFrag extends Fragment implements OpenDialogListener, CalculateListener, SubbaseListener {

    private FragDadosBinding mBinding;
    public static final Double INFINITO = -1D;

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
            defineK();
            calculateFadiga();
            calculateErosao();
            //TODO Verificar dados das outras telas antes de calcular e mandar pra tela que faltar dado
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Alguns dos dados não foram preenchidos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateErosao() {
        double hCM = 25; //TODO 25cm o qual eh a espessura chutada
        double hI = hCM / 2.54; //TODO aqui eh trocado pra inches
        double kConvertido = 130; //TODO usar defineK()
        double FSC = 1.1; //TODO FSC q eh pego baseado no tipo de carga

        double L = Math.pow((4000000 * (Math.pow(hI, 3)) / (11.73 * k)), 0.25); //TODO jah eh calculado no calculateFadiga, pode ser reaproveitado

        //TODO Pc tem oito valores possives, variando pra eixoSimples/Tanen + C/ ou S/ Acostamento + C/ ou S/ barras de transferencia
        double PcSimplesSemACSemBT = 1.571 + 46.127 / L + 4372.7 / Math.pow(L, 2) - 22886 / Math.pow(L, 3);
        double PcTanenSemACSemBT = 1.847 + 213.68 / L - 1260.8 / Math.pow(L, 2) + 22989 / Math.pow(L, 3);
        double PcSimplesComACSemBT = 0.5874 + 65.78 / L + 1130.9 / Math.pow(L, 2) - 5245.8 / Math.pow(L, 3);
        double PcTanenComACSemBT = 1.47 + 102.2 / L - 1072 / Math.pow(L, 2) + 14451 / Math.pow(L, 3);
        double PcSimplesSemACComBT = -0.3019 + 128.85 / L + 1105.8 / Math.pow(L, 2) + 3269.1 / Math.pow(L, 3);
        double PcTanenSemACComBT = 1.258 + 97.491 / L + 1484.1 / Math.pow(L, 2) - 180 / Math.pow(L, 3);
        double PcSimplesComACComBT = 0.018 + 72.99 / L + 323.1 / Math.pow(L, 2) + 1620 / Math.pow(L, 3);
        double PcTanenComACComBT = 0.0345 + 146.25 / L - 2385.6 / Math.pow(L, 2) + 23848 / Math.pow(L, 3);


        Double[] f5Simples = new Double[10];
        double carga = 6;//TODO comeca em 6 e vai ateh 15
        //TODO calcula f5 para eixo simples
        for (int i = 0; i < 10; i++) {
            double cargaConvertida = (carga * 10 * FSC) / 4.45;
            f5Simples[i] = cargaConvertida / 18;
            carga++;
        }

        Double[] f5Tanen = new Double[10];
        carga = 13;//TODO comeca em 13 e vai ateh 30
        //TODO calcula f5 para eixo tanen
        for (int i = 0; i < 18; i++) {
            double cargaConvertida = (carga * 10 * FSC) / 4.45;
            f5Tanen[i] = cargaConvertida / 36;
            carga++;
        }

        //TODO calcula f6 que depende do acostamento + barra de transferencia, porem soh tem 3 opcoes
        double f6SemACSemBT = 0.95;
        double f6ComACSemBT = 1.001 - Math.pow((0.26363 - kConvertido / 3034.5), 2);
        double f6ComBT = 1;

        //TODO calcula f7 que depende do acostamento
        double f7SemAC = 0.896;
        double f7ComAC = 1;

        //TODO calcula c1
        double c1 = 1 - Math.pow(((kConvertido / 2000) * 4 / hI), 2);

        //TODO calcula c2 que depende do ACOSTAMENTO
        double c2ComAC = 0.94;
        double c2SemAC = 0.06;

        Double[] deflexaoSimples = new Double[10];
        Double[] deflexaoTanen = new Double[18];
        Double[] pSimples = new Double[10];
        Double[] pTanen = new Double[18];
        Double[] nRepeticoesSimples = new Double[10];
        Double[] nRepeticoesTanen = new Double[18];

        //TODO Aqui vai variar no uso do PC, do F6, do F7 e do C2
        for (int i = 0; i < 10; i++) {
            deflexaoSimples[i] = PcSimplesSemACComBT * f5Simples[i] * f6ComBT * f7SemAC / kConvertido;
            pSimples[i] = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoSimples[i], 2) / hI;
            double cXp = c1 * pSimples[i];
            if (cXp > 9) {
                nRepeticoesSimples[i] = Math.pow(10, (14.524 - 6.777 * Math.pow((cXp - 9), 0.103) - Math.log10(c2SemAC)));
            } else {
                nRepeticoesSimples[i] = INFINITO;
            }
        }

        for (int i = 0; i < 18; i++) {
            deflexaoTanen[i] = PcTanenSemACComBT * f5Tanen[i] * f6ComBT * f7SemAC / kConvertido;
            pTanen[i] = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoTanen[i], 2) / hI;
            double cXp = c1 * pTanen[i];
            if (cXp > 9) {
                nRepeticoesTanen[i] = Math.pow(10, (14.524 - 6.777 * Math.pow((cXp - 9), 0.103) - Math.log10(c2SemAC)));
            } else {
                nRepeticoesTanen[i] = INFINITO;
            }
        }
    }

    private void calculateFadiga() {
        double hCM = 25; //TODO 25cm o qual eh a espessura chutada
        double hI = hCM / 2.54; //TODO aqui eh trocado pra inches
        double kConvertido = 130; //TODO usar defineK()
        double FSC = 1.1; //TODO FSC q eh pego baseado no tipo de carga

        double L = Math.pow((4000000 * (Math.pow(hI, 3)) / (11.73 * kConvertido)), 0.25);


        //TODO Se for sem acostamento
        double meSimples = -1600 + 2525 * Math.log10(L) + 24.42 * L + 0.204 * Math.pow(L, 2);
        double meTanen = 3029 - 2966.8 * Math.log10(L) + 133.69 * L - 0.0632 * Math.pow(L, 2);

        //TODO com acostamento
        double MeSimples2 = (-970.4 + 1202.6 * Math.log10(L) + 53.587 * L) * (0.8742 + 0.01088 * Math.pow(kConvertido, 0.447));
        double MeTanen2 = (2005.4 - 1980.9 * Math.log10(L) + 99.008 * L) * (0.8742 + 0.01088 * Math.pow(kConvertido, 0.447));

        //TODO Calcular F1 SIMPLES pra cada carga do eixo simples
        Double[] f1Simples = new Double[10];
        double carga = 6;//TODO comeca em 6 e vai ateh 15
        for (int i = 0; i < 10; i++) {
            double cargaConvertida = (carga * 10 * FSC) / 4.45;
            double f1 = Math.pow((24 / cargaConvertida), 0.06) * cargaConvertida / 18;
            f1Simples[i] = f1;
            carga++;
        }

        //TODO Calcular F1 TANEN pra cada carga do eixo tanen
        Double[] f1Tanen = new Double[18];
        carga = 13; // TODO comeca em 13 e vai ateh o 30
        for (int i = 0; i < 18; i++) {
            double cargaConvertida = (carga * 10 * FSC) / 4.45;
            double f1 = Math.pow((48 / cargaConvertida), 0.06) * cargaConvertida / 36;
            f1Tanen[i] = f1;
            carga++;
        }

        //TODO Calcular F2 s/ acostamento
        double f2Sem = 0.892 + hI / 85.71 - (Math.pow(hI, 2) / 3000);

        //TODO F2 c/ acostamento
        double f2Com = 1;

        double f3 = 0.894;

        double f4 = 0.953;

        Double[] tensaoSimples = new Double[10];
        Double[] tensaoTanen = new Double[18];
        Double[] tensaoSimplesPeloFCT = new Double[10];
        Double[] tensaoTanenPeloFCT = new Double[18];
        Double[] nRepeticoesSimples = new Double[10];
        Double[] nRepeticoesTanen = new Double[18];

        double fct = 4.5; //TODO Aqui pega o FCT dado como parametro
        double fctConvertido = fct * 145.038; //TODO Converter FCT

        //TODO Calcular a tensao pro eixo SIMPLES, pra cada carga; ATENCAO: pode variar pois o uso do ME e o F2 sao condicionais ao acostamento
        //TODO E tambem jah calcular a tensao dividada pelo FCT
        for (int i = 0; i < 10; i++) {
            tensaoSimples[i] = 6 * meSimples * f1Simples[i] * f2Sem * f3 * f4 / Math.pow(hI, 2);
            tensaoSimplesPeloFCT[i] = tensaoSimples[i] / fctConvertido;
            if (tensaoSimplesPeloFCT[i] > 0.55) {
                nRepeticoesSimples[i] = Math.pow(10, (11.737 - 12.077 * tensaoSimplesPeloFCT[i]));
            } else if (tensaoSimplesPeloFCT[i] > 0.45 && tensaoSimplesPeloFCT[i] < 0.55) {
                nRepeticoesSimples[i] = Math.pow((4.2577 / (tensaoSimplesPeloFCT[i] - 0.4325)), 3.268);
            } else {
                nRepeticoesSimples[i] = INFINITO;
            }
        }

        //TODO Calcular a tensao pro eixo TANEN, pra cada carga; ATENCAO: pode variar pois o uso do ME e o F2 sao condicionais ao acostamento
        //TODO E tambem jah calcular a tensao dividada pelo FCT
        for (int i = 0; i < 18; i++) {
            tensaoTanen[i] = 6 * meTanen * f1Tanen[i] * f2Sem * f3 * f4 / Math.pow(hI, 2);
            tensaoTanenPeloFCT[i] = tensaoTanen[i] / fctConvertido;
            if (tensaoTanenPeloFCT[i] > 0.55) {
                nRepeticoesTanen[i] = Math.pow(10, (11.737 - 12.077 * tensaoTanenPeloFCT[i]));
            } else if (tensaoTanenPeloFCT[i] > 0.45 && tensaoTanenPeloFCT[i] < 0.55) {
                nRepeticoesTanen[i] = Math.pow((4.2577 / (tensaoTanenPeloFCT[i] - 0.4325)), 3.268);
            } else {
                nRepeticoesTanen[i] = INFINITO;
            }
        }

        //TODO guardar os valores de nRepeticoes calculadas
    }

    private void defineK() {
//        mBinding.cbrField.getText(),toString(); //TODO aqui pegar CBR do campo
//        mBinding.subBaseRadioGroup //TODO pegar subbase
//        mBinding.espessuraRadioGroup //TODO pegar espessura da subbase

//        StaticData.subBaseGranular10[0] //TODO baseado nos dados anteriores, pergar o K

        double k = 0; //TODO pegar da tabela /\

        double kConvertido = k / 0.27;//TODO divir por 0.27
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


    //PRIVATE METHODS
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

//        System.out.println("SOMATORIO SIMPLES = " + Arrays.toString(somatorioSimples)); //TODO tirar sout
//        System.out.println("SOMATORIO DUPLO = " + Arrays.toString(somatorioDuplo));
//        System.out.println("SOMATORIO TRIPLO = " + Arrays.toString(somatorioTriplo));
    }

    private Double getCrescimentoAnual(Double trafego, Double projecao) {
        return trafego * (1 + (projecao / 100));
    }

}
