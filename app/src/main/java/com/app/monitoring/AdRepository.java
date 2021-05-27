package com.app.monitoring;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class AdRepository {

    private AdDAO mAdDAO;
    private ScrapedAdDao mScrapedAdDao;
    private AdSubscriptionDao mAdSubcriptionDao;
    private LiveData<List<Ad>> mAllAds;
    private LiveData<List<ScrapedAd>> mAllScrapedAds;
    private LiveData<List<AdSubscription>> mAllAdSubscriptions;
    List<AdSubscription> mListAdSubscriptions;
    private LiveData<List<ScrapedAd>> mAllVisibleScrapedAds;
    //    private List<ScrapedAd> mAllScrapedAdsList;

    // Note that in order to unit test the AdRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    AdRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        mAdDAO = db.adDAO();
        mScrapedAdDao = db.scrapedAdDao();
        mAdSubcriptionDao = db.adSubscriptionDao();

        mAllAds = mAdDAO.listAds();
        mAllScrapedAds = mScrapedAdDao.listAds();
        mAllVisibleScrapedAds = mScrapedAdDao.listVisibleAds();
        mAllAdSubscriptions = mAdSubcriptionDao.listLiveDataAdSubscriptions();
//        mListAdSubscriptions = mAdSubcriptionDao.listSubscriptions();
//        mAllScrapedAdsList = mScrapedAdDao.ArayListScrapedAd();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Ad>> getAllAds() {
        return mAllAds;
    }

    LiveData<List<ScrapedAd>> getAllScrapedAds() {
        return mAllScrapedAds;
    }

    LiveData<List<ScrapedAd>> getmAllVisibleScrapedAds() {
        return mAllVisibleScrapedAds;
    }

    LiveData<List<AdSubscription>> getAllAdSubscriptions() {
        return mAllAdSubscriptions;
    }

//    public List<AdSubscription> getListOfSubscriptions() {
//        return mListAdSubscriptions;
//    }

      public List<AdSubscription> getListOfSubscriptions() {
          AppDatabase.databaseWriteExecutor.execute(() -> {
              mListAdSubscriptions = mAdSubcriptionDao.listSubscriptions();
          });
          return mListAdSubscriptions;
    }



    //    List<ScrapedAd> getAllScrapedAdsList() {
//        return mAllScrapedAdsList;
//    }
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

    void deleteScrapedAd(ScrapedAd ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mScrapedAdDao.deleteAd(ad);
        });
    }

    void updateScrapedAd(ScrapedAd ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mScrapedAdDao.updateAd(ad);
        });
    }

    // AdSubscriptions
    void insertSubscription(AdSubscription ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdSubcriptionDao.insertAdSubscription(ad);
        });
    }

    void updateSubscription(AdSubscription ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdSubcriptionDao.updateAdSubscription(ad);
        });
    }

    void deleteAllSubscriptions() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdSubcriptionDao.deleteAllAdSubscriptions();
        });
    }

    void deleteSubscription(AdSubscription ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdSubcriptionDao.deleteAdSubscription(ad);
        });
    }

    void getSubscription(String name) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAdSubcriptionDao.getAdSubscriptionByName(name);
        });
    }
}