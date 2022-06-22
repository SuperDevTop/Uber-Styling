package com.foxy.arrive5.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.CheckPromoResponse;
import com.foxy.arrive5.utils.ReadPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoFragment extends Fragment {
    EditText etPromo;
    ImageView btnSubmit;
    ReadPref readPref;
    ProgressDialog progressDialog;
    public PromoFragment() {
    }

    public static PromoFragment newInstance(String param1, String param2) {
        PromoFragment fragment = new PromoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        readPref = new ReadPref(getContext());
        etPromo = view.findViewById(R.id.etPromo);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        getActivity().setTitle(getResources().getString(R.string.promos));
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = readPref.getUserId();
                String code = etPromo.getText().toString();
                checkPromo(userId, code);
            }
        });
        return view;
    }

    private void checkPromo(String userId, String code) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<CheckPromoResponse> checkPromoResponseCall = service.checkPromo(userId, code);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        checkPromoResponseCall.enqueue(new Callback<CheckPromoResponse>() {
            @Override
            public void onResponse(Call<CheckPromoResponse> call, Response<CheckPromoResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CheckPromoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
