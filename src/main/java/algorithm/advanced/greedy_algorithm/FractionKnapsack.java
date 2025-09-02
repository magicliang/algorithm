package algorithm.advanced.greedy_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * 分数背包问题（贪心算法）
 * 
 * 问题描述：
 * 给定一个容量为 cap 的背包和 n 个物品，每个物品有重量 weight 和价值 value。
 * 与 0-1 背包不同，分数背包允许将物品分割，即可以只取物品的一部分。
 * 目标是在不超过背包容量的前提下，使装入背包的物品总价值最大。
 * 
 * 贪心策略：
 * 按照物品的价值密度（value/weight）从高到低排序，优先选择价值密度高的物品。
 * 如果物品能完全装入，则完全装入；如果不能完全装入，则按比例装入剩余容量。
 * 
 * 时间复杂度：O(n log n) - 主要是排序的复杂度
 * 空间复杂度：O(n) - 用于存储 Item 数组
 */
public class FractionKnapsack {

    /**
     * 物品类，包含重量和价值
     */
    @AllArgsConstructor
    class Item {
        /** 物品重量 */
        int weight;
        /** 物品价值 */
        int value;
    }

    /**
     * 分数背包算法
     * 
     * @param wgt 物品重量数组
     * @param val 物品价值数组
     * @param cap 背包容量
     * @return 背包能装入的最大价值
     */
    public double fractionalKnapsack(int[] wgt, int[] val, int cap) {
        // 将重量和价值数组转换为物品对象数组，便于排序和处理
        Item[] items = new Item[wgt.length];
        for (int i = 0; i < wgt.length; i++) {
            items[i] = new Item(wgt[i], val[i]);
        }

        // 这里的逆序意味着是降序排序
        // 按价值密度（value/weight）降序排序，贪心选择价值密度最高的物品
        Arrays.sort(items, Comparator.comparingDouble(item -> -((double) item.value / item.weight)));

        double totalValue = 0;

        // 易错的点：cap 必须在各个 cap 里递减
        // 遍历排序后的物品，按贪心策略装入背包
        for (Item item : items) {
            if (item.weight < cap) {
                // 物品能完全装入背包，完全装入
                totalValue += item.value;
                cap -= item.weight;
            } else {
                // 用剩余比例装满
                // 物品不能完全装入，按剩余容量的比例装入
                totalValue += (cap * ((double) item.value / item.weight));

                // 易错的点：不要用外部的 while 循环，因为如果物品装不满，会触发 item 被重复获取，所以此处
                // 背包已满，直接返回结果
                return totalValue;
            }
        }

        // 所有物品都能完全装入的情况
        return totalValue;
    }

    /**
     * 测试用例
     */
    public static void main(String[] args) {
        FractionKnapsack fk = new FractionKnapsack();
        
        // 测试用例1：基本情况
        System.out.println("=== 测试用例1：基本情况 ===");
        int[] wgt1 = {10, 20, 30};
        int[] val1 = {60, 100, 120};
        int cap1 = 50;
        double result1 = fk.fractionalKnapsack(wgt1, val1, cap1);
        System.out.printf("物品重量: %s\n", Arrays.toString(wgt1));
        System.out.printf("物品价值: %s\n", Arrays.toString(val1));
        System.out.printf("背包容量: %d\n", cap1);
        System.out.printf("最大价值: %.2f\n", result1);
        System.out.printf("预期结果: 240.00 (完全装入物品2(价值密度5.0)和物品1(价值密度6.0)，部分装入物品3)\n\n");
        
        // 测试用例2：所有物品都能装入
        System.out.println("=== 测试用例2：所有物品都能装入 ===");
        int[] wgt2 = {10, 20, 30};
        int[] val2 = {60, 100, 120};
        int cap2 = 100;
        double result2 = fk.fractionalKnapsack(wgt2, val2, cap2);
        System.out.printf("物品重量: %s\n", Arrays.toString(wgt2));
        System.out.printf("物品价值: %s\n", Arrays.toString(val2));
        System.out.printf("背包容量: %d\n", cap2);
        System.out.printf("最大价值: %.2f\n", result2);
        System.out.printf("预期结果: 280.00 (所有物品都能完全装入)\n\n");
        
        // 测试用例3：只能装入一个物品的一部分
        System.out.println("=== 测试用例3：只能装入一个物品的一部分 ===");
        int[] wgt3 = {50, 20, 30};
        int[] val3 = {300, 100, 120};
        int cap3 = 25;
        double result3 = fk.fractionalKnapsack(wgt3, val3, cap3);
        System.out.printf("物品重量: %s\n", Arrays.toString(wgt3));
        System.out.printf("物品价值: %s\n", Arrays.toString(val3));
        System.out.printf("背包容量: %d\n", cap3);
        System.out.printf("最大价值: %.2f\n", result3);
        System.out.printf("预期结果: 150.00 (装入物品1的一半，价值密度6.0)\n\n");
        
        // 测试用例4：边界情况 - 背包容量为0
        System.out.println("=== 测试用例4：边界情况 - 背包容量为0 ===");
        int[] wgt4 = {10, 20, 30};
        int[] val4 = {60, 100, 120};
        int cap4 = 0;
        double result4 = fk.fractionalKnapsack(wgt4, val4, cap4);
        System.out.printf("背包容量: %d\n", cap4);
        System.out.printf("最大价值: %.2f\n", result4);
        System.out.printf("预期结果: 0.00 (背包容量为0)\n\n");
        
        // 测试用例5：单个物品
        System.out.println("=== 测试用例5：单个物品 ===");
        int[] wgt5 = {40};
        int[] val5 = {200};
        int cap5 = 30;
        double result5 = fk.fractionalKnapsack(wgt5, val5, cap5);
        System.out.printf("物品重量: %s\n", Arrays.toString(wgt5));
        System.out.printf("物品价值: %s\n", Arrays.toString(val5));
        System.out.printf("背包容量: %d\n", cap5);
        System.out.printf("最大价值: %.2f\n", result5);
        System.out.printf("预期结果: 150.00 (装入物品的3/4)\n");
    }
}