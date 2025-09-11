package algorithm.selectedtopics.leetcode.substring;

import java.util.*;

/**
 * 滑动窗口算法实现集合
 * 
 * 本类包含了滑动窗口算法适用性分析与实践指南中提到的所有算法实现：
 * 1. 标准滑动窗口算法
 * 2. 枚举型滑动窗口算法
 * 3. 至多/恰好/至少K个字符的各种实现
 * 
 * @author magicliang
 */
public class SlidingWindowAlgorithms {

    // ==================== 标准滑动窗口算法 ====================
    
    /**
     * 寻找无重复字符的最长子串
     * 
     * 问题：给定一个字符串，找出其中不含有重复字符的最长子串的长度
     * 算法：标准滑动窗口，时间复杂度 O(n)
     * 
     * @param s 输入字符串
     * @return 最长无重复字符子串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        int left = 0;
        int maxLength = 0;
        int[] freq = new int[128]; // 假设字符为ASCII

        for (int right = 0; right < s.length(); right++) {
            // 扩展右边界
            char rightChar = s.charAt(right);
            freq[rightChar]++;

            // 收缩左边界，直到无重复
            while (freq[rightChar] > 1) {
                char leftChar = s.charAt(left);
                freq[leftChar]--;
                left++;
            }

            // 更新结果
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    // ==================== 枚举型滑动窗口算法 ====================
    
    /**
     * 寻找每个字符都至少出现K次的最长子串
     * 
     * 问题：给定字符串s和整数k，找出最长的子串，使得子串中每个字符都至少出现k次
     * 算法：枚举型滑动窗口，时间复杂度 O(n × 26)
     * 
     * @param s 输入字符串
     * @param k 最小出现次数
     * @return 满足条件的最长子串长度
     */
    public int longestSubstring(String s, int k) {
        int maxLen = 0;
        int n = s.length();

        // 外层循环：枚举"最多包含 t 种字符"
        for (int t = 1; t <= 26; t++) {
            int left = 0;
            int[] freq = new int[26]; // 记录26个字母的频次
            int tot = 0; // 当前窗口内字符种类数
            int less = 0; // 频次 < k 的字符种类数

            // 内层循环：标准滑动窗口
            for (int right = 0; right < n; right++) {
                // 1. 扩展右边界
                int charIdx = s.charAt(right) - 'a';
                freq[charIdx]++;
                if (freq[charIdx] == 1) {
                    tot++;   // 新增一种字符
                    less++;  // 它频次=1，肯定不够k
                }
                if (freq[charIdx] == k) {
                    less--;  // 这个字符达标了
                }

                // 2. 收缩左边界：维持"最多t种字符"的约束
                while (tot > t) {
                    int leftCharIdx = s.charAt(left) - 'a';
                    if (freq[leftCharIdx] == k) {
                        less++; // 移除导致该字符频次<k
                    }
                    freq[leftCharIdx]--;
                    if (freq[leftCharIdx] == 0) {
                        tot--;  // 这个字符被移除
                        less--; // 它不再是"频次不足"的字符
                    }
                    left++;
                }

                // 3. 结算：如果所有字符频次都>=k，更新答案
                if (less == 0) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            }
        }

        return maxLen;
    }

