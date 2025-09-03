import algorithm.selectedtopics.computational.PancakeSorting;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestActualOutput {
    public static void main(String[] args) {
        PancakeSorting sorter = new PancakeSorting();
        
        // 重定向标准输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // 运行测试
        int[] input = {2, 1};
        sorter.run(input, input.length);
        sorter.output();
        
        // 恢复标准输出
        System.setOut(originalOut);
        
        // 检查输出
        String output = outputStream.toString();
        System.out.println("Actual output:");
        System.out.println("'" + output + "'");
        System.out.println("Length: " + output.length());
        System.out.println("Bytes: " + java.util.Arrays.toString(output.getBytes()));
        System.out.println("Matches .*\\\\d+.*: " + output.matches(".*\\\\d+.*"));
        System.out.println("Matches .*\\\\d.*: " + output.matches(".*\\\\d.*"));
        System.out.println("Contains '1': " + output.contains("1"));
        
        // 测试简单的正则表达式
        System.out.println("Test simple regex:");
        String test = "abc123def";
        System.out.println("'" + test + "' matches .*\\\\d+.*: " + test.matches(".*\\\\d+.*"));
    }
}
