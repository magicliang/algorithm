package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ZeroOrOneKnapsacksTest {

    private final ZeroOrOneKnapsacks zeroOrOneKnapsacks = new ZeroOrOneKnapsacks();

    @Test
    public void testKnapsacksDfsBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 4, capacity);
        assertEquals(7, result); // 选择物品0和1：重量2+3=5，价值3+4=7
    }

    @Test
    public void testKnapsacksDfsAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入
    }

    @Test
    public void testKnapsacksDfsZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 3, capacity);
        assertEquals(0, result); // 容量为0，无法装入任何物品
    }

    @Test
    public void testKnapsacksDfsNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 0, capacity);
        assertEquals(0, result); // 没有物品可装入
    }

    @Test
    public void testKnapsacksMemoizationBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 4, capacity);
        assertEquals(7, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 3, capacity);
        assertEquals(6, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 3, capacity);
        assertEquals(0, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 0, capacity);
        assertEquals(0, result); // 应该与DFS结果一致
    }

    @Test
    public void testKnapsacksMemoizationComplex() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        int dfsResult = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 4, capacity);
        int memoResult = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 4, capacity);
        
        assertEquals(dfsResult, memoResult); // 两种方法结果应该一致
        assertEquals(9, memoResult); // 最优解：选择物品1和3（重量3+4=7，价值4+5=9）
    }

    @Test
    public void testKnapsacksMemoizationSingleItem() {
        int[] weights = {3};
        int[] values = {5};
        int capacity = 4;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 1, capacity);
        assertEquals(5, result); // 单个物品可以放入
    }

    @Test
    public void testKnapsacksMemoizationSingleItemTooHeavy() {
        int[] weights = {5};
        int[] values = {10};
        int capacity = 3;
        
        int result = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 1, capacity);
        assertEquals(0, result); // 单个物品太重，无法放入
    }

    @Test
    public void testKnapsacksDpBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 4, capacity);
        assertEquals(7, result); // 选择物品0和1：重量2+3=5，价值3+4=7
    }

    @Test
    public void testKnapsacksDpAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入
    }

    @Test
    public void testKnapsacksDpZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 3, capacity);
        assertEquals(0, result); // 容量为0，无法装入任何物品
    }

    @Test
    public void testKnapsacksDpNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 0, capacity);
        assertEquals(0, result); // 没有物品可装入
    }

    @Test
    public void testKnapsacksDpComplex() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 4, capacity);
        assertEquals(9, result); // 最优解：选择物品1和2（重量3+4=7，价值4+5=9）
    }

    @Test
    public void testKnapsacksDpSingleItem() {
        int[] weights = {3};
        int[] values = {5};
        int capacity = 4;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 1, capacity);
        assertEquals(5, result); // 单个物品可以放入
    }

    @Test
    public void testKnapsacksDpSingleItemTooHeavy() {
        int[] weights = {5};
        int[] values = {10};
        int capacity = 3;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 1, capacity);
        assertEquals(0, result); // 单个物品太重，无法放入
    }

    @Test
    public void testKnapsacksDpConsistencyWithOtherMethods() {
        int[] weights = {2, 3, 4, 5, 1, 6};
        int[] values = {3, 4, 5, 6, 2, 8};
        int capacity = 8;
        
        int dfsResult = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 6, capacity);
        int memoResult = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 6, capacity);
        int dpResult = zeroOrOneKnapsacks.knapsacksDp(weights, values, 6, capacity);
        
        // 三种方法结果应该一致
        assertEquals(dfsResult, memoResult);
        assertEquals(memoResult, dpResult);
    }

    @Test
    public void testKnapsacksDpEdgeCaseLargeCapacity() {
        int[] weights = {1, 1, 1};
        int[] values = {1, 2, 3};
        int capacity = 100;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入，总价值为1+2+3=6
    }

    @Test
    public void testKnapsacksDpEdgeCaseSmallWeights() {
        int[] weights = {1, 1, 1, 1, 1};
        int[] values = {1, 2, 3, 4, 5};
        int capacity = 3;
        
        int result = zeroOrOneKnapsacks.knapsacksDp(weights, values, 5, capacity);
        assertEquals(12, result); // 选择价值最高的3个物品：3+4+5=12
    }

    @Test
    public void testKnapsacksDpOptimizedBasic() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 4, capacity);
        assertEquals(7, result); // 选择物品0和1：重量2+3=5，价值3+4=7
    }

    @Test
    public void testKnapsacksDpOptimizedAllItemsFit() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入
    }

    @Test
    public void testKnapsacksDpOptimizedZeroCapacity() {
        int[] weights = {1, 2, 3};
        int[] values = {1, 2, 3};
        int capacity = 0;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 3, capacity);
        assertEquals(0, result); // 容量为0，无法装入任何物品
    }

    @Test
    public void testKnapsacksDpOptimizedNoItems() {
        int[] weights = {};
        int[] values = {};
        int capacity = 10;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 0, capacity);
        assertEquals(0, result); // 没有物品可装入
    }

    @Test
    public void testKnapsacksDpOptimizedComplex() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 4, capacity);
        assertEquals(9, result); // 最优解：选择物品1和2（重量3+4=7，价值4+5=9）
    }

    @Test
    public void testKnapsacksDpOptimizedSingleItem() {
        int[] weights = {3};
        int[] values = {5};
        int capacity = 4;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 1, capacity);
        assertEquals(5, result); // 单个物品可以放入
    }

    @Test
    public void testKnapsacksDpOptimizedSingleItemTooHeavy() {
        int[] weights = {5};
        int[] values = {10};
        int capacity = 3;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 1, capacity);
        assertEquals(0, result); // 单个物品太重，无法放入
    }

    @Test
    public void testKnapsacksDpOptimizedConsistencyWithOtherMethods() {
        int[] weights = {2, 3, 4, 5, 1, 6};
        int[] values = {3, 4, 5, 6, 2, 8};
        int capacity = 8;
        
        int dfsResult = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 6, capacity);
        int memoResult = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 6, capacity);
        int dpResult = zeroOrOneKnapsacks.knapsacksDp(weights, values, 6, capacity);
        int optimizedResult = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 6, capacity);
        
        // 四种方法结果应该一致
        assertEquals(dfsResult, memoResult);
        assertEquals(memoResult, dpResult);
        assertEquals(dpResult, optimizedResult);
    }

    @Test
    public void testKnapsacksDpOptimizedEdgeCaseLargeCapacity() {
        int[] weights = {1, 1, 1};
        int[] values = {1, 2, 3};
        int capacity = 100;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 3, capacity);
        assertEquals(6, result); // 所有物品都能装入，总价值为1+2+3=6
    }

    @Test
    public void testKnapsacksDpOptimizedEdgeCaseSmallWeights() {
        int[] weights = {1, 1, 1, 1, 1};
        int[] values = {1, 2, 3, 4, 5};
        int capacity = 3;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 5, capacity);
        assertEquals(12, result); // 选择价值最高的3个物品：3+4+5=12
    }

    @Test
    public void testKnapsacksDpOptimizedStressTest() {
        // 测试较大输入，验证优化版本的空间效率
        int[] weights = {3, 4, 5, 6, 7, 8, 9, 10};
        int[] values = {4, 5, 6, 7, 8, 9, 10, 11};
        int capacity = 20;
        
        int dpResult = zeroOrOneKnapsacks.knapsacksDp(weights, values, 8, capacity);
        int optimizedResult = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 8, capacity);
        
        assertEquals(dpResult, optimizedResult);
    }

    @Test
    public void testKnapsacksDpOptimizedBoundaryValues() {
        // 测试边界值情况
        int[] weights = {1};
        int[] values = {100};
        int capacity = 1;
        
        int result = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 1, capacity);
        assertEquals(100, result); // 刚好能放入高价值物品
    }

    @Test
    public void testKnapsacksAllMethodsConsistency() {
        // 全面一致性测试
        int[] weights = {1, 2, 3, 4, 5};
        int[] values = {1, 3, 4, 5, 7};
        int capacity = 7;
        
        int dfs = zeroOrOneKnapsacks.knapsacksDfs(weights, values, 5, capacity);
        int memo = zeroOrOneKnapsacks.knapsacksMemoization(weights, values, 5, capacity);
        int dp = zeroOrOneKnapsacks.knapsacksDp(weights, values, 5, capacity);
        int optimized = zeroOrOneKnapsacks.knapsacksDpOptimized(weights, values, 5, capacity);
        
        assertEquals(dfs, memo);
        assertEquals(memo, dp);
        assertEquals(dp, optimized);
        assertEquals(10, optimized); // 验证具体结果
    }
}