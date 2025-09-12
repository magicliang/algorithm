package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CuttingStick 的单元测试
 * 测试切木棍问题的区间DP解法
 */
public class CuttingStickTest {

    private final CuttingStick solution = new CuttingStick();

    @Test
    public void testBasicCase() {
        // 测试基本用例：长度为7的木棍，在位置[1,3,4,5]切割
        int n = 7;
        int[] cuts = {1, 3, 4, 5};
        int expected = 16; // 最优切割顺序的总成本
        assertEquals(expected, solution.minCost(n, cuts));
    }

    @Test
    public void testSingleCut() {
        // 测试单次切割
        int n = 10;
        int[] cuts = {5};
        int expected = 10; // 只需要切一次，成本就是木棍长度
        assertEquals(expected, solution.minCost(n, cuts));
    }

    @Test
    public void testTwoCuts() {
        // 测试两次切割
        int n = 9;
        int[] cuts = {5, 6};
        int expected = 13; // 最优切割顺序：先切5(成本9)，再切6(成本4)，总成本13
        assertEquals(expected, solution.minCost(n, cuts));
    }

    @Test
    public void testThreeCuts() {
        // 测试三次切割
        int n = 6;
        int[] cuts = {1, 3, 4};
        int expected = 12; // 根据最优切割顺序计算
        assertEquals(expected, solution.minCost(n, cuts));
    }

    @Test
    public void testUnsortedCuts() {
        // 测试未排序的切割点
        int n = 8;
        int[] cuts = {5, 2, 7, 1};
        int result = solution.minCost(n, cuts);
        assertTrue(result > 0, "结果应该大于0");
        
        // 验证排序后结果相同
        int[] sortedCuts = {1, 2, 5, 7};
        int sortedResult = solution.minCost(n, sortedCuts);
        assertEquals(sortedResult, result, "排序前后结果应该相同");
    }

    @Test
    public void testLargeStick() {
        // 测试较长的木棍
        int n = 20;
        int[] cuts = {2, 8, 10, 15, 18};
        int result = solution.minCost(n, cuts);
        assertTrue(result > 0, "结果应该大于0");
        assertTrue(result >= n, "总成本至少应该包含一次完整切割");
    }

    @Test
    public void testNoCuts() {
        // 测试无切割点的情况
        int n = 10;
        int[] cuts = {};
        int expected = 0; // 不需要切割，成本为0
        assertEquals(expected, solution.minCost(n, cuts));
    }

    @Test
    public void testConsecutiveCuts() {
        // 测试连续切割点
        int n = 5;
        int[] cuts = {1, 2, 3, 4};
        int result = solution.minCost(n, cuts);
        assertTrue(result > 0, "结果应该大于0");
    }
}