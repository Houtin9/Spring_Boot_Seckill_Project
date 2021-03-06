package com.itstyle.seckill.common.algorithm;

public class MergeSort {

    public static void main(String[] args) {
        int[] list = {50, 10, 90, 30, 70};
        System.out.println("************归并排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        mergeSort(list, new int[list.length], 0, list.length - 1);
        display(list);
    }

    public static void mergeSort(int[] list, int[] tempList, int head, int rear) {
        if (head < rear) {
            // 取分割位置
            int middle = (head + rear) / 2;
            // 递归划分列表的左序列
            mergeSort(list, tempList, head, middle);
            // 递归划分列表的右序列
            mergeSort(list, tempList, middle + 1, rear);
            // 列表的合并操作
            merge(list, tempList, head, middle + 1, rear);
        }
    }

    public static void merge(int[] list, int[] tempList, int head, int middle, int rear) {
        // 左指针尾
        int headEnd = middle - 1;
        // 右指针头
        int rearStart = middle;
        // 临时列表的下标
        int tempIndex = head;
        // 列表合并后的长度
        int tempLength = rear - head + 1;

        // 先循环两个区间段都没有结束的情况
        while ((headEnd >= head) && (rearStart <= rear)) {
            // 如果发现右序列大，则将此数放入临时列表
            if (list[head] < list[rearStart]) {
                tempList[tempIndex++] = list[head++];
            } else {
                tempList[tempIndex++] = list[rearStart++];
            }
        }

        // 判断左序列是否结束
        while (head <= headEnd) {
            tempList[tempIndex++] = list[head++];
        }

        // 判断右序列是否结束
        while (rearStart <= rear) {
            tempList[tempIndex++] = list[rearStart++];
        }

        // 交换数据
        for (int i = 0; i < tempLength; i++) {
            list[rear] = tempList[rear];
            rear--;
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
