/**
 * Assignment: Project 3, Problem 3
 */

import java.util.*;

/**
 * Two graphs 𝐺1 and 𝐺2 (directed or undirected) are isomorphic if some orderings of their vertices produce equal
 * adjacency matrices. See Test Case 1 in Project3_TestCases Download Project3_TestCases for an example of two graphs
 * that are isomorphic.
 * <p>
 * a. Design and implement a backtracking algorithm that checks to see if two simple graphs are isomorphic. The
 * algorithm must print “not isomorphic”, if the two graphs are not isomorphic. The algorithm must print “isomorphic”
 * and the orderings of the vertices for which their adjacency matrices are equal, if the graphs are isomorphic. Test
 * your implemented algorithm with the three test cases in Project3_TestCases.Download Project3_TestCases.
 * <p>
 * b. Show the time complexity of the algorithm.
 * <p>
 * The main algorithm code is modified from the github repository:
 * https://github.com/lanoueben/CSC340Isomorphism/blob/96523d5c02a4cda59f2c5c13059e0e09db926766
 * /CSCE340Project3BenLanoue/GraphIso/src/graphiso/
 * <p>
 * Reference:
 * Class GraphIsomorphism:
 * https://github.com/lanoueben/CSC340Isomorphism/blob/96523d5c02a4cda59f2c5c13059e0e09db926766
 * /CSCE340Project3BenLanoue/GraphIso/src/graphiso/GraphIsomorphism.java
 * Class Graph:
 * https://github.com/lanoueben/CSC340Isomorphism/blob/96523d5c02a4cda59f2c5c13059e0e09db926766
 * /CSCE340Project3BenLanoue/GraphIso/src/graphiso/Graph.java
 */
public class Problem3 {

    public static void main(String[] args) {
        // Test Case 1
        System.out.println("Test Case 1:");
        List<String[]> edges1 = new ArrayList<>();
        edges1.add(new String[]{"1", "2"});
        edges1.add(new String[]{"1", "4"});
        edges1.add(new String[]{"2", "3"});
        edges1.add(new String[]{"2", "4"});
        edges1.add(new String[]{"2", "5"});
        edges1.add(new String[]{"3", "4"});
        edges1.add(new String[]{"3", "5"});
        Graph graph1 = new Graph(false, 5, 7, edges1);
        System.out.println("Graph 1 \n" + graph1);

        List<String[]> edges2 = new ArrayList<>();
        edges2.add(new String[]{"1", "2"});
        edges2.add(new String[]{"1", "4"});
        edges2.add(new String[]{"1", "5"});
        edges2.add(new String[]{"2", "4"});
        edges2.add(new String[]{"3", "4"});
        edges2.add(new String[]{"3", "5"});
        edges2.add(new String[]{"4", "5"});
        Graph graph2 = new Graph(false, 5, 7, edges2);
        System.out.println("Graph 2 \n" + graph2);

        LinkedList<Integer> p1 = new LinkedList<>();
        boolean success = GraphIsomorphism.extend(p1, graph1.adj, graph2.adj);
        printResult(success, graph1, graph2, p1);

        // Test Case 2
        System.out.println("Test Case 2:");
        edges1.clear();
        edges1.add(new String[]{"1", "2"}); // 1
        edges1.add(new String[]{"1", "3"}); // 2
        edges1.add(new String[]{"1", "5"}); // 3
        edges1.add(new String[]{"2", "3"}); // 4
        edges1.add(new String[]{"2", "6"}); // 5
        edges1.add(new String[]{"3", "4"}); // 6
        edges1.add(new String[]{"4", "5"}); // 7
        edges1.add(new String[]{"4", "6"}); // 8
        edges1.add(new String[]{"5", "6"});  // 9
        graph1 = new Graph(false, 6, 9, edges1);
        System.out.println("Graph 1 \n" + graph1);

        edges2.clear();
        edges2.add(new String[]{"1", "2"}); // 1
        edges2.add(new String[]{"1", "4"}); // 2
        edges2.add(new String[]{"1", "5"}); // 3
        edges2.add(new String[]{"2", "3"}); // 4
        edges2.add(new String[]{"2", "6"}); // 5
        edges2.add(new String[]{"3", "4"}); // 6
        edges2.add(new String[]{"3", "5"}); // 7
        edges2.add(new String[]{"4", "6"}); // 8
        edges2.add(new String[]{"5", "6"}); // 9
        graph2 = new Graph(false, 6, 9, edges2);
        System.out.println("Graph 2 \n" + graph2);

        LinkedList<Integer> p2 = new LinkedList<>();
        success = GraphIsomorphism.extend(p2, graph1.adj, graph2.adj);
        printResult(success, graph1, graph2, p2);

        // Test Case 3
        System.out.println("Test Case 3:");
        edges1.clear();
        edges1.add(new String[]{"1", "2"}); // 1
        edges1.add(new String[]{"1", "4"}); // 2
        edges1.add(new String[]{"1", "5"}); // 3
        edges1.add(new String[]{"2", "3"}); // 4
        edges1.add(new String[]{"2", "5"}); // 5
        edges1.add(new String[]{"3", "4"}); // 6
        edges1.add(new String[]{"3", "6"}); // 7
        edges1.add(new String[]{"4", "7"}); // 8
        edges1.add(new String[]{"5", "6"}); // 9
        edges1.add(new String[]{"5", "7"}); // 10
        edges1.add(new String[]{"6", "7"}); // 11
        graph1 = new Graph(false, 7, 11, edges1);
        System.out.println("Graph 1 \n" + graph1);

        edges2.clear();
        edges2.add(new String[]{"1", "2"}); // 1
        edges2.add(new String[]{"1", "4"}); // 2
        edges2.add(new String[]{"1", "6"}); // 3
        edges2.add(new String[]{"2", "3"}); // 4
        edges2.add(new String[]{"2", "5"}); // 5
        edges2.add(new String[]{"3", "4"}); // 6
        edges2.add(new String[]{"3", "6"}); // 7
        edges2.add(new String[]{"4", "7"}); // 8
        edges2.add(new String[]{"5", "6"}); // 9
        edges2.add(new String[]{"5", "7"}); // 10
        edges2.add(new String[]{"6", "7"}); // 11
        graph2 = new Graph(false, 7, 11, edges2);
        System.out.println("Graph 2 \n" + graph2);

        LinkedList<Integer> p3 = new LinkedList<>();
        success = GraphIsomorphism.extend(p3, graph1.adj, graph2.adj);
        printResult(success, graph1, graph2, p3);
    }

