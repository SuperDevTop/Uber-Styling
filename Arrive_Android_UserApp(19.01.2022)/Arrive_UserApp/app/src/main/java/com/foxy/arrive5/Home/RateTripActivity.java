package com.foxy.arrive5.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Adapter.ReasonsAdapter;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.RatingComments;
import com.foxy.arrive5.Response.RatingsCommentResponse;
import com.foxy.arrive5.utils.AppsContants;
import com.foxy.arrive5.utils.ReadPref;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateTripActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<List<RatingComments>> reasonsList;
    CircularImageView imgUser;
    RatingBar ratingBar;
    ReadPref readPref;
    EditText rating_comment;
    ReasonsAdapter adapter;
    String review = "", review1 = "";
    String rateValue = "1.0";
    TextView txtRate;
    ProgressDialog progressDialog;
    String comment = "";

    TextView txtDriving, txtProfsnal, txtCarSmell, txtMusic;
    TextView txtTraffic, txtConversation, txtNavigation;
    TextView txtCleanlines, txtOther;


    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_trip);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(RateTripActivity.this, true);
        readPref = new ReadPref(RateTripActivity.this);
        imgUser = findViewById(R.id.imgUser);
        txtRate = findViewById(R.id.txtRate);

        txtDriving = findViewById(R.id.txtDriving);
        txtProfsnal = findViewById(R.id.txtProfsnal);
        txtCarSmell = findViewById(R.id.txtCarSmell);
        txtMusic = findViewById(R.id.txtMusic);

        txtTraffic = findViewById(R.id.txtTraffic);
        txtConversation = findViewById(R.id.txtConversation);
        txtNavigation = findViewById(R.id.txtNavigation);

        txtCleanlines = findViewById(R.id.txtCleanlines);
        txtOther = findViewById(R.id.txtOther);

        txtDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Driving";

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtProfsnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Rush";

                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });
        txtCarSmell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Car smell";


                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Music";
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));
                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Traffic";
                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });


        txtConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Conversation";
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Navigation";

                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtCleanlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Cleanlines";
                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));

                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });

        txtOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = "Other";
                txtOther.setBackground(getResources().getDrawable(R.drawable.rating_comments_selected));
                txtDriving.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtProfsnal.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCarSmell.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtTraffic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtMusic.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtConversation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));

                txtNavigation.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));
                txtCleanlines.setBackground(getResources().getDrawable(R.drawable.rating_comments_show));


            }
        });


        txtRate.setText(getResources().getString(R.string.rate_with) + " " + readPref.getDriverName());
        ratingBar = findViewById(R.id.ratingBar);
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
        recyclerView = findViewById(R.id.recyclerView);
        rating_comment = findViewById(R.id.rating_comment);
        Glide.with(RateTripActivity.this).load(getIntent().getStringExtra("driverImage")).into(imgUser);
        getRatingComments();
    }

    public void SubmitClick(View view) {
        String userId = readPref.getUserId();
        String bookingId = readPref.getBookingId();
        String rate = String.valueOf(ratingBar.getRating());
        // String comment = rating_comment.getText().toString();

        for (int i = 0; i < adapter.getArrayList().size(); i++) {
            review = adapter.getArrayList().get(i);
            review1 = review1 + review + ",";
        }
        if (!review1.equalsIgnoreCase("")) {
            review1 = review1.substring(0, review1.length() - 1);
        } else {
            review1 = "";
        }
        review1 = "Review";
        rateByUser(userId, bookingId, rate, comment, review1);
    }

    private void rateByUser(String userId, String bookingId, String rate, String comment, String review) {

        progressDialog = new ProgressDialog(RateTripActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        service.rateUser(userId, bookingId, rate, comment, review).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseStr = "";
                try {
                    responseStr = response.body().string();
                    JSONObject responseObj = new JSONObject(responseStr);
                    String status = responseObj.getString("status");
                    String msg = responseObj.getString("message");
                    if (status.equalsIgnoreCase("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(RateTripActivity.this, msg, Toast.LENGTH_SHORT).show();

                        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                        editor.putString(AppsContants.BackBtnStatus, "0");
                        editor.commit();

                        Intent intent = new Intent(RateTripActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RateTripActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException e) {
                    progressDialog.dismiss();
                    Toast.makeText(RateTripActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RateTripActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RateTripActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getRatingComments() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<RatingsCommentResponse> ratingsCommentResponseCall = service.getRatingComments();

        progressDialog = new ProgressDialog(RateTripActivity.this);
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
                        Toast.makeText(RateTripActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RatingsCommentResponse> call, Throwable t) {
                Toast.makeText(RateTripActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void setAdapter() {
        adapter = new ReasonsAdapter(RateTripActivity.this, reasonsList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    public void BackClick(View view) {
        finish();
    }
}
