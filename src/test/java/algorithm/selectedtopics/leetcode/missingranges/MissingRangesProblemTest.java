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
}