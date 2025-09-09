package algorithm.datastructures.elementary;

/**
 * 快慢指针算法示例
 * <p>
 * 快慢指针是双指针技巧的一种，两个指针以不同的速度移动。
 * 常用于链表问题，如检测环、寻找中点、寻找倒数第k个节点等。
 * <p>
 * 双指针策略：快慢指针移动
 * - 慢指针每次移动1步
 * - 快指针每次移动2步（或更多步）
 * - 通过速度差异来解决特定问题
 *
 * 二叉树是分叉链表，我们遇到各种全局问题最好先观察子链表、子树结构有什么特别的地方，如果有不同，则归类到逻辑分支里，尝试构造一个递归形式的dc过程，最后得到全局解
 * 链表是由表头和子链表组成的，而二叉树是由根和左右子树构成的。
 *
 * 二分 merge 和前缀 merge
 *
 * @author magicliang
 * @date 2025-09-02
 */
public class LinkedList {

    /**
     * 链表节点定义
     * <p>
     * 提供三种构造方式：
     * 1. 默认构造：创建值为0的节点
     * 2. 值构造：创建指定值的节点
     * 3. 完整构造：创建指定值和下一个节点的节点
     */
    public static class ListNode {
        int val;        // 节点存储的值
        ListNode next;  // 指向下一个节点的指针

        /**
         * 默认构造函数，创建值为0的节点
         */
        ListNode() {
        }

        /**
         * 值构造函数
         *
         * @param val 节点的值
         */
        ListNode(int val) {
            this.val = val;
        }

        /**
         * 完整构造函数
         *
         * @param val  节点的值
         * @param next 下一个节点的引用
         */
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        /**
         * 重写toString方法，便于调试
         *
         * @return 节点的字符串表示
         */
        @Override
        public String toString() {
            return "ListNode{val=" + val + "}";
        }
    }

    /**
     * 反转链表
     * 时间复杂度：O(n)
     * 空间复杂度：不使用 dummy节点的解法，实际上仍然是 O(1) 复杂度的算法
     *
     * @return 反转后的链表头节点
     */
    public ListNode reverse(ListNode head) {

        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            // 这个算法每次都是引入 p c n
            // 先保存 n，再逆转c，再把 p 指向新头：
            //
            // 让 p c n 的顺序交换
            // 首先从中间节点开始
            // 从 p c n 变成 c p n
            // 然后把下一轮的 p 指向 cp 的头
            // 这样下一轮开始的时候，就产生新的 cp n
            ListNode next = current.next;

            // 反转自己，前链表和本链表断链
            current.next = prev;
            // 本链表整体成为一个 prev 链表
            prev = current;

            // 下一轮从后续链表开始
            current = next;

            // 当 current 等于null的时候，整个原链表都变成了prev链表，可以直接使用 prev 了

        }

