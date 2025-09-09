package algorithm.selectedtopics.leetcode.missingranges;

import java.util.Arrays;
import java.util.List;

/**
 * 算法对比演示程序
 *
 * 对比两种 Missing Ranges 算法的实现：
 * 1. findMissingRanges - 原始实现（基于索引遍历）
 * 2. findMissingRangesV2 - 优化实现（基于C++版本翻译，动态更新指针）
 */
public class AlgorithmComparison {

    public static void main(String[] args) {
        MissingRangesProblem solution = new MissingRangesProblem();

        System.out.println("=== Missing Ranges 算法对比演示 ===\n");

        // 测试用例
        TestCase[] testCases = {
                new TestCase("经典示例", new int[]{0, 1, 3, 50, 75}, 0, 99),
                new TestCase("空数组", new int[]{}, 1, 10),
                new TestCase("单元素", new int[]{5}, 1, 10),
                new TestCase("连续数组", new int[]{1, 2, 3, 4, 5}, 1, 5),
                new TestCase("负数范围", new int[]{-2, 0, 2}, -5, 5),
                new TestCase("提前终止", new int[]{1, 3, 5, 7, 10, 15, 20}, 0, 10)
        };

        for (TestCase testCase : testCases) {
            System.out.println("📋 测试用例: " + testCase.name);
            System.out.println("   输入数组: " + Arrays.toString(testCase.nums));
            System.out.println("   范围: [" + testCase.lower + ", " + testCase.upper + "]");

            // 算法1：原始实现
            long start1 = System.nanoTime();
            List<String> result1 = solution.findMissingRanges(testCase.nums, testCase.lower, testCase.upper);
            long time1 = System.nanoTime() - start1;

            // 算法2：优化实现
            long start2 = System.nanoTime();
            List<String> result2 = solution.findMissingRangesV2(testCase.nums, testCase.lower, testCase.upper);
            long time2 = System.nanoTime() - start2;

            System.out.println("   算法1结果: " + result1);
            System.out.println("   算法2结果: " + result2);
            System.out.println("   结果一致: " + (result1.equals(result2) ? "✅" : "❌"));
            System.out.println("   算法1耗时: " + time1 + " ns");
            System.out.println("   算法2耗时: " + time2 + " ns");
            System.out.println("   性能提升: " + (time1 > time2 ? "算法2更快" : "算法1更快"));
            System.out.println();
        }

        System.out.println("=== 算法分析总结 ===");
        System.out.println();
        System.out.println("🔍 算法1 (findMissingRanges) - 原始实现:");
        System.out.println("   • 基于索引遍历，需要处理复杂的边界条件");
        System.out.println("   • 需要特殊处理空数组情况");
        System.out.println("   • 逻辑相对复杂，容易出错");
        System.out.println("   • 时间复杂度: O(n)，空间复杂度: O(1)");
        System.out.println();
        System.out.println("🚀 算法2 (findMissingRangesV2) - 优化实现:");
        System.out.println("   • 基于动态更新lower指针，逻辑更清晰");
        System.out.println("   • 自然处理空数组，无需特殊判断");
        System.out.println("   • 提前终止优化：遇到upper时直接返回");
        System.out.println("   • 代码更简洁，不容易出错");
        System.out.println("   • 时间复杂度: O(n)，空间复杂度: O(1)");
        System.out.println();
        System.out.println("✨ 推荐使用算法2，因为:");
        System.out.println("   1. 代码更简洁优雅");
        System.out.println("   2. 逻辑更清晰易懂");
        System.out.println("   3. 不容易出现边界条件错误");
        System.out.println("   4. 有提前终止优化");
        System.out.println("   5. 更符合函数式编程思想");
    }

    static class TestCase {

        String name;
        int[] nums;
        int lower;
        int upper;

        TestCase(String name, int[] nums, int lower, int upper) {
            this.name = name;
            this.nums = nums;
            this.lower = lower;
            this.upper = upper;
        }
    }
}