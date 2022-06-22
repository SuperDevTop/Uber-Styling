package com.foxy.arrive5.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Home.SelectPoolActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CardListResponse;
import com.foxy.arrive5.Response.GetProfileResponse;
import com.foxy.arrive5.TestCallback;
import com.foxy.arrive5.utils.Constants;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by parangat on 4/18/18.
 */

public class SelectProfileAdapter extends RecyclerView.Adapter<SelectProfileAdapter.ViewHolder> {
    TestCallback callback;
    String cardId = "", custId;
    ReadPref readPref;
    List<CardListResponse.MsgBean> cardList;
    RadioButton rdbtn;
    SelectProfileAdapter.ViewHolder holder;
    private Context context;
    private List<GetProfileResponse.BusinessProfileListBean> business_profile_list;
    private ImageView checkedRadioButton;

    public SelectProfileAdapter(Context context, List<GetProfileResponse.BusinessProfileListBean> business_profile_list) {
        this.context = context;
        this.business_profile_list = business_profile_list;
        checkedRadioButton = new ImageView(context);
        callback = (TestCallback) context;
    }

    @Override
    public SelectProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.select_profile, parent, false);
        readPref = new ReadPref(context);
        return new SelectProfileAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SelectProfileAdapter.ViewHolder holder, final int position) {
        String type = business_profile_list.get(position).getType();
        String paymentType = business_profile_list.get(position).getPayment_method_type();
        if (type.equalsIgnoreCase("1")) {
            holder.txtType.setText("Personal");
        } else if (type.equalsIgnoreCase("2")) {
            holder.txtType.setText("Business");
        }
        if (business_profile_list.get(position).getCardList().size() > 0) {
            holder.radioGroup.setVisibility(View.VISIBLE);
            custId = readPref.getCustomerID();
            for (int i = 0; i < business_profile_list.get(position).getCardList().size(); i++) {
                rdbtn = new RadioButton(context);
                rdbtn.setId(i);
                rdbtn.setText(business_profile_list.get(position).getCardList().get(i).getLast4());
//                if (Build.VERSION.SDK_INT >= 21) {
//                    ColorStateList colorStateList = new ColorStateList(
//                            new int[][]{
//                                    new int[]{-android.R.attr.state_enabled}, //disabled
//                                    new int[]{android.R.attr.state_enabled} //enabled
//                            },
//                            new int[]{
//                                    Color.BLACK //disabled
//                                    , Color.WHITE //enabled
//                            }
//                    );
//                    rdbtn.setButtonTintList(colorStateList);
//                }
                rdbtn.setTextColor(ContextCompat.getColorStateList(context, R.color.blueColor));
                holder.radioGroup.addView(rdbtn);
            }
            // getCardLists(custId);
            holder.cardType.setText(business_profile_list.get(position).getCardList().get(position).getLast4());
        } else {
            if (paymentType.equalsIgnoreCase("1")) {
                holder.cardType.setText(business_profile_list.get(position).getCardList().get(position).getLast4());
            } else {
               // holder.cardType.setText("Cash");
                holder.radioGroup.setVisibility(View.GONE);
            }
        }
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cardId = business_profile_list.get(position).getCardList().get(checkedId).getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CARDTYPE, holder.cardType.getText().toString());
                bundle.putString(Constants.PROFILETYPE, holder.txtType.getText().toString());
                bundle.putString(Constants.CARDID, cardId);
                callback.getTestData(bundle);
                ((SelectPoolActivity) context).closeBottomSheet();
                //Toast.makeText(context, ""+cardId, Toast.LENGTH_SHORT).show();
            }
        });
        if (business_profile_list.get(position).getType().equalsIgnoreCase("1")) {
            holder.imgType.setImageDrawable(context.getResources().getDrawable(R.drawable.user_blue));
        } else if (business_profile_list.get(position).getType().equalsIgnoreCase("2")) {
            holder.imgType.setImageDrawable(context.getResources().getDrawable(R.drawable.bag));
        }
        holder.layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedRadioButton != holder.imgTick) {
                    if (checkedRadioButton != null) {
                        holder.imgTick.setVisibility(View.VISIBLE);
                        checkedRadioButton.setVisibility(View.GONE);
                        checkedRadioButton = holder.imgTick;
                    }
                }
                if (business_profile_list.get(position).getType().equalsIgnoreCase("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CARDTYPE, holder.cardType.getText().toString());
                    bundle.putString(Constants.PROFILETYPE, holder.txtType.getText().toString());
                    callback.getTestData(bundle);
                    ((SelectPoolActivity) context).closeBottomSheet();
                } else if (business_profile_list.get(position).getType().equalsIgnoreCase("2")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CARDTYPE, holder.cardType.getText().toString());
                    bundle.putString(Constants.PROFILETYPE, holder.txtType.getText().toString());
//                    callback.getTestData(bundle);
//                    ((SelectPoolActivity) context).closeBottomSheet();
                    if (!cardId.equalsIgnoreCase("") && business_profile_list.get(position).getType().equalsIgnoreCase("2")) {
                        bundle.putString(Constants.CARDID, cardId);
                        callback.getTestData(bundle);
                        ((SelectPoolActivity) context).closeBottomSheet();
                    } else {
                        Toast.makeText(context, "Please select any card", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void getCardLists(String custId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CardListResponse> cardListResponseCall = service.getCardList(custId);
        cardListResponseCall.enqueue(new Callback<CardListResponse>() {
            @Override
            public void onResponse(Call<CardListResponse> call, Response<CardListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        cardList = response.body().getMsg();
                        for (int i = 0; i < cardList.size(); i++) {
                            rdbtn = new RadioButton(context);
                            rdbtn.setId(i);
                            rdbtn.setText(cardList.get(i).getLast4());
                            if (Build.VERSION.SDK_INT >= 21) {
                                ColorStateList colorStateList = new ColorStateList(
                                        new int[][]{
                                                new int[]{-android.R.attr.state_enabled}, //disabled
                                                new int[]{android.R.attr.state_enabled} //enabled
                                        },
                                        new int[]{
                                                Color.BLACK //disabled
                                                , Color.WHITE //enabled
                                        }
                                );
                                rdbtn.setButtonTintList(colorStateList);
                            }
                            rdbtn.setTextColor(ContextCompat.getColorStateList(context, R.color.blueColor));
                            holder.radioGroup.addView(rdbtn);
                        }
                    } else {
                        Toast.makeText(context, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CardListResponse> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return business_profile_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgType, imgTick;
        LinearLayout layoutProfile;
        RadioGroup radioGroup;
        private TextView txtType, cardType;

        public ViewHolder(View itemView) {
            super(itemView);
            imgType = itemView.findViewById(R.id.imgType);
            imgTick = itemView.findViewById(R.id.imgTick);
            cardType = itemView.findViewById(R.id.cardType);
            txtType = itemView.findViewById(R.id.txtType);
            layoutProfile = itemView.findViewById(R.id.layoutProfile);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}
