package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

/**
 * ThreeSum的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class ThreeSumTest {

    private ThreeSum solution;

    @BeforeEach
    void setUp() {
        solution = new ThreeSum();
    }

    @Test
    void testThreeSumBasic() {
        // 基本测试用例
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = solution.threeSum(nums);
        
        assertEquals(2, result.size(), "应该找到2个三元组");
        assertTrue(result.contains(Arrays.asList(-1, -1, 2)), "应该包含[-1, -1, 2]");
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)), "应该包含[-1, 0, 1]");
    }

    @Test
    void testThreeSumNoSolution() {
        // 无解情况
        int[] nums = {0, 1, 1};
        List<List<Integer>> result = solution.threeSum(nums);
        assertEquals(0, result.size(), "应该没有解");
    }

    @Test
    void testThreeSumAllZeros() {
        // 全零情况
        int[] nums = {0, 0, 0};
        List<List<Integer>> result = solution.threeSum(nums);
        assertEquals(1, result.size(), "应该有一个解");
        assertTrue(result.contains(Arrays.asList(0, 0, 0)), "应该包含[0, 0, 0]");
    }

    @Test
    void testThreeSumDuplicates() {
        // 重复元素较多
        int[] nums = {-2, 0, 0, 2, 2};
        List<List<Integer>> result = solution.threeSum(nums);
        assertEquals(1, result.size(), "应该有一个解");
        assertTrue(result.contains(Arrays.asList(-2, 0, 2)), "应该包含[-2, 0, 2]");
    }

    @Test
    void testThreeSumEdgeCases() {
        // 边界情况
        assertNotNull(solution.threeSum(null), "null数组应该返回空列表");
        assertEquals(0, solution.threeSum(null).size(), "null数组应该返回空列表");
        
        assertEquals(0, solution.threeSum(new int[]{1, 2}).size(), "少于3个元素应该返回空列表");
        assertEquals(0, solution.threeSum(new int[0]).size(), "空数组应该返回空列表");
    }

    @Test
    void testThreeSumLargeArray() {
        // 较大数组
        int[] nums = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
        List<List<Integer>> result = solution.threeSum(nums);
        
        // 验证所有解的和都为0
        for (List<Integer> triplet : result) {
            assertEquals(3, triplet.size(), "每个三元组应该有3个元素");
            assertEquals(0, triplet.get(0) + triplet.get(1) + triplet.get(2), "三元组的和应该为0");
        }
    }

    @Test
    void testThreeSumCount() {
        // 测试计数方法
        int[] nums = {-1, 0, 1, 2, -1, -4};
        int count = solution.threeSumCount(nums, 0);
        assertEquals(2, count, "应该有2个和为0的三元组");
        
        // 测试其他目标值
        int[] nums2 = {1, 2, 3, 4, 5, 6};
        int count2 = solution.threeSumCount(nums2, 6); // 1+2+3=6
        assertEquals(1, count2, "应该有1个和为6的三元组");
    }

    @Test
    void testFourSum() {
        // 基本四数之和测试
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;
        List<List<Integer>> result = solution.fourSum(nums, target);
        
        assertTrue(result.size() > 0, "应该找到至少一个四元组");
        
        // 验证所有解的和都等于目标值
        for (List<Integer> quadruplet : result) {
            assertEquals(4, quadruplet.size(), "每个四元组应该有4个元素");
            int sum = quadruplet.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "四元组的和应该等于目标值");
        }
    }

    @Test
    void testFourSumNoSolution() {
        int[] nums = {1, 2, 3, 4};
        int target = 100;
        List<List<Integer>> result = solution.fourSum(nums, target);
        assertEquals(0, result.size(), "应该没有解");
    }

    @Test
    void testFourSumEdgeCases() {
        // 边界情况
        assertEquals(0, solution.fourSum(null, 0).size(), "null数组应该返回空列表");
        assertEquals(0, solution.fourSum(new int[]{1, 2, 3}, 0).size(), "少于4个元素应该返回空列表");
        assertEquals(0, solution.fourSum(new int[0], 0).size(), "空数组应该返回空列表");
    }

    @Test
    void testThreeSumAllPositive() {
        // 全正数，无解
        int[] nums = {1, 2, 3, 4, 5};
        List<List<Integer>> result = solution.threeSum(nums);
        assertEquals(0, result.size(), "全正数应该没有和为0的三元组");
    }

    @Test
    void testThreeSumAllNegative() {
        // 全负数，无解
        int[] nums = {-1, -2, -3, -4, -5};
        List<List<Integer>> result = solution.threeSum(nums);
        assertEquals(0, result.size(), "全负数应该没有和为0的三元组");
    }

    @Test
    void testThreeSumMixedNumbers() {
        // 混合正负数
        int[] nums = {-3, -1, 0, 2, 4, 5};
        List<List<Integer>> result = solution.threeSum(nums);
        
        // 验证结果
        for (List<Integer> triplet : result) {
            assertEquals(0, triplet.get(0) + triplet.get(1) + triplet.get(2), "三元组的和应该为0");
            assertTrue(triplet.get(0) <= triplet.get(1), "结果应该是有序的");
            assertTrue(triplet.get(1) <= triplet.get(2), "结果应该是有序的");
        }
    }
}