package algorithm.selectedtopics.leetcode.majorityelement;

import java.util.*;

/**
 * 多数元素问题的多种解法
 * 
 * 问题描述：
 * 给定一个大小为 n 的数组 nums，返回其中的多数元素。
 * 多数元素是指在数组中出现次数大于 ⌊n/2⌋ 的元素。
 * 
 * 记忆口诀：
 * "分治递归树高log，每层统计复杂度n，总体nlogn要记牢"
 * "投票算法最优雅，一次遍历O(n)佳，空间常数无可挑"
 * "随机选择碰运气，期望常数很神奇，概率分析要仔细"
 */
public class MajorityElementSolutions {
    
    /**
     * 解法1：分治法 (Divide and Conquer)
     * 
     * 算法思想：
     * 1. 分解：将数组分成两半
     * 2. 递归求解：分别找到左右两部分的候选多数元素
     * 3. 合并：统计两个候选元素在整个数组中的出现次数，返回次数更多的元素
     * 
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(log n)
     * 
     * 记忆口诀："分治递归树高log，每层统计复杂度n，总体nlogn要记牢"
     */
    public int majorityElementDivideConquer(int[] nums) {
        return majorityElementDC(nums, 0, nums.length - 1);
    }
    
    private int majorityElementDC(int[] nums, int begin, int end) {
        // 基础情况：只有一个元素时，该元素就是多数元素
        if (begin == end) {
            return nums[begin];
        }
        
        // 分解：计算中点，避免整数溢出
        int mid = begin + (end - begin) / 2;
        
        // 递归求解左半部分和右半部分的多数元素候选
        int left = majorityElementDC(nums, begin, mid);
        int right = majorityElementDC(nums, mid + 1, end);
        
        // 合并：统计两个候选元素在当前范围内的出现次数
        int leftCnt = countInRange(nums, left, begin, end);
        int rightCnt = countInRange(nums, right, begin, end);
        
        // 返回出现次数更多的元素
        return leftCnt > rightCnt ? left : right;
    }
    
    private int countInRange(int[] nums, int target, int begin, int end) {
        int count = 0;
        for (int i = begin; i <= end; i++) {
            if (nums[i] == target) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * 解法2：Boyer-Moore 投票算法 (最优解)
     * 
     * 算法思想：
     * 1. 维护一个候选元素和计数器
     * 2. 遍历数组，如果当前元素等于候选元素，计数器+1；否则计数器-1
     * 3. 当计数器为0时，更换候选元素
     * 4. 最后的候选元素就是多数元素
     * 
     * 数学原理：
     * 多数元素的出现次数 > n/2，其他所有元素的出现次数之和 < n/2
     * 因此多数元素在"投票"过程中一定能够胜出
     * 
     * 时间复杂度：O(n) - 只需要一次遍历
     * 空间复杂度：O(1) - 只使用常数额外空间
     * 
     * 记忆口诀："投票算法最优雅，一次遍历O(n)佳，空间常数无可挑"
     * 
     * 证明：
     * 设多数元素为 x，出现次数为 k > n/2
     * 其他元素总出现次数为 n-k < n/2
     * 在最坏情况下，x 的每次出现都被其他元素抵消一次
     * 但由于 k > n-k，最终 x 一定会剩余 k-(n-k) = 2k-n > 0 次
     * 
     * 重要边界条件分析：
     * - 当众数恰好比n/2多1个时：仍然可以胜出（如n=7,众数4次 vs 其他3次）
     * - 当众数恰好等于n/2个时：不满足众数定义，算法行为不确定
     * - 算法的数学保证基于众数出现次数严格大于n/2，这也是题目的前提条件
     */
    public int majorityElementBoyerMoore(int[] nums) {
        int candidate = nums[0];  // 候选多数元素
        int count = 1;            // 计数器
        
        // 第一阶段：寻找候选元素
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == candidate) {
                count++;          // 支持票+1
            } else {
                count--;          // 反对票+1
                if (count == 0) { // 票数归零，更换候选人
                    // 关键理解：为什么选择nums[i]而不是nums[i+1]？
                    // 1. nums[i]正是导致旧候选人被完全抵消的元素，它有潜力成为新候选人
                    // 2. 不能跳过nums[i]，每个元素都必须参与投票过程
                    // 3. count==0意味着前面元素已相互抵消，从nums[i]重新开始计票合理
                    candidate = nums[i];
                    count = 1;
                }
            }
        }
        
        // 由于题目保证一定存在多数元素，所以不需要第二阶段验证
        // 如果不保证存在多数元素，需要再次遍历验证候选元素的出现次数
        return candidate;
    }
    
