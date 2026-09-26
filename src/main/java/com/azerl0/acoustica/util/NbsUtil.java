package com.azerl0.acoustica.util;

public class NbsUtil {

    public static float getPitch(int keyArg) {
        int key = keyArg;
        if (key < 33) {
            key -= 9;
        } else if (key > 57) {
            key -= 57;
        } else {
            key -= 33;
        }
        return (float) (0.5 * (Math.pow(2, ((double) key / 12))));
    }
}
