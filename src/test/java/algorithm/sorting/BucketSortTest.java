package algorithm.sorting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
}