package com.foxy.arrive5.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CheckPromoCodeResponse;
import com.foxy.arrive5.Response.PromoCode;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by parangat on 7/8/18.
 */

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyViewHolder> {
    ReadPref readPref;
    String promoCode, promoCodeValue;
    private Context context;
    private List<PromoCode> promoCodeList;

    public PromoAdapter(Context context, List<PromoCode> promoCodeList) {
        this.context = context;
        this.promoCodeList = promoCodeList;
    }

    @Override
    public PromoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_promo, parent, false);
        readPref = new ReadPref(context);
        return new PromoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PromoAdapter.MyViewHolder holder, final int position) {
        promoCode = promoCodeList.get(position).getPromoCode();
        promoCodeValue = promoCodeList.get(position).getId();
        holder.txtPromo.setText(promoCodeList.get(position).getPromoCode());
        holder.txtDate.setText(promoCodeList.get(position).getValidTo());
        final String promoType = promoCodeList.get(position).getPromoTypeName();
        if (promoType.equalsIgnoreCase("Flat")) {
            holder.txtDiscount.setText("Flat" + " " + promoCodeList.get(position).getDiscount() + " " + "off");
        } else {
            holder.txtDiscount.setText("Upto" + " " + promoCodeList.get(position).getDiscount() + "%" + " " + "off");
        }
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = readPref.getUserId();
                String promoCode = promoCodeList.get(position).getPromoCode();
                checkPromo(userId, promoCode);
            }
        });
    }

    private void checkPromo(String userId, final String promoCode) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CheckPromoCodeResponse> checkPromoCodeResponseCall = service.checkPromoCode(userId, promoCode);
        checkPromoCodeResponseCall.enqueue(new Callback<CheckPromoCodeResponse>() {
            @Override
            public void onResponse(Call<CheckPromoCodeResponse> call, Response<CheckPromoCodeResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().isSuccess();
                    if (status) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("promoCode", promoCode);
                        intent.putExtra("promoValue", response.body().getResult().getId());
                        ((Activity) context).setResult(RESULT_OK, intent);
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckPromoCodeResponse> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return promoCodeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        private TextView txtPromo, txtDate, txtDiscount;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtPromo = itemView.findViewById(R.id.txtPromo);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
