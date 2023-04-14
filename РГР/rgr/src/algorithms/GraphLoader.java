package algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class GraphLoader {
    public int size;

    public int source;
    public int sink;
    public File file;
    public boolean printGraphToConsole;
//    private int [][] adjacencyMatrix;
    public GraphLoader(File file,boolean printGraphToConsole){
        this.file = file;
        this.printGraphToConsole = printGraphToConsole;
    }
    public int[][] getAdjacencyMatrix() throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        size = fileReader.nextInt();

        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = fileReader.nextInt();

            }
        }
        source = fileReader.nextInt();
        sink = fileReader.nextInt();

        return matrix;
    }

    public Map<Integer, SortedSet<Integer>> getAdjacencyList() throws FileNotFoundException {
        return convertToMap(getAdjacencyMatrix());
    }
    public static Map<Integer, SortedSet<Integer>> convertToMap(int[][] arr) {
        Map<Integer, SortedSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            SortedSet<Integer> set = new TreeSet<>();
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    set.add(j);
                }
            }
            map.put(i, set);
        }
        return map;
    }
}
