package algorithm.selectedtopics.leetcode.missingranges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * MissingRangesProblem 的 JUnit 测试类
 * 
 * 测试覆盖所有边界情况和核心功能：
 * 1. 空数组情况
 * 2. 单元素数组情况
 * 3. 连续数组情况（无缺失）
 * 4. 常规缺失范围情况
 * 5. 边界值测试
 * 6. 特殊范围测试
 */
@DisplayName("Missing Ranges Problem 测试")
public class MissingRangesProblemTest {

    private MissingRangesProblem solution;

    @BeforeEach
    void setUp() {
        solution = new MissingRangesProblem();
    }

    @Test
    @DisplayName("测试空数组情况 - 应返回整个范围")
    void testEmptyArray() {
        // 测试目的：验证空数组时返回完整的 [lower, upper] 范围
        int[] nums = {};
        int lower = 0;
        int upper = 99;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("0->99");
        
        assertEquals(expected, result, "空数组应该返回整个范围 [0, 99]");
    }

    @Test
    @DisplayName("测试单点范围的空数组")
    void testEmptyArraySinglePoint() {
        // 测试目的：验证当 lower == upper 时空数组的处理
        int[] nums = {};
        int lower = 5;
        int upper = 5;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("5");
        
        assertEquals(expected, result, "空数组且 lower == upper 时应返回单个数字");
    }

    @Test
    @DisplayName("测试经典示例 - LeetCode 原题")
    void testClassicExample() {
        // 测试目的：验证 LeetCode 163 题的经典示例
        // 输入: [0, 1, 3, 50, 75], lower = 0, upper = 99
        // 预期: ["2", "4->49", "51->74", "76->99"]
        int[] nums = {0, 1, 3, 50, 75};
        int lower = 0;
        int upper = 99;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("2", "4->49", "51->74", "76->99");
        
        assertEquals(expected, result, "经典示例测试失败");
    }

    @Test
    @DisplayName("测试单元素数组 - 元素在范围开头")
    void testSingleElementAtStart() {
        // 测试目的：验证单元素数组且元素等于 lower 的情况
        int[] nums = {0};
        int lower = 0;
        int upper = 10;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("1->10");
        
        assertEquals(expected, result, "单元素在范围开头时，应返回后续缺失范围");
    }

    @Test
    @DisplayName("测试单元素数组 - 元素在范围中间")
    void testSingleElementInMiddle() {
        // 测试目的：验证单元素数组且元素在范围中间的情况
        int[] nums = {5};
        int lower = 0;
        int upper = 10;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("0->4", "6->10");
        
        assertEquals(expected, result, "单元素在中间时，应返回前后两个缺失范围");
    }

    @Test
    @DisplayName("测试单元素数组 - 元素在范围末尾")
    void testSingleElementAtEnd() {
        // 测试目的：验证单元素数组且元素等于 upper 的情况
        int[] nums = {10};
        int lower = 0;
        int upper = 10;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("0->9");
        
        assertEquals(expected, result, "单元素在范围末尾时，应返回前面的缺失范围");
    }

    @Test
    @DisplayName("测试连续数组 - 无缺失范围")
    void testConsecutiveArrayNoMissing() {
        // 测试目的：验证完全连续的数组，不应有任何缺失范围
        int[] nums = {1, 2, 3, 4, 5};
        int lower = 1;
        int upper = 5;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList();
        
        assertEquals(expected, result, "连续数组应该没有缺失范围");
    }

    @Test
    @DisplayName("测试连续数组 - 有前后缺失")
    void testConsecutiveArrayWithGaps() {
        // 测试目的：验证连续数组但前后有缺失的情况
        int[] nums = {2, 3, 4};
        int lower = 0;
        int upper = 6;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("0->1", "5->6");
        
        assertEquals(expected, result, "连续数组前后有缺失时应正确识别");
    }

    @Test
    @DisplayName("测试负数范围")
    void testNegativeRange() {
        // 测试目的：验证包含负数的范围处理
        int[] nums = {-2, 0, 2};
        int lower = -5;
        int upper = 5;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("-5->-3", "-1", "1", "3->5");
        
        assertEquals(expected, result, "负数范围应该正确处理");
    }

