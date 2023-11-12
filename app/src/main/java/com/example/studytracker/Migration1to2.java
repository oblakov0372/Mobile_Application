package com.example.studytracker;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration1to2 extends Migration {
    public Migration1to2() {
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // Изменение типа данных столбца 'deadline' с INTEGER на TEXT
        database.execSQL("CREATE TABLE IF NOT EXISTS `tasks_temp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `deadline` TEXT, `subject` TEXT)");
        database.execSQL("INSERT INTO tasks_temp (id, title, description, deadline, subject) SELECT id, title, description, CAST(deadline AS TEXT), subject FROM tasks");
        database.execSQL("DROP TABLE tasks");
        database.execSQL("ALTER TABLE tasks_temp RENAME TO tasks");
    }
}