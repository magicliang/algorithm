package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 零钱兑换问题测试类
 * 
 * 【测试策略】：
 * 1. 基本功能测试：验证算法能正确计算最少硬币数量
 * 2. 边界条件测试：目标金额为0、无解情况等
 * 3. 特殊场景测试：单一硬币、大面额硬币等
 * 4. 复杂场景测试：多种硬币组合的最优选择
 * 5. 一致性测试：验证DFS和记忆化版本结果一致
 * 
 * 【零钱兑换问题特点】：
 * - 完全背包变种：每种硬币可以使用无限次
 * - 优化目标：最小化硬币数量（而不是最大化价值）
 * - 无解处理：返回-1表示无法凑出目标金额
 * - 贪心陷阱：不能简单地优先选择大面额硬币
 * 
 * @author magicliang
 * @date 2025-08-29
 */
public class CoinsProblemTest {

    private CoinsProblem solver;

    @BeforeEach
    public void setUp() {
        solver = new CoinsProblem();
    }

    /**
     * 测试基本的零钱兑换功能
     * 
     * 【测试场景】：经典的硬币组合[1,2,5]，目标金额11
     * 【最优解分析】：
     * - 贪心选择：5+5+1 = 3个硬币
     * - 实际最优：5+5+1 = 3个硬币（贪心恰好正确）
     * 【验证要点】：基本的递归逻辑和状态转移
     */
    @Test
    public void testBasicCoinChange() {
        int[] coins = {1, 2, 5};
        int targetAmount = 11;
        
        int dfsResult = solver.coinChangeDfs(coins, 3, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 3, targetAmount);
        
        assertEquals(3, dfsResult);
        assertEquals(3, memoResult);
        assertEquals(dfsResult, memoResult); // 两种方法结果应该一致
    }

    /**
     * 测试目标金额为0的边界情况
     */
    @Test
    public void testZeroAmount() {
        int[] coins = {1, 2, 5};
        int targetAmount = 0;
        
        int dfsResult = solver.coinChangeDfs(coins, 3, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 3, targetAmount);
        
        assertEquals(0, dfsResult);
        assertEquals(0, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试无解的情况
     */
    @Test
    public void testImpossibleCase() {
        int[] coins = {2, 4};
        int targetAmount = 3;
        
        int dfsResult = solver.coinChangeDfs(coins, 2, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 2, targetAmount);
        
        assertEquals(-1, dfsResult);
        assertEquals(-1, memoResult);
        assertEquals(dfsResult, memoResult); // 验证无解情况的一致性
    }

    /**
     * 测试贪心算法会失败的经典案例
     */
    @Test
    public void testGreedyTrap() {
        int[] coins = {1, 3, 4};
        int targetAmount = 6;
        
        // 最优解应该是3+3=2个硬币，而不是贪心的4+1+1=3个硬币
        int dfsResult = solver.coinChangeDfs(coins, 3, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 3, targetAmount);
        
        assertEquals(2, dfsResult);
        assertEquals(2, memoResult);
        assertEquals(dfsResult, memoResult); // 验证两种方法一致性
    }

    /**
     * 测试单一硬币的情况
     */
    @Test
    public void testSingleCoinType() {
        int[] coins = {2};
        int targetAmount = 6;
        
        int dfsResult = solver.coinChangeDfs(coins, 1, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 1, targetAmount);
        
        assertEquals(3, dfsResult);
        assertEquals(3, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试单一硬币无解的情况
     */
    @Test
    public void testSingleCoinImpossible() {
        int[] coins = {2};
        int targetAmount = 3;
        
        int dfsResult = solver.coinChangeDfs(coins, 1, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 1, targetAmount);
        
        assertEquals(-1, dfsResult);
        assertEquals(-1, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试大面额硬币的处理
     */
    @Test
    public void testLargeDenomination() {
        int[] coins = {1, 5, 10, 25};
        int targetAmount = 30;
        
        int dfsResult = solver.coinChangeDfs(coins, 4, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 4, targetAmount);
        
        assertEquals(2, dfsResult); // 25 + 5
        assertEquals(2, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试硬币面额大于目标金额的情况
     */
    @Test
    public void testCoinsLargerThanTarget() {
        int[] coins = {5, 10};
        int targetAmount = 3;
        
        int dfsResult = solver.coinChangeDfs(coins, 2, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 2, targetAmount);
        
        assertEquals(-1, dfsResult);
        assertEquals(-1, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试复杂的最优选择场景
     */
    @Test
    public void testComplexOptimalChoice() {
        int[] coins = {1, 2, 5, 10};
        int targetAmount = 18;
        
        int dfsResult = solver.coinChangeDfs(coins, 4, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 4, targetAmount);
        
        assertEquals(4, dfsResult); // 10 + 5 + 2 + 1
        assertEquals(4, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试目标金额为1的最小情况
     */
    @Test
    public void testMinimalAmount() {
        int[] coins = {1, 2, 5};
        int targetAmount = 1;
        
        int dfsResult = solver.coinChangeDfs(coins, 3, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 3, targetAmount);
        
        assertEquals(1, dfsResult);
        assertEquals(1, memoResult);
        assertEquals(dfsResult, memoResult);
    }

    /**
     * 测试没有面额为1的硬币的复杂情况
     */
    @Test
    public void testWithoutUnitCoin() {
        int[] coins = {2, 3, 5};
        int targetAmount = 9;
        
        int dfsResult = solver.coinChangeDfs(coins, 3, targetAmount);
        int memoResult = solver.coinChangeMemoization(coins, 3, targetAmount);
        
        assertEquals(3, dfsResult); // 3 + 3 + 3
        assertEquals(3, memoResult);
        assertEquals(dfsResult, memoResult);
    }
}