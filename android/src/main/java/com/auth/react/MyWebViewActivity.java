package com.auth.react;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if needed
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                String urlToMatch = ""; // Check if the URL matches the redirect URL //Change the url based on your
                                        // requirement
                if (url.startsWith(urlToMatch)) {
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

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
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
