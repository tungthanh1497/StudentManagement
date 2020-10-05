package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;

import com.ttc.tungtt.sm.models.SimpleModel;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:25
 */
@Entity(tableName = "SubjectTable")
public class SubjectEntity extends SimpleModel {
    public SubjectEntity(String name) {
        super(name);
    }
}
