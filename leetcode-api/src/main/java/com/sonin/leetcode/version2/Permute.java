package com.sonin.leetcode.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/11/13 17:40
 */
public class Permute {

    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrack(nums, track);
        return res;
    }

    private void backtrack(int[] nums, List<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
            return;
        }
        for (int num : nums) {
            if (track.contains(num)) {
                continue;
            }
            track.add(num);
            backtrack(nums, track);
            track.remove(track.size() - 1);
        }
    }

}
