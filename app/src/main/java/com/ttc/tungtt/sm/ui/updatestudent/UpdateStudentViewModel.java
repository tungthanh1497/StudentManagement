package com.ttc.tungtt.sm.ui.updatestudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.databases.AppDatabase;
import com.ttc.tungtt.sm.databases.entities.ClassEntity;
import com.ttc.tungtt.sm.databases.entities.GenderEntity;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.databases.entities.SubjectEntity;

import java.util.List;

public class UpdateStudentViewModel extends AndroidViewModel {

    private AppDatabase mAppDatabase;

    public UpdateStudentViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public void addStudent(StudentEntity student) {
        mAppDatabase.studentDAO().add(student);
    }

    public void updateStudent(StudentEntity student) {
        mAppDatabase.studentDAO().update(student);
    }

    /**
     * if database do not contain any class model, init some dummy classes for test
     * then get all classes
     *
     * @return list classes
     */
    public LiveData<List<ClassEntity>> getAllClass() {
        if (Constants.SIZE_EMPTY == mAppDatabase.classDAO().getCount()) {
            initClassList();
        }
        return mAppDatabase.classDAO().getAll();
    }

    /**
     * insert some dummy classes to database for testing
     */
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

    /**
     * if database do not contain any gender model, init some dummy genders for test
     * then get all genders
     *
     * @return list genders
     */
    public LiveData<List<GenderEntity>> getAllGender() {
        if (Constants.SIZE_EMPTY == mAppDatabase.genderDAO().getCount()) {
            initGenderList();
        }
        return mAppDatabase.genderDAO().getAll();
    }

    /**
     * insert some dummy genders to database for testing
     */
    public void initGenderList() {
        addGender(new GenderEntity("Unknown"));
        addGender(new GenderEntity("Male"));
        addGender(new GenderEntity("Female"));
    }

    private void addGender(GenderEntity genderModel) {
        mAppDatabase.genderDAO().add(genderModel);
    }

    /**
     * if database do not contain any subject model, init some dummy subjects for test
     * then get all subjects
     *
     * @return list subjects
     */
    public LiveData<List<SubjectEntity>> getAllSubject() {
        if (Constants.SIZE_EMPTY == mAppDatabase.subjectDAO().getCount()) {
            initSubjectList();
        }
        return mAppDatabase.subjectDAO().getAll();
    }

    /**
     * insert some dummy subjects to database for testing
     */
    public void initSubjectList() {
        addSubject(new SubjectEntity("Mathematics"));
        addSubject(new SubjectEntity("Physics"));
        addSubject(new SubjectEntity("Chemistry"));
    }

    private void addSubject(SubjectEntity subjectModel) {
        mAppDatabase.subjectDAO().add(subjectModel);
    }

    public LiveData<StudentEntity> getStudentById(String studentId) {
        return mAppDatabase.studentDAO().getById(studentId);
    }

    /**
     * get list students which start by :tempId
     *
     * @param tempId: searching id
     * @return list valid students
     */
    public LiveData<List<StudentEntity>> getListStudentLikeId(String tempId) {
        return mAppDatabase.studentDAO().getLikeId(tempId);
    }

    /**
     * generate an id by student name
     * format: <first name>+<first character of each word in last name>
     * example: firstName: "Tung" , lastName: "Trinh Thanh"
     * -> id: "tungtt"
     *
     * @param firstName: student's first name
     * @param lastName:  student's last name
     * @return generated id
     */
    public String formatNameToTempStudentId(String firstName, String lastName) {
        String[] lastNameArr = lastName.split("\\s+");
        StringBuilder tempIdBuilder = new StringBuilder(firstName);
        for (String item : lastNameArr) {
            tempIdBuilder.append(item.charAt(0));
        }
        return tempIdBuilder.toString().toLowerCase();
    }

    /**
     * find max idNumber part in list existed id of student
     * if existed: increase 1 then append to tempString part
     * else: tempString part is available
     *
     * @param tempId:      id which be generate from name
     * @param studentList: list existed student which can be has same generated id
     * @return minimize available id
     */
    public String getMinimizeAvailableId(String tempId, List<StudentEntity> studentList) {
        int maxId = -1;
        for (StudentEntity item : studentList) {
            // remove the same generated by name part
            String remainId = item.getId().replace(tempId, "");
            if (remainId.length() == 0) {
                // case student id is exactly like generated part
                maxId = Math.max(maxId, 0);
            } else {
                try {
                    // case student id is more than 0
                    maxId = Math.max(maxId, Integer.parseInt(remainId));
                } catch (Exception e) {
                    // case student id might be not the number
                    // example: generated part: "tungtt" - studentId: "tungtth"
                }
            }
        }
        return maxId >= 0 ? tempId + (maxId + 1) : tempId;
    }
}