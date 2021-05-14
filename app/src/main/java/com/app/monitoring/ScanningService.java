package com.app.monitoring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

// TODO implement this service later
public class ScanningService extends Service {
    private List<ScannedAd> scannedAds;
    private List<Subscription> subscriptionList;
    public static final String SUBSCRIPTION_NAME = "subscriptionName";
    public static final String SUBSCRIPTION_URL = "subscriptionUrl";
    public static final String TAG = "ScanningService";
    private final String CHANNEL_ID = "Channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        scannedAds = new ArrayList<>();
        subscriptionList = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String name = intent.getStringExtra(SUBSCRIPTION_NAME);
//        String url = intent.getStringExtra(SUBSCRIPTION_URL);
//        Subscription subscription = new Subscription(name, url);
//        subscriptionList.add(subscription);

        createNotificationChannel();
        Intent intentBrowsingActivity = new Intent(this, BrowsingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intentBrowsingActivity, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_baseline_open_in_browser_24)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build();

        startForeground(1, notification);
        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, "Foreground Notification", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);


        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


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




    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}