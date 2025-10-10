package algorithm.sorting;

import java.util.Arrays;

/**
 * @author liangchuan
 * 口诀：先按各位升序排，再按十位升序排，最后按百位升序排，对每一位进行计数排序-因为每一位最多有 base 个数，所以 counter 数组大小为 base。从右往左的意思是把中间结果按照从右往左的顺序消耗 counter 数组重新找位置
 * 基数排序（LSD Least Significant Digit 最低有效位，十进制）口诀与要点：
 * - 逐位稳定计数排序：先按个位升序排，再按十位升序排，再按百位……直到最高位。
 * - 计数数组 counter 的大小等于进制 base（此处为 10）：先计数，再做前缀和（累计），用于定位。
 * - 回写从右往左“消费”前缀和索引，保证稳定性（相同关键位元素相对顺序不变）。

 * 补充：
 * - 每一轮作用于“整个数组”（不是只处理某些元素）。
 * - 当前实现仅支持非负整数；如需支持负数，可通过偏移（通过把数据在 arr 中与 min 的相对位置转化为计数数组的 pos 下标来实现）或分桶处理再合并。
 */
public class MyRadixSort {

    /*
     * 结果数组，和输入数组等长
     */
    static int[] res;

    /*
     * base的大小等于一个 range 的大小
     */
    static int[] counter;

    public static synchronized int[] radixSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        int n = arr.length;
        res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = arr[i];
        }

        counter = new int[10];

        // 当前的指数结果，会从1递增到最高位

        int max = Arrays.stream(arr).max().orElse(0);

        int exp = 1;
        // 对521而言，1000才会让它大于0，这样可以保证它的全部位数都被消费了
        while (max / exp > 0) {
            countAndSort(exp);
            exp *= 10;
        }

        return res;
    }

    private static void countAndSort(int exp) {
        // 清洗 counter 数组
        int base = counter.length;
        for (int i = 0; i < base; i++) {
            counter[i] = 0;
        }

        for(int i = res.length - 1; i >= 0 ; i--) {
            int currentDigit;
            currentDigit = res[i] / exp % base;
            counter[currentDigit]++;
        }

        // 类似分数的排行，制造一个前缀和数组，修正这个 counter 数组
        for (int i = 1; i < base; i++) {
            // 这样每个 counter 本身变成一个 0-base 的栈，它的长度就是当前可排序队列的大小
            counter[i] += counter[i-1];
        }

        int[] tmpArr = new int[res.length];
        for(int i = 0; i < res.length; i++) {
            tmpArr[i] = res[i];
        }

        // 由待排序数组的每个数驱动，开始对 counter 进行“出栈”操作
        // 所谓的从右向左体现在两个地方：从最低位开始计数排序，排序的中间结果每次也是从右到左遍历
        // 从右向左遍历数组，确保相同值的元素在排序后保持原始顺序（稳定性）。
        for(int i = res.length - 1; i >= 0; i--) {
            int currentDigit = tmpArr[i] / exp % base;
            // 举例：当前计数数组有1个1，2个2，3个3，4个4
            // 此时我们的 tmpArr[i] 对应位是2，那么2应该排在 res 的第三位-从0base看就是2。因为前缀和性质，现在 counter[2]的值是3，所以位数要改成3-1
            res[counter[currentDigit] - 1] = tmpArr[i];
            counter[currentDigit]--;
        }
    }
}
