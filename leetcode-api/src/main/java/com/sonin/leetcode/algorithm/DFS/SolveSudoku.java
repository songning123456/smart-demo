package com.sonin.leetcode.algorithm.DFS;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/9/19 17:21
 * 回溯法-数独
 */
public class SolveSudoku {

    // 判断board[i][j]是否可以填入n
    private boolean isValid(char[][] board, int row, int col, char n) {
        for (int i = 0; i < 9; i++) {
            // 判断行是否存在重复
            if (board[row][i] == n) {
                return false;
            }
            // 判断列是否存在重复
            if (board[i][col] == n) {
                return false;
            }
            // 判断3*3方框内是否存在重复
            if (board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] == n) {
                return false;
            }
        }
        return true;
    }

    private boolean backtrack(char[][] board, int row, int col) {
        int m = 9, n = 9;
        if (col == n) {
            // 穷举到最后一列，重新换行
            return backtrack(board, row + 1, 0);
        }
        if (row == m) {
            return true;
        }
        for (int i = row; i < m; i++) {
            for (int j = col; j < n; j++) {
                if (board[i][j] != '.') {
                    // 如果有预设值，不用穷举
                    return backtrack(board, i, j + 1);
                }
                for (char ch = '1'; ch <= '9'; ch++) {
                    if (!isValid(board, i, j, ch)) {
                        continue;
                    }
                    board[i][j] = ch;
                    if (backtrack(board, i, j + 1)) {
                        return true;
                    }
                    board[i][j] = '.';
                }
                return false;
            }
        }
        return false;
    }

    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        SolveSudoku solveSudoku = new SolveSudoku();
        solveSudoku.solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }

}
