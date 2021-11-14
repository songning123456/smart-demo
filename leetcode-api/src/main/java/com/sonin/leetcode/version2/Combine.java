package com.sonin.leetcode.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/11/14 11:25
 */
public class Combine {

    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> track = new ArrayList<>();
        backtrack(n, k, 1, track);
        return res;
    }

    private void backtrack(int n, int k, int start, List<Integer> track) {
        if (k == track.size()) {
            res.add(new ArrayList<>(track));
        }
        for (int i = start; i <= n; i++) {
            track.add(i);
            backtrack(n, k, i + 1, track);
            track.remove(track.size() - 1);
        }
    }

}
