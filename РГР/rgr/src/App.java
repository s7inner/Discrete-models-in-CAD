import algorithms.FordFalkersonAlgorithm;
import algorithms.GraphLoader;
import gui.FFFrame;

import java.io.File;
import java.io.FileNotFoundException;

public class App
{
    static File file = new File("src/data/size_5.txt");

    public static void main( String[] args )
    {
        try {
            GraphLoader loader = new GraphLoader(file,true);
            FordFalkersonAlgorithm fordFalkersonAlgorithm =
                    new FordFalkersonAlgorithm(loader.getAdjacencyMatrix(), 0, 4);
            System.out.println("\nMaximum flow:\t" + fordFalkersonAlgorithm.maxFlow());

            //--------------------------------------------------
            FFFrame frame = new FFFrame(1200,720, file,fordFalkersonAlgorithm);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
