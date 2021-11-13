package com.sonin.leetcode.version1.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/19 13:52
 * 回溯法-子集
 */
public class Subsets {

    private List<List<Integer>> res = new LinkedList<>();

    private void backtrack(int[] nums, int start, LinkedList<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrack(nums, i + 1, track);
            track.removeLast();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
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
