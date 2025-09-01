package algorithm.advanced.dynamicprogramming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * LevenshteinProblem 类的 JUnit 5 测试用例
 * 测试编辑距离算法的两种实现：DFS版本和记忆化版本
 * 
 * @author magicliang
 * @version 1.0
 */
@DisplayName("编辑距离算法测试")
class LevenshteinProblemTest {

    private LevenshteinProblem solution;

    @BeforeEach
    void setUp() {
        solution = new LevenshteinProblem();
    }

    // ==================== 基本功能测试 ====================

    @Test
    @DisplayName("测试基本情况：horse -> ros")
    void testBasicCase() {
        String s = "horse";
        String t = "ros";
        int expected = 3; // 删除h, 删除r, 删除e
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试复杂转换：intention -> execution")
    void testComplexTransformation() {
        String s = "intention";
        String t = "execution";
        int expected = 5;
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    // ==================== 边界条件测试 ====================

    @Test
    @DisplayName("测试空字符串到非空字符串")
    void testEmptyToNonEmpty() {
        String s = "";
        String t = "abc";
        int expected = 3; // 插入a, b, c
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试非空字符串到空字符串")
    void testNonEmptyToEmpty() {
        String s = "abc";
        String t = "";
        int expected = 3; // 删除a, b, c
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试两个空字符串")
    void testBothEmpty() {
        String s = "";
        String t = "";
        int expected = 0;
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试null字符串处理")
    void testNullStrings() {
        // 测试null到非null
        assertEquals(3, solution.editDistanceDfs(null, "abc"));
        assertEquals(3, solution.editDistanceMemoization(null, "abc"));
        
        // 测试非null到null
        assertEquals(3, solution.editDistanceDfs("abc", null));
        assertEquals(3, solution.editDistanceMemoization("abc", null));
        
        // 测试两个null
        assertEquals(0, solution.editDistanceDfs(null, null));
        assertEquals(0, solution.editDistanceMemoization(null, null));
    }

    // ==================== 特殊情况测试 ====================

    @Test
    @DisplayName("测试相同字符串")
    void testIdenticalStrings() {
        String s = "hello";
        String t = "hello";
        int expected = 0; // 无需任何操作
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试单字符替换")
    void testSingleCharacterReplacement() {
        String s = "a";
        String t = "b";
        int expected = 1; // 替换a为b
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试只需插入操作")
    void testInsertionOnly() {
        String s = "cat";
        String t = "cats";
        int expected = 1; // 插入s
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试只需删除操作")
    void testDeletionOnly() {
        String s = "cats";
        String t = "cat";
        int expected = 1; // 删除s
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    @Test
    @DisplayName("测试长度差异很大的字符串")
    void testLargeLengthDifference() {
        String s = "a";
        String t = "abcdefghij";
        int expected = 9; // 插入9个字符
        
        assertEquals(expected, solution.editDistanceDfs(s, t));
        assertEquals(expected, solution.editDistanceMemoization(s, t));
    }

    // ==================== 算法一致性测试 ====================

    @Test
    @DisplayName("测试两种算法结果一致性")
    void testAlgorithmConsistency() {
        String[][] testCases = {
            {"", ""},
            {"a", ""},
            {"", "a"},
            {"a", "a"},
            {"ab", "ba"},
            {"abc", "def"},
            {"kitten", "sitting"},
            {"saturday", "sunday"},
            {"exponential", "polynomial"}
        };
        
        for (String[] testCase : testCases) {
            String s = testCase[0];
            String t = testCase[1];
            
            int dfsResult = solution.editDistanceDfs(s, t);
            int memoResult = solution.editDistanceMemoization(s, t);
            
            assertEquals(dfsResult, memoResult, 
                String.format("算法结果不一致：'%s' -> '%s', DFS: %d, Memo: %d", 
                    s, t, dfsResult, memoResult));
        }
    }

    // ==================== 性能测试 ====================

    @Test
    @DisplayName("测试记忆化版本性能优势")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testMemoizationPerformance() {
        // 使用中等长度的字符串测试性能
        String s = "abcdefgh";
        String t = "12345678";
        
        // 测试DFS版本
        long startTime = System.nanoTime();
        int dfsResult = solution.editDistanceDfs(s, t);
        long dfsTime = System.nanoTime() - startTime;
        
        // 测试记忆化版本
        startTime = System.nanoTime();
        int memoResult = solution.editDistanceMemoization(s, t);
        long memoTime = System.nanoTime() - startTime;
        
        // 验证结果一致
        assertEquals(dfsResult, memoResult);
        
        // 记忆化版本应该更快（对于这个规模的输入）
        System.out.printf("DFS耗时: %d ns, 记忆化耗时: %d ns, 性能提升: %.2fx%n", 
            dfsTime, memoTime, (double)dfsTime / memoTime);
    }

    @Test
    @DisplayName("测试大规模输入的记忆化性能")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testLargeInputMemoization() {
        // 只测试记忆化版本，因为DFS版本对大输入会很慢
        String s = "abcdefghijklmnop"; // 16个字符
        String t = "1234567890123456"; // 16个字符
        
        long startTime = System.nanoTime();
        int result = solution.editDistanceMemoization(s, t);
        long endTime = System.nanoTime();
        
        // 验证结果合理（完全不同的字符串，最多需要16次替换）
        assertTrue(result <= Math.max(s.length(), t.length()));
        assertTrue(result >= Math.abs(s.length() - t.length()));
        
        System.out.printf("大规模输入记忆化耗时: %d ns, 结果: %d%n", 
            endTime - startTime, result);
    }

    // ==================== 压力测试 ====================

    @Test
    @DisplayName("测试多次调用的稳定性")
    void testMultipleCallsStability() {
        String s = "test";
        String t = "best";
        int expected = 1; // 替换t为b
        
        // 多次调用验证结果稳定
        for (int i = 0; i < 100; i++) {
            assertEquals(expected, solution.editDistanceDfs(s, t));
            assertEquals(expected, solution.editDistanceMemoization(s, t));
        }
    }

    @Test
    @DisplayName("测试特殊字符和Unicode")
    void testSpecialCharacters() {
        // 测试包含特殊字符的字符串
        String s = "café";
        String t = "cave";
        
        int dfsResult = solution.editDistanceDfs(s, t);
        int memoResult = solution.editDistanceMemoization(s, t);
        
        assertEquals(dfsResult, memoResult);
        assertTrue(dfsResult >= 0);
        
        // 测试中文字符
        String s2 = "你好";
        String t2 = "再见";
        
        int dfsResult2 = solution.editDistanceDfs(s2, t2);
        int memoResult2 = solution.editDistanceMemoization(s2, t2);
        
        assertEquals(dfsResult2, memoResult2);
        assertTrue(dfsResult2 >= 0);
    }
}