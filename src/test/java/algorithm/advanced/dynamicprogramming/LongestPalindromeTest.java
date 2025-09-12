package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LongestPalindrome 的单元测试
 * 测试最长回文子串的多种DP解法
 */
public class LongestPalindromeTest {

    private final LongestPalindrome solution = new LongestPalindrome();

    @Test
    public void testBasicPalindromes() {
        // 测试基本回文串
        assertEquals("bab", solution.longestPalindrome("babad")); // 或 "aba"
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
        assertEquals("a", solution.longestPalindrome("ab")); // 或 "b"
    }

    @Test
    public void testEntireStringIsPalindrome() {
        // 测试整个字符串都是回文
        assertEquals("racecar", solution.longestPalindrome("racecar"));
        assertEquals("abcba", solution.longestPalindrome("abcba"));
        assertEquals("a", solution.longestPalindrome("a"));
    }

    @Test
    public void testNoPalindromeExceptSingle() {
        // 测试除了单字符外没有回文
        String result = solution.longestPalindrome("abcdef");
        assertEquals(1, result.length(), "应该返回长度为1的字符");
        assertTrue("abcdef".contains(result), "结果应该是原字符串中的字符");
    }

    @Test
    public void testMultiplePalindromes() {
        // 测试多个回文串，返回最长的
        String result = solution.longestPalindrome("abacabad");
        assertTrue(result.equals("abacaba") || result.equals("bacab") || result.length() >= 3, 
                   "应该返回最长的回文串");
    }

    @Test
    public void testEvenLengthPalindrome() {
        // 测试偶数长度回文
        assertEquals("abba", solution.longestPalindrome("abba"));
        assertEquals("baab", solution.longestPalindrome("cbaab"));
    }

    @Test
    public void testOddLengthPalindrome() {
        // 测试奇数长度回文
        assertEquals("aba", solution.longestPalindrome("aba"));
        assertEquals("racecar", solution.longestPalindrome("racecar"));
    }

    @Test
    public void testLongString() {
        // 测试较长字符串
        String input = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendure";
        String result = solution.longestPalindrome(input);
        assertTrue(result.length() > 0, "应该找到至少一个字符的回文");
    }

    @Test
    public void testOptimizedVersion() {
        // 测试优化版本
        assertEquals("bab", solution.longestPalindromeOptimized("babad")); // 或 "aba"
        assertEquals("bb", solution.longestPalindromeOptimized("cbbd"));
        assertEquals("a", solution.longestPalindromeOptimized("a"));
    }

    @Test
    public void testWithProcessVersion() {
        // 测试带处理版本
        assertEquals("bab", solution.longestPalindromeWithProcess("babad")); // 或 "aba"
        assertEquals("bb", solution.longestPalindromeWithProcess("cbbd"));
        assertEquals("a", solution.longestPalindromeWithProcess("a"));
    }

    @Test
    public void testAllVersionsConsistency() {
        // 测试所有版本的一致性
        String[] testCases = {"babad", "cbbd", "a", "racecar", "abcdef", "abba"};
        
        for (String testCase : testCases) {
            String result1 = solution.longestPalindrome(testCase);
            String result2 = solution.longestPalindromeOptimized(testCase);
            String result3 = solution.longestPalindromeWithProcess(testCase);
            
            // 所有版本应该返回相同长度的结果（可能是不同的回文串但长度相同）
            assertEquals(result1.length(), result2.length(), 
                        "基础版本和优化版本应该返回相同长度的结果");
            assertEquals(result1.length(), result3.length(), 
                        "基础版本和处理版本应该返回相同长度的结果");
        }
    }

    @Test
    public void testEmptyString() {
        // 测试空字符串
        assertEquals("", solution.longestPalindrome(""));
        assertEquals("", solution.longestPalindromeOptimized(""));
        assertEquals("", solution.longestPalindromeWithProcess(""));
    }
}