import com.rahmatullin.dev.algorithmRealisation.Point;
import com.rahmatullin.dev.algorithmRealisation.PathSeacrh;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathSearchTest {

    @Test
    void testReconstructPath() {
        // Create a simple path from start to end
        Point start = new Point(0, 0);
        Point middle = new Point(1, 1, start, 1, 1);
        Point end = new Point(2, 2, middle, 2, 2);

        // Reconstruct the path
        ArrayList<Point> path = PathSeacrh.reconstructPath(end);

        // Check that the path is correctly reconstructed
        assertEquals(3, path.size(), "Path should have 3 points");
        assertEquals(start, path.get(0), "First point in the path should be the start point");
        assertEquals(middle, path.get(1), "Second point in the path should be the middle point");
        assertEquals(end, path.get(2), "Last point in the path should be the end point");

        // Check that the status of each point in the path is PATH
        for (Point point : path) {
            assertEquals(Point.Status.PATH, point.status, "Each point in the path should have status PATH");
        }
    }

    @Test
    void testReconstructPathWithNoParent() {
        // Create a point without a parent
        Point point = new Point(0, 0);

        // Reconstruct the path
        ArrayList<Point> path = PathSeacrh.reconstructPath(point);

        // Check that the path contains only the point itself
        assertEquals(1, path.size(), "Path should contain only the point itself");
        assertEquals(point, path.get(0), "Path should contain the point itself");
    }
}
