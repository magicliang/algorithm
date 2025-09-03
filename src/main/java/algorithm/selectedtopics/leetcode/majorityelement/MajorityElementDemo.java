package algorithm.selectedtopics.leetcode.majorityelement;

/**
 * 多数元素问题演示程序
 * 展示所有算法的运行效果和性能对比
 */
public class MajorityElementDemo {
    
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