    /**
     * 解法3：随机化算法 (Randomized Algorithm)
     * 
     * 算法思想：
     * 1. 随机选择数组中的一个元素
     * 2. 统计该元素在数组中的出现次数
     * 3. 如果出现次数 > n/2，返回该元素；否则重复步骤1
     * 
     * 概率分析：
     * 多数元素占数组的比例 > 1/2
     * 因此每次随机选择，有超过 1/2 的概率选中多数元素
     * 期望尝试次数 < 2 次
     * 
     * 时间复杂度：期望 O(n) - 每次尝试需要 O(n) 时间统计，期望尝试次数为常数
     * 空间复杂度：O(1)
     * 
     * 记忆口诀："随机选择碰运气，期望常数很神奇，概率分析要仔细"
     * 
     * 数学证明：
     * 设多数元素出现次数为 k，数组长度为 n，则 k > n/2
     * 随机选择一个元素，选中多数元素的概率 P = k/n > 1/2
     * 期望尝试次数 E = 1/P < 1/(1/2) = 2
     * 因此期望时间复杂度为 O(n)
     */
    public int majorityElementRandomized(int[] nums) {
        Random random = new Random();
        int n = nums.length;
        
        while (true) {
            // 随机选择一个元素
            int candidate = nums[random.nextInt(n)];
            
            // 统计该元素的出现次数
            int count = 0;
            for (int num : nums) {
                if (num == candidate) {
                    count++;
                }
            }
            
            // 如果是多数元素，返回
            if (count > n / 2) {
                return candidate;
            }
            // 否则继续随机选择
        }
    }
    
    /**
     * 解法4：哈希表统计法
     * 
     * 算法思想：
     * 使用哈希表统计每个元素的出现次数，返回出现次数 > n/2 的元素
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int majorityElementHashMap(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int n = nums.length;
        
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
            if (countMap.get(num) > n / 2) {
                return num;
            }
        }
        
        // 题目保证存在多数元素，不会执行到这里
        return -1;
    }
    
    /**
     * 解法5：排序法
     * 
     * 算法思想：
     * 将数组排序后，多数元素一定会占据中间位置
     * 
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(1) 或 O(n)，取决于排序算法
     */
    public int majorityElementSorting(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
    
    /**
     * 演示和测试所有算法
     */
    public static void main(String[] args) {
        MajorityElementSolutions solutions = new MajorityElementSolutions();
        
        System.out.println("🎯 多数元素问题完整解决方案演示");
        System.out.println(repeat("=", 50));
        
        // 测试用例
        int[][] testCases = {
            {3, 2, 3},
            {2, 2, 1, 1, 1, 2, 2},
            {1},
            {1, 1, 2},
            {-1, -1, 0}
        };
        
        String[] descriptions = {
            "简单情况：[3,2,3]",
            "标准情况：[2,2,1,1,1,2,2]", 
            "单元素：[1]",
            "刚好超过一半：[1,1,2]",
            "包含负数：[-1,-1,0]"
        };
        
        // 测试所有算法的正确性
        System.out.println("\n📋 算法正确性验证");
        System.out.println(repeat("-", 30));
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            String desc = descriptions[i];
            
            System.out.println("\n测试用例: " + desc);
            System.out.print("数组: [");
            for (int j = 0; j < nums.length; j++) {
                System.out.print(nums[j]);
                if (j < nums.length - 1) System.out.print(", ");
            }
            System.out.println("]");
            
            int result1 = solutions.majorityElementDivideConquer(nums);
            int result2 = solutions.majorityElementBoyerMoore(nums);
            int result3 = solutions.majorityElementRandomized(nums);
            int result4 = solutions.majorityElementHashMap(nums);
            int result5 = solutions.majorityElementSorting(nums.clone());
            
            System.out.println("分治法结果: " + result1);
            System.out.println("Boyer-Moore: " + result2);
            System.out.println("随机化算法: " + result3);
            System.out.println("哈希表算法: " + result4);
            System.out.println("排序算法: " + result5);
            
            // 验证结果一致性
            boolean consistent = (result1 == result2) && (result2 == result3) && 
                               (result3 == result4) && (result4 == result5);
            System.out.println("结果一致性: " + (consistent ? "✅ 通过" : "❌ 失败"));
        }
        
        // 性能对比测试
        System.out.println("\n\n⚡ 性能对比测试");
        System.out.println(repeat("-", 30));
        
        performanceComparison(solutions);
        
        // 算法特点总结
        System.out.println("\n\n📊 算法特点总结");
        System.out.println(repeat("-", 30));
        
        printAlgorithmSummary();
        
        // 记忆口诀
        System.out.println("\n\n🎯 记忆口诀");
        System.out.println(repeat("-", 30));
        
        printMemoryTips();
    }
    
