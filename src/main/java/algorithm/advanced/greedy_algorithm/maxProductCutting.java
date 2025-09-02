package algorithm.advanced.greedy_algorithm;


/**
 * project name: algorithm
 *
 * description: 最大切分乘积问题
 * 易于证明：
 *
 * 1 无法再切分
 * 2 可以切分成1+1 和保持自己，就乘数因子而言，保持自身更优
 * 3 可以保持自己，切分成 1 + 1 + 1 或者 1 + 2，保持自己更优
 * 4 保持自己和切分成2+2 乘积相同，为了贪心策略的一致性，所有的4都应该切分成2*2
 * 5 另一方面，对于n≥5，切分成2和(n-2)的乘积 2×(n-2) > n，所以n > 4时总是应该切分的
 * 6 易于证明，2 * 3 互为乘积的因子，那么 2+2+2 < 3 * 3，所以3个2是劣于2个3的
 * 7 但是如果最后一个数是1，则1+3不如2*2，又应该让1+3变成2+2
 *
 * 贪心策略：尽量切3，如果n不是3的倍数，余数为1时用2*2替换1*3，余数为2时直接保留
 *
 * 1. 大于等于4的数应该继续拆分。
 * 2. 切分方案不包含1，如果有1，就应该用2 * 2 替换 1 * 3。
 * 3. 最多包含2个2，3个2就能替换为2个3。
 * @author magicliang
 *
 *         date: 2025-09-02 19:32
 */
public class maxProductCutting {

    /**
     * 计算正整数n切分后各部分乘积的最大值
     * 
     * 算法思路：
     * - 基于贪心策略，优先切分出尽可能多的3（数学密度最大）
     * - 处理余数：余数为0直接计算，余数为2保留一个2，余数为1用2*2替换1*3
     * - 时间复杂度：O(log a)，其中a为n除以3的商
     * - 空间复杂度：O(1)
     * 
     * @param n 待切分的正整数，要求n >= 2
     * @return 切分后各部分乘积的最大值
     * 
     * 示例：
     * - maxProductCutting(4) = 4 (切分为2+2，乘积为2*2=4)
     * - maxProductCutting(10) = 36 (切分为3+3+2+2，乘积为3*3*2*2=36)
     */
    public int maxProductCutting(int n) {
        // 其实3是数学密度最大的数，但是题目要求至少切分为2个数，所以3仍然要切分
        if (n <= 3) {
            return 1 * (n-1);
        }
        // 因为3密度最大，所以先切出到底有多少个3
        int a = n/3;
        int b = n % 3;

        if (b == 0) {
            return fastPower(3, a);
        } else if (b == 2) {
            // 2 是次优的数
            return fastPower(3, a) * 2;
        } else {
            // 1 太差了，可以证明 1 * 3 弱于 2 * 2-这是最多保留2个2的例子
            return fastPower(3, a - 1) * 2 * 2;
        }
    }

    /**
     * 快速幂算法实现，计算 n^power
     * 
     * 算法原理：
     * - 基于二进制表示的快速幂算法（平方求幂法）
     * - 通过位运算判断指数的奇偶性，优化计算过程
     * - 时间复杂度：O(log power)
     * - 空间复杂度：O(1)
     * 
     * @param n 底数
     * @param power 指数，要求power >= 0
     * @return n的power次幂
     * 
     * 示例：
     * - fastPower(3, 4) = 81 (3^4)
     * - fastPower(2, 10) = 1024 (2^10)
     */
    int fastPower(int n, int power) {
        int base = n;

        int result = 1;

        // 用 5 作例子记住这个算法
        while (power > 0) {
            // 如果 power 是奇数
            if ((power & 1) == 1) {
                result *= base;
                power--;
            } else {
                base *= base; // base 变大
                power = power >>> 1; // power 右移一位
            }
        }
        return result;
    }
}