package com.rahmatullin.dev.algorithmRealisation;

/*
 * File: PathSearch.java
 * Description: This class returns a shortest path from Node which is end points
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import java.util.ArrayList;
import java.util.Collections;

public class PathSeacrh {
    /**
     * Reconstructs the shortest path from the end point to the start point.
     * This method iterates backward from the end point to the start point,
     * adding each point to the path list. It then reverses the list to ensure
     * the path is ordered from start to end.
     * @param current The end point of the path.
     * @return An ArrayList of Points representing the shortest path from the start to the end.
     */
    public static ArrayList<Point> reconstructPath(Point current) {
        ArrayList<Point> path = new ArrayList<>(); // Initialize the path list
        while (current != null) { // Continue until the start point is reached
            path.add(current); // Add the current point to the path
            current.status = Point.Status.PATH; // Mark the current point as part of the path
            current = current.parent; // Move to the parent point
        }
        Collections.reverse(path); // Reverse the path to ensure it's ordered from start to end
        return path; // Return the reconstructed path
    }
}
