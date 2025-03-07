package interview;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val) {
        this.val = val;
    }
}

// https://github.com/KrisCheng/500-interview-question-for-programmers/blob/master/Algorithm.md

public class BinaryTree {
    // 递归实现求二叉树的深度
    public int findDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = findDepth(root.left);
        int rightDepth = findDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // 非递归实现求二叉树的深度
    public int findDepthNoRecursive(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        // 根节点 入队
        queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            // 记录这一层有多少个节点，后续直接出队
            int size = queue.size();
            // 左右节点 入队
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            depth++;
        }
        return depth;
    }

    // 层次遍历：102. 二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        // 根节点入队
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode x = queue.poll();
                list.add(x.val);
                if (x.left != null) queue.add(x.left);
                if (x.right != null) queue.add(x.right);
            }
            res.add(list);
        }
        return res;
    }

    // 257. 二叉树的所有路径
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new LinkedList<>();
        if (root == null) return res;
        StringBuilder sb = new StringBuilder();
        sb.append(root.val);
        findPaths(root, res, sb);
        return res;
    }

    public void findPaths(TreeNode root, List<String> res, StringBuilder sb) {
        if (root.left == null && root.right == null) {
            res.add(sb.toString());
            return;
        }
        if (root.left != null) {
            StringBuilder leftSB = new StringBuilder(sb);
            leftSB.append("->").append(root.left.val);
            findPaths(root.left, res, leftSB);
        }
        if (root.right != null) {
            StringBuilder rightSB = new StringBuilder(sb);
            rightSB.append("->").append(root.right.val);
            findPaths(root.right, res, rightSB);
        }
    }

    // 非递归：使用一个栈，存储 节点 + 当前路径字符串
    public List<String> binaryTreePathsNoRecursive(TreeNode root) {
        List<String> res = new LinkedList<>();
        if (root == null) return res;
        // 存放 节点
        Stack<TreeNode> nodeStack = new Stack<>();
        // 存放 path
        Stack<String> pathStack = new Stack<>();
        // 根节点 入栈
        nodeStack.push(root);
        pathStack.push(root.val + "");
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            String path = pathStack.pop();
            if (node.left == null && node.right == null) {
                res.add(path);
            }
            if (node.right != null) {
                pathStack.push(path + "->" + node.right.val);
                nodeStack.push(node.right);
            }

            if (node.left != null) {
                pathStack.push(path + "->" + node.left.val);
                nodeStack.push(node.left);
            }

        }
        return res;
    }

    // 110. 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftHeight = findDepth(root.left);
        int rightHeight = findDepth(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }


}
