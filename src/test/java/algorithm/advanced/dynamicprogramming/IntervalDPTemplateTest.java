package algorithm.advanced.dynamicprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * IntervalDPTemplate 的单元测试
 * 由于IntervalDPTemplate是抽象类，我们创建具体实现来测试模板方法
 */
public class IntervalDPTemplateTest {

    /**
     * 测试用的具体实现：简单的区间求和问题
     * dp[i][j] 表示区间[i,j]内所有元素的和
     */
    private static class SimpleIntervalSum extends IntervalDPTemplate {
        
        @Override
        protected int getArraySize(int[] input) {
            return input.length;
        }
        
        @Override
        protected int[] preprocessInput(int[] input) {
            // 不需要预处理，直接返回原数组
            return input.clone();
        }
        
        @Override
        protected void initializeBase(int[][] dp, int[] processedInput) {
            // 初始化单个元素的情况
            for (int i = 0; i < processedInput.length; i++) {
                dp[i][i] = processedInput[i];
            }
        }
        
        @Override
        protected int getStartLength() {
            return 2; // 从长度为2的区间开始
        }
        
        @Override
        protected int calculateCost(int[][] dp, int[] processedInput, int i, int j, int k) {
            // 区间求和：左区间和 + 右区间和
            return dp[i][k] + dp[k + 1][j];
        }
        
        @Override
        protected int updateOptimal(int currentOptimal, int newCost) {
            // 对于求和问题，所有分割方式结果相同，直接返回新值
            return newCost;
        }
        
        @Override
        protected int getFinalAnswer(int[][] dp, int size) {
            return dp[0][size - 1];
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
    public void testSimpleIntervalSum() {
        // 测试简单区间求和
        SimpleIntervalSum solver = new SimpleIntervalSum();
        
        int[] input1 = {1, 2, 3, 4};
        int result1 = solver.solve(input1);
        assertEquals(10, result1); // 1+2+3+4 = 10
        
        int[] input2 = {5};
        int result2 = solver.solve(input2);
        assertEquals(5, result2); // 单个元素
        
        int[] input3 = {1, 1, 1};
        int result3 = solver.solve(input3);
        assertEquals(3, result3); // 1+1+1 = 3
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
    public void testTemplateMethodPattern() {
        // 测试模板方法模式的正确性
        SimpleIntervalSum sumSolver = new SimpleIntervalSum();
        SimpleMatrixChain matrixSolver = new SimpleMatrixChain();
        
        // 相同输入，不同算法应该有不同结果
        int[] input = {2, 3, 4};
        
        int sumResult = sumSolver.solve(input);
        int matrixResult = matrixSolver.solve(input);
        
        assertNotEquals(sumResult, matrixResult, "不同算法应该产生不同结果");
        
        assertEquals(9, sumResult); // 2+3+4 = 9
        assertEquals(24, matrixResult); // 2*3*4 = 24
    }

    @Test
    public void testEdgeCases() {
        SimpleIntervalSum solver = new SimpleIntervalSum();
        
        // 测试单个元素
        int[] singleElement = {42};
        assertEquals(42, solver.solve(singleElement));
        
        // 测试两个元素
        int[] twoElements = {3, 7};
        assertEquals(10, solver.solve(twoElements));
        
        // 测试负数
        int[] negativeNumbers = {-1, -2, -3};
        assertEquals(-6, solver.solve(negativeNumbers));
        
        // 测试混合正负数
        int[] mixedNumbers = {-2, 5, -1, 3};
        assertEquals(5, solver.solve(mixedNumbers)); // -2+5+(-1)+3 = 5
    }

    @Test
    public void testLargerInput() {
        SimpleIntervalSum solver = new SimpleIntervalSum();
        
        // 测试较大的输入
        int[] largeInput = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int expected = 55; // 1+2+...+10 = 55
        assertEquals(expected, solver.solve(largeInput));
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
        SimpleIntervalSum solver = new SimpleIntervalSum();
        
        // 测试空数组
        int[] emptyArray = {};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            solver.solve(emptyArray);
        }, "空数组应该抛出异常");
    }

    @Test
    public void testTemplateMethodInvariant() {
        // 测试模板方法的不变性：相同输入应该产生相同输出
        SimpleIntervalSum solver1 = new SimpleIntervalSum();
        SimpleIntervalSum solver2 = new SimpleIntervalSum();
        
        int[] input = {1, 3, 5, 7, 9};
        
        int result1 = solver1.solve(input);
        int result2 = solver2.solve(input);
        
        assertEquals(result1, result2, "相同算法相同输入应该产生相同结果");
        assertEquals(25, result1); // 1+3+5+7+9 = 25
    }
}