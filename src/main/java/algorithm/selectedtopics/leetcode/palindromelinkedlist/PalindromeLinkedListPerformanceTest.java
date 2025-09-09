package algorithm.selectedtopics.leetcode.palindromelinkedlist;

/**
 * 回文链表算法性能测试
 * 比较5种解法的性能差异
 */
public class PalindromeLinkedListPerformanceTest {

    public static void main(String[] args) {
        System.out.println("=== 回文链表算法性能测试 ===\n");

        // 测试不同规模的数据
        int[] sizes = {100, 1000, 5000};

        for (int size : sizes) {
            System.out.printf("测试规模: %d 个节点\n", size);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50; i++) {
                sb.append("─");
            }
            System.out.println(sb);

            // 生成测试数据（回文链表）
            int[] palindromeData = generatePalindromeArray(size);

            // 测试各种方法
            testMethod("解法1: 整链反转比对", palindromeData, 1);
            testMethod("解法2: 栈", palindromeData, 2);

            // 递归方法在大数据量时可能栈溢出，只在小数据量时测试
            if (size <= 1000) {
                testMethod("解法3: 递归(双返回值)", palindromeData, 3);
                testMethod("解法4: 递归(封装类)", palindromeData, 4);
            } else {
                System.out.printf("%-25s: 跳过 (避免栈溢出)\n", "解法3: 递归(双返回值)");
                System.out.printf("%-25s: 跳过 (避免栈溢出)\n", "解法4: 递归(封装类)");
            }

            testMethod("解法5: 快慢指针+半链反转", palindromeData, 5);
            testMethod("解法5变种: 简化版", palindromeData, 6);

            System.out.println();
        }

        // 内存使用分析
        System.out.println("=== 内存使用分析 ===");
        analyzeMemoryUsage();
    }

    /**
     * 测试指定方法的性能
     */
    private static void testMethod(String methodName, int[] data, int methodType) {
        final int iterations = 100; // 测试次数
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            // 为每次测试创建新的链表
            ListNode head = ListNode.fromArray(data);

            long startTime = System.nanoTime();

            boolean result;
            switch (methodType) {
                case 1:
                    result = PalindromeLinkedListSolutions.isPalindromeByReverseWhole(head);
                    break;
                case 2:
                    result = PalindromeLinkedListSolutions.isPalindromeByStack(head);
                    break;
                case 3:
                    result = PalindromeLinkedListSolutions.isPalindromeByRecursion(head);
                    break;
                case 4:
                    result = PalindromeLinkedListSolutions.isPalindromeByComparator(head);
                    break;
                case 5:
                    result = PalindromeLinkedListSolutions.isPalindromeByTwoPointers(head);
                    break;
                case 6:
                    result = PalindromeLinkedListSolutions.isPalindromeByTwoPointersSimple(head);
                    break;
                default:
                    result = false;
            }

            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);

            // 验证结果正确性
            if (!result) {
                System.err.printf("错误: %s 返回了错误结果\n", methodName);
            }
        }

        double avgTimeMs = totalTime / (double) iterations / 1_000_000;
        System.out.printf("%-25s: %.3f ms (平均)\n", methodName, avgTimeMs);
    }

    /**
     * 生成回文数组
     */
    private static int[] generatePalindromeArray(int size) {
        int[] arr = new int[size];

        // 生成前半部分
        for (int i = 0; i < size / 2; i++) {
            arr[i] = i % 10 + 1; // 1-10的循环
        }

        // 中间元素（奇数长度时）
        if (size % 2 == 1) {
            arr[size / 2] = 5;
        }

        // 生成后半部分（镜像前半部分）
        for (int i = size / 2 + size % 2; i < size; i++) {
            arr[i] = arr[size - 1 - i];
        }

        return arr;
    }

    /**
     * 生成非回文数组
     */
    private static int[] generateNonPalindromeArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i % 10 + 1;
        }
        // 确保不是回文
        if (size > 1) {
            arr[size - 1] = arr[0] + 1;
        }
        return arr;
    }

    /**
     * 内存使用分析
     */
    private static void analyzeMemoryUsage() {
        System.out.println("各算法的空间复杂度分析:");
        System.out.println();

        System.out.println("1. 整链反转比对:");
        System.out.println("   - 需要复制整个链表: O(n) 额外空间");
        System.out.println("   - 内存使用: 2n 个节点");
        System.out.println();

        System.out.println("2. 栈:");
        System.out.println("   - 需要存储所有节点值: O(n) 额外空间");
        System.out.println("   - 内存使用: n 个整数 + 栈开销");
        System.out.println();

        System.out.println("3. 递归(双返回值):");
        System.out.println("   - 递归调用栈: O(n) 额外空间");
        System.out.println("   - 内存使用: n 层递归栈帧");
        System.out.println();

        System.out.println("4. 递归(封装类):");
        System.out.println("   - 递归调用栈: O(n) 额外空间");
        System.out.println("   - 内存使用: n 层递归栈帧 + 比对器对象");
        System.out.println();

        System.out.println("5. 快慢指针+半链反转:");
        System.out.println("   - 只使用常数个指针: O(1) 额外空间");
        System.out.println("   - 内存使用: 几个指针变量");
        System.out.println("   - ★ 唯一满足进阶要求的解法");
        System.out.println();

        // 实际内存测试
        testMemoryUsage();
    }

    /**
     * 实际内存使用测试
     */
    private static void testMemoryUsage() {
        System.out.println("=== 实际内存使用测试 ===");

        Runtime runtime = Runtime.getRuntime();
        int testSize = 10000;
        int[] testData = generatePalindromeArray(testSize);

        // 测试解法1的内存使用
        runtime.gc(); // 强制垃圾回收
        long memBefore1 = runtime.totalMemory() - runtime.freeMemory();

        ListNode head1 = ListNode.fromArray(testData);
        boolean result1 = PalindromeLinkedListSolutions.isPalindromeByReverseWhole(head1);

        long memAfter1 = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("解法1内存使用: %.2f KB\n", (memAfter1 - memBefore1) / 1024.0);

        // 测试解法5的内存使用
        runtime.gc(); // 强制垃圾回收
        long memBefore5 = runtime.totalMemory() - runtime.freeMemory();

        ListNode head5 = ListNode.fromArray(testData);
        boolean result5 = PalindromeLinkedListSolutions.isPalindromeByTwoPointersSimple(head5);

        long memAfter5 = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("解法5内存使用: %.2f KB\n", (memAfter5 - memBefore5) / 1024.0);

        System.out.printf("内存节省: %.1f%%\n",
                (1.0 - (double) (memAfter5 - memBefore5) / (memAfter1 - memBefore1)) * 100);
    }
}