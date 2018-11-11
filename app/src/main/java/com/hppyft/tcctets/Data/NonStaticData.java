package com.hppyft.tcctets.Data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.util.Pair;

import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.Triple;
import com.hppyft.tcctets.Util.Util;

import java.util.Objects;

public class NonStaticData {

    private static double ajusteETT = 2.85;

    private static Double[] mSomatorioTrafegoES;
    private static Double[] mSomatorioTrafegoETD;
    private static Double[] mSomatorioTrafegoETT;

    private static Double[] mFadigaSuportadaES;
    private static Double[] mFadigaSuportadaETD;
    private static Double[] mFadigaSuportadaETT;

    private static Double[] mErosaoSuportadaES;
    private static Double[] mErosaoSuportadaETD;
    private static Double[] mErosaoSuportadaETT;

    private static Double[] mErosaoPercentES;
    private static Double[] mErosaoPercentETD;
    private static Double[] mErosaoPercentETT;

    private static Double[] mFadigaPercentES;
    private static Double[] mFadigaPercentETD;
    private static Double[] mFadigaPercentETT;

    private static Double mEspessura;


    public static void calculate(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        double proj = sharedPref.getFloat(Keys.projecaoCrescimentoKey, 0);

        mSomatorioTrafegoES = calculateSomatorioTrafegoES(activity, proj);
        mSomatorioTrafegoETD = calculateSomatorioTrafegoETD(activity, proj);
        mSomatorioTrafegoETT = calculateSomatorioTrafegoETT(activity, proj);

        boolean finished = false;
        double espessuraAnterior = 0.0;
        double espessuraAtual = 25.0;
        Triple<Double[], Double[], Double[]> fadigaFinal = new Triple<>(new Double[0], new Double[0], new Double[0]);
        Triple<Double[], Double[], Double[]> erosaoFinal = new Triple<>(new Double[0], new Double[0], new Double[0]);
        Pair<Double[], Double[]> percentFinalES = new Pair<>(new Double[0], new Double[0]);
        Pair<Double[], Double[]> percentFinalETD = new Pair<>(new Double[0], new Double[0]);
        Pair<Double[], Double[]> percentFinalETT = new Pair<>(new Double[0], new Double[0]);

        while (!finished) {
            Triple<Double[], Double[], Double[]> fadigaAtual = calculateFadiga(activity, espessuraAtual);
            Triple<Double[], Double[], Double[]> erosaoAtual = calculateErosao(activity, espessuraAtual);
            Pair<Double[], Double[]> percentES = calculatePorcentagemES(activity, erosaoAtual.getFirst(), fadigaAtual.getFirst());
            Pair<Double[], Double[]> percentETD = calculatePorcentagemETD(activity, erosaoAtual.getSecond(), fadigaAtual.getSecond());
            Pair<Double[], Double[]> percentETT = calculatePorcentagemETT(activity, erosaoAtual.getThird(), fadigaAtual.getThird());

            double erosaoTotal = Util.sumArray(percentES.first) + Util.sumArray(percentETD.first) + Util.sumArray(percentETT.first);
            double fadigaTotal = Util.sumArray(percentES.second) + Util.sumArray(percentETD.second) + Util.sumArray(percentETT.second);

            boolean aceitavel = erosaoTotal > 100.0 || fadigaTotal > 100.0;

            if (espessuraAnterior > espessuraAtual && !aceitavel) {
                espessuraAtual = espessuraAnterior;
                finished = true;
            } else if ((espessuraAnterior > espessuraAtual || espessuraAnterior == 0.0) && aceitavel) {
                fadigaFinal = fadigaAtual;
                erosaoFinal = erosaoAtual;
                percentFinalES = percentES;
                percentFinalETD = percentETD;
                percentFinalETT = percentETT;
                espessuraAnterior = espessuraAtual;
                espessuraAtual--;
            } else if (!aceitavel) {
                espessuraAnterior = espessuraAtual;
                espessuraAtual++;
            } else {
                fadigaFinal = fadigaAtual;
                erosaoFinal = erosaoAtual;
                percentFinalES = percentES;
                percentFinalETD = percentETD;
                percentFinalETT = percentETT;
                finished = true;
            }
        }

        mErosaoPercentES = percentFinalES.first;
        mFadigaPercentES = percentFinalES.second;

        mErosaoPercentETD = percentFinalETD.first;
        mFadigaPercentETD = percentFinalETD.second;

        mErosaoPercentETT = percentFinalETT.first;
        mFadigaPercentETT = percentFinalETT.second;

        mErosaoSuportadaES = erosaoFinal.getFirst();
        mErosaoSuportadaETD = erosaoFinal.getSecond();
        mErosaoSuportadaETT = erosaoFinal.getThird();

        mFadigaSuportadaES = fadigaFinal.getFirst();
        mFadigaSuportadaETD = fadigaFinal.getSecond();
        mFadigaSuportadaETT = fadigaFinal.getThird();

        mEspessura = espessuraAtual;

        //TODO mostrar tela
    }

