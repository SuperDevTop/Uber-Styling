package com.foxy.arrive5.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

public class CreditCardEditText extends android.support.v7.widget.AppCompatEditText {
    public Context context;
    BackButtonListener backButtonListener;

    public CreditCardEditText(Context context) {
        super(context);
        this.context = context;
    }

    public CreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setOnBackButtonListener(BackButtonListener listener) {
        backButtonListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("ET", "onKeyPreIme: ");
            if (backButtonListener != null)
                backButtonListener.onBackClick();
            return true;
            // TODO: Hide your view as you do it in your activity
        }
        return false;
    }

    public interface BackButtonListener {
        public void onBackClick();
    }
}
