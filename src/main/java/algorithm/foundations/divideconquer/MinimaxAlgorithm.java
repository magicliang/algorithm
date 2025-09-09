package algorithm.foundations.divideconquer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Minimax算法实现 - 博弈树搜索。
 *
 * 问题描述：
 * 在零和博弈中寻找最优策略。Minimax算法通过递归搜索博弈树，
 * 假设对手总是选择对自己最有利的策略，从而找到当前玩家的最优移动。
 *
 * 算法原理：
 * 1. Max玩家试图最大化评估值
 * 2. Min玩家试图最小化评估值
 * 3. 递归搜索所有可能的移动
 * 4. 使用Alpha-Beta剪枝优化搜索效率
 *
 * 分治思想体现：
 * 1. 分 (Divide)：将博弈问题分解为子博弈
 * 2. 治 (Conquer)：递归求解每个子博弈的最优值
 * 3. 合 (Combine)：根据当前玩家选择最优子博弈
 *
 * 时间复杂度：O(b^d)，其中b是分支因子，d是搜索深度
 * 空间复杂度：O(d)，递归栈深度
 * Alpha-Beta剪枝可将时间复杂度优化到O(b^(d/2))
 *
 * 应用场景：
 * - 棋类游戏（象棋、围棋、五子棋等）
 * - 纸牌游戏策略
 * - 经济决策模型
 * - 军事战略规划
 *
 * @author magicliang
 * @date 2025-09-09 13:15
 */
public class MinimaxAlgorithm {

    /**
     * Minimax算法主入口。
     * 步骤：
     * 1. 参数验证
     * 2. 初始化搜索统计
     * 3. 调用递归的minimax算法
     * 4. 返回搜索结果
     *
     * @param state 当前游戏状态
     * @param depth 搜索深度
     * @param useAlphaBeta 是否使用Alpha-Beta剪枝
     * @return 搜索结果
     */
    public static MinimaxResult minimax(GameState state, int depth, boolean useAlphaBeta) {
        if (state == null) {
            throw new IllegalArgumentException("游戏状态不能为空");
        }
        if (depth < 0) {
            throw new IllegalArgumentException("搜索深度不能为负数");
        }

        long startTime = System.currentTimeMillis();
        int[] nodesVisited = {0}; // 使用数组以便在递归中修改

        int score;
        Move bestMove = null;

        if (useAlphaBeta) {
            MinimaxNode result = alphaBetaMinimax(state, depth, Integer.MIN_VALUE,
                    Integer.MAX_VALUE, true, nodesVisited);
            score = result.score;
            bestMove = result.move;
        } else {
            MinimaxNode result = simpleMinimax(state, depth, true, nodesVisited);
            score = result.score;
            bestMove = result.move;
        }

        long timeUsed = System.currentTimeMillis() - startTime;
        return new MinimaxResult(score, bestMove, nodesVisited[0], timeUsed);
    }

