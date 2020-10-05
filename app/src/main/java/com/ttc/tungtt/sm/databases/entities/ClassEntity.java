package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;

import com.ttc.tungtt.sm.models.SimpleModel;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 12:03
 */
@Entity(tableName = "ClassTable")
public class ClassEntity extends SimpleModel {
    public ClassEntity(String name) {
        super(name);
    }
}
