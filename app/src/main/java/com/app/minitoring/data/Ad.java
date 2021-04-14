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
    public int id;
    public String avito_ad_id;
    public String name;
    public String url;

    public Ad(String avito_ad_id, String name, String url) {
        this.avito_ad_id = avito_ad_id;
        this.name = name;
        this.url = url;
    }

    public Ad(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Ad() {}
}