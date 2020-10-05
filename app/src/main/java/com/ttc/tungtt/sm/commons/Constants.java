package com.ttc.tungtt.sm.commons;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:11
 */
public class Constants {
    public static final String DATABASE_NAME = "student_management_database";

    @Retention(RetentionPolicy.SOURCE)
    public @interface GENDER {
        String MALE = "male";
        String FEMALE = "female";
        String UNKNOWN = "unknown";
    }
}
