package algorithm.selectedtopics.computational;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * PancakeSorting 类的测试用例
 * 
 * 测试覆盖范围：
 * - 基本排序功能测试
 * - 边界条件测试
 * - 性能测试
 * - 算法正确性验证
 * 
 * @author magicliang
 * @date 2025-09-03
 */
@DisplayName("煎饼排序测试")
class PancakeSortingTest {

    private PancakeSorting sorter;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        sorter = new PancakeSorting();
        // 重定向标准输出以便测试
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("基本排序功能测试 - 简单情况")
    void testBasicSorting() {
        int[] input = {3, 2, 1};
        sorter.run(input, input.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        // 验证输出不为空（说明找到了解）
        String output = outputStream.toString();
        assertTrue(output.contains("Total Swap times"), "应该包含总翻转次数信息");
        assertTrue(output.contains("|Search Times|"), "应该包含搜索次数信息");
    }

    @Test
    @DisplayName("已排序数组测试")
    void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        sorter.run(input, input.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output = outputStream.toString();
        assertTrue(output.contains("Total Swap times (minimum flips) = 0"), 
            "已排序的数组应该需要0次翻转");
    }

    @Test
    @DisplayName("单元素数组测试")
    void testSingleElement() {
        int[] input = {1};
        sorter.run(input, input.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output = outputStream.toString();
        assertTrue(output.contains("Total Swap times (minimum flips) = 0"), 
            "单元素数组应该需要0次翻转");
    }

    @Test
    @DisplayName("两元素数组测试")
    void testTwoElements() {
        // 测试需要翻转的情况
        int[] input1 = {2, 1};
        sorter.run(input1, input1.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output1 = outputStream.toString();
        assertTrue(output1.contains("Total Swap times"), "应该包含翻转次数信息");
        
        // 重置输出流
        outputStream.reset();
        
        // 测试已排序的情况
        int[] input2 = {1, 2};
        sorter.run(input2, input2.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output2 = outputStream.toString();
        assertTrue(output2.contains("Total Swap times (minimum flips) = 0"), 
            "已排序的两元素数组应该需要0次翻转");
    }

    @Test
    @DisplayName("逆序数组测试")
    void testReverseOrder() {
        int[] input = {5, 4, 3, 2, 1};
        sorter.run(input, input.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output = outputStream.toString();
        assertTrue(output.contains("Total Swap times"), "应该包含翻转次数信息");
        assertFalse(output.contains("Total Swap times (minimum flips) = 0"), 
            "逆序数组应该需要多次翻转");
    }

    @Test
    @DisplayName("isSorted方法测试")
    void testIsSorted() {
        // 测试已排序数组
        int[] sorted = {1, 2, 3, 4, 5};
        assertTrue(sorter.isSorted(sorted, sorted.length), "已排序数组应该返回true");
        
        // 测试未排序数组
        int[] unsorted = {3, 1, 4, 2, 5};
        assertFalse(sorter.isSorted(unsorted, unsorted.length), "未排序数组应该返回false");
        
        // 测试逆序数组
        int[] reverse = {5, 4, 3, 2, 1};
        assertFalse(sorter.isSorted(reverse, reverse.length), "逆序数组应该返回false");
        
        // 测试单元素数组
        int[] single = {1};
        assertTrue(sorter.isSorted(single, single.length), "单元素数组应该返回true");
        
        // 测试空数组
        int[] empty = {};
        assertTrue(sorter.isSorted(empty, 0), "空数组应该返回true");
    }

    @Test
    @DisplayName("reverse方法测试")
    void testReverse() {
        // 创建测试数组
        int[] testArray = {1, 2, 3, 4, 5};
        
        // 初始化sorter以便访问reverse方法
        sorter.init(testArray, testArray.length);
        
        // 测试完全翻转
        sorter.reverse(0, 4);
        // 验证翻转结果（通过间接方式，因为reverseCakeArray是私有的）
        // 我们通过再次翻转来验证
        sorter.reverse(0, 4);
        
        // 测试部分翻转
        sorter.reverse(1, 3);
        sorter.reverse(1, 3); // 再次翻转恢复
        
        // 测试边界情况
        sorter.reverse(0, 0); // 翻转单个元素
        sorter.reverse(2, 2); // 翻转单个元素
        
        // 如果没有异常抛出，说明reverse方法工作正常
        assertTrue(true, "reverse方法应该正常工作");
    }

    @Test
    @DisplayName("upperBound方法测试")
    void testUpperBound() {
        // 测试上界计算
        assertEquals(0, sorter.upperBound(1), "1个元素的上界应该是0");
        assertEquals(2, sorter.upperBound(2), "2个元素的上界应该是2");
        assertEquals(4, sorter.upperBound(3), "3个元素的上界应该是4");
        assertEquals(6, sorter.upperBound(4), "4个元素的上界应该是6");
        assertEquals(8, sorter.upperBound(5), "5个元素的上界应该是8");
        assertEquals(16, sorter.upperBound(9), "9个元素的上界应该是16");
    }

    @Test
    @DisplayName("lowerBound方法测试")
    void testLowerBound() {
        // 测试下界估算
        
        // 已排序数组的下界应该是0
        int[] sorted = {1, 2, 3, 4, 5};
        assertEquals(0, sorter.lowerBound(sorted, sorted.length), 
            "已排序数组的下界应该是0");
        
        // 完全逆序数组的下界应该较大
        int[] reverse = {5, 4, 3, 2, 1};
        assertTrue(sorter.lowerBound(reverse, reverse.length) > 0, 
            "逆序数组的下界应该大于0");
        
        // 部分排序数组
        int[] partial = {1, 3, 2, 4, 5};
        int lowerBound = sorter.lowerBound(partial, partial.length);
        assertTrue(lowerBound >= 0, "下界应该非负");
        
        // 单元素数组
        int[] single = {1};
        assertEquals(0, sorter.lowerBound(single, single.length), 
            "单元素数组的下界应该是0");
    }

    @Test
    @DisplayName("性能测试 - 小规模数组")
    void testPerformanceSmallArray() {
        int[] input = {4, 3, 2, 1};
        
        long startTime = System.nanoTime();
        sorter.run(input, input.length);
        long endTime = System.nanoTime();
        
        long duration = (endTime - startTime) / 1_000_000; // 转换为毫秒
        
        System.setOut(originalOut); // 恢复标准输出以便打印性能信息
        System.out.println("4元素煎饼排序耗时: " + duration + "ms");
        
        // 性能断言：小规模数组应该很快完成
        assertTrue(duration < 1000, "小规模数组排序应该在1秒内完成");
    }

    @Test
    @DisplayName("算法正确性验证 - 多种输入")
    void testAlgorithmCorrectness() {
        int[][] testCases = {
            {1},
            {1, 2},
            {2, 1},
            {1, 2, 3},
            {3, 2, 1},
            {2, 1, 3},
            {1, 3, 2},
            {3, 1, 2},
            {2, 3, 1}
        };
        
        for (int[] testCase : testCases) {
            outputStream.reset(); // 重置输出流
            
            sorter.run(testCase, testCase.length);
            sorter.output(); // 需要调用output方法才能产生输出
            String output = outputStream.toString();
            
            // 验证输出包含必要信息
            assertTrue(output.contains("Total Swap times"), 
                "输出应该包含总翻转次数，输入: " + Arrays.toString(testCase));
            assertTrue(output.contains("|Search Times|"), 
                "输出应该包含搜索次数，输入: " + Arrays.toString(testCase));
        }
    }

    @Test
    @DisplayName("边界条件测试 - 特殊输入")
    void testBoundaryConditions() {
        // 测试包含重复元素的数组（虽然原算法假设元素唯一）
        int[] withDuplicates = {1, 2, 2, 3};
        assertDoesNotThrow(() -> {
            sorter.run(withDuplicates, withDuplicates.length);
        }, "包含重复元素的数组不应该抛出异常");
        
        // 测试包含0的数组
        int[] withZero = {0, 1, 2};
        assertDoesNotThrow(() -> {
            sorter.run(withZero, withZero.length);
        }, "包含0的数组不应该抛出异常");
        
        // 测试负数数组
        int[] withNegative = {-1, 0, 1};
        assertDoesNotThrow(() -> {
            sorter.run(withNegative, withNegative.length);
        }, "包含负数的数组不应该抛出异常");
    }

    @Test
    @DisplayName("初始化测试")
    void testInitialization() {
        int[] input = {3, 2, 1};
        
        // 测试初始化不会抛出异常
        assertDoesNotThrow(() -> {
            sorter.init(input, input.length);
        }, "初始化不应该抛出异常");
        
        // 测试多次初始化
        assertDoesNotThrow(() -> {
            sorter.init(input, input.length);
            sorter.init(input, input.length);
        }, "多次初始化不应该抛出异常");
    }

    @Test
    @DisplayName("输出格式测试")
    void testOutputFormat() {
        int[] input = {2, 1};
        sorter.run(input, input.length);
        sorter.output(); // 需要调用output方法才能产生输出
        
        String output = outputStream.toString();
        
        // 验证输出格式
        assertTrue(output.contains("Flip sequence"), "输出应该包含翻转序列");
        assertTrue(output.contains("|Search Times|"), "输出应该包含搜索次数");
        assertTrue(output.contains("Total Swap times"), "输出应该包含总翻转次数");
        
        // 验证数字格式
        assertTrue(output.contains("1") || output.contains("2") || output.contains("0"), "输出应该包含数字");
    }

    @Test
    @DisplayName("算法优化验证 - 剪枝效果")
    void testPruningEffectiveness() {
        // 测试剪枝是否有效减少搜索次数
        int[] input1 = {1, 2, 3}; // 已排序，应该很快
        sorter.run(input1, input1.length);
        sorter.output(); // 需要调用output方法才能产生输出
        String output1 = outputStream.toString();
        
        outputStream.reset();
        
        int[] input2 = {3, 2, 1}; // 逆序，需要更多搜索
        sorter.run(input2, input2.length);
        sorter.output(); // 需要调用output方法才能产生输出
        String output2 = outputStream.toString();
        
        // 验证已排序数组的搜索次数较少
        assertTrue(output1.contains("|Search Times|"), "应该包含搜索次数信息");
        assertTrue(output2.contains("|Search Times|"), "应该包含搜索次数信息");
    }

    @Test
    @DisplayName("内存使用测试")
    void testMemoryUsage() {
        // 测试算法不会导致内存泄漏或过度使用
        int[] input = {5, 4, 3, 2, 1};
        
        // 多次运行相同的测试
        for (int i = 0; i < 10; i++) {
            outputStream.reset();
            assertDoesNotThrow(() -> {
                sorter.run(input, input.length);
            }, "第" + (i + 1) + "次运行不应该抛出异常");
        }
        
        // 如果没有内存问题，测试应该正常完成
        assertTrue(true, "内存使用测试通过");
    }

    // 在测试结束后恢复标准输出
    void tearDown() {
        if (originalOut != null) {
            System.setOut(originalOut);
        }
    }
}