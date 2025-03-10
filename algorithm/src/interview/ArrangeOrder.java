package interview;

import java.util.*;

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

    // 4. 寻找两个正序数组的中位数 : （建立两个堆，最大堆&最小堆，复杂度分析）
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

    // 153. 寻找旋转排序数组中的最小值 :无重复元素，二分，总有一半有序，注意边界
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }else {
                right = mid;
            }
        }
        return nums[left];
    }

    // 154. 寻找旋转排序数组中的最小值 II : 有重复元素，需要解除相等时的死循环
    // 相对153:如果 nums[mid] == nums[right]，我们无法判断最小值是在左侧还是右侧
    // 需要 跳过 right 指向的重复元素。
    public int findMin2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }else if (nums[mid] < nums[right]) {
                right = mid;
            }else {
                // nums[mid] == nums[right]
                right--;
            }
        }
        return nums[left];
    }

    // LCR 170. 交易逆序对的总数
    public int reversePairs(int[] record) {
        if (record == null || record.length == 0) {
            return 0;
        }
        return mergeSort(record, 0, record.length - 1);
    }

    private int mergeSort(int[] arr, int left, int right) {
        if (left >= right) return 0;

        int mid = left + (right - left) / 2;
        int count = mergeSort(arr, left, mid) + mergeSort(arr, mid + 1, right);
        count += merge(arr, left, mid, right);
        return count;
    }

    private int merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];  // 临时数组
        int i = left, j = mid + 1, k = 0, count = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += (mid - i + 1);  // 计算逆序对
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        // 复制回原数组
        System.arraycopy(temp, 0, arr, left, temp.length);
        return count;
    }

    // 找出一个无序数组的中位数 （快排，缩小Partition区域 / 取一半元素建堆）
    // 返回无序数组中第 k 小的元素
    private int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        int pivotIndex = partition(arr, left, right);
        if (pivotIndex == k) {
            return arr[pivotIndex];
        } else if (pivotIndex > k) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            if (left < right) {
                arr[left] = arr[right];
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            if (left < right) {
                arr[right] = arr[left];
            }
        }
        arr[left] = pivot;
        return left;
    }

    public int GetMidNumNoSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("数组为空");
        }
        int n = nums.length;
        // 根据需求确定奇数时是第 (n-1)/2, 偶数时是 n/2 或 (n-1)/2
        int target = n / 2; // 这里返回的是上中位数
        return quickSelect(nums, 0, n - 1, target);
    }

    // 148. 排序链表
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ArrayList<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        Collections.sort(list, new Comparator<ListNode>() {

            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });
        head = list.get(0);
        ListNode pre = head;
        for (int i = 1; i < list.size(); i++) {
            pre.next = list.get(i);
            pre = pre.next;
        }
        pre.next = null;
        return head;
    }
    // 进阶 归并O（nlogn）
    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 1. 找到链表的中点（使用快慢指针）
        ListNode mid = getMiddle(head);
        ListNode rightHead = mid.next;
        mid.next = null; // 断开链表

        // 2. 递归排序左右两部分
        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);

        // 3. 归并两个有序链表
        return merge(left, right);
    }

    // 快慢指针找到中点（快指针走两步，慢指针走一步）
    private ListNode getMiddle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 归并两个有序链表
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        // 连接剩余部分
        if (l1 != null) cur.next = l1;
        if (l2 != null) cur.next = l2;

        return dummy.next;
    }

    // 最小的k个数
    public ArrayList<Integer> GetLeastNumbers_Solution (int[] input, int k) {
        // write code here
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int i : input) {
            if (pq.size() < k) {
                pq.add(i);
            }else {
                if (!pq.isEmpty() && pq.peek() > i) {
                    pq.poll();
                    pq.add(i);
                }
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }
        return res;
    }
    // 347. 前K 个高频元素
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 统计每个数字的频率
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 2. 使用小根堆，维护 K 个高频元素
        PriorityQueue<Map.Entry<Integer, Integer>> pq =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // 3. 遍历哈希表，维护前 K 个高频元素
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (pq.size() < k) {
                pq.add(entry);
            } else if (pq.peek().getValue() < entry.getValue()) {
                pq.poll();
                pq.add(entry);
            }
        }

        // 4. 从小根堆中取出 K 个元素
        int[] res = new int[k];
        int i = 0;
        while (!pq.isEmpty()) {
            res[i++] = pq.poll().getKey();  // 这里一定要 i++
        }
        return res;
    }

    // 692. 前K个高频单词
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (pq.size() < k) {
                pq.add(entry);
            }else {
                if (entry.getValue() > pq.peek().getValue()) {
                    pq.poll();
                    pq.add(entry);
                }
            }
        }
        List<String> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.poll().getKey());
        }
        Collections.reverse(res); // 因为小根堆是从低到高，取出后需要反转
        return res;
    }


}
