import java.util.Arrays;
import java.util.Random;

public class Quiz3 {
    // Algorithm 3.6: Minimum Multiplications
    public static int minmult(int n, int[] d, int[][] P) {
        int i, j, k, diagonal;
        int[][] M = new int[n + 1][n + 1];

        for (i = 1; i < n + 1; i++) {
            Arrays.fill(M[i], Integer.MAX_VALUE);
        }

        for (i = 1; i <= n; i++) {
            M[i][i] = 0;
        }

        for (diagonal = 1; diagonal <= n - 1; diagonal++) {
            for (i = 1; i <= n - diagonal; i++) {
                j = i + diagonal;
                for (k = i; k <= j - 1; k++) {
                    int num = M[i][k] + M[k + 1][j] + d[i - 1] * d[k] * d[j];
                    if (M[i][j] > num) {
                        M[i][j] = num;
                        P[i][j] = k;
                    }
                }
            }
        }
        return M[1][n];
    }

    // Algorithm 3.7: Print Optimal Order
    public static void order(int i, int j, int[][] P) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            int k = P[i][j];
            System.out.print("(");
            order(i, k, P);
            order(k + 1, j, P);
            System.out.print(")");
        }
    }

    public static void main(String[] args) {
        for (int n = 100; n <= 3000; n += 100) {
            int[] d = generateRandomArray(n);
            int[][] P = new int[d.length][d.length];
            long startTime = System.nanoTime();
            int result = minmult(n, d, P);
            long endTime = System.nanoTime();
            long elapsedtime = endTime - startTime;
            System.out.println("\nresult: " + result);
            System.out.printf("Algorithm 3.6: Minimum Multiplications: n = %d, elapsedtime = %d\n", n, elapsedtime);
            startTime = System.nanoTime();
            order(1, n, P);
            endTime = System.nanoTime();
            elapsedtime = endTime - startTime;
            System.out.println();
            System.out.printf("Algorithm 3.7: Print Optimal Order: n = %d, elapsedtime = %d\n", n, elapsedtime);
        }
    }

    public static int[] generateRandomArray(int n) {
        Random random = new Random();
        int[] data = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            data[i] = random.nextInt(100);
        }
        return data;
    }
}
