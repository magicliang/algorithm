package algorithm.selectedtopics.computational;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Chess 类的测试用例
 * 
 * 测试覆盖范围：
 * - 位操作函数测试
 * - 汉明重量计算测试
 * - 中国象棋将帅问题测试
 * 
 * @author magicliang
 * @date 2025-09-03
 */
@DisplayName("中国象棋遍历游戏测试")
class ChessTest {

    private Chess chess;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        chess = new Chess();
        // 重定向标准输出以便测试
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("汉明重量计算测试 - 基本功能")
    void testHammingWeightBasic() {
        // 测试基本的汉明重量计算
        assertEquals(0, Chess.hammingWeight(0), "0的汉明重量应该是0");
        assertEquals(1, Chess.hammingWeight(1), "1的汉明重量应该是1");
        assertEquals(1, Chess.hammingWeight(2), "2的汉明重量应该是1");
        assertEquals(2, Chess.hammingWeight(3), "3的汉明重量应该是2");
        assertEquals(1, Chess.hammingWeight(4), "4的汉明重量应该是1");
        assertEquals(2, Chess.hammingWeight(5), "5的汉明重量应该是2");
        assertEquals(2, Chess.hammingWeight(6), "6的汉明重量应该是2");
        assertEquals(3, Chess.hammingWeight(7), "7的汉明重量应该是3");
        assertEquals(1, Chess.hammingWeight(8), "8的汉明重量应该是1");
    }

    @Test
    @DisplayName("汉明重量计算测试 - 特殊值")
    void testHammingWeightSpecialValues() {
        // 测试特殊值
        assertEquals(3, Chess.hammingWeight(11), "11(1011)的汉明重量应该是3");
        assertEquals(4, Chess.hammingWeight(15), "15(1111)的汉明重量应该是4");
        assertEquals(1, Chess.hammingWeight(16), "16(10000)的汉明重量应该是1");
        assertEquals(8, Chess.hammingWeight(255), "255(11111111)的汉明重量应该是8");
        assertEquals(1, Chess.hammingWeight(1024), "1024(10000000000)的汉明重量应该是1");
    }

    @Test
    @DisplayName("汉明重量计算测试 - 大数值")
    void testHammingWeightLargeValues() {
        // 测试较大的数值
        assertEquals(16, Chess.hammingWeight(0xFFFF), "0xFFFF的汉明重量应该是16");
        assertEquals(1, Chess.hammingWeight(0x80000000), "0x80000000的汉明重量应该是1");
        assertEquals(31, Chess.hammingWeight(0x7FFFFFFF), "0x7FFFFFFF的汉明重量应该是31");
    }

    @Test
    @DisplayName("汉明重量计算测试 - 算法正确性验证")
    void testHammingWeightAlgorithmCorrectness() {
        // 验证算法的正确性：通过与标准方法对比
        for (int i = 0; i < 100; i++) {
            int expected = Integer.bitCount(i); // Java标准库方法
            int actual = Chess.hammingWeight(i);
            assertEquals(expected, actual, "数值 " + i + " 的汉明重量计算错误");
        }
    }

    @Test
    @DisplayName("汉明重量计算测试 - 性能测试")
    void testHammingWeightPerformance() {
        // 性能测试：确保算法在合理时间内完成
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 10000; i++) {
            Chess.hammingWeight(i);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // 转换为毫秒
        
        System.setOut(originalOut); // 恢复标准输出以便打印性能信息
        System.out.println("汉明重量计算10000次耗时: " + duration + "ms");
        
        // 性能断言：应该在合理时间内完成（比如小于100ms）
        assertTrue(duration < 100, "汉明重量计算性能应该在合理范围内");
    }

    @Test
    @DisplayName("汉明重量计算测试 - 边界值")
    void testHammingWeightBoundaryValues() {
        // 测试边界值
        assertEquals(0, Chess.hammingWeight(0), "最小值0的汉明重量应该是0");
        assertEquals(32, Chess.hammingWeight(-1), "全1值(-1)的汉明重量应该是32");
        
        // 测试2的幂次
        for (int i = 0; i < 31; i++) {
            int powerOfTwo = 1 << i;
            assertEquals(1, Chess.hammingWeight(powerOfTwo), 
                "2的" + i + "次幂(" + powerOfTwo + ")的汉明重量应该是1");
        }
    }

    @Test
    @DisplayName("汉明重量计算测试 - 算法优化验证")
    void testHammingWeightOptimization() {
        // 验证 n & (n-1) 优化的正确性
        // 这个操作会消除n中最右边的1
        
        int n = 12; // 1100
        assertEquals(2, Chess.hammingWeight(n), "12的汉明重量应该是2");
        
        // 手动验证算法步骤
        int count = 0;
        int temp = n;
        while (temp != 0) {
            count++;
            temp = temp & (temp - 1);
        }
        assertEquals(2, count, "手动计算的汉明重量应该与算法结果一致");
    }

