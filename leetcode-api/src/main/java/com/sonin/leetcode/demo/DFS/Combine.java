package com.sonin.leetcode.demo.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/21 9:48
 */
public class Combine {

    private List<List<Integer>> res = new ArrayList<>();

    private void backtrack(int n, int k, int start, LinkedList<Integer> track) {
        if (k == track.size()) {
            res.add(new ArrayList<>(track));
        }
        for (int i = start; i <= n; i++) {
            track.add(i);
            backtrack(n, k, i + 1, track);
            track.removeLast();
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        LinkedList<Integer> track = new LinkedList<>();
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
