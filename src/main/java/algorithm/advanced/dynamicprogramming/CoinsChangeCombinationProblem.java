package algorithm.advanced.dynamicprogramming;


import java.util.Arrays;

/**
 * project name: algorithm
 *
 * description: 兑换零钱组合问题
 * 组合问题是决策树加法原理：
 *
 * dp[item][amt] = dp[item - 1][amt] + dp[item][amt - coins[item - 1]]
 *
 * 【组合问题核心认知】：
 * 为什么组合问题在选择当前item时不需要加1？
 *
 * 【本质区别】：
 * - 组合问题：目标是"计数"（有多少种方法），属于计数问题
 * - 最少硬币问题：目标是"最优化"（最少用多少硬币），属于最优化问题
 *
 * 【加法原理 vs 最优选择】：
 * - 组合问题使用加法原理（类似二叉树的子树计数）：dp[item][amt] = dp[item-1][amt] + dp[item][amt-coins[item-1]]
 * 不选当前硬币的方案数 + 选当前硬币的方案数
 * - 最少硬币问题使用最优选择（类似寻找最优子树）：dp[i][amt] = min(dp[i-1][amt], dp[i][amt-coins[i-1]] + 1)
 * 不选当前硬币的硬币数 vs 选当前硬币的硬币数+1
 *
 * 【为什么组合问题不加1】：
 * 因为组合问题的目标是计数，每选择一个硬币只是缩小了问题规模，
 * 但并没有"消耗"一个硬币的代价。我们只是把问题从"凑出amt"变为"凑出amt-coin"，
 * 方法数直接相加即可，不需要累加代价。
 *
 * 【类比理解】：
 * - 组合问题：问"从A到B有多少条路？"——每多一条路只是多一种走法
 * - 最少硬币问题：问"从A到B最短要走多少步？"——每走一步代价+1
 *
 * 【数学本质】：
 * 组合问题的状态转移是纯粹的计数累加，而最少硬币问题的状态转移是代价累加。
 * 在组合问题中，选择硬币只是改变了子问题的规模，没有改变计数的方式。
 *
 * 【决策树视角】：
 * 在组合问题的决策树中：
 * - 每个叶子节点代表一种有效的组合方式
 * - 我们只需要统计叶子节点的数量，不需要考虑到达叶子节点的"代价"
 * - 因此状态转移时不需要加1来表示"选择"的代价
 *
 * 【状态转移方程的深度认知】：
 *
 * **核心问题**：为什么选择硬币后的子问题是 dp[i][a-coins[i-1]] 而不是 dp[i-1][a-coins[i-1]]？
 *
 * **问题分解的本质**：
 * - 原问题：用前i种硬币凑出金额a
 * - 选择第i种硬币后的新问题：用前i种硬币凑出金额(a - coins[i-1])
 * - 这个新问题的每个解，都隐含地包含了我们最初选择的那个coins[i-1]
 *
 * **为什么是dp[i]而不是dp[i-1]？**
 * - dp[i][a-coins[i-1]]：选择当前硬币后，仍然可以继续使用这种硬币（无限硬币问题）
 * - dp[i-1][a-coins[i-1]]：选择当前硬币后，不能再使用这种硬币（01背包问题）
 *
 * **数学递推的逻辑**：
 * ```
 * dp[i][a] = dp[i-1][a] + dp[i][a-coins[i-1]]
 * ↑不选择      ↑选择当前硬币后的子问题
 * 当前硬币
 * ```
 *
 * **关键洞察**：
 * 当我们选择了一个硬币，问题规模确实缩小了（从"凑出a"变成"凑出a-coins[i-1]"），
 * 这个缩小后的问题的解法数量，就是我们要加上的贡献。
 * 由于硬币可以重复使用，所以子问题仍然是在第i层决策空间内。
 *
 * **适用范围**：
 * 这种 dp[i][a-cost] 的模式只适用于：
 * - 无限背包问题（物品可重复选择）
 * - 硬币兑换问题（硬币可重复使用）
 * - 爬楼梯问题（每种步长可重复使用）
 *
 * 如果是01背包（每个物品只能选一次），就必须用：dp[i][w] = max(dp[i-1][w], dp[i-1][w-weight[i-1]] + value[i-1])
 *
 * @author magicliang
 *
 *         date: 2025-08-29 19:02
 */
public class CoinsChangeCombinationProblem {

