import java.io.File;
import java.io.FileNotFoundException;

public class App
{

    static File file = new File("src/L4_2.txt");

    public static void main( String[] args )
    {
        try {
            GraphLoader loader = new GraphLoader(file,true);
            FordFalkersonAlgorithm fordFalkersonAlgorithm =
                    new FordFalkersonAlgorithm(loader.getAdjacencyMatrix());
            System.out.println("\nMaximum flow:\t" + fordFalkersonAlgorithm.maxFlow(0,3));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
