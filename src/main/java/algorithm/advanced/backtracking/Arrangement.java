package algorithm.advanced.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 排列是第一个回溯法可以拿来解决的第一个经典问题
 *
 * ==================== 排列组合算法共识 ====================
 * 
 * 【核心概念】
 * 1. 排列(Permutation)：从n个不同元素中取出r个元素，按照一定顺序排成一列
 *    - 全排列：A(n,n) = n!，考虑元素顺序
 *    - 部分排列：A(n,r) = n!/(n-r)!
 * 
 * 2. 组合(Combination)：从n个不同元素中取出r个元素，不考虑顺序
 *    - C(n,r) = n!/(r!(n-r)!)
 * 
 * 【回溯算法三要素】
 * 1. 选择列表(choices)：当前可以做的选择
 * 2. 路径(path/state)：已经做过的选择
 * 3. 结束条件：到达决策树底层，无法再做选择
 * 
 * 【回溯模板】
 * ```
 * void backtrack(路径, 选择列表) {
 *     if (满足结束条件) {
 *         result.add(路径);
 *         return;
 *     }
 *     
 *     for (选择 in 选择列表) {
 *         做选择;
 *         backtrack(路径, 选择列表);
 *         撤销选择;
 *     }
 * }
 * ```
 * 
 * 【剪枝策略】
 * 1. 全路径剪枝：used[]数组标记已使用元素，避免重复选择同一位置
 * 2. 本轮剪枝：Set<Integer> duplicated 避免同一层选择相同值的元素
 * 3. 排序剪枝：对于有重复元素的情况，先排序再剪枝效果更好
 * 
 * 【时间复杂度分析】
 * - 排列：O(n! × n)，其中n!是排列数量，n是复制每个排列的时间
 * - 空间复杂度：O(n)，递归栈深度 + 辅助数组空间
 * 
 * 【经典变种】
 * 1. 全排列 I：无重复元素
 * 2. 全排列 II：有重复元素，需要去重
 * 3. 下一个排列：字典序的下一个排列
 * 4. 第k个排列：直接计算第k个排列而不生成所有排列
 * 
 * 【应用场景】
 * - 密码破解：生成所有可能的密码组合
 * - 任务调度：安排任务执行顺序
 * - 游戏AI：棋类游戏的走法生成
 * - 数据分析：A/B测试的实验设计
 * - 算法竞赛：搜索类问题的暴力解法
 * 
 * 【优化技巧】
 * 1. 提前终止：如果只需要前k个排列，找到k个就停止
 * 2. 迭代实现：避免递归栈溢出，使用非递归方式
 * 3. 位运算优化：用位掩码代替boolean数组
 * 4. 字典序生成：按字典序逐个生成，节省空间
 *
 * @author magicliang
 *
 *         date: 2025-08-06 21:00
 */
public class Arrangement {

    public static void main(String[] args) {
        // 测试用例1：基本测试
        int[] nums1 = {1, 2, 3};
        System.out.println("测试用例1 - 数组 [1,2,3] 的全排列: " + arrange(nums1));

        // 测试用例2：单个元素
        int[] nums2 = {5};
        System.out.println("测试用例2 - 数组 [5] 的全排列: " + arrange(nums2));

        // 测试用例3：两个元素
        int[] nums3 = {1, 2};
        System.out.println("测试用例3 - 数组 [1,2] 的全排列: " + arrange(nums3));

        // 测试用例4：四个元素
        int[] nums4 = {1, 2, 3, 4};
        List<List<Integer>> result4 = arrange(nums4);
        System.out.println("测试用例4 - 数组 [1,2,3,4] 的全排列数量: " + result4.size());
        System.out.println("测试用例4 - 前5个排列: " + result4.subList(0, Math.min(5, result4.size())));

        // 测试用例5：包含重复元素，如果要去除重复元素，要有一个横跨所有函数的去重方法
        int[] nums5 = {1, 1, 2};
        System.out.println("测试用例5 - 数组 [1,1,2] 的全排列: " + arrange(nums5));

        System.out.println("测试用例5 - 数组 [1,1,2] 的 permutationsII 全排列: " + permutationsII(nums5));

        // 测试用例6：空数组
        int[] nums6 = {};
        System.out.println("测试用例6 - 空数组的全排列: " + arrange(nums6));
    }

