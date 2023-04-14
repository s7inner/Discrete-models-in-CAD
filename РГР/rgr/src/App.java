import algorithms.GraphLoader;
import algorithms.MaxFlow;
import gui.FFFrame;

import java.io.File;
import java.io.FileNotFoundException;

public class App
{
    static File file = new File("src/data/size_6.txt");

    public static void main( String[] args )
    {
        try {
            GraphLoader loader = new GraphLoader(file,true);
            MaxFlow maxFlow =
                    new MaxFlow(loader.getAdjacencyMatrix(),loader.getAdjacencyMatrix(), loader.source, loader.sink);
            System.out.println("\nMaximum flow:\t" + maxFlow.findMaxFlow());

            //--------------------------------------------------
            FFFrame frame = new FFFrame(1200,720, file,maxFlow,loader );

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
