package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LongestPalindromeSimple 的单元测试
 * 测试简化版回文DP解法（没有枚举分割点的版本）
 */
public class LongestPalindromeSimpleTest {

    private final LongestPalindromeSimple solution = new LongestPalindromeSimple();

    @Test
    public void testBasicPalindromes() {
        // 测试基本回文串
        String result1 = solution.longestPalindrome("babad");
        assertTrue(result1.equals("bab") || result1.equals("aba"), 
                   "应该返回 'bab' 或 'aba'");
        
        assertEquals("bb", solution.longestPalindrome("cbbd"));
    }

    @Test
    public void testSingleCharacter() {
        // 测试单字符
        assertEquals("a", solution.longestPalindrome("a"));
        assertEquals("z", solution.longestPalindrome("z"));
    }

    @Test
    public void testTwoCharacters() {
        // 测试两字符
        assertEquals("aa", solution.longestPalindrome("aa"));
        String result = solution.longestPalindrome("ab");
        assertTrue(result.equals("a") || result.equals("b"), 
                   "应该返回 'a' 或 'b'");
    }

    @Test
    public void testEntireStringIsPalindrome() {
        // 测试整个字符串都是回文
        assertEquals("racecar", solution.longestPalindrome("racecar"));
        assertEquals("abcba", solution.longestPalindrome("abcba"));
        assertEquals("madam", solution.longestPalindrome("madam"));
    }

    @Test
    public void testNoPalindromeExceptSingle() {
        // 测试除了单字符外没有回文
        String result = solution.longestPalindrome("abcdef");
        assertEquals(1, result.length(), "应该返回长度为1的字符");
        assertTrue("abcdef".contains(result), "结果应该是原字符串中的字符");
    }

    @Test
    public void testEvenLengthPalindrome() {
        // 测试偶数长度回文
        assertEquals("abba", solution.longestPalindrome("abba"));
        assertEquals("baab", solution.longestPalindrome("cbaab"));
        assertEquals("aa", solution.longestPalindrome("aa"));
    }

    @Test
    public void testOddLengthPalindrome() {
        // 测试奇数长度回文
        assertEquals("aba", solution.longestPalindrome("aba"));
        assertEquals("racecar", solution.longestPalindrome("racecar"));
        assertEquals("level", solution.longestPalindrome("level"));
    }

    @Test
    public void testMultiplePalindromes() {
        // 测试多个回文串，应该返回最长的
        String result = solution.longestPalindrome("abacabad");
        assertTrue(result.length() >= 3, "应该找到长度至少为3的回文串");
        
        // 验证返回的确实是回文串
        String reversed = new StringBuilder(result).reverse().toString();
        assertEquals(result, reversed, "返回的字符串应该是回文串");
    }

    @Test
    public void testLongString() {
        // 测试较长字符串
        String input = "forgeeksskeegfor";
        String result = solution.longestPalindrome(input);
        assertTrue(result.length() >= 10, "应该找到较长的回文串");
        
        // 验证是回文串
        String reversed = new StringBuilder(result).reverse().toString();
        assertEquals(result, reversed, "返回的字符串应该是回文串");
    }

    @Test
    public void testRepeatingCharacters() {
        // 测试重复字符
        assertEquals("aaaa", solution.longestPalindrome("aaaa"));
        assertEquals("bbbb", solution.longestPalindrome("abbbb"));
        
        String result = solution.longestPalindrome("aabbaa");
        assertEquals("aabbaa", result); // 整个字符串是回文
    }

    @Test
    public void testMixedCase() {
        // 测试大小写混合（区分大小写）
        String result = solution.longestPalindrome("Aa");
        assertEquals(1, result.length(), "大小写不同，应该返回单字符");
        
        assertEquals("bAaAb", solution.longestPalindrome("bAaAb"));
    }

    @Test
    public void testEmptyString() {
        // 测试空字符串
        assertEquals("", solution.longestPalindrome(""));
    }

    @Test
    public void testPalindromeValidation() {
        // 验证所有返回的结果都是有效的回文串
        String[] testCases = {
            "babad", "cbbd", "racecar", "abcdef", "abba", 
            "aba", "aa", "a", "abacabad", "forgeeksskeegfor"
        };
        
        for (String testCase : testCases) {
            if (testCase.isEmpty()) continue;
            
            String result = solution.longestPalindrome(testCase);
            assertFalse(result.isEmpty(), "结果不应该为空");
            
            // 验证是回文串
            String reversed = new StringBuilder(result).reverse().toString();
            assertEquals(result, reversed, 
                        String.format("对于输入 '%s'，返回的 '%s' 应该是回文串", testCase, result));
            
            // 验证是原字符串的子串
            assertTrue(testCase.contains(result), 
                      String.format("返回的 '%s' 应该是原字符串 '%s' 的子串", result, testCase));
        }
    }
}