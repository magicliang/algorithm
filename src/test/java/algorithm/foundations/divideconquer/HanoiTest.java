package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Hanoi 汉诺塔算法的 JUnit 测试类。
 * 
 * 测试内容包括：
 * 1. 基本功能测试
 * 2. 边界情况测试
 * 3. 递归深度测试
 * 4. 算法正确性验证
 *
 * @author magicliang
 * @date 2025-09-09
 */
@DisplayName("汉诺塔算法测试")
class HanoiTest {

    private Hanoi hanoi;

    @BeforeEach
    void setUp() {
        hanoi = new Hanoi();
    }

    @Test
    @DisplayName("基本汉诺塔测试 - 1个盘子")
    void testHanoiWithOneDisk() {
        List<Integer> A = new ArrayList<>(Arrays.asList(1));
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(Arrays.asList(1), C, "目标柱子应该包含盘子1");
    }

    @Test
    @DisplayName("基本汉诺塔测试 - 2个盘子")
    void testHanoiWithTwoDisks() {
        List<Integer> A = new ArrayList<>(Arrays.asList(1, 2));
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(Arrays.asList(1, 2), C, "目标柱子应该包含所有盘子，顺序正确");
    }

    @Test
    @DisplayName("基本汉诺塔测试 - 3个盘子")
    void testHanoiWithThreeDisks() {
        List<Integer> A = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(Arrays.asList(1, 2, 3), C, "目标柱子应该包含所有盘子，顺序正确");
    }

    @Test
    @DisplayName("基本汉诺塔测试 - 4个盘子")
    void testHanoiWithFourDisks() {
        List<Integer> A = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(Arrays.asList(1, 2, 3, 4), C, "目标柱子应该包含所有盘子，顺序正确");
    }

    @Test
    @DisplayName("边界情况测试 - 空柱子")
    void testHanoiWithEmptyTower() {
        List<Integer> A = new ArrayList<>();
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该保持为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该保持为空");
        assertTrue(C.isEmpty(), "目标柱子应该保持为空");
    }

    @Test
    @DisplayName("边界情况测试 - 单个盘子多种情况")
    void testHanoiWithSingleDiskVariations() {
        // 测试不同大小的单个盘子
        for (int diskSize = 1; diskSize <= 5; diskSize++) {
            List<Integer> A = new ArrayList<>(Arrays.asList(diskSize));
            List<Integer> B = new ArrayList<>();
            List<Integer> C = new ArrayList<>();

            hanoi.solveHanoi(A, B, C);

            assertTrue(A.isEmpty(), "源柱子应该为空 (盘子大小: " + diskSize + ")");
            assertTrue(B.isEmpty(), "缓冲柱子应该为空 (盘子大小: " + diskSize + ")");
            assertEquals(Arrays.asList(diskSize), C, 
                "目标柱子应该包含盘子 " + diskSize);
        }
    }

    @Test
    @DisplayName("算法正确性验证 - 多种盘子数量")
    void testHanoiCorrectnessWithVariousSizes() {
        // 测试1到8个盘子的情况
        for (int n = 1; n <= 8; n++) {
            List<Integer> A = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                A.add(i);
            }
            List<Integer> B = new ArrayList<>();
            List<Integer> C = new ArrayList<>();

            List<Integer> expected = new ArrayList<>(A);

            hanoi.solveHanoi(A, B, C);

            assertTrue(A.isEmpty(), "源柱子应该为空 (n=" + n + ")");
            assertTrue(B.isEmpty(), "缓冲柱子应该为空 (n=" + n + ")");
            assertEquals(expected, C, "目标柱子应该包含所有盘子，顺序正确 (n=" + n + ")");
        }
    }

    @Test
    @DisplayName("move方法单独测试")
    void testMoveMethod() {
        List<Integer> src = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> dst = new ArrayList<>();

        hanoi.move(src, dst);

        assertEquals(Arrays.asList(1, 2), src, "源柱子应该移除顶部盘子");
        assertEquals(Arrays.asList(3), dst, "目标柱子应该添加移动的盘子");

        // 再次移动
        hanoi.move(src, dst);

        assertEquals(Arrays.asList(1), src, "源柱子应该再次移除顶部盘子");
        assertEquals(Arrays.asList(3, 2), dst, "目标柱子应该按顺序添加盘子");
    }

    @Test
    @DisplayName("递归深度测试 - 防止栈溢出")
    void testRecursionDepth() {
        // 测试较大数量的盘子，但不会导致栈溢出
        // 通常10个盘子需要2^10-1=1023次移动，递归深度约为10
        List<Integer> A = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            A.add(i);
        }
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        List<Integer> expected = new ArrayList<>(A);

        // 这个测试应该能够成功完成，不会出现栈溢出
        assertDoesNotThrow(() -> hanoi.solveHanoi(A, B, C), 
            "10个盘子的汉诺塔不应该导致栈溢出");

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(expected, C, "目标柱子应该包含所有盘子，顺序正确");
    }

    @Test
    @DisplayName("性能测试 - 移动次数验证")
    void testMoveCountVerification() {
        // 汉诺塔的理论移动次数为 2^n - 1
        // 我们通过计算实际移动次数来验证算法的正确性
        
        for (int n = 1; n <= 6; n++) {
            List<Integer> A = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                A.add(i);
            }
            List<Integer> B = new ArrayList<>();
            List<Integer> C = new ArrayList<>();

            // 创建一个计数器来跟踪移动次数
            MoveCounter counter = new MoveCounter();
            HanoiWithCounter hanoiCounter = new HanoiWithCounter(counter);
            
            hanoiCounter.solveHanoi(A, B, C);

            int expectedMoves = (1 << n) - 1; // 2^n - 1
            assertEquals(expectedMoves, counter.getCount(), 
                "n=" + n + "时，移动次数应该为" + expectedMoves);
        }
    }

    @Test
    @DisplayName("逆序盘子测试")
    void testReverseOrderDisks() {
        // 测试盘子按逆序排列的情况（虽然这不是标准汉诺塔，但测试算法的鲁棒性）
        List<Integer> A = new ArrayList<>(Arrays.asList(3, 2, 1));
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();

        hanoi.solveHanoi(A, B, C);

        assertTrue(A.isEmpty(), "源柱子应该为空");
        assertTrue(B.isEmpty(), "缓冲柱子应该为空");
        assertEquals(Arrays.asList(3, 2, 1), C, "目标柱子应该包含所有盘子，保持原顺序");
    }

    /**
     * 移动计数器辅助类
     */
    private static class MoveCounter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }

        public void reset() {
            count = 0;
        }
    }

    /**
     * 带计数器的汉诺塔实现，用于验证移动次数
     */
    private static class HanoiWithCounter {
        private final MoveCounter counter;

        public HanoiWithCounter(MoveCounter counter) {
            this.counter = counter;
        }

        public void solveHanoi(List<Integer> A, List<Integer> B, List<Integer> C) {
            int n = A.size();
            if (n > 0) {
                hanoi(n, A, B, C);
            }
        }

        private void hanoi(int i, List<Integer> src, List<Integer> buf, List<Integer> dst) {
            if (i <= 0) {
                return;
            }

            if (i == 1) {
                move(src, dst);
                return;
            }

            hanoi(i - 1, src, dst, buf);
            move(src, dst);
            hanoi(i - 1, buf, src, dst);
        }

        private void move(List<Integer> src, List<Integer> dst) {
            Integer pan = src.remove(src.size() - 1);
            dst.add(pan);
            counter.increment(); // 计数移动次数
        }
    }
}