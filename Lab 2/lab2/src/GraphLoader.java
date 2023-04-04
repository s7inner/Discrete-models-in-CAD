import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GraphLoader {
    private int [][] adjacencyMatrix;
    private LinkedList<Integer>[] adjacencyList;
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
        adjacencyList = new LinkedList[size];
        for (int i=0;i< adjacencyList.length;i++) {
            adjacencyList[i] = new LinkedList<>();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(adjacencyMatrix[i][j] != 0){
                    adjacencyList[i].add(j);
                }
            }
        }
    }
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    public LinkedList<Integer>[] getAdjacencyList(){
        return adjacencyList;
    }
}