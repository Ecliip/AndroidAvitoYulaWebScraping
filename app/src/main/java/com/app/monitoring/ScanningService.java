package com.app.monitoring;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

// TODO implement this service later
public class ScanningService extends IntentService {
    private List<ScannedAd> scannedAds;
    private List<Subscription> subscriptionList;
    public static final String SUBSCRIPTION_NAME = "subscriptionName";
    public static final String SUBSCRIPTION_URL = "subscriptionUrl";


    private class Subscription {
        private final String name;
        private final String url;

        private Subscription(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }


    public ScanningService() {
        super("ScanningService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String name = intent.getStringExtra(SUBSCRIPTION_NAME);
        String url = intent.getStringExtra(SUBSCRIPTION_URL);
        Subscription subscription = new Subscription(name, url);
        subscriptionList.add(subscription);

    }
}