package com.sonin.leetcode.algorithm.dynamicprogramming;

/**
 * @author sonin
 * @date 2021/10/30 15:26
 */
public class Fib {

    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] dpTable = new int[n + 1];
        dpTable[1] = dpTable[2] = 1;
        for (int i = 3; i <= n; i++) {
            dpTable[i] = dpTable[i - 1] + dpTable[i - 2];
        }
        return dpTable[n];
    }

    public int fib2(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int prev = 1, cur = 1;
        for (int i = 3; i <= n; i++) {
            int sum = prev + cur;
            prev = cur;
            cur = sum;
        }
        return cur;
    }

}
