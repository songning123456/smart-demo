package com.sonin.leetcode.version2;

import java.util.*;

/**
 * @author sonin
 * @date 2021/11/14 10:48
 */
public class SubsetsWithDup {

    private List<List<Integer>> res = new ArrayList<>();
    private Set<String> memory = new HashSet<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<Integer> track = new ArrayList<>();
        backtrack(nums, 0, track);
        memory.clear();
        return res;
    }

    private void backtrack(int[] nums, int start, List<Integer> track) {
        if (!memory.contains("" + track)) {
            memory.add("" + track);
            res.add(new ArrayList<>(track));
        }
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrack(nums, i + 1, track);
            track.remove(track.size() - 1);
        }
    }

}
