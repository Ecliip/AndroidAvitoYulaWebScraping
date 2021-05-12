package com.app.monitoring;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url"},
        unique = true)})
public class AdSubscription {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    @NonNull
    private final String name;
    @NonNull
    private final String url;

    public AdSubscription(@NonNull String name, @NonNull String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
