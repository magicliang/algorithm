package algorithm.majorityElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 多数元素问题解法的完整测试套件
 * 
 * 测试覆盖：
 * 1. 基本功能测试
 * 2. 边界条件测试
 * 3. 性能测试
 * 4. 压力测试
 */
@DisplayName("多数元素问题解法测试")
class MajorityElementSolutionsTest {
    
    private MajorityElementSolutions solutions;
    
    @BeforeEach
    void setUp() {
        solutions = new MajorityElementSolutions();
    }
    
    /**
     * 提供测试数据
     */
    static Stream<Arguments> testCases() {
        return Stream.of(
            // 基本测试用例
            Arguments.of(new int[]{3, 2, 3}, 3, "简单情况：[3,2,3]"),
            Arguments.of(new int[]{2, 2, 1, 1, 1, 2, 2}, 2, "标准情况：[2,2,1,1,1,2,2]"),
            
            // 边界情况
            Arguments.of(new int[]{1}, 1, "单元素数组：[1]"),
            Arguments.of(new int[]{1, 1}, 1, "两元素相同：[1,1]"),
            Arguments.of(new int[]{1, 2, 1}, 1, "三元素：[1,2,1]"),
            
            // 多数元素刚好超过一半
            Arguments.of(new int[]{1, 1, 2}, 1, "刚好超过一半：[1,1,2]"),
            Arguments.of(new int[]{1, 1, 1, 2, 2}, 1, "5个元素，3个多数：[1,1,1,2,2]"),
            
            // 多数元素占绝大多数
            Arguments.of(new int[]{1, 1, 1, 1, 2}, 1, "绝大多数：[1,1,1,1,2]"),
            Arguments.of(new int[]{5, 5, 5, 5, 5, 1, 2, 3}, 5, "8个元素，5个多数：[5,5,5,5,5,1,2,3]"),
            
            // 负数测试
            Arguments.of(new int[]{-1, -1, 0}, -1, "包含负数：[-1,-1,0]"),
            Arguments.of(new int[]{-2, -2, -2, 1, 1}, -2, "负数多数：[-2,-2,-2,1,1]"),
            
            // 较大数值
            Arguments.of(new int[]{1000000, 1000000, 999999}, 1000000, "大数值：[1000000,1000000,999999]")
        );
    }
    
    @Nested
    @DisplayName("分治法测试")
    class DivideConquerTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.majorityElement.MajorityElementSolutionsTest#testCases")
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
        @MethodSource("algorithm.majorityElement.MajorityElementSolutionsTest#testCases")
        @DisplayName("Boyer-Moore参数化测试")
        void testBoyerMoore(int[] nums, int expected, String description) {
            int result = solutions.majorityElementBoyerMoore(nums);
            assertEquals(expected, result, "Boyer-Moore算法失败：" + description);
        }
        
