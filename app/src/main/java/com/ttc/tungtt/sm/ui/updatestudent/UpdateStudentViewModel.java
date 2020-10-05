package com.ttc.tungtt.sm.ui.updatestudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.databases.AppDatabase;
import com.ttc.tungtt.sm.databases.entities.ClassEntity;
import com.ttc.tungtt.sm.databases.entities.GenderEntity;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.databases.entities.SubjectEntity;

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

    public LiveData<List<ClassEntity>> getAllClass() {
        if (Constants.SIZE_EMPTY == mAppDatabase.classDAO().getCount()) {
            initClassList();
        }
        return mAppDatabase.classDAO().getAll();
    }

    public void initClassList() {
        addClass(new ClassEntity("11a1"));
        addClass(new ClassEntity("11a2"));
        addClass(new ClassEntity("11a3"));
        addClass(new ClassEntity("11a4"));
        addClass(new ClassEntity("11a5"));
    }

    private void addClass(ClassEntity classModel) {
        mAppDatabase.classDAO().add(classModel);
    }

    public LiveData<List<GenderEntity>> getAllGender() {
        if (Constants.SIZE_EMPTY == mAppDatabase.genderDAO().getCount()) {
            initGenderList();
        }
        return mAppDatabase.genderDAO().getAll();
    }

    public void initGenderList() {
        addGender(new GenderEntity("Unknown"));
        addGender(new GenderEntity("Male"));
        addGender(new GenderEntity("Female"));
    }

    private void addGender(GenderEntity genderModel) {
        mAppDatabase.genderDAO().add(genderModel);
    }

    public LiveData<List<SubjectEntity>> getAllSubject() {
        if (Constants.SIZE_EMPTY == mAppDatabase.subjectDAO().getCount()) {
            initSubjectList();
        }
        return mAppDatabase.subjectDAO().getAll();
    }

    public void initSubjectList() {
        addSubject(new SubjectEntity("Mathematics"));
        addSubject(new SubjectEntity("Physics"));
        addSubject(new SubjectEntity("Chemistry"));
    }

    private void addSubject(SubjectEntity subjectModel) {
        mAppDatabase.subjectDAO().add(subjectModel);
    }
}