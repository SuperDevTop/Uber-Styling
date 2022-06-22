package com.foxy.arrive5.Home;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.ReadPref;

public class VehicleDetailBottomSheet extends BottomSheetDialogFragment {
    TextView txtVehicleType, txtCarType, txt2, txt1, txt3;
    ReadPref readPref;
    ImageView imgDone;
    String basicFare, bookingFare, chargePerMin, chargePerMile, minFare;

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
        View contentView = View.inflate(getContext(), R.layout.activity_vehicle_detail_bottom_sheet, null);
        readPref = new ReadPref(getContext());
        txtVehicleType = contentView.findViewById(R.id.txtVehicleType);
        imgDone = contentView.findViewById(R.id.imgDone);
        txtCarType = contentView.findViewById(R.id.txtCarType);
        txt2 = contentView.findViewById(R.id.txt2);
        txt1 = contentView.findViewById(R.id.txt1);
        txt3 = contentView.findViewById(R.id.txt3);
        String estTime = getArguments().getSerializable("estTime").toString();
        String capacity = getArguments().getSerializable("capacity").toString();
        minFare = getArguments().getSerializable("minFare").toString();
        basicFare = getArguments().getSerializable("basicFare").toString();
        bookingFare = getArguments().getSerializable("bookingFee").toString();
        chargePerMin = getArguments().getSerializable("chargePerMin").toString();
        chargePerMile = getArguments().getSerializable("chargePerMile").toString();
        txt1.setText(estTime);
        txt2.setText(minFare);
        txt3.setText(capacity);
        txtCarType.setText(readPref.getCarType());
        txtVehicleType.setText(readPref.getVehicleType());
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FareBreakdownActivity.class);
                intent.putExtra("basicFare", basicFare);
                intent.putExtra("bookingFare", bookingFare);
                intent.putExtra("minFare", minFare);
                intent.putExtra("chargePerMin", chargePerMin);
                intent.putExtra("chargePerMile", chargePerMile);
                startActivity(intent);
            }
        });
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.setContentView(contentView);
    }
}
