package model;

import java.util.*;

public class Utils {

    /*
    REQUIRES: times be positive
    EFFECTS: repeats str n times
     */
    public static String repeatString(String str, int times) {
        if (times == 1) {
            return str;
        }
        return str + repeatString(str, times - 1);
    }

    /*
    EFFECTS: true if list contains array
     */
    public static boolean listContainsArray(ArrayList<int[]> list, int[] array) {
        for (int[] i : list) {
            if (Arrays.equals(i, array)) {
                return true;
            }
        }
        return false;
    }
}
