package com.sciget.studentmeals.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helper {
    public static String distanceInMeters(float distance) {
        String distanceStr = "";
        if (distance > 0) {
            if (distance >= 1000) {
                distanceStr = " " + Float.toString(distance / 1000).replace(".", ",") + " km";
            } else {
                if (distance % 1 == 0) {
                    distanceStr = " " + (int) distance + " m";
                } else {
                    distanceStr = " " + Float.toString(distance).replace(".", ",") + " m";
                }
            }
        }
        return distanceStr;
    }
    
    public static String dataInNewLine(String title, String value) {
        if (value != null && value.length() > 0) {
            return "\n" + title + ": " + value;
        } else {
            return "";
        }
    }
    
    public static String data(String title, String value) {
        if (value != null && value.length() > 0) {
            return title + ": " + value;
        } else {
            return "";
        }
    }
    
    public static String toSloTime(Timestamp time) {
        if (time == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy H:mm");
        return sdf.format(cal.getTime());
    }
}
