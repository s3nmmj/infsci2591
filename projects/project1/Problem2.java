/**
 * Assignment: Project 1, Problem 2
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Problem2 {

    /*
        A subset of consecutive elements of an array whose elements are 0 or 1 is called balanced if the subset
        contains as many 0 as 1 elements. Design and implement an algorithm with linear complexity that finds
        the indices of the largest balanced subset in an array of n elements . The algorithm should print the
        indices of the largest balanced subset if one exists or otherwise, if there is no such a subset in the array,
        print "No Balanced Subset".
     */
    public static void largestBalancedSubset(int[] data) {
        int sum = 0;
        int largest = 0;
        int largestIndex = -1;
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            int num = data[i] == 0 ? -1 : data[i];
            sum = sum + num;
            if (sum == 0) {
                largest = i + 1;
                largestIndex = i;
            }

            if (seen.containsKey(sum)) {
                if (largest < i - seen.get(sum)) {
                    largest = i - seen.get(sum);
                    largestIndex = i;
                }
            } else
                seen.put(sum, i);
        }

        if (largestIndex != -1) {
            System.out.print("Indices of Largest Balanced Subset: ");
            for (int i = largestIndex - largest + 1; i <= largestIndex; i++) {
                System.out.print(i);
                if (i != largestIndex) {
                    System.out.print(", ");
                } else {
                    System.out.println();
                }
            }
        } else {
            System.out.println("No Balanced Subset");
        }

    }

    public static void main(String[] args) {
        System.out.println("Test Cases");
        // Test Case 1
        int[] testCase1 = {0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1};
        printTestCase(testCase1, 1);
        System.out.println("Output:");
        largestBalancedSubset(testCase1);

        // Test Case 2
        int[] testCase2 = {1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0};
        printTestCase(testCase2, 2);
        System.out.println("Output:");
        largestBalancedSubset(testCase2);

        // Test Case 3
        int[] testCase3 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
        printTestCase(testCase3, 3);
        System.out.println("Output:");
        largestBalancedSubset(testCase3);

        // Test Case 4
        int[] testCase4 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        printTestCase(testCase4, 4);
        System.out.println("Output:");
        largestBalancedSubset(testCase4);
    }

    public static void printTestCase(int[] testCase, int index) {
        System.out.printf("\nTest Case %d: %s\n", index, Arrays.toString(testCase).replace("[", "{").replace("]", "}"));
    }
}
