package com.foxy.arrive5.Home;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.PromoAdapter;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.PromoCode;
import com.foxy.arrive5.Response.PromoListResponse;
import com.foxy.arrive5.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foxy.arrive5.Home.RateTripActivity.setSystemBarTheme;

public class PromoCodeActivity extends AppCompatActivity {
    RecyclerView recycler_promo;
    ReadPref readPref;
    PromoAdapter promoAdapter;
    ProgressDialog progressDialog;
    private List<PromoCode> promoCodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(PromoCodeActivity.this, true);
        recycler_promo = findViewById(R.id.recycler_promo);
        readPref = new ReadPref(PromoCodeActivity.this);
        String userId = readPref.getUserId();
        getPromoList(userId);
    }

    private void getPromoList(String userId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<PromoListResponse> promoListResponseCall = service.promoList(userId);
        progressDialog = new ProgressDialog(PromoCodeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        promoListResponseCall.enqueue(new Callback<PromoListResponse>() {
            @Override
            public void onResponse(Call<PromoListResponse> call, Response<PromoListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        promoCodeList = response.body().getPromoCode();
                        setPromoAdapter();
                    } else {
                        Toast.makeText(PromoCodeActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PromoListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setPromoAdapter() {
        promoAdapter = new PromoAdapter(PromoCodeActivity.this, promoCodeList);
        recycler_promo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_promo.setAdapter(promoAdapter);
    }

    public void BackClick(View view) {
        finish();
    }
}
