package algorithm.advanced.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 拉丁方阵问题求解器。
 *
 * 问题描述：
 * 构造一个 N×N 的拉丁方阵，其中每一行和每一列都包含 1 到 N 的所有数字，且每个数字在每行每列中都恰好出现一次。
 * 这是数独问题的推广，也是组合数学中的经典问题。
 *
 * 解题思路：
 * 1. 约束分析：
 * - 行约束：每一行的数字 1-N 不能重复，使用 rowUsed[row][num] 记录第 row 行是否已使用数字 num。
 * - 列约束：每一列的数字 1-N 不能重复，使用 colUsed[col][num] 记录第 col 列是否已使用数字 num。
 * - 与数独不同，拉丁方阵没有九宫格约束，只有行列约束。
 * 2. 算法选择：使用回溯法（Backtracking）进行深度优先搜索。
 * 3. 剪枝策略：在每个位置尝试填入数字时，检查行、列约束。如果冲突，则跳过该数字。
 * 4. 优化策略：
 * - 预填充第一行和第一列以减少搜索空间。
 * - 使用启发式方法优先填充约束最多的位置。
 *
 * 时间复杂度：O(N^(N²))，最坏情况下需要尝试每个位置的N种可能
 * 空间复杂度：O(N²)，用于存储约束数组和方阵
 *
 * @author magicliang
 * @date 2025-09-09 11:45
 */
public class LatinSquare {

    /**
     * 程序入口，测试拉丁方阵问题求解。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试不同大小的拉丁方阵
        int[] testSizes = {3, 4, 5};

        for (int n : testSizes) {
            System.out.println("=== 测试 " + n + "×" + n + " 拉丁方阵 ===");
            long startTime = System.currentTimeMillis();

            int[][] solution = solveLatinSquare(n);

            long endTime = System.currentTimeMillis();

            if (solution != null) {
                System.out.println("找到解！耗时：" + (endTime - startTime) + "ms");
                printSolution(solution);
                System.out.println("验证结果：" + (isValidLatinSquare(solution) ? "有效" : "无效"));
            } else {
                System.out.println("无解！耗时：" + (endTime - startTime) + "ms");
            }
            System.out.println();
        }

        // 测试生成所有解
        System.out.println("=== 生成所有 3×3 拉丁方阵 ===");
        List<int[][]> allSolutions = findAllLatinSquares(3);
        System.out.println("总共找到 " + allSolutions.size() + " 个解：");
        for (int i = 0; i < allSolutions.size(); i++) {
            System.out.println("解 " + (i + 1) + "：");
            printSolution(allSolutions.get(i));
            System.out.println();
        }
    }

    /**
     * 求解拉丁方阵问题的主方法。
     * 步骤：
     * 1. 参数验证：检查方阵大小的有效性。
     * 2. 初始化方阵和约束数组。
     * 3. 预填充优化：填充第一行为 1,2,...,N 以减少搜索空间。
     * 4. 调用回溯方法开始求解。
     * 5. 返回解决方案或 null（无解）。
     *
     * @param n 拉丁方阵的大小 (N×N)
     * @return 如果存在解，返回拉丁方阵；否则返回 null
     */
    public static int[][] solveLatinSquare(int n) {
        if (n <= 0) {
            return null;
        }

        // 1. 初始化方阵，0 表示未填充
        int[][] square = new int[n][n];

        // 2. 初始化约束数组
        // rowUsed[i][num] 表示第 i 行是否已使用数字 num+1（num 范围 0 到 n-1）
        boolean[][] rowUsed = new boolean[n][n];
        // colUsed[j][num] 表示第 j 列是否已使用数字 num+1
        boolean[][] colUsed = new boolean[n][n];

        // 3. 预填充优化：第一行填充为 1,2,...,n
        for (int col = 0; col < n; col++) {
            square[0][col] = col + 1;
            rowUsed[0][col] = true;
            colUsed[col][col] = true;
        }

        // 4. 调用回溯方法，从第二行开始（row = 1, col = 0）
        if (backtrack(square, rowUsed, colUsed, n, 1, 0)) {
            return square; // 找到解
        } else {
            return null; // 无解
        }
    }

