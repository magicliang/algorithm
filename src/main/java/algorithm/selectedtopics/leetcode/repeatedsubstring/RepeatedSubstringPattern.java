package algorithm.selectedtopics.leetcode.repeatedsubstring;

/**
 * 重复子串模式问题 - KMP算法解法
 * 
 * 问题描述：
 * 给定一个非空的字符串 s，检查是否可以通过由它的一个子串重复多次构成。
 * 
 * 例如：
 * "abab" -> true (由"ab"重复2次构成)
 * "aba" -> false (无法由重复子串构成)
 * "abcabcabcabc" -> true (由"abc"重复4次构成)
 * 
 * KMP解法核心思想：
 * 利用KMP算法的失配函数(next数组)能够识别字符串内部周期性结构的特性
 */
public class RepeatedSubstringPattern {
    
    /**
     * KMP算法解法 - 最优解
     * 
     * 核心洞察：
     * 1. 如果字符串由重复子串构成，那么它具有周期性
     * 2. KMP的失配函数恰好能捕捉到这种周期性
     * 3. next[n-1]表示整个字符串的最长相等前后缀长度
     * 4. 重复单元长度 = n - next[n-1]
     * 5. 判断条件：字符串长度能被重复单元长度整除
     * 
     * 数学原理：
     * - 设字符串长度为n，重复单元长度为k
     * - 如果由重复子串构成，则next[n-1] = n - k
     * - 因此k = n - next[n-1]
     * - 必须满足：n % k == 0，即 n % (n - next[n-1]) == 0
     * 
     * 充分必要性证明：
     * 判断条件：next[n-1] > 0 && n % (n - next[n-1]) == 0
     * 
     * 【必要性】(重复字符串 → 条件成立)：
     * 设字符串S由长度k的子串P重复m次构成，即S = P^m，n = m*k
     * - 则S[0...n-k-1] = S[k...n-1] (前n-k个字符等于后n-k个字符)
     * - 这意味着next[n-1] ≥ n-k
     * - 由于k是最小重复周期，所以next[n-1] = n-k
     * - 因此：k = n - next[n-1]，且n % k = 0
     * - 同时next[n-1] = n-k > 0 (因为k < n，至少重复2次)
     * 
     * 【充分性】(条件成立 → 重复字符串)：
     * 设next[n-1] > 0且n % (n-next[n-1]) = 0，令k = n - next[n-1]
     * - 由next[n-1] = n-k，知S[0...n-k-1] = S[k...n-1]
     * - 这表示S[i] = S[i+k]对所有0 ≤ i ≤ n-k-1成立
     * - 由于n % k = 0，可以证明S[i] = S[i+k]对所有有效i成立
     * - 因此S = S[0...k-1]^(n/k)，即S由长度k的子串重复n/k次构成
     * 
     * 结论：该条件是重复字符串的充分必要条件，不仅仅是"可能"，而是"一定"！
     * 
     * 时间复杂度：O(n) - KMP预处理
     * 空间复杂度：O(n) - next数组
     */
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        
        // 构建KMP失配函数(next数组)
        int[] next = buildNext(s);
        
        // 获取整个字符串的最长相等前后缀长度
        int longestPrefixSuffix = next[n - 1];
        
        // 计算可能的重复单元长度
        int repeatUnitLength = n - longestPrefixSuffix;
        
