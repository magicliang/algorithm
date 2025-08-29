package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 0-1 背包问题
 *
 * ==================== 算法模板总结 ====================
 * 
 * 1. 普通递归搜索模板：
 *    - 定义递归函数和参数（状态表示）
 *    - 处理边界条件（递归终止条件）
 *    - 处理约束条件（资源不足时的处理）
 *    - 枚举所有可能的决策选择
 *    - 递归求解子问题
 *    - 合并子问题结果，返回最优解
 * 
 * 2. 记忆化搜索模板：
 *    - 基于普通递归搜索
 *    - 初始化备忘录（关键：避开合法返回值，如用-1避开0）
 *    - 处理边界条件（递归终止）
 *    - 检查缓存：如果本层已计算，直接返回
 *      【关键认知】：每一层只处理本层的缓存，不处理子问题的缓存
 *    - 专门处理资源约束条件（如容量不足）
 *    - 枚举决策，递归求解子问题
 *    - 合并子问题结果
 *    - 缓存本层结果并返回
 * 
 * 3. 动态规划模板：
 *    - 定义状态：dp[i][j]的含义
 *    - 处理边界条件和特殊情况
 *    - 初始化DP表（关键：使用合法值初始化，如边界状态设为0）
 *    - 【重要】：不使用递归，采用迭代方式（嵌套循环）
 *    - 确定状态转移顺序（通常是嵌套循环）
 *    - 在循环中：
 *      * 专门处理资源约束条件（如容量不足）
 *      * 枚举决策选择
 *      * 应用状态转移方程
 *      * 更新DP表
 *    - 返回目标状态的值
 * 
 * 核心认知：从"有重复组合"到"无重复组合"的优化
 * =====================================================
 *
 * @author magicliang
 *
 *         date: 2025-08-28 18:49
 */
public class ZeroOrOneKnapsacks {

