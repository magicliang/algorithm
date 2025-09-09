package algorithm.foundations.divideconquer;

/**
 * 三分搜索算法实现。
 *
 * 问题描述：
 * 在单峰函数（unimodal function）中寻找极值点。
 * 单峰函数是指只有一个极值点的函数，可能是单调递增后单调递减，或单调递减后单调递增。
 *
 * 算法原理：
 * 三分搜索将搜索区间分为三等份，通过比较两个分割点的函数值来缩小搜索范围：
 * 1. 将区间 [left, right] 分为三部分，分割点为 m1 和 m2
 * 2. 比较 f(m1) 和 f(m2) 的值
 * 3. 根据比较结果丢弃1/3的搜索空间
 * 4. 重复直到找到极值点
 *
 * 分治思想体现：
 * 1. 分 (Divide)：将搜索区间分为三部分
 * 2. 治 (Conquer)：比较分割点函数值，选择包含极值的区间
 * 3. 合 (Combine)：递归搜索选定的区间
 *
 * 时间复杂度：O(log₃ n) ≈ O(log n)
 * 空间复杂度：O(log n)，递归栈深度
 *
 * 应用场景：
 * - 优化问题中寻找最优解
 * - 物理学中寻找能量最小值
 * - 经济学中寻找成本最小点
 * - 机器学习中的参数优化
 *
 * @author magicliang
 * @date 2025-09-09 12:45
 */
public class TernarySearch {

    /**
     * 三分搜索寻找单峰函数的最大值点。
     * 步骤：
     * 1. 参数验证
     * 2. 递归三分搜索
     * 3. 返回极值点
     *
     * @param function 单峰函数
     * @param left 搜索区间左端点
     * @param right 搜索区间右端点
     * @param epsilon 精度要求
     * @return 最大值点的横坐标
     */
    public static double findMaximum(UnivariateFunction function, double left, double right, double epsilon) {
        if (function == null) {
            throw new IllegalArgumentException("函数不能为空");
        }
        if (left >= right) {
            throw new IllegalArgumentException("搜索区间无效");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("精度必须为正数");
        }

        return ternarySearchMaximum(function, left, right, epsilon);
    }

    /**
     * 三分搜索寻找单峰函数的最小值点。
     *
     * @param function 单峰函数
     * @param left 搜索区间左端点
     * @param right 搜索区间右端点
     * @param epsilon 精度要求
     * @return 最小值点的横坐标
     */
    public static double findMinimum(UnivariateFunction function, double left, double right, double epsilon) {
        if (function == null) {
            throw new IllegalArgumentException("函数不能为空");
        }
        if (left >= right) {
            throw new IllegalArgumentException("搜索区间无效");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("精度必须为正数");
        }

        return ternarySearchMinimum(function, left, right, epsilon);
    }

    /**
     * 递归的三分搜索算法（寻找最大值）。
     * 步骤：
     * 1. 递归基：区间足够小时返回中点
     * 2. 分：计算两个分割点 m1 和 m2
     * 3. 治：比较 f(m1) 和 f(m2)，选择包含最大值的区间
     * 4. 合：递归搜索选定的区间
     *
     * @param function 单峰函数
     * @param left 当前搜索区间左端点
     * @param right 当前搜索区间右端点
     * @param epsilon 精度要求
     * @return 最大值点的横坐标
     */
    private static double ternarySearchMaximum(UnivariateFunction function, double left, double right, double epsilon) {
        // 递归基：区间足够小
        if (right - left < epsilon) {
            return (left + right) / 2;
        }

        // 分：计算两个分割点
        double m1 = left + (right - left) / 3;
        double m2 = right - (right - left) / 3;

        // 治：比较函数值
        double f1 = function.apply(m1);
        double f2 = function.apply(m2);

        // 合：根据比较结果递归搜索
        if (f1 > f2) {
            // 最大值在左侧区间 [left, m2]
            return ternarySearchMaximum(function, left, m2, epsilon);
        } else {
            // 最大值在右侧区间 [m1, right]
            return ternarySearchMaximum(function, m1, right, epsilon);
        }
    }

