package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ttc.tungtt.sm.models.TranscriptModel;

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
    private int genderId;
    private int classId;
    private ArrayList<TranscriptModel> transcriptList;

    public StudentEntity(String firstName,
                         String lastName,
                         int genderId,
                         int classId,
                         ArrayList<TranscriptModel> transcriptList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderId = genderId;
        this.classId = classId;
        this.transcriptList = transcriptList;
    }

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

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
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
                ", gender='" + genderId + '\'' +
                ", classId=" + classId +
                ", transcriptList=" + transcriptList +
                '}';
    }
}
