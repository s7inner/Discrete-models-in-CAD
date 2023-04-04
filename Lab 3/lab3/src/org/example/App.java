package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class App 
{

    static File file = new File("src/org/example/L3_1.txt");
    public static void main( String[] args )
    {
        try {
            GraphLoader loader = new GraphLoader(file, true);
            TSPBranchAndBound bnb = new TSPBranchAndBound(loader.getAdjacencyMatrix());
            bnb.solve(0);
            System.out.print("Route:\t");
            bnb.getBestPath().stream().forEach(i -> System.out.print(i + "\t"));
            System.out.println("\nThe price of the route:\t" + bnb.getMinimumCost());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
