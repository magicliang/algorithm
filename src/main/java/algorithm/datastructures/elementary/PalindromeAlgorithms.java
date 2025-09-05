package algorithm.datastructures.elementary;

/**
 * project name: algorithm
 *
 * description: 基于栈实现的回文相关算法集合
 * 包含回文数、回文字符串、回文链表的检测算法
 *
 * @author magicliang
 * date: 2025-09-05 14:44
 */
public class PalindromeAlgorithms {

    /**
     * 使用栈检测回文数
     * 思路：将数字的每一位压入栈中，然后依次弹出与原数字比较
     *
     * @param num 待检测的数字
     * @return 如果是回文数返回true，否则返回false
     */
    public static boolean isPalindromeNumber(int num) {
        // 负数不是回文数
        if (num < 0) {
            return false;
        }
        
        // 单位数都是回文数
        if (num < 10) {
            return true;
        }
        
        Stack<Integer> stack = new ArrayStack<>();
        int originalNum = num;
        
        // 将每一位数字压入栈中
        while (num > 0) {
            stack.push(num % 10);
            num /= 10;
        }
        
        // 重新构造数字并与原数字比较
        int reconstructedNum = 0;
        int multiplier = 1;
        
        while (!stack.isEmpty()) {
            reconstructedNum += stack.pop() * multiplier;
            multiplier *= 10;
        }
        
        return originalNum == reconstructedNum;
    }

    /**
     * 使用栈检测回文字符串
     * 思路：将字符串的前半部分压入栈中，然后与后半部分逐个比较
     *
     * @param str 待检测的字符串
     * @return 如果是回文字符串返回true，否则返回false
     */
    public static boolean isPalindromeString(String str) {
        if (str == null || str.length() <= 1) {
            return true;
        }
        
        // 转换为小写并去除非字母数字字符
        String cleanStr = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        if (cleanStr.length() <= 1) {
            return true;
        }
        
        Stack<Character> stack = new LinkedStack<>();
        int length = cleanStr.length();
        int mid = length / 2;
        
        // 将前半部分字符压入栈中
        for (int i = 0; i < mid; i++) {
            stack.push(cleanStr.charAt(i));
        }
        
        // 从中间位置开始比较（奇数长度时跳过中间字符）
        int startIndex = length % 2 == 0 ? mid : mid + 1;
        
        for (int i = startIndex; i < length; i++) {
            if (stack.isEmpty() || !stack.pop().equals(cleanStr.charAt(i))) {
                return false;
            }
        }
        
        return stack.isEmpty();
    }

    /**
     * 使用栈检测回文链表
     * 思路：遍历链表将所有值压入栈中，然后再次遍历链表与栈中弹出的值比较
     *
     * @param head 链表头节点
     * @return 如果是回文链表返回true，否则返回false
     */
    public static boolean isPalindromeLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        Stack<Integer> stack = new ArrayStack<>();
        ListNode current = head;
        
        // 第一次遍历：将所有值压入栈中
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }
        
        // 第二次遍历：与栈中弹出的值比较
        current = head;
        while (current != null && !stack.isEmpty()) {
            if (!current.val.equals(stack.pop())) {
                return false;
            }
            current = current.next;
        }
        
        return stack.isEmpty() && current == null;
    }

    /**
     * 优化版本：使用快慢指针找到链表中点，只需要一半的栈空间
     * 思路：快慢指针找中点，将后半部分压入栈，然后与前半部分比较
     *
     * @param head 链表头节点
     * @return 如果是回文链表返回true，否则返回false
     */
    public static boolean isPalindromeLinkedListOptimized(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // 使用快慢指针找到链表中点
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 将后半部分压入栈
        Stack<Integer> stack = new LinkedStack<>();
        while (slow != null) {
            stack.push(slow.val);
            slow = slow.next;
        }
        
        // 与前半部分比较
        ListNode current = head;
        while (!stack.isEmpty()) {
            if (!current.val.equals(stack.pop())) {
                return false;
            }
            current = current.next;
        }
        
        return true;
    }

    /**
     * 使用栈将字符串转换为回文字符串
     * 思路：将字符串压入栈，然后弹出拼接到原字符串后面
     *
     * @param str 原字符串
     * @return 转换后的回文字符串
     */
    public static String makePalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        Stack<Character> stack = new ArrayStack<>();
        
        // 将字符串的每个字符压入栈中
        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        
        StringBuilder result = new StringBuilder(str);
        
        // 弹出栈中字符拼接到原字符串后面
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.toString();
    }

    /**
     * 使用栈检查括号是否平衡（经典栈应用）
     * 虽然不是回文问题，但是栈的经典应用之一
     *
     * @param str 包含括号的字符串
     * @return 如果括号平衡返回true，否则返回false
     */
    public static boolean isBalancedParentheses(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        
        Stack<Character> stack = new LinkedStack<>();
        
        for (char c : str.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') {
                        return false;
                    }
                    break;
                default:
                    // 忽略其他字符
                    break;
            }
        }
        
        return stack.isEmpty();
    }

    /**
     * 简单的链表节点定义
     */
    public static class ListNode {
        Integer val;
        ListNode next;
        
        ListNode() {}
        
        ListNode(Integer val) {
            this.val = val;
        }
        
        ListNode(Integer val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        
        /**
         * 便于测试的工具方法：从数组创建链表
         */
        public static ListNode fromArray(Integer[] arr) {
            if (arr == null || arr.length == 0) {
                return null;
            }
            
            ListNode head = new ListNode(arr[0]);
            ListNode current = head;
            
            for (int i = 1; i < arr.length; i++) {
                current.next = new ListNode(arr[i]);
                current = current.next;
            }
            
            return head;
        }
        
        /**
         * 便于测试的工具方法：将链表转换为数组
         */
        public Integer[] toArray() {
            java.util.List<Integer> list = new java.util.ArrayList<>();
            ListNode current = this;
            
            while (current != null) {
                list.add(current.val);
                current = current.next;
            }
            
            return list.toArray(new Integer[0]);
        }
    }
}