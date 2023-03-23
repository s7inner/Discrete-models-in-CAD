
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphLoader {
    int [][] adjacencyMatrix;
    public GraphLoader(File file) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        int size = fileReader.nextInt();
        System.out.println("size:\t"+size);
        adjacencyMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = fileReader.nextInt();
                System.out.print(adjacencyMatrix[i][j]+"\t");
            }
            System.out.println();
        }
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    public ArrayList<Edge> getListEdges(){
        ArrayList<Edge> edgeList = new ArrayList<>();

        for (int i = 0; i < this.adjacencyMatrix.length; i++){
            for (int j = i; j < adjacencyMatrix[i].length; j++){
                if (adjacencyMatrix[i][j] != 0){
                    edgeList.add(new Edge(i,j,adjacencyMatrix[i][j]));
                }
            }
        }

        return edgeList;
    }
}
