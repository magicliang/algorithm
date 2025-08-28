package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 背包问题
 *
 * @author magicliang
 *
 *         date: 2025-08-28 18:49
 */
public class Knapsacks {

    /**
     * 使用深度优先搜索解决0-1背包问题
     * 
     * @param weights 物品重量数组，索引从0开始
     * @param values 物品价值数组，索引从0开始
     * @param item 当前考虑的物品编号（1-based，即第1个物品对应数组索引0）。和上面两个参数不一样。
     * @param capacity 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     * 
     * 暴力搜索的决策树结构：
     * 1. 树的高度：n层（对应n个物品）
     * 2. 每层分叉：2个选择（选或不选当前物品）
     * 3. 总节点数：2^n个（指数级增长）
     * 4. 重复计算：同一个子问题在不同路径下被多次访问
     * 
     * 决策树可视化：
     *                    f(n, capacity)
     *                   /              \
     *         f(n-1, capacity)    f(n-1, capacity-w[n-1])
     *            /        \              /        \
     *   f(n-2,...)  f(n-2,...)  f(n-2,...)  f(n-2,...)
     * 
     * 每层分叉由选择数决定：
     * - 第1层：2个分叉（第1个物品的选/不选）
     * - 第2层：4个分叉（第2个物品的选/不选）
     * - ...
     * - 第n层：2^n个叶子节点
     * 
     * 由于每个物品都会产生不选和选两条搜索分支，因此时间复杂度为：O(2^n)，其中n是物品数量
     * 空间复杂度：O(n)，递归调用栈的深度（树的高度）
     */
    public int knapsacksDfs(int[] weights, int[] values, int item, int capacity) {
        // 缺省价值为0
        if (capacity == 0 || item == 0) {
            return 0;
        }

        // 这个算法有个吊诡的点，就是 item 是 1-based的，而两个数组都是0based，所以 item - 1 代表着当前物品在数组里的坐标
        if (weights[item - 1] > capacity) {
            // 如果无法把当前物品放入背包，则不放入，沿用上一个物品的解
            return knapsacksDfs(weights, values, item - 1, capacity);
        }

        // 在上一个解的基础上放入当前物品
        int yes = knapsacksDfs(weights, values, item - 1, capacity - weights[item - 1]) + values[item - 1];
        // 不放入当前物品
        int no = knapsacksDfs(weights, values, item - 1, capacity);
        return Math.max(yes, no);
    }

    // 记忆化搜索页需要 dfs 递归搜索的

    /**
     * 使用记忆化搜索解决0-1背包问题
     * 
     * @param weights 物品重量数组，索引从0开始
     * @param values 物品价值数组，索引从0开始
     * @param item 当前考虑的物品编号（1-based）
     * @param capacity 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     * 
     * 时间复杂度：O(n × capacity)，其中n是物品数量，capacity是背包容量
     * 空间复杂度：O(n × capacity)用于存储memo数组
     * 
     * 核心思想：从"有重复组合"到"无重复组合"的优化
     * 
     * DFS的问题：同一个子问题被重复计算多次，形成"有重复组合"
     * 例如：计算f(3,10)时，f(2,5)这个子问题可能在不同路径下被多次计算
     * 
     * 记忆化搜索的优化：每个子问题只计算一次，形成"无重复组合"
     * - 状态空间：(n+1) × (capacity+1) 个不同的子问题
     * - 每个子问题只计算一次，没有重复
     * - 等价于双层for循环的无重复遍历
     * 
     * 时间复杂度分析：
     * 1. 状态总数：共有 (n+1) × (capacity+1) 个不同的状态需要计算
     * 2. 每个状态计算时间：O(1) - 只需常数时间的比较和加法操作
     * 3. 总时间复杂度：状态数 × 每个状态的计算时间 = O(n × capacity)
     * 
     * 这确实等价于一次"没有重复的遍历搜索"：
     * - 相当于遍历了一个 (n+1) × (capacity+1) 的二维表格
     * - 每个格子只计算一次，没有重复计算
     * - 与嵌套循环 for(i=0..n) for(j=0..capacity) 的遍历次数完全相同
     * 
     * 对比DFS：从指数级 O(2^n) 优化到了多项式级 O(n × capacity)
     * 本质：从"有重复组合"优化为"无重复组合"
     */
    public int knapsacksMemoization(int[] weights, int[] values, int item, int capacity) {
        // 易错的点：负价值更安全
        // 初始化memo数组，-1表示该状态尚未计算
        int[][] memo = new int[item + 1][capacity + 1];
        for (int i = 0; i <= item; i++) {
            for (int j = 0; j <= capacity; j++) {
                memo[i][j] = -1;
            }
        }
        return knapsacksMemoization(weights, values, item, capacity, memo);
    }

