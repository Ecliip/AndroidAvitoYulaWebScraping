package com.app.monitoring;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ad.class, ScrapedAd.class, AdSubscription.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AdDAO adDAO();
    public abstract ScrapedAdDao scrapedAdDao();
    public abstract AdSubscriptionDao adSubscriptionDao();

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

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ScrapedAd " +
                    "ADD COLUMN hidden INTEGER DEFAULT 0");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE if not exists AdSubscription ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT not NULL," +
                    "url TEXT not NULL," +
                    "UNIQUE(name, url));");

            database.execSQL(
                    "CREATE UNIQUE INDEX index_AdSubscription_name_url ON AdSubscription (name, url);"
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
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}