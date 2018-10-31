package com.hppyft.tcctets.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;


public class SaveTextOnSharedPrefs implements TextWatcher {

    private Activity mActivity;
    private ValueEdition mValueEdition;

    public SaveTextOnSharedPrefs(FragmentActivity activity, ValueEdition valueEditon) {
        mActivity = activity;
        mValueEdition = valueEditon;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        SharedPreferences sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            mValueEdition.putValueOnEditor(editor);
        } catch (Exception e) {
            Toast.makeText(mActivity, "O valor inserido no campo não é permitido", Toast.LENGTH_SHORT).show();
        } finally {
            editor.commit();
        }
    }

    public interface ValueEdition {
        void putValueOnEditor(SharedPreferences.Editor editor);
    }
}
