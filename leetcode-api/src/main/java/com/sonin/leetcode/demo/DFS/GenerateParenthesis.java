package com.sonin.leetcode.demo.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/21 9:27
 */
public class GenerateParenthesis {

    private List<List<Character>> res = new ArrayList<>();

    private void backtrack(int left, int right, LinkedList<Character> track) {
        if (left > right) {
            return;
        }
        if (left < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            res.add(new ArrayList<>(track));
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
        List<String> newRes = new ArrayList<>();
        for (List<Character> item : res) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character c : item) {
                stringBuilder.append(c);
            }
            newRes.add(stringBuilder.toString());
        }
        return newRes;
    }

    public static void main(String[] args) {
        int n = 3;
        GenerateParenthesis generateParenthesis = new GenerateParenthesis();
        List<String> res = generateParenthesis.generateParenthesis(n);
        System.out.println(res);
    }
}
