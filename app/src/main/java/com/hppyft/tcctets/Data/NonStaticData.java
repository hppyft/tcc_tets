package com.hppyft.tcctets.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.hppyft.tcctets.R;

import java.util.Objects;

public class NonStaticData {

    public static Long[] getTrafegoEixoSimples(Activity activity) {
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

    public static Long[] getTrafegoEixoTD(Activity activity) {
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

    public static Long[] getTrafegoEixoTT(Activity activity) {
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

    public static Double[] calculateSomatorioTrafegoES(Activity activity, Double projecaoCrescimento) {
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

    public static Double[] calculateSomatorioTrafegoETD(Activity activity, Double projecaoCrescimento) {
        Long[] trafegoDuplo = NonStaticData.getTrafegoEixoTD(Objects.requireNonNull(activity));
        Double[] somatorioDuplo = new Double[11];
        for (int a = 0; a < 11; a++) {
            somatorioDuplo[a] = Double.valueOf(trafegoDuplo[a]);
            Double resultadoIteracao = somatorioDuplo[a];
            for (int b = 1; b <= 19; b++) {
                resultadoIteracao = getCrescimentoAnual(resultadoIteracao, projecaoCrescimento);
                somatorioDuplo[a] += resultadoIteracao;
            }
        }
        return somatorioDuplo;
    }

    public static Double[] calculateSomatorioTrafegoETT(Activity activity, Double projecaoCrescimento) {
        Long[] trafegoTriplo = NonStaticData.getTrafegoEixoTT(Objects.requireNonNull(activity));
        Double[] somatorioTriplo = new Double[9];

        for (int a = 0; a < 9; a++) {
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
                subBase = SubBases.GRANULAR;
                column = getColumnForEspessuraGranular(espessura);
                break;
            case R.id.solo_cimento_button:
                subBase = SubBases.SOLO_CIMENTO;
                column = getColumnForEspessuraSoloCimento(espessura);
                break;
            case R.id.solo_melhorado_button:
                subBase = SubBases.SOLO_MELHORADO;
                column = getColumnForEspessuraSoloMelhorado(espessura);
                break;
            case R.id.concreto_rolado_button:
                subBase = SubBases.CONCRETO_ROLADO;
                column = getColumnForEspessuraConcretoRolado(espessura);
                break;
            default:
                break;
        }
        double k = getKFromCloumn(cbr, column);
        return k / 0.27; //TODO divir por 0.27
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
}
