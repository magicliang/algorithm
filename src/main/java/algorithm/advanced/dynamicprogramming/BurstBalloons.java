package algorithm.advanced.dynamicprogramming;

import java.util.Arrays;

/**
 * 戳气球问题 - 区间DP经典问题
 * 
 * 问题描述：
 * 有 n 个气球，编号为0到n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
 * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i-1] * nums[i] * nums[i+1] 枚硬币。
 * 这里的 i-1 和 i+1 代表和 i 相邻的两个气球的序号。如果 i-1或 i+1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
 * 
 * 核心思想：不考虑先戳破哪个，而是考虑最后戳破哪个！
 */
public class BurstBalloons {
    
    /**
     * 戳气球获得最大硬币数
     * 时间复杂度：O(n³)
     * 空间复杂度：O(n²)
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        
        // 扩展数组，两端添加1，方便处理边界
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }
        
        // dp[i][j]表示戳破开区间(i,j)内所有气球能获得的最大硬币数
        // 注意：这里是开区间，不包括i和j位置的气球
        int[][] dp = new int[n + 2][n + 2];
        
        // 区间长度从3开始（开区间(i,j)至少需要3个位置才能戳破1个气球）
        for (int len = 3; len <= n + 2; len++) {
            // 枚举区间起点
            for (int i = 0; i <= n + 2 - len; i++) {
                int j = i + len - 1; // 区间终点
                
                // 枚举最后戳破的气球k（i < k < j）
                for (int k = i + 1; k < j; k++) {
                    // 最后戳破k时，左右两边的气球都已经被戳破了
                    // 此时k的相邻气球就是i和j位置的气球
                    int coins = dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[0][n + 1];
    }
    
    /**
     * 测试方法
     */
}