    @Test
    @DisplayName("测试单点缺失")
    void testSinglePointMissing() {
        // 测试目的：验证只缺失单个数字的情况
        int[] nums = {1, 3};
        int lower = 1;
        int upper = 3;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("2");
        
        assertEquals(expected, result, "单点缺失应该返回单个数字字符串");
    }

    @Test
    @DisplayName("测试边界值 - lower 等于 upper")
    void testBoundaryLowerEqualsUpper() {
        // 测试目的：验证 lower == upper 的边界情况
        int[] nums = {5};
        int lower = 5;
        int upper = 5;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList();
        
        assertEquals(expected, result, "当 lower == upper 且数组包含该值时，应无缺失");
    }

    @Test
    @DisplayName("测试边界值 - lower 等于 upper 但数组不包含")
    void testBoundaryLowerEqualsUpperMissing() {
        // 测试目的：验证 lower == upper 但数组不包含该值的情况
        int[] nums = {}; // 使用空数组，这样就不会有其他元素干扰
        int lower = 5;
        int upper = 5;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("5");
        
        assertEquals(expected, result, "当 lower == upper 且数组不包含该值时，应返回该值");
    }

    @Test
    @DisplayName("测试大范围数组")
    void testLargeRangeArray() {
        // 测试目的：验证较大范围的处理能力
        int[] nums = {0, 100, 200};
        int lower = 0;
        int upper = 300;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("1->99", "101->199", "201->300");
        
        assertEquals(expected, result, "大范围数组应该正确处理");
    }

    @Test
    @DisplayName("测试 format 方法 - 单点格式化")
    void testFormatSinglePoint() {
        // 测试目的：验证 format 方法对单点的格式化
        String result = solution.format(5, 5);
        assertEquals("5", result, "单点应该格式化为单个数字");
    }

    @Test
    @DisplayName("测试 format 方法 - 范围格式化")
    void testFormatRange() {
        // 测试目的：验证 format 方法对范围的格式化
        String result = solution.format(1, 10);
        assertEquals("1->10", result, "范围应该格式化为 'start->end' 形式");
    }

    @Test
    @DisplayName("测试数组首元素大于 lower 的情况")
    void testFirstElementGreaterThanLower() {
        // 测试目的：验证数组第一个元素大于 lower 时的前置缺失处理
        int[] nums = {5, 7, 9};
        int lower = 1;
        int upper = 10;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("1->4", "6", "8", "10");
        
        assertEquals(expected, result, "首元素大于 lower 时应正确处理前置缺失");
    }

    @Test
    @DisplayName("测试数组末元素小于 upper 的情况")
    void testLastElementLessThanUpper() {
        // 测试目的：验证数组最后一个元素小于 upper 时的后置缺失处理
        int[] nums = {1, 3, 5};
        int lower = 1;
        int upper = 10;
        
        List<String> result = solution.findMissingRanges(nums, lower, upper);
        List<String> expected = Arrays.asList("2", "4", "6->10");
        
        assertEquals(expected, result, "末元素小于 upper 时应正确处理后置缺失");
    }

    // ==================== 新算法 findMissingRangesV2 的测试 ====================

    @Test
    @DisplayName("V2算法 - 测试经典示例")
    void testFindMissingRangesV2ClassicExample() {
        // 测试目的：验证新算法对经典示例的处理
        int[] nums = {0, 1, 3, 50, 75};
        int lower = 0;
        int upper = 99;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList("2", "4->49", "51->74", "76->99");
        
        assertEquals(expected, result, "V2算法经典示例测试失败");
    }

    @Test
    @DisplayName("V2算法 - 测试空数组")
    void testFindMissingRangesV2EmptyArray() {
        // 测试目的：验证新算法对空数组的处理
        int[] nums = {};
        int lower = 1;
        int upper = 10;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList("1->10");
        
        assertEquals(expected, result, "V2算法空数组测试失败");
    }

