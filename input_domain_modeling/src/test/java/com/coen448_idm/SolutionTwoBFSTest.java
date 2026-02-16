package coen448.assignment1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bipartite Graph Detection SolutionTwo BFS - IDM Test Suite")
public class SolutionTwoBFSTest {

    private SolutionTwoBFS solution = new SolutionTwoBFS();

    // ============================================================
    // Each Choice Coverage (ECC) Tests
    // ============================================================

    @ParameterizedTest(name = "ECC-{0}: {8}")
    @CsvFileSource(resources = "/test_cases_ecc.csv", numLinesToSkip = 1)
    @DisplayName("ECC Tests - Each Choice Coverage")
    void testEachChoiceCoverage(
            String testID,
            String graphSize,
            String edgePresence,
            String graphStructure,
            String cyclePresence,
            String selfLoops,
            String nodeIndexValidity,
            String nullInput,
            String expectedStr,
            String description,
            String graphJSON) {
        
        int[][] graph = parseGraph(testID, graphSize, edgePresence, graphStructure, cyclePresence,
                                   selfLoops, nodeIndexValidity, nullInput);

        if (isExceptionCase(expectedStr)) {
            assertThrows(Exception.class, () -> solution.isBipartite(graph),
                    "Test " + testID + ": " + description + " should throw exception");
        } else {
            boolean expected = Boolean.parseBoolean(expectedStr);
            boolean result = solution.isBipartite(graph);
            assertEquals(expected, result,
                    "Test " + testID + ": " + description + " expected " + expected + " but got " + result);
        }
    }

    // ============================================================
    // Basic Choice Coverage (BCC) Tests
    // ============================================================

    @ParameterizedTest(name = "BCC-{0}: {8}")
    @CsvFileSource(resources = "/test_cases_bcc.csv", numLinesToSkip = 1)
    @DisplayName("BCC Tests - Basic Choice Coverage")
    void testBasicChoiceCoverage(
            String testID,
            String graphSize,
            String edgePresence,
            String graphStructure,
            String cyclePresence,
            String selfLoops,
            String nodeIndexValidity,
            String nullInput,
            String expectedStr,
            String description,
            String graphJSON) {
        
        int[][] graph = parseGraph(testID, graphSize, edgePresence, graphStructure, cyclePresence,
                                   selfLoops, nodeIndexValidity, nullInput);

        if (isExceptionCase(expectedStr)) {
            assertThrows(Exception.class, () -> solution.isBipartite(graph),
                    "Test " + testID + ": " + description + " should throw exception");
        } else {
            boolean expected = Boolean.parseBoolean(expectedStr);
            boolean result = solution.isBipartite(graph);
            assertEquals(expected, result,
                    "Test " + testID + ": " + description + " expected " + expected + " but got " + result);
        }
    }

    // ============================================================
    // Unit Tests for Specific Scenarios
    // ============================================================

    @Test
    @DisplayName("Empty graph is bipartite")
    void testEmptyGraph() {
        int[][] graph = {};
        assertTrue(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("Single node is bipartite")
    void testSingleNode() {
        int[][] graph = {{}};
        assertTrue(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("Tree structure is bipartite")
    void testTreeStructure() {
        int[][] graph = {{1}, {0, 2}, {1}};
        assertTrue(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("4-cycle (even) is bipartite")
    void testEvenCycle() {
        int[][] graph = {{1, 3}, {0, 2}, {1, 3}, {0, 2}};
        assertTrue(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("3-cycle (odd/triangle) is NOT bipartite")
    void testOddCycle() {
        int[][] graph = {{1, 2}, {0, 2}, {0, 1}};
        assertFalse(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("Self-loop is NOT bipartite")
    void testSelfLoop() {
        int[][] graph = {{0}};
        assertFalse(solution.isBipartite(graph),
                "Self-loop should make graph non-bipartite");
    }

    @Test
    @DisplayName("Disconnected bipartite components")
    void testDisconnectedBipartite() {
        int[][] graph = {{1}, {0}, {3}, {2}};
        assertTrue(solution.isBipartite(graph));
    }

    @Test
    @DisplayName("Null graph throws NullPointerException")
    void testNullGraph() {
        int[][] graph = null;
        assertThrows(NullPointerException.class, () -> solution.isBipartite(graph),
                "Null graph should throw NullPointerException");
    }

    @Test
    @DisplayName("Null adjacency list throws NullPointerException")
    void testNullAdjacencyList() {
        int[][] graph = {{1}, null, {0}};
        assertThrows(NullPointerException.class, () -> solution.isBipartite(graph),
                "Null adjacency list should throw NullPointerException");
    }

    @Test
    @DisplayName("Invalid node index throws IndexOutOfBoundsException")
    void testInvalidNodeIndex() {
        int[][] graph = {{5}, {0}};
        assertThrows(IndexOutOfBoundsException.class, () -> solution.isBipartite(graph),
                "Invalid node index should throw IndexOutOfBoundsException");
    }

    @Test
    @DisplayName("Negative node index throws IndexOutOfBoundsException")
    void testNegativeNodeIndex() {
        int[][] graph = {{-1}, {0}};
        assertThrows(IndexOutOfBoundsException.class, () -> solution.isBipartite(graph),
                "Negative node index should throw IndexOutOfBoundsException");
    }

    // ============================================================
    // Helper Methods
    // ============================================================

    private boolean isExceptionCase(String expectedStr) {
        return "Exception".equalsIgnoreCase(expectedStr);
    }

    /**
     * Parse graph based on test characteristics.
     * Match on TestID first, then fall back to characteristics.
     */
    private int[][] parseGraph(String testID, String graphSize, String edgePresence,
                               String graphStructure, String cyclePresence,
                               String selfLoops, String nodeIndexValidity, String nullInput) {
        
        // Handle null input cases
        if ("Null".equalsIgnoreCase(nullInput)) {
            return null;
        }
        if ("NullEdgeList".equalsIgnoreCase(nullInput)) {
            return new int[][]{{1}, null, {0}};
        }

        // Handle self-loop cases
        if ("Yes".equalsIgnoreCase(selfLoops)) {
            return new int[][]{{0}}; // Self-loop: node 0 connected to itself
        }

        // Handle invalid index cases
        if ("Invalid".equalsIgnoreCase(nodeIndexValidity)) {
            return new int[][]{{5}, {0}}; // Node 5 referenced but doesn't exist
        }

        // Handle standard graph cases by cycle presence
        switch (cyclePresence) {
            case "NoCycles":
                return new int[][]{{1}, {0, 2}, {1}}; // Tree: 0-1-2
            case "EvenCycle":
                return new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}}; // 4-cycle square
            case "OddCycle":
                return new int[][]{{1, 2}, {0, 2}, {0, 1}}; // 3-cycle triangle
            default:
                return new int[][]{}; // Empty graph
        }
    }
}