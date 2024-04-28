import com.rahmatullin.dev.algorithmRealisation.AStar;
import com.rahmatullin.dev.algorithmRealisation.Grid2D;
import com.rahmatullin.dev.algorithmRealisation.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {

    private AStar aStar;
    private Grid2D grid2D;
    private Point start;
    private Point end;
    private final ArrayList<Point> pointsArray = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // Initialize a simple 3x3 grid with a start and end point
        grid2D = new Grid2D(3, 3);
        grid2D.createObstaclesInGrid(1); // Create 1 obstacle
        var grid = grid2D.getGrid();
        for(int i = 0; i < grid2D.getGridWidth(); i++) {
            for(int j = 0; j < grid2D.getGridHeight(); j++) {
                if (grid[i][j].status == Point.Status.BLOCK) {
                    pointsArray.add(grid[i][j]);
                }
            }
        }
        start = grid2D.getGrid()[0][0];
        end = grid2D.getGrid()[2][2];
        aStar = new AStar(start, end, grid2D);
    }

    @Test
    void testAStarSearch() {
        ArrayList<Point> path = aStar.aStarSearch(false);
        assertNotNull(path, "Path should not be null");
        assertTrue(path.contains(start), "Path should contain the start point");
        assertTrue(path.contains(end), "Path should contain the end point");
    }

    @Test
    void testAStarSearchWithObstacle() {
        Point obstacle = pointsArray.getFirst();
        assertFalse(aStar.passable(obstacle), "Obstacle should not be passable");
    }

    @Test
    void testAStarSearchStartAndEndSame() {
        start = end; // Set start and end to be the same point
        aStar = new AStar(start, end, grid2D);

        ArrayList<Point> path = aStar.aStarSearch(false);
        assertNotNull(path, "Path should not be null");
        assertEquals(1, path.size(), "Path should contain only the start/end point");
        assertTrue(path.contains(start), "Path should contain the start/end point");
    }

    @Test
    void testAStarSearchDifferentGridSizesAndObstacles() {
        // Test with a 5x5 grid and different obstacle configurations
        grid2D = new Grid2D(5, 5);
        grid2D.createObstaclesInGrid(3); // Create 3 obstacles
        start = grid2D.getGrid()[0][0];
        end = grid2D.getGrid()[4][4];
        aStar = new AStar(start, end, grid2D);

        ArrayList<Point> path = aStar.aStarSearch(false);
        assertNotNull(path, "Path should not be null");
        assertTrue(path.contains(start), "Path should contain the start point");
        assertTrue(path.contains(end), "Path should contain the end point");
    }
}
