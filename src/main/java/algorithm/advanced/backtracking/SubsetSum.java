package algorithm.advanced.backtracking;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 子集和问题 - 寻找数组中所有和为指定目标值的子集
 * 允许重复使用数组中的元素
 *
 * <h3>DFS求和问题去重的核心诀窍：</h3>
 * <ol>
 *   <li><strong>排序预处理</strong>：必须先对数组排序，使相同元素相邻，为后续去重创造条件</li>
 *   <li><strong>层级去重机制</strong>：
 *     <ul>
 *       <li>使用条件判断 {@code i > start && nums[i] == nums[i-1]} 跳过同一层级的重复元素</li>
 *       <li>关键点在于比较当前索引i与起始位置start的关系</li>
 *     </ul>
 *   </li>
 *   <li><strong>两种典型场景处理</strong>：
 *     <ul>
 *       <li>允许重复使用元素时：直接遍历所有元素无需去重</li>
 *       <li>不允许重复使用元素时：递归传入i+1确保不重复使用同一元素</li>
 *     </ul>
 *   </li>
 *   <li><strong>去重本质</strong>：
 *     <ul>
 *       <li>水平去重：防止同一递归层级选择相同值</li>
 *       <li>垂直去重：通过start参数控制避免重复使用元素</li>
 *     </ul>
 *   </li>
 *   <li><strong>记忆口诀</strong>：排序→层级判断→相邻比较→递增起点</li>
 * </ol>
 *
 * <h3>双指针算法 vs 回溯算法对比：</h3>
 * <table border="1">
 *   <tr>
 *     <th>算法特点</th>
 *     <th>双指针算法</th>
 *     <th>回溯算法</th>
 *   </tr>
 *   <tr>
 *     <td>适用场景</td>
 *     <td>固定数量数字求和（如两数/三数之和）和有序数组</td>
 *     <td>不定数量数字求和（如子集和问题）和需要所有解的情况</td>
 *   </tr>
 *   <tr>
 *     <td>时间复杂度</td>
 *     <td>两数之和O(n)，三数之和O(n²)，四数之和O(n³)</td>
 *     <td>子集和问题O(2^n)</td>
 *   </tr>
 *   <tr>
 *     <td>去重机制</td>
 *     <td>利用单调性去重：排序后的确定性移动避免重复</td>
 *     <td>层级去重：通过条件判断跳过同层重复元素</td>
 *   </tr>
 *   <tr>
 *     <td>搜索空间</td>
 *     <td>线性搜索空间：只能在一条线性路径上移动</td>
 *     <td>树形搜索空间：可以探索所有可能的组合路径</td>
 *   </tr>
 *   <tr>
 *     <td>解的数量</td>
 *     <td>适合找到一对解或固定结构的解</td>
 *     <td>适合找到所有可能的解</td>
 *   </tr>
 *   <tr>
 *     <td>决策机制</td>
 *     <td>基于当前状态的确定性移动，无法回溯</td>
 *     <td>每个位置都有"选择"或"不选择"的决策树</td>
 *   </tr>
 * </table>
 *
 * <h3>算法选择建议：</h3>
 * <ul>
 *   <li><strong>固定数量求和</strong>：优先考虑双指针（效率更高）</li>
 *   <li><strong>不定长度组合</strong>：必须使用回溯（双指针无法处理）</li>
 *   <li><strong>需要所有解</strong>：只能使用回溯（双指针只能找到部分解）</li>
 *   <li><strong>单一解或少数解</strong>：双指针更高效</li>
 * </ul>
 *
 * @author magicliang
 *
 *         date: 2025-08-26 10:41
 */
public class SubsetSum {

    /**
     * 寻找数组中所有和为指定目标值的子集（允许重复选择元素）
     *
     * @param nums 输入整数数组，允许包含重复元素
     * @param target 目标和值
     * @return 所有满足条件的子集列表，每个子集是一个整数列表
     * @example 输入: nums = [2,3,6,7], target = 7
     *         输出: [[7], [2,2,3]]
     * @note 该方法允许重复使用数组中的元素
     * @note 结果中可能包含重复的子集（当输入数组有重复元素时）
     */
    public List<List<Integer>> subsetSumINaive(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == nums || nums.length == 0) {
            return result;
        }

        // 验证输入，防止无限递归
        for (int num : nums) {
            if (num <= 0) {
                throw new IllegalArgumentException("输入数组必须只包含正整数，避免无限递归");
            }
        }

