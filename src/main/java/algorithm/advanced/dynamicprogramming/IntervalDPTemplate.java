package algorithm.advanced.dynamicprogramming;

/**
 * 区间DP通用模板
 * 
 * 区间DP是一种特殊的动态规划方法，主要用于解决在一个区间内进行某种操作，
 * 使得代价最小或收益最大的问题。
 * 
 * 核心特点：
 * 1. 状态定义：dp[i][j] 表示在区间 [i, j] 内进行操作的最优值
 * 2. 状态转移：通过枚举区间内的分割点 k，将大区间分解为两个小区间
 * 3. 计算顺序：先计算小区间，再计算大区间（区间长度从小到大）
 */
public abstract class IntervalDPTemplate {
    
    /**
     * 区间DP标准模板方法
     * @param input 输入数据
     * @return 最优解
     */
    public final int solve(int[] input) {
        int n = getArraySize(input);
        
        // 预处理输入数据（如果需要）
        int[] processedInput = preprocessInput(input);
        int processedSize = processedInput.length;
        
        // 创建DP数组
        int[][] dp = new int[processedSize][processedSize];
        
        // 初始化基础情况
        initializeBase(dp, processedInput);
        
        // 枚举区间长度
        int startLen = getStartLength();
        int maxLen = getMaxLength(processedSize);
        
        for (int len = startLen; len <= maxLen; len++) {
            // 枚举区间起点
            for (int i = 0; i <= processedSize - len; i++) {
                int j = getEndIndex(i, len);
                
                // 跳过无效区间
                if (!isValidInterval(i, j, processedSize)) {
                    continue;
                }
                
                // 初始化当前区间的值
                dp[i][j] = getInitialValue();
                
                // 枚举分割点
                for (int k = getMinSplitPoint(i); k < getMaxSplitPoint(j); k++) {
                    if (!isValidSplitPoint(i, j, k)) {
                        continue;
                    }
                    
                    // 计算当前分割的代价
                    int cost = calculateCost(dp, processedInput, i, j, k);
                    
                    // 更新最优值
                    dp[i][j] = updateOptimal(dp[i][j], cost);
                }
            }
        }
        
        // 返回最终答案
        return getFinalAnswer(dp, processedSize);
    }
    
    // ========== 抽象方法，子类必须实现 ==========
    
    /**
     * 获取数组大小
     */
    protected abstract int getArraySize(int[] input);
    
    /**
     * 预处理输入数据（如扩展数组等）
     */
    protected abstract int[] preprocessInput(int[] input);
    
    /**
     * 初始化基础情况
     */
    protected abstract void initializeBase(int[][] dp, int[] processedInput);
    
    /**
     * 获取区间长度的起始值
     */
    protected abstract int getStartLength();
    
    /**
     * 计算当前分割的代价
     */
    protected abstract int calculateCost(int[][] dp, int[] processedInput, int i, int j, int k);
    
    /**
     * 更新最优值（取最大值或最小值）
     */
    protected abstract int updateOptimal(int currentOptimal, int newCost);
    
    /**
     * 获取最终答案
     */
    protected abstract int getFinalAnswer(int[][] dp, int size);
    
    // ========== 默认实现，子类可以重写 ==========
    
    /**
     * 获取最大区间长度
     */
    protected int getMaxLength(int size) {
        return size - 1;
    }
    
    /**
     * 根据起点和长度计算终点
     */
    protected int getEndIndex(int i, int len) {
        return i + len - 1;
    }
    
    /**
     * 检查区间是否有效
     */
    protected boolean isValidInterval(int i, int j, int size) {
        return j < size;
    }
    
    /**
     * 获取初始值（通常是最大值或最小值）
     */
    protected int getInitialValue() {
        return Integer.MAX_VALUE; // 求最小值时使用
        // return Integer.MIN_VALUE; // 求最大值时使用
    }
    
    /**
     * 获取最小分割点
     */
    protected int getMinSplitPoint(int i) {
        return i;
    }
    
    /**
     * 获取最大分割点
     */
    protected int getMaxSplitPoint(int j) {
        return j;
    }
    
    /**
     * 检查分割点是否有效
     */
    protected boolean isValidSplitPoint(int i, int j, int k) {
        return true;
    }
}

/**
 * 矩阵链乘法的模板实现示例
 */
class MatrixChainTemplate extends IntervalDPTemplate {
    
    @Override
    protected int getArraySize(int[] input) {
        return input.length - 1; // 矩阵个数
    }
    
    @Override
    protected int[] preprocessInput(int[] input) {
        return input; // 不需要预处理
    }
    
    @Override
    protected void initializeBase(int[][] dp, int[] processedInput) {
        // 单个矩阵不需要乘法
        for (int i = 1; i <= getArraySize(processedInput) + 1; i++) {
            if (i < dp.length) {
                dp[i][i] = 0;
            }
        }
    }
    
    @Override
    protected int getStartLength() {
        return 2; // 至少需要2个矩阵才能相乘
    }
    
    @Override
    protected int calculateCost(int[][] dp, int[] p, int i, int j, int k) {
        return dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
    }
    
    @Override
    protected int updateOptimal(int currentOptimal, int newCost) {
        return Math.min(currentOptimal, newCost); // 求最小值
    }
    
    @Override
    protected int getFinalAnswer(int[][] dp, int size) {
        return dp[1][size - 1];
    }
    
    @Override
    protected int getEndIndex(int i, int len) {
        return i + len - 1;
    }
    
    @Override
    protected boolean isValidInterval(int i, int j, int size) {
        return i >= 1 && j < size;
    }
    
    @Override
    protected int getMinSplitPoint(int i) {
        return i;
    }
    
    @Override
    protected int getMaxSplitPoint(int j) {
        return j;
    }
}

/**
 * 戳气球问题的模板实现示例
 */
class BurstBalloonsTemplate extends IntervalDPTemplate {
    
    @Override
    protected int getArraySize(int[] input) {
        return input.length;
    }
    
    @Override
    protected int[] preprocessInput(int[] input) {
        // 扩展数组，两端添加1
        int[] newNums = new int[input.length + 2];
        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;
        System.arraycopy(input, 0, newNums, 1, input.length);
        return newNums;
    }
    
    @Override
    protected void initializeBase(int[][] dp, int[] processedInput) {
        // 开区间长度小于3时，无法戳破气球，收益为0
        // 默认初始化已经是0，无需额外操作
    }
    
    @Override
    protected int getStartLength() {
        return 3; // 开区间至少需要3个位置才能戳破1个气球
    }
    
    @Override
    protected int calculateCost(int[][] dp, int[] nums, int i, int j, int k) {
        return dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j];
    }
    
    @Override
    protected int updateOptimal(int currentOptimal, int newCost) {
        return Math.max(currentOptimal, newCost); // 求最大值
    }
    
    @Override
    protected int getFinalAnswer(int[][] dp, int size) {
        return dp[0][size - 1];
    }
    
    @Override
    protected int getInitialValue() {
        return Integer.MIN_VALUE; // 求最大值时使用
    }
    
    @Override
    protected int getMinSplitPoint(int i) {
        return i + 1; // 开区间，分割点不能是端点
    }
    
    @Override
    protected int getMaxSplitPoint(int j) {
        return j; // 开区间，分割点不能是端点
    }
}

/**
 * 模板使用示例
 */
class IntervalDPTemplateDemo {
}