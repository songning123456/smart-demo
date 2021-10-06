package com.sonin.leetcode.algorithm.binarysearch;

/**
 * @author sonin
 * @date 2021/10/6 8:08
 */
public class BinarySearch {

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 9;
        BinarySearch binarySearch = new BinarySearch();
        int res = binarySearch.search(nums, target);
        System.out.println(res);
    }
}
