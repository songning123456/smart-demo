package com.sonin.leetcode.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/11/14 10:25
 */
public class Subsets {

    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrack(nums, 0, track);
        return res;
    }

    private void backtrack(int[] nums, int start, List<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrack(nums, i + 1, track);
            track.remove(track.size() - 1);
        }
    }

}
