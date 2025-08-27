# Algorithm Practice 算法练习项目

这是一个独立的算法练习项目，包含各种经典算法的实现和测试。

## 项目结构

```
algorithm-practice/
├── src/
│   ├── main/java/
│   │   └── algorithm/
│   │       ├── dp/              # 动态规划
│   │       ├── sort/            # 排序算法
│   │       ├── search/          # 搜索算法
│   │       ├── basicds/         # 基础数据结构
│   │       ├── backtracking/    # 回溯算法
│   │       └── ...
│   └── test/java/
│       └── algorithm/
│           ├── dp/
│           └── ...
├── pom.xml
└── README.md
```

## 环境要求

- JDK 17+
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

### 动态规划 (dp)
- `GridMinPath` - 网格最小路径和（4种实现方法）
- `ClimbSteps` - 爬楼梯问题
- `MaxSubArray` - 最大子数组和
- `Fibonacci` - 斐波那契数列

### 排序算法 (sort)
- `QuickSort` - 快速排序
- `QuickSort2` - 快速排序优化版
- `MergeSort` - 归并排序
- `InPlaceMergeSort` - 原地归并排序
- `BubbleSort` - 冒泡排序
- `BubbleSortWithFlag` - 带标志的冒泡排序
- `InsertionSort` - 插入排序
- `SelectionSort` - 选择排序
- `CountingSort` - 计数排序
- `RadixSort` - 基数排序
- `BitmapSort` - 位图排序

### 搜索算法 (search)
- `BinarySearch` - 二分搜索

### 基础数据结构 (basicds)
- `BTree` - 二叉树
- `BinarySearchTree` - 二叉搜索树
- `MaxHeap` - 最大堆
- `LinkedStack` - 链表栈
- `ArrayStack` - 数组栈
- `LinkedQueue` - 链表队列
- `ArrayQueue` - 数组队列
- `LinkedDeque` - 链表双端队列
- `ArrayDeque` - 数组双端队列
- `GraphAdjList` - 邻接表图
- `GraphAdjMat` - 邻接矩阵图

### 回溯算法 (beautiful/backtracing)
- `NQueen` - N皇后问题
- `Arrangement` - 排列问题
- `SubsetSum` - 子集和问题
- `FindNumber` - 数字查找
- `GenericBacktrackFinder` - 通用回溯查找器

### 分治算法 (divideconquer)
- `Hanoi` - 汉诺塔问题
- `MaxSubArray` - 最大子数组和（分治版）

### 数学算法 (math)
- `FastPower` - 快速幂
- `BigNumberCalculate` - 大数运算

### 字符串算法 (str)
- `Kmp` - KMP字符串匹配

### 编程珠玑 (pearls)
- `BestSubset` - 最佳子集
- `PhoneMnemonic8Plain` - 电话号码助记符
- `ThreeBlockSwapDemo` - 三块交换演示

### 其他算法
- `Shuffler` - 洗牌算法
- `ExpandExperiment` - 扩展实验

## 贡献指南

1. 每个算法都应该有详细的文档注释
2. 包含时间复杂度和空间复杂度分析
3. 提供完整的单元测试
4. 遵循Java编码规范