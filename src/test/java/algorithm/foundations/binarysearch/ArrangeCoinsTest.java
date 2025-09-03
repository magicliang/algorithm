package algorithm.foundations.binarysearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ArrangeCoins 测试类
 * 
 * 测试覆盖：
 * 1. 基本功能测试
 * 2. 边界条件测试  
 * 3. 大数测试（溢出场景）
 * 4. 多种解法一致性测试
 */
class ArrangeCoinsTest {
    
    private ArrangeCoins solution;
    
    @BeforeEach
    void setUp() {
        solution = new ArrangeCoins();
    }
    
    @Test
    @DisplayName("基本功能测试")
    void testBasicExamples() {
        assertEquals(2, solution.arrangeCoins(5));
        assertEquals(3, solution.arrangeCoins(8));
        assertEquals(1, solution.arrangeCoins(1));
        assertEquals(4, solution.arrangeCoins(10));
    }
    
    @ParameterizedTest
    @DisplayName("参数化测试")
    @CsvSource({
        "1, 1", "2, 1", "3, 2", "4, 2", "5, 2", "6, 3", "7, 3", "8, 3", 
        "9, 3", "10, 4", "15, 5", "20, 5", "21, 6", "100, 13", "1000, 44"
    })
    void testParameterized(int n, int expected) {
        assertEquals(expected, solution.arrangeCoins(n));
    }
    
    @ParameterizedTest
    @DisplayName("大数测试")
    @ValueSource(ints = {1000000, 10000000, 100000000, 1804289383})
    void testLargeNumbers(int n) {
        int result = solution.arrangeCoins(n);
        long currentSum = (long) result * (result + 1) / 2;
        long nextSum = (long) (result + 1) * (result + 2) / 2;
        
        assertTrue(currentSum <= n);
        assertTrue(nextSum > n);
    }
    
    @Test
    @DisplayName("多种解法一致性测试")
    void testConsistency() {
        int[] testCases = {1, 5, 8, 10, 15, 21, 100, 1000, 1804289383};
        
        for (int n : testCases) {
            int result1 = solution.arrangeCoins(n);
            int result2 = solution.arrangeCoins2(n);
            int result3 = solution.arrangeCoinsMath(n);
            
            assertEquals(result1, result2);
            assertEquals(result1, result3);
        }
    }
}