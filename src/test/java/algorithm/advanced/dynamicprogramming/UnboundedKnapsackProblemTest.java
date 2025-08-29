package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 完全背包问题测试类
 * 
 * 【测试策略说明】：
 * 1. 边界条件测试：容量为0、物品数为0等极端情况
 * 2. 基本功能测试：验证算法核心逻辑的正确性
 * 3. 特殊场景测试：物品过重、单一物品等特殊情况
 * 4. 算法一致性测试：验证不同实现方法的结果一致性
 * 5. 性能对比测试：通过测试用例体现记忆化的优势
 * 
 * 【完全背包测试要点】：
 * - 验证物品可重复选择的特性
 * - 确保最优解计算的正确性
 * - 测试不同容量下的解空间探索
 * 
 * @author magicliang
 * @date 2025-08-29
 */
public class UnboundedKnapsackProblemTest {

    /** 被测试的完全背包问题求解器 */
    private final UnboundedKnapsackProblem solver = new UnboundedKnapsackProblem();

    /**
     * 测试基本情况：验证完全背包的核心特性
     * 
     * 【测试场景】：3个物品，背包容量为4
     * 物品信息：
     * - 物品1：重量1，价值1，性价比1.0
     * - 物品2：重量2，价值4，性价比2.0 ← 最优选择
     * - 物品3：重量3，价值4，性价比1.33
     * 
     * 【决策分析】：
     * - 选择策略：重复选择物品2（重量2，价值4）
     * - 容量4可以装2个物品2：2×2=4（重量），2×4=8（价值）
     * - 其他组合都不如这个优秀
     * 
     * 【验证要点】：物品可重复选择，算法能找到最优解
     */
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

    /**
     * 测试边界条件：容量为0
     * 
     * 【测试目的】：验证算法对边界条件的处理
     * 【预期行为】：无论有多少物品，容量为0时价值必须为0
     * 【算法验证】：测试递归终止条件的正确性
     */
    @Test
    public void testZeroCapacity() {
        // 测试容量为0的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 3, 0);
        assertEquals(0, result);
    }

    /**
     * 测试边界条件：物品数为0
     * 
     * 【测试目的】：验证无物品可选时的算法行为
     * 【预期行为】：无论背包容量多大，没有物品时价值必须为0
     * 【算法验证】：测试另一个递归终止条件的正确性
     */
    @Test
    public void testZeroItems() {
        // 测试没有物品的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemDfs(wgt, val, 0, 10);
        assertEquals(0, result);
    }

    /**
     * 测试单一物品的重复选择特性
     * 
     * 【测试场景】：只有1个物品，验证完全背包的核心特性
     * 物品信息：重量2，价值3
     * 背包容量：5
     * 
     * 【决策分析】：
     * - 容量5可以装2个该物品：2×2=4（重量），2×3=6（价值）
     * - 剩余容量1无法再装入物品
     * 
     * 【验证要点】：单一物品的重复选择逻辑
     */
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

    /**
     * 测试物品过重的情况
     * 
     * 【测试场景】：所有物品重量都超过背包容量
     * 物品重量：{5, 6, 7}，背包容量：4
     * 
     * 【预期行为】：
     * - 所有物品都无法放入背包
     * - 算法应该正确处理这种情况，返回0
     * 
     * 【验证要点】：重量约束的正确处理
     */
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

    /**
     * 测试最优选择策略：高性价比物品的重复选择
     * 
     * 【测试场景】：验证算法能识别并重复选择最优物品
     * 物品信息：
     * - 物品1：重量1，价值15，性价比15.0 ← 最优选择
     * - 物品2：重量3，价值20，性价比6.67
     * - 物品3：重量4，价值30，性价比7.5
     * 
     * 【决策分析】：
     * - 容量4全部用来装物品1：4个物品1，总价值60
     * - 这比任何其他组合都要优秀
     * 
     * 【验证要点】：算法的最优性和贪心特性
     */
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

    /**
     * 测试较大容量下的算法表现
     * 
     * 【测试场景】：多物品、较大容量的综合测试
     * 物品信息：{重量2,价值3}, {重量3,价值4}, {重量4,价值5}, {重量5,价值6}
     * 背包容量：8
     * 
     * 【决策分析】：
     * - 物品1性价比：3/2=1.5 ← 最优
     * - 物品2性价比：4/3=1.33
     * - 物品3性价比：5/4=1.25  
     * - 物品4性价比：6/5=1.2
     * 
     * 最优策略：选择4个物品1，总重量8，总价值12
     * 
     * 【验证要点】：多物品场景下的最优决策
     */
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

    // ==================== 记忆化方法测试 ====================

    /**
     * 测试记忆化方法的基本功能
     * 
     * 【测试目的】：验证记忆化实现的正确性
     * 【测试数据】：与DFS方法使用相同数据，确保结果一致
     * 【性能优势】：记忆化避免重复计算，时间复杂度从指数级降到O(n×capacity)
     */
    @Test
    public void testMemoizationBasicCase() {
        // 测试记忆化方法的基本情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        int capacity = 4;
        
        int result = solver.unboundedKnapsackProblemMemoization(wgt, val, 3, capacity);
        assertEquals(8, result);
    }

    /**
     * 测试记忆化方法的边界条件处理
     * 
     * 【测试重点】：验证记忆化在边界条件下的正确性
     * 【缓存验证】：边界条件不需要缓存，但要正确返回
     */
    @Test
    public void testMemoizationZeroCapacity() {
        // 测试记忆化方法容量为0的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemMemoization(wgt, val, 3, 0);
        assertEquals(0, result);
    }

