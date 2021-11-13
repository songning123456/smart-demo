package com.sonin.leetcode.version1.datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/10/24 17:49
 */
public class MaxSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        MonotonicQueue window = new MonotonicQueue();
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                window.push(nums[i]);
            } else {
                window.push(nums[i]);
                res.add(window.max());
                window.pop(nums[i - k + 1]);
            }
        }
        return res.stream().mapToInt(Integer::valueOf).toArray();
    }

    private static class MonotonicQueue {
        LinkedList<Integer> queue = new LinkedList<>();

        public void push(int n) {
            while (!queue.isEmpty() && queue.getLast() < n) {
                queue.pollLast();
            }
            queue.addLast(n);
        }

        public int max() {
            return queue.getFirst();
        }

        public void pop(int n) {
            if (n == queue.getFirst()) {
                queue.pollFirst();
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        MaxSlidingWindow maxSlidingWindow = new MaxSlidingWindow();
        int[] res = maxSlidingWindow.maxSlidingWindow(nums, k);
        System.out.println(res);
    }
}
