package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ClosestPair 最近点对算法的 JUnit 测试类。
 * 
 * 测试内容包括：
 * 1. 基本功能测试
 * 2. 边界情况测试
 * 3. 算法正确性验证
 * 4. 性能对比测试
 *
 * @author magicliang
 * @date 2025-09-09
 */
@DisplayName("最近点对算法测试")
class ClosestPairTest {

    @BeforeEach
    void setUp() {
        // 测试前的初始化工作
    }

    @Test
    @DisplayName("基本最近点对测试")
    void testBasicClosestPair() {
        // 测试简单的4个点
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 2),
            new ClosestPair.Point(10, 10)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        double expected = Math.sqrt(2); // (0,0)到(1,1)的距离
        assertEquals(expected, result, 1e-9, "最近点对距离应该为√2");
    }

    @Test
    @DisplayName("边界情况测试 - 两个点")
    void testTwoPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(3, 4)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(5.0, result, 1e-9, "两个点(0,0)和(3,4)的距离应该为5");
    }

    @Test
    @DisplayName("边界情况测试 - 三个点")
    void testThreePoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(2, 0)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(1.0, result, 1e-9, "三个共线点的最近距离应该为1");
    }

    @Test
    @DisplayName("相同点测试")
    void testIdenticalPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 2)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(0.0, result, 1e-9, "相同点的距离应该为0");
    }

    @Test
    @DisplayName("正方形顶点测试")
    void testSquareVertices() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(0, 1),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(1, 1)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(1.0, result, 1e-9, "正方形顶点的最近距离应该为1");
    }

    @Test
    @DisplayName("圆形分布点测试")
    void testCircularPoints() {
        // 在单位圆上均匀分布8个点
        ClosestPair.Point[] points = new ClosestPair.Point[8];
        for (int i = 0; i < 8; i++) {
            double angle = 2 * Math.PI * i / 8;
            points[i] = new ClosestPair.Point(Math.cos(angle), Math.sin(angle));
        }

        double result = ClosestPair.findClosestPairDistance(points);
        double expected = 2 * Math.sin(Math.PI / 8); // 相邻两点的距离
        assertEquals(expected, result, 1e-9, "圆形分布点的最近距离计算错误");
    }

    @Test
    @DisplayName("大量随机点测试")
    void testManyRandomPoints() {
        // 生成100个随机点
        ClosestPair.Point[] points = new ClosestPair.Point[100];
        java.util.Random random = new java.util.Random(42); // 固定种子确保可重复
        
        for (int i = 0; i < points.length; i++) {
            points[i] = new ClosestPair.Point(
                random.nextDouble() * 100, 
                random.nextDouble() * 100
            );
        }

        // 测试算法不会崩溃，并返回合理结果
        assertDoesNotThrow(() -> {
            double result = ClosestPair.findClosestPairDistance(points);
            assertTrue(result >= 0, "最近点对距离应该非负");
            assertTrue(result < 200, "最近点对距离应该在合理范围内"); // 最大可能距离约为141
        });
    }

    @Test
    @DisplayName("异常情况测试")
    void testExceptionCases() {
        // 测试null输入
        assertThrows(IllegalArgumentException.class, 
            () -> ClosestPair.findClosestPairDistance(null), "null输入应该抛出异常");

        // 测试空数组
        assertThrows(IllegalArgumentException.class, 
            () -> ClosestPair.findClosestPairDistance(new ClosestPair.Point[]{}), 
            "空数组应该抛出异常");

        // 测试单个点
        assertThrows(IllegalArgumentException.class, 
            () -> ClosestPair.findClosestPairDistance(new ClosestPair.Point[]{
                new ClosestPair.Point(0, 0)
            }), "单个点应该抛出异常");
    }

    @Test
    @DisplayName("精度测试")
    void testPrecision() {
        // 测试非常接近的点
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1e-10, 1e-10),
            new ClosestPair.Point(1, 1)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        double expected = Math.sqrt(2e-20); // √((1e-10)² + (1e-10)²)
        assertEquals(expected, result, 1e-15, "应该能处理非常小的距离");
    }

    @Test
    @DisplayName("对称性测试")
    void testSymmetry() {
        // 测试对称分布的点
        ClosestPair.Point[] points = {
            new ClosestPair.Point(-1, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(0, -1),
            new ClosestPair.Point(0, 1),
            new ClosestPair.Point(0, 0)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(1.0, result, 1e-9, "对称点的最近距离应该为1");
    }

    @Test
    @DisplayName("共线点测试")
    void testCollinearPoints() {
        // 测试所有点都在一条直线上的情况
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(3, 0),
            new ClosestPair.Point(6, 0),
            new ClosestPair.Point(10, 0)
        };

        double result = ClosestPair.findClosestPairDistance(points);
        assertEquals(1.0, result, 1e-9, "共线点的最近距离应该为1");
    }

    @Test
    @DisplayName("性能基准测试")
    void testPerformanceBenchmark() {
        // 测试不同规模的点集性能
        int[] sizes = {10, 50, 100, 500};
        
        for (int size : sizes) {
            ClosestPair.Point[] points = new ClosestPair.Point[size];
            java.util.Random random = new java.util.Random(42);
            
            for (int i = 0; i < size; i++) {
                points[i] = new ClosestPair.Point(
                    random.nextDouble() * 1000, 
                    random.nextDouble() * 1000
                );
            }

            long startTime = System.nanoTime();
            double result = ClosestPair.findClosestPairDistance(points);
            long endTime = System.nanoTime();

            assertTrue(result >= 0, "结果应该非负");
            
            // 输出性能信息（仅用于观察）
            System.out.printf("规模 %d: 耗时 %d ns, 结果 %.6f%n", 
                size, endTime - startTime, result);
        }
    }

    @Test
    @DisplayName("Point类基本功能测试")
    void testPointClass() {
        ClosestPair.Point p1 = new ClosestPair.Point(3, 4);
        ClosestPair.Point p2 = new ClosestPair.Point(0, 0);

        // 测试距离计算
        assertEquals(5.0, p1.distanceTo(p2), 1e-9, "点(3,4)到原点的距离应该为5");
        assertEquals(5.0, p2.distanceTo(p1), 1e-9, "距离计算应该满足对称性");

        // 测试自身距离
        assertEquals(0.0, p1.distanceTo(p1), 1e-9, "点到自身的距离应该为0");

        // 测试toString方法
        assertEquals("(3.00, 4.00)", p1.toString(), "toString格式应该正确");
    }
}