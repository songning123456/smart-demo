package com.sonin.leetcode.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/10/30 17:52
 */
public class MinFallingPathSum {

    private int[][] memory;

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        memory = new int[n][n];
        for (int[] ints : memory) {
            Arrays.fill(ints, 66666);
        }
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, dpFunc(matrix, n - 1, j));
        }
        return res;
    }

    private int dpFunc(int[][] matrix, int i, int j) {
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length) {
            return 99999;
        }
        if (i == 0) {
            return matrix[0][j];
        }
        if (memory[i][j] != 66666) {
            return memory[i][j];
        }
        memory[i][j] = matrix[i][j] + minFunc(dpFunc(matrix, i - 1, j - 1), dpFunc(matrix, i - 1, j), dpFunc(matrix, i - 1, j + 1));
        return memory[i][j];
    }

    private int minFunc(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static void main(String[] args) {
        MinFallingPathSum minFallingPathSum = new MinFallingPathSum();
        int[][] matrix = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        int res = minFallingPathSum.minFallingPathSum(matrix);
        System.out.println(res);
    }
}
