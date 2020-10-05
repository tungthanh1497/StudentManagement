package com.ttc.tungtt.sm.ui.updatestudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ttc.tungtt.sm.databases.AppDatabase;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

public class UpdateStudentViewModel extends AndroidViewModel {

    private AppDatabase mAppDatabase;
    private MutableLiveData<List<StudentEntity>> studentLiveData;

    public UpdateStudentViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public void getAllStudent() {
        getStudentLiveData().setValue(mAppDatabase.studentDAO().getAll());
    }

    public long addStudent(StudentEntity student) {
        return mAppDatabase.studentDAO().add(student);
    }

    public MutableLiveData<List<StudentEntity>> getStudentLiveData() {
        if (studentLiveData == null) {
            studentLiveData = new MutableLiveData<>();
        }
        return studentLiveData;
    }
    // TODO: Implement the ViewModel
}