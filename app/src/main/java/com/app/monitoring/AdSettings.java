package com.app.monitoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.monitoring.databinding.ActivityAdSettingsBinding;

public class AdSettings extends AppCompatActivity {
    private ActivityAdSettingsBinding binding;
    private AdSubscriptionViewModel adSubscriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adSubscriptionViewModel = new ViewModelProvider(this).get(AdSubscriptionViewModel.class);
    }

    public void onClickOpenSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.avito.ru"));
        startActivity(browserIntent);
    }

    public void onClickStart(View view) {
        String subscriptionName = binding.editTextUrlName.getText().toString();
//                .trim();
        String subscriptionUrl = binding.editTextUrl.getText().toString();
//                .trim();

        // TODO check separately name and url to be unique
        if (!subscriptionName.isEmpty() && !subscriptionUrl.isEmpty()) {
            subscriptionName = subscriptionName.trim().toUpperCase();
            subscriptionUrl = subscriptionUrl.trim().toLowerCase();
            AdSubscription mySubscription = new AdSubscription(subscriptionName, subscriptionUrl);
            try {
                adSubscriptionViewModel.insert(mySubscription);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);


        }
    }
}