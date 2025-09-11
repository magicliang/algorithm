package algorithm.selectedtopics.leetcode.substring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 分治法字符串算法测试类
 * 
 * @author magicliang
 */
public class DivideAndConquerSubstringTest {
    
    private DivideAndConquerSubstring solution;
    
    @BeforeEach
    void setUp() {
        solution = new DivideAndConquerSubstring();
    }
    
    @Test
    @DisplayName("测试基本用例 - aaabb, k=3")
    void testBasicCase1() {
        String s = "aaabb";
        int k = 3;
        int expected = 3; // "aaa"
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试基本用例 - ababbc, k=2")
    void testBasicCase2() {
        String s = "ababbc";
        int k = 2;
        int expected = 5; // "ababb"
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试边界情况 - 空字符串")
    void testEmptyString() {
        String s = "";
        int k = 1;
        int expected = 0;
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试边界情况 - null字符串")
    void testNullString() {
        String s = null;
        int k = 1;
        int expected = 0;
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试边界情况 - 字符串长度小于k")
    void testStringLengthLessThanK() {
        String s = "abc";
        int k = 5;
        int expected = 0;
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试单字符重复 - aaaa, k=2")
    void testSingleCharacterRepeated() {
        String s = "aaaa";
        int k = 2;
        int expected = 4; // 整个字符串
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试所有字符频次都不足 - abcdef, k=2")
    void testAllCharactersInsufficientFrequency() {
        String s = "abcdef";
        int k = 2;
        int expected = 0;
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试复杂情况 - aabbccdd, k=2")
    void testComplexCase() {
        String s = "aabbccdd";
        int k = 2;
        int expected = 8; // 整个字符串，每个字符都出现2次
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试混合情况 - aaabbbcccddde, k=3")
    void testMixedCase() {
        String s = "aaabbbcccddde";
        int k = 3;
        int expected = 12; // "aaabbbcccdd"，e只出现1次会被分割掉，剩下的部分长度为12
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试k=1的情况 - 任何字符串都满足")
    void testKEqualsOne() {
        String s = "abcdefg";
        int k = 1;
        int expected = 7; // 整个字符串
        
        assertEquals(expected, solution.longestSubstringDivideConquer(s, k));
        assertEquals(expected, solution.longestSubstringDivideConquerV2(s, k));
    }
    
    @Test
    @DisplayName("测试长字符串性能")
    void testLongStringPerformance() {
        // 构造一个较长的字符串：1000个a + 1个b + 1000个c
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }
        sb.append('b'); // 这个b会成为分割点
        for (int i = 0; i < 1000; i++) {
            sb.append('c');
        }
        
        String s = sb.toString();
        int k = 500;
        int expected = 1000; // 左边1000个a或右边1000个c
        
        long startTime = System.currentTimeMillis();
        int result1 = solution.longestSubstringDivideConquer(s, k);
        long endTime = System.currentTimeMillis();
        
        assertEquals(expected, result1);
        System.out.println("分治法V1处理长度" + s.length() + "的字符串耗时: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        int result2 = solution.longestSubstringDivideConquerV2(s, k);
        endTime = System.currentTimeMillis();
        
        assertEquals(expected, result2);
        System.out.println("分治法V2处理长度" + s.length() + "的字符串耗时: " + (endTime - startTime) + "ms");
    }
    
    @Test
    @DisplayName("测试分治过程跟踪功能")
    void testTraceFunction() {
        String s = "ababbc";
        int k = 2;
        int expected = 5;
        
        // 这个测试主要是验证跟踪功能不会影响结果的正确性
        int result = solution.longestSubstringWithTrace(s, k);
        assertEquals(expected, result);
    }
    
    @Test
    @DisplayName("测试两种实现的一致性")
    void testConsistencyBetweenImplementations() {
        String[] testCases = {
            "aaabb", "ababbc", "aabbccdd", "abcdef", 
            "aaabbbcccddde", "a", "ab", "aaa"
        };
        int[] kValues = {1, 2, 3, 4, 5};
        
        for (String s : testCases) {
            for (int k : kValues) {
                int result1 = solution.longestSubstringDivideConquer(s, k);
                int result2 = solution.longestSubstringDivideConquerV2(s, k);
                
                assertEquals(result1, result2, 
                    String.format("两种实现结果不一致: s=\"%s\", k=%d, V1=%d, V2=%d", 
                    s, k, result1, result2));
            }
        }
    }
    
    @Test
    @DisplayName("测试极端情况 - 单字符字符串")
    void testSingleCharacter() {
        String s = "a";
        
        assertEquals(1, solution.longestSubstringDivideConquer(s, 1));
        assertEquals(0, solution.longestSubstringDivideConquer(s, 2));
        
        assertEquals(1, solution.longestSubstringDivideConquerV2(s, 1));
        assertEquals(0, solution.longestSubstringDivideConquerV2(s, 2));
    }
    
    @Test
    @DisplayName("测试分割边界情况")
    void testSplitBoundaryCase() {
        // 测试问题字符在开头、中间、结尾的情况
        String s1 = "baaaa"; // 问题字符在开头
        String s2 = "aabaa"; // 问题字符在中间  
        String s3 = "aaaab"; // 问题字符在结尾
        int k = 3;
        
        assertEquals(4, solution.longestSubstringDivideConquer(s1, k)); // "aaaa"
        assertEquals(0, solution.longestSubstringDivideConquer(s2, k)); // 分割后每部分都不足k长度
        assertEquals(4, solution.longestSubstringDivideConquer(s3, k)); // "aaaa"
        
        assertEquals(4, solution.longestSubstringDivideConquerV2(s1, k));
        assertEquals(0, solution.longestSubstringDivideConquerV2(s2, k));
        assertEquals(4, solution.longestSubstringDivideConquerV2(s3, k));
    }
}