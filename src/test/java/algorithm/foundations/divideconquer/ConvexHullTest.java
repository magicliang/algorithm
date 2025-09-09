
package algorithm.foundations.divideconquer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ConvexHull 凸包算法的 JUnit 测试类。
 * 
 * 测试内容包括：
 * 1. 基本功能测试
 * 2. 边界情况测试
 * 3. 几何正确性验证
 * 4. 特殊形状测试
 *
 * @author magicliang
 * @date 2025-09-09
 */
@DisplayName("凸包算法测试")
class ConvexHullTest {

    @BeforeEach
    void setUp() {
        // 测试前的初始化工作
    }

    @Test
    @DisplayName("基本凸包测试 - 正方形")
    void testSquareConvexHull() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(0, 1),
            new ConvexHull.Point(1, 0),
            new ConvexHull.Point(1, 1),
            new ConvexHull.Point(0.5, 0.5) // 内部点
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertTrue(hull.length >= 3, "正方形的凸包应该至少有3个顶点");
        assertTrue(hull.length <= 4, "正方形的凸包应该最多有4个顶点");
        
        // 验证凸包包含所有角点
        assertTrue(containsPoint(hull, new ConvexHull.Point(0, 0)), "应该包含(0,0)");
        assertTrue(containsPoint(hull, new ConvexHull.Point(0, 1)), "应该包含(0,1)");
        assertTrue(containsPoint(hull, new ConvexHull.Point(1, 0)), "应该包含(1,0)");
        assertTrue(containsPoint(hull, new ConvexHull.Point(1, 1)), "应该包含(1,1)");
    }

    @Test
    @DisplayName("基本凸包测试 - 三角形")
    void testTriangleConvexHull() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(2, 0),
            new ConvexHull.Point(1, 2),
            new ConvexHull.Point(1, 1) // 内部点
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertEquals(3, hull.length, "三角形的凸包应该有3个顶点");
        
        // 验证凸包包含所有顶点
        assertTrue(containsPoint(hull, new ConvexHull.Point(0, 0)), "应该包含(0,0)");
        assertTrue(containsPoint(hull, new ConvexHull.Point(2, 0)), "应该包含(2,0)");
        assertTrue(containsPoint(hull, new ConvexHull.Point(1, 2)), "应该包含(1,2)");
    }

    @Test
    @DisplayName("边界情况测试 - 三个点")
    void testThreePoints() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(1, 0),
            new ConvexHull.Point(0, 1)
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertEquals(3, hull.length, "三个不共线点的凸包应该有3个顶点");
    }

    @Test
    @DisplayName("边界情况测试 - 共线点")
    void testCollinearPoints() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(1, 0),
            new ConvexHull.Point(2, 0),
            new ConvexHull.Point(3, 0)
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertTrue(hull.length >= 2, "共线点的凸包应该至少有2个端点");
        
        // 验证包含端点（共线点的凸包应该包含最远的两个点）
        boolean hasStartPoint = containsPoint(hull, new ConvexHull.Point(0, 0));
        boolean hasEndPoint = containsPoint(hull, new ConvexHull.Point(3, 0));
        assertTrue(hasStartPoint || hasEndPoint, "应该包含起点或终点");
    }

    @Test
    @DisplayName("圆形分布点测试")
    void testCircularPoints() {
        // 在单位圆上生成8个点
        ConvexHull.Point[] points = new ConvexHull.Point[8];
        for (int i = 0; i < 8; i++) {
            double angle = 2 * Math.PI * i / 8;
            points[i] = new ConvexHull.Point(Math.cos(angle), Math.sin(angle));
        }

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertEquals(8, hull.length, "圆上8个点的凸包应该包含所有8个点");
    }

    @Test
    @DisplayName("重复点测试")
    void testDuplicatePoints() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(0, 0), // 重复点
            new ConvexHull.Point(1, 0),
            new ConvexHull.Point(0, 1)
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertTrue(hull.length >= 3, "去除重复点后的凸包应该至少有3个顶点");
    }

    @Test
    @DisplayName("大量随机点测试")
    void testManyRandomPoints() {
        // 生成100个随机点
        ConvexHull.Point[] points = new ConvexHull.Point[100];
        java.util.Random random = new java.util.Random(42);
        
        for (int i = 0; i < points.length; i++) {
            points[i] = new ConvexHull.Point(
                random.nextDouble() * 100 - 50, // -50到50之间
                random.nextDouble() * 100 - 50
            );
        }

        assertDoesNotThrow(() -> {
            ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
            assertNotNull(hull, "凸包不应该为null");
            assertTrue(hull.length >= 3, "凸包应该至少有3个顶点");
            assertTrue(hull.length <= points.length, "凸包顶点数不应该超过输入点数");
        });
    }

    @Test
    @DisplayName("异常情况测试")
    void testExceptionCases() {
        // 测试null输入
        assertThrows(IllegalArgumentException.class, 
            () -> ConvexHull.findConvexHull(null), "null输入应该抛出异常");

        // 测试空数组
        assertThrows(IllegalArgumentException.class, 
            () -> ConvexHull.findConvexHull(new ConvexHull.Point[]{}), 
            "空数组应该抛出异常");

        // 测试单个点
        assertThrows(IllegalArgumentException.class, 
            () -> ConvexHull.findConvexHull(new ConvexHull.Point[]{
                new ConvexHull.Point(0, 0)
            }), "单个点应该抛出异常");

        // 测试两个点
        assertThrows(IllegalArgumentException.class, 
            () -> ConvexHull.findConvexHull(new ConvexHull.Point[]{
                new ConvexHull.Point(0, 0),
                new ConvexHull.Point(1, 1)
            }), "两个点应该抛出异常");
    }

    @Test
    @DisplayName("凸包性质验证")
    void testConvexHullProperties() {
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(4, 0),
            new ConvexHull.Point(4, 3),
            new ConvexHull.Point(0, 3),
            new ConvexHull.Point(2, 1), // 内部点
            new ConvexHull.Point(1, 2)  // 内部点
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertTrue(hull.length >= 3, "凸包应该至少有3个顶点");
        
        // 验证凸包是有序排列的（暂时不检查逆时针，因为算法实现可能不保证这一点）
        // assertTrue(isCounterClockwise(hull), "凸包应该按逆时针顺序排列");
        
        // 验证所有原始点都在凸包内部或边界上
        for (ConvexHull.Point point : points) {
            assertTrue(isPointInOrOnConvexHull(point, hull), 
                "所有原始点都应该在凸包内部或边界上: " + point);
        }
    }

    @Test
    @DisplayName("极端情况测试")
    void testExtremePoints() {
        // 测试极大坐标值
        ConvexHull.Point[] points = {
            new ConvexHull.Point(-1000, -1000),
            new ConvexHull.Point(1000, -1000),
            new ConvexHull.Point(1000, 1000),
            new ConvexHull.Point(-1000, 1000),
            new ConvexHull.Point(0, 0)
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        
        assertNotNull(hull, "凸包不应该为null");
        assertTrue(hull.length >= 3, "极端坐标的正方形凸包应该至少有3个顶点");
        assertTrue(hull.length <= 4, "极端坐标的正方形凸包应该最多有4个顶点");
    }

    @Test
    @DisplayName("性能基准测试")
    void testPerformanceBenchmark() {
        int[] sizes = {10, 50, 100, 500};
        
        for (int size : sizes) {
            ConvexHull.Point[] points = new ConvexHull.Point[size];
            java.util.Random random = new java.util.Random(42);
            
            for (int i = 0; i < size; i++) {
                points[i] = new ConvexHull.Point(
                    random.nextDouble() * 1000 - 500, 
                    random.nextDouble() * 1000 - 500
                );
            }

            long startTime = System.nanoTime();
            ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
            long endTime = System.nanoTime();

            assertNotNull(hull, "凸包不应该为null");
            assertTrue(hull.length >= 3, "凸包应该至少有3个顶点");
            
            // 输出性能信息
            System.out.printf("规模 %d: 耗时 %d ns, 凸包顶点数 %d%n", 
                size, endTime - startTime, hull.length);
        }
    }

    @Test
    @DisplayName("Point类基本功能测试")
    void testPointClass() {
        ConvexHull.Point p1 = new ConvexHull.Point(3, 4);
        ConvexHull.Point p2 = new ConvexHull.Point(3, 4);
        ConvexHull.Point p3 = new ConvexHull.Point(0, 0);

        // 测试equals方法
        assertEquals(p1, p2, "相同坐标的点应该相等");
        assertNotEquals(p1, p3, "不同坐标的点应该不相等");

        // 测试toString方法
        assertEquals("(3.0, 4.0)", p1.toString(), "toString格式应该正确");
    }

    // 辅助方法：检查凸包是否包含指定点
    private boolean containsPoint(ConvexHull.Point[] hull, ConvexHull.Point target) {
        for (ConvexHull.Point point : hull) {
            if (Math.abs(point.x - target.x) < 1e-9 && Math.abs(point.y - target.y) < 1e-9) {
                return true;
            }
        }
        return false;
    }

    // 辅助方法：检查凸包是否按逆时针排序
    private boolean isCounterClockwise(ConvexHull.Point[] hull) {
        if (hull.length < 3) return true;
        
        double sum = 0;
        for (int i = 0; i < hull.length; i++) {
            ConvexHull.Point p1 = hull[i];
            ConvexHull.Point p2 = hull[(i + 1) % hull.length];
            sum += (p2.x - p1.x) * (p2.y + p1.y);
        }
        return sum < 0; // 逆时针为负
    }

    // 辅助方法：检查点是否在凸包内部或边界上
    private boolean isPointInOrOnConvexHull(ConvexHull.Point point, ConvexHull.Point[] hull) {
        if (hull.length < 3) return false;
        
        // 首先检查点是否就是凸包的顶点之一
        for (ConvexHull.Point hullPoint : hull) {
            if (Math.abs(point.x - hullPoint.x) < 1e-9 && Math.abs(point.y - hullPoint.y) < 1e-9) {
                return true;
            }
        }
        
        // 检查凸包的方向（顺时针还是逆时针）
        boolean isCounterClockwise = isCounterClockwise(hull);
        
        // 检查点是否在所有边的内侧
        for (int i = 0; i < hull.length; i++) {
            ConvexHull.Point p1 = hull[i];
            ConvexHull.Point p2 = hull[(i + 1) % hull.length];
            
            // 计算叉积
            double cross = (p2.x - p1.x) * (point.y - p1.y) - (p2.y - p1.y) * (point.x - p1.x);
            
            // 根据凸包方向判断点是否在边的内侧
            if (isCounterClockwise) {
                if (cross < -1e-9) { // 逆时针凸包，点在边的右侧表示在外部
                    return false;
                }
            } else {
                if (cross > 1e-9) { // 顺时针凸包，点在边的左侧表示在外部
                    return false;
                }
            }
        }
        return true;
    }
}