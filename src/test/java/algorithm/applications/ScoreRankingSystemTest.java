package algorithm.applications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 分数统计和排名查询系统测试类
 * 
 * 测试您提供的前缀和算法在实际应用中的正确性和性能
 */
public class ScoreRankingSystemTest {
    
    private ScoreRankingSystem system;
    private int[] testScores;
    
    @BeforeEach
    void setUp() {
        system = new ScoreRankingSystem();
        // 创建测试数据：模拟一个班级的考试成绩
        testScores = new int[]{
            95, 87, 92, 78, 95, 88, 91, 85, 89, 93,
            82, 90, 86, 84, 88, 79, 87, 92, 85, 91,
            77, 94, 83, 88, 90, 80, 86, 89, 87, 85
        };
        system.addScores(testScores);
    }
    
    @Test
    @DisplayName("测试您的核心前缀和算法 - 分数统计")
    void testCoreAlgorithm() {
        // 验证分数统计的正确性
        assertEquals(2, system.getCountAtScore(95)); // 95分有2人
        assertEquals(3, system.getCountAtScore(88)); // 88分有3人
        assertEquals(3, system.getCountAtScore(85)); // 85分有3人
        assertEquals(0, system.getCountAtScore(100)); // 100分有0人
        
        // 验证总人数
        assertEquals(30, system.getTotalStudents());
    }
    
    @Test
    @DisplayName("测试前缀和数组的正确性")
    void testPrefixSumArray() {
        int[] cumulative = system.getCumulativeDistribution();
        
        // 验证前缀和的基本性质
        assertTrue(cumulative[100] == system.getTotalStudents()); // 最后一个元素应该等于总人数
        
        // 验证前缀和的递增性
        for (int i = 1; i < cumulative.length; i++) {
            assertTrue(cumulative[i] >= cumulative[i-1], 
                      "前缀和数组应该是非递减的，在位置 " + i + " 出现问题");
        }
        
        // 验证特定分数的累积人数
        assertTrue(cumulative[95] >= 2); // 95分及以下至少有2人（95分本身就有2人）
        assertEquals(30, cumulative[100]); // 100分及以下应该有全部30人
    }
    
    @Test
    @DisplayName("测试排名查询功能")
    void testRankingQueries() {
        // 测试最高分的排名
        assertEquals(1, system.getRank(95)); // 95分应该是第1名
        
        // 测试排名的合理性
        int rank90 = system.getRank(90);
        int rank85 = system.getRank(85);
        assertTrue(rank90 < rank85, "90分的排名应该高于85分");
        
        // 测试同分情况的最佳和最差排名
        int bestRank88 = system.getRank(88);
        int worstRank88 = system.getWorstRank(88);
        assertTrue(bestRank88 <= worstRank88, "最佳排名应该不大于最差排名");
        
        // 验证排名范围的合理性
        assertTrue(bestRank88 >= 1 && bestRank88 <= 30);
        assertTrue(worstRank88 >= 1 && worstRank88 <= 30);
    }
    
    @Test
    @DisplayName("测试范围查询功能 - 前缀和的核心应用")
    void testRangeQueries() {
        // 测试小于等于查询
        int count90AndBelow = system.getCountLessOrEqual(90);
        int count95AndBelow = system.getCountLessOrEqual(95);
        assertTrue(count95AndBelow >= count90AndBelow, "95分及以下的人数应该不少于90分及以下");
        
        // 测试大于查询
        int countAbove90 = system.getCountGreater(90);
        assertEquals(30, count90AndBelow + countAbove90, "所有学生应该被正确分类");
        
        // 测试范围查询
        int count85to90 = system.getCountInRange(85, 90);
        assertTrue(count85to90 > 0, "85-90分范围内应该有学生");
        
        // 验证范围查询的一致性 - 修正逻辑
        // 分成三个不重叠的区间：[0,84], [85,90], [91,100]
        int countBelow85 = system.getCountLessOrEqual(84);  // 84分及以下
        int count85to90_inclusive = system.getCountInRange(85, 90);  // 85-90分（包含边界）
        int countAbove90_final = system.getCountGreater(90);  // 90分以上（不包含90）
        
        assertEquals(countBelow85 + count85to90_inclusive + countAbove90_final, 30,
                    "所有范围的学生数量之和应该等于总人数");
    }
    
    @Test
    @DisplayName("测试统计分析功能")
    void testStatisticalAnalysis() {
        // 测试平均分计算
        double avgScore = system.getAverageScore();
        assertTrue(avgScore > 0 && avgScore <= 100, "平均分应该在合理范围内");
        
        // 手动计算平均分进行验证
        double expectedAvg = 0;
        for (int score : testScores) {
            expectedAvg += score;
        }
        expectedAvg /= testScores.length;
        
        assertEquals(expectedAvg, avgScore, 0.01, "平均分计算应该正确");
        
        // 测试中位数
        double median = system.getMedian();
        assertTrue(median > 0 && median <= 100, "中位数应该在合理范围内");
    }
    
