/**

 */

import java.util.Arrays;

public class Quiz2 {
    /**
     * searchTarget returns the index of the target number in the give data list, if the target number is found.
     * Otherwise, returns -1.
     */
    public static int searchTarget(int[] data, int target) {
        return search(data, 0, data.length, target);
    }

    /**
     * search returns the index of the target number in the given range of the list, if the target number is found.
     * If not found, it will return -1.
     * In the program, the range is divided into three sublists.
     */
    public static int search(int[] data, int left, int right, int target) {
        if (left >= right && data[left] != target) {
            return -1;
        }

        int subListLength = (right - left) / 3;
        int pivot1 = left + subListLength;
        int pivot2 = pivot1 + subListLength;

        if (target == data[pivot1]) {
            return pivot1;
        } else if (target == data[pivot2]) {
            return pivot2;
        } else if (target < data[pivot1]) {
            return search(data, left, pivot1 - 1, target);
        } else if (target < data[pivot2]) {
            return search(data, pivot1 + 1, pivot2 - 1, target);
        } else {
            return search(data, pivot2 + 1, right, target);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test Cases");
        int[] testCase1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target1 = 8;
        int result = searchTarget(testCase1, target1);
        printResult(testCase1, target1, result, 1);
        int[] testCase2 = {1, 5, 7, 9, 15, 30, 33, 37, 41, 42, 43, 65, 69};
        int target2 = 32;
        result = searchTarget(testCase2, target2);
        printResult(testCase2, target2, result, 2);
    }

    public static void printResult(int[] data, int target, int result, int index) {
        System.out.printf("Test Case %d: Find %d in %s\n", index, target, Arrays.toString(data));
        if (result != -1) {
            System.out.printf("Output: The item %d is found at index %d.\n", target, result);
        } else {
            System.out.printf("Output: The item %d is not found.\n", target);
        }
        System.out.println();
    }
}
