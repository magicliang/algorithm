package algorithm.advanced.two_pointers;

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
public class FastSlowPointers {
    
    /**
     * 链表节点定义
     */
    public static class ListNode {
        int val;
        ListNode next;
        
        ListNode() {}
        
        ListNode(int val) {
            this.val = val;
        }
        
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 反转链表
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @return 反转后的链表头节点
     */
    public ListNode reverse(ListNode head) {
        // 不使用 dummy节点的解法，实际上仍然是 O(1) 复杂度的算法

        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            // 这个算法每次都是引入 p c n
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
        if (head == null || k <= 0) {
            return null;
        }
        
        ListNode fast = head; // 快指针
        ListNode slow = head; // 慢指针
        
        // 快指针先移动k步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                return null; // k超出链表长度
            }
            fast = fast.next;
        }
        
        // 快慢指针同时移动，直到快指针到达末尾
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow; // 慢指针指向倒数第k个节点
    }
    
    /**
     * 删除链表的倒数第k个节点
     * 
     * @param head 链表头节点
     * @param k 倒数第k个
     * @return 删除节点后的链表头
     */
    public ListNode removeKthFromEnd(ListNode head, int k) {
        // 创建虚拟头节点，简化边界处理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 快指针先移动k+1步
        for (int i = 0; i <= k; i++) {
            fast = fast.next;
        }
        
        // 快慢指针同时移动
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // 删除倒数第k个节点
        slow.next = slow.next.next;
        
        return dummy.next;
    }


}