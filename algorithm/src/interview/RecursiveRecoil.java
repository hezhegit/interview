package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursiveRecoil {
    // 131. 分割回文串
    /*
    * start：X ？是否是回文
    *
    * */
    List<List<String>> result = new ArrayList<>();
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return result;
        }

        dfs(new ArrayList<>(), s, 0);
        return result;
    }

    public void dfs(List<String> ans, String s, int start) {
        if (start >= s.length()) {
            result.add(new ArrayList<>(ans));
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (huiwen(s, start, i)) {
                ans.add(s.substring(start, i + 1));
                dfs(ans, s, i + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }
    private boolean huiwen(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }else {
                start++;
                end--;
            }
        }
        return true;
    }

    // 动态规划求解回文串
//    public List<List<String>> partition(String s) {
//        List<List<String>> res = new ArrayList<>();
//        List<String> path = new ArrayList<>();
//        boolean[][] dp = new boolean[s.length()][s.length()];
//
//        // 预处理，dp[i][j] 表示 s[i:j] 是否是回文
//        for (int right = 0; right < s.length(); right++) {
//            for (int left = 0; left <= right; left++) {
//                if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || dp[left + 1][right - 1])) {
//                    dp[left][right] = true;
//                }
//            }
//        }
//
//        // 回溯
//        backtrack(s, 0, path, res, dp);
//        return res;
//    }
//
//    private void backtrack(String s, int start, List<String> path, List<List<String>> res, boolean[][] dp) {
//        if (start == s.length()) {
//            res.add(new ArrayList<>(path));
//            return;
//        }
//        for (int end = start; end < s.length(); end++) {
//            if (dp[start][end]) { // 只有是回文才进入
//                path.add(s.substring(start, end + 1));
//                backtrack(s, end + 1, path, res, dp);
//                path.remove(path.size() - 1); // 撤销选择
//            }
//        }
//    }

    // 面试题 08.12. 八皇后
//    dfs函数(x) {
//        if x==n { //如果x等于n了，说明每行的皇后都放置完毕
//            //将棋盘内容的快照保存下来
//            return
//        }
//        for(y=0;i<n;++y) {
//            if [x,y]这个位置是有效的，即横、竖、两个斜线都有没有被攻击 {
//                将棋盘[x,y]位置设置为 Q
//                dfs(x+1) 继续尝试下一行
//                将棋盘[x,y]位置还原
//            }
//        }
//    }

    List<List<String>> ans = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        if (n <= 0) {
            return ans;
        }
        dfs(0, n, new int[n]);
        return ans;
    }

    private void dfs(int row, int n, int[] cols) {
        if (row == n) {
            ans.add(bord(cols));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, cols)) {
                cols[row] = col;
                dfs(row + 1, n, cols);
                cols[row] = 0;
            }
        }
    }
    private boolean isValid(int row, int col, int[] cols) {
        for (int i = 0; i < row; i++) {
//            cols[i] == col（同列冲突）
//            row - col == i - cols[i]（左对角线冲突）
//            row + col == i + cols[i]（右对角线冲突）
            // 行-行，列-列的绝对值是否🟰
            if (cols[i] == col
            || Math.abs(row - i) == Math.abs(col - cols[i]) ){
                return false;
            }
        }
        return true;
    }

    private List<String> bord(int[] cols) {
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < cols.length; i++) {
            char[] s = new char[cols.length];
            Arrays.fill(s, '.');
            s[cols[i]] = 'Q';
            ans.add(new String(s));
        }
        return ans;
    }
}
