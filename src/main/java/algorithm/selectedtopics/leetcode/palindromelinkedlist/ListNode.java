package algorithm.selectedtopics.leetcode.palindromelinkedlist;

/**
 * 链表节点定义
 * 用于回文链表问题的各种解法
 */
public class ListNode {
    public int val;
    public ListNode next;
    
    public ListNode() {}
    
    public ListNode(int val) {
        this.val = val;
    }
    
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    
    /**
     * 从数组创建链表
     * @param values 数组值
     * @return 链表头节点
     */
    public static ListNode fromArray(int[] values) {
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
    
    /**
     * 将链表转换为数组
     * @param head 链表头节点
     * @return 数组
     */
    public static int[] toArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        
        // 先计算长度
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // 填充数组
        int[] result = new int[length];
        current = head;
        for (int i = 0; i < length; i++) {
            result[i] = current.val;
            current = current.next;
        }
        
        return result;
    }
    
    /**
     * 打印链表
     * @param head 链表头节点
     * @return 字符串表示
     */
    public static String toString(ListNode head) {
        if (head == null) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 复制链表
     * @param head 原链表头节点
     * @return 新链表头节点
     */
    public static ListNode copy(ListNode head) {
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
     * 获取链表长度
     * @param head 链表头节点
     * @return 长度
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
}