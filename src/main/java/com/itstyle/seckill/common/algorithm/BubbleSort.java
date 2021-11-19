package com.itstyle.seckill.common.algorithm;

public class BubbleSort {

    public static void main(String[] args) {
        int[] list = {27, 76, 47, 23, 7, 32, 19, 86};
        System.out.println("************冒泡排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        bubbleSort(list);
        display(list);
    }


    public static void display(int[] list) {
        if (list != null && list.length > 0) {
            for (int num :
                    list) {
                System.out.print(num + " ");
            }
            System.out.println("");
        }
    }


    public static void bubbleSort(int[] list) {
    	int len = list.length ;
        // 做多少轮排序（最多length-1轮）
        for (int i = 0; i < len - 1; i++) {
            // 每一轮比较多少个
            for (int j = 0; j < len - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    // 交换次序
                	int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }
}