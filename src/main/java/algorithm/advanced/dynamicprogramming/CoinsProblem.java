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
 * - dp[i][0] = 0（金额为0不需要硬币），这里0是合法值
 * - dp[0][amount] = -1 或者 amount + 1（没有硬币无法凑出正金额，可以给出一个负值或者一个利于min比较的值）
 *
 * 【动态规划约束处理优先级原则】：
 * 本问题完美体现了DP中约束处理的优先级顺序：
 * 1. 资源约束优先原则（最高优先级）：硬币面额 > 目标金额时，强制选择"不使用"，但状态合法
 * 2. 状态边界检查原则（次优先级）：无硬币可用时，返回-1表示非法状态
 * 3. 优化选择原则（最低优先级）：在合法范围内选择硬币数量最少的方案
 * 
 * 关键洞察：资源极值时，状态极值的DP是合法的；状态极值时，DP可能非法
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
    public int coinChangeDfs(int[] coins, int i, int targetAmount) {
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
        int subProblemChoice = coinChangeDfs(coins, i - 1, targetAmount);
        
        // 【剪枝优化】：当前硬币面额大于目标金额，无法使用
        // 【资源约束优先原则】：当资源出现极值时（硬币面额 > 目标金额），
        // 虽然选择受限，但dp状态仍然合法，只是被迫选择"不使用当前硬币"的路径
        // 这体现了"资源极值时，状态极值的dp是合法的"原则
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
        int currentProblemChoice = coinChangeDfs(coins, i, targetAmount - coins[i - 1]);
        
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

    /**
     * 使用记忆化搜索解决零钱兑换问题（公共接口）
     * 
     * 【记忆化搜索的优势】：
     * 1. 时间复杂度：从DFS的O(2^(n+targetAmount))优化为O(n*targetAmount)
     * 2. 空间复杂度：O(n*targetAmount)用于存储memo数组
     * 3. 编程复杂度：相比动态规划更直观，保持了递归的思维模式
     * 
     * 【初始化策略】：
     * memo数组初始化为-1有特殊含义：
     * - -1：表示该状态尚未计算（区别于计算结果为-1的无解状态）
     * - 0或正数：表示已计算的有效结果
     * - 这种设计避免了"未计算"与"无解"状态的混淆
     * 
     * @param coins 硬币面额数组
     * @param i 硬币种类数量（1-based，表示前i种硬币）
     * @param targetAmount 目标金额
     * @return 最少硬币数量，无解时返回-1
     */
    public int coinChangeMemoization(int[] coins, int i, int targetAmount) {

        // 【步骤1】：创建记忆化数组
        // 维度说明：memo[i][amount]表示用前i种硬币凑出amount金额的最优解
        int[][] memo = new int[i + 1][targetAmount + 1];

        // 【步骤2】：初始化记忆化数组
        // 关键设计：使用-1标记"未计算"状态，区别于计算结果-1（无解）
        // 这样可以正确区分：
        // - memo[i][j] == -1：该子问题尚未计算
        // - 计算后返回-1：该子问题无解
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        // 【步骤3】：调用递归函数开始计算
        return coinChangeMemoization(coins, i, targetAmount, memo);
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
    private int coinChangeMemoization(int[] coins, int i, int targetAmount, int[][] memo) {
        // 【递归终止条件1】：目标金额为0，不需要任何硬币
        if (targetAmount == 0) {
            return 0;
        }

        // 【递归终止条件2】：没有硬币可用但目标金额不为0，无解
        // 这是零钱兑换问题的特殊性：等值约束可能导致无解
        if (i == 0) {
            return -1;
        }

        // 【缓存查询】：检查当前状态是否已经计算过
        // memo[i][targetAmount] != -1 表示该子问题已有缓存结果
        if (memo[i][targetAmount] != -1) {
            return memo[i][targetAmount];
        }

        // 【选择1】：不使用当前硬币coins[i-1]
        // 递归计算使用前i-1种硬币凑出targetAmount的最优解
        int subProblemChoice = coinChangeMemoization(coins, i - 1, targetAmount, memo);
        
        // 【缓存更新1】：先将不选择当前硬币的结果存入缓存
        // 这样即使后续选择当前硬币失败，也有一个备选方案
        memo[i][targetAmount] = subProblemChoice;

        // 【剪枝优化】：当前硬币面额大于目标金额，无法使用
        // 此时只能选择不使用当前硬币的方案
        if (coins[i - 1] > targetAmount) {
            return memo[i][targetAmount];
        }

        // 【选择2】：使用当前硬币coins[i-1]
        // 递归计算使用当前硬币后的子问题：目标金额减少，硬币种类不变（完全背包特性）
        int currentProblemChoice = coinChangeMemoization(coins, i, targetAmount - coins[i - 1], memo);
        
        // 【无解处理1】：如果使用当前硬币的方案无解
        // 保持原有的subProblemChoice结果，不进行进一步处理
        if (currentProblemChoice == -1) {
            return memo[i][targetAmount];
        }
        
        // 【硬币计数】：使用当前硬币需要额外消耗1个硬币
        currentProblemChoice += 1;
        
        // 【缓存更新2】：比较两种选择，更新为最优解
        // 需要处理subProblemChoice为-1（无解）的情况
        if (memo[i][targetAmount] == -1) {
            // 如果不选择当前硬币无解，直接使用选择当前硬币的方案
            memo[i][targetAmount] = currentProblemChoice;
        } else {
            // 两种方案都有解，选择硬币数量更少的方案
            memo[i][targetAmount] = Math.min(memo[i][targetAmount], currentProblemChoice);
        }

        return memo[i][targetAmount];
    }

    /**
     * 使用动态规划解决零钱兑换问题
     * 
     * 【动态规划的优势】：
     * 1. 时间复杂度：O(item * targetAmount)，与记忆化搜索相同但常数更小
     * 2. 空间复杂度：O(item * targetAmount)，可进一步优化为O(targetAmount)
     * 3. 编程复杂度：自底向上填表，避免递归调用栈开销
     * 4. 可读性：状态转移过程更直观，便于理解和调试
     * 
     * 【状态定义】：
     * dp[i][j] 表示使用前i种硬币凑出金额j所需的最少硬币数
     * 
     * 【状态转移方程】：
     * dp[i][j] = min(dp[i-1][j], dp[i][j-coins[i-1]] + 1)
     * - 不选当前硬币：dp[i-1][j]
     * - 选择当前硬币：dp[i][j-coins[i-1]] + 1（注意这里是dp[i]而不是dp[i-1]，体现完全背包特性）
     * 
     * 【边界条件】：
     * - dp[i][0] = 0：凑出金额0需要0个硬币
     * - dp[0][j] = MAX：没有硬币无法凑出正金额（用MAX标记无效状态）
     * 
     * 【无效状态处理】：
     * 使用MAX = targetAmount + 1作为无效状态标记，因为：
     * - 任何有效解的硬币数都不会超过targetAmount（最坏情况全用面额1的硬币）
     * - MAX比所有有效解都大，在min比较中会被自然淘汰
     * - 最终通过 dp[item][targetAmount] < MAX 判断是否有解
     * 
     * @param coins 硬币面额数组
     * @param item 硬币种类数量（1-based，表示前item种硬币）
     * @param targetAmount 目标金额
     * @return 最少硬币数量，无解时返回-1
     */
    public int coinChangeDp(int[] coins, int item, int targetAmount) {
        if (targetAmount == 0) {
            return 0;
        }

        if (item <= 0 || targetAmount < 0) {
            return -1;
        }

        // 【无效状态标记】：MAX = targetAmount + 1
        // 任何有效解的硬币数都不会超过targetAmount（最坏情况全用面额1的硬币）
        int MAX = targetAmount + 1;

        // 【步骤1】：创建DP表
        // dp[i][j] 表示用前i种硬币凑出金额j的最少硬币数
        int[][] dp = new int[item + 1][targetAmount + 1];

        // 【步骤2】：初始化边界条件
        // dp[i][0] = 0：凑出金额0需要0个硬币（默认值已经是0）
        
        // dp[0][j] = MAX：没有硬币无法凑出正金额，用MAX标记无效状态
        // MAX恰好比所有有效解都大1，在min函数中会被自然淘汰。注意，0行0列返回应该是0而不是 MAX
        for (int amount = 1; amount <= targetAmount; amount++) {
            dp[0][amount] = MAX;
        }

        // 【步骤3】：填充DP表
        for (int i = 1; i <= item; i++) {
            for (int j = 1; j <= targetAmount; j++) {
                // 【关键判断】：当前硬币面额是否大于当前金额j
                // 易错点：这里是j而不是targetAmount
                if (coins[i - 1] > j) {
                    // 【资源约束优先原则】：硬币面额 > 当前金额j（资源极值）
                    // 此时状态被迫选择"不使用当前硬币"（状态极值），但dp仍合法
                    // 体现了"资源极值时，状态极值的dp是合法的"原则
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 【情况2】：当前硬币可以使用，选择最优方案
                    // 注意：dp[i][j - coins[i-1]]而不是dp[i-1][j - coins[i-1]]
                    // 这体现了完全背包的特性：同一种硬币可以重复使用
                    dp[i][j] = Math.min(
                        dp[i - 1][j],                    // 不选当前硬币
                        dp[i][j - coins[i - 1]] + 1     // 选择当前硬币
                    );
                }
            }
        }

        // 【步骤4】：返回结果
        // 如果dp[item][targetAmount] < MAX，说明找到了有效解
        // 否则返回-1表示无解
        return dp[item][targetAmount] < MAX ? dp[item][targetAmount] : -1;
    }

    /**
     * 使用空间优化的动态规划解决零钱兑换问题
     * 
     * 【空间优化原理】：
     * 将二维DP表dp[i][j]压缩为一维数组dp[j]，空间复杂度从O(item*targetAmount)优化为O(targetAmount)
     * 
     * 【关键洞察】：
     * 在完全背包问题中，dp[i][j]只依赖于：
     * 1. dp[i-1][j]（上一轮迭代的值，即不选当前硬币）
     * 2. dp[i][j-coins[i-1]]（本轮已更新的值，即选择当前硬币）
     * 
     * 【遍历顺序的重要性】：
     * - 外层循环：硬币种类（从前往后）
     * - 内层循环：金额（从前往后）
     * 这样确保dp[j-coins[i-1]]是"当前硬币种类下已更新的值"，体现完全背包特性
     * 
     * @param coins 硬币面额数组
     * @param item 硬币种类数量
     * @param targetAmount 目标金额
     * @return 最少硬币数量，无解时返回-1
     */
    public int coinChangeDpOptimization(int[] coins, int item, int targetAmount) {
        if (targetAmount == 0) {
            return 0;
        }

        if (item <= 0 || targetAmount < 0) {
            return -1;
        }

        // 【无效状态标记】：MAX = targetAmount + 1
        // 任何有效解的硬币数都不会超过targetAmount（最坏情况全用面额1的硬币）
        int MAX = targetAmount + 1;

        // 【空间优化核心】：用一维数组dp[j]替代二维数组dp[i][j]
        // dp[j]表示凑出金额j所需的最少硬币数
        int[] dp = new int[targetAmount + 1];
        for (int i = 1; i <= targetAmount; i++) {
            // i = 0 的时候，不管金额是多少，都无法凑出，所以这一行除了0列都是非法值
            // 但是 targetAmount = 0 的时候，需要0个硬币凑出结果，所以这一列都赋值给合法值0（默认初始化就是0）
            dp[i] = MAX;
        }

        // 【外层循环】：遍历硬币种类，每次考虑一种新硬币
        for (int i = 1; i <= item; i++) {
            // 【内层循环】：遍历所有金额，更新使用当前硬币后的最优解
            for (int j = 1; j <= targetAmount; j++) {
                // dp[i][j] = min(dp[i-1][j], dp[i][j-coins[i-1]] + 1)
                // 这里要比的就是 j，就是当前这个序列上的容量
                
                // 【约束处理优先级原则的体现】：
                // 1. 资源约束检查（最高优先级）：coins[i-1] <= j
                // 2. 状态边界已在外层处理（targetAmount=0, item<=0等）
                // 3. 优化选择（最低优先级）：Math.min比较
                
                // 【状态转移】：dp[j]原值相当于dp[i-1][j]（不选当前硬币）
                // dp[j-coins[i-1]]相当于dp[i][j-coins[i-1]]（选择当前硬币，已在本轮更新）
                if (coins[i - 1] <= j) {
                    // 资源约束满足，可以进行优化选择
                    dp[j] = Math.min(dp[j], dp[j - coins[i - 1]] + 1);
                }
                // 资源约束不满足时，dp[j]保持原值（相当于强制选择"不使用当前硬币"）
            }
        }
        return dp[targetAmount] < MAX ? dp[targetAmount] : -1;
    }
}