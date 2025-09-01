# Algorithm Practice 算法练习项目

这是一个独立的算法练习项目，包含各种经典算法的实现和测试。

## 项目结构

```
algorithm/
├── src/
│   ├── main/java/
│   │   └── algorithm/
│   │       ├── advanced/                    # 高级算法
│   │       │   ├── backtracking/           # 回溯算法
│   │       │   └── dynamicprogramming/     # 动态规划
│   │       ├── datastructures/             # 数据结构
│   │       │   ├── elementary/             # 基础数据结构
│   │       │   ├── graphs/                 # 图
│   │       │   ├── heaps/                  # 堆
│   │       │   └── trees/                  # 树
│   │       ├── foundations/                # 基础算法
│   │       │   ├── divideconquer/          # 分治算法
│   │       │   └── math/                   # 数学算法
│   │       ├── greedy_algorithm/           # 贪心算法
│   │       ├── misc/                       # 杂项
│   │       ├── selectedtopics/             # 精选主题
│   │       │   ├── computational/          # 计算类算法
│   │       │   ├── leetcode/               # LeetCode题解
│   │       │   ├── numbertheory/           # 数论
│   │       │   └── stringalgorithms/       # 字符串算法
│   │       └── sorting/                    # 排序算法
│   └── test/java/
│       └── algorithm/                      # 对应的测试用例
├── target/                                 # Maven构建输出目录
├── .gitignore
├── pom.xml
├── verify-independence.sh                  # 独立性验证脚本
└── README.md
```

## 环境要求

- JDK 8+
- Maven 3.6+

## 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=GridMinPathTest

# 运行特定测试方法
mvn test -Dtest=GridMinPathTest#testBasic3x3Grid
```

## 编译项目

```bash
# 编译
mvn compile

# 打包
mvn package
```

## 算法分类

### 高级算法 (advanced)

#### 动态规划 (dynamicprogramming)
- `ClimbSteps` - 爬楼梯问题
- `CoinsChangeCombinationProblem` - 硬币找零组合问题
- `CoinsProblem` - 硬币问题
- `Fibonacci` - 斐波那契数列
- `GridMinPath` - 网格最小路径和
- `LevenshteinProblem` - 编辑距离问题
- `MaxSubArray` - 最大子数组和
- `UnboundedKnapsackProblem` - 无界背包问题
- `ZeroOrOneKnapsacks` - 0-1背包问题

#### 回溯算法 (backtracking)
- `Arrangement` - 排列问题
- `FindNumber` - 数字查找
- `GenericBacktrackFinder` - 通用回溯查找器
- `NQueen` - N皇后问题
- `Permutation` - 排列生成
- `SubsetSum` - 子集和问题

### 数据结构 (datastructures)

#### 基础数据结构 (elementary)
- `ArrayDeque` - 数组双端队列
- `ArrayQueue` - 数组队列
- `ArrayStack` - 数组栈
- `LinkedDeque` - 链表双端队列
- `LinkedQueue` - 链表队列
- `LinkedStack` - 链表栈
- `Deque` / `Queue` / `Stack` - 接口定义

#### 图 (graphs)
- `GraphAdjList` - 邻接表图
- `GraphAdjMat` - 邻接矩阵图

#### 堆 (heaps)
- `MaxHeap` - 最大堆

#### 树 (trees)
- `BinarySearchTree` - 二叉搜索树
- `BTree` - 二叉树

### 基础算法 (foundations)
- `BinarySearch` - 二分搜索

#### 分治算法 (divideconquer)
- `Hanoi` - 汉诺塔问题
- `MaxSubArray` - 最大子数组和（分治版）

#### 数学算法 (math)
- `FastPower` - 快速幂

### 贪心算法 (greedy_algorithm)
- `GreedyAlgorithm` - 贪心算法示例

### 排序算法 (sorting)
- `BitmapSort` - 位图排序
- `BubbleSort` - 冒泡排序
- `CountingSort` - 计数排序
- `InPlaceMergeSort` - 原地归并排序
- `InsertionSort` - 插入排序
- `MergeSort` - 归并排序
- `QuickSort` - 快速排序
- `RadixSort` - 基数排序
- `SelectionSort` - 选择排序

### 精选主题 (selectedtopics)

#### 计算类算法 (computational)
- `BestSubset` - 最佳子集
- `Chess` - 国际象棋
- `ExpandExperiment` - 扩展实验
- `PancakeSorting` - 煎饼排序
- `PhoneMnemonic8Plain` - 电话号码助记符
- `Shuffler` - 洗牌算法
- `ThreeBlockSwapDemo` - 三块交换演示

#### LeetCode题解 (leetcode)
- `BinaryTreeLCA` - 二叉树最近公共祖先
- `StringPermutation` - 字符串排列

#### 数论 (numbertheory)
- `BigNumberCalculate` - 大数运算

#### 字符串算法 (stringalgorithms)
- `Kmp` - KMP字符串匹配

### 工具类
- `PrintUtil` - 打印工具类

### 项目特点

- **完整的测试覆盖**: 每个算法都有对应的JUnit 5测试用例
- **详细的文档**: 包含算法原理、时间复杂度和空间复杂度分析
- **模块化设计**: 按算法类型分包组织，便于学习和维护
- **Maven构建**: 使用Maven进行依赖管理和项目构建
- **代码规范**: 遵循Java编码规范，代码可读性强
- **独立性验证**: 包含独立性验证脚本，确保项目可独立运行
- **丰富的算法实现**: 涵盖从基础到高级的各类算法
- **LeetCode题解**: 包含经典LeetCode问题的解决方案

## 依赖说明

- **JUnit 5**: 用于单元测试
- **Lombok**: 用于简化代码（可选依赖）

## 快速开始

```bash
# 克隆项目
git clone <repository-url>
cd algorithm

# 编译项目
mvn compile

# 运行所有测试
mvn test

# 验证项目独立性
./verify-independence.sh
```

## 贡献指南

1. 每个算法都应该有详细的文档注释
2. 包含时间复杂度和空间复杂度分析
3. 提供完整的单元测试
4. 遵循Java编码规范