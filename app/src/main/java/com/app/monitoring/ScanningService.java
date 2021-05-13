package com.app.monitoring;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

// TODO implement this service later
public class ScanningService extends IntentService {
    private List<ScannedAd> scannedAds;

    public ScanningService() {
        super("ScanningService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}