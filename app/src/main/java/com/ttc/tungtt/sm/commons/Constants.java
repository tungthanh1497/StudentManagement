package com.ttc.tungtt.sm.commons;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:11
 */
public class Constants {
    public static final String DATABASE_NAME = "student_management_database";

    public static final int SIZE_EMPTY = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SCREEN_TAG {
        String MAIN = "main";
        String UPDATE_STUDENT = "update_student";
    }
}
