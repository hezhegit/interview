package interview;

import java.util.*;

public class StringCode {

    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int start = 0;
        int left = 0;
        while (left < s.length()) {
            char c = s.charAt(left);
            if (map.containsKey(c)) {
                start = Math.max(start, map.get(c) + 1);
            }
            map.put(c, left);
            max = Math.max(max, left - start + 1);
            left++;
        }
        return max;
    }

    // 402. 移掉K位数字 ： 单调栈
    public String removeKdigits(String num, int k) {
        if (num.length() == k) return "0"; // 全部删除后必须返回 "0"

        Deque<Character> stack = new LinkedList<>();

        for (char digit : num.toCharArray()) {
            // 只要当前 digit 比栈顶小，并且 k > 0，就弹出栈顶元素
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > digit) {
                stack.pollLast();
                k--;
            }
            stack.addLast(digit);
        }

        // 如果 `k` 还有剩余，需要从末尾删除
        while (k > 0 && !stack.isEmpty()) {
            stack.pollLast();
            k--;
        }

        // 处理前导零
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            char c = stack.pollFirst();
            if (result.length() == 0 && c == '0') continue; // 去掉前导零
            result.append(c);
        }

        return result.length() == 0 ? "0" : result.toString();
    }

    // 340. 至多包含 K 个不同字符的最长子串
    // 给定一个字符串 s 和一个整数 k，求最多包含 k 个不同字符的最长子串长度。
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int max = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);

            while (map.size() > k) {  // ⚠️ 修正这里，只有超过 k 才收缩窗口
                char leftChar = s.charAt(left);
                map.put(leftChar, map.get(leftChar) - 1);
                if (map.get(leftChar) == 0) {
                    map.remove(leftChar);
                }
                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    // 面试题38. 字符串的排列
    Set<String> res = new TreeSet<>();
    public String[] goodsOrder(String goods) {
        char[] chars = goods.toCharArray();
        boolean[] used = new boolean[chars.length];
        dfs(chars, new StringBuilder(), used);
        return res.toArray(new String[0]);
    }

    public void dfs(char[] chars,StringBuilder ans, boolean[] used) {
        if (ans.length() == chars.length) {
            res.add(ans.toString());
            return;
        }
        for (int i = 0; i < chars.length; i++) {
            if (!used[i]) {
                ans.append(chars[i]);
                used[i] = true;
                dfs(chars, ans, used);
                used[i] = false;
                ans.deleteCharAt(ans.length() - 1);
            }
        }
    }

    // 46. 全排列
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        boolean[] used = new boolean[nums.length];
        dfs(nums, new ArrayList<>(), used);
        return ans;
    }
    public void dfs(int[] nums, List<Integer> res, boolean[] used) {
        if (res.size() == nums.length) {
            ans.add(new ArrayList<>(res));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                res.add(nums[i]);
                dfs(nums, res, used);
                used[i] = false;
                res.remove(res.size() - 1);
            }
        }
    }

}
