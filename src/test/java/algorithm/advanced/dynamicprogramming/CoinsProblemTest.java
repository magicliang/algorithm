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
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 3, targetAmount);
        assertEquals(3, result);
    }

    /**
     * 测试目标金额为0的边界情况
     * 
     * 【测试场景】：任意硬币组合，目标金额为0
     * 【预期行为】：不需要任何硬币，返回0
     * 【验证要点】：递归终止条件的正确性
     */
    @Test
    public void testZeroAmount() {
        int[] coins = {1, 2, 5};
        int targetAmount = 0;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 3, targetAmount);
        assertEquals(0, result);
    }

    /**
     * 测试无解的情况
     * 
     * 【测试场景】：硬币面额[2,4]，目标金额3
     * 【分析过程】：
     * - 所有硬币都是偶数面额
     * - 目标金额3是奇数
     * - 无论如何组合都无法凑出奇数金额
     * 【验证要点】：无解情况的正确识别和-1返回值
     */
    @Test
    public void testImpossibleCase() {
        int[] coins = {2, 4};
        int targetAmount = 3;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 2, targetAmount);
        assertEquals(-1, result);
    }

    /**
     * 测试单一硬币的情况
     * 
     * 【测试场景】：只有面额为2的硬币，目标金额6
     * 【最优解分析】：需要3个硬币（2+2+2=6）
     * 【验证要点】：硬币重复使用的逻辑
     */
    @Test
    public void testSingleCoinType() {
        int[] coins = {2};
        int targetAmount = 6;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 1, targetAmount);
        assertEquals(3, result);
    }

    /**
     * 测试单一硬币无解的情况
     * 
     * 【测试场景】：只有面额为2的硬币，目标金额3
     * 【分析过程】：2的倍数无法凑出3
     * 【验证要点】：单一硬币类型的无解识别
     */
    @Test
    public void testSingleCoinImpossible() {
        int[] coins = {2};
        int targetAmount = 3;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 1, targetAmount);
        assertEquals(-1, result);
    }

    /**
     * 测试贪心算法会失败的经典案例
     * 
     * 【测试场景】：硬币面额[1,3,4]，目标金额6
     * 【贪心陷阱】：
     * - 贪心选择：4+1+1 = 3个硬币
     * - 实际最优：3+3 = 2个硬币
     * 【验证要点】：动态规划能找到真正的最优解，避免贪心陷阱
     */
    @Test
    public void testGreedyTrap() {
        int[] coins = {1, 3, 4};
        int targetAmount = 6;
        
        // 最优解应该是3+3=2个硬币，而不是贪心的4+1+1=3个硬币
        int result = solver.unboundedKnapsackProblemDfs(coins, 3, targetAmount);
        assertEquals(2, result);
    }

    /**
     * 测试大面额硬币的处理
     * 
     * 【测试场景】：包含大面额硬币[1,5,10,25]，目标金额30
     * 【最优解分析】：25+5 = 2个硬币
     * 【验证要点】：算法能正确处理大面额硬币的组合
     */
    @Test
    public void testLargeDenomination() {
        int[] coins = {1, 5, 10, 25};
        int targetAmount = 30;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 4, targetAmount);
        assertEquals(2, result); // 25 + 5
    }

    /**
     * 测试硬币面额大于目标金额的情况
     * 
     * 【测试场景】：硬币面额[5,10]，目标金额3
     * 【分析过程】：所有硬币面额都大于目标金额，无法使用
     * 【验证要点】：剪枝逻辑的正确性
     */
    @Test
    public void testCoinsLargerThanTarget() {
        int[] coins = {5, 10};
        int targetAmount = 3;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 2, targetAmount);
        assertEquals(-1, result);
    }

    /**
     * 测试复杂的最优选择场景
     * 
     * 【测试场景】：硬币面额[1,2,5,10]，目标金额18
     * 【最优解分析】：
     * - 可能的组合：10+5+2+1, 10+8个1, 9个2等
     * - 最优解：10+5+2+1 = 4个硬币
     * 【验证要点】：复杂场景下的最优解计算
     */
    @Test
    public void testComplexOptimalChoice() {
        int[] coins = {1, 2, 5, 10};
        int targetAmount = 18;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 4, targetAmount);
        assertEquals(4, result); // 10 + 5 + 2 + 1
    }

    /**
     * 测试目标金额为1的最小情况
     * 
     * 【测试场景】：硬币面额[1,2,5]，目标金额1
     * 【最优解分析】：直接使用1个面额为1的硬币
     * 【验证要点】：最小目标金额的处理
     */
    @Test
    public void testMinimalAmount() {
        int[] coins = {1, 2, 5};
        int targetAmount = 1;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 3, targetAmount);
        assertEquals(1, result);
    }

    /**
     * 测试没有面额为1的硬币的复杂情况
     * 
     * 【测试场景】：硬币面额[2,3,5]，目标金额9
     * 【最优解分析】：
     * - 可能组合：3+3+3, 2+2+5, 2+2+2+3等
     * - 最优解：3+3+3 = 3个硬币
     * 【验证要点】：没有万能硬币(面额1)时的最优解计算
     */
    @Test
    public void testWithoutUnitCoin() {
        int[] coins = {2, 3, 5};
        int targetAmount = 9;
        
        int result = solver.unboundedKnapsackProblemDfs(coins, 3, targetAmount);
        assertEquals(3, result); // 3 + 3 + 3
    }
}