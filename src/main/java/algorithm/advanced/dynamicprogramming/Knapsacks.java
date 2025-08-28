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
     * @param item 当前考虑的物品编号（1-based，即第1个物品对应数组索引0）
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


}
