package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.VehicleDetail;
import com.foxy.arrive5.Response.VehicleType;
import com.foxy.arrive5.utils.SavePref;

import java.util.List;

/**
 * Created by parangat on 4/17/18.
 */

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.MyViewHolder> {
    SavePref savePref;
    List<VehicleType> vehicleType;
    private Context context;
    private List<VehicleDetail> pastRideDetailsArrayList;

    public ServiceTypeAdapter(Context context, List<VehicleDetail> pastRideDetailsArrayList) {
        this.context = context;
        this.pastRideDetailsArrayList = pastRideDetailsArrayList;
    }

    @Override
    public ServiceTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_service, parent, false);
        savePref = new SavePref(context);
        return new ServiceTypeAdapter.MyViewHolder(v);
    }

    public void refersh(final ServiceTypeAdapter.MyViewHolder holder, final int position) {
        final VehicleDetail pastRideDetails = pastRideDetailsArrayList.get(position);
        holder.txtName.setText(pastRideDetails.getVehicleTypeName());
        vehicleType = pastRideDetailsArrayList.get(position).getVehicleType();
        RecyclerTextAdapter adapter = new RecyclerTextAdapter(context, vehicleType);
        holder.recycler_bottom.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_bottom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ServiceTypeAdapter.MyViewHolder holder, final int position) {
        refersh(holder, position);
    }

    @Override
    public int getItemCount() {
        return pastRideDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout type_layout;
        RecyclerView recycler_bottom;
        private TextView txtName;

        public MyViewHolder(View itemView) {
            super(itemView);
            type_layout = itemView.findViewById(R.id.type_layout);
            txtName = itemView.findViewById(R.id.txtName);
            recycler_bottom = itemView.findViewById(R.id.recycler_bottom);
        }
    }
}
