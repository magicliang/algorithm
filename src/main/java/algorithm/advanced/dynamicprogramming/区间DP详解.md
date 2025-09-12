# 区间DP详解

## 1. 区间DP的核心思想

区间DP是一种特殊的动态规划方法，主要用于解决**在一个区间内进行某种操作，使得代价最小或收益最大**的问题。

### 核心特点：
1. **状态定义**：`dp[i][j]` 表示在区间 `[i, j]` 内进行操作的最优值
2. **状态转移**：通过枚举区间内的分割点 `k`，将大区间分解为两个小区间
3. **计算顺序**：先计算小区间，再计算大区间（区间长度从小到大）

## 2. 区间DP标准模板

```java
public class IntervalDP {
    
    /**
     * 区间DP标准模板
     * @param arr 输入数组
     * @return 最优解
     */
    public int intervalDP(int[] arr) {
        int n = arr.length;
        // dp[i][j] 表示区间[i,j]的最优解
        int[][] dp = new int[n][n];
        
        // 初始化：长度为1的区间（根据具体问题决定）
        for (int i = 0; i < n; i++) {
            dp[i][i] = 0; // 或其他初始值
        }
        
        // 枚举区间长度：从2开始到n
        for (int len = 2; len <= n; len++) {
            // 枚举区间起点
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1; // 区间终点
                dp[i][j] = Integer.MAX_VALUE; // 或Integer.MIN_VALUE
                
                // 枚举分割点k，将区间[i,j]分为[i,k]和[k+1,j]
                for (int k = i; k < j; k++) {
                    // 状态转移方程（根据具体问题调整）
                    int cost = dp[i][k] + dp[k+1][j] + calculateCost(i, j, k);
                    dp[i][j] = Math.min(dp[i][j], cost); // 或Math.max
                }
            }
        }
        
        return dp[0][n-1];
    }
    
    // 根据具体问题实现的代价计算函数
    private int calculateCost(int i, int j, int k) {
        // 具体实现
        return 0;
    }
}
```

## 3. 经典问题分析

### 3.1 戳气球问题

**问题描述**：有n个气球，戳破第i个气球获得 `nums[i-1] * nums[i] * nums[i+1]` 枚硬币。

**关键洞察**：不要考虑先戳破哪个，而是考虑**最后戳破哪个**！

```java
public class BurstBalloons {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // 扩展数组，两端添加1
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }
        
        // dp[i][j]表示戳破开区间(i,j)内所有气球的最大收益
        int[][] dp = new int[n + 2][n + 2];
        
        // 区间长度从3开始（至少要有一个气球可戳）
        for (int len = 3; len <= n + 2; len++) {
            for (int i = 0; i <= n + 2 - len; i++) {
                int j = i + len - 1;
                // 枚举最后戳破的气球k（i < k < j）
                for (int k = i + 1; k < j; k++) {
                    int coins = dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[0][n + 1];
    }
}
```

**区间定义技巧**：
- 原数组扩展为 `[1, nums[0], nums[1], ..., nums[n-1], 1]`
- `dp[i][j]` 表示开区间 `(i, j)` 内所有气球被戳破的最大收益
- 区间长度从3开始，因为至少需要3个位置才能戳破1个气球

### 3.2 切木棍问题（你提到的问题）

**问题描述**：长度为n的木棍，在cuts数组指定位置切割，每次切割成本为当前木棍长度。

**关键洞察**：不是在原木棍区间上做DP，而是在**切点区间**上做DP！

