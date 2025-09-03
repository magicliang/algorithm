package algorithm.selectedtopics.leetcode.repeatedsubstring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重复子串模式问题的JUnit测试用例
 * 
 * 测试覆盖：
 * 1. 基本功能测试 - 各种重复和非重复模式
 * 2. 边界条件测试 - 单字符、空字符串等
 * 3. 算法一致性测试 - 验证三种算法结果一致
 * 4. 性能测试 - 大数据量下的性能表现
 * 5. KMP算法特性测试 - next数组构建的正确性
 */
@DisplayName("重复子串模式问题测试")
class RepeatedSubstringPatternTest {
    
    private RepeatedSubstringPattern solution;
    
    @BeforeEach
    void setUp() {
        solution = new RepeatedSubstringPattern();
    }
    
    /**
     * 基本功能测试 - 使用参数化测试
     */
    @ParameterizedTest
    @DisplayName("基本重复模式测试")
    @CsvSource({
        "abab, true, ab重复2次",
        "aba, false, 无法重复构成", 
        "abcabcabcabc, true, abc重复4次",
        "a, false, 单字符无法重复",
        "aa, true, a重复2次",
        "aaa, true, a重复3次",
        "abcabc, true, abc重复2次",
        "abcabcabc, true, abc重复3次",
        "abcdefg, false, 无重复模式",
        "aabaaba, false, 看似重复但实际不是",
        "abababab, true, ab重复4次",
        "xyxyxyxy, true, xy重复4次"
    })
    void testBasicRepeatedPatterns(String input, boolean expected, String description) {
        boolean actual = solution.repeatedSubstringPattern(input);
        assertEquals(expected, actual, 
            String.format("测试用例: %s (%s)", input, description));
    }
    
    /**
     * 边界条件测试
     */
    @Test
    @DisplayName("边界条件测试")
    void testBoundaryConditions() {
        // 最小重复字符串
        assertTrue(solution.repeatedSubstringPattern("aa"), "最小重复字符串");
        
        // 较长的重复字符串
        assertTrue(solution.repeatedSubstringPattern("abcabcabcabcabc"), "长重复字符串");
        
        // 单字符重复
        assertTrue(solution.repeatedSubstringPattern("aaaa"), "单字符重复多次");
        
        // 复杂重复模式
        assertTrue(solution.repeatedSubstringPattern("abcdefabcdef"), "复杂模式重复");
    }
    
    /**
     * 算法一致性测试 - 验证三种算法结果一致
     */
    @ParameterizedTest
    @DisplayName("算法一致性测试")
    @ValueSource(strings = {
        "abab", "aba", "abcabcabcabc", "a", "aa", "aaa", 
        "abcabc", "abcabcabc", "abcdefg", "aabaaba",
        "abababab", "xyxyxyxy", "abcdefabcdef"
    })
    void testAlgorithmConsistency(String input) {
        boolean kmpResult = solution.repeatedSubstringPattern(input);
        boolean bruteResult = solution.repeatedSubstringPatternBruteForce(input);
        boolean concatResult = solution.repeatedSubstringPatternConcat(input);
        
        assertEquals(kmpResult, bruteResult, 
            String.format("KMP和暴力算法结果不一致: %s", input));
        assertEquals(bruteResult, concatResult, 
            String.format("暴力和拼接算法结果不一致: %s", input));
        assertEquals(kmpResult, concatResult, 
            String.format("KMP和拼接算法结果不一致: %s", input));
    }
    
    /**
     * KMP算法特性测试 - 验证next数组构建的正确性
     */
    @Test
    @DisplayName("KMP next数组构建测试")
    void testKMPNextArrayConstruction() {
        // 测试"abcabc"的next数组
        RepeatedSubstringPattern testSolution = new RepeatedSubstringPattern();
        
        // 通过反射或创建测试方法来验证next数组
        // 这里我们通过间接方式验证：检查重复模式识别的正确性
        
        // "abcabc" -> next应该是[0,0,0,1,2,3]
        // 最长相等前后缀长度为3，重复单元长度为6-3=3
        assertTrue(solution.repeatedSubstringPattern("abcabc"));
        
        // "abab" -> next应该是[0,0,1,2] 
        // 最长相等前后缀长度为2，重复单元长度为4-2=2
        assertTrue(solution.repeatedSubstringPattern("abab"));
        
        // "aba" -> next应该是[0,0,1]
        // 最长相等前后缀长度为1，重复单元长度为3-1=2，但3%2!=0
        assertFalse(solution.repeatedSubstringPattern("aba"));
    }
    
