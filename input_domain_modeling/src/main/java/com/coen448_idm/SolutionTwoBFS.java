package coen448.assignment1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Bipartite Graph Detection using BFS with validation and self-loop detection.
 */
public class SolutionTwoBFS {

    /**
     * Determine if a graph is bipartite using BFS coloring.
     * 
     * @param graph adjacency list representation of graph
     * @return true if bipartite, false otherwise
     * @throws NullPointerException if graph or any adjacency list is null
     * @throws IndexOutOfBoundsException if node index is out of valid range
     */
    public boolean isBipartite(int[][] graph) {
        // Bug Fix #2: Null validation
        if (graph == null) {
            throw new NullPointerException("Graph cannot be null");
        }

        // Validate all adjacency lists are non-null
        for (int i = 0; i < graph.length; i++) {
            if (graph[i] == null) {
                throw new NullPointerException("Adjacency list at index " + i + " is null");
            }
        }

        // Color array: 0 = uncolored, 1 = color A, -1 = color B
        int[] color = new int[graph.length];

        // Process each connected component
        for (int i = 0; i < graph.length; i++) {
            if (color[i] == 0) { // uncolored node
                if (!bfsColor(i, 1, color, graph)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * BFS-based coloring starting from a given node.
     * 
     * @param startNode node to start BFS from
     * @param initialColor initial color for startNode (1 or -1)
     * @param color color array
     * @param graph adjacency list
     * @return true if component is bipartite, false otherwise
     * @throws IndexOutOfBoundsException if neighbor index is invalid
     */
    private boolean bfsColor(int startNode, int initialColor, int[] color, int[][] graph) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startNode);
        color[startNode] = initialColor;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            int nodeColor = color[node];

            // Process all neighbors
            for (int neighbor : graph[node]) {
                // Bug Fix #2: Validate neighbor index
                if (neighbor < 0 || neighbor >= graph.length) {
                    throw new IndexOutOfBoundsException(
                            "Neighbor index " + neighbor + " out of valid range [0, " + (graph.length - 1) + "]"
                    );
                }

                // Bug Fix #1: Detect self-loop
                if (neighbor == node) {
                    // Self-loop: node cannot have opposite color to itself
                    return false;
                }

                // Color neighbor if uncolored
                if (color[neighbor] == 0) {
                    color[neighbor] = -nodeColor;
                    queue.offer(neighbor);
                } else if (color[neighbor] == nodeColor) {
                    // Conflict: adjacent nodes have same color
                    return false;
                }
            }
        }

        return true;
    }
}