package algorithm.selectedtopics.leetcode.missingranges;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * project name: algorithm
 *
 * description: Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
 * Output: ["2", "4->49", "51->74", "76->99"]
 *
 * @author magicliang
 *
 *         date: 2025-09-04 18:59
 */
public class MissingRangesProblem {

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        final int n = nums.length;

        // 处理空数组的情况
        if (n == 0) {
            result.add(format(lower, upper));
            return result;
        }

        // 这个问题易错的点在于目标数组的长度让一个点可能要处理很多可能。只有n只为0和非0的时候要互斥地处理
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                // 易错的点：不要把两个条件写在一起，这样第三个分支就不包含 i == 0 的情况
                if (nums[i] > lower) {
                    result.add(format(lower, nums[i] - 1));
                }
            } else {
                // 所有的非0节点都需要过一道过滤器
                if (nums[i] != nums[i - 1] + 1) {
                    result.add(format(nums[i - 1] + 1, nums[i] - 1));
                }
            }

            // 一个 i 有可能即是开头，也是结尾
            if (i == n-1 && nums[i] < upper) {
                result.add(format(nums[i] + 1, upper));
            }
        }
        return result;
    }

    String format(Integer begin, Integer end) {
        if (Objects.equals(begin, end)) {
            return begin.toString();
        }
        return String.format("%d->%d", begin, end);
    }
}
