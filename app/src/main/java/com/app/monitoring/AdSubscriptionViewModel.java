package com.app.monitoring;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AdSubscriptionViewModel extends AndroidViewModel {
    private AdRepository mRepository;
    private final LiveData<List<AdSubscription>> mAllAdSubscriptions;
    private AdScraper mScraper;

    public AdSubscriptionViewModel(Application application) {
        super(application);
        mRepository = new AdRepository(application);
        mAllAdSubscriptions = mRepository.getAllAdSubscriptions();
        mScraper = new AdScraper(getApplication().getApplicationContext());
    }

    public AdScraper getmScraper() {
        return mScraper;
    }

    LiveData<List<AdSubscription>> getmAllAdSubscriptions() {
        return mAllAdSubscriptions;
    }

    public void insert(AdSubscription ad) { mRepository.insertSubscription(ad); }

    public void deleteAdSubscription(AdSubscription ad) { mRepository.deleteSubscription(ad);}

    public void updateAdSubscription(AdSubscription ad) {mRepository.updateSubscription(ad); }

    public void removeAllAds() {
        mRepository.deleteAllSubscriptions();
    }
}