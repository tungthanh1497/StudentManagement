package com.ttc.tungtt.sm.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ttc.tungtt.sm.databases.entities.ClassEntity;

import java.util.List;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 12:05
 */
@Dao
public interface ClassDAO {
    @Query("select count(*) from ClassTable")
    int getCount();

    @Insert
    void add(ClassEntity classModel);

    @Query("select * from ClassTable")
    LiveData<List<ClassEntity>> getAll();
}
