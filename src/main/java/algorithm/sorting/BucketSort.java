package algorithm.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序实现
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