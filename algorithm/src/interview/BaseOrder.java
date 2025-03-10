package interview;

public class BaseOrder {
    // 手写快排
    public void quickSelect(int[] nums, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivot = partition(nums, low, high);
        quickSelect(nums, low, pivot - 1);
        quickSelect(nums, pivot + 1, high);

    }
    private int partition(int[] nums, int low, int high) {
        int pivot = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= pivot) {
                high--;
            }
            if (low < high) {
                nums[low] = nums[high];
            }
            while (low < high && nums[low] <= pivot) {
                low++;
            }
            if (low < high) {
                nums[high] = nums[low];
            }
        }
        nums[low] = pivot;
        return low;
    }
}
