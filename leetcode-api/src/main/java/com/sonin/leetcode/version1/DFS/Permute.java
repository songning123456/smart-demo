package com.sonin.leetcode.version1.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/9/19 13:26
 * 回溯法-全排列
 */
public class Permute {

    private List<List<Integer>> res = new LinkedList<>();

    private void backtrack(int[] nums, LinkedList<Integer> track) {

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
            track.removeLast();
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
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
