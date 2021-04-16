package com.app.minitoring;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AdViewModel extends AndroidViewModel {

    private AdRepository mRepository;
    private final LiveData<List<Ad>> mAllAds;

    public AdViewModel (Application application) {
        super(application);
        mRepository = new AdRepository(application);
        mAllAds = mRepository.getAllAds();
    }

    LiveData<List<Ad>> getAllAds() { return mAllAds; }

    public void insert(Ad ad) { mRepository.insert(ad); }
}
