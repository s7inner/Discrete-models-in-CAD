package algorithms;

import utils.MaxFlowAndPathData;

import java.util.*;

public class FordFalkersonAlgorithm {

    public int[][] graph;
    public int[][] residualGraph;
    public int source;
    public int sink;
    private int[] parent; // масив батьківських вузлів у знайденому шляху
    public List<MaxFlowAndPathData> pathData = new LinkedList<>();
    public int maxFlow = 0;


    public FordFalkersonAlgorithm(int[][] graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.residualGraph = new int[graph.length][graph[0].length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
    }

    // Пошук максимального потоку методом Форда-Фалкерсона
    public int maxFlow() {
        int maxFlow = 0;

        // Шукаємо шляхи з покращенням
        while (bfs(source, sink)) {
            // Знаходимо мінімальну пропускну здатність шляху
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            String pathString = "";
            LinkedList linkedListPath = new LinkedList<>();

            // Зменшуємо вагу кожного ребра на знайдену мінімальну пропускну здатність
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];

                //add path vertexs to list
                linkedListPath.add(v);

                pathString += (v + "->" + u + "\t");
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            linkedListPath.add(0);

            if (pathData.size() == 0) {
                pathData.add(new MaxFlowAndPathData(pathFlow, linkedListPath, graph.length));
            } else {
                pathData.add(new MaxFlowAndPathData(pathFlow, linkedListPath, pathData.get(pathData.size()-1).currentMinFlow.clone()));
            }

//            pathData.add(new MaxFlowAndPathData(pathFlow, linkedListPath));

            System.out.print("Maximum path flow:\t" + pathFlow);
//            System.out.println("\t||\t"+incrementString(pathString));
            System.out.println("\t||\t" + pathString);

            maxFlow += pathFlow; // Додаємо до максимального потоку знайдений шлях
        }


        return maxFlow;
    }

    // Пошук шляху методом BFS
    private boolean bfs(int source, int sink) {
        int n = residualGraph.length; // кількість вершин у графі
        boolean[] visited = new boolean[n]; // масив відвіданих вершин
        Queue<Integer> queue = new LinkedList<Integer>(); // черга для BFS
        queue.add(source); // додаємо початкову вершину до черги
        visited[source] = true; // позначаємо її як відвідану
        parent = new int[n];
        Arrays.fill(parent, -1); // ініціалізуємо масив батьківських вузлів

        // Знаходимо збільшуючий шлях до стокової вершини
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u; // зберігаємо батьківський вузол
                    visited[v] = true; // позначаємо вершину як відвідану
                }
            }
        }
        // Повертаємо true, якщо стокова вершина була досягнута
        return visited[sink];
    }

    public int maxFlowItaration() {

        // Шукаємо шляхи з покращенням
        if (bfs(source, sink)) {
            // Знаходимо мінімальну пропускну здатність шляху
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            String pathString = "";
            LinkedList linkedListPath = new LinkedList<>();

            // Зменшуємо вагу кожного ребра на знайдену мінімальну пропускну здатність
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];

                //add path vertexs to list
                linkedListPath.add(v);

                pathString += (v + "->" + u + "\t");
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
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

            maxFlow += pathFlow; // Додаємо до максимального потоку знайдений шлях
        }


        return maxFlow;
    }

    public String incrementString(String input) {
        StringBuilder output = new StringBuilder();
        String[] pairs = input.split("\t");
        for (String pair : pairs) {
            String[] numbers = pair.split("->");
            int firstNumber = Integer.parseInt(numbers[0]);
            int secondNumber = Integer.parseInt(numbers[1]);
            output.append((firstNumber + 1) + "->" + (secondNumber + 1) + "\t");
        }
        return output.toString();
    }


}