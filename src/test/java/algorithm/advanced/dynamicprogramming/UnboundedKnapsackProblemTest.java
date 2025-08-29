package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 完全背包问题测试类
 */
public class UnboundedKnapsackProblemTest {

    private final UnboundedKnapsackProblem solver = new UnboundedKnapsackProblem();

    @Test
    public void testBasicCase() {
        // 测试基本情况：3个物品，背包容量为4
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        int capacity = 4;
        
        // 最优解：选择2个重量为2的物品，总价值为8
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 3, capacity);
        assertEquals(8, result);
    }

    @Test
    public void testZeroCapacity() {
        // 测试容量为0的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 3, 0);
        assertEquals(0, result);
    }

    @Test
    public void testZeroItems() {
        // 测试没有物品的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 0, 10);
        assertEquals(0, result);
    }

    @Test
    public void testSingleItem() {
        // 测试只有一个物品的情况
        int[] wgt = {2};
        int[] val = {3};
        int capacity = 5;
        
        // 可以选择2个该物品，总价值为6
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 1, capacity);
        assertEquals(6, result);
    }

    @Test
    public void testItemTooHeavy() {
        // 测试物品重量超过背包容量的情况
        int[] wgt = {5, 6, 7};
        int[] val = {10, 12, 14};
        int capacity = 4;
        
        // 所有物品都放不进去
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 3, capacity);
        assertEquals(0, result);
    }

    @Test
    public void testOptimalSelection() {
        // 测试需要选择最优组合的情况
        int[] wgt = {1, 3, 4};
        int[] val = {15, 20, 30};
        int capacity = 4;
        
        // 最优解：选择4个重量为1的物品，总价值为60
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 3, capacity);
        assertEquals(60, result);
    }

    @Test
    public void testLargerCapacity() {
        // 测试较大容量的情况
        int[] wgt = {2, 3, 4, 5};
        int[] val = {3, 4, 5, 6};
        int capacity = 8;
        
        // 最优解：选择4个重量为2的物品，总价值为12
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 4, capacity);
        assertEquals(12, result);
    }
}