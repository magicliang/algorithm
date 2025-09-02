package algorithm.advanced.greedy_algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * maxProductCutting的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class maxProductCuttingTest {

    private maxProductCutting solution;

    @BeforeEach
    void setUp() {
        solution = new maxProductCutting();
    }

    @Test
    void testBoundaryCase() {
        // 测试边界情况
        assertEquals(1, solution.maxProductCutting(2), "n=2时应该返回1");
        assertEquals(2, solution.maxProductCutting(3), "n=3时应该返回2");
    }

    @Test
    void testSmallNumbers() {
        // 测试小数值
        assertEquals(4, solution.maxProductCutting(4), "n=4时应该返回4 (2*2)");
        assertEquals(6, solution.maxProductCutting(5), "n=5时应该返回6 (2*3)");
        assertEquals(9, solution.maxProductCutting(6), "n=6时应该返回9 (3*3)");
        assertEquals(12, solution.maxProductCutting(7), "n=7时应该返回12 (2*2*3)");
    }

    @Test
    void testMediumNumbers() {
        // 测试中等数值
        assertEquals(18, solution.maxProductCutting(8), "n=8时应该返回18 (2*3*3)");
        assertEquals(27, solution.maxProductCutting(9), "n=9时应该返回27 (3*3*3)");
        assertEquals(36, solution.maxProductCutting(10), "n=10时应该返回36 (2*2*3*3)");
    }

    @Test
    void testLargeNumbers() {
        // 测试大数值
        assertEquals(243, solution.maxProductCutting(15), "n=15时应该返回243 (3^5)");
        assertEquals(1458, solution.maxProductCutting(20), "n=20时应该返回1458 (2*3^6)");
    }

    @Test
    void testRemainderZero() {
        // 测试余数为0的情况（n能被3整除）
        assertEquals(27, solution.maxProductCutting(9), "n=9时余数为0，应该返回27 (3^3)");
        assertEquals(729, solution.maxProductCutting(18), "n=18时余数为0，应该返回729 (3^6)");
    }

    @Test
    void testRemainderOne() {
        // 测试余数为1的情况（用2*2替换1*3）
        assertEquals(12, solution.maxProductCutting(7), "n=7时余数为1，应该返回12 (2*2*3)");
        assertEquals(36, solution.maxProductCutting(10), "n=10时余数为1，应该返回36 (2*2*3*3)");
        assertEquals(972, solution.maxProductCutting(19), "n=19时余数为1，应该返回972 (2*2*3^5)");
    }

    @Test
    void testRemainderTwo() {
        // 测试余数为2的情况（直接保留一个2）
        assertEquals(6, solution.maxProductCutting(5), "n=5时余数为2，应该返回6 (2*3)");
        assertEquals(18, solution.maxProductCutting(8), "n=8时余数为2，应该返回18 (2*3*3)");
        assertEquals(486, solution.maxProductCutting(17), "n=17时余数为2，应该返回486 (2*3^5)");
    }

    @Test
    void testFastPowerMethod() {
        // 测试fastPower方法
        assertEquals(1, solution.fastPower(3, 0), "3^0应该等于1");
        assertEquals(3, solution.fastPower(3, 1), "3^1应该等于3");
        assertEquals(9, solution.fastPower(3, 2), "3^2应该等于9");
        assertEquals(27, solution.fastPower(3, 3), "3^3应该等于27");
        assertEquals(81, solution.fastPower(3, 4), "3^4应该等于81");
        assertEquals(243, solution.fastPower(3, 5), "3^5应该等于243");
        
        // 测试其他底数
        assertEquals(16, solution.fastPower(2, 4), "2^4应该等于16");
        assertEquals(32, solution.fastPower(2, 5), "2^5应该等于32");
        assertEquals(125, solution.fastPower(5, 3), "5^3应该等于125");
    }

    @Test
    void testGreedyStrategy() {
        // 验证贪心策略的正确性
        // n=11: 11/3=3余2，应该是3*3*3*2=54
        assertEquals(54, solution.maxProductCutting(11), "n=11时应该返回54");
        
        // n=12: 12/3=4余0，应该是3^4=81
        assertEquals(81, solution.maxProductCutting(12), "n=12时应该返回81");
        
        // n=13: 13/3=4余1，应该是3^3*2*2=108
        assertEquals(108, solution.maxProductCutting(13), "n=13时应该返回108");
        
        // n=14: 14/3=4余2，应该是3^4*2=162
        assertEquals(162, solution.maxProductCutting(14), "n=14时应该返回162");
    }

    @Test
    void testMathematicalCorrectness() {
        // 验证数学正确性：确保切分后的乘积确实大于原数
        // 注意：n=4时切分后乘积等于原数(2*2=4)，从n=5开始切分后乘积才大于原数
        for (int n = 5; n <= 20; n++) {
            int result = solution.maxProductCutting(n);
            assertTrue(result > n, String.format("n=%d时，切分后的乘积%d应该大于原数%d", n, result, n));
        }
        
        // 特别验证n=4的情况：切分后乘积等于原数
        assertEquals(4, solution.maxProductCutting(4), "n=4时，切分后的乘积应该等于原数4");
    }

    @Test
    void testConsistencyWithBruteForce() {
        // 对于小数值，验证与暴力解法的一致性
        // 这里我们手动计算一些小数值的最优解来验证
        
        // n=4: 可能的切分 2+2(乘积4) 或 1+3(乘积3) 或 1+1+2(乘积2) 或 1+1+1+1(乘积1)
        // 最优是2+2=4
        assertEquals(4, solution.maxProductCutting(4));
        
        // n=6: 可能的切分 3+3(乘积9) 或 2+2+2(乘积8) 或 2+4(乘积8) 等
        // 最优是3+3=9
        assertEquals(9, solution.maxProductCutting(6));
        
        // n=8: 可能的切分 3+3+2(乘积18) 或 2+2+2+2(乘积16) 或 2+3+3(乘积18) 等
        // 最优是3+3+2=18
        assertEquals(18, solution.maxProductCutting(8));
    }

    @Test
    void testEdgeCasesForFastPower() {
        // 测试fastPower的边界情况
        assertEquals(1, solution.fastPower(1, 100), "1的任何次幂都应该是1");
        assertEquals(1, solution.fastPower(100, 0), "任何数的0次幂都应该是1");
        assertEquals(2, solution.fastPower(2, 1), "2的1次幂应该是2");
    }

    @Test
    void testLargeExponents() {
        // 测试大指数的情况
        assertEquals(729, solution.fastPower(3, 6), "3^6应该等于729");
        assertEquals(2187, solution.fastPower(3, 7), "3^7应该等于2187");
        assertEquals(6561, solution.fastPower(3, 8), "3^8应该等于6561");
    }
}