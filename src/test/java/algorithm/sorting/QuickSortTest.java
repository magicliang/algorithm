package algorithm.sorting;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 快速排序测试类
 *
 * @author magicliang
 *
 *         date: 2025-08-21 16:00
 */
public class QuickSortTest {

    // ========== quickSort 测试用例 ==========
    // 以下测试用例是为 QuickSort.quickSort(...) 方法添加的

    @Test
    public void testQuickSortBasic() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, QuickSort.quickSort(new int[]{5, 4, 3, 2, 1}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, QuickSort.quickSort(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, QuickSort.quickSort(new int[]{3, 1, 4, 2, 5}));
    }

    @Test
    public void testQuickSortEmptyArray() {
        assertArrayEquals(new int[]{}, QuickSort.quickSort(new int[]{}));
    }

    @Test
    public void testQuickSortSingleElement() {
        assertArrayEquals(new int[]{42}, QuickSort.quickSort(new int[]{42}));
    }

    @Test
    public void testQuickSortTwoElements() {
        assertArrayEquals(new int[]{1, 2}, QuickSort.quickSort(new int[]{2, 1}));
        assertArrayEquals(new int[]{1, 2}, QuickSort.quickSort(new int[]{1, 2}));
    }

    @Test
    public void testQuickSortDuplicateElements() {
        assertArrayEquals(new int[]{1, 2, 2, 3, 3, 3}, QuickSort.quickSort(new int[]{3, 2, 1, 3, 2, 3}));
        assertArrayEquals(new int[]{5, 5, 5, 5}, QuickSort.quickSort(new int[]{5, 5, 5, 5}));
    }

    @Test
    public void testQuickSortNegativeNumbers() {
        assertArrayEquals(new int[]{-5, -3, -1, 0, 2, 4}, QuickSort.quickSort(new int[]{0, -1, 2, -3, 4, -5}));
        assertArrayEquals(new int[]{-10, -5, 0, 5, 10}, QuickSort.quickSort(new int[]{-5, 10, 0, -10, 5}));
    }

