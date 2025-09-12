package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Fibonacci 的单元测试
 * 测试斐波那契数列的DP解法
 */
public class FibonacciTest {

    @Test
    public void testBaseCase() {
        // 测试基础情况
        assertEquals(0L, Fibonacci.fib(0));
        assertEquals(1L, Fibonacci.fib(1));
    }

    @Test
    public void testSmallNumbers() {
        // 测试小数字
        assertEquals(1L, Fibonacci.fib(2)); // F(2) = F(1) + F(0) = 1 + 0 = 1
        assertEquals(2L, Fibonacci.fib(3)); // F(3) = F(2) + F(1) = 1 + 1 = 2
        assertEquals(3L, Fibonacci.fib(4)); // F(4) = F(3) + F(2) = 2 + 1 = 3
        assertEquals(5L, Fibonacci.fib(5)); // F(5) = F(4) + F(3) = 3 + 2 = 5
    }

    @Test
    public void testMediumNumbers() {
        // 测试中等数字
        assertEquals(8L, Fibonacci.fib(6));   // F(6) = 5 + 3 = 8
        assertEquals(13L, Fibonacci.fib(7));  // F(7) = 8 + 5 = 13
        assertEquals(21L, Fibonacci.fib(8));  // F(8) = 13 + 8 = 21
        assertEquals(34L, Fibonacci.fib(9));  // F(9) = 21 + 13 = 34
        assertEquals(55L, Fibonacci.fib(10)); // F(10) = 34 + 21 = 55
    }

    @Test
    public void testLargerNumbers() {
        // 测试较大数字
        assertEquals(89L, Fibonacci.fib(11));   // F(11) = 55 + 34 = 89
        assertEquals(144L, Fibonacci.fib(12));  // F(12) = 89 + 55 = 144
        assertEquals(233L, Fibonacci.fib(13));  // F(13) = 144 + 89 = 233
        assertEquals(377L, Fibonacci.fib(14));  // F(14) = 233 + 144 = 377
        assertEquals(610L, Fibonacci.fib(15));  // F(15) = 377 + 233 = 610
    }

    @Test
    public void testFibonacciSequence() {
        // 测试斐波那契数列的连续性
        for (int i = 2; i <= 20; i++) {
            long current = Fibonacci.fib(i);
            long prev1 = Fibonacci.fib(i - 1);
            long prev2 = Fibonacci.fib(i - 2);
            assertEquals(prev1 + prev2, current, 
                String.format("F(%d) should equal F(%d) + F(%d)", i, i-1, i-2));
        }
    }

    @Test
    public void testPerformance() {
        // 测试性能，确保DP解法能快速计算较大的斐波那契数
        long startTime = System.currentTimeMillis();
        long result = Fibonacci.fib(30);
        long endTime = System.currentTimeMillis();
        
        assertEquals(832040L, result); // F(30) = 832040
        assertTrue(endTime - startTime < 100, "计算F(30)应该在100ms内完成");
    }

    @Test
    public void testLargeNumber() {
        // 测试更大的数字（注意可能的整数溢出）
        long result = Fibonacci.fib(40);
        assertTrue(result > 0, "F(40)应该是正数");
        assertEquals(102334155L, result); // F(40) = 102334155
    }

    @Test
    public void testNegativeInput() {
        // 测试负数输入（根据实现可能抛出异常或返回特定值）
        assertThrows(IllegalArgumentException.class, () -> {
            Fibonacci.fib(-1);
        }, "负数输入应该抛出异常");
    }
}