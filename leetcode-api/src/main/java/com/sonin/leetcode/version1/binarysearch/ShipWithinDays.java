package com.sonin.leetcode.version1.binarysearch;

import java.util.Arrays;

/**
 * @author sonin
 * @date 2021/10/6 9:40
 */
public class ShipWithinDays {

    private boolean canFinish(int[] weights, int days, int cap) {
        int i = 0;
        for (int day = 1; day <= days; days++) {
            int tmpCap = cap;
            while ((tmpCap -= weights[i]) >= 0) {
                i++;
                if (i == weights.length) {
                    return true;
                }
            }
        }
        return false;
    }

    public int shipWithinDays(int[] weights, int days) {
        int left = Arrays.stream(weights).max().getAsInt();
        int right = Arrays.stream(weights).sum();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canFinish(weights, days, mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] weights = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days = 5;
        ShipWithinDays shipWithinDays = new ShipWithinDays();
        int res = shipWithinDays.shipWithinDays(weights, days);
        System.out.println(res);
    }
}
