package algorithms;

import java.util.*;

public class MaxFlowAlgorithm {

    // Returns true if there is a path from source to sink in the residual graph.
    public static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int n = residualGraph.length;
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }

    // Returns the maximum flow from source to sink in the given graph.
    public static int maxFlow(int[][] graph, int source, int sink) {
        int n = graph.length;

        // Initialize the residual graph as the original graph.
        int[][] residualGraph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        // Initialize the parent array for the BFS traversal.
        int[] parent = new int[n];

        int maxFlow = 0;

        // While there is a path from source to sink in the residual graph.
        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            // Find the maximum flow through the path found by BFS.
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            String pathString = "";

            // Update the residual capacities and reverse edges along the path.
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathString += (v + "->" + u + "\t");

                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            System.out.print("Maximum path flow:\t" + pathFlow);
//            System.out.println("\t||\t"+incrementString(pathString));
            System.out.println("\t||\t" + pathString);

            // Add path flow to overall flow.
            maxFlow += pathFlow;
        }

        // Return the maximum flow.
        return maxFlow;
    }
}