    @Test
    public void testQuickSortAllSameElements() {
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, QuickSort.quickSort(new int[]{7, 7, 7, 7, 7}));
    }

    @Test
    public void testQuickSortLargeNumbers() {
        assertArrayEquals(new int[]{1000000, 2000000, 3000000},
                QuickSort.quickSort(new int[]{3000000, 1000000, 2000000}));
    }

    @Test
    public void testQuickSortAlreadySorted() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                QuickSort.quickSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
    }

    @Test
    public void testQuickSortReverseSorted() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                QuickSort.quickSort(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
    }

    @Test
    public void testQuickSortOddLengthArray() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, QuickSort.quickSort(new int[]{3, 1, 5, 2, 4}));
    }

    @Test
    public void testQuickSortEvenLengthArray() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, QuickSort.quickSort(new int[]{6, 3, 1, 5, 2, 4}));
    }

    @Test
    public void testQuickSortZeroAndNegative() {
        assertArrayEquals(new int[]{-10, -5, 0, 0, 5, 10},
                QuickSort.quickSort(new int[]{0, -5, 10, -10, 0, 5}));
    }

    @Test
    public void testQuickSortMaxIntValues() {
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE},
                QuickSort.quickSort(new int[]{0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE}));
    }

    @Test
    public void testQuickSortVeryLargeArray() {
        int[] input = new int[1000];
        int[] expected = new int[1000];
        for (int i = 0; i < 1000; i++) {
            input[i] = 999 - i;
            expected[i] = i;
        }
        assertArrayEquals(expected, QuickSort.quickSort(input));
    }

    @Test
    public void testQuickSortSingleElementTypes() {
        assertArrayEquals(new int[]{0}, QuickSort.quickSort(new int[]{0}));
        assertArrayEquals(new int[]{-1}, QuickSort.quickSort(new int[]{-1}));
        assertArrayEquals(new int[]{Integer.MAX_VALUE}, QuickSort.quickSort(new int[]{Integer.MAX_VALUE}));
        assertArrayEquals(new int[]{Integer.MIN_VALUE}, QuickSort.quickSort(new int[]{Integer.MIN_VALUE}));
    }

    @Test
    public void testQuickSortPerformanceWithLargeArray() {
        int[] input = new int[10000];
        int[] expected = new int[10000];
        for (int i = 0; i < 10000; i++) {
            input[i] = 9999 - i;
            expected[i] = i;
        }
        long startTime = System.currentTimeMillis();
        int[] result = QuickSort.quickSort(input);
        long endTime = System.currentTimeMillis();
        assertArrayEquals(expected, result);
        // 确保在合理时间内完成（10000个元素应该<1000ms）
        assert (endTime - startTime) < 1000 : "Performance test failed - took too long";
    }

    @Test
    public void testQuickSortStability() {
        // 由于我们处理的是基本类型int，稳定性测试不适用
        // 但如果是对象排序，这里可以测试稳定性
        // 对于基础类型，我们只验证排序结果
        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3}, QuickSort.quickSort(new int[]{1, 2, 1, 3, 2, 3}));
    }

    // ========== Hoare分区快速排序测试用例 ==========

    @Test
    void testQuickSortHoare() {
        int[] arr = {3, 2, 1, 5, 6, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithDuplicates() {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int[] expected = {1, 2, 2, 3, 3, 4, 5, 5, 6};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithEmptyArray() {
        int[] arr = {};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testQuickSortHoareWithSingleElement() {
        int[] arr = {42};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testQuickSortHoareWithAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithNullArray() {
        int[] arr = null;
        QuickSort.quickSortHoare(arr);
        assertNull(arr);
    }

    @Test
    void testQuickSortHoareWithTwoElements() {
        int[] arr = {2, 1};
        int[] expected = {1, 2};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithThreeElements() {
        int[] arr = {3, 1, 2};
        int[] expected = {1, 2, 3};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithNegativeNumbers() {
        int[] arr = {-3, 2, -1, 5, -6, 4};
        int[] expected = {-6, -3, -1, 2, 4, 5};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithExtremeValues() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 1, -1};
        int[] expected = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithMixedDuplicates() {
        int[] arr = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        int[] expected = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortHoareWithZeroValues() {
        int[] arr = {0, 0, 0, 1, -1, 0};
        int[] expected = {-1, 0, 0, 0, 0, 1};
        QuickSort.quickSortHoare(arr);
        assertArrayEquals(expected, arr);
    }

    // ========== 三数取中法快速排序测试用例 ==========

    @Test
    void testQuickSortMedianOfThree() {
        int[] arr = {3, 2, 1, 5, 6, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithDuplicates() {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int[] expected = {1, 2, 2, 3, 3, 4, 5, 5, 6};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithEmptyArray() {
        int[] arr = {};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithSingleElement() {
        int[] arr = {42};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithNullArray() {
        int[] arr = null;
        QuickSort.quickSortMedianOfThree(arr);
        assertNull(arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithAllEqual() {
        int[] arr = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeLargeArray() {
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr.length - i; // 逆序排列
        }
        QuickSort.quickSortMedianOfThree(arr);

        // 验证排序结果
        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array should be sorted in ascending order");
        }
    }

    @Test
    void testQuickSortMedianOfThreeWithTwoElements() {
        int[] arr = {2, 1};
        int[] expected = {1, 2};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithThreeElements() {
        int[] arr = {3, 1, 2};
        int[] expected = {1, 2, 3};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithNegativeNumbers() {
        int[] arr = {-3, 2, -1, 5, -6, 4};
        int[] expected = {-6, -3, -1, 2, 4, 5};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithExtremeValues() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 1, -1};
        int[] expected = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithMixedDuplicates() {
        int[] arr = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        int[] expected = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortMedianOfThreeWithZeroValues() {
        int[] arr = {0, 0, 0, 1, -1, 0};
        int[] expected = {-1, 0, 0, 0, 0, 1};
        QuickSort.quickSortMedianOfThree(arr);
        assertArrayEquals(expected, arr);
    }

    // ========== Lomuto分区快速排序测试用例 ==========

    @Test
    void testQuickSortLomutoBasic() {
        int[] arr = {3, 2, 1, 5, 6, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        QuickSort.quickSortLomuto(arr);
        assertArrayEquals(expected, arr);
    }

    // ========== 尾递归优化快速排序测试用例 ==========

    @Test
    void testQuickSortTailRecursiveBasic() {
        int[] arr = {3, 2, 1, 5, 6, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        QuickSort.quickSortTailRecursive(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithDuplicates() {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int[] expected = {1, 2, 2, 3, 3, 4, 5, 5, 6};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithEmptyArray() {
        int[] arr = {};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithSingleElement() {
        int[] arr = {42};
        QuickSort.quickSortTailRecursive(arr, 0, 0);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithTwoElements() {
        int[] arr = {2, 1};
        int[] expected = {1, 2};
        QuickSort.quickSortTailRecursive(arr, 0, 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithThreeElements() {
        int[] arr = {3, 1, 2};
        int[] expected = {1, 2, 3};
        QuickSort.quickSortTailRecursive(arr, 0, 2);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithAllEqual() {
        int[] arr = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithNegativeNumbers() {
        int[] arr = {-3, 2, -1, 5, -6, 4};
        int[] expected = {-6, -3, -1, 2, 4, 5};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveWithExtremeValues() {
        int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 1, -1};
        int[] expected = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortTailRecursiveLargeArray() {
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr.length - i; // 逆序排列
        }
        QuickSort.quickSortTailRecursive(arr, 0, arr.length - 1);

        // 验证排序结果
        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array should be sorted in ascending order");
        }
    }

    @Test
    void testQuickSortTailRecursiveWithNullArray() {
        int[] arr = null;
        QuickSort.quickSortTailRecursive(arr, 0, 0);
        assertNull(arr);
    }

    @Test
    void testQuickSortTailRecursiveWithInvalidRange() {
        int[] arr = {1, 2, 3};
        // begin > end 应该直接返回
        QuickSort.quickSortTailRecursive(arr, 2, 1);
        assertArrayEquals(new int[]{1, 2, 3}, arr);
    }

    // ========== 分区方法测试用例 ==========

    @Test
    void testPartitionLomutoBasic() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = QuickSort.partitionLomuto(arr, 0, arr.length - 1);

        // 验证pivot左边的元素都小于pivot
        int pivotValue = arr[pivotIndex];
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] < pivotValue, "Element at index " + i + " should be < pivot");
        }

        // 验证pivot右边的元素都大于等于pivot
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            assertTrue(arr[i] >= pivotValue, "Element at index " + i + " should be >= pivot");
        }
    }

    @Test
    void testPartitionHoareBasic() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = QuickSort.partitionHoare(arr, 0, arr.length - 1);

        // 验证pivot左边的元素都小于等于pivot
        int pivotValue = arr[pivotIndex];
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] <= pivotValue, "Element at index " + i + " should be <= pivot");
        }

        // 验证pivot右边的元素都大于等于pivot
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            assertTrue(arr[i] >= pivotValue, "Element at index " + i + " should be >= pivot");
        }
    }

    @Test
    void testPartitionHoareWithDuplicates() {
        int[] arr = {5, 2, 8, 2, 9, 1, 5, 5};
        int pivotIndex = QuickSort.partitionHoare(arr, 0, arr.length - 1);

        int pivotValue = arr[pivotIndex];
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] <= pivotValue);
        }
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            assertTrue(arr[i] >= pivotValue);
        }
    }

    @Test
    void testPartitionMedianOfThreeBasic() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = QuickSort.partitionMedianOfThree(arr, 0, arr.length - 1);
        
        // 验证pivot左边的元素都小于等于pivot
        int pivotValue = arr[pivotIndex];
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] <= pivotValue, "Element at index " + i + " should be <= pivot");
        }

        // 验证pivot右边的元素都大于等于pivot
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            assertTrue(arr[i] >= pivotValue, "Element at index " + i + " should be >= pivot");
        }
    }

    @Test
    void testPartitionMedianWithDuplicates() {
        int[] arr = {5, 2, 8, 2, 9, 1, 5, 5};
        int pivotIndex = QuickSort.partitionMedianOfThree(arr, 0, arr.length - 1);
        
        int pivotValue = arr[pivotIndex];
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(arr[i] <= pivotValue);
        }
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            assertTrue(arr[i] >= pivotValue);
        }
    }

    @Test
    void testPartitionMedianSingleElement() {
        int[] arr = {42};
        int pivotIndex = QuickSort.partitionMedianOfThree(arr, 0, 0);
        assertEquals(0, pivotIndex);
        assertEquals(42, arr[0]);
    }

    @Test
    void testPartitionMedianTwoElements() {
        int[] arr = {2, 1};
        int pivotIndex = QuickSort.partitionMedianOfThree(arr, 0, 1);
        
        // 验证分区正确性
        assertTrue(arr[0] <= arr[1]);
    }

    @Test
    void testPartitionMedianAllEqual() {
        int[] arr = {5, 5, 5, 5, 5};
        int pivotIndex = QuickSort.partitionMedianOfThree(arr, 0, arr.length - 1);
        
        // 所有元素相等时，pivot可以是任意位置
        assertTrue(pivotIndex >= 0 && pivotIndex < arr.length);
    }

    @Test
    void testMedianOfThree() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};

        // 测试三数取中
        assertEquals(0, QuickSort.medianOfThree(arr, 0, 1, 2)); // 3,1,4 -> 3是中间值
        assertEquals(1, QuickSort.medianOfThree(arr, 1, 2, 3)); // 1,4,1 -> 1是中间值
        assertEquals(2, QuickSort.medianOfThree(arr, 2, 3, 4)); // 4,1,5 -> 4是中间值
    }

    // ========== 性能和一致性测试 ==========

    @Test
    void testQuickSortMedianOfThreePerformanceComparison() {
        // 测试三数取中法在不同情况下的表现
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // 已排序
        int[] arr2 = arr1.clone();

        QuickSort.quickSortHoare(arr1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr1);

        QuickSort.quickSortMedianOfThree(arr2);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr2);

        // 逆序数组
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        QuickSort.quickSortMedianOfThree(arr3);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr3);
    }

    @Test
    void testQuickSortTailRecursivePerformanceComparison() {
        // 测试尾递归优化版本与其他版本的性能一致性
        int[] arr1 = {3, 2, 1, 5, 6, 4, 7, 8, 9, 0};
        int[] arr2 = arr1.clone();
        int[] arr3 = arr1.clone();

        QuickSort.quickSortHoare(arr1, 0, arr1.length - 1);
        QuickSort.quickSortMedianOfThree(arr2, 0, arr2.length - 1);
        QuickSort.quickSortTailRecursive(arr3, 0, arr3.length - 1);

        // 验证三个版本结果一致
        assertArrayEquals(arr1, arr2);
        assertArrayEquals(arr2, arr3);
    }

    @Test
    void testAllQuickSortVariantsConsistency() {
        // 测试所有快速排序变体的一致性
        int[] original = {9, 3, 7, 1, 8, 2, 6, 4, 5};
        int[] arr1 = original.clone();
        int[] arr2 = original.clone();
        int[] arr3 = original.clone();
        int[] arr4 = original.clone();

        QuickSort.quickSort(arr1);                    // 默认方法（委托给三数取中法）
        QuickSort.quickSortLomuto(arr2);             // Lomuto分区
        QuickSort.quickSortHoare(arr3);              // Hoare分区
        QuickSort.quickSortMedianOfThree(arr4);      // 三数取中法

        // 验证所有版本结果一致
        assertArrayEquals(arr1, arr2);
        assertArrayEquals(arr2, arr3);
        assertArrayEquals(arr3, arr4);

        // 验证排序正确性
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expected, arr1);
    }

    @Test
    void testRandomArray() {
        // 测试随机数组
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000);
        }

        int[] arr2 = arr.clone();
        int[] arr3 = arr.clone();

        QuickSort.quickSortHoare(arr2);
        QuickSort.quickSortMedianOfThree(arr3);

        // 验证两个排序结果相同
        assertArrayEquals(arr2, arr3);

        // 验证排序正确性
        for (int i = 1; i < arr2.length; i++) {
            assertTrue(arr2[i - 1] <= arr2[i]);
            assertTrue(arr3[i - 1] <= arr3[i]);
        }
    }
}