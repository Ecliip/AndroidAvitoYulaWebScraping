package com.app.minitoring.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ad.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase minstance;
    private static final String DB_NAME = "scrapp_db";
    public abstract AdDAO getAdDAO();
    public static synchronized AppDatabase getInstance(Context ctx) {
        if(minstance == null) {
            minstance = Room.databaseBuilder(ctx.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return minstance;
    }
}
