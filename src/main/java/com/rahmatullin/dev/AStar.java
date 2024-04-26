package com.rahmatullin.dev;
/*
 * File: AStar.java
 * Description: Created A* algorithm to find the shortest path in GridGraph
 * Authors:
 *   - Damir Rakhmatullin
 * Copyright: (c) 2024 Damir Rakhmatullin
 * License: This file is licensed under the MIT License.
 */

import com.rahmatullin.dev.priorityQueue.PriorityQueueMin;

import java.util.*;
import java.util.stream.Collectors;

public class AStar {

    private PriorityQueueMin<Point> openSet; // Приоритетная очередь для узлов, ожидающих оценки
    private HashSet<Point> closedSet; // Множество для хранения уже оцененных узлов
    private Point start, end; // Начальный и конечный узлы
    private Grid2D grid2D;
    private Point[][] matrix;


    public AStar(Point start, Point end, Grid2D grid2D) {
        this.matrix = grid2D.getGrid();
        this.grid2D = grid2D;
        this.start = this.matrix[start.y][start.x];
        this.end = this.matrix[end.y][end.x];
        this.openSet = new PriorityQueueMin<>(Comparator.comparingDouble(point -> point.fCost)); // Сортировка узлов по общей стоимости
        this.closedSet = new HashSet<>();
    }
    public ArrayList<Point> aStarSearch() {
        start.status = Point.Status.OPENED;
        openSet.add(start);
        while (!openSet.isEmpty()) { // Пока в открытом множестве есть узлы
            Point current = openSet.extract(); // Выбор узла с наименьшей общей стоимостью
            current.status = Point.Status.OPENED;
            closedSet.add(current); // Перемещение узла в закрытое множество

            if (current.equals(end)) { // Если текущий узел является целевым
                return PathSeacrh.reconstructPath(current); // Восстановление пути и возврат его
            }

            for (Point neighbor : neighbors(current)) { // Рассмотрение всех соседей текущего узла
                if (closedSet.contains(neighbor)) continue; // Пропуск уже оцененных узлов

                int tentativeGCost = current.gCost + heuristic(current, neighbor); // Вычисление предварительной фактической стоимости
                if ((!(openSet.contains(neighbor))) || tentativeGCost < neighbor.gCost) { // Если узел не в открытом множестве или новая стоимость меньше
                    neighbor.parent = current; // Установка родителя для восстановления пути
                    neighbor.gCost = tentativeGCost; // Обновление фактической стоимости
                    neighbor.hCost = heuristic(neighbor, end); // Вычисление эвристической стоимости
                    neighbor.fCost = neighbor.gCost + neighbor.hCost; // Обновление общей стоимости

                    if (!(openSet.contains(neighbor))) { // Если узел еще не в открытом множестве
                        neighbor.status = Point.Status.OPENED;
                        openSet.add(neighbor); // Добавление узла в открытое множество
                    }
                }
            }
        }

        return null;

    }

//    public boolean inBounds(Point dot) {
//        return 0 <= dot.x && dot.x < grid2D.getGridWidth() && 0 <= dot.y && dot.y < grid2D.getGridHeight();
//    }

    public boolean passable(Point dot) {
        return !grid2D.getGridObstacles().contains(dot);
    }
    public int heuristic(Point first, Point second){
        return (int) (10 * Math.sqrt((second.x - first.x)*(second.x - first.x) + (second.y - first.y)*(second.y - first.y)));
    }
    public ArrayList<Point> neighbors(Point current) {
        ArrayList<Point> results = new ArrayList<>();

// Проверяем, не находится ли узел на краю сетки
        if (current.x + 1 < matrix.length) {
            results.add(matrix[current.x + 1][current.y]);
        }
        if (current.x - 1 >= 0) {
            results.add(matrix[current.x - 1][current.y]);
        }
        if (current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x][current.y + 1]);
        }
        if (current.y - 1 >= 0) {
            results.add(matrix[current.x][current.y - 1]);
        }
        if (current.x + 1 < matrix.length && current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x + 1][current.y + 1]);
        }
        if (current.x - 1 >= 0 && current.y - 1 >= 0) {
            results.add(matrix[current.x - 1][current.y - 1]);
        }
        if (current.x + 1 < matrix.length && current.y - 1 >= 0) {
            results.add(matrix[current.x + 1][current.y - 1]);
        }
        if (current.x - 1 >= 0 && current.y + 1 < matrix[0].length) {
            results.add(matrix[current.x - 1][current.y + 1]);
        }


        // Фильтрация соседей, которые находятся в пределах сетки и являются проходимыми
        results = results.stream()
                .filter(this::passable)
                .peek(val -> val.status = Point.Status.OPENED)
                .collect(Collectors.toCollection(ArrayList::new));

        return results;

    }
}

