package algorithm.selectedtopics.leetcode.majorityelement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * MajorityElementSolutions 类的完整测试用例
 * 
 * 测试覆盖范围：
 * - 分治法 (Divide and Conquer)
 * - Boyer-Moore 投票算法
 * - 随机化算法
 * - 哈希表统计法
 * - 排序法
 * 
 * @author magicliang
 * @date 2025-09-03
 */
@DisplayName("多数元素问题测试")
class MajorityElementSolutionsTest {

    private MajorityElementSolutions solutions;

    @BeforeEach
    void setUp() {
        solutions = new MajorityElementSolutions();
    }

    /**
     * 提供测试用例数据
     */
    static Stream<Arguments> testCases() {
        return Stream.of(
            // 基本测试用例
            Arguments.of(new int[]{3, 2, 3}, 3, "基本用例：[3,2,3]"),
            Arguments.of(new int[]{2, 2, 1, 1, 1, 2, 2}, 2, "基本用例：[2,2,1,1,1,2,2]"),
            
            // 边界情况
            Arguments.of(new int[]{1}, 1, "单元素：[1]"),
            Arguments.of(new int[]{1, 1}, 1, "两个相同元素：[1,1]"),
            Arguments.of(new int[]{1, 2, 1}, 1, "三元素：[1,2,1]"),
            
            // 多数元素在不同位置
            Arguments.of(new int[]{5, 5, 5, 1, 2}, 5, "多数元素在前：[5,5,5,1,2]"),
            Arguments.of(new int[]{1, 2, 5, 5, 5}, 5, "多数元素在后：[1,2,5,5,5]"),
            Arguments.of(new int[]{1, 5, 2, 5, 5}, 5, "多数元素分散：[1,5,2,5,5]"),
            
            // 负数测试
            Arguments.of(new int[]{-1, -1, -1, 1, 1}, -1, "负数多数：[-1,-1,-1,1,1]"),
            Arguments.of(new int[]{-2, -2, -2, 1, 1}, -2, "负数多数：[-2,-2,-2,1,1]"),
            
            // 较大数值
            Arguments.of(new int[]{1000000, 1000000, 999999}, 1000000, "大数值：[1000000,1000000,999999]")
        );
    }

    @Nested
    @DisplayName("分治法测试")
    class DivideConquerTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.selectedtopics.leetcode.majorityelement.MajorityElementSolutionsTest#testCases")
        @DisplayName("分治法参数化测试")
        void testDivideConquer(int[] nums, int expected, String description) {
            int result = solutions.majorityElementDivideConquer(nums);
            assertEquals(expected, result, "分治法失败：" + description);
        }
        
