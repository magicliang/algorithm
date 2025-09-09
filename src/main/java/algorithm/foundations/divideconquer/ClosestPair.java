package algorithm.foundations.divideconquer;

import java.util.*;

/**
 * 最近点对问题求解器 - 几何分治算法的经典应用。
 *
 * 问题描述：
 * 给定平面上 n 个点，找出其中距离最近的两个点。
 * 这是计算几何中的基础问题，在图形学、地理信息系统、聚类分析等领域有广泛应用。
 *
 * 解题思路：
 * 1. 暴力解法：O(n²) - 计算所有点对的距离，找出最小值
 * 2. 分治解法：O(n log n) - 将点集分为左右两部分，递归求解，然后处理跨越分界线的点对
 *
 * 分治算法核心思想：
 * 1. 分 (Divide)：按 x 坐标将点集分为左右两部分
 * 2. 治 (Conquer)：递归求解左半部分和右半部分的最近点对距离
 * 3. 合 (Combine)：处理跨越分界线的点对，可能存在比左右两部分更近的点对
 *
 * 关键优化：
 * 1. 预排序：按 x 坐标和 y 坐标分别排序，避免递归中重复排序
 * 2. 剪枝策略：在处理跨越分界线的点对时，只考虑距离分界线不超过当前最小距离的点
 * 3. 线性扫描：对于跨越分界线的候选点，按 y 坐标排序后线性扫描
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)，用于存储排序后的点集和递归栈
 *
 * @author magicliang
 * @date 2025-09-09 12:00
 */
public class ClosestPair {

    /**
     * 二维点类，表示平面上的一个点。
     */
    public static class Point {
        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 计算当前点到另一个点的欧几里得距离。
         *
         * @param other 另一个点
         * @return 两点之间的距离
         */
        public double distanceTo(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", x, y);
        }
    }

    /**
     * 最近点对结果类，包含两个最近的点和它们之间的距离。
     */
    public static class ClosestPairResult {
        public Point point1, point2;
        public double distance;

        public ClosestPairResult(Point p1, Point p2, double dist) {
            this.point1 = p1;
            this.point2 = p2;
            this.distance = dist;
        }

        @Override
        public String toString() {
            return String.format("最近点对: %s 和 %s，距离: %.6f", point1, point2, distance);
        }
    }

    /**
     * 程序入口，测试最近点对算法。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试用例1：简单的4个点
        System.out.println("=== 测试用例1：4个点 ===");
        Point[] points1 = {
            new Point(2, 3),
            new Point(12, 30),
            new Point(40, 50),
            new Point(5, 1)
        };
        testClosestPair(points1);

        // 测试用例2：更多点的随机分布
        System.out.println("\n=== 测试用例2：8个随机点 ===");
        Point[] points2 = {
            new Point(1, 1),
            new Point(2, 2),
            new Point(3, 3),
            new Point(10, 10),
            new Point(11, 11),
            new Point(20, 20),
            new Point(25, 25),
            new Point(30, 30)
        };
        testClosestPair(points2);

        // 测试用例3：包含重复点
        System.out.println("\n=== 测试用例3：包含重复点 ===");
        Point[] points3 = {
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),  // 重复点
            new Point(5, 5)
        };
        testClosestPair(points3);

        // 性能测试：大量随机点
        System.out.println("\n=== 性能测试：1000个随机点 ===");
        Point[] randomPoints = generateRandomPoints(1000);
        long startTime = System.currentTimeMillis();
        ClosestPairResult result = findClosestPair(randomPoints);
        long endTime = System.currentTimeMillis();
        System.out.println("分治算法结果：" + result);
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }

    /**
     * 测试最近点对算法，比较暴力解法和分治解法的结果。
     *
     * @param points 点集
     */
    private static void testClosestPair(Point[] points) {
        System.out.println("点集：" + Arrays.toString(points));

        // 暴力解法
        long startTime = System.currentTimeMillis();
        ClosestPairResult bruteResult = findClosestPairBrute(points);
        long bruteTime = System.currentTimeMillis() - startTime;

        // 分治解法
        startTime = System.currentTimeMillis();
        ClosestPairResult divideResult = findClosestPair(points);
        long divideTime = System.currentTimeMillis() - startTime;

        System.out.println("暴力解法：" + bruteResult + "，耗时：" + bruteTime + "ms");
        System.out.println("分治解法：" + divideResult + "，耗时：" + divideTime + "ms");
        System.out.println("结果一致：" + (Math.abs(bruteResult.distance - divideResult.distance) < 1e-9));
    }

    /**
     * 生成指定数量的随机点。
     *
     * @param n 点的数量
     * @return 随机点数组
     */
    private static Point[] generateRandomPoints(int n) {
        Random random = new Random(42); // 固定种子以便复现
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    /**
     * 使用分治法找到最近点对。
     * 步骤：
     * 1. 参数验证：检查点集的有效性
     * 2. 预处理：按 x 坐标和 y 坐标分别排序
     * 3. 调用递归分治方法
     * 4. 返回结果
     *
     * @param points 点集
     * @return 最近点对结果
     */
    public static ClosestPairResult findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("至少需要2个点");
        }

