/*
 * File: CSVExporter.java
 * Description: Writes a 2D array of table data,
 * consisting of generics that can be converted to strings,
 * to a comma-separated values (CSV) file format.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2023 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */

package com.rahmatullin.dev.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {
    /**
     * Writes a 2D array into a CSV file, optionally including a header
     * @param <T>        The type of elements of the table
     * @param tableData  A table as a 2D array
     * @param fileName   Path and name of the CSV file
     * @param delimiter  A string or character delimiting the column values
     * @param header     An 1D array of the column names
     */
    public static <T> void writeFile(
            T[][] tableData, String fileName,
            String delimiter, String[] header) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header if provided
            if (header != null) {
                writer.write(composeLine(header, delimiter));
                writer.newLine();
            }

            // Write the table data
            for (T[] row : tableData) {
                writer.write(composeLine(row, delimiter));
                writer.newLine();
            }
        }
    }

    /**
     * Creates a string containing the elements of a 1D array separated by a delimiter
     * @param <T>       Type of elements
     * @param array     1D array
     * @param delimiter Delimiting string
     * @return A string of elements
     */
    private static <T> String composeLine(T[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].toString());
            if (i < array.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
