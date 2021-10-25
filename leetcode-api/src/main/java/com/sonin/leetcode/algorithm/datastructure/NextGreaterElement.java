package com.sonin.leetcode.algorithm.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author sonin
 * @date 2021/10/24 9:50
 */
public class NextGreaterElement {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        Map<Integer, Integer> val2IndexMap = new HashMap<>(2);
        Stack<Integer> stack = new Stack<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums2[i]) {
                stack.pop();
            }
            int tmp = stack.isEmpty() ? -1 : stack.peek();
            val2IndexMap.put(nums2[i], tmp);
            stack.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            int tmp = val2IndexMap.getOrDefault(nums1[i], -1);
            res[i] = tmp;
        }
        return res;
    }

    public static void main(String[] args) {
        NextGreaterElement nextGreaterElement = new NextGreaterElement();
        int[] nums1 = new int[]{4, 1, 2};
        int[] nums2 = new int[]{1, 3, 4, 2};
        int[] res = nextGreaterElement.nextGreaterElement(nums1, nums2);
        System.out.println(res);
    }
}
