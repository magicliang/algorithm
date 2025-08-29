package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 硬币兑换组合问题的单元测试
 * 
 * @author magicliang
 * @date 2025-08-29
 */
public class CoinsChangeCombinationProblemTest {
    
    private CoinsChangeCombinationProblem solution;
    
    @BeforeEach
    void setUp() {
        solution = new CoinsChangeCombinationProblem();
    }
    
    @Test
    void testBasicCase() {
        // 测试基本情况：硬币[1,2,5]，金额5
        // 组合方式：5, 2+2+1, 2+1+1+1, 1+1+1+1+1
        int[] coins = {1, 2, 5};
        int amount = 5;
        int expected = 4;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "硬币[1,2,5]组成金额5应该有4种组合");
    }
    
    @Test
    void testNoSolution() {
        // 测试无解情况：只有面值2的硬币，无法组成奇数金额3
        int[] coins = {2};
        int amount = 3;
        int expected = 0;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "硬币[2]无法组成金额3");
    }
    
    @Test
    void testZeroAmount() {
        // 测试金额为0的情况：只有一种方式（不选任何硬币）
        int[] coins = {1, 2, 5};
        int amount = 0;
        int expected = 1;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "金额为0时应该有1种组合（空组合）");
    }
    
    @Test
    void testSingleCoin() {
        // 测试单一硬币情况：只有面值1的硬币
        int[] coins = {1};
        int amount = 10;
        int expected = 1;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "只有面值1的硬币组成金额10只有1种方式");
    }
    
    @Test
    void testLargerAmount() {
        // 测试较大金额：硬币[1,2,5]，金额11
        int[] coins = {1, 2, 5};
        int amount = 11;
        int expected = 11;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "硬币[1,2,5]组成金额11应该有11种组合");
    }
    
    @Test
    void testSmallExample() {
        // 测试小规模示例：硬币[1,2]，金额3
        // 组合方式：1+1+1, 1+2
        int[] coins = {1, 2};
        int amount = 3;
        int expected = 2;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "硬币[1,2]组成金额3应该有2种组合");
    }
    
    @Test
    void testEdgeCases() {
        // 测试边界情况
        
        // 空硬币数组
        assertEquals(0, solution.coinChange(new int[]{}, 5), "空硬币数组应返回0");
        
        // 硬币面值大于目标金额
        assertEquals(0, solution.coinChange(new int[]{10}, 5), "硬币面值大于目标金额应返回0");
    }
    
    @Test
    void testComplexCase() {
        // 测试复杂情况：多种硬币面值
        int[] coins = {1, 3, 4};
        int amount = 6;
        // 组合方式：
        // 1+1+1+1+1+1 (6个1)
        // 1+1+1+3 (3个1+1个3)
        // 3+3 (2个3)
        // 1+1+4 (2个1+1个4)
        int expected = 4;
        int actual = solution.coinChange(coins, amount);
        assertEquals(expected, actual, "硬币[1,3,4]组成金额6应该有4种组合");
    }
    
    @Test
    void testDirectDfsCall() {
        // 直接测试DFS方法
        int[] coins = {1, 2};
        
        // 测试各种参数组合
        assertEquals(1, solution.coinChangeDfs(coins, 2, 0), "目标金额为0应返回1");
        assertEquals(0, solution.coinChangeDfs(coins, 0, 5), "没有硬币可选应返回0");
        assertEquals(2, solution.coinChangeDfs(coins, 2, 3), "硬币[1,2]组成金额3应有2种方式");
    }
}