    /**
     * 特殊模式测试
     */
    @Test
    @DisplayName("特殊模式测试")
    void testSpecialPatterns() {
        // 回文但非重复
        assertFalse(solution.repeatedSubstringPattern("abcba"), "回文但非重复");
        
        // 部分重复但整体非重复
        assertFalse(solution.repeatedSubstringPattern("abcabcab"), "部分重复但整体非重复");
        
        // 嵌套重复模式
        assertTrue(solution.repeatedSubstringPattern("abababab"), "嵌套重复模式");
        
        // 长度为质数的字符串（除了全相同字符外，不可能重复）
        assertFalse(solution.repeatedSubstringPattern("abcdefg"), "质数长度非重复");
        assertTrue(solution.repeatedSubstringPattern("aaaaaaa"), "质数长度但全相同");
    }
    
    /**
     * 性能测试 - 大数据量
     */
    @Test
    @DisplayName("性能测试")
    void testPerformance() {
        // 创建大型重复字符串
        StringBuilder sb = new StringBuilder();
        String pattern = "abcdef";
        int repeatCount = 1000; // 重复1000次，总长度6000
        
        for (int i = 0; i < repeatCount; i++) {
            sb.append(pattern);
        }
        String largeRepeatedString = sb.toString();
        
        // 测试KMP算法性能
        long startTime = System.nanoTime();
        boolean result = solution.repeatedSubstringPattern(largeRepeatedString);
        long kmpTime = System.nanoTime() - startTime;
        
        assertTrue(result, "大型重复字符串应该返回true");
        
        // 验证性能合理（应该在毫秒级别完成）
        assertTrue(kmpTime < 10_000_000, // 10ms
            String.format("KMP算法性能测试失败，耗时: %d ns", kmpTime));
        
        System.out.printf("性能测试 - 字符串长度: %d, KMP耗时: %.2f ms%n", 
            largeRepeatedString.length(), kmpTime / 1_000_000.0);
    }
    
    /**
     * 数学性质验证测试
     */
    @Test
    @DisplayName("数学性质验证")
    void testMathematicalProperties() {
        // 验证：如果字符串长度为质数且不是全相同字符，则不可能重复
        String[] primeLength = {"abc", "abcde", "abcdefg"};
        for (String s : primeLength) {
            assertFalse(solution.repeatedSubstringPattern(s), 
                String.format("质数长度字符串不应该重复: %s", s));
        }
        
        // 验证：长度为合数的字符串可能重复
        assertTrue(solution.repeatedSubstringPattern("abab")); // 长度4=2*2
        assertTrue(solution.repeatedSubstringPattern("abcabc")); // 长度6=2*3
        assertTrue(solution.repeatedSubstringPattern("abcdabcd")); // 长度8=2*4
        
        // 验证：重复次数必须≥2
        assertFalse(solution.repeatedSubstringPattern("abc"), "单次出现不算重复");
    }
    
    /**
     * 错误输入处理测试
     */
    @Test
    @DisplayName("错误输入处理")
    void testErrorHandling() {
        // 注意：根据题目描述，输入保证非空，所以不测试null和空字符串
        // 但可以测试其他边界情况
        
        // 单字符
        assertFalse(solution.repeatedSubstringPattern("x"), "单字符不能重复");
        
        // 两个不同字符
        assertFalse(solution.repeatedSubstringPattern("xy"), "两个不同字符不能重复");
        
        // 两个相同字符
        assertTrue(solution.repeatedSubstringPattern("xx"), "两个相同字符可以重复");
    }
    
    /**
     * 复杂度验证测试
     */
    @Test
    @DisplayName("算法复杂度验证")
    void testComplexityVerification() {
        // 创建不同大小的测试数据，验证时间复杂度
        int[] sizes = {100, 500, 1000, 2000};
        long[] times = new long[sizes.length];
        
        for (int i = 0; i < sizes.length; i++) {
            // 创建重复字符串
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < sizes[i]; j++) {
                sb.append("ab");
            }
            String testString = sb.toString();
            
            // 测量执行时间
            long startTime = System.nanoTime();
            solution.repeatedSubstringPattern(testString);
            times[i] = System.nanoTime() - startTime;
        }
        
        // 验证时间复杂度大致为线性（允许一定误差）
        // 这里简单验证最大时间不会过长
        for (long time : times) {
            assertTrue(time < 5_000_000, // 5ms
                String.format("执行时间过长: %d ns", time));
        }
        
        System.out.println("复杂度验证测试完成:");
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("大小: %d, 时间: %.2f ms%n", 
                sizes[i] * 2, times[i] / 1_000_000.0);
        }
    }
}