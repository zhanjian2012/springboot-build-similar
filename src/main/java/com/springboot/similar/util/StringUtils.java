package com.springboot.similar.util;

public class StringUtils {

    public static boolean isEmpty(String sequence) {
        return sequence == null || sequence.length() == 0;
    }

    public static boolean isNotEmpty(String sequence) {
        return !isEmpty(sequence);
    }
}
