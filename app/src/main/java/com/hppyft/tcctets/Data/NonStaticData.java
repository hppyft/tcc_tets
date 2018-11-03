package com.hppyft.tcctets.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hppyft.tcctets.R;
import com.hppyft.tcctets.View.DadosFrag;

import java.util.Objects;

public class NonStaticData {

    private static Double[] mFadigaRepeticoesSimples;
    private static Double[] mFadigaRepeticoesTanen;
    private static Double[] mErosaoRepeticoesSimples;
    private static Double[] mErosaoRepeticoesTanen;

    public static void setmFadigaRepeticoesSimples(Double[] mFadigaRepeticoesSimples) {
        NonStaticData.mFadigaRepeticoesSimples = mFadigaRepeticoesSimples;
    }

    public static void setmFadigaRepeticoesTanen(Double[] mFadigaRepeticoesTanen) {
        NonStaticData.mFadigaRepeticoesTanen = mFadigaRepeticoesTanen;
    }

    public static void setmErosaoRepeticoesSimples(Double[] mErosaoRepeticoesSimples) {
        NonStaticData.mErosaoRepeticoesSimples = mErosaoRepeticoesSimples;
    }

    public static void setmErosaoRepeticoesTanen(Double[] mErosaoRepeticoesTanen) {
        NonStaticData.mErosaoRepeticoesTanen = mErosaoRepeticoesTanen;
    }

    private static Long[] getTrafegoEixoSimples(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Long[] trafegoES = new Long[10];
        trafegoES[0] = sharedPref.getLong(Keys.trafegoEsCarga6Key, 0);
        trafegoES[1] = sharedPref.getLong(Keys.trafegoEsCarga7Key, 0);
        trafegoES[2] = sharedPref.getLong(Keys.trafegoEsCarga8Key, 0);
        trafegoES[3] = sharedPref.getLong(Keys.trafegoEsCarga9Key, 0);
        trafegoES[4] = sharedPref.getLong(Keys.trafegoEsCarga10Key, 0);
        trafegoES[5] = sharedPref.getLong(Keys.trafegoEsCarga11Key, 0);
        trafegoES[6] = sharedPref.getLong(Keys.trafegoEsCarga12Key, 0);
        trafegoES[7] = sharedPref.getLong(Keys.trafegoEsCarga13Key, 0);
        trafegoES[8] = sharedPref.getLong(Keys.trafegoEsCarga14Key, 0);
        trafegoES[9] = sharedPref.getLong(Keys.trafegoEsCarga15Key, 0);
        return trafegoES;
    }

    private static Long[] getTrafegoEixoTD(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Long[] trafegoETD = new Long[11];
        trafegoETD[0] = sharedPref.getLong(Keys.trafegoEtdCarga13Key, 0);
        trafegoETD[1] = sharedPref.getLong(Keys.trafegoEtdCarga14Key, 0);
        trafegoETD[2] = sharedPref.getLong(Keys.trafegoEtdCarga15Key, 0);
        trafegoETD[3] = sharedPref.getLong(Keys.trafegoEtdCarga16Key, 0);
        trafegoETD[4] = sharedPref.getLong(Keys.trafegoEtdCarga17Key, 0);
        trafegoETD[5] = sharedPref.getLong(Keys.trafegoEtdCarga18Key, 0);
        trafegoETD[6] = sharedPref.getLong(Keys.trafegoEtdCarga19Key, 0);
        trafegoETD[7] = sharedPref.getLong(Keys.trafegoEtdCarga20Key, 0);
        trafegoETD[8] = sharedPref.getLong(Keys.trafegoEtdCarga21Key, 0);
        trafegoETD[9] = sharedPref.getLong(Keys.trafegoEtdCarga22Key, 0);
        trafegoETD[10] = sharedPref.getLong(Keys.trafegoEtdCarga23Key, 0);
        return trafegoETD;
    }

    private static Long[] getTrafegoEixoTT(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Long[] trafegoETT = new Long[9];
        trafegoETT[0] = sharedPref.getLong(Keys.trafegoEttCarga22Key, 0);
        trafegoETT[1] = sharedPref.getLong(Keys.trafegoEttCarga23Key, 0);
        trafegoETT[2] = sharedPref.getLong(Keys.trafegoEttCarga24Key, 0);
        trafegoETT[3] = sharedPref.getLong(Keys.trafegoEttCarga25Key, 0);
        trafegoETT[4] = sharedPref.getLong(Keys.trafegoEttCarga26Key, 0);
        trafegoETT[5] = sharedPref.getLong(Keys.trafegoEttCarga27Key, 0);
        trafegoETT[6] = sharedPref.getLong(Keys.trafegoEttCarga28Key, 0);
        trafegoETT[7] = sharedPref.getLong(Keys.trafegoEttCarga29Key, 0);
        trafegoETT[8] = sharedPref.getLong(Keys.trafegoEttCarga30Key, 0);
        return trafegoETT;
    }

