package algorithm.selectedtopics.leetcode.differencialarray;

/**
 * 区间加法实现类，用于处理多个区间加法操作。
 * 通过差分数组（Differencial）高效实现区间加法。
 */
public class RangeAddition {
    
    /**
     * 根据给定的区间操作，返回修改后的数组。
     * @param length 数组长度。
     * @param updates 区间操作数组，每个操作为 [start, end, inc]。
     * @return 修改后的数组。
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        // 直接给出0数组
        Differencial differencial = new Differencial(new int[length]);
        for (int[] update : updates) {
            int begin = update[0];
            int end = update[1];
            int diff = update[2];
            differencial.increment(begin, end, diff);
        }

        return differencial.result();
    }
}

/**
 * 差分数组工具类，用于高效处理区间加法操作。
 */
class Differencial {

    // 差分数组
    private int[] diff;

    /**
     * 构造函数：根据初始数组构造差分数组。
     * @param nums 初始数组。
     */
    public Differencial(int[] nums) {
        if (nums == null) {
        }
        int n = nums.length;
        diff = new int[n];
        if (n > 0) {
            diff[0] = nums[0];
        }

        for (int i = 1; i < n; i++) {
            // 这个差分数组没有下标偏移
            diff[i] = nums[i] - nums[i- 1];
        }
    }

    /**
     * 区间加法操作：给闭区间 [begin, end] 增加 val（可以是负数）。
     * @param begin 区间起始索引（包含）。
     * @param end 区间结束索引（包含）。
     * @param val 增加的值。
     * @throws IllegalArgumentException 如果区间不合法（begin < 0 || end >= length || begin > end）。
     */
    public void increment(int begin, int end, int val) {
        int n = diff.length;
        if (begin < 0 || end >= n || begin > end) {
            throw new IllegalArgumentException();
        }

        diff[begin] += val;
        if (end + 1 <= n - 1) {
            // 在可行的范围内，将下一个元素减去val
            diff[end + 1] -= val;
        }
    }

    /**
     * 返回结果数组：根据差分数组构造最终结果。
     * @return 结果数组。
     */
    public int[] result() {
        int n = diff.length;
        int[] res = new int[n];

        if (n == 0) {
            return res;
        }

        res[0] = diff[0];

        for (int i = 1; i < n; i++) {
            // 基于两个数组生成结果数组
            res[i] = res[i - 1] + diff[i];
        }

        return res;
    }

}
