package algorithm.advanced.dynamicprogramming;

import java.util.Arrays;

/**
 * 切木棍问题 - 区间DP经典问题
 * 
 * 问题描述：
 * 有一根长度为 n 个单位的木棍，棍上从 0 到 n 标记了若干位置。
 * 给你一个整数数组 cuts，其中 cuts[i] 表示你需要将棍子切开的位置。
 * 你可以按顺序完成切割，也可以根据需要更改切割的顺序。
 * 每次切割的成本都是当前要切割的棍子的长度，切棍子的总成本是历次切割成本的总和。
 * 
 * 核心思想：不是在原木棍区间上做DP，而是在切点区间上做DP！
 */
public class CuttingStick {
    
    /**
     * 切木棍的最小总成本
     * 时间复杂度：O(m³)，其中m是cuts数组的长度
     * 空间复杂度：O(m²)
     */
    public int minCost(int n, int[] cuts) {
        int length = cuts.length;
        Arrays.sort(cuts); // 首先对切点进行排序
        
        // 扩展切点数组，两端添加0和n
        // 这样形成完整的切点序列：[0, cuts[0], cuts[1], ..., cuts[m-1], n]
        int[] newCuts = new int[length + 2];
        newCuts[0] = 0;
        newCuts[length + 1] = n;
        for (int i = 0; i < length; i++) {
            newCuts[i + 1] = cuts[i];
        }
        
        int newLength = length + 2;
        // dp[i][j]表示在切点newCuts[i]和newCuts[j]之间进行所有必要切割的最小成本
        int[][] dp = new int[newLength][newLength];
        
        // 区间长度从2开始（相邻切点之间无需切割，成本为0）
        // 这里的长度是指切点数组中的索引距离
        for (int len = 2; len <= newLength - 1; len++) {
            // 枚举区间起点
            for (int i = 0; i + len < newLength; i++) {
                int j = i + len; // 区间终点
                dp[i][j] = Integer.MAX_VALUE;
                
                // 枚举中间切点k（i < k < j）
                for (int k = i + 1; k < j; k++) {
                    // 在位置newCuts[k]处切割的成本 = 当前木棍长度 = newCuts[j] - newCuts[i]
                    int currentCost = newCuts[j] - newCuts[i];
                    int totalCost = dp[i][k] + dp[k][j] + currentCost;
                    dp[i][j] = Math.min(dp[i][j], totalCost);
                }
            }
        }
        
        return dp[0][newLength - 1];
    }
    
    /**
     * 带详细过程输出的版本，用于理解算法过程
     */
    public int minCostWithProcess(int n, int[] cuts) {
        int length = cuts.length;
        Arrays.sort(cuts);
        
        int[] newCuts = new int[length + 2];
        newCuts[0] = 0;
        newCuts[length + 1] = n;
        for (int i = 0; i < length; i++) {
            newCuts[i + 1] = cuts[i];
        }
        
        System.out.println("扩展后的切点数组: " + Arrays.toString(newCuts));
        
        int newLength = length + 2;
        int[][] dp = new int[newLength][newLength];
        
        for (int len = 2; len <= newLength - 1; len++) {
            System.out.println("\n处理长度为 " + len + " 的区间:");
            for (int i = 0; i + len < newLength; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                
                System.out.printf("  计算dp[%d][%d] (木棍范围[%d,%d]):\n", 
                    i, j, newCuts[i], newCuts[j]);
                
                for (int k = i + 1; k < j; k++) {
                    int currentCost = newCuts[j] - newCuts[i];
                    int totalCost = dp[i][k] + dp[k][j] + currentCost;
                    
                    System.out.printf("    在位置%d切割: dp[%d][%d](%d) + dp[%d][%d](%d) + %d = %d\n",
                        newCuts[k], i, k, dp[i][k], k, j, dp[k][j], currentCost, totalCost);
                    
                    dp[i][j] = Math.min(dp[i][j], totalCost);
                }
                
                System.out.printf("  dp[%d][%d] = %d\n", i, j, dp[i][j]);
            }
        }
        
        return dp[0][newLength - 1];
    }
}