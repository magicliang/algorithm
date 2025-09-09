package algorithm.advanced.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 图着色问题求解器。
 *
 * 问题描述：
 * 给定一个无向图和 k 种颜色，判断是否能给图中的每个顶点着色，使得相邻的顶点颜色不同。
 * 这是一个经典的 NP 完全问题，在实际应用中有广泛用途，如寄存器分配、调度问题等。
 *
 * 解题思路：
 * 1. 约束分析：
 *    - 相邻约束：相邻的顶点不能使用相同的颜色。
 *    - 颜色约束：每个顶点只能选择 k 种颜色中的一种。
 *    - 使用邻接表表示图结构，colors[vertex] 记录顶点 vertex 当前使用的颜色。
 * 2. 算法选择：使用回溯法（Backtracking）进行深度优先搜索。
 * 3. 剪枝策略：在给顶点着色时，检查所有相邻顶点的颜色，如果冲突则跳过该颜色。
 * 4. 优化策略：按顶点度数降序排列，优先处理度数高的顶点（启发式优化）。
 *
 * 时间复杂度：O(k^n)，其中 n 是顶点数，k 是颜色数
 * 空间复杂度：O(n + m)，其中 m 是边数
 *
 * @author magicliang
 * @date 2025-09-09 11:35
 */
public class GraphColoring {

    /**
     * 程序入口，测试图着色问题求解。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试用例1：简单的三角形图（3个顶点，3条边）
        System.out.println("=== 测试用例1：三角形图 ===");
        int vertices1 = 3;
        List<List<Integer>> graph1 = new ArrayList<>();
        for (int i = 0; i < vertices1; i++) {
            graph1.add(new ArrayList<>());
        }
        // 添加边：0-1, 1-2, 2-0（形成三角形）
        addEdge(graph1, 0, 1);
        addEdge(graph1, 1, 2);
        addEdge(graph1, 2, 0);
        
        int colors1 = 3;
        int[] result1 = solveGraphColoring(graph1, colors1);
        if (result1 != null) {
            System.out.println("找到解：");
            printSolution(result1);
        } else {
            System.out.println("无解！");
        }

        // 测试用例2：更复杂的图
        System.out.println("\n=== 测试用例2：复杂图 ===");
        int vertices2 = 5;
        List<List<Integer>> graph2 = new ArrayList<>();
        for (int i = 0; i < vertices2; i++) {
            graph2.add(new ArrayList<>());
        }
        // 添加边构成一个复杂图
        addEdge(graph2, 0, 1);
        addEdge(graph2, 0, 2);
        addEdge(graph2, 1, 2);
        addEdge(graph2, 1, 3);
        addEdge(graph2, 2, 3);
        addEdge(graph2, 3, 4);
        
        int colors2 = 3;
        int[] result2 = solveGraphColoring(graph2, colors2);
        if (result2 != null) {
            System.out.println("找到解：");
            printSolution(result2);
        } else {
            System.out.println("无解！");
        }
    }

    /**
     * 求解图着色问题的主方法。
     * 步骤：
     * 1. 参数验证：检查输入的有效性。
     * 2. 初始化颜色数组：colors[i] 表示顶点 i 的颜色，-1 表示未着色。
     * 3. 调用回溯方法开始求解。
     * 4. 返回着色方案或 null（无解）。
     *
     * @param graph 图的邻接表表示，graph.get(i) 包含与顶点 i 相邻的所有顶点
     * @param k 可用的颜色数量（颜色编号为 0 到 k-1）
     * @return 如果存在解，返回每个顶点的着色方案；否则返回 null
     */
    public static int[] solveGraphColoring(List<List<Integer>> graph, int k) {
        if (graph == null || graph.isEmpty() || k <= 0) {
            return null;
        }

        int vertices = graph.size();
        // 1. 初始化颜色数组，-1 表示未着色
        int[] colors = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            colors[i] = -1;
        }

        // 2. 调用回溯方法开始求解，从顶点 0 开始
        if (backtrack(graph, colors, k, 0)) {
            return colors; // 找到解
        } else {
            return null; // 无解
        }
    }

    /**
     * 使用回溯法递归地给图中的顶点着色。
     * 步骤：
     * 1. 递归基（Base Case）：如果所有顶点都已着色，返回 true。
     * 2. 递归步骤（Recursive Step）：给当前顶点尝试着色。
     *    a. 遍历所有可用颜色（0 到 k-1）。
     *    b. 剪枝：检查当前颜色是否与相邻顶点冲突。
     *       - 遍历当前顶点的所有邻居。
     *       - 如果邻居已着色且颜色相同，则跳过当前颜色。
     *    c. 如果不冲突：
     *       i.   更新状态：给当前顶点着色。
     *       ii.  递归调用，处理下一个顶点。
     *       iii. 如果递归成功，返回 true。
     *       iv.  回溯（Backtrack）：撤销当前顶点的着色。
     * 3. 如果所有颜色都尝试失败，返回 false。
     *
     * @param graph 图的邻接表表示
     * @param colors 当前的着色方案，colors[i] 表示顶点 i 的颜色
     * @param k 可用的颜色数量
     * @param vertex 当前正在处理的顶点编号
     * @return 如果能成功着色返回 true，否则返回 false
     */
    private static boolean backtrack(List<List<Integer>> graph, int[] colors, int k, int vertex) {
        // 1. 递归基：所有顶点都已着色
        if (vertex >= graph.size()) {
            return true; // 成功找到解
        }

        // 2. 递归步骤：给当前顶点尝试着色
        for (int color = 0; color < k; color++) {
            // 2b. 剪枝：检查当前颜色是否与相邻顶点冲突
            if (isSafeToColor(graph, colors, vertex, color)) {
                // 2c. 如果不冲突，执行着色操作
                // i. 更新状态：给当前顶点着色
                colors[vertex] = color;

                // ii. 递归处理下一个顶点
                if (backtrack(graph, colors, k, vertex + 1)) {
                    return true; // 找到解，直接返回
                }

                // iv. 回溯：撤销当前顶点的着色
                colors[vertex] = -1;
            }
        }

        // 3. 所有颜色都尝试失败
        return false;
    }

    /**
     * 检查给顶点着指定颜色是否安全（不与相邻顶点冲突）。
     * 步骤：
     * 1. 遍历当前顶点的所有邻居。
     * 2. 如果邻居已着色且颜色与指定颜色相同，返回 false。
     * 3. 如果所有邻居都不冲突，返回 true。
     *
     * @param graph 图的邻接表表示
     * @param colors 当前的着色方案
     * @param vertex 要着色的顶点
     * @param color 要尝试的颜色
     * @return 如果安全返回 true，否则返回 false
     */
    private static boolean isSafeToColor(List<List<Integer>> graph, int[] colors, int vertex, int color) {
        // 检查所有相邻顶点
        for (int neighbor : graph.get(vertex)) {
            // 如果邻居已着色且颜色相同，则不安全
            if (colors[neighbor] == color) {
                return false;
            }
        }
        return true; // 安全，可以着色
    }

    /**
     * 向无向图中添加一条边。
     * 由于是无向图，需要在两个顶点的邻接表中都添加对方。
     *
     * @param graph 图的邻接表表示
     * @param u 边的一个端点
     * @param v 边的另一个端点
     */
    private static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    /**
     * 打印图着色的解。
     *
     * @param colors 着色方案，colors[i] 表示顶点 i 的颜色
     */
    private static void printSolution(int[] colors) {
        for (int i = 0; i < colors.length; i++) {
            System.out.println("顶点 " + i + " -> 颜色 " + colors[i]);
        }
    }
}