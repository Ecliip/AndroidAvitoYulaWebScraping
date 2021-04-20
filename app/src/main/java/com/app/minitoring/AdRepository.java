package com.app.minitoring;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class AdRepository {

    private AdDAO mAdDAO;
    private ScrapedAdDao mScrapedAdDao;
    private LiveData<List<Ad>> mAllAds;
    private LiveData<List<ScrapedAd>> mAllScrapedAds;

    // Note that in order to unit test the AdRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    AdRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mAdDAO = db.adDAO();
        mAllAds = mAdDAO.listAds();

        mScrapedAdDao = db.scrapedAdDao();
        mAllScrapedAds = mScrapedAdDao.listAds();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Ad>> getAllAds() {
        return mAllAds;
    }

    LiveData<List<ScrapedAd>> getAllScrapedAds() {
        return mAllScrapedAds;
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Ad ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdDAO.insertAd(ad);
        });
    }

    void insert(ScrapedAd ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mScrapedAdDao.insertAd(ad);
        });
    }

    void removeAllAds() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdDAO.deleteAll();
        });
    }

    void removeAllScrapedAds() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mScrapedAdDao.deleteAll();
        });
    }
}
