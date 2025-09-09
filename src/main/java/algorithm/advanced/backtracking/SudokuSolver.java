package algorithm.advanced.backtracking;

/**
 * 数独求解器。
 *
 * 问题描述：
 * 在一个 9×9 的数独棋盘上填入数字 1-9，使得每一行、每一列、每一个 3×3 的九宫格内都包含数字 1-9，且不重复。
 *
 * 解题思路：
 * 1. 约束分析：
 *    - 行约束：每一行的数字 1-9 不能重复，使用 rows[row][num] 记录第 row 行是否已使用数字 num。
 *    - 列约束：每一列的数字 1-9 不能重复，使用 cols[col][num] 记录第 col 列是否已使用数字 num。
 *    - 九宫格约束：每个 3×3 九宫格的数字 1-9 不能重复，使用 boxes[boxIndex][num] 记录第 boxIndex 个九宫格是否已使用数字 num。
 *      九宫格索引计算：boxIndex = (row / 3) * 3 + (col / 3)
 * 2. 算法选择：使用回溯法（Backtracking）进行深度优先搜索。
 * 3. 剪枝策略：在每个空位尝试填入数字时，检查行、列、九宫格约束。如果冲突，则跳过该数字，实现剪枝。
 * 4. 优化策略：只对空位（'.'）进行填充，已有数字的位置跳过。
 *
 * 时间复杂度：O(9^(空位数量))，最坏情况下为 O(9^81)
 * 空间复杂度：O(1)，约束数组大小固定
 *
 * @author magicliang
 * @date 2025-09-09 11:30
 */
public class SudokuSolver {

    /**
     * 程序入口，测试数独求解。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试用例：标准数独题目，'.' 表示空位
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println("原始数独：");
        printBoard(board);

        if (solveSudoku(board)) {
            System.out.println("\n求解后的数独：");
            printBoard(board);
        } else {
            System.out.println("\n该数独无解！");
        }
    }

    /**
     * 求解数独的主方法。
     * 步骤：
     * 1. 初始化三个约束数组：rows（行约束）、cols（列约束）、boxes（九宫格约束）。
     * 2. 遍历棋盘，将已有数字的约束信息记录到约束数组中。
     * 3. 调用回溯方法开始求解。
     * 4. 返回是否找到解。
     *
     * @param board 9x9 的数独棋盘，'.' 表示空位，'1'-'9' 表示已填入的数字
     * @return 如果找到解返回 true，否则返回 false
     */
    public static boolean solveSudoku(char[][] board) {
        // 1. 初始化约束数组
        // rows[i][num] 表示第 i 行是否已使用数字 num+1（num 范围 0-8，对应数字 1-9）
        boolean[][] rows = new boolean[9][9];
        // cols[j][num] 表示第 j 列是否已使用数字 num+1
        boolean[][] cols = new boolean[9][9];
        // boxes[k][num] 表示第 k 个九宫格是否已使用数字 num+1
        boolean[][] boxes = new boolean[9][9];

        // 2. 预处理：记录已有数字的约束信息
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != '.') {
                    int num = board[row][col] - '1'; // 将字符 '1'-'9' 转换为索引 0-8
                    int boxIndex = getBoxIndex(row, col);
                    
                    // 标记该数字在对应行、列、九宫格中已被使用
                    rows[row][num] = true;
                    cols[col][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }

        // 3. 调用回溯方法开始求解
        return backtrack(board, rows, cols, boxes, 0, 0);
    }

    /**
     * 使用回溯法递归地填充数独。
     * 步骤：
     * 1. 寻找下一个空位：从当前位置开始，找到下一个需要填充的空位（'.'）。
     * 2. 递归基（Base Case）：如果所有位置都已填充完毕，返回 true。
     * 3. 递归步骤（Recursive Step）：在当前空位尝试填入数字 1-9。
     *    a. 剪枝：检查当前数字是否与行、列、九宫格约束冲突。
     *    b. 如果不冲突：
     *       i.   更新状态：在棋盘上填入数字，更新约束数组。
     *       ii.  递归调用，处理下一个位置。
     *       iii. 如果递归成功，返回 true。
     *       iv.  回溯（Backtrack）：撤销当前填入的数字，恢复约束数组。
     * 4. 如果所有数字都尝试失败，返回 false。
     *
     * @param board 数独棋盘
     * @param rows 行约束数组
     * @param cols 列约束数组
     * @param boxes 九宫格约束数组
     * @param row 当前处理的行号
     * @param col 当前处理的列号
     * @return 如果能成功填充返回 true，否则返回 false
     */
    private static boolean backtrack(char[][] board, boolean[][] rows, boolean[][] cols, 
                                   boolean[][] boxes, int row, int col) {
        // 1. 寻找下一个空位
        while (row < 9) {
            while (col < 9 && board[row][col] != '.') {
                col++;
            }
            if (col < 9) {
                break; // 找到空位，跳出循环
            }
            // 当前行没有空位，移动到下一行
            row++;
            col = 0;
        }

        // 2. 递归基：所有位置都已填充完毕
        if (row >= 9) {
            return true; // 成功求解
        }

        // 3. 递归步骤：在当前空位 (row, col) 尝试填入数字 1-9
        int boxIndex = getBoxIndex(row, col);
        
        for (int num = 0; num < 9; num++) { // num 对应数字 1-9
            // 3a. 剪枝：检查约束冲突
            if (rows[row][num] || cols[col][num] || boxes[boxIndex][num]) {
                continue; // 冲突，跳过此数字
            }

            // 3b. 如果不冲突，执行填入操作
            // i. 更新状态
            char digit = (char) ('1' + num);
            board[row][col] = digit;
            rows[row][num] = cols[col][num] = boxes[boxIndex][num] = true;

            // ii. 递归处理下一个位置
            if (backtrack(board, rows, cols, boxes, row, col + 1)) {
                return true; // 找到解，直接返回
            }

            // iv. 回溯：撤销当前操作
            board[row][col] = '.';
            rows[row][num] = cols[col][num] = boxes[boxIndex][num] = false;
        }

        // 4. 所有数字都尝试失败
        return false;
    }

    /**
     * 计算给定位置所属的九宫格索引。
     * 九宫格编号规则：
     * 0 1 2
     * 3 4 5
     * 6 7 8
     * 
     * 计算公式：boxIndex = (row / 3) * 3 + (col / 3)
     *
     * @param row 行号 (0-8)
     * @param col 列号 (0-8)
     * @return 九宫格索引 (0-8)
     */
    private static int getBoxIndex(int row, int col) {
        return (row / 3) * 3 + (col / 3);
    }

    /**
     * 打印数独棋盘。
     *
     * @param board 9x9 数独棋盘
     */
    private static void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}