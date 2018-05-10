package com.xpoliceservices.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public PreferenceUtils(Context context){
        pref = context.getSharedPreferences("XPoliceServices", 0);
        editor = pref.edit();
    }


    public static void putStaringValue(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static void putLongValue(String key, long val){
        editor.putLong(key,val);
        editor.commit();
    }

    public static void putBooleanValue(String key,boolean boolVal){
        editor.putBoolean(key,boolVal);
        editor.commit();
    }

    public static String getStringValue(String key){
        return pref.getString(key,"");
    }

    public static long getLongValue(String key){
        return pref.getLong(key,0);
    }

    public static boolean getBoolValue(String key){
        return pref.getBoolean(key,false);
    }
}
