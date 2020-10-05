package com.ttc.tungtt.sm.databases.models;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:24
 */
public class TranscriptModel {
    private SubjectModel subject;
    private double result;

    public TranscriptModel(SubjectModel subject, double result) {
        this.subject = subject;
        this.result = result;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "TranscriptModel{" +
                "subject=" + subject +
                ", result=" + result +
                '}';
    }
}
