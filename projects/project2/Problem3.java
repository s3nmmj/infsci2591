/**
 * Assignment: Project 2, Problem 3
 */

import java.util.*;

public class Problem3 {

    /**
     * hungarianAlgorithmSolution is a wrapper function to call hungarianAlgorithm,
     * and returns the formatted and calculated result.
     */
    public static String hungarianAlgorithmSolution(Integer[][] matrix, String[] rowTitle, String[] columnTitle) {
        try {
            StringBuilder sb = new StringBuilder();
            int total = 0;
            Integer[][] result = hungarianAlgorithm(matrix);
            for (Integer[] rc : result) {
                total += matrix[rc[0]][rc[1]];
                sb.append(String.format("((%s, %s), %d),", rowTitle[rc[0]], columnTitle[rc[1]], matrix[rc[0]][rc[1]]));
            }
            return total + " from " + sb.substring(0, sb.length() - 1) + ")";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Problem 3
     * <p>
     * Learn about the Hungarian (also known as Kuhn–Munkres) algorithm from various sources, including textbooks,
     * papers, websites, videos, etc. Implement the algorithm, analyze its time complexity theoretically,
     * and print its results for the test cases in here.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Hungarian_algorithm#Matrix_interpretation">Matrix interpretation</a>
     * Time complexity: O(n^3)
     */
    public static Integer[][] hungarianAlgorithm(Integer[][] matrix) throws IllegalAccessException {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix.length != matrix[0].length) {
            throw new IllegalAccessException("The matrix is not square!");
        }
        int n = matrix.length;

        // copy the original matrix since the matrix will be changed in each step
        Integer[][] matrixCopy = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, n);
        }

        step1(matrixCopy);

        // print2dArray(matrixCopy);
        step2(matrixCopy);
        // print2dArray(matrixCopy);

        int markingLines = 0;
        while (markingLines < n) {
            Integer[][] markingMatrix = new Integer[n][n];
            for (Integer[] mm : markingMatrix) {
                Arrays.fill(mm, 0);
            }
            markingLines = step3(matrixCopy, markingMatrix);
            // print2dArray(matrixCopy);
            // print2dArray(markingMatrix);

            if (markingLines < n) {
                step4(matrixCopy, markingMatrix);
                // print2dArray(matrixCopy);
                // print2dArray(markingMatrix);
            }
        }

        // print2dArray(matrixCopy);
        // System.out.println(markingLines);

        Integer[][] result = findCostMinimizingAssignment(matrixCopy);
        //print2dArray(result);

