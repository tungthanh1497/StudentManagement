package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;

import com.ttc.tungtt.sm.models.SimpleModel;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 13:52
 */
@Entity(tableName = "GenderTable")
public class GenderEntity extends SimpleModel {
    public GenderEntity(String name) {
        super(name);
    }
}
