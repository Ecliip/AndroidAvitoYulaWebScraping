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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAdSubscription(AdSubscription adSubscription);

    @Update
    void updateAdSubscription(AdSubscription adSubscription);

    @Delete
    void deleteAdSubscription(AdSubscription adSubscription);

    @Query("SELECT * FROM AdSubscription")
    public LiveData<List<AdSubscription>> listLiveDataAdSubscriptions();

    @Query("SELECT * FROM AdSubscription")
    public List<AdSubscription> listSubscriptions();

    @Query("DELETE FROM AdSubscription")
    void deleteAllAdSubscriptions();

    @Query("SELECT * FROM AdSubscription WHERE name = :name")
    public AdSubscription getAdSubscriptionByName(String name);


}
