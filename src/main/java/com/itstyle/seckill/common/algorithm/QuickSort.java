package com.itstyle.seckill.common.algorithm;

public class QuickSort {

    public static void main(String[] args) {
        int[] list = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        System.out.println("************快速排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        quickSort(list, 0, list.length - 1);
        display(list);
    }

    public static void quickSort(int[] list, int left, int right) {
        if (left < right) {
            // 分割数组，找到分割点
            int point = partition(list, left, right);

            // 递归调用，对左子数组进行快速排序
            quickSort(list, left, point - 1);
            // 递归调用，对右子数组进行快速排序
            quickSort(list, point + 1, right);
        }
    }

    public static int partition(int[] list, int left, int right) {
        // 用数组的第一个元素作为基准数
        int first = list[left];
        while (left < right) {
            while (left < right && list[right] >= first) {
                right--;
            }
            // 交换
            swap(list, left, right);

            while (left < right && list[left] <= first) {
                left++;
            }
            // 交换
            swap(list, left, right);
        }
        // 返回分割点所在的位置
        return left;
    }

    public static void swap(int[] list, int left, int right) {
        int temp;
        if (list != null && list.length > 0) {
            temp = list[left];
            list[left] = list[right];
            list[right] = temp;
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