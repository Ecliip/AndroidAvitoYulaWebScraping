package com.app.monitoring;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;

import static com.app.monitoring.NotificationHelper.CANAL_ONE_ID;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "MainMenuActivity";
    PeriodicWorkRequest periodicWorkRequest;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);

        notificationManager = NotificationManagerCompat.from(this);
    }

    public void onClickSearch(View view) {
        EditText editViewUrl  = findViewById(R.id.editViewUrl);
        String targetUrl = editViewUrl.getText().toString();
        Intent intent = new Intent(this, SiteSearchActivity.class);
        intent.putExtra(SiteSearchActivity.EXTRA_TARGET_URL, targetUrl);
        startActivity(intent);
    }

    public void onClickOpenSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.avito.ru"));
        startActivity(browserIntent);
    }

    public void onNotify(View view) {
//       Intent intent = new Intent(this, NotificationService.class);
//       startService(intent);

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

        Log.i(TAG, "onNotify");
    }
}