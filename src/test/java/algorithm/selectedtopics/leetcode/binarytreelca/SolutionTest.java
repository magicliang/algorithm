package algorithm.selectedtopics.leetcode.binarytreelca;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 二叉树最近公共祖先算法的完整测试套件
 * 
 * 测试覆盖场景：
 * 1. 基本功能测试 - 正常情况下的最近公共祖先查找
 * 2. 边界条件测试 - 空树、单节点、根节点等特殊情况
 * 3. 特殊位置测试 - p或q是根节点、p是q的祖先等情况
 * 4. 复杂树结构测试 - 深层嵌套、不平衡树等
 * 5. 错误输入测试 - 节点不在树中的情况
 */
public class SolutionTest {
    
    private Solution solution;
    
    @BeforeEach
    void setUp() {
        solution = new Solution();
    }
    
    // ==================== 辅助方法 ====================
    
    /**
     * 创建简单的测试树
     *       3
     *      / \
     *     5   1
     *    / \ / \
     *   6  2 0  8
     *     / \
     *    7   4
     */
    private TreeNode createTestTree() {
        TreeNode node7 = new TreeNode(null, null, 7);
        TreeNode node4 = new TreeNode(null, null, 4);
        TreeNode node6 = new TreeNode(null, null, 6);
        TreeNode node2 = new TreeNode(node7, node4, 2);
        TreeNode node0 = new TreeNode(null, null, 0);
        TreeNode node8 = new TreeNode(null, null, 8);
        TreeNode node5 = new TreeNode(node6, node2, 5);
        TreeNode node1 = new TreeNode(node0, node8, 1);
        TreeNode root = new TreeNode(node5, node1, 3);
        
        return root;
    }
    
