package com.app.minitoring.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"url"},
        unique = true)})
public class OriginUrl {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String url;

    public OriginUrl(String url) {
        this.url = url;
    }

    public OriginUrl() {}
}