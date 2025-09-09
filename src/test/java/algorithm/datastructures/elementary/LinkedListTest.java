package algorithm.datastructures.elementary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinkedList类的单元测试
 * <p>
 * 测试覆盖范围：
 * <ul>
 * <li>基础链表操作：创建、转换、长度计算、打印</li>
 * <li>快慢指针算法：环检测、中点查找、倒数第k个节点</li>
 * <li>链表翻转算法：整体翻转、前N个翻转、两两交换、K组翻转</li>
 * <li>链表处理算法：去重、合并、回文检测、旋转</li>
 * <li>边界情况：空链表、单节点、特殊参数值</li>
 * </ul>
 *
 * @author magicliang
 * @date 2025-09-09
 */
public class LinkedListTest {

    private LinkedList linkedList;

    @BeforeEach
    void setUp() {
        linkedList = new LinkedList();
    }

    // ==================== 基础工具方法测试 ====================

    @Test
    @DisplayName("测试从数组创建链表")
    void testCreateFromArray() {
        // 测试正常情况
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        
        assertNotNull(head);
        assertEquals(1, head.val);
        assertEquals(2, head.next.val);
        assertEquals(5, getLastNodeValue(head));
        
        // 测试空数组
        assertNull(LinkedList.createFromArray(new int[0]));
        assertNull(LinkedList.createFromArray(null));
    }

    @Test
    @DisplayName("测试链表转数组")
    void testToArray() {
        int[] original = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(original);
        int[] result = LinkedList.toArray(head);
        
        assertArrayEquals(original, result);
        
        // 测试空链表
        assertArrayEquals(new int[0], LinkedList.toArray(null));
    }

    @Test
    @DisplayName("测试计算链表长度")
    void testGetLength() {
        assertEquals(0, LinkedList.getLength(null));
        
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        assertEquals(5, LinkedList.getLength(head));
    }

    // ==================== 链表翻转算法测试 ====================

    @Test
    @DisplayName("测试两两交换节点 - 递归实现")
    void testSwapPairsRecursive() {
        // 测试偶数长度链表：1->2->3->4 变为 2->1->4->3
        int[] input = {1, 2, 3, 4};
        int[] expected = {2, 1, 4, 3};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.swapPairsRecursive(head);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试奇数长度链表：1->2->3->4->5 变为 2->1->4->3->5
        int[] input2 = {1, 2, 3, 4, 5};
        int[] expected2 = {2, 1, 4, 3, 5};
        LinkedList.ListNode head2 = LinkedList.createFromArray(input2);
        LinkedList.ListNode result2 = linkedList.swapPairsRecursive(head2);
        assertArrayEquals(expected2, LinkedList.toArray(result2));
        
        // 测试边界情况
        assertNull(linkedList.swapPairsRecursive(null));
        
        LinkedList.ListNode single = new LinkedList.ListNode(1);
        assertEquals(1, linkedList.swapPairsRecursive(single).val);
    }

    @Test
    @DisplayName("测试两两交换节点 - 迭代实现")
    void testSwapPairsIterative() {
        // 测试偶数长度链表
        int[] input = {1, 2, 3, 4};
        int[] expected = {2, 1, 4, 3};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.swapPairsIterative(head);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试奇数长度链表
        int[] input2 = {1, 2, 3, 4, 5};
        int[] expected2 = {2, 1, 4, 3, 5};
        LinkedList.ListNode head2 = LinkedList.createFromArray(input2);
        LinkedList.ListNode result2 = linkedList.swapPairsIterative(head2);
        assertArrayEquals(expected2, LinkedList.toArray(result2));
        
        // 测试边界情况
        assertNull(linkedList.swapPairsIterative(null));
    }

    @Test
    @DisplayName("测试K个一组翻转链表")
    void testReverseKGroup() {
        // 测试k=3的情况：1->2->3->4->5->6->7->8 变为 3->2->1->6->5->4->7->8
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] expected = {3, 2, 1, 6, 5, 4, 7, 8};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.reverseKGroup(head, 3);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试k=4的情况：1->2->3->4->5->6->7->8 变为 4->3->2->1->8->7->6->5
        int[] expected2 = {4, 3, 2, 1, 8, 7, 6, 5};
        LinkedList.ListNode head2 = LinkedList.createFromArray(input);
        LinkedList.ListNode result2 = linkedList.reverseKGroup(head2, 4);
        assertArrayEquals(expected2, LinkedList.toArray(result2));
        
        // 测试k=1的情况（不翻转）
        LinkedList.ListNode head3 = LinkedList.createFromArray(input);
        LinkedList.ListNode result3 = linkedList.reverseKGroup(head3, 1);
        assertArrayEquals(input, LinkedList.toArray(result3));
        
