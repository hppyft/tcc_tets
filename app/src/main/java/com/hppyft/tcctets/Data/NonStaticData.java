package com.hppyft.tcctets.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
