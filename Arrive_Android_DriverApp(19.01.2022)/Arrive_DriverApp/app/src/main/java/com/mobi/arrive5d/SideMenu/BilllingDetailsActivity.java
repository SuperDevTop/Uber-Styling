package com.mobi.arrive5d.SideMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.BillingResponse;
import com.mobi.arrive5d.Response.RatingComments;
import com.mobi.arrive5d.Response.RatingsCommentResponse;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilllingDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<List<RatingComments>> reasonsList;
    CircleImageView imgUser;
    RatingBar ratingBar;
    ReadPref readPref;
    EditText rating_comment;
    TextView txtAmt;
    ReasonsAdapter adapter;
    String rateValue = "1.0";
    String review = "", review1 = "";
    ProgressDialog progressDialog;

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billling_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(BilllingDetailsActivity.this, true);
        readPref = new ReadPref(BilllingDetailsActivity.this);
        imgUser = findViewById(R.id.imgUser);
        txtAmt = findViewById(R.id.txtAmt);
        ratingBar = findViewById(R.id.ratingBar);
        recyclerView = findViewById(R.id.recyclerView);
        rating_comment = findViewById(R.id.rating_comment);
        txtAmt.setText("$" + " " + readPref.getBookingAmount());
        Glide.with(BilllingDetailsActivity.this).load(readPref.getImage()).into(imgUser);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = String.valueOf(ratingBar.getRating());
                double rate_double_type = Double.parseDouble(rateValue);
                int rate_type = (int) rate_double_type;
                adapter.setRatingType(rate_type);
                getRatingComments();
            }
        });
        getRatingComments();
    }

    private void getRatingComments() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<RatingsCommentResponse> ratingsCommentResponseCall = service.getRatingComments();
        progressDialog = new ProgressDialog(BilllingDetailsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ratingsCommentResponseCall.enqueue(new Callback<RatingsCommentResponse>() {
            @Override
            public void onResponse(Call<RatingsCommentResponse> call, Response<RatingsCommentResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        if (rateValue.equalsIgnoreCase("1.0")) {
                            reasonsList = response.body().getResult().getRatingOneList();
                        } else if (rateValue.equalsIgnoreCase("2.0")) {
                            reasonsList = response.body().getResult().getRatingTwoList();
                        } else if (rateValue.equalsIgnoreCase("3.0")) {
                            reasonsList = response.body().getResult().getRatingThreeList();
                        } else if (rateValue.equalsIgnoreCase("4.0")) {
                            reasonsList = response.body().getResult().getRatingFourList();
                        } else {
                            reasonsList = response.body().getResult().getRatingFiveList();
                        }
                        setAdapter();
                    } else {
                        Toast.makeText(BilllingDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RatingsCommentResponse> call, Throwable t) {
                Toast.makeText(BilllingDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void setAdapter() {
        adapter = new ReasonsAdapter(BilllingDetailsActivity.this, reasonsList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    public void BackClick(View view) {
        finish();
    }

    public void SubmitClick(View view) {
        String driverId = readPref.getDriverId();
        String bookingId = readPref.getBookingId();
        String rate = String.valueOf(ratingBar.getRating());
        String comment = rating_comment.getText().toString();
        for (int i = 0; i < adapter.getArrayList().size(); i++) {
            review = adapter.getArrayList().get(i);
            review1 = review1 + review + ",";
        }
        if (!review1.equalsIgnoreCase("")) {
            review1 = review1.substring(0, review1.length() - 1);
        } else {
            review1 = "";
        }

        review="review";
        billing(driverId, bookingId, rate, comment, review);
        Log.e("dfjghdfkjghdkjf","driver_id"+" "+driverId);
        Log.e("dfjghdfkjghdkjf","Booking id"+" "+bookingId);
        Log.e("dfjghdfkjghdkjf","Rate"+" "+rate);
        Log.e("dfjghdfkjghdkjf","Comment"+" "+comment);
        Log.e("dfjghdfkjghdkjf","Review"+" "+review);
    }

    private void billing(String driverId, String bookingId, String rate, String comment, String review) {

        progressDialog = new ProgressDialog(BilllingDetailsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<BillingResponse> billingResponseCall = service.billingResponse(driverId, bookingId, rate, comment, review);
        billingResponseCall.enqueue(new Callback<BillingResponse>() {
            @Override
            public void onResponse(Call<BillingResponse> call, Response<BillingResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Log.e("dkfrgdhjkfg","virend");
                        progressDialog.dismiss();
                        Toast.makeText(BilllingDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BilllingDetailsActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Log.e("dkfrgdhjkfg","virend");
                        Toast.makeText(BilllingDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BillingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("dkfrgdhjkfg",t.getMessage());
                Toast.makeText(BilllingDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FareReviewClick(View view) {
        Intent intent = new Intent(BilllingDetailsActivity.this, FareReviewActivity.class);
        startActivity(intent);
    }
}