        return result;
    }

    /**
     * Step 1:
     * Performs row operations on the matrix. To do this, the lowest of all ai is taken and is subtracted
     * from each element in that row. This will lead to at least one zero in that row .
     * This procedure is repeated for all rows. We now have a matrix with at least one zero per row.
     * Time complexity: O(n^2)
     */
    public static void step1(Integer[][] matrix) {
        int minimum;
        for (int i = 0; i < matrix.length; i++) {
            minimum = Collections.min(Arrays.asList(matrix[i]));
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] -= minimum;
            }
        }
    }

    /**
     * Step 2:
     * Repeat the above procedure(Step 1) for all columns
     * The minimum element in each column is subtracted from all the elements in that column)
     * Time complexity: O(n^2)
     */
    public static void step2(Integer[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            int minimum = Integer.MAX_VALUE;
            for (Integer[] integers : matrix) {
                if (integers[j] < minimum) {
                    minimum = integers[j];
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] -= minimum;
            }
        }
    }

    /**
     * Step 3:
     * All zeros in the matrix must be covered by marking as few rows and/or columns as possible.
     * Time complexity: O(n^3)
     */
    public static int step3(Integer[][] matrix, Integer[][] markingMatrix) {
        int markingLines = 0;
        int n = matrix.length;
        Integer[][] zeroMatrix = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                zeroMatrix[i][j] = Integer.MAX_VALUE;
                if (matrix[i][j] == 0) {
                    int rowZeros = 0;
                    for (int k = 0; k < n; k++) {
                        if (matrix[i][k] == 0) {
                            rowZeros++;
                        }
                    }
                    int columnZeros = 0;
                    for (int k = 0; k < n; k++) {
                        if (matrix[k][j] == 0) {
                            columnZeros++;
                        }
                    }
                    if (columnZeros > rowZeros) {
                        zeroMatrix[i][j] = columnZeros;
                    } else if (columnZeros < rowZeros) {
                        zeroMatrix[i][j] = -rowZeros;
                    } else {
                        zeroMatrix[i][j] = 0;
                    }
                }
            }
        }

        // print2dArray(zeroMatrix);
        List<Integer[]> zeros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroMatrix[i][j] < Integer.MAX_VALUE) {
                    zeros.add(new Integer[]{Math.abs(zeroMatrix[i][j]), i, j});
                }
            }
        }

        zeros.sort((o1, o2) -> o2[0].compareTo(o1[0]));

        for (Integer[] zero : zeros) {
            int x = zero[1];
            int y = zero[2];
            if (zeroMatrix[x][y] == Integer.MAX_VALUE) {
                continue;
            }
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < n; i++) {
                if (zeroMatrix[i][y] < Integer.MAX_VALUE - 1) {
                    colSum++;
                }
            }
            for (int j = 0; j < n; j++) {
                if (zeroMatrix[x][j] < Integer.MAX_VALUE - 1) {
                    rowSum++;
                }
            }
            if (rowSum > colSum) {
                zeroMatrix[x][y] = -1;
            } else {
                zeroMatrix[x][y] = 1;
            }
            // Time complexity: O(n^2)
            markingLines += mark(zeroMatrix, markingMatrix, x, y);
        }

        return markingLines;
    }

    /**
     * Time complexity: O(n)
     */
    public static int mark(Integer[][] zeroMatrix, Integer[][] markingMatrix, int row, int col) {
        int lines = 0;
        if (zeroMatrix[row][col] == Integer.MAX_VALUE) {
            return lines;
        }
        if (zeroMatrix[row][col] > 0) {
            lines++;
            for (int i = 0; i < zeroMatrix.length; i++) {
                zeroMatrix[i][col] = Integer.MAX_VALUE;
                markingMatrix[i][col] = 1;
            }
        } else {
            lines++;
            for (int i = 0; i < zeroMatrix.length; i++) {
                zeroMatrix[row][i] = Integer.MAX_VALUE;
                markingMatrix[row][i] = 1;
            }
        }
        return lines;
    }

    /**
     * Step 4:
     * From the elements that are left, find the lowest value.
     * Subtract this from every unmarked element and add it to every element covered by two lines.
     * This is equivalent to subtracting a number from all rows which are not crossed and adding the same number
     * to all columns which are crossed. These operations do not change optimal assignments.
     * Time complexity: O(n^2)
     */
    public static void step4(Integer[][] matrix, Integer[][] markingMatrix) {
        int n = matrix.length;
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < minimum && matrix[i][j] > 0 && markingMatrix[i][j] == 0) {
                    minimum = matrix[i][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int j = 0;
            boolean covered = true;
            while (covered && j < n) {
                if (markingMatrix[i][j] != 1) {
                    covered = false;
                }
                j++;
            }
            if (!covered) {
                for (int c = 0; c < n; c++) {
                    matrix[i][c] -= minimum;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            int i = 0;
            boolean covered = true;
            while (covered && i < n) {
                if (markingMatrix[i][j] != 1) {
                    covered = false;
                }
                i++;
            }
            if (covered) {
                for (int r = 0; r < n; r++) {
                    matrix[r][j] += minimum;
                }
            }
        }
    }

    /**
     * Time complexity: O(n^2)
     */
    public static Integer[][] findCostMinimizingAssignment(Integer[][] matrix) {
        List<Integer[]> zeros = new ArrayList<>();
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeros.add(new Integer[]{i, j, 0});
                }
            }
        }

        for (Integer[] zero : zeros) {
            for (Integer[] integers : matrix) {
                if (integers[zero[1]] == 0) {
                    zero[2]++;
                }
            }
            for (int j = 0; j < n; j++) {
                if (matrix[zero[0]][j] == 0) {
                    zero[2]++;
                }
            }
            zero[2] -= 2;
        }

        zeros.sort(Comparator.comparing(o -> o[2]));

        List<Integer[]> result = new ArrayList<>();
        while (zeros.size() > 0) {
            Integer[] zero = zeros.get(0);
            if (zero[2] != 0) {
                for (int i = 0; i < zeros.size(); i++) {
                    Integer[] tempZero = zeros.get(i);
                    if ((tempZero[0].compareTo(zero[0]) == 0 || tempZero[1].compareTo(zero[1]) == 0)
                            && !(tempZero[0].compareTo(zero[0]) == 0 && tempZero[1].compareTo(zero[1]) == 0)) {
                        zeros.remove(tempZero);
                        i--;
                    }
                }
            }
            result.add(zeros.remove(0));
        }

        result.sort(Comparator.comparing(o -> o[0]));

        Integer[][] solution = new Integer[n][2];
        for (int i = 0; i < n; i++) {
            solution[i] = new Integer[]{i, result.get(i)[1]};
        }

        return solution;
    }

    public static void main(String[] args) {
        System.out.println("Test Case 1:");
        Integer[][] testCase1 = {
                {108, 125, 149},
                {150, 135, 175},
                {122, 148, 250}};
        String[] testCase1RowTitle = {"Company A", "Company B", "Company C"};
        String[] testCase1ColumnTitle = {"Music service", "Cleaning service", "Driving service"};
        print2dArray(testCase1, testCase1RowTitle, testCase1ColumnTitle);

        String testCase1Result = hungarianAlgorithmSolution(testCase1, testCase1RowTitle, testCase1ColumnTitle);
        System.out.printf("Expected output: $%s\n", testCase1Result);

        System.out.println("Test Case 2:");
        Integer[][] testCase2 = {{22, 14, 120, 21, 4, 51},
                {19, 12, 172, 21, 28, 43},
                {161, 122, 2, 50, 128, 39},
                {19, 22, 90, 11, 28, 4},
                {1, 30, 113, 14, 28, 86},
                {60, 70, 170, 28, 68, 104}};

        String[] testCase2RowTitle = {"Driver A", "Driver B", "Driver C", "Driver D", "Driver E", "Driver F"};
        String[] testCase2ColumnTitle = {"#191", "#122", "#173", "#121", "#128", "#104"};
        print2dArray(testCase2, testCase2RowTitle, testCase2ColumnTitle);

        String testCase2Result = hungarianAlgorithmSolution(testCase2, testCase2RowTitle, testCase2ColumnTitle);
        System.out.printf("Expected output: %s\n", testCase2Result);
    }

    public static void print2dArray(Integer[][] array, String[] rowTitle, String[] columnTitle) {
        int row = 0;
        for (int col = 0; col < columnTitle.length; col++) {
            if (col == 0) {
                System.out.printf("\t%25s ", columnTitle[col]);
            } else {
                System.out.printf("%12s  ", columnTitle[col]);
            }
        }
        System.out.println();
        for (Integer[] arr : array) {
            for (int i = 0; i < arr.length; i++) {
                if (i == 0) {
                    System.out.printf("\t%10s [", rowTitle[row++]);
                }
                System.out.printf("%12d", arr[i]);
                if (i != arr.length - 1) {
                    System.out.print(", ");
                } else {
                    System.out.println("]");
                }
            }
        }
    }
}