    public static void printResult(boolean result, Graph graph1, Graph graph2, LinkedList<Integer> p) {
        if (!result) {
            System.out.println("not isomorphic\n");
        } else {
            // Found an isomorphism
            System.out.println("isomorphic");
            System.out.println("The orderings of the vertices for which their adjacency matrices are equal:");
            for (int v = 0; v < p.size(); v++) {
                System.out.printf("\t%s -> %s\n", graph1.vertexName(v), graph2.vertexName(p.get(v)));
            }
            System.out.println();
        }
    }
}

class GraphIsomorphism {
    static boolean extend(LinkedList<Integer> p, boolean[][] g, boolean[][] h) {
        if (p.size() == g.length) {
            return true;
        }
        for (int i = 0; i < g.length; i++) {
            if (compatible(p, i, g, h)) {
                p.add(i);
                boolean success = extend(p, g, h);
                if (success) {
                    return true;
                }
                p.removeLast();
            }
        }
        return false;
    }

    static boolean compatible(LinkedList<Integer> p, int v, boolean[][] g, boolean[][] h) {
        if (p.contains(v)) {
            return false;
        }
        for (int i = 0; i < p.size(); i++) {
            if (g[p.size()][i] != h[v][p.get(i)]) {
                return false;
            }
            if (g[i][p.size()] != h[p.get(i)][v]) {
                return false;
            }
        }
        return true;
    }
}

class Graph {
    boolean directed = true;
    int nVertices;
    int nEdges;
    boolean[][] adj;
    Map<String, Integer> translationMap;
    Map<String, List<String>> outputMap;
    String[] vertexToString;

    String vertexName(int v) {
        return vertexToString[v];
    }

    public Graph(boolean directed, int numVertices, int numEdges, List<String[]> edges) {
        this.outputMap = new TreeMap<>();
        this.directed = directed;
        nVertices = numVertices;
        nEdges = numEdges;

        adj = new boolean[nVertices][nVertices];
        vertexToString = new String[nVertices];
        translationMap = new HashMap<>();

        int vertexCounter = 0;
        for (int k = 0; k < nEdges; k++) {
            String[] edge = edges.get(k);
            String uName = edge[0];
            String vName = edge[1];
            Integer u, v;
            u = translationMap.get(uName);
            if (u == null) {
                u = vertexCounter++;
                vertexToString[u] = uName;
                translationMap.put(uName, u);
            }
            v = translationMap.get(vName);
            if (v == null) {
                v = vertexCounter++;
                vertexToString[v] = vName;
                translationMap.put(vName, v);
            }
            adj[u][v] = true;
            if (!directed) {
                adj[v][u] = true;
            }
        }
        // Initialize output map
        for (int u = 0; u < adj.length; u++) {
            for (int v = 0; v < adj.length; v++) {
                if (adj[u][v]) {
                    if (!outputMap.containsKey(vertexName(u))) {
                        outputMap.put(vertexName(u), new LinkedList<>());
                    }
                    outputMap.get(vertexName(u)).add(vertexName(v));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String vert : outputMap.keySet()) {
            builder.append(String.format("\t%s : %s\n", vert, outputMap.get(vert)));
        }
        return builder.toString();
    }
}
