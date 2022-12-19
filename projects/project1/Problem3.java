/**
 * Assignment: Project 1, Problem 2
 */

import java.util.Arrays;
import java.util.Random;

public class Problem3 {

    /*
        Design and implement a divide-and-conquer algorithm that finds the two indices (i and j, 1 <= i <= j <= n)
        in an array of integers with maximum sum of contiguous elements.
     */
    public static int[] maximumSum(int[] data) {
        int[] resultIndices = new int[2];
        maxSum(data, 0, data.length - 1, resultIndices);
        return resultIndices;
    }

    public static int maxSum(int[] data, int left, int right, int[] resultIndices) {
        if (left == right) {
            resultIndices[0] = resultIndices[1] = left + 1;
            return data[left];
        }

        int middle = (left + right) / 2;
        int maximum = Integer.max(maxSum(data, left, middle, resultIndices), maxSum(data, middle + 1, right, resultIndices));

        int leftSum = data[middle];
        int[] tempResultIndices = new int[2];
        tempResultIndices[0] = middle + 1;
        int temp = 0;
        for (int i = middle; i >= left; i--) {
            temp += data[i];
            leftSum = Integer.max(leftSum, temp);
            if (leftSum == temp) {
                tempResultIndices[0] = i + 1;
            }
        }

        int rightSum = data[middle + 1];
        tempResultIndices[1] = middle + 2;
        temp = 0;
        for (int i = middle + 1; i <= right; i++) {
            temp += data[i];
            rightSum = Integer.max(rightSum, temp);
            if (rightSum == temp) {
                tempResultIndices[1] = i + 1;
            }
        }

        int result = Integer.max(maximum, leftSum + rightSum);
        if (result == (leftSum + rightSum)) {
            resultIndices[0] = tempResultIndices[0];
            resultIndices[1] = tempResultIndices[1];
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("Test Cases");
        int[] result;
        // Test Case 1
        int[] testCase1 = {-2, -5, 6, -2, -3, 1, 5, -6};
        result = maximumSum(testCase1);
        printTestResult(testCase1, result, new int[]{3, 7}, 1);

        // Test Case 2
        int[] testCase2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, -10, -100, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        result = maximumSum(testCase2);
        printTestResult(testCase2, result, new int[]{12, 21}, 2);

        System.out.println();

        // Test for generating time complexity data
        long startTime, endTime, elapsedTime;
        int[] data;
        for (int n = 500000; n <= 10000000; n += 500000) {
            data = generateRandomArray(n);
            startTime = System.currentTimeMillis();
            maximumSum(data);
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            System.out.printf("N = %d, Elapsed Time = %d\n", n, elapsedTime);
        }
    }

    public static int[] generateRandomArray(int n) {
        Random random = new Random();
        int[] data = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            data[i] = random.nextInt(-n, n);
        }
        return data;
    }

    public static void printTestResult(int[] data, int[] result, int[] expected, int index) {
        System.out.printf("\nTest Case %d: %s \n", index, Arrays.toString(data).replace("[", "{").replace("]", "}"));
        System.out.printf("Expected Output: i = %d and j = %d\n", expected[0], expected[1]);
        System.out.printf("Actual Output: i = %d and j = %d\n", result[0], result[1]);
    }

}
