import java.io.File;
import java.io.FileNotFoundException;

public class App
{
    static File file1 = new File("src/L5_1.txt");
    static File file2 = new File("src/L5_2.txt");
    static File file3 = new File("src/L5_3.txt");


    public static void main( String[] args )
    {
        try {
            GraphLoader loaderGraph1 = new GraphLoader(file1,true);
            GraphLoader loaderGraph2 = new GraphLoader(file2,true);
            GraphLoader loaderGraph3 = new GraphLoader(file3,true);

            IsomorphGraphAlgorithm isomorphGraphAlgorithm = new IsomorphGraphAlgorithm();
            boolean isGraph1IsomorphicToGraph2 = isomorphGraphAlgorithm.isIsomorphic(
                    loaderGraph2.getAdjacencyList(),loaderGraph3.getAdjacencyList());
            System.out.println("\nG1 та G2 "+(isGraph1IsomorphicToGraph2?"The graphs are isomorphic":"The graphs are not isomorphic"));

            boolean isGraph1IsomorphicToGraph3 = isomorphGraphAlgorithm.isIsomorphic(
                    loaderGraph1.getAdjacencyList(),loaderGraph3.getAdjacencyList());
            System.out.println("\nG1 та G3 "+(isGraph1IsomorphicToGraph3?"The graphs are isomorphic":"The graphs are not isomorphic"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}