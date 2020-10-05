package com.ttc.tungtt.sm.models;

import com.ttc.tungtt.sm.databases.entities.SubjectEntity;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:24
 */
public class TranscriptModel {
    private SubjectEntity subject;
    private double result;

    public TranscriptModel(SubjectEntity subject) {
        this.subject = subject;
        this.result = 0;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TranscriptModel{" +
                "subject=" + subject +
                ", result=" + result +
                '}';
    }
}
