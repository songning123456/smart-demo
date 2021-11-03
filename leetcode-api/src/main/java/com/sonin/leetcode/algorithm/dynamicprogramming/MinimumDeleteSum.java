package com.sonin.leetcode.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/10/31 10:52
 */
public class MinimumDeleteSum {

    private int[][] memory;

    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        memory = new int[m][n];
        for (int[] row : memory) {
            Arrays.fill(row, -1);
        }
        return dp(s1, 0, s2, 0);
    }

    private int dp(String s1, int i, String s2, int j) {
        int res = 0;
        if (i == s1.length()) {
            while (j < s2.length()) {
                res += s2.charAt(j++);
            }
            return res;
        }
        if (j == s2.length()) {
            while (i < s1.length()) {
                res += s1.charAt(i++);
            }
            return res;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            memory[i][j] = dp(s1, i + 1, s2, j + 1);
        } else {
            memory[i][j] = Math.min(
                    s1.charAt(i) + dp(s1, i + 1, s2, j),
                    s2.charAt(j) + dp(s1, i, s2, j + 1)
            );
        }
        return memory[i][j];
    }

}
