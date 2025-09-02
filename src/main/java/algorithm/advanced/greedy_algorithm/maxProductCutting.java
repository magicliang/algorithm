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