package com.yu.util.algorithm.util;

import java.util.Arrays;
import java.util.Random;

public class Utils {
    public static void swap(int i, int j, int[] source) {
        if (i == j) return;
        int tmp = source[i];
        source[i] = source[j];
        source[j] = tmp;
    }

    public static int[] testArray(int length) {
        int[] array = new int[length];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (random.nextDouble() * 100);
        }
        return array;
    }

    public static Integer[] testObjectArray(int length) {
        Integer[] array = new Integer[length];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (random.nextDouble() * 100);
        }
        return array;
    }

    public static String format(int[] source, boolean isDebug) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(Arrays.toString(source) + "\n");
        if (isDebug) {
            int[] index = new int[source.length];
            for (int i = 0; i < index.length; i++) {
                index[i] = i;
            }
            sb.append(Arrays.toString(index));
        }
        return sb.toString();
    }

    public static String format(Integer[] source, boolean isDebug) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(Arrays.toString(source) + "\n");
        if (isDebug) {
            int[] index = new int[source.length];
            for (int i = 0; i < index.length; i++) {
                index[i] = i;
            }
            sb.append(Arrays.toString(index));
        }
        return sb.toString();
    }
}
