package com.foxy.arrive5.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.Home.VehicleDetailBottomSheet;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.VehicleType;
import com.foxy.arrive5.utils.SavePref;

import java.util.List;

/**
 * Created by parangat on 4/17/18.
 */

public class RecyclerTextAdapter extends RecyclerView.Adapter<RecyclerTextAdapter.MyViewHolder> {
    LayoutInflater inflater;
    SavePref savePref;
    String subTypeId;
    String carType;
    VehicleDetailBottomSheet vehicleDetailBottomSheet;
    private Context context;
    private ImageView checkedRadioButton;
    private ImageView checkedRadioButton1;
    private List<VehicleType> pastRideDetailsArrayList;

    public RecyclerTextAdapter(Context context, List<VehicleType> pastRideDetailsArrayList) {
        this.context = context;
        this.pastRideDetailsArrayList = pastRideDetailsArrayList;
        checkedRadioButton = new ImageView(context);
        checkedRadioButton1 = new ImageView(context);
    }

    @Override
    public RecyclerTextAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_text, parent, false);
        savePref = new SavePref(context);
        return new RecyclerTextAdapter.MyViewHolder(v);
    }

    public void refersh(final RecyclerTextAdapter.MyViewHolder holder, final int position) {
        holder.car_model.setText(pastRideDetailsArrayList.get(position).getVehicleModel());
        Glide.with(context).load(pastRideDetailsArrayList.get(position).getNonselectimg()).into(holder.car_img);
        holder.car_price.setText("$" + " " + pastRideDetailsArrayList.get(position).getFareAmt());
        holder.car_time.setText(pastRideDetailsArrayList.get(position).getDrivertaketimetoreach());
        if (position == 0) {
            Glide.with(context).load(pastRideDetailsArrayList.get(position).getSelectimg()).into(holder.car_img);
            checkedRadioButton1 = holder.car_img;
            subTypeId = pastRideDetailsArrayList.get(position).getId();
            carType = pastRideDetailsArrayList.get(position).getVehicleModel();
            savePref.saveCarType(carType);
            savePref.saveSubTypeId(subTypeId);
        }
        holder.type_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedRadioButton != holder.info_icon) {
                    if (checkedRadioButton != null) {
                        holder.info_icon.setVisibility(View.VISIBLE);
                        checkedRadioButton.setVisibility(View.GONE);
                        checkedRadioButton = holder.info_icon;
                    }
                }
                if (checkedRadioButton1 != holder.car_img) {
                    if (checkedRadioButton1 != null) {
                        Glide.with(context).load(pastRideDetailsArrayList.get(position).getNonselectimg()).into(checkedRadioButton1);
                        Glide.with(context).load(pastRideDetailsArrayList.get(position).getSelectimg()).into(holder.car_img);
                        carType = pastRideDetailsArrayList.get(position).getVehicleModel();
                        savePref.saveCarType(carType);
                        checkedRadioButton1 = holder.car_img;
                    }
                }
                subTypeId = pastRideDetailsArrayList.get(position).getId();
                savePref.saveSubTypeId(subTypeId);
            }
        });

        holder.info_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleDetailBottomSheet = new VehicleDetailBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putSerializable("estTime", holder.car_time.getText().toString());
                bundle.putSerializable("capacity", pastRideDetailsArrayList.get(position).getVehicleCapacity());
                bundle.putSerializable("minFare", pastRideDetailsArrayList.get(position).getMinimumFare());
                bundle.putSerializable("basicFare", pastRideDetailsArrayList.get(position).getBasePrice());
                bundle.putSerializable("bookingFee", pastRideDetailsArrayList.get(position).getBookingFare());
                bundle.putSerializable("chargePerMin", pastRideDetailsArrayList.get(position).getChargePerMin());
                bundle.putSerializable("chargePerMile", pastRideDetailsArrayList.get(position).getChargePerMile());
                vehicleDetailBottomSheet.setArguments(bundle);
                vehicleDetailBottomSheet.setCancelable(false);
                vehicleDetailBottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "BottomSheet Fragment");

            }
        });
    }

    @Override
    public void onBindViewHolder(final RecyclerTextAdapter.MyViewHolder holder, final int position) {
        refersh(holder, position);
    }

    @Override
    public int getItemCount() {
        return pastRideDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout type_layout;
        private TextView car_model, car_price, car_time;
        private ImageView car_img, info_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            car_img = itemView.findViewById(R.id.car_img);
            car_model = itemView.findViewById(R.id.car_model);
            car_price = itemView.findViewById(R.id.car_price);
            car_time = itemView.findViewById(R.id.car_time);
            info_icon = itemView.findViewById(R.id.info_icon);
            type_layout = itemView.findViewById(R.id.type_layout);
        }
    }
}
