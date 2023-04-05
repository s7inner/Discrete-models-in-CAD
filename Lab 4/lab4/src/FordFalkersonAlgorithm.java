import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FordFalkersonAlgorithm {

    private int[][] graph;
    private int[][] residualGraph;
    private int[] parent; // масив батьківських вузлів у знайденому шляху

    public FordFalkersonAlgorithm(int[][] graph) {
        this.graph = graph;
        this.residualGraph = new int[graph.length][graph[0].length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
    }

    // Пошук максимального потоку методом Форда-Фалкерсона
    public int maxFlow(int source, int sink) {
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
            // Зменшуємо вагу кожного ребра на знайдену мінімальну пропускну здатність
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathString+= (v+"->"+u+"\t");
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            System.out.print("Maximum path flow:\t"+pathFlow);
            System.out.println("\t||\t"+pathString);
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
}
