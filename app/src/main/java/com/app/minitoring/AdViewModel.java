package com.app.minitoring;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdViewModel extends AndroidViewModel {

    private WorkManager mWorkManager;
    private AdRepository mRepository;
    private final LiveData<List<Ad>> mAllAds;

    public AdViewModel (Application application) {
        super(application);
        mRepository = new AdRepository(application);
        mAllAds = mRepository.getAllAds();
        mWorkManager = WorkManager.getInstance(application);
    }

    public void callWorkManager() {
        mWorkManager.enqueue(new PeriodicWorkRequest.Builder(AdWorker.class, 5, TimeUnit.SECONDS).build());
    }

    LiveData<List<Ad>> getAllAds() { return mAllAds; }

    public void insert(Ad ad) { mRepository.insert(ad); }

    public void removeAllAds() {
        mRepository.removeAllAds();
    }
}
