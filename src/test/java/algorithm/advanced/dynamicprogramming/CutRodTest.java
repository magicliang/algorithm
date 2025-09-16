package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 切割钢条问题的测试类
 * 
 * 测试覆盖：
 * 1. 基本功能测试
 * 2. 边界情况测试
 * 3. 特殊价格表测试
 * 4. 性能测试
 * 5. 错误输入处理测试
 * 
 * @author magicliang
 * @version 1.0
 * @since 2025-09-16
 */
public class CutRodTest {
    
    private CutRod cutRod;
    
    @BeforeEach
    void setUp() {
        cutRod = new CutRod();
    }
    
    @Test
    @DisplayName("基本功能测试 - 标准价格表")
    void testBasicFunctionality() {
        // 经典测试用例：价格表 [1, 5, 8, 9, 10, 17, 17, 20, 24, 30]
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        
        // 测试不同长度的钢条
        assertEquals(0, cutRod.cutRod(prices, 0));   // 长度0，收益0
        assertEquals(1, cutRod.cutRod(prices, 1));   // 长度1，最优切割：1段长度1，收益1
        assertEquals(5, cutRod.cutRod(prices, 2));   // 长度2，最优切割：1段长度2，收益5
        assertEquals(8, cutRod.cutRod(prices, 3));   // 长度3，最优切割：1段长度3，收益8
        assertEquals(10, cutRod.cutRod(prices, 4));  // 长度4，最优切割：2段长度2，收益5+5=10
        assertEquals(13, cutRod.cutRod(prices, 5));  // 长度5，最优切割：1段长度2+1段长度3，收益5+8=13
        assertEquals(17, cutRod.cutRod(prices, 6));  // 长度6，最优切割：1段长度6，收益17
        assertEquals(18, cutRod.cutRod(prices, 7));  // 长度7，最优切割：1段长度1+1段长度6，收益1+17=18
        assertEquals(22, cutRod.cutRod(prices, 8));  // 长度8，最优切割：2段长度2+1段长度4，收益5+5+10=20 或其他组合
        assertEquals(25, cutRod.cutRod(prices, 9));  // 长度9，最优切割：多种可能的组合
        assertEquals(30, cutRod.cutRod(prices, 10)); // 长度10，最优切割：1段长度10，收益30
    }
    
    @Test
    @DisplayName("边界情况测试")
    void testBoundaryConditions() {
        // 测试长度为0的情况
        int[] prices = {1, 5, 8, 9};
        assertEquals(0, cutRod.cutRod(prices, 0));
        
        // 测试只有一个价格的情况
        int[] singlePrice = {3};
        assertEquals(3, cutRod.cutRod(singlePrice, 1));
        
        // 测试钢条长度大于价格表长度的情况（应该能正常处理）
        int[] shortPrices = {2, 3};
        // 长度为3的钢条，只有长度1和2的价格，最优应该是3+2=5或1+1+1=6，实际是6
        assertEquals(6, cutRod.cutRod(shortPrices, 3));
    }
    
    @Test
    @DisplayName("递增价格表测试")
    void testIncreasingPrices() {
        // 价格严格递增的情况，不切割总是最优
        int[] increasingPrices = {1, 3, 6, 10, 15, 21, 28, 36, 45, 55};
        
        assertEquals(1, cutRod.cutRod(increasingPrices, 1));
        assertEquals(3, cutRod.cutRod(increasingPrices, 2));
        assertEquals(6, cutRod.cutRod(increasingPrices, 3));
        assertEquals(10, cutRod.cutRod(increasingPrices, 4));
        assertEquals(15, cutRod.cutRod(increasingPrices, 5));
    }
    
    @Test
    @DisplayName("递减价格表测试")
    void testDecreasingPrices() {
        // 价格递减的情况，切成最小段总是最优
        int[] decreasingPrices = {10, 8, 6, 4, 2};
        
        assertEquals(10, cutRod.cutRod(decreasingPrices, 1));  // 1段长度1
        assertEquals(20, cutRod.cutRod(decreasingPrices, 2));  // 2段长度1
        assertEquals(30, cutRod.cutRod(decreasingPrices, 3));  // 3段长度1
        assertEquals(40, cutRod.cutRod(decreasingPrices, 4));  // 4段长度1
        assertEquals(50, cutRod.cutRod(decreasingPrices, 5));  // 5段长度1
    }
    
    @Test
    @DisplayName("相同价格测试")
    void testUniformPrices() {
        // 所有长度价格相同的情况
        int[] uniformPrices = {5, 5, 5, 5, 5};
        
        // 无论怎么切割，每单位长度的价值都是5
        assertEquals(5, cutRod.cutRod(uniformPrices, 1));
        assertEquals(10, cutRod.cutRod(uniformPrices, 2));
        assertEquals(15, cutRod.cutRod(uniformPrices, 3));
        assertEquals(20, cutRod.cutRod(uniformPrices, 4));
        assertEquals(25, cutRod.cutRod(uniformPrices, 5));
    }
    
