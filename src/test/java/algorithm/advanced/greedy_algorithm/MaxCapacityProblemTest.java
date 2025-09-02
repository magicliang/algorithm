package algorithm.advanced.greedy_algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MaxCapacityProblem的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class MaxCapacityProblemTest {

    private MaxCapacityProblem solution;

    @BeforeEach
    void setUp() {
        solution = new MaxCapacityProblem();
    }

    @Test
    void testBasicCase() {
        // 测试基本情况：[1,8,6,2,5,4,8,3,7]
        // 最大容量应该是49 (索引1和8，高度8和7，宽度7)
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int expected = 49;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "基本测试用例失败");
    }

    @Test
    void testTwoElements() {
        // 测试只有两个元素的情况
        int[] height = {1, 1};
        int expected = 1;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "两个元素测试用例失败");
    }

    @Test
    void testIncreasingHeight() {
        // 测试递增高度：[1,2,3,4,5]
        // 最大容量应该是6 (索引0和4，高度1和5，宽度4)
        int[] height = {1, 2, 3, 4, 5};
        int expected = 6;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "递增高度测试用例失败");
    }

    @Test
    void testDecreasingHeight() {
        // 测试递减高度：[5,4,3,2,1]
        // 最大容量应该是6 (索引0和4，高度5和1，宽度4)
        int[] height = {5, 4, 3, 2, 1};
        int expected = 6;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "递减高度测试用例失败");
    }

    @Test
    void testSameHeight() {
        // 测试相同高度：[3,3,3,3,3]
        // 最大容量应该是12 (任意两端，高度3，宽度4)
        int[] height = {3, 3, 3, 3, 3};
        int expected = 12;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "相同高度测试用例失败");
    }

    @Test
    void testLargeNumbers() {
        // 测试大数值
        int[] height = {1000, 1, 1, 1, 1000};
        int expected = 4000; // 索引0和4，高度1000，宽度4
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "大数值测试用例失败");
    }

    @Test
    void testZigzagPattern() {
        // 测试锯齿模式：[2,1,3,1,4,1,5]
        // 最大容量应该是12 (索引0和6，高度2和5，宽度6)
        int[] height = {2, 1, 3, 1, 4, 1, 5};
        int expected = 12;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "锯齿模式测试用例失败");
    }

    @Test
    void testNullArray() {
        // 测试空数组
        int[] height = null;
        int expected = 0;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "空数组测试用例失败");
    }

    @Test
    void testEmptyArray() {
        // 测试长度为0的数组
        int[] height = {};
        int expected = 0;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "空数组测试用例失败");
    }

    @Test
    void testSingleElement() {
        // 测试只有一个元素的数组
        int[] height = {5};
        int expected = 0;
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "单元素数组测试用例失败");
    }

    @Test
    void testMaxAtEnds() {
        // 测试最大值在两端的情况
        int[] height = {10, 1, 2, 3, 4, 5, 6, 7, 8, 10};
        int expected = 90; // 索引0和9，高度10，宽度9
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "两端最大值测试用例失败");
    }

    @Test
    void testMaxInMiddle() {
        // 测试最大值在中间的情况
        int[] height = {1, 2, 10, 10, 2, 1};
        int expected = 10; // 索引2和3，高度10，宽度1
        int actual = solution.maxArea(height);
        assertEquals(expected, actual, "中间最大值测试用例失败");
    }
}