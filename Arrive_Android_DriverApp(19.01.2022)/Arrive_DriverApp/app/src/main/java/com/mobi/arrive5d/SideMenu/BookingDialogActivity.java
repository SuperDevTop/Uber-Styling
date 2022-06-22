package com.mobi.arrive5d.SideMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.arrive5d.API.ApiClient;
import com.mobi.arrive5d.API.ApiService;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.Response.AcceptRejectResponse;
import com.mobi.arrive5d.utils.ReadPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDialogActivity extends AppCompatActivity {
    ReadPref readPref;
    int time = 60;
    TextView txtTime, txtName;
    ProgressDialog progressDialog;
    MediaPlayer mp;
    String tag;
    private boolean timer = true;

    public static final void setSystemBarTheme(final Activity pActivity, final boolean pIsDark) {
        final int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        pActivity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueColor));
        }
        setSystemBarTheme(BookingDialogActivity.this, true);
        readPref = new ReadPref(BookingDialogActivity.this);
        if (getIntent().hasExtra("pushtag") || getIntent().hasExtra("openActivity")) {
            tag = getIntent().getStringExtra("pushtag");
            if (tag != null) {
                if (tag.equalsIgnoreCase("booking") || tag.equalsIgnoreCase("Schedule Booking")) {
                    OpenPushActivity();
                }
            }
        }
    }

    private void OpenPushActivity() {
        final Button btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingId = readPref.getBookingId();
                String driver_id = readPref.getDriverId();
                String mode = "accept";
                acceptBooking(bookingId, driver_id, mode);
            }
        });
        txtName = findViewById(R.id.time);
        txtName.setText(readPref.getDuration());
        txtTime = findViewById(R.id.txtTime);
        final int timeBlinkInMilliseconds = 5000;
        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (timer) {
                    txtTime.setText("0:" + millisUntilFinished / 1000 + " " + "Seconds");
                    time--;
                    playAlertSound(R.raw.beep);
                    if (millisUntilFinished < timeBlinkInMilliseconds) {
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(100); //You can manage the blinking time with this parameter
                        anim.setStartOffset(50);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        txtTime.startAnimation(anim);
                    }
                } else {
                    mp.release();
                }
            }

            public void onFinish() {
                finish();
                mp.release();
            }
        }.start();
        TextView address1 = findViewById(R.id.address1);
        address1.setText(readPref.getEndPoint());
        TextView rating = findViewById(R.id.rating);
        rating.setText(readPref.getUserRating());
        TextView txtType = findViewById(R.id.txtType);
        txtType.setText(readPref.getVehicleSubType());
        TextView address2 = findViewById(R.id.time);
        address2.setText(readPref.getDuration());
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingId = readPref.getBookingId();
                String driver_id = readPref.getDriverId();
                String mode = "reject";
                acceptBooking(bookingId, driver_id, mode);
            }
        });
    }

    public void playAlertSound(int sound) {
        mp = MediaPlayer.create(getBaseContext(), sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });
        mp.setLooping(false);
        mp.setVolume(0.5f, 0.5f);
        mp.start();
    }
    private void acceptBooking(String bookingId, String driver_id, String mode) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<AcceptRejectResponse> acceptRejectResponseCall = service.bookingResponse(bookingId, driver_id, mode);
        progressDialog = new ProgressDialog(BookingDialogActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        acceptRejectResponseCall.enqueue(new Callback<AcceptRejectResponse>() {
            @Override
            public void onResponse(Call<AcceptRejectResponse> call, Response<AcceptRejectResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("true")) {
                        if (mp.isPlaying()) {
                            timer = false;
                            mp.pause();
                            mp.release();
                        }
                        if (response.body().getMessage().equalsIgnoreCase("Driver reject booking request.") || tag.equalsIgnoreCase("Schedule Booking") || status.equalsIgnoreCase("false")) {
                            Intent intent = new Intent(BookingDialogActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(BookingDialogActivity.this, StartNavigationActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        // Toast.makeText(BookingDialogActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookingDialogActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AcceptRejectResponse> call, Throwable t) {
                Toast.makeText(BookingDialogActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mp.isPlaying()) {
            timer = false;
            mp.pause();
            mp.release();
        }
    }
}
