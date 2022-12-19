/**
 * Assignment: Project 4, Problem 2
 */


import java.util.*;

/**
 * Design and implement two algorithms, one to find the largest independent-set in a connected graph and another to find
 * the largest clique in a connected graph.  Analyze the running times of both algorithms using Big O, and
 * experimentally measure and plot the running time performance of each algorithm for undirected graphs of different
 * sizes (from small to large, as large as executable in a given computing platform).
 */
public class Problem2 {

    /**
     * build a graph based on the vertices and edges using adjacency list
     *
     * @param vertices the number of vertices
     * @param edges    the edges
     * @return the graph with adjacency list
     */
    private Map<Integer, Set<Integer>> buildGraph(int vertices, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= vertices; i++) {
            graph.put(i, new TreeSet<>());
        }

        for (int[] edge : edges) {
            int vertex1 = edge[0];
            int vertex2 = edge[1];
            graph.get(vertex1).add(vertex2);
            graph.get(vertex2).add(vertex1);
        }

        return graph;
    }

    /**
     * findLargestIndependentSet finds the largest independent-set in a connected graph
     *
     * @param vertices the number of vertices
     * @param edges    the edges
     * @return the largest independent set
     */
    public Set<Integer> findLargestIndependentSet(int vertices, int[][] edges) {
        Map<Integer, Set<Integer>> graph = buildGraph(vertices, edges);
        return graphIndependentSets(graph);
    }


    /**
     * findLargestIndependentSet finds the largest independent-set in a connected graph
     *
     * @param graph the graph
     * @return the largest independent set
     * @Time Complexity: O(2^n)
     */
    private Set<Integer> graphIndependentSets(Map<Integer, Set<Integer>> graph) {
        if (graph.size() == 0) {
            return new TreeSet<>();
        }
        if (graph.size() == 1) {
            return new TreeSet<>(graph.keySet());
        }

        int vertex = graph.keySet().iterator().next();
        Map<Integer, Set<Integer>> tempGraph = new HashMap<>(graph);
        tempGraph.remove(vertex);
        Set<Integer> set1 = graphIndependentSets(tempGraph);
        for (Integer v : graph.get(vertex)) {
            tempGraph.remove(v);
        }
        Set<Integer> set2 = new TreeSet<>();
        set2.add(vertex);
        set2.addAll(graphIndependentSets(tempGraph));
        if (set1.size() >= set2.size()) {
            return set1;
        }
        return set2;
    }

    /**
     * findLargestClique finds the largest clique
     *
     * @param vertices the number of vertices
     * @param edges    the edges
     * @return the largest clique set
     * @Time Complexity: O(n^3)
     */
    public Set<Integer> findLargestClique(int vertices, int[][] edges) {
        Map<Integer, Set<Integer>> graph = buildGraph(vertices, edges);
        if (graph.size() == 0) {
            return new TreeSet<>();
        }
        if (graph.size() == 1) {
            return new TreeSet<>(graph.keySet());
        }

        Set<Integer> largestClique = new TreeSet<>();
        for (int node : graph.keySet()) {
            Set<Integer> clique = new TreeSet<>();
            // choose the start vertex
            clique.add(node);
            for (int vertex : graph.keySet()) {
                if (clique.contains(vertex)) {
                    continue;
                }
                boolean isNext = true;
                for (int v : clique) {
                    if (graph.get(vertex).contains(v)) {
                        continue;
                    } else {
                        isNext = false;
                        break;
                    }
                }
                if (isNext) {
                    clique.add(vertex);
                }
            }
            if (clique.size() > largestClique.size()) {
                largestClique = clique;
            }
        }


        return largestClique;
    }


    public static void main(String[] args) {
        runTestCase(1, 9, new int[][]{{2, 3}, {2, 5}, {3, 6}, {4, 5}, {5, 6}, {7, 8}, {7, 9}, {8, 9}});
        runTestCase(2, 9, new int[][]{{1, 4}, {4, 7}, {4, 8}, {7, 8}, {8, 9}, {2, 3}, {2, 5}, {2, 6},
                {3, 5}, {3, 6}, {5, 6}});

        System.out.println("*".repeat(64));
        System.out.println("Performance Experiment:");
        System.out.println("*".repeat(64));
        Problem2 problem2 = new Problem2();
        long startTime, endTime, elapsedTime1, elapsedTime2;
        for (int i = 1; i <= 20; i++) {
            int vertices = 10 * i;
            int[][] edges = generateRandomEdges(vertices);
            startTime = System.currentTimeMillis();
            problem2.findLargestIndependentSet(vertices, edges);
            endTime = System.currentTimeMillis();
            elapsedTime1 = endTime - startTime;
            startTime = System.currentTimeMillis();
            problem2.findLargestClique(vertices, edges);
            endTime = System.currentTimeMillis();
            elapsedTime2 = endTime - startTime;
            System.out.printf("Vertices: %d\tEdges: %d\tTime of LargestIndependentSet: %d\tTime of " +
                                      "LargestClique: %d\n", vertices, edges.length, elapsedTime1, elapsedTime2);
        }
    }

    private static void runTestCase(int index, int vertices, int[][] edges) {
        Problem2 problem2 = new Problem2();
        System.out.printf("Test Case %d:\n", index);
        Set<Integer> largestIndependentSet = problem2.findLargestIndependentSet(vertices, edges);
        System.out.printf("Largest Independent Set: %s\n", setToString(largestIndependentSet));
        Set<Integer> largestClique = problem2.findLargestClique(vertices, edges);
        System.out.printf("Largest Clique: %s\n", setToString(largestClique));
        System.out.println();
    }

    private static String setToString(Set<Integer> set) {
        return set.toString().replaceAll("[\\[\\]]", "");
    }

    private static int[][] generateRandomEdges(int vertices) {
        List<int[]> list = new ArrayList<>();
        Set<String> exitedEdge = new HashSet<>();
        Random random = new Random();
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                if (i != j) {
                    // if the random number can be divided by 5, it will add an edge.
                    if (random.nextInt(100) % 5 == 0) {
                        if (!exitedEdge.contains(String.format("%d,%d", i, j))) {
                            list.add(new int[]{i, j});
                            exitedEdge.add(String.format("%d,%d", i, j));
                            exitedEdge.add(String.format("%d,%d", j, i));
                        }
                    }
                }
            }
        }
        int[][] edges = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            edges[i] = list.get(i);
        }
        return edges;
    }
}
