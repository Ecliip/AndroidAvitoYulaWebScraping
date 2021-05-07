package com.app.monitoring;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.app.monitoring.NotificationHelper.CANAL_ONE_ID;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {

    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = NotificationManagerCompat.from(this);
    }

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int requestID = (int) System.currentTimeMillis();

        String name = "Notificatio name";
        String description = "Notification Description";

        NotificationCompat.Builder notification = new NotificationCompat.Builder(
                this,
                CANAL_ONE_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(name)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.GREEN)
                .setAutoCancel(true);

        Intent actionIntent = new Intent(this, MainMenuActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(
                this,
                requestID,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(actionPendingIntent);

        notificationManager.notify(1, notification.build());

        System.out.println("Notification Service");
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}