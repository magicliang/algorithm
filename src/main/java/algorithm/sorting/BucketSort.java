package algorithm.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序实现类
 *
 * 桶排序是一种非比较排序算法，适用于数据分布均匀的场景。
 * 通过将数据分到有限数量的桶中，每个桶再单独排序（通常使用插入排序或其他简单排序算法），
 * 最后合并所有桶的结果得到有序序列。
 *
 * 算法特点：
 * 1. 时间复杂度：平均 O(n + k)，最坏 O(n^2)，其中 n 是元素个数，k 是桶的数量
 * 2. 空间复杂度：O(n + k)
 * 3. 稳定性：取决于桶内排序算法的稳定性
 * 4. 适用场景：数据分布均匀且范围已知
 *
 * 与计数排序的对比：
 * - 桶排序：适用于数据分布均匀的场景，通过分桶减少比较次数
 * - 计数排序：适用于数据范围较小的整数排序，通过计数数组记录频次
 *
 * 使用限制与适用场景：
 * 桶排序的性能高度依赖于数据分布和桶的数量选择。如果数据分布不均匀，可能导致某些桶过载，
 * 影响整体性能。因此，桶排序适用于数据分布均匀且范围已知的情况。
 *
 * 桶排序的优化方向：
 * 1. 动态调整桶的数量和大小，以适应数据分布
 * 2. 桶内排序算法的选择（如插入排序、快速排序等）
 */
public class BucketSort {

    /**
     * 桶排序
     * @param arr 待排序数组
     * @param bucketSize 每个桶的大小
     */
    public static void sort(int[] arr, int bucketSize) {
        if (arr.length == 0) {
            return;
        }

        // 找到数组中的最小值和最大值
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int value : arr) {
            if (value < minValue) {
                minValue = value;
            } else if (value > maxValue) {
                maxValue = value;
            }
        }

        // 计算桶的数量
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // 将元素分配到桶中
        for (int value : arr) {
            int bucketIndex = (value - minValue) / bucketSize;
            buckets.get(bucketIndex).add(value);
        }

        // 对每个桶进行排序，并将结果合并到原数组
        int currentIndex = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int value : bucket) {
                arr[currentIndex++] = value;
            }
        }
    }
}