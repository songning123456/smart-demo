package com.sonin.leetcode.algorithm.poiner;

/**
 * @author sonin
 * @date 2021/10/6 21:39
 * 移除元素
 */
public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 2, 3};
        int val = 3;
        RemoveElement removeElement = new RemoveElement();
        int len = removeElement.removeElement(nums, val);
        System.out.println(len);
    }
}
