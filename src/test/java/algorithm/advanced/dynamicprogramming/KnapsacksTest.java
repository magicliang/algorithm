package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KnapsacksTest {

    private final Knapsacks knapsacks = new Knapsacks();

    @Test
    public void testKnapsacksDfsBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = knapsacks.knapsacksDfs(weights, values, 4, capacity);
        assertEquals(7, result); // 选择物品0和1：重量2+3=5，价值3+4=7
    }

    @Test
    public void testKnapsacksDfsAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = knapsacks.knapsacksDfs(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入
    }

    @Test
    public void testKnapsacksDfsZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = knapsacks.knapsacksDfs(weights, values, 3, capacity);
        assertEquals(0, result); // 容量为0，无法装入任何物品
    }

    @Test
    public void testKnapsacksDfsNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = knapsacks.knapsacksDfs(weights, values, 0, capacity);
        assertEquals(0, result); // 没有物品可装入
    }

    @Test
    public void testKnapsacksMemoizationBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 4, capacity);
        assertEquals(7, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 3, capacity);
        assertEquals(6, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 3, capacity);
        assertEquals(0, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 0, capacity);
        assertEquals(0, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationComplex() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        int dfsResult = knapsacks.knapsacksDfs(weights, values, 4, capacity);
        int memoResult = knapsacks.knapsacksMemoization(weights, values, 4, capacity);
        
        assertEquals(dfsResult, memoResult); // 两种方法结果应该一致
        assertEquals(9, memoResult); // 最优解：选择物品1和3（重量3+4=7，价值4+5=9）
    }

    @Test
    public void testKnapsacksMemoizationSingleItem() {
        int[] weights = {3};
        int[] values = {5};
        int capacity = 4;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 1, capacity);
        assertEquals(5, result); // 单个物品可以放入
    }

    @Test
    public void testKnapsacksMemoizationSingleItemTooHeavy() {
        int[] weights = {5};
        int[] values = {10};
        int capacity = 3;
        
        int result = knapsacks.knapsacksMemoization(weights, values, 1, capacity);
        assertEquals(0, result); // 单个物品太重，无法放入
    }
}