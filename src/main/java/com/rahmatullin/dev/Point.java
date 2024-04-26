package com.rahmatullin.dev;
/*
 * File: Point.java
 * Description:
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import java.util.Objects;

public class Point {
    public enum Status {
        CLOSED("\uD83D\uDFEB"),
        OPENED("\uD83D\uDFE7"),
        BLOCK("\uD83D\uDFE5"),
        PATH("\uD83D\uDFE9");
        private String title;

        public String getTitle() {
            return title;
        }
        Status(String title) {
            this.title = title;
        }
    }

    public int x;
    public int y;
    public Point parent;
    public int gCost;
    public int hCost;
    public int fCost;
    Status status;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.status = Status.CLOSED;
    }
    public Point(int x, int y, Point parent, int gCost, int hCost) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", parent=" + parent +
                ", gCost=" + gCost +
                ", hCost=" + hCost +
                ", fCost=" + fCost +
                ", status=" + status +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
