package algorithm.datastructures.elementary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinkedList类的单元测试
 * 测试快慢指针算法和链表操作的各种场景
 * 
 * @author magicliang
 * @date 2025-09-06
 */
public class LinkedListTest {
    
    private LinkedList linkedList;
    
    @BeforeEach
    void setUp() {
        linkedList = new LinkedList();
    }
    
    /**
     * 创建测试用的链表：1->2->3->4->5
     */
    private LinkedList.ListNode createTestList() {
        return LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
    }
    
    /**
     * 创建带环的测试链表：1->2->3->4->5->2 (环从节点2开始)
     */
    private LinkedList.ListNode createCyclicList() {
        LinkedList.ListNode node1 = new LinkedList.ListNode(1);
        LinkedList.ListNode node2 = new LinkedList.ListNode(2);
        LinkedList.ListNode node3 = new LinkedList.ListNode(3);
        LinkedList.ListNode node4 = new LinkedList.ListNode(4);
        LinkedList.ListNode node5 = new LinkedList.ListNode(5);
        
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2; // 形成环
        
        return node1;
    }
    
    /**
     * 将链表转换为数组，用于验证结果
     */
    private int[] listToArray(LinkedList.ListNode head) {
        return LinkedList.toArray(head);
    }
    
    // ==================== 反转链表测试 ====================
    
    @Test
    void testReverse_normalList() {
        LinkedList.ListNode head = createTestList();
        LinkedList.ListNode reversed = linkedList.reverse(head);
        
        int[] expected = {5, 4, 3, 2, 1};
        int[] actual = listToArray(reversed);
        
        assertArrayEquals(expected, actual, "反转链表结果不正确");
    }
    
    @Test
    void testReverse_singleNode() {
        LinkedList.ListNode head = new LinkedList.ListNode(1);
        LinkedList.ListNode reversed = linkedList.reverse(head);
        
        assertEquals(1, reversed.val, "单节点反转后值应该不变");
        assertNull(reversed.next, "单节点反转后next应该为null");
    }
    
    @Test
    void testReverse_emptyList() {
        LinkedList.ListNode reversed = linkedList.reverse(null);
        assertNull(reversed, "空链表反转后应该为null");
    }
    
    // ==================== 反转前n个节点测试 ====================
    
    @Test
    void testReverseFirstN_recursiveVersion() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = createTestList();
        
        // 反转前3个节点：1->2->3->4->5 变成 3->2->1->4->5
        LinkedList.ListNode result = reverser.reverse(head, 3);
        
