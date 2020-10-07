package com.ttc.tungtt.sm.commons.utils;

import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.text.WordUtils;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Tue, 06 Oct 2020 - 16:09
 */
public class StringUtils {

    /**
     * get string then format to capitalize case
     *
     * @param object
     * @return
     */
    public static String formatCapitalize(Object object) {
        return WordUtils.capitalize(formatString(object).toLowerCase());
    }

    /**
     * get string then remove all spaces and format to capitalize case
     *
     * @param object
     * @return
     */
    public static String formatCapitalizeWithoutSpace(Object object) {
        return WordUtils.capitalize(formatStringWithoutSpace(object).toLowerCase());
    }

    /**
     * trim string and replace multiple space by 1 space only
     *
     * @param object
     * @return
     */
    private static String formatString(Object object) {
        return getString(object).replaceAll("\\s+", " ").trim();
    }

    /**
     * remove all space in string
     *
     * @param object
     * @return
     */
    private static String formatStringWithoutSpace(Object object) {
        return getString(object).replaceAll("\\s+", "");
    }

    /**
     * get string from object
     *
     * @param object
     * @return
     */
    private static String getString(Object object) {
        if (object == null) {
            return "";
        } else if (object instanceof EditText) {
            return ((EditText) object).getText().toString();
        } else if (object instanceof TextView) {
            return ((TextView) object).getText().toString();
        } else if (object instanceof String) {
            return (String) object;
        } else {
            return "";
        }
    }

    /**
     * check if string is null or empty
     *
     * @param object
     * @return
     */
    public static boolean isNullOrEmpty(Object object) {
        return getString(object).trim().isEmpty();
    }
}
