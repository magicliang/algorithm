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

    /**
     * 使用空间优化的动态规划解决完全背包问题（一维数组实现）
     * 
     * 【完全背包 vs 0-1背包的扫描顺序差异】：
     * 
     * 核心问题：为什么0-1背包需要逆序扫描，而完全背包可以正序扫描？
     * 
     * 【根本原因】：依赖关系的不同
     * 
     * 1. 0-1背包的状态转移：
     *    dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i]] + v[i])
     *    - 选择物品i时，依赖的是dp[i-1][j-w[i]]（上一行的值）
     *    - 每个物品最多只能选择一次
     * 
     * 2. 完全背包的状态转移：
     *    dp[i][j] = max(dp[i-1][j], dp[i][j-w[i]] + v[i])
     *    - 选择物品i时，依赖的是dp[i][j-w[i]]（当前行的值）
     *    - 物品可以重复选择多次
     * 
     * 【空间优化后的影响】：
     * 
     * 0-1背包（需要逆序）：
     * - 状态转移：dp[j] = max(dp[j], dp[j-w[i]] + v[i])
     * - 需要的dp[j-w[i]]应该是"上一轮的旧值"（相当于dp[i-1][j-w[i]]）
     * - 如果正序扫描，dp[j-w[i]]会被提前更新，变成"当前轮的新值"
     * - 这会导致同一物品被重复选择，违反0-1背包约束
     * - 因此必须逆序扫描，保证使用的是未被更新的旧值
     * 
     * 完全背包（可以正序）：
     * - 状态转移：dp[j] = max(dp[j], dp[j-w[i]] + v[i])
     * - 需要的dp[j-w[i]]就是"当前轮的新值"（相当于dp[i][j-w[i]]）
     * - 正序扫描时，dp[j-w[i]]已被更新，这正是我们需要的
     * - 这样可以实现物品的重复选择，符合完全背包的要求
     * 
     * 【具体示例】：
     * 假设物品1：重量2，价值3，容量为6
     * 
     * 0-1背包（逆序j=6,5,4,3,2,1）：
     * - j=6: dp[6] = max(dp[6], dp[4] + 3) = max(0, 0+3) = 3
     * - j=4: dp[4] = max(dp[4], dp[2] + 3) = max(0, 0+3) = 3
     * - j=2: dp[2] = max(dp[2], dp[0] + 3) = max(0, 0+3) = 3
     * 结果：物品1最多选择1次，dp[6]=3
     * 
     * 完全背包（正序j=1,2,3,4,5,6）：
     * - j=2: dp[2] = max(dp[2], dp[0] + 3) = max(0, 0+3) = 3
     * - j=4: dp[4] = max(dp[4], dp[2] + 3) = max(0, 3+3) = 6 ← 使用了更新后的dp[2]
     * - j=6: dp[6] = max(dp[6], dp[4] + 3) = max(0, 6+3) = 9 ← 使用了更新后的dp[4]
     * 结果：物品1选择了3次，dp[6]=9
     * 
     * 【记忆口诀】：
     * - 0-1背包：物品只能选一次 → 需要旧值 → 逆序扫描
     * - 完全背包：物品可选多次 → 需要新值 → 正序扫描
     * 
     * @param wgt 物品重量数组
     * @param val 物品价值数组
     * @param cap 背包容量
     * @return 最大价值
     */
    public int unboundedKnapsackDpOptimized(int[] wgt, int[] val, int cap) {
        int n = wgt.length;
        
        // 初始化一维DP数组
        // dp[j] 表示容量为j时的最大价值
        int[] dp = new int[cap + 1];
        
        // 【关键】外层循环：遍历物品（i正序扫描）
        for (int i = 1; i <= n; i++) {
            // 【关键】内层循环：遍历容量（j正序扫描）
            // 正序扫描的原因：我们需要使用本轮已更新的值dp[j-w[i]]
            // 这样可以实现物品的重复选择
            for (int c = 1; c <= cap; c++) {
                if (wgt[i - 1] > c) {
                    // 若超过背包容量，则不选物品i
                    // dp[c] = dp[c]; // 保持不变，这行可以省略
                } else {
                    // 【核心状态转移】：
                    // dp[c] = max(不选物品i, 选择物品i)
                    // = max(dp[c], dp[c - wgt[i-1]] + val[i-1])
                    // 
                    // 关键理解：dp[c - wgt[i-1]]在正序扫描中已经被更新
                    // 它包含了可能多次选择物品i的结果，这正是完全背包所需要的
                    dp[c] = Math.max(dp[c], dp[c - wgt[i - 1]] + val[i - 1]);
                }
            }
        }
        
        return dp[cap];
    }
}
