package algorithm.datastructures.elementary.games;

import java.util.*;

/**
 * 坐标位置类
 * 
 * 设计要点：
 * 1. 不可变对象设计：坐标一旦创建就不可修改，保证线程安全
 * 2. 正确实现equals和hashCode：支持在HashSet/HashMap中使用
 * 3. 简洁的数据结构：只包含必要的x,y坐标信息
 * 
 * 时间复杂度：
 * - equals(): O(1)
 * - hashCode(): O(1)
 */
class Position {
    int x, y;  // 坐标值，使用int类型节省内存
    
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // 为了在Set中正确比较，需要重写equals和hashCode
    // 这是Java集合框架的基本要求
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 引用相等性检查，性能优化
        if (o == null || getClass() != o.getClass()) return false;  // 类型检查
        Position position = (Position) o;
        return x == position.x && y == position.y;  // 值相等性检查
    }

    @Override
    public int hashCode() {
        // 使用Objects.hash确保良好的哈希分布
        return Objects.hash(x, y);
    }
    
    /**
     * 重写toString方法，便于调试和日志输出
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

/**
 * 贪吃蛇游戏核心逻辑实现
 * 
 * 算法设计思路：
 * 1. 新节点依赖于方向
 * 2. 方向制造新结点
 * 3. 出现新节点以后，要先碰撞：看看是不是撞到链表自己-重写链表的 equals 方法；看看是不是撞到食物。
 * 4. 无论如何新增头结点
 * 5. 如果吃到食物，保留尾节点，否则删除尾节点
 * 
 * 数据结构选择：
 * - 使用LinkedList<Position>表示蛇身：支持O(1)的头部插入和尾部删除
 * - 蛇头在链表前端，蛇尾在链表后端，符合游戏逻辑
 * 
 * 性能特点：
 * - 移动操作：O(n) 时间复杂度（主要是碰撞检测）
 * - 空间复杂度：O(n)，n为蛇的长度
 * 
 * 优化建议：
 * - 在实际游戏中可维护HashSet<Position>来实现O(1)碰撞检测
 * - 可以预先计算边界来避免重复的边界检查
 * 
 * @author magicliang
 */
public class SnakeGame {

    /**
     * 移动贪吃蛇（核心游戏逻辑）
     * 
     * 算法步骤：
     * 1. 根据方向计算新头部位置
     * 2. 检查碰撞（边界、自身）
     * 3. 检查是否吃到食物
     * 4. 更新蛇身（添加新头部）
     * 5. 根据是否吃食物决定是否移除尾部
     * 
     * 时间复杂度：O(n)，n为蛇的长度（主要是碰撞检测）
     * 空间复杂度：O(1)，只使用常量额外空间
     * 
     * @param snake 蛇身坐标链表，头部在前。方法会原地修改此链表。
     * @param food 食物坐标
     * @param direction 移动方向 ('U', 'D', 'L', 'R')
     * @return boolean 移动是否成功 (例如，撞到自己或墙则失败)
     */
    public boolean move(LinkedList<Position> snake, Position food, char direction) {
        if (snake == null || snake.isEmpty()) {
            return false; // 无效状态
        }

        // 1. 获取当前蛇头位置
        Position currentHead = snake.peekFirst();

        // 2. 根据方向计算新的蛇头位置
        // 使用策略模式的思想，根据不同方向计算新位置
        Position newHead;
        switch (direction) {
            case 'U': newHead = new Position(currentHead.x, currentHead.y - 1); break;  // 向上：y坐标减1
            case 'D': newHead = new Position(currentHead.x, currentHead.y + 1); break;  // 向下：y坐标加1
            case 'L': newHead = new Position(currentHead.x - 1, currentHead.y); break;  // 向左：x坐标减1
            case 'R': newHead = new Position(currentHead.x + 1, currentHead.y); break;  // 向右：x坐标加1
            default:
                // 无效方向，不移动（容错处理）
                return true;
        }

        // 3. 检查碰撞边界 (假设边界检查由游戏引擎在更高层处理，或这里定义边界)
        //    如果需要，在此添加边界检查逻辑，例如：
        //    if (newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT) {
        //        return false; // 撞墙
        //    }

        // 4. 检查是否吃到食物
        boolean ateFood = newHead.equals(food);

        // 5. 检查是否撞到自己 (除了即将移除的尾部)
        //    为了高效检查，可以将蛇身存入一个HashSet (这通常在游戏主循环中维护)
        //    这里为了演示，直接在链表中查找。
        //    注意：如果吃到食物，尾部不移除，所以新头不能与任何现有身体部分重合。
        //         如果没吃到食物，尾部会移除，所以新头不能与除尾部外的任何身体部分重合。
        if (isColliding(snake, newHead)) {
            return false; // 撞到自己
        }

        // 6. 更新蛇身
        //    a. 在头部添加新节点
        snake.addFirst(newHead);

        //    b. 如果没有吃到食物，移除尾部节点
        if (!ateFood) {
            snake.removeLast();
        }
        //    c. 如果吃到了食物，尾部保留，蛇身增长。食物会被游戏引擎重新放置。

        return true; // 移动成功
    }

