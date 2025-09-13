package algorithm.datastructures.elementary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinkedList类的全面测试用例
 * 
 * 测试覆盖：
 * 1. 工具方法测试
 * 2. 基础链表操作测试
 * 3. 快慢指针算法测试
 * 4. 链表翻转算法测试
 * 5. 边界条件和异常情况测试
 * 
 * @author magicliang
 * @date 2025-09-13
 */
public class LinkedListTest {

    private LinkedList linkedList;

    @BeforeEach
    void setUp() {
        linkedList = new LinkedList();
    }

    // ==================== 工具方法测试 ====================

    @Test
    @DisplayName("测试从数组创建链表")
    void testCreateFromArray() {
        // 测试正常情况
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        
        assertNotNull(head);
        assertEquals(1, head.val);
        assertEquals(2, head.next.val);
        assertEquals(3, head.next.next.val);
        assertEquals(4, head.next.next.next.val);
        assertEquals(5, head.next.next.next.next.val);
        assertNull(head.next.next.next.next.next);
        
        // 测试空数组
        assertNull(LinkedList.createFromArray(new int[]{}));
        assertNull(LinkedList.createFromArray(null));
        
        // 测试单个元素
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{42});
        assertNotNull(single);
        assertEquals(42, single.val);
        assertNull(single.next);
    }

    @Test
    @DisplayName("测试链表转数组")
    void testToArray() {
        // 测试正常情况
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        int[] result = LinkedList.toArray(head);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
        
        // 测试空链表
        assertArrayEquals(new int[]{}, LinkedList.toArray(null));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        assertArrayEquals(new int[]{42}, LinkedList.toArray(single));
    }

    @Test
    @DisplayName("测试计算链表长度")
    void testGetLength() {
        // 测试空链表
        assertEquals(0, LinkedList.getLength(null));
        
        // 测试不同长度的链表
        assertEquals(1, LinkedList.getLength(LinkedList.createFromArray(new int[]{1})));
        assertEquals(3, LinkedList.getLength(LinkedList.createFromArray(new int[]{1, 2, 3})));
        assertEquals(5, LinkedList.getLength(LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5})));
    }

    @Test
    @DisplayName("测试打印链表")
    void testPrintList() {
        // 测试空链表
        assertEquals("null", LinkedList.printList(null));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        assertEquals("42 -> null", LinkedList.printList(single));
        
        // 测试多个节点
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{1, 2, 3});
        assertEquals("1 -> 2 -> 3 -> null", LinkedList.printList(head));
    }

    @Test
    @DisplayName("测试链表相等性检查")
    void testAreEqual() {
        // 测试两个空链表
        assertTrue(LinkedList.areEqual(null, null));
        
        // 测试一个空一个非空
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3});
        assertFalse(LinkedList.areEqual(null, head1));
        assertFalse(LinkedList.areEqual(head1, null));
        
        // 测试相同的链表
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3});
        assertTrue(LinkedList.areEqual(head1, head2));
        
        // 测试不同的链表
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 4});
        assertFalse(LinkedList.areEqual(head1, head3));
        
        // 测试长度不同的链表
        LinkedList.ListNode head4 = LinkedList.createFromArray(new int[]{1, 2});
        assertFalse(LinkedList.areEqual(head1, head4));
    }

    // ==================== 基础链表操作测试 ====================

    @Test
    @DisplayName("测试链表反转")
    void testReverse() {
        // 测试空链表
        assertNull(linkedList.reverse(null));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        LinkedList.ListNode reversedSingle = linkedList.reverse(single);
        assertEquals(42, reversedSingle.val);
        assertNull(reversedSingle.next);
        
        // 测试多个节点
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode reversed = linkedList.reverse(head);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, LinkedList.toArray(reversed));
        
        // 测试两个节点
        LinkedList.ListNode twoNodes = LinkedList.createFromArray(new int[]{1, 2});
        LinkedList.ListNode reversedTwo = linkedList.reverse(twoNodes);
        assertArrayEquals(new int[]{2, 1}, LinkedList.toArray(reversedTwo));
    }

    @Test
    @DisplayName("测试合并两个有序链表")
    void testMergeTwoLists() {
        // 测试两个空链表
        assertNull(linkedList.mergeTwoLists(null, null));
        
        // 测试一个空一个非空
        LinkedList.ListNode list1 = LinkedList.createFromArray(new int[]{1, 2, 3});
        LinkedList.ListNode merged1 = linkedList.mergeTwoLists(null, list1);
        assertArrayEquals(new int[]{1, 2, 3}, LinkedList.toArray(merged1));
        
        LinkedList.ListNode list2 = LinkedList.createFromArray(new int[]{4, 5, 6});
        LinkedList.ListNode merged2 = linkedList.mergeTwoLists(list2, null);
        assertArrayEquals(new int[]{4, 5, 6}, LinkedList.toArray(merged2));
        
        // 测试正常合并
        LinkedList.ListNode listA = LinkedList.createFromArray(new int[]{1, 2, 4});
        LinkedList.ListNode listB = LinkedList.createFromArray(new int[]{1, 3, 4});
        LinkedList.ListNode merged = linkedList.mergeTwoLists(listA, listB);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4}, LinkedList.toArray(merged));
        
        // 测试不同长度的链表
        LinkedList.ListNode shortList = LinkedList.createFromArray(new int[]{5});
        LinkedList.ListNode longList = LinkedList.createFromArray(new int[]{1, 2, 3, 4});
        LinkedList.ListNode merged3 = linkedList.mergeTwoLists(shortList, longList);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(merged3));
    }

    @Test
    @DisplayName("测试删除排序链表中的重复元素")
    void testDeleteDuplicates() {
        // 测试空链表
        assertNull(linkedList.deleteDuplicates(null));
        
        // 测试无重复元素
        LinkedList.ListNode noDup = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.deleteDuplicates(noDup);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result1));
        
        // 测试有重复元素
        LinkedList.ListNode withDup = LinkedList.createFromArray(new int[]{1, 1, 2, 3, 3});
        LinkedList.ListNode result2 = linkedList.deleteDuplicates(withDup);
        assertArrayEquals(new int[]{1, 2, 3}, LinkedList.toArray(result2));
        
        // 测试全部重复
        LinkedList.ListNode allSame = LinkedList.createFromArray(new int[]{1, 1, 1, 1});
        LinkedList.ListNode result3 = linkedList.deleteDuplicates(allSame);
        assertArrayEquals(new int[]{1}, LinkedList.toArray(result3));
        
        // 测试末尾重复
        LinkedList.ListNode endDup = LinkedList.createFromArray(new int[]{1, 2, 3, 3, 3});
        LinkedList.ListNode result4 = linkedList.deleteDuplicates(endDup);
        assertArrayEquals(new int[]{1, 2, 3}, LinkedList.toArray(result4));
    }

    @Test
    @DisplayName("测试删除排序链表中的所有重复元素")
    void testDeleteDuplicates2() {
        // 测试空链表
        assertNull(linkedList.deleteDuplicates2(null));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode result1 = linkedList.deleteDuplicates2(single);
        assertArrayEquals(new int[]{1}, LinkedList.toArray(result1));
        
        // 测试无重复元素
        LinkedList.ListNode noDup = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.deleteDuplicates2(noDup);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试有重复元素
        LinkedList.ListNode withDup = LinkedList.createFromArray(new int[]{1, 2, 3, 3, 4, 4, 5});
        LinkedList.ListNode result3 = linkedList.deleteDuplicates2(withDup);
        assertArrayEquals(new int[]{1, 2, 5}, LinkedList.toArray(result3));
        
        // 测试头部重复
        LinkedList.ListNode headDup = LinkedList.createFromArray(new int[]{1, 1, 2, 3});
        LinkedList.ListNode result4 = linkedList.deleteDuplicates2(headDup);
        assertArrayEquals(new int[]{2, 3}, LinkedList.toArray(result4));
        
        // 测试全部重复
        LinkedList.ListNode allDup = LinkedList.createFromArray(new int[]{1, 1, 2, 2});
        LinkedList.ListNode result5 = linkedList.deleteDuplicates2(allDup);
        assertArrayEquals(new int[]{}, LinkedList.toArray(result5));
    }

    // ==================== 快慢指针算法测试 ====================

    @Test
    @DisplayName("测试检测链表是否有环")
    void testHasCycle() {
        // 测试空链表
        assertFalse(linkedList.hasCycle(null));
        
        // 测试单个节点无环
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertFalse(linkedList.hasCycle(single));
        
        // 测试无环链表
        LinkedList.ListNode noCycle = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        assertFalse(linkedList.hasCycle(noCycle));
        
        // 测试有环链表
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        // 创建环：5 -> 2
        LinkedList.ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = head.next; // 5指向2，形成环
        assertTrue(linkedList.hasCycle(head));
        
        // 测试自环
        LinkedList.ListNode selfLoop = new LinkedList.ListNode(1);
        selfLoop.next = selfLoop;
        assertTrue(linkedList.hasCycle(selfLoop));
    }

    @Test
    @DisplayName("测试寻找链表中间节点")
    void testFindMiddle() {
        // 测试空链表
        assertNull(linkedList.findMiddle(null));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertEquals(1, linkedList.findMiddle(single).val);
        
        // 测试奇数个节点
        LinkedList.ListNode odd = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        assertEquals(3, linkedList.findMiddle(odd).val);
        
        // 测试偶数个节点
        LinkedList.ListNode even = LinkedList.createFromArray(new int[]{1, 2, 3, 4});
        assertEquals(3, linkedList.findMiddle(even).val);
        
        // 测试两个节点
        LinkedList.ListNode two = LinkedList.createFromArray(new int[]{1, 2});
        assertEquals(2, linkedList.findMiddle(two).val);
    }

    @Test
    @DisplayName("测试寻找倒数第k个节点")
    void testFindKthFromEnd() {
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        
        // 测试正常情况
        assertEquals(5, linkedList.findKthFromEnd(head, 1).val);
        assertEquals(4, linkedList.findKthFromEnd(head, 2).val);
        assertEquals(3, linkedList.findKthFromEnd(head, 3).val);
        assertEquals(2, linkedList.findKthFromEnd(head, 4).val);
        assertEquals(1, linkedList.findKthFromEnd(head, 5).val);
        
        // 测试边界情况
        assertNull(linkedList.findKthFromEnd(head, 0));
        assertNull(linkedList.findKthFromEnd(head, 6));
        assertNull(linkedList.findKthFromEnd(null, 1));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        assertEquals(42, linkedList.findKthFromEnd(single, 1).val);
        assertNull(linkedList.findKthFromEnd(single, 2));
    }

    @Test
    @DisplayName("测试删除倒数第k个节点")
    void testRemoveKthFromEnd() {
        // 测试删除中间节点
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.removeKthFromEnd(head1, 2);
        assertArrayEquals(new int[]{1, 2, 3, 5}, LinkedList.toArray(result1));
        
        // 测试删除头节点
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.removeKthFromEnd(head2, 5);
        assertArrayEquals(new int[]{2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试删除尾节点
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.removeKthFromEnd(head3, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4}, LinkedList.toArray(result3));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode result4 = linkedList.removeKthFromEnd(single, 1);
        assertArrayEquals(new int[]{}, LinkedList.toArray(result4));
        
        // 测试边界情况
        LinkedList.ListNode head5 = LinkedList.createFromArray(new int[]{1, 2, 3});
        assertNull(linkedList.removeKthFromEnd(head5, 0));
        assertNull(linkedList.removeKthFromEnd(head5, 4));
        assertNull(linkedList.removeKthFromEnd(null, 1));
    }

    @Test
    @DisplayName("测试删除倒数第k个节点（方法2）")
    void testRemoveKthFromEnd2() {
        // 测试删除中间节点
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.removeKthFromEnd2(head1, 2);
        assertArrayEquals(new int[]{1, 2, 3, 5}, LinkedList.toArray(result1));
        
        // 测试删除头节点
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.removeKthFromEnd2(head2, 5);
        assertArrayEquals(new int[]{2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试删除尾节点
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.removeKthFromEnd2(head3, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4}, LinkedList.toArray(result3));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode result4 = linkedList.removeKthFromEnd2(single, 1);
        assertArrayEquals(new int[]{}, LinkedList.toArray(result4));
    }

    @Test
    @DisplayName("测试求两个链表的交点")
    void testGetIntersectionNode() {
        // 测试无交点
        LinkedList.ListNode listA = LinkedList.createFromArray(new int[]{1, 2, 3});
        LinkedList.ListNode listB = LinkedList.createFromArray(new int[]{4, 5, 6});
        assertNull(linkedList.getIntersectionNode(listA, listB));
        
        // 测试有交点
        LinkedList.ListNode common = LinkedList.createFromArray(new int[]{8, 4, 5});
        LinkedList.ListNode headA = LinkedList.createFromArray(new int[]{4, 1});
        LinkedList.ListNode headB = LinkedList.createFromArray(new int[]{5, 6, 1});
        
        // 连接到公共部分
        LinkedList.ListNode tailA = headA;
        while (tailA.next != null) {
            tailA = tailA.next;
        }
        tailA.next = common;
        
        LinkedList.ListNode tailB = headB;
        while (tailB.next != null) {
            tailB = tailB.next;
        }
        tailB.next = common;
        
        LinkedList.ListNode intersection = linkedList.getIntersectionNode(headA, headB);
        assertNotNull(intersection);
        assertEquals(8, intersection.val);
        
        // 测试边界情况
        assertNull(linkedList.getIntersectionNode(null, listA));
        assertNull(linkedList.getIntersectionNode(listA, null));
        assertNull(linkedList.getIntersectionNode(null, null));
    }

    @Test
    @DisplayName("测试检测环的起始节点")
    void testDetectCycle() {
        // 测试无环链表
        LinkedList.ListNode noCycle = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        assertNull(linkedList.detectCycle(noCycle));
        
        // 测试有环链表
        LinkedList.ListNode head = LinkedList.createFromArray(new int[]{3, 2, 0, -4});
        // 创建环：-4 -> 2 (索引1)
        LinkedList.ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = head.next; // -4指向2，形成环
        
        LinkedList.ListNode cycleStart = linkedList.detectCycle(head);
        assertNotNull(cycleStart);
        assertEquals(2, cycleStart.val);
        
        // 测试自环
        LinkedList.ListNode selfLoop = new LinkedList.ListNode(1);
        selfLoop.next = selfLoop;
        LinkedList.ListNode selfCycleStart = linkedList.detectCycle(selfLoop);
        assertNotNull(selfCycleStart);
        assertEquals(1, selfCycleStart.val);
        
        // 测试边界情况
        assertNull(linkedList.detectCycle(null));
        
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertNull(linkedList.detectCycle(single));
    }

    @Test
    @DisplayName("测试判断回文链表")
    void testIsPalindrome() {
        // 测试空链表
        assertTrue(linkedList.isPalindrome(null));
        
        // 测试单个节点
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertTrue(linkedList.isPalindrome(single));
        
        // 测试回文链表（奇数长度）
        LinkedList.ListNode palindromeOdd = LinkedList.createFromArray(new int[]{1, 2, 3, 2, 1});
        assertTrue(linkedList.isPalindrome(palindromeOdd));
        
        // 测试回文链表（偶数长度）
        LinkedList.ListNode palindromeEven = LinkedList.createFromArray(new int[]{1, 2, 2, 1});
        assertTrue(linkedList.isPalindrome(palindromeEven));
        
        // 测试非回文链表
        LinkedList.ListNode notPalindrome = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        assertFalse(linkedList.isPalindrome(notPalindrome));
        
        // 测试两个节点
        LinkedList.ListNode twoSame = LinkedList.createFromArray(new int[]{1, 1});
        assertTrue(linkedList.isPalindrome(twoSame));
        
        LinkedList.ListNode twoDiff = LinkedList.createFromArray(new int[]{1, 2});
        assertFalse(linkedList.isPalindrome(twoDiff));
    }

    // ==================== 链表翻转算法测试 ====================

    @Test
    @DisplayName("测试两两交换链表节点（递归实现）")
    void testSwapPairsRecursive() {
        // 测试空链表
        assertNull(linkedList.swapPairsRecursive(null));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode result1 = linkedList.swapPairsRecursive(single);
        assertArrayEquals(new int[]{1}, LinkedList.toArray(result1));
        
        // 测试偶数个节点
        LinkedList.ListNode even = LinkedList.createFromArray(new int[]{1, 2, 3, 4});
        LinkedList.ListNode result2 = linkedList.swapPairsRecursive(even);
        assertArrayEquals(new int[]{2, 1, 4, 3}, LinkedList.toArray(result2));
        
        // 测试奇数个节点
        LinkedList.ListNode odd = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.swapPairsRecursive(odd);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, LinkedList.toArray(result3));
        
        // 测试两个节点
        LinkedList.ListNode two = LinkedList.createFromArray(new int[]{1, 2});
        LinkedList.ListNode result4 = linkedList.swapPairsRecursive(two);
        assertArrayEquals(new int[]{2, 1}, LinkedList.toArray(result4));
    }

    @Test
    @DisplayName("测试两两交换链表节点（迭代实现）")
    void testSwapPairsIterative() {
        // 测试空链表
        assertNull(linkedList.swapPairsIterative(null));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode result1 = linkedList.swapPairsIterative(single);
        assertArrayEquals(new int[]{1}, LinkedList.toArray(result1));
        
        // 测试偶数个节点
        LinkedList.ListNode even = LinkedList.createFromArray(new int[]{1, 2, 3, 4});
        LinkedList.ListNode result2 = linkedList.swapPairsIterative(even);
        assertArrayEquals(new int[]{2, 1, 4, 3}, LinkedList.toArray(result2));
        
        // 测试奇数个节点
        LinkedList.ListNode odd = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.swapPairsIterative(odd);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, LinkedList.toArray(result3));
        
        // 测试两个节点
        LinkedList.ListNode two = LinkedList.createFromArray(new int[]{1, 2});
        LinkedList.ListNode result4 = linkedList.swapPairsIterative(two);
        assertArrayEquals(new int[]{2, 1}, LinkedList.toArray(result4));
    }

    @Test
    @DisplayName("测试K个一组翻转链表")
    void testReverseKGroup() {
        // 测试空链表
        assertNull(linkedList.reverseKGroup(null, 2));
        
        // 测试k=1（不翻转）
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.reverseKGroup(head1, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result1));
        
        // 测试k=2
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.reverseKGroup(head2, 2);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, LinkedList.toArray(result2));
        
        // 测试k=3
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.reverseKGroup(head3, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result3));
        
        // 测试k等于链表长度
        LinkedList.ListNode head4 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result4 = linkedList.reverseKGroup(head4, 5);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, LinkedList.toArray(result4));
        
        // 测试k大于链表长度
        LinkedList.ListNode head5 = LinkedList.createFromArray(new int[]{1, 2, 3});
        LinkedList.ListNode result5 = linkedList.reverseKGroup(head5, 5);
        assertArrayEquals(new int[]{1, 2, 3}, LinkedList.toArray(result5));
    }

    @Test
    @DisplayName("测试通用分组翻转（支持不完整分组）")
    void testReverseKGroupWithIncomplete() {
        // 测试翻转不完整分组
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.reverseKGroup(head1, 3, true);
        assertArrayEquals(new int[]{3, 2, 1, 5, 4}, LinkedList.toArray(result1));
        
        // 测试不翻转不完整分组
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.reverseKGroup(head2, 3, false);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result2));
        
        // 测试完整分组
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5, 6});
        LinkedList.ListNode result3 = linkedList.reverseKGroup(head3, 3, true);
        assertArrayEquals(new int[]{3, 2, 1, 6, 5, 4}, LinkedList.toArray(result3));
    }

    @Test
    @DisplayName("测试向右旋转链表")
    void testRotateRight() {
        // 测试空链表
        assertNull(linkedList.rotateRight(null, 2));
        
        // 测试k=0
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.rotateRight(head1, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result1));
        
        // 测试正常旋转
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.rotateRight(head2, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, LinkedList.toArray(result2));
        
        // 测试k等于链表长度
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.rotateRight(head3, 5);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result3));
        
        // 测试k大于链表长度
        LinkedList.ListNode head4 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result4 = linkedList.rotateRight(head4, 7);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, LinkedList.toArray(result4));
        
        // 测试单个节点
        LinkedList.ListNode single = LinkedList.createFromArray(new int[]{1});
        LinkedList.ListNode resultSingle = linkedList.rotateRight(single, 3);
        assertArrayEquals(new int[]{1}, LinkedList.toArray(resultSingle));
    }

    // ==================== Reverser内部类测试 ====================

    @Test
    @DisplayName("测试Reverser类的递归翻转前N个节点")
    void testReverserRecursive() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 测试翻转前3个节点
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = reverser.reverse(head1, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result1));
        
        // 测试翻转前1个节点
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = reverser.reverse(head2, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试翻转全部节点
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = reverser.reverse(head3, 5);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, LinkedList.toArray(result3));
    }

    @Test
    @DisplayName("测试Reverser类的迭代翻转前N个节点")
    void testReverserIterative() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 测试翻转前3个节点
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = reverser.reverse2(head1, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result1));
        
        // 测试翻转前1个节点
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = reverser.reverse2(head2, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试n=0
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = reverser.reverse2(head3, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result3));
        
        // 测试空链表
        assertNull(reverser.reverse2(null, 3));
    }

    @Test
    @DisplayName("测试Reverser类的头插法翻转前N个节点")
    void testReverserHeadInsert() {
        LinkedList.Reverser reverser = linkedList.new Reverser();
        
        // 测试翻转前3个节点
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = reverser.reverse3(head1, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result1));
        
        // 测试翻转前1个节点
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = reverser.reverse3(head2, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result2));
        
        // 测试n=0
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = reverser.reverse3(head3, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result3));
        
        // 测试空链表
        assertNull(reverser.reverse3(null, 3));
    }

    // ==================== 其他翻转方法测试 ====================

    @Test
    @DisplayName("测试K个一组翻转链表（头插法实现）")
    void testReverseKGroupWithHeadInsert() {
        // 测试空链表
        assertNull(linkedList.reverseKGroupWithHeadInsert(null, 2));
        
        // 测试k=1
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.reverseKGroupWithHeadInsert(head1, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result1));
        
        // 测试k=2
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.reverseKGroupWithHeadInsert(head2, 2);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, LinkedList.toArray(result2));
        
        // 测试k=3
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.reverseKGroupWithHeadInsert(head3, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result3));
    }

    @Test
    @DisplayName("测试K个一组翻转链表（双重递归实现）")
    void testReverseKGroupWithDoubleRecursion() {
        // 测试空链表
        assertNull(linkedList.reverseKGroupWithDoubleRecursion(null, 2));
        
        // 测试k=1
        LinkedList.ListNode head1 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result1 = linkedList.reverseKGroupWithDoubleRecursion(head1, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, LinkedList.toArray(result1));
        
        // 测试k=2
        LinkedList.ListNode head2 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result2 = linkedList.reverseKGroupWithDoubleRecursion(head2, 2);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, LinkedList.toArray(result2));
        
        // 测试k=3
        LinkedList.ListNode head3 = LinkedList.createFromArray(new int[]{1, 2, 3, 4, 5});
        LinkedList.ListNode result3 = linkedList.reverseKGroupWithDoubleRecursion(head3, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, LinkedList.toArray(result3));
    }

    // ==================== 边界条件和性能测试 ====================

    @Test
    @DisplayName("测试大链表性能")
    void testLargeLinkedList() {
        // 创建大链表（1000个节点）
        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = i + 1;
        }
        
        LinkedList.ListNode largeHead = LinkedList.createFromArray(largeArray);
        
        // 测试基本操作
        assertEquals(1000, LinkedList.getLength(largeHead));
        assertEquals(500, linkedList.findMiddle(largeHead).val);
        assertEquals(1000, linkedList.findKthFromEnd(largeHead, 1).val);
        
        // 测试翻转
        LinkedList.ListNode reversed = linkedList.reverse(largeHead);
        assertEquals(1000, reversed.val);
        assertEquals(1, linkedList.findKthFromEnd(reversed, 1).val);
    }

    @Test
    @DisplayName("测试链表节点的toString方法")
    void testListNodeToString() {
        LinkedList.ListNode node = new LinkedList.ListNode(42);
        assertEquals("ListNode{val=42}", node.toString());
        
        LinkedList.ListNode nodeWithNext = new LinkedList.ListNode(1, new LinkedList.ListNode(2));
        assertEquals("ListNode{val=1}", nodeWithNext.toString());
    }

    @Test
    @DisplayName("测试所有构造函数")
    void testListNodeConstructors() {
        // 默认构造函数
        LinkedList.ListNode defaultNode = new LinkedList.ListNode();
        assertEquals(0, defaultNode.val);
        assertNull(defaultNode.next);
        
        // 值构造函数
        LinkedList.ListNode valueNode = new LinkedList.ListNode(42);
        assertEquals(42, valueNode.val);
        assertNull(valueNode.next);
        
        // 完整构造函数
        LinkedList.ListNode nextNode = new LinkedList.ListNode(2);
        LinkedList.ListNode fullNode = new LinkedList.ListNode(1, nextNode);
        assertEquals(1, fullNode.val);
        assertEquals(nextNode, fullNode.next);
    }
}