        int[] expected = {3, 2, 1, 4, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "递归版本反转前3个节点结果不正确");
    }
    
    @Test
    void testReverseFirstN_iterativeVersion() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = createTestList();
        
        // 反转前3个节点：1->2->3->4->5 变成 3->2->1->4->5
        LinkedList.ListNode result = reverser.reverse2(head, 3);
        
        int[] expected = {3, 2, 1, 4, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "迭代版本反转前3个节点结果不正确");
    }
    
    @Test
    void testReverseFirstN_reverseAll() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = createTestList();
        
        // 反转所有5个节点
        LinkedList.ListNode result = reverser.reverse2(head, 5);
        
        int[] expected = {5, 4, 3, 2, 1};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "反转所有节点结果不正确");
    }
    
    @Test
    void testReverseFirstN_singleNode() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = new LinkedList.ListNode(1);
        
        LinkedList.ListNode result = reverser.reverse2(head, 1);
        
        assertEquals(1, result.val, "反转单个节点值应该不变");
        assertNull(result.next, "反转单个节点next应该为null");
    }
    
    @Test
    void testReverseFirstN_dummyHeadVersion() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = createTestList();
        
        // 反转前3个节点：1->2->3->4->5 变成 3->2->1->4->5
        LinkedList.ListNode result = reverser.reverse3(head, 3);
        
        int[] expected = {3, 2, 1, 4, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "dummy头节点版本反转前3个节点结果不正确");
    }
    
    @Test
    void testReverseFirstN_dummyHeadVersion_reverseAll() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = createTestList();
        
        // 反转所有5个节点
        LinkedList.ListNode result = reverser.reverse3(head, 5);
        
        int[] expected = {5, 4, 3, 2, 1};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "dummy头节点版本反转所有节点结果不正确");
    }
    
    @Test
    void testReverseFirstN_dummyHeadVersion_singleNode() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        LinkedList.ListNode head = new LinkedList.ListNode(42);
        
        LinkedList.ListNode result = reverser.reverse3(head, 1);
        
        assertEquals(42, result.val, "dummy头节点版本反转单个节点值应该不变");
        assertNull(result.next, "dummy头节点版本反转单个节点next应该为null");
    }
    
    @Test
    void testReverseFirstN_compareAllVersions() {
        // 比较三种实现的结果是否一致
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 创建三个相同的测试链表
        LinkedList.ListNode head1 = createTestList();
        LinkedList.ListNode head2 = createTestList();
        LinkedList.ListNode head3 = createTestList();
        
        // 使用三种不同的方法反转前3个节点
        LinkedList.ListNode result1 = reverser.reverse(head1, 3);   // 递归版本
        LinkedList.ListNode result2 = reverser.reverse2(head2, 3);  // 迭代版本
        LinkedList.ListNode result3 = reverser.reverse3(head3, 3);  // dummy头节点版本
        
        // 验证三种方法的结果是否一致
        int[] array1 = listToArray(result1);
        int[] array2 = listToArray(result2);
        int[] array3 = listToArray(result3);
        
        assertArrayEquals(array1, array2, "递归版本和迭代版本结果应该一致");
        assertArrayEquals(array2, array3, "迭代版本和dummy头节点版本结果应该一致");
        assertArrayEquals(array1, array3, "递归版本和dummy头节点版本结果应该一致");
        
        // 验证具体结果
        int[] expected = {3, 2, 1, 4, 5};
        assertArrayEquals(expected, array1, "所有版本的结果都应该正确");
    }
    
    // ==================== 环检测测试 ====================
    
    @Test
    void testHasCycle_withCycle() {
        LinkedList.ListNode cyclicList = createCyclicList();
        assertTrue(linkedList.hasCycle(cyclicList), "应该检测到环");
    }
    
    @Test
    void testHasCycle_withoutCycle() {
        LinkedList.ListNode normalList = createTestList();
        assertFalse(linkedList.hasCycle(normalList), "不应该检测到环");
    }
    
    @Test
    void testHasCycle_emptyList() {
        assertFalse(linkedList.hasCycle(null), "空链表不应该有环");
    }
    
    @Test
    void testHasCycle_singleNode() {
        LinkedList.ListNode singleNode = new LinkedList.ListNode(1);
        assertFalse(linkedList.hasCycle(singleNode), "单节点无环链表不应该有环");
    }
    
    @Test
    void testHasCycle_singleNodeWithSelfCycle() {
        LinkedList.ListNode selfCycle = new LinkedList.ListNode(1);
        selfCycle.next = selfCycle;
        assertTrue(linkedList.hasCycle(selfCycle), "自环节点应该检测到环");
    }
    
    // ==================== 寻找中间节点测试 ====================
    
    @Test
    void testFindMiddle_oddLength() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        LinkedList.ListNode middle = linkedList.findMiddle(head);
        
        assertEquals(3, middle.val, "奇数长度链表的中间节点应该是3");
    }
    
    @Test
    void testFindMiddle_evenLength() {
        // 创建偶数长度链表：1->2->3->4
        LinkedList.ListNode head = createTestList();
        LinkedList.ListNode current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        current.next = null; // 移除最后一个节点
        
        LinkedList.ListNode middle = linkedList.findMiddle(head);
        assertEquals(3, middle.val, "偶数长度链表应该返回第二个中间节点");
    }
    
    @Test
    void testFindMiddle_singleNode() {
        LinkedList.ListNode singleNode = new LinkedList.ListNode(1);
        LinkedList.ListNode middle = linkedList.findMiddle(singleNode);
        
        assertEquals(1, middle.val, "单节点链表的中间节点就是自己");
    }
    
    @Test
    void testFindMiddle_emptyList() {
        LinkedList.ListNode middle = linkedList.findMiddle(null);
        assertNull(middle, "空链表的中间节点应该为null");
    }
    
    // ==================== 寻找倒数第k个节点测试 ====================
    
    @Test
    void testFindKthFromEnd_normalCase() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        
        LinkedList.ListNode result = linkedList.findKthFromEnd(head, 2);
        assertEquals(4, result.val, "倒数第2个节点应该是4");
        
        result = linkedList.findKthFromEnd(head, 1);
        assertEquals(5, result.val, "倒数第1个节点应该是5");
        
        result = linkedList.findKthFromEnd(head, 5);
        assertEquals(1, result.val, "倒数第5个节点应该是1");
    }
    
    @Test
    void testFindKthFromEnd_kTooLarge() {
        LinkedList.ListNode head = createTestList(); // 长度为5
        LinkedList.ListNode result = linkedList.findKthFromEnd(head, 6);
        
        assertNull(result, "k超出链表长度应该返回null");
    }
    
    @Test
    void testFindKthFromEnd_invalidK() {
        LinkedList.ListNode head = createTestList();
        
        assertNull(linkedList.findKthFromEnd(head, 0), "k=0应该返回null");
        assertNull(linkedList.findKthFromEnd(head, -1), "k<0应该返回null");
    }
    
    @Test
    void testFindKthFromEnd_emptyList() {
        LinkedList.ListNode result = linkedList.findKthFromEnd(null, 1);
        assertNull(result, "空链表应该返回null");
    }
    
    // ==================== 删除倒数第k个节点测试 ====================
    
    @Test
    void testRemoveKthFromEnd_normalCase() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        
        // 删除倒数第2个节点（节点4）
        LinkedList.ListNode result = linkedList.removeKthFromEnd(head, 2);
        
        int[] expected = {1, 2, 3, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "删除倒数第2个节点后应该是1->2->3->5");
    }
    
    @Test
    void testRemoveKthFromEnd_removeHead() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        
        // 删除倒数第5个节点（头节点）
        LinkedList.ListNode result = linkedList.removeKthFromEnd(head, 5);
        
        int[] expected = {2, 3, 4, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "删除头节点后应该是2->3->4->5");
    }
    
    @Test
    void testRemoveKthFromEnd_removeTail() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        
        // 删除倒数第1个节点（尾节点）
        LinkedList.ListNode result = linkedList.removeKthFromEnd(head, 1);
        
        int[] expected = {1, 2, 3, 4};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "删除尾节点后应该是1->2->3->4");
    }
    
    @Test
    void testRemoveKthFromEnd_singleNode() {
        LinkedList.ListNode singleNode = new LinkedList.ListNode(1);
        
        LinkedList.ListNode result = linkedList.removeKthFromEnd(singleNode, 1);
        
        assertNull(result, "删除单节点链表的唯一节点后应该为null");
    }
    
    // ==================== 链表交点测试 ====================
    
    @Test
    void testGetIntersectionNode_withIntersection() {
        // 创建两个相交的链表
        // listA: 1->2->3->6->7
        // listB: 4->5->3->6->7 (从节点3开始相交)
        LinkedList.ListNode node3 = new LinkedList.ListNode(3);
        LinkedList.ListNode node6 = new LinkedList.ListNode(6);
        LinkedList.ListNode node7 = new LinkedList.ListNode(7);
        node3.next = node6;
        node6.next = node7;
        
        LinkedList.ListNode listA = new LinkedList.ListNode(1);
        listA.next = new LinkedList.ListNode(2);
        listA.next.next = node3;
        
        LinkedList.ListNode listB = new LinkedList.ListNode(4);
        listB.next = new LinkedList.ListNode(5);
        listB.next.next = node3;
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNode(listA, listB);
        
        assertEquals(3, intersection.val, "交点应该是节点3");
        assertSame(node3, intersection, "应该返回相同的节点引用");
    }
    
    @Test
    void testGetIntersectionNode_noIntersection() {
        LinkedList.ListNode listA = createTestList(); // 1->2->3->4->5
        
        LinkedList.ListNode listB = new LinkedList.ListNode(6);
        listB.next = new LinkedList.ListNode(7);
        listB.next.next = new LinkedList.ListNode(8);
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNode(listA, listB);
        
        assertNull(intersection, "无交点的链表应该返回null");
    }
    
    @Test
    void testGetIntersectionNode_emptyLists() {
        assertNull(linkedList.getIntersectionNode(null, null), "两个空链表应该返回null");
        assertNull(linkedList.getIntersectionNode(createTestList(), null), "一个空链表应该返回null");
        assertNull(linkedList.getIntersectionNode(null, createTestList()), "一个空链表应该返回null");
    }
    
    // ==================== 通过环检测找交点测试 ====================
    
    @Test
    void testGetIntersectionNodeByCycle_withIntersection() {
        // 创建两个相交的链表
        LinkedList.ListNode node3 = new LinkedList.ListNode(3);
        LinkedList.ListNode node6 = new LinkedList.ListNode(6);
        LinkedList.ListNode node7 = new LinkedList.ListNode(7);
        node3.next = node6;
        node6.next = node7;
        
        LinkedList.ListNode listA = new LinkedList.ListNode(1);
        listA.next = new LinkedList.ListNode(2);
        listA.next.next = node3;
        
        LinkedList.ListNode listB = new LinkedList.ListNode(4);
        listB.next = new LinkedList.ListNode(5);
        listB.next.next = node3;
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNodeByCycle(listA, listB);
        
        assertEquals(3, intersection.val, "通过环检测找到的交点应该是节点3");
        assertSame(node3, intersection, "应该返回相同的节点引用");
    }
    
    @Test
    void testGetIntersectionNodeByCycle_noIntersection() {
        LinkedList.ListNode listA = createTestList();
        
        LinkedList.ListNode listB = new LinkedList.ListNode(6);
        listB.next = new LinkedList.ListNode(7);
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNodeByCycle(listA, listB);
        
        assertNull(intersection, "无交点时通过环检测应该返回null");
    }
    
    // ==================== 边界情况和异常测试 ====================
    
    @Test
    void testRemoveKthFromEnd2_normalCase() {
        LinkedList.ListNode head = createTestList(); // 1->2->3->4->5
        
        // 删除倒数第3个节点（节点3）
        LinkedList.ListNode result = linkedList.removeKthFromEnd2(head, 3);
        
        int[] expected = {1, 2, 4, 5};
        int[] actual = listToArray(result);
        
        assertArrayEquals(expected, actual, "removeKthFromEnd2删除倒数第3个节点后应该是1->2->4->5");
    }
    
    @Test
    void testListNodeConstructors() {
        // 测试ListNode的各种构造函数
        LinkedList.ListNode node1 = new LinkedList.ListNode();
        assertEquals(0, node1.val, "默认构造函数应该设置val为0");
        assertNull(node1.next, "默认构造函数应该设置next为null");
        
        LinkedList.ListNode node2 = new LinkedList.ListNode(42);
        assertEquals(42, node2.val, "单参数构造函数应该正确设置val");
        assertNull(node2.next, "单参数构造函数应该设置next为null");
        
        LinkedList.ListNode node3 = new LinkedList.ListNode(99, node2);
        assertEquals(99, node3.val, "双参数构造函数应该正确设置val");
        assertSame(node2, node3.next, "双参数构造函数应该正确设置next");
    }
    
    // ==================== 工具方法测试 ====================
    
    @Test
    void testCreateFromArray() {
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        
        int[] result = LinkedList.toArray(head);
        assertArrayEquals(values, result, "从数组创建的链表应该与原数组相同");
    }
    
    @Test
    void testCreateFromArray_emptyArray() {
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{});
        assertNull(head, "空数组应该创建null链表");
        
        head = LinkedList.createFromArray(null);
        assertNull(head, "null数组应该创建null链表");
    }
    
    @Test
    void testToArray() {
        LinkedList.ListNode head = createTestList();
        int[] result = LinkedList.toArray(head);
        
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, result, "toArray应该正确转换链表");
    }
    
    @Test
    void testGetLength() {
        LinkedList.ListNode head = createTestList();
        assertEquals(5, LinkedList.getLength(head), "链表长度应该是5");
        
        assertEquals(0, LinkedList.getLength(null), "空链表长度应该是0");
        
        LinkedList.ListNode singleNode = new LinkedList.ListNode(1);
        assertEquals(1, LinkedList.getLength(singleNode), "单节点链表长度应该是1");
    }
    
    @Test
    void testPrintList() {
        LinkedList.ListNode head = createTestList();
        String result = LinkedList.printList(head);
        
        assertEquals("1 -> 2 -> 3 -> 4 -> 5 -> null", result, "打印结果应该正确");
        
        assertEquals("null", LinkedList.printList(null), "空链表打印应该是null");
        
        LinkedList.ListNode singleNode = new LinkedList.ListNode(42);
        assertEquals("42 -> null", LinkedList.printList(singleNode), "单节点打印应该正确");
    }
    
    @Test
    void testAreEqual() {
        LinkedList.ListNode list1 = LinkedList.createFromArray(new int[]{1, 2, 3});
        LinkedList.ListNode list2 = LinkedList.createFromArray(new int[]{1, 2, 3});
        LinkedList.ListNode list3 = LinkedList.createFromArray(new int[]{1, 2, 4});
        
        assertTrue(LinkedList.areEqual(list1, list2), "相同内容的链表应该相等");
        assertFalse(LinkedList.areEqual(list1, list3), "不同内容的链表应该不相等");
        
        assertTrue(LinkedList.areEqual(null, null), "两个null链表应该相等");
        assertFalse(LinkedList.areEqual(list1, null), "非null和null链表应该不相等");
        assertFalse(LinkedList.areEqual(null, list1), "null和非null链表应该不相等");
    }
    
    @Test
    void testListNodeToString() {
        LinkedList.ListNode node = new LinkedList.ListNode(42);
        assertEquals("ListNode{val=42}", node.toString(), "toString方法应该正确格式化");
    }
}