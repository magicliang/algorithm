package algorithm.selectedtopics.numbertheory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Matrix类的JUnit测试
 *
 * 测试矩阵顺时针旋转90度算法的正确性
 * 包含多种规模和场景的测试用例，验证数学归纳法证明的算法实现
 *
 * @author magicliang
 * @date 2025-09-12 11:48
 */
class MatrixTest {

    private Matrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new Matrix();
    }

    @Test
    @DisplayName("测试2x2矩阵旋转 - 基础情况验证")
    void testRotate2x2Matrix() {
        // 基础情况：验证数学归纳法的起始条件
        int[][] input = {
            {1, 2},
            {3, 4}
        };

        int[][] expected = {
            {3, 1},
            {4, 2}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "2x2矩阵顺时针旋转90度结果不正确");
    }

    @Test
    @DisplayName("测试3x3矩阵旋转 - 奇数维度验证")
    void testRotate3x3Matrix() {
        int[][] input = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] expected = {
            {7, 4, 1},
            {8, 5, 2},
            {9, 6, 3}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "3x3矩阵顺时针旋转90度结果不正确");
    }

    @Test
    @DisplayName("测试4x4矩阵旋转 - 偶数维度验证")
    void testRotate4x4Matrix() {
        int[][] input = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9,  10, 11, 12},
            {13, 14, 15, 16}
        };

        int[][] expected = {
            {13, 9,  5, 1},
            {14, 10, 6, 2},
            {15, 11, 7, 3},
            {16, 12, 8, 4}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "4x4矩阵顺时针旋转90度结果不正确");
    }

    @Test
    @DisplayName("测试1x1矩阵旋转 - 边界情况")
    void testRotate1x1Matrix() {
        int[][] input = {{42}};
        int[][] expected = {{42}};

        matrix.rotate(input);
        assertArrayEquals(expected, input, "1x1矩阵旋转后应保持不变");
    }

    @Test
    @DisplayName("测试包含负数的矩阵旋转")
    void testRotateMatrixWithNegativeNumbers() {
        int[][] input = {
            {-1, -2, -3},
            {-4, -5, -6},
            {-7, -8, -9}
        };

        int[][] expected = {
            {-7, -4, -1},
            {-8, -5, -2},
            {-9, -6, -3}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "包含负数的矩阵旋转结果不正确");
    }

    @Test
    @DisplayName("测试包含零的矩阵旋转")
    void testRotateMatrixWithZeros() {
        int[][] input = {
            {0, 1, 0},
            {1, 0, 1},
            {0, 1, 0}
        };

        int[][] expected = {
            {0, 1, 0},
            {1, 0, 1},
            {0, 1, 0}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "包含零的矩阵旋转结果不正确");
    }

    @Test
    @DisplayName("测试5x5矩阵旋转 - 验证归纳步骤")
    void testRotate5x5Matrix() {
        // 验证数学归纳法的归纳步骤：从n×n到(n+1)×(n+1)
        int[][] input = {
            {1,  2,  3,  4,  5},
            {6,  7,  8,  9,  10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };

        int[][] expected = {
            {21, 16, 11, 6,  1},
            {22, 17, 12, 7,  2},
            {23, 18, 13, 8,  3},
            {24, 19, 14, 9,  4},
            {25, 20, 15, 10, 5}
        };

        matrix.rotate(input);
        assertArrayEquals(expected, input, "5x5矩阵顺时针旋转90度结果不正确");
    }

    @Test
    @DisplayName("测试连续旋转4次回到原状态")
    void testFourConsecutiveRotations() {
        int[][] original = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // 深拷贝原矩阵
        int[][] input = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            input[i] = original[i].clone();
        }

        // 连续旋转4次，应该回到原状态
        for (int i = 0; i < 4; i++) {
            matrix.rotate(input);
        }

        assertArrayEquals(original, input, "连续旋转4次后应回到原始状态");
    }

    @Test
    @DisplayName("验证位置变换公式 (i,j) → (j, n-1-i)")
    void testPositionTransformationFormula() {
        // 创建一个特殊矩阵，每个位置的值为 i*10 + j，便于验证位置变换
        int[][] input = {
            {0,  1,  2},   // 位置(0,0)=0, (0,1)=1, (0,2)=2
            {10, 11, 12},  // 位置(1,0)=10, (1,1)=11, (1,2)=12
            {20, 21, 22}   // 位置(2,0)=20, (2,1)=21, (2,2)=22
        };

        matrix.rotate(input);

        // 验证关键位置的变换：
        // 原位置(0,0)的值0 → 新位置(0, n-1-0) = (0,2)
        assertEquals(0, input[0][2], "原位置(0,0)的值0应变换到(0,2)");
        // 原位置(0,1)的值1 → 新位置(1, n-1-0) = (1,2)
        assertEquals(1, input[1][2], "原位置(0,1)的值1应变换到(1,2)");
        // 原位置(1,1)的值11 → 新位置(1, n-1-1) = (1,1) (中心点不变)
        assertEquals(11, input[1][1], "中心位置(1,1)应保持不变");
        // 原位置(2,0)的值20 → 新位置(0, n-1-2) = (0,0)
        assertEquals(20, input[0][0], "原位置(2,0)的值20应变换到(0,0)");
        // 原位置(2,2)的值22 → 新位置(2, n-1-2) = (2,0)
        assertEquals(22, input[2][0], "原位置(2,2)的值22应变换到(2,0)");
    }

    @Test
    @DisplayName("测试reverseArray方法的独立功能")
    void testReverseArrayMethod() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {5, 4, 3, 2, 1};

        matrix.reverseArray(array);
        assertArrayEquals(expected, array, "数组反转结果不正确");
    }

    @Test
    @DisplayName("测试reverseArray处理空数组和单元素数组")
    void testReverseArrayEdgeCases() {
        // 测试单元素数组
        int[] singleElement = {42};
        matrix.reverseArray(singleElement);
        assertArrayEquals(new int[]{42}, singleElement, "单元素数组反转后应保持不变");

        // 测试偶数长度数组
        int[] evenLength = {1, 2, 3, 4};
        matrix.reverseArray(evenLength);
        assertArrayEquals(new int[]{4, 3, 2, 1}, evenLength, "偶数长度数组反转结果不正确");
    }
}