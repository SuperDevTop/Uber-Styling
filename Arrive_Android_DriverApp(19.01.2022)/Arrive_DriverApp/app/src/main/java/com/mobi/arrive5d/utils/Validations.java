package com.mobi.arrive5d.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ADMIN on 1/5/2018.
 */

public class Validations {

    public static boolean hasText(String s) {
        if (s.equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    public static boolean isValidPassword(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(editText.getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidPhone(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(editText.getContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean doMatch(EditText txtpass, EditText txtconfirm) {
        String textPass = txtpass.getText().toString();
        String textconFirm = txtconfirm.getText().toString();
        if (!(textPass.equals(textconFirm))) {
            Toast.makeText(txtconfirm.getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
