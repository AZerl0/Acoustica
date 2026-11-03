package com.azerl0.acoustica.util;


public class ComponentUtil {

    public static String getTimeText(int tick) {
        int fullSec = tick / 20;

        int min = fullSec / 60;
        int sec = fullSec % 60;
        String secPad = "";
        if (sec < 10) secPad = "0";
        return min + ":" + secPad + sec;
    }
}
