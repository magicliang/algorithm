package algorithm.advanced.backtracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * SubsetSum类的JUnit测试
 * 测试子集和问题的各种实现方法
 */
public class SubsetSumTest {

    private SubsetSum subsetSum;

    @BeforeEach
    void setUp() {
        subsetSum = new SubsetSum();
    }

    // ==================== subsetSumINaive方法测试 ====================

    @Test
    @DisplayName("测试基本的子集和问题 - 允许重复使用元素")
    void testSubsetSumINaiveBasic() {
        int[] nums = {2, 3, 6, 7};
        int target = 7;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
        
        // 应该包含 [7] 和 [2,2,3] 等解
        assertTrue(containsSubset(result, Arrays.asList(7)), "应该包含单元素解[7]");
        assertTrue(containsSubset(result, Arrays.asList(2,2,3)), "应该包含多元素解[2,2,3]");
    }

    @Test
    @DisplayName("测试允许重复使用元素的复杂情况")
    void testSubsetSumINaiveComplex() {
        int[] nums = {2, 3, 5};
        int target = 8;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
        
        // 可能的解包括: [2,2,2,2], [2,3,3], [3,5]
        assertTrue(containsSubset(result, Arrays.asList(2,2,2,2)), "应该包含[2,2,2,2]");
        assertTrue(containsSubset(result, Arrays.asList(2,3,3)), "应该包含[2,3,3]");
        assertTrue(containsSubset(result, Arrays.asList(3,5)), "应该包含[3,5]");
    }

    @Test
    @DisplayName("测试单元素数组")
    void testSubsetSumINaiveSingleElement() {
        int[] nums = {5};
        int target = 15;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertEquals(1, result.size(), "应该只有一个解");
        assertEquals(Arrays.asList(5,5,5), result.get(0), "解应该是[5,5,5]");
    }

    @Test
    @DisplayName("测试无解情况")
    void testSubsetSumINaiveNoSolution() {
        int[] nums = {3, 5};
        int target = 1;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "应该没有解");
    }

    @Test
    @DisplayName("测试空数组")
    void testSubsetSumINaiveEmptyArray() {
        int[] nums = {};
        int target = 5;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "空数组应该返回空结果");
    }

    @Test
    @DisplayName("测试null数组")
    void testSubsetSumINaiveNullArray() {
        int[] nums = null;
        int target = 5;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "null数组应该返回空结果");
    }

    @Test
    @DisplayName("测试包含非正数的数组 - 应该抛出异常")
    void testSubsetSumINaiveWithNonPositiveNumbers() {
        int[] nums = {2, 0, 3};
        int target = 5;
        
        assertThrows(IllegalArgumentException.class, () -> {
            subsetSum.subsetSumINaive(nums, target);
        }, "包含非正数应该抛出IllegalArgumentException");
        
        int[] numsWithNegative = {2, -1, 3};
        assertThrows(IllegalArgumentException.class, () -> {
            subsetSum.subsetSumINaive(numsWithNegative, target);
        }, "包含负数应该抛出IllegalArgumentException");
    }

    @Test
    @DisplayName("测试目标值为0")
    void testSubsetSumINaiveZeroTarget() {
        int[] nums = {1, 2, 3};
        int target = 0;
        
        List<List<Integer>> result = subsetSum.subsetSumINaive(nums, target);
        
        assertNotNull(result);
        assertEquals(1, result.size(), "目标值为0应该有一个空解");
        assertTrue(result.get(0).isEmpty(), "解应该是空列表");
    }

    // ==================== subsetSumNoDuplicateCombination方法测试 ====================

    @Test
    @DisplayName("测试不重复使用元素的子集和")
    void testSubsetSumNoDuplicateCombination() {
        int[] nums = {2, 3, 6, 7};
        int target = 7;
        
        List<List<Integer>> result = subsetSum.subsetSumNoDuplicateCombination(nums, target);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
        
        // 应该包含 [7] 但不应该包含 [2,2,3]（因为不能重复使用元素）
        assertTrue(containsSubset(result, Arrays.asList(7)), "应该包含[7]");
    }

    @Test
    @DisplayName("测试包含重复元素的数组 - 不重复使用")
    void testSubsetSumNoDuplicateCombinationWithDuplicates() {
        int[] nums = {1, 1, 2, 5};
        int target = 4;
        
        List<List<Integer>> result = subsetSum.subsetSumNoDuplicateCombination(nums, target);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
        
        // 应该包含 [1,1,2]（使用两个不同位置的1）
        assertTrue(containsSubset(result, Arrays.asList(1,1,2)), "应该包含[1,1,2]");
    }

    // ==================== subsetSumNoDuplicateElements方法测试 ====================

    @Test
    @DisplayName("测试不重复使用元素的子集和 - 另一种实现")
    void testSubsetSumNoDuplicateElements() {
        int[] nums = {2, 3, 6, 7};
        int target = 7;
        
        List<List<Integer>> result = subsetSum.subsetSumNoDuplicateElements(nums, target);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
        
        // 应该包含 [7]
        assertTrue(containsSubset(result, Arrays.asList(7)), "应该包含[7]");
    }

    @Test
    @DisplayName("比较两种不重复使用元素的方法结果应该相同")
    void testBothNoDuplicateMethodsGiveSameResult() {
        int[] nums = {1, 2, 3, 4, 5};
        int target = 5;
        
        List<List<Integer>> result1 = subsetSum.subsetSumNoDuplicateCombination(nums, target);
        List<List<Integer>> result2 = subsetSum.subsetSumNoDuplicateElements(nums, target);
        
        // 排序结果以便比较
        sortResults(result1);
        sortResults(result2);
        
        assertEquals(result1.size(), result2.size(), "两种方法应该返回相同数量的解");
        
        for (int i = 0; i < result1.size(); i++) {
            assertEquals(result1.get(i), result2.get(i), "两种方法的解应该相同");
        }
    }

    // ==================== 性能和边界测试 ====================

    @Test
    @DisplayName("测试较大的目标值 - 性能测试")
    void testLargeTarget() {
        int[] nums = {1, 2, 5, 10};
        int target = 15;
        
        List<List<Integer>> result = subsetSum.subsetSumNoDuplicateElements(nums, target);
        
        assertNotNull(result);
        
        // 验证所有结果的和都等于目标值
        for (List<Integer> subset : result) {
            int sum = subset.stream().mapToInt(Integer::intValue).sum();
            assertEquals(target, sum, "子集和应该等于目标值");
        }
    }

    @Test
    @DisplayName("测试所有方法对非正数的异常处理")
    void testAllMethodsWithNonPositiveNumbers() {
        int[] nums = {2, 0, 3};
        int target = 5;
        
        assertThrows(IllegalArgumentException.class, () -> {
            subsetSum.subsetSumINaive(nums, target);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            subsetSum.subsetSumNoDuplicateCombination(nums, target);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            subsetSum.subsetSumNoDuplicateElements(nums, target);
        });
    }

    // ==================== 辅助方法 ====================

    /**
     * 检查结果列表中是否包含指定的子集
     */
    private boolean containsSubset(List<List<Integer>> results, List<Integer> target) {
        for (List<Integer> result : results) {
            List<Integer> sortedResult = new ArrayList<>(result);
            List<Integer> sortedTarget = new ArrayList<>(target);
            Collections.sort(sortedResult);
            Collections.sort(sortedTarget);
            if (sortedResult.equals(sortedTarget)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对结果列表进行排序，便于比较
     */
    private void sortResults(List<List<Integer>> results) {
        for (List<Integer> result : results) {
            Collections.sort(result);
        }
        results.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = a.get(i).compareTo(b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });
    }
}