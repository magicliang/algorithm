public class TestRegexSimple {
    public static void main(String[] args) {
        String test = "abc123def";
        System.out.println("Test string: '" + test + "'");
        System.out.println("matches(\".*\\\\d+.*\"): " + test.matches(".*\\\\d+.*"));
        System.out.println("matches(\".*\\\\d.*\"): " + test.matches(".*\\\\d.*"));
        System.out.println("matches(\".*[0-9]+.*\"): " + test.matches(".*[0-9]+.*"));
        System.out.println("matches(\".*[0-9].*\"): " + test.matches(".*[0-9].*"));
        
        // 测试实际输出
        String output = "Flip sequence (positions): 1 \\n|Search Times| : 2\\nTotal Swap times (minimum flips) = 1\\n";
        System.out.println("\\nOutput string: '" + output + "'");
        System.out.println("matches(\".*\\\\d+.*\"): " + output.matches(".*\\\\d+.*"));
        System.out.println("matches(\".*[0-9]+.*\"): " + output.matches(".*[0-9]+.*"));
        System.out.println("contains('1'): " + output.contains("1"));
    }
}
