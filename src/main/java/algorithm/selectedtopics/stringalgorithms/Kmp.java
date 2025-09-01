package algorithm.selectedtopics.stringalgorithms;

/**
 * project name: domain-driven-transaction-sys
 *
 * description:
 *
 * @author magicliang
 *
 *         date: 2025-08-15 20:43
 */
public class Kmp {

    /**
     * KMP算法构建next数组口诀：
     * 
     * 【核心口诀】
     * "j追i走，匹配j增，失配j跳，next记录"
     * 
     * 【口诀详解】
     * 1. j追i走：
     *    - i从1开始遍历pattern，j从0开始追踪
     *    - i是主角负责走，j是配角负责追
     *    - next[0] = 0，初始状态
     * 
     * 2. 匹配j增：
     *    - 当pattern[i] == pattern[j]时
     *    - j++，匹配长度增加
     *    - 前后缀可以延长
     * 
     * 3. 失配j跳：
     *    - 当pattern[i] ≠ pattern[j]时
     *    - j = next[j-1]，跳到上一个匹配位置
     *    - while循环直到匹配或j=0
     *    - 利用已有信息，避免从头开始
     * 
     * 4. next记录：
     *    - next[i] = j，记录当前最长前后缀长度
     *    - 每个位置都要记录，供后续使用
     * 
     * @param pattern 模式串
     * @return next数组，存储每个位置的最长相等前后缀长度
     */
    public int[] buildNextArray(String pattern) {
        int m = pattern.length();
        if (m == 0) {
            return new int[0];
        }

        int[] next = new int[m];
        // next[i] 的定义：存储子串 pattern[0...i] 的「最长相等前后缀」的长度。
        // "前缀"指不包含最后一个字符的子串，"后缀"指不包含第一个字符的子串。
        next[0] = 0; // 长度为1的串，没有真前缀和真后缀，长度为0。

        // j 的双重含义：
        // 1. (作为值) 它代表 pattern[0...i-1] 的最长相等前后缀的长度。
        // 2. (作为下标) 它指向下一个需要与后缀部分进行比较的前缀字符的位置。
        int j = 0;

        // i 遍历模式串，计算每个位置的 next 值。
        for (int i = 1; i < m; i++) { // 因为 next[0] 已经初始化为 0，所以当然是从1开始
            // 核心：当字符不匹配时，j 需要向前回退。
            // 比如 "ababc"，当 i=4 (c)，此时 j=2 (代表 "ab")。
            // 我们比较 pattern[i] (c) 和 pattern[j] (a)，发现不匹配。
            // 意味着以 'c' 结尾的后缀，无法与以 'a' 结尾的前缀 "aba" 匹配。
            // 我们需要缩短前缀的长度，寻找次优选择。
            // 新的前缀长度就是 next[j-1]，即 "ab" 的 next 值，也就是 next[1]=0。
            // j 就回退到了 0。
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            // 当字符匹配时，或者 j 回退到 0 后，进行判断。
            // 比如 "abab"，当 i=3 (b)，此时 j=1 (代表 "a")。
            // 我们比较 pattern[i] (b) 和 pattern[j] (b)，发现匹配。
            // 这意味着原来的最长前缀 "a" 可以被扩展为 "ab"。
            // 所以我们将 j 增加 1。
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 将计算出的最长相等前后缀长度 j，赋值给 next[i]。
            next[i] = j;
        }

        return next;
    }

    /**
     * KMP模式匹配口诀：
     * 
     * 【核心口诀】
     * "i扫j追，匹配双进，失配j退，满长成功"
     * 
     * 【口诀详解】
     * 1. i扫j追：
     *    - i扫描text文本，从0到n-1
     *    - j追踪pattern匹配进度，从0开始
     *    - i负责扫描，j负责计数
     * 
     * 2. 匹配双进：
     *    - 当text[i] == pattern[j]时
     *    - i自动前进（for循环），j手动前进（j++）
     *    - 双指针同时前进，匹配长度增加
     * 
     * 3. 失配j退：
     *    - 当text[i] ≠ pattern[j]时
     *    - i继续扫描不后退，j利用next数组后退
     *    - j = next[j-1]，退到最佳位置
     *    - while循环直到匹配或j=0
     * 
     * 4. 满长成功：
     *    - 当j == pattern.length()时，完全匹配
     *    - return i - m + 1，返回匹配起始位置
     *    - 扫描完毕未匹配则return -1
     * 
     * 时间复杂度：O(n + m)，其中n为文本长度，m为模式长度
     *
     * @param text 待搜索文本
     * @param pattern 要匹配的模式
     * @return 找到就返回起始下标；没找到返回 -1
     */
    public int search(String text, String pattern) {
        // 处理空模式串的情况
        if (pattern == null || pattern.length() == 0) {
            return 0; // 空字符串在任何位置都匹配，返回0
        }

        // 处理空文本的情况
        if (text == null || text.length() == 0) {
            return -1;
        }

        int[] next = buildNextArray(pattern);
        int m = pattern.length();
        int n = text.length();
        int j = 0;

        // 易错的点：i < m - n + 1 是错的，i必须遍历完整个text，也就是 i <
        // n。为什么？因为i代表的是当前正在处理的文本字符位置（用于与pattern[j]进行比较），而不是最初匹配的字符位置，匹配可能在text的任何位置开始，所以必须遍历完全部的text才能确保不遗漏任何可能的匹配
        for (int i = 0; i < n; i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1]; // 最大共同前缀长度回退
            }

            // 至少能干延长一点点，存在某个共同前后缀
            if (text.charAt(i) == pattern.charAt(j)) {
                j++; // j 要得到补偿
            }

            // 如果前缀长度达到 m 的水平
            if (j == m) {
                return i - m + 1; // 当前的 i 是长度为m 的 pattern 字符串的末尾，要找到这个字符串的开头
            }
        }

        return -1;
    }

}
