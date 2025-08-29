package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 完全背包问题，每个物品的值可以被重复使用，这也就意味着物品可以在 dp[i][...] 中出现多次，更新这一项多次
 *
 * 新的状态转换方程为：dp[i][c] = max(dp[i-1][c], dp[i][c-w[i]] + val[i])
 * 这个方程使用 1-based 的重量数组和价值数组
 *
 * @author magicliang
 *
 *         date: 2025-08-29 11:03
 */
public class UnboundedKnapsackProblem {

    /**
     * 使用深度优先搜索解决完全背包问题
     * 
     * @param wgt 物品重量数组
     * @param val 物品价值数组  
     * @param item 当前考虑的物品索引（从1开始）
     * @param cap 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     */
    public int unboundedKnapsackProblemDfs(int[] wgt, int[] val, int item, int cap) {
        // 在无容量的时候，和原始问题一样，返回 0
        if (item == 0 || cap == 0) {
            return 0;
        }

        // 不选择当前物品的情况
        final int no = unboundedKnapsackProblemDfs(wgt, val, item - 1, cap);
        if (wgt[item - 1] > cap) {
            // 不能放入这个物品
            return no;
        }

        // 选择当前物品的情况（注意：item不减1，因为可以重复选择）
        int yes = unboundedKnapsackProblemDfs(wgt, val, item, cap - wgt[item - 1]) + val[item - 1];
        // 返回两种情况的最大值
        return Math.max(yes, no);
    }
}
