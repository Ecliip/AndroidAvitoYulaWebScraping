package com.app.minitoring;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.List;

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

    public void callWorkManager(String tartgetUrl) {
        System.out.println(tartgetUrl);
        Data.Builder builder = new Data.Builder();
        if (tartgetUrl != null) {
            builder.putString("targetUrl", tartgetUrl);
        }
        Data data = builder.build();

        OneTimeWorkRequest blurRequest =
                new OneTimeWorkRequest.Builder(AdWorker.class)
                        .setInputData(data)
                        .build();
        mWorkManager.enqueue(blurRequest);
    }

    LiveData<List<Ad>> getAllAds() { return mAllAds; }

    public void insert(Ad ad) { mRepository.insert(ad); }

    public void removeAllAds() {
        mRepository.removeAllAds();
    }
}
