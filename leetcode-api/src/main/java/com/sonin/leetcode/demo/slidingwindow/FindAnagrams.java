package com.sonin.leetcode.demo.slidingwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/12 18:40
 */
public class FindAnagrams {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0;
        for (Character item : p.toCharArray()) {
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
            while (right - left >= p.length()) {
                if (need.size() == valid) {
                    result.add(left);
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
        return result;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p = "abc";
        FindAnagrams findAnagrams = new FindAnagrams();
        List<Integer> result = findAnagrams.findAnagrams(s, p);
        System.out.println(result);
    }
}
