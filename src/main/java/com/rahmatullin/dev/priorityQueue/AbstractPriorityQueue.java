/*
 * File: AbstractPriorityQueue.java
 * Description: An abstract class representing a priority queue.
 * Authors:
 *   - Ilya Tsivilskiy
 * Copyright: (c) 2024 Ilya Tsivilskiy
 * License: This file is licensed under the MIT License.
 */
package com.rahmatullin.dev.priorityQueue;



import com.rahmatullin.dev.utils.CollectionUtils;
import com.rahmatullin.dev.utils.CompareUtils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract class representing a priority queue.
 * This class provides common functionality for both max-PQ and min-PQ implementations,
 * depending on the operator specified.
 * @param <T> the type of elements stored in the priority queue
 */
public abstract class AbstractPriorityQueue<T> {
    protected T[] data;   // array containing elements, indexed from 1 to size
    protected int size;   // number of elements currently in the priority queue
    protected Comparator<T> comparator;  // comparator used for ordering elements (optional)

    /**
     * The operator defining the priority queue type (max -> less or min -> greater)
     * @return the comparison operator
     */
    public CompareUtils.Operators getPrimaryComparisonOperator() {
        return CompareUtils.Operators.LESS;
    }

    /**
     * Initializes an empty priority queue with the specified initial capacity.
     * @param capacity the initial capacity of this priority queue
     */
    public AbstractPriorityQueue(int capacity) {
        data = (T[]) new Object[capacity + 1];
        size = 0;
    }

    /**
     * Init an empty priority queue.
     */
    public AbstractPriorityQueue() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the specified initial capacity
     * and comparator.
     * @param capacity   the initial capacity of this priority queue
     * @param comparator the comparator defining the order of elements in the priority queue
     */

    public AbstractPriorityQueue(int capacity, Comparator<T> comparator) {
        this.comparator = comparator;
        data = (T[]) new Object[capacity + 1];
        size = 0;
    }

    /**
     * Initializes an empty priority queue with the specified comparator.
     * @param comparator the comparator defining the order of elements
     */
    public AbstractPriorityQueue(Comparator<T> comparator) {
        this(1, comparator);
    }

    /**
     * Initializes a priority queue using the provided array of items.
     * Constructing the queue takes time proportional to the number of elements,
     * utilizing a sink-based heap construction approach.
     * @param values the array of items to initialize the priority queue
     */
    public AbstractPriorityQueue(T[] values) {
        size = values.length;
        data = (T[]) new Object[values.length + 1];
        System.arraycopy(values, 0, data, 1, size);
        // sort the elements by their values to maintain the heap order
        for (int j = size / 2; j >= 1; j--) {
            sink(j);
        }
    }

    /**
     * Add an element to the collection
     * @param element An element to add to
     */

    public void add(T element) {
        // double size of array if necessary
        if (size == data.length - 1) {
            resize(2 * data.length);
        }

        // add an element, and pop it up
        // to maintain the heap order
        data[++size] = element;
        popup(size);
    }

    /**
     * Retrieve an element and remove it from the collection
     * @return Retrieved largest/smallest element
     */

    public T extract() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ underflow");
        }
        T extremal = get(); // max or min
        CollectionUtils.swap(data, 1, size--);
        sink(1);
        data[size + 1] = null; // force the garbage collection
        if ((size > 0) && (size == (data.length - 1) / 4)) {
            resize(data.length / 2);
        }
        return extremal;
    }

    /**
     * Retrieve/get an element from the collection without removing it
     * @return Retrieved largest/smallest element
     */
    public T get() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ underflow");
        }
        return data[1];
    }

    /**
     * Check whether the collection has elements
     * @return Returns true if the collection is empty, false otherwise
     */

    public boolean isEmpty() {
        return size == 0;
    }


    public int getSize() {
        return size;
    }

    /**
     * Resizes the data array to have the given capacity.
     * @param capacity the new capacity of the array
     */
    protected void resize(int capacity) {
        // create a new array with the specified capacity
        T[] temp = (T[]) new Object[capacity];
        // copy elements from the current array to the new array
        if (size >= 0) System.arraycopy(data, 1, temp, 1, size);
        // update the reference to the array to point to the new array
        data = temp;
    }

    /**
     * Returns the index of the parent node for the element at index i.
     * @param i the index of the element
     * @return the index of the parent node
     */
    protected int getParent(int i) {
        return i / 2;
    }

    /**
     * Returns the index of the left child node for the element at index i.
     * @param i the index of the element
     * @return the index of the left child node
     */
    protected int getLeftChild(int i) {
        return 2 * i;
    }

    /**
     * Returns the index of the right child node for the element at index i.
     * @param i the index of the element
     * @return the index of the right child node
     */
    protected int getRightChild(int i) {
        return getLeftChild(i) + 1;
    }

    /**
     * Compares two elements with respect to the given comparator or naturally.
     * @param i an index of the first element
     * @param j an index of the second element
     * @return true if the first element is considered greater than the second element
     *         according to the specified comparator or natural order, false otherwise
     */
    protected boolean compare(int i, int j) {
        return CompareUtils.getCompareResult(
                data[i],
                data[j],
                getPrimaryComparisonOperator(), comparator);
    }

    /**
     * Moves an element upwards in the heap
     * to restore the heap order property.
     * @param i the index of the element to be moved
     */
    protected void popup(int i) {
        while ((i > 1) && compare(getParent(i), i)) {
            // swap the element with its parent
            // if it violates the heap order
            CollectionUtils.swap(data, getParent(i), i); // swaps the element with its parent
            i = getParent(i); // updates the index to the parent's index
        }
    }

    /**
     * Moves an element downwards in the heap
     * to restore the heap order property.
     * @param i the index of the element to be moved
     */
    protected void sink(int i) {
        while (getLeftChild(i) <= size) {
            final int left = getLeftChild(i); // index of the left child
            final int right = getRightChild(i);
            int largerChild = left;
            // choose the larger child if it exists
            if ((largerChild < size) && compare(largerChild, right)) {
                largerChild++;
            }
            // breaks if the heap order is satisfied
            if (!compare(i, largerChild)) {
                break;
            }
            CollectionUtils.swap(data, i, largerChild); // swaps the element with its larger child
            i = largerChild; // updates the index to the position of the swapped child
        }
    }

    public Iterator<T> iterator() {
        return null;
    }

    /**
     * Travers the binary heap and saves it layer-by-layer to string object
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = data.length - 1; // Adjusting for 1-based indexing
        int depth = (int) (Math.log(size) / Math.log(2)) + 1;
        int index = 1; // Starting from index 1

        sb.append("\n");
        for (int i = 0; i < depth; i++) {
            // incomplete levels handling
            int levelNodes = Math.min((int) Math.pow(2, i), size - index + 1);
            int spaces = (int) Math.pow(2, depth - i - 1) - 1;
            appendSpaces(sb, spaces);

            for (int j = 0; j < levelNodes; j++) {
                sb.append(data[index++]);
                appendSpaces(sb, spaces * 2 + 1);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void appendSpaces(StringBuilder sb, int count) {
        sb.append("\t".repeat(Math.max(0, count)));
    }
}
