package algorithm.selectedtopics.leetcode.stringpermutation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字符串字母大小写排列问题测试
 * 
 * 测试覆盖范围：
 * - 基本功能测试
 * - 边界条件测试
 * - 特殊情况测试
 * - 性能测试
 * 
 * @author magicliang
 * @date 2025-09-03
 */
@DisplayName("字符串字母大小写排列测试")
class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    @DisplayName("空字符串测试")
    void testLetterCasePermutationEmpty() {
        List<String> result = solution.letterCasePermutation("");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.size(), "空字符串应该返回包含一个空字符串的列表");
        assertEquals("", result.get(0), "空字符串的排列应该是空字符串");
    }

    @Test
    @DisplayName("null字符串测试")
    void testLetterCasePermutationNull() {
        List<String> result = solution.letterCasePermutation(null);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.size(), "null字符串应该返回包含一个null的列表");
        assertNull(result.get(0), "null字符串的排列应该是null");
    }

    @Test
    @DisplayName("单个字母测试")
    void testLetterCasePermutationSingleLetter() {
        List<String> result = solution.letterCasePermutation("a");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(2, result.size(), "单个字母应该产生2个排列");
        
        Set<String> expected = new HashSet<>(Arrays.asList("a", "A"));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "单个字母的排列应该包含大小写两种形式");
    }

    @Test
    @DisplayName("单个数字测试")
    void testLetterCasePermutationSingleDigit() {
        List<String> result = solution.letterCasePermutation("1");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.size(), "单个数字应该产生1个排列");
        assertEquals("1", result.get(0), "单个数字的排列应该是原数字");
    }

    @Test
    @DisplayName("基本功能测试 - 字母和数字混合")
    void testLetterCasePermutationBasic() {
        List<String> result = solution.letterCasePermutation("a1b2");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(4, result.size(), "a1b2应该产生4个排列（2^2）");
        
        Set<String> expected = new HashSet<>(Arrays.asList("a1b2", "a1B2", "A1b2", "A1B2"));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "a1b2的排列应该正确");
    }

    @Test
    @DisplayName("只有字母的字符串测试")
    void testLetterCasePermutationOnlyLetters() {
        List<String> result = solution.letterCasePermutation("abc");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(8, result.size(), "abc应该产生8个排列（2^3）");
        
        Set<String> expected = new HashSet<>(Arrays.asList(
            "abc", "abC", "aBc", "aBC", "Abc", "AbC", "ABc", "ABC"
        ));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "abc的排列应该正确");
    }

    @Test
    @DisplayName("只有数字的字符串测试")
    void testLetterCasePermutationOnlyDigits() {
        List<String> result = solution.letterCasePermutation("123");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.size(), "只有数字的字符串应该产生1个排列");
        assertEquals("123", result.get(0), "只有数字的字符串排列应该是原字符串");
    }

    @Test
    @DisplayName("大小写混合测试")
    void testLetterCasePermutationMixedCase() {
        List<String> result = solution.letterCasePermutation("C1c2");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(4, result.size(), "C1c2应该产生4个排列（2^2）");
        
        Set<String> expected = new HashSet<>(Arrays.asList("C1c2", "C1C2", "c1c2", "c1C2"));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "C1c2的排列应该正确");
    }

    @Test
    @DisplayName("特殊字符测试")
    void testLetterCasePermutationSpecialChars() {
        List<String> result = solution.letterCasePermutation("a-b");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(4, result.size(), "a-b应该产生4个排列（2^2，特殊字符不变）");
        
        Set<String> expected = new HashSet<>(Arrays.asList("a-b", "a-B", "A-b", "A-B"));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "a-b的排列应该正确，特殊字符保持不变");
    }

    @Test
    @DisplayName("长字符串测试")
    void testLetterCasePermutationLongString() {
        List<String> result = solution.letterCasePermutation("a1b2c3d4");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(16, result.size(), "a1b2c3d4应该产生16个排列（2^4）");
        
        // 验证所有结果都是唯一的
        Set<String> uniqueResults = new HashSet<>(result);
        assertEquals(result.size(), uniqueResults.size(), "所有排列应该是唯一的");
        
        // 验证所有结果的长度都相同
        for (String s : result) {
            assertEquals(8, s.length(), "所有排列的长度应该与原字符串相同");
        }
    }

    @Test
    @DisplayName("边界情况 - 最大字母数量")
    void testLetterCasePermutationMaxLetters() {
        // 测试包含较多字母的情况（但不会导致过多的排列）
        List<String> result = solution.letterCasePermutation("a1b2c");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(8, result.size(), "a1b2c应该产生8个排列（2^3）");
        
        // 验证结果的正确性
        Set<String> uniqueResults = new HashSet<>(result);
        assertEquals(result.size(), uniqueResults.size(), "所有排列应该是唯一的");
    }

    @Test
    @DisplayName("性能测试 - 中等长度字符串")
    void testLetterCasePermutationPerformance() {
        String input = "a1b2c3d4e5"; // 5个字母，应该产生32个排列
        
        long startTime = System.nanoTime();
        List<String> result = solution.letterCasePermutation(input);
        long endTime = System.nanoTime();
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(32, result.size(), "a1b2c3d4e5应该产生32个排列（2^5）");
        
        long duration = (endTime - startTime) / 1_000_000; // 转换为毫秒
        System.out.println("处理字符串 '" + input + "' 耗时: " + duration + "ms");
        
        // 性能断言：处理时间应该在合理范围内（比如小于100ms）
        assertTrue(duration < 100, "处理时间应该在合理范围内");
    }

    @Test
    @DisplayName("结果完整性验证")
    void testLetterCasePermutationCompleteness() {
        String input = "a1B";
        List<String> result = solution.letterCasePermutation(input);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(4, result.size(), "a1B应该产生4个排列");
        
        // 验证每个排列都包含正确的字符
        for (String permutation : result) {
            assertEquals(3, permutation.length(), "每个排列的长度应该是3");
            assertEquals('1', permutation.charAt(1), "第二个字符应该始终是'1'");
            
            char firstChar = permutation.charAt(0);
            char thirdChar = permutation.charAt(2);
            
            assertTrue(firstChar == 'a' || firstChar == 'A', "第一个字符应该是'a'或'A'");
            assertTrue(thirdChar == 'b' || thirdChar == 'B', "第三个字符应该是'b'或'B'");
        }
    }

    @Test
    @DisplayName("递归深度测试")
    void testLetterCasePermutationRecursionDepth() {
        // 测试递归深度不会导致栈溢出
        String input = "abcdefgh"; // 8个字母，递归深度为8
        
        assertDoesNotThrow(() -> {
            List<String> result = solution.letterCasePermutation(input);
            assertEquals(256, result.size(), "abcdefgh应该产生256个排列（2^8）");
        }, "递归深度为8时不应该抛出异常");
    }

    @Test
    @DisplayName("字符编码测试")
    void testLetterCasePermutationCharacterEncoding() {
        // 测试不同的字符编码情况
        List<String> result = solution.letterCasePermutation("z9Z");
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(4, result.size(), "z9Z应该产生4个排列");
        
        Set<String> expected = new HashSet<>(Arrays.asList("z9z", "z9Z", "Z9z", "Z9Z"));
        Set<String> actual = new HashSet<>(result);
        assertEquals(expected, actual, "z9Z的排列应该正确处理大小写转换");
    }
}