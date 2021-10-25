package com.sonin.leetcode.algorithm.datastructure;

import com.sonin.leetcode.entity.ListNode;

/**
 * @author sonin
 * @date 2021/10/24 19:34
 */
public class HasCycle {

    public boolean hasCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

}
