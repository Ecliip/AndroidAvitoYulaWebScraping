package com.app.minitoring;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScrapedAdViewModel extends AndroidViewModel {
    private AdRepository mRepository;
    private final LiveData<List<ScrapedAd>> mAllScrapedAds;

    public ScrapedAdViewModel (Application application) {
        super(application);
        mRepository = new AdRepository(application);
        mAllScrapedAds = mRepository.getAllScrapedAds();
    }

    LiveData<List<ScrapedAd>> getAllAds() { return mAllScrapedAds; }

    public void insert(ScrapedAd ad) { mRepository.insert(ad); }

    public void removeAllAds() {
        mRepository.removeAllAds();
    }
}
