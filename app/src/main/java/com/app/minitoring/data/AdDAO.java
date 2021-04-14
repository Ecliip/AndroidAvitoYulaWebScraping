package com.app.minitoring.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AdDAO {
    @Insert
    void insertAd(Ad ad);
    @Update
    void updateAd(Ad ad);
    @Delete
    void deleteAd(Ad ad);
    @Query("SELECT * FROM Ad")
    public List<Ad> listAds();
}
