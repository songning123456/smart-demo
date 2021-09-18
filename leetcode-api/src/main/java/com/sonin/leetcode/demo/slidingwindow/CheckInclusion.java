package com.sonin.leetcode.demo.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/18 7:43
 */
public class CheckInclusion {

    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0;
        for (Character item : s1.toCharArray()) {
            need.put(item, need.getOrDefault(item, 0) + 1);
        }
        while (right < s2.length()) {
            Character rightChar = s2.charAt(right);
            right++;
            if (need.containsKey(rightChar)) {
                window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
                if (need.get(rightChar).equals(window.get(rightChar))) {
                    valid++;
                }
            }
            while (right - left >= s1.length()) {
                if (need.size() == valid) {
                    return true;
                }
                Character leftChar = s2.charAt(left);
                left++;
                if (need.containsKey(leftChar)) {
                    if (need.get(leftChar).equals(window.get(leftChar))) {
                        valid--;
                    }
                    window.put(leftChar, window.get(leftChar) - 1);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eidbaooo";
        CheckInclusion checkInclusion = new CheckInclusion();
        boolean res = checkInclusion.checkInclusion(s1, s2);
        System.out.println(res);
    }
}
