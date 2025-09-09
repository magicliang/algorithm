package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

/**
 * KaratsubaMultiplication 算法的 JUnit 测试类。
 * 
 * 测试内容包括：
 * 1. 基本功能测试
 * 2. 边界情况测试
 * 3. 正确性验证
 * 4. 性能对比测试
 *
 * @author magicliang
 * @date 2025-09-09
 */
@DisplayName("Karatsuba大整数乘法算法测试")
class KaratsubaMultiplicationTest {

    @BeforeEach
    void setUp() {
        // 测试前的初始化工作
    }

    @Test
    @DisplayName("基本乘法测试")
    void testBasicMultiplication() {
        // 测试小数字乘法
        assertEquals("6", KaratsubaMultiplication.karatsubaMultiply("2", "3"));
        assertEquals("56", KaratsubaMultiplication.karatsubaMultiply("7", "8"));
        assertEquals("81", KaratsubaMultiplication.karatsubaMultiply("9", "9"));
        
        // 测试两位数乘法
        assertEquals("143", KaratsubaMultiplication.karatsubaMultiply("11", "13"));
        assertEquals("2550", KaratsubaMultiplication.karatsubaMultiply("25", "102"));
        
        // 测试多位数乘法
        assertEquals("121932631112635269", KaratsubaMultiplication.karatsubaMultiply("123456789", "987654321"));
    }

    @Test
    @DisplayName("边界情况测试")
    void testEdgeCases() {
        // 测试0的情况
        assertEquals("0", KaratsubaMultiplication.karatsubaMultiply("0", "123"));
        assertEquals("0", KaratsubaMultiplication.karatsubaMultiply("456", "0"));
        assertEquals("0", KaratsubaMultiplication.karatsubaMultiply("0", "0"));
        
        // 测试1的情况
        assertEquals("123", KaratsubaMultiplication.karatsubaMultiply("1", "123"));
        assertEquals("456", KaratsubaMultiplication.karatsubaMultiply("456", "1"));
        
        // 测试前导0
        assertEquals("123", KaratsubaMultiplication.karatsubaMultiply("001", "123"));
        assertEquals("456", KaratsubaMultiplication.karatsubaMultiply("456", "001"));
    }

    @Test
    @DisplayName("大数乘法测试")
    void testLargeNumberMultiplication() {
        // 测试较大的数字
        String num1 = "12345678901234567890";
        String num2 = "98765432109876543210";
        String expected = "1219326311370217952237463801111263526900";
        
        String karatsubaResult = KaratsubaMultiplication.karatsubaMultiply(num1, num2);
        String traditionalResult = KaratsubaMultiplication.traditionalMultiply(num1, num2);
        
        assertEquals(expected, karatsubaResult);
        assertEquals(traditionalResult, karatsubaResult);
    }

    @Test
    @DisplayName("算法正确性验证")
    void testAlgorithmCorrectness() {
        // 使用随机数测试多组数据，验证Karatsuba算法与传统算法结果一致
        Random random = new Random(42); // 使用固定种子确保测试可重复
        
        for (int i = 0; i < 20; i++) {
            String num1 = generateRandomNumber(random, 10 + i);
            String num2 = generateRandomNumber(random, 8 + i);
            
            String karatsubaResult = KaratsubaMultiplication.karatsubaMultiply(num1, num2);
            String traditionalResult = KaratsubaMultiplication.traditionalMultiply(num1, num2);
            
            assertEquals(traditionalResult, karatsubaResult, 
                "算法结果不一致: " + num1 + " × " + num2);
        }
    }

    @Test
    @DisplayName("异常情况测试")
    void testExceptionCases() {
        // 测试null输入
        assertThrows(IllegalArgumentException.class, 
            () -> KaratsubaMultiplication.karatsubaMultiply(null, "123"));
        assertThrows(IllegalArgumentException.class, 
            () -> KaratsubaMultiplication.karatsubaMultiply("123", null));
        
        // 测试空字符串输入
        assertThrows(IllegalArgumentException.class, 
            () -> KaratsubaMultiplication.karatsubaMultiply("", "123"));
        assertThrows(IllegalArgumentException.class, 
            () -> KaratsubaMultiplication.karatsubaMultiply("123", ""));
    }

