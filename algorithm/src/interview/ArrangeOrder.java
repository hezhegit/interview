package interview;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ArrangeOrder {

    // LCR 164. 破解闯关密码
    public String crackPassword(int[] password) {
        StringBuilder sb = new StringBuilder();

        // 转换为字符串数组
        String[] strArr = Arrays.stream(password)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        // 自定义排序规则（按照拼接结果比较）
        Arrays.sort(strArr, (a, b) -> (a + b).compareTo(b + a));

        // 拼接结果
        for (String s : strArr) {
            sb.append(s);
        }

        return sb.toString();
    }

    // 26. 删除有序数组中的重复项
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            // 发现新元素
            if (nums[j] != nums[i]) {
                nums[++i] = nums[j];
            }
        }
        return i+1;
    }

    // 4. 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保 nums1 是较短的数组
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int left = 0, right = m, medianPos = (m + n + 1) / 2;  // medianPos 是左半部分总数

        while (left <= right) {
            int i = left + (right - left) / 2;  // nums1 的分割点
            int j = medianPos - i;              // nums2 的分割点

            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                // 找到合适的划分
                if ((m + n) % 2 == 0) {
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    return Math.max(left1, left2);
                }
            } else if (left1 > right2) {
                // `nums1[i-1]` 太大，需要向左移动
                right = i - 1;
            } else {
                // `nums2[j-1]` 太大，需要向右移动
                left = i + 1;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted!");
    }
}
