package algorithm.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 基础二叉树
 * 
 * 二叉树遍历的核心原理：
 * 1. DFS（深度优先搜索）本质上使用栈结构，BFS（广度优先搜索）本质上使用队列结构
 * 2. 递归实现的DFS利用系统调用栈；非递归实现需要显式使用栈数据结构
 * 3. 标准BFS使用队列按层级顺序处理节点；非队列实现需要额外的状态管理：
 *    - 可以使用递归+层级参数的方式（如levelOrderRecursive方法）
 *    - 需要显式保存层级信息、访问状态等辅助数据
 *    - 时间复杂度通常会从O(n)增加到O(n²)
 *
 * @author magicliang
 *
 *         date: 2025-08-12 21:21
 */
public class BTree {

    public static class Node {

        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        /**
         * 链式设置左子节点
         * 
         * 功能说明：
         * 1. 为当前节点创建并设置左子节点
         * 2. 返回新创建的左子节点，支持链式调用
         * 3. 适用于动态构建二叉树的场景
         * 
         * 使用示例：
         * Node root = new Node(1);
         * root.left(2).left(4);  // 创建 1->2->4 的左子树链
         * 
         * 注意事项：
         * - 如果当前节点已有左子节点，会被新节点覆盖
         * - 返回的是新创建的左子节点，不是当前节点
         * 
         * @param val 左子节点的值
         * @return 新创建的左子节点，支持继续链式调用
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public Node left(int val) {
            this.left = new Node(val);
            return this.left;
        }

        /**
         * 链式设置右子节点
         * 
         * 功能说明：
         * 1. 为当前节点创建并设置右子节点
         * 2. 返回新创建的右子节点，支持链式调用
         * 3. 适用于动态构建二叉树的场景
         * 
         * 使用示例：
         * Node root = new Node(1);
         * root.right(3).right(7);  // 创建 1->3->7 的右子树链
         * 
         * 注意事项：
         * - 如果当前节点已有右子节点，会被新节点覆盖
         * - 返回的是新创建的右子节点，不是当前节点
         * 
         * @param val 右子节点的值
         * @return 新创建的右子节点，支持继续链式调用
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public Node right(int val) {
            this.right = new Node(val);
            return this.right;
        }

        /**
         * 链式设置左子节点为已有节点
         * 
         * 功能说明：
         * 1. 将已存在的节点设置为当前节点的左子节点
         * 2. 返回当前节点本身，支持继续在当前节点上进行链式调用
         * 3. 适用于将已构建的子树连接到当前节点的场景
         * 
         * 使用示例：
         * Node leftSubtree = new Node(2);
         * Node root = new Node(1).left(leftSubtree).right(3);
         * 
         * 与 left(int val) 的区别：
         * - left(int val)：创建新节点并返回新节点
         * - left(Node node)：使用已有节点并返回当前节点
         * 
         * 注意事项：
         * - 如果传入null，会将左子节点设置为null
         * - 不会检查是否会形成环路，使用时需注意
         * 
         * @param node 要设置为左子节点的已有节点
         * @return 当前节点本身，支持继续链式调用
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public Node left(Node node) {
            this.left = node;
            return this;
        }

        /**
         * 链式设置右子节点为已有节点
         * 
         * 功能说明：
         * 1. 将已存在的节点设置为当前节点的右子节点
         * 2. 返回当前节点本身，支持继续在当前节点上进行链式调用
         * 3. 适用于将已构建的子树连接到当前节点的场景
         * 
         * 使用示例：
         * Node rightSubtree = new Node(3);
         * Node root = new Node(1).left(2).right(rightSubtree);
         * 
         * 与 right(int val) 的区别：
         * - right(int val)：创建新节点并返回新节点
         * - right(Node node)：使用已有节点并返回当前节点
         * 
         * 注意事项：
         * - 如果传入null，会将右子节点设置为null
         * - 不会检查是否会形成环路，使用时需注意
         * 
         * @param node 要设置为右子节点的已有节点
         * @return 当前节点本身，支持继续链式调用
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public Node right(Node node) {
            this.right = node;
            return this;
        }

        /**
         * 返回父节点，用于链式调用的回溯
         * 
         * 功能说明：
         * 1. 理论上应该返回当前节点的父节点，用于链式调用中的回溯
         * 2. 当前简化实现中返回this，实际使用中需要外部维护父节点引用
         * 3. 设计目的是支持复杂的链式构建模式
         * 
         * 理想使用场景：
         * Node root = new Node(1)
         *     .left(2).left(4).up().right(5).up()  // 回到节点2
         *     .up()  // 回到根节点1
         *     .right(3);
         * 
         * 当前实现的局限性：
         * - 没有维护父节点引用，无法实现真正的回溯
         * - 返回this只是占位实现，实际效果有限
         * - 如需完整实现，需要在Node中添加parent字段
         * 
         * 改进建议：
         * 1. 添加parent字段：private Node parent;
         * 2. 在设置子节点时同时设置parent引用
         * 3. up()方法返回parent != null ? parent : this;
         * 
         * @return 当前实现返回this（简化版本），完整实现应返回父节点
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public Node up() {
            // 注意：这里需要外部维护父节点引用，简化实现中返回this
            return this;
        }
    }

    /**
     * 二叉树构建器 - 提供可读性强的构造方式
     */
    public static class TreeBuilder {

        private final Node root;

        public TreeBuilder(int rootVal) {
            this.root = new Node(rootVal);
        }

        public static TreeBuilder create(int rootVal) {
            return new TreeBuilder(rootVal);
        }