        // 关键判断：字符串长度必须能被重复单元长度整除
        // 且重复单元长度不能等于字符串长度(至少要重复2次)
        return longestPrefixSuffix > 0 && n % repeatUnitLength == 0;
    }
    
    /**
     * 构建KMP算法的失配函数(next数组)
     * 
     * next[i]表示字符串s[0...i]的最长相等前后缀长度
     * 
     * 算法过程：
     * 1. next[0] = 0 (单个字符没有真前后缀)
     * 2. 对于每个位置i，寻找最长的相等前后缀
     * 3. 利用已计算的next值来加速计算
     * 
     * 为什么KMP能识别重复模式？
     * 当字符串由重复子串构成时，会形成规律的前后缀匹配模式
     * 例如："abcabc" -> next = [0,0,0,1,2,3]
     * next[5] = 3，意味着前3个字符"abc"与后3个字符"abc"相等
     */
    private int[] buildNext(String s) {
        int n = s.length();
        int[] next = new int[n];
        
        // 第一个字符的next值必为0
        next[0] = 0;
        
        // j指向当前最长相等前后缀的前缀末尾
        int j = 0;
        
        // i指向当前处理的字符位置
        for (int i = 1; i < n; i++) {
            // 当字符不匹配时，利用已计算的next值回退
            // 这是KMP算法的核心：避免重复比较
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];  // 回退到更短的相等前后缀
            }
            
            // 如果字符匹配，扩展当前的相等前后缀
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            
            // 记录当前位置的最长相等前后缀长度
            next[i] = j;
        }
        
        return next;
    }
    
    /**
     * 暴力解法 - 用于对比和理解
     * 
     * 思路：
     * 1. 枚举所有可能的重复单元长度(1到n/2)
     * 2. 对每个长度，检查是否能构成重复模式
     * 
     * 时间复杂度：O(n²)
     * 空间复杂度：O(1)
     */
    public boolean repeatedSubstringPatternBruteForce(String s) {
        int n = s.length();
        
        // 枚举重复单元长度，最大为n/2(至少重复2次)
        for (int len = 1; len <= n / 2; len++) {
            // 字符串长度必须是重复单元长度的倍数
            if (n % len != 0) {
                continue;
            }
            
            // 检查是否真的由该长度的子串重复构成
            boolean isRepeated = true;
            String pattern = s.substring(0, len);  // 重复单元
            
            // 检查每个重复单元是否都相等
            for (int i = len; i < n; i += len) {
                if (!s.substring(i, i + len).equals(pattern)) {
                    isRepeated = false;
                    break;
                }
            }
            
            if (isRepeated) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 字符串拼接解法 - 另一种巧妙思路
     * 
     * 核心思想：
     * 如果s由重复子串构成，那么s一定是(s+s)的子串(除了开头和结尾位置)
     * 
     * 原理：
     * 设s = "abcabc"，由"abc"重复构成
     * s+s = "abcabcabcabc"
     * 在s+s中去掉首尾字符后："bcabcabcab"
     * 原字符串"abcabc"仍然出现在其中
     * 
     * 时间复杂度：O(n) - 字符串查找
     * 空间复杂度：O(n) - 拼接字符串
     */
    public boolean repeatedSubstringPatternConcat(String s) {
        String doubled = s + s;
        // 去掉首尾字符，避免在原位置找到自己
        String sub = doubled.substring(1, doubled.length() - 1);
        return sub.contains(s);
    }
    
    /**
     * 演示和测试方法
     */
    public static void main(String[] args) {
        RepeatedSubstringPattern solution = new RepeatedSubstringPattern();
        
        System.out.println("🔄 重复子串模式问题 - KMP算法解法演示");
        System.out.println("=".repeat(50));
        
        // 测试用例
        String[] testCases = {
            "abab",           // true: "ab"重复2次
            "aba",            // false: 无法重复构成
            "abcabcabcabc",   // true: "abc"重复4次
            "a",              // false: 单字符无法重复
            "aa",             // true: "a"重复2次
            "aaa",            // true: "a"重复3次
            "abcabc",         // true: "abc"重复2次
            "abcabcabc",      // true: "abc"重复3次
            "abcdefg",        // false: 无重复模式
            "aabaaba"         // true: "aab"重复2次 + "a"
        };
        
        System.out.println("\n📋 测试结果对比");
        System.out.println("-".repeat(30));
        
        for (String test : testCases) {
            boolean kmpResult = solution.repeatedSubstringPattern(test);
            boolean bruteResult = solution.repeatedSubstringPatternBruteForce(test);
            boolean concatResult = solution.repeatedSubstringPatternConcat(test);
            
            System.out.printf("字符串: %-15s | KMP: %s | 暴力: %s | 拼接: %s%n", 
                "\"" + test + "\"", kmpResult, bruteResult, concatResult);
            
            // 验证结果一致性
            if (kmpResult != bruteResult || bruteResult != concatResult) {
                System.out.println("❌ 结果不一致！");
            }
        }
        
        // KMP算法详细演示
        System.out.println("\n🔍 KMP算法详细演示");
        System.out.println("-".repeat(30));
        
        demonstrateKMP(solution, "abcabc");
        demonstrateKMP(solution, "abab");
        demonstrateKMP(solution, "aba");
        
        // 算法复杂度对比
        System.out.println("\n📊 算法复杂度对比");
        System.out.println("-".repeat(30));
        printComplexityComparison();
    }
    
    /**
     * 演示KMP算法的详细过程
     */
    private static void demonstrateKMP(RepeatedSubstringPattern solution, String s) {
        System.out.println("\n字符串: \"" + s + "\"");
        
        // 构建next数组
        int[] next = solution.buildNext(s);
        
        // 显示next数组
        System.out.print("索引:   ");
        for (int i = 0; i < s.length(); i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        
        System.out.print("字符:   ");
        for (char c : s.toCharArray()) {
            System.out.printf("%2c ", c);
        }
        System.out.println();
        
        System.out.print("next:   ");
        for (int val : next) {
            System.out.printf("%2d ", val);
        }
        System.out.println();
        
        // 分析结果
        int n = s.length();
        int longestPS = next[n - 1];
        int repeatLen = n - longestPS;
        
        System.out.println("最长相等前后缀长度: " + longestPS);
        System.out.println("重复单元长度: " + repeatLen);
        System.out.println("是否能整除: " + (n % repeatLen == 0));
        System.out.println("是否有重复模式: " + (longestPS > 0 && n % repeatLen == 0));
        
        if (longestPS > 0 && n % repeatLen == 0) {
            System.out.println("重复单元: \"" + s.substring(0, repeatLen) + "\"");
        }
    }
    
    /**
     * 打印算法复杂度对比
     */
    private static void printComplexityComparison() {
        System.out.println("┌─────────────┬─────────────┬─────────────┬─────────────┐");
        System.out.println("│    算法     │ 时间复杂度  │ 空间复杂度  │    特点     │");
        System.out.println("├─────────────┼─────────────┼─────────────┼─────────────┤");
        System.out.println("│ KMP算法     │    O(n)     │    O(n)     │ 最优解法    │");
        System.out.println("│ 暴力枚举    │    O(n²)    │    O(1)     │ 直观易懂    │");
        System.out.println("│ 字符串拼接  │    O(n)     │    O(n)     │ 思路巧妙    │");
        System.out.println("└─────────────┴─────────────┴─────────────┴─────────────┘");
        
        System.out.println("\n💡 KMP算法优势:");
        System.out.println("• 时间复杂度最优: O(n)");
        System.out.println("• 利用字符串内在结构特性");
        System.out.println("• 一次预处理即可得到答案");
        System.out.println("• 体现了算法设计的数学美感");
    }
}

/**
 * 总结：
 * 
 * KMP解决重复子串问题的核心在于：
 * 1. 重复模式本质上是字符串的周期性结构
 * 2. KMP的失配函数能够精确捕捉这种周期性
 * 3. 通过数学关系 n % (n - next[n-1]) == 0 来判断
 * 4. 避免了暴力枚举的重复计算，达到最优时间复杂度
 * 
 * 这个问题展示了KMP算法的强大之处：
 * 不仅能用于模式匹配，还能解决字符串结构分析问题
 */