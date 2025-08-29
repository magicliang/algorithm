package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 硬币兑换组合问题的单元测试
 * 
 * 测试覆盖：
 * - 基本功能测试
 * - 边界条件测试
 * - 特殊情况测试
 * - 性能对比测试
 * 
 * @author magicliang
 * @date 2025-08-29
 */
public class CoinsChangeCombinationProblemTest {

    private CoinsChangeCombinationProblem problem;

    @BeforeEach
    void setUp() {
        problem = new CoinsChangeCombinationProblem();
    }

    /**
     * 测试基本功能：经典硬币组合问题
     * coins = [1, 2, 5], amount = 5
     * 预期组合：
     * 1. 5 = 5
     * 2. 5 = 2 + 2 + 1
     * 3. 5 = 2 + 1 + 1 + 1
     * 4. 5 = 1 + 1 + 1 + 1 + 1
     * 总共4种组合
     */
    @Test
    void testBasicCoinChange() {
        int[] coins = {1, 2, 5};
        int amount = 5;
        int expected = 4;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试边界条件：金额为0
     * 任何硬币组合，金额为0时都有1种方案（不选任何硬币）
     */
    @Test
    void testZeroAmount() {
        int[] coins = {1, 2, 5};
        int amount = 0;
        int expected = 1;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试边界条件：没有硬币
     * 没有硬币时，无法组成任何正数金额
     */
    @Test
    void testNoCoins() {
        int[] coins = {};
        int amount = 5;
        int expected = 0;

        assertEquals(expected, problem.coinChange(coins, amount));
    }

    /**
     * 测试边界条件：硬币数组为null
     */
    @Test
    void testNullCoins() {
        int[] coins = null;
        int amount = 5;
        int expected = 0;

        assertEquals(expected, problem.coinChange(coins, amount));
    }

    /**
     * 测试边界条件：负数金额
     */
    @Test
    void testNegativeAmount() {
        int[] coins = {1, 2, 5};
        int amount = -1;
        int expected = 0;

        assertEquals(expected, problem.coinChange(coins, amount));
    }

    /**
     * 测试无法组成的金额
     * coins = [2, 4], amount = 3
     * 无法用偶数面值硬币组成奇数金额
     */
    @Test
    void testImpossibleAmount() {
        int[] coins = {2, 4};
        int amount = 3;
        int expected = 0;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试单一硬币
     * coins = [1], amount = 3
     * 只有一种组合：1 + 1 + 1
     */
    @Test
    void testSingleCoin() {
        int[] coins = {1};
        int amount = 3;
        int expected = 1;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试硬币面值大于目标金额
     * coins = [5, 10], amount = 3
     * 所有硬币面值都大于目标金额，无法组成
     */
    @Test
    void testCoinsLargerThanAmount() {
        int[] coins = {5, 10};
        int amount = 3;
        int expected = 0;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试复杂情况
     * coins = [1, 2, 3], amount = 4
     * 预期组合：
     * 1. 4 = 3 + 1
     * 2. 4 = 2 + 2
     * 3. 4 = 2 + 1 + 1
     * 4. 4 = 1 + 1 + 1 + 1
     * 总共4种组合
     */
    @Test
    void testComplexCase() {
        int[] coins = {1, 2, 3};
        int amount = 4;
        int expected = 4;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试较大数值
     * coins = [1, 5, 10, 25], amount = 30
     * 测试算法在较大输入下的正确性
     */
    @Test
    void testLargerAmount() {
        int[] coins = {1, 5, 10, 25};
        int amount = 30;
        
        // 先用DFS计算期望值（作为基准）
        int expected = problem.coinChangeDfs(coins, coins.length, amount);
        
        // 验证所有方法结果一致
        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
        
        // 验证结果大于0（应该有多种组合方式）
        assertTrue(expected > 0);
    }

    /**
     * 测试重复硬币面值
     * coins = [2, 2, 3], amount = 6
     * 虽然有重复面值，但算法应该正确处理
     */
    @Test
    void testDuplicateCoins() {
        int[] coins = {2, 2, 3};
        int amount = 6;
        
        int result1 = problem.coinChangeDfs(coins, coins.length, amount);
        int result2 = problem.coinChangeMemoization(coins, coins.length, amount);
        int result3 = problem.coinChangeDp(coins, coins.length, amount);
        int result4 = problem.coinChangeDpOptimized(coins, coins.length, amount);
        
        // 所有方法应该返回相同结果
        assertEquals(result1, result2);
        assertEquals(result2, result3);
        assertEquals(result3, result4);
        
        // 结果应该大于0
        assertTrue(result1 > 0);
    }

    /**
     * 测试性能对比：记忆化 vs DP
     * 在相同输入下，记忆化和DP应该返回相同结果
     */
    @Test
    void testPerformanceComparison() {
        int[] coins = {1, 3, 5, 7};
        int amount = 15;
        
        long startTime, endTime;
        
        // 测试记忆化搜索
        startTime = System.nanoTime();
        int memoResult = problem.coinChangeMemoization(coins, coins.length, amount);
        endTime = System.nanoTime();
        long memoTime = endTime - startTime;
        
        // 测试DP
        startTime = System.nanoTime();
        int dpResult = problem.coinChangeDp(coins, coins.length, amount);
        endTime = System.nanoTime();
        long dpTime = endTime - startTime;
        
        // 测试优化DP
        startTime = System.nanoTime();
        int optimizedResult = problem.coinChangeDpOptimized(coins, coins.length, amount);
        endTime = System.nanoTime();
        long optimizedTime = endTime - startTime;
        
        // 验证结果一致性
        assertEquals(memoResult, dpResult);
        assertEquals(dpResult, optimizedResult);
        
        // 输出性能信息（可选）
        System.out.println("Memoization time: " + memoTime + " ns");
        System.out.println("DP time: " + dpTime + " ns");
        System.out.println("Optimized DP time: " + optimizedTime + " ns");
        System.out.println("Result: " + memoResult + " combinations");
    }

    /**
     * 测试边界情况：金额为1
     * coins = [1, 2, 5], amount = 1
     * 只有一种组合：1
     */
    @Test
    void testAmountOne() {
        int[] coins = {1, 2, 5};
        int amount = 1;
        int expected = 1;

        assertEquals(expected, problem.coinChange(coins, amount));
        assertEquals(expected, problem.coinChangeDfs(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(expected, problem.coinChangeDpOptimized(coins, coins.length, amount));
    }

    /**
     * 测试所有硬币面值相同
     * coins = [3, 3, 3], amount = 9
     * 应该只有一种组合：3 + 3 + 3
     */
    @Test
    void testAllSameCoins() {
        int[] coins = {3, 3, 3};
        int amount = 9;
        
        int result = problem.coinChangeDfs(coins, coins.length, amount);
        
        assertEquals(result, problem.coinChangeMemoization(coins, coins.length, amount));
        assertEquals(result, problem.coinChangeDp(coins, coins.length, amount));
        assertEquals(result, problem.coinChangeDpOptimized(coins, coins.length, amount));
        
        // 结果应该大于0
        assertTrue(result > 0);
    }
}