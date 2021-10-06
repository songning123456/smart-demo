package com.sonin.leetcode.algorithm.binarysearch;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/10/6 8:51
 */
public class MinEatingSpeed {

    private boolean canFinish(int[] piles, int speed, int H) {
        int time = 0;
        for (int pile : piles) {
            time = time + (pile / speed + ((pile % speed) > 0 ? 1 : 0));
        }
        return time <= H;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, h)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] piles = new int[]{3, 6, 7, 11};
        int H = 8;
        MinEatingSpeed minEatingSpeed = new MinEatingSpeed();
        int res = minEatingSpeed.minEatingSpeed(piles, H);
        System.out.println(res);
    }
}
