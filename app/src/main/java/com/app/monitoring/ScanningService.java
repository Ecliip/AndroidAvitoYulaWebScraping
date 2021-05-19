package com.app.monitoring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

// TODO implement this service later
public class ScanningService extends Service {
    private List<ScannedAd> scannedAds;
    private static List<AdSubscription> subscriptionList;
    public static final String SUBSCRIPTION_NAME = "subscriptionName";
    public static final String SUBSCRIPTION_URL = "subscriptionUrl";
    public static final String TAG = "ScanningService";
    private final String CHANNEL_ID = "Channel1";
    private Handler handler;
    public static boolean IS_SERVICE_RUNNING = false;
    AdRepository adRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        scannedAds = new ArrayList<>();
        subscriptionList = new ArrayList<>();
        IS_SERVICE_RUNNING = true;
        adRepository = new AdRepository(getApplication());
        Log.i(TAG, "inside onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "inside onStartCommand");
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
        startScanning();

        Log.i(TAG, "inside onStartCommand - notification created");
        return START_STICKY;
    }

    private void startScanning() {
        Log.i(TAG, "inside startScanning");
        handler = new Handler(Looper.myLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = getSubscriptionListAdString();
                        String theResult = result == null ? "empty" : result;
                        if (!result.isEmpty()) {
                            Log.i(TAG, "inside startScanning" + " " + theResult);
                        } else {
                            Log.i(TAG, "inside startScanning" + " No Subs");
                        }
                    }
                }).start();

                handler.postDelayed(this, 15000);
            }
        };
        handler.postDelayed (runnable,5000);
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


    private static class Subscription {
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
        IS_SERVICE_RUNNING = false;
        super.onDestroy();
    }

//    public static void addSubscription(String name, String url) {
//        AdSubscription subscription = new AdSubscription(name, url);
//        subscriptionList.add(subscription);
//    }

    public String getSubscriptionListAdString() {
        List<AdSubscription> subscriptions = accessAllSubscriptions();
        if (subscriptions == null) {
            return "";
        } else {
            return subscriptions.toString();
        }
    }

//    TODO: this function gives NullPointerException
    private List<AdSubscription> accessAllSubscriptions() {
        List<AdSubscription> adSubscriptions = adRepository.getListOfSubscriptions();
        String aResult;
        if(adSubscriptions == null) {
            System.out.println("List is empty");
            aResult = "";
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                adSubscriptions.stream().forEach(l -> System.out.println(TAG + " in FOREACH: " + l.getName()));
            }
        }

//        String theResult = aResult.isEmpty() ? "It's null" : aResult;
//        System.out.println(theResult);
        return adSubscriptions;
    }
}