/**
 * Assignment: Project 3, Problem 2
 */

import java.util.Arrays;

/**
 * 33. Use the Backtracking algorithm for the 0-1 Knapsack problem (Algorithm 5.7) to maximize the profit for the
 * following problem instance.
 * <p>
 * i   pi   wi   pi/wi
 * 1  $20   2     10
 * 2  $30   5      6
 * 3  $35   7      5       W = 9
 * 4  $12   3      4
 * 5  $3    1      3
 */
public class Problem2 {
    int W;

    int n;

    int[] w;

    int[] p;

    int maxProfit = 0;

    int numBest = 0;

    String[] bestSet;

    /**
     * Problem2 initializes the program.
     *
     * @param n number of nodes
     * @param W maximum weight
     * @param p profit
     * @param w weight
     */
    public Problem2(int n, int W, int[] p, int[] w) {
        this.n = n;
        this.W = W;
        this.p = new int[n + 1];
        Arrays.fill(this.p, 0);
        System.arraycopy(p, 0, this.p, 0, this.p.length);

        this.w = new int[n + 1];
        Arrays.fill(this.w, 0);
        System.arraycopy(w, 0, this.w, 0, this.w.length);

        this.bestSet = new String[n + 1];
    }

    /**
     * Algorithm 5.7 The Backtracking Algorithm for the 0-1 Knapsack ProbIem
     */

    public void knapsack(int i, int profit, int weight, String[] include) {
        if (weight <= W && profit > maxProfit) {
            maxProfit = profit;
            numBest = i;
            bestSet = Arrays.copyOf(include, bestSet.length);
        }

        if (promising(i, weight, profit)) {
            include[i + 1] = "yes";
            knapsack(i + 1, profit + p[i + 1], weight + w[i + 1], include);
            include[i + 1] = "no";
            knapsack(i + 1, profit, weight, include);
        }
    }

    public boolean promising(int i, int weight, int profit) {
        int j, k;
        int totalWeight = 0;
        float bound;

        if (weight >= W) {
            return false;
        } else {
            j = i + 1;
            bound = profit;
            totalWeight = weight;
            while (j <= n && totalWeight + w[j] <= W) {
                totalWeight += w[j];
                bound += p[j];
                j++;
            }
            k = j;
            if (k <= n) {
                bound += (W - totalWeight) * p[k] / w[k];
            }
            return bound > maxProfit;
        }
    }

    public static void main(String[] args) {
        int[] p = {0, 20, 30, 35, 12, 3};
        int[] w = {0, 2, 5, 7, 3, 1};
        int n = p.length - 1;
        int W = 9;
        String[] include = new String[n + 1];
        Arrays.fill(include, "no");
        Problem2 problem2 = new Problem2(n, W, p, w);
        problem2.knapsack(0, 0, 0, include);
        System.out.println("Best set:");
        for (int i = 0; i <= n; i++) {
            if (problem2.bestSet[i].equals("yes")) {
                System.out.printf("\ti: %d, p: %d, w: %d\n", i, p[i], w[i]);
            }
        }
        System.out.printf("Maximum profit: %d\n", problem2.maxProfit);
    }
}
