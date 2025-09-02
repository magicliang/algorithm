package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MergeTwoArrays的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class MergeTwoArraysTest {

    private MergeTwoArrays solution;

    @BeforeEach
    void setUp() {
        solution = new MergeTwoArrays();
    }

    @Test
    void testMergeBasic() {
        // 基本合并测试
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        solution.merge(nums1, 3, nums2, 3);
        
        int[] expected = {1, 2, 2, 3, 5, 6};
        assertArrayEquals(expected, nums1, "基本合并测试失败");
    }

    @Test
    void testMergeEmptyNums2() {
        // nums2为空
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {};
        solution.merge(nums1, 3, nums2, 0);
        
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, nums1, "nums2为空时应该保持nums1不变");
    }

    @Test
    void testMergeEmptyNums1() {
        // nums1有效部分为空
        int[] nums1 = {0, 0, 0};
        int[] nums2 = {1, 2, 3};
        solution.merge(nums1, 0, nums2, 3);
        
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, nums1, "nums1为空时应该复制nums2");
    }

    @Test
    void testMergeSingleElements() {
        // 单元素合并
        int[] nums1 = {1, 0};
        int[] nums2 = {2};
        solution.merge(nums1, 1, nums2, 1);
        
        int[] expected = {1, 2};
        assertArrayEquals(expected, nums1, "单元素合并测试失败");
    }

    @Test
    void testMergeToNewArray() {
        // 测试返回新数组的方法
        int[] nums1 = {1, 3, 5};
        int[] nums2 = {2, 4, 6};
        int[] result = solution.mergeToNewArray(nums1, nums2);
        
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, result, "合并到新数组测试失败");
    }

    @Test
    void testMergeToNewArrayWithNulls() {
        // 测试null数组
        int[] result1 = solution.mergeToNewArray(null, new int[]{1, 2, 3});
        assertArrayEquals(new int[]{1, 2, 3}, result1, "nums1为null时应该返回nums2");
        
        int[] result2 = solution.mergeToNewArray(new int[]{1, 2, 3}, null);
        assertArrayEquals(new int[]{1, 2, 3}, result2, "nums2为null时应该返回nums1");
        
        int[] result3 = solution.mergeToNewArray(null, null);
        assertArrayEquals(new int[0], result3, "两个都为null时应该返回空数组");
    }

    @Test
    void testMergeKSortedArrays() {
        // 测试合并k个有序数组
        int[][] arrays = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9}
        };
        int[] result = solution.mergeKSortedArrays(arrays);
        
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expected, result, "合并k个有序数组测试失败");
    }

    @Test
    void testMergeKSortedArraysEmpty() {
        // 空数组测试
        int[][] arrays = {};
        int[] result = solution.mergeKSortedArrays(arrays);
        assertArrayEquals(new int[0], result, "空数组应该返回空结果");
        
        int[] result2 = solution.mergeKSortedArrays(null);
        assertArrayEquals(new int[0], result2, "null应该返回空结果");
    }

    @Test
    void testMergeKSortedArraysSingle() {
        // 单个数组
        int[][] arrays = {{1, 2, 3, 4, 5}};
        int[] result = solution.mergeKSortedArrays(arrays);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result, "单个数组应该直接返回");
    }

    @Test
    void testIntersection() {
        // 测试交集
        int[] nums1 = {1, 2, 2, 3, 4};
        int[] nums2 = {2, 2, 3, 5};
        int[] result = solution.intersection(nums1, nums2);
        
        int[] expected = {2, 3};
        assertArrayEquals(expected, result, "交集测试失败");
    }

    @Test
    void testIntersectionNoCommon() {
        // 无交集
        int[] nums1 = {1, 3, 5};
        int[] nums2 = {2, 4, 6};
        int[] result = solution.intersection(nums1, nums2);
        assertArrayEquals(new int[0], result, "无交集应该返回空数组");
    }

    @Test
    void testIntersectionWithNulls() {
        // null数组测试
        int[] result1 = solution.intersection(null, new int[]{1, 2, 3});
        assertArrayEquals(new int[0], result1, "nums1为null应该返回空数组");
        
        int[] result2 = solution.intersection(new int[]{1, 2, 3}, null);
        assertArrayEquals(new int[0], result2, "nums2为null应该返回空数组");
    }

    @Test
    void testIntersectionWithDuplicates() {
        // 测试包含重复元素的交集
        int[] nums1 = {1, 2, 2, 3, 3, 4};
        int[] nums2 = {2, 2, 3, 3, 5};
        int[] result = solution.intersectionWithDuplicates(nums1, nums2);
        
        int[] expected = {2, 2, 3, 3};
        assertArrayEquals(expected, result, "包含重复元素的交集测试失败");
    }

    @Test
    void testMergeDifferentSizes() {
        // 不同大小数组合并
        int[] nums1 = {1, 3, 5, 7, 9, 0, 0};
        int[] nums2 = {2, 4};
        solution.merge(nums1, 5, nums2, 2);
        
        int[] expected = {1, 2, 3, 4, 5, 7, 9};
        assertArrayEquals(expected, nums1, "不同大小数组合并测试失败");
    }

    @Test
    void testMergeAllFromNums2First() {
        // nums2的所有元素都小于nums1
        int[] nums1 = {4, 5, 6, 0, 0, 0};
        int[] nums2 = {1, 2, 3};
        solution.merge(nums1, 3, nums2, 3);
        
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, nums1, "nums2元素都较小的合并测试失败");
    }

    @Test
    void testMergeAllFromNums1First() {
        // nums1的所有元素都小于nums2
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {4, 5, 6};
        solution.merge(nums1, 3, nums2, 3);
        
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, nums1, "nums1元素都较小的合并测试失败");
    }

    @Test
    void testMergeWithDuplicates() {
        // 包含重复元素的合并
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 3, 4};
        solution.merge(nums1, 3, nums2, 3);
        
        int[] expected = {1, 2, 2, 3, 3, 4};
        assertArrayEquals(expected, nums1, "包含重复元素的合并测试失败");
    }
}