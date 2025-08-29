package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 零钱兑换问题测试类
 * 
 * 测试覆盖：
 * 1. 基本功能测试
 * 2. 边界条件测试
 * 3. 无解情况测试
 * 4. 性能对比测试
 */
public class CoinsProblemTest {

    private final CoinsProblem coinsProblem = new CoinsProblem();

    /**
     * 测试基本功能：正常情况下的零钱兑换
     */
    @Test
    public void testBasicCoinChange() {
        int[] coins = {1, 3, 4};
        int targetAmount = 6;
        int coinTypes = coins.length;

        // 测试所有方法得到相同结果
        int dfsResult = coinsProblem.coinChangeDfs(coins, coinTypes, targetAmount);
        int memoResult = coinsProblem.coinChangeMemoization(coins, coinTypes, targetAmount);
        int dpResult = coinsProblem.coinChangeDp(coins, coinTypes, targetAmount);
        int optimizedResult = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);

        // 期望结果：6 = 3 + 3，需要2个硬币
        assertEquals(2, dfsResult);
        assertEquals(2, memoResult);
        assertEquals(2, dpResult);
        assertEquals(2, optimizedResult);
    }

    /**
     * 测试经典案例：[1,2,5] 凑出 11
     */
    @Test
    public void testClassicCase() {
        int[] coins = {1, 2, 5};
        int targetAmount = 11;
        int coinTypes = coins.length;

        int optimizedResult = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        int dpResult = coinsProblem.coinChangeDp(coins, coinTypes, targetAmount);

        // 期望结果：11 = 5 + 5 + 1，需要3个硬币
        assertEquals(3, optimizedResult);
        assertEquals(3, dpResult);
    }

    /**
     * 测试边界条件：目标金额为0
     */
    @Test
    public void testZeroAmount() {
        int[] coins = {1, 3, 4};
        int targetAmount = 0;
        int coinTypes = coins.length;

        int result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        assertEquals(0, result);
    }

    /**
     * 测试无解情况：无法凑出目标金额
     */
    @Test
    public void testNoSolution() {
        int[] coins = {2, 4};  // 只有偶数面额
        int targetAmount = 3;   // 奇数目标金额
        int coinTypes = coins.length;

        int optimizedResult = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        int dpResult = coinsProblem.coinChangeDp(coins, coinTypes, targetAmount);

        assertEquals(-1, optimizedResult);
        assertEquals(-1, dpResult);
    }

    /**
     * 测试单一硬币情况
     */
    @Test
    public void testSingleCoin() {
        int[] coins = {5};
        int targetAmount = 15;
        int coinTypes = coins.length;

        int result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        // 期望结果：15 = 5 + 5 + 5，需要3个硬币
        assertEquals(3, result);

        // 测试无法整除的情况
        targetAmount = 7;
        result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        assertEquals(-1, result);
    }

    /**
     * 测试大面额硬币
     */
    @Test
    public void testLargeCoin() {
        int[] coins = {1, 5, 10, 25};
        int targetAmount = 30;
        int coinTypes = coins.length;

        int result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        // 期望结果：30 = 25 + 5，需要2个硬币
        assertEquals(2, result);
    }

    /**
     * 测试异常输入
     */
    @Test
    public void testInvalidInput() {
        int[] coins = {1, 3, 4};
        
        // 测试硬币种类为0
        int result = coinsProblem.coinChangeDpOptimization(coins, 0, 5);
        assertEquals(-1, result);

        // 测试负数硬币种类
        result = coinsProblem.coinChangeDpOptimization(coins, -1, 5);
        assertEquals(-1, result);

        // 测试负数目标金额
        result = coinsProblem.coinChangeDpOptimization(coins, coins.length, -1);
        assertEquals(-1, result);
    }

    /**
     * 测试性能：比较优化前后的结果一致性
     */
    @Test
    public void testOptimizationConsistency() {
        int[] coins = {1, 5, 10, 21, 25};
        int[] testAmounts = {0, 1, 11, 23, 63, 100};

        for (int amount : testAmounts) {
            int dpResult = coinsProblem.coinChangeDp(coins, coins.length, amount);
            int optimizedResult = coinsProblem.coinChangeDpOptimization(coins, coins.length, amount);
            
            assertEquals(dpResult, optimizedResult, 
                String.format("Results differ for amount %d: dp=%d, optimized=%d", 
                    amount, dpResult, optimizedResult));
        }
    }

    /**
     * 测试贪心算法失效的经典案例
     */
    @Test
    public void testGreedyFailureCase() {
        int[] coins = {1, 3, 4};
        int targetAmount = 6;
        int coinTypes = coins.length;

        int result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        
        // 贪心算法会选择：4 + 1 + 1 = 3个硬币
        // 动态规划找到最优解：3 + 3 = 2个硬币
        assertEquals(2, result);
    }

    /**
     * 测试完全背包特性：同一硬币可重复使用
     */
    @Test
    public void testUnlimitedCoins() {
        int[] coins = {2};
        int targetAmount = 10;
        int coinTypes = coins.length;

        int result = coinsProblem.coinChangeDpOptimization(coins, coinTypes, targetAmount);
        // 期望结果：10 = 2 + 2 + 2 + 2 + 2，需要5个硬币
        assertEquals(5, result);
    }
}