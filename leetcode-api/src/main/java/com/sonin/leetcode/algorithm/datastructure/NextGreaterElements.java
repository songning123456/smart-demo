package com.sonin.leetcode.algorithm.datastructure;

import java.util.Stack;

/**
 * @author sonin
 * @date 2021/10/24 10:08
 */
public class NextGreaterElements {

    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        int len = nums.length;
        for (int i = 2 * len - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i % len]) {
                stack.pop();
            }
            res[i % len] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % len]);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 1};
        NextGreaterElements nextGreaterElements = new NextGreaterElements();
        int[] res = nextGreaterElements.nextGreaterElements(nums);
        System.out.println(res);
    }
}