    /**
     * 性能对比测试
     */
    private static void performanceComparison(MajorityElementSolutions solutions) {
        int[] sizes = {1000, 10000, 50000};
        
        for (int size : sizes) {
            System.out.println("\n数组大小: " + size + " 个元素");
            
            // 创建测试数组
            int[] testArray = createTestArray(size, 42);
            
            // Boyer-Moore (最优)
            long start = System.nanoTime();
            int result1 = solutions.majorityElementBoyerMoore(testArray);
            long time1 = System.nanoTime() - start;
            System.out.printf("Boyer-Moore:  %2dms (结果: %d)\n", time1/1_000_000, result1);
            
            // 哈希表
            start = System.nanoTime();
            int result2 = solutions.majorityElementHashMap(testArray);
            long time2 = System.nanoTime() - start;
            System.out.printf("哈希表:      %2dms (结果: %d)\n", time2/1_000_000, result2);
            
            // 随机化
            start = System.nanoTime();
            int result3 = solutions.majorityElementRandomized(testArray);
            long time3 = System.nanoTime() - start;
            System.out.printf("随机化:      %2dms (结果: %d)\n", time3/1_000_000, result3);
            
            // 排序
            start = System.nanoTime();
            int result4 = solutions.majorityElementSorting(testArray.clone());
            long time4 = System.nanoTime() - start;
            System.out.printf("排序:        %2dms (结果: %d)\n", time4/1_000_000, result4);
            
            // 分治 (较小数据量)
            if (size <= 10000) {
                start = System.nanoTime();
                int result5 = solutions.majorityElementDivideConquer(testArray);
                long time5 = System.nanoTime() - start;
                System.out.printf("分治:        %2dms (结果: %d)\n", time5/1_000_000, result5);
            }
        }
    }
    
    /**
     * 创建测试数组
     */
    private static int[] createTestArray(int size, int majorityElement) {
        int[] array = new int[size];
        int majorityCount = size / 2 + 1;
        
        // 填充多数元素
        for (int i = 0; i < majorityCount; i++) {
            array[i] = majorityElement;
        }
        
        // 填充其他元素
        for (int i = majorityCount; i < size; i++) {
            array[i] = majorityElement + 1 + (i % 100);
        }
        
        // 简单打乱
        java.util.Random random = new java.util.Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        
        return array;
    }
    
    /**
     * 打印算法特点总结
     */
    private static void printAlgorithmSummary() {
        System.out.println("┌─────────────┬─────────────┬─────────────┬─────────────┬─────────────┐");
        System.out.println("│    算法     │ 时间复杂度  │ 空间复杂度  │    优点     │    缺点     │");
        System.out.println("├─────────────┼─────────────┼─────────────┼─────────────┼─────────────┤");
        System.out.println("│ Boyer-Moore │    O(n)     │    O(1)     │ 最优解法    │ 理解稍难    │");
        System.out.println("│   哈希表    │    O(n)     │    O(n)     │ 直观易懂    │ 需要空间    │");
        System.out.println("│   随机化    │  期望O(n)   │    O(1)     │ 期望性能好  │ 不够稳定    │");
        System.out.println("│   排序法    │  O(n log n) │    O(1)     │ 简单直接    │ 时间较长    │");
        System.out.println("│   分治法    │  O(n log n) │  O(log n)   │ 教学价值高  │ 效率不优    │");
        System.out.println("└─────────────┴─────────────┴─────────────┴─────────────┴─────────────┘");
        
        System.out.println("\n🏆 推荐排序:");
        System.out.println("1. Boyer-Moore 投票算法 (首选)");
        System.out.println("2. 哈希表统计法 (备选)");
        System.out.println("3. 随机化算法 (特殊场景)");
        System.out.println("4. 排序法 (简单粗暴)");
        System.out.println("5. 分治法 (学习用途)");
    }
    
    /**
     * 打印记忆口诀
     */
    private static void printMemoryTips() {
        System.out.println("🎵 分治法: \"分治递归树高log，每层统计复杂度n，总体nlogn要记牢\"");
        System.out.println("🎵 Boyer-Moore: \"投票算法最优雅，一次遍历O(n)佳，空间常数无可挑\"");
        System.out.println("🎵 随机化: \"随机选择碰运气，期望常数很神奇，概率分析要仔细\"");
        System.out.println("🎵 哈希表: \"哈希统计最直观，空间换时间很划算，O(n)复杂度要记全\"");
        System.out.println("🎵 排序法: \"排序中位必多数，nlogn时间要付出，简单粗暴最好懂\"");
        
        System.out.println("\n💡 核心记忆点:");
        System.out.println("• Boyer-Moore = 投票机制，候选人PK");
        System.out.println("• 分治法 = 左右分治，统计合并");
        System.out.println("• 随机化 = 碰运气选择，期望很好");
        System.out.println("• 哈希表 = 直接统计，空间换时间");
        System.out.println("• 排序法 = 排序取中位，简单粗暴");
    }
    
    /**
     * 字符串重复方法 (兼容旧版本Java)
     */
    private static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}

/**
 * 复杂度对比总结：
 * 
 * | 算法 | 时间复杂度 | 空间复杂度 | 优缺点 |
 * |------|------------|------------|--------|
 * | 分治法 | O(n log n) | O(log n) | 思路清晰，教学价值高，但效率不是最优 |
 * | Boyer-Moore | O(n) | O(1) | 最优解，一次遍历，常数空间 |
 * | 随机化 | 期望O(n) | O(1) | 期望性能好，但最坏情况可能很差 |
 * | 哈希表 | O(n) | O(n) | 直观易懂，但需要额外空间 |
 * | 排序法 | O(n log n) | O(1) | 简单直接，但时间复杂度较高 |
 * 
 * 推荐使用 Boyer-Moore 投票算法，它是时间和空间复杂度都最优的解法。
 */