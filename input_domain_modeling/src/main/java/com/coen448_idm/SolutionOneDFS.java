package coen448.assignment1;

/**
 * Bipartite Graph Detection using DFS with validation and self-loop detection.
 */
public class SolutionOneDFS {

    /**
     * Determine if a graph is bipartite using DFS coloring.
     * 
     * @param graph adjacency list representation of graph
     * @return true if bipartite, false otherwise
     * @throws NullPointerException if graph or any adjacency list is null
     * @throws IndexOutOfBoundsException if node index is out of valid range
     */
    public boolean isBipartite(final int[][] graph) {
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

        final int[] color = new int[graph.length]; // 0=uncolored, 1/-1=colors

        // Process each connected component
        for (int i = 0; i < graph.length; i++) {
            if (color[i] == 0) { // uncolored node
                if (!dfsColor(i, 1, color, graph)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * DFS-based coloring starting from a given node.
     * 
     * @param node current node to color
     * @param assignedColor color to assign to this node
     * @param color color array
     * @param graph adjacency list
     * @return true if component is bipartite, false otherwise
     * @throws IndexOutOfBoundsException if neighbor index is invalid
     */
    private boolean dfsColor(int node, int assignedColor, final int[] color, final int[][] graph) {
        color[node] = assignedColor;

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

            if (color[neighbor] == 0) {
                // Uncolored neighbor: assign opposite color recursively
                if (!dfsColor(neighbor, -assignedColor, color, graph)) {
                    return false;
                }
            } else if (color[neighbor] == assignedColor) {
                // Conflict: neighbor has same color
                return false;
            }
        }

        return true;
    }
}