package algorithm.datastructures.heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * project name: domain-driven-transaction-sys
 * <p>
 * description: 基于数组的完全二叉树（complete binary tree）的堆实现
 * <p>
 * 这个类型的成员方法的 i 都是指的元素的索引
 * <p>
 * 关于堆操作的复杂度分析：
 * - siftUp（上浮）操作相对简单：每次只需与1个父节点比较，边界条件简单（只需检查是否到达根节点）
 * - siftDown（下沉）操作相对复杂：每次需与2个子节点比较并找出最大值，边界条件复杂（需检查左右子节点是否存在）
 * - 虽然两者时间复杂度都是O(log n)，但siftDown的常数因子更大，代码逻辑也更复杂
 * - 在教学中通常先介绍siftUp，再介绍siftDown，因为前者更容易理解和实现
 *
 * @author magicliang
 *
 *         date: 2025-08-18 19:12
 */
public class MaxHeap {

    /**
     * 堆的内部存储，为了不解决扩容的问题，这里偷懒使用 list
     */
    private final List<Integer> heap;

    /**
     * 默认构造函数，创建一个空的最大堆
     */
    public MaxHeap() {
        heap = new ArrayList<>();
    }

//    public MaxHeap(List<Integer> queue) {
//       this();
//       // 这个算法时间复杂度是 O(nlgn)
//       for (Integer i : queue) {
//           push(i);
//       }
//    }

