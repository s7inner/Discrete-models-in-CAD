import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphLoader {
    private int [][] adjacencyMatrix;
    private List<List<Integer>> adjacencyList;
    public GraphLoader(File file,boolean printGraphToConsole) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        int size = fileReader.nextInt();
        if(printGraphToConsole)
            System.out.println("size:\t"+size);
        adjacencyMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = fileReader.nextInt();
                if(printGraphToConsole)
                    System.out.print(adjacencyMatrix[i][j]+"\t");
            }
            if(printGraphToConsole)
                System.out.println();
        }
        adjacencyList = new ArrayList<List<Integer>>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyList.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(adjacencyMatrix[i][j] != 0){
                    adjacencyList.get(i).add(j);
                }
            }
        }
    }
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    public List<List<Integer>> getAdjacencyList(){
        return adjacencyList;
    }
}
