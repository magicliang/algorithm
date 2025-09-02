package algorithm.advanced.greedy_algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * FractionKnapsack 的 JUnit 测试类
 * 测试分数背包问题的各种场景
 */
public class FractionKnapsackTest {

    private FractionKnapsack fractionKnapsack;

    @BeforeEach
    void setUp() {
        fractionKnapsack = new FractionKnapsack();
    }

    /**
     * 测试基本情况：需要部分装入物品
     * 物品价值密度：[3.0, 2.0, 1.5]
     * 预期：装入物品1全部 + 物品2全部 + 物品3的一部分
     */
    @Test
    void testBasicCase() {
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 50;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 240.0; // 60 + 100 + 80 (120 * 20/30)
        
        assertEquals(expected, result, 0.001, "基本情况测试失败");
    }

    /**
     * 测试所有物品都能完全装入的情况
     */
    @Test
    void testAllItemsFit() {
        int[] weights = {5, 10, 15};
        int[] values = {30, 40, 45};
        int capacity = 50;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 115.0; // 30 + 40 + 45
        
        assertEquals(expected, result, 0.001, "所有物品装入测试失败");
    }

    /**
     * 测试只能装入一个物品的部分情况
     */
    @Test
    void testPartialSingleItem() {
        int[] weights = {50};
        int[] values = {100};
        int capacity = 30;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 60.0; // 100 * 30/50
        
        assertEquals(expected, result, 0.001, "部分单个物品测试失败");
    }

    /**
     * 测试背包容量为0的边界情况
     */
    @Test
    void testZeroCapacity() {
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 0;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 0.0;
        
        assertEquals(expected, result, 0.001, "零容量测试失败");
    }

    /**
     * 测试单个物品完全装入的情况
     */
    @Test
    void testSingleItemFullyFits() {
        int[] weights = {10};
        int[] values = {50};
        int capacity = 20;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 50.0;
        
        assertEquals(expected, result, 0.001, "单个物品完全装入测试失败");
    }

    /**
     * 测试相同价值密度的物品
     */
    @Test
    void testSameValueDensity() {
        int[] weights = {10, 20, 30};
        int[] values = {20, 40, 60}; // 所有物品价值密度都是2.0
        int capacity = 35;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 70.0; // 20 + 40 + 10 (60 * 5/30)
        
        assertEquals(expected, result, 0.001, "相同价值密度测试失败");
    }

    /**
     * 测试高价值密度物品优先选择
     */
    @Test
    void testHighValueDensityFirst() {
        int[] weights = {10, 20, 30};
        int[] values = {100, 60, 40}; // 价值密度：[10.0, 3.0, 1.33]
        int capacity = 25;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 145.0; // 100 + 45 (60 * 15/20)
        
        assertEquals(expected, result, 0.001, "高价值密度优先测试失败");
    }

    /**
     * 测试空数组的情况
     */
    @Test
    void testEmptyArrays() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 0.0;
        
        assertEquals(expected, result, 0.001, "空数组测试失败");
    }

    /**
     * 测试精确装满背包的情况
     */
    @Test
    void testExactCapacity() {
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 30; // 正好装入前两个物品
        
        double result = fractionKnapsack.fractionalKnapsack(weights, values, capacity);
        double expected = 160.0; // 60 + 100
        
        assertEquals(expected, result, 0.001, "精确容量测试失败");
    }
}