/**

 * Problem: Quiz 10
 */

import java.util.*;

/**
 * 2. Implement Algorithm 6.1 on your system and run it on the problem instance of Exercise 1.
 * i   pi   wi   pi/wi
 * 1  $20   2     10
 * 2  $30   5      6
 * 3  $35   7      5       W = 13
 * 4  $12   3      4
 * 5  $3    1      3
 */
public class Quiz10 {
    /**
     * Algorithm 6.1 The Breadth-First Search with Branch-and-Bound Pruning Algorithm for the 0-1 Knapsack problem
     */
    public static void knapsack2(int n, int[] p, int[] w, int W, int[] maxProfit) {
        Map<Integer, Integer> bestItems = new TreeMap<>();
        Queue<Node> Q = new LinkedList<>();
        int promisingNodesCount = 0;
        int nonPromisingNodesCount = 0;
        Node v = new Node(0, 0, 0);
        Node u = new Node(0, 0, 0);
        maxProfit[0] = 0;
        Q.add(v);
        while (!Q.isEmpty()) {
            v = Q.poll();
            u.level = v.level + 1;
            u.profit = v.profit + p[u.level];
            u.weight = v.weight + w[u.level];
            if (u.weight <= W && u.profit > maxProfit[0]) {
                maxProfit[0] = u.profit;
                if (!bestItems.containsKey(v.level)) {
                    bestItems.put(v.level, v.profit);
                }
            }
            if (bound(u, n, p, w, W) > maxProfit[0]) {
                Q.add(u.clone());
                ++promisingNodesCount;
            } else {
                ++nonPromisingNodesCount;
            }

            u.profit = v.profit;
            u.weight = v.weight;

            if (bound(u, n, p, w, W) > maxProfit[0]) {
                Q.add(u.clone());
                ++promisingNodesCount;
            } else {
                ++nonPromisingNodesCount;
            }
        }

        System.out.printf("maxProfit: %d\n", maxProfit[0]);
        List<Integer> bestIndices = new ArrayList<>();
        int lastProfit = 0;
        Integer[] bestItemProfit = bestItems.values().toArray(new Integer[0]);
        for (int i = 0; i < bestItemProfit.length - 1; i++) {
            for (int j = 1; j < p.length; j++) {
                if ((bestItemProfit[i + 1] - bestItemProfit[i]) == p[j]) {
                    bestIndices.add(j);
                    lastProfit = bestItemProfit[i + 1];
                    break;
                }
            }
        }
        if (maxProfit[0] > lastProfit) {
            for (int j = 1; j < p.length; j++) {
                if ((maxProfit[0] - lastProfit) == p[j]) {
                    bestIndices.add(j);
                    break;
                }
            }
        }
        System.out.println("best items indices: " + bestIndices.toString());
        System.out.printf("number of nodes examined: %d\n", promisingNodesCount + nonPromisingNodesCount + 1);
    }

    public static float bound(Node u, int n, int[] p, int[] w, int W) {
        int j, k;
        int totWeight;
        float result;

        if (u.weight >= W) {
            return 0;
        } else {
            result = (float) u.profit;
            j = u.level + 1;
            totWeight = u.weight;
            while (j <= n && totWeight + w[j] <= W) {
                totWeight = totWeight + w[j];
                result = result + p[j];
                ++j;
            }
            k = j;
            if (k <= n) {
                result = result + (W - totWeight) * (p[k] / w[k]);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int W = 13;
        int[] w = {0, 2, 5, 7, 3, 1};
        int[] p = {0, 20, 30, 35, 12, 3};
        int[] maxProfit = {0};
        knapsack2(n, p, w, W, maxProfit);
    }
}

class Node implements Cloneable {
    int level;
    int profit;
    int weight;

    public Node(int level, int profit, int weight) {
        this.level = level;
        this.profit = profit;
        this.weight = weight;
    }

    @Override
    public Node clone() {
        try {
            return (Node) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}