package com.itstyle.seckill.common.algorithm;

public class InsertSort {

    public static void main(String[] args) {
    	int[] list = {27, 76, 47, 23, 7, 32, 19, 86};
        System.out.println("************直接插入排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        insertSort(list);
        display(list);
    }

    public static void insertSort(int[] list) {
    	int len = list.length ;
        // 从无序序列中取出第一个元素 (注意无序序列是从第二个元素开始的)
        for (int i = 1; i < len; i++) {
            int temp = list[i];
            int j;
            // 遍历有序序列
            // 如果有序序列中的元素比临时元素大，则将有序序列中比临时元素大的元素依次后移
            for (j = i - 1; j >= 0 && list[j] > temp; j--) {
                list[j + 1] = list[j];
            }
            // 将临时元素插入到腾出的位置中
            list[j + 1] = temp;
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