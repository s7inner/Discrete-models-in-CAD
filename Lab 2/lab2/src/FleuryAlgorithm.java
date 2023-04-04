import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class FleuryAlgorithm {
    private final int[][] graph;
    boolean hasEulerCycle = true;
    boolean hasEulerRoute = true;


    public FleuryAlgorithm(int[][] graph) {
        this.graph = graph;
    }

    public ArrayList<Edge> findEulerPath() {
        int verticesCount = graph.length;
        int[] degrees = new int[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if(graph[i][j]>0)
                    degrees[i] += 1;//graph[i][j];
            }
            System.out.println("deg( "+i+" )\t = \t"+degrees[i]);
        }
        if(Arrays.stream(degrees).filter(l->l%2==1).count()>=2){
            System.out.println("There is no Euler cycle!");
            hasEulerCycle = false;
        }

        int startVertex = 0;
        System.out.println();
        for (int i = 0; i < verticesCount; i++) {
            if (degrees[i] % 2 != 0) {
                startVertex = i;
                break;
            }
        }

        return findEulerPath(startVertex);
    }

    public ArrayList<Edge> findEulerPath(int start) {
        ArrayList<Edge> path = new ArrayList<>();
        Stack<Edge> stack = new Stack<>();

        // Додавання ребер що інцидентні початковій вершині
        for (int i = graph[start].length- 1; i >= 0 ; i--) {
            if (graph[start][i] != 0) {
                stack.push(new Edge(start,i,graph[start][i]));
                graph[start][i] = 0;
                graph[i][start] = 0;
                break;
            }
        }

        while (!stack.isEmpty()) {
            var node = stack.peek();
            boolean found = false;

            for (int i = graph[node.endVertex].length - 1; i >= 0; i--) {
                if (graph[node.endVertex][i] != 0) {
                    found = true;
                    stack.push(new Edge(node.endVertex,i,graph[node.endVertex][i]));
                    graph[node.endVertex][i] = 0;
                    graph[i][node.endVertex] = 0;
                    break;
                }
            }
            if (!found) {
                stack.pop();
                path.add(node);
            }
        }

        Collections.reverse(path);
        return path;
    }
    public int[][] getGraph() {
        return graph;
    }

}