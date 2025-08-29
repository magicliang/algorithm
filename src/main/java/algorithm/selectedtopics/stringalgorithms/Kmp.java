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
     * KMP算法构建next数组 - 【双指针建表诀】
     * 
     * 【核心口诀】
     * "i走j追，配则进，不配退，表中记"
     * 
     * 【口诀解读】
     * 📍 i走j追：
     *    - i：主指针，从1开始遍历pattern，负责"走"
     *    - j：追踪指针，从0开始，负责"追"，记录当前最长前后缀长度
     *    - next[0] = 0，起始状态，j归零
     * 
     * ⬆️ 配则进：
     *    - 当 pattern[i] == pattern[j]，字符匹配
     *    - j++，追踪指针前进一步
     *    - 前后缀长度增加1
     * 
     * ⬅️ 不配退：
     *    - 当 pattern[i] ≠ pattern[j]，字符不匹配
     *    - j = next[j-1]，追踪指针退到上一个有效位置
     *    - 利用已建好的表，避免从头开始
     *    - while循环直到匹配或j退到0
     * 
     * 📝 表中记：
     *    - next[i] = j，将当前的前后缀长度记录到表中
     *    - 为后续的匹配和退步提供依据
     * 
     * 【记忆要点】
     * - i是主角，负责遍历；j是配角，负责计数
     * - 匹配就进步，不匹配就退步，每步都要记录
     * - next表就像GPS导航，告诉你退到哪里最合适
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
     * KMP模式匹配 - 【双指针匹配诀】
     * 
     * 【核心口诀】
     * "i扫j跟，配则双进，不配j退，满长即中"
     * 
     * 【口诀解读】
     * 🔍 i扫j跟：
     *    - i：扫描指针，遍历text文本，从0到n-1
     *    - j：跟踪指针，跟踪pattern匹配进度，从0开始
     *    - i负责扫描，j负责跟踪匹配了多少个字符
     * 
     * ⬆️⬆️ 配则双进：
     *    - 当 text[i] == pattern[j]，字符匹配成功
     *    - i++（自动，for循环），j++（手动）
     *    - 双指针同时前进，匹配长度增加
     * 
     * ⬅️ 不配j退：
     *    - 当 text[i] ≠ pattern[j]，字符不匹配
     *    - i继续扫描（不回退），j退到next[j-1]
     *    - while循环让j退到能匹配的位置或退到0
     *    - 利用next表避免i回退，节省时间
     * 
     * 🎯 满长即中：
     *    - 当 j == pattern.length()，跟踪指针达到模式串长度
     *    - 说明完全匹配，return i - m + 1（匹配起始位置）
     *    - 若扫描完毕仍未满长，return -1（未找到）
     * 
     * 【算法精髓】
     * - i只进不退，保证O(n)时间扫描
     * - j能进能退，利用next表智能回退
     * - 总时间复杂度O(n+m)，高效匹配
     * 
     * @param text 待搜索的文本
     * @param pattern 要匹配的模式串
     * @return 匹配的起始位置，未找到返回-1
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
