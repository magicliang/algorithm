# 回文链表问题 - 5种经典解法

本包实现了 LeetCode 234. 回文链表问题的5种经典解法，涵盖了从基础到进阶的所有常见思路。

## 问题描述

给你一个单链表的头节点 `head`，请你判断该链表是否为回文链表。如果是，返回 `true`；否则，返回 `false`。

**进阶**：你能否用 O(1) 空间复杂度解决此问题？

## 文件结构

```
palindromelinkedlist/
├── ListNode.java                           # 链表节点定义
├── PalindromeLinkedListSolutions.java     # 5种解法实现
├── PalindromeLinkedListTest.java          # 功能测试
├── PalindromeLinkedListPerformanceTest.java # 性能测试
└── README.md                              # 说明文档
```

## 5种解法详解

### 解法1：整链反转比对

**思路**：把原链表复制一份并整体反转，然后同步遍历一次。

```java
public static boolean isPalindromeByReverseWhole(ListNode head)
```

- **时间复杂度**：O(n)
- **空间复杂度**：O(n) - 复制新链表
- **特点**：最容易想到，但额外占用 n 个节点

### 解法2：栈

**思路**：第一次遍历把所有节点压栈；第二次遍历同时弹栈比对。

```java
public static boolean isPalindromeByStack(ListNode head)
```

- **时间复杂度**：O(n)
- **空间复杂度**：O(n) - 栈大小 = 节点数
- **特点**：代码最短，符合"后进先出"直觉

### 解法3：递归（双返回值）

**思路**：利用"长度"做递归基准，每次把左端指针与递归返回的右端指针相向比对；递归返回 boolean + 新的右端指针（双返回值）。

```java
public static boolean isPalindromeByRecursion(ListNode head)
```

- **时间复杂度**：O(n)
- **空间复杂度**：O(n) - 递归栈深度
- **特点**：最贴近提示 4 的表述，体现"返回多个值"技巧

### 解法4：递归（封装类）

**思路**：把上一种递归里的"boolean 结果 + 右端指针"封装成一个小类，消除多返回值歧义。

```java
public static boolean isPalindromeByComparator(ListNode head)
```

- **时间复杂度**：O(n)
- **空间复杂度**：O(n) - 递归栈深度
- **特点**：代码可读性更好，也是 LeetCode 官方递归解法常用写法

### 解法5：快慢指针 + 原地半链反转（★推荐）

**思路**：
1. 快慢指针找中点
2. 反转后半段
3. 双指针同步比对
4. （可选）把链表还原

```java
public static boolean isPalindromeByTwoPointers(ListNode head)
public static boolean isPalindromeByTwoPointersSimple(ListNode head) // 简化版
```

- **时间复杂度**：O(n)
- **空间复杂度**：O(1) - 只动指针
- **特点**：满足题目"进阶"要求，面试最常考

## 算法复杂度对比

| 解法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|------------|------------|------|
| 1. 整链反转比对 | O(n) | O(n) | 最容易想到 |
| 2. 栈 | O(n) | O(n) | 代码最短 |
| 3. 递归(双返回值) | O(n) | O(n) | 体现多返回值技巧 |
| 4. 递归(封装类) | O(n) | O(n) | 代码可读性好 |
| 5. 快慢指针+半链反转 | O(n) | O(1) | ★满足进阶要求 |

## 使用示例

```java
// 创建测试链表: [1, 2, 2, 1]
ListNode head = ListNode.fromArray(new int[]{1, 2, 2, 1});

// 使用不同解法测试
boolean result1 = PalindromeLinkedListSolutions.isPalindromeByReverseWhole(head);
boolean result2 = PalindromeLinkedListSolutions.isPalindromeByStack(head);
boolean result3 = PalindromeLinkedListSolutions.isPalindromeByRecursion(head);
boolean result4 = PalindromeLinkedListSolutions.isPalindromeByComparator(head);
boolean result5 = PalindromeLinkedListSolutions.isPalindromeByTwoPointers(head);

// 所有结果都应该是 true
```

## 运行测试

### 功能测试

```bash
cd /path/to/algorithm
javac -cp . src/main/java/algorithm/selectedtopics/leetcode/palindromelinkedlist/*.java
java -cp src/main/java algorithm.selectedtopics.leetcode.palindromelinkedlist.PalindromeLinkedListTest
```

### 性能测试

```bash
java -cp src/main/java algorithm.selectedtopics.leetcode.palindromelinkedlist.PalindromeLinkedListPerformanceTest
```

## 测试用例

测试包含以下场景：
- 空链表
- 单节点
- 两节点（回文/非回文）
- 奇数长度（回文/非回文）
- 偶数长度（回文/非回文）
- 长回文链表
- 相同值链表
- 边界情况

## 性能分析

根据性能测试结果：

1. **解法5（快慢指针）** 在时间和空间上都是最优的
2. **解法2（栈）** 代码最简洁，适合快速实现
3. **解法3/4（递归）** 在大数据量时可能栈溢出
4. **解法1（整链反转）** 内存开销最大

## 面试建议

1. **首选解法5**：满足进阶要求，展示算法功底
2. **备选解法2**：如果时间紧张，栈解法最快实现
3. **展示思路**：可以先说出多种思路，再选择最优解实现
4. **注意细节**：
   - 处理空链表和单节点
   - 快慢指针的边界条件
   - 是否需要还原链表结构

## 扩展思考

1. **链表修改**：解法5会修改原链表结构，是否需要还原？
2. **并发安全**：如果多线程访问，如何保证安全？
3. **其他数据结构**：如何扩展到双向链表、循环链表？
4. **相关问题**：回文子串、最长回文子序列等变种问题