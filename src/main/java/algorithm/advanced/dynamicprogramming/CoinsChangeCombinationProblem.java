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
 *   不选当前硬币的方案数 + 选当前硬币的方案数
 * - 最少硬币问题使用最优选择（类似寻找最优子树）：dp[i][amt] = min(dp[i-1][amt], dp[i][amt-coins[i-1]] + 1)
 *   不选当前硬币的硬币数 vs 选当前硬币的硬币数+1
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

    public int coinChangeMemoization(int[] coins, int item, int targetAmount) {
        int[][] memo = new int[item + 1][targetAmount + 1];

        // -1 意味着先初始化为未计算值
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return coinChangeMemoization(coins, item, targetAmount, memo);
    }

    private int coinChangeMemoization(int[] coins, int item, int targetAmount, int[][] memo) {
        // 0 和 1 都是合法值，没有非法值，所以无需处理非法值问题
        if (targetAmount == 0) {
            return 1;
        }
        if (item == 0) {
            return 0;
        }

        if (memo[item][targetAmount] != -1) {
            return memo[item][targetAmount];
        }

        // dp[item][targetAmount] = dp[item - 1][targetAmount] + dp[item][targetAmount - coins[item - 1]]

        int no = coinChangeMemoization(coins, item - 1, targetAmount, memo);
        // 剪掉一个不可选的枝
        if (coins[item -1] > targetAmount) {
            memo[item][targetAmount] = no;
            return memo[item][targetAmount];
        }

        int yes =coinChangeMemoization(coins, item, targetAmount - coins[item - 1], memo);
        memo[item][targetAmount] = no + yes;

        return memo[item][targetAmount];
    }

    public int coinChangeDp(int[] coins, int item, int targetAmount) {
        if (targetAmount == 0) {
            return 1;
        }
        if (item == 0) {
            return 0;
        }

        int[][] dp = new int[item + 1][targetAmount + 1];

        for (int i = 0; i <= item; i++) {
            dp[i][0] = 1;
        }

        for (int j = 1; j <= targetAmount; j++) {
            dp[0][j] = 0;
        }
        // dp[i][a] = dp[i-1][a] + dp[i][a - coins[i - 1]]
        // 易错的点：i 从 1 开始，而不是从 0 开始。有意义的值是从边缘值开始的。
        for (int i = 1; i <= item; i++) {
            for (int j = 1; j <= targetAmount; j++) {
                if (coins[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];;
                }
            }
        }
        return dp[item][targetAmount];
    }

}
