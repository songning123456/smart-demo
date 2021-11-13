package com.sonin.leetcode.version1.dp;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/10/31 10:30
 */
public class LongestCommonSubsequence {

    private int[][] memory;

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        memory = new int[m][n];
        for (int[] ints : memory) {
            Arrays.fill(ints, -1);
        }
        return dp(text1, 0, text2, 0);
    }

    private int dp(String s1, int i, String s2, int j) {
        if (i == s1.length() || j == s2.length()) {
            return 0;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            memory[i][j] = 1 + dp(s1, i + 1, s2, j + 1);
        } else {
            memory[i][j] = Integer.max(dp(s1, i + 1, s2, j), dp(s1, i, s2, j + 1));
        }
        return memory[i][j];
    }

}
