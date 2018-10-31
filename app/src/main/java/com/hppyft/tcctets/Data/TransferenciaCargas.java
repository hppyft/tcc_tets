package com.hppyft.tcctets.Data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        TransferenciaCargas.BARRAS,
        TransferenciaCargas.ENTROSAGEM
})
public @interface TransferenciaCargas {
    int BARRAS = 0;
    int ENTROSAGEM = 1;
}