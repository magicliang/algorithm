package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 完全背包问题，每个物品的值可以被重复使用，这也就意味着物品可以在 dp[i][...] 中出现多次，更新这一项多次
 *
 * 新的状态转换方程为：dp[i][c] = max(dp[i-1][c], dp[i][c-w[i]] + val[i])
 * 这个方程使用 1-based 的重量数组和价值数组
 *
 * @author magicliang
 *
 *         date: 2025-08-29 11:03
 */
public class UnboundedKnapsackProblem {

    /**
     * 使用深度优先搜索解决完全背包问题
     *
     * @param wgt 物品重量数组
     * @param val 物品价值数组
     * @param item 当前考虑的物品索引（从1开始）
     * @param cap 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     */
    public int unboundedKnapsackProblemDfs(int[] wgt, int[] val, int item, int cap) {
        // 在无容量的时候，和原始问题一样，返回 0
        if (item == 0 || cap == 0) {
            return 0;
        }

        // 不选择当前物品的情况
        final int no = unboundedKnapsackProblemDfs(wgt, val, item - 1, cap);
        if (wgt[item - 1] > cap) {
            // 不能放入这个物品
            return no;
        }

        // 选择当前物品的情况（注意：item不减1，因为可以重复选择）
        int yes = unboundedKnapsackProblemDfs(wgt, val, item, cap - wgt[item - 1]) + val[item - 1];
        // 返回两种情况的最大值
        return Math.max(yes, no);
    }

    /**
     * 使用记忆化搜索解决完全背包问题
     *
     * @param wgt 物品重量数组
     * @param val 物品价值数组
     * @param item 物品数量
     * @param cap 背包容量
     * @return 在给定容量下能获得的最大价值
     */
    public int unboundedKnapsackProblemMemoization(int[] wgt, int[] val, int item, int cap) {
        // 创建记忆化数组，memo[i][c] 表示前i个物品在容量c下的最大价值
        int[][] memo = new int[item + 1][cap + 1];

        return unboundedKnapsackProblemMemoization(wgt, val, item, cap, memo);
    }

    /**
     * 记忆化搜索的递归实现
     *
     * @param wgt 物品重量数组
     * @param val 物品价值数组
     * @param item 当前考虑的物品索引（从1开始）
     * @param cap 背包剩余容量
     * @param memo 记忆化数组
     * @return 在给定容量下能获得的最大价值
     */
    private int unboundedKnapsackProblemMemoization(int[] wgt, int[] val, int item, int cap, int[][] memo) {
        // 这些边界值是没有缓存的，但是却可以推导出"更上层"的缓存
        if (item == 0 || cap == 0) {
            return 0;
        }

        // 如果已经计算过，直接返回缓存结果
        if (memo[item][cap] != 0) {
            return memo[item][cap];
        }

        // 不选择当前物品的情况
        int no = unboundedKnapsackProblemMemoization(wgt, val, item - 1, cap, memo);
        if (wgt[item - 1] > cap) {
            // 当前物品重量超过容量，只能不选择
            memo[item][cap] = no;
            return memo[item][cap];
        }
        // 选择当前物品的情况（注意：item不减1，因为可以重复选择）
        int yes = unboundedKnapsackProblemMemoization(wgt, val, item, cap - wgt[item - 1], memo) + val[item - 1];
        // 缓存并返回两种情况的最大值
        memo[item][cap] = Math.max(yes, no);
        return memo[item][cap];
    }

    /**
     * 使用动态规划解决完全背包问题
     * 
     * 【完全背包DP核心思想】：
     * 与0-1背包的关键区别在状态转移方程：
     * - 0-1背包：dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i]] + v[i])
     * - 完全背包：dp[i][j] = max(dp[i-1][j], dp[i][j-w[i]] + v[i])
     * 
     * 【状态定义】：
     * dp[i][j] 表示前i个物品，背包容量为j时的最大价值
     * 
     * 【状态转移详解】：
     * 对于物品i和容量j，有两种选择：
     * 1. 不选物品i：dp[i][j] = dp[i-1][j]
     * 2. 选物品i：dp[i][j] = dp[i][j-w[i]] + v[i]
     *    注意：这里是dp[i][j-w[i]]而不是dp[i-1][j-w[i]]
     *    因为物品可以重复选择，所以还是在第i层决策
     * 
     * 【填表顺序】：
     * - 外层循环：物品（从1到n）
     * - 内层循环：容量（从1到capacity）
     * - 这样保证计算dp[i][j]时，dp[i][j-w[i]]已经计算过
     * 
     * 【时间复杂度】：O(n × capacity)
     * 【空间复杂度】：O(n × capacity)
     * 
     * @param wgt 物品重量数组（0-based索引）
     * @param val 物品价值数组（0-based索引）
     * @param item 物品数量
     * @param cap 背包容量
     * @return 在给定容量下能获得的最大价值
     */
    public int unboundedKnapsackProblemDp(int[] wgt, int[] val, int item, int cap) {
        // 边界条件检查
        if (item == 0 || cap == 0) {
            return 0;
        }

        // 创建DP表，dp[i][j]表示前i个物品在容量j下的最大价值
        // 注意：Java数组默认初始化为0，这里0是合法的价值，所以不需要特殊初始化
        int[][] dp = new int[item + 1][cap + 1];
        
        // 初始化边界条件（实际上Java已经默认为0，这里显式写出便于理解）
        // dp[0][j] = 0：没有物品时，价值为0
        // dp[i][0] = 0：容量为0时，价值为0
        for (int i = 0; i <= item; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= cap; j++) {
            dp[0][j] = 0;
        }

        // 填充DP表
        for (int i = 1; i <= item; i++) {
            for (int j = 1; j <= cap; j++) {
                // 不选择当前物品i的价值
                dp[i][j] = dp[i - 1][j];
                
                // 如果当前物品重量不超过容量，考虑选择当前物品
                if (wgt[i - 1] <= j) {
                    // 选择当前物品的价值：dp[i][j-wgt[i-1]] + val[i-1]
                    // 注意：这里是dp[i][...]而不是dp[i-1][...]，体现了物品可重复选择
                    int chooseItem = dp[i][j - wgt[i - 1]] + val[i - 1];
                    dp[i][j] = Math.max(dp[i][j], chooseItem);
                }
            }
        }

        return dp[item][cap];
    }
}
