package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LevenshteinProblem 的单元测试类
 * 测试编辑距离算法的各种场景
 */
@DisplayName("编辑距离算法测试")
class LevenshteinProblemTest {

    private LevenshteinProblem levenshteinProblem;

    @BeforeEach
    void setUp() {
        levenshteinProblem = new LevenshteinProblem();
    }

    @Nested
    @DisplayName("边界条件测试")
    class BoundaryConditionTests {

        @Test
        @DisplayName("两个空字符串的编辑距离应该为0")
        void testBothEmptyStrings() {
            assertEquals(0, levenshteinProblem.editDistanceDfs("", ""));
        }

        @Test
        @DisplayName("源字符串为空时，编辑距离等于目标字符串长度")
        void testSourceStringEmpty() {
            assertEquals(3, levenshteinProblem.editDistanceDfs("", "abc"));
            assertEquals(5, levenshteinProblem.editDistanceDfs("", "hello"));
        }

        @Test
        @DisplayName("目标字符串为空时，编辑距离等于源字符串长度")
        void testTargetStringEmpty() {
            assertEquals(3, levenshteinProblem.editDistanceDfs("abc", ""));
            assertEquals(5, levenshteinProblem.editDistanceDfs("hello", ""));
        }

        @Test
        @DisplayName("处理null字符串")
        void testNullStrings() {
            assertEquals(0, levenshteinProblem.editDistanceDfs(null, null));
            assertEquals(3, levenshteinProblem.editDistanceDfs(null, "abc"));
            assertEquals(3, levenshteinProblem.editDistanceDfs("abc", null));
        }
    }

    @Nested
    @DisplayName("基本功能测试")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("相同字符串的编辑距离应该为0")
        void testIdenticalStrings() {
            assertEquals(0, levenshteinProblem.editDistanceDfs("hello", "hello"));
            assertEquals(0, levenshteinProblem.editDistanceDfs("a", "a"));
            assertEquals(0, levenshteinProblem.editDistanceDfs("algorithm", "algorithm"));
        }

        @Test
        @DisplayName("单字符替换")
        void testSingleCharacterReplacement() {
            assertEquals(1, levenshteinProblem.editDistanceDfs("a", "b"));
            assertEquals(1, levenshteinProblem.editDistanceDfs("cat", "bat"));
        }

        @Test
        @DisplayName("单字符插入")
        void testSingleCharacterInsertion() {
            assertEquals(1, levenshteinProblem.editDistanceDfs("a", "ab"));
            assertEquals(1, levenshteinProblem.editDistanceDfs("cat", "cats"));
        }

