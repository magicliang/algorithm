package algorithm.selectedtopics.leetcode.differencialarray;

public class RangeAddition {
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

class Differencial {

    private int[] diff;

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
