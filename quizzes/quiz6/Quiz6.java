/**

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 15. Modify Dijkstra’s algorithm (Algorithm 4.3) so that it computes the lengths of the
 * shortest paths. Analyze the modified algorithm and show the results using order
 * notation.
 */
public class Quiz6 {
    final static int INF = 10000;

    static class Edge {
        int[] vertex = new int[2];
        int weight;

        @Override
        public String toString() {
            return "Edge{" +
                    "vertex=" + Arrays.toString(vertex) +
                    ", weight=" + weight +
                    "}";
        }
    }

    /**
     * Computes the shortest paths for all nodes.
     * Time Complexity: O(n^3)
     */
    public static void dijkstraAllNodes(final int[][] W) {
        long startTime, endTime, elapsedTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i < W.length; i++) {
            List<Edge> F = new ArrayList<>();
            dijkstra(W.length - 1, W, F, i);
            System.out.println("Node: " + i);
            System.out.println(F.toString().replace("[", "").replace("]", ""));
        }
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.printf("The number of nodes N = %d, Elapsed Time = %d\n", W.length - 1, elapsedTime);
    }

    /**
     * Algorithm 4.3: Dijkstra’s Algorithm
     * Time Complexity: O(n) + O((n-1)*2n) = O(n^2)
     */
    public static void dijkstra(int n, final int[][] W, List<Edge> F, int startVertex) {
        int i;
        int[] touch = new int[n + 1];
        int[] length = new int[n + 1];

        for (i = 1; i <= n; i++) {
            touch[i] = startVertex;
            length[i] = W[startVertex][i];
        }

        int repeat = 0;
        while (repeat++ < n) {
            int min = INF;
            int vnear = 0;
            for (i = 1; i <= n; i++) {
                if (i != startVertex && length[i] >= 0 && length[i] < min) {
                    min = length[i];
                    vnear = i;
                }
            }
            if (vnear == 0) {
                break;
            }

            Edge e = new Edge();
            e.vertex[0] = startVertex;
            e.vertex[1] = vnear;
            e.weight = min;
            F.add(e);

            for (i = 1; i <= n; i++) {
                if (length[vnear] + W[vnear][i] < length[i]) {
                    length[i] = length[vnear] + W[vnear][i];
                    touch[i] = vnear;
                }
            }
            length[vnear] = -1;
        }
    }

    /**
     * randomly generated a directed and weighted graph for testing
     */
    private static int[][] randomGenerateDirectedWeightedGraph(int n) {
        int[][] graph = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0 || i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;
                }
            }
        }

        Random random = new Random();
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                graph[i][j] = random.nextInt(100);
                if (graph[i][j] % 5 == 0) {
                    graph[i][j] = INF;
                }
            }
        }
        // print(graph);
        return graph;
    }

    public static void main(String[] args) {
        System.out.println("Test Modified Algorithm 4.3: Dijkstra’s Algorithm correction 1:");
        int[][] graph1 = {{0, 0, 0, 0, 0},
                {0, 0, 10, INF, 30},
                {0, INF, 0, 13, INF},
                {0, 4, INF, 0, 6},
                {0, INF, INF, INF, 0}};
        dijkstraAllNodes(graph1);
        System.out.println("*".repeat(36));

        System.out.println("Test Modified Algorithm 4.3: Dijkstra’s Algorithm correction 2:");
        int[][] graph = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 32, INF, 17, INF, INF, INF, INF, INF, INF},
                {0, 32, 0, INF, INF, 45, INF, INF, INF, INF, INF},
                {0, INF, INF, 0, 18, INF, INF, 5, INF, INF, INF},
                {0, 17, INF, 18, 0, 10, INF, INF, 3, INF, INF},
                {0, INF, 45, INF, 10, 0, 28, INF, INF, 25, INF},
                {0, INF, INF, INF, INF, 28, 0, INF, INF, INF, 6},
                {0, INF, INF, 5, INF, INF, INF, 0, 59, INF, INF},
                {0, INF, INF, INF, 3, INF, INF, 59, 0, 4, INF},
                {0, INF, INF, INF, INF, 25, INF, INF, 4, 0, 12},
                {0, INF, INF, INF, INF, INF, 6, INF, INF, 12, 0}};

        dijkstraAllNodes(graph);
        System.out.println("*".repeat(36));

        System.out.println("Modified Algorithm 4.3: Dijkstra’s Algorithm Testing:");
        int[][] W;
        for (int n = 50; n <= 2000; n += 50) {
            W = randomGenerateDirectedWeightedGraph(n);
            dijkstraAllNodes(W);
        }
    }
}