    private static Double[] calculateSomatorioTrafegoES(Activity activity, Double projecaoCrescimento) {
        Long[] trafegoSimples = NonStaticData.getTrafegoEixoSimples(Objects.requireNonNull(activity));
        Double[] somatorioSimples = new Double[10];
        for (int a = 0; a < 10; a++) {
            somatorioSimples[a] = Double.valueOf(trafegoSimples[a]);
            Double resultadoIteracao = somatorioSimples[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioSimples[a] += resultadoIteracao;
            }
        }
        return somatorioSimples;
    }

    private static Double[] calculateSomatorioTrafegoETanen(Activity activity, Double projecaoCrescimento) {
        Double[] somatorioTD = calculateSomatorioTrafegoETD(activity, projecaoCrescimento);
        Double[] somatorioTT = calculateSomatorioTrafegoETT(activity, projecaoCrescimento);
        Double[] somatorioTanen = new Double[18];

        for (int i = 0; i < somatorioTanen.length; i++) {
            if (i < 9) {
                somatorioTanen[i] = somatorioTD[i];
            } else if (i < 11) {
                somatorioTanen[i] = somatorioTD[i] + somatorioTT[i - 9];
            } else {
                somatorioTanen[i] = somatorioTT[i - 9];
            }
        }
        return somatorioTanen;
    }

    private static Double[] calculateSomatorioTrafegoETD(Activity activity, Double projecaoCrescimento) {
        Long[] trafegoDuplo = NonStaticData.getTrafegoEixoTD(Objects.requireNonNull(activity));
        Double[] somatorioDuplo = new Double[11];
        for (int a = 0; a < somatorioDuplo.length; a++) {
            somatorioDuplo[a] = Double.valueOf(trafegoDuplo[a]);
            Double resultadoIteracao = somatorioDuplo[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioDuplo[a] += resultadoIteracao;
            }
        }
        return somatorioDuplo;
    }

    private static Double[] calculateSomatorioTrafegoETT(Activity activity, Double projecaoCrescimento) {
        Long[] trafegoTriplo = NonStaticData.getTrafegoEixoTT(Objects.requireNonNull(activity));
        Double[] somatorioTriplo = new Double[9];

        for (int a = 0; a < somatorioTriplo.length; a++) {
            somatorioTriplo[a] = Double.valueOf(trafegoTriplo[a]);
            Double resultadoIteracao = somatorioTriplo[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioTriplo[a] += resultadoIteracao;
            }
        }
        return somatorioTriplo;
    }

    private static Double getCrescimentoAnual(Double trafego, Double projecao) {
        return trafego * (1 + (projecao / 100));
    }

    public static Double defineK(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        float cbr = sharedPref.getFloat(Keys.cbrKey, 0f);
        int subBase = sharedPref.getInt(Keys.tipoSubBaseKey, -1);
        int espessura = sharedPref.getInt(Keys.espessuraKey, -1);
        int[] column = new int[19];

        switch (subBase) {
            case R.id.granular_button:
                column = getColumnForEspessuraGranular(espessura);
                break;
            case R.id.solo_cimento_button:
                column = getColumnForEspessuraSoloCimento(espessura);
                break;
            case R.id.solo_melhorado_button:
                column = getColumnForEspessuraSoloMelhorado(espessura);
                break;
            case R.id.concreto_rolado_button:
                column = getColumnForEspessuraConcretoRolado(espessura);
                break;
            default:
                break;
        }
        double k = getKFromCloumn(cbr, column);
        //Conversao de k
        return k / 0.27;
    }

    private static double getKFromCloumn(float cbr, int[] column) {
        int kAnterior = 0;
        int cbrAnterior = 0;
        int kPosterior = 0;

        for (int i = 0; i < 19; i++) {
            int cbrToCompare = i + 2;
            if (cbr == cbrToCompare) {
                return column[i];
            } else if (cbr > cbrToCompare) {
                kAnterior = column[i];
                cbrAnterior = cbrToCompare;
            } else {
                kPosterior = column[i];
                break;
            }
        }

        int kDif = kPosterior - kAnterior;
        double cbrDif = cbr - cbrAnterior;
        return (kDif * cbrDif) + kAnterior;
    }

