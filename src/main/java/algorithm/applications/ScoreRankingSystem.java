package algorithm.applications;

import java.util.Arrays;

/**
 * 分数统计和排名查询系统
 * 
 * 这是您提供的前缀和问题的完美应用场景！
 * 
 * 核心思想：
 * 1. 使用计数数组统计每个分数的学生人数
 * 2. 构建前缀和数组实现O(1)时间复杂度的排名查询
 * 3. 支持各种统计分析功能
 * 
 * 原始代码片段：
 * ```java
 * int[] scores; // 存储着所有同学的分数
 * // 试卷满分 100 分
 * int[] count = new int[100 + 1];
 * // 记录每个分数有几个同学
 * for (int score : scores)
 *     count[score]++;
 * // 构造前缀和
 * for (int i = 1; i < count.length; i++)
 *     count[i] = count[i] + count[i-1];
 * ```
 * 
 * 应用场景：
 * - 考试成绩统计与排名查询
 * - 竞赛分数分析
 * - 学生成绩管理系统
 * - 分数分布统计
 * 
 * 时间复杂度：
 * - 构建：O(n + k)，其中n是学生数量，k是分数范围(0-100)
 * - 查询：O(1)
 * 
 * 空间复杂度：O(k) = O(101)
 * 
 * @author CodeBuddy
 */
public class ScoreRankingSystem {
    
    private final int[] count;      // 每个分数的人数统计 - 对应您的原始count数组
    private final int[] prefixSum;  // 前缀和数组，用于快速排名查询
    private final int maxScore;     // 最大分数
    private int totalStudents;      // 总学生数
    
    /**
     * 构造函数
     * @param maxScore 试卷满分，默认100分
     */
    public ScoreRankingSystem(int maxScore) {
        this.maxScore = maxScore;
        this.count = new int[maxScore + 1];        // 对应您的 int[] count = new int[100 + 1]
        this.prefixSum = new int[maxScore + 1];    // 前缀和数组
        this.totalStudents = 0;
    }
    
    /**
     * 默认构造函数，满分100分
     */
    public ScoreRankingSystem() {
        this(100);
    }
    
    /**
     * 批量添加学生分数 - 实现您提供的核心算法
     * @param scores 所有学生的分数数组
     */
    public void addScores(int[] scores) {
        if (scores == null) {
            throw new IllegalArgumentException("分数数组不能为null");
        }
        
        // 重置统计数据
        Arrays.fill(count, 0);
        Arrays.fill(prefixSum, 0);
        this.totalStudents = scores.length;
        
        // 🎯 实现您的核心算法：记录每个分数有几个同学
        for (int score : scores) {
            if (score < 0 || score > maxScore) {
                throw new IllegalArgumentException("分数必须在0-" + maxScore + "范围内，当前分数：" + score);
            }
            count[score]++;  // 对应您的 count[score]++
        }
        
        // 🎯 实现您的前缀和构造算法
        // prefixSum[i] 表示分数 <= i 的学生总数
        prefixSum[0] = count[0];
        for (int i = 1; i < count.length; i++) {
            prefixSum[i] = prefixSum[i-1] + count[i];  // 对应您的 count[i] = count[i] + count[i-1]
        }
        
        System.out.println("✅ 成功应用您的前缀和算法！");
        System.out.println("📊 分数统计完成，共 " + totalStudents + " 名学生");
    }
    
    /**
     * 查询指定分数的排名（从1开始，从高到低排名）
     * 利用前缀和数组实现O(1)查询
     * 
     * @param score 查询的分数
     * @return 该分数的最佳排名（最高排名）
     */
    public int getRank(int score) {
        validateScore(score);
        
        // 计算分数严格大于该分数的学生人数 + 1 = 最佳排名
        int higherScoreCount = totalStudents - prefixSum[score];
        return higherScoreCount + 1;
    }
    
    /**
     * 查询指定分数的最差排名（考虑同分情况）
     * @param score 查询的分数
     * @return 该分数的最差排名
     */
    public int getWorstRank(int score) {
        validateScore(score);
        
        // 计算分数大于等于该分数的学生人数，该分数在同分中排最后
        int greaterOrEqualCount;
        if (score == 0) {
            greaterOrEqualCount = totalStudents;
        } else {
            greaterOrEqualCount = totalStudents - prefixSum[score - 1];
        }
        
        return greaterOrEqualCount;
    }
    
    /**
     * 查询分数小于等于指定分数的学生人数
     * 直接使用前缀和数组，O(1)时间复杂度
     * 
     * @param score 分数阈值
     * @return 分数 <= score 的学生人数
     */
    public int getCountLessOrEqual(int score) {
        validateScore(score);
        return prefixSum[score];  // 前缀和的直接应用
    }
    
