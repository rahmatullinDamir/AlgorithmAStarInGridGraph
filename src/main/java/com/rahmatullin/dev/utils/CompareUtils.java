/*
 * File: CompareUtils.java
 * Description: A helper class with methods that make the comparison simpler.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2024 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */
package com.rahmatullin.dev.utils;

import java.util.Comparator;

public class CompareUtils {
    public enum Operators {
        EQUALS, LESS, GREATER, LESS_OR_EQUALS, GREATER_OR_EQUALS
    }

    /**
     * Compares two comparable values
     * to check if the first value is less than the second value.
     * @param a the first comparable value
     * @param b the second comparable value
     * @param cmpr A comparator that might be null
     * @return {@code true} if {@code a} < {@code b}; {@code false} otherwise
     * @param <T> the type of the comparable values
     */
    public static <T> boolean less(
            T a, T b, Comparator<T> cmpr) {
        return getCompareResult(a, b, Operators.LESS, cmpr);
    }

    /**
     * Compares two comparable values
     * to check if the first value is less than or equal to the second value.
     * @param a the first comparable value
     * @param b the second comparable value
     * @param cmpr A comparator that might be null
     * @return {@code true} if {@code a} <= {@code b}; {@code false} otherwise
     * @param <T> the type of the comparable values
     */
    public static <T> boolean lessOrEquals(
            T a, T b, Comparator<T> cmpr) {
        return getCompareResult(a, b, Operators.LESS_OR_EQUALS, cmpr);
    }

    /**
     * Compares two comparable values
     * to check if the first value is greater than the second value.
     * @param a the first comparable value
     * @param b the second comparable value
     * @param cmpr A comparator that might be null
     * @return {@code true} if {@code a} > {@code b}; {@code false} otherwise
     * @param <T> the type of the comparable values
     */
    public static <T> boolean greater(
            T a, T b, Comparator<T> cmpr) {
        return getCompareResult(a, b, Operators.GREATER, cmpr);
    }

    /**
     * Compares two comparable values
     * to check if the first value is greater than or equal to the second value.
     * @param a the first comparable value
     * @param b the second comparable value
     * @param cmpr A comparator that might be null
     * @return {@code true} if {@code a} >= {@code b}; {@code false} otherwise
     * @param <T> the type of the comparable values
     */
    public static <T> boolean greaterOrEquals(
            T a, T b, Comparator<T> cmpr) {
        return getCompareResult(a, b, Operators.GREATER_OR_EQUALS, cmpr);
    }

    /**
     * Compares two comparable values
     * to check if the first value equals the second value.
     * @param a the first comparable value
     * @param b the second comparable value
     * @param cmpr A comparator that might be null
     * @return {@code true} if {@code a} == {@code b}; {@code false} otherwise
     * @param <T> the type of the comparable values
     */
    public static <T> boolean equals(
            T a, T b, Comparator<T> cmpr) {
        return getCompareResult(a, b, Operators.EQUALS, cmpr);
    }

    /**
     * Compares two objects
     * using the specified comparison operator
     * and, if provided, the specified {@code Comparator}
     * @param a the first object to be compared
     * @param b the second object to be compared
     * @param operator the comparison operator to be applied
     * @param cmpr the {@code Comparator} to determine the order of the objects (can be {@code null})
     * @param <T> the type of objects being compared
     * @return {@code true} if the specified comparison condition holds; {@code false} otherwise
     */
    public static <T> boolean getCompareResult(
            T a, T b, Operators operator, Comparator<T> cmpr) {
        switch (operator) {
            case EQUALS -> {
                return (cmpr != null) ? compare(a, b, cmpr) == 0 : compare(a, b) == 0;
            }
            case LESS -> {
                return (cmpr != null) ? compare(a, b, cmpr) < 0 : compare(a, b) < 0;
            }
            case LESS_OR_EQUALS -> {
                return (cmpr != null) ? compare(a, b, cmpr) <= 0 : compare(a, b) <= 0;
            }
            case GREATER -> {
                return (cmpr != null) ? compare(a, b, cmpr) > 0 : compare(a, b) > 0;
            }
            case GREATER_OR_EQUALS -> {
                return (cmpr != null) ? compare(a, b, cmpr) >= 0 : compare(a, b) >= 0;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Compares two objects using their natural ordering
     * as defined by the {@code Comparable} interface.
     * @param a the first object to be compared
     * @param b the second object to be compared
     * @param <T> the type of objects being compared, which must implement the {@code Comparable} interface
     * @return a negative integer, zero, or a positive integer
     * as the first argument is less than, equal to, or greater than the second
     */
    public static <T> int compare(
            T a, T b) {
        return ((Comparable<T>) a).compareTo(b);
    }

    /**
     * Compares two objects using the specified {@code Comparator}
     * @param a the first object to be compared
     * @param b the second object to be compared
     * @param cmpr the {@code Comparator} to determine the order of the objects
     * @param <T> the type of objects being compared
     * @return a negative integer, zero, or a positive integer
     * as the first argument is less than, equal to, or greater than the second
     */
    public static <T> int compare(
            T a, T b, Comparator<T> cmpr) {
        return cmpr.compare(a, b);
    }

    /**
     * Checks whether vMin <= v <= vMax
     * @param v A variable to check its being in the range
     * @param vMin Left-hand inclusive bound of the range
     * @param vMax Right-hand inclusive bound of the range
     * @param cmpr A comparator that might be null
     * @param <T> The type of the variables to compare
     * @return true if v is in the range [vMin, vMax], false otherwise
     */
    public static <T> boolean inRange(
            T v, T vMin, T vMax, Comparator<T> cmpr) {
        return CompareUtils.greaterOrEquals(v, vMin, cmpr)
                && CompareUtils.lessOrEquals(v, vMax, cmpr);
    }

    /**
     * Constrains a value to be in the range [vMin, vMax], bounds inclusive
     * @param v A value to constrain
     * @param vMin Left-hand inclusive bound of the range
     * @param vMax Right-hand inclusive bound of the range
     * @param cmpr A comparator that might be null
     * @param <T> The type of the value to constrain
     * @return The constrained value
     */
    public static <T> T constrain(
            T v, T vMin, T vMax, Comparator<T> cmpr) {
        if (CompareUtils.less(v, vMin, cmpr)) {
            return vMin;
        } else if (CompareUtils.greater(v, vMax, cmpr)) {
            return vMax;
        } else {
            return v;
        }
    }

    /**
     * Checks if the array is sorted from {@code arr[from]} to {@code arr[to]} in ascending order.
     * @param arr an array to check
     * @param from the index of the first element in the range
     * @param to the index of the last element in the range
     * @param cmpr a comparator that might be null
     * @return {@code true} if the specified range of the array is sorted in ascending order; {@code false} otherwise
     * @param <T> the type of the array elements
     */
    public static <T> boolean isSorted(
            T[] arr, int from, int to, Comparator<T> cmpr) {
        for (int i = from + 1; i <= to; i++) {
            if (CompareUtils.less(arr[i], arr[i - 1], cmpr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the whole array is sorted in ascending order.
     * @param arr an array to check
     * @return {@code true} if  the array is sorted in ascending order; {@code false} otherwise
     * @param cmpr A comparator that might be null
     * @param <T> the type of the array elements
     */
    public static <T> boolean isSorted(
            T[] arr, Comparator<T> cmpr) {
        return CompareUtils.isSorted(arr, 0, arr.length - 1, cmpr);
    }
}