    @Test
    @DisplayName("V2算法 - 测试提前终止优化")
    void testFindMissingRangesV2EarlyTermination() {
        // 测试目的：验证当数组中某个元素等于upper时的提前终止优化
        int[] nums = {1, 3, 5, 7, 10, 15, 20}; // 10 == upper，后面的15,20应该被忽略
        int lower = 0;
        int upper = 10;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList("0", "2", "4", "6", "8->9");
        
        assertEquals(expected, result, "V2算法提前终止优化测试失败");
    }

    @Test
    @DisplayName("V2算法 - 测试单点范围")
    void testFindMissingRangesV2SinglePoint() {
        // 测试目的：验证新算法对单点范围的处理
        int[] nums = {};
        int lower = 5;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList("5");
        
        assertEquals(expected, result, "V2算法单点范围测试失败");
    }

    @Test
    @DisplayName("V2算法 - 测试连续数组无缺失")
    void testFindMissingRangesV2NoMissing() {
        // 测试目的：验证新算法对连续无缺失数组的处理
        int[] nums = {1, 2, 3, 4, 5};
        int lower = 1;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList();
        
        assertEquals(expected, result, "V2算法连续数组无缺失测试失败");
    }

    @Test
    @DisplayName("V2算法 - 测试负数范围")
    void testFindMissingRangesV2NegativeRange() {
        // 测试目的：验证新算法对负数范围的处理
        int[] nums = {-2, 0, 2};
        int lower = -5;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesV2(nums, lower, upper);
        List<String> expected = Arrays.asList("-5->-3", "-1", "1", "3->5");
        
        assertEquals(expected, result, "V2算法负数范围测试失败");
    }

    @Test
    @DisplayName("V2算法 - 对比两种算法结果一致性")
    void testBothAlgorithmsConsistency() {
        // 测试目的：验证两种算法在各种情况下结果的一致性
        int[][] testCases = {
            {},                    // 空数组
            {1},                   // 单元素
            {1, 2, 3},            // 连续数组
            {0, 1, 3, 50, 75},    // 经典示例
            {-2, 0, 2},           // 负数
            {5, 7, 9}             // 有间隔
        };
        
        int[] lowers = {0, 1, 1, 0, -5, 1};
        int[] uppers = {10, 1, 3, 99, 5, 10};
        
        for (int i = 0; i < testCases.length; i++) {
            List<String> result1 = solution.findMissingRanges(testCases[i], lowers[i], uppers[i]);
            List<String> result2 = solution.findMissingRangesV2(testCases[i], lowers[i], uppers[i]);
            
            assertEquals(result1, result2, 
                String.format("测试用例 %d: 两种算法结果不一致\n数组: %s, lower: %d, upper: %d", 
                    i, Arrays.toString(testCases[i]), lowers[i], uppers[i]));
        }
    }

    // ==================== Pretty算法 findMissingRangesPretty 的测试 ====================

    @Test
    @DisplayName("Pretty算法 - 测试经典示例")
    void testFindMissingRangesPrettyClassicExample() {
        // 测试目的：验证Pretty算法对经典示例的处理
        int[] nums = {0, 1, 3, 50, 75};
        int lower = 0;
        int upper = 99;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("2", "4->49", "51->74", "76->99");
        
        assertEquals(expected, result, "Pretty算法经典示例测试失败");
    }

    @Test
    @DisplayName("Pretty算法 - 测试空数组")
    void testFindMissingRangesPrettyEmptyArray() {
        // 测试目的：验证Pretty算法对空数组的处理
        int[] nums = {};
        int lower = 1;
        int upper = 10;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("1->10");
        
        assertEquals(expected, result, "Pretty算法空数组测试失败");
    }

    @Test
    @DisplayName("Pretty算法 - 测试提前终止优化")
    void testFindMissingRangesPrettyEarlyTermination() {
        // 测试目的：验证当数组中某个元素等于upper时的提前终止优化
        int[] nums = {1, 3, 5, 7, 10, 15, 20}; // 10 == upper，后面的15,20应该被忽略
        int lower = 0;
        int upper = 10;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("0", "2", "4", "6", "8->9");
        
        assertEquals(expected, result, "Pretty算法提前终止优化测试失败");
    }

