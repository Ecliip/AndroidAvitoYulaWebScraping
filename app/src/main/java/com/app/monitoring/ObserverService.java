package com.app.monitoring;

import android.app.IntentService;
import android.os.Build;
import android.util.Log;

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

    public ObserverService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myRepository = new AdRepository(getApplication());
        myRepository.getAllScrapedAds().observe(this, ads -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, String.valueOf(ads.stream().count()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}