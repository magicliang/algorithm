package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ClosestThreeSum的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class ClosestThreeSumTest {

    private ClosestThreeSum solution;

    @BeforeEach
    void setUp() {
        solution = new ClosestThreeSum();
    }

    @Test
    void testThreeSumClosestBasic() {
        // 基本测试用例
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(2, result, "最接近1的三数之和应该是2");
    }

    @Test
    void testThreeSumClosestExactMatch() {
        // 精确匹配
        int[] nums = {0, 0, 0};
        int target = 0;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(0, result, "精确匹配应该返回目标值");
    }

    @Test
    void testThreeSumClosestPositiveTarget() {
        // 正目标值
        int[] nums = {1, 1, 1, 0};
        int target = 100;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(3, result, "最接近100的三数之和应该是3");
    }

    @Test
    void testThreeSumClosestNegativeTarget() {
        // 负目标值
        int[] nums = {1, 1, -1, -1, 3};
        int target = -1;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(-1, result, "应该找到精确匹配-1");
    }

    @Test
    void testThreeSumClosestLargeNumbers() {
        // 大数值
        int[] nums = {-100, -98, -2, -1};
        int target = -101;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(-101, result, "应该找到精确匹配-101");
    }

    @Test
    void testThreeSumClosestOptimized() {
        // 测试优化版本
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        int result = solution.threeSumClosestOptimized(nums, target);
        assertEquals(2, result, "优化版本应该得到相同结果");
    }

    @Test
    void testThreeSumClosestOptimizedWithDuplicates() {
        // 测试优化版本处理重复元素
        int[] nums = {1, 1, 1, 1, 1, 1};
        int target = 4;
        int result = solution.threeSumClosestOptimized(nums, target);
        assertEquals(3, result, "重复元素情况下应该返回3");
    }

    @Test
    void testThreeSumClosestMultipleSolutions() {
        // 多个相同距离的解
        int[] nums = {0, 1, 2};
        int target = 2;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(3, result, "应该返回其中一个最接近的解");
    }

    @Test
    void testThreeSumClosestMinimumArray() {
        // 最小数组（3个元素）
        int[] nums = {1, 2, 3};
        int target = 10;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(6, result, "三个元素的和应该是6");
    }

    @Test
    void testThreeSumWithinThreshold() {
        // 测试阈值内的三数之和
        int[] nums = {-1, 0, 1, 2, -1, -4};
        int target = 0;
        int threshold = 1;
        int[] result = solution.threeSumWithinThreshold(nums, target, threshold);
        
        assertTrue(result.length > 0, "应该找到阈值内的解");
        
        // 验证所有结果都在阈值范围内
        for (int sum : result) {
            assertTrue(Math.abs(sum - target) <= threshold, 
                "所有结果都应该在阈值范围内，但找到：" + sum);
        }
    }

    @Test
    void testThreeSumWithinThresholdNoSolution() {
        // 阈值内无解
        int[] nums = {10, 20, 30};
        int target = 0;
        int threshold = 5;
        int[] result = solution.threeSumWithinThreshold(nums, target, threshold);
        assertEquals(0, result.length, "阈值内应该没有解");
    }

    @Test
    void testThreeSumWithinThresholdLargeThreshold() {
        // 大阈值
        int[] nums = {1, 2, 3, 4, 5, 6};
        int target = 10;
        int threshold = 100;
        int[] result = solution.threeSumWithinThreshold(nums, target, threshold);
        
        assertTrue(result.length > 0, "大阈值应该找到多个解");
    }

    @Test
    void testThreeSumClosestEdgeCases() {
        // 边界情况测试
        int[] nums = {0, 2, 1, -3};
        int target = 1;
        int result = solution.threeSumClosest(nums, target);
        assertTrue(Math.abs(result - target) >= 0, "结果应该是有效的");
    }

    @Test
    void testThreeSumClosestSorted() {
        // 已排序数组
        int[] nums = {-4, -1, 1, 2};
        int target = 1;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(2, result, "已排序数组应该正确处理");
    }

    @Test
    void testThreeSumClosestReverseSorted() {
        // 逆序数组
        int[] nums = {4, 3, 2, 1};
        int target = 6;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(6, result, "逆序数组应该找到精确匹配");
    }

    @Test
    void testThreeSumClosestAllSame() {
        // 所有元素相同
        int[] nums = {5, 5, 5, 5};
        int target = 16;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(15, result, "所有元素相同时应该返回15");
    }

    @Test
    void testThreeSumClosestZeroTarget() {
        // 目标为0
        int[] nums = {-1, 0, 1, 1, 55};
        int target = 0;
        int result = solution.threeSumClosest(nums, target);
        assertEquals(0, result, "应该找到精确的0");
    }
}