    /**
     * 在树中查找指定值的节点
     */
    private TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.getVal() == val) return root;
        
        TreeNode left = findNode(root.getLeft(), val);
        if (left != null) return left;
        
        return findNode(root.getRight(), val);
    }
    
    /**
     * 创建单节点树
     */
    private TreeNode createSingleNodeTree(int val) {
        return new TreeNode(null, null, val);
    }
    
    /**
     * 创建线性树（只有左子树）
     *   1
     *  /
     * 2
     * /
     *3
     */
    private TreeNode createLinearTree() {
        TreeNode node3 = new TreeNode(null, null, 3);
        TreeNode node2 = new TreeNode(node3, null, 2);
        TreeNode node1 = new TreeNode(node2, null, 1);
        return node1;
    }
    
    // ==================== 基本功能测试 ====================
    
    @Test
    void testLowestCommonAncestor_BasicCase() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 5);
        TreeNode q = findNode(root, 1);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(3, result.getVal(), "节点5和节点1的最近公共祖先应该是节点3");
    }
    
    @Test
    void testLowestCommonAncestor_SameSubtree() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 7);
        TreeNode q = findNode(root, 4);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(2, result.getVal(), "节点7和节点4的最近公共祖先应该是节点2");
    }
    
    @Test
    void testLowestCommonAncestor_DeepNodes() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 6);
        TreeNode q = findNode(root, 4);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(5, result.getVal(), "节点6和节点4的最近公共祖先应该是节点5");
    }
    
    // ==================== 边界条件测试 ====================
    
    @Test
    void testLowestCommonAncestor_NullRoot() {
        TreeNode p = new TreeNode(null, null, 1);
        TreeNode q = new TreeNode(null, null, 2);
        
        TreeNode result = solution.lowestCommonAncestor(null, p, q);
        
        assertNull(result, "空树的最近公共祖先应该为null");
    }
    
    @Test
    void testLowestCommonAncestor_SingleNode() {
        TreeNode root = createSingleNodeTree(1);
        TreeNode p = root;
        TreeNode q = root;
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.getVal(), "单节点树中，节点自己就是最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_TwoNodeTree() {
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode left = new TreeNode(null, null, 2);
        root.setLeft(left);
        
        TreeNode result = solution.lowestCommonAncestor(root, root, left);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.getVal(), "根节点应该是自己和子节点的最近公共祖先");
    }
    
    // ==================== 特殊位置测试 ====================
    
    @Test
    void testLowestCommonAncestor_PIsRoot() {
        TreeNode root = createTestTree();
        TreeNode p = root; // 根节点
        TreeNode q = findNode(root, 7);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(3, result.getVal(), "当p是根节点时，根节点就是最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_QIsRoot() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 8);
        TreeNode q = root; // 根节点
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(3, result.getVal(), "当q是根节点时，根节点就是最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_PIsAncestorOfQ() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 5); // 节点5
        TreeNode q = findNode(root, 7); // 节点7，是节点5的后代
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(5, result.getVal(), "当p是q的祖先时，p就是最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_QIsAncestorOfP() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 4); // 节点4
        TreeNode q = findNode(root, 2); // 节点2，是节点4的祖先
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(2, result.getVal(), "当q是p的祖先时，q就是最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_SameNode() {
        TreeNode root = createTestTree();
        TreeNode node = findNode(root, 5);
        
        TreeNode result = solution.lowestCommonAncestor(root, node, node);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(5, result.getVal(), "同一个节点的最近公共祖先是它自己");
    }
    
    // ==================== 复杂树结构测试 ====================
    
    @Test
    void testLowestCommonAncestor_LinearTree() {
        TreeNode root = createLinearTree();
        TreeNode p = findNode(root, 1);
        TreeNode q = findNode(root, 3);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(1, result.getVal(), "线性树中，根节点是所有节点的最近公共祖先");
    }
    
    @Test
    void testLowestCommonAncestor_LinearTreeMiddleNodes() {
        TreeNode root = createLinearTree();
        TreeNode p = findNode(root, 2);
        TreeNode q = findNode(root, 3);
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(2, result.getVal(), "线性树中，上层节点是下层节点的祖先");
    }
    
    /**
     * 创建完全二叉树进行测试
     *       1
     *      / \
     *     2   3
     *    / \ / \
     *   4  5 6  7
     */
    @Test
    void testLowestCommonAncestor_CompleteBinaryTree() {
        TreeNode node4 = new TreeNode(null, null, 4);
        TreeNode node5 = new TreeNode(null, null, 5);
        TreeNode node6 = new TreeNode(null, null, 6);
        TreeNode node7 = new TreeNode(null, null, 7);
        TreeNode node2 = new TreeNode(node4, node5, 2);
        TreeNode node3 = new TreeNode(node6, node7, 3);
        TreeNode root = new TreeNode(node2, node3, 1);
        
        // 测试不同子树的节点
        TreeNode result1 = solution.lowestCommonAncestor(root, node4, node6);
        assertEquals(1, result1.getVal(), "不同子树节点的最近公共祖先应该是根节点");
        
        // 测试同一子树的节点
        TreeNode result2 = solution.lowestCommonAncestor(root, node4, node5);
        assertEquals(2, result2.getVal(), "同一子树节点的最近公共祖先应该是子树根节点");
    }
    
    // ==================== 使用TreeBuilder的测试 ====================
    
    @Test
    void testLowestCommonAncestor_UsingTreeBuilder() {
        TreeBuilder.TreeRet treeRet = TreeBuilder.build();
        TreeNode root = treeRet.getRoot();
        TreeNode p = treeRet.getP();
        TreeNode q = treeRet.getQ();
        
        TreeNode result = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(3, result.getVal(), "使用TreeBuilder构建的树，节点5和节点1的最近公共祖先应该是节点3");
    }
    
    // ==================== 性能和压力测试 ====================
    
    /**
     * 创建深度较大的树进行性能测试
     */
    @Test
    void testLowestCommonAncestor_DeepTree() {
        // 创建深度为10的右偏树
        TreeNode root = new TreeNode(null, null, 1);
        TreeNode current = root;
        TreeNode deepestNode = null;
        
        for (int i = 2; i <= 10; i++) {
            TreeNode newNode = new TreeNode(null, null, i);
            current.setRight(newNode);
            current = newNode;
            if (i == 10) {
                deepestNode = newNode;
            }
        }
        
        // 在中间添加一个左子节点
        TreeNode middleNode = findNode(root, 5);
        TreeNode leftChild = new TreeNode(null, null, 15);
        middleNode.setLeft(leftChild);
        
        TreeNode result = solution.lowestCommonAncestor(root, leftChild, deepestNode);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(5, result.getVal(), "深树中的最近公共祖先查找应该正确");
    }
    
    // ==================== 边界值测试 ====================
    
    @Test
    void testLowestCommonAncestor_NegativeValues() {
        TreeNode root = new TreeNode(null, null, -1);
        TreeNode left = new TreeNode(null, null, -2);
        TreeNode right = new TreeNode(null, null, -3);
        root.setLeft(left);
        root.setRight(right);
        
        TreeNode result = solution.lowestCommonAncestor(root, left, right);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(-1, result.getVal(), "负值节点的最近公共祖先查找应该正确");
    }
    
    @Test
    void testLowestCommonAncestor_ZeroValues() {
        TreeNode root = new TreeNode(null, null, 0);
        TreeNode left = new TreeNode(null, null, 0);
        TreeNode right = new TreeNode(null, null, 0);
        root.setLeft(left);
        root.setRight(right);
        
        TreeNode result = solution.lowestCommonAncestor(root, left, right);
        
        assertNotNull(result, "结果不应该为null");
        assertEquals(0, result.getVal(), "零值节点的最近公共祖先查找应该正确");
    }
    
    // ==================== 算法正确性验证 ====================
    
    /**
     * 验证算法的基本性质：
     * 1. LCA(p, q) 是 p 和 q 的公共祖先
     * 2. LCA(p, q) 是所有公共祖先中最深的一个
     */
    @Test
    void testLowestCommonAncestor_AlgorithmProperties() {
        TreeNode root = createTestTree();
        TreeNode p = findNode(root, 7);
        TreeNode q = findNode(root, 8);
        
        TreeNode lca = solution.lowestCommonAncestor(root, p, q);
        
        assertNotNull(lca, "LCA不应该为null");
        assertEquals(3, lca.getVal(), "节点7和节点8的LCA应该是根节点3");
        
        // 验证LCA确实是公共祖先
        assertTrue(isAncestor(lca, p), "LCA应该是p的祖先");
        assertTrue(isAncestor(lca, q), "LCA应该是q的祖先");
    }
    
    /**
     * 辅助方法：检查ancestor是否是descendant的祖先
     */
    private boolean isAncestor(TreeNode ancestor, TreeNode descendant) {
        if (ancestor == null || descendant == null) return false;
        if (ancestor == descendant) return true;
        
        return isAncestor(ancestor.getLeft(), descendant) || 
               isAncestor(ancestor.getRight(), descendant);
    }
    
    // ==================== 综合测试 ====================
    
    @Test
    void testLowestCommonAncestor_ComprehensiveTest() {
        TreeNode root = createTestTree();
        
        // 测试多组不同的节点对
        int[][] testCases = {
            {5, 1, 3},  // 不同子树
            {6, 2, 5},  // 同一子树
            {7, 4, 2},  // 兄弟节点
            {0, 8, 1},  // 兄弟节点
            {6, 7, 5},  // 表兄弟节点
            {3, 5, 3},  // 根节点和子节点
            {2, 7, 2}   // 父节点和子节点
        };
        
        for (int[] testCase : testCases) {
            TreeNode p = findNode(root, testCase[0]);
            TreeNode q = findNode(root, testCase[1]);
            int expectedLCA = testCase[2];
            
            TreeNode result = solution.lowestCommonAncestor(root, p, q);
            
            assertNotNull(result, 
                String.format("节点%d和节点%d的LCA不应该为null", testCase[0], testCase[1]));
            assertEquals(expectedLCA, result.getVal(), 
                String.format("节点%d和节点%d的LCA应该是节点%d", testCase[0], testCase[1], expectedLCA));
        }
    }
}