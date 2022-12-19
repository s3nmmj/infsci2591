import java.util.Random;

public class Quiz5 {
    final static int INF = 10000;

    /**
     * Algorithm 3.4, Floyd’s Algorithm for Shortest Paths 2.
     * Rows and columns indexed from 1 to n (n is included)
     *
     * @param n the number of the nodes
     * @param W the graph
     * @param D contains the lengths of the shortest paths
     * @param P the P[i][j] the highest index of an intermediate vertex on the shortest path from vi to vj.
     *          If no intermediate vertex exists, it will be 0.
     *          <p>
     *          Time complexity: O(n^3)
     */
    public static void floyd2(int n, int[][] W, int[][] D, int[][] P) {
        int i, j, k;
        // O(n^2)
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= n; j++) {
                P[i][j] = 0;
                D[i][j] = W[i][j];
            }
        }

        // O(n^3)
        for (k = 1; k <= n; k++) {
            for (i = 1; i <= n; i++) {
                for (j = 1; j <= n; j++) {
                    if (D[i][k] + D[k][j] < D[i][j]) {
                        P[i][j] = k;
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
        // Overall O(n^3)
    }

    /**
     * Prints the given adjacency matrix
     */
    private static void print(int[][] array) {
        System.out.println("Adjacency matrix:");
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[0].length; ++j) {
                if (array[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(array[i][j] + "   ");
            }
            System.out.println();
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
        System.out.println("Algorithm 3.4, Floyd’s Algorithm for Shortest Paths 2.");
        int[][] graph = {{0, 0, 0, 0, 0},
                {0, 0, 10, INF, 30},
                {0, INF, 0, 13, INF},
                {0, INF, INF, 0, 6},
                {0, INF, INF, INF, 0}};
        int[][] distance = new int[5][5];
        int[][] indices = new int[5][5];
        print(graph);
        floyd2(graph.length - 1, graph, distance, indices);
        print(distance);
        print(indices);


        int[][] W, D, P;
        long startTime, endTime, elapsedTime;
        System.out.println("Algorithm 3.4: Floyd’s Algorithm for Shortest Paths 2 Testing:");
        for (int n = 50; n <= 2000; n += 50) {
            W = randomGenerateDirectedWeightedGraph(n);
            D = new int[W.length][W[0].length];
            P = new int[W.length][W[0].length];
            startTime = System.currentTimeMillis();
            floyd2(W.length - 1, W, D, P);
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            System.out.printf("The number of nodes N = %d, Elapsed Time = %d\n", n, elapsedTime);
        }


    }
}
