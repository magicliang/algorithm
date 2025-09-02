package algorithm.advanced.greedy_algorithm;


/**
 * project name: algorithm
 *
 * description: 最大切分乘积问题
 * 易于证明：
 *
 * 1 无法再切分
 * 2 可以切分成1+1 和保持自己，就乘数因子而言，保持自身更优
 * 3 3 可以保持自己，切分成 1 + 1 + 1 或者 1 + 2，保持自己更优
 * 4 保持自己和切分成2+2 相同，就贪心简单而言，不增加切分点，所有切4都可以保留成2 *2。
 * 5 另一方面，n 只切1不会让自己更大，但是切割 2(n-2) > n 意味着 n  > 4 的时候总能总是应该切的。
 * 6 易于证明，2 * 3 互为乘积的因子，那么 2+2+2 < 3 * 3，所以3个2是劣于2个3的。
 * 7 但是如果最后一个数是1，则1+3不如2*2，又应该让1+3变成2+2
 *
 * 假设我们尽量切3，只要是3的倍数，则我们最后一定得到3的余数、
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
            // 1 太差了，可以证明 1 * 3 弱于 2 * 2
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