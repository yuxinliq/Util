package com.yu.util.algorithm.sort;

import com.yu.util.algorithm.util.Utils;

import java.util.Arrays;

public class TimSort {
    public static void main(String[] args) {
        Integer[] source = Utils.testObjectArray(400);
        System.out.println("测试数组:" + Utils.format(source, false));
        long start = System.nanoTime();
        Arrays.sort(source);
        long end = System.nanoTime();
        System.out.println(Utils.format(source, false));
        System.out.println("耗时:" + (end - start));
    }

    public static int minrun(int n) {
        int r = 0;      // Becomes 1 if any 1 bits are shifted off
        while (n >= 32) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
}
