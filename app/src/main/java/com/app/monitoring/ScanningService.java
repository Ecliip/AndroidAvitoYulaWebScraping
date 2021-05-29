package com.app.monitoring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

public class ScanningService extends LifecycleService {
    private List<ScannedAd> scannedAds;
    private static List<AdSubscription> subscriptionList;
    public static final String SUBSCRIPTION_NAME = "subscriptionName";
    public static final String SUBSCRIPTION_URL = "subscriptionUrl";
    public static final String TAG = "ScanningService";
    private final String CHANNEL_ID = "Channel1";
    private Handler handler;
    public static boolean IS_SERVICE_RUNNING = false;
    AdRepository adRepository;
    private static String currentUrl;
    private int urlCounter = 0;
    private int [] controller;
    private AdDAO adDao;
    private ScrapedAdDao scrapedAdDao;
    private AdSubscriptionDao adSubscriptionDao;
    private Runnable runnable;
    private AdRepository myRepository;
    private Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();

        scannedAds = new ArrayList<>();
        subscriptionList = new ArrayList<>();
        IS_SERVICE_RUNNING = true;
        adRepository = new AdRepository(getApplication());
        Log.i(TAG, "inside onCreate");
        scrapedAdDao = AppDatabase.getDatabase(getApplicationContext()).scrapedAdDao();
        adSubscriptionDao = AppDatabase.getDatabase(getApplicationContext()).adSubscriptionDao();
        intent = new Intent(this, EmbeddedPageActivity.class);


        createNotificationChannelForAds();
        myRepository = new AdRepository(getApplication());
        myRepository.getmAllVisibleScrapedAds().observe(this, ads -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Long countAds = ads.stream().count();
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (countAds > 0) {
                        String name = "New ads in the site";
                        String description = String.format("Check for new %s ads", countAds);
                        intent = new Intent(this, EmbeddedPageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                        NotificationCompat.Builder notification = new NotificationCompat.Builder(
                                this,
                                CHANNEL_ID)
                                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                                .setContentTitle(name)
                                .setContentText(description)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setColor(Color.GREEN)
                                .setVibrate(new long[] {1000, 1000, 1000, 1000})
                                .setContentIntent(pendingIntent)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(description))
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                        notificationManager.notify(1, notification.build());
                    }
                }
            }
        });
    }

    private void createNotificationChannelForAds() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.i(TAG, "inside onStartCommand");
        createNotificationChannel();
        Intent intentBrowsingActivity = new Intent(this, MainMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentBrowsingActivity, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_baseline_open_in_browser_24)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build();

//        scrapedAdDao.deleteAll();
        startForeground(1, notification);
        startScanning();

        Log.i(TAG, "inside onStartCommand - notification created");
        return START_STICKY;
    }

    private void startScanning() {
//        currentUrl = getNextSubscriptionUrl();
        Log.i(TAG, "inside startScanning");
        handler = new Handler(Looper.myLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                currentUrl = getNextSubscriptionUrl();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ScrapedAd anAdWithCurrentUrl = scrapedAdDao.checkIfAdWithUrlExists(currentUrl);
                        Elements ads = getAdsFromTheWeb();
                        if (anAdWithCurrentUrl == null) {
                            insertAdsAtDb(ads, true, "NEW HIDDEN ADD");
                        } else {
                            insertAdsAtDb(ads, false, "NEW VISIBLE ADD");
                        }
                    }
                }).start();
                handler.postDelayed(this, 20000);
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
        super.onBind(intent);
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


//    public static void addSubscription(String name, String url) {
//        AdSubscription subscription = new AdSubscription(name, url);
//        subscriptionList.add(subscription);
//    }
    public String getNextSubscriptionUrl() {
        List<AdSubscription> subscriptions = accessAllSubscriptions();
        String result = null;
        System.out.println("Size "+subscriptions.size());
        if (subscriptions == null || subscriptions.isEmpty()) {
            onDestroy();
        } else {
            if (subscriptions.size() > 0) {
                result = subscriptions.get(urlCounter).getUrl();
            }
        }
        return result;
    }

//    TODO: this function gives NullPointerException
    private List<AdSubscription> accessAllSubscriptions() {
        Log.i(TAG, "INSIDE accessAllSubscriptions()");
        // TODO: access the list of subscription
        new Thread(new Runnable() {
            @Override
            public void run() {
                subscriptionList = adSubscriptionDao.listSubscriptions();
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriptionList;
    }

    private void insertAdsAtDb(Elements ads, boolean isHidden, String debugString) {
        if (ads != null) {
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
                    Log.i(debugString, String.format("%s: %s - %s", TAG, adName, id));
                    scrapedAdDao.insertAd(scrapedAd);
                }
            }
        }
    }

    private Elements getAdsFromTheWeb() {
        Document doc = null;
        Elements ads;
        try {
            doc = Jsoup.connect(currentUrl).get();
            ads = doc.select(".iva-item-root-G3n7v");
        } catch (Exception e) {
            e.printStackTrace();
            ads = null;
        }
        if (ads != null) {
            return ads;
        } else {
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("!!! -- IN DESTROY!!!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                scrapedAdDao.deleteAll();
            }
        }).start();
        handler.removeCallbacks(runnable);
        stopForeground(true);
        stopSelf();
        IS_SERVICE_RUNNING = false;
    }

    public static String getCurrentUrl() {
        return currentUrl;
    }
}