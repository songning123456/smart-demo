package com.sonin.leetcode.version1.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/19 17:47
 * 回溯法-括号生成
 */
public class GenerateParenthesis {

    private List<LinkedList<Character>> res = new LinkedList<>();

    private void backtrack(int left, int right, LinkedList<Character> track) {
        if (right < left) {
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            res.add(new LinkedList<>(track));
            return;
        }

        track.add('(');
        backtrack(left - 1, right, track);
        track.removeLast();

        track.add(')');
        backtrack(left, right - 1, track);
        track.removeLast();
    }

    public List<String> generateParenthesis(int n) {
        LinkedList<Character> track = new LinkedList<>();
        backtrack(n, n, track);

        List<String> result = new ArrayList<>();
        for (LinkedList<Character> item : res) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character c : item) {
                stringBuilder.append(c);
            }
            result.add(stringBuilder.toString());
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 3;
        GenerateParenthesis generateParenthesis = new GenerateParenthesis();
        List<String> res = generateParenthesis.generateParenthesis(n);
        System.out.println(res);
    }
}
