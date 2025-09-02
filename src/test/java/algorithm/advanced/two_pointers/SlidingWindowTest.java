package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SlidingWindow的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class SlidingWindowTest {

    private SlidingWindow solution;

    @BeforeEach
    void setUp() {
        solution = new SlidingWindow();
    }

    @Test
    void testLengthOfLongestSubstring() {
        // 基本测试用例
        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"), "abcabcbb的最长无重复子串长度应该是3");
        assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"), "bbbbb的最长无重复子串长度应该是1");
        assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"), "pwwkew的最长无重复子串长度应该是3");
        
        // 边界情况
        assertEquals(0, solution.lengthOfLongestSubstring(""), "空字符串应该返回0");
        assertEquals(0, solution.lengthOfLongestSubstring(null), "null应该返回0");
        assertEquals(1, solution.lengthOfLongestSubstring("a"), "单字符应该返回1");
        
        // 全部不重复
        assertEquals(5, solution.lengthOfLongestSubstring("abcde"), "全部不重复字符应该返回字符串长度");
    }

    @Test
    void testMinWindow() {
        // 基本测试用例
        assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"), "最小覆盖子串测试失败");
        assertEquals("a", solution.minWindow("a", "a"), "单字符覆盖测试失败");
        assertEquals("", solution.minWindow("a", "aa"), "无法覆盖的情况应该返回空字符串");
        
        // 边界情况
        assertEquals("", solution.minWindow("", "a"), "空源字符串应该返回空字符串");
        assertEquals("", solution.minWindow("a", ""), "空目标字符串应该返回空字符串");
        assertEquals("", solution.minWindow(null, "a"), "null源字符串应该返回空字符串");
        assertEquals("", solution.minWindow("a", null), "null目标字符串应该返回空字符串");
        
        // 重复字符
        assertEquals("aa", solution.minWindow("aa", "aa"), "重复字符覆盖测试失败");
    }

    @Test
    void testMaxSlidingWindow() {
        // 基本测试用例
        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] expected1 = {3, 3, 5, 5, 6, 7};
        assertArrayEquals(expected1, solution.maxSlidingWindow(nums1, 3), "滑动窗口最大值测试失败");
        
        // 单元素窗口
        int[] nums2 = {1, 2, 3, 4, 5};
        int[] expected2 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected2, solution.maxSlidingWindow(nums2, 1), "单元素窗口测试失败");
        
        // 窗口大小等于数组长度
        int[] nums3 = {1, 3, 2};
        int[] expected3 = {3};
        assertArrayEquals(expected3, solution.maxSlidingWindow(nums3, 3), "窗口等于数组长度测试失败");
        
        // 边界情况
        assertArrayEquals(new int[0], solution.maxSlidingWindow(null, 3), "null数组应该返回空数组");
        assertArrayEquals(new int[0], solution.maxSlidingWindow(new int[0], 3), "空数组应该返回空数组");
        assertArrayEquals(new int[0], solution.maxSlidingWindow(new int[]{1, 2, 3}, 0), "窗口大小为0应该返回空数组");
    }

    @Test
    void testMinSubArrayLen() {
        // 基本测试用例
        assertEquals(2, solution.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}), "最小子数组长度测试失败");
        assertEquals(1, solution.minSubArrayLen(4, new int[]{1, 4, 4}), "单元素满足条件测试失败");
        assertEquals(0, solution.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}), "无法满足条件应该返回0");
        
        // 边界情况
        assertEquals(0, solution.minSubArrayLen(5, null), "null数组应该返回0");
        assertEquals(0, solution.minSubArrayLen(5, new int[0]), "空数组应该返回0");
        
        // 整个数组的和刚好等于target
        assertEquals(3, solution.minSubArrayLen(6, new int[]{1, 2, 3}), "整个数组和等于target测试失败");
        
        // 单个元素就满足条件
        assertEquals(1, solution.minSubArrayLen(3, new int[]{5, 1, 2}), "单个元素满足条件测试失败");
        
        // 所有元素都需要
        assertEquals(4, solution.minSubArrayLen(10, new int[]{1, 2, 3, 4}), "需要所有元素测试失败");
    }

    @Test
    void testLengthOfLongestSubstringSpecialCases() {
        // 特殊字符
        assertEquals(3, solution.lengthOfLongestSubstring("!@#!@#"), "特殊字符测试失败");
        
        // 数字字符
        assertEquals(3, solution.lengthOfLongestSubstring("123123"), "数字字符测试失败");
        
        // 混合字符
        assertEquals(6, solution.lengthOfLongestSubstring("abc123"), "混合字符测试失败");
    }

    @Test
    void testMinWindowComplexCases() {
        // 目标字符串有重复字符
        assertEquals("cwae", solution.minWindow("ADOBECODEBANC", "AABC"), "目标有重复字符测试失败");
        
        // 源字符串中目标字符分散
        assertEquals("dcodeba", solution.minWindow("ADOBECODEBANC", "ABCD"), "分散字符测试失败");
    }

    @Test
    void testMaxSlidingWindowEdgeCases() {
        // 递增数组
        int[] increasing = {1, 2, 3, 4, 5};
        int[] expectedInc = {3, 4, 5};
        assertArrayEquals(expectedInc, solution.maxSlidingWindow(increasing, 3), "递增数组测试失败");
        
        // 递减数组
        int[] decreasing = {5, 4, 3, 2, 1};
        int[] expectedDec = {5, 4, 3};
        assertArrayEquals(expectedDec, solution.maxSlidingWindow(decreasing, 3), "递减数组测试失败");
        
        // 相同元素
        int[] same = {2, 2, 2, 2, 2};
        int[] expectedSame = {2, 2, 2};
        assertArrayEquals(expectedSame, solution.maxSlidingWindow(same, 3), "相同元素测试失败");
    }
}