package com.ttc.tungtt.sm.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:34
 */
@Dao
public interface StudentDAO {
    @Query("select * from StudentTable")
    LiveData<List<StudentEntity>> getAll();

    @Insert
    long add(StudentEntity student);

    @Query("select * from StudentTable where id = :studentId")
    LiveData<StudentEntity> getById(int studentId);

    @Update
    void update(StudentEntity student);
}