    /**
     * 查询分数大于指定分数的学生人数
     * @param score 分数阈值
     * @return 分数 > score 的学生人数
     */
    public int getCountGreater(int score) {
        validateScore(score);
        return totalStudents - prefixSum[score];
    }
    
    /**
     * 查询分数在指定范围内的学生人数
     * 利用前缀和的差值计算，O(1)时间复杂度
     * 
     * @param minScore 最低分数（包含）
     * @param maxScore 最高分数（包含）
     * @return 分数在[minScore, maxScore]范围内的学生人数
     */
    public int getCountInRange(int minScore, int maxScore) {
        validateScore(minScore);
        validateScore(maxScore);
        
        if (minScore > maxScore) {
            throw new IllegalArgumentException("最低分数不能大于最高分数");
        }
        
        if (minScore == 0) {
            return prefixSum[maxScore];
        }
        
        // 前缀和差值计算：prefixSum[max] - prefixSum[min-1]
        return prefixSum[maxScore] - prefixSum[minScore - 1];
    }
    
    /**
     * 获取指定分数的学生人数
     * @param score 分数
     * @return 该分数的学生人数
     */
    public int getCountAtScore(int score) {
        validateScore(score);
        return count[score];  // 直接从计数数组获取
    }
    
    /**
     * 获取分数分布统计
     * @return 分数分布数组的副本
     */
    public int[] getScoreDistribution() {
        return Arrays.copyOf(count, count.length);
    }
    
    /**
     * 获取累积分布（前缀和数组）
     * @return 前缀和数组的副本
     */
    public int[] getCumulativeDistribution() {
        return Arrays.copyOf(prefixSum, prefixSum.length);
    }
    
    /**
     * 计算平均分
     * @return 平均分
     */
    public double getAverageScore() {
        if (totalStudents == 0) {
            return 0.0;
        }
        
        long totalScore = 0;
        for (int score = 0; score <= maxScore; score++) {
            totalScore += (long) score * count[score];
        }
        
        return (double) totalScore / totalStudents;
    }
    
    /**
     * 获取中位数
     * @return 中位数
     */
    public double getMedian() {
        if (totalStudents == 0) {
            return 0.0;
        }
        
        if (totalStudents % 2 == 1) {
            // 奇数个学生，返回中间位置的分数
            int targetRank = (totalStudents + 1) / 2;
            return getScoreAtRank(targetRank);
        } else {
            // 偶数个学生，返回中间两个分数的平均值
            int rank1 = totalStudents / 2;
            int rank2 = rank1 + 1;
            return (getScoreAtRank(rank1) + getScoreAtRank(rank2)) / 2.0;
        }
    }
    
    /**
     * 根据排名获取分数（从高到低排名，排名从1开始）
     * @param rank 排名
     * @return 该排名对应的分数
     */
    public int getScoreAtRank(int rank) {
        if (rank < 1 || rank > totalStudents) {
            throw new IllegalArgumentException("排名必须在1-" + totalStudents + "范围内");
        }
        
        // 从高分到低分查找第rank名的分数
        int currentCount = 0;
        for (int score = maxScore; score >= 0; score--) {
            currentCount += count[score];
            if (currentCount >= rank) {
                return score;
            }
        }
        
        return 0; // 理论上不会到达这里
    }
    
    /**
     * 打印详细的统计报告
     */
    public void printDetailedReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("📊 分数统计与排名分析报告");
        System.out.println(repeatString("=", 60));
        
        System.out.println("📈 基本统计信息:");
        System.out.println("   总学生数: " + totalStudents);
        System.out.printf("   平均分: %.2f\n", getAverageScore());
        System.out.printf("   中位数: %.2f\n", getMedian());
        
        // 显示前缀和算法的效果
        System.out.println("\n🎯 前缀和算法应用效果:");
        System.out.println("   ✅ 计数数组构建完成，记录每个分数的学生人数");
        System.out.println("   ✅ 前缀和数组构建完成，支持O(1)时间复杂度查询");
        System.out.println("   ✅ 可进行快速排名查询、范围统计、百分位计算");
        
        // 打印分数分布（只显示有学生的分数）
        System.out.println("\n📋 分数分布详情:");
        boolean hasData = false;
        for (int score = maxScore; score >= 0; score--) {
            if (count[score] > 0) {
                hasData = true;
                int bestRank = getRank(score);
                int worstRank = getWorstRank(score);
                double percentage = (double) count[score] / totalStudents * 100;
                
                System.out.printf("   分数 %3d: %3d人 (%.1f%%) | 排名 %d", 
                                score, count[score], percentage, bestRank);
                if (bestRank != worstRank) {
                    System.out.printf("-%d", worstRank);
                }
                System.out.println();
            }
        }
        