        /**
         * 设置左子树
         * 示例：left(2).left(4).right(5)
         */
        public TreeBuilder left(int val) {
            root.left = new Node(val);
            return this;
        }

        /**
         * 设置右子树
         * 示例：right(3).left(6).right(7)
         */
        public TreeBuilder right(int val) {
            root.right = new Node(val);
            return this;
        }

        /**
         * 设置左子树为已有节点
         */
        public TreeBuilder left(Node node) {
            root.left = node;
            return this;
        }

        /**
         * 设置右子树为已有节点
         */
        public TreeBuilder right(Node node) {
            root.right = node;
            return this;
        }

        /**
         * 设置左子树为子构建器
         */
        public TreeBuilder left(TreeBuilder leftBuilder) {
            root.left = leftBuilder.build();
            return this;
        }

        /**
         * 设置右子树为子构建器
         */
        public TreeBuilder right(TreeBuilder rightBuilder) {
            root.right = rightBuilder.build();
            return this;
        }

        /**
         * 获取根节点
         */
        public Node build() {
            return root;
        }

        /**
         * 链式设置左子节点并返回子构建器
         * 
         * 功能说明：
         * 1. 为当前根节点创建左子节点
         * 2. 返回一个新的TreeBuilder，以新创建的左子节点为根
         * 3. 支持在子树上继续进行构建操作
         * 
         * 使用场景：
         * - 需要在子节点上继续构建复杂子树的情况
         * - 适合构建深层嵌套的树结构
         * 
         * 使用示例：
         * TreeBuilder builder = TreeBuilder.create(1)
         *     .leftChild(2)      // 返回以节点2为根的构建器
         *         .left(4)       // 在节点2上添加左子节点4
         *         .right(5);     // 在节点2上添加右子节点5
         * 
         * 与 left(int val) 的区别：
         * - left(int val)：返回当前构建器，继续在当前根上操作
         * - leftChild(int val)：返回新构建器，转向子节点操作
         * 
         * 注意事项：
         * - 返回的是新的TreeBuilder实例，与原构建器无关
         * - 无法直接回到父节点的构建器，需要重新引用
         * 
         * @param val 左子节点的值
         * @return 以新创建的左子节点为根的TreeBuilder
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public TreeBuilder leftChild(int val) {
            root.left = new Node(val);
            return new TreeBuilder(val);
        }

        /**
         * 链式设置右子节点并返回子构建器
         * 
         * 功能说明：
         * 1. 为当前根节点创建右子节点
         * 2. 返回一个新的TreeBuilder，以新创建的右子节点为根
         * 3. 支持在子树上继续进行构建操作
         * 
         * 使用场景：
         * - 需要在子节点上继续构建复杂子树的情况
         * - 适合构建深层嵌套的树结构
         * 
         * 使用示例：
         * TreeBuilder builder = TreeBuilder.create(1)
         *     .rightChild(3)     // 返回以节点3为根的构建器
         *         .left(6)       // 在节点3上添加左子节点6
         *         .right(7);     // 在节点3上添加右子节点7
         * 
         * 与 right(int val) 的区别：
         * - right(int val)：返回当前构建器，继续在当前根上操作
         * - rightChild(int val)：返回新构建器，转向子节点操作
         * 
         * 注意事项：
         * - 返回的是新的TreeBuilder实例，与原构建器无关
         * - 无法直接回到父节点的构建器，需要重新引用
         * 
         * @param val 右子节点的值
         * @return 以新创建的右子节点为根的TreeBuilder
         * 
         * 时间复杂度：O(1)
         * 空间复杂度：O(1)
         */
        public TreeBuilder rightChild(int val) {
            root.right = new Node(val);
            return new TreeBuilder(val);
        }
    }

    /**
     * 快速创建树的工具方法
     * 
     * 功能说明：
     * 1. 提供一个简洁的静态方法来创建 TreeBuilder 实例
     * 2. 避免了直接使用 TreeBuilder.create() 的冗余写法
     * 3. 提供更加直观和简洁的 API
     * 
     * 使用场景：
     * - 需要快速创建二叉树的所有情况
     * - 作为测试中构建树的便捷入口
     * - 提供更加友好的 API 设计
     * 
     * 使用示例：
     * // 传统写法
     * TreeBuilder builder = TreeBuilder.create(1);
     * 
     * // 简化写法
     * TreeBuilder builder = BTree.tree(1);
     * 
     * // 链式调用示例
     * Node root = BTree.tree(1)
     *     .left(2)
     *     .right(3)
     *     .build();
     * 
     * 设计优势：
     * - 更简洁的语法：BTree.tree(1) vs TreeBuilder.create(1)
     * - 与类名保持一致，提高可读性
     * - 静态导入时更加方便
     * 
     * @param rootVal 根节点的值
     * @return TreeBuilder 实例，用于继续构建二叉树
     * 
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    public static TreeBuilder tree(int rootVal) {
        return TreeBuilder.create(rootVal);
    }

    public List<Integer> levelOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final Node current = queue.poll();
            result.add(current.val);
            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return result;
    }

    /**
     * 纯递归实现的层序遍历（不使用队列）
     *
     * 算法核心：分层递归下探模式
     * 1. 分层：将树按层分解（1,2,3...层）
     * 2. 递归下探：通过level参数控制下探深度
     * 3. 条件触发：level=1时收集节点值
     *
     * 工作流程：
     * - 先计算树的总层数（最长路径节点数）
     * - 从第1层到第totalLevel层，逐层处理
     * - 每层都从根节点开始递归，level参数控制下探深度
     *
     * 时间复杂度：O(n²) - 每层都从根开始遍历，存在重复计算
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     */
    public List<Integer> levelOrderRecursive(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 计算树的总层数（最长路径上的节点数量，1-based）
        // 例如：单节点树返回1，两层树返回2，以此类推
        int totalLevel = getTotalLevel(root);

        // 逐层处理：从第1层到第totalLevel层
        // 每层都从根节点开始递归，level参数控制下探深度
        for (int level = 1; level <= totalLevel; level++) {
            levelOrderLevel(result, root, level);
        }

        return result;
    }

