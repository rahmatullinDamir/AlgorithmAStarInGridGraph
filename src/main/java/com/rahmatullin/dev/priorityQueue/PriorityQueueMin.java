/*
 * File: PriorityQueueMin.java
 * Description: Implementation of a min-priority queue data structure,
 * which is based on the AbstractPriorityQueue data structure.
 * It stores items in a resizable array (not in the linked list),
 * organized in a partially sorted binary heap manner.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2024 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */
package com.rahmatullin.dev.priorityQueue;


import com.rahmatullin.dev.utils.CompareUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of a priority queue supporting minimal priority, based on a max-heap.
 * Extends the AbstractStaque class, providing operations for adding and extracting elements
 * with minimal priority, as well as retrieving the minimumal element without removal.
 * @param <T> the type of elements in the priority queue
 */

public class PriorityQueueMin<T> extends AbstractPriorityQueue<T> {
    /**
     * Iterator implementation for PriorityQueueMin, iterating over elements in descending order.
     */
    public class PQMinIterator implements Iterator<T> {
        private PriorityQueueMin<T> clone; // A clone of the priority queue

        /**
         * Constructs an iterator for PriorityQueueMin.
         * Initializes a clone of the priority queue and adds all items to it.
         * Takes linear time since already in heap order so no elements move.
         */
        public PQMinIterator() {
            if (comparator == null) {
                clone = new PriorityQueueMin<T>(getSize());
            } else  {
                clone = new PriorityQueueMin<T>(getSize(), comparator);
            }
            for (int i = 1; i <= size; i++) {
                clone.add(data[i]);
            }
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public T next() {
            if (!hasNext()) {
//                Logger.writeLine("No such element");
                throw new NoSuchElementException();
            }
            return clone.extract();
        }

        /**
         * Returns true if the iteration has more elements.
         * @return true if the iteration has more elements, otherwise false
         */
        public boolean hasNext() {
            return !clone.isEmpty();
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * (Unsupported operation for priority queue.)
         */
        public void remove() {
            // no need to implement for PQ
        }
    }

    private int indexOf(Object o) {   // base function implements from java.util.PriorityQueue realisation
        if (o != null) {              // this function check is object in PriorityQueue or not and returns
            final Object[] es = data; // index of element/-1 if element not in
            for (int i = 0, n = size; i < n; i++)
                if (o.equals(es[i]))
                    return i;
        }
        return -1;
    }
    public boolean contains(Object o) { // base function implements from java.util.PriorityQueue realisation
        return indexOf(o) >= 0;         // this function return true if object in PriorityQueue or false if it not in
    }

    /**
     * The operator defining the priority queue type (min -> greater)
     * @return the comparison operator
     */
    public CompareUtils.Operators getPrimaryComparisonOperator() {
        return CompareUtils.Operators.GREATER;
    }

    public PriorityQueueMin(int capacity) {
        super(capacity);
    }

    public PriorityQueueMin() {
        super(1);
    }
    public PriorityQueueMin(int capacity, Comparator<T> comparator) {
        super(capacity, comparator);
    }

    public PriorityQueueMin(Comparator<T> comparator) {
        super(1, comparator);
    }

    public PriorityQueueMin(T[] values) {
        super(values);
    }

    @Override
    public Iterator<T> iterator() {
        return new PQMinIterator();
    }
}
