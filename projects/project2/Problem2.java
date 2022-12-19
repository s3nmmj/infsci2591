/**
 * Assignment: Project 2, Problem 2
 */

import java.util.Arrays;

public class Problem2 {

    public static Integer[] buildMirrorBinaryTree(Integer[] tree) {
        Integer[] treeCopy = new Integer[tree.length];
        System.arraycopy(tree, 0, treeCopy, 0, tree.length);
        int index = 0;
        int level = 0;
        while (index < treeCopy.length) {
            if (index > 0) {
                int i = index;
                int j = index + (int) Math.pow(2, level) - 1;
                while (i < j) {
                    Integer temp = treeCopy[i];
                    treeCopy[i] = treeCopy[j];
                    treeCopy[j] = temp;
                    i++;
                    j--;
                }
            }

            index += (int) (Math.pow(2, level));
            level++;
        }

        return treeCopy;
    }

    public static String checkMirrorBinaryTree(Integer[] tree1, Integer[] tree2) {
        if (tree1 == null || tree2 == null || tree1.length != tree2.length || tree1[0] != tree2[0]) {
            return "Not mirror image";
        }
        int index = 0;
        int level = 0;
        while (index < tree1.length) {
            if (index > 0) {
                int i = index;
                int j = index + (int) Math.pow(2, level) - 1;
                while (i < j) {
                    if (tree1[i] != tree2[j]) {
                        return "Not Mirror Image";
                    }
                    i++;
                    j--;
                }
            }

            index += (int) (Math.pow(2, level));
            level++;
        }

        return "Mirror Image";
    }

    public static void main(String[] args) {
        System.out.println("Problem 2, Part A:");
        Integer[] testA1 = {4, 2, 7, 1, 3, 6, 9};
        Integer[] resultA1 = buildMirrorBinaryTree(testA1);
        System.out.printf("\tTest case 1. Input: %s, Output: %s\n", Arrays.toString(testA1), Arrays.toString(resultA1));

        Integer[] testA2 = {34, 24, 96, 10, null, null, null};
        Integer[] resultA2 = buildMirrorBinaryTree(testA2);
        System.out.printf("\tTest case 2. Input: %s, Output: %s\n", Arrays.toString(testA2), Arrays.toString(resultA2));
        System.out.println();

        System.out.println("Problem 2, Part B:");
        Integer[] testB1_1 = {1, 2, 3, 4, 5, 6, 7};
        Integer[] testB1_2 = {1, 3, 2, 7, 6, 5, 4};
        String resultB1 = checkMirrorBinaryTree(testB1_1, testB1_2);
        System.out.printf("\tTest case 1. Input tree 1: %s, Input tree 2: %s Output: \"%s\"\n", Arrays.toString(testB1_1), Arrays.toString(testB1_2), resultB1);

        Integer[] testB2_1 = {1, 2, 2, null, 3, null, 3};
        Integer[] testB2_2 = {1, 2, 2, 3, null, 3, null};
        String resultB2 = checkMirrorBinaryTree(testB2_1, testB2_2);
        System.out.printf("\tTest case 2. Input tree 1: %s, Input tree 2: %s Output: \"%s\"\n", Arrays.toString(testB2_1), Arrays.toString(testB2_2), resultB2);
    }
}
