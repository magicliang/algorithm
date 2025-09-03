package algorithm.selectedtopics.leetcode.binarytreelca;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 二叉树最近公共祖先问题测试
 * 
 * 测试覆盖范围：
 * - 基本功能测试
 * - 边界条件测试
 * - 特殊情况测试
 * 
 * @author magicliang
 * @date 2025-09-03
 */
@DisplayName("二叉树最近公共祖先测试")
class SolutionTest {

    private Solution solution;
    private TreeBuilder.TreeRet treeRet;

    @BeforeEach
    void setUp() {
        solution = new Solution();
        treeRet = TreeBuilder.build();
    }

    @Test
    @DisplayName("基本功能测试 - 使用预构建的树")
    void testLowestCommonAncestorBasic() {
        TreeNode root = treeRet.getRoot();
        TreeNode p = treeRet.getP(); // node5
        TreeNode q = treeRet.getQ(); // node1
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "最近公共祖先不应该为null");
        assertEquals(3, result.getVal(), "最近公共祖先应该是根节点3");
    }

    @Test
    @DisplayName("空树测试")
    void testLowestCommonAncestorNullRoot() {
        TreeNode result = solution.lowestCommonAncestor(null, null, null);
        assertNull(result, "空树的最近公共祖先应该为null");
    }

    @Test
    @DisplayName("单节点树测试")
    void testLowestCommonAncestorSingleNode() {
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode result = solution.lowestCommonAncestor(root, root, root);
        
        assertNotNull(result, "单节点树的最近公共祖先不应该为null");
        assertEquals(1, result.getVal(), "单节点树的最近公共祖先应该是自己");
    }

    @Test
    @DisplayName("其中一个节点是根节点")
    void testLowestCommonAncestorOneIsRoot() {
        TreeNode root = treeRet.getRoot();
        TreeNode p = root; // 根节点3
        TreeNode q = treeRet.getP(); // node5
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "最近公共祖先不应该为null");
        assertEquals(3, result.getVal(), "当其中一个节点是根节点时，最近公共祖先应该是根节点");
    }

    @Test
    @DisplayName("两个节点在同一子树中")
    void testLowestCommonAncestorSameSubtree() {
        // 构建一个更复杂的树来测试同一子树的情况
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode left = new TreeNode(null, null, 2);
        TreeNode right = new TreeNode(null, null, 3);
        TreeNode leftLeft = new TreeNode(null, null, 4);
        TreeNode leftRight = new TreeNode(null, null, 5);
        
        root.setLeft(left);
        root.setRight(right);
        left.setLeft(leftLeft);
        left.setRight(leftRight);
        
        TreeNode result = solution.lowestCommonAncestor(root, leftLeft, leftRight);
        
        assertNotNull(result, "最近公共祖先不应该为null");
        assertEquals(2, result.getVal(), "同一子树中两个节点的最近公共祖先应该是它们的父节点");
    }

    @Test
    @DisplayName("两个节点分别在左右子树中")
    void testLowestCommonAncestorDifferentSubtrees() {
        // 构建测试树
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode left = new TreeNode(null, null, 2);
        TreeNode right = new TreeNode(null, null, 3);
        TreeNode leftLeft = new TreeNode(null, null, 4);
        TreeNode rightRight = new TreeNode(null, null, 5);
        
        root.setLeft(left);
        root.setRight(right);
        left.setLeft(leftLeft);
        right.setRight(rightRight);
        
        TreeNode result = solution.lowestCommonAncestor(root, leftLeft, rightRight);
        
        assertNotNull(result, "最近公共祖先不应该为null");
        assertEquals(1, result.getVal(), "分别在左右子树的两个节点的最近公共祖先应该是根节点");
    }

    @Test
    @DisplayName("深层嵌套测试")
    void testLowestCommonAncestorDeepNesting() {
        // 构建深层嵌套的树
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode current = root;
        
        // 构建左侧链
        for (int i = 2; i <= 5; i++) {
            TreeNode left = new TreeNode(null, null, i);
            current.setLeft(left);
            current = left;
        }
        
        // 在第3层添加右子树
        TreeNode node3 = root.getLeft(); // 值为2的节点
        TreeNode rightSubtree = new TreeNode(null, null, 10);
        node3.setRight(rightSubtree);
        
        // 测试深层节点和右子树节点的最近公共祖先
        TreeNode deepNode = current; // 值为5的节点
        TreeNode result = solution.lowestCommonAncestor(root, deepNode, rightSubtree);
        
        assertNotNull(result, "最近公共祖先不应该为null");
        assertEquals(2, result.getVal(), "深层嵌套情况下的最近公共祖先应该正确");
    }

    @Test
    @DisplayName("相同节点测试")
    void testLowestCommonAncestorSameNode() {
        TreeNode root = treeRet.getRoot();
        TreeNode p = treeRet.getP();
        
        TreeNode result = solution.lowestCommonAncestor(root, p, p);
        
        assertNotNull(result, "相同节点的最近公共祖先不应该为null");
        assertEquals(p.getVal(), result.getVal(), "相同节点的最近公共祖先应该是节点本身");
    }

    @Test
    @DisplayName("父子关系测试")
    void testLowestCommonAncestorParentChild() {
        // 构建父子关系的树
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode child = new TreeNode(null, null, 2);
        TreeNode grandchild = new TreeNode(null, null, 3);
        
        root.setLeft(child);
        child.setLeft(grandchild);
        
        TreeNode result = solution.lowestCommonAncestor(root, child, grandchild);
        
        assertNotNull(result, "父子关系的最近公共祖先不应该为null");
        assertEquals(2, result.getVal(), "父子关系中，最近公共祖先应该是父节点");
    }

    @Test
    @DisplayName("复杂树结构测试")
    void testLowestCommonAncestorComplexTree() {
        /*
         * 构建如下树结构：
         *       1
         *      / \
         *     2   3
         *    / \   \
         *   4   5   6
         *  /   / \
         * 7   8   9
         */
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode node2 = new TreeNode(null, null, 2);
        TreeNode node3 = new TreeNode(null, null, 3);
        TreeNode node4 = new TreeNode(null, null, 4);
        TreeNode node5 = new TreeNode(null, null, 5);
        TreeNode node6 = new TreeNode(null, null, 6);
        TreeNode node7 = new TreeNode(null, null, 7);
        TreeNode node8 = new TreeNode(null, null, 8);
        TreeNode node9 = new TreeNode(null, null, 9);
        
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setRight(node6);
        node4.setLeft(node7);
        node5.setLeft(node8);
        node5.setRight(node9);
        
        // 测试多种情况
        assertEquals(2, solution.lowestCommonAncestor(root, node7, node8).getVal(), "节点7和8的最近公共祖先应该是2");
        assertEquals(5, solution.lowestCommonAncestor(root, node8, node9).getVal(), "节点8和9的最近公共祖先应该是5");
        assertEquals(1, solution.lowestCommonAncestor(root, node7, node6).getVal(), "节点7和6的最近公共祖先应该是1");
        assertEquals(1, solution.lowestCommonAncestor(root, node4, node3).getVal(), "节点4和3的最近公共祖先应该是1");
    }
}