    /**
     * 使用深度优先搜索（DFS）计算硬币兑换的组合数
     *
     * @param coins 硬币面值数组
     * @param item 考虑前item种硬币（1-based索引）
     * @param targetAmount 目标金额
     * @return 能够组成目标金额的组合数
     */
    public int coinChangeDfs(int[] coins, int item, int targetAmount) {

        // 递归终止条件1：目标金额为0，找到一种有效组合
        // 无论还剩多少种硬币，都只有一种方式：不选任何硬币
        if (targetAmount == 0) {
            return 1;
        }

        // 递归终止条件2：没有硬币可选择，无法组成目标金额
        // 当item=0时，表示没有任何硬币可以使用
        if (item == 0) {
            return 0;
        }

        // 情况1：不选择当前硬币（第item种硬币）
        // 问题转化为：用前(item-1)种硬币组成targetAmount的方案数
        final int no = coinChangeDfs(coins, item - 1, targetAmount);

        // 剪枝优化：如果当前硬币面值大于目标金额，无法选择
        // 只能采用"不选择"的方案
        if (coins[item - 1] > targetAmount) {
            return no;
        }

        // 情况2：选择当前硬币（第item种硬币）
        // 问题转化为：用前item种硬币组成(targetAmount - coins[item-1])的方案数
        // 注意：这里仍然是item而不是item-1，因为硬币可以重复使用
        final int yes = coinChangeDfs(coins, item, targetAmount - coins[item - 1]);

        // 加法原理：总方案数 = 不选当前硬币的方案数 + 选当前硬币的方案数
        return no + yes;
    }

