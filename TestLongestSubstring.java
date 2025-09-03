import algorithm.advanced.two_pointers.SlidingWindow;

public class TestLongestSubstring {
    public static void main(String[] args) {
        SlidingWindow solution = new SlidingWindow();
        
        try {
            System.out.println("Test 1: " + solution.lengthOfLongestSubstring("abcabcbb"));
            System.out.println("Test 2: " + solution.lengthOfLongestSubstring("bbbbb"));
            System.out.println("Test 3: " + solution.lengthOfLongestSubstring("pwwkew"));
            System.out.println("Test 4: " + solution.lengthOfLongestSubstring(""));
            System.out.println("Test 5: " + solution.lengthOfLongestSubstring(null));
            System.out.println("Test 6: " + solution.lengthOfLongestSubstring("a"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
