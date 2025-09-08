package algorithm.datastructures.trees;

import algorithm.datastructures.trees.BTree.Node;
import java.util.List;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 二叉搜索树实现
 * 支持插入、搜索、删除等基本操作
 * 复用BTree.Node作为节点结构
 *
 * @author magicliang
 *
 *         date: 2025-08-14 11:24
 */
public class BinarySearchTree {

    /**
     * 复用BTree的Node结构
     */
    private BTree.Node root;

    /**
     * 插入新值到BST中
     * 如果值已存在，则不进行任何操作
     * 普通二叉树没有办法通过插入实现自动建树的功能，但是搜索二叉树可以
     *
     * @param val 要插入的值
     */
    public void insert(int val) {
        root = insertRecursive(root, val);
    }

    /**
     * 非递归方式插入新值到BST中
     * 如果值已存在，则不进行任何操作
     * 使用迭代方式替代递归，避免栈溢出风险
     *
     * @param val 要插入的值
     */
    void insertNoneRecursive(int val) {
        root = insertNoneRecursive(root, val);
    }

    /**
     * 递归插入的辅助方法
     * 核心思想是，想办法在某个 null 值的地方插入一棵子树
     *
     * @param node 当前子树的根节点
     * @param val 要插入的值
     * @return 插入后的子树根节点
     */
    BTree.Node insertRecursive(BTree.Node node, int val) {
       if (node == null) {
           // 最差的情况是，连根都没有，这时候就直接生成一个根再直接返回
           return new Node(val);
       }

       if (node.val > val) {
           // 如果存在根，则尝试在左子树完成插入，最终还是调动到某个空叶子节点的子树
           node.left = insertNoneRecursive(node.left, val);
       } else if (node.val < val) {
           node.right = insertNoneRecursive(node.right, val);
       }

       // 二叉搜索树里不允许存在重复节点
       return node;
    }

    /**
     * 非递归插入的辅助方法
     * 使用迭代方式遍历树结构，找到合适的插入位置
     * 时间复杂度：O(h)，其中h是树的高度
     * 空间复杂度：O(1)，只使用了常数个额外变量
     *
     * @param node 当前子树的根节点
     * @param val 要插入的值
     * @return 插入后的子树根节点
     */
    BTree.Node insertNoneRecursive(BTree.Node node, int val) {
        if (node == null) {
            return new Node(val);
        }

        BTree.Node current = node;
        BTree.Node prev = null;

        // 遍历树结构，找到合适的插入位置
        while (current != null) {
            prev = current;
            if (val < current.val) {
                current = current.left;
            } else if (val > current.val) {
                current = current.right;
            } else {
                // 如果相等不用插入了直接返回
                return node;
            }
        }

        // 当current为null时，prev是其父节点
        BTree.Node newNode = new Node(val);
        if (val < prev.val) {
            prev.left = newNode;
        } else {
            // 这里不可能再有相等的节点了，因为等于的情况在上面已经得到返回值了
            prev.right = newNode;
        }
        return node;
    }

    /**
     * 搜索指定值是否存在
     *
     * @param val 要搜索的值
     * @return 如果存在返回true，否则返回false
     */
    public boolean search(int val) {
        return searchRecursive(root, val);
    }

    public boolean searchNoneRecursive(int val) {
        return searchNoneRecursive(root, val);
    }

    /**
     * 递归搜索的辅助方法
     *
     * 这里返回是否存在而不是返回具体的节点，是因为像 contains 之类的操作就是这样实现的
     *
     * @param node 当前子树的根节点
     * @param val 要搜索的值
     * @return 如果找到返回true，否则返回false
     */
    boolean searchRecursive(BTree.Node node, int val) {
        if (node == null) {
            return false;
        }
        if (node.val == val) {
            return true;
        } else if (val < node.val) {
            return searchRecursive(node.left, val);
        } else {
            return searchRecursive(node.right, val);
        }
    }

