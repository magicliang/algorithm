package algorithm.foundations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode 3510 解法分析：模拟 vs 贪心
 *
 * 问题核心：模拟和贪心在这个问题中的关系
 *
 * 结论：模拟就是贪心策略的实现方式
 * - 贪心策略：每次选择和最小的相邻对
 * - 模拟过程：重复执行这个贪心选择
 *
 * @author liangchuan
 */
public class GreedyVsSimulation {

    public static void main(String[] args) {
        GreedyVsSimulation solution = new GreedyVsSimulation();

        int[] nums = {4, 3, 2, 1, 2, 3, 1};
        System.out.println("原数组: " + Arrays.toString(nums));
        System.out.println();

        System.out.println("=== 方法1: 直接模拟（贪心策略） ===");
        int result1 = solution.simulationApproach(nums.clone());
        System.out.println("操作次数: " + result1);
        System.out.println();

        System.out.println("=== 方法2: 优化的贪心实现 ===");
        int result2 = solution.optimizedGreedy(nums.clone());
        System.out.println("操作次数: " + result2);
        System.out.println();

        System.out.println("=== 算法复杂度分析 ===");
        solution.complexityAnalysis();
    }

    /**
     * 方法1: 直接模拟（这本身就是贪心策略）
     *
     * 算法思路：
     * 1. 每次扫描所有相邻对，找到和最小的
     * 2. 合并这一对
     * 3. 重复直到数组有序
     *
     * 这是贪心策略的直接实现
     */
    public int simulationApproach(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        int operations = 0;

        while (!isNonDecreasing(list)) {
            // 贪心选择：找到和最小的相邻对
            Integer minSum = null;  // 使用可空的Integer
            int minIndex = -1;

            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (minSum == null || sum < minSum) {  // 更优雅的比较
                    minSum = sum;
                    minIndex = i;
                }
            }

            // 执行贪心选择
            if (minSum != null) {  // 更清晰的判断条件
                list.set(minIndex, minSum);
                list.remove(minIndex + 1);
                operations++;

                System.out.println("第" + operations + "次操作: 选择和最小的相邻对，和=" + minSum);
                System.out.println("当前数组: " + list);
            }
        }

        return operations;
    }

    /**
     * 方法2: 优化的贪心实现
     *
     * 使用优先队列来快速找到最小和的相邻对
     * 本质上还是同样的贪心策略，只是实现更高效
     */
    public int optimizedGreedy(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        int operations = 0;

        while (!isNonDecreasing(list) && list.size() > 1) {
            // 使用优先队列实现贪心选择
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]); // 按和排序
                }
                return Integer.compare(a[1], b[1]); // 和相同时按索引排序
            });

            // 将所有相邻对加入优先队列
            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                pq.offer(new int[]{sum, i});
            }

            // 贪心选择：取出和最小的相邻对
            if (!pq.isEmpty()) {
                int[] minPair = pq.poll();
                int minSum = minPair[0];
                int minIndex = minPair[1];

                if (minIndex < list.size() - 1) {
                    list.set(minIndex, minSum);
                    list.remove(minIndex + 1);
                    operations++;

                    System.out.println("第" + operations + "次操作: 贪心选择和=" + minSum);
                    System.out.println("当前数组: " + list);
                }
            }
        }

        return operations;
    }

    /**
     * 检查数组是否为非递减序列
     */
    private boolean isNonDecreasing(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 算法复杂度分析
     */
    public void complexityAnalysis() {
        System.out.println("【算法本质分析】");
        System.out.println("1. 这个问题的核心是贪心策略：每次选择和最小的相邻对");
        System.out.println("2. 模拟是实现这个贪心策略的方法");
        System.out.println("3. 模拟 ≠ 暴力，模拟是有策略的（贪心策略）");
        System.out.println();

        System.out.println("【为什么贪心策略是正确的？】");
        System.out.println("1. 每次选择和最小的相邻对，能够最小化总的合并成本");
        System.out.println("2. 局部最优选择能够导致全局最优解");
        System.out.println("3. 这是因为合并操作的单调性：合并后的值只会增大");
        System.out.println();

        System.out.println("【时间复杂度分析】");
        System.out.println("- 直接模拟: O(n³) - 最多n-1次操作，每次O(n²)");
        System.out.println("- 优化版本: O(n²log n) - 使用优先队列优化");
        System.out.println();

        System.out.println("【其他可能的方法】");
        System.out.println("1. 动态规划：理论上可行，但状态空间巨大，不实用");
        System.out.println("2. 回溯算法：会超时，因为搜索空间太大");
        System.out.println("3. 贪心+模拟：最佳选择，既正确又高效");
        System.out.println();

        System.out.println("【结论】");
        System.out.println("对于这个问题，模拟就是贪心策略的最佳实现方式！");
        System.out.println("模拟不是暴力搜索，而是有策略的逐步求解。");
    }
}