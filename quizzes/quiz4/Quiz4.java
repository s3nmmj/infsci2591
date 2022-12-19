/**

 */

import java.util.*;

public class Quiz4 {
    class Node {
        int vertex;
        int weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{" +
                    "vertex=" + vertex +
                    ", weight=" + weight +
                    '}';
        }
    }

    class Graph {
        int numVertices;
        ArrayList<ArrayList<Node>> adjLists;

        public Graph(int numVertices) {
            this.numVertices = numVertices;
            // the index of graph start with 1, the 0 index will not be used.
            this.adjLists = new ArrayList<>(numVertices + 1);
            for (int i = 0; i <= numVertices; i++) {
                this.adjLists.add(new ArrayList<>());
            }
        }

        public void addLink(int v1, int v2) {
            int weight = new Random().nextInt(1, 101);
            this.adjLists.get(v1).add(new Node(v2, weight));
            this.adjLists.get(v2).add(new Node(v1, weight));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.adjLists.size(); i++) {
                sb.append(i);
                for (Node node : this.adjLists.get(i)) {
                    sb.append(" -> ").append(node.toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        public int[][] toAdjacencyMatrices() {
            int[][] adjacencyMatrices = new int[this.numVertices + 1][this.numVertices + 1];
            for (int[] row : adjacencyMatrices) {
                Arrays.fill(row, 0);
            }
            for (int i = 1; i < this.adjLists.size(); i++) {
                for (Node node : this.adjLists.get(i)) {
                    adjacencyMatrices[i][node.vertex] = node.weight;
                }
            }
            return adjacencyMatrices;
        }
    }

    class GraphProperties {
        int[] smallestWeightLink;
        int vertexWithLargestLinks;
        int vertexSmallestSumWeight;
        int vertexLargestSumWeight;

        public GraphProperties(int[] smallestWeightLink, int vertexWithLargestLinks, int vertexSmallestSumWeight, int vertexLargestSumWeight) {
            this.smallestWeightLink = smallestWeightLink;
            this.vertexWithLargestLinks = vertexWithLargestLinks;
            this.vertexSmallestSumWeight = vertexSmallestSumWeight;
            this.vertexLargestSumWeight = vertexLargestSumWeight;
        }

        @Override
        public String toString() {
            return "Graph Properties:" +
                    "\nThe link with the smallest weight: Nodes " + Arrays.toString(smallestWeightLink) +
                    "\nThe node number with the largest number of links connected to it: " + vertexWithLargestLinks +
                    "\nThe node with the smallest sum of the link weights: " + vertexSmallestSumWeight +
                    "\nThe node with the largest sum of the link weights: " + vertexLargestSumWeight;
        }
    }

    /**
     * Generate creates an undirected weighted graph of n nodes with m links.
     * Precondition: n > 0 and (n-1) <= m <= n(n-1)/2
     */
    public Graph Generate(int n, int m) {
        if (n <= 0 || m < (n - 1) || m > n * (n - 1) / 2) {
            return null;
        }
        Graph graph = new Graph(n);
        Set<String> seen = new HashSet<>();
        // next is to control the random pair to make sure no lonely node.
        int next = n;
        while (m > 0) {
            int[] pair = generatePair(n, next);
            if (seen.contains(String.format("%s,%s", pair[0], pair[1]))
                    || seen.contains(String.format("%s,%s", pair[1], pair[0]))) {
                continue;
            }
            graph.addLink(pair[0], pair[1]);
            seen.add(String.format("%s,%s", pair[0], pair[1]));
            m--;
            next--;
        }
        return graph;
    }

    public static int[] generatePair(int n, int next) {
        Random random = new Random();
        int[] pair = new int[2];
        // make sure each node be visited at least once.
        if (next <= 0) {
            pair[0] = random.nextInt(1, n + 1);
        } else {
            pair[0] = next;
        }

        pair[1] = random.nextInt(1, n + 1);
        while (pair[1] == pair[0]) {
            pair[1] = random.nextInt(1, n + 1);
        }
        return pair;
    }

    /**
     * Properties returns four properties with the given graph.
     * Precondition: graph is not null.
     */
    public GraphProperties Properties(Graph graph) {
        if (graph == null) {
            return null;
        }
        int[] smallestWeightLink = new int[2];
        int smallestWeight = Integer.MAX_VALUE;
        int vertexWithLargestLinks = 0;
        int largestLinks = 0;
        int vertexSmallestSumWeight = 0;
        int smallestSumWeight = Integer.MAX_VALUE;
        int vertexLargestSumWeight = 0;
        int largestSumWeight = 0;

        for (int i = 1; i <= graph.numVertices; i++) {
            ArrayList<Node> nodes = graph.adjLists.get(i);
            largestLinks = Math.max(largestLinks, nodes.size());
            if (largestLinks == nodes.size()) {
                vertexWithLargestLinks = i;
            }
            int sumWeight = 0;
            for (int j = 0; j < nodes.size(); j++) {
                Node node = nodes.get(j);
                int weight = node.weight;
                sumWeight += weight;
                smallestWeight = Math.min(smallestWeight, weight);
                if (smallestWeight == weight) {
                    smallestWeightLink[0] = i;
                    smallestWeightLink[1] = node.vertex;
                }
            }
//            System.out.printf("Node:%d, sumWeight:%d\n", i, sumWeight);
            smallestSumWeight = Math.min(smallestSumWeight, sumWeight);
            if (smallestSumWeight == sumWeight) {
                vertexSmallestSumWeight = i;
            }
            largestSumWeight = Math.max(largestSumWeight, sumWeight);
            if (largestSumWeight == sumWeight) {
                vertexLargestSumWeight = i;
            }
        }


        return new GraphProperties(smallestWeightLink, vertexWithLargestLinks, vertexSmallestSumWeight, vertexLargestSumWeight);
    }

    public static void main(String[] args) {
        Quiz4 quiz4 = new Quiz4();
        System.out.println("Test Case:");
        // Test case 1: n = 10
        int n = 10;
        int m = randomM(n);
        System.out.printf("\nTest Case 1: n = %d, m = %d\n", n, m);
        System.out.println("Test Case 1 Output:");
        Graph graph = quiz4.Generate(n, m);
//        System.out.println(graph);
        printGraphAdjacencyMatrices(graph);
        GraphProperties graphProperties = quiz4.Properties(graph);
        System.out.println(graphProperties.toString());
        // Test case 2: n = 20
        n = 20;
        m = randomM(n);
        System.out.printf("\nTest Case 2: n = %d, m = %d\n", n, m);
        System.out.println("Test Case 2 Output:");
        graph = quiz4.Generate(n, m);
//        System.out.println(graph);
        printGraphAdjacencyMatrices(graph);
        graphProperties = quiz4.Properties(graph);
        System.out.println(graphProperties.toString());

    }

    public static int randomM(int n) {
        return new Random().nextInt(n - 1, n * (n - 1) / 2 + 1);
    }

    public static void printGraphAdjacencyMatrices(Graph graph) {
        System.out.println("Graph Adjacency Matrices:");
        int[][] adjacencyMatrices = graph.toAdjacencyMatrices();
        for (int i = 1; i <= graph.numVertices; i++) {
            if (i == 1) {
                System.out.printf("%8d", i);
            } else {
                System.out.printf("%4d", i);
            }
        }
        System.out.println();
        for (int i = 1; i < adjacencyMatrices.length; i++) {
            System.out.printf("%2d [", i);
            for (int j = 1; j < adjacencyMatrices[i].length; j++) {
                System.out.printf("%4d", adjacencyMatrices[i][j]);
            }
            System.out.println("]");
        }
    }


}
