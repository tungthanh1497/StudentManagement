package com.ttc.tungtt.sm.ui.updatestudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ttc.tungtt.sm.databases.AppDatabase;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

public class UpdateStudentViewModel extends AndroidViewModel {

    private AppDatabase mAppDatabase;

    public UpdateStudentViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<List<StudentEntity>> getAllStudent() {
        return mAppDatabase.studentDAO().getAll();
    }
    // TODO: Implement the ViewModel
}