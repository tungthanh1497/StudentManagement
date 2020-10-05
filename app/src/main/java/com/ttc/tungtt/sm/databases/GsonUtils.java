package com.ttc.tungtt.sm.databases;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ttc.tungtt.sm.models.TranscriptModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 10:39
 */
public class GsonUtils {
    @TypeConverter
    public static ArrayList<TranscriptModel> getListTranscriptFromString(String transcriptString) {
        Type transcriptType = new TypeToken<ArrayList<TranscriptModel>>() {
        }.getType();
        return new Gson().fromJson(transcriptString, transcriptType);
    }

    @TypeConverter
    public static String listTranscriptToString(ArrayList<TranscriptModel> transcriptList) {
        Gson gson = new Gson();
        return gson.toJson(transcriptList);
    }
}
