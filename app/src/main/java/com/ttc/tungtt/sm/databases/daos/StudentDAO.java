package com.ttc.tungtt.sm.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    void add(StudentEntity student);

    @Query("select * from StudentTable where id = :studentId")
    LiveData<StudentEntity> getById(String studentId);

    @Update
    void update(StudentEntity student);

    @Query("select * from StudentTable where id like :tempId || '%'")
    LiveData<List<StudentEntity>> getLikeId(String tempId);

    @Delete
    void delete(StudentEntity deletingStudent);
}
