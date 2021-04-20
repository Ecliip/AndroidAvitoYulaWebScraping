package com.app.minitoring;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ad.class, ScrapedAd.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AdDAO adDAO();
    public abstract ScrapedAdDao scrapedAdDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE if not exists ScrapedAd ( " +
                    "scrapedAdId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "avito_ad_id TEXT NOT NULL," +
                    "name TEXT not NULL," +
                    "url TEXT not NULL," +
                    "user_query TEXT not NULL," +
                            "UNIQUE(name, url, avito_ad_id));"
            );
            database.execSQL(
                    "CREATE UNIQUE INDEX index_ScrapedAd_name_url_avito_ad_id ON ScrapedAd (name, url, avito_ad_id);"
            );
        }
    };

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "ad_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
