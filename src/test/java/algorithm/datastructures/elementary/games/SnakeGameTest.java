package algorithm.datastructures.elementary.games;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/**
 * SnakeGame类的单元测试
 * 测试贪吃蛇游戏的各种场景和边界情况
 * 
 * @author magicliang
 * @date 2025-09-07
 */
public class SnakeGameTest {
    
    private SnakeGame game;
    
    @BeforeEach
    void setUp() {
        game = new SnakeGame();
    }
    
    /**
     * 创建标准测试蛇身：[(2,0), (1,0), (0,0)]
     * 蛇头在(2,0)，向右移动
     */
    private LinkedList<Position> createTestSnake() {
        LinkedList<Position> snake = new LinkedList<>();
        snake.add(new Position(2, 0));  // 头部
        snake.add(new Position(1, 0));  // 身体
        snake.add(new Position(0, 0));  // 尾部
        return snake;
    }
    
    // ==================== 基本移动测试 ====================
    
    @Test
    void testMoveRight_normalMovement() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(5, 5);  // 食物不在路径上
        
        boolean success = game.move(snake, food, 'R');
        
        assertTrue(success, "向右移动应该成功");
        assertEquals(3, snake.size(), "蛇身长度应该保持不变");
        assertEquals(new Position(3, 0), snake.peekFirst(), "新头部应该在(3,0)");
        assertEquals(new Position(1, 0), snake.peekLast(), "新尾部应该在(1,0)");
    }
    
    @Test
    void testMoveLeft_normalMovement() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(5, 5);
        
        boolean success = game.move(snake, food, 'L');
        
        assertTrue(success, "向左移动应该成功");
        assertEquals(3, snake.size(), "蛇身长度应该保持不变");
        assertEquals(new Position(1, 0), snake.peekFirst(), "新头部应该在(1,0)");
        assertEquals(new Position(1, 0), snake.peekLast(), "新尾部应该在(1,0)");
    }
    
    @Test
    void testMoveUp_normalMovement() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(5, 5);
        
        boolean success = game.move(snake, food, 'U');
        
        assertTrue(success, "向上移动应该成功");
        assertEquals(3, snake.size(), "蛇身长度应该保持不变");
        assertEquals(new Position(2, -1), snake.peekFirst(), "新头部应该在(2,-1)");
        assertEquals(new Position(1, 0), snake.peekLast(), "新尾部应该在(1,0)");
    }
    
    @Test
    void testMoveDown_normalMovement() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(5, 5);
        
        boolean success = game.move(snake, food, 'D');
        
        assertTrue(success, "向下移动应该成功");
        assertEquals(3, snake.size(), "蛇身长度应该保持不变");
        assertEquals(new Position(2, 1), snake.peekFirst(), "新头部应该在(2,1)");
        assertEquals(new Position(1, 0), snake.peekLast(), "新尾部应该在(1,0)");
    }
    
    // ==================== 吃食物测试 ====================
    
    @Test
    void testEatFood_snakeGrows() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(3, 0);  // 食物在蛇头前方
        
        boolean success = game.move(snake, food, 'R');
        
        assertTrue(success, "吃食物移动应该成功");
        assertEquals(4, snake.size(), "吃食物后蛇身应该增长");
        assertEquals(new Position(3, 0), snake.peekFirst(), "新头部应该在食物位置");
        assertEquals(new Position(0, 0), snake.peekLast(), "尾部应该保留");
    }
    
    @Test
    void testEatFood_multipleTimes() {
        LinkedList<Position> snake = createTestSnake();
        
        // 第一次吃食物
        Position food1 = new Position(3, 0);
        game.move(snake, food1, 'R');
        assertEquals(4, snake.size(), "第一次吃食物后长度应该是4");
        
        // 第二次吃食物
        Position food2 = new Position(4, 0);
        game.move(snake, food2, 'R');
        assertEquals(5, snake.size(), "第二次吃食物后长度应该是5");
    }
    
    // ==================== 碰撞检测测试 ====================
    
    @Test
    void testCollisionWithSelf() {
        // 创建一个会撞到自己的场景
        LinkedList<Position> snake = new LinkedList<>();
        snake.add(new Position(1, 1));  // 头部
        snake.add(new Position(1, 0));  // 身体
        snake.add(new Position(0, 0));  // 身体
        snake.add(new Position(0, 1));  // 尾部
        
        Position food = new Position(5, 5);
        
        // 向左移动会撞到尾部
        boolean success = game.move(snake, food, 'L');
        
        assertFalse(success, "撞到自己应该返回false");
    }
    
    @Test
    void testNoCollisionWithTail_whenNotEating() {
        // 测试正常情况下不会与即将移除的尾部碰撞
        LinkedList<Position> snake = new LinkedList<>();
        snake.add(new Position(1, 0));  // 头部
        snake.add(new Position(0, 0));  // 尾部
        
        Position food = new Position(5, 5);  // 不吃食物
        
        // 向下移动，然后向右，再向上，最后向左回到原尾部位置
        game.move(snake, food, 'D');  // 头部到(1,1)，尾部到(1,0)
        game.move(snake, food, 'R');  // 头部到(2,1)，尾部到(1,1)
        game.move(snake, food, 'U');  // 头部到(2,0)，尾部到(2,1)
        boolean success = game.move(snake, food, 'L');  // 头部到(1,0)，这是最初的尾部位置
        
        assertTrue(success, "移动到原尾部位置应该成功");
    }
    
    // ==================== 边界情况测试 ====================
    
    @Test
    void testEmptySnake() {
        LinkedList<Position> emptySnake = new LinkedList<>();
        Position food = new Position(0, 0);
        
        boolean success = game.move(emptySnake, food, 'R');
        
        assertFalse(success, "空蛇应该返回false");
    }
    
    @Test
    void testNullSnake() {
        boolean success = game.move(null, new Position(0, 0), 'R');
        
        assertFalse(success, "null蛇应该返回false");
    }
    
    @Test
    void testSingleNodeSnake() {
        LinkedList<Position> singleSnake = new LinkedList<>();
        singleSnake.add(new Position(0, 0));
        Position food = new Position(5, 5);
        
        boolean success = game.move(singleSnake, food, 'R');
        
        assertTrue(success, "单节点蛇移动应该成功");
        assertEquals(1, singleSnake.size(), "单节点蛇长度应该保持1");
        assertEquals(new Position(1, 0), singleSnake.peekFirst(), "头部应该移动到新位置");
    }
    
    @Test
    void testSingleNodeSnake_eatFood() {
        LinkedList<Position> singleSnake = new LinkedList<>();
        singleSnake.add(new Position(0, 0));
        Position food = new Position(1, 0);  // 食物在移动方向上
        
        boolean success = game.move(singleSnake, food, 'R');
        
        assertTrue(success, "单节点蛇吃食物应该成功");
        assertEquals(2, singleSnake.size(), "吃食物后应该增长到2个节点");
        assertEquals(new Position(1, 0), singleSnake.peekFirst(), "头部应该在食物位置");
        assertEquals(new Position(0, 0), singleSnake.peekLast(), "原头部应该成为尾部");
    }
    
    @Test
    void testInvalidDirection() {
        LinkedList<Position> snake = createTestSnake();
        Position food = new Position(5, 5);
        
        boolean success = game.move(snake, food, 'X');  // 无效方向
        
        assertTrue(success, "无效方向应该返回true（不移动）");
        assertEquals(3, snake.size(), "蛇身长度应该不变");
        assertEquals(new Position(2, 0), snake.peekFirst(), "头部位置应该不变");
    }
    
    // ==================== Position类测试 ====================
    
    @Test
    void testPositionEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 1);
        
        assertEquals(pos1, pos2, "相同坐标的Position应该相等");
        assertNotEquals(pos1, pos3, "不同坐标的Position应该不相等");
        assertEquals(pos1, pos1, "Position应该等于自己");
        assertNotEquals(pos1, null, "Position不应该等于null");
        assertNotEquals(pos1, "string", "Position不应该等于其他类型对象");
    }
    
    @Test
    void testPositionHashCode() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 1);
        
        assertEquals(pos1.hashCode(), pos2.hashCode(), "相等的Position应该有相同的hashCode");
        // 注意：不相等的对象可能有相同的hashCode，但这里测试不同坐标通常有不同hashCode
        assertNotEquals(pos1.hashCode(), pos3.hashCode(), "不同坐标的Position通常应该有不同的hashCode");
    }
    
    @Test
    void testPositionToString() {
        Position pos = new Position(3, 4);
        String str = pos.toString();
        
        assertTrue(str.contains("3"), "toString应该包含x坐标");
        assertTrue(str.contains("4"), "toString应该包含y坐标");
        assertEquals("(3,4)", str, "toString格式应该正确");
    }
    
    // ==================== 游戏逻辑综合测试 ====================
    
    @Test
    void testCompleteGameSequence() {
        LinkedList<Position> snake = createTestSnake();
        
        // 模拟一个完整的游戏序列
        Position food1 = new Position(3, 0);
        assertTrue(game.move(snake, food1, 'R'), "第1步：向右吃食物");
        assertEquals(4, snake.size(), "吃食物后长度应该是4");
        
        Position food2 = new Position(5, 5);  // 移走食物
        assertTrue(game.move(snake, food2, 'D'), "第2步：向下移动");
        assertEquals(4, snake.size(), "普通移动长度应该保持4");
        
        assertTrue(game.move(snake, food2, 'L'), "第3步：向左移动");
        assertTrue(game.move(snake, food2, 'L'), "第4步：继续向左移动");
        
        // 验证最终状态
        assertEquals(new Position(1, 1), snake.peekFirst(), "最终头部位置应该正确");
        assertEquals(4, snake.size(), "最终长度应该是4");
    }
    
    @Test
    void testSnakeGameToString() {
        String result = game.toString();
        assertEquals("SnakeGame Logic", result, "toString应该返回正确的描述");
    }
}