package com.yunqing.demoleetcode.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * 时间复杂度 O(n²)
 *
 * 冒泡排序没什么好解释的，每次比较相邻两个数，小前大后交换，共比较 n - 1 趟
 * @author kangqing
 * @since 2021/1/12 11:39
 */
public class BubbleSort {
    public static void main(String[] args) {
        // 定义需要进行排序的数组
        int[] arr = {5, 7, 1, 13, 6, 2};
        // 下面进行冒泡排序

        // 第 i 趟排序
        for (int i = 0; i < arr.length - 1; i++) {
            // 此处多减了个 i 是因为后面的大数已经确定 i 个了，就不用比较了
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 相邻两个数比对交换，小的在前，大的在后
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                System.out.println(Arrays.toString(arr));
            }
            // 第 i 趟排序后的结果
            System.out.println("第" + i + "趟排序后的结果：" + Arrays.toString(arr));
        }
    }
}
