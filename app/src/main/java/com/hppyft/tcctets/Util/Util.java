package com.hppyft.tcctets.Util;

public class Util {

    public static String[] putStringsBetweenSpaces(String[] strings) {
        String[] newStrings = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            newStrings[i] = "\n" + strings[i] + "\n";
        }
        return newStrings;
    }

    public static double sumArray(Double[] array) {
        double result = 0.0;
        for (Double db : array) {
            result += db;
        }
        return result;
    }
}