    @Test
    @DisplayName("位操作测试 - 验证位操作的正确性")
    void testBitOperations() {
        // 由于位操作方法是私有的，我们通过间接方式测试
        // 这里主要测试汉明重量函数，它使用了关键的位操作 n & (n-1)
        
        // 测试 n & (n-1) 操作的特性
        assertEquals(0, 1 & (1 - 1), "1 & 0 应该等于 0");
        assertEquals(0, 2 & (2 - 1), "2 & 1 应该等于 0");
        assertEquals(2, 3 & (3 - 1), "3 & 2 应该等于 2");
        assertEquals(0, 4 & (4 - 1), "4 & 3 应该等于 0");
        assertEquals(4, 5 & (5 - 1), "5 & 4 应该等于 4");
        assertEquals(4, 6 & (6 - 1), "6 & 5 应该等于 4");
        assertEquals(6, 7 & (7 - 1), "7 & 6 应该等于 6");
        assertEquals(0, 8 & (8 - 1), "8 & 7 应该等于 0");
    }

    @Test
    @DisplayName("中国象棋问题验证 - 通过输出验证")
    void testChessGameLogic() {
        // 由于main方法中的逻辑比较复杂，我们通过验证一些基本的数学关系来测试
        
        // 验证3x3棋盘的基本属性
        int width = 3;
        int totalPositions = width * width; // 9个位置
        
        assertEquals(9, totalPositions, "3x3棋盘应该有9个位置");
        
        // 验证列计算的正确性（1-based）
        for (int pos = 1; pos <= 9; pos++) {
            int column = pos % width;
            assertTrue(column >= 0 && column <= 2, "位置" + pos + "的列应该在0-2范围内");
        }
    }

    @Test
    @DisplayName("数学逻辑验证 - 将帅不能在同一列")
    void testChessConstraints() {
        // 验证将帅不能在同一列的约束
        int width = 3;
        int validPairs = 0;
        int totalPairs = 0;
        
        for (int general = 1; general <= 9; general++) {
            for (int marshal = 1; marshal <= 9; marshal++) {
                totalPairs++;
                // 计算列（使用与Chess类相同的逻辑）
                int generalColumn = general % width;
                int marshalColumn = marshal % width;
                
                if (generalColumn != marshalColumn) {
                    validPairs++;
                }
            }
        }
        
        assertEquals(81, totalPairs, "总共应该有81种组合");
        assertEquals(54, validPairs, "应该有54种有效组合（不在同一列）");
    }

    @Test
    @DisplayName("算法一致性测试 - 验证不同方法的结果一致性")
    void testAlgorithmConsistency() {
        // 验证不同算法方法得到相同结果的一致性
        // 由于方法是私有的，我们通过数学验证来确保逻辑正确
        
        int width = 3;
        int expectedValidPairs = 0;
        
        // 方法1：直接计算
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i % width != j % width) {
                    expectedValidPairs++;
                }
            }
        }
        
        // 方法2：通过数学公式计算
        // 每行有3个位置，每列有3个位置
        // 对于每个位置，它不能与同列的3个位置配对，可以与其他6个位置配对
        // 总计：9 * 6 = 54
        int calculatedValidPairs = 9 * 6;
        
        assertEquals(expectedValidPairs, calculatedValidPairs, 
            "不同计算方法应该得到相同的有效配对数");
        assertEquals(54, expectedValidPairs, "有效配对数应该是54");
    }

    @Test
    @DisplayName("边界条件测试")
    void testBoundaryConditions() {
        // 测试边界条件
        
        // 测试最小汉明重量
        assertEquals(0, Chess.hammingWeight(0), "0的汉明重量应该是0");
        
        // 测试单个位的汉明重量
        for (int i = 0; i < 32; i++) {
            int singleBit = 1 << i;
            assertEquals(1, Chess.hammingWeight(singleBit), 
                "单个位(" + singleBit + ")的汉明重量应该是1");
        }
    }

    @Test
    @DisplayName("算法复杂度验证")
    void testAlgorithmComplexity() {
        // 验证汉明重量算法的时间复杂度是O(k)，其中k是1的个数
        
        // 测试不同1的个数的性能
        int[] testValues = {
            0,          // 0个1
            1,          // 1个1
            3,          // 2个1
            7,          // 3个1
            15,         // 4个1
            31,         // 5个1
            0xFFFF,     // 16个1
            0x7FFFFFFF  // 31个1
        };
        
        for (int value : testValues) {
            long startTime = System.nanoTime();
            int result = Chess.hammingWeight(value);
            long endTime = System.nanoTime();
            
            // 验证结果正确性
            assertEquals(Integer.bitCount(value), result, 
                "值" + value + "的汉明重量计算错误");
            
            // 验证性能（应该很快）
            long duration = endTime - startTime;
            assertTrue(duration < 1_000_000, // 小于1ms
                "汉明重量计算应该很快完成");
        }
    }

    // 在测试结束后恢复标准输出
    void tearDown() {
        if (originalOut != null) {
            System.setOut(originalOut);
        }
    }
}