package algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class GraphLoader {
    public int size;
    private int [][] adjacencyMatrix;
    public GraphLoader(File file,boolean printGraphToConsole) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        size = fileReader.nextInt();
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
    public Map<Integer, SortedSet<Integer>> getAdjacencyList() {
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
