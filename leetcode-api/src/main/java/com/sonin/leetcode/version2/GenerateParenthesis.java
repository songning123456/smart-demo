package com.sonin.leetcode.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/11/14 13:53
 */
public class GenerateParenthesis {

    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        String track = "";
        backtrack(n, n, track);
        return res;
    }

    private void backtrack(int left, int right, String track) {
        if (left > right) {
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            res.add(track);
        }

        track += "(";
        backtrack(left - 1, right, track);
        track = track.substring(0, track.length() - 1);

        track += ")";
        backtrack(left, right - 1, track);
        track = track.substring(0, track.length() - 1);
    }

}
