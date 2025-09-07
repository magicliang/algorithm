package algorithm.datastructures.elementary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {
    
    private LinkedList linkedList;
    
    @BeforeEach
    void setUp() {
        linkedList = new LinkedList();
    }
    
    // 辅助方法：创建链表
    private LinkedList.ListNode createList(int... values) {
        if (values.length == 0) return null;
        
        LinkedList.ListNode head = new LinkedList.ListNode(values[0]);
        LinkedList.ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new LinkedList.ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    // 辅助方法：将链表转换为数组
    private int[] listToArray(LinkedList.ListNode head) {
        if (head == null) return new int[0];
        
        java.util.List<Integer> result = new java.util.ArrayList<>();
        LinkedList.ListNode current = head;
        
        while (current != null) {
            result.add(current.val);
            current = current.next;
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    @Test
    void testReverse_NormalCase() {
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = linkedList.reverse(head);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, listToArray(result));
    }
    
    @Test
    void testReverse_EdgeCases() {
        // 测试 null
        assertNull(linkedList.reverse(null));
        
        // 测试单节点
        LinkedList.ListNode head = createList(1);
        assertArrayEquals(new int[]{1}, listToArray(linkedList.reverse(head)));
        
        // 测试双节点
        head = createList(1, 2);
        assertArrayEquals(new int[]{2, 1}, listToArray(linkedList.reverse(head)));
    }
    
    @Test
    void testReverser_Methods() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 测试 reverse 方法
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = reverser.reverse(head, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, listToArray(result));
        
        // 测试 reverse2 方法
        head = createList(1, 2, 3, 4, 5);
        result = reverser.reverse2(head, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, listToArray(result));
        
        // 测试 reverse3 方法
        head = createList(1, 2, 3, 4, 5);
        result = reverser.reverse3(head, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, listToArray(result));
    }
    
    @Test
    void testReverser_EdgeCases() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 测试 reverse2 边界情况
        assertNull(reverser.reverse2(null, 3));
        
        LinkedList.ListNode head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(reverser.reverse2(head, 0)));
        
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(reverser.reverse2(head, -1)));
        
        // 测试 reverse3 边界情况
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(reverser.reverse3(head, 0)));
        
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(reverser.reverse3(head, -1)));
    }
    
    @Test
    void testReverseFirstN_NormalCase() {
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = linkedList.reverseFirstN(head, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, listToArray(result));
    }
    
    @Test
    void testReverseFirstN_EdgeCases() {
        // 测试 null
        assertNull(linkedList.reverseFirstN(null, 3));
        
        // 测试 n <= 1
        LinkedList.ListNode head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(linkedList.reverseFirstN(head, 0)));
        
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(linkedList.reverseFirstN(head, 1)));
        
        // 测试 n = 2
        head = createList(1, 2, 3, 4);
        assertArrayEquals(new int[]{2, 1, 3, 4}, listToArray(linkedList.reverseFirstN(head, 2)));
        
        // 测试 n 等于链表长度
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{3, 2, 1}, listToArray(linkedList.reverseFirstN(head, 3)));
    }
    
    @Test
    void testRotateRight_NormalCase() {
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = linkedList.rotateRight(head, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, listToArray(result));
    }
    
    @Test
    void testRotateRight_EdgeCases() {
        // 测试 null
        assertNull(linkedList.rotateRight(null, 2));
        
        // 测试 k = 0
        LinkedList.ListNode head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(linkedList.rotateRight(head, 0)));
        
        // 测试单节点 k = 1 (这是你提到的关键测试用例)
        head = createList(1);
        assertArrayEquals(new int[]{1}, listToArray(linkedList.rotateRight(head, 1)));
        
        // 测试 k 等于链表长度
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(linkedList.rotateRight(head, 3)));
        
        // 测试 k 大于链表长度
        head = createList(1, 2, 3);
        assertArrayEquals(new int[]{3, 1, 2}, listToArray(linkedList.rotateRight(head, 4)));
        
        // 测试双节点
        head = createList(1, 2);
        assertArrayEquals(new int[]{2, 1}, listToArray(linkedList.rotateRight(head, 1)));
    }
    
    @Test
    void testHasCycle() {
        // 测试无环链表
        LinkedList.ListNode head = createList(1, 2, 3, 4);
        assertFalse(linkedList.hasCycle(head));
        
        // 测试有环链表
        LinkedList.ListNode node1 = new LinkedList.ListNode(1);
        LinkedList.ListNode node2 = new LinkedList.ListNode(2);
        LinkedList.ListNode node3 = new LinkedList.ListNode(3);
        LinkedList.ListNode node4 = new LinkedList.ListNode(4);
        
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2; // 形成环
        
        assertTrue(linkedList.hasCycle(node1));
        
        // 测试单节点自环
        LinkedList.ListNode selfLoop = new LinkedList.ListNode(1);
        selfLoop.next = selfLoop;
        assertTrue(linkedList.hasCycle(selfLoop));
        
        // 测试空链表
        assertFalse(linkedList.hasCycle(null));
        
        // 测试单节点无环
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertFalse(linkedList.hasCycle(single));
    }
    
    @Test
    void testDeleteDuplicates() {
        // 测试有重复的链表
        LinkedList.ListNode head = createList(1, 1, 2, 3, 3);
        LinkedList.ListNode result = linkedList.deleteDuplicates(head);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(result));
        
        // 测试无重复的链表
        head = createList(1, 2, 3);
        result = linkedList.deleteDuplicates(head);
        assertArrayEquals(new int[]{1, 2, 3}, listToArray(result));
        
        // 测试全部重复的链表
        head = createList(1, 1, 1);
        result = linkedList.deleteDuplicates(head);
        assertArrayEquals(new int[]{1}, listToArray(result));
        
        // 测试空链表
        assertNull(linkedList.deleteDuplicates(null));
        
        // 测试单节点
        head = createList(1);
        result = linkedList.deleteDuplicates(head);
        assertArrayEquals(new int[]{1}, listToArray(result));
    }
    
    @Test
    void testFindMiddle() {
        // 测试奇数长度链表
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode middle = linkedList.findMiddle(head);
        assertEquals(3, middle.val);
        
        // 测试偶数长度链表
        head = createList(1, 2, 3, 4);
        middle = linkedList.findMiddle(head);
        assertEquals(3, middle.val); // 返回第二个中间节点
        
        // 测试单节点
        head = createList(1);
        middle = linkedList.findMiddle(head);
        assertEquals(1, middle.val);
        
        // 测试双节点
        head = createList(1, 2);
        middle = linkedList.findMiddle(head);
        assertEquals(2, middle.val);
        
        // 测试空链表
        assertNull(linkedList.findMiddle(null));
    }
    
    @Test
    void testFindKthFromEnd() {
        // 测试正常情况
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode kth = linkedList.findKthFromEnd(head, 2);
        assertEquals(4, kth.val);
        
        // 测试 k = 1 (最后一个节点)
        kth = linkedList.findKthFromEnd(head, 1);
        assertEquals(5, kth.val);
        
        // 测试 k 等于链表长度 (第一个节点)
        kth = linkedList.findKthFromEnd(head, 5);
        assertEquals(1, kth.val);
        
        // 测试 k 大于链表长度
        assertNull(linkedList.findKthFromEnd(head, 6));
        
        // 测试空链表
        assertNull(linkedList.findKthFromEnd(null, 1));
    }
    
    @Test
    void testRemoveKthFromEnd() {
        // 测试删除倒数第2个节点
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = linkedList.removeKthFromEnd(head, 2);
        assertArrayEquals(new int[]{1, 2, 3, 5}, listToArray(result));
        
        // 测试删除最后一个节点
        head = createList(1, 2, 3);
        result = linkedList.removeKthFromEnd(head, 1);
        assertArrayEquals(new int[]{1, 2}, listToArray(result));
        
        // 测试删除第一个节点
        head = createList(1, 2, 3);
        result = linkedList.removeKthFromEnd(head, 3);
        assertArrayEquals(new int[]{2, 3}, listToArray(result));
        
        // 测试单节点
        head = createList(1);
        result = linkedList.removeKthFromEnd(head, 1);
        assertArrayEquals(new int[]{}, listToArray(result));
    }
    
    @Test
    void testRemoveKthFromEnd2() {
        // 测试删除倒数第2个节点
        LinkedList.ListNode head = createList(1, 2, 3, 4, 5);
        LinkedList.ListNode result = linkedList.removeKthFromEnd2(head, 2);
        assertArrayEquals(new int[]{1, 2, 3, 5}, listToArray(result));
        
        // 测试删除最后一个节点
        head = createList(1, 2, 3);
        result = linkedList.removeKthFromEnd2(head, 1);
        assertArrayEquals(new int[]{1, 2}, listToArray(result));
        
        // 测试删除第一个节点
        head = createList(1, 2, 3);
        result = linkedList.removeKthFromEnd2(head, 3);
        assertArrayEquals(new int[]{2, 3}, listToArray(result));
    }
    
    @Test
    void testGetIntersectionNode() {
        // 创建两个相交的链表
        LinkedList.ListNode common = createList(8, 4, 5);
        
        LinkedList.ListNode headA = createList(4, 1);
        LinkedList.ListNode tailA = headA.next;
        tailA.next = common;
        
        LinkedList.ListNode headB = createList(5, 6, 1);
        LinkedList.ListNode tailB = headB.next.next;
        tailB.next = common;
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNode(headA, headB);
        assertEquals(common, intersection);
        
        // 测试不相交的链表
        LinkedList.ListNode headC = createList(1, 2, 3);
        LinkedList.ListNode headD = createList(4, 5, 6);
        assertNull(linkedList.getIntersectionNode(headC, headD));
        
        // 测试空链表
        assertNull(linkedList.getIntersectionNode(null, headA));
        assertNull(linkedList.getIntersectionNode(headA, null));
    }
}