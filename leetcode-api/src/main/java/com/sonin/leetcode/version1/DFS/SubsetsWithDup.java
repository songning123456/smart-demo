package com.sonin.leetcode.version1.DFS;

import java.util.*;

/**
 * @author sonin
 * @date 2021/9/20 20:00
 */
public class SubsetsWithDup {

    private List<List<Integer>> res = new ArrayList<>();

    private void backtrack(int[] nums, int start, LinkedList<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            // 剔除重复元素
            if (i != start && nums[i] == nums[i - 1]) {
                continue;
            }
            track.add(nums[i]);
            backtrack(nums, i + 1, track);
            track.removeLast();
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, 0, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 4, 4, 1, 4};
        SubsetsWithDup subsetsWithDup = new SubsetsWithDup();
        List<List<Integer>> res = subsetsWithDup.subsetsWithDup(nums);
        System.out.println(res);
    }
}
