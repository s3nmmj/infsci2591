/**

 * Problem: Quiz 9
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 27. Implement the Backtracking algorithm for the Hamiltonian Circuits  problem (Algorithm 5.6) on your system,
 * and run it on the problem  instance of Exercise 26.
 */
public class Quiz9 {
    int n;
    int[][] W;

    int[] vIndex;

    public Quiz9(int n, int[][] w) {
        this.n = n;
        W = w;
        this.vIndex = new int[n];
        this.vIndex[0] = 1;
    }

    /**
     * Algorithm 5.6 The Backtracking Algorithm for the Hamiltonian Circuits Problem
     */
    public void hamiltonian(int i, List<String> circuits) {
        int j;
        if (promising(i)) {
            if (i == n - 1) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < n; k++) {
                    if (k == n - 1) {
                        sb.append("V[").append(vIndex[k]).append("]\n");
                    } else {
                        sb.append("V[").append(vIndex[k]).append("] -> ");
                    }
                }
                circuits.add(sb.toString());
            } else {
                for (j = 2; j <= n; j++) {
                    vIndex[i + 1] = j;
                    hamiltonian(i + 1, circuits);
                }
            }
        }
    }

    public boolean promising(int i) {
        int j = 1;
        boolean switchFlag = true;
        if (i == n - 1 && W[vIndex[n - 1] - 1][vIndex[0] - 1] == 0) {
            switchFlag = false;
        } else if (i > 0 && W[vIndex[i - 1] - 1][vIndex[i] - 1] == 0) {
            switchFlag = false;
        } else {
            while (j < i && switchFlag) {
                if (vIndex[i] == vIndex[j]) {
                    switchFlag = false;
                }
                j++;
            }
        }

        return switchFlag;
    }

    public static void main(String[] args) {
        System.out.println("Test Case 1: a complete undirected graph of 4 nodes");
        int n = 4;
        int[][] testCase1W = {
                {0, 1, 1, 1},  // V1
                {1, 0, 1, 1},  // V2
                {1, 1, 0, 1},  // V3
                {1, 1, 1, 0}   // V4
        };
        Quiz9 quiz91 = new Quiz9(n, testCase1W);
        List<String> circuits = new ArrayList<>();
        quiz91.hamiltonian(0, circuits);
        printCircuits(circuits);

        System.out.println("\nTest Case 2: ");
        n = 12;
        int[][] testCase2W = {
                //1 2  3  4  5  6  7  8  9  10 11 12
                {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},  // V1
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0},  // V2
                {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},  // V3
                {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},  // V4
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},  // V5
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0},  // V6
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},  // V7
                {0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0},  // V8
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},  // V9
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},  // V10
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1},  // V11
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}   // V12
        };
        Quiz9 quiz92 = new Quiz9(n, testCase2W);
        circuits.clear();
        quiz92.hamiltonian(0, circuits);
        printCircuits(circuits);

    }

    public static void printCircuits(List<String> circuits) {
        if (circuits.size() == 0) {
            System.out.println("There is no Hamiltonian Cycle in the graph");
        } else {
            circuits.forEach(System.out::print);
        }
    }

}
