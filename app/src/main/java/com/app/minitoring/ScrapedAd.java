package com.app.minitoring;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url", "avito_ad_id"},
        unique = true)})
public class ScrapedAd {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long scrapedAdId;
    @NonNull
    public String avito_ad_id;
    @NonNull
    public String name;
    @NonNull
    public String url;
    @NonNull
    public String user_query;


    public ScrapedAd(@NonNull String avito_ad_id, @NonNull String name, @NonNull String url, @NonNull String user_query) {
        this.avito_ad_id = avito_ad_id;
        this.name = name;
        this.url = url;
        this.user_query = user_query;
    }
}
