import algorithm.advanced.two_pointers.TwoSumSorted;
import java.util.Arrays;

public class TestTwoSum {
    public static void main(String[] args) {
        TwoSumSorted solution = new TwoSumSorted();
        int[] numbers = {-3, -1, 0, 2, 4};
        int target = 1;
        int[] result = solution.twoSum(numbers, target);
        System.out.println("Input: " + Arrays.toString(numbers));
        System.out.println("Target: " + target);
        System.out.println("Result: " + Arrays.toString(result));
        System.out.println("Values: " + numbers[result[0]-1] + " + " + numbers[result[1]-1] + " = " + (numbers[result[0]-1] + numbers[result[1]-1]));
    }
}
