package coen448.assignment1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Basis Path Testing - dfsColor() Function")
public class BasisPathTestingTest {

    private SolutionOneDFS dfsSolution;
    private SolutionTwoBFS bfsSolution;

    @BeforeEach
    void setUp() {
        dfsSolution = new SolutionOneDFS();
        bfsSolution = new SolutionTwoBFS();
    }

    // ============================================================
    // DFS Solution - Basis Path Tests (CC = 6)
    // ============================================================

    @Test
    @DisplayName("DFS Path 1: Invalid neighbor index throws IndexOutOfBoundsException")
    void testDFSPathInvalidIndex() {
        int[][] graph = {{5}, {0}};
        assertThrows(IndexOutOfBoundsException.class, 
            () -> dfsSolution.isBipartite(graph),
            "Path 1: Should throw exception for invalid index");
    }

    @Test
    @DisplayName("DFS Path 2: Self-loop detected, returns false")
    void testDFSPathSelfLoop() {
        int[][] graph = {{0}};
        assertFalse(dfsSolution.isBipartite(graph),
            "Path 2: Self-loop should return false");
    }

    @Test
    @DisplayName("DFS Path 3: Tree structure - recursive success")
    void testDFSPathTreeRecursiveSuccess() {
        int[][] graph = {{1}, {0, 2}, {1}};
        assertTrue(dfsSolution.isBipartite(graph),
            "Path 3: Tree should be bipartite");
    }

    @Test
    @DisplayName("DFS Path 4: Odd cycle - recursive failure")
    void testDFSPathOddCycleRecursiveFailure() {
        int[][] graph = {{1, 2}, {0, 2}, {0, 1}};
        assertFalse(dfsSolution.isBipartite(graph),
            "Path 4: Triangle should not be bipartite (odd cycle)");
    }

    @Test
    @DisplayName("DFS Path 5: Color conflict with same-colored neighbor")
    void testDFSPathColorConflict() {
        int[][] graph = {{1, 2}, {0, 2}, {0, 1}};
        assertFalse(dfsSolution.isBipartite(graph),
            "Path 5: Conflict should return false");
    }

    @Test
    @DisplayName("DFS Path 6: Even cycle - no conflict with already-colored neighbor")
    void testDFSPathAlreadyColoredCorrect() {
        int[][] graph = {{1, 3}, {0, 2}, {1, 3}, {0, 2}};
        assertTrue(dfsSolution.isBipartite(graph),
            "Path 6: Even cycle should be bipartite");
    }

    // ============================================================
    // BFS Solution - Basis Path Tests (CC = 6)
    // ============================================================

    @Test
    @DisplayName("BFS Path 1: Invalid neighbor index throws IndexOutOfBoundsException")
    void testBFSPathInvalidIndex() {
        int[][] graph = {{5}, {0}};
        assertThrows(IndexOutOfBoundsException.class,
            () -> bfsSolution.isBipartite(graph),
            "Path 1: Should throw exception for invalid index");
    }

    @Test
    @DisplayName("BFS Path 2: Self-loop detected, returns false")
    void testBFSPathSelfLoop() {
        int[][] graph = {{0}};
        assertFalse(bfsSolution.isBipartite(graph),
            "Path 2: Self-loop should return false");
    }

    @Test
    @DisplayName("BFS Path 3: Tree structure - success")
    void testBFSPathTreeSuccess() {
        int[][] graph = {{1}, {0, 2}, {1}};
        assertTrue(bfsSolution.isBipartite(graph),
            "Path 3: Tree should be bipartite");
    }

    @Test
    @DisplayName("BFS Path 4: Odd cycle - failure")
    void testBFSPathOddCycleFailure() {
        int[][] graph = {{1, 2}, {0, 2}, {0, 1}};
        assertFalse(bfsSolution.isBipartite(graph),
            "Path 4: Triangle should not be bipartite");
    }

    @Test
    @DisplayName("BFS Path 5: Color conflict detection")
    void testBFSPathColorConflict() {
        int[][] graph = {{1, 2}, {0, 2}, {0, 1}};
        assertFalse(bfsSolution.isBipartite(graph),
            "Path 5: Conflict should return false");
    }

    @Test
    @DisplayName("BFS Path 6: Even cycle - no conflict")
    void testBFSPathAlreadyColoredCorrect() {
        int[][] graph = {{1, 3}, {0, 2}, {1, 3}, {0, 2}};
        assertTrue(bfsSolution.isBipartite(graph),
            "Path 6: Even cycle should be bipartite");
    }

    // ============================================================
    // Additional Edge Cases for Complete Coverage
    // ============================================================

    @Test
    @DisplayName("Edge Case: Empty graph")
    void testEmptyGraph() {
        int[][] graph = {};
        assertTrue(dfsSolution.isBipartite(graph));
        assertTrue(bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Single node")
    void testSingleNode() {
        int[][] graph = {{}};
        assertTrue(dfsSolution.isBipartite(graph));
        assertTrue(bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Two nodes, one edge")
    void testTwoNodesOneEdge() {
        int[][] graph = {{1}, {0}};
        assertTrue(dfsSolution.isBipartite(graph));
        assertTrue(bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Disconnected bipartite components")
    void testDisconnectedComponents() {
        int[][] graph = {{1}, {0}, {3}, {2}};
        assertTrue(dfsSolution.isBipartite(graph));
        assertTrue(bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Negative neighbor index")
    void testNegativeNeighborIndex() {
        int[][] graph = {{-1}, {0}};
        assertThrows(IndexOutOfBoundsException.class,
            () -> dfsSolution.isBipartite(graph));
        assertThrows(IndexOutOfBoundsException.class,
            () -> bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Null graph")
    void testNullGraph() {
        int[][] graph = null;
        assertThrows(NullPointerException.class,
            () -> dfsSolution.isBipartite(graph));
        assertThrows(NullPointerException.class,
            () -> bfsSolution.isBipartite(graph));
    }

    @Test
    @DisplayName("Edge Case: Null adjacency list")
    void testNullAdjacencyList() {
        int[][] graph = {{1}, null, {0}};
        assertThrows(NullPointerException.class,
            () -> dfsSolution.isBipartite(graph));
        assertThrows(NullPointerException.class,
            () -> bfsSolution.isBipartite(graph));
    }
}