    /**
     * 包含所有字符且每个字符频次都在[minFreq, maxFreq]范围内的最长子串
     * 
     * 问题：给定字符串s，以及两个整数minFreq和maxFreq，找出最长的子串，
     *      使得该子串中每个出现的字符的频次都在[minFreq, maxFreq]范围内
     * 算法：枚举型滑动窗口，时间复杂度 O(n × 26)
     * 
     * @param s 输入字符串
     * @param minFreq 最小频次
     * @param maxFreq 最大频次
     * @return 满足条件的最长子串长度
     */
    public int longestValidSubstring(String s, int minFreq, int maxFreq) {
        int maxLen = 0;
        int n = s.length();
        
        // 外层循环：枚举"最多包含 t 种字符"
        for (int t = 1; t <= 26; t++) {
            int left = 0;
            int[] freq = new int[26];
            int distinctCount = 0; // 当前窗口内不同字符种类数
            int validCount = 0;    // 频次在[minFreq, maxFreq]范围内的字符种类数
            
            // 内层循环：标准滑动窗口
            for (int right = 0; right < n; right++) {
                // 1. 扩展右边界
                int charIdx = s.charAt(right) - 'a';
                if (freq[charIdx] == 0) {
                    distinctCount++; // 新增一种字符
                }
                freq[charIdx]++;
                
                // 更新validCount
                if (freq[charIdx] == minFreq) {
                    validCount++; // 这个字符进入有效范围
                } else if (freq[charIdx] == maxFreq + 1) {
                    validCount--; // 这个字符超出有效范围
                }
                
                // 2. 收缩左边界：维持"最多t种字符"的约束
                while (distinctCount > t) {
                    int leftCharIdx = s.charAt(left) - 'a';
                    if (freq[leftCharIdx] == minFreq) {
                        validCount--; // 移除导致该字符频次不足
                    } else if (freq[leftCharIdx] == maxFreq + 1) {
                        validCount++; // 移除使该字符回到有效范围
                    }
                    
                    freq[leftCharIdx]--;
                    if (freq[leftCharIdx] == 0) {
                        distinctCount--; // 这个字符被完全移除
                    }
                    left++;
                }
                
                // 3. 结算：如果所有字符频次都在有效范围内，更新答案
                if (validCount == distinctCount && distinctCount > 0) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            }
        }
        
        return maxLen;
    }

    // ==================== 至多/恰好/至少K个字符问题 ====================
    
