/*
 * File: CollectionUtils.java
 * Description: Some helper methods for collections.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2024 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */

package com.rahmatullin.dev.utils;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static com.rahmatullin.dev.utils.Utils.getRandomValue;


/**
 * Provides helper functions for some collections data structures.
 */
public class CollectionUtils {
    /**
     * Swaps two elements in the given array.
     * @param arr the array in which elements will be swapped
     * @param i the index of the first element to be swapped
     * @param j the index of the second element to be swapped
     */
    public static void swap(Object[] arr, int i, int j) {
        Object oldI = arr[i];
        arr[i] = arr[j];
        arr[j] = oldI;
    }

    /**
     * Checks if an array contains at least one element of a given value.
     * @param arr the array to find an element
     * @param value the value to find in the array elements
     * @return {@code true} if the array contains the {@code value}; {@code false} otherwise
     * @param <T> the type of the array elements
     */
    public static <T extends Comparable<T>> boolean contains(T[] arr, T value) {
        for (T item : arr) {
            if (item != null && CompareUtils.equals(item, value, null)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes a new generic array of elements with the same uniform values.
     * @param size the total number of elements in the array
     * @param uniformValue the value to assign to each element
     * @return an array of uniform values
     * @param <T> the type of the array elements
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] makeUniform(int size, T uniformValue) {
        //T[] arr = (T[]) new Object[size];
        T[] arr = (T[]) Array.newInstance(Comparable.class, size);
        Arrays.fill(arr, uniformValue);
        return arr;
    }

    /**
     * Generates an array of random unique values within the specified range.
     * @param size the size of the array to generate
     * @param vMin the minimum value (inclusive) of the range
     * @param vMax the maximum value (inclusive) of the range
     * @param <T> the type of elements in the array
     * @return an array of random unique values within the specified range
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] makeRandom(int size, T vMin, T vMax, Class<T> elementType) {
        //T[] arr = (T[]) new Object[size]; // T is not known at runtime due to type erasure
        //T[] arr = (T[]) Array.newInstance(Comparable.class, size);
        T[] arr = (T[]) Array.newInstance(elementType, size);

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            T randomValue = getRandomValue(random, vMin, vMax);
            // regenerate a value if duplicates found
            while (CollectionUtils.contains(arr, randomValue)) {
                randomValue = getRandomValue(random, vMin, vMax);
            }
            arr[i] = randomValue;
        }

        return arr;
    }

    /**
     * Finds the minimum value in the generic array.
     * @param array the array in which to find the minimum value
     * @return the minimum value in the array
     * @param <T> the type of elements in the array
     */
    public static <T extends Comparable<T>> T min(T[] array) {
        T vmin = array[0];
        for (T item : array) {
            if (CompareUtils.less(item, vmin, null)) {
                vmin = item;
            }
        }
        return vmin;
    }

    /**
     * Finds the maximum value in the generic array.
     * @param array the array in which to find the maximum value
     * @return the maximum value in the array
     * @param <T> the type of elements in the array
     */
    public static <T extends Comparable<T>> T max(T[] array) {
        T vmax = array[0];
        for (T item : array) {
            if (CompareUtils.greater(item, vmax, null)) {
                vmax = item;
            }
        }
        return vmax;
    }

    /**
     * Copies elements from one array to another.
     * @param from the source array to copy elements from
     * @param to the destination array to copy elements to
     * @param <T> the type of elements in the arrays
     */
    public static <T> void copy(T[] from, T[] to) {
        System.arraycopy(from, 0, to, 0, to.length);
    }

    /**
     * Clone an array
     * @param array an array to copy the elements from
     * @return independent copy of a given array
     * @param <T> the type of elements in the array
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] clone(T[] array) {
        T[] cloned = (T[]) new Object[array.length];
        CollectionUtils.copy(array, cloned);
        return cloned;
    }
}