    @Test
    @DisplayName("性能对比测试")
    void testPerformanceComparison() {
        // 测试不同长度数字的性能表现
        int[] lengths = {20, 50, 100};
        
        for (int len : lengths) {
            String num1 = generateRandomNumber(new Random(), len);
            String num2 = generateRandomNumber(new Random(), len);
            
            // 测试传统方法
            long startTime = System.nanoTime();
            String traditionalResult = KaratsubaMultiplication.traditionalMultiply(num1, num2);
            long traditionalTime = System.nanoTime() - startTime;
            
            // 测试Karatsuba方法
            startTime = System.nanoTime();
            String karatsubaResult = KaratsubaMultiplication.karatsubaMultiply(num1, num2);
            long karatsubaTime = System.nanoTime() - startTime;
            
            // 验证结果一致性
            assertEquals(traditionalResult, karatsubaResult);
            
            // 输出性能信息（仅用于观察，不作为测试断言）
            System.out.printf("长度 %d: 传统算法 %d ns, Karatsuba算法 %d ns%n", 
                len, traditionalTime, karatsubaTime);
        }
    }

    @Test
    @DisplayName("特殊数字模式测试")
    void testSpecialPatterns() {
        // 测试全9的数字
        assertEquals("9801", KaratsubaMultiplication.karatsubaMultiply("99", "99"));
        assertEquals("998001", KaratsubaMultiplication.karatsubaMultiply("999", "999"));
        
        // 测试10的幂次
        assertEquals("1000", KaratsubaMultiplication.karatsubaMultiply("10", "100"));
        assertEquals("10000", KaratsubaMultiplication.karatsubaMultiply("100", "100"));
        
        // 测试回文数
        assertEquals("14677641", KaratsubaMultiplication.karatsubaMultiply("1221", "12021"));
    }

    /**
     * 生成指定长度的随机数字字符串。
     *
     * @param random 随机数生成器
     * @param length 数字长度
     * @return 随机数字字符串
     */
    private String generateRandomNumber(Random random, int length) {
        if (length <= 0) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        
        // 第一位不能是0（除非长度为1且要生成0）
        sb.append(random.nextInt(9) + 1);
        
        for (int i = 1; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        
        return sb.toString();
    }

    /**
     * 性能基准测试（可选运行）。
     * 这个测试比较耗时，通常在性能调优时才运行。
     */
    // @Test
    @DisplayName("性能基准测试")
    void benchmarkTest() {
        System.out.println("=== Karatsuba vs 传统乘法性能对比 ===");
        int[] lengths = {50, 100, 200, 500, 1000};
        
        for (int len : lengths) {
            String num1 = generateRandomNumber(new Random(42), len);
            String num2 = generateRandomNumber(new Random(24), len);
            
            // 预热
            for (int i = 0; i < 5; i++) {
                KaratsubaMultiplication.traditionalMultiply(num1, num2);
                KaratsubaMultiplication.karatsubaMultiply(num1, num2);
            }
            
            // 测试传统方法
            long startTime = System.nanoTime();
            for (int i = 0; i < 10; i++) {
                KaratsubaMultiplication.traditionalMultiply(num1, num2);
            }
            long traditionalTime = (System.nanoTime() - startTime) / 10;
            
            // 测试Karatsuba方法
            startTime = System.nanoTime();
            for (int i = 0; i < 10; i++) {
                KaratsubaMultiplication.karatsubaMultiply(num1, num2);
            }
            long karatsubaTime = (System.nanoTime() - startTime) / 10;
            
            double speedup = (double) traditionalTime / karatsubaTime;
            
            System.out.printf("长度 %4d: 传统 %8d ns, Karatsuba %8d ns, 加速比 %.2fx%n", 
                len, traditionalTime, karatsubaTime, speedup);
        }
    }
}