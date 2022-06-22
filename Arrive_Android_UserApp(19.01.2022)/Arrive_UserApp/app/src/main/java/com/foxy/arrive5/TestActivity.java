package com.foxy.arrive5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.foxy.arrive5.Settings.ScheduleReportActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://arrive5.pcthepro.com/admin/payment/getpayment?user_id=13&amount=345");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("url", url);
                if (!url.startsWith("source:")) {
                    view.loadUrl(url);
                }
                if (url.startsWith("source://")) {
                    String html = null;
                    try {
                        html = URLDecoder.decode(url, "UTF-8").substring(9);
                        String s = html.substring(html.indexOf("<h1>Your transction id is 40019545766</h1>") + 52, html.indexOf("<h1>Your transction id is 40019545766</h1>") + 63);
                        String status = html.substring(html.indexOf("<h1>status = true</h1>") + 13, html.indexOf("<h1>status = true</h1>") + 17);
                        String transactionId = s;
                        if (status.equalsIgnoreCase("true")) {
                            Intent intent = new Intent(TestActivity.this, ScheduleReportActivity.class);
                            intent.putExtra("business_email", getIntent().getStringExtra("business_email"));
                            intent.putExtra("payment_type", getIntent().getStringExtra("payment_type"));
                            startActivity(intent);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.startsWith("http://arrive5.pcthepro.com/admin/payment/do_user_payment")) {
                    webView.loadUrl(
                            "javascript:this.document.location.href = 'source://' + encodeURI(document.documentElement.outerHTML);");
                }
            }
        });
    }
}
