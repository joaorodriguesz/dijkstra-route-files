package files.route.dijkstra.view;

import java.security.InvalidAlgorithmParameterException;
import java.util.*;

public class Graph {

	private static final int UNDEFINED = -1;
	private int vertices[][];

	public Graph(final int numVertices) {
		vertices = new int[numVertices][numVertices];
	}

	public void createEdge(final int sourceNode, final int destinationNode, final int weight) throws InvalidAlgorithmParameterException {
		if (weight >= 0) {
			vertices[sourceNode][destinationNode] = weight;
			vertices[destinationNode][sourceNode] = weight;
		} else {
			throw new InvalidAlgorithmParameterException("O peso do nó de origem [" + sourceNode + "] ao nó de destino [" + destinationNode + "] não pode ser negativo [" + weight + "]");
		}
	}

	public int getCost(final int sourceNode, final int destinationNode) {
		int cost = 0;
		if (sourceNode > vertices.length) {
			throw new ArrayIndexOutOfBoundsException("O nó de origem [" + sourceNode + "] não existe no gráfico");
		} else if (destinationNode > vertices.length) {
			throw new ArrayIndexOutOfBoundsException("O nó de destino [" + destinationNode + "] não existe no gráfico");
		} else {
			cost = vertices[sourceNode][destinationNode];
		}

		return cost;
	}

	public List<Integer> getNeighbors(final int node) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int i = 0; i < vertices[node].length; i++) {
			if (vertices[node][i] > 0) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}

	public int getClosest(final int costList[], final Set<Integer> unvisitedList) {

		double minDistance = Integer.MAX_VALUE;
		int closestNode = 0;
		for (Integer i : unvisitedList) {
			if (costList[i] < minDistance) {
				minDistance = costList[i];
				closestNode = i;
			}
		}
		return closestNode;
	}

	public List<Integer> shortestPath(final int sourceNode, final int destinationNode) {

		int cost[] = new int[vertices.length];
		int predecessor[] = new int[vertices.length];
		Set<Integer> unvisitedNodes = new HashSet<Integer>();

		cost[sourceNode] = 0;

		for (int v = 0; v < vertices.length; v++) {
			if (v != sourceNode) {
				cost[v] = Integer.MAX_VALUE;
			}
			predecessor[v] = UNDEFINED;
			unvisitedNodes.add(v);
		}

		while (!unvisitedNodes.isEmpty()) {

			int closestNode = getClosest(cost, unvisitedNodes);

			unvisitedNodes.remove(closestNode);

			for (Integer neighbor : getNeighbors(closestNode)) {
				int totalCost = cost[closestNode] + getCost(closestNode, neighbor);
				if (totalCost < cost[neighbor]) {
					cost[neighbor] = totalCost;
					predecessor[neighbor] = closestNode;
				}
			}

			if (closestNode == destinationNode) {
				return getShortestPath(predecessor, closestNode);
			}
		}

		return Collections.emptyList();
	}

	private List<Integer> getShortestPath(final int predecessor[], int closestNode) {
		List<Integer> path = new ArrayList<Integer>();
		path.add(closestNode);
		while (predecessor[closestNode] != UNDEFINED) {
			path.add(predecessor[closestNode]);
			closestNode = predecessor[closestNode];
		}

		Collections.reverse(path);
		return path;
	}
}
