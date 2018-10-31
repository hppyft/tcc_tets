package com.hppyft.tcctets.Data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Acostamentos.COM,
        Acostamentos.SEM
})
public @interface Acostamentos {
    int COM = 0;
    int SEM = 1;
}