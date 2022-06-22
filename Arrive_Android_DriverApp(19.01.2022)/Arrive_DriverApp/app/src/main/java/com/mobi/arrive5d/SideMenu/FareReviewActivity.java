package com.mobi.arrive5d.SideMenu;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.FareReviewListResponse;
import com.mobi.arrive5d.Response.FareReviewResponse;
import com.mobi.arrive5d.utils.ReadPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FareReviewActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    ProgressDialog progressDialog;
    RadioButton rdbtn;
    String reason = "";
    EditText review_comment;
    String commentReview = "";
    ReadPref readPref;
    List<FareReviewListResponse.ResultBean> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_review);
        radioGroup = findViewById(R.id.radioGroup);
        review_comment = findViewById(R.id.review_comment);
        readPref = new ReadPref(FareReviewActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        getFareReviewList();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                reason = String.valueOf(group.getCheckedRadioButtonId());
                if (radioButton.getText().toString().equalsIgnoreCase("Other")) {
                    review_comment.setVisibility(View.VISIBLE);
                    commentReview = review_comment.getText().toString();
                } else {
                    review_comment.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getFareReviewList() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<FareReviewListResponse> fareReviewListResponseCall = service.reviewList();
        progressDialog = new ProgressDialog(FareReviewActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        fareReviewListResponseCall.enqueue(new Callback<FareReviewListResponse>() {
            @Override
            public void onResponse(Call<FareReviewListResponse> call, Response<FareReviewListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        result = response.body().getResult();
                        for (int i = 0; i < result.size(); i++) {
                            rdbtn = new RadioButton(FareReviewActivity.this);
                            rdbtn.setId(i);
                            rdbtn.setText(result.get(i).getType());
                            rdbtn.setTextColor(getResources().getColor(R.color.whiteColor));
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


                                rdbtn.setButtonTintList(colorStateList);//set the color tint list
                                rdbtn.invalidate(); //could not be necessary
                            }
                            radioGroup.addView(rdbtn);
                        }
                    } else {
                        Toast.makeText(FareReviewActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FareReviewListResponse> call, Throwable t) {
                Toast.makeText(FareReviewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void BackClick(View view) {
        finish();
    }

    public void SubmitClick(View view) {
        String driverId = readPref.getDriverId();
        String reasonId = reason;
        String review = commentReview;
        submitReview(driverId, reasonId, review);
    }

    private void submitReview(String driverId, String reasonId, String review) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<FareReviewResponse> fareReviewResponseCall = service.submitReview(driverId, reasonId, review);
        progressDialog = new ProgressDialog(FareReviewActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        fareReviewResponseCall.enqueue(new Callback<FareReviewResponse>() {
            @Override
            public void onResponse(Call<FareReviewResponse> call, Response<FareReviewResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(FareReviewActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(FareReviewActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FareReviewResponse> call, Throwable t) {
                Toast.makeText(FareReviewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
