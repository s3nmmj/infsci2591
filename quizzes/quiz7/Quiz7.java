/**
 * Problem: Quiz 7
 */

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/** * Design and implement an algorithm that reads in an undirected sparse graph, stores it in an adjacency list, and
 * prints the vertex with the largest degree (the degree of a vertex in a graph is the number of edges incident on the
 * vertex). You may assume that all edges of the undirected sparse graphs have a weight of 1.
 */
public class Quiz7 {
    static class Vertex implements Comparable<Vertex> {
        private final String data;

        private final LinkedList<Edge> edges;

        public Vertex(String data) {
            this.data = data;
            this.edges = new LinkedList<>();
        }

        public String getData() {
            return data;
        }

        public LinkedList<Edge> getEdges() {
            return edges;
        }

        public void addEdge(Edge edge) {
            this.edges.add(edge);
        }

        public int getDegree() {
            return this.edges.size();
        }

        /**         * in order to reuse the vertex, add reset function to clear the edges and return the vertex
         */
        public Vertex reset() {
            this.edges.clear();
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("\tVertex " + this.data + " -> ");
            for (int i = 0; i < this.edges.size(); i++) {
                sb.append(edges.get(i).toString());
                if (i < this.edges.size() - 1) {
                    sb.append("->");
                }
            }

            return sb.toString();
        }

        @Override
        public int compareTo(Vertex o) {
            return data.compareTo(o.data);
        }
    }

    static class Edge {
        private final int weight;

        private final Vertex vertex;

        public Edge(int weight, Vertex vertex) {
            this.weight = weight;
            this.vertex = vertex;
        }

        public int getWeight() {
            return weight;
        }

        public Vertex getVertex() {
            return vertex;
        }

        @Override
        public String toString() {
            return String.format("{%s, %d}", vertex.data, weight);
        }
    }

    static class SparseGraph {
        private final Set<Vertex> vertices;

        public SparseGraph() {
            vertices = new TreeSet<>();
        }

        public void addVertex(Vertex vertex) {
            vertices.add(vertex);
        }

        public void addVertices(Set<Vertex> vertices) {
            this.vertices.addAll(vertices);
        }

        /**         * pro-condition: vertex1 and vertex2 are already in the vertices
         */
        public void addEdge(Vertex vertex1, Vertex vertex2, int weight) {
            vertex1.addEdge(new Edge(weight, vertex2));
            vertex2.addEdge(new Edge(weight, vertex1));
        }

        /**         * pro-condition: vertex1 and vertex2 are already in the vertices
         */
        public void addEdge(Vertex vertex1, Vertex vertex2) {
            addEdge(vertex1, vertex2, 1);
        }

        public void printGraph() {
            System.out.println("Adjacency List of Sparse Graph:");
            int largestDegree = 0;
            for (Vertex vertex : vertices) {
                System.out.println(vertex.toString());
                if (vertex.getDegree() > largestDegree) {
                    largestDegree = vertex.getDegree();
                }
            }
            System.out.println("Largest Degree Vertex:");
            for (Vertex vertex : vertices) {
                if (vertex.getDegree() == largestDegree) {
                    System.out.println("\tVertex: " + vertex.getData() + ", Degree: " + vertex.getDegree());
                }
            }
        }

        public void clear() {
            this.vertices.clear();
        }
    }

    public static void main(String[] args) {
        SparseGraph sparseGraph = new SparseGraph();
        Vertex va = new Vertex("A");
        Vertex vb = new Vertex("B");
        Vertex vc = new Vertex("C");
        Vertex vd = new Vertex("D");
        Vertex ve = new Vertex("E");
        Vertex vf = new Vertex("F");
        Vertex vg = new Vertex("G");
        Set<Vertex> vertices = new TreeSet<>();
        vertices.add(va);
        vertices.add(vb);
        vertices.add(vc);
        vertices.add(vd);
        vertices.add(ve);
        vertices.add(vf);
        vertices.add(vg);

        System.out.println("\nTest Case 1:");
        resetVertices(vertices);
        sparseGraph.clear();
        sparseGraph.addVertices(vertices);
        // add edges
        sparseGraph.addEdge(vc, vd);
        sparseGraph.printGraph();
        System.out.println("\nTest Case 2:");
        sparseGraph.clear();
        resetVertices(vertices);
        sparseGraph.addVertices(vertices);
        // add edges
        sparseGraph.addEdge(vc, vd);
        sparseGraph.addEdge(vc, vg);
        sparseGraph.addEdge(vd, vg);
        sparseGraph.printGraph();

        System.out.println("\nTest Case 3:");
        sparseGraph.clear();
        resetVertices(vertices);
        sparseGraph.addVertices(vertices);
        // add edges
        sparseGraph.addEdge(va, vd);
        sparseGraph.addEdge(vc, vd);
        sparseGraph.addEdge(vf, vd);
        sparseGraph.printGraph();
    }

    public static void resetVertices(Set<Vertex> vertices) {
        vertices.forEach(Vertex::reset);
    }

}