    /**
     * Floyd建堆算法
     * <p>
     * 使用给定的列表构建最大堆，时间复杂度为O(n)
     * 这个方法的本质，是倒序让父节点下沉，从最后一个非叶子节点开始，
     * 依次向前处理每个父节点，直到根节点下沉完成后，整个堆才满足最大堆性质
     * @param queue 用于构建堆的整数列表
     */
    public MaxHeap(List<Integer> queue) {
        this();
        this.heap.addAll(queue);
        // 这个算法时间复杂度是 O(n)。小技巧，最后一个叶子节点的父是第一个父亲，所有父亲之后的节点已经是一个最大堆的根节点了，我们要确定父节点和它们的孩子是不是最大堆
        for (int i = parent(heap.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    /**
     * 方法一：遍历选择（直接实现）
     * <p>
     * 通过遍历查找最大的k个元素，时间复杂度为O(n*k)
     *
     * @param nums 输入的整数列表
     * @param k 需要返回的最大元素个数
     * @return 包含最大的k个元素的列表
     */
    public static List<Integer> traversalTopK(List<Integer> nums, int k) {
        List<Integer> result = new ArrayList<>();
        if (k <= 0 || nums.isEmpty()) {
            return result;
        }

        List<Integer> copy = new ArrayList<>(nums); // 复制原始数据
        for (int i = 0; i < k; i++) {
            if (copy.isEmpty()) {
                break;
            }

            // 遍历寻找最大值
            int maxIndex = 0;
            for (int j = 1; j < copy.size(); j++) {
                if (copy.get(j) > copy.get(maxIndex)) {
                    maxIndex = j;
                }
            }
            // 将最大值移到结果集并从副本中移除
            result.add(copy.get(maxIndex));
            copy.remove(maxIndex);
        }
        return result;
    }

    /**
     * 方法二：排序选择（直接实现）
     * <p>
     * 通过排序后选择最大的k个元素，时间复杂度为O(n*log(n))
     *
     * @param nums 输入的整数列表
     * @param k 需要返回的最大元素个数
     * @return 包含最大的k个元素的列表
     */
    public static List<Integer> sortTopK(List<Integer> nums, int k) {
        if (k <= 0) {
            return new ArrayList<>();
        }
        if (k >= nums.size()) {
            return new ArrayList<>(nums);
        }

        // 创建副本并排序（升序）
        List<Integer> copy = new ArrayList<>(nums);
        Collections.sort(copy);

        // 取最大的k个元素（从末尾开始）
        return copy.subList(copy.size() - k, copy.size());
    }

    /**
     * 使用最大堆查找最小的k个元素
     * <p>
     * 维护一个大小为k的最大堆，堆顶元素为当前k个最小元素中的最大值
     * 遍历数组，当遇到比堆顶小的元素时替换堆顶并调整堆
     * 时间复杂度为O(n*log(k))
     *
     * @param list 输入的整数列表
     * @param k 需要返回的最小元素个数
     * @return 包含最小的k个元素的列表
     */
    public static List<Integer> heapMinK(List<Integer> list, int k) {
        List<Integer> result = new ArrayList<>();
        if (list == null || list.isEmpty() || k <= 0) {
            return result;
        }

        // 如果k大于等于列表大小，返回所有元素
        if (k >= list.size()) {
            return new ArrayList<>(list);
        }

        // 先把前k个数放进最大堆里，这k个数就是当前队列最小的k个数，其中堆顶是这k个数的边界
        MaxHeap maxHeap = new MaxHeap();
        for (int i = 0; i < k; i++) {
            maxHeap.push(list.get(i));
        }

        for (int j = k; j < list.size(); j++) {
            int current = list.get(j);

            // 确保堆不为空再进行比较
            if (!maxHeap.toList().isEmpty() && current < maxHeap.peek()) {
                maxHeap.pop();
                maxHeap.push(current);
            }
        }

        return maxHeap.toList();
    }

    /**
     * 使用Java原生PriorityQueue实现用最大堆查找最小的k个元素
     * <p>
     * 通过传入自定义Comparator将PriorityQueue配置为最大堆
     * 维护一个大小为k的最大堆，堆顶元素为当前k个最小元素中的最大值
     * 遍历数组，当遇到比堆顶小的元素时替换堆顶并调整堆
     * 时间复杂度为O(n*log(k))
     * <p>
     * 注意：此方法使用Java内置的PriorityQueue（通过Comparator实现最大堆）实现
     *
     * @param nums 输入的整数数组
     * @param k 需要返回的最小元素个数
     * @return 包含最小的k个元素的优先队列（最大堆）
     * @throws IllegalArgumentException 如果nums为null或k为负数
     */
    public static Queue<Integer> minKWithPriorityQueue(int[] nums, int k) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative");
        }
        if (k == 0) {
            return new PriorityQueue<>(Collections.reverseOrder());
        }

        // 使用Collections.reverseOrder()创建最大堆
        Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // 先把前k个元素放进最大堆里
        int limit = Math.min(k, nums.length);
        for (int i = 0; i < limit; i++) {
            maxHeap.offer(nums[i]);
        }

        // 如果k大于数组长度，直接返回包含所有元素的堆
        if (k >= nums.length) {
            return maxHeap;
        }

        // 处理剩余的元素
        for (int i = k; i < nums.length; i++) {
            if (nums[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(nums[i]);
            }
        }

        return maxHeap;
    }

    /**
     * 合并两个最大堆
     * <p>
     * 将另一个最大堆的所有元素合并到当前堆中，使用Floyd建堆算法（从最后一个非叶子节点开始下沉）重新构建堆结构
     * 时间复杂度为O(n)，其中n是两个堆的总元素数
     * <p>
     * 注意：此方法会修改当前堆，不会创建新的堆实例
     *
     * @param maxHeap 要合并的另一个最大堆
     * @return 返回合并后的当前堆实例
     * @throws IllegalArgumentException 如果maxHeap为null
     */
    public MaxHeap mergeHeap(MaxHeap maxHeap) {
        if (maxHeap == null) {
            throw new IllegalArgumentException("Cannot merge with null heap");
        }

        if (maxHeap.heap.isEmpty()) {
            return this; // 如果另一个堆为空，直接返回当前堆
        }

        this.heap.addAll(maxHeap.toList());
        int size = this.heap.size();
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
        return this;
    }

    /**
     * 使用最小堆查找最大的k个元素
     * <p>
     * 维护一个大小为k的最小堆，堆顶元素为当前k个最大元素中的最小值
     * 遍历数组，当遇到比堆顶大的元素时替换堆顶并调整堆
     * 时间复杂度为O(n*log(k))
     * <p>
     * 注意：此方法使用Java内置的PriorityQueue（最小堆）实现
     *
     * @param nums 输入的整数数组
     * @param k 需要返回的最大元素个数
     * @return 包含最大的k个元素的优先队列（最小堆）
     * @throws IllegalArgumentException 如果nums为null或k为负数
     */
    public Queue<Integer> topK(int[] nums, int k) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative");
        }
        if (k == 0) {
            return new PriorityQueue<>();
        }

        // java 默认使用最小堆
        Queue<Integer> heap = new PriorityQueue<>();

        // 先把前k个元素放进最小堆里，当作当前最大的k个元素，其中堆顶是k个元素中的最小值
        int limit = Math.min(k, nums.length);
        for (int i = 0; i < limit; i++) {
            heap.offer(nums[i]);
        }

        // 如果k大于数组长度，直接返回包含所有元素的堆
        if (k >= nums.length) {
            return heap;
        }

        // 处理剩余的元素
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > heap.peek()) {
                heap.poll();
                heap.offer(nums[i]);
            }
        }

        return heap;
    }

    /**
     * 将堆转换为列表形式返回
     *
     * @return 包含堆中所有元素的列表
     */
    public List<Integer> toList() {
        return heap;
    }

    /**
     * 获取左子节点的索引
     *
     * @param i 父节点的索引
     * @return 左子节点的索引
     */
    public static int left(int i) {
        return 2 * i + 1;
    }

    /**
     * 获取右子节点的索引
     *
     * @param i 父节点的索引
     * @return 右子节点的索引
     */
    public static int right(int i) {
        return 2 * i + 2;
    }

    /**
     * 获取父节点的索引
     *
     * @param i 子节点的索引
     * @return 父节点的索引
     */
    public static int parent(int i) {
        // 堆的性质告诉我们怎样让2个数变成1个数
        return (i - 1) / 2;
    }

    /**
     * 查看堆顶元素（最大值）
     *
     * @return 堆顶的最大元素
     * @throws IndexOutOfBoundsException 如果堆为空
     */
    public int peek() {
        if (heap.isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
        return heap.get(0);
    }

    /**
     * 向堆中插入一个新元素
     * <p>
     * 将元素添加到堆的末尾，然后通过上浮操作恢复堆的性质
     *
     * @param val 要插入的整数值
     */
    public void push(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    /**
     * 移除并返回堆顶元素（最大值）
     * <p>
     * 将堆顶元素与最后一个元素交换，移除堆顶，然后通过下沉操作恢复堆的性质
     *
     * @return 堆顶的最大元素
     * @throws IllegalStateException 如果堆为空
     */
    public int pop() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        swap(0, heap.size() - 1);
        int result = heap.remove(heap.size() - 1);
        siftDown(0);

        return result;
    }

    /**
     * 上浮操作，用于恢复堆的性质
     * <p>
     * 从给定的索引开始，向上比较并交换元素，直到满足最大堆性质
     *
     * @param i 开始上浮的索引
     */
    void siftUp(int i) {
        // 要考虑 i 已经是堆顶的情况
        if (i < 0 || i >= heap.size()) {
            return;
        }

        // 如果违反最大堆性质，则交换两个节点，堆操作满足性质以后会自动退出循环
        while (i != 0 && heap.get(i) > heap.get(parent(i))) {
            // i != 0 说明i还有取 parent 的余地
            int p = parent(i);
            swap(i, p);
            i = p; // 最后一步 swap 完会导致 i 变成 0
        }
    }

    /**
     * 下沉操作，用于恢复堆的性质
     * <p>
     * 从给定的索引开始，向下比较并交换元素，直到满足最大堆性质
     *
     * @param i 开始下沉的索引
     */
    void siftDown(int i) {
        if (i < 0 || i >= heap.size()) {
            return;
        }

        int size = heap.size();
        // i往下走走到头就退出循环，或者堆的性质得到满足则退出循环
        while (true) {
            int l = left(i);
            int r = right(i);

            // 注意，不是先跟l比较，l比较大就交换l，最大堆的意思是，i节点的左右子节点中，最大的那个节点跟i交换

            int largest = i;

            // 这里不能直接用 max 来比对，是因为 heap get 取出值以后，要比值，但是 largest 的坐标

            if (l < size && heap.get(l) > heap.get(largest)) {
                largest = l;
            }
            if (r < size && heap.get(r) > heap.get(largest)) {
                largest = r;
            }

            // 不可交换意味着 l r可能都超标，或者都满足性质
            if (largest == i) {
                // 性质已满足，不再下移
                break;
            }

            swap(i, largest);
            i = largest;
        }
    }

    /**
     * 堆排序算法实现（简单版本）
     * <p>
     * 使用最大堆实现排序功能，将输入列表按升序排列。
     * 算法步骤：
     * 1. 使用Floyd建堆算法构建最大堆（时间复杂度O(n)）
     * 2. 重复执行pop操作取出堆顶元素（最大元素），共执行n次
     * 3. 将结果列表反转，得到升序排列的结果
     * <p>
     * 时间复杂度：O(n log n)，其中n是列表长度
     * 空间复杂度：O(n)，需要额外的空间存储结果
     * <p>
     * 注意：这不是最优的堆排序实现，最优版本请参考heapSortOptimal方法
     * <p>
     * 使用示例：
     * <pre>{@code
     * MaxHeap maxHeap = new MaxHeap();
     * List<Integer> input = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
     * List<Integer> sorted = maxHeap.heapSort(input);
     * // 结果: [1, 1, 2, 3, 4, 5, 6, 9]
     * }</pre>
     *
     * @param list 需要排序的整数列表
     * @return 按升序排列的整数列表
     * @throws IllegalArgumentException 如果输入列表为null
     */
    public List<Integer> heapSort(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        MaxHeap maxHeap = new MaxHeap(list);
        List<Integer> result = new ArrayList<>(list.size());
        while (!maxHeap.isEmpty()) {
            result.add(maxHeap.pop());
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * 最优堆排序算法实现（原地排序版本）
     * <p>
     * 这是真正最优的堆排序实现，具有以下特点：
     * 1. 原地排序：空间复杂度O(1)，不需要额外存储空间
     * 2. 时间复杂度：O(n log n)，与简单版本相同但常数因子更小
     * 3. 稳定性能：避免了额外的内存分配和数据复制
     * 4. 代码复用：使用类中已有的parent()方法，保持一致性
     * <p>
     * 算法核心思想：
     * 1. 使用Floyd建堆算法构建最大堆（O(n)时间）
     * 2. 重复执行以下步骤n-1次：
     *    - 将堆顶（最大元素）与堆的最后一个元素交换
     *    - 减小堆的大小（逻辑上移除最大元素）
     *    - 对新的堆顶执行下沉操作恢复堆性质-要实现有界的下沉
     * 3. 排序完成后，数组变为升序排列
     * <p>
     * 为什么这是最优实现？
     * - 空间最优：O(1)额外空间，真正的原地排序
     * - 时间最优：避免了pop操作中的元素移除开销
     * - 缓存友好：所有操作都在原数组上进行，局部性更好
     * - 实用性强：这是工业级堆排序的标准实现
     * - 代码一致：复用类中已有的索引计算方法
     * <p>
     * 使用示例：
     * <pre>{@code
     * List<Integer> data = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
     * MaxHeap.heapSortOptimal(data);
     * // data现在是: [1, 1, 2, 3, 4, 5, 6, 9]
     * }</pre>
     *
     * @param list 需要排序的整数列表（会被原地修改）
     * @throws IllegalArgumentException 如果输入列表为null
     */
    public static void heapSortOptimal(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        
        if (list.size() <= 1) {
            return; // 空列表或单元素列表已经有序
        }
        
        int n = list.size();
        
        // 第一阶段：构建最大堆（Floyd建堆算法）
        // 从最后一个非叶子节点开始，向前遍历所有非叶子节点
        // 使用静态parent()方法计算最后一个非叶子节点索引
        for (int i = parent(n - 1); i >= 0; i--) {
            heapifyDown(list, i, n);
        }
        
        // 第二阶段：排序过程
        // 重复执行：取出最大元素，缩小堆，重新调整
        for (int i = n - 1; i > 0; i--) {
            // 将当前最大元素（堆顶）移到正确位置（数组末尾）
            swap(list, 0, i);
            
            // 缩小堆的大小（逻辑上移除已排序的元素）
            // 对新的堆顶执行下沉操作，恢复堆性质
            heapifyDown(list, 0, i);
        }
    }
    
    /**
     * 堆的下沉操作（用于堆排序的辅助方法）
     * <p>
     * 这是专门为堆排序优化的下沉操作，与实例方法siftDown的区别：
     * 1. 静态方法，不依赖实例状态
     * 2. 支持指定堆的大小（用于排序过程中逐步缩小堆）
     * 3. 直接操作List，避免额外的抽象层开销
     * 4. 使用静态left()和right()方法，保持代码一致性
     * <p>
     * 算法逻辑：
     * 1. 从指定节点开始，找出其与左右子节点中的最大值
     * 2. 如果最大值不是当前节点，则交换并继续下沉
     * 3. 重复直到满足堆性质或到达叶子节点
     *
     * @param list 堆数组
     * @param index 开始下沉的节点索引
     * @param heapSize 当前堆的大小（用于排序过程中的边界控制）
     */
    private static void heapifyDown(List<Integer> list, int index, int heapSize) {
        while (true) {
            int largest = index;
            int leftChild = left(index);   // 使用静态left()方法
            int rightChild = right(index); // 使用静态right()方法
            
            // 找出父节点和左右子节点中的最大值
            if (leftChild < heapSize && list.get(leftChild) > list.get(largest)) {
                largest = leftChild;
            }
            
            if (rightChild < heapSize && list.get(rightChild) > list.get(largest)) {
                largest = rightChild;
            }
            
            // 如果最大值就是当前节点，说明堆性质已满足
            if (largest == index) {
                break;
            }
            
            // 交换当前节点与最大子节点，继续下沉
            swap(list, index, largest);
            index = largest;
        }
    }
    
    /**
     * 交换列表中两个位置的元素（堆排序辅助方法）
     *
     * @param list 列表
     * @param i 第一个元素的索引
     * @param j 第二个元素的索引
     */
    private static void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * 删除堆中任意位置的元素（值替换删除技巧的完美示例）
     * <p>
     * 算法步骤：
     * 1. 用最后一个元素替换要删除的元素
     * 2. 删除最后一个元素
     * 3. 恢复堆性质（关键：可能需要上浮或下沉）
     * <p>
     * 为什么需要同时调用siftUp和siftDown？
     * - 替换元素可能比原元素大（需要上浮）
     * - 替换元素可能比原元素小（需要下沉）  
     * - 替换元素可能正好合适（两个操作都会立即返回）
     * <p>
     * 调用顺序：先siftUp再siftDown
     * - siftUp更简单高效（只与1个父节点比较）
     * - 如果siftUp移动了元素，siftDown会立即返回
     * - 如果siftUp没移动，再检查是否需要siftDown
     *
     * @param index 要删除的元素索引
     * @return 如果删除成功返回true，索引无效返回false
     */
    public boolean removeAt(int index) {
        if (index < 0 || index >= heap.size()) {
            return false;
        }
        
        // 特殊情况：删除最后一个元素，直接移除
        if (index == heap.size() - 1) {
            heap.remove(heap.size() - 1);
            return true;
        }
        
        // 核心技巧：用最后一个元素替换要删除的元素
        int lastElement = heap.get(heap.size() - 1);
        heap.set(index, lastElement);
        heap.remove(heap.size() - 1);
        
        // 恢复堆性质：关键是要同时考虑上浮和下沉的可能性
        // 情况分析：
        // 1. 如果lastElement > 原元素：可能需要上浮
        // 2. 如果lastElement < 原元素：可能需要下沉
        // 3. 如果lastElement = 原元素：可能都不需要（但我们不知道原元素值）
        
        siftUp(index);    // 先尝试上浮（更高效）
        siftDown(index);  // 再尝试下沉（如果上浮成功，这里会立即返回）
        
        return true;
    }
    
    /**
     * 删除堆中第一个匹配的指定值
     * <p>
     * 展示值替换删除技巧在堆中的应用
     *
     * @param val 要删除的值
     * @return 如果删除成功返回true，值不存在返回false
     */
    public boolean remove(int val) {
        // 1. 查找元素位置（O(n)时间复杂度）
        int index = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i) == val) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            return false; // 元素不存在
        }
        
        // 2. 使用removeAt方法删除（展示技巧的复用性）
        return removeAt(index);
    }
    
    /**
     * 优化版本的删除方法（参考Java PriorityQueue的实现）
     * <p>
     * 通过检查元素是否移动来优化调用顺序
     *
     * @param index 要删除的元素索引
     * @return 如果删除成功返回true，索引无效返回false
     */
    public boolean removeAtOptimized(int index) {
        if (index < 0 || index >= heap.size()) {
            return false;
        }
        
        if (index == heap.size() - 1) {
            heap.remove(heap.size() - 1);
            return true;
        }
        
        // 记录替换前的元素，用于优化判断
        int lastElement = heap.get(heap.size() - 1);
        heap.set(index, lastElement);
        heap.remove(heap.size() - 1);
        
        // 优化：先尝试下沉，如果元素没有移动，再尝试上浮
        int originalElement = heap.get(index);
        siftDown(index);
        
        // 如果下沉后元素还在原位置，说明可能需要上浮
        if (heap.get(index) == originalElement) {
            siftUp(index);
        }
        
        return true;
    }

    /**
     * 交换堆中两个位置的元素
     *
     * @param a 第一个元素的索引
     * @param b 第二个元素的索引
     */
    private void swap(int a, int b) {
        Integer tmp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, tmp);
    }
}

