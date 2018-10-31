package com.hppyft.tcctets.Data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Espessuras.cm10,
        Espessuras.cm125,
        Espessuras.cm15,
        Espessuras.cm20,
        Espessuras.cm30

})
public @interface Espessuras {
    int cm10 = 0;
    int cm125 = 1;
    int cm15 = 2;
    int cm20 = 3;
    int cm30 = 4;

}