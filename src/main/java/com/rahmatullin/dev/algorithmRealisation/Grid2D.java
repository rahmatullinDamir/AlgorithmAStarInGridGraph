package com.rahmatullin.dev.algorithmRealisation;

/*
 * File: Grid2D.java
 * Description: This class creates a GridGraph
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import java.util.ArrayList;
import java.util.Random;

public class Grid2D {
    // Initialize the random number generator
    private final Random rnd = new Random();
    // Two-dimensional array of points representing the grid
    private final Point[][] grid2D;
    // Width and height of the grid
    private int gridWidth;
    private int gridHeight;

    // List of obstacles in the grid
    private ArrayList<Point> gridObstacles;

    /**
     * Constructor for creating a grid with specified dimensions.
     * Initializes each cell of the grid with a new Point.
     *
     * @param gridWidth The width of the grid.
     * @param gridHeight The height of the grid.
     */
    public Grid2D(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.grid2D = new Point[gridWidth][gridHeight];
        // Initialize each cell of the grid with a new Point
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid2D[i][j] = new Point(i, j);
            }
        }
    }

    /**
     * Creates obstacles in the grid.
     * Generates a specified number of unique obstacles and adds them to the grid.
     *
     * @param numObstacles The number of obstacles to create.
     */
    public void createObstaclesInGrid(int numObstacles) {
        var obstacles = generateObstacles(numObstacles);
        gridObstacles = obstacles;

        // Add obstacles to the grid
        for (Point obstacle : obstacles) {
            int x = obstacle.x;
            int y = obstacle.y;
            // Check that the obstacle's coordinates are within the grid bounds
            if (x >= 0 && x < grid2D.length && y >= 0 && y < grid2D[0].length) {
                obstacle.status = Point.Status.BLOCK; // changing status to print
                grid2D[x][y] = obstacle; // Set the obstacle
            }
        }
    }

    /**
     * Generates a specified number of unique obstacles.
     * Ensures that each obstacle has a unique position within the grid.
     *
     * @param numObstacles The number of obstacles to generate.
     * @return An ArrayList of Points representing the obstacles.
     */
    private ArrayList<Point> generateObstacles(int numObstacles) {
        var obstacles = new ArrayList<Point>();
        // Generate unique coordinates for obstacles
        while (obstacles.size() < numObstacles) {
            int x = rnd.nextInt(gridWidth);
            int y = rnd.nextInt(gridHeight);
            Point obstacleToAdd = new Point(x, y);
            if (!obstacles.contains(obstacleToAdd)) {
                obstacles.add(obstacleToAdd);
            }
        }
        return obstacles;
    }

    /**
     * Retrieves the grid.
     *
     * @return A two-dimensional array of Points representing the grid.
     */
    public Point[][] getGrid() {
        return grid2D;
    }

    /**
     * Retrieves the list of obstacles in the grid.
     *
     * @return An ArrayList of Points representing the obstacles.
     */
    public ArrayList<Point> getGridObstacles() {
        return gridObstacles;
    }

    /**
     * Retrieves the width of the grid.
     *
     * @return The width of the grid.
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Retrieves the height of the grid.
     *
     * @return The height of the grid.
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Overrides the toString method to provide a string representation of the grid.
     *
     * @return A string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Build a string representation of the grid
        for (int ir = 0; ir < gridWidth; ir++) {
            for (int ic = 0; ic < gridHeight; ic++) {
                sb.append(grid2D[ir][ic].status.getTitle()).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
