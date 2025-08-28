package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 背包问题
 *
 * @author magicliang
 *
 *         date: 2025-08-28 18:49
 */
public class Knapsacks {

    /**
     * 使用深度优先搜索解决0-1背包问题
     * 
     * @param weights 物品重量数组，索引从0开始
     * @param values 物品价值数组，索引从0开始
     * @param item 当前考虑的物品编号（1-based，即第1个物品对应数组索引0）。和上面两个参数不一样。
     * @param capacity 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     * 
     * 由于每个物品都会产生不选和选两条搜索分支，因此时间复杂度为：O(2^n)，其中n是物品数量
     * 空间复杂度：O(n)，递归调用栈的深度
     */
    public int knapsacksDfs(int[] weights, int[] values, int item, int capacity) {
        // 缺省价值为0
        if (capacity == 0 || item == 0) {
            return 0;
        }

        // 这个算法有个吊诡的点，就是 item 是 1-based的，而两个数组都是0based，所以 item - 1 代表着当前物品在数组里的坐标
        if (weights[item - 1] > capacity) {
            // 如果无法把当前物品放入背包，则不放入，沿用上一个物品的解
            return knapsacksDfs(weights, values, item - 1, capacity);
        }

        // 在上一个解的基础上放入当前物品
        int yes = knapsacksDfs(weights, values, item - 1, capacity - weights[item - 1]) + values[item - 1];
        // 不放入当前物品
        int no = knapsacksDfs(weights, values, item - 1, capacity);
        return Math.max(yes, no);
    }

    // 记忆化搜索页需要 dfs 递归搜索的

    /**
     * 使用记忆化搜索解决0-1背包问题
     * 
     * @param weights 物品重量数组，索引从0开始
     * @param values 物品价值数组，索引从0开始
     * @param item 当前考虑的物品编号（1-based）
     * @param capacity 背包剩余容量
     * @return 在给定容量下能获得的最大价值
     * 
     * 时间复杂度：O(n × capacity)，其中n是物品数量
     * 空间复杂度：O(n × capacity)用于存储memo数组
     */
    public int knapsacksMemoization(int[] weights, int[] values, int item, int capacity) {
        // 易错的点：负价值更安全
        // 初始化memo数组，-1表示该状态尚未计算
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

}
