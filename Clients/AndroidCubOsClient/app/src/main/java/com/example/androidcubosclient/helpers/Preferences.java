package com.example.androidcubosclient.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static String APP_PREFERENCES = "mainsettings";
    private static SharedPreferences mSettings;

    public static final String SETTINGS_HOST_ADDRESS = "SETTINGS_HOST_ADDRESS";
    public static final String SETTINGS_HOST_PORT = "SETTINGS_HOST_PORT";
    public static final String IMAGE_SCALE = "IMAGE_SCALE";
    public static final String SCREEN_WIDTH = "SCREEN_WIDTH";
    public static final String SCREEN_HEIGHT = "SCREEN_HEIGHT";

    static public void init(Context context){
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    static public String getString(String variable, String defaulValue){
        return mSettings.getString(variable, defaulValue);
    }

    static public void putString(String variable, String value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(variable, value);
        editor.apply();
    }

    static public void putInt(String variable, int value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(variable, value);
        editor.apply();
    }

    static public void putInt(String variable, String value, int defInt){
        int value_int = defInt;

        try{
            value_int = Integer.parseInt(value);
        }catch(Exception e){}

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(variable, value_int);
        editor.apply();
    }

    static public int getInt(String variable, int defaulValue){
        return mSettings.getInt(variable, defaulValue);
    }

    static public void putBoolean(String variable, boolean value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(variable, value);
        editor.apply();
    }

    static public boolean getBoolean(String variable, boolean defaulValue){
        return mSettings.getBoolean(variable, defaulValue);
    }

}