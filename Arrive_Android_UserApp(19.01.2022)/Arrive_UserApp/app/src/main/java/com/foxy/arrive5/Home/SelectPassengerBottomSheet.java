package com.foxy.arrive5.Home;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.SavePref;

public class SelectPassengerBottomSheet extends BottomSheetDialogFragment {
    TextView txt1, txt2;
    String noPass;
    SavePref savePref;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {

                case BottomSheetBehavior.STATE_COLLAPSED: {
                    Log.d("BSB", "collapsed");
                }
                case BottomSheetBehavior.STATE_SETTLING: {
                    Log.d("BSB", "settling");
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    Log.d("BSB", "expanded");
                }
                case BottomSheetBehavior.STATE_HIDDEN: {
                    Log.d("BSB", "hidden");
                    dismiss();
                }
                case BottomSheetBehavior.STATE_DRAGGING: {
                    Log.d("BSB", "dragging");
                }
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            Log.d("BSB", "sliding " + slideOffset);
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.activity_passenger_bottom_sheet, null);
        savePref = new SavePref(getContext());
        txt1 = contentView.findViewById(R.id.txt1);
        txt2 = contentView.findViewById(R.id.txt2);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SelectPoolActivity) getContext()).closeBottomSheet1();
                noPass = "1";
                savePref.saveNoPass(noPass);
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SelectPoolActivity) getContext()).closeBottomSheet1();
                noPass = "3";
                savePref.saveNoPass(noPass);

            }
        });
        dialog.setContentView(contentView);
    }

}
