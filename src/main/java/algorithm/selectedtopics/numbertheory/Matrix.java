package algorithm.selectedtopics.numbertheory;


import java.util.ArrayList;
import java.util.List;

/**
 * project name: algorithm
 *
 * description: 矩阵顺时针旋转90度算法 - 两步法
 *
 * 核心思想：对角线翻转可以让我们先把行完整地变成列，但是起始节点的位置没有变，
 * 只要行做中心反转就可以得到目标列。而且所有目标列的移动都一样，这可以用数学归纳法来证明：
 *
 * 数学归纳法证明：
 * 1. 基础情况：从一个2×2矩阵开始
 * 原矩阵: [a b]    对角线翻转后: [a c]    每行反转后: [c a]
 * [c d]                   [b d]               [d b]
 * 这正是原矩阵顺时针旋转90度的结果
 *
 * 2. 归纳假设：假设对于n×n矩阵，两步法能正确实现顺时针旋转90度
 *
 * 3. 归纳步骤：对于(n+1)×(n+1)矩阵
 * - 可以分解为：n×n的内部矩阵 + 最外层的边界元素
 * - 对角线翻转：将第i行第j列的元素移动到第j行第i列
 * - 每行反转：将每行的元素左右对称交换
 * - 组合效果：原来位置(i,j)的元素最终到达位置(j, n-1-i)，这正是顺时针旋转90度的变换公式
 *
 * 算法步骤：
 * Step 1: 沿主对角线翻转（转置矩阵）- 将matrix[i][j]与matrix[j][i]交换
 * Step 2: 水平翻转每一行 - 将每行元素左右对称交换
 *
 * 时间复杂度：O(n²)，空间复杂度：O(1)
 *
 * @author magicliang
 *
 *         date: 2025-09-12 11:39
 */
public class Matrix {

    /**
     * 螺旋遍历矩阵 - 按顺时针方向遍历矩阵的所有元素
     * 
     * 算法思路：
     * 1. 维护四个边界：上边界(top)、下边界(bottom)、左边界(left)、右边界(right)
     * 2. 按照 右→下→左→上 的顺序遍历，每完成一个方向就收缩对应边界
     * 3. 当边界交叉时停止遍历
     * 
     * 时间复杂度：O(m×n)，空间复杂度：O(1)（不计算结果数组）
     * 
     * @param matrix 输入的二维矩阵
     * @return 螺旋遍历的结果列表
     */
    List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }

        // 定义四个边界
        int top = 0;                    // 上边界
        int bottom = matrix.length - 1; // 下边界  
        int left = 0;                   // 左边界
        int right = matrix[0].length - 1; // 右边界

        while (top <= bottom && left <= right) {
            // 1. 从左到右遍历上边界
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++; // 上边界下移

            // 2. 从上到下遍历右边界
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--; // 右边界左移

            // 3. 从右到左遍历下边界（需要检查是否还有行）
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--; // 下边界上移
            }

            // 4. 从下到上遍历左边界（需要检查是否还有列）
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++; // 左边界右移
            }
        }

        return result;
    }

    /**
     * 矩阵顺时针旋转90度的主方法
     *
     * 算法实现基于数学归纳法证明的两步法：
     * Step 1: 沿主对角线翻转（转置） - 将行变成列，保持元素间相对位置
     * Step 2: 水平翻转每一行 - 调整列内元素顺序，完成最终的旋转变换
     *
     * @param matrix 待旋转的n×n矩阵，原地修改
     */
    public void rotate(int[][] matrix) {

        // Step 1: 沿主对角线翻转（转置矩阵）
        // 数学原理：将matrix[i][j]与matrix[j][i]交换，实现行列互换
        // 注意：只遍历上三角部分（j > i），避免重复交换
        for (int i = 0; i < matrix.length; i++) {// 逐行遍历
            for (int j = i + 1; j < matrix[i].length; j++) { // 只处理上三角部分
                // 交换matrix[i][j]和matrix[j][i]，完成转置
                int temp = matrix[j][i];
                matrix[j][i] = matrix[i][j];
                matrix[i][j] = temp;
            }
        }

        // Step 2: 水平翻转每一行
        // 数学原理：经过转置后，每行反转可以将列内元素调整到正确的旋转位置
        // 组合效果：原位置(i,j) → 转置后(j,i) → 行反转后(j, n-1-i)，完成顺时针90度旋转
        for (int i = 0; i < matrix.length; i++) {
            reverseArray(matrix[i]); // 反转第i行
        }
    }

    /**
     * 矩阵逆时针旋转90度的方法
     *
     * 算法实现基于副对角线翻转的两步法：
     * Step 1: 沿副对角线翻转 - 将matrix[i][j]与matrix[n-1-j][n-1-i]交换
     * Step 2: 水平翻转每一行 - 调整行内元素顺序，完成最终的逆时针旋转变换
     *
     * 数学原理：
     * - 副对角线翻转：原位置(i,j) → (n-1-j, n-1-i)
     * - 行反转：(n-1-j, n-1-i) → (n-1-j, i)
     * - 组合效果：(i,j) → (n-1-j, i)，这正是逆时针旋转90度的变换公式
     *
     * @param matrix 待旋转的n×n矩阵，原地修改
     */
    public void reverseRotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: 沿副对角线翻转（从右上到左下的对角线）
        // 只遍历副对角线上方的元素，避免重复交换
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                // 交换matrix[i][j]和matrix[n-1-j][n-1-i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
                matrix[n - 1 - j][n - 1 - i] = temp;
            }
        }

        // Step 2: 水平翻转每一行
        // 完成副对角线翻转后，每行反转得到最终的逆时针旋转结果
        for (int i = 0; i < matrix.length; i++) {
            reverseArray(matrix[i]); // 反转第i行
        }
    }

    /**
     * 数组原地反转的辅助方法
     *
     * 使用双指针技术实现数组的水平翻转：
     * - 左指针从数组开始位置向右移动
     * - 右指针从数组结束位置向左移动
     * - 交换两指针指向的元素，直到指针相遇
     *
     * 在矩阵旋转中的作用：完成转置后每行的水平翻转，
     * 将转置得到的"列"调整为最终旋转后的正确顺序
     *
     * @param array 待反转的一维数组，原地修改
     */
    void reverseArray(int[] array) {
        int l = 0; // 左指针，指向数组开始
        int r = array.length - 1; // 右指针，指向数组结束

        // 双指针向中间靠拢，交换对称位置的元素
        while (l < r) {
            // 交换array[l]和array[r]
            int temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            // 指针向中间移动
            l++; // 左指针右移
            r--; // 右指针左移
        }
    }
}