package algorithm.foundations.divideconquer;

import java.util.*;
import java.util.Arrays;

/**
 * 凸包算法 - 分治法实现。
 *
 * 问题描述：
 * 给定平面上的一组点，找到包含所有点的最小凸多边形（凸包）。
 * 凸包是包含所有给定点的最小凸集合，其边界由部分给定点构成。
 *
 * 算法原理：
 * 使用分治策略求解凸包问题：
 * 1. 将点集按 x 坐标排序
 * 2. 递归地将点集分为左右两部分，分别求凸包
 * 3. 合并两个凸包，找到公切线
 *
 * 分治思想体现：
 * 1. 分 (Divide)：将点集按 x 坐标分为两部分
 * 2. 治 (Conquer)：递归求解左右两部分的凸包
 * 3. 合 (Combine)：合并两个凸包，通过找公切线连接
 *
 * 时间复杂度：T(n) = 2T(n/2) + O(n) = O(n log n)
 * 空间复杂度：O(n log n)，递归栈深度为 O(log n)
 *
 * 应用场景：
 * - 计算几何中的基础问题
 * - 图像处理中的轮廓提取
 * - 机器人路径规划
 * - 碰撞检测优化
 * - 数据可视化中的边界绘制
 *
 * @author magicliang
 * @date 2025-09-09 13:00
 */
public class ConvexHull {