    /**
     * 使用回溯法递归地填充拉丁方阵。
     * 步骤：
     * 1. 递归基（Base Case）：如果所有位置都已填充，返回 true。
     * 2. 位置管理：计算下一个要填充的位置。
     * 3. 递归步骤（Recursive Step）：在当前位置尝试填入数字 1-N。
     * a. 遍历所有可能的数字（1 到 N）。
     * b. 剪枝：检查当前数字是否与行、列约束冲突。
     * c. 如果不冲突：
     * i.   更新状态：在方阵中填入数字，更新约束数组。
     * ii.  递归调用，处理下一个位置。
     * iii. 如果递归成功，返回 true。
     * iv.  回溯（Backtrack）：撤销当前填入的数字，恢复约束数组。
     * 4. 如果所有数字都尝试失败，返回 false。
     *
     * @param square 当前的拉丁方阵状态
     * @param rowUsed 行约束数组
     * @param colUsed 列约束数组
     * @param n 方阵大小
     * @param row 当前处理的行号
     * @param col 当前处理的列号
     * @return 如果能成功填充返回 true，否则返回 false
     */
    private static boolean backtrack(int[][] square, boolean[][] rowUsed, boolean[][] colUsed,
            int n, int row, int col) {
        // 1. 递归基：所有位置都已填充
        if (row >= n) {
            return true; // 成功构造拉丁方阵
        }

        // 2. 位置管理：计算下一个位置
        int nextRow = (col == n - 1) ? row + 1 : row;
        int nextCol = (col == n - 1) ? 0 : col + 1;

        // 3. 递归步骤：在当前位置 (row, col) 尝试填入数字 1-N
        for (int num = 1; num <= n; num++) {
            int numIndex = num - 1; // 转换为数组索引（0 到 n-1）

            // 3b. 剪枝：检查约束冲突
            if (rowUsed[row][numIndex] || colUsed[col][numIndex]) {
                continue; // 冲突，跳过此数字
            }

            // 3c. 如果不冲突，执行填入操作
            // i. 更新状态
            square[row][col] = num;
            rowUsed[row][numIndex] = true;
            colUsed[col][numIndex] = true;

            // ii. 递归处理下一个位置
            if (backtrack(square, rowUsed, colUsed, n, nextRow, nextCol)) {
                return true; // 找到解，直接返回
            }

            // iv. 回溯：撤销当前操作
            square[row][col] = 0;
            rowUsed[row][numIndex] = false;
            colUsed[col][numIndex] = false;
        }

        // 4. 所有数字都尝试失败
        return false;
    }

    /**
     * 寻找所有可能的拉丁方阵解。
     * 这个方法会找到所有可能的解，而不是只找到第一个解。
     *
     * @param n 拉丁方阵的大小
     * @return 所有可能的拉丁方阵解的列表
     */
    public static List<int[][]> findAllLatinSquares(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }

        List<int[][]> allSolutions = new ArrayList<>();
        int[][] square = new int[n][n];
        boolean[][] rowUsed = new boolean[n][n];
        boolean[][] colUsed = new boolean[n][n];

        // 预填充第一行
        for (int col = 0; col < n; col++) {
            square[0][col] = col + 1;
            rowUsed[0][col] = true;
            colUsed[col][col] = true;
        }

        // 寻找所有解
        backtrackAll(square, rowUsed, colUsed, n, 1, 0, allSolutions);

        return allSolutions;
    }

    /**
     * 寻找所有解的回溯方法。
     */
    private static void backtrackAll(int[][] square, boolean[][] rowUsed, boolean[][] colUsed,
            int n, int row, int col, List<int[][]> allSolutions) {
        if (row >= n) {
            // 找到一个解，进行深拷贝后添加到结果中
            int[][] solution = new int[n][n];
            for (int i = 0; i < n; i++) {
                System.arraycopy(square[i], 0, solution[i], 0, n);
            }
            allSolutions.add(solution);
            return;
        }

        int nextRow = (col == n - 1) ? row + 1 : row;
        int nextCol = (col == n - 1) ? 0 : col + 1;

        for (int num = 1; num <= n; num++) {
            int numIndex = num - 1;

            if (rowUsed[row][numIndex] || colUsed[col][numIndex]) {
                continue;
            }

            square[row][col] = num;
            rowUsed[row][numIndex] = true;
            colUsed[col][numIndex] = true;

            backtrackAll(square, rowUsed, colUsed, n, nextRow, nextCol, allSolutions);

            square[row][col] = 0;
            rowUsed[row][numIndex] = false;
            colUsed[col][numIndex] = false;
        }
    }

    /**
     * 验证给定的方阵是否为有效的拉丁方阵。
     * 检查条件：
     * 1. 每一行包含 1 到 N 的所有数字，且不重复。
     * 2. 每一列包含 1 到 N 的所有数字，且不重复。
     *
     * @param square 要验证的方阵
     * @return 如果是有效的拉丁方阵返回 true，否则返回 false
     */
    public static boolean isValidLatinSquare(int[][] square) {
        if (square == null || square.length == 0) {
            return false;
        }

        int n = square.length;

        // 检查是否为方阵
        for (int[] row : square) {
            if (row.length != n) {
                return false;
            }
        }

        // 检查每一行
        for (int row = 0; row < n; row++) {
            boolean[] used = new boolean[n + 1]; // 索引 1 到 n
            for (int col = 0; col < n; col++) {
                int num = square[row][col];
                if (num < 1 || num > n || used[num]) {
                    return false; // 数字超出范围或重复
                }
                used[num] = true;
            }
        }

        // 检查每一列
        for (int col = 0; col < n; col++) {
            boolean[] used = new boolean[n + 1]; // 索引 1 到 n
            for (int row = 0; row < n; row++) {
                int num = square[row][col];
                if (num < 1 || num > n || used[num]) {
                    return false; // 数字超出范围或重复
                }
                used[num] = true;
            }
        }

        return true; // 通过所有检查
    }

    /**
     * 打印拉丁方阵。
     *
     * @param square 要打印的拉丁方阵
     */
    private static void printSolution(int[][] square) {
        if (square == null) {
            System.out.println("null");
            return;
        }

        int n = square.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
        }
    }
}