package algorithm.datastructures.elementary;

/**
 * project name: algorithm
 *
 * description: 回文算法的测试类
 * 测试基于栈实现的各种回文检测算法
 *
 * @author magicliang
 * date: 2025-09-05 14:44
 */
public class PalindromeAlgorithmsTest {

    public static void main(String[] args) {
        System.out.println("=== 回文算法测试 ===\n");
        
        testPalindromeNumber();
        testPalindromeString();
        testPalindromeLinkedList();
        testMakePalindrome();
        testBalancedParentheses();
        
        System.out.println("所有测试完成！");
    }

    /**
     * 测试回文数检测
     */
    private static void testPalindromeNumber() {
        System.out.println("1. 回文数检测测试：");
        
        int[] testNumbers = {121, 12321, 123, -121, 0, 7, 1001, 12345};
        
        for (int num : testNumbers) {
            boolean result = PalindromeAlgorithms.isPalindromeNumber(num);
            System.out.printf("  %d -> %s\n", num, result ? "是回文数" : "不是回文数");
        }
        System.out.println();
    }

    /**
     * 测试回文字符串检测
     */
    private static void testPalindromeString() {
        System.out.println("2. 回文字符串检测测试：");
        
        String[] testStrings = {
            "racecar",
            "A man a plan a canal Panama",
            "race a car",
            "hello",
            "Madam",
            "Was it a car or a cat I saw?",
            "",
            "a"
        };
        
        for (String str : testStrings) {
            boolean result = PalindromeAlgorithms.isPalindromeString(str);
            System.out.printf("  \"%s\" -> %s\n", str, result ? "是回文字符串" : "不是回文字符串");
        }
        System.out.println();
    }

    /**
     * 测试回文链表检测
     */
    private static void testPalindromeLinkedList() {
        System.out.println("3. 回文链表检测测试：");
        
        // 测试用例：[1,2,2,1], [1,2,3,2,1], [1,2,3], [1], []
        Integer[][] testArrays = {
            {1, 2, 2, 1},
            {1, 2, 3, 2, 1},
            {1, 2, 3},
            {1},
            {}
        };
        
        for (Integer[] arr : testArrays) {
            PalindromeAlgorithms.ListNode head = PalindromeAlgorithms.ListNode.fromArray(arr);
            boolean result1 = PalindromeAlgorithms.isPalindromeLinkedList(head);
            
            // 重新创建链表测试优化版本（因为第一次测试可能修改了链表）
            head = PalindromeAlgorithms.ListNode.fromArray(arr);
            boolean result2 = PalindromeAlgorithms.isPalindromeLinkedListOptimized(head);
            
            System.out.printf("  %s -> 普通版本: %s, 优化版本: %s\n", 
                java.util.Arrays.toString(arr), 
                result1 ? "是回文链表" : "不是回文链表",
                result2 ? "是回文链表" : "不是回文链表");
        }
        System.out.println();
    }

    /**
     * 测试字符串转回文
     */
    private static void testMakePalindrome() {
        System.out.println("4. 字符串转回文测试：");
        
        String[] testStrings = {"abc", "hello", "a", "", "12"};
        
        for (String str : testStrings) {
            String result = PalindromeAlgorithms.makePalindrome(str);
            System.out.printf("  \"%s\" -> \"%s\"\n", str, result);
        }
        System.out.println();
    }

    /**
     * 测试括号平衡检测
     */
    private static void testBalancedParentheses() {
        System.out.println("5. 括号平衡检测测试：");
        
        String[] testStrings = {
            "()",
            "()[]{}",
            "([{}])",
            "([)]",
            "(((",
            ")))",
            "",
            "a(b[c{d}e]f)g"
        };
        
        for (String str : testStrings) {
            boolean result = PalindromeAlgorithms.isBalancedParentheses(str);
            System.out.printf("  \"%s\" -> %s\n", str, result ? "括号平衡" : "括号不平衡");
        }
        System.out.println();
    }

    /**
     * 性能测试方法
     */
    public static void performanceTest() {
        System.out.println("=== 性能测试 ===");
        
        // 测试大数字回文检测
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            PalindromeAlgorithms.isPalindromeNumber(12321);
        }
        long endTime = System.nanoTime();
        System.out.printf("回文数检测 100,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        
        // 测试长字符串回文检测
        String longString = "abcdefghijklmnopqrstuvwxyz";
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            PalindromeAlgorithms.isPalindromeString(longString);
        }
        endTime = System.nanoTime();
        System.out.printf("回文字符串检测 10,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        
        // 测试长链表回文检测
        Integer[] longArray = new Integer[1000];
        for (int i = 0; i < 500; i++) {
            longArray[i] = i;
            longArray[999 - i] = i;
        }
        
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            PalindromeAlgorithms.ListNode head = PalindromeAlgorithms.ListNode.fromArray(longArray);
            PalindromeAlgorithms.isPalindromeLinkedListOptimized(head);
        }
        endTime = System.nanoTime();
        System.out.printf("回文链表检测 1,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
    }
}