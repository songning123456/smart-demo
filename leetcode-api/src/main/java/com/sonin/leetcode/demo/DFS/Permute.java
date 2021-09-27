package com.sonin.leetcode.demo.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/27 8:39
 */
public class Permute {

    private List<List<Integer>> res = new ArrayList<>();

    private void backtrack(int[] nums, List<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
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

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrack(nums, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        Permute permute = new Permute();
        List<List<Integer>> res = permute.permute(nums);
        System.out.println(res);
    }

}
