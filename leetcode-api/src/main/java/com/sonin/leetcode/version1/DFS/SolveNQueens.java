package com.sonin.leetcode.version1.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/20 10:11
 * 回溯法-N皇后
 */
public class SolveNQueens {

    private List<Character[][]> res = new LinkedList<>();

    private Character[][] deepClone(Character[][] src) {
        Character[][] target = new Character[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[0].length);
        }
        return target;
    }

    private boolean isValid(Character[][] board, int row, int col) {
        int n = board.length;
        for (Character[] characters : board) {
            if (characters[col] == 'Q') {
                return false;
            }
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    private void backtrack(Character[][] board, int row) {
        // 结束条件
        if (row == board.length) {
            res.add(deepClone(board));
            return;
        }
        int n = board[row].length;
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) {
                continue;
            }
            // 做选择
            board[row][col] = 'Q';
            // 进入下一行决策
            backtrack(board, row + 1);
            // 撤销选择
            board[row][col] = '.';
        }
    }

    public List<List<String>> solveNQueens(int n) {
        // 初始化
        Character[][] board = new Character[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        backtrack(board, 0);
        // 转换结果集
        List<List<String>> result = new ArrayList<>();
        for (Character[][] character2s : res) {
            List<String> stringList = new ArrayList<>();
            for (Character[] characters : character2s) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Character character : characters) {
                    stringBuilder.append(character);
                }
                stringList.add(stringBuilder.toString());
            }
            result.add(stringList);
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 8;
        SolveNQueens solveNQueens = new SolveNQueens();
        List<List<String>> res = solveNQueens.solveNQueens(n);
        System.out.println(res);
    }
}
