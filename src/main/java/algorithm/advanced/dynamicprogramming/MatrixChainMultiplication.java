package algorithm.advanced.dynamicprogramming;

import java.util.Arrays;

/**
 * 矩阵链乘法问题 - 区间DP的鼻祖问题
 * 
 * 问题描述：
 * 给定n个矩阵的链A1, A2, ..., An，其中矩阵Ai的维数为p[i-1] × p[i]。
 * 求完全括号化方案，使得计算A1A2...An所需的标量乘法次数最少。
 * 
 * 例如：A1(10×20) × A2(20×30) × A3(30×40) × A4(40×30)
 * 不同的括号化方案会导致不同的计算量：
 * - ((A1A2)A3)A4: 10×20×30 + 10×30×40 + 10×40×30 = 6000 + 12000 + 12000 = 30000
 * - (A1(A2A3))A4: 20×30×40 + 10×20×40 + 10×40×30 = 24000 + 8000 + 12000 = 44000
 * 
 * 核心思想：dp[i][j]表示计算矩阵Ai到Aj的最少标量乘法次数
 */
public class MatrixChainMultiplication {
    
    /**
     * 计算矩阵链乘法的最少标量乘法次数
     * @param p 矩阵维度数组，p[i-1] × p[i]是第i个矩阵的维度
     * @return 最少标量乘法次数
     * 
     * 时间复杂度：O(n³)
     * 空间复杂度：O(n²)
     */
    public int matrixChainOrder(int[] p) {
        if (p == null || p.length < 2) {
            throw new IllegalArgumentException("至少需要2个维度来定义1个矩阵");
        }
        int n = p.length - 1; // 矩阵个数
        
        // dp[i][j]表示计算矩阵Ai到Aj的最少标量乘法次数
        // 注意：这里使用1-based索引，dp[1][n]是最终答案
        int[][] dp = new int[n + 1][n + 1];
        
        // 长度为1的链（单个矩阵）不需要乘法
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 0;
        }
        
        // 枚举链长度：从2到n
        for (int len = 2; len <= n; len++) {
            // 枚举起始位置
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1; // 结束位置
                dp[i][j] = Integer.MAX_VALUE;
                
                // 枚举分割点k：在k和k+1之间分割
                for (int k = i; k < j; k++) {
                    // 计算在k处分割的代价
                    // dp[i][k]: 计算Ai...Ak的代价
                    // dp[k+1][j]: 计算A(k+1)...Aj的代价  
                    // p[i-1]*p[k]*p[j]: 两个结果矩阵相乘的代价
                    int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[1][n];
    }
    
    /**
     * 带路径记录的版本，可以输出最优括号化方案
     */
    public class MatrixChainResult {
        public int minCost;
        public String optimalParentheses;
        
        public MatrixChainResult(int minCost, String optimalParentheses) {
            this.minCost = minCost;
            this.optimalParentheses = optimalParentheses;
        }
    }
    
    public MatrixChainResult matrixChainOrderWithPath(int[] p) {
        int n = p.length - 1;
        int[][] dp = new int[n + 1][n + 1];
        int[][] split = new int[n + 1][n + 1]; // 记录最优分割点
        
        // 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 0;
        }
        
        // 填充DP表
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                        split[i][j] = k; // 记录最优分割点
                    }
                }
            }
        }
        
        // 构造最优括号化方案
        String parentheses = constructOptimalParentheses(split, 1, n);
        
        return new MatrixChainResult(dp[1][n], parentheses);
    }
    
    /**
     * 根据分割点表构造最优括号化方案
     */
    private String constructOptimalParentheses(int[][] split, int i, int j) {
        if (i == j) {
            return "A" + i;
        } else {
            int k = split[i][j];
            return "(" + constructOptimalParentheses(split, i, k) + 
                   constructOptimalParentheses(split, k + 1, j) + ")";
        }
    }
    
    /**
     * 带详细过程输出的版本
     */
    public int matrixChainOrderWithProcess(int[] p) {
        int n = p.length - 1;
        int[][] dp = new int[n + 1][n + 1];
        
        System.out.println("矩阵维度数组: " + Arrays.toString(p));
        System.out.println("矩阵个数: " + n);
        
        // 打印矩阵信息
        for (int i = 1; i <= n; i++) {
            System.out.printf("A%d: %d×%d\n", i, p[i-1], p[i]);
        }
        
        // 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 0;
        }
        
        System.out.println("\nDP计算过程:");
        
        // 填充DP表
        for (int len = 2; len <= n; len++) {
            System.out.println("长度为 " + len + " 的矩阵链:");
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                
                System.out.printf("  计算dp[%d][%d] (A%d到A%d):\n", i, j, i, j);
                
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                    System.out.printf("    k=%d: dp[%d][%d](%d) + dp[%d][%d](%d) + %d×%d×%d = %d\n",
                        k, i, k, dp[i][k], k+1, j, dp[k+1][j], 
                        p[i-1], p[k], p[j], cost);
                    
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
                
                System.out.printf("  dp[%d][%d] = %d\n", i, j, dp[i][j]);
            }
        }
        
        // 打印DP表
        System.out.println("\n最终DP表:");
        System.out.print("    ");
        for (int j = 1; j <= n; j++) {
            System.out.printf("%8d", j);
        }
        System.out.println();
        
        for (int i = 1; i <= n; i++) {
            System.out.printf("%3d ", i);
            for (int j = 1; j <= n; j++) {
                if (i <= j) {
                    System.out.printf("%8d", dp[i][j]);
                } else {
                    System.out.printf("%8s", "-");
                }
            }
            System.out.println();
        }
        
        return dp[1][n];
    }
    
    /**
     * 测试方法
     */
}