package com.itstyle.seckill.common.algorithm;

public class HeapSort {

    public static void main(String[] args) {
    	int[] list = {27, 76, 47, 23, 7, 32, 19, 86};
        System.out.println("************堆排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序后：");
        heapSort(list);
        display(list);
    }

    public static void heapSort(int[] list) {
        // 将无序堆构造成一个大根堆，大根堆有length/2个父节点
        for (int i = list.length / 2 - 1; i >= 0; i--) {
            headAdjust(list, i, list.length);
        }

        // 逐步将每个最大值的根节点与末尾元素交换，并且再调整其为大根堆
        for (int i = list.length - 1; i > 0; i--) {
            // 将堆顶节点和当前未经排序的子序列的最后一个元素交换位置
            swap(list, 0, i);
            headAdjust(list, 0, i);
        }
    }

    public static void headAdjust(int[] list, int parent, int length) {
        // 保存当前父节点
        int temp = list[parent];

        // 得到左孩子节点
        int leftChild = 2 * parent + 1;

        while (leftChild < length) {
            // 如果parent有右孩子，则要判断左孩子是否小于右孩子
            if (leftChild + 1 < length && list[leftChild] < list[leftChild + 1]) {
                leftChild++;
            }
            // 父亲节点大于子节点，就不用做交换
            if (temp >= list[leftChild]) {
                break;
            }
            // 将较大子节点的值赋给父亲节点
            list[parent] = list[leftChild];
            // 然后将子节点做为父亲节点
            parent = leftChild;
            // 找到该父亲节点较小的左孩子节点
            leftChild = 2 * parent + 1;
        }
        // 最后将temp值赋给较大的子节点，以形成两值交换
        list[parent] = temp;
    }

    public static void swap(int[] list, int top, int last) {
        int temp = list[top];
        list[top] = list[last];
        list[last] = temp;
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
