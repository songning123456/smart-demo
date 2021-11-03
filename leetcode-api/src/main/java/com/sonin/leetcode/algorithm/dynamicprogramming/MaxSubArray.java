package com.sonin.leetcode.algorithm.dynamicprogramming;

/**
 * @author sonin
 * @date 2021/10/31 9:34
 */
public class MaxSubArray {

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.max(nums[i], nums[i] + dp[i - 1]);
        }
        int res = Integer.MIN_VALUE;
        for (int value : dp) {
            res = Integer.max(res, value);
        }
        return res;
    }

}
