package com.ttc.tungtt.sm.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ttc.tungtt.sm.databases.AppDatabase;
import com.ttc.tungtt.sm.databases.entities.ClassEntity;
import com.ttc.tungtt.sm.databases.entities.GenderEntity;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private AppDatabase mAppDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<List<StudentEntity>> getAllStudents() {
        return mAppDatabase.studentDAO().getAll();
    }

    public LiveData<List<GenderEntity>> getAllGenders() {
        return mAppDatabase.genderDAO().getAll();
    }

    public LiveData<List<ClassEntity>> getAllClasses() {
        return mAppDatabase.classDAO().getAll();
    }

    public void deleteStudent(StudentEntity deletingStudent) {
        mAppDatabase.studentDAO().delete(deletingStudent);
    }
    // TODO: Implement the ViewModel
}