    /**
     * 二维点类。
     */
    public static class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 计算两点间距离。
         */
        public double distanceTo(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算向量叉积，用于判断三点的方向关系。
         * 返回值 > 0：逆时针
         * 返回值 < 0：顺时针  
         * 返回值 = 0：共线
         */
        public static double crossProduct(Point a, Point b, Point c) {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        }

        /**
         * 判断点c是否在点a的左侧（相对于向量ab）。
         */
        public static boolean isLeftTurn(Point a, Point b, Point c) {
            return crossProduct(a, b, c) > 0;
        }

        /**
         * 判断点c是否在点a的右侧（相对于向量ab）。
         */
        public static boolean isRightTurn(Point a, Point b, Point c) {
            return crossProduct(a, b, c) < 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return Math.abs(x - point.x) < 1e-9 && Math.abs(y - point.y) < 1e-9;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%.1f, %.1f)", x, y);
        }
    }

    /**
     * 分治法求凸包主入口（数组版本）。
     * 这是为了兼容测试文件而提供的适配器方法。
     *
     * @param points 输入点数组
     * @return 凸包顶点数组（按逆时针顺序）
     * @throws IllegalArgumentException 如果输入为null、空数组或点数少于3
     */
    public static Point[] findConvexHull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("输入点数组不能为null");
        }
        if (points.length == 0) {
            throw new IllegalArgumentException("输入点数组不能为空");
        }
        if (points.length < 3) {
            throw new IllegalArgumentException("至少需要3个点才能构成凸包");
        }

        // 转换为List并调用主方法
        List<Point> pointList = Arrays.asList(points);
        List<Point> hullList = convexHull(pointList);
        
        // 转换回数组
        return hullList.toArray(new Point[0]);
    }

    /**
     * 分治法求凸包主入口。
     * 步骤：
     * 1. 参数验证和预处理
     * 2. 处理特殊情况（点数少于3）
     * 3. 按 x 坐标排序
     * 4. 调用递归的分治算法
     *
     * @param points 输入点集
     * @return 凸包顶点列表（按逆时针顺序）
     */
    public static List<Point> convexHull(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>();
        }

        // 去除重复点
        Set<Point> uniquePoints = new LinkedHashSet<>(points);
        List<Point> uniqueList = new ArrayList<>(uniquePoints);

        int n = uniqueList.size();
        if (n < 3) {
            return uniqueList; // 少于3个点无法构成凸包
        }

        // 按 x 坐标排序，x 相同时按 y 坐标排序
        uniqueList.sort((p1, p2) -> {
            int cmp = Double.compare(p1.x, p2.x);
            return cmp != 0 ? cmp : Double.compare(p1.y, p2.y);
        });

        return divideAndConquerHull(uniqueList);
    }

    /**
     * 递归的分治凸包算法。
     * 步骤：
     * 1. 递归基：点数少于等于3时直接处理
     * 2. 分：将点集分为左右两部分
     * 3. 治：递归求解左右两部分的凸包
     * 4. 合：合并两个凸包
     *
     * @param points 已排序的点集
     * @return 凸包顶点列表
     */
    private static List<Point> divideAndConquerHull(List<Point> points) {
        int n = points.size();

        // 递归基：点数少于等于3
        if (n <= 3) {
            return handleSmallCase(points);
        }

        // 分：将点集分为左右两部分
        int mid = n / 2;
        List<Point> leftPoints = points.subList(0, mid);
        List<Point> rightPoints = points.subList(mid, n);

        // 治：递归求解左右凸包
        List<Point> leftHull = divideAndConquerHull(leftPoints);
        List<Point> rightHull = divideAndConquerHull(rightPoints);

        // 合：合并两个凸包
        return mergeHulls(leftHull, rightHull);
    }

    /**
     * 处理点数少于等于3的情况。
     *
     * @param points 点集
     * @return 凸包顶点列表
     */
    private static List<Point> handleSmallCase(List<Point> points) {
        int n = points.size();
        
        if (n == 1) {
            return new ArrayList<>(points);
        }
        
        if (n == 2) {
            return new ArrayList<>(points);
        }
        
        // n == 3的情况
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        Point p3 = points.get(2);
        
        // 检查三点是否共线
        double cross = Point.crossProduct(p1, p2, p3);
        if (Math.abs(cross) < 1e-9) {
            // 共线，返回端点
            List<Point> result = new ArrayList<>();
            result.add(p1);
            if (!p3.equals(p1)) {
                result.add(p3);
            }
            return result;
        }
        
        // 按逆时针顺序排列
        List<Point> result = new ArrayList<>();
        if (cross > 0) {
            result.add(p1);
            result.add(p2);
            result.add(p3);
        } else {
            result.add(p1);
            result.add(p3);
            result.add(p2);
        }
        
        return result;
    }

    /**
     * 合并两个凸包。
     * 步骤：
     * 1. 找到两个凸包的上公切线和下公切线
     * 2. 构造合并后的凸包
     *
     * @param leftHull 左凸包
     * @param rightHull 右凸包
     * @return 合并后的凸包
     */
    private static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        if (leftHull.isEmpty()) return rightHull;
        if (rightHull.isEmpty()) return leftHull;
        if (leftHull.size() == 1 && rightHull.size() == 1) {
            List<Point> result = new ArrayList<>(leftHull);
            result.addAll(rightHull);
            return result;
        }

        // 找到左凸包的最右点和右凸包的最左点
        int leftRightmost = findRightmostPoint(leftHull);
        int rightLeftmost = findLeftmostPoint(rightHull);

        // 找上公切线
        int[] upperTangent = findUpperTangent(leftHull, rightHull, leftRightmost, rightLeftmost);
        int upperLeft = upperTangent[0];
        int upperRight = upperTangent[1];

        // 找下公切线
        int[] lowerTangent = findLowerTangent(leftHull, rightHull, leftRightmost, rightLeftmost);
        int lowerLeft = lowerTangent[0];
        int lowerRight = lowerTangent[1];

        // 构造合并后的凸包
        List<Point> result = new ArrayList<>();
        
        // 添加左凸包从下公切线到上公切线的部分
        int i = lowerLeft;
        while (true) {
            result.add(leftHull.get(i));
            if (i == upperLeft) break;
            i = (i + 1) % leftHull.size();
        }
        
        // 添加右凸包从上公切线到下公切线的部分
        i = upperRight;
        while (true) {
            result.add(rightHull.get(i));
            if (i == lowerRight) break;
            i = (i + 1) % rightHull.size();
        }

        return result;
    }

    /**
     * 找到凸包中最右边的点的索引。
     */
    private static int findRightmostPoint(List<Point> hull) {
        int rightmost = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(rightmost).x) {
                rightmost = i;
            }
        }
        return rightmost;
    }

    /**
     * 找到凸包中最左边的点的索引。
     */
    private static int findLeftmostPoint(List<Point> hull) {
        int leftmost = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(leftmost).x) {
                leftmost = i;
            }
        }
        return leftmost;
    }

    /**
     * 找到两个凸包的上公切线。
     */
    private static int[] findUpperTangent(List<Point> leftHull, List<Point> rightHull, 
                                        int leftStart, int rightStart) {
        int leftIdx = leftStart;
        int rightIdx = rightStart;
        boolean done = false;

        while (!done) {
            done = true;
            
            // 调整右侧点
            while (Point.isLeftTurn(leftHull.get(leftIdx), rightHull.get(rightIdx), 
                                  rightHull.get((rightIdx + 1) % rightHull.size()))) {
                rightIdx = (rightIdx + 1) % rightHull.size();
                done = false;
            }
            
            // 调整左侧点
            while (Point.isRightTurn(rightHull.get(rightIdx), leftHull.get(leftIdx), 
                                   leftHull.get((leftIdx - 1 + leftHull.size()) % leftHull.size()))) {
                leftIdx = (leftIdx - 1 + leftHull.size()) % leftHull.size();
                done = false;
            }
        }

        return new int[]{leftIdx, rightIdx};
    }

    /**
     * 找到两个凸包的下公切线。
     */
    private static int[] findLowerTangent(List<Point> leftHull, List<Point> rightHull, 
                                        int leftStart, int rightStart) {
        int leftIdx = leftStart;
        int rightIdx = rightStart;
        boolean done = false;

        while (!done) {
            done = true;
            
            // 调整右侧点
            while (Point.isRightTurn(leftHull.get(leftIdx), rightHull.get(rightIdx), 
                                   rightHull.get((rightIdx - 1 + rightHull.size()) % rightHull.size()))) {
                rightIdx = (rightIdx - 1 + rightHull.size()) % rightHull.size();
                done = false;
            }
            
            // 调整左侧点
            while (Point.isLeftTurn(rightHull.get(rightIdx), leftHull.get(leftIdx), 
                                  leftHull.get((leftIdx + 1) % leftHull.size()))) {
                leftIdx = (leftIdx + 1) % leftHull.size();
                done = false;
            }
        }

        return new int[]{leftIdx, rightIdx};
    }

    /**
     * Graham扫描算法求凸包（用于对比）。
     * 时间复杂度：O(n log n)
     *
     * @param points 输入点集
     * @return 凸包顶点列表
     */
    public static List<Point> grahamScan(List<Point> points) {
        if (points == null || points.size() < 3) {
            return new ArrayList<>(points != null ? points : Collections.emptyList());
        }

        // 找到最下方的点（y最小，y相同时x最小）
        Point pivot = points.stream()
            .min((p1, p2) -> {
                int cmp = Double.compare(p1.y, p2.y);
                return cmp != 0 ? cmp : Double.compare(p1.x, p2.x);
            })
            .orElse(points.get(0));

        // 按极角排序
        List<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.remove(pivot);
        sortedPoints.sort((p1, p2) -> {
            double cross = Point.crossProduct(pivot, p1, p2);
            if (Math.abs(cross) < 1e-9) {
                // 共线时按距离排序
                return Double.compare(pivot.distanceTo(p1), pivot.distanceTo(p2));
            }
            return cross > 0 ? -1 : 1;
        });

        // Graham扫描
        Stack<Point> stack = new Stack<>();
        stack.push(pivot);
        
        for (Point point : sortedPoints) {
            while (stack.size() > 1) {
                Point top = stack.pop();
                Point second = stack.peek();
                if (Point.isLeftTurn(second, top, point)) {
                    stack.push(top);
                    break;
                }
            }
            stack.push(point);
        }

        return new ArrayList<>(stack);
    }

    /**
     * 计算凸包的面积。
     *
     * @param hull 凸包顶点列表
     * @return 面积
     */
    public static double calculateArea(List<Point> hull) {
        if (hull.size() < 3) return 0;

        double area = 0;
        int n = hull.size();
        
        for (int i = 0; i < n; i++) {
            Point current = hull.get(i);
            Point next = hull.get((i + 1) % n);
            area += current.x * next.y - next.x * current.y;
        }
        
        return Math.abs(area) / 2.0;
    }

    /**
     * 计算凸包的周长。
     *
     * @param hull 凸包顶点列表
     * @return 周长
     */
    public static double calculatePerimeter(List<Point> hull) {
        if (hull.size() < 2) return 0;

        double perimeter = 0;
        int n = hull.size();
        
        for (int i = 0; i < n; i++) {
            Point current = hull.get(i);
            Point next = hull.get((i + 1) % n);
            perimeter += current.distanceTo(next);
        }
        
        return perimeter;
    }
}