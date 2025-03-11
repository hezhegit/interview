package interview;

import java.util.Stack;

public class LinkedListCode {

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    // 206. 反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    // 141. 环形链表
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // 142. 环形链表 II
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }else {
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }

    }

    // 946. 验证栈序列
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int n = pushed.length;
        int m = popped.length;
        if (m != n) {
            return false;
        }
        int i = 0, j = 0;
        Stack<Integer> stack = new Stack<>();
        while (i < n && j < m) {
            if (pushed[i] == popped[j]) {
                i++;
                j++;
            }else if (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }else {
                stack.push(pushed[i]);
                i++;
            }
        }
        while (j < m) {
            if (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }else {
                return false;
            }
        }
        return stack.isEmpty();

    }

    // 32. 最长有效括号
    public int longestValidParentheses(String s) {
        // 栈初始化，存储索引
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 这个 -1 是为了处理从第一个字符开始的有效括号情况

        int maxLen = 0; // 用于记录最长有效括号的长度

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            }else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                }else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}
