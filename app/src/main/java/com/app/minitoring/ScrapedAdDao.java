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
    void insertAd(Ad ad);

    @Update
    void updateAd(Ad ad);

    @Delete
    void deleteAd(Ad ad);

    @Query("SELECT * FROM Ad")
    public LiveData<List<Ad>> listAds();

//    ROOM FINISH THIS QUERY LATER
//    https://stackoverflow.com/questions/4253960/sql-how-to-properly-check-if-a-record-exists
//    @Query("SELECT 1 FROM ScrapedAd WHERE")
//    public List<Ad> checkIfExtists();

    @Query("DELETE FROM Ad")
    void deleteAll();
}
