public class Edge implements Comparable<Edge> {
    public int u;
    public int v;
    public int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int compareTo(Edge other) {
        return Integer.compare(weight, other.weight);
    }
}
