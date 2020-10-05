package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.databases.models.TranscriptModel;

import java.util.ArrayList;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:16
 */
@Entity(tableName = "StudentTable")
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String firstName;
    private String lastName;
    private String gender;
    private int classId;
    private ArrayList<TranscriptModel> transcriptList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender == null ? Constants.GENDER.UNKNOWN : gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public ArrayList<TranscriptModel> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(ArrayList<TranscriptModel> transcriptList) {
        this.transcriptList = transcriptList;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", classId=" + classId +
                ", transcriptList=" + transcriptList +
                '}';
    }
}
