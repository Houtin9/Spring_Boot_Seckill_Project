package com.itstyle.seckill.common.algorithm;

import java.util.*;

public class FindMedian {
	//maxHeap保存较小的半边数据，minHeap保存较大的半边数据。
    private static PriorityQueue<Integer> maxHeap, minHeap;

    public static void main(String[] args) {

        Comparator<Integer> revCmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                return right.compareTo(left);
            }
        };

        maxHeap = new PriorityQueue<Integer>(100, revCmp);
        minHeap = new PriorityQueue<Integer>(100);
        Random ra =new Random();
        for(int i=0;i<=100;i++){
        	int number = ra.nextInt(200);
        	addNumber(number);
        }
        System.out.println(minHeap);
        System.out.println(maxHeap);
        System.out.println(getMedian());
    }


    public static void addNumber(int value) {
        if (maxHeap.size() == minHeap.size()) {
            if (minHeap.peek() != null && value > minHeap.peek()) {
                maxHeap.offer(minHeap.poll());
                minHeap.offer(value);
            } else {
                maxHeap.offer(value);
            }
        } else {
            if (value < maxHeap.peek()) {
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(value);
            } else {
                minHeap.offer(value);
            }
        }
    }

    public static double getMedian() {
        if (maxHeap.isEmpty()) {
            return -1; 
        }
        
        if (maxHeap.size() == minHeap.size()) {
            return (double)(minHeap.peek() + maxHeap.peek())/2;
        } else {
            return maxHeap.peek();
        }
    }
}
