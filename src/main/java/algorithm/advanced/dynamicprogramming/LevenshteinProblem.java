package algorithm.advanced.dynamicprogramming;


/**
 * project name: algorithm
 *
 * description: 这个问题是说有个 source 字符串，有个 target 字符串，求要用最少多少步，能干把 source 变成 target
 *
 * 这个问题是从 source 的结尾开始遍历，通过比对 source 的子问题和 target 的子问题来比对最终的解。
 * 假设 dp[i, j] 意味着共i个字符串变成j个字符串需要的最少步数，则我们对比如下：
 * 1. 如果当前子问题的结尾的字符相同：则不需要变更-变更操作为0，当前的子问题自动替换为子问题的最优解，没有累加步骤。
 * 2. 如果当前子问题的结尾字符不一样，则需要编辑：
 *   2.1 如果要把 t[j-1] 插入 s[i-1] 后，则 dp [i, j] = dp[i, j - 1] + 1。i不变，但是目标j实际上变少了。
 *   2.2 如果要把 s[i-1] 删除，则 dp[i, j] = dp[i - 1, j] + 1。j 不变，但是 i 变少了。
 *   2.3 如果要把 s[i-1] 替换为 t[j-1]，则 dp[i, j] = dp[i - 1, j - 1] + 1。i 和 j 都变少了。
 *
 * 总结，如果要做选择：首先做一层选择：要不要做出编辑，这代表两个逻辑分支。
 *
 * 其中，做出编辑的选择意味着三个分叉选择。所以一共有4个逻辑分支。
 *
 * @author magicliang
 *
 *         date: 2025-09-01 19:20
 */
public class LevenshteinProblem {

    public int editDistanceDfs(String s, String t) {

          // 0 字符串的变换需要全部另一个字符串的字符数
          if (s == null || s.isEmpty()) {
              return t.length();
          }

        if (t == null || t.isEmpty()) {
              return s.length();
        }
        // 先解决尾部问题
        final int n = s.length();
        final int m = t.length();

        char last1 = s.charAt(n-1);
        char last2 = t.charAt(n-1);
        if (last1 == last2) {
            return editDistanceDfs(s.substring(0, n -1), t.substring(0, m - 1));
        }

        int insert = editDistanceDfs(s, t.substring(m -1)) + 1;
        int delete = editDistanceDfs(s.substring(0, n -1), t) + 1;
        int update = editDistanceDfs(s.substring(0, n -1), t.substring(m -1)) + 1;

        return Math.min(Math.min(insert, delete), update);
    }

}