        // 预处理：按 x 坐标排序
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));

        // 按 y 坐标排序（用于后续的跨分界线处理）
        Point[] sortedByY = points.clone();
        Arrays.sort(sortedByY, Comparator.comparingDouble(p -> p.y));

        // 调用递归分治方法
        return closestPairRec(sortedByX, sortedByY, 0, points.length - 1);
    }

    /**
     * 递归的分治方法，处理 sortedByX[left...right] 范围内的点。
     * 步骤：
     * 1. 递归基：点数 ≤ 3 时使用暴力方法
     * 2. 分：找到中点，将点集分为左右两部分
     * 3. 治：递归求解左半部分和右半部分的最近点对
     * 4. 合：处理跨越分界线的点对，可能存在更近的点对
     *
     * @param sortedByX 按 x 坐标排序的点集
     * @param sortedByY 按 y 坐标排序的点集
     * @param left 左边界（包含）
     * @param right 右边界（包含）
     * @return 最近点对结果
     */
    private static ClosestPairResult closestPairRec(Point[] sortedByX, Point[] sortedByY, 
                                                   int left, int right) {
        int n = right - left + 1;

        // 递归基：点数较少时使用暴力方法
        if (n <= 3) {
            return findClosestPairBruteInRange(sortedByX, left, right);
        }

        // 分：找到中点
        int mid = left + (right - left) / 2;
        Point midPoint = sortedByX[mid];

        // 将按 y 坐标排序的点集分为左右两部分
        List<Point> leftByY = new ArrayList<>();
        List<Point> rightByY = new ArrayList<>();
        
        for (Point p : sortedByY) {
            if (p.x <= midPoint.x) {
                leftByY.add(p);
            } else {
                rightByY.add(p);
            }
        }

        // 治：递归求解左右两部分
        ClosestPairResult leftResult = closestPairRec(sortedByX, 
                                                     leftByY.toArray(new Point[0]), 
                                                     left, mid);
        ClosestPairResult rightResult = closestPairRec(sortedByX, 
                                                      rightByY.toArray(new Point[0]), 
                                                      mid + 1, right);

        // 找到左右两部分中的最小距离
        ClosestPairResult minResult = (leftResult.distance <= rightResult.distance) ? 
                                     leftResult : rightResult;
        double minDistance = minResult.distance;

        // 合：处理跨越分界线的点对
        ClosestPairResult crossResult = findClosestCrossPair(sortedByY, midPoint.x, minDistance);
        
        if (crossResult != null && crossResult.distance < minDistance) {
            return crossResult;
        } else {
            return minResult;
        }
    }

    /**
     * 处理跨越分界线的点对，寻找可能更近的点对。
     * 优化策略：
     * 1. 只考虑距离分界线不超过 minDistance 的点
     * 2. 对这些点按 y 坐标排序
     * 3. 对每个点，只检查其后面 y 坐标差不超过 minDistance 的点
     *
     * @param sortedByY 按 y 坐标排序的点集
     * @param midX 分界线的 x 坐标
     * @param minDistance 当前已知的最小距离
     * @return 跨越分界线的最近点对，如果不存在更近的则返回 null
     */
    private static ClosestPairResult findClosestCrossPair(Point[] sortedByY, double midX, 
                                                         double minDistance) {
        // 筛选出距离分界线不超过 minDistance 的点
        List<Point> strip = new ArrayList<>();
        for (Point p : sortedByY) {
            if (Math.abs(p.x - midX) < minDistance) {
                strip.add(p);
            }
        }

        ClosestPairResult result = null;
        double minDist = minDistance;

        // 对于strip中的每个点，检查其后面的点
        for (int i = 0; i < strip.size(); i++) {
            Point p1 = strip.get(i);
            // 关键优化：只需要检查后面最多7个点
            // 这是因为在 minDistance × 2minDistance 的矩形中，最多只能有8个点
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {
                Point p2 = strip.get(j);
                // 如果 y 坐标差超过 minDistance，后面的点也不用检查了
                if (p2.y - p1.y >= minDist) {
                    break;
                }
                
                double dist = p1.distanceTo(p2);
                if (dist < minDist) {
                    minDist = dist;
                    result = new ClosestPairResult(p1, p2, dist);
                }
            }
        }

        return result;
    }

    /**
     * 暴力方法：计算所有点对的距离，找出最小值。
     * 时间复杂度：O(n²)
     * 用于小规模问题和验证分治算法的正确性。
     *
     * @param points 点集
     * @return 最近点对结果
     */
    public static ClosestPairResult findClosestPairBrute(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("至少需要2个点");
        }

        double minDistance = Double.MAX_VALUE;
        Point closestP1 = null, closestP2 = null;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestP1 = points[i];
                    closestP2 = points[j];
                }
            }
        }

        return new ClosestPairResult(closestP1, closestP2, minDistance);
    }

    /**
     * 在指定范围内使用暴力方法找最近点对。
     * 用于递归基的处理。
     *
     * @param points 点集
     * @param left 左边界（包含）
     * @param right 右边界（包含）
     * @return 最近点对结果
     */
    private static ClosestPairResult findClosestPairBruteInRange(Point[] points, int left, int right) {
        double minDistance = Double.MAX_VALUE;
        Point closestP1 = null, closestP2 = null;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestP1 = points[i];
                    closestP2 = points[j];
                }
            }
        }

        return new ClosestPairResult(closestP1, closestP2, minDistance);
    }
}