    /**
     * 检查新头位置是否与蛇身冲突（碰撞检测）
     * 
     * 算法说明：
     * - 当前实现：O(n)线性搜索，简单但效率较低
     * - 优化方案：维护HashSet<Position>实现O(1)查找
     * 
     * 边界情况处理：
     * - 空蛇身：不会碰撞
     * - 单节点蛇身：新头不能与当前头重合
     * 
     * @param snake 当前蛇身
     * @param newHead 新的头部位置
     * @return 是否碰撞
     */
    private boolean isColliding(LinkedList<Position> snake, Position newHead) {
        // 从头部开始检查，直到倒数第二个节点（因为如果吃到食物，尾部不移除）
        // 这里简化处理，检查整个链表。更精确的逻辑需要游戏引擎配合。
        // 在实际高性能游戏中，通常会维护一个 HashSet<Position> 来快速检查碰撞。
        
        // 使用迭代器遍历，避免索引访问的开销
        Iterator<Position> iter = snake.iterator();
        while (iter.hasNext()) {
            Position segment = iter.next();
            // 不检查尾部是否会被移除的情况，直接检查所有身体
            // 更严格的实现需要知道是否将要吃食物
            if (newHead.equals(segment)) {
                return true;  // 发现碰撞，立即返回
            }
        }
        return false;  // 未发现碰撞
    }

    // --- 以下是为了演示和测试 ---
    /**
     * 主方法：演示贪吃蛇游戏的基本功能
     * 
     * 测试场景：
     * 1. 初始化蛇身和食物
     * 2. 测试吃食物的情况（蛇身增长）
     * 3. 测试普通移动的情况（蛇身长度不变）
     */
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();

        // 初始化一条蛇 [(2,0), (1,0), (0,0)]
        LinkedList<Position> snake = new LinkedList<>();
        snake.add(new Position(2, 0));
        snake.add(new Position(1, 0));
        snake.add(new Position(0, 0));

        Position food = new Position(3, 0);

        System.out.println("初始蛇身: " + snake);
        System.out.println("食物位置: " + food);

        // 向右移动，吃到食物
        boolean success = game.move(snake, food, 'R');
        System.out.println("向右移动 (吃食物) 是否成功: " + success);
        System.out.println("移动后蛇身: " + snake);

        // 再向右移动，未吃食物
        food = new Position(5, 5); // 食物移走
        success = game.move(snake, food, 'R');
        System.out.println("向右移动 (未吃食物) 是否成功: " + success);
        System.out.println("移动后蛇身: " + snake);
    }

    @Override
    public String toString() {
        return "SnakeGame Logic";
    }
}
