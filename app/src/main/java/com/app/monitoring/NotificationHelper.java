package com.app.monitoring;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationHelper extends Application {
    public static final String CANAL_ONE_ID = "canal1";
    public static final String CANAL_ONE_NAME = "Canal One";
    public static final String CANAL_ONE_DESCRIPTION = "Canal One Example";

    @Override
    public void onCreate() {
        super.onCreate();

        crearCanalesNotificaciones();
    }

    private void crearCanalesNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canalOne = new NotificationChannel(
                    CANAL_ONE_ID,
                    CANAL_ONE_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            canalOne.setDescription(CANAL_ONE_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canalOne);
        }
    }
}
