import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main( String[] args )
    {
        File file = new File("src/L2_1.txt");

        try {
            int[][] graph = new GraphLoader(file ,true).getAdjacencyMatrix();
            FleuryAlgorithm fa = new FleuryAlgorithm(graph);
            var path_list = fa.findEulerPath();

            System.out.println("Sequence of edges:");
            path_list.stream().forEach(i-> System.out.print(i.beginVertex +"->"+i.endVertex +"\n"));
            System.out.println();
            var graph_copy =  new GraphLoader(file,false).getAdjacencyMatrix();
            PathChecker checker = new PathChecker(graph_copy);

            path_list = checker.checkPath(path_list);
            System.out.println("\nEuler's route:");
            path_list.stream().forEach(i-> System.out.print(i.beginVertex +"->"+i.endVertex +"\n"));//"+i.weight+"

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}