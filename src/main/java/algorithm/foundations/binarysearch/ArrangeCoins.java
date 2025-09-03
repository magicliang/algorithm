package algorithm.foundations.binarysearch;

/**
 * 排列硬币问题 - 二分查找解法深度分析
 * 
 * 问题描述：
 * 给定n枚硬币，按阶梯状排列，第i行有i枚硬币，求能形成的完整阶梯行数。
 * 例如：n=8时，可以排成3行完整阶梯（1+2+3=6枚），剩余2枚无法形成完整的第4行。
 * 
 * 核心思想：
 * 二分查找满足条件的最大行数k，使得 k*(k+1)/2 <= n
 * 
 * 关键技术点总结：
 * 
 * 1. 整数溢出问题：
 *    - 当n很大时，mid*(mid+1)可能超出int范围
 *    - 解决方案：使用long类型或将除法转换为乘法比较
 *    - 错误写法：int sum = mid*(mid+1)/2
 *    - 正确写法：long sum = (long)mid*(mid+1)/2 或比较 mid*(mid+1) <= 2*n
 * 
 * 2. 二分查找模板选择：
 * 
 *    A. 三分支模板 while(l <= r)：
 *       - 适用于：可能存在精确匹配的查找问题
 *       - 特点：有 ==、>、< 三个分支，可以提前返回精确值
 *       - 边界处理：循环结束时 l = r + 1，需要仔细选择返回l还是r
 *       - 本题中：返回r（最后一个满足sum<=n的位置）
 * 
 *    B. 二分支模板 while(left < right)：
 *       - 适用于：寻找边界、最值问题
 *       - 特点：只有满足/不满足两个分支，逻辑更统一
 *       - 边界处理：循环结束时 left == right，返回值明确
 *       - mid计算：使用 (right-left+1)/2+left 避免死循环
 * 
 * 3. 为什么三分支模板要返回r而不是l？
 *    - 循环结束时：l指向第一个不满足条件的位置，r指向最后一个满足条件的位置
 *    - 我们要找的是最大的满足条件的k值，所以返回r
 *    - 例如n=8：最终l=4(需要10枚硬币>8)，r=3(需要6枚硬币<=8)，答案是3
 * 
 * 4. 模板选择建议：
 *    - 查找特定值：用三分支模板
 *    - 寻找边界/最值：用二分支模板  
 *    - 本题：虽然三分支能工作，但二分支在逻辑上更适合（寻找最大满足条件的值）
 * 
 * 5. 其他解法：
 *    - 数学解法：直接求解二次方程 k^2 + k - 2n <= 0
 *    - 公式：k = floor((-1 + sqrt(1 + 8n)) / 2)
 * 
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class ArrangeCoins {
    
    /**
     * 错误解法 - 存在整数溢出和边界处理问题
     */
    public int arrangeCoinsWrong(int n) {
        int l = 1;
        int r = n;

        while (l <= r) {
            int mid = l + (r - l)/2;
            // 问题1: 整数溢出 - mid*(mid + 1) 可能超出int范围
            int sum = mid*(mid + 1)/2;
            if (sum == n) {
                return mid;
            } else if (sum > n) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        
        /*
         * 为什么返回 r 而不是 l？
         * 
         * 在 while (l <= r) 的二分模板中，循环结束时必然有 l = r + 1
         * 
         * 我们要找的是满足 sum <= n 的最大的 mid 值
         * 
         * 循环过程分析：
         * - 当 sum <= n 时，说明 mid 行数还不够，可能还能再多几行，所以 l = mid + 1
         * - 当 sum > n 时，说明 mid 行数太多了，需要减少，所以 r = mid - 1
         * 
         * 循环结束时的状态：
         * - r 指向最后一个满足 sum <= n 的位置（即我们要的答案）
         * - l 指向第一个满足 sum > n 的位置（即 r + 1）
         * 
         * 举例：n = 8
         * 初始：l=1, r=8
         * mid=4, sum=10 > 8, r=3
         * mid=2, sum=3 <= 8, l=3  
         * mid=3, sum=6 <= 8, l=4
         * 现在 l=4, r=3, l > r 退出循环
         * 
         * 此时：r=3 是答案（3行需要6枚硬币 <= 8）
         *      l=4 不是答案（4行需要10枚硬币 > 8）
         */
        return r;
    }
    
    /**
     * 正确解法1 - 标准二分查找模板
     */
    public int arrangeCoins(int n) {
        int left = 1, right = n;
        while (left < right) {
            // 使用 (right - left + 1) / 2 + left 避免死循环
            int mid = (right - left + 1) / 2 + left;
            // 使用long避免溢出，比较 mid*(mid+1) <= 2*n
            if ((long) mid * (mid + 1) <= (long) 2 * n) {
                left = mid;  // mid可能是答案，保留
            } else {
                right = mid - 1;  // mid太大，排除
            }
        }
        return left;
    }
    
    /**
     * 正确解法2 - 另一种二分模板
     */
    public int arrangeCoins2(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 使用long避免溢出
            long sum = (long) mid * (mid + 1) / 2;
            if (sum <= n) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;  // 返回满足条件的最大值
    }
    
    /**
     * 数学解法 - 直接求解二次方程
     */
    public int arrangeCoinsMath(int n) {
        // k*(k+1)/2 <= n
        // k^2 + k - 2n <= 0
        // k <= (-1 + sqrt(1 + 8n)) / 2
        return (int) ((-1 + Math.sqrt(1 + 8.0 * n)) / 2);
    }
    
    /**
     * 演示为什么返回 r 的详细过程
     */
    public int arrangeCoinsWithTrace(int n) {
        System.out.printf("\n=== 演示 n=%d 的二分查找过程 ===\n", n);
        int l = 1;
        int r = n;
        int step = 0;

        while (l <= r) {
            step++;
            int mid = l + (r - l)/2;
            int sum = mid*(mid + 1)/2;
            
            System.out.printf("第%d步: l=%d, r=%d, mid=%d, sum=%d", step, l, r, mid, sum);
            
            if (sum == n) {
                System.out.printf(" -> sum==n, 直接返回 %d\n", mid);
                return mid;
            } else if (sum > n) {
                System.out.printf(" -> sum>n, r=mid-1=%d\n", mid-1);
                r = mid - 1;
            } else {
                System.out.printf(" -> sum<n, l=mid+1=%d\n", mid+1);
                l = mid + 1;
            }
        }
        
        System.out.printf("循环结束: l=%d, r=%d\n", l, r);
        System.out.printf("返回 r=%d (最后一个满足条件的值)\n", r);
        System.out.printf("如果返回 l=%d，那就是第一个不满足条件的值，错误！\n", l);
        
        return r;
    }
    
    public static void main(String[] args) {
        ArrangeCoins solution = new ArrangeCoins();
        
        // 演示具体的二分查找过程
        System.out.println("=== 演示为什么返回 r 而不是 l ===");
        solution.arrangeCoinsWithTrace(8);
        solution.arrangeCoinsWithTrace(5);
        
        // 测试用例
        int[] testCases = {5, 8, 1, 3, 6, 10};
        
        System.out.println("\n测试结果对比：");
        System.out.println("n\t正确解法\t数学解法");
        System.out.println("--------------------------------");
        
        for (int n : testCases) {
            int result1 = solution.arrangeCoins(n);
            int result2 = solution.arrangeCoinsMath(n);
            System.out.printf("%d\t%d\t\t%d\n", n, result1, result2);
        }
    }
}