package algorithm.advanced.two_pointers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import algorithm.advanced.two_pointers.FastSlowPointers.ListNode;

/**
 * FastSlowPointers的测试类
 * 
 * @author magicliang
 * @date 2025-09-02
 */
public class FastSlowPointersTest {

    private FastSlowPointers solution;

    @BeforeEach
    void setUp() {
        solution = new FastSlowPointers();
    }

    // 辅助方法：创建链表
    private ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }

    // 辅助方法：创建有环链表
    private ListNode createCyclicList(int[] values, int cycleStart) {
        ListNode head = createList(values);
        if (head == null || cycleStart < 0 || cycleStart >= values.length) {
            return head;
        }
        
        ListNode tail = head;
        ListNode cycleNode = head;
        
        // 找到尾节点和环开始节点
        for (int i = 0; i < values.length - 1; i++) {
            if (i == cycleStart) {
                cycleNode = tail;
            }
            tail = tail.next;
        }
        
        // 创建环
        tail.next = cycleNode;
        return head;
    }

    @Test
    void testHasCycleWithCycle() {
        // 创建有环链表：1->2->3->4->2 (环从索引1开始)
        ListNode head = createCyclicList(new int[]{1, 2, 3, 4}, 1);
        assertTrue(solution.hasCycle(head), "应该检测到环");
    }

    @Test
    void testHasCycleWithoutCycle() {
        // 创建无环链表：1->2->3->4->null
        ListNode head = createList(new int[]{1, 2, 3, 4});
        assertFalse(solution.hasCycle(head), "不应该检测到环");
    }

    @Test
    void testHasCycleEmptyList() {
        assertFalse(solution.hasCycle(null), "空链表不应该有环");
    }

    @Test
    void testHasCycleSingleNode() {
        ListNode head = new ListNode(1);
        assertFalse(solution.hasCycle(head), "单节点无环链表不应该有环");
        
        // 创建自环
        head.next = head;
        assertTrue(solution.hasCycle(head), "单节点自环应该检测到环");
    }

    @Test
    void testFindMiddleOddLength() {
        // 奇数长度：1->2->3->4->5，中间是3
        ListNode head = createList(new int[]{1, 2, 3, 4, 5});
        ListNode middle = solution.findMiddle(head);
        assertEquals(3, middle.val, "奇数长度链表中间节点错误");
    }

    @Test
    void testFindMiddleEvenLength() {
        // 偶数长度：1->2->3->4，中间是3（第二个中间节点）
        ListNode head = createList(new int[]{1, 2, 3, 4});
        ListNode middle = solution.findMiddle(head);
        assertEquals(3, middle.val, "偶数长度链表中间节点错误");
    }

    @Test
    void testFindMiddleSingleNode() {
        ListNode head = new ListNode(1);
        ListNode middle = solution.findMiddle(head);
        assertEquals(1, middle.val, "单节点链表中间节点错误");
    }

    @Test
    void testFindMiddleEmptyList() {
        assertNull(solution.findMiddle(null), "空链表中间节点应该为null");
    }

    @Test
    void testFindKthFromEnd() {
        // 链表：1->2->3->4->5
        ListNode head = createList(new int[]{1, 2, 3, 4, 5});
        
        // 倒数第1个：5
        ListNode kth1 = solution.findKthFromEnd(head, 1);
        assertEquals(5, kth1.val, "倒数第1个节点错误");
        
        // 倒数第3个：3
        ListNode kth3 = solution.findKthFromEnd(head, 3);
        assertEquals(3, kth3.val, "倒数第3个节点错误");
        
        // 倒数第5个：1
        ListNode kth5 = solution.findKthFromEnd(head, 5);
        assertEquals(1, kth5.val, "倒数第5个节点错误");
    }

    @Test
    void testFindKthFromEndInvalidK() {
        ListNode head = createList(new int[]{1, 2, 3});
        
        // k超出链表长度
        assertNull(solution.findKthFromEnd(head, 5), "k超出长度应该返回null");
        
        // k为0或负数
        assertNull(solution.findKthFromEnd(head, 0), "k为0应该返回null");
        assertNull(solution.findKthFromEnd(head, -1), "k为负数应该返回null");
    }

    @Test
    void testRemoveKthFromEnd() {
        // 链表：1->2->3->4->5，删除倒数第2个（4）
        ListNode head = createList(new int[]{1, 2, 3, 4, 5});
        ListNode result = solution.removeKthFromEnd(head, 2);
        
        // 验证结果：1->2->3->5
        int[] expected = {1, 2, 3, 5};
        ListNode current = result;
        for (int value : expected) {
            assertNotNull(current, "链表长度不正确");
            assertEquals(value, current.val, "节点值不正确");
            current = current.next;
        }
        assertNull(current, "链表应该结束");
    }

    @Test
    void testRemoveKthFromEndFirstNode() {
        // 删除倒数第5个（第1个）：1->2->3->4->5 变成 2->3->4->5
        ListNode head = createList(new int[]{1, 2, 3, 4, 5});
        ListNode result = solution.removeKthFromEnd(head, 5);
        
        assertEquals(2, result.val, "删除第一个节点后，头节点应该是2");
    }

    @Test
    void testRemoveKthFromEndSingleNode() {
        // 删除单节点链表的倒数第1个
        ListNode head = new ListNode(1);
        ListNode result = solution.removeKthFromEnd(head, 1);
        
        assertNull(result, "删除单节点后应该返回null");
    }
}