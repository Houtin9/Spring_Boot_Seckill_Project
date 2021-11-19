package com.itstyle.seckill.common.algorithm;

public class SelectionSort {

    public static void main(String[] args) {
    	int[] list = {27, 76, 47, 23, 7, 32, 19, 86};
        System.out.println("************直接选择排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        selectionSort(list);
        display(list);
    }

    public static void selectionSort(int[] list) {
    	int len = list.length ;
        // 要遍历的次数（length-1次）
        for (int i = 0; i < len - 1; i++) {
            // 将当前下标定义为最小值下标
            int min = i;

            // 遍历min后面的数据
            for (int j = i + 1; j <= len - 1; j++) {
                // 如果有小于当前最小值的元素，将它的下标赋值给min
                if (list[j] < list[min]) {
                    min = j;
                }
            }
            // 如果min不等于i，说明找到真正的最小值
            if (min != i) {
                swap(list, min, i);
            }
        }
    }

    public static void swap(int[] list, int min, int i) {
        int temp = list[min];
        list[min] = list[i];
        list[i] = temp;
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
