package algorithm.datastructures.elementary;

/**
 * project name: algorithm
 *
 * description: 回文算法性能测试
 *
 * @author magicliang
 * date: 2025-09-05 14:44
 */
public class PalindromePerformanceTest {

    public static void main(String[] args) {
        System.out.println("=== 回文算法性能测试 ===\n");
        
        testNumberPerformance();
        testStringPerformance();
        testLinkedListPerformance();
    }

    private static void testNumberPerformance() {
        System.out.println("1. 回文数检测性能测试：");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            PalindromeAlgorithms.isPalindromeNumber(12321);
        }
        long endTime = System.nanoTime();
        System.out.printf("  回文数检测 100,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.println();
    }

    private static void testStringPerformance() {
        System.out.println("2. 回文字符串检测性能测试：");
        
        String longString = "abcdefghijklmnopqrstuvwxyz";
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            PalindromeAlgorithms.isPalindromeString(longString);
        }
        long endTime = System.nanoTime();
        System.out.printf("  回文字符串检测 10,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.println();
    }

    private static void testLinkedListPerformance() {
        System.out.println("3. 回文链表检测性能测试：");
        
        // 创建一个回文链表
        Integer[] palindromeArray = new Integer[100];
        for (int i = 0; i < 50; i++) {
            palindromeArray[i] = i;
            palindromeArray[99 - i] = i;
        }
        
        // 测试普通版本
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            PalindromeAlgorithms.ListNode head = PalindromeAlgorithms.ListNode.fromArray(palindromeArray);
            PalindromeAlgorithms.isPalindromeLinkedList(head);
        }
        long endTime = System.nanoTime();
        System.out.printf("  普通版本 1,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        
        // 测试优化版本
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            PalindromeAlgorithms.ListNode head = PalindromeAlgorithms.ListNode.fromArray(palindromeArray);
            PalindromeAlgorithms.isPalindromeLinkedListOptimized(head);
        }
        endTime = System.nanoTime();
        System.out.printf("  优化版本 1,000 次耗时: %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.println();
    }
}