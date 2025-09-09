package algorithm.advanced.greedy_algorithm;


/**
 * project name: algorithm
 *
 * description: 最大容量问题（盛最多水的容器）
 *
 * 问题描述：
 * 给定一个数组，其中的每个元素代表一个垂直隔板的高度。数组中的任意两个隔板，
 * 以及它们之间的空间可以组成一个容器。容器的容量等于高度和宽度的乘积（面积），
 * 其中高度由较短的隔板决定，宽度是两个隔板的数组索引之差。
 * 请在数组中选择两个隔板，使得组成的容器的容量最大，返回最大容量。
 *
 * 贪心算法认知：
 * 1. 贪心策略：每次移动较短的板子
 * 2. 贪心选择的正确性：
 * - 当前左指针在i，右指针在j，且height[i] < height[j]
 * - 如果移动右指针到j-1, j-2...，由于高度仍受限于height[i]，而宽度减小，容量只会更小
 * - 因此移动短板是唯一可能找到更优解的选择
 * 3. 最优子结构：每次贪心选择都能保证不会错过最优解
 * 4. 这是一种能够保证找到全局最优解的贪心算法，不是"尝试性"贪心
 *
 * 双指针策略：
 * 初始化两指针，使其分列容器两端，每轮向内收缩短板对应的指针，直至两指针相遇。
 *
 * 算法思路：
 * 1. 使用双指针贪心策略，左指针从0开始，右指针从末尾开始
 * 2. 每次计算当前容器的容量：(右指针-左指针) * min(左高度, 右高度)
 * 3. 移动较短板对应的指针，因为移动长板不可能得到更大的容量
 * 4. 重复直到两指针相遇
 *
 * 时间复杂度：O(n) - 每个元素最多被访问一次
 * 空间复杂度：O(1) - 只使用常数额外空间
 *
 * @author magicliang
 *
 *         date: 2025-09-02 17:22
 */
public class MaxCapacityProblem {

    /**
     * 计算最大容器容量
     *
     * @param height 隔板高度数组
     * @return 最大容量
     */
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int l = 0;                    // 左指针
        int r = height.length - 1;    // 右指针
        int result = 0;               // 最大容量

        while (l < r) {
            // 计算当前容器容量：宽度 * 高度（由短板决定）
            int currentArea = (r - l) * Math.min(height[l], height[r]);
            result = Math.max(result, currentArea);

            // 与普通双指针不一样的地方是，我们此时应该移动的是哪一个指针呢？
            // 应该移动两个 height 里面更小的那个。因为收缩区间必然带来 width 的损失，所以我们更不能损失 height 了
            if (height[l] < height[r]) {
                l++;  // 移动左指针（短板）
            } else {
                r--;  // 移动右指针（短板或等高）
            }
            // 无需去重，进行下一轮比对
        }

        return result;
    }
}
