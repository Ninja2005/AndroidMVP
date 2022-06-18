package com.hqumath.androidmvp.utils;

import java.util.Arrays;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2022/3/8 13:28
 * 文件描述:
 * 注意事项: 测试排序算法，从小到大排序
 * <p>
 * <p>
 * ****************************************************************
 */
public class SortUtil {
    public static void main(String[] args) {

        int[] data = {1, 81, 3, 16, 8, 0, 32, 82, 6, 83, 10};
        int[] data1 = SelectionSort(data);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(" " + data1[i]);
        }
        System.out.println(sb.toString());
    }

    /**
     * 冒泡排序，比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 时间复杂度 O(n^2)
     */
    public static int[] BubbleSort(int[] sourceArray) {
        //浅克隆,不改变原数组内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        for (int i = 0; i < arr.length - 1; i++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag)
                break;
        }
        return arr;
    }

    /**
     * 插入排序，第一个元素看作有序序列，未排序序列一次插入有序序列。
     * 时间复杂度 O(n^2)
     */
    public static int[] InsertSort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];//要插入的数据
            int j = i - 1;
            while (j >= 0 && temp < arr[j]) {//逆序比较，不能插入为止
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
        return arr;
    }

    /**
     * 选择排序，直观方法，找最小元素。
     * 时间复杂度 O(n^2)
     */
    public static int[] SelectionSort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
        return arr;
    }
}
