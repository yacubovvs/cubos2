package com.example.androidcubosclient.helpers;

import java.util.HashMap;

public class Profiler {
    static HashMap<String, Long> startTimers = new HashMap<>();
    static HashMap<String, Long> pointTimers = new HashMap<>();

    static public void start(String tag){
        startTimers.put(tag, System.currentTimeMillis());
        pointTimers.put(tag, System.currentTimeMillis());
    }

    static public void point(String tag){
        if(startTimers.get(tag)==null) start(tag);
        long startTimer = startTimers.get(tag);

        long pointTimer = pointTimers.get(tag);


        System.out.println(tag + " point " + (System.currentTimeMillis() - startTimer) + "           "  + (System.currentTimeMillis() - pointTimer));

        pointTimers.put(tag, System.currentTimeMillis());
    }
}
