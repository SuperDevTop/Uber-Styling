package com.foxy.arrive5.IntroScreens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.SavePref;
import com.foxy.arrive5.utils.Validations;

public class EmailActivity extends AppCompatActivity {
    SavePref savePref;
    EditText cardNumber;
    EditText month;
    EditText year;
    EditText editCvv;
    EditText editPaypalEmail;
    EditText editPaypalPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.whiteColor));
        }
        cardNumber = findViewById(R.id.cardNumber);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        editCvv = findViewById(R.id.editCvv);
        editPaypalEmail = findViewById(R.id.editPaypalEmail);
        editPaypalPass = findViewById(R.id.editPaypalPass);
        savePref = new SavePref(EmailActivity.this);
    }

    public void BackClick(View view) {
        finish();
    }

    public void NextClick(View view) {


            if(cardNumber.getText().toString().length()==0){

                Toast.makeText(this, "Enter Credit card Number", Toast.LENGTH_SHORT).show();
            }
            else if(month.getText().toString().length()==0){

                Toast.makeText(this, "Enter Credit card expiry month", Toast.LENGTH_SHORT).show();
            }

            else if(year.getText().toString().length()==0){

                Toast.makeText(this, "Enter Credit card expiry year", Toast.LENGTH_SHORT).show();
            }

            else if(editPaypalEmail.getText().toString().length()==0){

                Toast.makeText(this, "Enter palypal email", Toast.LENGTH_SHORT).show();
            }
            else if(editPaypalPass.getText().toString().length()==0){

                Toast.makeText(this, "Enter palypal password", Toast.LENGTH_SHORT).show();
            }
            else {


                savePref.saveCreditCardNum(cardNumber.getText().toString());
                savePref.saveExpMonth(month.getText().toString());
                savePref.saveExpYear(year.getText().toString());
                savePref.saveCVV(editCvv.getText().toString());
                savePref.savePayPalEmail(editPaypalEmail.getText().toString());
                savePref.savePayPalPass(editPaypalPass.getText().toString());
                Intent intent = new Intent(EmailActivity.this, MobileActivity.class);
                startActivity(intent);
            }

    }
}