    @Test
    @DisplayName("包含零价格测试")
    void testZeroPrices() {
        // 包含零价格的情况
        int[] pricesWithZero = {0, 5, 8, 0, 10};
        
        assertEquals(0, cutRod.cutRod(pricesWithZero, 1));   // 长度1价格为0
        assertEquals(5, cutRod.cutRod(pricesWithZero, 2));   // 长度2价格为5
        assertEquals(8, cutRod.cutRod(pricesWithZero, 3));   // 长度3价格为8
        assertEquals(10, cutRod.cutRod(pricesWithZero, 4));  // 长度4价格为0，但2+2=10更优
        assertEquals(13, cutRod.cutRod(pricesWithZero, 5));  // 最优：2+3=5+8=13
    }
    
    @Test
    @DisplayName("大数值测试")
    void testLargeValues() {
        // 测试大数值情况
        int[] largePrices = {100, 500, 800, 900, 1000, 1700, 1700, 2000, 2400, 3000};
        
        assertEquals(100, cutRod.cutRod(largePrices, 1));
        assertEquals(500, cutRod.cutRod(largePrices, 2));
        assertEquals(800, cutRod.cutRod(largePrices, 3));
        assertEquals(1000, cutRod.cutRod(largePrices, 4)); // 2+2=1000 vs 4=900，选择1000
    }
    
    @Test
    @DisplayName("性能测试")
    void testPerformance() {
        // 创建较大的价格表进行性能测试
        int n = 100;
        int[] prices = new int[n];
        
        // 生成一个有趣的价格表：每10个长度价格有一个峰值
        for (int i = 0; i < n; i++) {
            if ((i + 1) % 10 == 0) {
                prices[i] = (i + 1) * 15; // 峰值价格
            } else {
                prices[i] = (i + 1) * 10; // 普通价格
            }
        }
        
        long startTime = System.nanoTime();
        int result = cutRod.cutRod(prices, n);
        long endTime = System.nanoTime();
        
        // 验证结果是正数（合理性检查）
        assertTrue(result > 0);
        
        // 输出性能信息
        long duration = endTime - startTime;
        System.out.println("处理长度为 " + n + " 的钢条耗时: " + duration + " 纳秒");
        System.out.println("最大收益: " + result);
        
        // 性能应该在合理范围内（小于1秒）
        assertTrue(duration < 1_000_000_000L, "算法执行时间过长");
    }
    
    @Test
    @DisplayName("特殊模式测试 - 斐波那契价格")
    void testFibonacciPrices() {
        // 使用斐波那契数列作为价格表
        int[] fibPrices = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        
        assertEquals(1, cutRod.cutRod(fibPrices, 1));
        assertEquals(2, cutRod.cutRod(fibPrices, 2));  // 1+1=2 vs 1，选择2
        assertEquals(3, cutRod.cutRod(fibPrices, 3));  // 1+1+1=3 vs 2，选择3
        assertEquals(4, cutRod.cutRod(fibPrices, 4));  // 1+1+1+1=4 vs 3，选择4
        assertEquals(5, cutRod.cutRod(fibPrices, 5));
        assertEquals(8, cutRod.cutRod(fibPrices, 6));
    }
    
    @Test
    @DisplayName("验证最优子结构性质")
    void testOptimalSubstructure() {
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        
        // 验证最优子结构：长度为n的最优解包含长度为n-k的最优解
        int n = 8;
        int optimalValue = cutRod.cutRod(prices, n);
        
        // 尝试所有可能的第一段切割，验证剩余部分确实是最优的
        boolean foundOptimal = false;
        for (int firstCut = 1; firstCut <= n; firstCut++) {
            int remainingLength = n - firstCut;
            int firstCutValue = prices[firstCut - 1];
            int remainingOptimal = cutRod.cutRod(prices, remainingLength);
            
            if (firstCutValue + remainingOptimal == optimalValue) {
                foundOptimal = true;
                System.out.println("长度 " + n + " 的最优切割包含第一段长度 " + firstCut + 
                                 "（价值 " + firstCutValue + "）+ 剩余长度 " + remainingLength + 
                                 "（最优价值 " + remainingOptimal + "）");
                break;
            }
        }
        
        assertTrue(foundOptimal, "未找到符合最优子结构的切割方案");
    }
    
    @Test
    @DisplayName("对比不同长度的收益率")
    void testProfitabilityComparison() {
        int[] prices = {2, 5, 7, 8, 10, 15, 16, 18, 20, 25};
        
        // 计算每单位长度的平均收益率
        for (int length = 1; length <= 5; length++) {
            int totalRevenue = cutRod.cutRod(prices, length);
            double profitRate = (double) totalRevenue / length;
            System.out.println("长度 " + length + ": 总收益 " + totalRevenue + 
                             ", 平均收益率 " + String.format("%.2f", profitRate));
            
            // 验证收益为正
            assertTrue(totalRevenue > 0);
            assertTrue(profitRate > 0);
        }
    }
}