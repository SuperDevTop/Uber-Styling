package com.foxy.arrive5.Stripe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.Home.PaymentActivity;
import com.foxy.arrive5.Home.RateTripActivity;
import com.foxy.arrive5.Home.SelectDriverActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.PaymentResponse;
import com.foxy.arrive5.utils.ReadPref;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    Stripe stripe;

    String name;
    EditText editAmount;
    Card card;
    Token tok;
    String strUserId = "";
    String strEmail = "";
    String amount = "";
    ReadPref readPref;
    ProgressDialog progressDialog;
    EditText cardNumberField;
    EditText monthField;
    EditText yearField;
    EditText cvcField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        readPref = new ReadPref(PayActivity.this);

        name = readPref.getFname() + " " + readPref.getLname();
        strUserId = readPref.getUserId();
        strEmail = readPref.getEmailid();
        // amount = readPref.getBookingAmount();
        amount = readPref.getFinalAount();
        editAmount = findViewById(R.id.editAmount);
        editAmount.setText("$" + " " + amount);

         cardNumberField =  findViewById(R.id.cardNumber);
         monthField =  findViewById(R.id.month);
         yearField =  findViewById(R.id.year);
         cvcField =  findViewById(R.id.cvc);

        cardNumberField.setText(readPref.getCreditCard());
        monthField.setText(readPref.getExpMonth());
        yearField.setText(readPref.getExpYear());
        cvcField.setText(readPref.getCVV());



        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            stripe = new Stripe("pk_live_Kj3jfNSfhdRNcqHwaEHT9GD4");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public void submitCard(View view) {
        // TODO: replace with your own test key


        card = new Card(
                cardNumberField.getText().toString(),
                Integer.valueOf(monthField.getText().toString()),
                Integer.valueOf(yearField.getText().toString()),
                cvcField.getText().toString()
        );

        card.setCurrency("usd");
        card.setName("Theodhor Pandeli");
        card.setAddressZip("1000");
        /*
        card.setNumber(4242424242424242);
        card.setExpMonth(12);
        card.setExpYear(19);
        card.setCVC("123");
        */


        stripe.createToken(card, "pk_live_Kj3jfNSfhdRNcqHwaEHT9GD4", new TokenCallback() {
            public void onSuccess(Token token) {
                // TODO: Send Token information to your backend to initiate a charge
                //Toast.makeText(PayActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                tok = token;
                MakePayment(token.getId());
                //  new StripeCharge(token.getId()).execute();

            }

            public void onError(Exception error) {
                Toast.makeText(PayActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("Stripe", error.getLocalizedMessage());
            }
        });
    }

    public void MakePayment(String id) {
        AndroidNetworking.post("https://maestrosinfotech.com/arrive/application/controllers/webservice/Stripe.php")
                .addBodyParameter("name", name)
                .addBodyParameter("email", strEmail)
                .addBodyParameter("card_num", card.getNumber())
                .addBodyParameter("cvc", card.getCVC())
                .addBodyParameter("exp_month", String.valueOf(card.getExpMonth()))
                .addBodyParameter("exp_year", String.valueOf(card.getExpYear()))
                .addBodyParameter("amount", amount)
                .addBodyParameter("user_id", strUserId)
                .setTag("Stripe")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("dfgkdhfgdsfsd", response.toString());
                try {

                    if (response.getString("msg").equals("Payment made successfully.")) {

                        Toast.makeText(PayActivity.this, "Payment succesfull", Toast.LENGTH_SHORT).show();

                        if (readPref.getProfileType().equalsIgnoreCase("Business")) {
                            String bookingId = readPref.getBookingId();
                            String userId = readPref.getUserId();
                            String customerId = readPref.getCustomerID();
                            String cardId = readPref.getCardId();
                            //  String amount = readPref.getBookingAmount();
                            //String amount = "1";
                            finalPay(bookingId, userId, customerId, cardId, amount);
                        } else {
                            Intent intent = new Intent(PayActivity.this, RateTripActivity.class);
                            intent.putExtra("driverImage", getIntent().getStringExtra("driverImage"));
                            intent.putExtra("driverName", getIntent().getStringExtra("driverName"));
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(PayActivity.this, "Payment failed trry again!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception ex) {
                    Log.e("dfgkdhfgdsfsd", ex.getMessage());
                }

            }

            @Override
            public void onError(ANError anError) {
                Log.e("dfgkdhfgdsfsd", anError.getMessage());
            }
        });
    }

    public class StripeCharge extends AsyncTask<String, Void, String> {
        String token;

        public StripeCharge(String token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {
            new Thread() {
                @Override
                public void run() {
                    postData(name, token, "" + readPref.getBookingAmount());
                }
            }.start();
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result", s);
        }
    }

    public void postData(String description, String token, String amount) {
        // Create a new HttpClient and Post Header
        try {
            //URL url = new URL("https://experts24hr.com/experts/stripeapp/app/charge.php");
            URL url = new URL("https://maestrosinfotech.com/arrive/application/controllers/webservice/Stripe.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

           /* params.add(new NameValuePair("method", "charge"));
            params.add(new NameValuePair("description", description));
            params.add(new NameValuePair("source", token));
            params.add(new NameValuePair("amount", amount));*/

            params.add(new NameValuePair("name", name));
            params.add(new NameValuePair("email", strEmail));
            params.add(new NameValuePair("card_num", card.getNumber()));
            params.add(new NameValuePair("cvc", card.getCVC()));
            params.add(new NameValuePair("exp_month", String.valueOf(card.getExpMonth())));
            params.add(new NameValuePair("exp_year", String.valueOf(card.getExpYear())));
            params.add(new NameValuePair("amount", readPref.getBookingAmount()));
            params.add(new NameValuePair("user_id", strUserId));

            OutputStream os = null;
            os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {

            Log.e("ghjkgjfghdf", e.getMessage());
            e.printStackTrace();
        }
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        Log.e("Query", result.toString());
        return result.toString();
    }

    public class NameValuePair {
        String name, value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    private void finalPay(String bookingId, String userId, String customerId, String cardId, String amount) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<PaymentResponse> paymentResponseCall = service.finalPayment(bookingId, userId, customerId, cardId, amount);
        progressDialog = new ProgressDialog(PayActivity.this);
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
                        Toast.makeText(PayActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayActivity.this, RateTripActivity.class);
                        intent.putExtra("driverImage", getIntent().getStringExtra("driverImage"));
                        intent.putExtra("driverName", getIntent().getStringExtra("driverName"));
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PayActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(PayActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
