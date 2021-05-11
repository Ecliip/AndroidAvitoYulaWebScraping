package com.app.monitoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.monitoring.databinding.ActivityAdSettingsBinding;

public class AdSettings extends AppCompatActivity {
    private ActivityAdSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void onClickOpenSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.avito.ru"));
        startActivity(browserIntent);
    }

    public void onClickStart(View view) {

    }


}