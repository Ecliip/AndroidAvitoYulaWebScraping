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
public interface AdSubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AdSubscription adSubscription);

    @Update
    void update(AdSubscription adSubscription);

    @Delete
    void deleteAd(AdSubscription adSubscription);

    @Query("SELECT * FROM AdSubscription")
    public LiveData<List<AdSubscription>> listAdSubscriptions();

    @Query("DELETE FROM AdSubscription")
    void deleteAllAdSubscriptions();

    @Query("SELECT * FROM AdSubscription WHERE name = :name")
    public AdSubscription getIfExists(String name);
}
