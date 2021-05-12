package com.app.monitoring;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.monitoring.databinding.ActivityPrincipalMenuBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements AdSubscriptionAdapter.AdSubscriptionClickInterface {
    private static final String TAG = "MainMenuActivity";
    private static final String CHANNEL_ID = "1";
    private ActivityPrincipalMenuBinding binding;
    private AdSubscriptionViewModel mAdSubscriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FloatingActionButton fab = binding.addFab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, AdSettings.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = binding.recyclerviewSubscriptions;
        final AdSubscriptionAdapter adapter = new AdSubscriptionAdapter(new AdSubscriptionAdapter.AdDiff(),
                (AdSubscriptionAdapter.AdSubscriptionClickInterface) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdSubscriptionViewModel = new ViewModelProvider(this).get(AdSubscriptionViewModel.class);
        mAdSubscriptionViewModel.getmAllAdSubscriptions().observe(this, mySubscriptions -> adapter.submitList(mySubscriptions));
    }

    public void onClickSearch(View view) {
    }

    public void onClickOpenSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.avito.ru"));
        startActivity(browserIntent);
    }

    public void onNotify(View view) {
        String name = "Notificatio name";
        String description = "Notification Description";

        Intent intent = new Intent(this, MainMenuActivity.class);
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
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, notification.build());

        Log.i(TAG, "onNotify");
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

    @Override
    public void onDelete(int position) {
        final List<AdSubscription> subscriptions = mAdSubscriptionViewModel.getmAllAdSubscriptions().getValue();
        final AdSubscription subscription = subscriptions.get(position);
        mAdSubscriptionViewModel.deleteAdSubscription(subscription);
    }

    @Override
    public void onEdit(int position) {

    }
}