package com.sonin.leetcode.algorithm.BFS;

import com.sonin.leetcode.entity.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author sonin
 * @date 2021/10/7 8:42
 * 二叉树的最小深度
 */
public class MinDepth {

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left == null && cur.right == null) {
                    return depth;
                }
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            depth++;
        }
        return depth;
    }

    public static void main(String[] args) {
        TreeNode treeNode9 = new TreeNode(9);
        TreeNode treeNode15 = new TreeNode(15);
        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode20 = new TreeNode(20, treeNode15, treeNode7);
        TreeNode treeNode3 = new TreeNode(3, treeNode9, treeNode20);
        MinDepth minDepth = new MinDepth();
        int res = minDepth.minDepth(treeNode3);
        System.out.println(res);
    }

}
