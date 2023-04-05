import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphLoader {
    private int [][] adjacencyMatrix;
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
    }
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

}
