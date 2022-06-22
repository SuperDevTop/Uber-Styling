package com.foxy.arrive5.Settings;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.foxy.arrive5.API.ApiClient;
import com.foxy.arrive5.API.ApiService;
import com.foxy.arrive5.CCFragment.CCNameFragment;
import com.foxy.arrive5.CCFragment.CCNumberFragment;
import com.foxy.arrive5.CCFragment.CCSecureCodeFragment;
import com.foxy.arrive5.CCFragment.CCValidityFragment;
import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.SaveCardResponse;
import com.foxy.arrive5.utils.CreditCardUtils;
import com.foxy.arrive5.utils.ReadPref;
import com.foxy.arrive5.utils.ViewPagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {
    public CardFrontFragment cardFrontFragment;
    public CardBackFragment cardBackFragment;
    Button btnNext;
    CCNumberFragment numberFragment;
    CCNameFragment nameFragment;
    CCValidityFragment validityFragment;
    CCSecureCodeFragment secureCodeFragment;
    int total_item;
    ReadPref readPref;
    boolean backTrack = false;
    String cardNumber, cardCVV, cardValidity, cardName;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private boolean mShowingBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        btnNext = findViewById(R.id.btnNext);
        readPref = new ReadPref(CheckOutActivity.this);
        cardFrontFragment = new CardFrontFragment();
        cardBackFragment = new CardBackFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cardFrontFragment).commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == total_item)
                    btnNext.setText("SUBMIT");
                else
                    btnNext.setText("NEXT");
                Log.d("track", "onPageSelected: " + position);
                if (position == total_item) {
                    flipCard();
                    backTrack = true;
                } else if (position == total_item - 1 && backTrack) {
                    flipCard();
                    backTrack = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                if (pos < total_item) {
                    viewPager.setCurrentItem(pos + 1);
                } else {
                    checkEntries();
                }
            }
        });
    }

    public void checkEntries() {
        cardName = nameFragment.getName();
        cardNumber = numberFragment.getCardNumber();
        cardValidity = validityFragment.getValidity();
        cardCVV = secureCodeFragment.getValue();
        if (TextUtils.isEmpty(cardName)) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ", ""))) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid card number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardValidity) || !CreditCardUtils.isValidDate(cardValidity)) {
            Toast.makeText(CheckOutActivity.this, "Enter correct validity", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardCVV) || cardCVV.length() < 3) {
            Toast.makeText(CheckOutActivity.this, "Enter valid security number", Toast.LENGTH_SHORT).show();
        } else
        {
            String custId = readPref.getCustomerID();
            String expMonth = cardValidity.substring(0, 2);
            String expYear = cardValidity.substring(3);
            String cardNo = cardNumber;
            String cvv = cardCVV;
            String userId = readPref.getUserId();
            String email = getIntent().getStringExtra("business_email");
            String desc = "";
            String name = cardName;
            saveCard(custId, expMonth, expYear, cardNo, cvv, userId, email, desc, name);
        }
    }

    private void saveCard(String custId, String expMonth, String expYear, String cardNo, String cvv, String userId, String email, String desc, String name) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<SaveCardResponse> saveCardResponseCall = service.saveCard(custId, expMonth, expYear, cardNo, cvv, userId, email, desc, name);
        progressDialog = new ProgressDialog(CheckOutActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        saveCardResponseCall.enqueue(new Callback<SaveCardResponse>() {
            @Override
            public void onResponse(Call<SaveCardResponse> call, Response<SaveCardResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResult();
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(CheckOutActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        String businessEmail = getIntent().getStringExtra("business_email");
                        String paymentType = getIntent().getStringExtra("payment_type");
                        Intent intent = new Intent(CheckOutActivity.this, ScheduleReportActivity.class);
                        intent.putExtra("business_email", businessEmail);
                        intent.putExtra("payment_type", paymentType);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CheckOutActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SaveCardResponse> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        numberFragment = new CCNumberFragment();
        nameFragment = new CCNameFragment();
        validityFragment = new CCValidityFragment();
        secureCodeFragment = new CCSecureCodeFragment();
        adapter.addFragment(numberFragment);
        adapter.addFragment(nameFragment);
        adapter.addFragment(validityFragment);
        adapter.addFragment(secureCodeFragment);
        total_item = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragment_container, cardBackFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int pos = viewPager.getCurrentItem();
        if (pos > 0) {
            viewPager.setCurrentItem(pos - 1);
        } else
            super.onBackPressed();
    }

    public void nextClick() {
        btnNext.performClick();
    }
}
