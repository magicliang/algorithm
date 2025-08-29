package algorithm.advanced.dynamicprogramming;


import java.util.Arrays;

/**
 * project name: algorithm
 *
 * description: 零钱兑换问题（完全背包变种）
 *
 * 【问题特点】：
 * 与传统背包问题的关键区别在于约束条件：
 * - 传统背包：容量 ≤ 限制值（不超过约束）
 * - 零钱兑换：总金额 = 目标值（等值约束）
 * 
 * 这种等值约束导致可能出现"无解"情况，需要特殊处理返回-1。
 *
 * 【状态定义】：
 * dp[i][amount] 表示用前i种硬币凑成金额amount所需的最少硬币数
 * 
 * 【状态转移】：
 * dp[i][amount] = min(dp[i-1][amount], dp[i][amount-coins[i-1]] + 1)
 * - 不选当前硬币：dp[i-1][amount]
 * - 选择当前硬币：dp[i][amount-coins[i-1]] + 1（可重复选择）
 * 
 * 【边界条件】：
 * - dp[i][0] = 0（金额为0不需要硬币）
 * - dp[0][amount] = -1（没有硬币无法凑出正金额）
 *
 * @author magicliang
 *
 *         date: 2025-08-29 15:10
 */
public class CoinsProblem {

    /**
     * 使用深度优先搜索解决零钱兑换问题
     * 
     * 【问题定义】：
     * 给定不同面额的硬币coins和一个总金额targetAmount，
     * 计算可以凑成总金额所需的最少硬币个数。
     * 如果不能凑成总金额，返回-1。
     * 
     * 【状态转移方程】：
     * dp[i][amount] = min(dp[i-1][amount], dp[i][amount-coins[i-1]] + 1)
     * 
     * 【参数说明】：
     * @param coins 硬币面额数组（0-based索引）
     * @param i 当前考虑的硬币种类数量（1-based，表示前i种硬币）
     * @param targetAmount 目标金额
     * @return 最少硬币数量，无解时返回-1
     * 
     * 【算法特点】：
     * - 每种硬币可以使用无限次（完全背包特性）
     * - 目标是最小化硬币数量（而不是最大化价值）
     * - 使用-1表示无解状态
     */
    // 返回值是达成 targetAmount 的硬币数量，i 是硬币的搜索规模，即使是 dfs 也需要先写 dp 方程才好搜索
    public int unboundedKnapsackProblemDfs(int[] coins, int i, int targetAmount) {
        // 【递归终止条件1】：目标金额为0，不需要任何硬币
        if (targetAmount == 0) {
            return 0;
        }

        // 【递归终止条件2】：没有硬币可用但目标金额不为0，无解
        // 零钱兑换问题的特殊性：目标约束是等于某个数值（总价）而不是不小于某个值（背包容量）
        // 这种等值约束会出现凑不出的情况，与一般背包问题不同，需要返回异常值-1
        if (i == 0) {
            return -1;
        }

        // 【选择1】：不使用当前硬币coins[i-1]，问题规模缩减为前i-1种硬币
        int subProblemChoice = unboundedKnapsackProblemDfs(coins, i - 1, targetAmount);
        
        // 【剪枝优化】：当前硬币面额大于目标金额，无法使用
        // 易错的点：容易被忘记的资源约束，当前不能再做选择了，因为单个选择就突破资源限制
        if (coins[i - 1] > targetAmount) {
            return subProblemChoice;
        }

        // 【选择2】：使用当前硬币coins[i-1]，目标金额减少，但硬币种类不变（可重复使用）
        // 【易错的点】：处理"无解"情况的逻辑
        // 
        // 在零钱兑换问题中，-1表示"无法凑出目标金额"
        // 我们有两个选择：
        // 1. subProblemChoice：不使用当前硬币的方案
        // 2. currentProblemChoice：使用当前硬币的方案
        // 
        // 但是这两个选择都可能返回-1（无解），所以需要分情况讨论：
        // - 如果使用当前硬币无解，那只能选择不使用的方案
        // - 如果不使用当前硬币无解，那只能选择使用的方案  
        // - 如果两个都有解，选择硬币数量更少的方案
        // 
        // 这就是为什么要分别检查每种情况的原因
        int currentProblemChoice = unboundedKnapsackProblemDfs(coins, i, targetAmount - coins[i - 1]);
        
        // 【情况1】：使用当前硬币的方案无解，只能选择不使用的方案
        if (currentProblemChoice == -1) {
            return subProblemChoice;
        }

        // 【重要】：使用当前硬币需要额外消耗1个硬币
        currentProblemChoice += 1;
        
        // 【情况2】：不使用当前硬币的方案无解，只能选择使用的方案
        if (subProblemChoice == -1) {
            return currentProblemChoice;
        }

        // 【情况3】：两种方案都有解，选择硬币数量更少的方案
        return Math.min(subProblemChoice, currentProblemChoice);
    }

