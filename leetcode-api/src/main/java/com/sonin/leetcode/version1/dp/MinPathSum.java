package com.sonin.leetcode.version1.dp;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/11/13 15:31
 */
public class MinPathSum {

    private int[][] memory;

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        memory = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memory[i], -1);
        }
        return dp(grid, m - 1, n - 1);
    }

    private int dp(int[][] grid, int i, int j) {
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (memory[i][j] != -1) {
            return memory[i][j];
        }
        memory[i][j] = Math.min(dp(grid, i - 1, j), dp(grid, i, j - 1)) + grid[i][j];
        return memory[i][j];
    }

}
