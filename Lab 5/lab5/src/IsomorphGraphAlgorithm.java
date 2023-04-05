import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsomorphGraphAlgorithm {

    public boolean isIsomorphic(List<List<Integer>> g1, List<List<Integer>> g2) {
        int n = g1.size();
        int m = g2.size();
        boolean isIsomorphic;
        if(n!=m)
            return false;

        ArrayList<VertexInfo> graphVertices1 = new ArrayList<>();
        ArrayList<VertexInfo> graphVertices2 = new ArrayList<>();
        int[] permutationG1ToG2 = new int[n]; // vertex mapping from g1 to g2
        //Arrays.fill(permutationG1ToG2, -1); // no mappings initially
        for (int i = 0; i < g1.size(); i++) {
            graphVertices1.add(new VertexInfo(i,g1.get(i).size()));
            graphVertices2.add(new VertexInfo(i,g2.get(i).size()));
        }
        graphVertices1.sort(VertexInfo::compareTo);
        graphVertices2.sort(VertexInfo::compareTo);

        for (int i = 0; i < graphVertices1.size(); i++)
            if(graphVertices2.get(i).degree!=graphVertices1.get(i).degree)
                return false;


        var allCombination = getAllPermutations(graphVertices1);
        for (var combo:allCombination) {

            Arrays.fill(permutationG1ToG2, -1);

            System.out.println();
            for (int i = 0; i < combo.size(); i++){
                permutationG1ToG2[combo.get(i).vertex] = graphVertices2.get(i).vertex;
            }
            List<Edge> edgeListG1 = new ArrayList<>();
            for (int i = 0; i < g1.size(); i++) {
                for (int j = 0; j < g1.get(i).size(); j++) {
                    if(g1.get(i).get(j)>i)
                        edgeListG1.add(new Edge(i,g1.get(i).get(j)));
                }
            }
            List<Edge> edgeListG2 = new ArrayList<>();
            for (Edge value : edgeListG1) {
                edgeListG2.add(new Edge(
                        permutationG1ToG2[value.v1],
                        permutationG1ToG2[value.v2]));
            }
            isIsomorphic = true;

            for (int i = 0; i < edgeListG1.size(); i++) {
                Edge edge = edgeListG2.get(i);
                if(!g2.get(edge.v1).contains(edge.v2)){
                    isIsomorphic = false;
                    break;
                }
            }
            if (isIsomorphic) {
                System.out.println("Correspondence of vertices");
                for (int i = 0; i < combo.size(); i++) {
                    System.out.println(combo.get(i).vertex +
                            " <-> " + graphVertices2.get(i).vertex);
                }
                return true;
            }

        }

        return false;
    }
    public static ArrayList<ArrayList<VertexInfo>> getAllPermutations(ArrayList<VertexInfo> edges) {
        ArrayList<ArrayList<VertexInfo>> permutations = new ArrayList<>();
        int n = edges.size();

        if (n == 0) {
            return permutations;
        }

        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        permutations.add(new ArrayList<>(edges));

        while (true) {
            int i = n - 2;
            while (i >= 0 && indices[i] >= indices[i + 1]) {
                i--;
            }

            if (i == -1) {
                break;
            }

            int j = n - 1;
            while (indices[i] >= indices[j]) {
                j--;
            }

            int tmp = indices[i];
            indices[i] = indices[j];
            indices[j] = tmp;

            for (int a = i + 1, b = n - 1; a < b; a++, b--) {
                tmp = indices[a];
                indices[a] = indices[b];
                indices[b] = tmp;
            }

            ArrayList<VertexInfo> permutation = new ArrayList<>();
            for (int k = 0; k < n; k++) {
                permutation.add(edges.get(indices[k]));
            }

            // перевірка на порушення неспадного порядку
            boolean isNonDecreasing = true;
            for (int k = 1; k < n; k++) {
                if (permutation.get(k - 1).compareTo(permutation.get(k)) > 0) {
                    isNonDecreasing = false;
                    break;
                }
            }

            // додавання перестановки до списку комбінацій, якщо порядок не порушено
            if (isNonDecreasing) {
                permutations.add(permutation);
            }
        }

        return permutations;
    }

    private class Edge implements Comparable<Edge>{
        public int v1;
        public int v2;

        public Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        @Override
        public int compareTo(Edge o) {
            if((v1 ==o.v1 && v2 == o.v2)||(v2 ==o.v1 && v2 == o.v2))
                return 0;
            else return 1;
        }
    }
    private class VertexInfo implements Comparable<VertexInfo>{
        int vertex;
        int degree;

        public VertexInfo(int vertex, int degree) {
            this.vertex = vertex;
            this.degree = degree;
        }

        public int getVertex() {
            return vertex;
        }

        public void setVertex(int vertex) {
            this.vertex = vertex;
        }

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        @Override
        public int compareTo(VertexInfo o) {
            return -Integer.compare(degree,o.degree);
        }
    }
}
