package com.app.monitoring;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.monitoring.databinding.ActivityEmbeddedPageBinding;

public class EmbeddedPageActivity extends AppCompatActivity {
    private ActivityEmbeddedPageBinding binding;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmbeddedPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        webView = binding.myWebView;
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }
}