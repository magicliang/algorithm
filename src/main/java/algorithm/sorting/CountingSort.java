package algorithm.sorting;

import java.util.Arrays;

/**
 * 计数排序实现类
 * <p>
 * 计数排序是一种非比较排序算法，适用于整数排序场景，特别是当数据范围不大时。
 * 与位图排序不同，计数排序可以处理重复元素，通过计数数组记录每个元素的出现次数。
 * <p>
 * 算法特点：
 * 1. 时间复杂度：O(n + k)，其中n是元素个数，k是数据范围
 * 2. 空间复杂度：O(k)
 * 3. 稳定性：可以保持稳定性（通过累积计数实现）
 * 4. 适用场景：数据范围已知且有限的整数排序
 * <p>
 * 与位图排序的对比：
 * - 计数排序：记录频次，支持重复元素，空间复杂度O(k)
 * - 位图排序：记录存在性，自动去重，空间复杂度O(k/8)
 * <p>
 * 内存权衡：当内存不足以一次性处理整个数据范围时，可以采用分块处理策略，
 * 类似于位图排序中的segmentedBitmapSort方法。
 * <p>
 * 使用限制与适用场景：
 * 看到这里，你也许会觉得计数排序非常巧妙，仅通过统计数量就可以实现高效的排序。
 * 然而，使用计数排序的前置条件相对较为严格。
 * <p>
 * 计数排序只适用于非负整数。若想将其用于其他类型的数据，需要确保这些数据可以
 * 转换为非负整数，并且在转换过程中不能改变各个元素之间的相对大小关系。
 * <p>
 * 数组索引必须非负，但可以通过适当的转换处理其他数据类型，只要保持相对大小关系不变
 * <p>
 * 例如，对于包含负数的整数数组，可以先给所有数字加上一个常数，将全部数字转化为正数，
 * 排序完成后再转换回去。
 * <p>
 * 计数排序适用于数据量大但数据范围较小的情况。比如，在上述示例中 k 不能太大，
 * 否则会占用过多空间。而当 k ≫ n 时（此时数据范围大，分布稀疏），计数排序使用 O(n + k) 时间（服从一般桶排序的规律），可能比
 * O(n log n) 的排序算法还要慢。
 */
public class CountingSort {

    /**
     * 标准计数排序实现（非稳定版本）
     *
     * @param arr      待排序数组
     * @param minValue 数组中的最小值
     * @param maxValue 数组中的最大值
     * @return 排序后的新数组
     */
    public static int[] countingSort(int[] arr, int minValue, int maxValue) {
        if (arr == null || arr.length <= 1) {
            return arr != null ? arr.clone() : new int[0];
        }

        // 计算数据范围
        int range = maxValue - minValue + 1;

        // 1. 创建计数数组
        int[] count = new int[range];

        // 2. 统计每个元素的出现次数
        for (int num : arr) {
            if (num < minValue || num > maxValue) {
                throw new IllegalArgumentException("元素 " + num + " 超出范围 [" + minValue + ", " + maxValue + "]");
            }
            count[num - minValue]++;
        }

        // 3. 构建排序结果
        int[] result = new int[arr.length];

        // 结果数组不重复移动
        int index = 0;

        for (int i = 0; i < range; i++) {
            // 消耗完一个下标内的全部次数以后，本计数器才移动
            while (count[i] > 0) {
                // 把数组的相对位置转换成原始结果
                result[index++] = i + minValue;
                count[i]--;
            }
        }

        return result;
    }

