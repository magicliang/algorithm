package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Knapsacks类的JUnit测试用例
 * 
 * @author magicliang
 * @date 2025-08-28
 */
public class KnapsacksTest {

    private Knapsacks knapsacks;

    @BeforeEach
    public void setUp() {
        knapsacks = new Knapsacks();
    }

    /**
     * 测试基本场景：标准0-1背包问题
     * 物品重量：[2, 3, 4, 5]
     * 物品价值：[3, 4, 5, 6]
     * 背包容量：5
     * 期望结果：7（选择重量2和3的物品，价值3+4=7）
     */
    @Test
    public void testKnapsacksDfsBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(7, result);
    }

    /**
     * 测试边界情况：所有物品都能放入背包
     * 物品重量：[1, 2, 3]
     * 物品价值：[1, 2, 3]
     * 背包容量：10（足够大）
     * 期望结果：6（所有物品都能放入）
     */
    @Test
    public void testKnapsacksDfsAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(6, result);
    }

    /**
     * 测试边界情况：背包容量为0
     * 期望结果：0（无法放入任何物品）
     */
    @Test
    public void testKnapsacksDfsZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(0, result);
    }

    /**
     * 测试边界情况：没有物品
     * 期望结果：0（没有物品可选）
     */
    @Test
    public void testKnapsacksDfsNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = knapsacks.knapsacksDfs(weights, values, 0, capacity);
        assertEquals(0, result);
    }

    /**
     * 测试复杂场景：多个物品的最优选择
     * 物品重量：[1, 3, 4, 5]
     * 物品价值：[1, 4, 5, 7]
     * 背包容量：7
     * 期望结果：9（选择重量3和4的物品，价值4+5=9）
     */
    @Test
    public void testKnapsacksDfsComplex() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(9, result);
    }

    /**
     * 测试单个物品刚好能放入的情况
     * 物品重量：[5]
     * 物品价值：[10]
     * 背包容量：5
     * 期望结果：10
     */
    @Test
    public void testKnapsacksDfsSingleItem() {
        int[] weights = {5};
        int[] values = {10};
        int capacity = 5;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(10, result);
    }

    /**
     * 测试单个物品无法放入的情况
     * 物品重量：[10]
     * 物品价值：[20]
     * 背包容量：5
     * 期望结果：0
     */
    @Test
    public void testKnapsacksDfsSingleItemTooHeavy() {
        int[] weights = {10};
        int[] values = {20};
        int capacity = 5;
        
        int result = knapsacks.knapsacksDfs(weights, values, weights.length, capacity);
        assertEquals(0, result);
    }
}