package com.app.monitoring;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url", "avito_ad_id"},
        unique = true)})
public class ScrapedAd {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long scrapedAdId;
    @NonNull
    private final String avito_ad_id;
    @NonNull
    private final String name;
    @NonNull
    private final String url;
    @NonNull
    private String user_query;

    @ColumnInfo(defaultValue = "0")
    private Boolean hidden;

    public ScrapedAd(@NonNull String avito_ad_id, @NonNull String name, @NonNull String url, @NonNull String user_query, Boolean hidden) {
        this.avito_ad_id = avito_ad_id;
        this.name = name;
        this.url = url;
        this.user_query = user_query;
        this.hidden = hidden;
    }

    public long getScrapedAdId() {
        return scrapedAdId;
    }

    @NonNull
    public String getAvito_ad_id() {
        return avito_ad_id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public String getUser_query() {
        return user_query;
    }

    public void setScrapedAdId(long scrapedAdId) {
        this.scrapedAdId = scrapedAdId;
    }

    @NonNull
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}