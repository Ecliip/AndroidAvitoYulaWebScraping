package com.app.minitoring;

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

//    ROOM FINISH THIS QUERY LATER
//    https://stackoverflow.com/questions/4253960/sql-how-to-properly-check-if-a-record-exists
    @Query("SELECT * FROM ScrapedAd WHERE avito_ad_id = :avitoAdId")
    public LiveData<List<ScrapedAd>> checkIfExtists(String avitoAdId);

    @Query("DELETE FROM ScrapedAd")
    void deleteAll();
}