    @Test
    @DisplayName("Pretty算法 - 测试单点范围")
    void testFindMissingRangesPrettySinglePoint() {
        // 测试目的：验证Pretty算法对单点范围的处理
        int[] nums = {};
        int lower = 5;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("5");
        
        assertEquals(expected, result, "Pretty算法单点范围测试失败");
    }

    @Test
    @DisplayName("Pretty算法 - 测试连续数组无缺失")
    void testFindMissingRangesPrettyNoMissing() {
        // 测试目的：验证Pretty算法对连续无缺失数组的处理
        int[] nums = {1, 2, 3, 4, 5};
        int lower = 1;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList();
        
        assertEquals(expected, result, "Pretty算法连续数组无缺失测试失败");
    }

    @Test
    @DisplayName("Pretty算法 - 测试负数范围")
    void testFindMissingRangesPrettyNegativeRange() {
        // 测试目的：验证Pretty算法对负数范围的处理
        int[] nums = {-2, 0, 2};
        int lower = -5;
        int upper = 5;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("-5->-3", "-1", "1", "3->5");
        
        assertEquals(expected, result, "Pretty算法负数范围测试失败");
    }

    @Test
    @DisplayName("三种算法结果一致性测试")
    void testAllThreeAlgorithmsConsistency() {
        // 测试目的：验证三种算法在各种情况下结果的一致性
        int[][] testCases = {
            {},                    // 空数组
            {1},                   // 单元素
            {1, 2, 3},            // 连续数组
            {0, 1, 3, 50, 75},    // 经典示例
            {-2, 0, 2},           // 负数
            {5, 7, 9},            // 有间隔
            {0},                  // 单元素在起始位置
            {10},                 // 单元素在结束位置
            {-10, -5, 0, 5, 10}   // 跨越正负数的大范围
        };
        
        int[] lowers = {0, 1, 1, 0, -5, 1, 0, 0, -15};
        int[] uppers = {10, 1, 3, 99, 5, 10, 10, 10, 15};
        
        for (int i = 0; i < testCases.length; i++) {
            List<String> result1 = solution.findMissingRanges(testCases[i], lowers[i], uppers[i]);
            List<String> result2 = solution.findMissingRangesV2(testCases[i], lowers[i], uppers[i]);
            List<String> result3 = solution.findMissingRangesPretty(testCases[i], lowers[i], uppers[i]);
            
            assertEquals(result1, result2, 
                String.format("测试用例 %d: 算法1和算法2结果不一致\n数组: %s, lower: %d, upper: %d", 
                    i, Arrays.toString(testCases[i]), lowers[i], uppers[i]));
            
            assertEquals(result1, result3, 
                String.format("测试用例 %d: 算法1和Pretty算法结果不一致\n数组: %s, lower: %d, upper: %d", 
                    i, Arrays.toString(testCases[i]), lowers[i], uppers[i]));
            
            assertEquals(result2, result3, 
                String.format("测试用例 %d: 算法2和Pretty算法结果不一致\n数组: %s, lower: %d, upper: %d", 
                    i, Arrays.toString(testCases[i]), lowers[i], uppers[i]));
        }
    }

    // ==================== 边界和特殊情况测试 ====================

    @Test
    @DisplayName("测试极端边界 - Integer最小值和最大值")
    void testExtremeIntegerBounds() {
        // 测试目的：验证算法在极端整数边界下的表现
        // 注意：这里使用相对较小的范围来避免内存问题
        int[] nums = {Integer.MIN_VALUE + 1, 0, Integer.MAX_VALUE - 1};
        int lower = Integer.MIN_VALUE;
        int upper = Integer.MAX_VALUE;
        
        List<String> result1 = solution.findMissingRanges(nums, lower, upper);
        List<String> result2 = solution.findMissingRangesV2(nums, lower, upper);
        List<String> result3 = solution.findMissingRangesPretty(nums, lower, upper);
        
        // 验证三种算法结果一致
        assertEquals(result1, result2, "极端边界测试：算法1和算法2结果不一致");
        assertEquals(result1, result3, "极端边界测试：算法1和Pretty算法结果不一致");
        
        // 验证结果的正确性
        List<String> expected = Arrays.asList(
            String.valueOf(Integer.MIN_VALUE),
            (Integer.MIN_VALUE + 2) + "->" + (-1),
            "1->" + (Integer.MAX_VALUE - 2),
            String.valueOf(Integer.MAX_VALUE)
        );
        assertEquals(expected, result1, "极端边界测试结果不正确");
    }