    /**
     * 公共接口方法，简化调用
     *
     * @param coins 硬币面值数组
     * @param amount 目标金额
     * @return 能够组成目标金额的组合数
     */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        return coinChangeDfs(coins, coins.length, amount);
    }

    /**
     * 使用记忆化搜索计算硬币兑换的组合数（自顶向下动态规划）
     *
     * 【记忆化搜索核心思想】：
     * 在递归搜索的基础上，使用缓存避免重复计算相同的子问题。
     *
     * 【时间复杂度】：O(item × targetAmount)
     * 【空间复杂度】：O(item × targetAmount) - 缓存空间 + O(item) - 递归栈空间
     *
     * @param coins 硬币面值数组
     * @param item 考虑前item种硬币（1-based索引）
     * @param targetAmount 目标金额
     * @return 能够组成目标金额的组合数
     */
    public int coinChangeMemoization(int[] coins, int item, int targetAmount) {
        int[][] memo = new int[item + 1][targetAmount + 1];

        // -1 意味着先初始化为未计算值
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return coinChangeMemoization(coins, item, targetAmount, memo);
    }

    /**
     * 记忆化搜索的递归实现
     *
     * 【关键认知】：记忆化搜索 = 递归 + 缓存
     * - 保持与DFS完全相同的递归逻辑
     * - 在递归调用前检查缓存，在返回前存储结果
     * - 注意：这里选择硬币后仍然递归调用 coinChangeMemoization(coins, item, ...)
     * 体现了硬币可以重复使用的特性
     *
     * @param coins 硬币面值数组
     * @param item 考虑前item种硬币（1-based索引）
     * @param targetAmount 目标金额
     * @param memo 记忆化缓存数组，-1表示未计算
     * @return 能够组成目标金额的组合数
     */
    private int coinChangeMemoization(int[] coins, int item, int targetAmount, int[][] memo) {
        // 递归终止条件：与DFS版本完全相同
        if (targetAmount == 0) {
            return 1;
        }
        if (item == 0) {
            return 0;
        }

        // 检查缓存：如果已经计算过，直接返回缓存结果
        if (memo[item][targetAmount] != -1) {
            return memo[item][targetAmount];
        }

        // 状态转移方程：dp[item][targetAmount] = dp[item - 1][targetAmount] + dp[item][targetAmount - coins[item - 1]]

        // 情况1：不选择当前硬币
        int no = coinChangeMemoization(coins, item - 1, targetAmount, memo);

        // 剪枝优化：如果当前硬币面值大于目标金额，无法选择
        if (coins[item - 1] > targetAmount) {
            memo[item][targetAmount] = no;
            return memo[item][targetAmount];
        }

        // 情况2：选择当前硬币（注意：仍然是item，体现硬币可重复使用）
        int yes = coinChangeMemoization(coins, item, targetAmount - coins[item - 1], memo);

        // 缓存结果并返回
        memo[item][targetAmount] = no + yes;
        return memo[item][targetAmount];
    }

    /**
     * 使用动态规划计算硬币兑换的组合数（自底向上动态规划）
     *
     * 【DP核心思想】：
     * 通过填表的方式，从小问题逐步构建到大问题的解。
     *
     * 【状态定义】：
     * dp[i][j] 表示用前i种硬币组成金额j的方案数
     *
     * 【状态转移方程】：
     * dp[i][j] = dp[i-1][j] + dp[i][j-coins[i-1]]
     * - dp[i-1][j]：不选择第i种硬币的方案数
     * - dp[i][j-coins[i-1]]：选择第i种硬币的方案数（注意是dp[i]，体现硬币可重复使用）
     *
     * 【边界条件】：
     * - dp[i][0] = 1：金额为0时，有1种方案（不选任何硬币）
     * - dp[0][j] = 0 (j>0)：没有硬币时，无法组成正数金额
     *
     * 【时间复杂度】：O(item × targetAmount)
     * 【空间复杂度】：O(item × targetAmount)
     *
     * @param coins 硬币面值数组
     * @param item 硬币种类数量
     * @param targetAmount 目标金额
     * @return 能够组成目标金额的组合数
     */
    public int coinChangeDp(int[] coins, int item, int targetAmount) {
        // 边界条件检查
        if (targetAmount == 0) {
            return 1;
        }
        if (item == 0) {
            return 0;
        }

        // 创建DP表
        int[][] dp = new int[item + 1][targetAmount + 1];

        // 初始化边界条件
        // dp[i][0] = 1：金额为0时，有1种方案（不选任何硬币）
        for (int i = 0; i <= item; i++) {
            dp[i][0] = 1;
        }

        // dp[0][j] = 0：没有硬币时，无法组成正数金额
        for (int j = 1; j <= targetAmount; j++) {
            dp[0][j] = 0;
        }

        // 填充DP表
        // 状态转移方程：dp[i][a] = dp[i-1][a] + dp[i][a - coins[i - 1]]
        // 易错的点：i 从 1 开始，而不是从 0 开始。有意义的值是从边缘值开始的。
        for (int i = 1; i <= item; i++) {
            for (int j = 1; j <= targetAmount; j++) {
                // 不选择当前硬币的方案数
                dp[i][j] = dp[i - 1][j];

                // 如果当前硬币面值不超过目标金额，考虑选择当前硬币
                if (coins[i - 1] <= j) {
                    // 选择当前硬币的方案数：dp[i][j - coins[i - 1]]
                    // 注意：这里是dp[i]而不是dp[i-1]，体现了硬币可重复使用
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[item][targetAmount];
    }

    /**
     * 使用空间优化的动态规划计算硬币兑换的组合数（一维数组实现）
     *
     * 【空间优化核心思想】：
     * 利用DP状态转移只依赖上一行的特性，将二维数组压缩为一维数组。
     *
     * 【关键认知：扫描顺序的本质】：
     *
     * **为什么硬币问题使用正序扫描？**
     * - 硬币可以重复使用，我们需要的是当前行的新值 dp[i][j-coins[i-1]]
     * - 正序扫描时，dp[j-coins[i-1]] 会被提前更新，成为"当前行新值"
     * - 这正好满足了硬币重复使用的需求
     *
     * **对比01背包的逆序扫描**：
     * - 01背包每个物品只能选一次，需要的是上一行的旧值 dp[i-1][j-weight[i-1]]
     * - 逆序扫描保护 dp[j-weight[i-1]] 不被提前更新，维持其作为"上一行旧值"
     *
     * **统一的表面现象与不同的内在需求**：
     * ```java
     * // 两种问题空间优化后的状态转移形式完全相同
     * dp[j] = dp[j] + dp[j-coins[i-1]]  // 硬币问题
     * dp[j] = max(dp[j], dp[j-weight[i-1]] + value[i-1])  // 01背包
     * ```
     * 但扫描顺序不同：
     * - 硬币问题：正序扫描，让 dp[j-coins[i-1]] 成为新值（支持重复选择）
     * - 01背包：逆序扫描，让 dp[j-weight[i-1]] 保持旧值（避免重复选择）
     *
     * 【时间复杂度】：O(item × targetAmount)
     * 【空间复杂度】：O(targetAmount) - 相比二维版本大幅优化
     *
     * @param coins 硬币面值数组
     * @param item 硬币种类数量
     * @param targetAmount 目标金额
     * @return 能够组成目标金额的组合数
     */
    public int coinChangeDpOptimized(int[] coins, int item, int targetAmount) {
        // 边界条件检查
        if (targetAmount == 0) {
            return 1;
        }
        if (item == 0) {
            return 0;
        }

        // 初始化一维DP数组
        int[] dp = new int[targetAmount + 1];
        // 金额为0时，有1种方案（不选任何硬币）
        dp[0] = 1;

        // 外层循环：遍历硬币种类
        for (int i = 1; i <= item; i++) {
            // 内层循环：遍历金额（正序扫描）
            // 【关键】正序扫描让 dp[j-coins[i-1]] 成为已更新的新值
            // 这样实现了硬币的重复使用
            for (int j = 1; j <= targetAmount; j++) {
                // 状态转移：dp[i][a] = dp[i-1][a] + dp[i][a-coins[i-1]]
                // 压缩后：dp[j] = dp[j] + dp[j-coins[i-1]]
                if (coins[i - 1] <= j) {
                    dp[j] = dp[j] + dp[j - coins[i - 1]];
                }
            }
        }
        return dp[targetAmount];
    }
}
