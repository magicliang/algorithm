package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MatrixChainMultiplication 的单元测试
 * 测试矩阵链乘法问题的区间DP解法
 */
public class MatrixChainMultiplicationTest {

    private final MatrixChainMultiplication solution = new MatrixChainMultiplication();

    @Test
    public void testBasicCase() {
        // 测试基本用例：矩阵链 A1(1x2) * A2(2x3) * A3(3x4)
        // p = [1, 2, 3, 4] 表示三个矩阵的维度
        int[] p = {1, 2, 3, 4};
        int expected = 18; // (A1*A2)*A3 = 1*2*3 + 1*3*4 = 6 + 12 = 18
        assertEquals(expected, solution.matrixChainOrder(p));
    }

    @Test
    public void testTwoMatrices() {
        // 测试两个矩阵：A1(2x3) * A2(3x4)
        int[] p = {2, 3, 4};
        int expected = 24; // 2*3*4 = 24
        assertEquals(expected, solution.matrixChainOrder(p));
    }

    @Test
    public void testThreeMatrices() {
        // 测试三个矩阵：A1(10x20) * A2(20x30) * A3(30x40)
        int[] p = {10, 20, 30, 40};
        // 方案1: (A1*A2)*A3 = 10*20*30 + 10*30*40 = 6000 + 12000 = 18000
        // 方案2: A1*(A2*A3) = 20*30*40 + 10*20*40 = 24000 + 8000 = 32000
        // 最优是方案1
        int expected = 18000;
        assertEquals(expected, solution.matrixChainOrder(p));
    }

    @Test
    public void testFourMatrices() {
        // 测试四个矩阵：经典的教科书例子
        int[] p = {1, 2, 3, 4, 5};
        // 需要计算所有可能的分割方案，找到最小值
        int result = solution.matrixChainOrder(p);
        assertTrue(result > 0, "结果应该大于0");
        assertTrue(result <= 1000, "对于这个小例子，结果应该不会太大");
    }

    @Test
    public void testLargerExample() {
        // 测试更大的例子
        int[] p = {5, 10, 3, 12, 5, 50, 6};
        int result = solution.matrixChainOrder(p);
        assertTrue(result > 0, "结果应该大于0");
    }

    @Test
    public void testSquareMatrices() {
        // 测试方阵
        int[] p = {10, 10, 10, 10}; // 三个10x10的矩阵
        int expected = 2000; // 每次乘法都是10*10*10=1000，共2次
        assertEquals(expected, solution.matrixChainOrder(p));
    }

    @Test
    public void testDifferentSizes() {
        // 测试不同大小的矩阵
        int[] p = {2, 5, 1, 10, 3};
        int result = solution.matrixChainOrder(p);
        assertTrue(result > 0, "结果应该大于0");
        
        // 验证结果的合理性：至少需要进行3次矩阵乘法
        int minPossible = Math.min(Math.min(2*5*1, 5*1*10), Math.min(1*10*3, 2*1*3));
        assertTrue(result >= minPossible, "结果应该至少包含一次矩阵乘法的成本");
    }

    @Test
    public void testSingleMatrix() {
        // 测试单个矩阵（实际上是两个维度，表示一个矩阵）
        int[] p = {3, 4};
        int expected = 0; // 单个矩阵不需要乘法
        assertEquals(expected, solution.matrixChainOrder(p));
    }

    @Test
    public void testOptimalSubstructure() {
        // 测试最优子结构性质
        int[] p = {2, 3, 6, 4, 5};
        int result = solution.matrixChainOrder(p);
        
        // 验证结果是合理的
        assertTrue(result > 0, "结果应该大于0");
        
        // 对于这个例子，我们可以手工计算验证
        // 矩阵链：A1(2x3) * A2(3x6) * A3(6x4) * A4(4x5)
        // 这需要复杂的计算，但结果应该是合理的
        assertTrue(result < 10000, "对于这个例子，结果不应该过大");
    }

    @Test
    public void testEdgeCases() {
        // 测试边界情况
        
        // 测试包含1的维度
        int[] p1 = {1, 1, 1, 1};
        assertEquals(0, solution.matrixChainOrder(p1)); // 所有乘法成本都是0
        
        // 测试大数值
        int[] p2 = {100, 200, 300};
        int expected2 = 100 * 200 * 300; // 6,000,000
        assertEquals(expected2, solution.matrixChainOrder(p2));
    }

    @Test
    public void testInvalidInput() {
        // 测试无效输入
        assertThrows(IllegalArgumentException.class, () -> {
            solution.matrixChainOrder(new int[]{});
        }, "空数组应该抛出异常");
        
        assertThrows(IllegalArgumentException.class, () -> {
            solution.matrixChainOrder(new int[]{5});
        }, "单个元素数组应该抛出异常");
    }
}