        // 在回溯框架里，有时候 states 和 states 是可以合并的
        List<Integer> states = new ArrayList<>();
        backtrack(nums, target, states, 0, result);
        return result;
    }

    /**
     * 回溯算法核心实现 - 允许重复选择元素的子集和求解
     *
     * @param nums 输入整数数组，允许包含重复元素
     * @param target 剩余需要达到的目标和值
     * @param states 当前已选择的元素列表，表示当前路径状态
     * @param total 当前已选择元素的总和
     * @param result 存储所有满足条件的子集结果列表
     * @implNote 实现细节：
     *         - 使用深度优先搜索(DFS)遍历所有可能的组合
     *         - 允许重复使用数组中的元素（通过不更新start索引实现）
     *         - 在进入递归前进行剪枝，避免无效搜索
     *         - 使用回溯模板：选择→递归→撤销选择
     * @algorithm 回溯算法步骤：
     *         1. 终止条件：当total等于target时，找到有效解
     *         2. 剪枝条件：当total超过target时，停止当前路径搜索
     *         3. 选择阶段：遍历所有元素，允许重复选择
     *         4. 递归探索：对每个选择进行深度优先搜索
     *         5. 撤销选择：回溯到上一步状态，继续探索其他可能性
     * @timeComplexity O(n ^ m) 其中n是数组长度，m是target/min(nums)的近似值
     * @spaceComplexity O(m) 递归栈的最大深度
     */
    void backtrack(int[] nums, int target, List<Integer> states, int total, List<List<Integer>> result) {

        // 只处理等于的情况
        if (total == target) {
            result.add(new ArrayList<>(states));
            return;
        }

        // 添加深度限制防止无限递归
//        if (states.size() > 100) {  // 或其他合理限制
//            return;
//        }

        // 因为可以重复取值，所以每一轮循环都不校验 used 的纵向使用，而允许全嵌套
        for (int num : nums) {
            // 在进入选择前剪枝，和在下一个递归里剪枝的结果是一样的
            if (total + num > target) {
                continue;
            }
            // 选择即记录
            total += num;
            states.add(num);
            backtrack(nums, target, states, total, result);
            // 撤销选择即记录
            states.remove(states.size() - 1);
            total -= num;
        }

    }

    /**
     * 寻找数组中所有和为指定目标值的子集（不重复使用元素，避免重复组合）
     *
     * @param nums 输入整数数组，可能包含重复元素
     * @param target 目标和值
     * @return 所有满足条件的子集列表，每个子集是一个整数列表
     * @example 输入: nums = [2,3,6,7], target = 7
     *         输出: [[2,5], [7]]  // 假设数组包含5
     * @note 该方法不重复使用数组中的元素（每个位置的元素最多使用一次）
     * @note 通过排序和跳过重复元素来避免产生重复的组合
     * @note 时间复杂度: O(2^n)，其中n是数组长度
     * @note 空间复杂度: O(n)用于递归栈空间
     */
    public List<List<Integer>> subsetSumNoDuplicateCombination(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == nums || nums.length == 0) {
            return result;
        }

        // 直接在开头避免负数的无限递归
        for (int num : nums) {
            if (num <= 0) {
                throw new IllegalArgumentException("输入数组必须只包含正整数，避免无限递归");
            }
        }

        // 易错的点：要去重要保持单调有序
        Arrays.sort(nums);
        backtrackNoDuplicateCombination(nums, target, new ArrayList<>(), 0, result);

        return result;
    }

    /**
     * 不重复使用元素的子集和回溯算法实现
     *
     * @param nums 已排序的输入整数数组，必须预先排序以支持重复元素去重
     * @param target 剩余需要达到的目标和值
     * @param states 当前已选择的元素列表，表示当前路径状态
     * @param start 起始搜索位置索引，确保不重复使用前面的元素-这种排除坐标本质上就是类似双指针嵌套搜索的时候的内指针，防止重复选择
     * @param result 存储所有满足条件的子集结果列表
     * @implNote 实现细节：
     *         - 使用排序后的数组来支持重复元素的去重处理
     *         - 通过start参数控制元素使用范围，避免重复使用
     *         - 在同一层级跳过重复值，避免产生重复解
     *         - 利用排序特性进行负值剪枝，提前终止无效搜索
     * @algorithm 回溯算法步骤：
     *         1. 终止条件：当target减至0时，找到有效解
     *         2. 选择范围：从start位置开始遍历，避免重复使用元素
     *         3. 去重处理：跳过同一层级的重复元素
     *         4. 剪枝优化：利用排序特性，提前终止不可能的路径
     *         5. 递归探索：对每个有效选择进行深度优先搜索
     *         6. 回溯撤销：移除最后选择的元素，继续探索其他可能性
     * @implNote <strong>DFS去重的核心诀窍详解：</strong>
     *         <ul>
     *           <li><strong>排序是前提</strong>：{@code Arrays.sort(nums)} 使相同元素相邻</li>
     *           <li><strong>层级去重条件</strong>：{@code i > start && nums[i] == nums[i-1]}
     *             <ul>
     *               <li>{@code i > start}：确保只在同一层级跳过重复，不影响不同层级的相同值</li>
     *               <li>{@code nums[i] == nums[i-1]}：检测相邻重复元素</li>
     *             </ul>
     *           </li>
     *           <li><strong>垂直去重</strong>：{@code i + 1} 作为下次递归的start，确保不重复使用元素</li>
     *           <li><strong>水平去重</strong>：跳过同层重复值，避免 [1,1,2] 和 [1,1,2] 这样的重复组合</li>
     *         </ul>
     * @implNote <strong>为什么不能用双指针解决此问题：</strong>
     *         <ul>
     *           <li><strong>搜索空间不同</strong>：双指针是线性搜索，回溯是树形搜索</li>
     *           <li><strong>解的数量要求</strong>：双指针适合找一对解，回溯适合找所有解</li>
     *           <li><strong>组合长度</strong>：双指针适合固定长度（如三数和），回溯适合不定长度</li>
     *           <li><strong>决策机制</strong>：双指针是确定性移动，回溯有选择/不选择的决策树</li>
     *         </ul>
     * @timeComplexity O(2 ^ n) 其中n是数组长度，每个元素都有选或不选两种选择
     * @spaceComplexity O(n) 递归栈的最大深度，最坏情况下为数组长度
     * @see #subsetSumNoDuplicateCombination(int[], int) 公有方法调用此私有方法
     */
    private void backtrackNoDuplicateCombination(int[] nums, int target, List<Integer> states, int start,
            List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(states));
            // 结尾剪枝
            return;
        }

        final int length = nums.length;
        // 从start开始，确保不重复使用前面的元素
        for (int i = start; i < length; i++) {
            // 跳过重复元素，避免重复解 - DFS去重的核心诀窍
            // 关键：i > start 确保只在同一层级跳过重复，不影响不同层级的相同值
            // 诀窍解析：
            // 1. 排序预处理使相同元素相邻：[1,1,2] 而不是 [1,2,1]
            // 2. i > start：只在同一递归层级去重，允许不同层级使用相同值
            // 3. nums[i] == nums[i-1]：检测相邻重复，这是排序的直接好处
            // 4. 水平去重：防止同层选择重复值产生 [1₁,2] 和 [1₂,2] 这样的重复组合
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            // 做负值剪枝
            // 由于数组已排序，后续元素更大，可以提前终止
            if (target - nums[i] < 0) {
                continue;
            }

            states.add(nums[i]);

            // 传入i+1确保不重复使用当前元素 - 垂直去重的关键
            // 关键：i+1 确保每个元素只使用一次
            // 垂直去重诀窍：通过递增start参数，类似双指针的内指针机制
            // 区别于双指针：双指针是线性确定性移动，这里是树形递归探索
            // 为什么不能用双指针：需要找所有解而非一对解，需要回溯探索所有路径
            backtrackNoDuplicateCombination(nums, target - nums[i], states, i + 1, result);

            states.remove(states.size() - 1);
        }
    }

    /**
     * 寻找数组中所有和为指定目标值的子集（不重复使用元素，避免重复组合）
     *
     * @param nums 输入整数数组，可能包含重复元素
     * @param target 目标和值
     * @return 所有满足条件的子集列表，每个子集是一个整数列表
     * @implNote 方法命名说明：
     *         - "NoDuplicateElements"指的是避免重复使用同一个位置的元素（每个元素最多使用一次）
     *         - 但允许数组中存在重复值，并通过排序和跳过机制避免产生重复的组合结果
     *         - 与{@link #subsetSumNoDuplicateCombination}方法功能完全相同，只是命名不同
     * @example 输入: nums = [1,1,2,5], target = 4
     *         输出: [[1,1,2]]  // 两个1来自不同位置，但值相同
     * @example 输入: nums = [2,3,6,7], target = 7
     *         输出: [[7]]  // 只有一个7可用
     * @note 该方法与{@link #subsetSumNoDuplicateCombination}实现逻辑完全一致
     * @note 方法命名中的"Elements"强调元素不重复使用，而非值不重复
     * @note 通过排序和跳过机制处理数组中的重复值，避免产生重复的组合
     * @note 时间复杂度: O(2^n)，其中n是数组长度
     * @note 空间复杂度: O(n)用于递归栈空间
     * @see #subsetSumNoDuplicateCombination(int[], int) 功能相同的方法
     */
    public List<List<Integer>> subsetSumNoDuplicateElements(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == nums || nums.length == 0) {
            return result;
        }

        // 直接在开头避免负数的无限递归
        for (int num : nums) {
            if (num <= 0) {
                throw new IllegalArgumentException("输入数组必须只包含正整数，避免无限递归");
            }
        }

        // 易错的点：要去重要保持单调有序
        // 排序是后续跳过重复元素的前提条件
        Arrays.sort(nums);

        // 初始化回溯过程
        // 创建一个空的states列表来跟踪当前路径的选择
        // 从索引0开始搜索，确保可以访问所有元素
        backtrackNoDuplicateElements(nums, target, new ArrayList<>(), 0, result);

        return result;
    }

    /**
     * 不重复使用元素的子集和回溯算法实现（与subsetSumNoDuplicateCombination功能相同）
     *
     * @param nums 已排序的输入整数数组，必须预先排序以支持重复元素去重
     * @param target 剩余需要达到的目标和值
     * @param states 当前已选择的元素列表，表示当前路径状态
     * @param start 起始搜索位置索引，确保不重复使用前面的元素
     * @param result 存储所有满足条件的子集结果列表
     * @implNote 实现细节：
     *         - 此实现与{@link #backtrackNoDuplicateCombination}方法功能完全相同
     *         - 使用排序后的数组来支持重复元素的去重处理
     *         - 通过start参数控制元素使用范围，避免重复使用
     *         - 在同一层级跳过重复值，避免产生重复解
     *         - 利用排序特性进行负值剪枝，提前终止无效搜索
     * @algorithm 回溯算法步骤：
     *         1. 终止条件：当target减至0时，找到有效解
     *         2. 选择范围：从start位置开始遍历，避免重复使用元素
     *         3. 去重处理：跳过同一层级的重复元素（i > start && nums[i] == nums[i-1]）
     *         4. 剪枝优化：利用排序特性，当target - nums[i] < 0时提前终止
     *         5. 递归探索：对每个有效选择进行深度优先搜索
     *         6. 回溯撤销：移除最后选择的元素，继续探索其他可能性
     * @timeComplexity O(2 ^ n) 其中n是数组长度，每个元素都有选或不选两种选择
     * @spaceComplexity O(n) 递归栈的最大深度，最坏情况下为数组长度
     * @see #subsetSumNoDuplicateElements(int[], int) 公有方法调用此私有方法
     * @see #backtrackNoDuplicateCombination(int[], int, List, int, List) 功能相同的方法
     */
    private void backtrackNoDuplicateElements(int[] nums, int target, List<Integer> states, int start,
            List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(states));
            return;
        }

        final int length = nums.length;
        for (int i = start; i < length; i++) {
            // 跳过重复元素，避免重复解 - DFS去重的核心诀窍（与上面方法相同）
            // 关键：只在同一层级跳过重复，不影响不同层级的相同值
            // 去重诀窍记忆口诀：排序→层级判断→相邻比较→递增起点
            // 1. 排序：Arrays.sort(nums) 使相同元素相邻
            // 2. 层级判断：i > start 确保只在同层去重
            // 3. 相邻比较：nums[i] == nums[i-1] 检测重复
            // 4. 递增起点：下次递归传入 i+1 避免重复使用
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            // 作负值剪枝
            // 由于数组已排序，后续元素更大，可以提前终止循环
            if (target - nums[i] < 0) {
                continue;
            }

            states.add(nums[i]);
            // 此处是 i + 1，不是 i 了 - 垂直去重机制
            // 修改递归值是类似 twosum 的改法，但本质不同：
            // 双指针：线性搜索空间，确定性移动，适合找一对解
            // 回溯：树形搜索空间，回溯探索，适合找所有解
            // 关键：i+1 确保每个元素只使用一次，避免重复使用
            // 为什么必须用回溯而非双指针：组合长度不固定且需要所有可能的解
            backtrackNoDuplicateElements(nums, target - nums[i], states, i + 1, result);
            states.remove(states.size() - 1);
        }
    }
}