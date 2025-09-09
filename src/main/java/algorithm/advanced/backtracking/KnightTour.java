package algorithm.advanced.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 骑士巡游问题求解器。
 *
 * 问题描述：
 * 在一个 N×N 的国际象棋棋盘上，放置一个骑士（马），要求骑士按照象棋中马的走法（日字形），
 * 访问棋盘上的每一个格子恰好一次，最终形成一条哈密顿路径。
 *
 * 解题思路：
 * 1. 约束分析：
 * - 位置约束：骑士只能按照马的走法移动（8个可能方向）。
 * - 访问约束：每个格子只能访问一次，使用 visited[row][col] 记录格子是否已被访问。
 * - 路径约束：需要访问所有 N×N 个格子，使用 moveCount 记录当前已访问的格子数。
 * 2. 算法选择：使用回溯法（Backtracking）进行深度优先搜索。
 * 3. 剪枝策略：
 * - 边界剪枝：检查下一步是否越界。
 * - 访问剪枝：检查下一步的格子是否已被访问。
 * 4. 优化策略：Warnsdorff启发式 - 优先选择可达格子数最少的下一步（减少搜索空间）。
 *
 * 时间复杂度：O(8^(N²))，最坏情况下需要尝试每个位置的8个方向
 * 空间复杂度：O(N²)，用于存储访问状态和路径
 *
 * @author magicliang
 * @date 2025-09-09 11:40
 */
public class KnightTour {