    private static int[] getColumnForEspessuraConcretoRolado(int espessura) {
        switch (espessura) {
            case R.id.espessura_button_1:
                return StaticData.subBaseRolada10;
            case R.id.espessura_button_2:
                return StaticData.subBaseRolada125;
            case R.id.espessura_button_3:
                return StaticData.subBaseRolada20;
            default:
                return null;
        }
    }

    private static int[] getColumnForEspessuraSoloCimento(int espessura) {
        switch (espessura) {
            case R.id.espessura_button_1:
                return StaticData.subBaseCimento10;
            case R.id.espessura_button_2:
                return StaticData.subBaseCimento15;
            case R.id.espessura_button_3:
                return StaticData.subBaseCimento20;
            default:
                return null;
        }
    }

    private static int[] getColumnForEspessuraSoloMelhorado(int espessura) {
        switch (espessura) {
            case R.id.espessura_button_1:
                return StaticData.subBaseMelhorada10;
            case R.id.espessura_button_2:
                return StaticData.subBaseMelhorada15;
            case R.id.espessura_button_3:
                return StaticData.subBaseMelhorada20;
            default:
                return null;
        }
    }

    private static int[] getColumnForEspessuraGranular(int espessura) {
        switch (espessura) {
            case R.id.espessura_button_1:
                return StaticData.subBaseGranular10;
            case R.id.espessura_button_2:
                return StaticData.subBaseGranular15;
            case R.id.espessura_button_3:
                return StaticData.subBaseGranular20;
            case R.id.espessura_button_4:
                return StaticData.subBaseGranular30;
            default:
                return null;
        }
    }

    public static double getFSC(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int carga = sharedPref.getInt(Keys.tipoCargaKey, -1);
        switch (carga) {
            case TipoCarga.FSC10:
                return 1.0;
            case TipoCarga.FSC11:
                return 1.1;
            case TipoCarga.FSC12:
                return 1.2;
            case TipoCarga.FSC15:
                return 1.5;
            default:
                return 0; //TODO jogar exception (lembrar de fazer em todos os lugares onde pega algo do shared prefs)
        }
    }

    public static void calculatePorcentagemTotal(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        double proj = sharedPref.getFloat(Keys.projecaoCrescimentoKey, 0);

        Double[] somatTrafES = calculateSomatorioTrafegoES(activity, proj);
        Double[] somatTrafET = calculateSomatorioTrafegoETanen(activity, proj);

        Double[] porcentErosaoES = new Double[somatTrafES.length];
        Double[] porcentErosaoET = new Double[somatTrafET.length];
        Double[] porcentFadigaES = new Double[somatTrafES.length];
        Double[] porcentFadigaET = new Double[somatTrafET.length];

        Double ErosaoTotal = 0d;
        Double FadigaTotal = 0d;

        for (int i = 0; i < somatTrafES.length; i++) {
            if (!mErosaoRepeticoesSimples[i].equals(DadosFrag.INFINITO)) {
                porcentErosaoES[i] = mErosaoRepeticoesSimples[i] * 100 / somatTrafES[i];
            } else {
                porcentErosaoES[i] = 0d;
            }
            if (!mFadigaRepeticoesSimples[i].equals(DadosFrag.INFINITO)) {
                porcentFadigaES[i] = mFadigaRepeticoesSimples[i] * 100 / somatTrafES[i];
            } else {
                porcentFadigaES[i] = 0d;
            }
            ErosaoTotal += porcentErosaoES[i];
            FadigaTotal += porcentFadigaES[i];
        }
        for (int i = 0; i < somatTrafET.length; i++) {
            if (!mErosaoRepeticoesTanen[i].equals(DadosFrag.INFINITO)) {
                porcentErosaoET[i] = mErosaoRepeticoesTanen[i] * 100 / somatTrafET[i];
            } else {
                porcentErosaoET[i] = 0d;
            }
            if (!mFadigaRepeticoesTanen[i].equals(DadosFrag.INFINITO)) {
                porcentFadigaET[i] = mFadigaRepeticoesTanen[i] * 100 / somatTrafET[i];
            } else {
                porcentFadigaET[i] = 0d;
            }
            ErosaoTotal += porcentErosaoET[i];
            FadigaTotal += porcentFadigaET[i];
        }

        System.out.println("\n <>\nErosao Total = " + ErosaoTotal + "\nFadiga Total = " + FadigaTotal);
    }
}
