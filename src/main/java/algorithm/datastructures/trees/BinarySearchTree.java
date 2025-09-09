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
     * 递归插入的辅助方法
     * 核心思想是，想办法在某个 null 值的地方插入一棵子树
     * 这个插入只会在叶子节点处插入一个值
     *
     * 关键洞察：BST插入的根本约束
     * 约束BST只能在叶子处插入的性质只有一条：不能破坏原树结构
     *
     * 具体分析：
     * 1. BST的有序性不变式：对于任意节点N，必须满足左子树所有值 < N.val < 右子树所有值
     * 2. 在内部节点插入会破坏现有结构：
     * - 破坏父子关系（节点只能容纳一个键值）
     * - 违反有序性
     * - 需要复杂的结构调整
     * 3. 叶子节点插入的优势：
     * - 不影响现有节点关系
     * - 树结构自然扩展
     * - 自动保持BST性质
     *
     * 与其他搜索树的对比：
     * - B树节点有多个键值空间，允许内部插入
     * - BST节点只能容纳一个键值，没有插入空间
     * - 这体现了不同数据结构设计权衡的差异
     *
     * @param node 当前子树的根节点
     * @param val 要插入的值
     * @return 插入后的子树根节点
     */
    static BTree.Node insertRecursive(BTree.Node node, int val) {
        if (node == null) {
            // 最差的情况是，连根都没有，这时候就直接生成一个根再直接返回
            return new Node(val);
        }

        if (node.val > val) {
            // 如果存在根，则尝试在左子树完成插入，最终还是调动到某个空叶子节点的子树
            node.left = insertRecursive(node.left, val);
        } else if (node.val < val) {
            node.right = insertRecursive(node.right, val);
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
    static BTree.Node insertNoneRecursive(BTree.Node node, int val) {
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
     * 递归搜索节点的辅助方法
     *
     * @param node 当前子树的根节点
     * @param val 要搜索的值
     * @return 如果找到返回对应节点，否则返回null
     */
    static BTree.Node searchNodeRecursive(BTree.Node node, int val) {
        if (node == null) {
            return null;
        }

        if (node.val == val) {
            return node;
        } else if (node.val < val) {
            return searchNodeRecursive(node.right, val);
        } else {
            return searchNodeRecursive(node.left, val);
        }
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
    static boolean searchRecursive(BTree.Node node, int val) {
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
    static boolean searchNoneRecursive(BTree.Node node, int val) {
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
     * 递归删除的辅助方法
     * 核心思想：将复杂的度为2删除问题转化为简单的度≤1删除问题
     *
     * 算法设计的核心洞察：
     * 1. 所有删除操作最终都能转化为度≤1的简单情况
     * 2. 这种转化是通过选择特殊的替换节点实现的
     *
     * 处理三种情况（按节点的度分类）：
     * - 度为0（叶子节点）：直接删除，返回null
     * - 度为1（单子节点）：用唯一子节点替换当前节点
     * - 度为2（双子节点）：用右子树最小值替换当前值，然后递归删除最小值
     *
     * 关键性质分析：
     * 性质1：BST中任何子树的最小值节点，其度必然 ≤ 1
     * 证明：如果最小值节点有左子树，那么左子树中必然存在更小的值，
     * 这与"最小值"的定义矛盾，因此最小值节点不可能有左子树
     *
     * 性质2：不仅最小值节点具有度≤1的性质，最大值节点同样如此
     * 原因：最大值节点不可能有右子树（否则右子树中存在更大值）
     *
     * 算法优雅性体现：
     * - 问题分解：复杂情况（度=2） → 简单情况（度≤1）
     * - 递归不变式：每次递归都在处理更简单的子问题
     * - 终止保证：最小值/最大值节点的度≤1性质保证递归必然终止
     *
     * 替换策略选择：
     * - 方案1：右子树最小值（中序后继）- 当前实现
     * - 方案2：左子树最大值（中序前驱）- 同样有效
     * 两种方案在理论上等价，时间复杂度相同，选择其一即可
     *
     * @param node 当前子树的根节点
     * @param val 要删除的值
     * @return 删除后的子树根节点。重要：因为外部是有穿针引线的准备的，所以从本节点返回的是本子树的 root 而不是全树的root，直接返回即可
     */
    static BTree.Node deleteRecursive(BTree.Node node, int val) {
        if (node == null) {
            return null;
        }

        // 易错的点：我们业已证明，删除链表/树的节点，prev是必不可少的，所以在非递归的算法里需要保存 prev，在递归的算法里我们要把当前节点和子树重新连起来
        if (val < node.val) {
            // 易错的点：忘记穿针引线
            node.left = deleteRecursive(node.left, val);
            return node;
        } else if (val > node.val) {
            // 易错的点：忘记穿针引线
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
            // 情况2：度为2的节点删除 - 核心策略：问题转化
            //
            // 为什么必须进行值替换而不能直接移动节点？
            // 答：度为2的节点有两个子树，直接删除会破坏树结构。
            //     需要找到一个值来替换，使得替换后仍满足BST性质：
            //     新值必须同时满足：大于左子树所有值 且 小于右子树所有值
            //     只有中序前驱（左子树最大值）或中序后继（右子树最小值）满足此条件
            /*
             * 为什么选择右子树最小值？关键性质分析：
             *
             * 性质1：右子树最小值是当前节点的中序后继
             * 性质2：右子树最小值节点的度必然 ≤ 1（不可能有左子树）
             *
             * 证明性质2：
             * - 假设最小值节点M有左子树L
             * - 则L中必然存在比M更小的值
             * - 这与M是"最小值"矛盾
             * - 因此M不可能有左子树，即M.left == null
             *
             * 删除过程示例：
             *
             * 原始树（删除50）：
             *       50 ← 要删除（度为2）
             *      /  \
             *     30   70
             *    /    /  \
             *   20   60   80
             *           /
             *          55 ← 右子树最小值（度为1，只有右子树或度为0）
             *
             * 步骤1：用55替换50的值
             *       55 ← 值被替换
             *      /  \
             *     30   70
             *    /    /  \
             *   20   60   80
             *           /
             *          55 ← 需要删除的重复值（度≤1）
             *
             * 步骤2：递归删除右子树中的55（简单情况）
             *       55
             *      /  \
             *     30   70
             *    /    /  \
             *   20   60   80 ← 55被删除，问题转化为度≤1的删除
            /*
             * 算法设计的数学美感体现：
             *
             * 1. 分治思想：将复杂问题（度=2）分解为简单问题（度≤1）
             * 2. 递归不变式：每次递归调用都在处理结构更简单的子问题
             * 3. 终止保证：利用最小值节点度≤1的性质，保证递归必然在简单情况终止
             * 4. 结构保持：替换过程保持BST的有序性质不变
             *
             * 关键洞察：通过选择最小值，我们保证了递归删除时遇到的是简单情况！
             * 这就是BST删除算法的优雅之处 - 巧妙利用树的结构性质解决复杂问题
             */
            node.val = findMin(node.right); // 用右子树最小值替换当前节点值
            // 易错的点：忘记穿针引线
            node.right = deleteRecursive(node.right, node.val); // 递归删除最小值（必然是度≤1的简单情况）
        }

        // 易错的点：我们业已证明，删除链表/树的节点，prev是必不可少的，所以在非递归的算法里需要保存 prev，在递归的算法里我们要把当前节点和子树重新连起来
        return node;
    }

    /**
     * 纯迭代删除方法 - 避免递归栈溢出的高效实现
     * 
     * 算法特点：
     * - 实现方式：纯迭代，无递归调用
     * - 时间复杂度：O(h)，其中h是树的高度
     * - 空间复杂度：O(1)，只使用常数个额外变量
     * - 适用场景：深度较大的树，避免栈溢出风险
     * 
     * 核心思想：
     * 1. 迭代搜索目标节点，同时记录父节点
     * 2. 手动处理所有删除情况
     * 3. 显式维护父子关系
     * 
     * 删除策略详解：
     * - 度为0/1：直接用子节点替换目标节点
     * - 度为2：手动找到右子树最小值，替换后删除最小值节点
     * 
     * 关键技术点：
     * - 使用prev指针维护父子关系
     * - 手动处理最小值节点的删除（区分是否为右子树根）
     * - 特殊处理根节点删除情况
     * 
     * 测试用例覆盖：
     * - 删除叶子节点：remove_DeleteLeafNode_Test
     * - 删除单子节点：remove_DeleteSingleChildNode_Test
     * - 删除双子节点：remove_DeleteDoubleChildNode_Test
     * - 删除根节点：remove_DeleteRootNode_Test
     * - 删除不存在节点：remove_DeleteNonExistentNode_Test
     * 
     * @param root 树的根节点
     * @param val 要删除的值
     * @return 删除后的树根节点
     */
    static Node remove(Node root, int val) {
        // 基础情况：空树直接返回
        if (root == null) {
            return root;
        }

        // 第一阶段：迭代搜索目标节点
        Node current = root;
        Node prev = null; // 关键：记录父节点，用于后续的父子关系维护

        Node toRemove = null; // 要删除的目标节点

        // 迭代搜索：根据BST性质定位目标节点
        // 用break 来代替提前退出
        while (current != null) {
            if (current.val == val) {
                toRemove = current; // 找到目标节点
                break; // 重要：找到后立即退出，保持prev指向父节点
            } else if (current.val > val) {
                // 目标值在左子树
                prev = current;
                current = current.left;
            } else {
                // 目标值在右子树
                prev = current;
                current = current.right;
            }
        }

        // 未找到目标节点，无需删除
        // 不删除
        if (toRemove == null) {
            return root;
        }

        // 第二阶段：根据节点度数分类处理删除
        // 区分度来删除，最核心的共同点是：肯定是子树替换子树，但某些场景下是 val 替换 toRemove 的val

        // 情况1&2：度为0或1的节点（简单情况）
        if (toRemove.left == null || toRemove.right == null) {
            // 选择存在的子节点作为替换（可能为null）
            Node newChild = toRemove.left != null ? toRemove.left : toRemove.right;

            // 特殊情况：删除的是根节点
            // 如果 prev == null，则 toRemove 必定是 root，这里有2种写法
            if (toRemove == root) {
                root = newChild; // 直接更新根节点
                return root;
            }
            
            // 一般情况：更新父节点的子指针
            if (prev.left == toRemove) {
                prev.left = newChild; // 目标节点是父节点的左子节点
            } else {
                prev.right = newChild; // 目标节点是父节点的右子节点
            }
        } else { 
            // 情况3：度为2的节点（复杂情况）
            // toRemove.left != null && toRemove.right != null
            // 两个左右孩子都不等于 null，则我只要把右孩子的最小值取过来，然后删除那个节点就行了，再用遍历的方法再来一次，是 root 也不怕

            // 策略：找到右子树最小值，用其替换目标节点值，然后删除最小值节点
            
            // 寻找右子树最小值节点及其父节点
            // 开始右折左回
            Node minParent = toRemove; // 最小值节点的父节点
            Node minChild = toRemove.right; // 从右子树开始搜索

            // 一直向左走，找到最小值节点
            while (minChild.left != null) {
                minParent = minChild;
                // 找到最左节点
                minChild = minChild.left;
            }

            // 删除最小值节点：需要判断其位置关系
            // 关键：删除深处的子树，也需要用子树替换法
            // 校验到底是右回还是左折
            if (minParent.left == minChild) {
                // 情况A：最小值节点在更深的左子树中
                // 子树替换子树：删除 < 的左节点，用右当左
                minParent.left = minChild.right;
            } else {
                // 情况B：右子树根节点就是最小值（没有左子树）
                // 子树替换子树：删除 \ 的右节点，用右当右
                minParent.right = minChild.right;
            }
            
            // 用最小值替换目标节点的值
            // 在顶部替换
            toRemove.val = minChild.val;
        }
        
        return root; // 返回可能更新的根节点
    }

    /**
     * 混合删除方法 - 迭代搜索 + 递归删除的优化实现
     * 
     * 算法特点：
     * - 实现方式：混合策略，结合两种方法优点
     * - 搜索阶段：迭代实现，避免不必要的递归开销
     * - 删除阶段：对度为2节点使用递归，简化复杂逻辑
     * - 时间复杂度：O(h)，其中h是树的高度
     * - 空间复杂度：O(h)，最坏情况下递归删除最小值的栈深度
     * 
     * 设计思想：
     * 1. 搜索用迭代：高效定位，无栈开销
     * 2. 简单删除用迭代：度≤1的情况直接处理
     * 3. 复杂删除用递归：度=2的情况委托给递归方法
     * 
     * 优势分析：
     * - 相比纯递归：减少了搜索阶段的栈开销
     * - 相比纯迭代：简化了度为2节点的删除逻辑
     * - 代码可读性：逻辑清晰，易于理解和维护
     * 
     * 适用场景：
     * - 平衡考虑性能和代码简洁性
     * - 树深度中等，既要避免过多递归又要保持代码清晰
     * 
     * 测试用例覆盖：
     * - 删除叶子节点：remove2_DeleteLeafNode_Test
     * - 删除单子节点：remove2_DeleteSingleChildNode_Test
     * - 删除双子节点：remove2_DeleteDoubleChildNode_Test
     * - 删除根节点：remove2_DeleteRootNode_Test
     * - 删除不存在节点：remove2_DeleteNonExistentNode_Test
     * 
     * @param root 树的根节点
     * @param val 要删除的值
     * @return 删除后的树根节点
     */
    static Node remove2(Node root, int val) {
        // 基础情况：空树直接返回
        if (root == null) {
            return root;
        }

        // 第一阶段：迭代搜索目标节点（与remove方法相同）
        Node current = root;
        Node prev = null; // 记录父节点，用于维护父子关系

        Node toRemove = null; // 要删除的目标节点

        // 迭代搜索：高效定位目标节点
        // 用break 来代替提前退出
        while (current != null) {
            if (current.val == val) {
                toRemove = current;
                break; // 找到目标节点，退出搜索
            } else if (current.val > val) {
                // 目标在左子树
                prev = current;
                current = current.left;
            } else {
                // 目标在右子树
                prev = current;
                current = current.right;
            }
        }

        // 未找到目标节点
        // 不删除
        if (toRemove == null) {
            return root;
        }

        // 第二阶段：根据节点度数选择删除策略
        // 区分度来删除，最核心的共同点是：肯定是子树替换子树，但某些场景下是 val 替换 toRemove 的val

        // 情况1&2：度为0或1的节点 - 使用迭代处理（简单高效）
        if (toRemove.left == null || toRemove.right == null) {
            // 选择存在的子节点作为替换
            Node newChild = toRemove.left != null ? toRemove.left : toRemove.right;

            // 特殊情况：删除根节点
            // 如果 prev == null，则 toRemove 必定是 root，这里有2种写法
            if (toRemove == root) {
                root = newChild;
                return root;
            }
            
            // 一般情况：更新父节点指针
            if (prev.left == toRemove) {
                prev.left = newChild;
            } else {
                prev.right = newChild;
            }

        } else { 
            // 情况3：度为2的节点 - 使用递归处理（简化逻辑）
            // toRemove.left != null && toRemove.right != null
            // 两个左右孩子都不等于 null，则我只要把右孩子的最小值取过来，然后删除那个节点就行了，再用遍历的方法再来一次，是 root 也不怕

            // 混合策略的核心：委托给递归方法处理复杂情况
            int min = findMin(toRemove.right); // 找到右子树最小值
            toRemove.val = min; // 用最小值替换当前节点值
            // 递归删除最小值节点（利用递归的简洁性）
            toRemove.right = remove2(toRemove.right, min);
        }
        
        return root; // 返回可能更新的根节点
    }

    /**
     * 递归删除方法 - 简洁优雅的纯递归实现
     * 
     * 算法特点：
     * - 实现方式：纯递归，代码简洁优雅
     * - 时间复杂度：O(h)，其中h是树的高度
     * - 空间复杂度：O(h)，递归栈深度
     * - 适用场景：树高度不大，追求代码简洁性
     * 
     * 核心思想：
     * 1. 递归搜索目标节点
     * 2. 找到后根据节点度数分类处理
     * 3. 自动维护父子关系（通过返回值重新连接）
     * 
     * 删除策略分析：
     * - 度为0（叶子节点）：直接删除，返回null
     * - 度为1（单子节点）：用唯一子节点替换
     * - 度为2（双子节点）：用右子树最小值替换，递归删除最小值节点
     * 
     * 测试用例覆盖：
     * - 删除叶子节点：removeRecursive_DeleteLeafNode_Test
     * - 删除单子节点：removeRecursive_DeleteSingleChildNode_Test  
     * - 删除双子节点：removeRecursive_DeleteDoubleChildNode_Test
     * - 删除根节点：removeRecursive_DeleteRootNode_Test
     * - 删除不存在节点：removeRecursive_DeleteNonExistentNode_Test
     * 
     * @param root 当前子树的根节点
     * @param val 要删除的值
     * @return 删除后的子树根节点
     */
    static Node removeRecursive(Node root, int val) {
        // 基础情况：空树或递归到叶子节点的子树
        if (root == null) {
            return root; // 未找到目标节点，返回null
        }

        // 递归搜索阶段：根据BST性质决定搜索方向
        // 递归搜索，穿针引线
        if (root.val < val) {
            // 目标值在右子树，递归搜索右子树
            root.right = removeRecursive(root.right, val);
            return root; // 重要：重新连接右子树
        } else if (root.val > val) {
            // 目标值在左子树，递归搜索左子树
            root.left = removeRecursive(root.left, val);
            return root; // 重要：重新连接左子树
        }

        // 找到目标节点，开始删除操作
        // 真的删除，才是真的删除，也要做子树/值替换

        // 情况1&2：度为0或1的节点（简单情况）
        // 首先，如果当前的节点可以直接被子节点替代，则不需要维护树形结构，只要有一个子树不为空就行了
        if (root.left == null || root.right == null) {
            // 选择存在的子节点作为替换（可能为null）
            root = root.left != null ? root.left : root.right;
        } else {
            // 情况3：度为2的节点（复杂情况）
            // 否则，要处理度为2的节点，需要值替换删除

            // 策略：用右子树最小值替换当前节点值，然后删除最小值节点
            // 找到最小值，替换当前值，删除那个最小值
            int min = findMin(root.right); // 找到右子树最小值（中序后继）
            root.val = min; // 用最小值替换当前节点值
            root.right = removeRecursive(root.right, min); // 递归删除最小值节点（必然是度≤1）
        }

        return root; // 返回处理后的子树根节点
    }

    /**
     * 递归查找最小值的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 最小值
     */
    static int findMin(BTree.Node node) {
        // 探索算法，不用 current，用 left 来处理
        while (node.left != null) {
            node = node.left;
        }
        return node.val;
    }

    /**
     * 递归查找最大值的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 最大值
     */
    static int findMax(BTree.Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.val;
    }

    /**
     * 递归计算树高的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 子树的高度
     */
    static int height(BTree.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 递归计算节点数的辅助方法
     *
     * @param node 当前子树的根节点
     * @return 子树的节点数
     */
    static int size(BTree.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    /**
     * 实现一个返回值的删除
     * 1. 先搜索再删除。二叉搜索树的特性，让我们看到的前驱总是它的父节点。
     * 2. 如果搜索不到，则不删除。
     * 3. 对于度为2的节点，执行右孩子（后继）删除：要先区分是不是有不同的度，度为  2 的树就直接用右孩子最大节点来替代自己，然后删除这个孩子。但是删除右子树的过程是先右后左的曲折结构，所以要确定找到的节点是父节点的
     * 右孩子（只折了一折，所以没有左孩子），还是父节点的右孩子的子孙（全部都在最左子树线上找到待删除点），如果是前者，则因为不存在任何左子树，用右子树的右孩子替代右子树的根节点，就删除了右节点；否则，用左孩子的右子
     * 树来替代左孩子。然后用右孩子的值来替代自己。
     * 4. 度为 1 的节点就直接用子节点替代自己，连值替换都不需要。
     * 5. 如果被删除的节点是 root，记得修改 root 本身-因为归根结底是要返回新树的 root 的。
     *
     * @param val 要删除的值
     */
    void remove(int val) {
        // 无法开始搜索的算法就不删除
        if (root == null) {
            return;
        }

        Node current = root;
        // 易错的点：必须保持prev，保证穿针引线
        Node prev = null;

        // 第一阶段：搜索要删除的节点，同时记录其父节点
        Node toRemove = null;
        while (current != null) {
            if (current.val == val) {
                toRemove = current;
                // 易错的点：忘记退出循环这里会造成 toRemove 始终为 null
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
            while (minChild.left != null) { // 让 minChild 是以非空结尾，所以这里不能用 minChild != null
                minParent = minChild;
                minChild = minChild.left;
            }

            // 关键：删除最小值节点，需要判断它是父节点的左子节点还是右子节点。先确定它是右折还是左折
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

            // 易错的点：如果要删除节点是 root，则 prev 是没用的，而 root 本身是不需要区分左右孩子的
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
     * 搜索指定值的节点
     *
     * @param val 要搜索的值
     * @return 如果找到返回对应节点，否则返回null
     */
    public BTree.Node searchNode(int val) {
        return searchNodeRecursive(root, val);
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
     * 获取树的节点总数
     *
     * @return 节点总数
     */
    public int size() {
        return size(root);
    }
}