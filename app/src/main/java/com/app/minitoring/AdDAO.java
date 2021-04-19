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
public interface AdDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAd(Ad ad);

    @Update
    void updateAd(Ad ad);

    @Delete
    void deleteAd(Ad ad);

    @Query("SELECT * FROM Ad")
    public LiveData<List<Ad>> listAds();

    @Query("DELETE FROM Ad")
    void deleteAll();

    @Query("SELECT * FROM Ad WHERE avito_ad_id = :avitoAdId")
    public Ad checkIfExtists(String avitoAdId);
}
