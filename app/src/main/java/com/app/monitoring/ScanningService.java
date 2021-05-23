package com.app.monitoring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO implement real scanning for an URL
// TODO launch notification for scanned ads
// TODO scan multiple URLs
// TODO launch notification for each of them
// TODO improve navigation between views
// TODO improve views for different screen sizes (also vertical and horizontal)
// TODO improve verification new URLs
// TODO add a switcher for each URL (turn on and turn off)



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
    private String currentUrl;
    private int urlCounter = 0;
    private int [] controller;
    private AdDAO adDao;
    private ScrapedAdDao scrapedAdDao;

    @Override
    public void onCreate() {
        super.onCreate();
        scannedAds = new ArrayList<>();
        subscriptionList = new ArrayList<>();
        IS_SERVICE_RUNNING = true;
        adRepository = new AdRepository(getApplication());
        Log.i(TAG, "inside onCreate");
        scrapedAdDao = AppDatabase.getDatabase(getApplicationContext()).scrapedAdDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "inside onStartCommand");
        createNotificationChannel();
        Intent intentBrowsingActivity = new Intent(this, MainMenuActivity.class);
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
        currentUrl = getNextSubscriptionUrl();
        Log.i(TAG, "inside startScanning");
        handler = new Handler(Looper.myLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentUrl = getNextSubscriptionUrl();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ScrapedAd anAdWithCurrentUrl = scrapedAdDao.checkIfAdWithUrlExists(currentUrl);
                        Elements ads = getAdsFromTheWeb();
                            if (anAdWithCurrentUrl == null) {
                                insertAdsAtDb(ads, true);
                            } else {
                                insertAdsAtDb(ads, false);
                            }
                    }
                }).start();
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(runnable);
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

    public String getNextSubscriptionUrl() {
        List<AdSubscription> subscriptions = accessAllSubscriptions();
        String result;
        if (subscriptions == null) {
            result = "";
        } else {
            if (subscriptions.size() > 0) {
                result = subscriptions.get(urlCounter).getUrl();
            } else {
                result = "";
            }
        }
        return result;
    }

//    TODO: this function gives NullPointerException
    private List<AdSubscription> accessAllSubscriptions() {
        Log.i(TAG, "INSIDE accessAllSubscriptions()");
        List<AdSubscription> adSubscriptions = adRepository.getListOfSubscriptions();
        return adSubscriptions;
    }

    private void insertAdsAtDb(Elements ads, boolean isHidden) {
        for (Element ad : ads) {
            Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
            String headingHref = heading.selectFirst("a").attr("href");
            String adName = ad.select("h3").text();
            // avito ad id
            String id = ad.attr("id");
            headingHref = "https://www.avito.ru".concat(headingHref);
            ScrapedAd scrapedAd = new ScrapedAd(id, adName, headingHref, currentUrl, isHidden);
//                                Ad adResult = adDao.checkIfExtists(scrapedAd.getAvito_ad_id());
            ScrapedAd scrapedAdResult = scrapedAdDao.checkIfExists(id);
            if (scrapedAdResult == null) {
                Log.i("NEW HIDDEN ADD", String.format("%s: %s - %s", TAG, adName, id));
                scrapedAdDao.insertAd(scrapedAd);
            }
        }
    }

    private Elements getAdsFromTheWeb() {
        Document doc = null;
        try {
            doc = Jsoup.connect(currentUrl)
    //                                    .userAgent("Chrome/90.0.4430.85")
    //                                    .referrer("http://www.google.com")
    //                                   .timeout(10000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ads = doc.select(".iva-item-root-G3n7v");
        return ads;
    }

}