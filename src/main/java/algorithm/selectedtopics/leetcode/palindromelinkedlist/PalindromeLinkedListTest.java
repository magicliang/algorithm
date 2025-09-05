package algorithm.selectedtopics.leetcode.palindromelinkedlist;

/**
 * 回文链表算法测试类
 * 测试5种不同的解法
 */
public class PalindromeLinkedListTest {
    
    public static void main(String[] args) {
        System.out.println("=== 回文链表算法测试 ===\n");
        
        // 测试用例
        TestCase[] testCases = {
            new TestCase("空链表", null, true),
            new TestCase("单节点", new int[]{1}, true),
            new TestCase("两节点回文", new int[]{1, 1}, true),
            new TestCase("两节点非回文", new int[]{1, 2}, false),
            new TestCase("奇数长度回文", new int[]{1, 2, 3, 2, 1}, true),
            new TestCase("偶数长度回文", new int[]{1, 2, 2, 1}, true),
            new TestCase("奇数长度非回文", new int[]{1, 2, 3, 4, 5}, false),
            new TestCase("偶数长度非回文", new int[]{1, 2, 3, 4}, false),
            new TestCase("长回文链表", new int[]{1, 2, 3, 4, 5, 4, 3, 2, 1}, true),
            new TestCase("相同值回文", new int[]{2, 2, 2, 2}, true),
            new TestCase("部分相同非回文", new int[]{1, 2, 2, 3}, false)
        };
        
        // 测试所有解法
        String[] methodNames = {
            "解法1: 整链反转比对",
            "解法2: 栈",
            "解法3: 递归(双返回值)",
            "解法4: 递归(封装类)",
            "解法5: 快慢指针+半链反转",
            "解法5变种: 简化版"
        };
        
        boolean allPassed = true;
        
        for (int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];
            System.out.printf("测试用例 %d: %s\n", i + 1, testCase.description);
            System.out.printf("输入: %s\n", arrayToString(testCase.input));
            System.out.printf("期望: %s\n", testCase.expected);
            
            // 为每个方法创建独立的链表（因为有些方法会修改链表）
            ListNode[] heads = new ListNode[6];
            for (int j = 0; j < 6; j++) {
                heads[j] = testCase.input == null ? null : ListNode.fromArray(testCase.input);
            }
            
            // 测试所有方法
            boolean[] results = {
                PalindromeLinkedListSolutions.isPalindromeByReverseWhole(heads[0]),
                PalindromeLinkedListSolutions.isPalindromeByStack(heads[1]),
                PalindromeLinkedListSolutions.isPalindromeByRecursion(heads[2]),
                PalindromeLinkedListSolutions.isPalindromeByComparator(heads[3]),
                PalindromeLinkedListSolutions.isPalindromeByTwoPointers(heads[4]),
                PalindromeLinkedListSolutions.isPalindromeByTwoPointersSimple(heads[5])
            };
            
            boolean testPassed = true;
            for (int j = 0; j < results.length; j++) {
                String status = results[j] == testCase.expected ? "✓" : "✗";
                System.out.printf("  %s %s: %s\n", status, methodNames[j], results[j]);
                if (results[j] != testCase.expected) {
                    testPassed = false;
                    allPassed = false;
                }
            }
            
            System.out.printf("结果: %s\n\n", testPassed ? "通过" : "失败");
        }
        
        System.out.println("=== 测试总结 ===");
        System.out.printf("总体结果: %s\n", allPassed ? "所有测试通过!" : "存在测试失败!");
        
        if (allPassed) {
            System.out.println("\n=== 算法复杂度对比 ===");
            printComplexityComparison();
        }
    }
    
    /**
     * 打印算法复杂度对比
     */
    private static void printComplexityComparison() {
        System.out.println("┌─────────────────────────────┬──────────┬──────────┬─────────────────┐");
        System.out.println("│           解法              │ 时间复杂度 │ 空间复杂度 │      特点       │");
        System.out.println("├─────────────────────────────┼──────────┼──────────┼─────────────────┤");
        System.out.println("│ 1. 整链反转比对              │   O(n)   │   O(n)   │ 最容易想到       │");
        System.out.println("│ 2. 栈                       │   O(n)   │   O(n)   │ 代码最短         │");
        System.out.println("│ 3. 递归(双返回值)            │   O(n)   │   O(n)   │ 体现多返回值技巧  │");
        System.out.println("│ 4. 递归(封装类)              │   O(n)   │   O(n)   │ 代码可读性好     │");
        System.out.println("│ 5. 快慢指针+半链反转         │   O(n)   │   O(1)   │ 满足进阶要求     │");
        System.out.println("└─────────────────────────────┴──────────┴──────────┴─────────────────┘");
    }
    
    /**
     * 数组转字符串
     */
    private static String arrayToString(int[] arr) {
        if (arr == null) {
            return "null";
        }
        if (arr.length == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 测试用例类
     */
    private static class TestCase {
        String description;
        int[] input;
        boolean expected;
        
        TestCase(String description, int[] input, boolean expected) {
            this.description = description;
            this.input = input;
            this.expected = expected;
        }
    }
}