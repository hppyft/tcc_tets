package com.hppyft.tcctets.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hppyft.tcctets.Data.Model;
import com.hppyft.tcctets.R;
import com.hppyft.tcctets.Util.Util;
import com.hppyft.tcctets.databinding.ActivityResultadosBinding;

public class ResultadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultadosBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_resultados);
        mBinding.setActivity(this);

        Integer espessura = Model.mEspessura.intValue();
        mBinding.espessuraText.setText(getString(R.string.espessura_result, espessura));

        Double fadiga = Util.sumArray(Model.mFadigaPercentES) + Util.sumArray(Model.mFadigaPercentETD) + Util.sumArray(Model.mFadigaPercentETT);
        Double erosao = Util.sumArray(Model.mErosaoPercentES) + Util.sumArray(Model.mErosaoPercentETD) + Util.sumArray(Model.mErosaoPercentETT);
        mBinding.desc.setText(getString(R.string.desc_result, fadiga, erosao));
    }
}
