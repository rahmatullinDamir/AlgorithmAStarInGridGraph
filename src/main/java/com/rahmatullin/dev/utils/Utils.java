/*
 * File: Utils.java
 * Description: Some helpful utility functions.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2024 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */
package com.rahmatullin.dev.utils;

import java.util.Random;

public class Utils {
    /**
     * Generates a random value within the specified range.
     * @param randomizer the Random object used to generate random values
     * @param vMin the minimum value (inclusive) of the range
     * @param vMax the maximum value (inclusive) of the range
     * @return a random value within the specified range
     * @param <T> the type of the generated value
     * @throws IllegalArgumentException if the types of vmin and vmax are not supported
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRandomValue(Random randomizer, T vMin, T vMax) {
        if (vMin instanceof Integer && vMax instanceof Integer) {
            // cast to Integer if T is Integer
            int minValue = (Integer) vMin;
            int maxValue = (Integer) vMax;
            return (T) (Integer.valueOf(minValue + randomizer.nextInt(maxValue - minValue + 1)));
        } else if (vMin instanceof Double && vMax instanceof Double) {
            // cast to Double if T is Double
            double minValue = (Double) vMin;
            double maxValue = (Double) vMax;
            return (T) (Double.valueOf(minValue + randomizer.nextDouble() * (maxValue - minValue)));
        } else {
            throw new IllegalArgumentException("Unsupported type for vmin and vmax");
        }
    }
}
