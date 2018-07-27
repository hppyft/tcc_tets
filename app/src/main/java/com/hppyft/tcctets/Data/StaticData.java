package com.hppyft.tcctets.Data;

public class StaticData {

    private static String[] tipoCargaList;

    public static String[] getTipoCargaList() {
        if (tipoCargaList == null) {
            tipoCargaList = new String[]{
                    "FSC 1,0 - Ruas com tráfego com pequena porcentagem de caminhões e pisos e condições semelhantes de tráfego (estacionamentos, por exemplo)",
                    "FSC 1,1 - Estradas e vias com moderada frequência de caminhões",
                    "FSC 1,2 - Alto volume de caminhões",
                    "FSC até 1,5 - Pavimentos que necessitam de um desempenho acima do normal"};
        }
        return tipoCargaList;
    }
}
