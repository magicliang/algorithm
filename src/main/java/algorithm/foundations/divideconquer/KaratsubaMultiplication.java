package algorithm.foundations.divideconquer;

/**
 * 大整数乘法算法 - Karatsuba算法实现。
 *
 * 问题描述：
 * 计算两个大整数的乘积。传统的竖式乘法时间复杂度为 O(n²)，
 * 而 Karatsuba 算法通过分治策略将复杂度降低到 O(n^log₂3) ≈ O(n^1.585)。
 *
 * 算法原理：
 * 对于两个 n 位数 x 和 y，将它们分别分为高位和低位两部分：
 * x = x₁ × 10^(n/2) + x₀
 * y = y₁ × 10^(n/2) + y₀
 *
 * 传统方法需要计算4个乘积：x₁y₁, x₁y₀, x₀y₁, x₀y₀
 * Karatsuba 算法巧妙地只需要3个乘积：
 * 1. z₂ = x₁ × y₁
 * 2. z₀ = x₀ × y₀
 * 3. z₁ = (x₁ + x₀) × (y₁ + y₀) - z₂ - z₀
 *
 * 最终结果：x × y = z₂ × 10^n + z₁ × 10^(n/2) + z₀
 *
 * 分治思想体现：
 * 1. 分 (Divide)：将大整数分为高位和低位两部分
 * 2. 治 (Conquer)：递归计算三个较小的乘积
 * 3. 合 (Combine)：根据公式合并结果
 *
 * 时间复杂度：T(n) = 3T(n/2) + O(n) = O(n^log₂3) ≈ O(n^1.585)
 * 空间复杂度：O(n log n)，递归栈深度为 O(log n)，每层需要 O(n) 空间
 *
 * @author magicliang
 * @date 2025-09-09 12:15
 */
public class KaratsubaMultiplication {

