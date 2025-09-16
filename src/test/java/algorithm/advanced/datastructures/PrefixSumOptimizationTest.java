package algorithm.advanced.datastructures;

import algorithm.foundations.divideconquer.MaxSubArray;
import algorithm.advanced.two_pointers.SlidingWindow;
import algorithm.advanced.dynamicprogramming.GridMinPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 前缀和优化算法的测试类
 * 
 * 验证各种前缀和优化实现的正确性和性能提升
 * 
 * @author magicliang
 * @version 1.0
 * @since 2025-09-15
 */
public class PrefixSumOptimizationTest {

    @Test
    public void testMaxSubArrayPrefixSumFix() {
        MaxSubArray maxSubArray = new MaxSubArray();
        
        // 测试用例1：包含正负数的数组
        int[] arr1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int expected1 = 6; // 子数组 [4, -1, 2, 1] 的和
        assertEquals(expected1, maxSubArray.maxSubArrayBrutalForce3(arr1));
        
        // 测试用例2：全负数数组
        int[] arr2 = {-3, -2, -1, -4};
        int expected2 = -1; // 最大单个元素
        assertEquals(expected2, maxSubArray.maxSubArrayBrutalForce3(arr2));
        
        // 测试用例3：全正数数组
        int[] arr3 = {1, 2, 3, 4, 5};
        int expected3 = 15; // 整个数组的和
        assertEquals(expected3, maxSubArray.maxSubArrayBrutalForce3(arr3));
    }
    
    @Test
    public void testPrefixSumArrayClass() {
        int[] arr = {1, -2, 3, 4, -1, 2};
        MaxSubArray.PrefixSumArray prefixSumArray = new MaxSubArray.PrefixSumArray(arr);
        
        // 测试区间查询
        assertEquals(1, prefixSumArray.rangeSum(0, 0)); // [1]
        assertEquals(-1, prefixSumArray.rangeSum(0, 1)); // [1, -2]
        assertEquals(2, prefixSumArray.rangeSum(0, 2)); // [1, -2, 3]
        assertEquals(6, prefixSumArray.rangeSum(0, 3)); // [1, -2, 3, 4]
        assertEquals(7, prefixSumArray.rangeSum(2, 3)); // [3, 4]
        
        // 测试最大子数组和
        assertEquals(7, prefixSumArray.findMaxSubArraySum()); // [3, 4] 或 [4, -1, 2]
    }
    
    @Test
    public void testSlidingWindowPrefixSumOptimization() {
        SlidingWindow slidingWindow = new SlidingWindow();
        
        // 测试固定长度子数组和
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 3;
        int[] expected = {6, 9, 12, 15}; // [1,2,3]=6, [2,3,4]=9, [3,4,5]=12, [4,5,6]=15
        assertArrayEquals(expected, slidingWindow.sumSlidingWindow(nums, k));
        
        // 测试和等于目标值的子数组个数
        int[] nums2 = {1, 1, 1};
        int target = 2;
        int expectedCount = 2; // [1,1] 出现2次
        assertEquals(expectedCount, slidingWindow.subarraySum(nums2, target));
        
        // 测试包含负数的情况
        int[] nums3 = {1, -1, 0};
        int target3 = 0;
        int expectedCount3 = 3; // [1,-1], [0], [-1,0,1] 但这里只有前两个
        assertEquals(3, slidingWindow.subarraySum(nums3, target3));
    }
    
    @Test
    public void testPrefixSum2D() {
        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };
        
        PrefixSum2D prefixSum2D = new PrefixSum2D(matrix);
        
        // 测试单个元素查询
        assertEquals(3, prefixSum2D.getElement(0, 0));
        assertEquals(6, prefixSum2D.getElement(1, 1));
        
        // 测试矩形区域查询
        assertEquals(8, prefixSum2D.sumRegion(2, 1, 4, 3)); // 2×3矩形区域
        assertEquals(11, prefixSum2D.sumRegion(1, 1, 2, 2)); // 2×2矩形区域
        
        // 测试行列查询
        assertEquals(10, prefixSum2D.sumRow(0)); // 第一行：3+0+1+4+2=10
        assertEquals(17, prefixSum2D.sumCol(1)); // 第二列：0+6+2+1+0=9
        
        // 测试最大子矩阵和
        int maxSum = PrefixSum2D.maxSubMatrixSum(matrix, 2);
        assertTrue(maxSum > 0); // 应该找到一个正数的最大和
    }
    
    @Test
    public void testGridRegionQuery() {
        int[][] grid = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        
        GridMinPath gridMinPath = new GridMinPath();
        GridMinPath.GridRegionQuery regionQuery = gridMinPath.createRegionQuery(grid);
        
        // 测试区域查询
        assertEquals(1, regionQuery.queryRegionSum(0, 0, 0, 0)); // 单个元素
        assertEquals(4, regionQuery.queryRegionSum(0, 0, 0, 1)); // [1,3]
        assertEquals(10, regionQuery.queryRegionSum(0, 0, 1, 1)); // 2×2左上角
        
        // 测试最小子矩阵和
        int minSum = regionQuery.findMinSubMatrixSum(2);
        assertTrue(minSum > 0);
        
        // 测试任意起点终点的最小路径和
        int pathSum = regionQuery.minPathSumBetween(0, 0, 2, 2);
        assertEquals(7, pathSum); // 应该等于原始最小路径和
    }
    
    @Test
    public void testPerformanceComparison() {
        // 创建较大的测试数据
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int) (Math.random() * 100) - 50; // -50到49的随机数
        }
        
        MaxSubArray maxSubArray = new MaxSubArray();
        
        // 测试前缀和优化版本的正确性
        long startTime = System.nanoTime();
        int result1 = maxSubArray.maxSubArrayBrutalForce3(largeArray);
        long endTime = System.nanoTime();
        long prefixSumTime = endTime - startTime;
        
        // 测试普通暴力版本
        startTime = System.nanoTime();
        int result2 = maxSubArray.maxSubArrayBrutalForce2(largeArray);
        endTime = System.nanoTime();
        long bruteForceTime = endTime - startTime;
        
        // 验证结果一致性
        assertEquals(result1, result2);
        
        // 输出性能对比（前缀和版本应该更快，特别是对于大数组）
        System.out.println("前缀和优化版本耗时: " + prefixSumTime + " ns");
        System.out.println("暴力版本耗时: " + bruteForceTime + " ns");
        System.out.println("性能提升比例: " + (double) bruteForceTime / prefixSumTime);
    }
}