package algorithm.selectedtopics.leetcode.stringpermutation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 覆盖 letterCasePermutation 的必要测试用例：
 * - 混合字母数字
 * - 仅数字
 * - 全字母（验证 2^n 规模）
 * - 单字符（字母/数字）
 * - 空串
 * - null 入参（按当前实现语义：返回仅含 null 的列表）
 */
public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    void testMixedLettersDigits_a1b2() {
        List<String> result = solution.letterCasePermutation("a1b2");
        Set<String> expected = new HashSet<>(Arrays.asList("A1B2", "A1b2", "a1B2", "a1b2"));
        assertEquals(4, result.size(), "结果数量应为 4");
        assertEquals(expected, new HashSet<>(result), "结果集合应匹配预期集合（忽略顺序）");
    }

    @Test
    void testLettersOnly_abc() {
        List<String> result = solution.letterCasePermutation("abc");
        Set<String> expected = new HashSet<>(Arrays.asList(
                "ABC","ABc","AbC","Abc","aBC","aBc","abC","abc"
        ));
        assertEquals(8, result.size(), "3 个字母应产生 2^3=8 种排列");
        assertEquals(expected, new HashSet<>(result));
    }

    @Test
    void testDigitsOnly_12345() {
        List<String> result = solution.letterCasePermutation("12345");
        assertEquals(1, result.size(), "纯数字应只返回自身");
        assertEquals("12345", result.get(0));
    }

    @Test
    void testSingleLetter_A() {
        List<String> result = solution.letterCasePermutation("A");
        Set<String> expected = new HashSet<>(Arrays.asList("A", "a"));
        assertEquals(2, result.size());
        assertEquals(expected, new HashSet<>(result));
    }

    @Test
    void testSingleDigit_7() {
        List<String> result = solution.letterCasePermutation("7");
        assertEquals(1, result.size());
        assertEquals("7", result.get(0));
    }

    @Test
    void testEmptyString() {
        List<String> result = solution.letterCasePermutation("");
        assertEquals(1, result.size(), "空串按实现应返回仅包含空串的列表");
        assertEquals("", result.get(0));
    }

    @Test
    void testNullString() {
        List<String> result = solution.letterCasePermutation(null);
        // 按当前实现：null 或 空串 都走同一分支，返回 Collections.singletonList(str)
        assertEquals(1, result.size(), "null 入参按实现返回仅包含 null 的列表");
        assertNull(result.get(0));
    }
}