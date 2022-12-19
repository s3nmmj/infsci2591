/**

 * Problem: Quiz 8
 */

import java.util.Arrays;
import java.util.Random;

public class Quiz8 {
    /**
     * Algorithm 5.3
     * Monte Carlo Estimate for Algorithm 5.1 (The Backtracking Algorithm for the n-Queens Problem)
     */
    public static int estimateNQueens(int n) {
        int i = 0;
        int j;
        int[] col = new int[n + 1];
        Arrays.fill(col, 0);

        int m = 1;
        int mprod = 1;
        int numnodes = 1;

        int[] promChildren = new int[n + 1];
        Arrays.fill(promChildren, 0);

        while (m != 0 && i != n) {
            mprod = mprod * m;
            numnodes = numnodes + mprod * n;
            i++;
            m = 0;
            Arrays.fill(promChildren, 0);
            for (j = 1; j <= n; j++) {
                col[i] = j;
                if (promising(i, col)) {
                    m++;
                    promChildren[j] = 1;
                }
            }
            if (m != 0) {
                while (true) {
                    int random = new Random().nextInt(1, n + 1);
                    if (promChildren[random] == 1) {
                        j = random;
                        break;
                    }
                }
                col[i] = j;
            }
        }

        return numnodes;
    }

    /**
     * Function promising is the one in Algorithm 5.1
     * The Backtracking Algorithm for the n-Queens Problem
     */
    public static boolean promising(int i, int[] col) {
        int k = 1;
        boolean switchFlag = true;
        while (k < i && switchFlag) {
            if (col[i] == col[k] || Math.abs(col[i] - col[k]) == i - k) {
                switchFlag = false;
            }
            k++;
        }

        return switchFlag;
    }

    public static void main(String[] args) {
        int result;
        int total = 0;
        int totalTrials = 20;
        int N = 8;

        for (int i = 0; i < totalTrials; i++) {
            result = estimateNQueens(N);
            total += result;
            System.out.printf("Trial %2d, estimate of the number of nodes: %6d\n", i + 1, result);
        }
        System.out.printf("The total number of nodes of the 20 estimates is %d\n", total);
        System.out.printf("The average of the 20 estimates is %d\n", total / totalTrials);
    }
}
