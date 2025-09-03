import algorithm.selectedtopics.computational.PancakeSorting;

public class TestPancakeSingle {
    public static void main(String[] args) {
        PancakeSorting sorter = new PancakeSorting();
        
        System.out.println("=== Single element test ===");
        int[] input1 = {1};
        sorter.run(input1, input1.length);
        sorter.output();
        
        System.out.println("\n=== Already sorted test ===");
        int[] input2 = {1, 2, 3};
        sorter = new PancakeSorting(); // 重新创建实例
        sorter.run(input2, input2.length);
        sorter.output();
        
        System.out.println("\n=== Two elements test ===");
        int[] input3 = {1, 2};
        sorter = new PancakeSorting(); // 重新创建实例
        sorter.run(input3, input3.length);
        sorter.output();
    }
}
