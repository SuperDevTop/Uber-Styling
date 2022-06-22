package com.foxy.arrive5.Settings;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy.arrive5.CCFragment.CCSecureCodeFragment;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.FontTypeChange;

public class CardBackFragment extends Fragment {
    TextView tv_cvv;
    FontTypeChange fontTypeChange;
    CheckOutActivity activity;
    CCSecureCodeFragment securecode;
    public CardBackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("BF", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_card_back, container, false);
        tv_cvv = view.findViewById(R.id.tv_cvv);
        fontTypeChange = new FontTypeChange(getActivity());
        tv_cvv.setTypeface(fontTypeChange.get_fontface(1));
        activity = (CheckOutActivity) getActivity();
        securecode = activity.secureCodeFragment;
        securecode.setCvv(tv_cvv);
        if (!TextUtils.isEmpty(securecode.getValue()))
            tv_cvv.setText(securecode.getValue());
        return view;
    }
}
