package com.rahmatullin.dev.algorithmRealisation;
/*
 * File: PathSearch.java
 * Description:
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import java.util.ArrayList;
import java.util.Collections;

public class PathSeacrh {
    public static ArrayList<Point> reconstructPath(Point current) {
        ArrayList<Point> path = new ArrayList<>();
        while (current != null) { // Пока текущий узел не равен null
            path.add(current); // Добавление узла в путь
            current.status = Point.Status.PATH; // Переход к родительскому узлу
            current = current.parent; // Переход к родительскому узлу
        }
        Collections.reverse(path); // Переворот пути для корректного порядка следования
        return path;
    }
}