    /**
     * 递归的三分搜索算法（寻找最小值）。
     *
     * @param function 单峰函数
     * @param left 当前搜索区间左端点
     * @param right 当前搜索区间右端点
     * @param epsilon 精度要求
     * @return 最小值点的横坐标
     */
    private static double ternarySearchMinimum(UnivariateFunction function, double left, double right, double epsilon) {
        // 递归基：区间足够小
        if (right - left < epsilon) {
            return (left + right) / 2;
        }

        // 分：计算两个分割点
        double m1 = left + (right - left) / 3;
        double m2 = right - (right - left) / 3;

        // 治：比较函数值
        double f1 = function.apply(m1);
        double f2 = function.apply(m2);

        // 合：根据比较结果递归搜索
        if (f1 < f2) {
            // 最小值在左侧区间 [left, m2]
            return ternarySearchMinimum(function, left, m2, epsilon);
        } else {
            // 最小值在右侧区间 [m1, right]
            return ternarySearchMinimum(function, m1, right, epsilon);
        }
    }

    /**
     * 迭代版本的三分搜索（寻找最大值）。
     * 避免递归调用，节省栈空间。
     *
     * @param function 单峰函数
     * @param left 搜索区间左端点
     * @param right 搜索区间右端点
     * @param epsilon 精度要求
     * @return 最大值点的横坐标
     */
    public static double findMaximumIterative(UnivariateFunction function, double left, double right, double epsilon) {
        if (function == null) {
            throw new IllegalArgumentException("函数不能为空");
        }
        if (left >= right) {
            throw new IllegalArgumentException("搜索区间无效");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("精度必须为正数");
        }

        while (right - left >= epsilon) {
            double m1 = left + (right - left) / 3;
            double m2 = right - (right - left) / 3;

            if (function.apply(m1) > function.apply(m2)) {
                right = m2;
            } else {
                left = m1;
            }
        }

        return (left + right) / 2;
    }

    /**
     * 迭代版本的三分搜索（寻找最小值）。
     *
     * @param function 单峰函数
     * @param left 搜索区间左端点
     * @param right 搜索区间右端点
     * @param epsilon 精度要求
     * @return 最小值点的横坐标
     */
    public static double findMinimumIterative(UnivariateFunction function, double left, double right, double epsilon) {
        if (function == null) {
            throw new IllegalArgumentException("函数不能为空");
        }
        if (left >= right) {
            throw new IllegalArgumentException("搜索区间无效");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("精度必须为正数");
        }

        while (right - left >= epsilon) {
            double m1 = left + (right - left) / 3;
            double m2 = right - (right - left) / 3;

            if (function.apply(m1) < function.apply(m2)) {
                right = m2;
            } else {
                left = m1;
            }
        }

        return (left + right) / 2;
    }

    /**
     * 带统计信息的三分搜索。
     *
     * @param function 单峰函数
     * @param left 搜索区间左端点
     * @param right 搜索区间右端点
     * @param epsilon 精度要求
     * @param findMaximum true表示寻找最大值，false表示寻找最小值
     * @return 搜索结果
     */
    public static SearchResult searchWithStats(UnivariateFunction function, double left, double right,
            double epsilon, boolean findMaximum) {
        if (function == null) {
            throw new IllegalArgumentException("函数不能为空");
        }
        if (left >= right) {
            throw new IllegalArgumentException("搜索区间无效");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("精度必须为正数");
        }

        int iterations = 0;
        double originalLeft = left;
        double originalRight = right;

        while (right - left >= epsilon) {
            iterations++;
            double m1 = left + (right - left) / 3;
            double m2 = right - (right - left) / 3;

            double f1 = function.apply(m1);
            double f2 = function.apply(m2);

            if (findMaximum) {
                if (f1 > f2) {
                    right = m2;
                } else {
                    left = m1;
                }
            } else {
                if (f1 < f2) {
                    right = m2;
                } else {
                    left = m1;
                }
            }
        }

        double resultX = (left + right) / 2;
        double resultValue = function.apply(resultX);

        return new SearchResult(resultX, resultValue, iterations);
    }

    /**
     * 函数接口，用于定义待搜索的单峰函数。
     */
    @FunctionalInterface
    public interface UnivariateFunction {

        double apply(double x);
    }

    /**
     * 搜索结果类，包含极值点和极值。
     */
    public static class SearchResult {

        public final double x;      // 极值点横坐标
        public final double value;  // 极值
        public final int iterations; // 迭代次数

        public SearchResult(double x, double value, int iterations) {
            this.x = x;
            this.value = value;
            this.iterations = iterations;
        }

        @Override
        public String toString() {
            return String.format("x=%.6f, f(x)=%.6f, iterations=%d", x, value, iterations);
        }
    }
}