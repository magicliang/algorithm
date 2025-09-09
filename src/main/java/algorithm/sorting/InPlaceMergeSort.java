package algorithm.sorting;

/**
 * project name: domain-driven-transaction-sys
 *
 * description: 原地归并排序实现
 * 在原始数组上进行归并排序，减少内存使用
 * 
 * <h2>空间复杂度分析与优化策略</h2>
 * 
 * <h3>1. 当前实现的空间复杂度：O(n)</h3>
 * <ul>
 *   <li><strong>临时数组</strong>：O(n) - 只创建一次，大小为待排序区间</li>
 *   <li><strong>递归栈</strong>：O(log n) - 递归深度</li>
 *   <li><strong>总空间复杂度</strong>：O(n) - 这已经是归并排序的理论最优解</li>
 * </ul>
 * 
 * <h3>2. 空间复杂度对比分析</h3>
 * <table border="1">
 *   <tr>
 *     <th>实现方式</th>
 *     <th>空间复杂度</th>
 *     <th>优缺点</th>
 *   </tr>
 *   <tr>
 *     <td><strong>当前实现（复用数组）</strong></td>
 *     <td><strong>O(n)</strong></td>
 *     <td>✅ 空间最优<br>✅ 实现复杂度适中</td>
 *   </tr>
 *   <tr>
 *     <td>每次递归创建新数组</td>
 *     <td>O(n log n)</td>
 *     <td>❌ 空间浪费<br>❌ 可能导致栈溢出</td>
 *   </tr>
 *   <tr>
 *     <td>完全原地排序</td>
 *     <td>O(log n)</td>
 *     <td>✅ 空间极优<br>❌ 时间复杂度退化到O(n²)</td>
 *   </tr>
 * </table>
 * 
 * <h3>3. 为什么每次递归创建新数组会导致O(n log n)空间复杂度？</h3>
 * <p>如果每次递归调用都创建新的临时数组：</p>
 * <ul>
 *   <li><strong>递归层数</strong>：log n 层</li>
 *   <li><strong>每层创建的临时数组总大小</strong>：O(n)</li>
 *   <li><strong>总空间复杂度</strong>：n × log n = O(n log n)</li>
 * </ul>
 * 
 * <h3>4. 当前实现的优化策略：空间复用</h3>
 * <ul>
 *   <li><strong>一次分配</strong>：只在入口处分配一个临时数组</li>
 *   <li><strong>递归复用</strong>：所有递归层级都使用同一个临时数组</li>
 *   <li><strong>局部映射</strong>：通过索引映射实现不同区间的合并</li>
 * </ul>
 * 
 * <h3>5. 与其他排序算法的空间复杂度对比</h3>
 * <ul>
 *   <li><strong>快速排序</strong>：O(log n) - 但不稳定</li>
 *   <li><strong>堆排序</strong>：O(1) - 但不稳定</li>
 *   <li><strong>归并排序</strong>：O(n) - 稳定排序的最优解</li>
 * </ul>
 * 
 * <p><strong>结论</strong>：当前实现的O(n)空间复杂度已经是归并排序在保持稳定性和时间效率前提下的最优解。</p>
 * 
 * <h2>算法流程：中转机制的设计思想</h2>
 * 
 * <h3>1. 数据流转过程</h3>
 * <p>每次归并操作的核心流程：</p>
 * <pre>
 * 原数组区间 → 辅助数组 → 归并处理 → 写回原数组区间
 * </pre>
 * <ul>
 *   <li><strong>步骤1</strong>：将待排序区间复制到辅助数组（避免数据覆盖）</li>
 *   <li><strong>步骤2</strong>：在辅助数组上进行双指针归并（安全操作）</li>
 *   <li><strong>步骤3</strong>：将排序结果写回原数组对应区间（完成排序）</li>
 * </ul>
 * 
 * <h3>2. 与汉诺塔问题的相似性分析</h3>
 * <table border="1">
 *   <tr>
 *     <th>特征</th>
 *     <th>汉诺塔</th>
 *     <th>归并排序</th>
 *   </tr>
 *   <tr>
 *     <td><strong>辅助结构</strong></td>
 *     <td>中间柱子</td>
 *     <td>辅助数组 temp</td>
 *   </tr>
 *   <tr>
 *     <td><strong>数据转移</strong></td>
 *     <td>盘子在柱子间移动</td>
 *     <td>数据在数组间复制</td>
 *   </tr>
 *   <tr>
 *     <td><strong>分治思想</strong></td>
 *     <td>大盘子依赖小盘子</td>
 *     <td>大区间依赖小区间</td>
 *   </tr>
 *   <tr>
 *     <td><strong>临时存储</strong></td>
 *     <td>暂存到辅助柱</td>
 *     <td>暂存到辅助数组</td>
 *   </tr>
 *   <tr>
 *     <td><strong>最终目标</strong></td>
 *     <td>移到目标柱</td>
 *     <td>排序后写回原数组</td>
 *   </tr>
 * </table>
 * 
 * <h3>3. 关键差异</h3>
 * <ul>
 *   <li><strong>汉诺塔</strong>：数据永久性转移，不会"写回"</li>
 *   <li><strong>归并排序</strong>：数据临时转移，最终要写回原位置</li>
 * </ul>
 * 
 * <h3>4. 为什么需要"中转"机制？</h3>
 * <ul>
 *   <li><strong>避免数据覆盖</strong>：直接在原数组上归并会覆盖未处理的数据</li>
 *   <li><strong>保证归并正确性</strong>：需要同时访问两个已排序的子区间</li>
 *   <li><strong>提供数据快照</strong>：辅助数组避免边归并边修改的冲突</li>
 * </ul>
 *
 * @author magicliang
 *
 *         date: 2025-08-21 16:00
 */
