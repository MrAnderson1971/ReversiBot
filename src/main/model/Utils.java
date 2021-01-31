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

    /*
    EFFECTS: counts the number of times i shows up in array.
     */
    public static int countObjects(int[][] array, int i) {
        int count = 0;
        for (int[] x : array) {
            for (int y : x) {
                if (y == i) {
                    count++;
                }
            }
        }
        return count;
    }
}
