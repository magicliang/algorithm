package algorithm.advanced.dynamicprogramming;


import java.util.Arrays;

/**
 * project name: algorithm
 *
 * description: 这个问题是说有个 source 字符串，有个 target 字符串，求要用最少多少步，能干把 source 变成 target
 *
 * 这个问题是从 source 的结尾开始遍历，通过比对 source 的子问题和 target 的子问题来比对最终的解。
 * 假设 dp[i, j] 意味着共i个字符串变成j个字符串需要的最少步数，则我们对比如下：
 * 1. 如果当前子问题的结尾的字符相同：则不需要变更-变更操作为0，当前的子问题自动替换为子问题的最优解，没有累加步骤。
 * 2. 如果当前子问题的结尾字符不一样，则需要编辑：
 *   2.1 如果要把 t[j-1] 插入 s[i-1] 后，则 dp [i, j] = dp[i, j - 1] + 1。i不变，但是目标j实际上变少了。
 *   2.2 如果要把 s[i-1] 删除，则 dp[i, j] = dp[i - 1, j] + 1。j 不变，但是 i 变少了。
 *   2.3 如果要把 s[i-1] 替换为 t[j-1]，则 dp[i, j] = dp[i - 1, j - 1] + 1。i 和 j 都变少了。
 *
 * 总结，如果要做选择：首先做一层选择：要不要做出编辑，这代表两个逻辑分支。
 *
 * 其中，做出编辑的选择意味着三个分叉选择。所以一共有4个逻辑分支。
 *
 * @author magicliang
 *
 *         date: 2025-09-01 19:20
 */
public class LevenshteinProblem {

    /**
     * 使用深度优先搜索（DFS）计算两个字符串之间的编辑距离
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return 将源字符串转换为目标字符串所需的最少编辑操作数
     */
    public int editDistanceDfs(String s, String t) {
        // 边界条件：如果源字符串为空，需要插入目标字符串的所有字符
        if (s == null || s.isEmpty()) {
            return t == null ? 0 : t.length();
        }

        // 边界条件：如果目标字符串为空，需要删除源字符串的所有字符
        if (t == null || t.isEmpty()) {
            return s.length();
        }

        // 获取两个字符串的长度
        final int n = s.length();
        final int m = t.length();

        // 比较两个字符串的最后一个字符
        char lastCharOfS = s.charAt(n - 1);
        char lastCharOfT = t.charAt(m - 1);

        // 如果最后一个字符相同，则递归处理去掉最后一个字符的子字符串
        if (lastCharOfS == lastCharOfT) {
            return editDistanceDfs(s.substring(0, n - 1), t.substring(0, m - 1));
        }

        // 如果最后一个字符不同，考虑三种编辑操作：

        // 1. 插入操作：在s的末尾插入t的最后一个字符
        // 相当于将s转换为t去掉最后一个字符的结果，然后加1步插入操作
        int insertCost = editDistanceDfs(s, t.substring(0, m - 1)) + 1;

        // 2. 删除操作：删除s的最后一个字符
        // 相当于将s去掉最后一个字符转换为t，然后加1步删除操作
        int deleteCost = editDistanceDfs(s.substring(0, n - 1), t) + 1;

        // 3. 替换操作：将s的最后一个字符替换为t的最后一个字符
        // 相当于将s和t都去掉最后一个字符进行转换，然后加1步替换操作
        int replaceCost = editDistanceDfs(s.substring(0, n - 1), t.substring(0, m - 1)) + 1;

        // 返回三种操作中成本最小的一种
        return Math.min(Math.min(insertCost, deleteCost), replaceCost);
    }

