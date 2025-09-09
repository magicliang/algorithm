package algorithm.selectedtopics.leetcode.palindromelinkedlist;

/**
 * 回文链表算法演示
 * 展示5种解法的使用方式和特点
 */
public class PalindromeLinkedListDemo {

    public static void main(String[] args) {
        System.out.println("=== 回文链表算法演示 ===\n");

        // 演示不同的测试用例
        demonstrateAlgorithms();

        System.out.println("\n=== 算法选择建议 ===");
        printRecommendations();
    }

    /**
     * 演示各种算法
     */
    private static void demonstrateAlgorithms() {
        // 测试用例
        int[][] testCases = {
                {1, 2, 3, 2, 1},    // 奇数长度回文
                {1, 2, 2, 1},       // 偶数长度回文
                {1, 2, 3, 4, 5},    // 非回文
                {1},                // 单节点
                {1, 1}              // 两节点回文
        };

        String[] descriptions = {
                "奇数长度回文 [1,2,3,2,1]",
                "偶数长度回文 [1,2,2,1]",
                "非回文链表 [1,2,3,4,5]",
                "单节点 [1]",
                "两节点回文 [1,1]"
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("测试用例: %s\n", descriptions[i]);

            // 为每个方法创建独立的链表
            ListNode[] heads = new ListNode[6];
            for (int j = 0; j < 6; j++) {
                heads[j] = ListNode.fromArray(testCases[i]);
            }

            // 测试所有方法
            System.out.printf("  解法1 (整链反转): %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByReverseWhole(heads[0]));
            System.out.printf("  解法2 (栈): %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByStack(heads[1]));
            System.out.printf("  解法3 (递归双返回): %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByRecursion(heads[2]));
            System.out.printf("  解法4 (递归封装): %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByComparator(heads[3]));
            System.out.printf("  解法5 (快慢指针): %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByTwoPointers(heads[4]));
            System.out.printf("  解法5简化版: %s\n",
                    PalindromeLinkedListSolutions.isPalindromeByTwoPointersSimple(heads[5]));

            System.out.println();
        }
    }

    /**
     * 打印算法选择建议
     */
    private static void printRecommendations() {
        System.out.println("🎯 面试场景建议:");
        System.out.println("  1. 首选: 解法5 (快慢指针) - 满足O(1)空间要求，展示算法功底");
        System.out.println("  2. 备选: 解法2 (栈) - 代码简洁，思路清晰");
        System.out.println("  3. 进阶: 可以提及多种思路，展示思维广度");
        System.out.println();

        System.out.println("⚡ 性能对比:");
        System.out.println("  • 时间复杂度: 所有解法都是 O(n)");
        System.out.println("  • 空间复杂度: 解法5是O(1)，其他都是O(n)");
        System.out.println("  • 实际性能: 解法5 > 解法1 > 解法3/4 > 解法2");
        System.out.println();

        System.out.println("🔧 实际应用建议:");
        System.out.println("  • 小数据量: 任何解法都可以");
        System.out.println("  • 大数据量: 优先选择解法5");
        System.out.println("  • 内存敏感: 必须使用解法5");
        System.out.println("  • 代码简洁性: 解法2最简单");
        System.out.println();

        System.out.println("⚠️  注意事项:");
        System.out.println("  • 解法3/4在大数据量时可能栈溢出");
        System.out.println("  • 解法5会修改原链表结构（可选择是否还原）");
        System.out.println("  • 解法1需要额外的内存空间");
        System.out.println("  • 所有解法都正确处理了边界情况");
    }

    /**
     * 演示链表修改情况
     */
    public static void demonstrateListModification() {
        System.out.println("=== 链表修改演示 ===");

        int[] data = {1, 2, 3, 2, 1};
        ListNode original = ListNode.fromArray(data);

        System.out.println("原始链表: " + ListNode.toString(original));

        // 使用会修改链表的方法
        boolean result = PalindromeLinkedListSolutions.isPalindromeByTwoPointersSimple(original);
        System.out.println("检测结果: " + result);
        System.out.println("检测后链表: " + ListNode.toString(original));

        // 使用会还原链表的方法
        ListNode original2 = ListNode.fromArray(data);
        System.out.println("\n原始链表: " + ListNode.toString(original2));
        boolean result2 = PalindromeLinkedListSolutions.isPalindromeByTwoPointers(original2);
        System.out.println("检测结果: " + result2);
        System.out.println("检测后链表: " + ListNode.toString(original2));
    }
}