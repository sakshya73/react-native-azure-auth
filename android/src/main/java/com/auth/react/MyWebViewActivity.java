package com.auth.react;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MyWebViewActivity extends Activity {

    private WebView webView;
    private ProgressBar progressBar;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String redirectUri = intent.getStringExtra("redirectUri");
        webView = new WebView(this);
        progressBar = findViewById(R.id.progressBar);

        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if needed
        webView.setWebViewClient(new WebViewClient() {

          @Override
          public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
          }

          @Override
            public void onPageFinished(WebView view, String url) {
            Log.d("WebView", "onPageFinished: ");
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.d("WebView", "Returned URL: " + url);
                // Check if the URL matches the redirect URL
                if (url.startsWith(redirectUri)) {
                    // Communicate data back to React Native
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", url);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    return true; // We handled the URL loading
                }
                // Continue loading the URL as usual
                return super.shouldOverrideUrlLoading(view, request);
            }
        });


        if (url != null) {
            webView.loadUrl(url);
        }
          setContentView(webView);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
          Intent returnIntent = new Intent();
          setResult(Activity.RESULT_CANCELED, returnIntent);
          finish();
        }
    }
}
