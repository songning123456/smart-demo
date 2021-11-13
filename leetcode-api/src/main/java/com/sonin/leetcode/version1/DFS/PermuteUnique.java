package com.sonin.leetcode.version1.DFS;

import java.util.*;

/**
 * @author sonin
 * @date 2021/9/20 16:04
 */
public class PermuteUnique {

    private List<List<Integer>> res = new LinkedList<>();
    private boolean[] vis;

    private void backtrack(int[] nums, LinkedList<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (vis[i] || (i > 0 && nums[i - 1] == nums[i] && !vis[i - 1])) {
                continue;
            }
            track.add(nums[i]);
            vis[i] = true;
            backtrack(nums, track);
            vis[i] = false;
            track.removeLast();
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        vis = new boolean[nums.length];
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2};
        PermuteUnique permuteUnique = new PermuteUnique();
        List<List<Integer>> res = permuteUnique.permuteUnique(nums);
        System.out.println(res);
    }
}
