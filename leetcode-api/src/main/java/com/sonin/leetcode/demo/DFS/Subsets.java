package com.sonin.leetcode.demo.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/27 8:27
 */
public class Subsets {

    private List<List<Integer>> res = new ArrayList<>();

    private void backtrack(int[] nums, int start, List<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrack(nums, i + 1, track);
            track.remove(track.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrack(nums, 0, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        Subsets subsets = new Subsets();
        List<List<Integer>> res = subsets.subsets(nums);
        System.out.println(res);
    }
}
