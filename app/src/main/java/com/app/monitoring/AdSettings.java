package com.app.monitoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.monitoring.databinding.ActivityAdSettingsBinding;

public class AdSettings extends AppCompatActivity {
    private static final String TAG = "AdSettings";
    private ActivityAdSettingsBinding binding;
    private AdSubscriptionViewModel adSubscriptionViewModel;
    String subscriptionName;
    String subscriptionUrl;
    private boolean isNewSubscription;
//    private final Intent service = new Intent(this, ScanningService.class);

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
            saveStringValues();
        // TODO check separately name and url to be unique
        Log.i(TAG, "will check if fields are not empty");
        if (!subscriptionName.isEmpty() && !subscriptionUrl.isEmpty()) {
            processStringValues();
            saveSubscriptionInDatabase();

            Log.i(TAG, "will check if service is running");
            if(!ScanningService.IS_SERVICE_RUNNING) {
                Log.i(TAG, "will create service");
                isNewSubscription = true;
                // TODO define isNewSubscription
                if (isNewSubscription) {
                    Intent service = new Intent(this, ScanningService.class);
//                    service.putExtra(ScanningService.SUBSCRIPTION_NAME, subscriptionName);
//                    service.putExtra(ScanningService.SUBSCRIPTION_URL, subscriptionUrl);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(service);
                        Log.i(TAG, "Android v >= Oreo: foreground service was created");
                    } else {
                        startService(service);
                        Log.i(TAG, "Android v < Oreo: foreground service was created");
                    }
                    Intent intent = new Intent(this, MainMenuActivity.class);
                    startActivity(intent);
                    Log.i(TAG, "Main activity started");
                }
            }
//            ScanningService.addSubscription(subscriptionName, subscriptionUrl);
        }
    }

    private void saveStringValues() {
        subscriptionName = binding.editTextUrlName.getText().toString();
        subscriptionUrl = binding.editTextUrl.getText().toString();
    }

    private void processStringValues() {
        subscriptionName = subscriptionName.trim().toUpperCase();
        subscriptionUrl = subscriptionUrl.trim().toLowerCase();
    }

    private void saveSubscriptionInDatabase() {
        AdSubscription mySubscription = new AdSubscription(subscriptionName, subscriptionUrl);
        adSubscriptionViewModel.insert(mySubscription);
    }
}

