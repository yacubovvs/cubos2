package ru.cubos.commonHelpers.profiler;

import java.util.HashMap;

public class Profiler {
    static HashMap<String, Long> startTimers = new HashMap<>();
    static HashMap<String, Long> pointTimers = new HashMap<>();
    static HashMap<String, Long> sumTimers = new HashMap<>();

    static public void start(String tag){
        startTimers.put(tag, System.currentTimeMillis());
        pointTimers.put(tag, System.currentTimeMillis());
    }

    static public void stop(String tag){
        if(startTimers.get(tag)==null) start(tag);
        long startTimer = startTimers.get(tag);
        long currentTimer = (System.currentTimeMillis() - startTimer);
        if(sumTimers.get(tag)!=null) currentTimer+=sumTimers.get(tag);

        sumTimers.put(tag, currentTimer);
    }

    static public void point(String tag){
        if(startTimers.get(tag)==null) start(tag);
        long startTimer = startTimers.get(tag);

        long pointTimer = pointTimers.get(tag);


        System.out.println(tag + " point " + (System.currentTimeMillis() - startTimer) + "           "  + (System.currentTimeMillis() - pointTimer));

        pointTimers.put(tag, System.currentTimeMillis());
    }

    static public void showSumTimers(){
        if(sumTimers.size()!=0){
            System.out.println("\nProfiler timers:");
            for(String tag: sumTimers.keySet()){
                System.out.println(tag + ": " + sumTimers.get(tag) + " ms");
            }
        }
    }

}
