package com.itstyle.seckill.common.algorithm;

public class ShellSort {

    public static void main(String[] args) {
    	int[] list = {27, 76, 47, 23, 7, 32, 19, 86};
        System.out.println("************希尔排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("");

        System.out.println("排序后：");
        shellSort(list);
        display(list);
    }

    public static void shellSort(int[] list) {
    	int len = list.length ;
        // 取增量
        int gap = len / 2;

        while (gap >= 1) {
            // 无序序列
            for (int i = gap; i < len; i++) {
                int temp = list[i];
                int j;

                // 有序序列
                for (j = i - gap; j >= 0 && list[j] > temp; j = j - gap) {
                    list[j + gap] = list[j];
                }
                list[j + gap] = temp;
            }

            // 缩小增量
            gap = gap / 2;
        }
    }

    public static void display(int[] list) {
        if (list != null && list.length > 0) {
            for (int num :list) {
                System.out.print(num + " ");
            }
            System.out.println("");
        }
    }
}
