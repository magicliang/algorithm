package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TwoSumSorted的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class TwoSumSortedTest {

    private TwoSumSorted solution;

    @BeforeEach
    void setUp() {
        solution = new TwoSumSorted();
    }

    @Test
    void testBasicCase() {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] expected = {1, 2}; // 1-based索引
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "基本测试用例失败");
    }

    @Test
    void testZeroBasedIndex() {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] expected = {0, 1}; // 0-based索引
        int[] actual = solution.twoSumZeroBased(numbers, target);
        assertArrayEquals(expected, actual, "0-based索引测试用例失败");
    }

    @Test
    void testTwoElements() {
        int[] numbers = {1, 2};
        int target = 3;
        int[] expected = {1, 2};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "两个元素测试用例失败");
    }

    @Test
    void testNegativeNumbers() {
        int[] numbers = {-3, -1, 0, 2, 4};
        int target = 1;
        int[] expected = {1, 5}; // -3 + 4 = 1, 索引为0和4，1-based为1和5
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "负数测试用例失败");
    }

    @Test
    void testTargetAtEnds() {
        int[] numbers = {1, 2, 3, 4, 5};
        int target = 6; // 1 + 5 = 6
        int[] expected = {1, 5};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "两端元素测试用例失败");
    }

    @Test
    void testNoSolution() {
        int[] numbers = {1, 2, 3, 4};
        int target = 10;
        int[] expected = {};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "无解测试用例失败");
    }

    @Test
    void testDuplicateNumbers() {
        int[] numbers = {1, 1, 2, 2, 3, 3};
        int target = 4; // 1 + 3 = 4 或 2 + 2 = 4
        int[] actual = solution.twoSum(numbers, target);
        assertTrue(actual.length == 2, "应该找到一个解");
        assertEquals(target, numbers[actual[0] - 1] + numbers[actual[1] - 1], "解应该满足目标和");
    }

    @Test
    void testLargeArray() {
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = i + 1;
        }
        int target = 1999; // 999 + 1000 = 1999
        int[] expected = {999, 1000};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "大数组测试用例失败");
    }

    @Test
    void testNullArray() {
        int[] numbers = null;
        int target = 5;
        int[] expected = {};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "空数组测试用例失败");
    }

    @Test
    void testEmptyArray() {
        int[] numbers = {};
        int target = 5;
        int[] expected = {};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "空数组测试用例失败");
    }

    @Test
    void testSingleElement() {
        int[] numbers = {5};
        int target = 10;
        int[] expected = {};
        int[] actual = solution.twoSum(numbers, target);
        assertArrayEquals(expected, actual, "单元素数组测试用例失败");
    }
}