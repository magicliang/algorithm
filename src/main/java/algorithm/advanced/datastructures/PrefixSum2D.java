package algorithm.advanced.datastructures;

/**
 * 二维前缀和数据结构
 * 
 * 用于高效计算二维矩阵中任意矩形区域的元素和。
 * 支持O(1)时间复杂度的区域查询，适用于需要频繁进行矩形区域统计的场景。
 * 
 * 核心思想：
 * - 预处理：构建二维前缀和矩阵，prefixSum[i][j] 表示从(0,0)到(i-1,j-1)的矩形区域和
 * - 查询：利用容斥原理计算任意矩形区域和
 * 
 * 应用场景：
 * - 图像处理中的区域统计
 * - 游戏开发中的地图区域计算  
 * - 数据分析中的二维范围查询
 * - 网格路径问题的扩展优化
 * 
 * @author magicliang
 * @version 1.0
 * @since 2025-09-15
 */
public class PrefixSum2D {
    
    private final int[][] prefixSum;
    private final int rows;
    private final int cols;
    
    /**
     * 构造二维前缀和数据结构
     * 
     * 时间复杂度：O(m×n)
     * 空间复杂度：O(m×n)
     * 
     * @param matrix 原始二维矩阵，不能为null或空
     * @throws IllegalArgumentException 如果矩阵无效
     */
    public PrefixSum2D(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }
        
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        
        // prefixSum[i][j] 表示从(0,0)到(i-1,j-1)的矩形区域和
        // 多分配一行一列，便于边界处理
        this.prefixSum = new int[rows + 1][cols + 1];
        
        // 构建二维前缀和矩阵
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                // 容斥原理：当前区域和 = 上方区域和 + 左方区域和 - 重叠区域和 + 当前元素
                prefixSum[i][j] = prefixSum[i-1][j] + prefixSum[i][j-1] 
                                - prefixSum[i-1][j-1] + matrix[i-1][j-1];
            }
        }
    }
    
    /**
     * 查询矩形区域 [(row1,col1), (row2,col2)] 的元素和（包含边界）
     * 
     * 使用容斥原理：
     * 区域和 = 大矩形和 - 上方多余部分 - 左方多余部分 + 重复减去的左上角部分
     * 
     * 时间复杂度：O(1)
     * 
     * @param row1 左上角行索引
     * @param col1 左上角列索引  
     * @param row2 右下角行索引
     * @param col2 右下角列索引
     * @return 矩形区域的元素和
     * @throws IllegalArgumentException 如果索引越界或区域无效
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // 参数验证
        if (row1 < 0 || col1 < 0 || row2 >= rows || col2 >= cols || 
            row1 > row2 || col1 > col2) {
            throw new IllegalArgumentException("Invalid region coordinates");
        }
        
        // 容斥原理计算矩形区域和
        // 注意：prefixSum数组的索引比原矩阵大1
        return prefixSum[row2 + 1][col2 + 1] 
             - prefixSum[row1][col2 + 1] 
             - prefixSum[row2 + 1][col1] 
             + prefixSum[row1][col1];
    }
    
    /**
     * 查询单个元素的值
     * 
     * @param row 行索引
     * @param col 列索引
     * @return 元素值
     */
    public int getElement(int row, int col) {
        return sumRegion(row, col, row, col);
    }
    
    /**
     * 查询整行的和
     * 
     * @param row 行索引
     * @return 该行所有元素的和
     */
    public int sumRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Invalid row index");
        }
        return sumRegion(row, 0, row, cols - 1);
    }
    
    /**
     * 查询整列的和
     * 
     * @param col 列索引
     * @return 该列所有元素的和
     */
    public int sumCol(int col) {
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid column index");
        }
        return sumRegion(0, col, rows - 1, col);
    }
    
    /**
     * 查询整个矩阵的和
     * 
     * @return 矩阵所有元素的和
     */
    public int sumAll() {
        return sumRegion(0, 0, rows - 1, cols - 1);
    }
    
    /**
     * 获取矩阵的行数
     * 
     * @return 行数
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * 获取矩阵的列数
     * 
     * @return 列数
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * 静态工具方法：计算二维矩阵中所有大小为k×k的子矩阵的最大和
     * 
     * 应用场景：图像处理中的滤波器、游戏中的区域效果计算等
     * 
     * @param matrix 原始矩阵
     * @param k 子矩阵边长
     * @return 最大子矩阵和
     */
    public static int maxSubMatrixSum(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || k <= 0) {
            return 0;
        }
        
        PrefixSum2D prefixSum2D = new PrefixSum2D(matrix);
        int maxSum = Integer.MIN_VALUE;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 枚举所有可能的k×k子矩阵
        for (int i = 0; i <= rows - k; i++) {
            for (int j = 0; j <= cols - k; j++) {
                int sum = prefixSum2D.sumRegion(i, j, i + k - 1, j + k - 1);
                maxSum = Math.max(maxSum, sum);
            }
        }
        
        return maxSum;
    }
}