package com.yu.util.algorithm.sort;

import com.yu.util.algorithm.util.Utils;

public class InsetSort implements SortAlgotithm {
    public static void main(String[] args) {
        InsetSort insetSort = new InsetSort();
        int[] source = Utils.testArray(400);
        System.out.println("测试数组:" + format(source));
        long start = System.nanoTime();
        insetSort.sort(source);
        long end = System.nanoTime();
        System.out.println(format(source));
        System.out.println("耗时:" + (end - start));
//        System.out.println("交换次数:" + swapCount);
//        System.out.println("循环次数:" + compareCount);
    }

    public static String format(int[] source) {
        return Utils.format(source, false);
    }

    public void sort(int[] list) {
        // 打印第一个元素
        // 第1个数肯定是有序的，从第2个数开始遍历，依次插入有序序列
        for (int i = 1; i < list.length; i++) {//i是当前需要插入的元素
            int j = i - 1;//j是指向有序数组的指针,默认处于最后,j+1是当前元素可以插入的位置
            int temp = list[i]; // 取出当前需要插入的元素，和前面的有序数组比较后，插入合适位置
            // 因为前i-1个数都是从小到大的有序序列，所以只要当前比较的数(list[j])比temp大，就把这个数后移一位
            for (; j >= 0 && temp < list[j]; j--) {
                list[j + 1] = list[j];//将[j]元素后移,指针向前移动直至0或找到合适位置
            }
            list[j + 1] = temp;
        }
    }
}
