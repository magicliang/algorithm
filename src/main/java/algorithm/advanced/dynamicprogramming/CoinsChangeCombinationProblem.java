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
     * 测试方法
     */
    public static void main(String[] args) {
        CoinsChangeCombinationProblem solution = new CoinsChangeCombinationProblem();
        
        // 测试用例1：基本情况
        int[] coins1 = {1, 2, 5};
        int amount1 = 5;
        int result1 = solution.coinChange(coins1, amount1);
        System.out.println("测试1 - 硬币: [1,2,5], 金额: " + amount1 + ", 组合数: " + result1);
        // 预期结果: 4种组合 (5, 2+2+1, 2+1+1+1, 1+1+1+1+1)
        
        // 测试用例2：无解情况
        int[] coins2 = {2};
        int amount2 = 3;
        int result2 = solution.coinChange(coins2, amount2);
        System.out.println("测试2 - 硬币: [2], 金额: " + amount2 + ", 组合数: " + result2);
        // 预期结果: 0种组合
        
        // 测试用例3：金额为0
        int[] coins3 = {1, 2, 5};
        int amount3 = 0;
        int result3 = solution.coinChange(coins3, amount3);
        System.out.println("测试3 - 硬币: [1,2,5], 金额: " + amount3 + ", 组合数: " + result3);
        // 预期结果: 1种组合（不选任何硬币）
        
        // 测试用例4：较大金额
        int[] coins4 = {1, 2, 5};
        int amount4 = 11;
        int result4 = solution.coinChange(coins4, amount4);
        System.out.println("测试4 - 硬币: [1,2,5], 金额: " + amount4 + ", 组合数: " + result4);
        // 预期结果: 11种组合
        
        // 测试用例5：单一硬币
        int[] coins5 = {1};
        int amount5 = 10;
        int result5 = solution.coinChange(coins5, amount5);
        System.out.println("测试5 - 硬币: [1], 金额: " + amount5 + ", 组合数: " + result5);
        // 预期结果: 1种组合（10个1元硬币）
        
        // 验证递归调用过程（小规模示例）
        System.out.println("\n=== 递归过程演示 ===");
        int[] demoCoins = {1, 2};
        int demoAmount = 3;
        System.out.println("演示：硬币[1,2]组成金额3的过程");
        int demoResult = solution.coinChangeDfs(demoCoins, demoCoins.length, demoAmount);
        System.out.println("最终结果: " + demoResult + "种组合");
        // 手工验证：3=1+1+1, 3=1+2 共2种组合
    }
}
