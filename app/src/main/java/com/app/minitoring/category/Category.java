package com.app.minitoring.category;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "url"},
        unique = true)})
public class Category {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String name;
    public String url;
    public Category(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public Category() {}
}