    /**
     * 记忆化搜索的辅助方法
     * 
     * @param weights 物品重量数组
     * @param values 物品价值数组
     * @param item 当前考虑的物品编号（1-based）
     * @param capacity 背包剩余容量
     * @param memo 记忆化数组，存储已计算的结果
     * @return 在给定容量下能获得的最大价值
     */
    private int knapsacksMemoization(int[] weights, int[] values, int item, int capacity, int[][] memo) {
        // 基本情况：没有物品或容量为0
        if (item == 0 || capacity == 0) {
            return 0;
        }
        // 易错的点：每一层应该只记忆本层的结果，而不是下一层的结果
        // 如果已经计算过该状态，直接返回结果
        if (memo[item][capacity] != -1) {
            return memo[item][capacity];
        }

        int result;
        if (weights[item - 1] > capacity) {
            // 易错的点：忘记记录这个无法放入的值
            // 当前物品太重，无法放入背包
            result = knapsacksMemoization(weights, values, item - 1, capacity, memo);
        } else {
            // 计算放入和不放入当前物品的两种情况
            int no = knapsacksMemoization(weights, values, item - 1, capacity, memo);
            int yes = knapsacksMemoization(weights, values, item - 1, capacity - weights[item - 1], memo) + values[item - 1];
            result = Math.max(no, yes);
        }

        // 易错的点：没有在返回前统一记录
        // 存储计算结果并返回
        memo[item][capacity] = result;
        return result;
    }

    /**
     * 使用动态规划解决0-1背包问题（自底向上迭代实现）
     * 
     * @param weights 物品重量数组，索引从0开始
     * @param values 物品价值数组，索引从0开始
     * @param item 物品数量（1-based，表示前item个物品）
     * @param capacity 背包总容量
     * @return 在给定容量下能获得的最大价值
     * 
     * 算法核心思想：自底向上的表格填充法
     * 
     * 状态定义：
     * dp[i][j] 表示考虑前i个物品，在背包容量为j时能获得的最大价值
     * 其中：i ∈ [0, item]，j ∈ [0, capacity]
     * 
     * 状态转移方程：
     * 1. 如果第i个物品重量 > 当前容量j：dp[i][j] = dp[i-1][j]
     *    （当前物品太重，无法放入，继承前i-1个物品的最优解）
     * 2. 否则：dp[i][j] = max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1])
     *    （不放入当前物品 vs 放入当前物品，取较大值）
     * 
     * 边界条件：
     * - dp[0][j] = 0, ∀j ∈ [0, capacity]（0个物品时价值为0）
     * - dp[i][0] = 0, ∀i ∈ [0, item]（容量为0时价值为0）
     * 
     * 填表顺序：
     * 外层循环：物品i从1到item（逐物品考虑）
     * 内层循环：容量j从1到capacity（逐容量考虑）
     * 
     * 时间复杂度：O(n × capacity)，其中n是物品数量，capacity是背包容量
     * 空间复杂度：O(n × capacity)，使用二维数组存储所有状态
     * 
     * 与记忆化搜索的对比：
     * - 记忆化搜索：自顶向下，按需计算，可能跳过某些状态
     * - 动态规划：自底向上，系统性地计算所有状态
     * - 两者时间复杂度相同，但DP的空间利用更规律
     * 
     * 优化空间：
     * 可优化为一维数组，空间复杂度降至O(capacity)
     * 但需要逆序遍历容量以避免覆盖问题
     * 
     * 实现细节注意：
     * 1. weights和values数组是0-based，而i是1-based
     * 2. 需要处理weights[i-1] > j的边界情况
     * 3. j-weights[i-1]必须非负（由if条件保证）
     */
    public int knapsacksDp(int[] weights, int[] values, int item, int capacity) {

        if (item == 0 || capacity == 0) {
            // 返回 0 意味着 0 价值
            return 0;
        }

        // 准备 dp 表，初始化边缘值
        int[][] dp = new int[item + 1][capacity + 1];
        for (int i = 1; i <= item; i++) {
            dp[i][0] = 0;
        }
        for (int j = 1; j <= capacity; j++) {
            dp[0][j] = 0;
        }

        // 易错的点：把下面的搜索放在递归里，实际上就在这里循环迭代就可以了
        for (int i = 1; i <= item; i++) {
            for (int j = 1; j <= capacity; j++) {
                // 易错的点：没有处理约束耗尽的问题
                if (weights[i-1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // weights 和 values 是 0-based 的
                    // 易错的点：j 不是索引，只有i是 weights 和 values 的索引
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i-1]] + values[i-1]);
                }
            }
        }
        return dp[item][capacity];
    }

}