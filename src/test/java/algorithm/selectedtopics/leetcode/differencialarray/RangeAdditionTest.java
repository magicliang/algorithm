package algorithm.selectedtopics.leetcode.differencialarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RangeAdditionTest {
    @Test
    public void testGetModifiedArray() {
        RangeAddition rangeAddition = new RangeAddition();

        // 测试用例1：基本区间加法
        int[] result1 = rangeAddition.getModifiedArray(5, new int[][]{{1, 3, 2}, {2, 4, 3}, {0, 2, -2}});
        assertArrayEquals(new int[]{-2, 0, 3, 5, 3}, result1);

        // 测试用例2：空操作
        int[] result2 = rangeAddition.getModifiedArray(5, new int[][]{});
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, result2);

        // 测试用例3：单个区间覆盖整个数组
        int[] result3 = rangeAddition.getModifiedArray(5, new int[][]{{0, 4, 1}});
        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, result3);
    }
}
