package com.yu.util.algorithm.sort;

import com.yu.util.algorithm.util.Utils;

import java.util.Random;

public class QuickSort implements SortAlgotithm {
    public static int swapCount = 0;
    public static int compareCount = 0;
    public static boolean isDebug = false;
    public static Random random = new Random();

    public static void main(String[] args) {
        int[] source = Utils.testArray(400);
        System.out.println("测试数组:" + format(source));
        long start = System.nanoTime();
        quickSort(0, source.length - 1, source);
        long end = System.nanoTime();
        System.out.println(format(source));
//        System.out.println("交换次数:" + swapCount);
//        System.out.println("循环次数:" + compareCount);
        System.out.println("耗时:" + (end - start));
    }

    public static String format(int[] source) {
        return Utils.format(source, isDebug);
    }

    public static void quickSort(int start, int end, int[] source) {
        if (start < end) {
            int pivot = compareAndSwap(start, end, source);
            quickSort(start, pivot - 1, source);
            quickSort(pivot + 1, end, source);
        }
    }

    @Override
    public void sort(int[] ints) {
        quickSort(0, ints.length - 1, ints);
    }

    /**
     * @return the index of pivot after sorted
     */
    public static int compareAndSwap(int start, int end, int[] source) {
        int pivotIndex = choosePivot(start, end);
        int pivotValue = source[pivotIndex];
        if (isDebug) {
            System.out.println("选取pivot(" + pivotIndex + ":" + pivotValue + ")");
        }
        Utils.swap(pivotIndex, end, source);
        for (int i = start, j = end, space = end; ; ) {
//                compareCount++;
            if (i == j) {
                source[i] = pivotValue;
                return i;
            }
            if (space == j) {//空位在j指针处,此时i在向后移动
                int value = source[i];
                if (value <= pivotValue) {
                    i++;
                    continue;
                }
                if (value > pivotValue) {//大于pivot的值需要移动到数组后端
                    source[space] = value;//空位填入该值
                    space = i;//[i]位置变成空位
                }
            }
            if (space == i) {//空位在i指针处,此时j在向前移动
                int value = source[j];
                if (value > pivotValue) {
                    j--;
                    continue;
                }
                if (value <= pivotValue) {//小于等于pivot的值需要移动到数组前端
                    source[space] = value;//空位填入该值
                    space = j;//[j]位置变成空位
                }
            }
        }
    }

    private static int choosePivot(int start, int end) {
//            return start + (int) ((end - start) * random.nextDouble());
        return (start + end) >>> 1;
    }
}

