import files.route.dijkstra.GraphFactory;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;


public class main {
    public static void main(String[] args) {

        String input = "0004;0050;5\n" +
                "01A1=B2\n" +
                "01A1=C3\n" +
                "01B2=D4\n" +
                "02A1;B2=10\n" +
                "02A1;C3=20\n" +
                "02B2;D4=30\n" +
                "09RC=03;RP=03;060;";

        var graph = GraphFactory.createGraphFromInput(input);
        System.out.println(graph);
    }
}
