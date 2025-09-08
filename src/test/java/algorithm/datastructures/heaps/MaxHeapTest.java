package algorithm.datastructures.heaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * MaxHeap类的单元测试类
 * <p>
 * 测试覆盖范围：
 * - 构造函数测试
 * - 基本堆操作测试（push, pop, peek）
 * - 合并操作测试
 * - TopK算法测试
 * - 边界条件测试
 * - 异常情况测试
 */
@DisplayName("MaxHeap单元测试")
class MaxHeapTest {

    private MaxHeap emptyHeap;
    private MaxHeap singleElementHeap;
    private MaxHeap multiElementHeap;

    @BeforeEach
    void setUp() {
        emptyHeap = new MaxHeap();

        singleElementHeap = new MaxHeap();
        singleElementHeap.push(42);

        multiElementHeap = new MaxHeap();
        int[] values = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        for (int val : values) {
            multiElementHeap.push(val);
        }
    }

    // ==================== 构造函数测试 ====================

    @Test
    @DisplayName("测试默认构造函数创建空堆")
    void testDefaultConstructor() {
        MaxHeap heap = new MaxHeap();
        assertTrue(heap.toList().isEmpty());
    }

    @Test
    @DisplayName("测试列表构造函数创建堆")
    void testListConstructor() {
        List<Integer> values = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        MaxHeap heap = new MaxHeap(values);

        assertEquals(8, heap.toList().size());
        assertEquals(9, heap.peek()); // 最大堆的堆顶应该是最大值
    }

    @Test
    @DisplayName("测试空列表构造函数")
    void testEmptyListConstructor() {
        List<Integer> emptyList = new ArrayList<>();
        MaxHeap heap = new MaxHeap(emptyList);

        assertTrue(heap.toList().isEmpty());
    }

    // ==================== 基本堆操作测试 ====================

    @Test
    @DisplayName("测试push操作")
    void testPush() {
        emptyHeap.push(10);
        assertEquals(1, emptyHeap.toList().size());
        assertEquals(10, emptyHeap.peek());

        emptyHeap.push(20);
        assertEquals(2, emptyHeap.toList().size());
        assertEquals(20, emptyHeap.peek());

        emptyHeap.push(5);
        assertEquals(3, emptyHeap.toList().size());
        assertEquals(20, emptyHeap.peek()); // 最大值仍然是20
    }

    @Test
    @DisplayName("测试pop操作")
    void testPop() {
        assertEquals(9, multiElementHeap.pop());
        assertEquals(6, multiElementHeap.pop());
        assertEquals(5, multiElementHeap.pop());
    }

    @Test
    @DisplayName("测试空堆pop抛出异常")
    void testPopEmptyHeap() {
        assertThrows(IllegalStateException.class, () -> emptyHeap.pop());
    }

    @Test
    @DisplayName("测试peek操作")
    void testPeek() {
        assertEquals(9, multiElementHeap.peek());

        MaxHeap heap = new MaxHeap();
        heap.push(100);
        assertEquals(100, heap.peek());
    }

    @Test
    @DisplayName("测试空堆peek抛出异常")
    void testPeekEmptyHeap() {
        assertThrows(IndexOutOfBoundsException.class, () -> emptyHeap.peek());
    }

    // ==================== 工具方法测试 ====================

    @Test
    @DisplayName("测试left方法")
    void testLeft() {
        assertEquals(1, emptyHeap.left(0));
        assertEquals(3, emptyHeap.left(1));
        assertEquals(5, emptyHeap.left(2));
    }

    @Test
    @DisplayName("测试right方法")
    void testRight() {
        assertEquals(2, emptyHeap.right(0));
        assertEquals(4, emptyHeap.right(1));
        assertEquals(6, emptyHeap.right(2));
    }

