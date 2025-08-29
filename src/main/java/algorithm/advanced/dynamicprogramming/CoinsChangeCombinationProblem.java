package algorithm.advanced.dynamicprogramming;


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

    public int coinChangeDfs(int[] coins, int item, int targetAmount) {

        // 目标金额为0，只有一种组合方式-这对非 dfs 而言也是一个合法值
        if (targetAmount == 0) {
            return 1;
        }

        // 当问题规模为0时，没有组合方式，这也是一个合法值
        if (item == 0) {
            return 0;
        }

        final int no = coinChangeDfs(coins, item - 1, targetAmount);
        if (coins[item - 1] > targetAmount) {
            return no;
        }

        // dp[item][amt] = dp[item - 1][amt] + dp[item][amt - coins[item - 1]]
        final int yes = coinChangeDfs(coins, item, targetAmount - coins[item - 1]);
        return no + yes;
    }
}
