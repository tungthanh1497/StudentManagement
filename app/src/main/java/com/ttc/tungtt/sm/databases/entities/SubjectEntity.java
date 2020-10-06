package com.ttc.tungtt.sm.databases.entities;

import androidx.room.Entity;

import com.ttc.tungtt.sm.models.SimpleModel;

import java.util.Objects;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:25
 */
@Entity(tableName = "SubjectTable")
public class SubjectEntity extends SimpleModel {
    public SubjectEntity(String name) {
        super(name);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
