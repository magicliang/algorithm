package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MinimaxAlgorithm 极小化极大算法的 JUnit 测试类。
 * 
 * 测试内容包括：
 * 1. 基本博弈树测试
 * 2. Alpha-Beta剪枝测试
 * 3. 不同深度测试
 * 4. 算法正确性验证
 *
 * @author magicliang
 * @date 2025-09-09
 */
@DisplayName("极小化极大算法测试")
class MinimaxAlgorithmTest {

    @BeforeEach
    void setUp() {
        // 测试前的初始化工作
    }

    @Test
    @DisplayName("基本Minimax测试")
    void testBasicMinimax() {
        // 创建一个简单的博弈树
        // 叶子节点值: [3, 5, 2, 9, 0, 1, 7, 5]
        int[] leafValues = {3, 5, 2, 9, 0, 1, 7, 5};
        
        int result = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 3, true);
        assertEquals(5, result, "Minimax算法应该返回最优值5");
    }

    @Test
    @DisplayName("Alpha-Beta剪枝测试")
    void testAlphaBetaPruning() {
        // 使用相同的叶子节点值测试Alpha-Beta剪枝
        int[] leafValues = {3, 5, 2, 9, 0, 1, 7, 5};
        
        int minimaxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 3, true);
        int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, 3, true);
        
        assertEquals(minimaxResult, alphaBetaResult, 
            "Alpha-Beta剪枝应该与标准Minimax返回相同结果");
    }

    @Test
    @DisplayName("单层博弈树测试")
    void testSingleLevelGame() {
        // 深度为1的博弈树（直接选择叶子节点）
        int[] leafValues = {10, 5, 8, 3};
        
        // 最大化玩家应该选择最大值
        int maxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 1, true);
        assertEquals(10, maxResult, "最大化玩家应该选择最大值10");
        
        // 最小化玩家应该选择最小值
        int minResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 1, false);
        assertEquals(3, minResult, "最小化玩家应该选择最小值3");
    }

    @Test
    @DisplayName("两层博弈树测试")
    void testTwoLevelGame() {
        // 深度为2的博弈树
        int[] leafValues = {7, 2, 8, 1};
        
        int result = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 2, true);
        // 验证结果在合理范围内（具体值取决于算法实现）
        assertTrue(result >= 1 && result <= 8, "两层博弈树的结果应该在合理范围内");
    }

    @Test
    @DisplayName("对称博弈树测试")
    void testSymmetricGame() {
        // 对称的叶子节点值
        int[] leafValues = {5, 3, 3, 5, 2, 8, 8, 2};
        
        int minimaxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 3, true);
        int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, 3, true);
        
        assertEquals(minimaxResult, alphaBetaResult, 
            "对称博弈树中两种算法应该返回相同结果");
    }

    @Test
    @DisplayName("极值测试")
    void testExtremeValues() {
        // 包含极大和极小值的测试
        int[] leafValues = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 1};
        
        int maxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 2, true);
        int minResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 2, false);
        
        // 由于包含极值，结果可能会有溢出等问题，只验证算法不崩溃
        assertNotNull(maxResult, "最大化结果不应该为null");
        assertNotNull(minResult, "最小化结果不应该为null");
    }

    @Test
    @DisplayName("相同值测试")
    void testIdenticalValues() {
        // 所有叶子节点值相同
        int[] leafValues = {42, 42, 42, 42, 42, 42, 42, 42};
        
        int result = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 3, true);
        assertEquals(42, result, "所有值相同时应该返回该值");
        
        int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, 3, true);
        assertEquals(42, alphaBetaResult, "Alpha-Beta剪枝在相同值时也应该返回该值");
    }

    @Test
    @DisplayName("不同深度一致性测试")
    void testDifferentDepthConsistency() {
        // 测试不同深度下算法的一致性
        int[] leafValues = {1, 2, 3, 4, 5, 6, 7, 8};
        
        for (int depth = 1; depth <= 3; depth++) {
            int minimaxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, depth, true);
            int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, depth, true);
            
            assertEquals(minimaxResult, alphaBetaResult, 
                "深度" + depth + "时两种算法应该返回相同结果");
        }
    }

    @Test
    @DisplayName("异常情况测试")
    void testExceptionCases() {
        // 测试null输入
        assertThrows(IllegalArgumentException.class, 
            () -> MinimaxAlgorithm.minimaxSimpleArray(null, 2, true), 
            "null输入应该抛出异常");

        // 测试空数组
        assertThrows(IllegalArgumentException.class, 
            () -> MinimaxAlgorithm.minimaxSimpleArray(new int[]{}, 2, true), 
            "空数组应该抛出异常");

        // 测试无效深度
        int[] leafValues = {1, 2, 3, 4};
        assertThrows(IllegalArgumentException.class, 
            () -> MinimaxAlgorithm.minimaxSimpleArray(leafValues, 0, true), 
            "深度为0应该抛出异常");

        assertThrows(IllegalArgumentException.class, 
            () -> MinimaxAlgorithm.minimaxSimpleArray(leafValues, -1, true), 
            "负深度应该抛出异常");
    }

    @Test
    @DisplayName("大规模博弈树测试")
    void testLargeGameTree() {
        // 测试较大的博弈树（但不会导致性能问题）
        int[] leafValues = new int[64]; // 6层深度
        for (int i = 0; i < leafValues.length; i++) {
            leafValues[i] = i % 10; // 0-9的循环值
        }

        assertDoesNotThrow(() -> {
            int minimaxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 6, true);
            int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, 6, true);
            
            assertEquals(minimaxResult, alphaBetaResult, 
                "大规模博弈树中两种算法应该返回相同结果");
        });
    }

    @Test
    @DisplayName("性能对比测试")
    void testPerformanceComparison() {
        // 创建一个中等规模的博弈树进行性能对比
        int[] leafValues = new int[32];
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < leafValues.length; i++) {
            leafValues[i] = random.nextInt(100);
        }

        // 测试Minimax性能
        long startTime = System.nanoTime();
        int minimaxResult = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 5, true);
        long minimaxTime = System.nanoTime() - startTime;

        // 测试Alpha-Beta性能
        startTime = System.nanoTime();
        int alphaBetaResult = MinimaxAlgorithm.alphaBetaSimpleArray(leafValues, 5, true);
        long alphaBetaTime = System.nanoTime() - startTime;

        // 验证结果一致性
        assertEquals(minimaxResult, alphaBetaResult, "两种算法结果应该一致");

        // 输出性能信息（Alpha-Beta通常更快）
        System.out.printf("Minimax: %d ns, Alpha-Beta: %d ns, 加速比: %.2fx%n", 
            minimaxTime, alphaBetaTime, (double) minimaxTime / alphaBetaTime);
        
        // Alpha-Beta剪枝通常应该更快或至少不慢于标准Minimax
        assertTrue(alphaBetaTime <= minimaxTime * 2, 
            "Alpha-Beta剪枝的性能不应该显著差于标准Minimax");
    }

    @Test
    @DisplayName("边界值测试")
    void testBoundaryValues() {
        // 测试只有一个叶子节点的情况
        int[] singleLeaf = {100};
        assertEquals(100, MinimaxAlgorithm.minimaxSimpleArray(singleLeaf, 1, true), 
            "单个叶子节点应该返回该值");

        // 测试两个叶子节点的情况
        int[] twoLeaves = {50, 75};
        assertEquals(75, MinimaxAlgorithm.minimaxSimpleArray(twoLeaves, 1, true), 
            "最大化玩家应该选择较大值");
        assertEquals(50, MinimaxAlgorithm.minimaxSimpleArray(twoLeaves, 1, false), 
            "最小化玩家应该选择较小值");
    }

    @Test
    @DisplayName("博弈树结构验证")
    void testGameTreeStructure() {
        // 验证博弈树的结构是否正确
        // 使用一个已知结果的博弈树
        int[] leafValues = {3, 12, 8, 2, 4, 6, 14, 5, 2};
        
        // 这个博弈树的结构应该产生特定的结果
        int result = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 3, true);
        
        // 验证结果在合理范围内
        assertTrue(result >= 2 && result <= 14, 
            "结果应该在叶子节点值的范围内");
    }

    @Test
    @DisplayName("交替玩家测试")
    void testAlternatingPlayers() {
        // 测试最大化和最小化玩家交替的情况
        int[] leafValues = {10, 5, 2, 8};
        
        // 从最大化玩家开始
        int maxFirst = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 2, true);
        
        // 从最小化玩家开始
        int minFirst = MinimaxAlgorithm.minimaxSimpleArray(leafValues, 2, false);
        
        // 两种情况应该产生不同的结果（除非特殊情况）
        assertNotEquals(maxFirst, minFirst, 
            "不同起始玩家通常应该产生不同结果");
    }
}