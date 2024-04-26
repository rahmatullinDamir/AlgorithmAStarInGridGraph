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

public class Main {
    public static void main(String[] args) {
        Logger.writeLine("Start!\n");
        Stopwatch timer = new Stopwatch();

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


        // allow the unicode characters support
        System.setProperty("file.encoding", "UTF-8");

        // make 'saves' folder
        String savesDirName = "saves";
        String currentDirectory = System.getProperty("user.dir");
        String savesFolderPath = Paths.get(currentDirectory, savesDirName).toString();

        try {
            // Check if the 'saves' folder exists, if not, create it
            if (!Files.exists(Paths.get(savesFolderPath))) {
                Files.createDirectory(Paths.get(savesFolderPath));
            }
        } catch (IOException e) {
            Logger.write("Error creating 'saves' folder", e.getMessage(), "\n");
        }

        // define the program settings
        boolean writeToConsole = true; // write grid data to console
        boolean writeToImage = true; // write grid data to image
        int resx = 20; // cells count in each direction
        int resy = 20; // cells count in each direction
        int x1 = 0; // cells count in each direction
        int y1 = 0; // cells count in each direction
        int x2 = 12; // cells count in each direction
        int y2 = 10; // cells count in each direction
        int obstaclesNum = 20; // cells count in each direction
        int imageMinRes = 300; // minimal resolution of the image to save to
        int exp = 4; // number of experiments
        Scanner scan = new Scanner(System.in);

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
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
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-obs") && i + 1 < args.length) {
                    try {
                        obstaclesNum = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-x1") && i + 1 < args.length) {
                    try {
                        x1 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-y1") && i + 1 < args.length) {
                    try {
                        y1 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-x2") && i + 1 < args.length) {
                    try {
                        x2 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-y2") && i + 1 < args.length) {
                    try {
                        y2 = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-exp") && i + 1 < args.length) {
                    try {
                        exp = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException ex) {
                        Logger.writeLine("Invalid exp value. Using default!");
                    }
                    i++; // Skip the next argument
                }
                else if (args[i].equals("-console")) {
                    writeToConsole = true;
                } else if (args[i].equals("-image")) {
                    writeToImage = true;
                }
            }
        }

        String[][] table = new String[exp][6]; // for write data to csv table
        for (int i = 0; i < exp; i++) {
            timer.reset();
            Logger.writeLine("------------------------------------");
            Logger.writeLine("          Exp number: " + (i + 1));
            Logger.writeLine("------------------------------------");
            try {
                Grid2D grid = new Grid2D(resx, resy);
                grid.createObstaclesInGrid(obstaclesNum);
                var startPoint = new Point(x1, y1);
                var endPoint = new Point(x2, y2);
                AStar astr = new AStar(startPoint, endPoint, grid);

                if (writeToConsole) {
                    Logger.writeLine("Initial state of the gridGraph:");
                    Logger.write(grid.toString());
                    Logger.writeLine("------------------------------------");
                }
                // save to image
                if (writeToImage) {
                    String fileName = Paths.get(savesDirName, String.format("%06d.ppm", i)).toString();
                    PPMExporter.writeFile(grid.getGrid(),
                            grid.getGridHeight(), grid.getGridWidth(), fileName,
                            Point.is(Point.Status.CLOSED),
                            Point.is(Point.Status.PATH),
                            ((resx < imageMinRes)) ? imageMinRes / resx : (resy < imageMinRes) ? imageMinRes / resy : 1);
                }
                if (astr.aStarSearch(writeToConsole) != null) {
                    Logger.writeLine(grid.toString());
                    Logger.writeLine("Ok!");
                    Logger.write("Elapsed time =", timer.getElapsedTime(), "[s]\n");
                    table[i][0] = String.valueOf(i);
                    table[i][1] = "[" + x1 + "," + y1 + "]";
                    table[i][2] = "[" + x2 + "," + y2 + "]";
                    table[i][3] = resx + "/" + resy;
                    table[i][4] = String.valueOf(obstaclesNum);
                    table[i][5] = String.valueOf(timer.getElapsedTime()) + "[s]";
                    if (i != exp - 1) {
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
                            Logger.writeLine("Invalid value. Using default!");
                        }
                    }
                } else {
                    Logger.writeLine("AStar Algorithm can't find path, try again...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        String[] header = {"e", "startCoords", "endCoords" ,"res x/y", "numOfObstacles", "time"};
        String fileName = "timeToGetPathInGrid.csv";
        String delimiter = ";";


        try {
            CSVExporter.writeFile(table, fileName, delimiter, header);
            System.out.println("CSV file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}