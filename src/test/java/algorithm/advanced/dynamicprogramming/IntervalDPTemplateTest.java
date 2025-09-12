package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * IntervalDPTemplate 的单元测试
 * 由于IntervalDPTemplate是抽象类，我们创建具体实现来测试模板方法
 */
public class IntervalDPTemplateTest {

    /**
     * 测试用的具体实现：矩阵链乘法问题（简化版）
     */
    private static class SimpleMatrixChain extends IntervalDPTemplate {
        
        @Override
        protected int getArraySize(int[] input) {
            return input.length;
        }
        
        @Override
        protected int[] preprocessInput(int[] input) {
            return input.clone();
        }
        
        @Override
        protected void initializeBase(int[][] dp, int[] processedInput) {
            // 单个矩阵不需要乘法，成本为0
            for (int i = 0; i < processedInput.length - 1; i++) {
                dp[i][i] = 0;
            }
        }
        
        @Override
        protected int getStartLength() {
            return 2;
        }
        
        @Override
        protected int calculateCost(int[][] dp, int[] processedInput, int i, int j, int k) {
            // 矩阵链乘法的成本计算
            return dp[i][k] + dp[k + 1][j] + processedInput[i] * processedInput[k + 1] * processedInput[j + 1];
        }
        
        @Override
        protected int updateOptimal(int currentOptimal, int newCost) {
            return Math.min(currentOptimal, newCost);
        }
        
        @Override
        protected int getFinalAnswer(int[][] dp, int size) {
            return dp[0][size - 2]; // 注意矩阵链的索引
        }
        
        @Override
        protected int getMaxLength(int size) {
            return size - 1;
        }
        
        @Override
        protected boolean isValidInterval(int i, int j, int size) {
            return j < size - 1; // 矩阵链的特殊处理
        }
        
        @Override
        protected int getMinSplitPoint(int i) {
            return i;
        }
        
        @Override
        protected int getMaxSplitPoint(int j) {
            return j;
        }
        
        @Override
        protected boolean isValidSplitPoint(int i, int j, int k) {
            return k >= i && k < j;
        }
    }

    @Test
    public void testSimpleMatrixChain() {
        // 测试简化版矩阵链乘法
        SimpleMatrixChain solver = new SimpleMatrixChain();
        
        // 两个矩阵：A1(2x3) * A2(3x4)
        int[] input1 = {2, 3, 4};
        int result1 = solver.solve(input1);
        assertEquals(24, result1); // 2*3*4 = 24
        
        // 三个矩阵：A1(1x2) * A2(2x3) * A3(3x4)
        int[] input2 = {1, 2, 3, 4};
        int result2 = solver.solve(input2);
        assertEquals(18, result2); // 最优分割的成本
    }

    @Test
    public void testMatrixChainEdgeCases() {
        SimpleMatrixChain solver = new SimpleMatrixChain();
        
        // 测试最小情况：两个矩阵
        int[] twoMatrices = {10, 20, 30};
        assertEquals(6000, solver.solve(twoMatrices)); // 10*20*30
        
        // 测试方阵
        int[] squareMatrices = {5, 5, 5, 5};
        assertEquals(250, solver.solve(squareMatrices)); // 最优分割
    }

    @Test
    public void testInvalidInputHandling() {
        SimpleMatrixChain solver = new SimpleMatrixChain();
        
        // 测试空数组
        int[] emptyArray = {};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            solver.solve(emptyArray);
        }, "空数组应该抛出异常");
    }

    @Test
    public void testTemplateMethodInvariant() {
        // 测试模板方法的不变性：相同输入应该产生相同输出
        SimpleMatrixChain solver1 = new SimpleMatrixChain();
        SimpleMatrixChain solver2 = new SimpleMatrixChain();
        
        int[] input = {1, 3, 5, 7, 9};
        
        int result1 = solver1.solve(input);
        int result2 = solver2.solve(input);
        
        assertEquals(result1, result2, "相同算法相同输入应该产生相同结果");
        assertTrue(result1 >= 0, "矩阵链乘法成本应该非负");
    }
}