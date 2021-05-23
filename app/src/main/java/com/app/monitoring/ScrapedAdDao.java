package com.app.monitoring;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScrapedAdDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAd(ScrapedAd scrapedAd);

    @Update
    void updateAd(ScrapedAd scrapedAd);

    @Delete
    void deleteAd(ScrapedAd scrapedAd);

    @Query("SELECT * FROM ScrapedAd")
    public LiveData<List<ScrapedAd>> listAds();

//    @Query("SELECT * FROM ScrapedAd")
//    public List<ScrapedAd> ArayListScrapedAd();

    @Query("SELECT * FROM ScrapedAd WHERE avito_ad_id = :avitoAdId")
    public ScrapedAd checkIfExists(String avitoAdId);

    @Query("SELECT * FROM ScrapedAd WHERE user_query = :url")
    public ScrapedAd checkIfAdWithUrlExists(String url);

    @Query("DELETE FROM ScrapedAd")
    void deleteAll();
}