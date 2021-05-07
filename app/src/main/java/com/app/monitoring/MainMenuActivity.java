package com.app.monitoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;

public class MainMenuActivity extends AppCompatActivity {
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
       Intent intent = new Intent(this, NotificationService.class);
       startService(intent);
    }
}