    /**
     * 计算树的总层数（最长路径上的节点数量）
     *
     * 注意：这里计算的是节点高度（节点数），不是边高度（边数）
     * 定义：从当前节点到最远叶子节点的节点数量
     *
     * @param root 当前子树的根节点
     * @return 以root为根的子树的总层数
     */
    private int getTotalLevel(Node root) {
        if (root == null) { // 易错的点：忘记处理根/子节点为空的情况
            return 0;
        }

        // 叶子节点（无左右子节点）返回1，表示当前层
        if (root.left == null && root.right == null) {
            return 1;
        }

        // 非叶子节点：当前层(1) + 左右子树的最大层数
        return 1 + Math.max(getTotalLevel(root.left), getTotalLevel(root.right));
    }

    /**
     * 递归访问指定层的所有节点
     *
     * 分层递归下探模式的核心实现：
     * - level参数就像"下探指令"，告诉函数还需要向下深入多少层
     * - level=1：到达目标层，当前节点就是要收集的节点
     * - level>1：继续向下传递，level-1传给子节点
     *
     * 形象理解：就像逐层剥洋葱，level就是"剥几层才能看到核心"
     * 每次递归调用level减1，模拟逐层深入的过程
     *
     * @param result 结果收集器
     * @param root 当前子树的根节点
     * @param level 还需要下探的层数（1表示当前层就是要找的层）
     */
    private void levelOrderLevel(List<Integer> result, Node root, int level) {
        if (root == null) {
            return;
        }

        // 分层递归下探的核心逻辑：
        // 当level=1时，表示已经"下探"到了目标层，当前节点就是要收集的节点
        // 当level>1时，需要继续向下传递，让子节点在level-1时处理

        if (level == 1) {
            // 到达目标层，收集当前节点的值
            result.add(root.val);
            return;
        }

        // 继续向下展开：将level-1传给左右子树
        // 就像把"下探指令"传递给下一层工人
        levelOrderLevel(result, root.left, level - 1);
        levelOrderLevel(result, root.right, level - 1);
    }

    /**
     * 前序遍历（根左右）的公共接口
     *
     * 前序遍历顺序：根节点 → 左子树 → 右子树
     * 对于二叉树，访问顺序为：当前节点 → 递归遍历左子树 → 递归遍历右子树
     *
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     *
     * @param root 二叉树的根节点
     * @return 前序遍历结果列表
     */
    public List<Integer> preOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 使用辅助方法进行递归遍历
        preOrder(result, root);
        return result;
    }

    /**
     * 前序遍历的递归辅助方法
     *
     * 递归终止条件：节点为null时返回
     * 递归过程：
     * 1. 访问当前节点（添加到结果列表）
     * 2. 递归遍历左子树
     * 3. 递归遍历右子树
     *
     * @param result 结果收集器
     * @param root 当前子树的根节点
     */
    private void preOrder(List<Integer> result, Node root) {
        if (null == root) {
            return;
        }

        // 根：先访问当前节点
        result.add(root.val);

        // 左：递归遍历左子树
        preOrder(result, root.left);

        // 右：递归遍历右子树
        preOrder(result, root.right);
    }

    /**
     * 非递归前序遍历（根-左-右）实现
     * <p>
     * 算法思路：使用栈模拟递归调用栈，实现深度优先遍历
     * 前序遍历顺序：根节点 → 左子树 → 右子树
     * <p>
     * 实现要点：
     * 1. 使用LinkedList作为栈结构（后进先出）
     * 2. 关键点：由于栈是后进先出，需要先压右子节点，再压左子节点
     * 这样才能保证左子节点先被处理，符合根-左-右的顺序
     * 3. 从根节点开始，每次处理栈顶节点，然后将其右、左子节点压栈
     * <p>
     * 工作流程：
     * 1. 初始化栈，压入根节点
     * 2. 循环直到栈为空：
     * - 弹出栈顶节点并访问
     * - 先压右子节点（后处理）
     * - 再压左子节点（先处理）
     * <p>
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 栈的最大深度为树高h
     *
     * @param root 二叉树的根节点
     * @return 前序遍历结果列表
     */
    public List<Integer> preOrderNonRecursive(Node root) {

        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 使用栈实现前序遍历（根-左-右）
        // 关键点：由于栈是后进先出，需要先压右子节点，再压左子节点
        // 这样才能保证左子节点先被处理
        LinkedList<Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final Node current = stack.pop();
            // 其实这步不一定需要存在，因为不会push null进栈里面
            if (current == null) {
                break;
            }
            result.add(current.val);
            // 注意顺序：先右后左，这样左子节点会先被处理
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return result;
    }

    /**
     * 中序遍历（左根右）的公共接口
     *
     * 中序遍历顺序：左子树 → 根节点 → 右子树
     * 对于二叉搜索树，中序遍历的结果是有序的（升序排列）
     *
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     *
     * @param root 二叉树的根节点
     * @return 中序遍历结果列表
     */
    public List<Integer> midOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (null == root) {
            return result;
        }
        midOrder(root.left, result);
        result.add(root.val);
        midOrder(root.right, result);