    /**
     * 使用深度优先搜索解决0-1背包问题
     * 
     * 【普通递归搜索模板实现】：
     * 1. 定义递归函数和参数（状态表示）✓
     * 2. 处理边界条件（递归终止条件）✓
     * 3. 处理约束条件（资源不足时的处理）✓
     * 4. 枚举所有可能的决策选择✓
     * 5. 递归求解子问题✓
     * 6. 合并子问题结果，返回最优解✓
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

        int no = knapsacksDfs(weights, values, item - 1, capacity);

        // 这个算法有个吊诡的点，就是 item 是 1-based的，而两个数组都是0based，所以 item - 1 代表着当前物品在数组里的坐标
        if (weights[item - 1] > capacity) {
            // 如果无法把当前物品放入背包，则不放入，沿用上一个物品的解
            return no;
        }

        // 在上一个解的基础上放入当前物品
        int yes = knapsacksDfs(weights, values, item - 1, capacity - weights[item - 1]) + values[item - 1];
        // 不放入当前物品
        return Math.max(yes, no);
    }

    // 记忆化搜索页需要 dfs 递归搜索的

    /**
     * 使用记忆化搜索解决0-1背包问题
     * 
     * 【记忆化搜索模板实现】：
     * 1. 基于普通递归搜索✓
     * 2. 初始化备忘录（关键：避开合法返回值，如用-1避开0）✓
     * 3. 处理边界条件（递归终止）✓
     * 4. 检查缓存：如果本层已计算，直接返回✓
     *    【关键认知】：每一层只处理本层的缓存，不处理子问题的缓存
     * 5. 专门处理资源约束条件（如容量不足）✓
     * 6. 枚举决策，递归求解子问题✓
     * 7. 合并子问题结果✓
     * 8. 缓存本层结果并返回✓
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
        
        // 关键设计决策：为什么使用-1而不是0作为"未计算"的标记？
        // 
        // 1. 0是一个合法的计算结果
        //    在背包问题中，0是完全有效的返回值：
        //    - 当item == 0（没有物品）时，返回0
        //    - 当capacity == 0（背包容量为0）时，返回0
        //    - 当所有物品重量都大于当前容量时，返回0
        //
        // 2. 如果用0表示"未计算"，会导致：
        //    - 误判缓存命中：当某个子问题的真实最优解就是0时
        //    - 算法会错误地认为这个状态已经计算过，直接返回0
        //    - 跳过了实际的计算过程，导致结果错误
        //
        // 3. 示例场景：
        //    weights = [10], values = [5], capacity = 5
        //    此时knapsacksMemoization(weights, values, 1, 5)的正确结果应该是0
        //    （因为物品太重放不进去）
        //    但如果用0表示未计算，memo[1][5]会被错误地认为是已缓存的结果
        //
        // 4. 为什么-1是安全的：
        //    - -1在背包问题中永远不可能是一个合法的返回值（价值不可能为负）
        //    - 因此可以明确区分"未计算"和"计算结果为0"这两种状态
        //
        // 5. 替代方案（但不如-1简洁）：
        //    - 使用Boolean[][] computed数组单独记录哪些状态已计算
        //    - 使用包装类型Integer[][]并用null表示未计算
        //    - 使用特殊标记值如Integer.MIN_VALUE
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
     * 【动态规划模板实现】：
     * 1. 定义状态：dp[i][j]的含义✓
     * 2. 处理边界条件和特殊情况✓
     * 3. 初始化DP表（关键：使用合法值初始化，如边界状态设为0）✓
     * 4. 【重要】：不使用递归，采用迭代方式（嵌套循环）✓
     * 5. 确定状态转移顺序（通常是嵌套循环）✓
     * 6. 在循环中：
     *    - 专门处理资源约束条件（如容量不足）✓
     *    - 枚举决策选择✓
     *    - 应用状态转移方程✓
     *    - 更新DP表✓
     * 7. 返回目标状态的值✓
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

    /**
     * 使用空间优化的动态规划解决0-1背包问题（一维数组实现）
     * 
     * 【DP扫描模式核心认知】：
     * 
     * 扫描顺序的内在规律：依赖关系决定扫描顺序
     * 
     * 1. i维度扫描（物品维度）：
     *    - 正序扫描（i++）：当前行依赖上一行 → dp[i][j] 依赖 dp[i-1][*]
     *    - 这是最常见的模式，适用于大多数DP问题
     * 
     * 2. j维度扫描（容量维度）：
     *    - 逆序扫描（j--）：需要使用本行左边的"旧值"（未更新值）
     *    - 正序扫描（j++）：需要使用本行左边的"新值"（已更新值）
     * 
     * 【0-1背包为什么j要逆序？】
     * 状态转移：dp[j] = max(dp[j], dp[j-w[i]] + v[i])
     * - dp[j]：上一轮的值（相当于dp[i-1][j]）
     * - dp[j-w[i]]：需要的是上一轮的值（相当于dp[i-1][j-w[i]]）
     * 
     * 如果j正序扫描：
     * - dp[j-w[i]]可能已被本轮更新，变成了dp[i][j-w[i]]
     * - 这会导致同一物品被重复选择，违反0-1背包的约束
     * 
     * 如果j逆序扫描：
     * - dp[j-w[i]]保持上一轮的值dp[i-1][j-w[i]]
     * - 确保每个物品最多只被选择一次
     * 
     * 【对比完全背包】：
     * 完全背包允许重复选择，所以j正序扫描：
     * dp[j] = max(dp[j], dp[j-w[i]] + v[i])
     * 这里dp[j-w[i]]使用本轮更新的值是正确的，因为允许重复选择
     * 
     * 【判断方法论】：
     * 1. 分析状态转移方程中的依赖关系
     * 2. 确定需要的是"旧值"还是"新值"
     * 3. 旧值→逆序扫描，新值→正序扫描
     * 
     * @param weights 物品重量数组
     * @param values 物品价值数组  
     * @param item 物品数量
     * @param capacity 背包容量
     * @return 最大价值
     */
    public int knapsacksDpOptimized(int[] weights, int[] values, int item, int capacity) {
        if (item == 0 || capacity == 0) {
            return 0;
        }
        // 方程：dp[i][c] = max(dp[i-1][c], dp[i-1][c - w[i]] + v[i] )

        // 遍历 item，所以每一行都是 capacity。对于 item = 0 的矩阵而言，整行的初始值都是0。
        int[] dp = new int[capacity + 1]; // 我们假设这一行是第0行，意味着 i 为 0 也是一个可选的初始行。且capacity本身的状态就意味着下标。

        // 开始扫行 - i正序扫描（当前行依赖上一行）
        for (int i = 1; i <= item; i++) {
            // 这一步其实没有必要，但是写出来，让我们提醒每一行 j 等于 0 的时候合法值 是 0
            dp[0] = 0;
            // 【关键】j逆序扫描：确保使用上一轮的"旧值"，避免重复选择同一物品
            // 对每行，开始逐列从右到左移动，这样可以避免跳跃式移动的时候，对前方的j的状态污染
            for (int j = capacity; j >= 1; j--) {
                // 【补充】逆序遍历的关键：确保使用的是上一轮未被更新的"旧值"
                // 如果正序遍历，dp[j - weights[i-1]]可能已经被本轮更新，导致状态污染
                if (weights[i - 1] <= j) {
                    // dp[j] 在未更新前等于 dp[i-1][j]，dp[j - weights[i - 1]] 等于 dp[i-1][j-weights[i-1]]
                    // 【补充】空间优化：二维转一维的核心思想
                    // 原二维状态：dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i-1]] + v[i-1])
                    // 一维优化：dp[j] = max(dp[j], dp[j-w[i-1]] + v[i-1])
                    // 关键：逆序遍历保证dp[j-w[i-1]]使用的是上一轮的值
                    dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
                }
                // 【补充】当weights[i-1] > j时，dp[j]保持不变（即dp[i][j] = dp[i-1][j]）
            }
        }
        // 【补充】最终结果：dp[capacity] = dp[item][capacity]
        // 经过item轮迭代后，dp数组存储的就是考虑所有物品时的最优解
        return dp[capacity];
    }

}