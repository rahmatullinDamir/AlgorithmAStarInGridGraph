package com.rahmatullin.dev;

import com.rahmatullin.dev.algorithmRealisation.AStar;
import com.rahmatullin.dev.algorithmRealisation.Grid2D;
import com.rahmatullin.dev.algorithmRealisation.Point;
import com.rahmatullin.dev.io.CSVExporter;
import com.rahmatullin.dev.io.PPMExporter;
import com.rahmatullin.dev.utils.Logger;
import com.rahmatullin.dev.utils.Stopwatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Main class for the A* pathfinding algorithm demonstration.
 * This class initializes the grid, sets up the start and end points,
 * and runs the A* algorithm to find the shortest path.
 * It also handles command-line arguments for customizing the grid and pathfinding process.
 * The results, including the grid state and execution time, can be logged to the console or saved as an image or csv table.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize logging and timing
        Logger.writeLine("Start!\n");
        Stopwatch timer = new Stopwatch();

        // Log the purpose and usage of the program
        Logger.writeLine("Find the shortest path on a 2D grid.",
                "Marking cells: ",
                "\uD83D\uDFEB - closed cells",
                "\uD83D\uDFE7 - open cells",
                "\uD83D\uDFE5 - wall cells",
                "\uD83D\uDFE9 - cells that our path contains",
                "To find shortest path program use AStar Algorithm",
                "in realisation of this algorithm program use a PriorityQueueMin",
                "the algorithm can move diagonally, up, down, left, right.",
                "Command-line arguments:",
                "\tGrid resolution x: -resx x",
                "\tGrid resolution y: -resy y",
                "\tCount of obstacles: -obs countOfObstacles",
                "\tx coordinate of start dot : -x1 x1",
                "\ty coordinate of start dot : -y1 y1",
                "\ty coordinate of end dot : -x2 x2",
                "\ty coordinate of end dot : -y2 y2",
                "\tCount of obstacles: -obs countOfObstacles",
                "\tWrite cell data to console: -console",
                "\tChoose count of expirements: -exp N",
                "\tWrite cell data to PPM file: -image \n");

        // Enable Unicode character support
        System.setProperty("file.encoding", "UTF-8");

        // Create a 'saves' directory for storing output files
        String savesDirName = "saves";
        String currentDirectory = System.getProperty("user.dir");
        String savesFolderPath = Paths.get(currentDirectory, savesDirName).toString();

        try {
            // Ensure the 'saves' directory exists
            if (!Files.exists(Paths.get(savesFolderPath))) {
                Files.createDirectory(Paths.get(savesFolderPath));
            }
        } catch (IOException e) {
            Logger.write("Error creating 'saves' folder", e.getMessage(), "\n");
        }

        // Define default settings and parse command-line arguments
        boolean writeToConsole = true; // Default: write grid data to console
        boolean writeToImage = true; // Default: write grid data to image
        int resx = 50; // Default grid resolution in x-direction
        int resy = 50; // Default grid resolution in y-direction
        int x1 = 15; // Default start point x-coordinate
        int y1 = 10; // Default start point y-coordinate
        int x2 = 40; // Default end point x-coordinate
        int y2 = 48; // Default end point y-coordinate
        int obstaclesNum = 20; // Default number of obstacles
        int imageMinRes = 300; // Minimal resolution for image output
        int exp = 1; // Default number of experiments
        Scanner scan = new Scanner(System.in);

        // Parse command-line arguments
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                // Parse resolution arguments
                if (args[i].equals("-resx") && i + 1 < args.length) {
                    try {
                        resx = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid resolution value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-resy") && i + 1 < args.length) {
                    try {
                        resy = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid resolution value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                // Parse obstacle count argument
                else if (args[i].equals("-obs") && i + 1 < args.length) {
                    try {
                        obstaclesNum = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid obstacle count. Using default!");
                    }
                    i++; // Skip the next argument
                }
                // Parse start and end point coordinates
                else if (args[i].equals("-x1") && i + 1 < args.length) {
                    try {
                        x1 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid start point x-coordinate. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-y1") && i + 1 < args.length) {
                    try {
                        y1 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid start point y-coordinate. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-x2") && i + 1 < args.length) {
                    try {
                        x2 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid end point x-coordinate. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-y2") && i + 1 < args.length) {
                    try {
                        y2 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid end point y-coordinate. Using default!");
                    }
                    i++; // Skip the next argument
                }
                // Parse experiment count argument
                else if (args[i].equals("-exp") && i + 1 < args.length) {
                    try {
                        exp = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid experiment count. Using default!");
                    }
                    i++; // Skip the next argument
                }
                // Parse console and image output flags
                else if (args[i].equals("-console")) {
                    writeToConsole = true;
                } else if (args[i].equals("-image")) {
                    writeToImage = true;
                }
            }
        }

        // Initialize a table for CSV export
        String[][] table = new String[exp][6]; // for write data to csv table
        for (int i = 0; i < exp; i++) {
            timer.reset();
            Logger.writeLine("------------------------------------");
            Logger.writeLine("          Exp number: " + (i + 1));
            Logger.writeLine("------------------------------------");
            try {
                // Initialize the grid and obstacles
                Grid2D grid = new Grid2D(resx, resy);
                grid.createObstaclesInGrid(obstaclesNum);
                var startPoint = new Point(x1, y1);
                var endPoint = new Point(x2, y2);
                AStar astr = new AStar(startPoint, endPoint, grid);

                // Log the initial grid state if requested
                if (writeToConsole) {
                    Logger.writeLine("Initial state of the gridGraph:");
                    Logger.write(grid.toString());
                    Logger.writeLine("------------------------------------");
                }
                // Run the A* algorithm and log the results
                if (astr.aStarSearch(writeToConsole) != null) {
                    Logger.writeLine(grid.toString());
                    Logger.writeLine("Ok!");
                    Logger.write("Elapsed time =", timer.getElapsedTime(), "[s]\n");
                    table[i][0] = String.valueOf(i);
                    table[i][1] = "[" + x1 + "," + y1 + "]";
                    table[i][2] = "[" + x2 + "," + y2 + "]";
                    table[i][3] = resx + "," + resy;
                    table[i][4] = String.valueOf(obstaclesNum);
                    table[i][5] = String.valueOf(timer.getElapsedTime()) + "[s]";
                    // Check if the current experiment is not the last one
                    if (i != exp - 1) {
                        // Prompt the user to input new values for the next experiment
                        try {
                            System.out.println("Введите x (разрешение) для следующего эксперемента");
                            resx = scan.nextInt();
                            System.out.println("Введите y (разрешение) для следующего эксперемента");
                            resy = scan.nextInt();
                            System.out.println("Введите x1 координату по x стартовой точки");
                            x1 = scan.nextInt();
                            System.out.println("Введите y1 координату по y стартовой точки");
                            y1 = scan.nextInt();
                            System.out.println("Введите x2 координату по x конечной точки");
                            x2 = scan.nextInt();
                            System.out.println("Введите y2 координату по y конечной точки");
                            y2 = scan.nextInt();
                            System.out.println("Введите количество препятствий в графе-сетке");
                            obstaclesNum = scan.nextInt();
                        } catch (Exception e) {
                            // Log an error message if the input is invalid
                            Logger.writeLine("Invalid value. Using default!");
                        }
                    }
                }
                else {
                    // Log a message if the A* algorithm couldn't find a path
                    Logger.writeLine("AStar Algorithm can't find path, try again...");
                }
                // Save the grid state to an image if requested
                if (writeToImage) {
                    String fileName = Paths.get(savesDirName, String.format("2%06d.ppm", i)).toString();
                    PPMExporter.writeFile(grid.getGrid(),
                            grid.getGridWidth(), grid.getGridHeight(), fileName,
                            Point.is(Point.Status.CLOSED),
                            Point.is(Point.Status.PATH),
                            (((resx < imageMinRes)) ? imageMinRes / resx : (resy < imageMinRes) ? imageMinRes / resy : 1));
                }
            } catch (Exception e) {
                // print exception if something went wrong for example couldn't write a file.
                e.printStackTrace();
            }
        }

        // Define the header for the CSV file
        String[] header = {"e", "startCoords", "endCoords" ,"res x/y", "numOfObstacles", "time"};
        // Specify the file name and delimiter for the CSV file
        String fileName = "timeToGetPathInGrid.csv";
        String delimiter = ";";

        // Attempt to write the data to a CSV file
        try {
            CSVExporter.writeFile(table, fileName, delimiter, header);
            System.out.println("CSV file created successfully!");
        } catch (IOException e) {
            // Log any IOException that occurs during the file writing process
            e.printStackTrace();
        }

    }
}