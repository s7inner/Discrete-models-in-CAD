package algorithms;

import utils.MaxFlowAndPathData;

import java.util.*;

public class MaxFlow {
    public int[][] graph;
    public int[][] residualGraph;
    public int source;
    public int sink;
    public int[] parent;
    public boolean[] visited;
    public List<MaxFlowAndPathData> pathData = new LinkedList<>();
    public int maxFlow = 0;


    public MaxFlow(int[][] graph,int[][] residualGraph, int source, int sink) {
        this.graph = graph;
        this.residualGraph = residualGraph;
        this.parent = new int[residualGraph.length];
        this.visited = new boolean[residualGraph.length];
        this.source = source;
        this.sink = sink;
    }

    public int findMaxFlow() {
        while (bfs()) {
            int pathFlow = Integer.MAX_VALUE;
            int current = sink;
            while (current != source) {
                int previous = parent[current];
                pathFlow = Math.min(pathFlow, residualGraph[previous][current]);
                current = previous;
            }
            current = sink;
            String pathString = "";
            LinkedList linkedListPath = new LinkedList<>();

            while (current != source) {
                int previous = parent[current];

                //add path vertexs to list
                linkedListPath.add(current);
                pathString += (current + "->" + previous + "\t");

                residualGraph[previous][current] -= pathFlow;
                residualGraph[current][previous] += pathFlow;
                current = previous;
            }
            linkedListPath.add(0);

            if (pathData.size() == 0) {
                pathData.add(new MaxFlowAndPathData(pathFlow, linkedListPath, graph.length));
            } else {
                pathData.add(new MaxFlowAndPathData(pathFlow, linkedListPath, pathData.get(pathData.size()-1).currentMinFlow.clone()));
            }

            System.out.print("Maximum path flow:\t" + pathFlow);
//            System.out.println("\t||\t"+incrementString(pathString));
            System.out.println("\t||\t" + pathString);
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    private boolean bfs() {
        Arrays.fill(visited, false);
        Queue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return residualGraph[parent[o2]][o2] - residualGraph[parent[o1]][o1];
            }
        });
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == sink) {
                return true;
            }
            for (int i = 0; i < residualGraph.length; i++) {
                if (!visited[i] && residualGraph[current][i] > 0) {
                    visited[i] = true;
                    parent[i] = current;
                    queue.add(i);
                }
            }
        }
        return false;
    }
}
