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

    // ==================== 前序遍历测试 ====================

    /**
     * 测试前序遍历 - 空树
     */
    @Test
    void testPreOrder_EmptyTree() {
        List<Integer> result = bTree.preOrder(null);
        assertTrue(result.isEmpty(), "空树的前序遍历应该返回空列表");
    }

    /**
     * 测试前序遍历 - 单节点
     */
    @Test
    void testPreOrder_SingleNode() {
        BTree.Node root = new BTree.Node(1);
        List<Integer> result = bTree.preOrder(root);
        assertEquals(Arrays.asList(1), result, "单节点的前序遍历");
    }

    /**
     * 测试前序遍历 - 完全二叉树
     */
    @Test
    void testPreOrder_CompleteBinaryTree() {
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

        List<Integer> result = bTree.preOrder(root);
        // 前序遍历：根-左-右
        assertEquals(Arrays.asList(1, 2, 4, 5, 3, 6, 7), result, "完全二叉树的前序遍历");
    }

    /**
     * 测试前序遍历 - 左偏树
     */
    @Test
    void testPreOrder_LeftSkewedTree() {
        BTree.Node root = new BTree.Node(1);
        root.left = new BTree.Node(2);
        root.left.left = new BTree.Node(3);

        List<Integer> result = bTree.preOrder(root);
        assertEquals(Arrays.asList(1, 2, 3), result, "左偏树的前序遍历");
    }

    /**
     * 测试前序遍历 - 右偏树
     */
    @Test
    void testPreOrder_RightSkewedTree() {
        BTree.Node root = new BTree.Node(1);
        root.right = new BTree.Node(2);
        root.right.right = new BTree.Node(3);

        List<Integer> result = bTree.preOrder(root);
        assertEquals(Arrays.asList(1, 2, 3), result, "右偏树的前序遍历");
    }

    // ==================== 非递归前序遍历测试 ====================

    /**
     * 测试非递归前序遍历 - 空树
     */
    @Test
    void testPreOrderNonRecursive_EmptyTree() {
        List<Integer> result = bTree.preOrderNonRecursive(null);
        assertTrue(result.isEmpty(), "空树的非递归前序遍历应该返回空列表");
    }

    /**
     * 测试非递归前序遍历 - 完全二叉树
     */
    @Test
    void testPreOrderNonRecursive_CompleteBinaryTree() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.preOrderNonRecursive(root);
        assertEquals(Arrays.asList(1, 2, 4, 5, 3, 6, 7), result, "非递归前序遍历结果");
    }

    /**
     * 对比测试：递归与非递归前序遍历结果一致性
     */
    @Test
    void testPreOrder_RecursiveVsNonRecursive() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> recursiveResult = bTree.preOrder(root);
        List<Integer> nonRecursiveResult = bTree.preOrderNonRecursive(root);
        
        assertEquals(recursiveResult, nonRecursiveResult, "递归与非递归前序遍历结果应该一致");
    }

    // ==================== 中序遍历测试 ====================

    /**
     * 测试中序遍历 - 空树
     */
    @Test
    void testMidOrder_EmptyTree() {
        List<Integer> result = bTree.midOrder(null);
        assertTrue(result.isEmpty(), "空树的中序遍历应该返回空列表");
    }

    /**
     * 测试中序遍历 - 单节点
     */
    @Test
    void testMidOrder_SingleNode() {
        BTree.Node root = new BTree.Node(1);
        List<Integer> result = bTree.midOrder(root);
        assertEquals(Arrays.asList(1), result, "单节点的中序遍历");
    }

    /**
     * 测试中序遍历 - 完全二叉树
     */
    @Test
    void testMidOrder_CompleteBinaryTree() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.midOrder(root);
        // 中序遍历：左-根-右
        assertEquals(Arrays.asList(4, 2, 5, 1, 6, 3, 7), result, "完全二叉树的中序遍历");
    }

    /**
     * 测试中序遍历 - 二叉搜索树（验证有序性）
     */
    @Test
    void testMidOrder_BinarySearchTree() {
        // 构建二叉搜索树：
        //       4
        //      / \
        //     2   6
        //    / \ / \
        //   1  3 5  7
        BTree.Node root = BTree.tree(4)
            .left(BTree.tree(2)
                .left(new BTree.Node(1))
                .right(new BTree.Node(3)))
            .right(BTree.tree(6)
                .left(new BTree.Node(5))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.midOrder(root);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), result, "二叉搜索树的中序遍历应该是有序的");
    }

    // ==================== 非递归中序遍历测试 ====================

    /**
     * 测试非递归中序遍历 - 空树
     */
    @Test
    void testMidOrderNonRecursive_EmptyTree() {
        List<Integer> result = bTree.midOrderNonRecursive(null);
        assertTrue(result.isEmpty(), "空树的非递归中序遍历应该返回空列表");
    }

    /**
     * 测试非递归中序遍历 - 完全二叉树
     */
    @Test
    void testMidOrderNonRecursive_CompleteBinaryTree() {
        BTree.Node root = BTree.tree(4)
            .left(BTree.tree(2)
                .left(new BTree.Node(1))
                .right(new BTree.Node(3)))
            .right(BTree.tree(6)
                .left(new BTree.Node(5))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.midOrderNonRecursive(root);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), result, "非递归中序遍历结果");
    }

    /**
     * 对比测试：递归与非递归中序遍历结果一致性
     */
    @Test
    void testMidOrder_RecursiveVsNonRecursive() {
        BTree.Node root = BTree.tree(4)
            .left(BTree.tree(2)
                .left(new BTree.Node(1))
                .right(new BTree.Node(3)))
            .right(BTree.tree(6)
                .left(new BTree.Node(5))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> recursiveResult = bTree.midOrder(root);
        List<Integer> nonRecursiveResult = bTree.midOrderNonRecursive(root);
        
        assertEquals(recursiveResult, nonRecursiveResult, "递归与非递归中序遍历结果应该一致");
    }

    // ==================== 后序遍历测试 ====================

    /**
     * 测试后序遍历 - 空树
     */
    @Test
    void testPostOrder_EmptyTree() {
        List<Integer> result = bTree.postOrder(null);
        assertTrue(result.isEmpty(), "空树的后序遍历应该返回空列表");
    }

    /**
     * 测试后序遍历 - 单节点
     */
    @Test
    void testPostOrder_SingleNode() {
        BTree.Node root = new BTree.Node(1);
        List<Integer> result = bTree.postOrder(root);
        assertEquals(Arrays.asList(1), result, "单节点的后序遍历");
    }

    /**
     * 测试后序遍历 - 完全二叉树
     */
    @Test
    void testPostOrder_CompleteBinaryTree() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.postOrder(root);
        // 后序遍历：左-右-根
        assertEquals(Arrays.asList(4, 5, 2, 6, 7, 3, 1), result, "完全二叉树的后序遍历");
    }

    /**
     * 测试后序遍历 - 左偏树
     */
    @Test
    void testPostOrder_LeftSkewedTree() {
        BTree.Node root = new BTree.Node(1);
        root.left = new BTree.Node(2);
        root.left.left = new BTree.Node(3);

        List<Integer> result = bTree.postOrder(root);
        assertEquals(Arrays.asList(3, 2, 1), result, "左偏树的后序遍历");
    }

    // ==================== 非递归后序遍历测试 ====================

    /**
     * 测试非递归后序遍历 - 空树
     */
    @Test
    void testPostOrderNonRecursive_EmptyTree() {
        List<Integer> result = bTree.postOrderNonRecursive(null);
        assertTrue(result.isEmpty(), "空树的非递归后序遍历应该返回空列表");
    }

    /**
     * 测试非递归后序遍历 - 完全二叉树
     */
    @Test
    void testPostOrderNonRecursive_CompleteBinaryTree() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> result = bTree.postOrderNonRecursive(root);
        assertEquals(Arrays.asList(4, 5, 2, 6, 7, 3, 1), result, "非递归后序遍历结果");
    }

    /**
     * 对比测试：递归与非递归后序遍历结果一致性
     */
    @Test
    void testPostOrder_RecursiveVsNonRecursive() {
        BTree.Node root = BTree.tree(1)
            .left(BTree.tree(2)
                .left(new BTree.Node(4))
                .right(new BTree.Node(5)))
            .right(BTree.tree(3)
                .left(new BTree.Node(6))
                .right(new BTree.Node(7)))
            .build();

        List<Integer> recursiveResult = bTree.postOrder(root);
        List<Integer> nonRecursiveResult = bTree.postOrderNonRecursive(root);
        
        assertEquals(recursiveResult, nonRecursiveResult, "递归与非递归后序遍历结果应该一致");
    }

    // ==================== 构建树测试 ====================

    /**
     * 测试根据前序和中序遍历构建树 - 空数组
     */
    @Test
    void testBuildTree_EmptyArrays() {
        int[] preorder = {};
        int[] inorder = {};
        BTree.Node result = bTree.buildTree(preorder, inorder);
        assertNull(result, "空数组应该返回null");
    }

    /**
     * 测试根据前序和中序遍历构建树 - null输入
     */
    @Test
    void testBuildTree_NullInput() {
        BTree.Node result1 = bTree.buildTree(null, new int[]{1, 2, 3});
        BTree.Node result2 = bTree.buildTree(new int[]{1, 2, 3}, null);
        BTree.Node result3 = bTree.buildTree(null, null);
        
        assertNull(result1, "前序数组为null应该返回null");
        assertNull(result2, "中序数组为null应该返回null");
        assertNull(result3, "两个数组都为null应该返回null");
    }

    /**
     * 测试根据前序和中序遍历构建树 - 长度不匹配
     */
    @Test
    void testBuildTree_LengthMismatch() {
        int[] preorder = {1, 2, 3};
        int[] inorder = {1, 2};
        BTree.Node result = bTree.buildTree(preorder, inorder);
        assertNull(result, "长度不匹配的数组应该返回null");
    }

    /**
     * 测试根据前序和中序遍历构建树 - 单节点
     */
    @Test
    void testBuildTree_SingleNode() {
        int[] preorder = {1};
        int[] inorder = {1};
        BTree.Node result = bTree.buildTree(preorder, inorder);
        
        assertNotNull(result, "应该成功构建单节点树");
        assertEquals(1, result.val, "根节点值应该正确");
        assertNull(result.left, "左子节点应该为null");
        assertNull(result.right, "右子节点应该为null");
    }

    /**
     * 测试根据前序和中序遍历构建树 - 完全二叉树
     */
    @Test
    void testBuildTree_CompleteBinaryTree() {
        // 前序：根-左-右 = [3, 9, 20, 15, 7]
        // 中序：左-根-右 = [9, 3, 15, 20, 7]
        // 对应的树：
        //     3
        //    / \
        //   9  20
        //     /  \
        //    15   7
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        BTree.Node result = bTree.buildTree(preorder, inorder);
        
        assertNotNull(result, "应该成功构建树");
        assertEquals(3, result.val, "根节点值");
        assertEquals(9, result.left.val, "左子节点值");
        assertEquals(20, result.right.val, "右子节点值");
        assertEquals(15, result.right.left.val, "右子节点的左子节点值");
        assertEquals(7, result.right.right.val, "右子节点的右子节点值");
        
        // 验证构建的树的遍历结果
        List<Integer> builtPreorder = bTree.preOrder(result);
        List<Integer> builtInorder = bTree.midOrder(result);
        
        assertEquals(Arrays.asList(3, 9, 20, 15, 7), builtPreorder, "构建的树前序遍历应该匹配");
        assertEquals(Arrays.asList(9, 3, 15, 20, 7), builtInorder, "构建的树中序遍历应该匹配");
    }

    /**
     * 测试根据前序和中序遍历构建树 - 包含重复值（应该抛出异常）
     */
    @Test
    void testBuildTree_WithDuplicates() {
        int[] preorder = {1, 2, 2};
        int[] inorder = {2, 1, 2};
        
        assertThrows(IllegalArgumentException.class, () -> {
            bTree.buildTree(preorder, inorder);
        }, "包含重复值应该抛出IllegalArgumentException");
    }

    /**
     * 测试根据前序和中序遍历构建树（支持重复值版本）
     */
    @Test
    void testBuildTreeWithDuplicates_ValidInput() {
        // 包含重复值的树：
        //     1
        //    / \
        //   2   2
        //  /   / \
        // 3   4   3
        int[] preorder = {1, 2, 3, 2, 4, 3};
        int[] inorder = {3, 2, 1, 4, 2, 3};
        BTree.Node result = bTree.buildTreeWithDuplicates(preorder, inorder);
        
        assertNotNull(result, "应该成功构建包含重复值的树");
        assertEquals(1, result.val, "根节点值");
        assertEquals(2, result.left.val, "左子节点值");
        assertEquals(2, result.right.val, "右子节点值");
        
        // 验证构建的树的遍历结果
        List<Integer> builtPreorder = bTree.preOrder(result);
        List<Integer> builtInorder = bTree.midOrder(result);
        
        assertEquals(Arrays.asList(1, 2, 3, 2, 4, 3), builtPreorder, "构建的树前序遍历应该匹配");
        assertEquals(Arrays.asList(3, 2, 1, 4, 2, 3), builtInorder, "构建的树中序遍历应该匹配");
    }

    /**
     * 测试根据前序和中序遍历构建树（支持重复值版本）- 空输入
     */
    @Test
    void testBuildTreeWithDuplicates_EmptyInput() {
        int[] preorder = {};
        int[] inorder = {};
        BTree.Node result = bTree.buildTreeWithDuplicates(preorder, inorder);
        assertNull(result, "空数组应该返回null");
    }

    // ==================== Node链式方法测试 ====================

    /**
     * 测试Node的链式方法 - left(int val)
     */
    @Test
    void testNodeChaining_LeftWithValue() {
        BTree.Node root = new BTree.Node(1);
        BTree.Node leftChild = root.left(2);
        
        assertEquals(2, root.left.val, "左子节点值应该正确设置");
        assertEquals(2, leftChild.val, "返回的左子节点值应该正确");
        assertSame(root.left, leftChild, "返回的应该是新创建的左子节点");
    }

    /**
     * 测试Node的链式方法 - right(int val)
     */
    @Test
    void testNodeChaining_RightWithValue() {
        BTree.Node root = new BTree.Node(1);
        BTree.Node rightChild = root.right(3);
        
        assertEquals(3, root.right.val, "右子节点值应该正确设置");
        assertEquals(3, rightChild.val, "返回的右子节点值应该正确");
        assertSame(root.right, rightChild, "返回的应该是新创建的右子节点");
    }

    /**
     * 测试Node的链式方法 - left(Node node)
     */
    @Test
    void testNodeChaining_LeftWithNode() {
        BTree.Node root = new BTree.Node(1);
        BTree.Node existingNode = new BTree.Node(2);
        BTree.Node returnedNode = root.left(existingNode);
        
        assertSame(existingNode, root.left, "左子节点应该是传入的节点");
        assertSame(root, returnedNode, "应该返回当前节点本身");
    }

    /**
     * 测试Node的链式方法 - right(Node node)
     */
    @Test
    void testNodeChaining_RightWithNode() {
        BTree.Node root = new BTree.Node(1);
        BTree.Node existingNode = new BTree.Node(3);
        BTree.Node returnedNode = root.right(existingNode);
        
        assertSame(existingNode, root.right, "右子节点应该是传入的节点");
        assertSame(root, returnedNode, "应该返回当前节点本身");
    }

    /**
     * 测试Node的链式方法 - up()
     */
    @Test
    void testNodeChaining_Up() {
        BTree.Node root = new BTree.Node(1);
        BTree.Node returnedNode = root.up();
        
        assertSame(root, returnedNode, "up()方法应该返回当前节点本身（简化实现）");
    }

    /**
     * 测试Node的复杂链式调用
     */
    @Test
    void testNodeChaining_ComplexChaining() {
        BTree.Node root = new BTree.Node(1);
        
        // 测试链式构建：1 -> left(2) -> left(4), right(5)
        root.left(2).left(4);
        root.left.right(5);
        
        assertEquals(2, root.left.val, "左子节点值");
        assertEquals(4, root.left.left.val, "左子节点的左子节点值");
        assertEquals(5, root.left.right.val, "左子节点的右子节点值");
    }

    // ==================== TreeBuilder测试 ====================

    /**
     * 测试TreeBuilder基本功能
     */
    @Test
    void testTreeBuilder_BasicFunctionality() {
        BTree.Node root = BTree.tree(1)
            .left(2)
            .right(3)
            .build();
        
        assertEquals(1, root.val, "根节点值");
        assertEquals(2, root.left.val, "左子节点值");
        assertEquals(3, root.right.val, "右子节点值");
    }

    /**
     * 测试TreeBuilder复杂构建
     */
    @Test
    void testTreeBuilder_ComplexBuilding() {
        BTree.Node leftSubtree = BTree.tree(2)
            .left(4)
            .right(5)
            .build();
        
        BTree.Node root = BTree.tree(1)
            .left(leftSubtree)
            .right(BTree.tree(3)
                .left(6)
                .right(7))
            .build();
        
        assertEquals(1, root.val, "根节点值");
        assertEquals(2, root.left.val, "左子树根节点值");
        assertEquals(4, root.left.left.val, "左子树左子节点值");
        assertEquals(5, root.left.right.val, "左子树右子节点值");
        assertEquals(3, root.right.val, "右子树根节点值");
        assertEquals(6, root.right.left.val, "右子树左子节点值");
        assertEquals(7, root.right.right.val, "右子树右子节点值");
    }

    /**
     * 测试TreeBuilder的leftChild和rightChild方法
     */
    @Test
    void testTreeBuilder_ChildMethods() {
        BTree.TreeBuilder leftChildBuilder = BTree.tree(1).leftChild(2);
        BTree.TreeBuilder rightChildBuilder = BTree.tree(1).rightChild(3);
        
        BTree.Node leftChild = leftChildBuilder.build();
        BTree.Node rightChild = rightChildBuilder.build();
        
        assertEquals(2, leftChild.val, "leftChild方法应该返回以左子节点为根的构建器");
        assertEquals(3, rightChild.val, "rightChild方法应该返回以右子节点为根的构建器");
    }

    // ==================== 综合测试 ====================

    /**
     * 综合测试：验证所有遍历方法在同一棵树上的结果
     */
    @Test
    void testAllTraversalMethods_SameTree() {
        // 构建测试树
        BTree.Node root = BTree.tree(4)
            .left(BTree.tree(2)
                .left(new BTree.Node(1))
                .right(new BTree.Node(3)))
            .right(BTree.tree(6)
                .left(new BTree.Node(5))
                .right(new BTree.Node(7)))
            .build();

        // 获取所有遍历结果
        List<Integer> preOrderResult = bTree.preOrder(root);
        List<Integer> preOrderNonRecResult = bTree.preOrderNonRecursive(root);
        List<Integer> midOrderResult = bTree.midOrder(root);
        List<Integer> midOrderNonRecResult = bTree.midOrderNonRecursive(root);
        List<Integer> postOrderResult = bTree.postOrder(root);
        List<Integer> postOrderNonRecResult = bTree.postOrderNonRecursive(root);
        List<Integer> levelOrderResult = bTree.levelOrder(root);
        List<Integer> levelOrderRecResult = bTree.levelOrderRecursive(root);
        
        // 验证递归与非递归版本的一致性
        assertEquals(preOrderResult, preOrderNonRecResult, "前序遍历递归与非递归结果一致");
        assertEquals(midOrderResult, midOrderNonRecResult, "中序遍历递归与非递归结果一致");
        assertEquals(postOrderResult, postOrderNonRecResult, "后序遍历递归与非递归结果一致");
        assertEquals(levelOrderResult, levelOrderRecResult, "层序遍历递归与非递归结果一致");
        
        // 验证各种遍历的预期结果
        assertEquals(Arrays.asList(4, 2, 1, 3, 6, 5, 7), preOrderResult, "前序遍历结果");
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), midOrderResult, "中序遍历结果（有序）");
        assertEquals(Arrays.asList(1, 3, 2, 5, 7, 6, 4), postOrderResult, "后序遍历结果");
        assertEquals(Arrays.asList(4, 2, 6, 1, 3, 5, 7), levelOrderResult, "层序遍历结果");
    }
}