    private static Pair<Double[], Double[]> calculatePorcentagemES(Activity activity, Double[] erosaoES, Double[] fadigaES) {
        Double[] percentErosaoES = new Double[mSomatorioTrafegoES.length];
        Double[] percentFadigaES = new Double[mSomatorioTrafegoES.length];
        for (int i = 0; i < mSomatorioTrafegoES.length; i++) {
            if (!erosaoES[i].equals(StaticData.INFINITO)) {
                percentErosaoES[i] = mSomatorioTrafegoES[i] * 100.0 / erosaoES[i];
            } else {
                percentErosaoES[i] = 0.0;
            }
            if (!fadigaES[i].equals(StaticData.INFINITO)) {
                percentFadigaES[i] = mSomatorioTrafegoES[i] * 100.0 / fadigaES[i];
            } else {
                percentFadigaES[i] = 0.0;
            }
        }
        return new Pair<>(percentErosaoES, percentFadigaES);
    }

    private static Pair<Double[], Double[]> calculatePorcentagemETD(Activity activity, Double[] erosaoETD, Double[] fadigaETD) {
        Double[] percentErosaoETD = new Double[mSomatorioTrafegoETD.length];
        Double[] percentFadigaETD = new Double[mSomatorioTrafegoETD.length];

        for (int i = 0; i < mSomatorioTrafegoETD.length; i++) {
            if (!erosaoETD[i].equals(StaticData.INFINITO)) {
                percentErosaoETD[i] = mSomatorioTrafegoETD[i] * 100.0 / erosaoETD[i];
            } else {
                percentErosaoETD[i] = 0.0;
            }
            if (!fadigaETD[i].equals(StaticData.INFINITO)) {
                percentFadigaETD[i] = mSomatorioTrafegoETD[i] * 100.0 / fadigaETD[i];
            } else {
                percentFadigaETD[i] = 0.0;
            }
        }
        return new Pair<>(percentErosaoETD, percentFadigaETD);
    }

    private static Pair<Double[], Double[]> calculatePorcentagemETT(Activity activity, Double[] erosaoETT, Double[] fadigaETT) {
        Double[] percentErosaoETT = new Double[mSomatorioTrafegoETT.length];
        Double[] percentFadigaETT = new Double[mSomatorioTrafegoETT.length];

        for (int i = 0; i < mSomatorioTrafegoETT.length; i++) {
            if (!erosaoETT[i].equals(StaticData.INFINITO)) {
                percentErosaoETT[i] = mSomatorioTrafegoETT[i] * 100.0 / erosaoETT[i];
            } else {
                percentErosaoETT[i] = 0.0;
            }
            if (!fadigaETT[i].equals(StaticData.INFINITO)) {
                percentFadigaETT[i] = mSomatorioTrafegoETT[i] * 100.0 / fadigaETT[i];
            } else {
                percentFadigaETT[i] = 0.0;
            }
        }
        return new Pair<>(percentErosaoETT, percentFadigaETT);
    }

