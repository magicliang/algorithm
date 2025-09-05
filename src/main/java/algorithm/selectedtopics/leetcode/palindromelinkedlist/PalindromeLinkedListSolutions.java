package algorithm.selectedtopics.leetcode.palindromelinkedlist;

import java.util.Stack;

/**
 * 回文链表问题的5种解法
 * 
 * LeetCode 234. 回文链表
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。
 * 如果是，返回 true ；否则，返回 false 。
 * 
 * 进阶：你能否用 O(1) 空间复杂度解决此问题？
 */
public class PalindromeLinkedListSolutions {
    
    /**
     * 解法1：先反转整条链表，再比对
     * 
     * 思路：把原链表复制一份并整体反转，然后同步遍历一次。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) - 复制新链表
     * 特点：最容易想到，但额外占用 n 个节点
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByReverseWhole(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // 复制原链表
        ListNode copyHead = copyList(head);
        
        // 反转复制的链表
        ListNode reversedHead = reverseList(copyHead);
        
        // 同步遍历比较
        ListNode original = head;
        ListNode reversed = reversedHead;
        
        while (original != null && reversed != null) {
            if (original.val != reversed.val) {
                return false;
            }
            original = original.next;
            reversed = reversed.next;
        }
        
        return true;
    }
    
    /**
     * 解法2：用栈
     * 
     * 思路：第一次遍历把所有节点压栈；第二次遍历同时弹栈比对。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) - 栈大小 = 节点数
     * 特点：代码最短，符合"后进先出"直觉
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByStack(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        Stack<Integer> stack = new Stack<>();
        
        // 第一次遍历：将所有值压入栈
        ListNode current = head;
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }
        
        // 第二次遍历：弹栈比对
        current = head;
        while (current != null) {
            if (current.val != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        
        return true;
    }
    
    /**
     * 解法3：递归（隐含长度已知）- 双返回值
     * 
     * 思路：利用"长度"做递归基准，每次把左端指针与递归返回的右端指针相向比对；
     *      递归返回 boolean + 新的右端指针（双返回值）。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) - 递归栈深度
     * 特点：最贴近提示 4 的表述，体现"返回多个值"技巧
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByRecursion(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        int length = getLength(head);
        RecursionResult result = checkPalindromeRecursive(head, length);
        return result.isPalindrome;
    }
    
    /**
     * 递归检查回文的内部方法
     * @param head 当前节点
     * @param length 剩余长度
     * @return 递归结果（包含是否回文和右端指针）
     */
    private static RecursionResult checkPalindromeRecursive(ListNode head, int length) {
        // 基础情况：长度为0或1
        if (length == 0) {
            return new RecursionResult(true, head);
        }
        if (length == 1) {
            return new RecursionResult(true, head.next);
        }
        
        // 递归处理中间部分
        RecursionResult subResult = checkPalindromeRecursive(head.next, length - 2);
        
        if (!subResult.isPalindrome) {
            return subResult;
        }
        
        // 比较当前左端和递归返回的右端
        boolean currentMatch = (head.val == subResult.rightNode.val);
        
        return new RecursionResult(currentMatch, subResult.rightNode.next);
    }
    
    /**
     * 递归结果封装类（双返回值）
     */
    private static class RecursionResult {
        boolean isPalindrome;
        ListNode rightNode;
        
        RecursionResult(boolean isPalindrome, ListNode rightNode) {
            this.isPalindrome = isPalindrome;
            this.rightNode = rightNode;
        }
    }
    
    /**
     * 解法4：递归（封装比对器类）
     * 
     * 思路：把上一种递归里的"boolean 结果 + 右端指针"封装成一个小类，消除多返回值歧义。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) - 递归栈深度
     * 特点：代码可读性更好，也是 LeetCode 官方递归解法常用写法
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByComparator(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        PalindromeComparator comparator = new PalindromeComparator();
        comparator.leftNode = head;
        return comparator.checkPalindrome(head);
    }
    
    /**
     * 回文比对器类
     */
    private static class PalindromeComparator {
        ListNode leftNode;
        
        boolean checkPalindrome(ListNode rightNode) {
            if (rightNode == null) {
                return true;
            }
            
            // 递归到最右端
            boolean isSubPalindrome = checkPalindrome(rightNode.next);
            
            if (!isSubPalindrome) {
                return false;
            }
            
            // 比较左右端点
            boolean currentMatch = (leftNode.val == rightNode.val);
            
            // 左指针向右移动
            leftNode = leftNode.next;
            
            return currentMatch;
        }
    }
    
    /**
     * 解法5：快慢指针 + 原地半链反转（O(1) 空间）
     * 
     * 思路：
     * ① 快慢指针找中点；
     * ② 反转后半段；
     * ③ 双指针同步比对；
     * ④ （可选）把链表还原。
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(1) - 只动指针
     * 特点：满足题目"进阶"要求，面试最常考
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByTwoPointers(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // 步骤1：快慢指针找中点
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 步骤2：反转后半段
        ListNode secondHalf = reverseList(slow.next);
        
        // 步骤3：双指针同步比对
        ListNode firstHalf = head;
        ListNode reversedSecondHalf = secondHalf;
        boolean isPalindrome = true;
        
        while (reversedSecondHalf != null) {
            if (firstHalf.val != reversedSecondHalf.val) {
                isPalindrome = false;
                break;
            }
            firstHalf = firstHalf.next;
            reversedSecondHalf = reversedSecondHalf.next;
        }
        
        // 步骤4：（可选）还原链表
        slow.next = reverseList(secondHalf);
        
        return isPalindrome;
    }
    
    /**
     * 解法5的变种：不还原链表，更简洁
     * 
     * @param head 链表头节点
     * @return 是否为回文链表
     */
    public static boolean isPalindromeByTwoPointersSimple(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // 快慢指针找中点
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 反转后半段
        ListNode secondHalf = reverseList(slow.next);
        
        // 双指针比对
        ListNode firstHalf = head;
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        
        return true;
    }
    
    // ==================== 辅助方法 ====================
    
    /**
     * 复制链表
     * @param head 原链表头节点
     * @return 新链表头节点
     */
    private static ListNode copyList(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode newHead = new ListNode(head.val);
        ListNode current = newHead;
        ListNode original = head.next;
        
        while (original != null) {
            current.next = new ListNode(original.val);
            current = current.next;
            original = original.next;
        }
        
        return newHead;
    }
    
    /**
     * 反转链表
     * @param head 原链表头节点
     * @return 反转后的链表头节点
     */
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        return prev;
    }
    
    /**
     * 获取链表长度
     * @param head 链表头节点
     * @return 长度
     */
    private static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
}