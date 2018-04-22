package com.example.user.exercisetimer;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextTimerWatcher implements TextWatcher {
    private EditText et;
    public TextTimerWatcher(EditText et) {
        this.et = et;
    }
    public void afterTextChanged(Editable s) {}

    public void beforeTextChanged(CharSequence s, int start, int count, int after){}

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String strEnteredVal = this.et.getText().toString();

        if (!strEnteredVal.equals("")) {
            int num = Integer.parseInt(strEnteredVal);
            if (num < 60){
                this.et.removeTextChangedListener(this);
                this.et.setText("");
                this.et.append(strEnteredVal);
                this.et.addTextChangedListener(this);
            }else{
                this.et.setText(strEnteredVal.substring(0, strEnteredVal.length() - 1));
            }
        }
    }
}