    /**
     * 稳定计数排序实现
     * 保持相同元素的原始相对顺序
     *
     * @param arr      待排序数组
     * @param minValue 数组中的最小值
     * @param maxValue 数组中的最大值
     * @return 排序后的新数组（稳定排序）
     */
    public static int[] stableCountingSort(int[] arr, int minValue, int maxValue) {
        if (arr == null || arr.length <= 1) {
            return arr != null ? arr.clone() : new int[0];
        }

        int range = maxValue - minValue + 1;

        // 1. 创建计数数组
        int[] count = new int[range];

        // 2. 统计频次
        for (int num : arr) {
            if (num < minValue || num > maxValue) {
                throw new IllegalArgumentException("元素 " + num + " 超出范围 [" + minValue + ", " + maxValue + "]");
            }
            count[num - minValue]++;
        }

        // 3. 计算累积计数（关键步骤，实现稳定性）
        // 将计数数组转换为前缀和数组，用于确定每个元素在结果数组中的最终位置
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1]; // 当前桶的结束位置 = 前一个桶的结束位置 + 当前桶的计数
        }

        // 4. 从后向前构建稳定排序结果
        // 通过倒序遍历原数组，确保相同元素的相对顺序保持不变（稳定性）
        // 最难的地方就是消费前缀和数组，这类似一个引导插入过程：等于首先找到要排序的数，然后把要排序的数从 counter 数组里面找到特定的位置，然后反插入进结果数组里面
        // 稳定性的来源：从右向左消费前缀和数组：通过倒序遍历原数组，确保相同键值的元素在结果数组中的相对顺序不变。所以原数组和前缀和数组的使用都是需要的。
        int[] result = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            int num = arr[i]; // 当前处理的元素
            // 一加一减，注意这个 - 1 的操作。想象第一个排的元素，恰好就是前缀和数组n的最后一个元素、最大的元素，它在结果数组里的位置恰好是n-1
            int pos = count[num - minValue] - 1; // 根据前缀和数组确定当前元素的正确位置
            result[pos] = num; // 将元素放入结果数组的对应位置
            count[num - minValue]--; // 更新前缀和数组，为下一个相同元素预留位置
        }

        return result;
    }

    /**
     * 原地计数排序（内存优化版本）
     * 直接在原数组上进行排序，减少内存分配
     *
     * @param arr      待排序数组（会被修改）
     * @param minValue 数组中的最小值
     * @param maxValue 数组中的最大值
     */
    public static void inPlaceCountingSort(int[] arr, int minValue, int maxValue) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int range = maxValue - minValue + 1;
        int[] count = new int[range];

        // 统计频次
        for (int num : arr) {
            if (num < minValue || num > maxValue) {
                throw new IllegalArgumentException("元素 " + num + " 超出范围 [" + minValue + ", " + maxValue + "]");
            }
            count[num - minValue]++;
        }

        // 原地重写排序结果
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) {
                arr[index++] = i + minValue;
                count[i]--;
            }
        }
    }

    /**
     * 自动检测范围的计数排序
     * 自动计算数组中的最小值和最大值
     *
     * @param arr 待排序数组
     * @return 排序后的新数组
     */
    public static int[] countingSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr != null ? arr.clone() : new int[0];
        }

        // 自动检测范围
        int minValue = arr[0];
        int maxValue = arr[0];

        for (int num : arr) {
            minValue = Math.min(minValue, num);
            maxValue = Math.max(maxValue, num);
        }

        return countingSort(arr, minValue, maxValue);
    }

    /**
     * 分块计数排序（处理超大数据范围）
     * 当数据范围过大导致内存不足时使用
     *
     * @param arr       待排序数组
     * @param blockSize 每个块的大小
     * @return 排序后的新数组
     */
    public static int[] segmentedCountingSort(int[] arr, int blockSize) {
        if (arr == null || arr.length <= 1) {
            return arr != null ? arr.clone() : new int[0];
        }

        // 检测范围
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int num : arr) {
            minValue = Math.min(minValue, num);
            maxValue = Math.max(maxValue, num);
        }

        int[] result = new int[arr.length];
        int resultIndex = 0;

        // 分块处理
        for (int blockStart = minValue; blockStart <= maxValue; blockStart += blockSize) {
            int blockEnd = Math.min(blockStart + blockSize - 1, maxValue);

            // 收集当前块内的元素
            int[] blockElements = new int[arr.length];
            int blockCount = 0;

            for (int num : arr) {
                if (num >= blockStart && num <= blockEnd) {
                    blockElements[blockCount++] = num;
                }
            }

            // 对当前块排序
            if (blockCount > 0) {
                int[] sortedBlock = countingSort(
                        Arrays.copyOf(blockElements, blockCount),
                        blockStart,
                        blockEnd
                );

                // 复制到结果数组
                System.arraycopy(sortedBlock, 0, result, resultIndex, sortedBlock.length);
                resultIndex += sortedBlock.length;
            }
        }

        return result;
    }

    /**
     * 示例用法
     */
    public static void main(String[] args) {
        // 示例1：标准计数排序
        int[] arr1 = {4, 2, 2, 8, 3, 3, 1};
        int[] sorted1 = countingSort(arr1, 1, 8);
        System.out.println("标准计数排序: " + Arrays.toString(sorted1));

        // 示例2：稳定计数排序
        int[] arr2 = {4, 2, 2, 8, 3, 3, 1};
        int[] sorted2 = stableCountingSort(arr2, 1, 8);
        System.out.println("稳定计数排序: " + Arrays.toString(sorted2));

        // 示例3：自动范围检测
        int[] arr3 = {100, 95, 87, 92, 100, 95};
        int[] sorted3 = countingSort(arr3);
        System.out.println("自动范围计数排序: " + Arrays.toString(sorted3));

        // 示例4：原地排序
        int[] arr4 = {5, 2, 9, 1, 5, 6};
        inPlaceCountingSort(arr4, 1, 9);
        System.out.println("原地计数排序: " + Arrays.toString(arr4));

        // 示例5：分块处理
        int[] arr5 = {1000, 500, 2000, 1500, 800, 1200};
        int[] sorted5 = segmentedCountingSort(arr5, 500);
        System.out.println("分块计数排序: " + Arrays.toString(sorted5));
    }
}
