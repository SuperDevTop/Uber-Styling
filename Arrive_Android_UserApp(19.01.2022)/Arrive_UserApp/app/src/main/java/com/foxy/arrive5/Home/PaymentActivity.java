package com.foxy.arrive5.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.IntroScreens.CreateProfileActivity;
import com.foxy.arrive5.PayPalConfig;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.PaymentResponse;
import com.foxy.arrive5.Stripe.PayActivity;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.SavePref;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView txt1, txt2, txt3, txt4, txt5, txtName, txtAmt;
    CircleImageView overlapImage;
    ReadPref readPref;
    int amount;
    String finalAmount;
    ProgressDialog progressDialog;
    //Payment Amount
    private String paymentAmount;
    Button btnRoundAmount;
    TextView txtShowAmt;
    TextView txtHintRound;
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    String strPayPalAmount = "";
    String FinalPayAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        overlapImage = findViewById(R.id.overlapImage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        readPref = new ReadPref(PaymentActivity.this);
        txtName = findViewById(R.id.txtName);
        txtAmt = findViewById(R.id.txtAmt);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);

        //  txtAmt.setText("$" + " " + getIntent().getStringExtra("amount"));
        txtName.setText(readPref.getDriverName());
        txtAmt.setText("$" + " " + readPref.getBookingAmount() + "." + "0");
        strPayPalAmount = readPref.getBookingAmount() + "." + "0";

        btnRoundAmount = findViewById(R.id.btnRoundAmount);
        txtShowAmt = findViewById(R.id.txtShowAmt);
        txtHintRound = findViewById(R.id.txtHintRound);

        Log.e("Virenbfdsfs", strPayPalAmount);


        Glide.with(PaymentActivity.this).load(getIntent().getStringExtra("driverImage")).into(overlapImage);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setBackgroundColor(getResources().getColor(R.color.darkBg));
                txt1.setTextColor(getResources().getColor(R.color.whiteColor));
                txt2.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt2.setTextColor(getResources().getColor(R.color.darkBg));
                txt3.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt3.setTextColor(getResources().getColor(R.color.darkBg));
                txt4.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt4.setTextColor(getResources().getColor(R.color.darkBg));
                txt5.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt5.setTextColor(getResources().getColor(R.color.darkBg));
                amount = 0;
                finalAmount = String.valueOf(Float.parseFloat(getIntent().getStringExtra("amount")) + amount);
                txtAmt.setText("$" + " " + finalAmount);
                strPayPalAmount = finalAmount;
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt1.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt1.setTextColor(getResources().getColor(R.color.darkBg));
                txt2.setBackgroundColor(getResources().getColor(R.color.darkBg));
                txt2.setTextColor(getResources().getColor(R.color.whiteColor));
                txt3.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt3.setTextColor(getResources().getColor(R.color.darkBg));
                txt4.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt4.setTextColor(getResources().getColor(R.color.darkBg));
                txt5.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt5.setTextColor(getResources().getColor(R.color.darkBg));
                amount = 1;
                finalAmount = String.valueOf(Float.parseFloat(getIntent().getStringExtra("amount")) + amount);
                txtAmt.setText("$" + " " + finalAmount);
                strPayPalAmount = finalAmount;
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt1.setTextColor(getResources().getColor(R.color.darkBg));
                txt2.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt2.setTextColor(getResources().getColor(R.color.darkBg));
                txt3.setBackgroundColor(getResources().getColor(R.color.darkBg));
                txt3.setTextColor(getResources().getColor(R.color.whiteColor));
                txt4.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt4.setTextColor(getResources().getColor(R.color.darkBg));
                txt5.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt5.setTextColor(getResources().getColor(R.color.darkBg));
                amount = 2;
                finalAmount = String.valueOf(Float.parseFloat(getIntent().getStringExtra("amount")) + amount);
                txtAmt.setText("$" + " " + finalAmount);
                strPayPalAmount = finalAmount;
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt1.setTextColor(getResources().getColor(R.color.darkBg));
                txt2.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt2.setTextColor(getResources().getColor(R.color.darkBg));
                txt3.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt3.setTextColor(getResources().getColor(R.color.darkBg));
                txt4.setBackgroundColor(getResources().getColor(R.color.darkBg));
                txt4.setTextColor(getResources().getColor(R.color.whiteColor));
                txt5.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt5.setTextColor(getResources().getColor(R.color.darkBg));
                amount = 5;
                finalAmount = String.valueOf(Float.parseFloat(getIntent().getStringExtra("amount")) + amount);
                txtAmt.setText("$" + " " + finalAmount);
                strPayPalAmount = finalAmount;
            }
        });
        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt1.setTextColor(getResources().getColor(R.color.darkBg));
                txt2.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt2.setTextColor(getResources().getColor(R.color.darkBg));
                txt3.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt3.setTextColor(getResources().getColor(R.color.darkBg));
                txt4.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                txt4.setTextColor(getResources().getColor(R.color.darkBg));
                txt5.setBackgroundColor(getResources().getColor(R.color.darkBg));
                txt5.setTextColor(getResources().getColor(R.color.whiteColor));
            }
        });


        btnRoundAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("dfgdjgdfgdf", strPayPalAmount);

                if (btnRoundAmount.getText().equals("Round Amount")) {

                    /*work for applying round amount*/
                    SavePref pref = new SavePref(PaymentActivity.this);
                    pref.saveRoundAmountStatus("1");
                    String strSplit[] = strPayPalAmount.split("\\.");
                    String getVal = strSplit[0];
                    String lastVal = String.valueOf(getVal.charAt(getVal.length() - 1));
                    int ivb = 10 - Integer.parseInt(lastVal);
                    int intTotal = Integer.parseInt(getVal) + ivb;
                    FinalPayAmount = String.valueOf(intTotal);
                    Log.e("testss", FinalPayAmount);
                    txtShowAmt.setVisibility(View.VISIBLE);
                    txtShowAmt.setText("$" + " " + FinalPayAmount + "." + "0");
                    txtHintRound.setVisibility(View.VISIBLE);
                    btnRoundAmount.setText("No Round Amount");
                    /*work for applying round amount ends here*/


                } else {

                    SavePref pref = new SavePref(PaymentActivity.this);
                    pref.saveRoundAmountStatus("2");
                    txtShowAmt.setVisibility(View.GONE);
                    txtHintRound.setVisibility(View.GONE);
                    btnRoundAmount.setText("Round Amount");
                }
            }
        });
    }

    public void PayByStripe(View view) {

        ReadPref readPref = new ReadPref(PaymentActivity.this);
        String strStatus = readPref.getRoundAmountStatus();

        if (strStatus.equals("1")) {

            SavePref pref = new SavePref(PaymentActivity.this);
            pref.saveFinalAmount(FinalPayAmount);/*this is rounded amount*/
            startActivity(new Intent(PaymentActivity.this, PayActivity.class));

        } else {
            SavePref pref = new SavePref(PaymentActivity.this);
            pref.saveFinalAmount(strPayPalAmount);/*this is simple amount*/
            startActivity(new Intent(PaymentActivity.this, PayActivity.class));
        }


    }

    public void RateClick(View view) {


        ReadPref readPref = new ReadPref(PaymentActivity.this);
        String strStatus = readPref.getRoundAmountStatus();

       if (strStatus.equals("1")) {

            String amount = FinalPayAmount;
            Log.e("fghfkghjfgh", amount);
            getPayment(amount);
        } else {
            String amount = strPayPalAmount;
            Log.e("fghfkghjfgh", amount);
            getPayment(amount);
        }


        // String amount = txtAmt.getText().toString().substring(1);
        //  String amount = "0.01";


      /* if (readPref.getProfileType().equalsIgnoreCase("Business")) {
            String bookingId = readPref.getBookingId();
            String userId = readPref.getUserId();
            String customerId = readPref.getCustomerID();
            String cardId = readPref.getCardId();
            String amount = txtAmt.getText().toString().substring(1);
            finalPay(bookingId, userId, customerId, cardId, amount);
        }
        else {
            Intent intent = new Intent(PaymentActivity.this, RateTripActivity.class);
            intent.putExtra("driverImage", getIntent().getStringExtra("driverImage"));
            intent.putExtra("driverName", getIntent().getStringExtra("driverName"));
            startActivity(intent);
        }*/
    }

    private void finalPay(String bookingId, String userId, String customerId, String cardId, String amount) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<PaymentResponse> paymentResponseCall = service.finalPayment(bookingId, userId, customerId, cardId, amount);
        progressDialog = new ProgressDialog(PaymentActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        paymentResponseCall.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().isResult();
                    if (status) {
                        Toast.makeText(PaymentActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this, RateTripActivity.class);
                        intent.putExtra("driverImage", getIntent().getStringExtra("driverImage"));
                        intent.putExtra("driverName", getIntent().getStringExtra("driverName"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(PaymentActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    public void BackClick(View view) {
        finish();
    }

    private void getPayment(String amount) {
        //Getting the amount from editText
        //paymentAmount = String.valueOf("0.01");
        paymentAmount = amount;

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Arrive rider payment",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                      /*  //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));*/

                        if (readPref.getProfileType().equalsIgnoreCase("Business")) {
                            String bookingId = readPref.getBookingId();
                            String userId = readPref.getUserId();
                            String customerId = readPref.getCustomerID();
                            String cardId = readPref.getCardId();
                            String amount = txtAmt.getText().toString().substring(1);

                            finalPay(bookingId, userId, customerId, cardId, amount);

                        } else {
                            Intent intent = new Intent(PaymentActivity.this, RateTripActivity.class);
                            intent.putExtra("driverImage", getIntent().getStringExtra("driverImage"));
                            intent.putExtra("driverName", getIntent().getStringExtra("driverName"));
                            startActivity(intent);


                        }


                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

}
