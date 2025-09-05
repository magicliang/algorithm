# 基于栈的回文算法实现

本项目基于自定义的栈数据结构实现了多种回文相关的算法，包括回文数检测、回文字符串检测、回文链表检测等。

## 文件结构

```
src/main/java/algorithm/datastructures/elementary/
├── Stack.java                      # 栈接口定义
├── ArrayStack.java                 # 基于数组的栈实现
├── LinkedStack.java                # 基于链表的栈实现
├── PalindromeAlgorithms.java       # 回文算法实现
├── PalindromeAlgorithmsTest.java   # 功能测试
└── PalindromePerformanceTest.java  # 性能测试
```

## 算法实现

### 1. 回文数检测 (`isPalindromeNumber`)

**思路**：将数字的每一位压入栈中，然后依次弹出重构数字与原数字比较。

**时间复杂度**：O(log n)，其中 n 是输入数字的大小  
**空间复杂度**：O(log n)，栈空间

**示例**：
```java
PalindromeAlgorithms.isPalindromeNumber(121);   // true
PalindromeAlgorithms.isPalindromeNumber(123);   // false
```

### 2. 回文字符串检测 (`isPalindromeString`)

**思路**：将字符串的前半部分压入栈中，然后与后半部分逐个比较。支持忽略大小写和非字母数字字符。

**时间复杂度**：O(n)，其中 n 是字符串长度  
**空间复杂度**：O(n/2)，栈空间

**示例**：
```java
PalindromeAlgorithms.isPalindromeString("racecar");                    // true
PalindromeAlgorithms.isPalindromeString("A man a plan a canal Panama"); // true
```

### 3. 回文链表检测

#### 普通版本 (`isPalindromeLinkedList`)
**思路**：遍历链表将所有值压入栈中，然后再次遍历链表与栈中弹出的值比较。

**时间复杂度**：O(n)  
**空间复杂度**：O(n)

#### 优化版本 (`isPalindromeLinkedListOptimized`)
**思路**：使用快慢指针找到链表中点，将后半部分压入栈，然后与前半部分比较。

**时间复杂度**：O(n)  
**空间复杂度**：O(n/2)

**示例**：
```java
ListNode head = ListNode.fromArray(new Integer[]{1, 2, 2, 1});
PalindromeAlgorithms.isPalindromeLinkedList(head);          // true
PalindromeAlgorithms.isPalindromeLinkedListOptimized(head); // true
```

### 4. 字符串转回文 (`makePalindrome`)

**思路**：将字符串的每个字符压入栈，然后弹出拼接到原字符串后面。

**示例**：
```java
PalindromeAlgorithms.makePalindrome("abc"); // "abccba"
```

### 5. 括号平衡检测 (`isBalancedParentheses`)

**思路**：遇到左括号压栈，遇到右括号弹栈匹配。

**示例**：
```java
PalindromeAlgorithms.isBalancedParentheses("([{}])"); // true
PalindromeAlgorithms.isBalancedParentheses("([)]");   // false
```

## 栈实现对比

| 特性 | ArrayStack | LinkedStack |
|------|------------|-------------|
| 底层结构 | 动态数组 | 单向链表 |
| 空间效率 | 较高（连续内存） | 较低（指针开销） |
| 扩容机制 | 需要扩容 | 无需扩容 |
| 缓存友好性 | 好 | 一般 |

## 性能测试结果

基于测试环境的性能数据：

- **回文数检测**：100,000 次操作耗时约 5.55ms
- **回文字符串检测**：10,000 次操作耗时约 14.14ms  
- **回文链表检测**：
  - 普通版本：1,000 次操作耗时约 2.96ms
  - 优化版本：1,000 次操作耗时约 1.79ms（节省约40%空间和时间）

## 运行测试

### 功能测试
```bash
cd /path/to/algorithm
javac -cp . src/main/java/algorithm/datastructures/elementary/*.java
java -cp src/main/java algorithm.datastructures.elementary.PalindromeAlgorithmsTest
```

### 性能测试
```bash
java -cp src/main/java algorithm.datastructures.elementary.PalindromePerformanceTest
```

## 算法特点

1. **基于栈的实现**：充分利用了栈的LIFO特性来处理回文问题
2. **多种优化策略**：提供了空间优化的算法版本
3. **完整的测试覆盖**：包含功能测试和性能测试
4. **良好的代码结构**：遵循面向对象设计原则，易于扩展和维护

## 扩展思考

1. **其他回文应用**：可以扩展到回文子串查找、最长回文子序列等问题
2. **算法优化**：可以考虑使用双指针等方法进一步优化空间复杂度
3. **并发安全**：当前实现非线程安全，可以考虑添加同步机制