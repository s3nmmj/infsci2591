/**
 * Assignment: Project 4, Problem 1
 */

import java.util.Arrays;

/**
 * Problem 1
 * Sorting by comparison counting is a technique where for each element of the array to be sorted, the total number
 * of elements smaller than this element is stored in a table. These numbers will indicate the positions of the
 * elements in the sorted array. By copying the elements of the array to their appropriate positions, the new array
 * will be sorted. Here are two examples: if the count is 10 for an element, it should be in the 11th position in the
 * sorted array; if the element is the smallest in the array, the count would be 0 and the element should be in the
 * 1st position in the sorted array. Below is an original array, the table for the counts, and the sorted array.
 * <p>
 * Original array: {62, 31, 84, 96, 19, 47}
 * <p>
 * Table for the counts: [3, 1, 4, 5, 0, 2]
 * <p>
 * Sorted array: {19, 31, 47, 62, 84, 96}
 */
public class Problem1 {
    public class Result {
        int[] countingTable;
        int[] sortedArray;

        public Result(int n) {
            countingTable = new int[n];
            Arrays.fill(countingTable, 0);

            sortedArray = new int[n];
            Arrays.fill(sortedArray, 0);
        }
    }

    public Result comparisonCountingSort(int[] array) {
        int n = array.length;
        Result result = new Result(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] > array[j]) {
                    result.countingTable[i]++;
                } else {
                    result.countingTable[j]++;
                }
            }
        }
        for (int i = 0; i < array.length; i++) {
            result.sortedArray[result.countingTable[i]] = array[i];
        }

        return result;
    }

    public static void main(String[] args) {
        runTestCase(0, new int[]{62, 31, 84, 96, 19, 47});
        runTestCase(1, new int[]{1, 4, 1, 2, 7, 5, 2});
        runTestCase(2, new int[]{5, 2, 9, 5, 2, 3, 5});
    }

    private static void runTestCase(int index, int[] array) {
        System.out.printf("Test Case %d:\n", index);
        Problem1 problem1 = new Problem1();
        Result result = problem1.comparisonCountingSort(array);
        System.out.printf("\tOriginal array: %s\n", arrayToString(array));
        System.out.printf("\tTable for the counts: %s\n", arrayToString(result.countingTable));
        System.out.printf("\tSorted array: %s\n", arrayToString(result.sortedArray));
    }

    private static String arrayToString(int[] array) {
        return Arrays.toString(array).replace("[", "{").replace("]", "}");
    }
}
