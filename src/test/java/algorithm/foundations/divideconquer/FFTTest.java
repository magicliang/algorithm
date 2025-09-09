package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

/**
 * FFT (快速傅里叶变换) 算法测试类。
 * 
 * 测试内容：
 * 1. 复数基本运算测试
 * 2. 简单FFT变换测试
 * 3. FFT和IFFT互逆性测试
 * 4. 正弦波频谱分析测试
 * 5. 多项式乘法测试
 * 6. 大规模FFT性能测试
 * 7. 边界情况测试
 * 
 * @author magicliang
 * @date 2025-09-09 12:30
 */
@DisplayName("FFT 快速傅里叶变换算法测试")
public class FFTTest {

    private FFT fft;

    @BeforeEach
    void setUp() {
        fft = new FFT();
    }

    @Test
    @DisplayName("测试复数基本运算")
    void testComplexArithmetic() {
        System.out.println("=== 测试复数运算 ===");
        
        FFT.Complex c1 = new FFT.Complex(3, 4);
        FFT.Complex c2 = new FFT.Complex(1, 2);
        
        FFT.Complex sum = c1.add(c2);
        FFT.Complex diff = c1.subtract(c2);
        FFT.Complex product = c1.multiply(c2);
        
        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);
        System.out.println("c1 + c2 = " + sum);
        System.out.println("c1 - c2 = " + diff);
        System.out.println("c1 * c2 = " + product);
        System.out.println("模长 |c1| = " + c1.magnitude());
        System.out.println("相位 arg(c1) = " + c1.phase());
        
        // 验证加法：(3+4i) + (1+2i) = (4+6i)
        assertEquals(4.0, sum.real, 1e-9, "复数加法实部计算错误");
        assertEquals(6.0, sum.imag, 1e-9, "复数加法虚部计算错误");
        
        // 验证减法：(3+4i) - (1+2i) = (2+2i)
        assertEquals(2.0, diff.real, 1e-9, "复数减法实部计算错误");
        assertEquals(2.0, diff.imag, 1e-9, "复数减法虚部计算错误");
        
        // 验证乘法：(3+4i) * (1+2i) = (3-8) + (6+4)i = -5+10i
        assertEquals(-5.0, product.real, 1e-9, "复数乘法实部计算错误");
        assertEquals(10.0, product.imag, 1e-9, "复数乘法虚部计算错误");
        
        // 验证模长：|3+4i| = sqrt(9+16) = 5
        assertEquals(5.0, c1.magnitude(), 1e-9, "复数模长计算错误");
        
