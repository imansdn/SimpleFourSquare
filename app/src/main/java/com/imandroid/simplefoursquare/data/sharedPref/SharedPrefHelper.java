package com.imandroid.simplefoursquare.data.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    private static  SharedPrefHelper instance;

    private SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefHelper getInstance(Context context) {
        if (instance == null){
            instance = new SharedPrefHelper(context);
        }

        return instance;
    }

    public void setOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unRegisterSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


    public  String read(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }



    public  boolean read(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }
    public  int read(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }


    public float read(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public long read(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public  void write(String key, String value) {
        editor.putString(key, value).apply();
    }

    public  void write(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public  void write(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public  void write(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public  void write(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public  void remove(String key){
        editor.remove(key).apply();
    }

}
