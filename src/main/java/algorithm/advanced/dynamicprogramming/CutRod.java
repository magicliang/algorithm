package algorithm.advanced.dynamicprogramming;

/**
 * 切割钢条问题（Rod Cutting Problem）
 * 
 * 问题描述：
 * 给定一根长度为 n 的钢条和一个价格表 p_i（i = 1, 2, ..., n），
 * 其中 p_i 表示长度为 i 的钢条的价格。要求将钢条切割为若干段，
 * 使得销售这些段钢条的总收益最大。
 * 
 * 动态规划解法：
 * 1. 自顶向下（带备忘录）：递归分解问题，但保存子问题的解以避免重复计算。
 * 2. 自底向上：按钢条长度从小到大计算最优解，逐步构建最终解。
 * 
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
public class CutRod {
    /**
     * 切割钢条问题的动态规划解法（自底向上）
     *
     * @param prices 价格表，prices[i] 表示长度为 i+1 的钢条的价格
     * @param n 钢条的总长度
     * @return 最大收益
     */
    public int cutRod(int[] prices, int n) {
        // 初始化动态规划数组
        int[] dp = new int[n + 1];
        dp[0] = 0; // 长度为0的钢条收益为0

        // 自底向上填充dp数组
        for (int i = 1; i <= n; i++) {
            int maxVal = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                maxVal = Math.max(maxVal, prices[j] + dp[i - j - 1]);
            }
            dp[i] = maxVal;
        }

        return dp[n];
    }
}
