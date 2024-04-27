package com.rahmatullin.dev.algorithmRealisation;

/*
 * File: AStar.java
 * Description: Created A* algorithm to find the shortest path in GridGraph
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import com.rahmatullin.dev.priorityQueue.PriorityQueueMin;
import com.rahmatullin.dev.utils.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class AStar {

    // Priority queue for nodes awaiting evaluation
    private PriorityQueueMin<Point> openSet;
    // Set for storing evaluated nodes
    private HashSet<Point> closedSet;
    // Start and end nodes
    private Point start, end;
    // Reference to the grid
    private Grid2D grid2D;
    // Grid matrix
    private Point[][] matrix;

    /**
     * Constructor for initializing the A* algorithm.
     * Initializes the algorithm with the start and end points and the grid.
     * Sets up the open set sorted by total cost and initializes the closed set.
     *
     * @param start The starting point of the path.
     * @param end The ending point of the path.
     * @param grid2D The grid on which the path is to be found.
     */
    public AStar(Point start, Point end, Grid2D grid2D) {
        this.matrix = grid2D.getGrid();
        this.grid2D = grid2D;
        this.start = this.matrix[start.y][start.x];
        this.end = this.matrix[end.y][end.x];
        this.openSet = new PriorityQueueMin<>(Comparator.comparingDouble(point -> point.fCost)); // Sorting nodes by total cost
        this.closedSet = new HashSet<>();
    }

    /**
     * Main method for pathfinding using the A* algorithm.
     * Iterates through the open set, evaluating nodes until the end point is found or the open set is empty.
     * Prints intermediate states if requested.
     *
     * @param printIntermediateStates Whether to print the grid's state at each iteration.
     * @return An ArrayList of Points representing the shortest path, or null if no path is found.
     */
    public ArrayList<Point> aStarSearch(boolean printIntermediateStates) {
        start.status = Point.Status.OPENED;
        openSet.add(start);
        while (!openSet.isEmpty()) { // While there are nodes in the open set
            if (printIntermediateStates) {
                Logger.writeLine(grid2D.toString());
            }
            Point current = openSet.extract(); // Select the node with the lowest total cost
            current.status = Point.Status.OPENED;
            closedSet.add(current); // Move the node to the closed set

            if (current.equals(end)) { // If the current node is the end point
                return PathSeacrh.reconstructPath(current); // Reconstruct and return the path
            }

            for (Point neighbor : neighbors(current)) { // Consider all neighbors of the current node
                if (closedSet.contains(neighbor)) continue; // Skip already evaluated nodes

                int tentativeGCost = current.gCost + heuristic(current, neighbor); // Calculate the tentative gCost
                if ((!(openSet.contains(neighbor))) || tentativeGCost < neighbor.gCost) { // If the node is not in the open set or the new cost is lower
                    neighbor.parent = current; // Set the parent for path reconstruction
                    neighbor.gCost = tentativeGCost; // Update the gCost
                    neighbor.hCost = heuristic(neighbor, end); // Calculate the heuristic cost
                    neighbor.fCost = neighbor.gCost + neighbor.hCost; // Update the total cost

                    if (!(openSet.contains(neighbor))) { // If the node is not in the open set
                        neighbor.status = Point.Status.OPENED;
                        openSet.add(neighbor); // Add the node to the open set
                    }
                }
            }
        }

        return null;
    }

    /**
     * Checks if a node is passable.
     * Determines if a node is not an obstacle in the grid.
     *
     * @param dot The node to check.
     * @return True if the node is passable, false otherwise.
     */
    public boolean passable(Point dot) {
        return !grid2D.getGridObstacles().contains(dot);
    }

    /**
     * Calculates the heuristic cost.
     * Uses the Euclidean distance to estimate the cost from one point to another.
     *
     * @param first The starting point.
     * @param second The ending point.
     * @return The heuristic cost as an integer.
     */
    public int heuristic(Point first, Point second){
        return (int) (10 * Math.sqrt((second.x - first.x)*(second.x - first.x) + (second.y - first.y)*(second.y - first.y)));
    }

    /**
     * Retrieves the neighbors of the current node.
     * Considers all valid neighbors within the grid boundaries.
     * Filters out impassable neighbors and marks them as opened.
     *
     * @param current The current node.
     * @return An ArrayList of Points representing the neighbors.
     */
    public ArrayList<Point> neighbors(Point current) {
        ArrayList<Point> results = new ArrayList<>();

        // Check neighbors on the edges of the grid
        if (current.x + 1 < matrix.length) {
            results.add(matrix[current.x + 1][current.y]);
        }
        if (current.x - 1 >= 0) {
            results.add(matrix[current.x - 1][current.y]);
        }
        if (current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x][current.y + 1]);
        }
        if (current.y - 1 >= 0) {
            results.add(matrix[current.x][current.y - 1]);
        }
        if (current.x + 1 < matrix.length && current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x + 1][current.y + 1]);
        }
        if (current.x - 1 >= 0 && current.y - 1 >= 0) {
            results.add(matrix[current.x - 1][current.y - 1]);
        }
        if (current.x + 1 < matrix.length && current.y - 1 >= 0) {
            results.add(matrix[current.x + 1][current.y - 1]);
        }
        if (current.x - 1 >= 0 && current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x - 1][current.y + 1]);
        }

        // Filter neighbors that are passable and mark them as opened
        results = results.stream()
                .filter(this::passable)
                .peek(val -> val.status = Point.Status.OPENED)
                .collect(Collectors.toCollection(ArrayList::new));

        return results;
    }
}