    @Test
    @DisplayName("测试parent方法")
    void testParent() {
        assertEquals(0, emptyHeap.parent(1));
        assertEquals(0, emptyHeap.parent(2));
        assertEquals(1, emptyHeap.parent(3));
        assertEquals(1, emptyHeap.parent(4));
    }

    @Test
    @DisplayName("测试toList方法")
    void testToList() {
        List<Integer> list = multiElementHeap.toList();
        assertNotNull(list);
        assertEquals(10, list.size());

        // 验证列表包含所有插入的元素
        assertTrue(list.contains(1));
        assertTrue(list.contains(9));
        assertTrue(list.contains(5));
    }

    // ==================== 合并操作测试 ====================

    @Test
    @DisplayName("测试合并两个非空堆")
    void testMergeHeap() {
        MaxHeap heap1 = new MaxHeap(Arrays.asList(1, 3, 5));
        MaxHeap heap2 = new MaxHeap(Arrays.asList(2, 4, 6));

        MaxHeap merged = heap1.mergeHeap(heap2);

        assertEquals(6, merged.toList().size());
        assertEquals(6, merged.peek());
        assertSame(heap1, merged); // 验证返回的是当前堆实例
    }

    @Test
    @DisplayName("测试合并空堆")
    void testMergeEmptyHeap() {
        MaxHeap heap1 = new MaxHeap(Arrays.asList(1, 2, 3));
        MaxHeap empty = new MaxHeap();

        MaxHeap merged = heap1.mergeHeap(empty);

        assertEquals(3, merged.toList().size());
        assertEquals(3, merged.peek());
    }

    @Test
    @DisplayName("测试合并null堆抛出异常")
    void testMergeNullHeap() {
        assertThrows(IllegalArgumentException.class, () -> multiElementHeap.mergeHeap(null));
    }

    // ==================== TopK算法测试 ====================

    @Test
    @DisplayName("测试traversalTopK正常情况")
    void testTraversalTopK() {
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        List<Integer> result = MaxHeap.traversalTopK(nums, 3);

        assertEquals(3, result.size());
        assertEquals(9, result.get(0));
        assertEquals(6, result.get(1));
        assertEquals(5, result.get(2));
    }

    @Test
    @DisplayName("测试traversalTopK边界条件")
    void testTraversalTopKBoundary() {
        List<Integer> nums = Arrays.asList(1, 2, 3);

        assertTrue(MaxHeap.traversalTopK(nums, 0).isEmpty());
        assertEquals(3, MaxHeap.traversalTopK(nums, 5).size());
        assertTrue(MaxHeap.traversalTopK(new ArrayList<>(), 3).isEmpty());
    }

    @Test
    @DisplayName("测试sortTopK正常情况")
    void testSortTopK() {
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        List<Integer> result = MaxHeap.sortTopK(nums, 3);

        assertEquals(3, result.size());
        assertEquals(9, result.get(2));
        assertEquals(6, result.get(1));
        assertEquals(5, result.get(0));
    }

    @Test
    @DisplayName("测试sortTopK边界条件")
    void testSortTopKBoundary() {
        List<Integer> nums = Arrays.asList(1, 2, 3);

        assertTrue(MaxHeap.sortTopK(nums, 0).isEmpty());
        assertEquals(3, MaxHeap.sortTopK(nums, 5).size());
        assertTrue(MaxHeap.sortTopK(new ArrayList<>(), 3).isEmpty());
    }

