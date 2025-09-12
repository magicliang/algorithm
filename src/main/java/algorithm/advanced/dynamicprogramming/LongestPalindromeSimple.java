package algorithm.advanced.dynamicprogramming;

/**
 * 最长回文子串 - 简化版区间DP
 * 
 * 这是一个特殊的区间DP问题，不需要枚举分割点k，因为回文串的结构是固定的：
 * - 如果s[i...j]是回文，那么必须满足：
 *   1. s[i] == s[j] (两端字符相等)
 *   2. s[i+1...j-1]也是回文 (内部子串也是回文)
 * 
 * 与标准区间DP的区别：
 * - 标准区间DP: dp[i][j] = opt(dp[i][k] + dp[k][j]) for all k
 * - 回文DP: dp[i][j] = s[i]==s[j] && dp[i+1][j-1]
 * 
 * 时间复杂度: O(n²)
 * 空间复杂度: O(n²)
 */
public class LongestPalindromeSimple {
    
    /**
     * 寻找最长回文子串 - 区间DP解法（无分割点枚举）
     * 
     * @param s 输入字符串
     * @return 最长回文子串
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int n = s.length();
        int begin = 0, end = 0;
        
        // dp[i][j] 表示 s[i...j] 是否为回文串
        boolean[][] dp = new boolean[n][n];
        
        // 按长度递增的顺序填充DP表
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                
                if (len == 1) {
                    // 单个字符必然是回文
                    dp[i][i] = true;
                    // 更新最长回文子串的位置
                    if (len > end - begin) {
                        begin = i;
                        end = j + 1; // 使用开区间便于substring
                    }
                } else if (len == 2) {
                    // 两个字符的情况
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                    if (dp[i][j] && len > end - begin) {
                        begin = i;
                        end = j + 1;
                    }
                } else {
                    // 长度大于2的情况：两端字符相等 且 内部子串是回文
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
                    if (dp[i][j] && len > end - begin) {
                        begin = i;
                        end = j + 1;
                    }
                }
            }
        }
        
        return s.substring(begin, end);
    }
    
    /**
     * 优化版本：合并len==2的情况到通用逻辑中
     */
    public String longestPalindromeOptimized(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int n = s.length();
        int begin = 0, end = 0;
        boolean[][] dp = new boolean[n][n];
        
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                
                if (len == 1) {
                    dp[i][i] = true;
                } else {
                    // 关键：len <= 2时，dp[i+1][j-1]自动为true（空串或单字符）
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) && (dp[i + 1][j - 1] || len <= 2);
                }
                
                // 更新最长回文子串
                if (dp[i][j] && len > end - begin) {
                    begin = i;
                    end = j + 1;
                }
            }
        }
        
        return s.substring(begin, end);
    }
    
    /**
     * 中心扩展法 - 另一种O(n²)解法
     * 作为对比，展示不同的思路
     */
    public String longestPalindromeCenterExpand(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int start = 0, maxLen = 1;
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文（以i为中心）
            int len1 = expandAroundCenter(s, i, i);
            // 偶数长度回文（以i和i+1为中心）
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
}