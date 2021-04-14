package com.app.minitoring.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url", "avito_ad_id"},
        unique = true)})
public class Ad {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long adId;
    public String avito_ad_id;
    public String name;
    public String url;

}