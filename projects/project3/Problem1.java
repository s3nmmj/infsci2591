/**
 * Assignment: Project 3, Problem 1
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 18. Use the Backtracking algorithm for the m-Coloring problem (Algorithm5.5) to find all possible colorings of the
 * graph below using the three colors red, green, and white.
 * <p>
 * v1 -- v2 -- v3
 * |      |     |
 * v4 -- v5 -- v6
 */
public class Problem1 {
    private final static Map<Integer, String> colors = new HashMap<>();

    static {
        colors.put(1, "red");
        colors.put(2, "green");
        colors.put(3, "white");
    }

    /**
     * Algorithm 5.5 The Backtracking Algorithm for the m-Coloring Problem
     */
    public static void mColoring(int[][] graph, int i, int[] vColor, final int m, int[] total) {
        if (promising(graph, i, vColor)) {
            if (i == graph.length - 1) {
                for (int j = 0; j < graph.length; j++) {
                    System.out.printf("%d -> %5s, ", j + 1, colors.get(vColor[j]));
                }
                System.out.println();
                total[0]++;
            } else {
                for (int color = 1; color <= m; color++) {
                    vColor[i + 1] = color;
                    mColoring(graph, i + 1, vColor, m, total);
                }
            }
        }
    }

    public static boolean promising(int[][] graph, int i, int[] vColor) {
        if (i == -1) {
            return true;
        }
        int j = 0;
        boolean switchFlag = true;

        while (j < i && switchFlag) {
            if (graph[i][j] == 1 && vColor[i] == vColor[j]) {
                switchFlag = false;
            }
            j++;
        }

        return switchFlag;
    }

    public static void main(String[] args) {
        int[][] graph = {
                //  0  1  2  3  4  5
                {0, 1, 0, 1, 0, 0}, // 0
                {1, 0, 1, 0, 1, 0}, // 1
                {0, 1, 0, 0, 0, 1},  // 2
                {1, 0, 0, 0, 1, 0},  // 3
                {0, 1, 0, 1, 0, 1},  // 4
                {0, 0, 1, 0, 1, 0}   // 5
        };
        final int m = 3;
        int[] vColor = new int[graph.length];
        int[] total = new int[1];
        Arrays.fill(vColor, 0);
        System.out.println("Coloring Solutions(Vertex -> Color):");
        mColoring(graph, -1, vColor, m, total);
        System.out.printf("Total number of solutions is %d.\n", total[0]);
    }
}