    /**
     * Karatsuba 乘法算法主入口。
     * 步骤：
     * 1. 参数验证和预处理
     * 2. 处理特殊情况（0、单位数）
     * 3. 调用递归的 Karatsuba 算法
     * 4. 后处理（去除前导0）
     *
     * @param num1 第一个数字（字符串形式）
     * @param num2 第二个数字（字符串形式）
     * @return 乘积结果（字符串形式）
     */
    public static String karatsubaMultiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.isEmpty() || num2.isEmpty()) {
            throw new IllegalArgumentException("输入数字不能为空");
        }

        // 去除前导0
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        // 处理0的情况
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        // 调用递归算法
        String result = karatsubaMultiplyRec(num1, num2);

        // 去除结果中的前导0
        return removeLeadingZeros(result);
    }

    /**
     * 递归的 Karatsuba 乘法算法。
     * 步骤：
     * 1. 递归基：单位数相乘直接计算
     * 2. 分：将两个数字分为高位和低位
     * 3. 治：递归计算三个乘积 z₂, z₀, z₁
     * 4. 合：根据公式合并结果
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 乘积结果
     */
    private static String karatsubaMultiplyRec(String num1, String num2) {
        // 递归基：单位数相乘
        if (num1.length() == 1 && num2.length() == 1) {
            int product = (num1.charAt(0) - '0') * (num2.charAt(0) - '0');
            return String.valueOf(product);
        }

        // 确保两个数字长度相等（在较短的数字前补0）
        int maxLen = Math.max(num1.length(), num2.length());
        num1 = padWithZeros(num1, maxLen);
        num2 = padWithZeros(num2, maxLen);

        // 如果长度为奇数，再补一个0使其为偶数
        if (maxLen % 2 == 1) {
            maxLen++;
            num1 = "0" + num1;
            num2 = "0" + num2;
        }

        // 分：将数字分为高位和低位
        int mid = maxLen / 2;
        String x1 = num1.substring(0, mid);        // 高位
        String x0 = num1.substring(mid);           // 低位
        String y1 = num2.substring(0, mid);        // 高位
        String y0 = num2.substring(mid);           // 低位

        // 治：递归计算三个乘积
        String z2 = karatsubaMultiplyRec(x1, y1);                    // z₂ = x₁ × y₁
        String z0 = karatsubaMultiplyRec(x0, y0);                    // z₀ = x₀ × y₀

        String sum1 = addStrings(x1, x0);                            // x₁ + x₀
        String sum2 = addStrings(y1, y0);                            // y₁ + y₀
        String z1_temp = karatsubaMultiplyRec(sum1, sum2);           // (x₁ + x₀) × (y₁ + y₀)
        String z1 = subtractStrings(subtractStrings(z1_temp, z2), z0); // z₁ = z1_temp - z₂ - z₀

        // 合：根据公式合并结果
        // result = z₂ × 10^n + z₁ × 10^(n/2) + z₀
        String term1 = multiplyByPowerOf10(z2, maxLen);              // z₂ × 10^n
        String term2 = multiplyByPowerOf10(z1, mid);                 // z₁ × 10^(n/2)
        String term3 = z0;                                           // z₀

        String result = addStrings(addStrings(term1, term2), term3);
        return removeLeadingZeros(result);
    }

    /**
     * 传统的竖式乘法算法，用于对比和验证。
     * 时间复杂度：O(n²)
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 乘积结果
     */
    public static String traditionalMultiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.isEmpty() || num2.isEmpty()) {
            throw new IllegalArgumentException("输入数字不能为空");
        }

        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        int[] result = new int[num1.length() + num2.length()];

        // 从右到左逐位相乘
        for (int i = num1.length() - 1; i >= 0; i--) {
            for (int j = num2.length() - 1; j >= 0; j--) {
                int digit1 = num1.charAt(i) - '0';
                int digit2 = num2.charAt(j) - '0';
                int product = digit1 * digit2;

                int pos1 = i + j;
                int pos2 = i + j + 1;

                int sum = product + result[pos2];
                result[pos2] = sum % 10;
                result[pos1] += sum / 10;
            }
        }

        // 转换为字符串
        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            sb.append(digit);
        }

        return removeLeadingZeros(sb.toString());
    }

    /**
     * 字符串形式的大整数加法。
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 和
     */
    private static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int sum = digit1 + digit2 + carry;

            result.append(sum % 10);
            carry = sum / 10;

            i--;
            j--;
        }

        return result.reverse().toString();
    }

    /**
     * 字符串形式的大整数减法（假设 num1 >= num2）。
     *
     * @param num1 被减数
     * @param num2 减数
     * @return 差
     */
    private static String subtractStrings(String num1, String num2) {
        // 确保 num1 >= num2
        if (compareStrings(num1, num2) < 0) {
            throw new IllegalArgumentException("被减数必须大于等于减数");
        }

        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0) {
            int digit1 = num1.charAt(i) - '0' - borrow;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;

            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(digit1 - digit2);
            i--;
            j--;
        }

        return removeLeadingZeros(result.reverse().toString());
    }

    /**
     * 比较两个字符串形式的数字大小。
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 比较结果：-1(num1 < num2), 0(num1 == num2), 1(num1 > num2)
     */
    private static int compareStrings(String num1, String num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        if (num1.length() < num2.length()) {
            return -1;
        }
        if (num1.length() > num2.length()) {
            return 1;
        }

        return num1.compareTo(num2);
    }

    /**
     * 将数字乘以 10 的幂次。
     *
     * @param num 数字
     * @param power 幂次
     * @return 结果
     */
    private static String multiplyByPowerOf10(String num, int power) {
        if ("0".equals(removeLeadingZeros(num)) || power == 0) {
            return num;
        }

        StringBuilder result = new StringBuilder(num);
        for (int i = 0; i < power; i++) {
            result.append('0');
        }

        return result.toString();
    }

    /**
     * 在数字前补0使其达到指定长度。
     *
     * @param num 数字
     * @param targetLength 目标长度
     * @return 补0后的数字
     */
    private static String padWithZeros(String num, int targetLength) {
        if (num.length() >= targetLength) {
            return num;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetLength - num.length(); i++) {
            sb.append('0');
        }
        sb.append(num);

        return sb.toString();
    }

    /**
     * 去除字符串前导0。
     *
     * @param num 数字字符串
     * @return 去除前导0后的字符串
     */
    private static String removeLeadingZeros(String num) {
        if (num == null || num.isEmpty()) {
            return "0";
        }

        int i = 0;
        while (i < num.length() && num.charAt(i) == '0') {
            i++;
        }

        return (i == num.length()) ? "0" : num.substring(i);
    }
}