    // 骑士的8个可能移动方向（马走日字）
    private static final int[] MOVE_X = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] MOVE_Y = {1, 2, 2, 1, -1, -2, -2, -1};

    /**
     * 程序入口，测试骑士巡游问题求解。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试不同大小的棋盘
        int[] testSizes = {5, 6, 8};

        for (int n : testSizes) {
            System.out.println("=== 测试 " + n + "×" + n + " 棋盘 ===");
            long startTime = System.currentTimeMillis();

            int[][] solution = solveKnightTour(n, 0, 0);

            long endTime = System.currentTimeMillis();

            if (solution != null) {
                System.out.println("找到解！耗时：" + (endTime - startTime) + "ms");
                printSolution(solution);
            } else {
                System.out.println("无解！耗时：" + (endTime - startTime) + "ms");
            }
            System.out.println();
        }
    }

    /**
     * 求解骑士巡游问题的主方法。
     * 步骤：
     * 1. 参数验证：检查棋盘大小和起始位置的有效性。
     * 2. 初始化访问状态数组和路径记录数组。
     * 3. 标记起始位置为已访问，并记录为第1步。
     * 4. 调用回溯方法开始求解。
     * 5. 返回解决方案或 null（无解）。
     *
     * @param n 棋盘大小 (N×N)
     * @param startX 起始位置的行坐标
     * @param startY 起始位置的列坐标
     * @return 如果存在解，返回路径矩阵（每个位置记录访问顺序）；否则返回 null
     */
    public static int[][] solveKnightTour(int n, int startX, int startY) {
        if (n <= 0 || startX < 0 || startX >= n || startY < 0 || startY >= n) {
            return null;
        }

        // 1. 初始化访问状态数组
        boolean[][] visited = new boolean[n][n];

        // 2. 初始化路径记录数组，solution[i][j] 表示位置 (i,j) 是第几步访问的
        int[][] solution = new int[n][n];

        // 3. 标记起始位置
        visited[startX][startY] = true;
        solution[startX][startY] = 1; // 第1步

        // 4. 调用回溯方法，从第2步开始（moveCount = 2）
        if (backtrack(n, startX, startY, 2, visited, solution)) {
            return solution; // 找到解
        } else {
            return null; // 无解
        }
    }

    /**
     * 使用回溯法递归地寻找骑士巡游路径。
     * 步骤：
     * 1. 递归基（Base Case）：如果已访问的格子数等于总格子数，返回 true。
     * 2. 递归步骤（Recursive Step）：尝试骑士的8个可能移动。
     * a. 遍历8个移动方向。
     * b. 计算下一步的位置坐标。
     * c. 剪枝：检查下一步是否有效（不越界且未访问）。
     * d. 如果有效：
     * i.   更新状态：标记新位置为已访问，记录步数。
     * ii.  递归调用，继续寻找下一步。
     * iii. 如果递归成功，返回 true。
     * iv.  回溯（Backtrack）：撤销当前步的标记。
     * 3. 如果所有方向都尝试失败，返回 false。
     *
     * @param n 棋盘大小
     * @param currentX 当前骑士的行坐标
     * @param currentY 当前骑士的列坐标
     * @param moveCount 当前是第几步（从1开始计数）
     * @param visited 访问状态数组
     * @param solution 路径记录数组
     * @return 如果能完成巡游返回 true，否则返回 false
     */
    private static boolean backtrack(int n, int currentX, int currentY, int moveCount,
            boolean[][] visited, int[][] solution) {
        // 1. 递归基：所有格子都已访问
        if (moveCount > n * n) {
            return true; // 成功完成巡游
        }

        // 2. 递归步骤：尝试8个可能的移动方向
        for (int i = 0; i < 8; i++) {
            // 2b. 计算下一步位置
            int nextX = currentX + MOVE_X[i];
            int nextY = currentY + MOVE_Y[i];

            // 2c. 剪枝：检查下一步是否有效
            if (isValidMove(n, nextX, nextY, visited)) {
                // 2d. 如果有效，执行移动操作
                // i. 更新状态
                visited[nextX][nextY] = true;
                solution[nextX][nextY] = moveCount;

                // ii. 递归寻找下一步
                if (backtrack(n, nextX, nextY, moveCount + 1, visited, solution)) {
                    return true; // 找到解，直接返回
                }

                // iv. 回溯：撤销当前移动
                visited[nextX][nextY] = false;
                solution[nextX][nextY] = 0;
            }
        }

        // 3. 所有方向都尝试失败
        return false;
    }

    /**
     * 检查骑士的下一步移动是否有效。
     * 有效条件：
     * 1. 不越界：坐标在 [0, n) 范围内。
     * 2. 未访问：目标格子尚未被访问过。
     *
     * @param n 棋盘大小
     * @param x 目标位置的行坐标
     * @param y 目标位置的列坐标
     * @param visited 访问状态数组
     * @return 如果移动有效返回 true，否则返回 false
     */
    private static boolean isValidMove(int n, int x, int y, boolean[][] visited) {
        return x >= 0 && x < n && y >= 0 && y < n && !visited[x][y];
    }

    /**
     * 使用Warnsdorff启发式优化的骑士巡游求解器。
     * Warnsdorff规则：总是选择可达位置数最少的下一步，这样可以显著减少搜索空间。
     *
     * @param n 棋盘大小
     * @param startX 起始位置的行坐标
     * @param startY 起始位置的列坐标
     * @return 如果存在解，返回路径矩阵；否则返回 null
     */
    public static int[][] solveKnightTourWithHeuristic(int n, int startX, int startY) {
        if (n <= 0 || startX < 0 || startX >= n || startY < 0 || startY >= n) {
            return null;
        }

        boolean[][] visited = new boolean[n][n];
        int[][] solution = new int[n][n];

        visited[startX][startY] = true;
        solution[startX][startY] = 1;

        if (backtrackWithHeuristic(n, startX, startY, 2, visited, solution)) {
            return solution;
        } else {
            return null;
        }
    }

    /**
     * 使用Warnsdorff启发式的回溯方法。
     */
    private static boolean backtrackWithHeuristic(int n, int currentX, int currentY, int moveCount,
            boolean[][] visited, int[][] solution) {
        if (moveCount > n * n) {
            return true;
        }

        // 获取所有有效的下一步，并按可达位置数排序
        List<Move> validMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nextX = currentX + MOVE_X[i];
            int nextY = currentY + MOVE_Y[i];

            if (isValidMove(n, nextX, nextY, visited)) {
                int accessibility = getAccessibility(n, nextX, nextY, visited);
                validMoves.add(new Move(nextX, nextY, accessibility));
            }
        }

        // 按可达位置数升序排序（Warnsdorff启发式）
        validMoves.sort((a, b) -> Integer.compare(a.accessibility, b.accessibility));

        // 尝试每个有效移动
        for (Move move : validMoves) {
            visited[move.x][move.y] = true;
            solution[move.x][move.y] = moveCount;

            if (backtrackWithHeuristic(n, move.x, move.y, moveCount + 1, visited, solution)) {
                return true;
            }

            visited[move.x][move.y] = false;
            solution[move.x][move.y] = 0;
        }

        return false;
    }

    /**
     * 计算从指定位置可以到达的未访问位置数量。
     */
    private static int getAccessibility(int n, int x, int y, boolean[][] visited) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nextX = x + MOVE_X[i];
            int nextY = y + MOVE_Y[i];
            if (isValidMove(n, nextX, nextY, visited)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 打印骑士巡游的解。
     *
     * @param solution 路径矩阵，每个位置记录访问顺序
     */
    private static void printSolution(int[][] solution) {
        int n = solution.length;
        System.out.println("骑士巡游路径（数字表示访问顺序）：");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%3d ", solution[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 表示一个移动的内部类。
     */
    private static class Move {

        int x, y;
        int accessibility; // 可达位置数

        Move(int x, int y, int accessibility) {
            this.x = x;
            this.y = y;
            this.accessibility = accessibility;
        }
    }
}