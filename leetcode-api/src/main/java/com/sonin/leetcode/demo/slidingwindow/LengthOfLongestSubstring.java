package com.sonin.leetcode.demo.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/16 8:04
 */
public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int res = 0;
        while (right < s.length()) {
            Character rightChar = s.charAt(right);
            right++;
            window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
            while (window.get(rightChar) > 1) {
                Character leftChar = s.charAt(left);
                left++;
                window.put(leftChar, window.get(leftChar) - 1);
            }
            res = Integer.max(right - left, res);
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "pwwkew";
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        int res = lengthOfLongestSubstring.lengthOfLongestSubstring(s);
        System.out.println(res);
    }

}