        @Test
        @DisplayName("单字符删除")
        void testSingleCharacterDeletion() {
            assertEquals(1, levenshteinProblem.editDistanceDfs("ab", "a"));
            assertEquals(1, levenshteinProblem.editDistanceDfs("cats", "cat"));
        }
    }

    @Nested
    @DisplayName("复杂场景测试")
    class ComplexScenarioTests {

        @ParameterizedTest
        @DisplayName("经典编辑距离测试用例")
        @CsvSource({
            "kitten, sitting, 3",
            "saturday, sunday, 3",
            "intention, execution, 5",
            "distance, difference, 5",
            "horse, ros, 3",
            "abc, def, 3"
        })
        void testClassicEditDistanceCases(String source, String target, int expected) {
            assertEquals(expected, levenshteinProblem.editDistanceDfs(source, target));
        }

        @Test
        @DisplayName("完全不同的字符串")
        void testCompletelyDifferentStrings() {
            assertEquals(3, levenshteinProblem.editDistanceDfs("abc", "xyz"));
            assertEquals(4, levenshteinProblem.editDistanceDfs("abcd", "wxyz"));
        }

        @Test
        @DisplayName("一个字符串是另一个的子串")
        void testSubstringRelationship() {
            assertEquals(2, levenshteinProblem.editDistanceDfs("ab", "abcd"));
            assertEquals(3, levenshteinProblem.editDistanceDfs("test", "testing"));
        }

        @Test
        @DisplayName("反向字符串")
        void testReversedStrings() {
            assertEquals(2, levenshteinProblem.editDistanceDfs("abc", "cba"));
            assertEquals(4, levenshteinProblem.editDistanceDfs("hello", "olleh"));
        }
    }

    @Nested
    @DisplayName("特殊字符测试")
    class SpecialCharacterTests {

        @Test
        @DisplayName("包含数字的字符串")
        void testStringsWithNumbers() {
            assertEquals(3, levenshteinProblem.editDistanceDfs("abc123", "abc456"));
            assertEquals(3, levenshteinProblem.editDistanceDfs("123", "456"));
        }

        @Test
        @DisplayName("包含特殊符号的字符串")
        void testStringsWithSpecialCharacters() {
            assertEquals(1, levenshteinProblem.editDistanceDfs("hello!", "hello?"));
            assertEquals(2, levenshteinProblem.editDistanceDfs("a@b", "a#c"));
        }

        @Test
        @DisplayName("包含空格的字符串")
        void testStringsWithSpaces() {
            assertEquals(1, levenshteinProblem.editDistanceDfs("hello world", "helloworld"));
            assertEquals(2, levenshteinProblem.editDistanceDfs("a b c", "abc"));
        }
    }

    @Nested
    @DisplayName("性能测试")
    class PerformanceTests {

        @Test
        @DisplayName("中等长度字符串测试")
        void testMediumLengthStrings() {
            String s1 = "programming";
            String s2 = "algorithm";
            
            // 这个测试主要确保算法能在合理时间内完成
            long startTime = System.currentTimeMillis();
            int result = levenshteinProblem.editDistanceDfs(s1, s2);
            long endTime = System.currentTimeMillis();
            
            assertTrue(result >= 0, "编辑距离应该是非负数");
            assertTrue(endTime - startTime < 1000, "计算时间应该在1秒内");
        }
    }

    @Nested
    @DisplayName("对称性测试")
    static class SymmetryTests {

        @ParameterizedTest
        @DisplayName("编辑距离应该满足对称性（在某些情况下）")
        @MethodSource("provideStringPairs")
        void testEditDistanceSymmetry(String s1, String s2) {
            LevenshteinProblem problem = new LevenshteinProblem();
            int distance1 = problem.editDistanceDfs(s1, s2);
            int distance2 = problem.editDistanceDfs(s2, s1);
            
            // 编辑距离在大多数情况下是对称的
            assertEquals(distance1, distance2, 
                String.format("编辑距离应该对称: editDistance('%s', '%s') = %d, editDistance('%s', '%s') = %d", 
                    s1, s2, distance1, s2, s1, distance2));
        }

        static Stream<Arguments> provideStringPairs() {
            return Stream.of(
                Arguments.of("abc", "def"),
                Arguments.of("hello", "world"),
                Arguments.of("test", "best"),
                Arguments.of("cat", "dog"),
                Arguments.of("algorithm", "logarithm")
            );
        }
    }

    @Nested
    @DisplayName("三角不等式测试")
    class TriangleInequalityTests {

        @Test
        @DisplayName("编辑距离应该满足三角不等式")
        void testTriangleInequality() {
            String s1 = "abc";
            String s2 = "def";
            String s3 = "ghi";
            
            int d12 = levenshteinProblem.editDistanceDfs(s1, s2);
            int d23 = levenshteinProblem.editDistanceDfs(s2, s3);
            int d13 = levenshteinProblem.editDistanceDfs(s1, s3);
            
            // 三角不等式：d(s1,s3) <= d(s1,s2) + d(s2,s3)
            assertTrue(d13 <= d12 + d23, 
                String.format("三角不等式应该成立: d('%s','%s')=%d <= d('%s','%s')=%d + d('%s','%s')=%d", 
                    s1, s3, d13, s1, s2, d12, s2, s3, d23));
        }
    }
}