        if (!hasData) {
            System.out.println("   暂无数据");
        }
        
        // 打印分数段统计
        System.out.println("\n🏆 分数段统计:");
        int[][] ranges = {{90, maxScore}, {80, 89}, {70, 79}, {60, 69}, {0, 59}};
        String[] grades = {"优秀(90-100)", "良好(80-89)", "中等(70-79)", "及格(60-69)", "不及格(0-59)"};
        
        for (int i = 0; i < ranges.length; i++) {
            int minScore = ranges[i][0];
            int maxScore = ranges[i][1];
            int count = getCountInRange(minScore, maxScore);
            double percentage = totalStudents > 0 ? (double) count / totalStudents * 100 : 0;
            System.out.printf("   %s: %3d人 (%.1f%%)\n", grades[i], count, percentage);
        }
        
        // 展示前缀和数组的部分内容（用于理解算法）
        System.out.println("\n🔍 前缀和数组示例 (部分高分段):");
        System.out.println("   分数 | 人数 | 累计人数(前缀和)");
        System.out.println("   -----|------|---------------");
        for (int score = Math.min(maxScore, 100); score >= Math.max(0, maxScore - 10); score--) {
            if (count[score] > 0 || score % 5 == 0) {
                System.out.printf("   %3d  | %3d  | %3d\n", score, count[score], prefixSum[score]);
            }
        }
        
        System.out.println(repeatString("=", 60));
    }
    
    /**
     * 验证分数的有效性
     */
    private void validateScore(int score) {
        if (score < 0 || score > maxScore) {
            throw new IllegalArgumentException("分数必须在0-" + maxScore + "范围内");
        }
    }
    
    /**
     * 兼容性方法：重复字符串（替代Java 11的String.repeat()）
     */
    private static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    // Getter方法
    public int getTotalStudents() { return totalStudents; }
    public int getMaxScore() { return maxScore; }
    
    /**
     * 演示您的前缀和算法的完整应用
     */
    public static void main(String[] args) {
        System.out.println("🎯 演示您提供的前缀和算法在分数统计中的应用");
        System.out.println("原始算法片段:");
        System.out.println("```java");
        System.out.println("int[] scores; // 存储着所有同学的分数");
        System.out.println("int[] count = new int[100 + 1]; // 试卷满分 100 分");
        System.out.println("// 记录每个分数有几个同学");
        System.out.println("for (int score : scores)");
        System.out.println("    count[score]++;");
        System.out.println("// 构造前缀和");
        System.out.println("for (int i = 1; i < count.length; i++)");
        System.out.println("    count[i] = count[i] + count[i-1];");
        System.out.println("```");
        
        // 创建测试数据
        int[] scores = {
            95, 87, 92, 78, 95, 88, 91, 85, 89, 93,
            82, 90, 86, 84, 88, 79, 87, 92, 85, 91,
            77, 94, 83, 88, 90, 80, 86, 89, 87, 85,
            96, 75, 92, 88, 91, 84, 89, 87, 93, 86
        };
        
        // 应用您的算法
        ScoreRankingSystem system = new ScoreRankingSystem();
        system.addScores(scores);
        
        // 展示完整的统计报告
        system.printDetailedReport();
        
        // 演示各种查询功能
        System.out.println("\n🔍 查询功能演示:");
        
        // 排名查询
        System.out.println("\n📊 排名查询示例:");
        int[] queryScores = {95, 90, 85, 80};
        for (int score : queryScores) {
            if (system.getCountAtScore(score) > 0) {
                int bestRank = system.getRank(score);
                int worstRank = system.getWorstRank(score);
                int count = system.getCountAtScore(score);
                
                System.out.printf("   分数 %d: %d人，最佳排名 %d，最差排名 %d\n", 
                                score, count, bestRank, worstRank);
            }
        }
        
        // 范围查询
        System.out.println("\n📈 范围查询示例:");
        System.out.println("   90分以上: " + system.getCountGreater(89) + " 人");
        System.out.println("   85-90分: " + system.getCountInRange(85, 90) + " 人");
        System.out.println("   80分以下: " + system.getCountLessOrEqual(79) + " 人");
        
        // 百分位查询
        System.out.println("\n🏆 排名分析:");
        System.out.println("   第5名分数: " + system.getScoreAtRank(5));
        System.out.println("   第10名分数: " + system.getScoreAtRank(10));
        System.out.println("   第20名分数: " + system.getScoreAtRank(20));
        
        System.out.println("\n✅ 您的前缀和算法应用演示完成！");
        System.out.println("💡 这个算法在考试系统、竞赛排名、成绩分析等场景中非常实用！");
    }
}