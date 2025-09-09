package algorithm.advanced.two_pointers;

import java.util.Arrays;

/**
 * 最接近的三数之和问题
 *
 * 问题描述：
 * 给你一个长度为n的整数数组nums和一个目标值target。
 * 请你从nums中选出三个整数，使它们的和与target最接近。
 * 返回这三个数的和。假定每组输入只存在恰好一个解。
 *
 * 双指针策略：基于差值的移动
 * - 先对数组排序
 * - 固定第一个数，用双指针寻找最接近的组合
 * - 根据当前和与目标值的差异决定指针移动方向
 *
 * 时间复杂度：O(n²)
 * 空间复杂度：O(1)
 *
 * @author magicliang
 * @date 2025-09-02
 */
public class ClosestThreeSum {

    /**
     * 寻找最接近目标值的三数之和
     *
     * @param nums 输入数组
     * @param target 目标值
     * @return 最接近目标值的三数之和
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums); // 排序数组
        int closest = nums[0] + nums[1] + nums[2]; // 初始化最接近的和

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                // 如果找到精确匹配，直接返回
                if (sum == target) {
                    return sum;
                }

                // 更新最接近的和
                if (Math.abs(sum - target) < Math.abs(closest - target)) {
                    closest = sum;
                }

                // 根据差值决定移动方向
                if (sum < target) {
                    left++;  // 和小于目标，需要增大
                } else {
                    right--; // 和大于目标，需要减小
                }
            }
        }

        return closest;
    }

    /**
     * 寻找最接近目标值的三数之和（带优化）
     *
     * 优化策略：
     * 1. 跳过重复元素
     * 2. 提前剪枝：如果当前最小可能和都大于目标，或最大可能和都小于目标
     *
     * @param nums 输入数组
     * @param target 目标值
     * @return 最接近目标值的三数之和
     */
    public int threeSumClosestOptimized(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);
        int closest = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            // 提前剪枝：检查当前固定数字的最小和最大可能值
            int minSum = nums[i] + nums[left] + nums[left + 1];
            if (minSum > target) {
                // 当前最小可能和都大于目标，更新closest并跳出
                if (Math.abs(minSum - target) < Math.abs(closest - target)) {
                    closest = minSum;
                }
                break;
            }

            int maxSum = nums[i] + nums[right - 1] + nums[right];
            if (maxSum < target) {
                // 当前最大可能和都小于目标，更新closest并继续下一轮
                if (Math.abs(maxSum - target) < Math.abs(closest - target)) {
                    closest = maxSum;
                }
                continue;
            }

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    return sum; // 找到精确匹配
                }

                if (Math.abs(sum - target) < Math.abs(closest - target)) {
                    closest = sum;
                }

                if (sum < target) {
                    // 跳过重复的左指针元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    left++;
                } else {
                    // 跳过重复的右指针元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                }
            }
        }

        return closest;
    }

    /**
     * 寻找所有与目标值差距在threshold内的三数之和
     *
     * @param nums 输入数组
     * @param target 目标值
     * @param threshold 允许的最大差距
     * @return 符合条件的三数之和数组
     */
    public int[] threeSumWithinThreshold(int[] nums, int target, int threshold) {
        if (nums == null || nums.length < 3) {
            return new int[0];
        }

        Arrays.sort(nums);
        java.util.List<Integer> result = new java.util.ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                // 检查是否在阈值范围内
                if (Math.abs(sum - target) <= threshold) {
                    result.add(sum);

                    // 跳过重复元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}