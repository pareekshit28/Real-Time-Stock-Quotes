package com.example.zerodhademo.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zerodhademo.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(request.getUrl().getHost().equals("127.0.0.1")){
                    Log.d("token", request.getUrl().getQueryParameter("request_token"));
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("requestToken", request.getUrl().getQueryParameter("request_token"));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                return false;
            }
        });
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}