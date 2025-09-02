package algorithm.advanced.two_pointers;

/**
 * 有序数组两数之和问题
 * 
 * 问题描述：
 * 给定一个已按升序排列的整数数组numbers，请你从数组中找出两个数满足相加之和等于目标数target。
 * 函数应该以长度为2的整数数组的形式返回这两个数的下标值。
 * 
 * 双指针策略：固定步长移动
 * - 左指针从数组开始，右指针从数组末尾开始
 * - 根据当前和与目标值的比较，决定移动哪个指针
 * - 和小于目标值：移动左指针（增大和）
 * - 和大于目标值：移动右指针（减小和）
 * - 和等于目标值：找到答案
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class TwoSumSorted {
    
    /**
     * 在有序数组中寻找两数之和等于目标值的索引
     * 
     * @param numbers 有序整数数组
     * @param target 目标和
     * @return 两个数的索引数组，如果不存在则返回空数组
     */
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) {
            return new int[0];
        }
        
        int left = 0;                    // 左指针
        int right = numbers.length - 1;  // 右指针
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                // 找到目标，返回索引（通常题目要求1-based索引）
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                // 和小于目标，需要增大和，移动左指针
                left++;
            } else {
                // 和大于目标，需要减小和，移动右指针
                right--;
            }
        }
        
        // 未找到满足条件的两数
        return new int[0];
    }
    
    /**
     * 返回0-based索引的版本
     * 
     * @param numbers 有序整数数组
     * @param target 目标和
     * @return 两个数的索引数组（0-based），如果不存在则返回空数组
     */
    public int[] twoSumZeroBased(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) {
            return new int[0];
        }
        
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[0];
    }
}