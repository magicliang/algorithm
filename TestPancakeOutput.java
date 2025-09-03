import algorithm.selectedtopics.computational.PancakeSorting;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestPancakeOutput {
    public static void main(String[] args) {
        PancakeSorting sorter = new PancakeSorting();
        
        // 重定向标准输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // 运行测试
        int[] input = {1};
        sorter.run(input, input.length);
        sorter.output();
        
        // 恢复标准输出
        System.setOut(originalOut);
        
        // 检查输出
        String output = outputStream.toString();
        System.out.println("Captured output:");
        System.out.println("'" + output + "'");
        System.out.println("Length: " + output.length());
        System.out.println("Contains 'Total Swap times': " + output.contains("Total Swap times"));
        System.out.println("Contains '|Search Times|': " + output.contains("|Search Times|"));
        System.out.println("Contains 'minimum flips) = 0': " + output.contains("minimum flips) = 0"));
    }
}
