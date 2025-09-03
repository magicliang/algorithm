package algorithm.datastructures.trees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * BTree类的单元测试
 * 
 * 主要测试内容：
 * 1. levelOrderByDepth方法的各种场景测试
 * 2. 与原有levelOrder和levelOrderRecursive方法的对比验证
 * 3. 边界情况和异常情况的处理
 */
public class BTreeTest {

    private BTree bTree;

    @BeforeEach
    void setUp() {
        bTree = new BTree();
    }

    /**
     * 测试空树的情况
     */
    @Test
    void testLevelOrderByDepth_EmptyTree() {
        List<List<Integer>> result = bTree.levelOrderByDepth(null);
        assertTrue(result.isEmpty(), "空树应该返回空列表");
    }

    /**
     * 测试单节点树
     */
    @Test
    void testLevelOrderByDepth_SingleNode() {
        BTree.Node root = new BTree.Node(1);
        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(1, result.size(), "单节点树应该只有一层");
        assertEquals(Arrays.asList(1), result.get(0), "第一层应该包含根节点");
    }

    /**
     * 测试完全二叉树
     */
    @Test
    void testLevelOrderByDepth_CompleteBinaryTree() {
        // 构建完全二叉树：
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "完全二叉树应该有3层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层：根节点");
        assertEquals(Arrays.asList(2, 3), result.get(1), "第2层：左右子节点");
        assertEquals(Arrays.asList(4, 5, 6, 7), result.get(2), "第3层：叶子节点");
    }

    /**
     * 测试不平衡二叉树（左偏）
     */
    @Test
    void testLevelOrderByDepth_LeftSkewedTree() {
        // 构建左偏树：
        //   1
        //  /
        // 2
        ///
        //3
        BTree.Node root = new BTree.Node(1);
        root.left = new BTree.Node(2);
        root.left.left = new BTree.Node(3);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "左偏树应该有3层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层：根节点");
        assertEquals(Arrays.asList(2), result.get(1), "第2层：左子节点");
        assertEquals(Arrays.asList(3), result.get(2), "第3层：左子节点");
    }

    /**
     * 测试不平衡二叉树（右偏）
     */
    @Test
    void testLevelOrderByDepth_RightSkewedTree() {
        // 构建右偏树：
        // 1
        //  \
        //   2
        //    \
        //     3
        BTree.Node root = new BTree.Node(1);
        root.right = new BTree.Node(2);
        root.right.right = new BTree.Node(3);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "右偏树应该有3层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层：根节点");
        assertEquals(Arrays.asList(2), result.get(1), "第2层：右子节点");
        assertEquals(Arrays.asList(3), result.get(2), "第3层：右子节点");
    }

    /**
     * 测试不规则二叉树
     */
    @Test
    void testLevelOrderByDepth_IrregularTree() {
        // 构建不规则树：
        //       1
        //      / \
        //     2   3
        //    /     \
        //   4       5
        //  /       / \
        // 6       7   8
        BTree.Node root = new BTree.Node(1);
        root.left = new BTree.Node(2);
        root.right = new BTree.Node(3);
        root.left.left = new BTree.Node(4);
        root.right.right = new BTree.Node(5);
        root.left.left.left = new BTree.Node(6);
        root.right.right.left = new BTree.Node(7);
        root.right.right.right = new BTree.Node(8);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(4, result.size(), "不规则树应该有4层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层");
        assertEquals(Arrays.asList(2, 3), result.get(1), "第2层");
        assertEquals(Arrays.asList(4, 5), result.get(2), "第3层");
        assertEquals(Arrays.asList(6, 7, 8), result.get(3), "第4层");
    }

    /**
     * 测试包含负数的二叉树
     */
    @Test
    void testLevelOrderByDepth_WithNegativeNumbers() {
        // 构建包含负数的树：
        //      -1
        //     /  \
        //   -2    3
        //   /    / \
        // -4    5  -6
        BTree.Node root = new BTree.Node(-1);
        root.left = new BTree.Node(-2);
        root.right = new BTree.Node(3);
        root.left.left = new BTree.Node(-4);
        root.right.left = new BTree.Node(5);
        root.right.right = new BTree.Node(-6);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "包含负数的树应该有3层");
        assertEquals(Arrays.asList(-1), result.get(0), "第1层：负数根节点");
        assertEquals(Arrays.asList(-2, 3), result.get(1), "第2层：包含负数");
        assertEquals(Arrays.asList(-4, 5, -6), result.get(2), "第3层：包含负数");
    }

    /**
     * 对比测试：验证levelOrderByDepth与原有方法的一致性
     */
    @Test
    void testLevelOrderByDepth_CompareWithOriginalMethods() {
        // 构建测试树
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        // 获取三种方法的结果
        List<List<Integer>> depthResult = bTree.levelOrderByDepth(root);
        List<Integer> originalResult = bTree.levelOrder(root);
        List<Integer> recursiveResult = bTree.levelOrderRecursive(root);

        // 将分层结果展平为一维列表
        List<Integer> flattenedDepthResult = new ArrayList<>();
        for (List<Integer> level : depthResult) {
            flattenedDepthResult.addAll(level);
        }

        // 验证三种方法的结果一致性
        assertEquals(originalResult, flattenedDepthResult, 
            "levelOrderByDepth展平后应该与levelOrder结果一致");
        assertEquals(recursiveResult, flattenedDepthResult, 
            "levelOrderByDepth展平后应该与levelOrderRecursive结果一致");
        
        // 验证分层结构的正确性
        assertEquals(Arrays.asList(1), depthResult.get(0), "第1层正确");
        assertEquals(Arrays.asList(2, 3), depthResult.get(1), "第2层正确");
        assertEquals(Arrays.asList(4, 5, 6, 7), depthResult.get(2), "第3层正确");
    }

    /**
     * 性能对比测试：验证不同算法的时间复杂度特征
     */
    @Test
    void testLevelOrderByDepth_PerformanceCharacteristics() {
        // 构建一个较大的完全二叉树进行性能测试
        BTree.Node root = buildLargeCompleteBinaryTree(4); // 深度为4的完全二叉树

        // 测试levelOrderByDepth方法
        long startTime = System.nanoTime();
        List<List<Integer>> depthResult = bTree.levelOrderByDepth(root);
        long depthTime = System.nanoTime() - startTime;

        // 测试原有的levelOrder方法
        startTime = System.nanoTime();
        List<Integer> originalResult = bTree.levelOrder(root);
        long originalTime = System.nanoTime() - startTime;

        // 测试递归的levelOrderRecursive方法
        startTime = System.nanoTime();
        List<Integer> recursiveResult = bTree.levelOrderRecursive(root);
        long recursiveTime = System.nanoTime() - startTime;

        // 验证结果正确性
        List<Integer> flattenedDepthResult = new ArrayList<>();
        for (List<Integer> level : depthResult) {
            flattenedDepthResult.addAll(level);
        }
        assertEquals(originalResult, flattenedDepthResult, "结果应该一致");

        // 输出性能信息（用于观察，不做断言）
        System.out.println("Performance comparison:");
        System.out.println("levelOrderByDepth: " + depthTime + " ns");
        System.out.println("levelOrder: " + originalTime + " ns");
        System.out.println("levelOrderRecursive: " + recursiveTime + " ns");
        
        // levelOrderByDepth应该比levelOrderRecursive更高效（O(n) vs O(n²)）
        assertTrue(depthTime < recursiveTime * 2, 
            "levelOrderByDepth应该比levelOrderRecursive更高效");
    }

    /**
     * 辅助方法：构建指定深度的完全二叉树
     */
    private BTree.Node buildLargeCompleteBinaryTree(int depth) {
        if (depth <= 0) {
            return null;
        }
        
        BTree.Node root = new BTree.Node(1);
        buildCompleteBinaryTreeHelper(root, 1, depth, 1);
        return root;
    }

    /**
     * 递归构建完全二叉树的辅助方法
     */
    private void buildCompleteBinaryTreeHelper(BTree.Node node, int currentDepth, int maxDepth, int value) {
        if (currentDepth >= maxDepth) {
            return;
        }
        
        node.left = new BTree.Node(value * 2);
        node.right = new BTree.Node(value * 2 + 1);
        
        buildCompleteBinaryTreeHelper(node.left, currentDepth + 1, maxDepth, value * 2);
        buildCompleteBinaryTreeHelper(node.right, currentDepth + 1, maxDepth, value * 2 + 1);
    }

    /**
     * 边界测试：测试深度为1的树（只有根节点）
     */
    @Test
    void testLevelOrderByDepth_DepthOne() {
        BTree.Node root = new BTree.Node(42);
        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(1, result.size(), "深度为1的树应该只有一层");
        assertEquals(Arrays.asList(42), result.get(0), "唯一的层应该包含根节点");
    }

    /**
     * 边界测试：测试只有左子树的情况
     */
    @Test
    void testLevelOrderByDepth_OnlyLeftSubtree() {
        BTree.Node root = new BTree.Node(1);
        root.left = new BTree.Node(2);
        root.left.left = new BTree.Node(3);
        root.left.right = new BTree.Node(4);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "应该有3层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层");
        assertEquals(Arrays.asList(2), result.get(1), "第2层");
        assertEquals(Arrays.asList(3, 4), result.get(2), "第3层");
    }

    /**
     * 边界测试：测试只有右子树的情况
     */
    @Test
    void testLevelOrderByDepth_OnlyRightSubtree() {
        BTree.Node root = new BTree.Node(1);
        root.right = new BTree.Node(2);
        root.right.left = new BTree.Node(3);
        root.right.right = new BTree.Node(4);

        List<List<Integer>> result = bTree.levelOrderByDepth(root);
        
        assertEquals(3, result.size(), "应该有3层");
        assertEquals(Arrays.asList(1), result.get(0), "第1层");
        assertEquals(Arrays.asList(2), result.get(1), "第2层");
        assertEquals(Arrays.asList(3, 4), result.get(2), "第3层");
    }
}