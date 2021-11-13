package com.sonin.leetcode.version1.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/11/13 16:23
 */
public class SuperEggDrop {

    private Map<String, Integer> memory = new HashMap<>();

    public int superEggDrop(int k, int n) {
        return dp(k, n);
    }

    private int dp(int k, int n) {
        if (k == 1) {
            return n;
        }
        if (n == 0) {
            return 0;
        }
        if (memory.get(k + "-" + n) != null) {
            return memory.get(k + "-" + n);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res, Math.max(dp(k - 1, i - 1) + 1, dp(k, n - i)));
        }
        memory.put(k + "-" + n, res);
        return res;
    }

}
