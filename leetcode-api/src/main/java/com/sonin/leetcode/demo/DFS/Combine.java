package com.sonin.leetcode.demo.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/27 8:44
 */
public class Combine {

    private List<List<Integer>> res = new ArrayList<>();

    private void backtrack(int n, int k, int start, List<Integer> track) {
        if (track.size() == k) {
            res.add(new ArrayList<>(track));
            return;
        }
        for (int i = start; i <= n; i++) {
            track.add(i);
            backtrack(n, k, i + 1, track);
            track.remove(track.size() - 1);
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> track = new ArrayList<>();
        backtrack(n, k, 1, track);
        return res;
    }

    public static void main(String[] args) {
        int n = 4, k = 2;
        Combine combine = new Combine();
        List<List<Integer>> res = combine.combine(n, k);
        System.out.println(res);
    }

}
