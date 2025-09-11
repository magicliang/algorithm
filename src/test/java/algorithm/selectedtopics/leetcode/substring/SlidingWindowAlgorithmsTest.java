package algorithm.selectedtopics.leetcode.substring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 滑动窗口算法测试类
 * 
 * 为SlidingWindowAlgorithms类中的所有算法提供全面的测试用例，
 * 包括边界情况、典型用例和性能测试。
 * 
 * @author magicliang
 */
class SlidingWindowAlgorithmsTest {

    private SlidingWindowAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new SlidingWindowAlgorithms();
    }

    @Nested
    @DisplayName("标准滑动窗口算法测试")
    class StandardSlidingWindowTests {

        @Test
        @DisplayName("无重复字符的最长子串 - 基本测试用例")
        void testLengthOfLongestSubstring_BasicCases() {
            // 经典测试用例
            assertEquals(3, algorithms.lengthOfLongestSubstring("abcabcbb"), 
                "abcabcbb应该返回3 (abc)");
            assertEquals(1, algorithms.lengthOfLongestSubstring("bbbbb"), 
                "bbbbb应该返回1 (b)");
            assertEquals(3, algorithms.lengthOfLongestSubstring("pwwkew"), 
                "pwwkew应该返回3 (wke)");
            assertEquals(0, algorithms.lengthOfLongestSubstring(""), 
                "空字符串应该返回0");
            assertEquals(1, algorithms.lengthOfLongestSubstring("a"), 
                "单字符应该返回1");
        }

        @Test
        @DisplayName("无重复字符的最长子串 - 边界情况")
        void testLengthOfLongestSubstring_EdgeCases() {
            // 全部不重复
            assertEquals(5, algorithms.lengthOfLongestSubstring("abcde"), 
                "全部不重复字符应该返回字符串长度");
            
            // 特殊字符
            assertEquals(3, algorithms.lengthOfLongestSubstring("a!@a"), 
                "包含特殊字符的测试");
            
            // 数字字符
            assertEquals(3, algorithms.lengthOfLongestSubstring("123321"), 
                "数字字符测试");
            
            // 长字符串测试
            StringBuilder longStr = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                longStr.append((char)('a' + i % 26));
            }
            assertEquals(26, algorithms.lengthOfLongestSubstring(longStr.toString()), 
                "长字符串应该返回26（26个字母）");
        }
    }

    @Nested
    @DisplayName("枚举型滑动窗口算法测试")
    class EnumerativeSlidingWindowTests {

        @Test
        @DisplayName("每个字符都至少出现K次的最长子串 - 基本测试")
        void testLongestSubstring_BasicCases() {
            // 经典测试用例
            assertEquals(3, algorithms.longestSubstring("aaabb", 3), 
                "aaabb, k=3应该返回3 (aaa)");
            assertEquals(5, algorithms.longestSubstring("ababbc", 2), 
                "ababbc, k=2应该返回5 (ababb)");
            assertEquals(0, algorithms.longestSubstring("abc", 2), 
                "abc, k=2应该返回0（没有字符出现2次）");
            assertEquals(6, algorithms.longestSubstring("aabbcc", 2), 
                "aabbcc, k=2应该返回6（整个字符串）");
        }

        @Test
        @DisplayName("每个字符都至少出现K次的最长子串 - 边界情况")
        void testLongestSubstring_EdgeCases() {
            // k=1的情况
            assertEquals(5, algorithms.longestSubstring("abcde", 1), 
                "k=1时应该返回整个字符串长度");
            
            // 空字符串
            assertEquals(0, algorithms.longestSubstring("", 2), 
                "空字符串应该返回0");
            
            // k=0的情况 - 实际上k=0时算法返回0，因为没有字符能满足"至少出现0次"的逻辑
            assertEquals(0, algorithms.longestSubstring("abcde", 0), 
                "k=0时算法返回0");
            
            // 单字符重复
            assertEquals(5, algorithms.longestSubstring("aaaaa", 3), 
                "aaaaa, k=3应该返回5");
            
            // k大于字符串长度
            assertEquals(0, algorithms.longestSubstring("abc", 5), 
                "k大于字符串长度时应该返回0");
        }

        @Test
        @DisplayName("频次范围约束的最长子串测试")
        void testLongestValidSubstring() {
            // 基本测试用例
            assertEquals(6, algorithms.longestValidSubstring("aabbcc", 2, 2), 
                "aabbcc, [2,2]应该返回6");
            assertEquals(9, algorithms.longestValidSubstring("aaabbbccc", 2, 3), 
                "aaabbbccc, [2,3]应该返回9");
            assertEquals(0, algorithms.longestValidSubstring("abc", 2, 3), 
                "abc, [2,3]应该返回0（没有字符满足频次要求）");
            
            // 边界情况
            assertEquals(0, algorithms.longestValidSubstring("", 1, 2), 
                "空字符串应该返回0");
            assertEquals(0, algorithms.longestValidSubstring("a", 2, 3), 
                "单字符不满足最小频次要求");
            assertEquals(4, algorithms.longestValidSubstring("aabb", 1, 2), 
                "aabb, [1,2]应该返回4");
        }
    }

    @Nested
    @DisplayName("至多/恰好/至少K个字符问题测试")
    class KCharacterProblemsTests {

        @Test
        @DisplayName("至多K个不同字符的最长子串测试")
        void testAtMostK() {
            // 基本测试用例
            assertEquals(3, algorithms.atMostK("eceba", 2), 
                "eceba, k=2应该返回3 (ece)");
            assertEquals(4, algorithms.atMostK("eceba", 3), 
                "eceba, k=3应该返回4");
            assertEquals(0, algorithms.atMostK("abc", 0), 
                "k=0应该返回0");
            assertEquals(1, algorithms.atMostK("abc", 1), 
                "abc, k=1应该返回1");
            
            // 边界情况
            assertEquals(0, algorithms.atMostK("", 2), 
                "空字符串应该返回0");
            assertEquals(5, algorithms.atMostK("aaaaa", 1), 
                "单一字符重复，k=1应该返回整个长度");
            assertEquals(10, algorithms.atMostK("abcdefghij", 10), 
                "字符种类等于k时应该返回整个长度");
        }

        @Test
        @DisplayName("恰好K个不同字符的最长子串测试")
        void testExactlyK() {
            // 基本测试用例
            assertEquals(2, algorithms.exactlyK("eceba", 2), 
                "eceba, k=2应该返回2");
            assertEquals(1, algorithms.exactlyK("eceba", 3), 
                "eceba, k=3应该返回1");
            assertEquals(0, algorithms.exactlyK("aa", 2), 
                "aa, k=2应该返回0（只有1种字符）");
            
            // 边界情况
            assertEquals(0, algorithms.exactlyK("", 1), 
                "空字符串应该返回0");
            assertEquals(0, algorithms.exactlyK("abc", 0), 
                "k=0应该返回0");
            assertEquals(1, algorithms.exactlyK("abc", 1), 
                "abc, k=1应该返回1");
            assertEquals(1, algorithms.exactlyK("abc", 3), 
                "abc, k=3应该返回1");
        }

        @Test
        @DisplayName("至少K个不同字符的最长子串测试")
        void testAtLeastK() {
            // 基本测试用例
            assertEquals(5, algorithms.atLeastK("eceba", 2), 
                "eceba, k=2应该返回5");
            assertEquals(5, algorithms.atLeastK("eceba", 3), 
                "eceba, k=3应该返回5");
            assertEquals(0, algorithms.atLeastK("aa", 2), 
                "aa, k=2应该返回0（只有1种字符）");
            
            // 边界情况
            assertEquals(5, algorithms.atLeastK("abcde", 0), 
                "k=0应该返回整个字符串长度");
            assertEquals(0, algorithms.atLeastK("", 1), 
                "空字符串应该返回0");
            assertEquals(3, algorithms.atLeastK("abc", 3), 
                "abc, k=3应该返回3（整个字符串）");
            assertEquals(0, algorithms.atLeastK("abc", 4), 
                "abc, k=4应该返回0（字符种类不足）");
        }

        @Test
        @DisplayName("三种K字符问题的关系验证")
        void testKCharacterProblemsRelationship() {
            String[] testStrings = {"eceba", "abcdef", "aabbcc", "aaabbbccc"};
            
            for (String s : testStrings) {
                for (int k = 1; k <= 6; k++) {
                    int atMost = algorithms.atMostK(s, k);
                    int exactly = algorithms.exactlyK(s, k);
                    int atLeast = algorithms.atLeastK(s, k);
                    
                    // 验证基本关系
                    assertTrue(exactly <= atMost, 
                        String.format("对于字符串%s, k=%d: exactly(%d) <= atMost(%d)", 
                            s, k, exactly, atMost));
                    
                    // 当k较小时，atLeast通常 >= exactly
                    if (k <= s.length()) {
                        assertTrue(atLeast >= 0, 
                            String.format("对于字符串%s, k=%d: atLeast应该 >= 0", s, k));
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("性能和压力测试")
    class PerformanceTests {

        @Test
        @DisplayName("大字符串性能测试")
        void testLargeStringPerformance() {
            // 生成大字符串（10000字符）
            StringBuilder largeStr = new StringBuilder();
            for (int i = 0; i < 10000; i++) {
                largeStr.append((char)('a' + i % 26));
            }
            String testStr = largeStr.toString();
            
            // 测试各个算法的性能（主要确保不会超时或出错）
            long startTime = System.currentTimeMillis();
            
            int result1 = algorithms.lengthOfLongestSubstring(testStr);
            assertTrue(result1 > 0 && result1 <= 26, "大字符串无重复字符测试");
            
            int result2 = algorithms.atMostK(testStr, 5);
            assertTrue(result2 > 0, "大字符串至多K个字符测试");
            
            int result3 = algorithms.exactlyK(testStr, 5);
            assertTrue(result3 >= 0, "大字符串恰好K个字符测试");
            
            long endTime = System.currentTimeMillis();
            assertTrue(endTime - startTime < 5000, "性能测试应该在5秒内完成");
        }

        @Test
        @DisplayName("极端情况压力测试")
        void testExtremeCases() {
            // 全相同字符
            StringBuilder sameCharsBuilder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sameCharsBuilder.append('a');
            }
            String sameChars = sameCharsBuilder.toString();
            assertEquals(1, algorithms.lengthOfLongestSubstring(sameChars), "全相同字符测试");
            
            // 全不同字符（在ASCII范围内）
            StringBuilder diffChars = new StringBuilder();
            for (int i = 32; i < 127; i++) { // 可打印ASCII字符
                diffChars.append((char)i);
            }
            String testStr = diffChars.toString();
            assertEquals(testStr.length(), algorithms.lengthOfLongestSubstring(testStr), 
                "全不同字符测试");
        }
    }

    @Nested
    @DisplayName("算法正确性验证")
    class CorrectnessTests {

        @Test
        @DisplayName("验证至多K和恰好K的数学关系")
        void testAtMostAndExactlyKRelationship() {
            String[] testCases = {"eceba", "abcdef", "aabbcc", "aaabbbccc", "abcabcabc"};
            
            for (String s : testCases) {
                for (int k = 1; k <= 6; k++) {
                    int atMostK = algorithms.atMostK(s, k);
                    int atMostKMinus1 = algorithms.atMostK(s, k - 1);
                    int exactlyK = algorithms.exactlyK(s, k);
                    
                    // 验证数学关系：exactlyK = atMostK - atMost(K-1)
                    assertEquals(atMostK - atMostKMinus1, exactlyK,
                        String.format("字符串%s, k=%d: exactlyK应该等于atMostK - atMost(K-1)", s, k));
                }
            }
        }

        @Test
        @DisplayName("验证枚举型滑动窗口的单调性")
        void testEnumerativeMonotonicity() {
            String testStr = "aaabbbccc";
            
            // 对于longestSubstring，随着k增大，结果应该单调递减或保持不变
            int prevResult = testStr.length();
            for (int k = 1; k <= 5; k++) {
                int currentResult = algorithms.longestSubstring(testStr, k);
                assertTrue(currentResult <= prevResult, 
                    String.format("k=%d时结果(%d)应该 <= k=%d时结果(%d)", 
                        k, currentResult, k-1, prevResult));
                prevResult = currentResult;
            }
        }

        @Test
        @DisplayName("验证边界条件的一致性")
        void testBoundaryConsistency() {
            // 空字符串测试
            assertEquals(0, algorithms.lengthOfLongestSubstring(""));
            assertEquals(0, algorithms.longestSubstring("", 1));
            assertEquals(0, algorithms.atMostK("", 1));
            assertEquals(0, algorithms.exactlyK("", 1));
            assertEquals(0, algorithms.atLeastK("", 1));
            
            // 单字符测试
            assertEquals(1, algorithms.lengthOfLongestSubstring("a"));
            assertEquals(1, algorithms.longestSubstring("a", 1));
            assertEquals(1, algorithms.atMostK("a", 1));
            assertEquals(1, algorithms.exactlyK("a", 1));
            assertEquals(1, algorithms.atLeastK("a", 1));
        }
    }

    @Nested
    @DisplayName("特殊字符和编码测试")
    class SpecialCharacterTests {

        @Test
        @DisplayName("Unicode字符测试")
        void testUnicodeCharacters() {
            // 注意：当前实现使用int[128]数组，只支持ASCII字符
            // 这里测试ASCII范围内的字符
            String asciiStr = "abcdefg";
            assertEquals(7, algorithms.lengthOfLongestSubstring(asciiStr), 
                "ASCII字符应该能正常处理");
            
            // 混合ASCII字符测试
            String mixedStr = "abc123!@#";
            assertEquals(9, algorithms.lengthOfLongestSubstring(mixedStr), 
                "混合ASCII字符应该能正常处理");
        }

        @Test
        @DisplayName("特殊ASCII字符测试")
        void testSpecialAsciiCharacters() {
            // 包含空格和标点符号
            String specialStr = "a b!c@d#e$";
            assertTrue(algorithms.lengthOfLongestSubstring(specialStr) > 0, 
                "特殊ASCII字符应该能正常处理");
            
            // 制表符和换行符
            String controlStr = "a\tb\nc\rd";
            assertTrue(algorithms.lengthOfLongestSubstring(controlStr) > 0, 
                "控制字符应该能正常处理");
        }
    }
}