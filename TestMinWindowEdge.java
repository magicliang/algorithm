import algorithm.advanced.two_pointers.SlidingWindow;

public class TestMinWindowEdge {
    public static void main(String[] args) {
        SlidingWindow solution = new SlidingWindow();
        
        try {
            System.out.println("Test empty target: '" + solution.minWindow("a", "") + "'");
            System.out.println("Test null target: '" + solution.minWindow("a", null) + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
