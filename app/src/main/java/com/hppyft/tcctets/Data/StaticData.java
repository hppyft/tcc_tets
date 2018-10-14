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

    public static int[] subBaseGranular10 = {19, 27, 34, 38, 42, 45, 48, 52, 54, 56, 58, 59, 61, 62, 64, 65, 66, 67, 68};
    public static int[] subBaseGranular15 = {22, 31, 38, 42, 46, 50, 53, 56, 58, 60, 62, 63, 65, 66, 68, 69, 70, 71, 72};
    public static int[] subBaseGranular20 = {27, 37, 44, 49, 53, 56, 60, 63, 65, 67, 69, 70, 72, 73, 75, 76, 77, 78, 79};
    public static int[] subBaseGranular30 = {33, 45, 54, 59, 65, 69, 72, 76, 79, 81, 84, 85, 87, 88, 91, 92, 93, 94, 96};
    public static int[] subBaseCimento10 = {50, 69, 81, 80, 98, 103, 109, 115, 119, 122, 126, 128, 131, 133, 137, 139, 140, 142, 144};
    public static int[] subBaseCimento15 = {66, 91, 108, 119, 130, 138, 146, 153, 158, 163, 168, 171, 176, 178, 183, 185, 188, 190, 192};
    public static int[] subBaseCimento20 = {89, 122, 145, 160, 174, 185, 195, 205, 212, 218, 225, 229, 235, 239, 245, 248, 251, 255, 258};
    public static int[] subBaseMelhorada10 = {36, 50, 60, 66, 73, 77, 82, 86, 89, 92, 95, 96, 99, 101, 103, 105, 106, 108, 109};
    public static int[] subBaseMelhorada15 = {54, 72, 84, 92, 99, 105, 110, 115, 119, 122, 125, 127, 130, 132, 137, 139, 140, 141};
    public static int[] subBaseMelhorada20 = {69, 91, 107, 117, 126, 133, 140, 146, 151, 155, 159, 162, 166, 168, 172, 174, 176, 178, 180};
    public static int[] subBaseRolada10 = {65, 87, 101, 111, 120, 127, 133, 140, 144, 148, 152, 154, 158, 160, 164, 166, 168, 170, 172};
    public static int[] subBaseRolada125 = {77, 101, 118, 128, 138, 145, 152, 159, 164, 168, 173, 175, 179, 182, 186, 188, 190, 192, 194};
    public static int[] subBaseRolada20 = {98, 126, 145, 158, 169, 177, 186, 194, 199, 204, 209, 211, 216, 219, 224, 226, 229, 231, 233};

}