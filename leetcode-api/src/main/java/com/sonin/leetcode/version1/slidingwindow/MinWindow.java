package com.sonin.leetcode.version1.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/11 14:45
 * 滑动窗口-最小覆盖子串
 */
public class MinWindow {

    public String minWindow(String s, String t) {
        // t中字符出现次数
        Map<Character, Integer> need = new HashMap<>();
        // 窗口中的相应字符的出现次数
        Map<Character, Integer> window = new HashMap<>();
        // 窗口的两端
        int left = 0, right = 0;
        // 窗口中满足need条件的字符个数
        int valid = 0;
        // 最小覆盖子串的起始索引及长度
        int start = 0, len = Integer.MAX_VALUE;
        for (Character c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        while (right < s.length()) {
            // 将要进入窗口的字符
            Character rightChar = s.charAt(right);
            // 右移
            right++;
            if (need.containsKey(rightChar)) {
                window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
                if (need.get(rightChar).equals(window.get(rightChar))) {
                    valid++;
                }
            }
            // 判断左侧窗口是否需要收缩
            while (valid == need.size()) {
                // 更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // 左侧将要移出的子串
                Character leftChar = s.charAt(left);
                left++;
                if (need.containsKey(leftChar)) {
                    if (window.get(leftChar).equals(need.get(leftChar))) {
                        valid--;
                    }
                    window.put(leftChar, window.get(leftChar) - 1);
                }
            }
        }
        // 返回最小覆盖子串
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
