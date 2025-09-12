package algorithm.advanced.dynamicprogramming;

/**
 * 最长回文子串问题 - 区间DP经典问题
 * 
 * 问题描述：
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 
 * 区间DP解法思路：
 * dp[i][j] 表示字符串s[i...j]是否为回文串
 * 状态转移：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
 */
public class LongestPalindrome {
    
    /**
     * 区间DP解法 - 找到最长回文子串
     * 时间复杂度：O(n²)
     * 空间复杂度：O(n²)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int n = s.length();
        // dp[i][j]表示s[i...j]是否为回文串
        boolean[][] dp = new boolean[n][n];
        int start = 0; // 最长回文串的起始位置
        int maxLen = 1; // 最长回文串的长度
        
        // 长度为1的子串都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // 长度为2的子串
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
            }
        }
        
        // 长度为3及以上的子串
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                // 状态转移：首尾字符相同且去掉首尾后仍是回文串
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    start = i;
                    maxLen = len;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    /**
     * 中心扩展法 - 另一种O(n²)解法，空间复杂度O(1)
     * 对比区间DP，这种方法更节省空间
     */
    public String longestPalindromeOptimized(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int start = 0, maxLen = 1;
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文串（以i为中心）
            int len1 = expandAroundCenter(s, i, i);
            // 偶数长度回文串（以i和i+1为中心）
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    /**
     * 从中心向两边扩展，找到最长回文串长度
     */
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    /**
     * 带详细过程输出的区间DP版本
     */
    public String longestPalindromeWithProcess(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;
        
        System.out.println("字符串: " + s);
        System.out.println("DP过程:");
        
        // 长度为1
        System.out.println("长度为1的子串:");
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            System.out.printf("  dp[%d][%d] = true ('%c')\n", i, i, s.charAt(i));
        }
        
        // 长度为2
        System.out.println("长度为2的子串:");
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
                System.out.printf("  dp[%d][%d] = true ('%s') - 更新最长回文\n", 
                    i, i + 1, s.substring(i, i + 2));
            } else {
                System.out.printf("  dp[%d][%d] = false ('%s')\n", 
                    i, i + 1, s.substring(i, i + 2));
            }
        }
        
        // 长度为3及以上
        for (int len = 3; len <= n; len++) {
            System.out.println("长度为" + len + "的子串:");
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                String substr = s.substring(i, j + 1);
                
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLen) {
                        start = i;
                        maxLen = len;
                        System.out.printf("  dp[%d][%d] = true ('%s') - 更新最长回文\n", 
                            i, j, substr);
                    } else {
                        System.out.printf("  dp[%d][%d] = true ('%s')\n", i, j, substr);
                    }
                } else {
                    System.out.printf("  dp[%d][%d] = false ('%s')\n", i, j, substr);
                }
            }
        }
        
        String result = s.substring(start, start + maxLen);
        System.out.println("最长回文子串: " + result);
        return result;
    }
    
    /**
     * 测试方法
     */
}