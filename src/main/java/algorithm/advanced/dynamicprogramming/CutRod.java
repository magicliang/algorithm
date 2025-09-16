package algorithm.advanced.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

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
 * 
 * @author magicliang
 * @version 2.0
 * @since 2025-09-16
 */
public class CutRod {
    
    /**
     * 切割方案结果类
     */
    public static class CutResult {
        private final int maxRevenue;
        private final List<Integer> cuts;
        
        public CutResult(int maxRevenue, List<Integer> cuts) {
            this.maxRevenue = maxRevenue;
            this.cuts = new ArrayList<>(cuts);
        }
        
        public int getMaxRevenue() {
            return maxRevenue;
        }
        
        public List<Integer> getCuts() {
            return new ArrayList<>(cuts);
        }
        
        @Override
        public String toString() {
            return "CutResult{maxRevenue=" + maxRevenue + ", cuts=" + cuts + "}";
        }
    }
    
    /**
     * 切割钢条问题的动态规划解法（自底向上）
     *
     * @param prices 价格表，prices[i] 表示长度为 i+1 的钢条的价格
     * @param n 钢条的总长度
     * @return 最大收益
     */
    public int cutRod(int[] prices, int n) {
        if (prices == null || n < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        
        if (n == 0) {
            return 0;
        }
        
        // 初始化动态规划数组
        int[] dp = new int[n + 1];
        dp[0] = 0; // 长度为0的钢条收益为0

        // 自底向上填充dp数组
        for (int i = 1; i <= n; i++) {
            int maxVal = Integer.MIN_VALUE;
            for (int j = 0; j < i && j < prices.length; j++) {
                maxVal = Math.max(maxVal, prices[j] + dp[i - j - 1]);
            }
            dp[i] = maxVal;
        }

        return dp[n];
    }
    
    /**
     * 切割钢条问题的动态规划解法（带切割方案追踪）
     * 
     * @param prices 价格表，prices[i] 表示长度为 i+1 的钢条的价格
     * @param n 钢条的总长度
     * @return 包含最大收益和切割方案的结果对象
     */
    public CutResult cutRodWithSolution(int[] prices, int n) {
        if (prices == null || n < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        
        if (n == 0) {
            return new CutResult(0, new ArrayList<>());
        }
        
        // 初始化动态规划数组和切割记录数组
        int[] dp = new int[n + 1];
        int[] cuts = new int[n + 1]; // cuts[i] 记录长度为i时的第一段最优切割长度
        
        dp[0] = 0;
        cuts[0] = 0;

        // 自底向上填充dp数组
        for (int i = 1; i <= n; i++) {
            int maxVal = Integer.MIN_VALUE;
            int bestCut = 0;
            
            for (int j = 0; j < i && j < prices.length; j++) {
                int currentVal = prices[j] + dp[i - j - 1];
                if (currentVal > maxVal) {
                    maxVal = currentVal;
                    bestCut = j + 1; // 切割长度（1-indexed）
                }
            }
            
            dp[i] = maxVal;
            cuts[i] = bestCut;
        }

        // 重构切割方案
        List<Integer> solution = new ArrayList<>();
        int remaining = n;
        while (remaining > 0) {
            int cutLength = cuts[remaining];
            solution.add(cutLength);
            remaining -= cutLength;
        }
        
        return new CutResult(dp[n], solution);
    }
    
    /**
     * 自顶向下的递归解法（带备忘录）
     * 
     * @param prices 价格表
     * @param n 钢条长度
     * @return 最大收益
     */
    public int cutRodTopDown(int[] prices, int n) {
        if (prices == null || n < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        
        int[] memo = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1; // 初始化为-1表示未计算
        }
        
        return cutRodTopDownHelper(prices, n, memo);
    }
    
    /**
     * 自顶向下递归的辅助方法
     */
    private int cutRodTopDownHelper(int[] prices, int n, int[] memo) {
        if (n == 0) {
            return 0;
        }
        
        if (memo[n] != -1) {
            return memo[n]; // 返回已计算的结果
        }
        
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < n && i < prices.length; i++) {
            maxVal = Math.max(maxVal, prices[i] + cutRodTopDownHelper(prices, n - i - 1, memo));
        }
        
        memo[n] = maxVal;
        return maxVal;
    }
    
    /**
     * 计算每种长度的单位收益率
     * 
     * @param prices 价格表
     * @return 每种长度的单位收益率数组
     */
    public double[] calculateProfitRates(int[] prices) {
        if (prices == null || prices.length == 0) {
            return new double[0];
        }
        
        double[] rates = new double[prices.length];
        for (int i = 0; i < prices.length; i++) {
            rates[i] = (double) prices[i] / (i + 1);
        }
        
        return rates;
    }
    
    /**
     * 验证价格表的合理性
     * 
     * @param prices 价格表
     * @return 价格表是否合理（无负价格）
     */
    public boolean validatePrices(int[] prices) {
        if (prices == null) {
            return false;
        }
        
        for (int price : prices) {
            if (price < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 比较不同算法的性能
     * 
     * @param prices 价格表
     * @param n 钢条长度
     */
    public void performanceComparison(int[] prices, int n) {
        System.out.println("=== 切割钢条算法性能比较 ===");
        System.out.println("钢条长度: " + n);
        System.out.println("价格表长度: " + prices.length);
        
        // 测试自底向上方法
        long startTime = System.nanoTime();
        int result1 = cutRod(prices, n);
        long endTime = System.nanoTime();
        long bottomUpTime = endTime - startTime;
        
        // 测试自顶向下方法
        startTime = System.nanoTime();
        int result2 = cutRodTopDown(prices, n);
        endTime = System.nanoTime();
        long topDownTime = endTime - startTime;
        
        // 测试带方案追踪的方法
        startTime = System.nanoTime();
        CutResult result3 = cutRodWithSolution(prices, n);
        endTime = System.nanoTime();
        long withSolutionTime = endTime - startTime;
        
        System.out.println("自底向上方法: " + result1 + " (耗时: " + bottomUpTime + " ns)");
        System.out.println("自顶向下方法: " + result2 + " (耗时: " + topDownTime + " ns)");
        System.out.println("带方案追踪: " + result3.getMaxRevenue() + " (耗时: " + withSolutionTime + " ns)");
        System.out.println("最优切割方案: " + result3.getCuts());
        
        // 验证结果一致性
        if (result1 == result2 && result2 == result3.getMaxRevenue()) {
            System.out.println("✓ 所有算法结果一致");
        } else {
            System.out.println("✗ 算法结果不一致！");
        }
    }
}