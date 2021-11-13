package com.sonin.leetcode.version1.array;

import java.util.*;

/**
 * @author sonin
 * @date 2021/10/6 11:03
 * 去除重复字母
 */
public class RemoveDuplicateLetters {

    private Stack<Character> stack = new Stack<>();
    private Set<Character> inStack = new HashSet<>();
    private Map<Character, Integer> countMap = new HashMap<>();

    public String removeDuplicateLetters(String s) {
        for (Character ch : s.toCharArray()) {
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }
        for (Character ch : s.toCharArray()) {
            countMap.put(ch, countMap.get(ch) - 1);
            if (inStack.contains(ch)) {
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > ch) {
                if (countMap.get(ch) == 0) {
                    break;
                }
                inStack.remove(stack.pop());
            }
            stack.push(ch);
            inStack.add(ch);
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.reverse().toString();
    }

    public static void main(String[] args) {
        String s = "bcabc";
        RemoveDuplicateLetters removeDuplicateLetters = new RemoveDuplicateLetters();
        String res = removeDuplicateLetters.removeDuplicateLetters(s);
        System.out.println(res);
    }
}