    /**
     * 生成给定数组的所有不重复全排列
     *
     * @param nums 输入的整数数组，可能包含重复元素
     * @return 所有不重复的全排列结果列表
     */
    public static List<List<Integer>> arrange(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        boolean[] used = new boolean[nums.length];
        List<Integer> current = new ArrayList<>();

        // 好的递归 backtrack 不依赖于返回值，而依赖于全局变量或者传递变量
        // 竞赛的方式是在第一层初始化全部数据结构，但真正使用数据结构在第二层方法
        backtrack(nums, used, current, result);
        return result;
    }

    /**
     * 回溯算法的核心实现
     *
     * @param nums 原始输入数组
     * @param used 标记每个元素是否已被使用的布尔数组
     * @param current 当前正在构建的排列
     * @param result 存储所有完整排列的结果列表
     */
    private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // 终止条件：当前排列长度等于数组长度
        if (current.size() == nums.length) {  // 这是一个剪枝
            result.add(new ArrayList<>(current));
            return;
        }

        // 选择列表：遍历所有未被使用的元素

        // 这个列表可以过滤掉本轮用过的元素，如果全局有n个元素（比如2个1），那么剪枝过后，结果数量会/n。比如 [1,1,2] 原本输出6个排列，现在只剩下3个
        // 跨层之间的重复仍然是可能存在的，但是本层里面的多个重复选择会塌缩到一个选择
        Set<Integer> duplicated = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 这是一个全路径剪枝和本轮剪枝结合的做法
            if (used[i] // 重复元素剪枝
                    || duplicated.contains(nums[i]) // 相等元素剪枝
            ) {
                continue;
            }

            // 做出选择
            used[i] = true;
            current.add(nums[i]);

            // 在同层里用duplicated去重，这样这一层会少一个循环-这样剪枝意味着嵌套的层数变少

            duplicated.add(nums[i]);
            // 递归进入下一层
            backtrack(nums, used, current, result);

            // 撤销选择（回溯），易错的点：used 和 current 两个要一起撤销。current意味着当前的数据要穿梭到下游的数据，当本轮结束后，current的路径点上是不应该包含本 nums[i] 了
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    /**
     * 回溯算法：全排列 II
     * 回溯算法的另一种实现方式
     * 使用状态列表而非全局变量来传递当前状态
     *
     * @param state 当前构建的排列状态
     * @param choices 原始选择数组
     * @param selected 标记哪些索引已被选择的布尔数组
     * @param res 存储结果的列表
     */
    static void backtrack(List<Integer> state, int[] choices, boolean[] selected, List<List<Integer>> res) {
        // 当状态长度等于元素数量时，记录解
        if (state.size() == choices.length) {
            res.add(new ArrayList<>(state));
            return;
        }
        // 遍历所有选择
        Set<Integer> duplicated = new HashSet<>();
        for (int i = 0; i < choices.length; i++) {
            int choice = choices[i];
            // 剪枝：不允许重复选择元素 且 不允许重复选择相等元素
            if (!selected[i] && !duplicated.contains(choice)) {
                // 尝试：做出选择，更新状态
                duplicated.add(choice); // 记录选择过的元素值
                selected[i] = true;
                state.add(choice);
                // 进行下一轮选择
                backtrack(state, choices, selected, res);
                // 回退：撤销选择，恢复到之前的状态
                selected[i] = false;
                state.remove(state.size() - 1);
            }
        }
    }

    /**
     * 全排列II的入口方法
     * 提供与arrange方法相同的功能，但使用不同的实现方式
     *
     * @param nums 输入的整数数组
     * @return 所有不重复的全排列
     */
    static List<List<Integer>> permutationsII(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        backtrack(new ArrayList<>(), nums, new boolean[nums.length], res);
        return res;
    }
}