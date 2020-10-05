package com.ttc.tungtt.sm.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ttc.tungtt.sm.databases.entities.SubjectEntity;

import java.util.List;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 12:05
 */
@Dao
public interface SubjectDAO {
    @Query("select count(*) from SubjectTable")
    int getCount();

    @Insert
    void add(SubjectEntity subjectModel);

    @Query("select * from SubjectTable")
    LiveData<List<SubjectEntity>> getAll();
}
