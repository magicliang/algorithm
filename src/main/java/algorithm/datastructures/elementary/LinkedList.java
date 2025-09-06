package algorithm.datastructures.elementary;

/**
 * 快慢指针算法示例
 * 
 * 快慢指针是双指针技巧的一种，两个指针以不同的速度移动。
 * 常用于链表问题，如检测环、寻找中点、寻找倒数第k个节点等。
 * 
 * 双指针策略：快慢指针移动
 * - 慢指针每次移动1步
 * - 快指针每次移动2步（或更多步）
 * - 通过速度差异来解决特定问题
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class LinkedList {
    
    /**
     * 链表节点定义
     * 
     * 提供三种构造方式：
     * 1. 默认构造：创建值为0的节点
     * 2. 值构造：创建指定值的节点
     * 3. 完整构造：创建指定值和下一个节点的节点
     */
    public static class ListNode {
        int val;        // 节点存储的值
        ListNode next;  // 指向下一个节点的指针
        
        /** 默认构造函数，创建值为0的节点 */
        ListNode() {}
        
        /** 
         * 值构造函数
         * @param val 节点的值
         */
        ListNode(int val) {
            this.val = val;
        }
        
        /** 
         * 完整构造函数
         * @param val 节点的值
         * @param next 下一个节点的引用
         */
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        
        /**
         * 重写toString方法，便于调试
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

        // 最后 prev 是新头
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
         * 
         * 算法思路：
         * 1. 递归到第n个节点作为新的头节点
         * 2. 在回溯过程中逐步反转指针方向
         * 3. 保存第n+1个节点作为successor，用于连接未反转部分
         * 
         * 时间复杂度：O(n)
         * 空间复杂度：O(n)（递归栈开销）
         *
         * @param head 链表头节点
         * @param n    需要反转的节点数
         * @return     反转后的链表头节点
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
         * 
         * 算法思路：
         * 1. 使用三个指针：prev（前驱）、current（当前）、next（后继）
         * 2. 逐个反转前n个节点的指针方向
         * 3. 反转完成后，原头节点变成尾节点，连接剩余未反转部分
         * 
         * 相比递归实现的优势：
         * - 空间复杂度更优：O(1)
         * - 避免递归栈溢出风险
         * - 逻辑更直观易懂
         * 
         * 时间复杂度：O(n)
         * 空间复杂度：O(1)
         *
         * @param head 链表头节点
         * @param n    需要反转的节点数
         * @return     反转后的链表头节点
         */
        public ListNode reverse2(ListNode head, int n) {
            ListNode current =  head;
            ListNode prev = null;

            // 边界情况处理
            if (head == null || n == 1) {
                return head;
            }

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

    }


        /**
         * 检测链表是否有环（Floyd判圈算法）
         *
         * 算法原理：
         * - 如果链表有环，快指针最终会追上慢指针
         * - 如果链表无环，快指针会先到达末尾
         *
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
     * 
     * 算法原理：
     * - 当快指针到达末尾时，慢指针正好在中间位置
     * - 对于偶数长度链表，返回第二个中间节点
     * 
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
     * 
     * 算法原理：
     * - 让快指针先移动k步
     * - 然后快慢指针同时移动，直到快指针到达末尾
     * - 此时慢指针指向倒数第k个节点
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 
     * @param head 链表头节点
     * @param k 倒数第k个
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
     * 没有 dummyHead 不能处理头结点删除
     * 如果有了 dummyHead，如果k等于链表长度，k+1 也能受得住，无从删除起
     * 所以查找节点不需要 dummyHead，删除节点需要
     * 定律：
     * 1. 如果要让 slow 停在倒数第k个节点，要让 fast 先走 k 步。
     * 2. 如果要删除 k 节点（包括头），就要让 slow 停在倒数 k + 1 处。
     * 3. 所以要让 fast 先走 k + 1 步。
     * @param head 链表头节点
     * @param k 倒数第k个
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
     * 这个算法是正确的，因为头结点可以被删除，所以 dummy 节点是必须的
     *
     * @param head 链表头节点
     * @param k 倒数第k个
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
        // 不管快慢节点是不是从 dummy 节点开始出发，他们之间的相对距离此时是 k，而且下面的循环仍然会让 fast 停在链表末尾的 null上，这也就意味着 slow会停在倒数 k 个节点上
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.val = slow.next.val;
        slow.next = slow.next.next;

        // 仍然使用 dummy 来解决这个问题
        return dummy.next;
    }

    /**
     * 求两个链表的交点（假设链表无环）
     * 
     * 算法原理：
     * - 双指针法：指针A从链表A出发，指针B从链表B出发。
     * - 当指针A到达链表A末尾时，跳转到链表B头部；指针B同理。
     * - 如果两链表相交，指针A和指针B会在交点相遇；否则同时到达末尾（null）。
     * 
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
     * 
     * 算法原理：
     * - 将链表A的尾部连接到其头部，形成环。
     * - 使用快慢指针法从链表B的头节点出发，检测环并找到入环点（即交点）。
     * - 最后还原链表A的结构。
     * 
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

}
