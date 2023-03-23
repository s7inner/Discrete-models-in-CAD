import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KruskalAlgoritm {

    private int[][] graph;
    private int numVertices;
    private int[] parent;

    public KruskalAlgoritm(int[][] graph) {
        this.graph = graph;
        this.numVertices = graph.length;
        this.parent = new int[numVertices];
        Arrays.fill(parent, -1);
    }
    public int[][] findMST() {
        int[][] mst = new int[numVertices-1][2];
        List<Edge> edges = new ArrayList<Edge>();

        // add all edges to the list
        for (int i = 0; i < numVertices; i++) {
            for (int j = i+1; j < numVertices; j++) {
                if (graph[i][j] > 0) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        // sort the edges by weight
        Collections.sort(edges);

        int count = 0;
        int index = 0;
        while (count < numVertices-1 && index < edges.size()) {
            Edge edge = edges.get(index);
            int u = edge.u;
            int v = edge.v;
            int weight = edge.weight;
            int pu = find(u);
            int pv = find(v);
            if (pu != pv) {
                mst[count][0] = u;
                mst[count][1] = v;
                count++;
                union(pu, pv);
            }
            index++;
        }

        return mst;
    }

    private int find(int u) {
        if (parent[u] == -1) {
            return u;
        }
        parent[u] = find(parent[u]);
        return parent[u];
    }

    private void union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        parent[pu] = pv;
    }
    // test the algorithm with an example graph
    public static void main(String[] args) {

        File file = new File("src/l1_2.txt");

        GraphLoader graphLoaderObj;
        try {
            graphLoaderObj = new GraphLoader(file);
            int[][] graph = graphLoaderObj.getAdjacencyMatrix();
            KruskalAlgoritm ka = new KruskalAlgoritm(graph);
            int[][] mst = ka.findMST();
            System.out.println("Minimum Spanning Tree:");
            for (int i = 0; i < mst.length; i++) {
                System.out.println(mst[i][0] + " - " + mst[i][1]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
