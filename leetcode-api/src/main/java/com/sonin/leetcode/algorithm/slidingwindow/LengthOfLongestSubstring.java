package com.sonin.leetcode.algorithm.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/12 8:19
 * 滑动窗口-无重复字符的最长子串
 */
public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int result = 0;
        while (right < s.length()) {
            Character rightChar = s.charAt(right);
            right++;
            window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
            while (window.get(rightChar) > 1) {
                Character leftChar = s.charAt(left);
                left++;
                window.put(leftChar, window.get(leftChar) - 1);
            }
            result = Math.max(result, right - left);
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        int result = lengthOfLongestSubstring.lengthOfLongestSubstring(s);
        System.out.println(result);
    }
}