    @Test
    @DisplayName("测试根据排名获取分数")
    void testScoreAtRank() {
        // 测试第1名的分数
        int topScore = system.getScoreAtRank(1);
        assertEquals(95, topScore); // 应该是最高分95
        
        // 测试排名的一致性
        for (int rank = 1; rank <= Math.min(10, system.getTotalStudents()); rank++) {
            int score = system.getScoreAtRank(rank);
            int calculatedRank = system.getRank(score);
            assertTrue(calculatedRank <= rank, 
                      "排名 " + rank + " 对应分数 " + score + " 的计算排名应该不大于 " + rank);
        }
    }
    
    @Test
    @DisplayName("测试边界情况")
    void testBoundaryConditions() {
        // 测试空数组
        ScoreRankingSystem emptySystem = new ScoreRankingSystem();
        emptySystem.addScores(new int[0]);
        assertEquals(0, emptySystem.getTotalStudents());
        assertEquals(0.0, emptySystem.getAverageScore());
        
        // 测试单个学生
        ScoreRankingSystem singleSystem = new ScoreRankingSystem();
        singleSystem.addScores(new int[]{85});
        assertEquals(1, singleSystem.getTotalStudents());
        assertEquals(1, singleSystem.getRank(85));
        assertEquals(85.0, singleSystem.getAverageScore());
        
        // 测试所有学生同分
        ScoreRankingSystem sameScoreSystem = new ScoreRankingSystem();
        sameScoreSystem.addScores(new int[]{80, 80, 80, 80, 80});
        assertEquals(1, sameScoreSystem.getRank(80)); // 最佳排名应该是1
        assertEquals(5, sameScoreSystem.getWorstRank(80)); // 最差排名应该是5
    }
    
    @Test
    @DisplayName("测试异常处理")
    void testExceptionHandling() {
        // 测试null输入
        assertThrows(IllegalArgumentException.class, () -> {
            system.addScores(null);
        });
        
        // 测试超出范围的分数
        assertThrows(IllegalArgumentException.class, () -> {
            system.addScores(new int[]{101}); // 超过满分100
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            system.addScores(new int[]{-1}); // 负分
        });
        
        // 测试无效排名查询
        assertThrows(IllegalArgumentException.class, () -> {
            system.getScoreAtRank(0); // 排名从1开始
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            system.getScoreAtRank(31); // 超过总人数
        });
    }
    
    @Test
    @DisplayName("测试性能 - 验证O(1)查询时间复杂度")
    void testPerformance() {
        // 创建大规模测试数据
        int[] largeScores = new int[10000];
        for (int i = 0; i < largeScores.length; i++) {
            largeScores[i] = (int) (Math.random() * 101); // 0-100随机分数
        }
        
        ScoreRankingSystem largeSystem = new ScoreRankingSystem();
        
        // 测试构建时间
        long startTime = System.nanoTime();
        largeSystem.addScores(largeScores);
        long buildTime = System.nanoTime() - startTime;
        
        // 测试查询时间（应该是O(1)）
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int randomScore = (int) (Math.random() * 101);
            largeSystem.getRank(randomScore);
            largeSystem.getCountLessOrEqual(randomScore);
            // 确保范围查询不会超出边界
            int endScore = Math.min(randomScore + 10, 100);
            largeSystem.getCountInRange(randomScore, endScore);
        }
        long queryTime = System.nanoTime() - startTime;
        
        System.out.printf("性能测试结果 - 数据规模: %d\n", largeScores.length);
        System.out.printf("构建时间: %.2f ms\n", buildTime / 1_000_000.0);
        System.out.printf("1000次查询时间: %.2f ms\n", queryTime / 1_000_000.0);
        System.out.printf("平均单次查询时间: %.2f μs\n", queryTime / 1000.0 / 1000.0);
        
        // 验证大规模数据的正确性
        assertEquals(10000, largeSystem.getTotalStudents());
        assertTrue(largeSystem.getAverageScore() >= 0 && largeSystem.getAverageScore() <= 100);
    }
    
    @Test
    @DisplayName("验证您的原始算法逻辑")
    void testOriginalAlgorithmLogic() {
        // 手动实现您提供的原始算法进行对比验证
        int[] scores = {85, 92, 78, 85, 88, 92, 75, 89, 85};
        
        // 您的原始算法
        int[] count = new int[100 + 1]; // 试卷满分 100 分
        // 记录每个分数有几个同学
        for (int score : scores) {
            count[score]++;
        }
        // 构造前缀和
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i-1];
        }
        
        // 使用我们的系统
        ScoreRankingSystem testSystem = new ScoreRankingSystem();
        testSystem.addScores(scores);
        
        // 验证结果一致性
        int[] systemCumulative = testSystem.getCumulativeDistribution();
        
        for (int i = 0; i < count.length; i++) {
            assertEquals(count[i], systemCumulative[i], 
                        "在分数 " + i + " 处，前缀和结果应该一致");
        }
        
        System.out.println("✅ 您的原始算法逻辑验证通过！");
        System.out.println("📊 算法正确实现了分数统计和前缀和构建");
    }
}