        @Test
        @DisplayName("分治法性能测试")
        void testDivideConquerPerformance() {
            // 创建较大的测试数组
            int[] largeArray = createLargeArray(10000, 1);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementDivideConquer(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(1, result);
            System.out.println("分治法处理10000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
    }

    @Nested
    @DisplayName("Boyer-Moore投票算法测试")
    class BoyerMooreTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.selectedtopics.leetcode.majorityelement.MajorityElementSolutionsTest#testCases")
        @DisplayName("Boyer-Moore算法参数化测试")
        void testBoyerMoore(int[] nums, int expected, String description) {
            int result = solutions.majorityElementBoyerMoore(nums);
            assertEquals(expected, result, "Boyer-Moore算法失败：" + description);
        }
        
        @Test
        @DisplayName("Boyer-Moore算法性能测试")
        void testBoyerMoorePerformance() {
            int[] largeArray = createLargeArray(1000000, 42);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementBoyerMoore(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(42, result);
            System.out.println("Boyer-Moore算法处理1000000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
        
        @Test
        @DisplayName("Boyer-Moore算法投票过程测试")
        void testBoyerMooreVotingProcess() {
            // 测试投票过程中候选人变化的情况
            int[] nums = {1, 2, 1, 3, 1, 1, 4, 1};
            int result = solutions.majorityElementBoyerMoore(nums);
            assertEquals(1, result, "复杂投票过程应该正确识别多数元素");
        }
    }

    @Nested
    @DisplayName("随机化算法测试")
    class RandomizedTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.selectedtopics.leetcode.majorityelement.MajorityElementSolutionsTest#testCases")
        @DisplayName("随机化算法参数化测试")
        void testRandomized(int[] nums, int expected, String description) {
            int result = solutions.majorityElementRandomized(nums);
            assertEquals(expected, result, "随机化算法失败：" + description);
        }
        
        @Test
        @DisplayName("随机化算法多次运行测试")
        void testRandomizedMultipleRuns() {
            int[] nums = {2, 2, 1, 1, 1, 2, 2};
            
            // 运行多次确保算法稳定性
            for (int i = 0; i < 10; i++) {
                int result = solutions.majorityElementRandomized(nums);
                assertEquals(2, result, "第" + (i + 1) + "次运行失败");
            }
        }
    }

    @Nested
    @DisplayName("哈希表统计法测试")
    class HashMapTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.selectedtopics.leetcode.majorityelement.MajorityElementSolutionsTest#testCases")
        @DisplayName("哈希表统计法参数化测试")
        void testHashMap(int[] nums, int expected, String description) {
            int result = solutions.majorityElementHashMap(nums);
            assertEquals(expected, result, "哈希表统计法失败：" + description);
        }
        
        @Test
        @DisplayName("哈希表统计法性能测试")
        void testHashMapPerformance() {
            int[] largeArray = createLargeArray(100000, 999);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementHashMap(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(999, result);
            System.out.println("哈希表统计法处理100000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
    }

    @Nested
    @DisplayName("排序算法测试")
    class SortingTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.selectedtopics.leetcode.majorityelement.MajorityElementSolutionsTest#testCases")
        @DisplayName("排序算法参数化测试")
        void testSorting(int[] nums, int expected, String description) {
            int result = solutions.majorityElementSorting(nums.clone()); // 使用clone避免修改原数组
            assertEquals(expected, result, "排序算法失败：" + description);
        }
        
        @Test
        @DisplayName("排序算法性能测试")
        void testSortingPerformance() {
            int[] largeArray = createLargeArray(100000, 888);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementSorting(largeArray.clone());
            long endTime = System.nanoTime();
            
            assertEquals(888, result);
            System.out.println("排序算法处理100000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
    }

    @Nested
    @DisplayName("算法对比测试")
    class AlgorithmComparisonTest {
        
        @Test
        @DisplayName("所有算法结果一致性测试")
        void testAllAlgorithmsConsistency() {
            int[] testArray = {3, 3, 4, 2, 4, 4, 2, 4, 4};
            
            int result1 = solutions.majorityElementDivideConquer(testArray);
            int result2 = solutions.majorityElementBoyerMoore(testArray);
            int result3 = solutions.majorityElementRandomized(testArray);
            int result4 = solutions.majorityElementHashMap(testArray);
            int result5 = solutions.majorityElementSorting(testArray.clone());
            
            assertEquals(result1, result2, "分治法与Boyer-Moore结果不一致");
            assertEquals(result2, result3, "Boyer-Moore与随机化算法结果不一致");
            assertEquals(result3, result4, "随机化算法与哈希表法结果不一致");
            assertEquals(result4, result5, "哈希表法与排序法结果不一致");
            
            assertEquals(4, result1, "多数元素应该是4");
        }
        
        @Test
        @DisplayName("性能对比测试")
        void testPerformanceComparison() {
            int[] largeArray = createLargeArray(50000, 123);
            
            // Boyer-Moore算法（最优）
            long start = System.nanoTime();
            solutions.majorityElementBoyerMoore(largeArray);
            long boyerMooreTime = System.nanoTime() - start;
            
            // 哈希表法
            start = System.nanoTime();
            solutions.majorityElementHashMap(largeArray);
            long hashMapTime = System.nanoTime() - start;
            
            // 排序法
            start = System.nanoTime();
            solutions.majorityElementSorting(largeArray.clone());
            long sortingTime = System.nanoTime() - start;
            
            System.out.println("性能对比（50000元素）：");
            System.out.println("Boyer-Moore: " + boyerMooreTime / 1_000_000 + "ms");
            System.out.println("哈希表法: " + hashMapTime / 1_000_000 + "ms");
            System.out.println("排序法: " + sortingTime / 1_000_000 + "ms");
            
            // Boyer-Moore应该是最快的
            assertTrue(boyerMooreTime <= hashMapTime, "Boyer-Moore应该不慢于哈希表法");
        }
    }

    /**
     * 创建大型测试数组的辅助方法
     * @param size 数组大小
     * @param majorityElement 多数元素
     * @return 测试数组
     */
    private int[] createLargeArray(int size, int majorityElement) {
        int[] array = new int[size];
        int majorityCount = size / 2 + 1; // 确保是多数元素
        
        // 填充多数元素
        for (int i = 0; i < majorityCount; i++) {
            array[i] = majorityElement;
        }
        
        // 填充其他元素
        for (int i = majorityCount; i < size; i++) {
            array[i] = majorityElement + 1 + (i % 10); // 创建不同的非多数元素
        }
        
        // 简单打乱数组
        for (int i = 0; i < size; i++) {
            int j = (int) (Math.random() * size);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        
        return array;
    }
}