    @Test
    @DisplayName("测试算法健壮性 - 边界相邻情况")
    void testAlgorithmRobustness() {
        // 测试目的：测试算法在边界相邻情况下的健壮性
        // 使用无重复元素但有特殊边界情况的数组
        int[] nums = {0, 2, 4, 6};  // 每两个数字间隔1
        int lower = 0;
        int upper = 6;
        
        // 所有算法都应该能正确处理
        List<String> result1 = solution.findMissingRanges(nums, lower, upper);
        List<String> result2 = solution.findMissingRangesV2(nums, lower, upper);
        List<String> result3 = solution.findMissingRangesPretty(nums, lower, upper);
        
        // 验证三种算法结果一致
        assertEquals(result1, result2, "边界相邻测试：算法1和算法2结果不一致");
        assertEquals(result1, result3, "边界相邻测试：算法1和Pretty算法结果不一致");
        
        // 验证结果正确性
        List<String> expected = Arrays.asList("1", "3", "5");
        assertEquals(expected, result1, "边界相邻测试结果不正确");
    }

    @Test
    @DisplayName("测试大间隔数组")
    void testLargeGapArray() {
        // 测试目的：验证算法处理大间隔数组的能力
        int[] nums = {1, 1000000, 2000000};
        int lower = 0;
        int upper = 3000000;
        
        List<String> result = solution.findMissingRangesPretty(nums, lower, upper);
        List<String> expected = Arrays.asList("0", "2->999999", "1000001->1999999", "2000001->3000000");
        
        assertEquals(expected, result, "大间隔数组测试失败");
    }

    @Test
    @DisplayName("测试formatRange方法的边界情况")
    void testFormatRangeMethod() {
        // 测试目的：直接测试formatRange方法（通过反射或创建实例）
        // 由于formatRange是private方法，我们通过公共方法间接测试
        
        // 测试单点格式化
        int[] nums = {};
        List<String> result = solution.findMissingRangesPretty(nums, 5, 5);
        assertEquals(Arrays.asList("5"), result, "formatRange单点格式化测试失败");
        
        // 测试范围格式化
        result = solution.findMissingRangesPretty(nums, 1, 10);
        assertEquals(Arrays.asList("1->10"), result, "formatRange范围格式化测试失败");
    }

    @Test
    @DisplayName("性能对比测试 - 小规模数据")
    void testPerformanceComparison() {
        // 测试目的：简单的性能对比测试
        int[] nums = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        int lower = 0;
        int upper = 20;
        
        // 预热JVM
        for (int i = 0; i < 1000; i++) {
            solution.findMissingRanges(nums, lower, upper);
            solution.findMissingRangesV2(nums, lower, upper);
            solution.findMissingRangesPretty(nums, lower, upper);
        }
        
        // 简单的时间测量（仅作为参考）
        long start1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solution.findMissingRanges(nums, lower, upper);
        }
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solution.findMissingRangesV2(nums, lower, upper);
        }
        long time2 = System.nanoTime() - start2;
        
        long start3 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            solution.findMissingRangesPretty(nums, lower, upper);
        }
        long time3 = System.nanoTime() - start3;
        
        // 输出性能信息（不做断言，因为性能可能因环境而异）
        System.out.printf("算法1耗时: %d ns%n", time1);
        System.out.printf("算法2耗时: %d ns%n", time2);
        System.out.printf("Pretty算法耗时: %d ns%n", time3);
        
        // 验证结果一致性
        List<String> result1 = solution.findMissingRanges(nums, lower, upper);
        List<String> result2 = solution.findMissingRangesV2(nums, lower, upper);
        List<String> result3 = solution.findMissingRangesPretty(nums, lower, upper);
        
        assertEquals(result1, result2, "性能测试：算法1和算法2结果不一致");
        assertEquals(result1, result3, "性能测试：算法1和Pretty算法结果不一致");
    }
}