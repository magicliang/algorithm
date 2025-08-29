package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 零钱兑换问题
 *
 * 这道题的目标仍然是求解某个特定的 dp 值，但是硬币数量等于 dp值。
 * 其余状态分别是
 *
 * 1. 问题的规模：i
 * 2. 问题的要求：总面值必须等于一个值 amount
 *
 * 而这两个问题都是可以“收减”的，这构成了这类 dp 问题的通用模板
 * 允许重复选择本币多次
 *
 * dp[i][amount] = Math.min(dp[i-1][amount], dp[i][amount-coins[i-1]] + 1);
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
        // 如果目标金额为0但是硬币问题规模为0（或者 targetAmount 被上层改成负数了，但是可以通过下面的去除剪枝来专门去掉），意味着无法凑出，应该直接返回错误值
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

}