    /**
     * 简单的Minimax算法（无剪枝）。
     * 步骤：
     * 1. 递归基：达到搜索深度或游戏结束
     * 2. 分：获取所有可能的移动
     * 3. 治：递归搜索每个移动的结果
     * 4. 合：选择最优移动
     *
     * @param state 当前状态
     * @param depth 剩余搜索深度
     * @param maximizing 是否为最大化玩家
     * @param nodesVisited 访问节点计数器
     * @return 搜索结果
     */
    private static MinimaxNode simpleMinimax(GameState state, int depth, boolean maximizing,
            int[] nodesVisited) {
        nodesVisited[0]++;

        // 递归基：达到搜索深度或游戏结束
        if (depth == 0 || state.isGameOver()) {
            return new MinimaxNode(state.evaluate(), null);
        }

        List<Move> possibleMoves = state.getPossibleMoves();
        if (possibleMoves.isEmpty()) {
            return new MinimaxNode(state.evaluate(), null);
        }

        Move bestMove = null;
        int bestScore;

        if (maximizing) {
            // 最大化玩家：选择分数最高的移动
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                GameState newState = state.makeMove(move);
                MinimaxNode result = simpleMinimax(newState, depth - 1, false, nodesVisited);

                if (result.score > bestScore) {
                    bestScore = result.score;
                    bestMove = move;
                }
            }
        } else {
            // 最小化玩家：选择分数最低的移动
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                GameState newState = state.makeMove(move);
                MinimaxNode result = simpleMinimax(newState, depth - 1, true, nodesVisited);

                if (result.score < bestScore) {
                    bestScore = result.score;
                    bestMove = move;
                }
            }
        }

        return new MinimaxNode(bestScore, bestMove);
    }

    /**
     * 带Alpha-Beta剪枝的Minimax算法。
     * 步骤：
     * 1. 递归基：达到搜索深度或游戏结束
     * 2. 分：获取所有可能的移动
     * 3. 治：递归搜索每个移动，使用剪枝优化
     * 4. 合：选择最优移动，更新alpha/beta值
     *
     * @param state 当前状态
     * @param depth 剩余搜索深度
     * @param alpha Alpha值（最大化玩家的下界）
     * @param beta Beta值（最小化玩家的上界）
     * @param maximizing 是否为最大化玩家
     * @param nodesVisited 访问节点计数器
     * @return 搜索结果
     */
    private static MinimaxNode alphaBetaMinimax(GameState state, int depth, int alpha, int beta,
            boolean maximizing, int[] nodesVisited) {
        nodesVisited[0]++;

        // 递归基：达到搜索深度或游戏结束
        if (depth == 0 || state.isGameOver()) {
            return new MinimaxNode(state.evaluate(), null);
        }

        List<Move> possibleMoves = state.getPossibleMoves();
        if (possibleMoves.isEmpty()) {
            return new MinimaxNode(state.evaluate(), null);
        }

        Move bestMove = null;

        if (maximizing) {
            // 最大化玩家
            int maxScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                GameState newState = state.makeMove(move);
                MinimaxNode result = alphaBetaMinimax(newState, depth - 1, alpha, beta,
                        false, nodesVisited);

                if (result.score > maxScore) {
                    maxScore = result.score;
                    bestMove = move;
                }

                alpha = Math.max(alpha, result.score);
                if (beta <= alpha) {
                    break; // Beta剪枝
                }
            }
            return new MinimaxNode(maxScore, bestMove);
        } else {
            // 最小化玩家
            int minScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                GameState newState = state.makeMove(move);
                MinimaxNode result = alphaBetaMinimax(newState, depth - 1, alpha, beta,
                        true, nodesVisited);

                if (result.score < minScore) {
                    minScore = result.score;
                    bestMove = move;
                }

                beta = Math.min(beta, result.score);
                if (beta <= alpha) {
                    break; // Alpha剪枝
                }
            }
            return new MinimaxNode(minScore, bestMove);
        }
    }

    /**
     * 简化的Minimax算法，直接处理叶子节点值数组。
     * 为了兼容测试代码而提供的重载方法。
     *
     * @param leafValues 叶子节点值数组
     * @param depth 博弈树深度
     * @param isMaximizing 是否为最大化玩家
     * @return 最优值
     * @throws IllegalArgumentException 如果输入无效
     */
    public static int minimaxSimpleArray(int[] leafValues, int depth, boolean isMaximizing) {
        if (leafValues == null || leafValues.length == 0) {
            throw new IllegalArgumentException("叶子节点值数组不能为空");
        }
        if (depth <= 0) {
            throw new IllegalArgumentException("搜索深度必须为正数");
        }

        return minimaxSimple(leafValues, 0, leafValues.length - 1, depth, isMaximizing);
    }

    /**
     * 简化的Alpha-Beta剪枝算法，直接处理叶子节点值数组。
     * 为了兼容测试代码而提供的重载方法。
     *
     * @param leafValues 叶子节点值数组
     * @param depth 博弈树深度
     * @param isMaximizing 是否为最大化玩家
     * @return 最优值
     * @throws IllegalArgumentException 如果输入无效
     */
    public static int alphaBetaSimpleArray(int[] leafValues, int depth, boolean isMaximizing) {
        if (leafValues == null || leafValues.length == 0) {
            throw new IllegalArgumentException("叶子节点值数组不能为空");
        }
        if (depth <= 0) {
            throw new IllegalArgumentException("搜索深度必须为正数");
        }

        return alphaBetaSimple(leafValues, 0, leafValues.length - 1, depth,
                Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizing);
    }

    /**
     * 简化的Minimax递归实现。
     */
    private static int minimaxSimple(int[] leafValues, int left, int right, int depth, boolean isMaximizing) {
        // 递归基：到达叶子节点
        if (depth == 1) {
            if (isMaximizing) {
                int max = Integer.MIN_VALUE;
                for (int i = left; i <= right; i++) {
                    max = Math.max(max, leafValues[i]);
                }
                return max;
            } else {
                int min = Integer.MAX_VALUE;
                for (int i = left; i <= right; i++) {
                    min = Math.min(min, leafValues[i]);
                }
                return min;
            }
        }

        // 计算每个子树的范围
        int rangeSize = (right - left + 1) / 2;

        if (isMaximizing) {
            int leftResult = minimaxSimple(leafValues, left, left + rangeSize - 1, depth - 1, false);
            int rightResult = minimaxSimple(leafValues, left + rangeSize, right, depth - 1, false);
            return Math.max(leftResult, rightResult);
        } else {
            int leftResult = minimaxSimple(leafValues, left, left + rangeSize - 1, depth - 1, true);
            int rightResult = minimaxSimple(leafValues, left + rangeSize, right, depth - 1, true);
            return Math.min(leftResult, rightResult);
        }
    }

    /**
     * 简化的Alpha-Beta剪枝递归实现。
     */
    private static int alphaBetaSimple(int[] leafValues, int left, int right, int depth,
            int alpha, int beta, boolean isMaximizing) {
        // 递归基：到达叶子节点
        if (depth == 1) {
            if (isMaximizing) {
                int max = Integer.MIN_VALUE;
                for (int i = left; i <= right; i++) {
                    max = Math.max(max, leafValues[i]);
                    alpha = Math.max(alpha, max);
                    if (beta <= alpha) {
                        break; // Beta剪枝
                    }
                }
                return max;
            } else {
                int min = Integer.MAX_VALUE;
                for (int i = left; i <= right; i++) {
                    min = Math.min(min, leafValues[i]);
                    beta = Math.min(beta, min);
                    if (beta <= alpha) {
                        break; // Alpha剪枝
                    }
                }
                return min;
            }
        }

        // 计算每个子树的范围
        int rangeSize = (right - left + 1) / 2;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;

            // 左子树
            int leftResult = alphaBetaSimple(leafValues, left, left + rangeSize - 1,
                    depth - 1, alpha, beta, false);
            maxEval = Math.max(maxEval, leftResult);
            alpha = Math.max(alpha, leftResult);

            if (beta <= alpha) {
                return maxEval; // Beta剪枝
            }

            // 右子树
            int rightResult = alphaBetaSimple(leafValues, left + rangeSize, right,
                    depth - 1, alpha, beta, false);
            maxEval = Math.max(maxEval, rightResult);

            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            // 左子树
            int leftResult = alphaBetaSimple(leafValues, left, left + rangeSize - 1,
                    depth - 1, alpha, beta, true);
            minEval = Math.min(minEval, leftResult);
            beta = Math.min(beta, leftResult);

            if (beta <= alpha) {
                return minEval; // Alpha剪枝
            }

            // 右子树
            int rightResult = alphaBetaSimple(leafValues, left + rangeSize, right,
                    depth - 1, alpha, beta, true);
            minEval = Math.min(minEval, rightResult);

            return minEval;
        }
    }

    /**
     * 玩家枚举。
     */
    public enum Player {
        MAX, MIN
    }

    // ==================== 井字棋游戏实现（用于测试） ====================

    /**
     * 游戏状态接口。
     */
    public interface GameState {

        /**
         * 获取当前可能的移动。
         */
        List<Move> getPossibleMoves();

        /**
         * 执行移动，返回新的游戏状态。
         */
        GameState makeMove(Move move);

        /**
         * 判断游戏是否结束。
         */
        boolean isGameOver();

        /**
         * 评估当前状态的分数（从当前玩家角度）。
         */
        int evaluate();

        /**
         * 获取当前玩家。
         */
        Player getCurrentPlayer();

        /**
         * 创建状态的副本。
         */
        GameState copy();
    }

    /**
     * 移动接口。
     */
    public interface Move {

        /**
         * 获取移动的描述。
         */
        String getDescription();
    }

    // ==================== 简化的Minimax方法（用于测试） ====================

    /**
     * Minimax搜索结果。
     */
    public static class MinimaxResult {

        public final int score;          // 最优分数
        public final Move bestMove;      // 最优移动
        public final int nodesVisited;  // 访问的节点数
        public final long timeUsed;     // 搜索耗时（毫秒）

        public MinimaxResult(int score, Move bestMove, int nodesVisited, long timeUsed) {
            this.score = score;
            this.bestMove = bestMove;
            this.nodesVisited = nodesVisited;
            this.timeUsed = timeUsed;
        }

        @Override
        public String toString() {
            return String.format("Score: %d, Move: %s, Nodes: %d, Time: %dms",
                    score, bestMove != null ? bestMove.getDescription() : "None",
                    nodesVisited, timeUsed);
        }
    }

    /**
     * 内部节点类，用于存储搜索结果。
     */
    private static class MinimaxNode {

        public final int score;
        public final Move move;

        public MinimaxNode(int score, Move move) {
            this.score = score;
            this.move = move;
        }
    }

    /**
     * 井字棋游戏状态实现。
     */
    public static class TicTacToeState implements GameState {

        private final char[][] board;
        private final Player currentPlayer;
        private final int size;

        public TicTacToeState(int size) {
            this.size = size;
            this.board = new char[size][size];
            this.currentPlayer = Player.MAX;

            // 初始化棋盘
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = ' ';
                }
            }
        }

        private TicTacToeState(char[][] board, Player currentPlayer, int size) {
            this.size = size;
            this.board = new char[size][size];
            this.currentPlayer = currentPlayer;

            // 复制棋盘
            for (int i = 0; i < size; i++) {
                System.arraycopy(board[i], 0, this.board[i], 0, size);
            }
        }

        @Override
        public List<Move> getPossibleMoves() {
            List<Move> moves = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == ' ') {
                        moves.add(new TicTacToeMove(i, j));
                    }
                }
            }
            return moves;
        }

        @Override
        public GameState makeMove(Move move) {
            TicTacToeMove ticMove = (TicTacToeMove) move;
            TicTacToeState newState = new TicTacToeState(board,
                    currentPlayer == Player.MAX ? Player.MIN : Player.MAX, size);

            char symbol = currentPlayer == Player.MAX ? 'X' : 'O';
            newState.board[ticMove.row][ticMove.col] = symbol;

            return newState;
        }

        @Override
        public boolean isGameOver() {
            return getWinner() != null || getPossibleMoves().isEmpty();
        }

        @Override
        public int evaluate() {
            Character winner = getWinner();
            if (winner == null) {
                return 0; // 平局或游戏未结束
            }
            return winner == 'X' ? 100 : -100; // X是MAX玩家，O是MIN玩家
        }

        @Override
        public Player getCurrentPlayer() {
            return currentPlayer;
        }

        @Override
        public GameState copy() {
            return new TicTacToeState(board, currentPlayer, size);
        }

        /**
         * 获取游戏获胜者。
         */
        public Character getWinner() {
            // 检查行
            for (int i = 0; i < size; i++) {
                if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    return board[i][0];
                }
            }

            // 检查列
            for (int j = 0; j < size; j++) {
                if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    return board[0][j];
                }
            }

            // 检查对角线
            if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return board[0][0];
            }
            if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                return board[0][2];
            }

            return null; // 无获胜者
        }

        /**
         * 打印棋盘。
         */
        public void printBoard() {
            System.out.println("  0 1 2");
            for (int i = 0; i < size; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < size; j++) {
                    System.out.print(board[i][j]);
                    if (j < size - 1) {
                        System.out.print("|");
                    }
                }
                System.out.println();
                if (i < size - 1) {
                    System.out.println("  -----");
                }
            }
            System.out.println();
        }
    }

    /**
     * 井字棋移动实现。
     */
    public static class TicTacToeMove implements Move {

        public final int row;
        public final int col;

        public TicTacToeMove(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String getDescription() {
            return String.format("(%d,%d)", row, col);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TicTacToeMove that = (TicTacToeMove) obj;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}