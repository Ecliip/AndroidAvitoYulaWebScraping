package com.app.monitoring;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.app.monitoring.databinding.ActivityEmbeddedPageBinding;

public class EmbeddedPageActivity extends AppCompatActivity {
    public static final String URL = "URL";
    private final String TAG = "EmbeddedPageActivity";
    private ActivityEmbeddedPageBinding binding;
    private WebView webView;
    private String urlToOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmbeddedPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        webView = binding.myWebView;
        urlToOpen = ScanningService.getCurrentUrl();
        Log.i(TAG, " IN OnCreate: Url to Open ->>" + urlToOpen);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //view.loadUrl(url);
                System.out.println("hello");
                return false;
            }
        });
        webView.loadUrl(urlToOpen);


//        String url = "https://paul.kinlan.me/";
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//
//        int colorInt = Color.parseColor("#FF0000"); //red
//
//        CustomTabsIntent customTabsIntent = builder
//                .setShowTitle(true)
//                .set
//                .build();
//        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}