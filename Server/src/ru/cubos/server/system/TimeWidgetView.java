package ru.cubos.server.system;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeWidgetView {
    public String getCurrentTime(){
        //return "11:59";
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");

        return  formatForDateNow.format(dateNow);
    }
}
