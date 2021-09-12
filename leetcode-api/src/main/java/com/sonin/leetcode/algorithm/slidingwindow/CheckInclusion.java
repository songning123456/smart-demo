package com.sonin.leetcode.algorithm.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/11 19:50
 * 滑动窗口-字符串排列
 */
public class CheckInclusion {

    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>(2);
        Map<Character, Integer> window = new HashMap<>(2);
        int left = 0, right = 0;
        int valid = 0;
        for (Character c : s1.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
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
                Character leftCharacter = s2.charAt(left);
                left++;
                if (need.containsKey(leftCharacter)) {
                    if (window.get(leftCharacter).equals(need.get(leftCharacter))) {
                        valid--;
                    }
                    window.put(leftCharacter, window.get(leftCharacter) - 1);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eidbaooo";
        CheckInclusion checkInclusion = new CheckInclusion();
        boolean result = checkInclusion.checkInclusion(s1, s2);
        System.out.println(result);
    }

}
