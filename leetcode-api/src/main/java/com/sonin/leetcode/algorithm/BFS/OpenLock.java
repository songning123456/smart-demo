package com.sonin.leetcode.algorithm.BFS;

import java.util.*;

/**
 * @author sonin
 * @date 2021/10/7 9:35
 * 752. 打开转盘锁
 */
public class OpenLock {

    private String plusOne(String s, int j) {
        char[] characters = s.toCharArray();
        if (characters[j] == '9') {
            characters[j] = '0';
        } else {
            characters[j] += 1;
        }
        return new String(characters);
    }

    private String minusOne(String s, int j) {
        char[] characters = s.toCharArray();
        if (characters[j] == '0') {
            characters[j] = '9';
        } else {
            characters[j] -= 1;
        }
        return new String(characters);
    }

    public int openLock(String[] deadends, String target) {
        Set<String> deads = new HashSet<>();
        Collections.addAll(deads, deadends);
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        visited.add("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (deads.contains(cur)) {
                    continue;
                }
                if (target.equals(cur)) {
                    return step;
                }
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) {
                        queue.offer(up);
                        visited.add(up);
                    }
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) {
                        queue.offer(down);
                        visited.add(down);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] deadends = new String[]{"0201", "0101", "0102", "1212", "2002"};
        String target = "0202";
        OpenLock openLock = new OpenLock();
        int res = openLock.openLock(deadends, target);
        System.out.println(res);
    }

}
