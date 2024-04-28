import com.rahmatullin.dev.algorithmRealisation.Grid2D;
import com.rahmatullin.dev.algorithmRealisation.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

    class Grid2DTest {

        private Grid2D grid2D;

        @BeforeEach
        void setUp() {
            grid2D = new Grid2D(3, 3); // Create a 3x3 grid
        }

        @Test
        void testGridDimensions() {
            assertEquals(3, grid2D.getGridWidth(), "Grid width should be 3");
            assertEquals(3, grid2D.getGridHeight(), "Grid height should be 3");
        }

        @Test
        void testCreateObstaclesInGrid() {
            grid2D.createObstaclesInGrid(2); // Create 2 obstacles
            ArrayList<Point> obstacles = grid2D.getGridObstacles();
            assertEquals(2, ((ArrayList<?>) obstacles).size(), "Should have 2 obstacles");
            // Add more assertions to check the positions of the obstacles
        }

        @Test
        void testGridInitialization() {
            Point[][] grid = grid2D.getGrid();
            assertNotNull(grid, "Grid should not be null");
            assertEquals(3, grid.length, "Grid should have 3 rows");
            assertEquals(3, grid[0].length, "Grid should have 3 columns");
            // Add more assertions to check the initialization of each cell
        }

        @Test
        void testToString() {
            String gridString = grid2D.toString();
            assertNotNull(gridString, "Grid string representation should not be null");
            // Add more assertions based on expected string representation
        }
    }
