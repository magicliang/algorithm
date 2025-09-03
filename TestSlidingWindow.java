import algorithm.advanced.two_pointers.SlidingWindow;

public class TestSlidingWindow {
    public static void main(String[] args) {
        SlidingWindow solution = new SlidingWindow();
        
        // 测试基本情况
        System.out.println("Test 1: " + solution.minWindow("ADOBECODEBANC", "ABC"));
        
        // 测试复杂情况
        System.out.println("Test 2: " + solution.minWindow("ADOBECODEBANC", "AABC"));
        System.out.println("Test 3: " + solution.minWindow("ADOBECODEBANC", "ABCD"));
        
        // 测试边界情况
        System.out.println("Test 4: " + solution.minWindow("a", "a"));
        System.out.println("Test 5: " + solution.minWindow("a", "aa"));
    }
}
