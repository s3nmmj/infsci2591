/**

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz1 {
    public static void main(String[] args) {
        System.out.println("Test Cases");
        int[] testCase1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        printTestCase(testCase1, 1);
        List<int[]> result1 = sublistWithMinimizedDifference(testCase1);
        printResult(result1);

        int[] testCase2 = new int[]{10, 20, 15, 5, 25, 100};
        printTestCase(testCase2, 2);
        List<int[]> result2 = sublistWithMinimizedDifference(testCase2);
        printResult(result2);
    }

    public static List<int[]> sublistWithMinimizedDifference(int[] nums) {
        int n = nums.length / 2;
        List<int[]> combinations = combination(nums, n);
        int sumOfNums = sum(nums);
        int minDifference = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < combinations.size(); i++) {
            int sumOfCombination = sum(combinations.get(i));
            int difference = Math.abs(2 * sumOfCombination - sumOfNums);
            if (minDifference > difference) {
                minDifference = difference;
                index = i;
            }
        }
        List<int[]> result = new ArrayList<>();
        int[] firstSublist = combinations.get(index);
        result.add(firstSublist);
        // remain numbers
        int[] secondSublist = new int[n];
        int k = 0;
        int i = 0;
        int j = 0;
        Arrays.sort(firstSublist);
        Arrays.sort(nums);
        while (i < nums.length) {
            if (j < firstSublist.length && nums[i] == firstSublist[j]) {
                i++;
                j++;
            } else {
                secondSublist[k] = nums[i];
                i++;
                k++;
            }
        }
        result.add(secondSublist);

        return result;
    }

    public static List<int[]> combination(int[] nums, int n) {
        List<int[]> result = new ArrayList<>();
        if (n < 1 || n > nums.length) {
            return result;
        }
        if (n == 1) {
            for (int num : nums) {
                result.add(new int[]{num});
            }
        } else {
            int head = nums[0];
            nums = Arrays.copyOfRange(nums, 1, nums.length);

            while (nums.length >= n - 1) {
                List<int[]> subCombination = combination(nums, n - 1);
                for (int[] sub : subCombination) {
                    int[] com = new int[sub.length + 1];
                    com[0] = head;
                    for (int i = 1; i < com.length; i++) {
                        com[i] = sub[i - 1];
                    }
                    result.add(com);
                }
                head = nums[0];
                nums = Arrays.copyOfRange(nums, 1, nums.length);
            }
        }

        return result;
    }

    public static int sum(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        return sum;
    }

    public static void printTestCase(int[] nums, int index) {
        System.out.printf("Test Case %d: ", index);
        System.out.println(arrayToString(nums));
    }
    public static void printResult(List<int[]> result) {
        int[] left = result.get(0);
        int[] right = result.get(1);
        int leftSum = sum(left);
        int rightSum = sum(right);
        if (leftSum < rightSum) {
            int temp = rightSum;
            rightSum = leftSum;
            leftSum = temp;
            left = result.get(1);
            right = result.get(0);
        }
        System.out.print("Output: ");
        System.out.printf("%s ", arrayToString(left));
        System.out.printf("%s ", arrayToString(right));
        System.out.println();
        System.out.printf("%s - %s = %d - %d = %d\n",
                arrayToExpression(left),
                arrayToExpression(right),
                leftSum,
                rightSum,
                leftSum - rightSum);
        System.out.println();
    }

    public static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(", ");
            } else {
                sb.append("]");
            }
        }
        return sb.toString();
    }

    public static String arrayToExpression(int[] array) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append("+");
            } else {
                sb.append(")");
            }
        }
        return sb.toString();
    }
}