```java
public class CuttingStick {
    public int minCost(int n, int[] cuts) {
        int length = cuts.length;
        Arrays.sort(cuts);
        
        // 扩展切点数组，两端添加0和n
        int[] newCuts = new int[length + 2];
        newCuts[0] = 0;
        newCuts[length + 1] = n;
        for (int i = 0; i < length; i++) {
            newCuts[i + 1] = cuts[i];
        }
        
        int newLength = length + 2;
        // dp[i][j]表示在切点区间[i,j]之间进行所有切割的最小成本
        int[][] dp = new int[newLength][newLength];
        
        // 区间长度从2开始（相邻切点无需切割，成本为0）
        for (int len = 2; len <= newLength - 1; len++) {
            for (int i = 0; i + len < newLength; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                
                // 枚举中间切点k
                for (int k = i + 1; k < j; k++) {
                    // 当前切割成本 = 木棍长度 = newCuts[j] - newCuts[i]
                    int cost = dp[i][k] + dp[k][j] + newCuts[j] - newCuts[i];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[0][newLength - 1];
    }
}
```

**区间定义技巧**：
- 切点数组扩展为 `[0, cuts[0], cuts[1], ..., cuts[m-1], n]`
- `dp[i][j]` 表示在切点 `newCuts[i]` 和 `newCuts[j]` 之间进行所有切割的最小成本
- 相邻切点之间无需切割，所以区间长度从2开始

### 3.3 最长回文子串问题

**问题描述**：找到字符串中最长的回文子串。

```java
public class LongestPalindrome {
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n == 0) return "";
        
        // dp[i][j]表示s[i...j]是否为回文串
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;
        
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
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    start = i;
                    maxLen = len;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
}
```

## 4. 区间DP vs 其他DP的区别

### 4.1 切木棍 vs 切钢条

| 问题 | 切木棍 | 切钢条 |
|------|--------|--------|
| **状态定义** | dp[i][j] = 切点区间[i,j]的最小成本 | dp[i] = 长度为i的钢条最大收益 |
| **转移方程** | dp[i][j] = min(dp[i][k] + dp[k][j] + cost) | dp[i] = max(dp[i], dp[j] + price[i-j]) |
| **计算顺序** | 区间长度从小到大 | 长度从小到大 |
| **时间复杂度** | O(n³) | O(n²) |

**核心区别**：
- **切木棍**：每次切割成本是当前木棍长度，需要考虑切割顺序，是区间DP
- **切钢条**：每段钢条有固定价值，不考虑切割顺序，是线性DP

### 4.2 区间长度的"诡异"之处

1. **戳气球**：区间长度从3开始
   - 原因：开区间(i,j)至少需要3个位置才能戳破1个气球

2. **切木棍**：区间长度从2开始
   - 原因：相邻切点之间无需切割，成本为0

3. **回文串**：区间长度从1开始
   - 原因：单个字符就是回文串

## 5. 区间DP解题步骤

1. **识别问题特征**：
   - 在区间内进行操作
   - 操作顺序影响结果
   - 可以分解为子问题

2. **确定状态定义**：
   - 通常是 `dp[i][j]` 表示区间 `[i,j]` 的最优解
   - 注意是闭区间还是开区间

3. **找到状态转移**：
   - 枚举分割点k
   - 考虑在k处进行操作的代价

4. **确定计算顺序**：
   - 区间长度从小到大
   - 保证计算dp[i][j]时，所有子区间已计算

5. **处理边界条件**：
   - 长度为1或2的区间如何处理
   - 是否需要扩展数组

## 6. 常见陷阱与技巧

### 6.1 区间定义陷阱
- **错误**：直接在原数组上定义区间
- **正确**：根据问题特点，可能需要扩展数组或重新定义区间含义

### 6.2 计算顺序陷阱
- **错误**：随意枚举i和j
- **正确**：必须按区间长度从小到大计算

### 6.3 状态转移陷阱
- **错误**：忘记加上当前操作的代价
- **正确**：`dp[i][j] = dp[i][k] + dp[k][j] + currentCost`

## 7. 总结

区间DP的"诡异"之处在于：
1. **区间定义**：不一定是原数组的直接区间，可能需要扩展或重新定义
2. **区间长度**：起始长度根据问题特点确定，不一定从1开始
3. **思维转换**：有时需要逆向思考（如戳气球考虑最后戳破哪个）

掌握这些技巧后，区间DP就不再"诡异"，而是一种强大的解题工具！