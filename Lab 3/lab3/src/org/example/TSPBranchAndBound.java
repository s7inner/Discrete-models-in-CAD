package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TSPBranchAndBound {
    private int[][] adjacencyMatrix;
    private int numberOfNodes;
    private boolean[] visited;
    private int minimumCost;
    private List<Integer> bestPath;

    public TSPBranchAndBound(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfNodes = adjacencyMatrix.length;
        this.visited = new boolean[numberOfNodes];
        this.minimumCost = Integer.MAX_VALUE;
        this.bestPath = new ArrayList<>();
    }

    public void solve(int beginVertex) {
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(beginVertex);
        visited[beginVertex] = true;

        int currentCost = 0;
        int level = 0;

        BranchAndBoundNode root = new BranchAndBoundNode(currentPath, currentCost, level);
        PriorityQueue<BranchAndBoundNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(root);

        while (!priorityQueue.isEmpty()) {
            BranchAndBoundNode currentNode = priorityQueue.poll();

            if (currentNode.getCurrentPath().size() == numberOfNodes && adjacencyMatrix[currentNode.getCurrentPath().get(numberOfNodes - 1)][beginVertex] != 0) {
                int pathCost = currentNode.getCurrentCost() + adjacencyMatrix[currentNode.getCurrentPath().get(numberOfNodes - 1)][beginVertex];

                if (pathCost < minimumCost) {
                    minimumCost = pathCost;
                    bestPath = new ArrayList<>(currentNode.getCurrentPath());
                    bestPath.add(beginVertex);
                }

                continue;
            }

            for (int i = 0; i < numberOfNodes; i++) {
                if (!currentNode.getCurrentPath().contains(i) && adjacencyMatrix[currentNode.getCurrentPath().get(currentNode.getLevel())][i] != 0) {
                    List<Integer> newPath = new ArrayList<>(currentNode.getCurrentPath());
                    newPath.add(i);

                    int newCost = currentNode.getCurrentCost() + adjacencyMatrix[currentNode.getCurrentPath().get(currentNode.getLevel())][i];

                    BranchAndBoundNode node = new BranchAndBoundNode(newPath, newCost, currentNode.getLevel() + 1);

                    if (node.getLowerBound() < minimumCost) {
                        priorityQueue.add(node);
                    }
                }
            }
        }
    }

    public int getMinimumCost() {
        return minimumCost;
    }

    public List<Integer> getBestPath() {
        return bestPath;
    }

    public class BranchAndBoundNode implements Comparable<BranchAndBoundNode> {
        private List<Integer> currentPath;
        private int currentCost;
        private int level;

        private BranchAndBoundNode(List<Integer> currentPath, int currentCost, int level) {
            this.currentPath = currentPath;
            this.currentCost = currentCost;
            this.level = level;
        }

        public List<Integer> getCurrentPath() {
            return currentPath;
        }

        public int getCurrentCost() {
            return currentCost;
        }

        public int getLevel() {
            return level;
        }

        public int getLowerBound() {
            int lowerBound = currentCost;

            for (int i = 0; i < numberOfNodes; i++) {
                if (!visited[i]) {
                    int min = Integer.MAX_VALUE;

                    for (int j = 0; j < numberOfNodes; j++) {
                        if (i != j && adjacencyMatrix[i][j] != 0 && adjacencyMatrix[i][j] < min) {
                            min = adjacencyMatrix[i][j];
                        }
                    }

                    lowerBound += min;
                }
            }

            return lowerBound;
        }

        @Override
        public int compareTo(BranchAndBoundNode node) {
            return Integer.compare(this.getLowerBound(), node.getLowerBound());
        }
    }
}
