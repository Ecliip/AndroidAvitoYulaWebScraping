package com.app.minitoring.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OriginUrlDAO {
    @Insert
    void insertOriginUrl(OriginUrl url);
    @Update
    void updateOriginUrl(OriginUrl url);
    @Delete
    void deleteOriginUrl(OriginUrl url);
    @Query("SELECT * FROM OriginUrl")
    public List<OriginUrl> listOriginUrls();
}