    /**
     * 测试记忆化方法的最优选择能力
     * 
     * 【测试重点】：验证记忆化不影响最优解的计算
     * 【缓存效果】：重复子问题被缓存，避免重复计算
     */
    @Test
    public void testMemoizationOptimalSelection() {
        // 测试记忆化方法的最优选择
        int[] wgt = {1, 3, 4};
        int[] val = {15, 20, 30};
        int capacity = 4;
        
        int result = solver.unboundedKnapsackProblemMemoization(wgt, val, 3, capacity);
        assertEquals(60, result);
    }

    /**
     * 测试两种算法实现的一致性
     * 
     * 【测试目的】：确保DFS和记忆化方法产生相同结果
     * 【质量保证】：通过对比验证算法实现的正确性
     * 【性能对比】：
     * - DFS：时间复杂度O(2^n)，空间复杂度O(n)
     * - 记忆化：时间复杂度O(n×capacity)，空间复杂度O(n×capacity)
     * 
     * 【实际应用】：记忆化在大规模问题中有显著性能优势
     */
    @Test
    public void testBothMethodsConsistency() {
        // 测试两种方法结果的一致性
        int[] wgt = {2, 3, 4, 5};
        int[] val = {3, 4, 5, 6};
        int capacity = 8;
        
        int dfsResult = solver.unboundedKnapsackProblemDfs(wgt, val, 4, capacity);
        int memoResult = solver.unboundedKnapsackProblemMemoization(wgt, val, 4, capacity);
        
        assertEquals(dfsResult, memoResult, "DFS和记忆化方法应该返回相同结果");
    }

    // ==================== 动态规划方法测试 ====================

    /**
     * 测试动态规划方法的基本功能
     * 
     * 【测试目的】：验证DP实现的正确性
     * 【DP优势】：
     * - 自底向上计算，避免递归开销
     * - 时间复杂度O(n×capacity)，与记忆化相同但常数更小
     * - 空间复杂度O(n×capacity)，可优化为O(capacity)
     */
    @Test
    public void testDpBasicCase() {
        // 测试DP方法的基本情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        int capacity = 4;
        
        int result = solver.unboundedKnapsackProblemDp(wgt, val, 3, capacity);
        assertEquals(8, result);
    }

    /**
     * 测试DP方法的边界条件处理
     * 
     * 【测试重点】：验证DP表的初始化和边界处理
     */
    @Test
    public void testDpZeroCapacity() {
        // 测试DP方法容量为0的情况
        int[] wgt = {1, 2, 3};
        int[] val = {1, 4, 4};
        
        int result = solver.unboundedKnapsackProblemDp(wgt, val, 3, 0);
        assertEquals(0, result);
    }

    /**
     * 测试DP方法的最优选择能力
     * 
     * 【测试重点】：验证DP状态转移的正确性
     */
    @Test
    public void testDpOptimalSelection() {
        // 测试DP方法的最优选择
        int[] wgt = {1, 3, 4};
        int[] val = {15, 20, 30};
        int capacity = 4;
        
        int result = solver.unboundedKnapsackProblemDp(wgt, val, 3, capacity);
        assertEquals(60, result);
    }

    /**
     * 测试三种算法实现的完全一致性
     * 
     * 【测试目的】：确保DFS、记忆化、DP三种方法产生相同结果
     * 【算法对比】：
     * - DFS：递归实现，时间复杂度O(2^n)，适合小规模问题
     * - 记忆化：自顶向下DP，时间复杂度O(n×capacity)，递归有开销
     * - DP：自底向上DP，时间复杂度O(n×capacity)，无递归开销
     * 
     * 【实际选择】：
     * - 小规模：DFS简单直观
     * - 中等规模：记忆化易于理解
     * - 大规模：DP性能最优
     */
    @Test
    public void testAllMethodsConsistency() {
        // 测试三种方法结果的一致性
        int[] wgt = {2, 3, 4, 5};
        int[] val = {3, 4, 5, 6};
        int capacity = 8;
        
        int dfsResult = solver.unboundedKnapsackProblemDfs(wgt, val, 4, capacity);
        int memoResult = solver.unboundedKnapsackProblemMemoization(wgt, val, 4, capacity);
        int dpResult = solver.unboundedKnapsackProblemDp(wgt, val, 4, capacity);
        
        assertEquals(dfsResult, memoResult, "DFS和记忆化方法应该返回相同结果");
        assertEquals(memoResult, dpResult, "记忆化和DP方法应该返回相同结果");
        assertEquals(dfsResult, dpResult, "DFS和DP方法应该返回相同结果");
    }

    /**
     * 测试DP方法在复杂场景下的表现
     * 
     * 【测试场景】：多物品、不同性价比的综合测试
     * 【验证要点】：DP填表过程的正确性和最优子结构性质
     */
    @Test
    public void testDpComplexCase() {
        // 测试DP方法的复杂情况
        int[] wgt = {1, 2, 3, 4};
        int[] val = {2, 3, 4, 5};
        int capacity = 6;
        
        // 最优解分析：
        // 物品1性价比：2/1=2.0
        // 物品2性价比：3/2=1.5  
        // 物品3性价比：4/3=1.33
        // 物品4性价比：5/4=1.25
        // 最优策略：选择6个物品1，总价值12
        
        int result = solver.unboundedKnapsackProblemDp(wgt, val, 4, capacity);
        assertEquals(12, result);
    }
}
