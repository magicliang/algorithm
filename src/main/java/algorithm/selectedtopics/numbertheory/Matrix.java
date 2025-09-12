package algorithm.selectedtopics.numbertheory;


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
 *    原矩阵: [a b]    对角线翻转后: [a c]    每行反转后: [c a]
 *           [c d]                   [b d]               [d b]
 *    这正是原矩阵顺时针旋转90度的结果
 * 
 * 2. 归纳假设：假设对于n×n矩阵，两步法能正确实现顺时针旋转90度
 * 
 * 3. 归纳步骤：对于(n+1)×(n+1)矩阵
 *    - 可以分解为：n×n的内部矩阵 + 最外层的边界元素
 *    - 对角线翻转：将第i行第j列的元素移动到第j行第i列
 *    - 每行反转：将每行的元素左右对称交换
 *    - 组合效果：原来位置(i,j)的元素最终到达位置(j, n-1-i)，这正是顺时针旋转90度的变换公式
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
            for (int j = i + 1; j <matrix[i].length; j++) { // 只处理上三角部分
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
    public void rerotate(int[][] matrix) {
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