/**
 * Assignment: Project 2, Problem 1
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem1 {
    /**
     * Problem 1
     * Design and implement a dynamic programming algorithm that reads in an array A whose elements are 0 and 1
     * and finds the largest square of 1 in the array.
     * The algorithm must output the size of the square and the indices of the top left corner of the square.
     * In case the matrix contains more than one largest square of the same size,
     * the algorithm must output the indices for each square.
     */
    public static int largestSquare(int[][] array, List<int[]> indices) {
        int rows = array.length;
        int columns = rows > 0 ? array[0].length : 0;
        int[][] dp = new int[rows + 1][columns + 1];
        int largestSquareSide = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                if (array[i - 1][j - 1] == 1) {
                    // dp[i][j]=min(dp[i−1][j],dp[i−1][j−1],dp[i][j−1])+1
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    largestSquareSide = Math.max(largestSquareSide, dp[i][j]);
                }
            }
        }

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (dp[i][j] == largestSquareSide) {
                    indices.add(new int[]{i - largestSquareSide + 1, j - largestSquareSide + 1});
                }
            }
        }


        return largestSquareSide;
    }

    public static void main(String[] args) {
        System.out.println("Test Case 1:");
        int[][] test1 = {
                {1, 0, 1, 0, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0}
        };
        List<int[]> test1Indices = new ArrayList<>();
        int test1SquareSize = largestSquare(test1, test1Indices);
        printArray(test1);
        System.out.printf("\n\tOutput: square size = %d, indices %s\n", test1SquareSize, formatIndices(test1Indices));
        System.out.println();

        System.out.println("Test Case 2:");
        int[][] test2 = {
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0},
                {1, 0, 1, 0, 1, 1},
                {0, 0, 1, 1, 1, 1}
        };
        List<int[]> test2Indices = new ArrayList<>();
        int test2SquareSize = largestSquare(test2, test2Indices);
        printArray(test2);
        System.out.printf("\n\tOutput: square size = %d, indices %s\n", test2SquareSize, formatIndices(test2Indices));
    }

    public static void printArray(int[][] array) {
        for (int[] arr : array) {
            System.out.println("\t" + Arrays.toString(arr).replaceAll("[\\[\\]]", ""));
        }
    }

    public static String formatIndices(List<int[]> indices) {
        StringBuilder indicesSb = new StringBuilder();
        for (int i = 0; i < indices.size(); i++) {
            indicesSb.append(Arrays.toString(indices.get(i)));
            if (i != indices.size() - 1) {
                indicesSb.append(";");
            }
        }
        return indicesSb.toString();
    }
}
