package algorithm.sorting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for MyRadixSort.
 *
 * 说明与范围：
 * - 当前实现按十进制 LSD（从低位到高位）进行稳定计数排序，只支持非负整数（>= 0）。
 * - 由于实现未对负数做特殊处理，测试范围不包含负数用例。
 * - 为了验证稳定性与正确性，我们覆盖多种典型场景，并与 Arrays.sort 的结果对比。
 *
 * 注意：
 * - 不修改被测类中的任何原注释，仅在测试中添加说明性注释。
 */
public class MyRadixSortTest {

    @Test
    @DisplayName("null 输入返回 null")
    void testNullInput() {
        assertNull(MyRadixSort.radixSort(null));
    }

    @Test
    @DisplayName("空数组与单元素数组")
    void testEmptyAndSingle() {
        int[] empty = new int[0];
        assertArrayEquals(empty, MyRadixSort.radixSort(empty));

        int[] single = new int[]{42};
        assertArrayEquals(new int[]{42}, MyRadixSort.radixSort(single));
    }

    @Test
    @DisplayName("已排序数组保持不变（幂等）")
    void testAlreadySorted() {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5};
        int[] expected = Arrays.copyOf(arr, arr.length);
        assertArrayEquals(expected, MyRadixSort.radixSort(arr));

        // 再次排序应得到相同结果（幂等性）
        assertArrayEquals(expected, MyRadixSort.radixSort(expected));
    }

    @Test
    @DisplayName("逆序数组")
    void testReverseSorted() {
        int[] arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        int[] actual = MyRadixSort.radixSort(arr);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("包含重复元素")
    void testWithDuplicates() {
        int[] arr = new int[]{5, 3, 3, 2, 8, 8, 8, 1, 5, 0, 0};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        int[] actual = MyRadixSort.radixSort(arr);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("混合数值（包含 0 与多位数，均为非负）")
    void testMixedValues() {
        int[] arr = new int[]{0, 10, 2, 100, 21, 3, 11, 200, 1, 99, 101, 20};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        int[] actual = MyRadixSort.radixSort(arr);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("大规模随机用例（确定性种子）")
    void testLargeRandom() {
        int n = 10000;
        int[] arr = new int[n];
        Random rnd = new Random(20250920L);
        for (int i = 0; i < n; i++) {
            // 仅使用非负整数，范围适配当前实现
            arr[i] = rnd.nextInt(1_000_000);
        }
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        int[] actual = MyRadixSort.radixSort(arr);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("多次调用之间互不干扰（静态状态的正确复用）")
    void testMultipleCallsIndependence() {
        int[] a1 = new int[]{170, 45, 75, 90, 802, 24, 2, 66};
        int[] a2 = new int[]{3, 3, 3, 2, 2, 1, 0, 0, 9};
        int[] e1 = Arrays.copyOf(a1, a1.length);
        int[] e2 = Arrays.copyOf(a2, a2.length);
        Arrays.sort(e1);
        Arrays.sort(e2);

        int[] r1 = MyRadixSort.radixSort(a1);
        int[] r2 = MyRadixSort.radixSort(a2);

        assertArrayEquals(e1, r1);
        assertArrayEquals(e2, r2);
    }

    // 可选：如果未来需要支持负数，可以在此添加包含负数的用例，
    // 并与期望行为（如抛异常/分桶处理负数）对齐。目前跳过负数测试。
}