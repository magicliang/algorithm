package algorithm.selectedtopics.leetcode.substring;

/**
 * 分治法解决字符串子串问题
 * 
 * 本类专门实现使用分治策略解决字符串相关问题，与滑动窗口算法形成对比
 * 
 * @author magicliang
 */
public class DivideAndConquerSubstring {
    
    /**
     * LeetCode 395: 至少有K个重复字符的最长子串 - 分治法实现
     * 
     * 问题描述：
     * 给你一个字符串 s 和一个整数 k，请你找出 s 中的最长子串，
     * 要求该子串中的每一字符出现次数都不少于 k。返回这一子串的长度。
     * 
     * 分治思想：
     * 对于字符串 s，如果存在某个字符 ch，它的出现次数大于 0 且小于 k，
     * 则任何包含 ch 的子串都不可能满足要求。
     * 因此，我们可以将字符串按照 ch 切分成若干段，
     * 满足要求的最长子串一定出现在某个被切分的段内。
     * 
     * 时间复杂度：O(N * 字符集大小)，最坏情况下每层递归都只能排除一个字符
     * 空间复杂度：O(字符集大小)，递归栈深度最多为字符集大小
     * 
     * @param s 输入字符串
     * @param k 最小重复次数
     * @return 满足条件的最长子串长度
     */
    public int longestSubstringDivideConquer(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return divideAndConquer(s, 0, s.length() - 1, k);
    }
    
    /**
     * 分治递归实现
     * 
     * @param s 字符串
     * @param left 左边界（包含）
     * @param right 右边界（包含）
     * @param k 最小重复次数
     * @return 当前区间内满足条件的最长子串长度
     */
    private int divideAndConquer(String s, int left, int right, int k) {
        // 基础情况：区间长度小于k，不可能满足条件
        if (right - left + 1 < k) {
            return 0;
        }
        
        // 统计当前区间内每个字符的频次
        int[] freq = new int[26];
        for (int i = left; i <= right; i++) {
            freq[s.charAt(i) - 'a']++;
        }
        
        // 寻找"问题字符"：出现次数 > 0 且 < k 的字符
        for (int i = left; i <= right; i++) {
            char ch = s.charAt(i);
            int charFreq = freq[ch - 'a'];
            
            // 发现问题字符，以它为分割点进行分治
            if (charFreq > 0 && charFreq < k) {
                // 递归处理左半部分
                int leftResult = divideAndConquer(s, left, i - 1, k);
                // 递归处理右半部分  
                int rightResult = divideAndConquer(s, i + 1, right, k);
                // 返回两部分的最大值
                return Math.max(leftResult, rightResult);
            }
        }
        
        // 如果没有找到问题字符，说明当前区间内所有字符频次都 >= k
        // 整个区间都满足条件
        return right - left + 1;
    }
    
    /**
     * 分治法的另一种实现：显式收集分割段
     * 
     * 这种实现更直观地展示了"按问题字符分割"的思想
     * 
     * @param s 输入字符串
     * @param k 最小重复次数
     * @return 满足条件的最长子串长度
     */
    public int longestSubstringDivideConquerV2(String s, int k) {
        if (s == null || s.length() < k) {
            return 0;
        }
        
        // 统计字符频次
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // 寻找第一个问题字符
        char splitChar = 0;
        for (char c : s.toCharArray()) {
            if (freq[c - 'a'] > 0 && freq[c - 'a'] < k) {
                splitChar = c;
                break;
            }
        }
        
        // 如果没有问题字符，整个字符串都满足条件
        if (splitChar == 0) {
            return s.length();
        }
        
        // 按问题字符分割字符串，递归处理每个段
        int maxLen = 0;
        String[] segments = s.split(String.valueOf(splitChar));
        for (String segment : segments) {
            maxLen = Math.max(maxLen, longestSubstringDivideConquerV2(segment, k));
        }
        
        return maxLen;
    }
    
    /**
     * 演示分治过程的辅助方法
     * 
     * @param s 输入字符串
     * @param k 最小重复次数
     * @return 满足条件的最长子串长度
     */
    public int longestSubstringWithTrace(String s, int k) {
        System.out.println("=== 分治法求解过程演示 ===");
        System.out.println("输入字符串: \"" + s + "\", k = " + k);
        int result = divideAndConquerWithTrace(s, 0, s.length() - 1, k, 0);
        System.out.println("最终结果: " + result);
        return result;
    }
    
    /**
     * 带跟踪信息的分治实现
     */
    private int divideAndConquerWithTrace(String s, int left, int right, int k, int depth) {
        StringBuilder indentBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indentBuilder.append("  ");
        }
        String indent = indentBuilder.toString();
        String currentSegment = s.substring(left, right + 1);
        System.out.println(indent + "处理区间 [" + left + ", " + right + "]: \"" + currentSegment + "\"");
        
        if (right - left + 1 < k) {
            System.out.println(indent + "区间长度 < k，返回 0");
            return 0;
        }
        
        // 统计字符频次
        int[] freq = new int[26];
        for (int i = left; i <= right; i++) {
            freq[s.charAt(i) - 'a']++;
        }
        
        // 寻找问题字符
        for (int i = left; i <= right; i++) {
            char ch = s.charAt(i);
            int charFreq = freq[ch - 'a'];
            
            if (charFreq > 0 && charFreq < k) {
                System.out.println(indent + "发现问题字符 '" + ch + "' (频次=" + charFreq + " < " + k + ")，进行分割");
                
                int leftResult = divideAndConquerWithTrace(s, left, i - 1, k, depth + 1);
                int rightResult = divideAndConquerWithTrace(s, i + 1, right, k, depth + 1);
                int result = Math.max(leftResult, rightResult);
                
                System.out.println(indent + "左半部分结果: " + leftResult + ", 右半部分结果: " + rightResult + ", 取最大值: " + result);
                return result;
            }
        }
        
        System.out.println(indent + "所有字符频次都 >= " + k + "，整个区间满足条件，长度: " + (right - left + 1));
        return right - left + 1;
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        DivideAndConquerSubstring solution = new DivideAndConquerSubstring();
        
        // 测试用例1
        String s1 = "aaabb";
        int k1 = 3;
        System.out.println("测试1: s=\"" + s1 + "\", k=" + k1);
        System.out.println("结果: " + solution.longestSubstringDivideConquer(s1, k1));
        System.out.println();
        
        // 测试用例2
        String s2 = "ababbc";
        int k2 = 2;
        System.out.println("测试2: s=\"" + s2 + "\", k=" + k2);
        System.out.println("结果: " + solution.longestSubstringDivideConquer(s2, k2));
        System.out.println();
        
        // 演示分治过程
        System.out.println("=== 分治过程演示 ===");
        solution.longestSubstringWithTrace("ababbc", 2);
    }
}