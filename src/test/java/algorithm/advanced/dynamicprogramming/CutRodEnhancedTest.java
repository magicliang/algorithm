package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 切割钢条问题增强功能的测试类
 * 
 * 测试新增的功能：
 * 1. 带切割方案追踪的算法
 * 2. 自顶向下递归算法
 * 3. 性能比较功能
 * 4. 辅助工具方法
 * 
 * @author magicliang
 * @version 1.0
 * @since 2025-09-16
 */
public class CutRodEnhancedTest {
    
    private CutRod cutRod;
    
    @BeforeEach
    void setUp() {
        cutRod = new CutRod();
    }
    
    @Test
    @DisplayName("测试带切割方案追踪的算法")
    void testCutRodWithSolution() {
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        
        // 测试长度为4的钢条
        CutRod.CutResult result4 = cutRod.cutRodWithSolution(prices, 4);
        assertEquals(10, result4.getMaxRevenue());
        
        // 验证切割方案的总长度等于原长度
        int totalLength = result4.getCuts().stream().mapToInt(Integer::intValue).sum();
        assertEquals(4, totalLength);
        
        // 验证切割方案的总价值等于最大收益
        int totalValue = result4.getCuts().stream()
                .mapToInt(cut -> prices[cut - 1])
                .sum();
        assertEquals(10, totalValue);
        
        // 测试长度为7的钢条
        CutRod.CutResult result7 = cutRod.cutRodWithSolution(prices, 7);
        assertEquals(18, result7.getMaxRevenue());
        
        totalLength = result7.getCuts().stream().mapToInt(Integer::intValue).sum();
        assertEquals(7, totalLength);
        
        System.out.println("长度7的最优切割方案: " + result7.getCuts());
    }
    
    @Test
    @DisplayName("测试自顶向下递归算法")
    void testCutRodTopDown() {
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        
        // 测试各种长度，确保与自底向上算法结果一致
        for (int n = 0; n <= 10; n++) {
            int bottomUpResult = cutRod.cutRod(prices, n);
            int topDownResult = cutRod.cutRodTopDown(prices, n);
            assertEquals(bottomUpResult, topDownResult, 
                "长度 " + n + " 的结果不一致：自底向上=" + bottomUpResult + ", 自顶向下=" + topDownResult);
        }
    }
    
    @Test
    @DisplayName("测试边界情况处理")
    void testEdgeCases() {
        int[] prices = {1, 5, 8, 9};
        
        // 测试长度为0
        assertEquals(0, cutRod.cutRod(prices, 0));
        assertEquals(0, cutRod.cutRodTopDown(prices, 0));
        
        CutRod.CutResult result0 = cutRod.cutRodWithSolution(prices, 0);
        assertEquals(0, result0.getMaxRevenue());
        assertTrue(result0.getCuts().isEmpty());
        
        // 测试异常输入
        assertThrows(IllegalArgumentException.class, () -> cutRod.cutRod(null, 5));
        assertThrows(IllegalArgumentException.class, () -> cutRod.cutRod(prices, -1));
        assertThrows(IllegalArgumentException.class, () -> cutRod.cutRodTopDown(null, 5));
        assertThrows(IllegalArgumentException.class, () -> cutRod.cutRodWithSolution(prices, -1));
    }
    
    @Test
    @DisplayName("测试单位收益率计算")
    void testProfitRateCalculation() {
        int[] prices = {2, 5, 7, 8, 10};
        double[] rates = cutRod.calculateProfitRates(prices);
        
        assertEquals(5, rates.length);
        assertEquals(2.0, rates[0], 0.001);  // 2/1 = 2.0
        assertEquals(2.5, rates[1], 0.001);  // 5/2 = 2.5
        assertEquals(2.333, rates[2], 0.001); // 7/3 ≈ 2.333
        assertEquals(2.0, rates[3], 0.001);  // 8/4 = 2.0
        assertEquals(2.0, rates[4], 0.001);  // 10/5 = 2.0
        
        // 测试空数组
        double[] emptyRates = cutRod.calculateProfitRates(new int[0]);
        assertEquals(0, emptyRates.length);
        
        // 测试null输入
        double[] nullRates = cutRod.calculateProfitRates(null);
        assertEquals(0, nullRates.length);
    }
    
    @Test
    @DisplayName("测试价格表验证")
    void testPriceValidation() {
        // 有效价格表
        assertTrue(cutRod.validatePrices(new int[]{1, 2, 3, 4, 5}));
        assertTrue(cutRod.validatePrices(new int[]{0, 1, 2})); // 包含0是有效的
        assertTrue(cutRod.validatePrices(new int[]{})); // 空数组是有效的
        
        // 无效价格表
        assertFalse(cutRod.validatePrices(new int[]{1, -1, 3})); // 包含负数
        assertFalse(cutRod.validatePrices(new int[]{-5})); // 全负数
        assertFalse(cutRod.validatePrices(null)); // null输入
    }
    
