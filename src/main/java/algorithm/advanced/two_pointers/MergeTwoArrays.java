package algorithm.advanced.two_pointers;

/**
 * 合并两个有序数组问题
 *
 * 问题描述：
 * 给你两个按非递减顺序排列的整数数组nums1和nums2，另有两个整数m和n，
 * 分别表示nums1和nums2中元素的数目。请你合并nums2到nums1中，
 * 使合并后的数组同样按非递减顺序排列。
 *
 * 双指针策略：分离双指针
 * - 两个指针分别处理不同的数组
 * - 根据比较结果移动相应指针
 * - 从后往前填充，避免覆盖未处理的元素
 *
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(1)
 *
 * @author magicliang
 * @date 2025-09-02
 */
public class MergeTwoArrays {

    /**
     * 合并两个有序数组（原地合并）
     *
     * 算法思路：
     * - 从后往前合并，避免覆盖nums1中未处理的元素
     * - 使用三个指针：i指向nums1的有效元素末尾，j指向nums2末尾，k指向合并位置
     *
     * @param nums1 第一个有序数组（有足够空间容纳合并结果）
     * @param m nums1中有效元素个数
     * @param nums2 第二个有序数组
     * @param n nums2中元素个数
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;      // nums1有效元素的最后一个索引
        int j = n - 1;      // nums2的最后一个索引
        int k = m + n - 1;  // 合并后数组的最后一个索引

        // 从后往前合并
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }

        // 如果nums2还有剩余元素，复制到nums1
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }

        // 如果nums1还有剩余元素，它们已经在正确位置，无需移动
    }

    /**
     * 合并两个有序数组（返回新数组）
     *
     * @param nums1 第一个有序数组
     * @param nums2 第二个有序数组
     * @return 合并后的新数组
     */
    public int[] mergeToNewArray(int[] nums1, int[] nums2) {
        if (nums1 == null) {
            nums1 = new int[0];
        }
        if (nums2 == null) {
            nums2 = new int[0];
        }

        int[] result = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;

        // 合并两个数组
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                result[k] = nums1[i];
                i++;
            } else {
                result[k] = nums2[j];
                j++;
            }
            k++;
        }

        // 复制剩余元素
        while (i < nums1.length) {
            result[k] = nums1[i];
            i++;
            k++;
        }

        while (j < nums2.length) {
            result[k] = nums2[j];
            j++;
            k++;
        }

        return result;
    }

    /**
     * 合并k个有序数组
     *
     * 使用分治法，两两合并
     *
     * @param arrays k个有序数组
     * @return 合并后的数组
     */
    public int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        return mergeKArraysHelper(arrays, 0, arrays.length - 1);
    }

    private int[] mergeKArraysHelper(int[][] arrays, int left, int right) {
        if (left == right) {
            return arrays[left];
        }

        if (left + 1 == right) {
            return mergeToNewArray(arrays[left], arrays[right]);
        }

        int mid = left + (right - left) / 2;
        int[] leftMerged = mergeKArraysHelper(arrays, left, mid);
        int[] rightMerged = mergeKArraysHelper(arrays, mid + 1, right);

        return mergeToNewArray(leftMerged, rightMerged);
    }

    /**
     * 寻找两个有序数组的交集
     *
     * @param nums1 第一个有序数组
     * @param nums2 第二个有序数组
     * @return 交集数组
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return new int[0];
        }

        java.util.List<Integer> result = new java.util.ArrayList<>();
        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                // 避免重复元素
                if (result.isEmpty() || result.get(result.size() - 1) != nums1[i]) {
                    result.add(nums1[i]);
                }
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 寻找两个有序数组的交集（包含重复元素）
     *
     * @param nums1 第一个有序数组
     * @param nums2 第二个有序数组
     * @return 交集数组（包含重复）
     */
    public int[] intersectionWithDuplicates(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return new int[0];
        }

        java.util.List<Integer> result = new java.util.ArrayList<>();
        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                result.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}