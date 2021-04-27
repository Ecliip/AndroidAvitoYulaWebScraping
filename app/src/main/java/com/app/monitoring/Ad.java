package com.app.monitoring;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url", "avito_ad_id"},
        unique = true)})
public class Ad {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long adId;
    private final String avito_ad_id;
    private final String name;
    private final String url;
    private final String user_query;


    public Ad(@NonNull String avito_ad_id, @NonNull String name, @NonNull String url, @NonNull String user_query) {
        this.avito_ad_id = avito_ad_id;
        this.name = name;
        this.url = url;
        this.user_query = user_query;
    }

    public long getAdId() {
        return adId;
    }

    public String getAvito_ad_id() {
        return avito_ad_id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUser_query() {
        return user_query;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }
}