    /**
     * 至多包含K个不同字符的最长子串
     * 
     * 问题：给定字符串s和整数k，找出最长的子串，使得子串中最多包含k个不同字符
     * 算法：标准滑动窗口，时间复杂度 O(n)
     * 
     * @param s 输入字符串
     * @param k 最多包含的不同字符数
     * @return 满足条件的最长子串长度
     */
    public int atMostK(String s, int k) {
        if (k == 0) return 0;
        
        int left = 0, maxLen = 0;
        Map<Character, Integer> freq = new HashMap<>();
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            freq.put(rightChar, freq.getOrDefault(rightChar, 0) + 1);
            
            // 违反"至多K个"约束时收缩左边界
            while (freq.size() > k) {
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                if (freq.get(leftChar) == 0) {
                    freq.remove(leftChar);
                }
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    /**
     * 恰好包含K个不同字符的最长子串
     * 
     * 问题：给定字符串s和整数k，找出最长的子串，使得子串中恰好包含k个不同字符
     * 算法：数学转化，时间复杂度 O(n)
     * 
     * @param s 输入字符串
     * @param k 恰好包含的不同字符数
     * @return 满足条件的最长子串长度
     */
    public int exactlyK(String s, int k) {
        if (k == 0) return 0;
        return atMostK(s, k) - atMostK(s, k - 1);
    }

    /**
     * 至少包含K个不同字符的最长子串
     * 
     * 问题：给定字符串s和整数k，找出最长的子串，使得子串中至少包含k个不同字符
     * 算法：枚举型滑动窗口，时间复杂度 O(n × 字符集大小)
     * 
     * @param s 输入字符串
     * @param k 至少包含的不同字符数
     * @return 满足条件的最长子串长度
     */
    public int atLeastK(String s, int k) {
        if (k == 0) return s.length();
        
        int maxLen = 0;
        int n = s.length();
        
        // 枚举"恰好包含t种字符"
        for (int t = k; t <= 26; t++) {
            int left = 0;
            Map<Character, Integer> freq = new HashMap<>();
            
            for (int right = 0; right < n; right++) {
                char rightChar = s.charAt(right);
                freq.put(rightChar, freq.getOrDefault(rightChar, 0) + 1);
                
                // 维持"至多t种字符"的约束
                while (freq.size() > t) {
                    char leftChar = s.charAt(left);
                    freq.put(leftChar, freq.get(leftChar) - 1);
                    if (freq.get(leftChar) == 0) {
                        freq.remove(leftChar);
                    }
                    left++;
                }
                
                // 检查是否满足"至少k种字符"
                if (freq.size() >= k) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            }
        }
        
        return maxLen;
    }

    // ==================== 通用模板方法 ====================
    
    /**
     * 枚举型滑动窗口通用模板
     * 
     * 这是一个抽象的模板方法，展示了枚举型滑动窗口的通用结构
     * 实际使用时需要根据具体问题实现相应的辅助方法
     * 
     * @param s 输入字符串
     * @param target 目标参数
     * @return 问题的解
     */
    public int enumerativeSlidingWindowTemplate(String s, int target) {
        int result = 0;
        
        // 外层：枚举辅助参数
        for (int param = 1; param <= 26; param++) {
            // 内层：标准滑动窗口
            result = Math.max(result, standardSlidingWindow(s, param, target));
        }
        
        return result;
    }

    /**
     * 标准滑动窗口模板的具体实现
     * 
     * @param s 输入字符串
     * @param param 辅助参数
     * @param target 目标参数
     * @return 在给定辅助参数下的最优解
     */
    private int standardSlidingWindow(String s, int param, int target) {
        int left = 0, result = 0;
        // 维护窗口状态的数据结构
        Map<Character, Integer> window = new HashMap<>();
        
        for (int right = 0; right < s.length(); right++) {
            // 1. 扩展右边界，更新窗口状态
            updateWindowOnExpand(window, s.charAt(right));
            
            // 2. 收缩左边界，维持辅助约束
            while (violatesConstraint(window, param)) {
                updateWindowOnShrink(window, s.charAt(left));
                left++;
            }
            
            // 3. 检查是否满足原始条件，更新结果
            if (satisfiesOriginalCondition(window, target)) {
                result = Math.max(result, right - left + 1);
            }
        }
        
        return result;
    }

    // ==================== 辅助方法 ====================
    
    /**
     * 扩展窗口时更新状态
     */
    private void updateWindowOnExpand(Map<Character, Integer> window, char c) {
        window.put(c, window.getOrDefault(c, 0) + 1);
    }

    /**
     * 收缩窗口时更新状态
     */
    private void updateWindowOnShrink(Map<Character, Integer> window, char c) {
        window.put(c, window.get(c) - 1);
        if (window.get(c) == 0) {
            window.remove(c);
        }
    }

    /**
     * 检查是否违反辅助约束
     */
    private boolean violatesConstraint(Map<Character, Integer> window, int param) {
        // 这里以"字符种类数超过param"为例
        return window.size() > param;
    }

    /**
     * 检查是否满足原始条件
     */
    private boolean satisfiesOriginalCondition(Map<Character, Integer> window, int target) {
        // 具体实现取决于问题的原始条件
        // 这里只是一个示例
        return window.size() >= target;
    }

    // ==================== 测试方法 ====================
    
    /**
     * 测试所有算法的正确性
     */
    public static void main(String[] args) {
        SlidingWindowAlgorithms algorithms = new SlidingWindowAlgorithms();
        
        // 测试无重复字符的最长子串
        System.out.println("=== 测试无重复字符的最长子串 ===");
        System.out.println("abcabcbb -> " + algorithms.lengthOfLongestSubstring("abcabcbb")); // 期望: 3
        System.out.println("bbbbb -> " + algorithms.lengthOfLongestSubstring("bbbbb")); // 期望: 1
        System.out.println("pwwkew -> " + algorithms.lengthOfLongestSubstring("pwwkew")); // 期望: 3
        
        // 测试每个字符都至少出现K次的最长子串
        System.out.println("\n=== 测试每个字符都至少出现K次的最长子串 ===");
        System.out.println("aaabb, k=3 -> " + algorithms.longestSubstring("aaabb", 3)); // 期望: 3
        System.out.println("ababbc, k=2 -> " + algorithms.longestSubstring("ababbc", 2)); // 期望: 5
        
        // 测试至多/恰好/至少K个不同字符
        System.out.println("\n=== 测试至多/恰好/至少K个不同字符 ===");
        String testStr = "eceba";
        int k = 2;
        System.out.println("字符串: " + testStr + ", K=" + k);
        System.out.println("至多" + k + "个不同字符: " + algorithms.atMostK(testStr, k));
        System.out.println("恰好" + k + "个不同字符: " + algorithms.exactlyK(testStr, k));
        System.out.println("至少" + k + "个不同字符: " + algorithms.atLeastK(testStr, k));
        
        // 测试频次范围约束
        System.out.println("\n=== 测试频次范围约束 ===");
        System.out.println("aabbcc, [2,2] -> " + algorithms.longestValidSubstring("aabbcc", 2, 2)); // 期望: 6
        System.out.println("aaabbbccc, [2,3] -> " + algorithms.longestValidSubstring("aaabbbccc", 2, 3)); // 期望: 9
    }
}