    /**
     * 非递归搜索的辅助方法
     * 使用迭代方式遍历树结构，避免递归栈溢出风险
     * 时间复杂度：O(h)，其中h是树的高度
     * 空间复杂度：O(1)，只使用了常数个额外变量
     *
     * @param node 当前子树的根节点
     * @param val 要搜索的值
     * @return 如果找到返回true，否则返回false
     */
     boolean searchNoneRecursive(BTree.Node node, int val) {
        BTree.Node current = node;
        while (current != null) {
            if (current.val == val) {
                return true;
            } else if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    /**
     * 删除指定值
     * 如果值不存在，则不进行任何操作
     *
     * @param val 要删除的值
     */
    public void delete(int val) {
        // 这个如果没有返回值，则需要考虑删除 root 的问题，就比较麻烦
        root = deleteRecursive(root, val);
    }

    /**
     * 递归删除的辅助方法
     * 处理三种情况：
     * 1. 叶子节点：直接删除
     * 2. 只有一个子节点：用子节点替换
     * 3. 有两个子节点：用右子树的最小值替换
     *
     * @param node 当前子树的根节点
     * @param val 要删除的值
     * @return 删除后的子树根节点
     */
     BTree.Node deleteRecursive(BTree.Node node, int val) {
        if (node == null) {
            return null;
        }

        if (val < node.val) {
            node.left = deleteRecursive(node.left, val);
            return node;
        } else if (val > node.val) {
            node.right = deleteRecursive(node.right, val);
            return node;
        }

        // 进入相等状况，要删除本节点

        // 删除节点以后比较痛苦的是到底要拿哪个节点来替换节点的问题，要区分度为2或者非2的场景，度为2的场景非常麻烦

        // 情况1：度非2
        if (node.left == null) {
            // 如果左节点为空，用右节点替代，而不用处理左子树问题
            node = node.right;
        } else if (node.right == null) {
            node = node.left; // 注意：对于度为1的节点，走到这里整个节点就变成 null了
        } else { // 易错的点：这里忘记用 else
            // 情况2：有两个子节点，所以右子树必然是存在的，把那个最小值拿上来，把它在那个子树里删除就行了-
            // 这个解似乎必然是递归最好，因为迭代法要处理很深的嵌套问题
            node.val = findMin(node.right); // 用 val 来替换当前 node
            node.right = deleteRecursive(node.right, node.val); // 删除这个子树的这个值，并将返回值赋给node.right
        }

        return node;
    }

    /**
     * 实现一个返回值的删除
     * 1. 先搜索再删除。二叉搜索树的特性，让我们看到的前驱总是它的父节点。
     * 2. 如果搜索不到，则不删除
     * 3. 要先区分是不是有不同的度，度为 2 的树就直接用右孩子最大节点来替代自己，然后删除这个孩子。但是删除右子树的过程是先右后左的曲折结构，所以要确定找到的节点是父节点的右孩子（只折了一折，所以没有左孩子），还是父节点的右孩子的子孙（全部都在最左子树线上找到待删除点）。
     * 4. 度为 1 的节点就直接用子节点替代自己。
     *
     * @param val 要删除的值
     */
    void remove(int val) {
        // 无法开始搜索的算法就不删除
        if (root == null) {
            return;
        }

        Node current = root;
        Node prev = null;

        // 第一阶段：搜索要删除的节点，同时记录其父节点
        Node toRemove = null;
        while (current != null) {
            if (current.val == val) {
                toRemove = current;
                // 这里相当于一个小 return
                break;
            }
            // 发生进一步探索才赋值
            prev = current;
            if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (toRemove == null) {
            return; // 易错的点：忘记 return
        }

        // 第二阶段：根据删除节点的子节点数量分情况处理
        // 全子树的节点（度为2的节点）
        /*
         * 删除度为2的节点示例：
         * 
         * 原始树结构：
         *       50 (要删除的节点)
         *      /  \
         *     30   70
         *    /    /  \
         *   20   60   80
         *           /
         *          55
         * 
         * 删除步骤：
         * 1. 找到右子树的最小值：55
         * 2. 用55替换50的值
         * 3. 删除原来的55节点
         * 
         * 结果树结构：
         *       55 (原50位置，值被替换)
         *      /  \
         *     30   70
         *    /    /  \
         *   20   60   80
         *         (55节点被删除)
         */
        if (toRemove.left != null && toRemove.right != null) {
            // int newVal = findMin(toRemove.right);
            // remove(newVal); // 这是一种做法，但是会有很复杂的递归

            // 我们接下来实现一种纯迭代
            // 关键思路：找到右子树的最小值节点，用它替换要删除的节点
            Node minParent = toRemove;  // 最小值节点的父节点
            Node minChild = minParent.right;  // 从右子树开始搜索最小值

            // 右子树的最小值在左直线上
            // 一直向左走，直到找到没有左子树的节点
            /*
             * 寻找右子树最小值的过程示例：
             * 
             * 初始状态：
             *      50 (toRemove)
             *     /  \
             *    30   70 (minParent=50, minChild=70)
             *        /  \
             *       60   80
             *      /  \
             *     55   65
             *    /
             *   52
             * 
             * while循环执行过程：
             * 第1次：minParent=70, minChild=60 (70.left != null)
             * 第2次：minParent=60, minChild=55 (60.left != null)  
             * 第3次：minParent=55, minChild=52 (55.left != null)
             * 第4次：52.left == null，循环结束
             * 
             * 最终：minParent=55, minChild=52 (52就是最小值)
             */
            while (minChild.left != null) {
                minParent = minChild;
                minChild = minChild.left;
            }

            // 关键：删除最小值节点，需要判断它是父节点的左子节点还是右子节点
            if (minParent.right == minChild) {
                // 情况1：右子树的根节点就是最小值（while循环没有执行）
                // 此时 minChild 是minParent的右子节点
                /*
                 * 例子：删除节点50
                 *      50 (toRemove/minParent)
                 *     /  \
                 *    30   60 (minChild，这就是最小值！因为它没有左子树)
                 *          \
                 *           70
                 * 
                 * 执行过程：
                 * 1. minParent = toRemove (指向50)
                 * 2. minChild = minParent.right (指向60)
                 * 3. while循环：60.left == null，循环不执行
                 * 4. 判断：minParent.right == minChild → 50.right == 60 → true
                 * 5. 执行：minParent.right = minChild.right → 50.right = 70
                 */
                minParent.right = minChild.right;
            } else {
                // 情况2：最小值节点在更深的左子树中（while循环执行了）
                // 此时minChild是minParent的左子节点
                /*
                 * 例子：删除节点50
                 *      50 (toRemove)
                 *     /  \
                 *    30   70
                 *        /  \
                 *       60   80
                 *      /
                 *     55 (minChild，最小值)
                 * 
                 * 执行过程：
                 * 1. minParent = toRemove (指向50)
                 * 2. minChild = minParent.right (指向70)
                 * 3. 第一次循环：minParent = 70, minChild = 60
                 * 4. 第二次循环：minParent = 60, minChild = 55
                 * 5. 第三次循环检查：55.left == null，循环结束
                 * 6. 判断：minParent.right == minChild → 60.right == 55 → false
                 * 7. 执行：minParent.left = minChild.right → 60.left = null
                 */
                minParent.left = minChild.right;
            }

            // 在删除完整棵树以后再重新赋值到这个节点
            // 用最小值替换要删除节点的值，完成删除操作
            toRemove.val = minChild.val;
        } else {
            // 第三阶段：处理度为0或1的节点（简单情况）
            /*
             * 删除度为0的节点（叶子节点）示例：
             *       50
             *      /  \
             *     30   70
             *    /      \
             *   20       80 (要删除的叶子节点)
             * 
             * 删除后：
             *       50
             *      /  \
             *     30   70
             *    /
             *   20
             * 
             * 删除度为1的节点示例：
             *       50
             *      /  \
             *     30   70 (要删除，只有右子节点)
             *    /      \
             *   20       80
             * 
             * 删除后：
             *       50
             *      /  \
             *     30   80 (80直接替换70的位置)
             *    /
             *   20
             */
            // 不全子树的节点处理比较简单，选一个子节点出来删除就行了
            // 选择存在的子节点作为替换节点（可能为null）
            Node newChild = toRemove.left != null ? toRemove.left : toRemove.right;

            // 如果要删除节点是 root，则 prev 是没用的
            if (toRemove == root) {
                // 直接更新根节点
                root = newChild;
                return; // 易错的点：忘记 return，可能触发多次删除
            }

            if (toRemove == prev.left) {
                // 否则必有 prev，就不用担心空指针问题
                // 要删除的节点是父节点的左子节点
                prev.left = newChild;
            } else {
                // 要删除的节点是父节点的右子节点
                prev.right = newChild;
            }
        }
    }

    /**
     * 查找最小值
     *
     * @return 最小值
     * @throws IllegalStateException 如果树为空
     */
    public int findMin() {
        if (root == null) {
            throw new IllegalStateException("BST is empty");
        }
        return findMin(root);
    }

    /**
     * 递归查找最小值的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 最小值
     */
    int findMin(BTree.Node node) {
        // 探索算法，不用 current，用 left 来处理
        while (node.left != null) {
            node = node.left;
        }
        return node.val;
    }

    /**
     * 查找最大值
     *
     * @return 最大值
     * @throws IllegalStateException 如果树为空
     */
    public int findMax() {
        if (root == null) {
            throw new IllegalStateException("BST is empty");
        }
        return findMax(root);
    }

    /**
     * 递归查找最大值的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 最大值
     */
    private int findMax(BTree.Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.val;
    }

    /**
     * 获取中序遍历结果（升序排列）
     * 复用BTree的中序遍历方法
     *
     * @return 升序排列的值列表
     */
    public List<Integer> inOrder() {
        BTree bTree = new BTree();
        return bTree.midOrder(root);
    }

    /**
     * 获取前序遍历结果
     * 复用BTree的前序遍历方法
     *
     * @return 前序遍历结果列表
     */
    public List<Integer> preOrder() {
        BTree bTree = new BTree();
        return bTree.preOrder(root);
    }

    /**
     * 获取后序遍历结果
     * 复用BTree的后序遍历方法
     *
     * @return 后序遍历结果列表
     */
    public List<Integer> postOrder() {
        BTree bTree = new BTree();
        return bTree.postOrder(root);
    }

    /**
     * 获取层序遍历结果
     * 复用BTree的层序遍历方法
     *
     * @return 层序遍历结果列表
     */
    public List<Integer> levelOrder() {
        BTree bTree = new BTree();
        return bTree.levelOrder(root);
    }

    /**
     * 检查树是否为空
     *
     * @return 如果为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 获取根节点（用于测试）
     *
     * @return 根节点
     */
    public BTree.Node getRoot() {
        return root;
    }

    /**
     * 清空整棵树
     */
    public void clear() {
        root = null;
    }

    /**
     * 获取树的高度
     *
     * @return 树的高度（节点数）
     */
    public int height() {
        return height(root);
    }

    /**
     * 递归计算树高的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 子树的高度
     */
    private int height(BTree.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 获取树的节点总数
     *
     * @return 节点总数
     */
    public int size() {
        return size(root);
    }

    /**
     * 递归计算节点数的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 子树的节点数
     */
    int size(BTree.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
}