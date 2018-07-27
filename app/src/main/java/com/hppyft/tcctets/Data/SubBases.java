package com.hppyft.tcctets.Data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        SubBases.GRANULAR,
        SubBases.SOLO_CIMENTO,
        SubBases.SOLO_MELHORADO,
        SubBases.CONCRETO_ROLADO
})

public @interface SubBases {
    int GRANULAR = 0;
    int SOLO_CIMENTO = 1;
    int SOLO_MELHORADO = 2;
    int CONCRETO_ROLADO = 3;
}