        return result;
    }

    /**
     * 中序遍历的递归辅助方法
     *
     * 递归终止条件：节点为null时返回
     * 递归过程：
     * 1. 递归遍历左子树
     * 2. 访问当前节点（添加到结果列表）
     * 3. 递归遍历右子树
     *
     * 对于二叉搜索树，这个过程会按照从小到大的顺序访问所有节点
     *
     * @param root 当前子树的根节点
     * @param result 结果收集器
     */
    private void midOrder(Node root, List<Integer> result) {
        if (null == root) {
            return;
        }
        // 左：递归遍历左子树
        midOrder(root.left, result);

        // 根：访问当前节点
        result.add(root.val);

        // 右：递归遍历右子树
        midOrder(root.right, result);
    }

    /**
     * 非递归中序遍历（左-根-右）实现
     * <p>
     * 算法思路：使用栈模拟递归调用栈，实现深度优先遍历
     * 中序遍历顺序：左子树 → 根节点 → 右子树
     * <p>
     * 实现要点：
     * 1. 使用LinkedList作为栈结构（后进先出）
     * 2. 关键点：需要先将所有左子节点压栈，直到最左叶子节点
     * 3. 处理顺序：弹出栈顶节点（此时该节点的左子树已处理完）→ 访问该节点 → 处理右子树
     * 4. 使用current指针跟踪当前处理的节点，避免重复压栈
     * <p>
     * 工作流程：
     * 1. 初始化栈和current指针（指向根节点）
     * 2. 循环直到栈为空且current为null：
     * - 如果current不为null：压栈并继续向左深入（current = current.left）
     * - 如果current为null：弹出栈顶节点并访问，然后转向右子树（current = current.right）
     * <p>
     * 形象理解：就像沿着左边界一直往下走，走到尽头后回溯，每回溯一步就处理一个节点，然后转向右子树
     * <p>
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 栈的最大深度为树高h，最坏情况下为O(n)（树退化为链表）
     *
     * @param root 二叉树的根节点
     * @return 中序遍历结果列表
     */
    public List<Integer> midOrderNonRecursive(Node root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        LinkedList<Node> stack = new LinkedList<>();
        Node current = root;

        // 核心循环：模拟递归的中序遍历过程
        // 每个节点都是某棵子树的根，处理完左子树后处理根节点，再处理右子树
        while (current != null || !stack.isEmpty()) {
            // 阶段1：沿着左子树一直向下，将所有左子节点压栈
            // 这一步模拟了递归中的"一直向左深入"过程
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                // 阶段2：左子树已处理完，开始回溯
                // 弹出栈顶节点（此时该节点的左子树已全部处理完）
                // 这就是一个回溯动作。对中序遍历而言，回溯一次，就要继续探索右子树
                current = stack.pop();
                // 这个方法的神髓：访问当前节点（根节点），只有被 pop 出来的才处理
                result.add(current.val);

                // 阶段3：转向右子树
                // 每一个节点处理完以后都要考虑先处理自己的右节点，然后才到父节点，父节点已经在栈里了，最终能弹出来
                // 让右子树进入下一轮循环的处理流程
                // 注意：这里不是直接处理右子树，而是让右子树在下一轮循环中
                // 按照"左-根-右"的顺序被处理
                // current 被赋值表示继续探索
                current = current.right;
            }
        }
        return result;
    }

    /**
     * 后序遍历（左-右-根）的公共接口
     *
     * 后序遍历顺序：左子树 → 右子树 → 根节点
     * 对于二叉树，访问顺序为：递归遍历左子树 → 递归遍历右子树 → 访问当前节点
     *
     * 应用场景：
     * 1. 删除二叉树（需要先删除子节点再删除父节点）
     * 2. 计算目录大小（需要先计算子目录大小）
     * 3. 后缀表达式计算
     *
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     *
     * @param root 二叉树的根节点
     * @return 后序遍历结果列表
     */
    public List<Integer> postOrder(Node root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        postOrder(root.left, result);
        postOrder(root.right, result);
        result.add(root.val);
        return result;
    }

    /**
     * 后序遍历的递归辅助方法
     *
     * 递归终止条件：节点为null时返回
     * 递归过程：
     * 1. 递归遍历左子树
     * 2. 递归遍历右子树
     * 3. 访问当前节点（添加到结果列表）
     *
     * 关键理解：只有当一个节点的左右子树都处理完后，才处理该节点本身
     * 这就像"先处理完所有后代，再处理祖先"
     *
     * @param root 当前子树的根节点
     * @param result 结果收集器
     */
    private void postOrder(Node root, List<Integer> result) {
        if (root == null) {
            return;
        }
        // 左：递归遍历左子树
        postOrder(root.left, result);

        // 右：递归遍历右子树
        postOrder(root.right, result);

        // 根：访问当前节点（最后处理）
        result.add(root.val);
    }

    /**
     * 非递归后序遍历（左-右-根）实现
     * <p>
     * 算法思路：使用栈模拟递归的后序遍历过程
     * 后序遍历顺序：左子树 → 右子树 → 根节点
     * <p>
     * 核心思想：只有当一个节点的左右子树都访问过后，才能访问该节点
     * 这就像"先处理完所有后代，再处理祖先"
     * <p>
     * 实现要点：
     * 1. 使用LinkedList作为栈结构
     * 2. 需要记录上次访问的节点，用于判断右子树是否已经处理完
     * 3. 分两个阶段：先一直向左深入，然后决定是继续探索右子树还是回溯处理当前节点
     * <p>
     * 工作流程（通俗易懂版）：
     * 1. 从根节点开始，尽可能向左深入，把所有经过的节点压栈
     * 2. 当无法继续向左时，查看栈顶节点的右子树：
     * - 如果右子树存在且没处理过 → 转向右子树继续探索
     * - 如果右子树不存在或已处理过 → 弹出并访问当前节点
     * 3. 重复上述过程直到栈为空
     * <p>
     * 形象比喻：就像探险一样，先沿着左边的小路一直走到底，
     * 然后回头看看右边有没有没走过的路，有就走右边，没有就标记当前位置已探索
     * <p>
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 栈的最大深度为树高h
     *
     * @param root 二叉树的根节点
     * @return 后序遍历结果列表
     */
    public List<Integer> postOrderNonRecursive(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // current：当前正在探索的节点
        // 关键状态说明：
        // - 当current != null时：表示还有新的节点需要探索，继续向下深入
        // - 当current == null时：表示已经走到尽头，需要考虑回溯问题
        //   此时需要从栈中弹出节点进行处理
        Node current = root;
        LinkedList<Node> stack = new LinkedList<>();

        // lastVisited：记录上次访问的节点，用于判断右子树是否已经处理
        // 这个变量的作用是：当我们回到一个节点时，能知道它的右子树是否已经处理过
        // 如果lastVisited == 当前节点的右子节点，说明右子树已处理完，可以处理当前节点
        // 这个指针的存在提示我们，默认我们要先处理自己的孩子再处理自己，但是等到我们二次访问自己的时候，可以通过这类设计表达孩子是不是被访问过了。
        Node lastVisited = null;

        // 探索和回溯如果没有做完，不退出循环
        // 循环条件：只要还有节点需要处理（current不为空）或者还有节点在栈中等待处理
        while (current != null || !stack.isEmpty()) {
            // 阶段1：尽可能向左深入，把所有经过的节点压栈
            // 这一步就像沿着左边的小路一直往前走
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                // 如果本轮探索完了，要考虑是继续探索右子树，还是直接处理当前根节点

                // peek 的用处这就体现出来了-当然，也可以不嫌麻烦先 push 再 pop
                Node top = stack.peek(); // 和其他栈一样，弹出来的都当作某个根节点
                final Node right = top.right;
                // 如果没有探索过右子树
                if (right != null && lastVisited != right) {
                    // 右子树存在且还没处理过 → 转向右子树继续探索
                    current = right;
                } else {
                    // 如果探索过右子树，则可以处理当前根了：没有右子树要处理了，可能右子树为空，也可能本身是右子树，也可能右子树被处理了
                    // 注意处理是一种消费，此时要弹出
                    top = stack.pop();
                    result.add(top.val);
                    lastVisited = top; // 这时候让这个根成为 lastVisited，如果这个根是某个右子树的根，可以帮助下一轮回溯继续向上，这个根可能是一颗左子树的根，也可能是
                    // 顶部的 root，也可能是右子树的根

                    // 和中序遍历不一样，中序遍历在这一步会继续给 current 赋 right，意味着要对右子树开始探索
                    // 但是这里处理完了以后就是要回溯的，也就意味着不再向下，要向上，所以这里不给 current 赋值，下一轮循环甚至以后的循环都是在这个大 else 里执行了
                }
            }
        }

        return result;
    }

    /**
     * 递归实现的分层层序遍历（返回二维列表）
     *
     * 算法核心：深度递归分层收集模式
     * 1. 深度参数：使用depth参数标识当前节点所在的层级（0-based）
     * 2. 动态扩展：当访问到新层级时，动态创建该层的列表
     * 3. 层级收集：将同一层的节点值收集到对应层级的列表中
     *
     * 工作流程：
     * - 从根节点开始，depth=0
     * - 每向下一层，depth+1
     * - 检查result是否有对应层级的列表，没有则创建
     * - 将当前节点值添加到对应层级的列表中
     * - 递归处理左右子树，传递depth+1
     *
     * 与levelOrderRecursive的区别：
     * - levelOrderRecursive：返回一维列表，需要预先计算层数
     * - levelOrderByDepth：返回二维列表，动态创建层级列表
     *
     * 时间复杂度：O(n) - 每个节点访问一次
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     *
     * @param root 二叉树的根节点
     * @return 分层的层序遍历结果，每一层是一个独立的列表
     */
    public List<List<Integer>> levelOrderByDepth(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        levelOrderHelper(root, result, 0);
        return result;
    }

    /**
     * 递归层序遍历的辅助方法
     *
     * 深度递归分层收集模式的核心实现：
     * - depth参数就像"楼层标识"，告诉函数当前节点在第几层
     * - 动态扩展：当访问到新楼层时，为该楼层创建房间（列表）
     * - 层级收集：将访客（节点值）安排到对应楼层的房间中
     *
     * 算法精髓：
     * 1. 深度优先遍历 + 层级标识 = 分层收集
     * 2. 通过depth参数实现层级区分，避免了预先计算层数的开销
     * 3. 动态扩展result列表，适应任意深度的树结构
     *
     * 形象理解：就像给一栋楼的每个房间分配访客
     * - depth就是楼层号
     * - result就是整栋楼
     * - result.get(depth)就是指定楼层的房间
     * - 每个访客（节点值）都被安排到正确的楼层
     *
     * @param root 当前子树的根节点
     * @param result 分层结果收集器
     * @param depth 当前节点的深度（层级），从0开始
     */
    private void levelOrderHelper(Node root, List<List<Integer>> result, int depth) {
        if (root == null) {
            return;
        }

        // 动态扩展：如果当前深度对应的层级列表还不存在，则创建它
        // 这是一个关键的动态扩展逻辑：
        // - 当depth=0时，创建第0层列表
        // - 当depth=1时，创建第1层列表
        // - 以此类推...
        if (result.size() == depth) {
            result.add(new ArrayList<>());
        }

        // 获取当前层级的列表，并将当前节点值添加进去
        List<Integer> currentLevel = result.get(depth);
        currentLevel.add(root.val);

        // 递归处理左右子树，深度加1
        // 这里体现了深度优先遍历的特点：
        // - 先处理左子树的所有节点
        // - 再处理右子树的所有节点
        // - 但通过depth参数，每个节点都被正确分配到对应的层级
        levelOrderHelper(root.left, result, depth + 1);
        levelOrderHelper(root.right, result, depth + 1);
    }

    /**
     * 根据前序遍历和中序遍历构建二叉树（优化版本）
     *
     * 算法核心认知：
     * 1. 前序数组用来找根节点：
     *    - 前序遍历的特点是"根-左-右"，所以每个子树的第一个元素就是该子树的根节点
     *    - 递归每次的参数preOrderIndex都是当前子树根节点在前序数组中的位置
     *    - 通过preorder[preOrderIndex]可以直接获取当前子树的根节点值
     *
     * 2. 中序数组确定左右子树的大小：
     *    - 中序遍历的特点是"左-根-右"，根节点将中序数组分为两部分
     *    - 在中序数组中找到根节点位置rootIndex后：
     *      * 左子树大小 = rootIndex - inStart（根节点位置 - 左边界）
     *      * 右子树大小 = inEnd - rootIndex（右边界 - 根节点位置）
     *    - 根据左子树大小可以反推前序数组中左右子树根节点的位置：
     *      * 左子树根节点位置 = preOrderIndex + 1（当前根节点的下一个位置）
     *      * 右子树根节点位置 = preOrderIndex + 1 + leftSize（跳过当前根节点和整个左子树）
     *
     * 3. 递归构建过程：
     *    - 每次创建当前子树的根节点
     *    - 递归构建左子树，并将结果赋给root.left
     *    - 递归构建右子树，并将结果赋给root.right
     *    - 返回构建好的根节点给上层调用者（父节点）
     *    - 这样自底向上地完成整棵树的构建
     *
     * 分治法的经典应用：
     * 这种构建方式是分治法的典型体现，包含分治法的三个核心步骤：
     * 1. 分解（Divide）：将原问题（构建整棵树）分解为构建左子树和右子树两个子问题
     *    - 原问题：根据前序[preOrderIndex...end]和中序[inStart...inEnd]构建整棵树
     *    - 子问题1：根据前序[preOrderIndex+1...preOrderIndex+leftSize]和中序[inStart...rootIndex-1]构建左子树
     *    - 子问题2：根据前序[preOrderIndex+1+leftSize...end]和中序[rootIndex+1...inEnd]构建右子树
     * 2. 解决（Conquer）：递归地解决每个子问题
     *    - 递归构建左子树：dfsConstructTree2(preorder, inorderMap, preOrderIndex+1, inStart, rootIndex-1)
     *    - 递归构建右子树：dfsConstructTree2(preorder, inorderMap, preOrderIndex+1+leftSize, rootIndex+1, inEnd)
     * 3. 合并（Combine）：将子问题的解合并为原问题的解
     *    - 创建根节点：new Node(preorder[preOrderIndex])
     *    - 连接左子树：root.left = leftSubtree
     *    - 连接右子树：root.right = rightSubtree
     *    - 返回完整的树：return root
     *
     * 算法原理：
     * 前序遍历的第一个元素是根节点，中序遍历中根节点左侧是左子树，右侧是右子树
     * 通过递归构建左右子树
     *
     * @param preorder 前序遍历数组
     * @param inorder 中序遍历数组
     * @return 构建好的二叉树根节点
     *
     *         时间复杂度：O(n) - 每个节点只处理一次
     *         空间复杂度：O(n) - 哈希表存储和递归栈空间
     * @throws IllegalArgumentException 如果输入数组无效或包含重复值
     */
    public Node buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        final int n = inorder.length;
        if (n == 0) {
            return null;
        }

        // 可以认为数组本身是 i -> v 的映射，新的 map 恰好翻转了这种 kv 关系
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (inorderMap.containsKey(inorder[i])) {
                throw new IllegalArgumentException("有重复的项");
            }
            inorderMap.put(inorder[i], i);
        }

        // 从根节点开始处理，preOrderIndex 为0意味着当前子树的根节点是preorder[0]，inStart 和 inEnd 的当前值意味着当前涉及的子树范围在全部 inorderMap 范围内
        // inStart 和 inEnd 实际上和 rootIndex 在同一个序列比较的，但是 rootIndex 依赖的 inorder 数组却不在函数参数里，让人很费解
        return dfsConstructTree(preorder, inorderMap, 0, 0, n - 1);
    }

    /**
     * 深度优先搜索构建二叉树的递归辅助方法（优化版本）
     *
     * 核心认知详解：
     * 1. 前序数组负责找根节点：
     *    - preorder[preOrderIndex] 就是当前子树的根节点值
     *    - 参数preOrderIndex是当前子树根节点在前序数组中的位置
     *    - 每次递归调用时，preOrderIndex指向的都是某个子树的根节点
     *
     * 2. 中序数组确定子树大小和边界：
     *    - 通过inorderMap快速找到根节点在中序数组中的位置rootIndex
     *    - 中序数组的[inStart, inEnd]区间表示当前子树的范围
     *    - 根节点位置rootIndex将中序数组分割为：
     *      * 左子树区间：[inStart, rootIndex-1]，大小为 leftSize = rootIndex - inStart
     *      * 右子树区间：[rootIndex+1, inEnd]，大小为 rightSize = inEnd - rootIndex
     *
     * 3. 计算公式推导逻辑：
     *    - 已知：当前根节点在前序数组的位置是preOrderIndex
     *    - 已知：左子树大小为 leftSize = rootIndex - inStart
     *    - 推导：左子树根节点位置 = preOrderIndex + 1（紧跟当前根节点）
     *    - 推导：右子树根节点位置 = preOrderIndex + 1 + leftSize（跳过当前根节点和整个左子树）
     *    - 公式解释：前序遍历顺序是"根-左子树-右子树"，所以：
     *      * 位置preOrderIndex：当前根节点
     *      * 位置preOrderIndex+1到preOrderIndex+leftSize：左子树的所有节点
     *      * 位置preOrderIndex+1+leftSize：右子树的根节点
     *
     * 4. 递归构建和返回过程：
     *    - 创建当前子树的根节点：new Node(preorder[preOrderIndex])
     *    - 递归构建左子树，返回左子树根节点，赋值给root.left
     *    - 递归构建右子树，返回右子树根节点，赋值给root.right
     *    - 将构建好的根节点返回给父节点，完成子树的拼接
     *
     * 参数设计理念：
     * - inorderMap、inStart、inEnd：用于在中序遍历数据中定位和移动，提供子树边界信息
     * - preOrderIndex：用于在前序遍历数组中定位，提供根节点信息
     * - 核心思想：前序遍历提供根节点，中序遍历提供子树边界
     *
     * 参数说明：
     * @param preorder 前序遍历数组（提供根节点）
     * @param inorderMap 中序遍历值到索引的映射（快速定位，确定子树大小）
     * @param preOrderIndex 当前子树根节点在前序遍历中的索引
     * @param inStart 当前子树在中序遍历中的左边界（包含）
     * @param inEnd 当前子树在中序遍历中的右边界（包含），这第二个参数是很难被理解的，因为右区间的参数意味着可以收束 rootIndex 的范围，区间收束完意味着递归终止
     * @return 构建好的子树根节点，返回给父节点进行拼接
     *
     * 时间复杂度：O(n) - 每个节点只处理一次
     * 空间复杂度：O(h) - 递归栈深度，h为树高
     *
     * 递归过程详解：
     * 1. 终止条件：当右边界小于左边界时，表示子树为空
     * 2. 创建根节点：使用前序遍历中索引preOrderIndex处的值
     * 3. 找到根节点在中序遍历中的位置rootIndex
     * 4. 计算左子树的大小：leftSize = rootIndex - inStart
     * 5. 递归构建左子树：前序索引preOrderIndex+1，中序区间[inStart, rootIndex-1]
     * 6. 递归构建右子树：前序索引preOrderIndex+1+leftSize，中序区间[rootIndex+1, inEnd]
     * 7. 返回根节点给上层调用者
     */
    private Node dfsConstructTree(int[]preorder, Map<Integer, Integer> inorderMap, int preOrderIndex, int inStart,
            int inEnd) {

        /*
         * 边界检查设计原则：
         * 1. 信任调用者原则：假设初始调用参数正确，递归过程中的边界计算也是安全的
         * 2. 防御计算结果：对通过计算得出的索引进行边界检查，防止访问越界
         * 3. 算法逻辑检查：验证算法逻辑的正确性，如根节点是否在正确的子树范围内
         *
         * 为什么不检查 inStart/inEnd 的绝对边界：
         * - 假设初始调用是正确的：dfsConstructTree2(preorder, inorderMap, 0, 0, inorder.length - 1)
         * - 递归过程中的边界计算是安全的：[inStart, rootIndex-1] 和 [rootIndex+1, inEnd]
         * - 如果初始调用错误（如inStart=-1），那是调用者的责任，不是算法内部的责任
         *
         * 为什么要检查 preOrderIndex：
         * - preOrderIndex 是通过计算得出的：preOrderIndex + 1 + (rootIndex - inStart)
         * - 当树结构异常或数据不一致时，计算结果可能越界
         * - 这是算法内部的保护，防止访问数组时程序崩溃
         */

        // 子树区间为空时终止递归
        if (inEnd < inStart || inStart < 0 || inEnd >= preorder.length) {
            return null;
        }

        // 检查前序遍历索引边界 - 防御计算结果，避免数组访问越界
        if (preOrderIndex < 0 || preOrderIndex >= preorder.length) {
            return null;
        }

        int rootVal = preorder[preOrderIndex];
        Integer rootIndexObj = inorderMap.get(rootVal);

        // 如果找不到对应的索引，说明数据不一致
        if (rootIndexObj == null) {
            return null;
        }

        int rootIndex = rootIndexObj;

        // 算法逻辑检查：确保根节点在当前子树的有效范围内
        // 这不是边界保护，而是算法正确性验证
        if (rootIndex < inStart || rootIndex > inEnd) {
            return null;
        }

        // 本算法的核心是分治 inOrderMap。
        // 用 preorder 找到第一个根，用第一个根的 inorder index 得到左区间大小和右区间大小
        // 则左子树的根在 preorder 的 preOrderIndex + 1 处，第二个子树的根在 preorder 里左子树之后第一个后继，左子树大小 rootIndex - inStart
        // 左右区间是用来类似 dc 分治的方法来帮助界定下一个子问题的参数的，实行逐渐收窄 inorder 左右区间边界的逻辑

        Node root = new Node(rootVal);
        int leftRootPreorderIndex = preOrderIndex + 1;
        int rightRootPreorderIndex = preOrderIndex + 1 + (rootIndex - inStart); // 越过整个左子树在前序遍历里的位置，这里没有使用 inEnd 参数，因为

        // 围栏的右边界：[inStart, inEnd] 就像给当前子树划定的一个围栏
        // 搜索范围的限制：只能在这个围栏内寻找和构建节点
        // 分治的边界传递：每次递归都会缩小围栏范围，但 inEnd 在构建右子树时保持不变

        root.left = dfsConstructTree(preorder, inorderMap, leftRootPreorderIndex, inStart, rootIndex - 1);
        root.right = dfsConstructTree(preorder, inorderMap, rightRootPreorderIndex, rootIndex + 1, inEnd);

        return root;
    }

        /**
         * 根据前序遍历和中序遍历构建二叉树（支持重复值版本）
         *
         * 算法原理：
         * 前序遍历的第一个元素是根节点，中序遍历中根节点左侧是左子树，右侧是右子树
         * 由于允许重复值，不能使用HashMap进行快速查找，需要使用线性搜索
         *
         * 为什么需要这个方法：
         * 1. 原有的buildTree方法使用HashMap存储值到索引的映射，当存在重复值时，后面的值会覆盖前面的值
         * 2. 这会导致无法正确识别根节点在中序遍历中的位置，从而构建错误的树结构
         * 3. 线性搜索虽然时间复杂度较高(O(n²))，但能正确处理重复值的情况
         *
         * @param preorder 前序遍历数组
         * @param inorder 中序遍历数组
         * @return 构建好的二叉树根节点
         *
         *         时间复杂度：O(n²) - 每个节点需要线性搜索根节点位置
         *         空间复杂度：O(h) - 递归栈深度，h为树高
         */
    public Node buildTreeWithDuplicates(int[] preorder, int[] inorder) {
        // 参数校验
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        if (preorder.length == 0) {
            return null;
        }

        // 从根节点开始处理，i为0意味着当前子树的根节点是preorder[0]
        // l和r的当前值意味着当前涉及的子树范围在全部inorder数组范围内
        Node root = dfsConstructTreeWithDuplicates(preorder, inorder, 0, 0, inorder.length - 1);
        return root;
    }

    /**
     * 深度优先搜索构建二叉树的递归辅助方法（支持重复值版本）
     *
     * 算法原理：
     * 1. 前序遍历的第一个元素是当前子树的根节点
     * 2. 在中序遍历中找到该根节点的位置，将数组分为左子树和右子树
     * 3. 由于允许重复值，使用线性搜索找到根节点位置
     * 4. 根据左子树的节点数，确定前序遍历中左右子树的起始位置
     *
     * 关键理解：
     * - 对于普通二叉树（非二叉搜索树），重复值是允许的
     * - 前序遍历的第一个元素总是当前子树的根节点
     * - 在中序遍历中，根节点左侧是左子树，右侧是右子树
     * - 即使有重复值，这个分割逻辑仍然成立
     *
     * 参数说明：
     *
     * @param preorder 前序遍历数组
     * @param inorder 中序遍历数组
     * @param preIndex 当前子树根节点在前序遍历中的索引
     * @param inStart 当前子树在中序遍历中的左边界（包含）
     * @param inEnd 当前子树在中序遍历中的右边界（包含）
     * @return 构建好的子树根节点
     *
     *         时间复杂度：O(n²) - 每个节点需要线性搜索根节点位置
     *         空间复杂度：O(h) - 递归栈深度，h为树高
     */
    private Node dfsConstructTreeWithDuplicates(int[] preorder, int[] inorder, int preIndex, int inStart, int inEnd) {
        // 子树区间为空时终止
        if (inStart > inEnd) {
            return null;
        }

        // 当前子树的根节点值
        int rootValue = preorder[preIndex];
        Node root = new Node(rootValue);

        // 在中序遍历的当前区间内找到根节点的位置
        // 对于重复值，我们使用第一个匹配的位置作为根节点
        // 这是因为在构建树时，前序遍历的顺序决定了根节点的位置
        int rootIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootValue) {
                rootIndex = i;
                break;
            }
        }

        // 如果找不到根节点（理论上不应该发生，除非输入无效）
        if (rootIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid input: root value " + rootValue + " not found in inorder array");
        }

        // 计算左子树的大小
        int leftSize = rootIndex - inStart;

        // 递归构建左子树
        // 左子树的前序遍历起始位置：preIndex + 1
        // 左子树的中序遍历区间：[inStart, rootIndex - 1]
        root.left = dfsConstructTreeWithDuplicates(preorder, inorder, preIndex + 1, inStart, rootIndex - 1);

        // 递归构建右子树
        // 右子树的前序遍历起始位置：preIndex + 1 + leftSize
        // 右子树的中序遍历区间：[rootIndex + 1, inEnd]
        root.right = dfsConstructTreeWithDuplicates(preorder, inorder, preIndex + 1 + leftSize, rootIndex + 1, inEnd);

        return root;
    }
}