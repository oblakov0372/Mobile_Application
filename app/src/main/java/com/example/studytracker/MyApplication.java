package com.example.studytracker;

import android.app.Application;
import androidx.room.Room;

public class MyApplication extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mydatabase")
                .allowMainThreadQueries()
                .addMigrations(new Migration1to2())
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}