package com.ttc.tungtt.sm.models;

import androidx.room.PrimaryKey;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 11:41
 */
public class SimpleModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public SimpleModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