        // 验证相位角
        double expectedPhase = Math.atan2(4, 3);
        assertEquals(expectedPhase, c1.phase(), 1e-9, "复数相位角计算错误");
    }

    @Test
    @DisplayName("测试简单4点FFT")
    void testSimpleFFT() {
        System.out.println("\n=== 测试简单 FFT ===");
        
        // 测试简单的4点FFT
        FFT.Complex[] input = {
            new FFT.Complex(1, 0),
            new FFT.Complex(2, 0),
            new FFT.Complex(3, 0),
            new FFT.Complex(4, 0)
        };
        
        FFT.Complex[] result = FFT.fft(input);
        
        System.out.println("输入信号：");
        for (int i = 0; i < input.length; i++) {
            System.out.println("x[" + i + "] = " + input[i]);
        }
        
        System.out.println("FFT 结果：");
        for (int i = 0; i < result.length; i++) {
            System.out.println("X[" + i + "] = " + result[i]);
        }
        
        // 验证结果长度（应该扩展到2的幂次）
        assertTrue(result.length >= input.length, "FFT结果长度应该大于等于输入长度");
        assertTrue((result.length & (result.length - 1)) == 0, "FFT结果长度应该是2的幂次");
        
        // 验证 DC 分量（第一个元素应该是所有输入的和）
        assertEquals(10.0, result[0].real, 1e-9, "DC分量计算错误");
        assertEquals(0.0, result[0].imag, 1e-9, "DC分量虚部应该为0");
    }

    @Test
    @DisplayName("测试FFT和IFFT的互逆性")
    void testFFTAndIFFT() {
        System.out.println("\n=== 测试 FFT 和 IFFT 的互逆性 ===");
        
        FFT.Complex[] original = {
            new FFT.Complex(1, 0),
            new FFT.Complex(0, 1),
            new FFT.Complex(-1, 0),
            new FFT.Complex(0, -1)
        };
        
        // FFT 然后 IFFT
        FFT.Complex[] fftResult = FFT.fft(original);
        FFT.Complex[] ifftResult = FFT.ifft(fftResult);
        
        System.out.println("原始信号：");
        for (int i = 0; i < original.length; i++) {
            System.out.println("x[" + i + "] = " + original[i]);
        }
        
        System.out.println("IFFT 恢复信号：");
        for (int i = 0; i < Math.min(ifftResult.length, original.length); i++) {
            System.out.println("x'[" + i + "] = " + ifftResult[i]);
        }
        
        // 验证恢复的信号与原始信号相等（在误差范围内）
        for (int i = 0; i < original.length; i++) {
            assertEquals(original[i].real, ifftResult[i].real, 1e-9, 
                "IFFT恢复信号实部与原始信号不匹配，索引: " + i);
            assertEquals(original[i].imag, ifftResult[i].imag, 1e-9, 
                "IFFT恢复信号虚部与原始信号不匹配，索引: " + i);
        }
        
        System.out.println("FFT-IFFT 互逆性验证通过");
    }

    @Test
    @DisplayName("测试正弦波频谱分析")
    void testSineWaveFFT() {
        System.out.println("\n=== 测试正弦波 FFT ===");
        
        // 生成包含两个频率分量的信号：5Hz 和 10Hz
        double sampleRate = 64;  // 采样率
        double duration = 1.0;   // 持续时间
        
        FFT.Complex[] signal5Hz = FFT.generateSineWave(5, (int)sampleRate, duration);
        FFT.Complex[] signal10Hz = FFT.generateSineWave(10, (int)sampleRate, duration);
        
        // 合成信号
        FFT.Complex[] combinedSignal = new FFT.Complex[signal5Hz.length];
        for (int i = 0; i < combinedSignal.length; i++) {
            combinedSignal[i] = signal5Hz[i].add(signal10Hz[i]);
        }
        
        // 执行 FFT
        FFT.Complex[] spectrum = FFT.fft(combinedSignal);
        
        System.out.println("频谱分析结果（前16个频率分量）：");
        for (int i = 0; i < Math.min(16, spectrum.length); i++) {
            double frequency = i * sampleRate / spectrum.length;
            double magnitude = spectrum[i].magnitude();
            System.out.printf("频率 %.1f Hz: 幅度 = %.3f\n", frequency, magnitude);
        }
        
        // 验证在5Hz和10Hz处有峰值
        int index5Hz = (int) Math.round(5 * spectrum.length / sampleRate);
        int index10Hz = (int) Math.round(10 * spectrum.length / sampleRate);
        
        assertTrue(spectrum[index5Hz].magnitude() > 10, 
            "5Hz处应该有较大幅度，实际幅度: " + spectrum[index5Hz].magnitude());
        assertTrue(spectrum[index10Hz].magnitude() > 10, 
            "10Hz处应该有较大幅度，实际幅度: " + spectrum[index10Hz].magnitude());
        
        System.out.println("正弦波频谱分析验证通过");
    }

    @Test
    @DisplayName("测试多项式乘法")
    void testPolynomialMultiplication() {
        System.out.println("\n=== 测试多项式乘法 ===");
        
        // 测试 (1 + 2x) * (3 + 4x) = 3 + 10x + 8x²
        double[] poly1 = {1, 2};    // 1 + 2x
        double[] poly2 = {3, 4};    // 3 + 4x
        
        double[] result = FFT.polynomialMultiply(poly1, poly2);
        
        System.out.println("多项式1: 1 + 2x");
        System.out.println("多项式2: 3 + 4x");
        System.out.print("乘积: ");
        for (int i = 0; i < result.length; i++) {
            if (i > 0 && result[i] >= 0) System.out.print(" + ");
            if (i == 0) {
                System.out.print(result[i]);
            } else if (i == 1) {
                System.out.print(result[i] + "x");
            } else {
                System.out.print(result[i] + "x^" + i);
            }
        }
        System.out.println();
        
        // 验证结果：3 + 10x + 8x²
        assertEquals(3, result.length, "多项式乘积长度错误");
        assertEquals(3.0, result[0], 1e-9, "常数项系数错误");
        assertEquals(10.0, result[1], 1e-9, "x项系数错误");
        assertEquals(8.0, result[2], 1e-9, "x²项系数错误");
        
        // 测试更复杂的多项式乘法
        double[] poly3 = {1, 2, 3};    // 1 + 2x + 3x²
        double[] poly4 = {4, 5};       // 4 + 5x
        double[] result2 = FFT.polynomialMultiply(poly3, poly4);
        
        // (1 + 2x + 3x²) * (4 + 5x) = 4 + 13x + 22x² + 15x³
        assertEquals(4, result2.length, "复杂多项式乘积长度错误");
        assertEquals(4.0, result2[0], 1e-9, "复杂多项式常数项错误");
        assertEquals(13.0, result2[1], 1e-9, "复杂多项式x项错误");
        assertEquals(22.0, result2[2], 1e-9, "复杂多项式x²项错误");
        assertEquals(15.0, result2[3], 1e-9, "复杂多项式x³项错误");
        
        System.out.println("多项式乘法验证通过");
    }

    @Test
    @DisplayName("测试大规模FFT性能")
    void testLargeFFT() {
        System.out.println("\n=== 测试大规模 FFT 性能 ===");
        
        int[] sizes = {64, 256, 1024, 4096};
        
        for (int size : sizes) {
            // 生成随机信号
            FFT.Complex[] signal = new FFT.Complex[size];
            Random random = new Random(42); // 固定种子确保可重复性
            
            for (int i = 0; i < size; i++) {
                signal[i] = new FFT.Complex(random.nextGaussian(), random.nextGaussian());
            }
            
            // 测量 FFT 时间
            long startTime = System.currentTimeMillis();
            FFT.Complex[] fftResult = FFT.fft(signal);
            long fftTime = System.currentTimeMillis() - startTime;
            
            // 测量 IFFT 时间
            startTime = System.currentTimeMillis();
            FFT.Complex[] ifftResult = FFT.ifft(fftResult);
            long ifftTime = System.currentTimeMillis() - startTime;
            
            System.out.printf("大小 %d: FFT %dms, IFFT %dms\n", size, fftTime, ifftTime);
            
            // 验证 FFT-IFFT 的互逆性
            double maxError = 0;
            for (int i = 0; i < size; i++) {
                double errorReal = Math.abs(signal[i].real - ifftResult[i].real);
                double errorImag = Math.abs(signal[i].imag - ifftResult[i].imag);
                maxError = Math.max(maxError, Math.max(errorReal, errorImag));
            }
            
            System.out.printf("最大恢复误差: %.2e\n", maxError);
            assertTrue(maxError < 1e-10, "FFT-IFFT 恢复误差过大，大小: " + size + ", 误差: " + maxError);
        }
        
        System.out.println("大规模FFT性能测试通过");
    }

    @Test
    @DisplayName("测试边界情况")
    void testEdgeCases() {
        System.out.println("\n=== 测试边界情况 ===");
        
        // 测试单点 FFT
        FFT.Complex[] singlePoint = {new FFT.Complex(5, 3)};
        FFT.Complex[] result = FFT.fft(singlePoint);
        assertEquals(1, result.length, "单点FFT结果长度错误");
        assertEquals(5.0, result[0].real, 1e-9, "单点FFT实部错误");
        assertEquals(3.0, result[0].imag, 1e-9, "单点FFT虚部错误");
        
        // 测试空输入
        assertThrows(IllegalArgumentException.class, () -> {
            FFT.fft(null);
        }, "null输入应该抛出异常");
        
        assertThrows(IllegalArgumentException.class, () -> {
            FFT.fft(new FFT.Complex[0]);
        }, "空数组输入应该抛出异常");
        
        // 测试IFFT的空输入
        assertThrows(IllegalArgumentException.class, () -> {
            FFT.ifft(null);
        }, "IFFT null输入应该抛出异常");
        
        assertThrows(IllegalArgumentException.class, () -> {
            FFT.ifft(new FFT.Complex[0]);
        }, "IFFT空数组输入应该抛出异常");
        
        // 测试全零输入
        FFT.Complex[] zeros = {
            new FFT.Complex(0), new FFT.Complex(0), 
            new FFT.Complex(0), new FFT.Complex(0)
        };
        FFT.Complex[] zeroResult = FFT.fft(zeros);
        for (int i = 0; i < zeroResult.length; i++) {
            assertEquals(0.0, zeroResult[i].real, 1e-9, "全零输入FFT实部应该为0，索引: " + i);
            assertEquals(0.0, zeroResult[i].imag, 1e-9, "全零输入FFT虚部应该为0，索引: " + i);
        }
        
        // 测试多项式乘法的边界情况
        double[] emptyPoly = {};
        double[] normalPoly = {1, 2, 3};
        
        double[] emptyResult = FFT.polynomialMultiply(emptyPoly, normalPoly);
        assertEquals(0, emptyResult.length, "空多项式乘法结果应该为空");
        
        double[] nullResult = FFT.polynomialMultiply(null, normalPoly);
        assertEquals(0, nullResult.length, "null多项式乘法结果应该为空");
        
        System.out.println("所有边界情况测试通过");
    }

    @Test
    @DisplayName("测试复数相等性")
    void testComplexEquality() {
        FFT.Complex c1 = new FFT.Complex(1.0, 2.0);
        FFT.Complex c2 = new FFT.Complex(1.0, 2.0);
        FFT.Complex c3 = new FFT.Complex(1.0, 2.1);
        
        assertEquals(c1, c2, "相同值的复数应该相等");
        assertNotEquals(c1, c3, "不同值的复数应该不相等");
        assertNotEquals(c1, null, "复数与null应该不相等");
        assertNotEquals(c1, "string", "复数与字符串应该不相等");
        
        // 测试浮点数精度
        FFT.Complex c4 = new FFT.Complex(1.0000000001, 2.0);
        assertEquals(c1, c4, "在精度范围内的复数应该相等");
    }

    @Test
    @DisplayName("测试正弦波生成")
    void testSineWaveGeneration() {
        double frequency = 1.0; // 1Hz
        int sampleRate = 8;     // 8 samples per second
        double duration = 1.0;  // 1 second
        
        FFT.Complex[] wave = FFT.generateSineWave(frequency, sampleRate, duration);
        
        assertEquals(8, wave.length, "正弦波样本数量错误");
        
        // 验证第一个样本（t=0时，sin(0) = 0）
        assertEquals(0.0, wave[0].real, 1e-9, "t=0时正弦波值应该为0");
        assertEquals(0.0, wave[0].imag, 1e-9, "正弦波虚部应该为0");
        
        // 验证第二个样本（t=1/8时，sin(2π*1*1/8) = sin(π/4) = √2/2）
        double expected = Math.sin(2 * Math.PI * frequency * (1.0/8));
        assertEquals(expected, wave[1].real, 1e-9, "t=1/8时正弦波值错误");
    }
}