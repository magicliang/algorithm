package algorithm.selectedtopics.leetcode.differencialarray;

public class RangeAddition {
    public int[] getModifiedArray(int length, int[][] updates) {
        // 初始数组全为 0
        int[] nums = new int[length];
        Difference df = new Difference(nums);

        for (int[] update : updates) {
            int start = update[0];
            int end = update[1];
            int inc = update[2];
            df.increment(start, end, inc);
        }

        return df.result();
    }
}