        // 测试k大于链表长度的情况
        int[] shortInput = {1, 2, 3};
        LinkedList.ListNode head4 = LinkedList.createFromArray(shortInput);
        LinkedList.ListNode result4 = linkedList.reverseKGroup(head4, 5);
        assertArrayEquals(shortInput, LinkedList.toArray(result4));
    }

    @Test
    @DisplayName("测试通用分组翻转（包含不完整分组）")
    void testReverseKGroupWithIncomplete() {
        // 测试翻转不完整分组：1->2->3->4->5->6->7->8 (k=3) 变为 3->2->1->6->5->4->8->7
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] expected = {3, 2, 1, 6, 5, 4, 8, 7};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.reverseKGroup(head, 3, true);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试不翻转不完整分组（默认行为）
        int[] expected2 = {3, 2, 1, 6, 5, 4, 7, 8};
        LinkedList.ListNode head2 = LinkedList.createFromArray(input);
        LinkedList.ListNode result2 = linkedList.reverseKGroup(head2, 3, false);
        assertArrayEquals(expected2, LinkedList.toArray(result2));
    }

    @Test
    @DisplayName("测试整体链表翻转")
    void testReverse() {
        // 测试正常情况
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {5, 4, 3, 2, 1};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.reverse(head);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试边界情况
        assertNull(linkedList.reverse(null));
        
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        assertEquals(42, linkedList.reverse(single).val);
    }

    // ==================== 快慢指针算法测试 ====================

    @Test
    @DisplayName("测试环检测")
    void testHasCycle() {
        // 测试无环链表
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        assertFalse(linkedList.hasCycle(head));
        
        // 测试有环链表
        LinkedList.ListNode cycleHead = LinkedList.createFromArray(values);
        LinkedList.ListNode tail = getLastNode(cycleHead);
        tail.next = cycleHead.next; // 创建环：尾节点指向第二个节点
        assertTrue(linkedList.hasCycle(cycleHead));
        
        // 测试边界情况
        assertFalse(linkedList.hasCycle(null));
        assertFalse(linkedList.hasCycle(new LinkedList.ListNode(1)));
    }

    @Test
    @DisplayName("测试查找中间节点")
    void testFindMiddle() {
        // 测试奇数长度链表：1->2->3->4->5，中间节点是3
        int[] oddValues = {1, 2, 3, 4, 5};
        LinkedList.ListNode oddHead = LinkedList.createFromArray(oddValues);
        assertEquals(3, linkedList.findMiddle(oddHead).val);
        
        // 测试偶数长度链表：1->2->3->4，中间节点是3（第二个中间节点）
        int[] evenValues = {1, 2, 3, 4};
        LinkedList.ListNode evenHead = LinkedList.createFromArray(evenValues);
        assertEquals(3, linkedList.findMiddle(evenHead).val);
        
        // 测试边界情况
        assertNull(linkedList.findMiddle(null));
        
        LinkedList.ListNode single = new LinkedList.ListNode(42);
        assertEquals(42, linkedList.findMiddle(single).val);
    }

    @Test
    @DisplayName("测试查找倒数第k个节点")
    void testFindKthFromEnd() {
        int[] values = {1, 2, 3, 4, 5};
        LinkedList.ListNode head = LinkedList.createFromArray(values);
        
        // 测试正常情况
        assertEquals(5, linkedList.findKthFromEnd(head, 1).val); // 倒数第1个
        assertEquals(4, linkedList.findKthFromEnd(head, 2).val); // 倒数第2个
        assertEquals(1, linkedList.findKthFromEnd(head, 5).val); // 倒数第5个
        
        // 测试边界情况
        assertNull(linkedList.findKthFromEnd(head, 6)); // k超出链表长度
        assertNull(linkedList.findKthFromEnd(head, 0));  // k为0
        assertNull(linkedList.findKthFromEnd(null, 1));  // 空链表
    }

    @Test
    @DisplayName("测试删除倒数第k个节点")
    void testRemoveKthFromEnd() {
        // 测试删除中间节点
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 4, 5}; // 删除倒数第2个节点（4）
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.removeKthFromEnd(head, 2);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试删除头节点
        int[] expected2 = {2, 3, 4, 5}; // 删除倒数第5个节点（1）
        LinkedList.ListNode head2 = LinkedList.createFromArray(input);
        LinkedList.ListNode result2 = linkedList.removeKthFromEnd(head2, 5);
        assertArrayEquals(expected2, LinkedList.toArray(result2));
        
        // 测试删除尾节点
        int[] expected3 = {1, 2, 3, 4}; // 删除倒数第1个节点（5）
        LinkedList.ListNode head3 = LinkedList.createFromArray(input);
        LinkedList.ListNode result3 = linkedList.removeKthFromEnd(head3, 1);
        assertArrayEquals(expected3, LinkedList.toArray(result3));
    }

    // ==================== 链表处理算法测试 ====================

    @Test
    @DisplayName("测试合并两个有序链表")
    void testMergeTwoLists() {
        int[] list1Values = {1, 2, 4};
        int[] list2Values = {1, 3, 4};
        int[] expected = {1, 1, 2, 3, 4, 4};
        
        LinkedList.ListNode list1 = LinkedList.createFromArray(list1Values);
        LinkedList.ListNode list2 = LinkedList.createFromArray(list2Values);
        LinkedList.ListNode result = linkedList.mergeTwoLists(list1, list2);
        
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试一个链表为空的情况
        LinkedList.ListNode list3 = LinkedList.createFromArray(list1Values);
        LinkedList.ListNode result2 = linkedList.mergeTwoLists(list3, null);
        assertArrayEquals(list1Values, LinkedList.toArray(result2));
    }

    @Test
    @DisplayName("测试回文链表检测")
    void testIsPalindrome() {
        // 测试回文链表
        int[] palindrome1 = {1, 2, 2, 1};
        LinkedList.ListNode head1 = LinkedList.createFromArray(palindrome1);
        assertTrue(linkedList.isPalindrome(head1));
        
        int[] palindrome2 = {1, 2, 3, 2, 1};
        LinkedList.ListNode head2 = LinkedList.createFromArray(palindrome2);
        assertTrue(linkedList.isPalindrome(head2));
        
        // 测试非回文链表
        int[] nonPalindrome = {1, 2, 3, 4, 5};
        LinkedList.ListNode head3 = LinkedList.createFromArray(nonPalindrome);
        assertFalse(linkedList.isPalindrome(head3));
        
        // 测试边界情况
        assertTrue(linkedList.isPalindrome(null));
        assertTrue(linkedList.isPalindrome(new LinkedList.ListNode(1)));
    }

    @Test
    @DisplayName("测试删除重复元素")
    void testDeleteDuplicates() {
        // 测试有重复元素的链表
        int[] input = {1, 1, 2, 3, 3};
        int[] expected = {1, 2, 3};
        LinkedList.ListNode head = LinkedList.createFromArray(input);
        LinkedList.ListNode result = linkedList.deleteDuplicates(head);
        assertArrayEquals(expected, LinkedList.toArray(result));
        
        // 测试无重复元素的链表
        int[] input2 = {1, 2, 3, 4, 5};
        LinkedList.ListNode head2 = LinkedList.createFromArray(input2);
        LinkedList.ListNode result2 = linkedList.deleteDuplicates(head2);
        assertArrayEquals(input2, LinkedList.toArray(result2));
        
        // 测试边界情况
        assertNull(linkedList.deleteDuplicates(null));
    }

    // ==================== 综合测试方法 ====================

    @Test
    @DisplayName("综合测试：链表翻转算法对比")
    void testComprehensiveReverseAlgorithms() {
        System.out.println("=== 链表翻转算法综合测试 ===");
        
        // 测试数据：1->2->3->4->5->6->7->8
        int[] testData = {1, 2, 3, 4, 5, 6, 7, 8};
        
        System.out.println("原始链表: " + LinkedList.printList(LinkedList.createFromArray(testData)));
        
        // 测试两两交换 - 递归实现
        LinkedList.ListNode swappedRecursive = linkedList.swapPairsRecursive(LinkedList.createFromArray(testData));
        System.out.println("两两交换(递归): " + LinkedList.printList(swappedRecursive));
        int[] expectedSwap = {2, 1, 4, 3, 6, 5, 8, 7};
        assertArrayEquals(expectedSwap, LinkedList.toArray(swappedRecursive));
        
        // 测试两两交换 - 迭代实现  
        LinkedList.ListNode swappedIterative = linkedList.swapPairsIterative(LinkedList.createFromArray(testData));
        System.out.println("两两交换(迭代): " + LinkedList.printList(swappedIterative));
        assertArrayEquals(expectedSwap, LinkedList.toArray(swappedIterative));
        
        // 测试K组翻转 (k=3)
        LinkedList.ListNode reversed3Group = linkedList.reverseKGroup(LinkedList.createFromArray(testData), 3);
        System.out.println("3个一组翻转: " + LinkedList.printList(reversed3Group));
        int[] expected3Group = {3, 2, 1, 6, 5, 4, 7, 8};
        assertArrayEquals(expected3Group, LinkedList.toArray(reversed3Group));
        
        // 测试K组翻转 (k=4)
        LinkedList.ListNode reversed4Group = linkedList.reverseKGroup(LinkedList.createFromArray(testData), 4);
        System.out.println("4个一组翻转: " + LinkedList.printList(reversed4Group));
        int[] expected4Group = {4, 3, 2, 1, 8, 7, 6, 5};
        assertArrayEquals(expected4Group, LinkedList.toArray(reversed4Group));
        
        // 测试通用分组翻转（翻转不完整分组）
        LinkedList.ListNode reversedWithIncomplete = linkedList.reverseKGroup(LinkedList.createFromArray(testData), 3, true);
        System.out.println("3个一组翻转(含不完整组): " + LinkedList.printList(reversedWithIncomplete));
        int[] expectedWithIncomplete = {3, 2, 1, 6, 5, 4, 8, 7};
        assertArrayEquals(expectedWithIncomplete, LinkedList.toArray(reversedWithIncomplete));
        
        System.out.println("=== 综合测试完成 ===");
    }

    // ==================== 辅助方法 ====================

    /**
     * 获取链表的最后一个节点
     */
    private LinkedList.ListNode getLastNode(LinkedList.ListNode head) {
        if (head == null) return null;
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }

    /**
     * 获取链表最后一个节点的值
     */
    private int getLastNodeValue(LinkedList.ListNode head) {
        LinkedList.ListNode last = getLastNode(head);
        return last != null ? last.val : -1;
    }
}