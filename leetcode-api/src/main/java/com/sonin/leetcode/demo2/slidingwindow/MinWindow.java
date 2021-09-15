package com.sonin.leetcode.demo2.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/15 8:09
 */
public class MinWindow {

    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int start = 0, len = Integer.MAX_VALUE;
        int valid = 0;
        for (Character item : t.toCharArray()) {
            need.put(item, need.getOrDefault(item, 0) + 1);
        }
        while (right < s.length()) {
            Character rightChar = s.charAt(right);
            right++;
            if (need.containsKey(rightChar)) {
                window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
                if (need.get(rightChar).equals(window.get(rightChar))) {
                    valid++;
                }
            }
            while (need.size() == valid) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                Character leftChar = s.charAt(left);
                left++;
                if (need.containsKey(leftChar)) {
                    if (need.get(leftChar).equals(window.get(leftChar))) {
                        valid--;
                    }
                    window.put(leftChar, window.get(leftChar) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        MinWindow minWindow = new MinWindow();
        String result = minWindow.minWindow(s, t);
        System.out.println(result);
    }
}
