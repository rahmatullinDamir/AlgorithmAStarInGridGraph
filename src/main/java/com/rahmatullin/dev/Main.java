package com.rahmatullin.dev;

import com.rahmatullin.dev.io.CSVExporter;
import com.rahmatullin.dev.utils.Logger;
import com.rahmatullin.dev.utils.Stopwatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                "\tWrite cell data to console: -console",
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
        boolean writeToImage = false; // write grid data to image
        int resx = 20; // cells count in each direction
        int resy= 20; // cells count in each direction
        int imageMinRes = 300; // minimal resolution of the image to save to
        int exp = 50; // number of experiments

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
                else if (args[i].equals("-console")) {
                    writeToConsole = true;
                } else if (args[i].equals("-image")) {
                    writeToImage = true;
                }
            }
        }


        String[] header = {"e", "N", "porosity"};
        String fileName = "timeToGetPathInGrid.csv";
        String delimiter = ";";

//
//        try {
//            CSVExporter.writeFile(table, fileName, delimiter, header);
//            System.out.println("CSV file created successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

// TODO: написать обработку исключения кода путь не построился
        Grid2D grid = new Grid2D(resx,resy);
        grid.createObstaclesInGrid(60);
        var startPoint = new Point(0,0);
        var endPoint = new Point( 14,18);
        AStar astr = new AStar(startPoint, endPoint, grid);
        astr.aStarSearch();
//        Grid2D.printPath(astr.aStarSearch());
        grid.printMatrix();
//        grid.drawGrid(graph, astr.aStarSearch(graph, startPoint, endPoint));


    }
}