    @Test
    @DisplayName("测试性能比较功能")
    void testPerformanceComparison() {
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        
        // 这个测试主要验证性能比较方法不会抛出异常
        // 并且所有算法返回相同的结果
        assertDoesNotThrow(() -> {
            cutRod.performanceComparison(prices, 8);
        });
        
        // 验证三种算法的结果一致性
        int n = 6;
        int result1 = cutRod.cutRod(prices, n);
        int result2 = cutRod.cutRodTopDown(prices, n);
        CutRod.CutResult result3 = cutRod.cutRodWithSolution(prices, n);
        
        assertEquals(result1, result2);
        assertEquals(result2, result3.getMaxRevenue());
    }
    
    @Test
    @DisplayName("测试大规模数据性能")
    void testLargeScalePerformance() {
        // 创建大规模价格表
        int n = 50;
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) {
            prices[i] = (i + 1) * 2 + (int)(Math.random() * 5); // 基础价格 + 随机波动
        }
        
        long startTime, endTime;
        
        // 测试自底向上算法
        startTime = System.nanoTime();
        int result1 = cutRod.cutRod(prices, n);
        endTime = System.nanoTime();
        long bottomUpTime = endTime - startTime;
        
        // 测试自顶向下算法
        startTime = System.nanoTime();
        int result2 = cutRod.cutRodTopDown(prices, n);
        endTime = System.nanoTime();
        long topDownTime = endTime - startTime;
        
        // 测试带方案追踪算法
        startTime = System.nanoTime();
        CutRod.CutResult result3 = cutRod.cutRodWithSolution(prices, n);
        endTime = System.nanoTime();
        long withSolutionTime = endTime - startTime;
        
        // 验证结果一致性
        assertEquals(result1, result2);
        assertEquals(result2, result3.getMaxRevenue());
        
        // 输出性能信息
        System.out.println("=== 大规模性能测试 (n=" + n + ") ===");
        System.out.println("自底向上: " + bottomUpTime + " ns");
        System.out.println("自顶向下: " + topDownTime + " ns");
        System.out.println("带方案追踪: " + withSolutionTime + " ns");
        System.out.println("最优收益: " + result1);
        System.out.println("切割方案: " + result3.getCuts());
        
        // 验证性能在合理范围内（应该都在1秒以内）
        assertTrue(bottomUpTime < 1_000_000_000L);
        assertTrue(topDownTime < 1_000_000_000L);
        assertTrue(withSolutionTime < 1_000_000_000L);
    }
    
    @Test
    @DisplayName("测试切割方案的合理性")
    void testSolutionValidation() {
        int[] prices = {3, 7, 1, 3, 9};
        
        for (int n = 1; n <= 5; n++) {
            CutRod.CutResult result = cutRod.cutRodWithSolution(prices, n);
            
            // 验证切割方案的总长度
            int totalLength = result.getCuts().stream().mapToInt(Integer::intValue).sum();
            assertEquals(n, totalLength, "长度 " + n + " 的切割方案总长度不正确");
            
            // 验证切割方案的总价值
            int totalValue = result.getCuts().stream()
                    .mapToInt(cut -> prices[cut - 1])
                    .sum();
            assertEquals(result.getMaxRevenue(), totalValue, 
                "长度 " + n + " 的切割方案价值计算不正确");
            
            // 验证所有切割段都是有效的（长度在价格表范围内）
            for (int cut : result.getCuts()) {
                assertTrue(cut >= 1 && cut <= prices.length, 
                    "无效的切割长度: " + cut);
            }
            
            System.out.println("长度 " + n + ": 收益=" + result.getMaxRevenue() + 
                             ", 方案=" + result.getCuts());
        }
    }
    
    @Test
    @DisplayName("测试特殊价格模式")
    void testSpecialPricePatterns() {
        // 测试价格呈指数增长的情况
        int[] exponentialPrices = {1, 2, 4, 8, 16, 32};
        
        // 在指数增长的情况下，不切割通常是最优的
        for (int n = 1; n <= 6; n++) {
            int result = cutRod.cutRod(exponentialPrices, n);
            int expectedNocut = exponentialPrices[n - 1];
            assertEquals(expectedNocut, result, 
                "指数价格模式下长度 " + n + " 的结果不符合预期");
        }
        
        // 测试价格平坦的情况（每单位价格相同）
        int[] flatPrices = {3, 6, 9, 12, 15};
        
        for (int n = 1; n <= 5; n++) {
            int result = cutRod.cutRod(flatPrices, n);
            int expected = n * 3; // 每单位价格都是3
            assertEquals(expected, result, 
                "平坦价格模式下长度 " + n + " 的结果不符合预期");
        }
    }
}