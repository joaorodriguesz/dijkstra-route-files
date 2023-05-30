package files.route.dijkstra;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.Map;

public class GraphFactory {

    public static Graph<Integer, DefaultWeightedEdge> createGraphFromInput(String input) {
        String[] lines = input.split("\n");

        // Parsing HEADER
        String header = lines[0].substring(2);
        int numNodes = Integer.parseInt(header.substring(0, 2));
        int numEdges = Integer.parseInt(header.substring(2, 4));
        int numWeights = Integer.parseInt(header.substring(4));

        // Create a directed weighted graph
        DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Map to store edge weights
        Map<DefaultWeightedEdge, Double> edgeWeights = new HashMap<>();

        // Parsing RESUMO DE CONEXOES
        int lineIndex = 1;
        for (int i = 0; i < numEdges; i++) {
            String connection = lines[lineIndex].substring(2);
            int source = Integer.parseInt(connection.substring(0, 2));
            int target = Integer.parseInt(connection.substring(3));
            graph.addVertex(source);
            graph.addVertex(target);
            DefaultWeightedEdge edge = graph.addEdge(source, target);
            edgeWeights.put(edge, 0.0); // Initialize weight to 0
            lineIndex++;
        }

        // Parsing RESUMO DOS PESOS
        for (int i = 0; i < numWeights; i++) {
            String weight = lines[lineIndex].substring(2);
            int source = Integer.parseInt(weight.substring(0, 2));
            int target = Integer.parseInt(weight.substring(3, 5));
            double weightValue = Double.parseDouble(weight.substring(6));
            DefaultWeightedEdge edge = graph.getEdge(source, target);
            edgeWeights.put(edge, weightValue);
            lineIndex++;
        }

        // Associate edge weights with the edges
        for (DefaultWeightedEdge edge : edgeWeights.keySet()) {
            double weight = edgeWeights.get(edge);
            graph.setEdgeWeight(edge, weight);
        }

        return graph;
    }
}