    private static Triple<Double[], Double[], Double[]> calculateErosao(Activity activity, double espessura) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        double hCM = espessura;
        double hI = hCM / 2.54;
        double kConvertido = defineK(activity);
        double fsc = NonStaticData.getFSC(activity);
        double L = Math.pow((4000000.0 * (Math.pow(hI, 3.0)) / (11.73 * kConvertido)), 0.25);

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

        double pcSimples;
        double pcTanen;
        double f6;
        double f7;
        double c2;
        if (comAcostamento && comBarras) {
            f6 = 1.0;
            f7 = 1.0;
            c2 = 0.94;
            pcSimples = 0.018 + 72.99 / L + 323.1 / Math.pow(L, 2.0) + 1620.0 / Math.pow(L, 3.0);
            pcTanen = 0.0345 + 146.25 / L - 2385.6 / Math.pow(L, 2.0) + 23848.0 / Math.pow(L, 3.0);
        } else if (comAcostamento) {
            f6 = 1.001 - Math.pow((0.26363 - kConvertido / 3034.5), 2.0);
            f7 = 1.0;
            c2 = 0.94;
            pcSimples = 0.5874 + 65.108 / L + 1130.9 / Math.pow(L, 2.0) - 5245.8 / Math.pow(L, 3.0);
            pcTanen = 1.47 + 102.2 / L - 1072.0 / Math.pow(L, 2.0) + 14451.0 / Math.pow(L, 3.0);
        } else if (comBarras) {
            f7 = 0.896;
            f6 = 1.0;
            c2 = 0.06;
            pcSimples = -0.3019 + 128.85 / L + 1105.8 / Math.pow(L, 2.0) + 3269.1 / Math.pow(L, 3.0);
            pcTanen = 1.258 + 97.491 / L + 1484.1 / Math.pow(L, 2.0) - 180.0 / Math.pow(L, 3.0);
        } else {
            f7 = 0.896;
            f6 = 0.95;
            c2 = 0.06;
            pcSimples = 1.571 + 46.127 / L + 4372.7 / Math.pow(L, 2.0) - 22886.0 / Math.pow(L, 3.0);
            pcTanen = 1.847 + 213.68 / L - 1260.8 / Math.pow(L, 2.0) + 22989.0 / Math.pow(L, 3.0);
        }

        Double[] f5Simples = new Double[10];
        //Comeca em 6 e vai ateh 15
        for (int i = 0, carga = 6; i < f5Simples.length; i++, carga++) {
            double cargaConvertida = (carga * 10.0 * fsc) / 4.45;
            f5Simples[i] = cargaConvertida / 18.0;
        }

        Double[] f5TanenDuplo = new Double[11];
        //Comeca em 13 e vai ateh 23
        for (int i = 0, carga = 13; i < f5TanenDuplo.length; i++, carga++) {
            double cargaConvertida = (carga * 10.0 * fsc) / 4.45;
            f5TanenDuplo[i] = cargaConvertida / 36.0;
        }

        Double[] f5TanenTriplo = new Double[9];
        //Comeca em 22 e vai ateh 30
        for (int i = 0, carga = 22; i < f5TanenTriplo.length; i++, carga++) {
            double cargaConvertida = ((carga * 10.0 * fsc) / 4.45) / 3;
            f5TanenTriplo[i] = cargaConvertida / 36.0;
        }

        double c1 = 1.0 - Math.pow(((kConvertido / 2000.0) * 4.0 / hI), 2.0);