    @Test
    @DisplayName("测试heapMinK正常情况")
    void testHeapMinK() {
        List<Integer> nums = Arrays.asList(7, 10, 4, 3, 20, 15);
        List<Integer> result = MaxHeap.heapMinK(nums, 3);

        assertEquals(3, result.size());
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.contains(7));
    }

    @Test
    @DisplayName("测试heapMinK边界条件")
    void testHeapMinKBoundary() {
        List<Integer> nums = Arrays.asList(1, 2, 3);

        assertTrue(MaxHeap.heapMinK(nums, 0).isEmpty());
        assertEquals(3, MaxHeap.heapMinK(nums, 5).size());
        assertTrue(MaxHeap.heapMinK(new ArrayList<>(), 3).isEmpty());
        assertTrue(MaxHeap.heapMinK(null, 3).isEmpty());
    }

    @Test
    @DisplayName("测试minKWithPriorityQueue正常情况")
    void testMinKWithPriorityQueue() {
        int[] nums = {7, 10, 4, 3, 20, 15};
        Queue<Integer> result = MaxHeap.minKWithPriorityQueue(nums, 3);

        assertEquals(3, result.size());
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.contains(7));
    }

    @Test
    @DisplayName("测试minKWithPriorityQueue边界条件")
    void testMinKWithPriorityQueueBoundary() {
        int[] nums = {1, 2, 3};

        assertEquals(0, MaxHeap.minKWithPriorityQueue(nums, 0).size());
        assertEquals(3, MaxHeap.minKWithPriorityQueue(nums, 5).size());
        assertEquals(0, MaxHeap.minKWithPriorityQueue(new int[]{}, 3).size());

        assertThrows(IllegalArgumentException.class, () -> MaxHeap.minKWithPriorityQueue(null, 3));
        assertThrows(IllegalArgumentException.class, () -> MaxHeap.minKWithPriorityQueue(nums, -1));
    }

    @Test
    @DisplayName("测试topK正常情况")
    void testTopK() {
        int[] nums = {7, 10, 4, 3, 20, 15};
        Queue<Integer> result = multiElementHeap.topK(nums, 3);

        assertEquals(3, result.size());
        assertTrue(result.contains(20));
        assertTrue(result.contains(15));
        assertTrue(result.contains(10));
    }

    @Test
    @DisplayName("测试topK边界条件")
    void testTopKBoundary() {
        int[] nums = {1, 2, 3};

        assertEquals(0, multiElementHeap.topK(nums, 0).size());
        assertEquals(3, multiElementHeap.topK(nums, 5).size());
        assertEquals(0, multiElementHeap.topK(new int[]{}, 3).size());

        assertThrows(IllegalArgumentException.class, () -> multiElementHeap.topK(null, 3));
        assertThrows(IllegalArgumentException.class, () -> multiElementHeap.topK(nums, -1));
    }

    // ==================== 性能测试 ====================

    @Test
    @DisplayName("测试大数组操作")
    void testLargeArrayOperations() {
        MaxHeap heap = new MaxHeap();
        int size = 10000;

        // 测试大量插入
        for (int i = 0; i < size; i++) {
            heap.push(i);
        }
        assertEquals(size, heap.toList().size());
        assertEquals(size - 1, heap.peek());

        // 测试大量删除
        for (int i = 0; i < 1000; i++) {
            heap.pop();
        }
        assertEquals(size - 1000, heap.toList().size());
    }

    // ==================== 堆性质验证测试 ====================

    @Test
    @DisplayName("验证最大堆性质")
    void testMaxHeapProperty() {
        MaxHeap heap = new MaxHeap(Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8));

        List<Integer> list = heap.toList();
        for (int i = 0; i < list.size(); i++) {
            int left = heap.left(i);
            int right = heap.right(i);

            if (left < list.size()) {
                assertTrue(list.get(i) >= list.get(left));
            }
            if (right < list.size()) {
                assertTrue(list.get(i) >= list.get(right));
            }
        }
    }

    // ==================== 堆排序测试 ====================

    @Test
    @DisplayName("测试堆排序 - 正常情况")
    void testHeapSort_NormalCase() {
        List<Integer> input = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        List<Integer> expected = Arrays.asList(1, 1, 2, 3, 4, 5, 6, 9);

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(expected, result, "堆排序结果应该与预期升序排列一致");
    }

    @Test
    @DisplayName("测试堆排序 - 空列表")
    void testHeapSort_EmptyList() {
        List<Integer> input = Collections.emptyList();
        List<Integer> result = emptyHeap.heapSort(input);

        assertTrue(result.isEmpty(), "空列表的排序结果应该是空列表");
    }

    @Test
    @DisplayName("测试堆排序 - 单元素列表")
    void testHeapSort_SingleElement() {
        List<Integer> input = Collections.singletonList(42);
        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(1, result.size(), "单元素列表排序后应该只有一个元素");
        assertEquals(42, result.get(0), "单元素列表排序后元素值应该保持不变");
    }

    @Test
    @DisplayName("测试堆排序 - 已排序列表")
    void testHeapSort_AlreadySorted() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(expected, result, "已排序列表的排序结果应该保持不变");
    }

    @Test
    @DisplayName("测试堆排序 - 逆序列表")
    void testHeapSort_ReverseSorted() {
        List<Integer> input = Arrays.asList(5, 4, 3, 2, 1);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(expected, result, "逆序列表应该被正确排序为升序");
    }

    @Test
    @DisplayName("测试堆排序 - 包含重复元素")
    void testHeapSort_WithDuplicates() {
        List<Integer> input = Arrays.asList(3, 3, 3, 1, 1, 2, 2);
        List<Integer> expected = Arrays.asList(1, 1, 2, 2, 3, 3, 3);

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(expected, result, "包含重复元素的列表应该被正确排序");
    }

    @Test
    @DisplayName("测试堆排序 - 包含负数")
    void testHeapSort_WithNegativeNumbers() {
        List<Integer> input = Arrays.asList(-3, 5, -1, 0, 2, -8);
        List<Integer> expected = Arrays.asList(-8, -3, -1, 0, 2, 5);

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(expected, result, "包含负数的列表应该被正确排序");
    }

    @Test
    @DisplayName("测试堆排序 - 大列表")
    void testHeapSort_LargeList() {
        List<Integer> input = new ArrayList<>();
        for (int i = 1000; i >= 0; i--) {
            input.add(i);
        }

        List<Integer> result = emptyHeap.heapSort(input);

        assertEquals(1001, result.size(), "大列表排序后元素数量应该保持不变");

        // 验证排序结果
        for (int i = 0; i < result.size(); i++) {
            assertEquals(i, result.get(i), "大列表应该被正确排序");
        }
    }

    @Test
    @DisplayName("测试堆排序 - null输入应该抛出异常")
    void testHeapSort_NullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> emptyHeap.heapSort(null),
                "null输入应该抛出IllegalArgumentException");
    }

    @Test
    @DisplayName("测试堆排序 - 性能测试")
    void testHeapSort_Performance() {
        // 测试中等规模数据的性能
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add((int) (Math.random() * 10000));
        }

        long startTime = System.currentTimeMillis();
        List<Integer> result = emptyHeap.heapSort(input);
        long endTime = System.currentTimeMillis();

        // 验证排序正确性
        assertEquals(10000, result.size());
        for (int i = 1; i < result.size(); i++) {
            assertTrue(result.get(i) >= result.get(i - 1),
                    "结果应该是升序排列");
        }

        // 性能应该在合理范围内（1万条数据应该小于1秒）
        assertTrue(endTime - startTime < 1000,
                "排序1万条数据应该在1秒内完成");
    }

    @Test
    @DisplayName("测试堆基本操作 - push和pop")
    void testBasicOperations() {
        emptyHeap.push(5);
        emptyHeap.push(3);
        emptyHeap.push(7);
        emptyHeap.push(1);

        assertEquals(7, emptyHeap.pop(), "应该弹出最大值7");
        assertEquals(5, emptyHeap.pop(), "应该弹出次大值5");
        assertEquals(3, emptyHeap.pop(), "应该弹出3");
        assertEquals(1, emptyHeap.pop(), "应该弹出1");
    }

    @Test
    @DisplayName("测试堆基本操作 - isEmpty")
    void testIsEmpty() {
        assertTrue(emptyHeap.isEmpty(), "新创建的堆应该是空的");

        emptyHeap.push(1);
        assertFalse(emptyHeap.isEmpty(), "添加元素后堆不应该为空");

        emptyHeap.pop();
        assertTrue(emptyHeap.isEmpty(), "移除所有元素后堆应该为空");
    }

    // ==================== 删除操作测试 ====================

    @Test
    @DisplayName("测试removeAt - 删除堆顶元素")
    void testRemoveAt_RemoveRoot() {
        // 构建测试堆: [9, 6, 5, 3, 5, 4, 2, 1, 1, 3]
        MaxHeap heap = new MaxHeap(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3));
        int originalSize = heap.size();
        int maxValue = heap.peek();

        // 删除堆顶元素（索引0）
        assertTrue(heap.removeAt(0), "删除堆顶应该成功");
        assertEquals(originalSize - 1, heap.size(), "删除后堆大小应该减1");
        
        // 验证新的堆顶不是原来的最大值
        assertTrue(heap.peek() < maxValue, "新堆顶应该小于原来的最大值");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAt - 删除叶子节点")
    void testRemoveAt_RemoveLeaf() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6, 1, 2, 3));
        int originalSize = heap.size();
        
        // 删除最后一个叶子节点（索引9）
        assertTrue(heap.removeAt(9), "删除叶子节点应该成功");
        assertEquals(originalSize - 1, heap.size(), "删除后堆大小应该减1");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAt - 删除中间节点")
    void testRemoveAt_RemoveMiddleNode() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6, 1, 2, 3));
        int originalSize = heap.size();
        
        // 删除中间节点（索引2，值为9）
        assertTrue(heap.removeAt(2), "删除中间节点应该成功");
        assertEquals(originalSize - 1, heap.size(), "删除后堆大小应该减1");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAt - 删除最后一个元素")
    void testRemoveAt_RemoveLastElement() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 5, 8));
        
        // 删除最后一个元素（索引2）
        assertTrue(heap.removeAt(2), "删除最后一个元素应该成功");
        assertEquals(2, heap.size(), "删除后堆大小应该为2");
        assertEquals(10, heap.peek(), "堆顶应该保持不变");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAt - 删除单元素堆")
    void testRemoveAt_RemoveSingleElement() {
        MaxHeap heap = new MaxHeap();
        heap.push(42);
        
        assertTrue(heap.removeAt(0), "删除单元素应该成功");
        assertTrue(heap.isEmpty(), "删除后堆应该为空");
    }

    @Test
    @DisplayName("测试removeAt - 无效索引")
    void testRemoveAt_InvalidIndex() {
        MaxHeap heap = new MaxHeap(Arrays.asList(1, 2, 3));
        
        // 测试负索引
        assertFalse(heap.removeAt(-1), "负索引应该返回false");
        
        // 测试超出范围的索引
        assertFalse(heap.removeAt(3), "超出范围的索引应该返回false");
        assertFalse(heap.removeAt(10), "超出范围的索引应该返回false");
        
        // 测试空堆
        MaxHeap emptyHeap = new MaxHeap();
        assertFalse(emptyHeap.removeAt(0), "空堆删除应该返回false");
        
        // 验证原堆未被修改
        assertEquals(3, heap.size(), "无效删除不应该修改堆大小");
    }

    @Test
    @DisplayName("测试removeAt - 需要上浮的情况")
    void testRemoveAt_RequiresSiftUp() {
        // 构造一个特殊的堆，删除某个节点后需要上浮
        MaxHeap heap = new MaxHeap();
        heap.push(10);  // 根节点
        heap.push(5);   // 左子节点
        heap.push(8);   // 右子节点
        heap.push(3);   // 左子节点的左子节点
        heap.push(4);   // 左子节点的右子节点
        heap.push(6);   // 右子节点的左子节点
        heap.push(7);   // 右子节点的右子节点
        heap.push(20);  // 最后添加一个大值，会成为叶子节点
        
        // 删除索引1的节点（值为5），用最后的元素（20）替换
        // 这会导致需要上浮操作
        assertTrue(heap.removeAt(1), "删除应该成功");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAt - 需要下沉的情况")
    void testRemoveAt_RequiresSiftDown() {
        // 构造一个堆，删除某个节点后需要下沉
        MaxHeap heap = new MaxHeap(Arrays.asList(20, 15, 18, 10, 12, 16, 17, 5, 8, 1));
        
        // 删除根节点，用最后的元素（1）替换
        // 这会导致需要下沉操作
        assertTrue(heap.removeAt(0), "删除根节点应该成功");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试remove - 删除存在的值")
    void testRemove_ExistingValue() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6, 1, 2, 3));
        int originalSize = heap.size();
        
        // 删除存在的值
        assertTrue(heap.remove(7), "删除存在的值应该成功");
        assertEquals(originalSize - 1, heap.size(), "删除后堆大小应该减1");
        
        // 验证值确实被删除
        assertFalse(heap.toList().contains(7), "删除的值不应该再存在于堆中");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试remove - 删除不存在的值")
    void testRemove_NonExistingValue() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6));
        int originalSize = heap.size();
        
        // 删除不存在的值
        assertFalse(heap.remove(100), "删除不存在的值应该返回false");
        assertEquals(originalSize, heap.size(), "删除失败后堆大小应该保持不变");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试remove - 删除重复值")
    void testRemove_DuplicateValues() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 5, 8, 5, 3, 5, 6));
        
        // 删除重复值（应该只删除第一个找到的）
        assertTrue(heap.remove(5), "删除重复值应该成功");
        assertEquals(6, heap.size(), "删除后堆大小应该减1");
        
        // 验证还有其他5存在
        assertTrue(heap.toList().contains(5), "其他重复值应该仍然存在");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试remove - 删除堆顶值")
    void testRemove_RemoveMaxValue() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6));
        int maxValue = heap.peek();
        
        assertTrue(heap.remove(maxValue), "删除堆顶值应该成功");
        assertTrue(heap.peek() < maxValue, "新堆顶应该小于原来的最大值");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试remove - 空堆删除")
    void testRemove_EmptyHeap() {
        MaxHeap heap = new MaxHeap();
        
        assertFalse(heap.remove(1), "空堆删除应该返回false");
        assertTrue(heap.isEmpty(), "空堆应该保持为空");
    }

    @Test
    @DisplayName("测试removeAtOptimized - 基本功能")
    void testRemoveAtOptimized_BasicFunctionality() {
        MaxHeap heap = new MaxHeap(Arrays.asList(10, 8, 9, 4, 7, 5, 6, 1, 2, 3));
        int originalSize = heap.size();
        
        // 删除中间节点
        assertTrue(heap.removeAtOptimized(2), "优化删除应该成功");
        assertEquals(originalSize - 1, heap.size(), "删除后堆大小应该减1");
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试removeAtOptimized - 与removeAt结果一致性")
    void testRemoveAtOptimized_ConsistencyWithRemoveAt() {
        // 创建两个相同的堆
        List<Integer> values = Arrays.asList(15, 10, 20, 8, 12, 16, 25, 6, 9, 11, 13, 17, 22, 30);
        MaxHeap heap1 = new MaxHeap(new ArrayList<>(values));
        MaxHeap heap2 = new MaxHeap(new ArrayList<>(values));
        
        // 使用不同方法删除相同位置的元素
        int indexToRemove = 3;
        heap1.removeAt(indexToRemove);
        heap2.removeAtOptimized(indexToRemove);
        
        // 验证两个堆的大小相同
        assertEquals(heap1.size(), heap2.size(), "两种删除方法后堆大小应该相同");
        
        // 验证两个堆都满足堆性质
        verifyMaxHeapProperty(heap1);
        verifyMaxHeapProperty(heap2);
        
        // 验证两个堆的堆顶相同（可能的最大值应该相同）
        assertEquals(heap1.peek(), heap2.peek(), "两种删除方法后堆顶应该相同");
    }

    @Test
    @DisplayName("测试removeAtOptimized - 无效索引")
    void testRemoveAtOptimized_InvalidIndex() {
        MaxHeap heap = new MaxHeap(Arrays.asList(1, 2, 3));
        
        // 测试无效索引
        assertFalse(heap.removeAtOptimized(-1), "负索引应该返回false");
        assertFalse(heap.removeAtOptimized(3), "超出范围的索引应该返回false");
        
        // 验证堆未被修改
        assertEquals(3, heap.size(), "无效删除不应该修改堆大小");
    }

    @Test
    @DisplayName("测试删除操作的性能")
    void testDeletionPerformance() {
        // 创建大堆进行性能测试
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            values.add((int) (Math.random() * 10000));
        }
        
        MaxHeap heap = new MaxHeap(values);
        
        long startTime = System.currentTimeMillis();
        
        // 删除1000个元素
        for (int i = 0; i < 1000; i++) {
            if (!heap.isEmpty()) {
                heap.removeAt(0); // 总是删除堆顶
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        // 验证删除后的堆仍然满足性质
        verifyMaxHeapProperty(heap);
        
        // 性能应该在合理范围内
        assertTrue(endTime - startTime < 1000, "删除1000个元素应该在1秒内完成");
    }

    @Test
    @DisplayName("测试连续删除操作")
    void testConsecutiveDeletions() {
        MaxHeap heap = new MaxHeap(Arrays.asList(20, 15, 18, 10, 12, 16, 17, 5, 8, 9, 11, 13, 14));
        
        // 连续删除多个元素
        while (heap.size() > 3) {
            int sizeBeforeRemoval = heap.size();
            assertTrue(heap.removeAt(0), "删除应该成功");
            assertEquals(sizeBeforeRemoval - 1, heap.size(), "每次删除后大小应该减1");
            verifyMaxHeapProperty(heap);
        }
        
        // 验证最终状态
        assertEquals(3, heap.size(), "最终应该剩余3个元素");
        verifyMaxHeapProperty(heap);
    }

    @Test
    @DisplayName("测试删除后的堆重建")
    void testHeapReconstructionAfterDeletion() {
        MaxHeap heap = new MaxHeap(Arrays.asList(100, 90, 80, 70, 60, 50, 40, 30, 20, 10));
        
        // 删除几个元素
        heap.removeAt(2); // 删除80
        heap.removeAt(1); // 删除90（现在的索引1）
        heap.remove(60);  // 删除60
        
        // 验证堆性质
        verifyMaxHeapProperty(heap);
        
        // 添加新元素，验证堆仍然正常工作
        heap.push(95);
        heap.push(85);
        
        verifyMaxHeapProperty(heap);
        
        // 验证新添加的元素能正确排序
        assertTrue(heap.peek() >= 95, "堆顶应该是最大值");
    }

    /**
     * 验证最大堆性质的辅助方法
     * 确保每个父节点都大于等于其子节点
     */
    private void verifyMaxHeapProperty(MaxHeap heap) {
        List<Integer> list = heap.toList();
        for (int i = 0; i < list.size(); i++) {
            int left = heap.left(i);
            int right = heap.right(i);
            
            if (left < list.size()) {
                assertTrue(list.get(i) >= list.get(left), 
                    String.format("父节点 %d (索引%d) 应该 >= 左子节点 %d (索引%d)", 
                        list.get(i), i, list.get(left), left));
            }
            if (right < list.size()) {
                assertTrue(list.get(i) >= list.get(right), 
                    String.format("父节点 %d (索引%d) 应该 >= 右子节点 %d (索引%d)", 
                        list.get(i), i, list.get(right), right));
            }
        }
    }
}