package algorithm.sorting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

/**
 * 桶排序测试类
 */
public class BucketSortTest {

    @Test
    public void testSort() {
        int[] arr = {29, 25, 3, 49, 9, 37, 21, 43};
        int[] expected = {3, 9, 21, 25, 29, 37, 43, 49};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        BucketSort.sort(arr, 10);
        assertEquals(0, arr.length);
    }

    @Test
    public void testSingleElement() {
        int[] arr = {5};
        int[] expected = {5};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testDuplicateElements() {
        int[] arr = {5, 3, 5, 2, 3};
        int[] expected = {2, 3, 3, 5, 5};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testNegativeAndZeroElements() {
        int[] arr = {-1, 0, -5, 3};
        int[] expected = {-5, -1, 0, 3};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testExtremeBucketSize() {
        int[] arr = {29, 25, 3, 49};
        int[] expected = {3, 25, 29, 49};
        BucketSort.sort(arr, 1); // 桶大小为 1
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testLargeDataset() {
        int[] arr = new int[1000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        BucketSort.sort(arr, 10);
        assertArrayEquals(expected, arr);
    }
}