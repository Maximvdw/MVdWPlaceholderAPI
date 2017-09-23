package be.maximvdw.placeholderapi.internal.utils;

import java.util.List;

public class ListUtils {
    public static String[] listToArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static Boolean[] booleanListToArray(List<Boolean> list) {
        return list.toArray(new Boolean[list.size()]);
    }

    public static Integer[] integerListToArray(List<Integer> list) {
        return list.toArray(new Integer[list.size()]);
    }
}
