package algorithm.advanced.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和问题
 * 
 * 问题描述：
 * 给你一个包含n个整数的数组nums，判断nums中是否存在三个元素a,b,c，
 * 使得a + b + c = 0？请你找出所有和为0且不重复的三元组。
 * 
 * 双指针策略：跳跃式移动
 * - 先对数组排序
 * - 固定第一个数，用双指针寻找另外两个数
 * - 跳过重复元素，避免重复解
 * 
 * 时间复杂度：O(n²)
 * 空间复杂度：O(1)（不考虑结果存储空间）
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class ThreeSum {
    
    /**
     * 寻找所有和为0的三元组
     * 
     * @param nums 输入数组
     * @return 所有不重复的三元组列表
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 排序数组，为双指针做准备
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 如果第一个数大于0，后面不可能有和为0的三元组
            if (nums[i] > 0) {
                break;
            }
            
            // 使用双指针寻找另外两个数
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    // 找到一个三元组
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复的左指针元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 跳过重复的右指针元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 移动双指针
                    left++;
                    right--;
                } else if (sum < 0) {
                    // 和小于0，需要增大，移动左指针
                    left++;
                } else {
                    // 和大于0，需要减小，移动右指针
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * 寻找和为target的三元组数量
     * 
     * @param nums 输入数组
     * @param target 目标和
     * @return 三元组数量
     */
    public int threeSumCount(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        
        Arrays.sort(nums);
        int count = 0;
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == target) {
                    count++;
                    
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
        
        return count;
    }
    
    /**
     * 四数之和问题
     * 
     * 问题描述：给你一个由n个整数组成的数组nums，和一个目标值target。
     * 请你找出并返回满足下述全部条件且不重复的四元组。
     * 
     * @param nums 输入数组
     * @param target 目标和
     * @return 所有不重复的四元组列表
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 4) {
            return result;
        }
        
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 3; i++) {
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            for (int j = i + 1; j < nums.length - 2; j++) {
                // 跳过重复的第二个数
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                int left = j + 1;
                int right = nums.length - 1;
                
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
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
        }
        
        return result;
    }
}