        Double[] erosaoSimples = new Double[10];
        for (int i = 0; i < erosaoSimples.length; i++) {
            double deflexaoSimples = pcSimples * f5Simples[i] * f6 * f7 / kConvertido;
            double pSimples = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoSimples, 2.0) / hI;
            double cXp = c1 * pSimples;
            if (cXp > 9) {
                erosaoSimples[i] = Math.pow(10.0, (14.524 - 6.777 * Math.pow((cXp - 9.0), 0.103) - Math.log10(c2)));
            } else {
                erosaoSimples[i] = StaticData.INFINITO;
            }
        }

        Double[] erosaoTanenDuplo = new Double[11];
        for (int i = 0; i < erosaoTanenDuplo.length; i++) {
            double deflexaoTanen = pcTanen * f5TanenDuplo[i] * f6 * f7 / kConvertido;
            double pTanen = 268.7 * (Math.pow(kConvertido, 1.27)) * Math.pow(deflexaoTanen, 2.0) / hI;
            double cXp = c1 * pTanen;
            if (cXp > 9) {
                erosaoTanenDuplo[i] = Math.pow(10.0, (14.524 - 6.777 * Math.pow((cXp - 9.0), 0.103) - Math.log10(c2)));
            } else {
                erosaoTanenDuplo[i] = StaticData.INFINITO;
            }
        }

        Double[] erosaoTanenTriplo = new Double[9];
        for (int i = 0; i < erosaoTanenTriplo.length; i++) {
            double deflexaoTanen = pcSimples * ajusteETT * f5TanenTriplo[i] * f6 * f7 / kConvertido; //TODO usado pcSimples * 2.73 pra ajuste de ETT
            double pTanen = 268.7 * Math.pow(kConvertido, 1.27) * Math.pow(deflexaoTanen, 2.0) / hI;
            double cXp = c1 * pTanen;
            if (cXp > 9) {
                erosaoTanenTriplo[i] = Math.pow(10.0, (14.524 - 6.777 * Math.pow((cXp - 9.0), 0.103) - Math.log10(c2)));
            } else {
                erosaoTanenTriplo[i] = StaticData.INFINITO;
            }
//            System.out.println("\n cXp -->" + cXp);
//            System.out.println("\n erosaoTanenTriplo[" + i + "] -->" + erosaoTanenTriplo[i]);
        }

        return new Triple<>(erosaoSimples, erosaoTanenDuplo, erosaoTanenTriplo);
    }

    private static Triple<Double[], Double[], Double[]> calculateFadiga(Activity activity, double espessura) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        double hCM = espessura;
        double hI = hCM / 2.54;
        //Pega K
        double kConvertido = defineK(activity);
        //Pega o FSC
        double fsc = NonStaticData.getFSC(activity);
        //Pega o FCT
        double fct = sharedPref.getFloat(Keys.fctKey, 0f);
        //Converte FCT
        double fctConvertido = fct * 145.038;
        //Calcula L
        double L = Math.pow((4000000.0 * (Math.pow(hI, 3.0)) / (11.73 * kConvertido)), 0.25);
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
            meSimples = -1600.0 + 2525.0 * Math.log10(L) + 24.42 * L + 0.204 * Math.pow(L, 2.0);
            meTanen = 3029.0 - 2966.8 * Math.log10(L) + 133.69 * L - 0.0632 * Math.pow(L, 2.0);
            f2 = 0.892 + hI / 85.71 - (Math.pow(hI, 2.0) / 3000.0);
        }


        Double[] f1Simples = new Double[10];
        //Comeca em 6 e vai ateh 15
        double carga = 6.0;
        for (int i = 0; i < f1Simples.length; i++) {
            double cargaConvertida = (carga * 10.0 * fsc) / 4.45;
            double f1 = Math.pow((24.0 / cargaConvertida), 0.06) * cargaConvertida / 18.0;
            f1Simples[i] = f1;
            carga++;
        }

        Double[] f1TanenDuplo = new Double[11];
        //Comeca em 13 e vai ateh o 23
        carga = 13.0;
        for (int i = 0; i < f1TanenDuplo.length; i++) {
            double cargaConvertida = (carga * 10.0 * fsc) / 4.45;
            double f1 = Math.pow((48.0 / cargaConvertida), 0.06) * cargaConvertida / 36.0;
            f1TanenDuplo[i] = f1;
            carga++;
        }

        Double[] f1TanenTriplo = new Double[9];
        //Comeca em 22 e vai ateh o 30
        carga = 22.0;
        for (int i = 0; i < f1TanenTriplo.length; i++) {
            double cargaConvertida = ((carga * 10.0 * fsc) / 4.45) / 3;
            double f1 = Math.pow((48.0 / cargaConvertida), 0.06) * cargaConvertida / 36.0;
            f1TanenTriplo[i] = f1;
            carga++;
        }

        double f3 = 0.894;
        double f4 = 0.953;

        Double[] fadigaRepeticoesSimples = new Double[10];
        //Calcula o numero de repeticoes suportadas pra cada carga
        for (int i = 0; i < fadigaRepeticoesSimples.length; i++) {
            double tensaoSimples = 6.0 * meSimples * f1Simples[i] * f2 * f3 * f4 / Math.pow(hI, 2.0);
            double tensaoSimplesPeloFCT = tensaoSimples / fctConvertido;
            if (tensaoSimplesPeloFCT > 0.55) {
                fadigaRepeticoesSimples[i] = Math.pow(10.0, (11.737 - 12.077 * tensaoSimplesPeloFCT));
            } else if (tensaoSimplesPeloFCT > 0.45 && tensaoSimplesPeloFCT < 0.55) {
                fadigaRepeticoesSimples[i] = Math.pow((4.2577 / (tensaoSimplesPeloFCT - 0.4325)), 3.268);
            } else {
                fadigaRepeticoesSimples[i] = StaticData.INFINITO;
            }
        }

        Double[] fadigaTanenDuplo = new Double[11];
        //Calcula o numero de repeticoes suportadas pra cada carga
        for (int i = 0; i < fadigaTanenDuplo.length; i++) {
            double tensaoTanen = 6.0 * meTanen * f1TanenDuplo[i] * f2 * f3 * f4 / Math.pow(hI, 2.0);
            double tensaoTanenPeloFCT = tensaoTanen / fctConvertido;
            if (tensaoTanenPeloFCT > 0.55) {
                fadigaTanenDuplo[i] = Math.pow(10.0, (11.737 - 12.077 * tensaoTanenPeloFCT));
            } else if (tensaoTanenPeloFCT > 0.45 && tensaoTanenPeloFCT < 0.55) {
                fadigaTanenDuplo[i] = Math.pow((4.2577 / (tensaoTanenPeloFCT - 0.4325)), 3.268);
            } else {
                fadigaTanenDuplo[i] = StaticData.INFINITO;
            }
        }

        Double[] fadigaTanenTriplo = new Double[9];
        //Calcula o numero de repeticoes suportadas pra cada carga
        for (int i = 0; i < fadigaTanenTriplo.length; i++) {
            double tensaoTanen = 6.0 * meTanen * f1TanenTriplo[i] * f2 * f3 * f4 / Math.pow(hI, 2.0); //TODO usado meTanen para ajuste
            double tensaoTanenPeloFCT = tensaoTanen / fctConvertido;
            if (tensaoTanenPeloFCT > 0.55) {
                fadigaTanenTriplo[i] = Math.pow(10.0, (11.737 - 12.077 * tensaoTanenPeloFCT));
            } else if (tensaoTanenPeloFCT > 0.45 && tensaoTanenPeloFCT < 0.55) {
                fadigaTanenTriplo[i] = Math.pow((4.2577 / (tensaoTanenPeloFCT - 0.4325)), 3.268);
            } else {
                fadigaTanenTriplo[i] = StaticData.INFINITO;
            }
        }

        return new Triple<>(fadigaRepeticoesSimples, fadigaTanenDuplo, fadigaTanenTriplo);
    }

    private static Double defineK(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

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

    private static double getFSC(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
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

    private static Long[] getTrafegoEixoSimples(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
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

}
