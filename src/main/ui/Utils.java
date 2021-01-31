package ui;

public class Utils {
    public static String repeatString(String str, int times) {
        if (times == 0) {
            return str;
        }
        return str + repeatString(str, times - 1);
    }
}