    public int unboundedKnapsackProblemMemoization(int[] coins, int i, int targetAmount) {

        // 创建记忆化数组
        int[][] memo = new int[i + 1][targetAmount + 1];

        // 初始化记忆化数组的目的是为了让"未计算"和"合法值0"分开
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        return unboundedKnapsackProblemMemoization(coins, i, targetAmount, memo);
    }

    /**
     * 【记忆化搜索的核心思想】：
     * 将DFS的指数级复杂度O(2^(n+targetAmount))优化为多项式级O(n*targetAmount)
     * 通过缓存已计算的子问题结果，避免重复计算
     * 
     * 【缓存设计】：
     * memo[i][amount]存储"使用前i种硬币凑出amount金额"的最优解
     * -1表示该子问题尚未计算
     * 其他值表示已计算的最优解（包括-1表示无解）
     * 
     * 【状态传播机制】：
     * 1. 缓存命中：直接返回已存储的结果
     * 2. 缓存未命中：计算后存储并返回
     * 3. 无解状态传播：-1作为特殊值在递归中正确传播
     * 
     * 【与DFS的等价性】：
     * 记忆化搜索与DFS的决策树结构完全相同，只是通过缓存避免了重复遍历
     * 
     * @param coins 硬币面额数组
     * @param i 当前考虑的硬币种类数量（1-based）
     * @param targetAmount 目标金额
     * @param memo 记忆化缓存数组
     * @return 最少硬币数量，无解时返回-1
     */
    private int unboundedKnapsackProblemMemoization(int[] coins, int i, int targetAmount, int[][] memo) {
        if (targetAmount == 0) {
            return 0;
        }

        // 如果硬币已经为0了，targetAmount 不为 0，说明无解
        if (i == 0) {
            return -1;
        }

        // targetAmount 不会小于0，下面会剪枝

        // 命中缓存
        if (memo[i][targetAmount] != -1) {
            return memo[i][targetAmount];
        }

        int subProblemChoice = unboundedKnapsackProblemMemoization(coins, i - 1, targetAmount, memo);
        memo[i][targetAmount] = subProblemChoice;

        // 剪枝来了：不能做出选择
        if (coins[i - 1] > targetAmount) {
            return memo[i][targetAmount];
        }

        int currentProblemChoice = unboundedKnapsackProblemMemoization(coins, i, targetAmount - coins[i - 1], memo);
        // 不能用 min 比对 -1，所以还是返回 subProblemChoice，不要把 + 1加上去
        if (currentProblemChoice == -1) {
            return memo[i][targetAmount];
        }
        currentProblemChoice += 1;
        
        // 【缓存更新策略】：
        // 1. 如果subProblemChoice是无解(-1)，直接使用currentProblemChoice
        // 2. 如果currentProblemChoice是无解(-1)，保持subProblemChoice
        // 3. 两者都有解时，选择较小的值
        if (memo[i][targetAmount] == -1) {
            memo[i][targetAmount] = currentProblemChoice;
            return memo[i][targetAmount];
        }

        memo[i][targetAmount] = Math.min(subProblemChoice, currentProblemChoice);
        return memo[i][targetAmount];
    }

}