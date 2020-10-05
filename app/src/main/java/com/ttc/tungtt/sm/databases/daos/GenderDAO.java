package com.ttc.tungtt.sm.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ttc.tungtt.sm.databases.entities.GenderEntity;

import java.util.List;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 12:05
 */
@Dao
public interface GenderDAO {
    @Query("select count(*) from GenderTable")
    int getCount();

    @Insert
    void add(GenderEntity classModel);

    @Query("select * from GenderTable")
    LiveData<List<GenderEntity>> getAll();
}
