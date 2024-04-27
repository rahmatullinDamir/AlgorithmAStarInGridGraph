package com.rahmatullin.dev.algorithmRealisation;

/*
 * File: Point.java
 * Description: This class creates a Node dot in GridGraph
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import java.util.Objects;

public class Point {
    /**
     * Enum to represent the status of a point in the grid.
     * Each status is represented by an emoji for visualization.
     */
    public enum Status {
        CLOSED("\uD83D\uDFEB"), // Closed point, not yet evaluated
        OPENED("\uD83D\uDFE7"), // Opened point, in the evaluation queue
        BLOCK("\uD83D\uDFE5"), // Blocked point, cannot be traversed
        PATH("\uD83D\uDFE9"); // Path point, part of the shortest path
        private String title;

        public String getTitle() {
            return title;
        }
        Status(String title) {
            this.title = title;
        }
    }

    // Coordinates of the point
    public int x;
    public int y;
    // Parent point in the path
    public Point parent;
    // Costs for A* algorithm
    public int gCost; // Cost from start to this point
    public int hCost; // Heuristic cost from this point to the goal
    public int fCost; // Total cost (gCost + hCost)
    // Status of the point
    public Status status;

    /**
     * Constructor for a point with default status CLOSED.
     * Initializes the point with the given coordinates and sets its status to CLOSED.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.status = Status.CLOSED;
    }

    /**
     * Constructor for a point with specified parent, gCost, hCost, and fCost.
     * Initializes the point with the given coordinates, parent, and costs.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param parent The parent point in the path.
     * @param gCost The cost from the start to this point.
     * @param hCost The heuristic cost from this point to the goal.
     */
    public Point(int x, int y, Point parent, int gCost, int hCost) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
    }

    /**
     * Overrides the equals method to compare points based on their coordinates.
     * Two points are considered equal if their x and y coordinates are the same.
     *
     * @param o The object to compare with this point.
     * @return True if the object is a Point with the same x and y coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    /**
     * Static method to get the integer representation of a point's status.
     * Returns the ordinal value of the status enum.
     *
     * @param status The status of the point.
     * @return The integer representation of the status.
     */
    public static int is(Status status) {
        return status.ordinal();
    }


    /**
     * Overrides the hashCode method to ensure consistent hashing based on coordinates.
     * Uses the Objects.hash method with the x and y coordinates to generate a hash code.
     *
     * @return A hash code value for this point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
