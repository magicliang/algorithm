package algorithm.selectedtopics.leetcode.missingranges;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * project name: algorithm
 *
 * description: Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
 * Output: ["2", "4->49", "51->74", "76->99"]
 *
 * @author magicliang
 *
 *         date: 2025-09-04 18:59
 */
public class MissingRangesProblem {

    /**
     * 查找缺失的范围 - 基于索引遍历的实现
     * 
     * 算法思路：
     * 1. 特殊处理空数组情况
     * 2. 遍历数组，分别处理三种缺失情况：
     *    - 第一个元素前的缺失范围
     *    - 相邻元素间的缺失范围  
     *    - 最后一个元素后的缺失范围
     * 
     * 时间复杂度：O(n)，其中n是数组长度
     * 空间复杂度：O(1)，不考虑结果列表的空间
     * 
     * @param nums 已排序的整数数组，所有元素都在[lower, upper]范围内
     * @param lower 范围下界
     * @param upper 范围上界
     * @return 缺失范围的字符串列表，格式为单个数字或"start->end"
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        final int n = nums.length;

        // 处理空数组的情况：整个[lower, upper]范围都缺失
        if (n == 0) {
            result.add(format(lower, upper));
            return result;
        }

        // 遍历数组，处理三种缺失情况
        // 注意：每个元素可能同时涉及多种情况的处理
        for (int i = 0; i < n; i++) {
            // 情况1：处理第一个元素前的缺失范围
            if (i == 0) {
                // 如果第一个元素大于lower，说明[lower, nums[0]-1]范围缺失
                if (nums[i] > lower) {
                    result.add(format(lower, nums[i] - 1));
                }
            } else {
                // 情况2：处理相邻元素间的缺失范围
                // 如果当前元素不是前一个元素的连续数字，说明中间有缺失
                if (nums[i] != nums[i - 1] + 1) {
                    result.add(format(nums[i - 1] + 1, nums[i] - 1));
                }
            }

            // 情况3：处理最后一个元素后的缺失范围
            // 注意：同一个元素可能既是第一个元素，也是最后一个元素（单元素数组）
            if (i == n - 1 && nums[i] < upper) {
                result.add(format(nums[i] + 1, upper));
            }
        }
        return result;
    }

    /**
     * 更简洁优雅的算法实现 - 基于C++版本翻译
     * 
     * 核心思想：
     * 1. 动态更新lower指针，避免复杂的索引判断
     * 2. 遍历数组时，检查当前数字前是否有缺失范围
     * 3. 提前终止：如果当前数字等于upper，直接返回结果
     * 4. 最后检查数组结束后是否还有缺失范围
     * 
     * 优势：
     * - 代码更简洁，逻辑更清晰
     * - 避免了复杂的边界条件判断
     * - 自然处理空数组情况
     * - 时间复杂度O(n)，空间复杂度O(1)
     */
    public List<String> findMissingRangesV2(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        
        // 遍历数组中的每个数字
        for (int num : nums) {
            // 如果当前数字大于lower，说明[lower, num-1]范围内有缺失
            if (num > lower) {
                result.add(formatRange(lower, num - 1));
            }
            
            // 提前终止优化：如果当前数字等于upper，后面不可能再有缺失
            if (num == upper) {
                return result;
            }
            
            // 更新lower为下一个可能的缺失起点
            lower = num + 1;
        }
        
        // 检查数组结束后是否还有缺失范围
        if (lower <= upper) {
            result.add(formatRange(lower, upper));
        }
        
        return result;
    }

    /**
     * 查找缺失的范围 - 优雅简洁的实现版本
     * 
     * 算法核心思想：
     * 1. 使用动态更新的lower指针作为"下一个期望的数字"
     * 2. 遍历数组时检查实际数字与期望数字的差距
     * 3. 如果实际数字大于期望数字，说明中间有缺失范围
     * 4. 每处理完一个数字，更新期望数字为下一个位置
     * 5. 最后检查是否还有未处理的尾部范围
     * 
     * 优势：
     * - 逻辑清晰：lower始终表示"下一个期望填充的位置"
     * - 代码简洁：避免复杂的索引和边界判断
     * - 自然处理：空数组、单元素等特殊情况无需额外处理
     * - 提前终止：遇到upper时立即返回，提高效率
     * 
     * 时间复杂度：O(n)，其中n是数组长度
     * 空间复杂度：O(1)，不考虑结果列表的空间
     * 
     * @param nums 已排序的整数数组，所有元素都在[lower, upper]范围内
     * @param lower 范围下界
     * @param upper 范围上界
     * @return 缺失范围的字符串列表，格式为单个数字或"start->end"
     */
    public List<String> findMissingRangesPretty(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();

        // 遍历数组中的每个数字
        for (int num : nums) {
            // 检查当前数字前是否有缺失范围
            // lower表示"下一个期望的数字"，如果实际数字大于期望，说明有缺失
            if (num > lower) {
                result.add(formatRange(lower, num - 1));
            }
            
            // 提前终止优化：如果当前数字已经是上界，后续不可能再有缺失
            if (num == upper) {
                return result;
            }

            // 关键步骤：更新lower为下一个期望的数字位置
            // 这样下次循环时，lower就代表"下一个应该被填充的位置"
            lower = num + 1;
        }

        // 处理数组遍历完成后的情况
        // 此时lower表示数组最后一个元素之后的位置
        // 如果lower <= upper，说明还有尾部范围未被填充
        if (lower <= upper) {
            result.add(formatRange(lower, upper));
        }

        return result;
    }

    /**
     * 格式化范围的辅助方法
     * 如果begin == end，返回单个数字
     * 否则返回"begin->end"格式
     */
    private String formatRange(int begin, int end) {
        if (begin == end) {
            return String.valueOf(begin);
        }
        return begin + "->" + end;
    }

    String format(Integer begin, Integer end) {
        if (Objects.equals(begin, end)) {
            return begin.toString();
        }
        return String.format("%d->%d", begin, end);
    }
}