        // 最后 prev 是新头，pcn 的最终返回值就是 p
        return prev;
    }

    /**
     * 链表反转器内部类
     * 提供两种反转前n个节点的实现方式：递归和迭代
     */
    class Reverser {

        ListNode successor; // 保存未反转部分的头节点

        /**
         * 反转链表的前 n 个节点（递归实现）
         * <p>
         * 算法思路：
         * 1. 递归到第n个节点作为新的头节点
         * 2. 在回溯过程中逐步反转指针方向
         * 3. 保存第n+1个节点作为successor，用于连接未反转部分
         * <p>
         * 时间复杂度：O(n)
         * 空间复杂度：O(n)（递归栈开销）
         *
         * @param head 链表头节点
         * @param n    需要反转的节点数
         * @return 反转后的链表头节点
         */
        public ListNode reverse(ListNode head, int n) {
            if (n == 1) {
                // 找到尾巴节点
                successor = head.next; // 记录第 n+1 个节点
                return head;
            }
            ListNode newHead = reverse(head.next, n - 1);
            // 此时本头还保留新尾的引用，即 head.next 是子链表的尾，把本头放到子链表的尾部去
            head.next.next = head;
            // 从底部返回 successor 必已赋值
            head.next = successor; // 这个 head.next 会发生多次，最终是最初的head连上了 successor
            return newHead; // 这个 newHead 回不断被返回回去，不会被替换
        }

        /**
         * 反转链表的前 n 个节点（迭代实现）
         * <p>
         * 算法思路：
         * 1. 使用三个指针：prev（前驱）、current（当前）、next（后继）
         * 2. 逐个反转前n个节点的指针方向
         * 3. 反转完成后，原头节点变成尾节点，连接剩余未反转部分
         * <p>
         * 相比递归实现的优势：
         * - 空间复杂度更优：O(1)
         * - 避免递归栈溢出风险
         * - 逻辑更直观易懂
         * <p>
         * 时间复杂度：O(n)
         * 空间复杂度：O(1)
         *
         * @param head 链表头节点
         * @param n    需要反转的节点数
         * @return 反转后的链表头节点
         */
        public ListNode reverse2(ListNode head, int n) {

            // 边界情况处理，对于不需要翻转的场景， head.next = current; 会导致错误
            if (head == null || n <= 0) {
                return head;
            }

            ListNode current = head;
            ListNode prev = null;

            // 反转前n个节点
            while (n > 0) {
                ListNode nxt = current.next;  // 保存下一个节点
                current.next = prev;          // 反转当前节点指针
                prev = current;               // 移动prev指针
                current = nxt;                // 移动current指针
                n--;
            }

            // 这个算法里的head天然就是末尾，只要直接赋值到 current 的越界值就行了
            // 连接反转部分和未反转部分：原头节点现在是尾节点，连接到剩余部分
            head.next = current;

            // prev 是新的头
            return prev;
        }

        /**
         * 反转链表的前 n 个节点（dummy头节点实现）
         * <p>
         * 算法思路：
         * 1. 使用dummy头节点简化边界处理
         * 2. 通过头插法逐个将节点插入到dummy节点后面
         * 3. 最终形成反转效果
         * <p>
         * 核心技巧：头插法反转
         * - 每次将current节点插入到dummyHead的后面
         * - 这样自然形成了反转的顺序
         * <p>
         * 相比其他实现的特点：
         * - 使用dummy节点避免特殊处理头节点
         * - 头插法思路清晰直观
         * - 代码简洁优雅
         * <p>
         * 时间复杂度：O(n)
         * 空间复杂度：O(1)
         *
         * @param head 链表头节点
         * @param n    需要反转的节点数
         * @return 反转后的链表头节点
         */
        public ListNode reverse3(ListNode head, int n) {
            if (head == null || n <= 0) {
                return head;
            }

            // 创建dummy头节点，简化边界处理
            ListNode dummyHead = new ListNode();
            dummyHead.next = head;

            ListNode current = head;
            while (n > 0) {
                // 这一步对所有的翻转都是一样的
                // 头插法：将current节点插入到dummyHead后面
                ListNode nxt = current.next;        // 保存下一个节点，下一圈的节点有用
                current.next = dummyHead.next;      // current指向当前dummyHead的下一个节点
                dummyHead.next = current;           // dummyHead指向current（头插）
                current = nxt;                      // 移动到下一个节点
                n--;
            }

            // 此时 head 本身就是新链表的末尾，链表结构即使被破坏了也无所谓
            // 连接反转部分和未反转部分
            head.next = current;
            return dummyHead.next;
        }

    }


    /**
     * 检测链表是否有环（Floyd判圈算法）
     * <p>
     * 算法原理：
     * - 如果链表有环，快指针最终会追上慢指针
     * - 如果链表无环，快指针会先到达末尾
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 是否有环
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;      // 慢指针，每次移动1步
        ListNode fast = head.next; // 快指针，每次移动2步

        while (slow != fast) {
            // 快指针到达末尾，说明无环
            if (fast == null || fast.next == null) {
                return false;
            }

            slow = slow.next;        // 慢指针移动1步
            fast = fast.next.next;   // 快指针移动2步
        }

        return true; // 快慢指针相遇，说明有环
    }

    /**
     * 寻找链表的中间节点
     * <p>
     * 算法原理：
     * - 当快指针到达末尾时，慢指针正好在中间位置
     * - 对于偶数长度链表，返回第二个中间节点
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 中间节点
     */
    public ListNode findMiddle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode slow = head; // 慢指针
        ListNode fast = head; // 快指针

        // 快指针每次移动2步，慢指针每次移动1步
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow; // 慢指针指向中间节点
    }

    /**
     * 寻找链表的倒数第k个节点
     * <p>
     * 算法原理：
     * - 让快指针先移动k步
     * - 然后快慢指针同时移动，直到快指针到达末尾
     * - 此时慢指针指向倒数第k个节点
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @param k    倒数第k个
     * @return 倒数第k个节点，如果k超出链表长度则返回null
     */
    public ListNode findKthFromEnd(ListNode head, int k) {
        // k 必须至少是1，这样才能至少走到尾部
        if (head == null || k <= 0) {
            return null;
        }

        ListNode fast = head; // 快指针
        ListNode slow = head; // 慢指针

        // 我们对链表到底有多长是一无所知的，所以要主动一步一步试错
        for (int i = 0; i < k; i++) {
            // 能走七步算七步
            if (fast == null) {
                return null;
            }

            // fast 是有可能走到 null 的
            fast = fast.next;
        }

        // fast 要走过最后一个节点，slow 要走到倒数第k个，这样其实fast和slow距离是k，fast走到null以后，slow和尾部节点的距离是 k-1
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * 删除链表的倒数第k个节点
     * 这个算法的 k + 1 和 dummyHead 是必须匹配存在的
     * 没有 dummyHead 不能处理头节点删除
     * 如果有了 dummyHead，如果k等于链表长度，k+1 也能受得住，无从删除起
     * 所以查找节点有没有 dummyHead 无所谓，删除节点需要这个操作来简化复杂度
     * 定律：
     * 1. 如果要让 slow 停在倒数第k个节点，要让 fast 先走 k 步。
     * 2. 如果要删除 k 节点（包括头），就要让 slow 停在倒数 k + 1 处。
     * 3. 所以要让 fast 先走 k + 1 步。
     *
     * @param head 链表头节点
     * @param k    倒数第k个
     * @return 删除节点后的链表头
     */
    public ListNode removeKthFromEnd(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }

        // 之所以要引入 dummy 节点，是因为 head 可能被删除，比较麻烦
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;
        // 不管快慢节点是不是从 dummy 节点开始出发，他们之间的相对距离此时是 k + 1而不是k，而且下面的循环仍然会让 fast 停在链表末尾的 null上，这也就意味着 slow会停在倒数 k + 1
        // 个节点上（也就是k的前驱）
        for (int i = 0; i < k + 1; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // 在没有前驱节点的前提下，用后继节点删除本节点错误
//        slow.val = slow.next.val;
//        slow.next = slow.next.next;

        // 删除 slow 的下一个节点
        slow.next = slow.next.next;

        // 仍然使用 dummy 来解决这个问题
        return dummy.next;
    }

    /**
     * 删除链表的倒数第k个节点
     * 这个算法是正确的，因为头节点可以被删除，所以 dummy 节点是必须的
     *
     * @param head 链表头节点
     * @param k    倒数第k个
     * @return 删除节点后的链表头
     */
    public ListNode removeKthFromEnd2(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }

        // 之所以要引入 dummy 节点，是因为 head 可能被删除，比较麻烦
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;
        // fast 先走 k+1 步，这样当 fast 到达 null 时，slow 指向要删除节点的前一个节点
        for (int i = 0; i <= k; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // slow 现在指向要删除节点的前一个节点
        // 删除 slow.next 节点
        if (slow.next != null) {
            slow.next = slow.next.next;
        }

        // 仍然使用 dummy 来解决这个问题
        return dummy.next;
    }

    /**
     * 求两个链表的交点（假设链表无环）
     * <p>
     * 算法原理：
     * - 双指针法：指针A从链表A出发，指针B从链表B出发。
     * - 当指针A到达链表A末尾时，跳转到链表B头部；指针B同理。
     * - 如果两链表相交，指针A和指针B会在交点相遇；否则同时到达末尾（null）。
     * <p>
     * 时间复杂度：O(m + n)
     * 空间复杂度：O(1)
     *
     * @param headA 链表A的头节点
     * @param headB 链表B的头节点
     * @return 交点节点，若无交点则返回null
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pA = headA;
        ListNode pB = headB;

        // 双指针遍历，直到相遇或同时到达末尾（null）
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA; // 返回交点或null
    }

    /**
     * 求两个链表的交点（通过修改链表结构形成环）
     * <p>
     * 算法原理：
     * - 将链表A的尾部连接到其头部，形成环。
     * - 使用快慢指针法从链表B的头节点出发，检测环并找到入环点（即交点）。
     * - 最后还原链表A的结构。
     * <p>
     * 时间复杂度：O(m + n)
     * 空间复杂度：O(1)
     *
     * @param headA 链表A的头节点
     * @param headB 链表B的头节点
     * @return 交点节点，若无交点则返回null
     */
    public ListNode getIntersectionNodeByCycle(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        // 1. 找到链表A的尾节点，并记录其原始next指针
        ListNode tailA = headA;
        while (tailA.next != null) {
            tailA = tailA.next;
        }
        ListNode originalTailNext = tailA.next;

        // 2. 将链表A的尾部连接到链表A的头部，形成环
        tailA.next = headA;

        // 3. 使用快慢指针法从链表B的头节点出发，寻找入环点（即交点）
        ListNode slow = headB;
        ListNode fast = headB;

        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }

        ListNode intersection = null;
        if (hasCycle) {
            slow = headB;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            intersection = slow;
        }

        // 4. 还原链表A的结构
        tailA.next = originalTailNext;

        return intersection;
    }

    /**
     * 检测链表中环的起始节点（Floyd判圈算法 + 数学推导）
     * <p>
     * 算法原理：
     * 1. 第一阶段：使用快慢指针检测是否有环
     * 2. 第二阶段：如果有环，找到环的起始节点
     * <p>
     * 数学推导：
     * - 设链表头到环起点距离为 a
     * - 环起点到快慢指针相遇点距离为 b  
     * - 相遇点到环起点距离为 c
     * - 环长为 b + c
     * <p>
     * 当快慢指针相遇时：
     * - 慢指针走过距离：a + b
     * - 快指针走过距离：a + b + n(b + c)，其中n为快指针多走的圈数
     * - 由于快指针速度是慢指针2倍：2(a + b) = a + b + n(b + c)
     * - 化简得：a = n(b + c) - b = (n-1)(b + c) + c
     * <p>
     * 这意味着：从链表头走 a 步 = 从相遇点走 c 步（都到达环起点）
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 环的起始节点，如果无环则返回null
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        // 第一阶段：检测是否有环
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;

        while (fast != null && fast.next != null) {
            slow = slow.next;        // 慢指针每次移动1步
            fast = fast.next.next;   // 快指针每次移动2步
            
            if (slow == fast) {      // 快慢指针相遇，说明有环
                hasCycle = true;
                break;
            }
        }

        // 如果无环，直接返回null
        if (!hasCycle) {
            return null;
        }

        // 第二阶段：找到环的起始节点
        // 将慢指针重置到链表头，快指针保持在相遇点
        slow = head;
        
        // 两指针以相同速度移动，相遇点即为环的起始节点
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow; // 返回环的起始节点
    }

    /**
     * 删除排序链表中的所有重复元素（完全删除）
     * <p>
     * 与 deleteDuplicates 不同，此方法会完全删除所有重复的元素，
     * 而不是保留一个。
     * <p>
     * 例如：1->2->3->3->4->4->5 变为 1->2->5
     * <p>
     * 算法思路：
     * 1. 使用dummy头节点简化边界处理
     * 2. 使用prev指针跟踪前一个不重复的节点
     * 3. 使用current指针遍历链表
     * 4. 当发现重复时，跳过所有相同值的节点
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 已排序链表的头节点
     * @return 删除所有重复元素后的链表头节点
     */
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 使用dummy头节点简化边界处理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;    // 指向前一个不重复的节点
        ListNode current = head;  // 当前遍历的节点

        while (current != null) {
            // 检查当前节点是否有重复
            if (current.next != null && current.val == current.next.val) {
                int duplicateVal = current.val;
                
                // 跳过所有值为duplicateVal的节点
                while (current != null && current.val == duplicateVal) {
                    current = current.next;
                }
                
                // 将prev连接到跳过重复节点后的位置
                prev.next = current;
            } else {
                // 当前节点不重复，prev指针前移
                prev = current;
                current = current.next;
            }
        }

        return dummy.next;
    }

    /**
     * 合并两个有序链表
     * <p>
     * 将两个升序链表合并为一个新的升序链表并返回。
     * 新链表是通过拼接给定的两个链表的所有节点组成的。
     * <p>
     * 算法思路：
     * 1. 使用dummy头节点简化处理
     * 2. 使用双指针分别遍历两个链表
     * 3. 每次选择较小的节点连接到结果链表
     * 4. 处理剩余节点
     * <p>
     * 时间复杂度：O(m + n)，其中m和n分别是两个链表的长度
     * 空间复杂度：O(1)
     *
     * @param list1 第一个有序链表
     * @param list2 第二个有序链表
     * @return 合并后的有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 使用dummy头节点简化边界处理
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 双指针遍历两个链表
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // 连接剩余的节点
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        return dummy.next;
    }

    /**
     * 判断链表是否为回文链表
     * <p>
     * 算法思路：
     * 1. 使用快慢指针找到链表中点
     * 2. 翻转后半部分链表
     * 3. 比较前半部分和翻转后的后半部分
     * 4. 恢复链表结构（可选）
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 第一步：找到链表中点
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 第二步：翻转后半部分链表
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null; // 断开前后两部分

        // 第三步：比较前半部分和后半部分
        ListNode firstHalf = head;
        boolean isPalindrome = true;
        
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                isPalindrome = false;
                break;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return isPalindrome;
    }

    /**
     * 翻转整个链表（辅助方法）
     * <p>
     * 用于 isPalindrome 方法中翻转后半部分链表
     *
     * @param head 链表头节点
     * @return 翻转后的链表头节点
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }

    // ==================== 辅助工具方法 ====================

    /**
     * 根据数组创建链表
     * <p>
     * 工具方法，便于测试和调试
     *
     * @param values 数组值
     * @return 创建的链表头节点
     */
    public static ListNode createFromArray(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode();
        ListNode current = dummy;

        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }

        return dummy.next;
    }

    /**
     * 将链表转换为数组
     * <p>
     * 工具方法，便于测试验证结果
     * 注意：此方法假设链表无环，最多遍历1000个节点防止无限循环
     *
     * @param head 链表头节点
     * @return 链表值组成的数组
     */
    public static int[] toArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        java.util.List<Integer> result = new java.util.ArrayList<>();
        ListNode current = head;
        int count = 0;

        // 防止无限循环，最多遍历1000个节点
        while (current != null && count < 1000) {
            result.add(current.val);
            current = current.next;
            count++;
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 计算链表长度
     * <p>
     * 注意：此方法假设链表无环
     *
     * @param head 链表头节点
     * @return 链表长度
     */
    public static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;

        while (current != null) {
            length++;
            current = current.next;
        }

        return length;
    }

    /**
     * 打印链表内容
     * <p>
     * 调试工具方法，格式：1 -> 2 -> 3 -> null
     *
     * @param head 链表头节点
     * @return 链表的字符串表示
     */
    public static String printList(ListNode head) {
        if (head == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        int count = 0;

        // 防止无限循环
        while (current != null && count < 100) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
            count++;
        }

        if (count >= 100) {
            sb.append(" -> ... (可能有环或链表过长)");
        } else {
            sb.append(" -> null");
        }

        return sb.toString();
    }

    /**
     * 检查两个链表是否相等（值相等且顺序相同）
     *
     * @param list1 第一个链表
     * @param list2 第二个链表
     * @return 是否相等
     */
    public static boolean areEqual(ListNode list1, ListNode list2) {
        ListNode current1 = list1;
        ListNode current2 = list2;

        while (current1 != null && current2 != null) {
            if (current1.val != current2.val) {
                return false;
            }
            current1 = current1.next;
            current2 = current2.next;
        }

        // 两个链表都应该同时到达末尾
        return current1 == null && current2 == null;
    }

    /**
     * 删除排序链表中的重复元素
     * <p>
     * 给定一个已排序的链表，删除所有重复的元素，使得每个元素只出现一次。
     * <p>
     * 算法原理：快慢指针
     * - `slow` 指针：指向当前不重复部分的最后一个节点。
     * - `fast` 指针：向前探索，寻找与 `slow` 指针值不同的新节点。
     * <p>
     * 过程：
     * 1. 初始化 `slow` 和 `fast` 都指向头节点 `head`。
     * 2. `fast` 指针遍历整个链表。
     * 3. 如果 `fast` 指向的节点值与 `slow` 不同，说明遇到了一个新的不重复元素。
     *    - 将 `slow` 的 `next` 指向 `fast`，将这个新元素连接到不重复部分的末尾。
     *    - `slow` 指针前进一步，指向这个新的末尾节点。
     * 4. 循环结束后，`fast` 指向 `null`。此时 `slow` 是不重复部分的最后一个节点。
     * 5. **关键步骤**：将 `slow.next` 设置为 `null`。这是为了处理链表末尾有重复元素的情况（例如 1->2->3->3->3）。
     *    如果不执行此步，`slow` 会指向第一个3，但它后面的 `3->3` 链依然存在。
     * <p>
     * 时间复杂度：O(n)，因为 `fast` 指针遍历链表一次。
     * 空间复杂度：O(1)，只使用了常数个额外指针。
     *
     * @param head 已排序链表的头节点
     * @return 删除重复元素后的链表头节点
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 如果链表为空，直接返回
        if (head == null) {
            return null;
        }

        // 初始化快慢指针
        ListNode slow = head, fast = head;
        while (fast != null) {
            // 当快指针找到一个与慢指针值不同的新节点时
            if (slow.val != fast.val) {
                // 将慢指针的 next 指向这个新节点，实际上是跳过了中间所有重复的节点
                slow.next = fast;
                // 慢指针前进到这个新的不重复节点上
                slow = slow.next;
            }
            // 快指针持续前进
            fast = fast.next;
        }

        // 易错点：循环结束后，slow 指向最后一个不重复元素。
        // 必须将它的 next 指针设为 null，以断开与后续可能存在的重复元素的连接。
        // 例如，对于 1->2->2，循环结束后 slow 指向第一个 2，fast 为 null。
        // 此时 slow.next 仍然指向第二个 2，需要断开它。
        slow.next = null;

        return head;
    }

    /**
     * 向右旋转链表 k 个位置
     * <p>
     * 算法思路：三次翻转法
     * 1. 翻转整个链表
     * 2. 翻转前 n 个节点（n = k % length）
     * 3. 翻转后 (length - n) 个节点
     * 4. 连接翻转后的两部分
     * <p>
     * 例如：[1,2,3,4,5] 向右旋转 2 位
     * 1. 翻转整个链表：[5,4,3,2,1]
     * 2. 翻转前2个：[4,5,3,2,1]
     * 3. 翻转后3个：[4,5,1,2,3]
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @param k    向右旋转的位置数
     * @return 旋转后的链表头节点
     */
    public ListNode rotateRight(ListNode head, int k) {
        // 边界情况：空链表或不需要旋转
        if (head == null || k == 0) {
            return head;
        }

        // 计算链表长度，优化旋转次数
        int length = getLength(head);
        int n = k % length; // 实际需要旋转的位置数

        if (n == 0) {
            // 关键边界检查：当 n = 0 时直接返回，避免不必要的翻转操作
            // 在不需要翻转的时候翻转，则下面 newHead1 是原数组的尾巴，newHead3 是原数组的头，newHead1.next = newHead3;会造成尾头相连的环
            return head;
        }

        // 第一步：翻转整个链表
        ListNode newHead1 = reverseFirstN(head, length);
        ListNode p = newHead1; // p 用于定位分割点

        // 第二步：找到分割点（前 n 个节点的下一个位置）
        // 易错的点：要越过的是前n个点，不是m个点
        for (int i = 0; i < n; i++) {
            p = p.next;
        }

        // 第三步：分别翻转前 n 个节点和后 (length - n) 个节点
        ListNode newHead2 = reverseFirstN(newHead1, n);        // 翻转前 n 个节点
        ListNode newHead3 = reverseFirstN(p, length - n);      // 翻转后 (length - n) 个节点
        
        // 第四步：连接两部分（newHead1 现在是前半部分的尾节点）
        newHead1.next = newHead3;

        // 返回新的头节点（前半部分翻转后的头节点）
        return newHead2;
    }

    /**
     * 翻转链表的前 n 个节点（迭代实现）
     * <p>
     * 算法思路：
     * 1. 使用三指针技术：prev（前驱）、current（当前）、tmp（临时保存下一个）
     * 2. 逐个翻转前 n 个节点的指针方向
     * 3. 翻转完成后，原头节点变成尾节点，连接剩余未翻转部分
     * <p>
     * 边界处理：
     * - n <= 1：不需要翻转或翻转1个节点等于不翻转，直接返回原链表
     * - n > 链表长度：只翻转实际存在的节点
     * <p>
     * 时间复杂度：O(min(n, 链表长度))
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @param n    需要翻转的节点数
     * @return 翻转后的链表头节点
     */
    ListNode reverseFirstN(ListNode head, int n) {
        // 边界情况：空链表或不需要翻转
        if (head == null || n <= 1) {
            return head;
        }
        
        // 初始化三个指针
        ListNode prev = null;    // 前驱节点，初始为null
        ListNode current = head; // 当前处理的节点
        
        // 翻转前 n 个节点
        while (current != null && n > 0) {
            ListNode tmp = current.next; // 临时保存下一个节点
            current.next = prev;         // 翻转当前节点的指针
            prev = current;              // 前驱指针前移
            current = tmp;               // 当前指针前移
            n--;                         // 计数器递减
        }
        
        // 翻转完成后的状态：
        // head 现在是翻转部分的尾节点
        // current 是未翻转部分的头节点  
        // prev 是翻转部分的新头节点
        
        // 连接翻转部分和未翻转部分
        head.next = current;
        
        // 返回翻转部分的新头节点
        return prev;
    }
}
