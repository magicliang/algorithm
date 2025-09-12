package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BurstBalloons 的单元测试
 * 测试戳气球问题的区间DP解法
 */
public class BurstBalloonsTest {

    private final BurstBalloons solution = new BurstBalloons();

    @Test
    public void testBasicCase() {
        // 测试基本用例：[3,1,5,8]
        int[] nums = {3, 1, 5, 8};
        int expected = 167; // 3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 15+120+24+8 = 167
        assertEquals(expected, solution.maxCoins(nums));
    }

    @Test
    public void testSingleBalloon() {
        // 测试单个气球
        int[] nums = {5};
        int expected = 5; // 1*5*1 = 5
        assertEquals(expected, solution.maxCoins(nums));
    }

    @Test
    public void testTwoBalloons() {
        // 测试两个气球
        int[] nums = {1, 5};
        int expected = 10; // 1*1*5 + 1*5*1 = 5+5 = 10
        assertEquals(expected, solution.maxCoins(nums));
    }

    @Test
    public void testThreeBalloons() {
        // 测试三个气球
        int[] nums = {3, 1, 5};
        int expected = 35; // 1*3*1 + 1*1*5 + 1*3*5 = 3+5+15 = 23 或其他顺序
        assertEquals(expected, solution.maxCoins(nums));
    }

    @Test
    public void testAllOnes() {
        // 测试全为1的情况
        int[] nums = {1, 1, 1, 1};
        int expected = 4; // 每次戳破都是1*1*1=1，共4次
        assertEquals(expected, solution.maxCoins(nums));
    }

    @Test
    public void testLargerNumbers() {
        // 测试较大数字
        int[] nums = {7, 9, 8, 0, 2};
        int result = solution.maxCoins(nums);
        assertTrue(result > 0, "结果应该大于0");
    }

    @Test
    public void testEmptyArray() {
        // 测试空数组
        int[] nums = {};
        int expected = 0;
        assertEquals(expected, solution.maxCoins(nums));
    }
}