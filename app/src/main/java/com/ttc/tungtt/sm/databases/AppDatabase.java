package com.ttc.tungtt.sm.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.databases.daos.StudentDAO;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:06
 */
@Database(version = 1,
        entities = StudentEntity.class)
@TypeConverters({GsonUtils.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract StudentDAO studentDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
