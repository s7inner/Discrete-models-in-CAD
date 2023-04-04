import java.util.*;

public class PathChecker {
    private final int[][] graph;

    public PathChecker(int[][] graph) {
        this.graph = graph;
    }

    public ArrayList<Edge> checkPath(ArrayList<Edge> path) {
        int current_gap_index;

        while (hasGap(path)!=-1){
            current_gap_index = hasGap(path);
            ArrayList<Edge> link = shortestPath(
                    path.get(current_gap_index).endVertex,
                    path.get(current_gap_index+1).beginVertex);
            path = insertPath(path,link,current_gap_index);
        };

        return path;
    }
    public ArrayList<Edge> insertPath(ArrayList<Edge> oldEulerPath,ArrayList<Edge> link,int gapIndex){
        ArrayList<Edge> newEulerPath = new ArrayList<>();
        for (int i = 0; i <= gapIndex; i++) {
            newEulerPath.add(oldEulerPath.get(i));
        }
        newEulerPath.addAll(link);
        for (int i = gapIndex+1; i < oldEulerPath.size(); i++) {
            newEulerPath.add(oldEulerPath.get(i));
        }
        return newEulerPath;
    }
    public int hasGap(ArrayList<Edge> path){
        int gapIndex = -1;
        for(int i =0;i<path.size()-1;i++){
            if(path.get(i).endVertex !=path.get(i+1).beginVertex){
                gapIndex = i;
                break;
            }
        }
        return gapIndex;
    }
    public ArrayList<Edge> shortestPath(int startVertex, int endVertex) {
        int numVertices = graph.length;
        boolean[] visited = new boolean[numVertices];
        int[] distances = new int[numVertices];
        int[] predecessors = new int[numVertices];
        Arrays.fill(visited, false);
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(predecessors, -1);
        distances[startVertex] = 0;

        // Пошук в ширину
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            visited[currentVertex] = true;
            for (int i = 0; i < numVertices; ++i) {
                if (graph[currentVertex][i] != 0 && !visited[i]) {
                    int newDistance = distances[currentVertex] + graph[currentVertex][i];
                    if (newDistance < distances[i]) {
                        distances[i] = newDistance;
                        predecessors[i] = currentVertex;
                    }
                    queue.add(i);
                }
            }
        }

        // Формування маршруту
        ArrayList<Edge> path = new ArrayList<>();
        int currentVertex = endVertex;
        while (currentVertex != startVertex) {
            int predecessor = predecessors[currentVertex];
            if (predecessor == -1) {
                // Якщо не існує шляху між заданими вершинами, повернути пустий список
                return new ArrayList<>();
            }
            path.add(new Edge(predecessor,currentVertex, graph[predecessor][currentVertex]));
            currentVertex = predecessor;
        }
        Collections.reverse(path);
        return path;
    }
}