package com.hppyft.tcctets.Data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        TipoCarga.FSC10,
        TipoCarga.FSC11,
        TipoCarga.FSC12,
        TipoCarga.FSC15
})
public @interface TipoCarga {
    int FSC10 = 0;
    int FSC11 = 1;
    int FSC12 = 2;
    int FSC15 = 3;
}