/**
 * Assignment: Project 4, Problem 3
 */


import java.util.*;

/**
 * Design and implement the following algorithms for the Traveling Salesperson Problem (test each algorithm using the
 * provided test cases).
 * <p>
 * A greedy algorithm.
 * A heuristic algorithm: always choose the nearest unvisited node.
 */
public class Problem3 {
    static class Edge {
        int src;
        int dest;
        double weight;

        Edge(int src, int dest, double weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    static class Graph {
        char[] vertices;
        int[][] matrix;

        public Graph(char[] vertices, int[][] matrix) {
            this.vertices = vertices;
            this.matrix = matrix;
        }
    }

    /**
     * A greedy algorithm for the Traveling Salesperson Problem
     * time complexity: O(n^2logn)
     */
    public void greedyTSP(Graph graph) {
        // A list of all edges in the graph
        List<TSP.Edge> edges = new ArrayList<>();

        // A list of the vertices in our route
        List<Character> route = new ArrayList<>();
        List<Integer> cost = new ArrayList<>();
        for (int i = 0; i < graph.vertices.length; i++) {
            for (int j = 0; j < graph.vertices.length; j++) {
                if (i != j) {
                    edges.add(new TSP.Edge(i, j, graph.matrix[i][j]));
                }
            }
        }

        // Sort the edges by weight
        edges.sort(new Comparator<TSP.Edge>() {
            public int compare(TSP.Edge e1, TSP.Edge e2) {
                return Double.compare(e1.weight, e2.weight);
            }
        });

        // Initialize the route with the first vertex
        route.add(graph.vertices[0]);

        // Repeatedly select the shortest edge that doesn't create a cycle with less than N edges
        // or increase the degree of any node to more than 2
        int currentVertex = 0;
        while (route.size() < graph.vertices.length) {
            for (TSP.Edge e : edges) {
                if (route.contains(graph.vertices[e.src]) && !route.contains(graph.vertices[e.dest])) {
                    route.add(graph.vertices[e.dest]);
                    cost.add(graph.matrix[currentVertex][e.dest]);
                    currentVertex = e.dest;
                    break;
                }
            }
        }
        cost.add(graph.matrix[currentVertex][0]);
        route.add(graph.vertices[0]);
        // Print the route
//        System.out.println(route);

        System.out.println(Arrays.toString(route.toArray()).replaceAll("[\\[\\]]", "").replaceAll(",", " ->"));
        int totalCost = 0;
        for (int k = 0; k < cost.size(); k++) {
            System.out.print(cost.get(k));
            totalCost += cost.get(k);
            if (k == cost.size() - 1) {
                System.out.println(" = " + totalCost);
            } else {
                System.out.print(" + ");
            }
        }
    }

    /**
     * A heuristic algorithm for the Traveling Salesperson Problem
     * time complexity: O(n^2)
     */
    public void heuristicTSP(Graph graph) {
        List<Character> route = new ArrayList<>();
        List<Integer> cost = new ArrayList<>();

        // create an array to store the visited nodes
        int numNodes = graph.vertices.length;
        boolean[] visited = new boolean[numNodes];

        int currentNode = 0;
        route.add(graph.vertices[0]);

        while (true) {
            // mark the current node as visited
            visited[currentNode] = true;

            // find the nearest unvisited node
            int nearestNode = -1;
            int nearestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < numNodes; i++) {
                if (!visited[i]) {
                    int distance = graph.matrix[currentNode][i];
                    if (distance < nearestDistance) {
                        nearestNode = i;
                        nearestDistance = distance;
                    }
                }
            }

            // if no unvisited nodes remain, break out of the loop
            if (nearestNode == -1) {
                break;
            }

            cost.add(nearestDistance);

            // set the current node to the nearest unvisited node
            currentNode = nearestNode;
            route.add(graph.vertices[currentNode]);
        }
        cost.add(graph.matrix[currentNode][0]);
        route.add(graph.vertices[0]);

        System.out.println(Arrays.toString(route.toArray()).replaceAll("[\\[\\]]", "").replaceAll(",", " ->"));
        int totalCost = 0;
        for (int k = 0; k < cost.size(); k++) {
            System.out.print(cost.get(k));
            totalCost += cost.get(k);
            if (k == cost.size() - 1) {
                System.out.println(" = " + totalCost);
            } else {
                System.out.print(" + ");
            }
        }
    }


    public static void main(String[] args) {
        int[][] testcase1 = {
                {0, 60, 100, 50, 90},
                {60, 0, 70, 40, 30},
                {100, 70, 0, 65, 55},
                {50, 40, 65, 0, 110},
                {90, 30, 55, 110, 0}};

        runTestcase(1, "greedy", testcase1);
        runTestcase(1, "heuristic", testcase1);

        int[][] testcase2 = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}};
        runTestcase(2, "greedy", testcase2);
        runTestcase(2, "heuristic", testcase2);
    }

    private static Graph buildGraph(int[][] matrix) {
        int n = matrix.length;
        char[] vertices = new char[n];
        // the vertex label will be A, B, C, D and so on.
        for (int i = 0; i < n; i++) {
            vertices[i] = (char) ('A' + i);
        }

        return new Graph(vertices, matrix);
    }

    private static void runTestcase(int index, String algorithm, int[][] matrix) {
        Problem3 problem3 = new Problem3();
        Graph graph = buildGraph(matrix);
        System.out.printf("\nTestcase %d:\n", index);
        if (algorithm.equalsIgnoreCase("greedy")) {
            System.out.println("Greedy algorithm TSP");
            problem3.greedyTSP(graph);
        } else if (algorithm.equalsIgnoreCase("heuristic")) {
            System.out.println("Heuristic algorithm TSP");
            problem3.heuristicTSP(graph);
        } else {
            System.out.println("Invalid algorithm: " + algorithm);
        }
    }
}
