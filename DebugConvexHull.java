import algorithm.foundations.divideconquer.ConvexHull;

public class DebugConvexHull {
    public static void main(String[] args) {
        System.out.println("=== 测试凸包性质验证用例 ===");
        ConvexHull.Point[] points = {
            new ConvexHull.Point(0, 0),
            new ConvexHull.Point(4, 0),
            new ConvexHull.Point(4, 3),
            new ConvexHull.Point(0, 3),
            new ConvexHull.Point(2, 1), // 内部点
            new ConvexHull.Point(1, 2)  // 内部点
        };

        ConvexHull.Point[] hull = ConvexHull.findConvexHull(points);
        System.out.println("Hull size: " + hull.length);
        for (ConvexHull.Point p : hull) {
            System.out.println(p);
        }
        
        System.out.println("\n=== 检查点是否在凸包内 ===");
        for (ConvexHull.Point point : points) {
            boolean inside = isPointInOrOnConvexHull(point, hull);
            System.out.println(point + " -> " + inside);
        }
    }
    
    // 复制测试中的方法
    private static boolean isPointInOrOnConvexHull(ConvexHull.Point point, ConvexHull.Point[] hull) {
        if (hull.length < 3) return false;
        
        // 首先检查点是否就是凸包的顶点之一
        for (ConvexHull.Point hullPoint : hull) {
            if (Math.abs(point.x - hullPoint.x) < 1e-9 && Math.abs(point.y - hullPoint.y) < 1e-9) {
                return true;
            }
        }
        
        // 检查凸包的方向（顺时针还是逆时针）
        boolean isCounterClockwise = isCounterClockwise(hull);
        System.out.println("Hull is counter-clockwise: " + isCounterClockwise);
        
        // 检查点是否在所有边的内侧
        for (int i = 0; i < hull.length; i++) {
            ConvexHull.Point p1 = hull[i];
            ConvexHull.Point p2 = hull[(i + 1) % hull.length];
            
            // 计算叉积
            double cross = (p2.x - p1.x) * (point.y - p1.y) - (p2.y - p1.y) * (point.x - p1.x);
            System.out.println("Edge " + p1 + " -> " + p2 + ", cross product for " + point + ": " + cross);
            
            // 根据凸包方向判断点是否在边的内侧
            if (isCounterClockwise) {
                if (cross > 1e-9) { // 逆时针凸包，点在边的右侧表示在外部
                    return false;
                }
            } else {
                if (cross < -1e-9) { // 顺时针凸包，点在边的左侧表示在外部
                    return false;
                }
            }
        }
        return true;
    }
    
    // 检查凸包是否按逆时针排序
    private static boolean isCounterClockwise(ConvexHull.Point[] hull) {
        if (hull.length < 3) return true;
        
        double sum = 0;
        for (int i = 0; i < hull.length; i++) {
            ConvexHull.Point p1 = hull[i];
            ConvexHull.Point p2 = hull[(i + 1) % hull.length];
            sum += (p2.x - p1.x) * (p2.y + p1.y);
        }
        return sum < 0; // 逆时针为负
    }
}