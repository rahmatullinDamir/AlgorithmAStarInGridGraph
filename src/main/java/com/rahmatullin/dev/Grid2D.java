package com.rahmatullin.dev;
/*
 * File: Grid2D.java
 * Description:
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */


import java.util.ArrayList;
import java.util.Random;

public class Grid2D {
    private final Random rnd = new Random();
    private final Point[][] grid2D;
    private int gridWidth;
    private int gridHeight;

    private ArrayList<Point> gridObstacles;


    Grid2D(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.grid2D = new Point[gridWidth][gridHeight];
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                grid2D[i][j] = new Point(i, j);
            }
        }
    }


    public void createObstaclesInGrid(int numObstacles) {
        var obstacles = generateObstacles(numObstacles);
        gridObstacles = obstacles;

        for (Point obstacle : obstacles) {
            int x = obstacle.x;
            int y = obstacle.y;
            if (x >= 0 && x < grid2D.length && y >= 0 && y < grid2D[0].length) {
                obstacle.status = Point.Status.BLOCK;
                grid2D[x][y] = obstacle; // Устанавливаем препятствие
            }
        }
    }

    private ArrayList<Point> generateObstacles(int numObstacles) {
        var obstacles = new ArrayList<Point>();
        while (obstacles.size() < numObstacles) {
            int x = rnd.nextInt(gridWidth);
            int y = rnd.nextInt(gridHeight);
            Point obstacleToAdd = new Point(x, y);
//            int [] obstacle = {x, y};
            if (!obstacles.contains(obstacleToAdd)) {
                obstacles.add(obstacleToAdd);
            }
        }
        return obstacles;
    }

    public Point[][] getGrid() {
        return grid2D;
    }

    public ArrayList<Point> getGridObstacles() {
        return gridObstacles;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }


    public void printMatrix() {
        for (int ir = 0; ir < gridWidth; ir++) {
            for (int ic = 0; ic < gridHeight; ic++) {
                System.out.print(grid2D[ir][ic].status.getTitle() + " ");
            }
            System.out.println();
        }
    }

//        public static void printPath(ArrayList<Point> path) {
//            for (Point point : path) {
//                System.out.print("(" + point.x + ", " + point.y + ") ");
//            }
//            System.out.println();
//        }
    }


//    public void print() {
//        for (int[] row : grid2D) {
//            for (int cell : row) {
//                System.out.print(cell + " ");
//            }
//            System.out.println();
//        }
//    }