    /**
     * 使用记忆化搜索计算两个字符串之间的编辑距离
     * 通过缓存中间结果避免重复计算，提高算法效率
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return 将源字符串转换为目标字符串所需的最少编辑操作数
     */
    public int editDistanceMemoization(String s, String t) {
        // 边界条件：如果源字符串为空，需要插入目标字符串的所有字符
        if (s == null || s.isEmpty()) {
            return t == null ? 0 : t.length();
        }

        // 边界条件：如果目标字符串为空，需要删除源字符串的所有字符
        if (t == null || t.isEmpty()) {
            return s.length();
        }

        // 获取字符串长度
        int m = s.length();
        int n = t.length();

        // 初始化记忆化数组，-1表示未计算过的状态
        int[][] memo = new int[m + 1][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // 调用辅助方法进行记忆化搜索
        return editDistanceMemoization(s, t, memo);
    }

    /**
     * 使用记忆化搜索计算两个字符串之间的编辑距离（私有辅助方法）
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @param memo 记忆化数组，memo[i][j]表示长度为i的字符串转换为长度为j的字符串的最小编辑距离
     * @return 将源字符串转换为目标字符串所需的最少编辑操作数
     */
    /**
     * 使用记忆化搜索计算两个字符串之间的编辑距离（私有辅助方法）
     * 
     * 算法思路：
     * 1. 如果两个字符串的最后一个字符相同，则问题转化为处理去掉最后字符的子字符串
     * 2. 如果不同，则考虑三种编辑操作：插入、删除、替换，选择成本最小的
     * 3. 使用记忆化数组缓存已计算的结果，避免重复计算
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @param memo 记忆化数组，memo[i][j]表示长度为i的字符串转换为长度为j的字符串的最小编辑距离
     * @return 将源字符串转换为目标字符串所需的最少编辑操作数
     */
    int editDistanceMemoization(String s, String t, int[][] memo) {
        // 易错的点：不能不再剪枝，因为 editDistanceDfs 递归调用自己，它能处理"0问题"，但是这个辅助方法也要解决"0问题"

        // 边界条件：处理空字符串
        // 如果源字符串为空，需要插入目标字符串的所有字符
        if (s.isEmpty()) {
            memo[s.length()][t.length()] = t.length();
            return t.length();
        }
        // 如果目标字符串为空，需要删除源字符串的所有字符
        if (t.isEmpty()) {
            memo[s.length()][t.length()] = s.length();
            return s.length();
        }

        // 检查记忆化缓存，如果已经计算过则直接返回
        if (memo[s.length()][t.length()] != -1) {
            return memo[s.length()][t.length()];
        }

        // 获取两个字符串的长度
        final int n = s.length();
        final int m = t.length();

        // 获取两个字符串的最后一个字符进行比较
        char lastCharOfS = s.charAt(n - 1);
        char lastCharOfT = t.charAt(m - 1); // 修复bug：应该是m-1而不是n-1

        // 如果最后一个字符相同，则递归处理去掉最后一个字符的子字符串
        // 此时不需要任何编辑操作，直接处理子问题
        if (lastCharOfS == lastCharOfT) {
            memo[s.length()][t.length()] = editDistanceMemoization(s.substring(0, n - 1), t.substring(0, m - 1), memo);
            return memo[s.length()][t.length()];
        }

        // 如果最后一个字符不同，考虑三种编辑操作：

        // 1. 插入操作：在s的末尾插入t的最后一个字符
        // 相当于将s转换为t去掉最后一个字符的结果，然后加1步插入操作
        int insertCost = editDistanceMemoization(s, t.substring(0, m - 1), memo) + 1;

        // 2. 删除操作：删除s的最后一个字符
        // 相当于将s去掉最后一个字符转换为t，然后加1步删除操作
        int deleteCost = editDistanceMemoization(s.substring(0, n - 1), t, memo) + 1;

        // 3. 替换操作：将s的最后一个字符替换为t的最后一个字符
        // 相当于将s和t都去掉最后一个字符进行转换，然后加1步替换操作
        int replaceCost = editDistanceMemoization(s.substring(0, n - 1), t.substring(0, m - 1), memo) + 1;

        // 返回三种操作中成本最小的一种，并存储到记忆化数组中
        memo[s.length()][t.length()] = Math.min(Math.min(insertCost, deleteCost), replaceCost);
        return memo[s.length()][t.length()];
    }

    /**
     * 使用动态规划计算两个字符串之间的编辑距离
     * 这是最优的解法，时间复杂度O(m*n)，空间复杂度O(m*n)
     * 
     * 算法思路：
     * 1. 创建二维DP表，dp[i][j]表示s[0..i-1]转换为t[0..j-1]的最小编辑距离
     * 2. 初始化边界条件：空字符串到非空字符串的转换成本
     * 3. 状态转移：
     *    - 如果字符相同：dp[i][j] = dp[i-1][j-1]
     *    - 如果字符不同：dp[i][j] = min(插入, 删除, 替换) + 1
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return 将源字符串转换为目标字符串所需的最少编辑操作数
     */
    public int editDistanceDp(String s, String t) {

        // 边界条件：如果源字符串为空，需要插入目标字符串的所有字符
        if (s == null || s.isEmpty()) {
            return t == null ? 0 : t.length();
        }

        // 边界条件：如果目标字符串为空，需要删除源字符串的所有字符
        if (t == null || t.isEmpty()) {
            return s.length();
        }

        // 获取字符串长度
        int m = s.length();
        int n = t.length();

        // 准备dp表，dp 的意思是 dp[i][j] 表示 s[0..i-1] 转换成 t[0..j-1] 的最小编辑距离
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界值

        // 如果 s 是非空字符串，t是空字符串，则0列所有值都是 s 的长度
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // 易错的点：每个子问题的复制值是非空子字符串本身，而不是总字符串
        }

        // 同理，如果 s 空，t 非空，
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 最后，如果 s 和 t 都是空字符串，没有编辑距离，0是合法值
        dp[0][0] = 0;

        // 易错的点：从初始值之外的值开始遍历，因为0值实际上是非问题本身，是问题退化到极致才会遇到的值
        for (int i = 1; i <= m; i++) { // 易错的点：这里的变量是问题规模，而不是索引
            for (int j = 1; j <= n; j++) { // 易错的点：这里的变量是问题规模，而不是索引
                // 比较两个字符串的当前位置字符
                char lastCharOfS = s.charAt(i - 1); // 易错的点：虽然是从1开始，但是索引还是从0开始
                char lastCharOfT = t.charAt(j - 1); // 易错的点：虽然是从1开始，但是索引还是从0开始
                
                // 如果字符相同，不需要编辑操作，直接继承对角线的值
                if (lastCharOfS == lastCharOfT) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果字符不同，考虑三种操作，选择成本最小的：
                    // dp[i-1][j] + 1: 删除操作
                    // dp[i][j-1] + 1: 插入操作  
                    // dp[i-1][j-1] + 1: 替换操作
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        
        // 返回右下角的值，即完整字符串的编辑距离
        return dp[m][n];
    }

}
