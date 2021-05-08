package com.app.monitoring;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ObserverService extends LifecycleService {
    private static final String TAG = "ObserverService";
    private ScrapedAdViewModel mScrapedAdViewModel;
    private AdRepository myRepository;
    private static final String CHANNEL_ID = "1";

    public ObserverService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        myRepository = new AdRepository(getApplication());
        myRepository.getAllScrapedAds().observe(this, ads -> {
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

                        Intent intent = new Intent(this, SiteSearchActivity.class);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    private void createNotificationChannel() {
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
}