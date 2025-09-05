package algorithm.foundations;

import java.util.*;

/**
 * LeetCode 3510: 移除最小数对使数组有序 II
 * 
 * 问题描述：
 * 给你一个数组 nums，你可以执行以下操作任意次数：
 * - 选择相邻元素对中和最小的一对。如果存在多个这样的对，选择最左边的一个。
 * - 用它们的和替换这对元素。
 * 返回将数组变为非递减所需的最小操作次数。
 * 
 * 关键点：
 * 1. 每次必须选择相邻元素对中和最小的一对
 * 2. 如果有多个最小和，选择最左边的
 * 3. 目标是使数组变成非递减序列
 * 
 * 解题思路：
 * 1. 贪心算法：每次选择和最小的相邻对进行合并
 * 2. 重复此过程直到数组有序
 * 3. 统计操作次数
 * 
 * @author liangchuan
 */
public class MinimumMergeOperations {
    
    public static void main(String[] args) {
        MinimumMergeOperations solution = new MinimumMergeOperations();
        
        // LeetCode 3510 测试用例
        int[] nums1 = {3, 1, 4, 1, 5};
        System.out.println("原数组: " + Arrays.toString(nums1));
        int operations1 = solution.minOperationsToMakeNonDecreasing(nums1.clone());
        System.out.println("最小操作次数: " + operations1);
        System.out.println();
        
        int[] nums2 = {5, 4, 3, 2, 1};
        System.out.println("原数组: " + Arrays.toString(nums2));
        int operations2 = solution.minOperationsToMakeNonDecreasing(nums2.clone());
        System.out.println("最小操作次数: " + operations2);
        System.out.println();
        
        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("原数组: " + Arrays.toString(nums3));
        int operations3 = solution.minOperationsToMakeNonDecreasing(nums3.clone());
        System.out.println("最小操作次数: " + operations3);
        System.out.println();
        
        int[] nums4 = {2, 1, 3, 1, 1};
        System.out.println("原数组: " + Arrays.toString(nums4));
        int operations4 = solution.minOperationsToMakeNonDecreasing(nums4.clone());
        System.out.println("最小操作次数: " + operations4);
        System.out.println();
        
        // 更多测试用例
        int[] nums5 = {2, 1};
        System.out.println("原数组: " + Arrays.toString(nums5));
        int operations5 = solution.minOperationsToMakeNonDecreasing(nums5.clone());
        System.out.println("最小操作次数: " + operations5);
        System.out.println();
        
        int[] nums6 = {4, 3, 2, 1, 2, 3, 1};
        System.out.println("原数组: " + Arrays.toString(nums6));
        int operations6 = solution.minOperationsToMakeNonDecreasing(nums6.clone());
        System.out.println("最小操作次数: " + operations6);
    }
    
    /**
     * LeetCode 3510: 移除最小数对使数组有序 II
     * 
     * 算法思路：
     * 1. 每次选择相邻元素对中和最小的一对进行合并
     * 2. 如果有多个最小和，选择最左边的一个
     * 3. 重复此过程直到数组变成非递减序列
     * 4. 统计操作次数
     * 
     * 关键点：
     * - 必须选择和最小的相邻对，而不是任意的逆序对
     * - 这是贪心策略的核心
     * 
     * @param nums 输入数组
     * @return 最小操作次数
     */
    public int minOperationsToMakeNonDecreasing(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        
        // 创建变量存储输入（按题目要求）
        int[] wexthorbin = nums.clone();
        
        List<Integer> list = new ArrayList<>();
        for (int num : wexthorbin) {
            list.add(num);
        }
        
        int operations = 0;
        
        // 持续合并直到数组有序
        while (!isNonDecreasing(list)) {
            // 找到所有相邻对的和
            Integer minSum = null;  // 使用可空的Integer
            int minIndex = -1;
            
            // 找到和最小的相邻对（最左边的）
            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (minSum == null || sum < minSum) {  // 更优雅的比较
                    minSum = sum;
                    minIndex = i;
                }
            }
            
            // 如果找到了最小和的相邻对，进行合并
            if (minSum != null) {  // 更清晰的判断条件
                int val1 = list.get(minIndex);
                int val2 = list.get(minIndex + 1);
                
                // 用和替换第一个元素，删除第二个元素
                list.set(minIndex, minSum);
                list.remove(minIndex + 1);
                
                operations++;
                
                System.out.println("第" + operations + "次操作: 合并位置 " + minIndex + 
                                 " 的元素 " + val1 + " 和 " + val2 + " (和=" + minSum + ")");
                System.out.println("当前数组: " + list);
            } else {
                // 理论上不应该到达这里
                System.out.println("错误: 未找到可合并的相邻对");
                break;
            }
        }
        
        return operations;
    }
    
    /**
     * 检查数组是否为非递减序列
     * 
     * @param list 要检查的列表
     * @return 是否为非递减序列
     */
    private boolean isNonDecreasing(List<Integer> list) {
        if (list.size() <= 1) {
            return true;
        }
        
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 优化版本：更高效的实现
     * 
     * 使用优先队列来快速找到最小和的相邻对
     * 
     * @param nums 输入数组
     * @return 最小操作次数
     */
    public int minOperationsOptimized(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        
        int operations = 0;
        
        while (!isNonDecreasing(list) && list.size() > 1) {
            // 使用优先队列存储 (和, 索引) 对
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
                if (a[0] != b[0]) return Integer.compare(a[0], b[0]); // 按和排序
                return Integer.compare(a[1], b[1]); // 和相同时按索引排序（最左边优先）
            });
            
            // 将所有相邻对加入优先队列
            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                pq.offer(new int[]{sum, i});
            }
            
            // 取出和最小的相邻对
            if (!pq.isEmpty()) {
                int[] minPair = pq.poll();
                int minSum = minPair[0];
                int minIndex = minPair[1];
                
                // 验证索引仍然有效（因为之前的操作可能改变了数组）
                if (minIndex < list.size() - 1) {
                    int val1 = list.get(minIndex);
                    int val2 = list.get(minIndex + 1);
                    
                    // 合并
                    list.set(minIndex, val1 + val2);
                    list.remove(minIndex + 1);
                    
                    operations++;
                    
                    System.out.println("第" + operations + "次操作: 合并 " + val1 + " 和 " + val2 + 
                                     " = " + (val1 + val2));
                    System.out.println("当前数组: " + list);
                }
            }
        }
        
        return operations;
    }
}