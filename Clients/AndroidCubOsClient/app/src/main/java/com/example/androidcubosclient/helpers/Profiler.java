package com.example.androidcubosclient.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class Profiler {
    static HashMap<String, Long> startTimers = new HashMap<>();
    static HashMap<String, Long> pointTimers = new HashMap<>();
    //public static Context context;

    static public void start(String tag){
        startTimers.put(tag, System.currentTimeMillis());
        pointTimers.put(tag, System.currentTimeMillis());
    }

    static public void point(String tag){
        if(startTimers.get(tag)==null) start(tag);
        long startTimer = startTimers.get(tag);

        long pointTimer = pointTimers.get(tag);


        System.out.println(tag + " point " + (System.currentTimeMillis() - startTimer) + "           "  + (System.currentTimeMillis() - pointTimer));
        Log.d("Profiler: " + tag, tag + " point " + (System.currentTimeMillis() - startTimer) + "           "  + (System.currentTimeMillis() - pointTimer));
        //Toast toast = Toast.makeText(context, "Пора покормить кота!", Toast.LENGTH_SHORT);
        //toast.show();

        pointTimers.put(tag, System.currentTimeMillis());
    }
}
