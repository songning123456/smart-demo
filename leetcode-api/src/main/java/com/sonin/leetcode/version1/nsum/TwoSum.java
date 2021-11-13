package com.sonin.leetcode.version1.nsum;

import java.util.*;

/**
 * @author sonin
 * @date 2021/10/5 20:00
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> val2IndexMap = new HashMap<>(2);
        for (int i = 0; i < nums.length; i++) {
            val2IndexMap.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            if (val2IndexMap.containsKey(other) && val2IndexMap.get(other) != i) {
                return new int[]{i, val2IndexMap.get(other)};
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        TwoSum twoSum = new TwoSum();
        int[] res = twoSum.twoSum(nums, target);
        System.out.println(res);
    }
}