public class InPlaceMergeSort {

    /**
     * 原地归并排序入口方法
     * 在原始数组上进行归并排序，减少内存使用
     * 
     * <p><strong>空间复杂度优化关键</strong>：</p>
     * <ul>
     *   <li>创建一次临时数组 temp，大小为待排序区间</li>
     *   <li>所有递归调用都复用这个临时数组，避免O(n log n)的空间开销</li>
     *   <li>通过索引映射技巧实现不同区间的数据合并</li>
     * </ul>
     *
     * @param arr 待排序的整数数组
     * @param left 排序区间的起始索引
     * @param right 排序区间的结束索引
     */
    public static void inPlaceMergeSort(int[] arr, int left, int right) {

        // 某些情况下不该排序
        if (arr == null || arr.length == 1 || left >= right) {
            return;
        }

        // 【空间复杂度优化核心】：只创建一次临时数组，大小为待排序区间
        // 这是实现O(n)空间复杂度的关键：避免每次递归都创建新数组导致O(n log n)空间开销
        // 所有递归层级都会复用这个临时数组，通过索引映射实现不同区间的合并
        int[] temp = new int[right - left + 1];

        // 再排右部分：因为偏左，所以加一是安全的
        inPlaceMergeSort(arr, temp, left, right);
    }

    /**
     * 原地归并排序的递归实现
     * 使用临时数组辅助合并操作
     *
     * @param arr 待排序的数组
     * @param temp 临时数组，用于合并操作
     * @param left 当前排序区间的起始索引
     * @param right 当前排序区间的结束索引
     */
    public static void inPlaceMergeSort(int[] arr, int[] temp, int left, int right) {
        // 某些情况下不该排序
        if (arr == null || arr.length == 1 || left >= right) {
            return;
        }
        // 取中点：对奇数处于正中央，对于偶数处于偏左的位置。对于单值意味着本值本身
        int mid = left + (right - left) / 2;

        // 先排左部分
        inPlaceMergeSort(arr, temp, left, mid);

        // 再排右部分：因为偏左，所以加一是安全的
        inPlaceMergeSort(arr, temp, mid + 1, right);

        // 易错的点：分治以后应该在每一层都做好 merge
        inPlaceMerge(arr, temp, left, mid, right);
    }

    /**
     * 原地合并算法
     * 将两个已排序的子数组合并成一个有序数组
     * 
     * <p><strong>中转机制的核心实现</strong>：</p>
     * <ol>
     *   <li><strong>临时转移</strong>：将待处理区间复制到辅助空间</li>
     *   <li><strong>安全归并</strong>：在辅助空间上进行双指针归并</li>
     *   <li><strong>写回原位</strong>：将排序结果写回原数组对应区间</li>
     * </ol>
     * 
     * <p>这种设计类似汉诺塔的"借助辅助结构完成复杂操作"思想，
     * 既保证了算法的正确性，又通过空间复用实现了O(n)的最优空间复杂度。</p>
     *
     * @param arr 原始数组
     * @param temp 临时数组，用于合并操作
     * @param left 左子数组的起始索引
     * @param mid 左子数组的结束索引
     * @param right 右子数组的结束索引
     */
    // 原地合并算法
    private static void inPlaceMerge(int[] arr, int[] temp, int left, int mid, int right) {
        // 已排序
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }

        // 【空间复用策略】：原地内存操作的核心思想是：准备一个和现空间一样大的空间，先把数据拷贝过去，然后再拷贝回这个共享数据段
        // 这个临时数组被所有递归层级复用，通过局部映射避免了每次递归创建新数组的O(n log n)空间开销


        // 【索引映射技巧】：如果数组完全和原始 arr 总长相等，则可以一对一映射
        // 否则，这里面的 temp 要从0开始，而 arr要从 left 开始，实现局部区间的数据映射
        if (right + 1 - left >= 0) {
            System.arraycopy(arr, left, temp, 0, right + 1 - left);
        }

        // 设置两个游标，分别在区间的起点


        // 先对距离在 temp 里是从0开始的，映射到 arr 的 left 区间
        int i = 0;
        int j = mid - left + 1;
        int tempMid = j - 1;
        int end = right - left;
        int k = left;
        while (i <= tempMid && j <= end) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        while (i <= tempMid) {
            arr[k++] = temp[i++];
        }

        while (j <= end) {
            arr[k++] = temp[j++];
        }
    }
}