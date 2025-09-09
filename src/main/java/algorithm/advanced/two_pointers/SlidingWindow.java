package algorithm.advanced.two_pointers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 滑动窗口算法示例
 *
 * 滑动窗口是双指针技巧的一种特殊形式，维护一个窗口，通过移动窗口边界来解决问题。
 * 通常用于字符串或数组的子串/子数组问题。
 *
 * 双指针策略：滑动窗口移动
 * - 右指针扩展窗口，直到满足条件
 * - 左指针收缩窗口，优化结果
 * - 动态调整窗口大小
 *
 * @author magicliang
 * @date 2025-09-02
 */
public class SlidingWindow {

    /**
     * 寻找无重复字符的最长子串
     *
     * 问题描述：给定一个字符串s，请你找出其中不含有重复字符的最长子串的长度。
     *
     * 算法思路：
     * - 使用滑动窗口维护一个无重复字符的窗口
     * - 右指针扩展窗口，左指针收缩窗口
     * - 使用Set记录窗口中的字符
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(min(m,n))，m是字符集大小
     *
     * @param s 输入字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Set<Character> window = new HashSet<>(); // 滑动窗口中的字符
        int left = 0;    // 左指针
        int right = 0;   // 右指针
        int maxLen = 0;  // 最大长度

        while (right < s.length()) {
            char rightChar = s.charAt(right);

            // 如果右指针字符已在窗口中，收缩左边界
            while (window.contains(rightChar)) {
                window.remove(s.charAt(left));
                left++;
            }

            // 扩展右边界
            window.add(rightChar);
            right++;

            // 更新最大长度
            maxLen = Math.max(maxLen, right - left);
        }

        return maxLen;
    }

    /**
     * 最小覆盖子串
     *
     * 问题描述：给你一个字符串s、一个字符串t。返回s中涵盖t所有字符的最小子串。
     * 如果s中不存在涵盖t所有字符的子串，则返回空字符串""。
     *
     * 算法思路：
     * - 使用滑动窗口，右指针扩展直到包含所有t中的字符
     * - 左指针收缩窗口，寻找最小覆盖子串
     * - 使用Map记录字符频次
     *
     * 时间复杂度：O(|s| + |t|)
     * 空间复杂度：O(|s| + |t|)
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return 最小覆盖子串
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || t.length() == 0 || s.length() < t.length()) {
            return "";
        }

        // 统计t中各字符的频次
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>(); // 窗口中字符频次
        int left = 0, right = 0;     // 左右指针
        int valid = 0;               // 窗口中满足need条件的字符个数
        int start = 0, len = Integer.MAX_VALUE; // 最小覆盖子串的起始位置和长度

        while (right < s.length()) {
            char rightChar = s.charAt(right);
            right++;

            // 进行窗口内数据的一系列更新
            if (need.containsKey(rightChar)) {
                window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
                if (window.get(rightChar).equals(need.get(rightChar))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char leftChar = s.charAt(left);
                left++;

                // 进行窗口内数据的一系列更新
                if (need.containsKey(leftChar)) {
                    if (window.get(leftChar).equals(need.get(leftChar))) {
                        valid--;
                    }
                    window.put(leftChar, window.get(leftChar) - 1);
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    /**
     * 固定长度子数组的最大值
     *
     * 问题描述：给定一个数组和一个固定长度k，找出所有长度为k的子数组中的最大值。
     *
     * 算法思路：
     * - 维护一个固定大小为k的滑动窗口
     * - 窗口滑动时更新最大值
     *
     * 时间复杂度：O(n*k)（简单实现）
     * 空间复杂度：O(1)
     *
     * @param nums 输入数组
     * @param k 子数组长度
     * @return 每个长度为k的子数组的最大值数组
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int max = nums[i];
            // 在当前窗口中找最大值
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }

        return result;
    }

    /**
     * 长度最小的子数组
     *
     * 问题描述：给定一个含有n个正整数的数组和一个正整数target。
     * 找出该数组中满足其和≥target的长度最小的连续子数组，并返回其长度。
     * 如果不存在符合条件的子数组，返回0。
     *
     * 算法思路：
     * - 使用滑动窗口，右指针扩展直到和≥target
     * - 左指针收缩窗口，寻找最小长度
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param target 目标和
     * @param nums 输入数组
     * @return 最小长度
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;           // 左指针
        int sum = 0;            // 当前窗口和
        int minLen = Integer.MAX_VALUE; // 最小长度

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right]; // 扩展右边界

            // 当窗口和≥target时，尝试收缩左边界
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}