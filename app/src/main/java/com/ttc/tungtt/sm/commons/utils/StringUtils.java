package com.ttc.tungtt.sm.commons.utils;

import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.text.WordUtils;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Tue, 06 Oct 2020 - 16:09
 */
public class StringUtils {

    public static String formatCapitalize(Object object) {
        return WordUtils.capitalize(formatString(object).toLowerCase());
    }

    public static String formatCapitalizeWithoutSpace(Object object) {
        return WordUtils.capitalize(formatStringWithoutSpace(object).toLowerCase());
    }

    private static String formatString(Object object) {
        return getString(object).replaceAll("\\s+", " ").trim();
    }

    private static String formatStringWithoutSpace(Object object) {
        return getString(object).replaceAll("\\s+", "");
    }

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

    public static boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return ((String) object).trim().length() == 0;
        } else {
            return false;
        }
    }
}
