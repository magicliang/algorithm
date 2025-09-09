package algorithm.advanced.greedy_algorithm;

import java.util.Arrays;
import java.util.Comparator;
import lombok.AllArgsConstructor;

public class FractionKnapsack {

    public double fractionalKnapsack(int[] wgt, int[] val, int cap) {
        Item[] items = new Item[wgt.length];
        for (int i = 0; i < wgt.length; i++) {
            items[i] = new Item(wgt[i], val[i]);
        }

        // 这里的逆序意味着是降序排序
        Arrays.sort(items, Comparator.comparingDouble(item -> -((double) item.value / item.weight)));

        double totalValue = 0;

        // 易错的点：cap 必须在各个 cap 里递减
        for (Item item : items) {
            if (item.weight < cap) {
                totalValue += item.value;
                cap -= item.weight;
            } else {
                // 用剩余比例装满
                totalValue += (cap * ((double) item.value / item.weight));

                // 易错的点：不要用外部的 while 循环，因为如果物品装不满，会触发 item 被重复获取，所以此处
                return totalValue;
            }
        }

        return totalValue;
    }

    @AllArgsConstructor
    class Item {

        int weight;

        int value;

    }
}