        @Test
        @DisplayName("Boyer-Moore性能测试")
        void testBoyerMoorePerformance() {
            int[] largeArray = createLargeArray(100000, 42);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementBoyerMoore(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(42, result);
            System.out.println("Boyer-Moore处理100000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
        
        @Test
        @DisplayName("Boyer-Moore算法步骤验证")
        void testBoyerMooreSteps() {
            // 手动验证算法步骤
            int[] nums = {2, 2, 1, 1, 1, 2, 2};
            int result = solutions.majorityElementBoyerMoore(nums);
            assertEquals(2, result);
            
            // 验证边界情况：候选元素变化
            int[] nums2 = {1, 2, 2, 2, 3};
            int result2 = solutions.majorityElementBoyerMoore(nums2);
            assertEquals(2, result2);
        }
    }
    
    @Nested
    @DisplayName("随机化算法测试")
    class RandomizedTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.majorityElement.MajorityElementSolutionsTest#testCases")
        @DisplayName("随机化算法参数化测试")
        void testRandomized(int[] nums, int expected, String description) {
            int result = solutions.majorityElementRandomized(nums);
            assertEquals(expected, result, "随机化算法失败：" + description);
        }
        
        @Test
        @DisplayName("随机化算法多次运行测试")
        void testRandomizedMultipleRuns() {
            int[] nums = {3, 2, 3};
            
            // 运行多次确保结果一致
            for (int i = 0; i < 10; i++) {
                int result = solutions.majorityElementRandomized(nums);
                assertEquals(3, result, "第" + (i+1) + "次运行失败");
            }
        }
        
        @Test
        @DisplayName("随机化算法性能测试")
        void testRandomizedPerformance() {
            int[] largeArray = createLargeArray(50000, 99);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementRandomized(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(99, result);
            System.out.println("随机化算法处理50000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
    }
    
    @Nested
    @DisplayName("哈希表算法测试")
    class HashMapTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.majorityElement.MajorityElementSolutionsTest#testCases")
        @DisplayName("哈希表算法参数化测试")
        void testHashMap(int[] nums, int expected, String description) {
            int result = solutions.majorityElementHashMap(nums);
            assertEquals(expected, result, "哈希表算法失败：" + description);
        }
        
        @Test
        @DisplayName("哈希表算法性能测试")
        void testHashMapPerformance() {
            int[] largeArray = createLargeArray(100000, 777);
            
            long startTime = System.nanoTime();
            int result = solutions.majorityElementHashMap(largeArray);
            long endTime = System.nanoTime();
            
            assertEquals(777, result);
            System.out.println("哈希表算法处理100000元素耗时: " + (endTime - startTime) / 1_000_000 + "ms");
        }
    }
    
    @Nested
    @DisplayName("排序算法测试")
    class SortingTest {
        
        @ParameterizedTest(name = "{2}")
        @MethodSource("algorithm.majorityElement.MajorityElementSolutionsTest#testCases")
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
    class ComparisonTest {
        
        @Test
        @DisplayName("所有算法结果一致性测试")
        void testAllAlgorithmsConsistency() {
            int[] testArray = {1, 2, 2, 3, 2, 2, 4, 2};
            
            int result1 = solutions.majorityElementDivideConquer(testArray);
            int result2 = solutions.majorityElementBoyerMoore(testArray);
            int result3 = solutions.majorityElementRandomized(testArray);
            int result4 = solutions.majorityElementHashMap(testArray);
            int result5 = solutions.majorityElementSorting(testArray.clone());
            
            assertEquals(result1, result2, "分治法与Boyer-Moore结果不一致");
            assertEquals(result2, result3, "Boyer-Moore与随机化结果不一致");
            assertEquals(result3, result4, "随机化与哈希表结果不一致");
            assertEquals(result4, result5, "哈希表与排序结果不一致");
            
            assertEquals(2, result1, "期望结果应为2");
        }
        
        @Test
        @DisplayName("大数据量性能对比")
        void testPerformanceComparison() {
            int[] largeArray = createLargeArray(50000, 12345);
            
            System.out.println("\n=== 性能对比测试 (50000个元素) ===");
            
            // Boyer-Moore (最优)
            long start = System.nanoTime();
            int result1 = solutions.majorityElementBoyerMoore(largeArray);
            long time1 = System.nanoTime() - start;
            System.out.println("Boyer-Moore: " + time1/1_000_000 + "ms");
            
            // 哈希表
            start = System.nanoTime();
            int result2 = solutions.majorityElementHashMap(largeArray);
            long time2 = System.nanoTime() - start;
            System.out.println("哈希表: " + time2/1_000_000 + "ms");
            
            // 随机化
            start = System.nanoTime();
            int result3 = solutions.majorityElementRandomized(largeArray);
            long time3 = System.nanoTime() - start;
            System.out.println("随机化: " + time3/1_000_000 + "ms");
            
            // 排序
            start = System.nanoTime();
            int result4 = solutions.majorityElementSorting(largeArray.clone());
            long time4 = System.nanoTime() - start;
            System.out.println("排序: " + time4/1_000_000 + "ms");
            
            // 分治 (最慢，数据量小一些)
            int[] smallerArray = createLargeArray(10000, 12345);
            start = System.nanoTime();
            int result5 = solutions.majorityElementDivideConquer(smallerArray);
            long time5 = System.nanoTime() - start;
            System.out.println("分治(10000元素): " + time5/1_000_000 + "ms");
            
            // 验证结果一致性
            assertEquals(12345, result1);
            assertEquals(12345, result2);
            assertEquals(12345, result3);
            assertEquals(12345, result4);
            assertEquals(12345, result5);
            
            // Boyer-Moore应该是最快的
            assertTrue(time1 <= time2, "Boyer-Moore应该不慢于哈希表");
        }
    }
    
    @Nested
    @DisplayName("边界和异常测试")
    class EdgeCaseTest {
        
        @Test
        @DisplayName("最小数组测试")
        void testMinimumArray() {
            int[] singleElement = {42};
            
            assertEquals(42, solutions.majorityElementDivideConquer(singleElement));
            assertEquals(42, solutions.majorityElementBoyerMoore(singleElement));
            assertEquals(42, solutions.majorityElementRandomized(singleElement));
            assertEquals(42, solutions.majorityElementHashMap(singleElement));
            assertEquals(42, solutions.majorityElementSorting(singleElement));
        }
        
        @Test
        @DisplayName("极值测试")
        void testExtremeValues() {
            int[] extremeArray = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
            
            assertEquals(Integer.MAX_VALUE, solutions.majorityElementBoyerMoore(extremeArray));
            assertEquals(Integer.MAX_VALUE, solutions.majorityElementHashMap(extremeArray));
        }
        
        @Test
        @DisplayName("重复元素测试")
        void testAllSameElements() {
            int[] allSame = {7, 7, 7, 7, 7};
            
            assertEquals(7, solutions.majorityElementBoyerMoore(allSame));
            assertEquals(7, solutions.majorityElementDivideConquer(allSame));
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
            array[i] = majorityElement + 1 + (i % 100); // 避免与多数元素相同
        }
        
        // 打乱数组
        shuffleArray(array);
        
        return array;
    }
    
    /**
     * 打乱数组的辅助方法
     */
    private void shuffleArray(int[] array) {
        java.util.Random random = new java.util.Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}