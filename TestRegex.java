public class TestRegex {
    public static void main(String[] args) {
        String output = "Flip sequence (positions): 1 \n|Search Times| : 1\nTotal Swap times (minimum flips) = 1\n";
        System.out.println("Output: '" + output + "'");
        System.out.println("Matches .*\\d+.*: " + output.matches(".*\\d+.*"));
        System.out.println("Contains digit: " + output.matches(".*\\d.*"));
        System.out.println("Contains number: " + output.contains("1"));
    }
}
