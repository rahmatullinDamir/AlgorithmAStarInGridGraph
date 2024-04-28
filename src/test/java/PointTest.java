import com.rahmatullin.dev.algorithmRealisation.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);

        assertTrue(point1.equals(point2), "Points with same coordinates should be equal");
        assertFalse(point1.equals(point3), "Points with different coordinates should not be equal");
    }

    @Test
    void testHashCode() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);

        assertEquals(point1.hashCode(), point2.hashCode(), "Points with same coordinates should have same hash code");
        assertNotEquals(point1.hashCode(), point3.hashCode(), "Points with different coordinates should have different hash codes");
    }

    @Test
    void testStatusRepresentation() {
        Point point = new Point(1, 2);
        assertEquals("\uD83D\uDFEB", Point.Status.CLOSED.getTitle(), "CLOSED status should have correct emoji representation");
        assertEquals("\uD83D\uDFE7", Point.Status.OPENED.getTitle(), "OPENED status should have correct emoji representation");
        assertEquals("\uD83D\uDFE5", Point.Status.BLOCK.getTitle(), "BLOCK status should have correct emoji representation");
        assertEquals("\uD83D\uDFE9", Point.Status.PATH.getTitle(), "PATH status should have correct emoji representation");
    }
    @Test
    void testStatusChange() {
        Point point = new Point(1, 2);
        assertEquals(Point.Status.CLOSED, point.status, "New point should have CLOSED status");

        point.status = Point.Status.OPENED;
        assertEquals(Point.Status.OPENED, point.status, "Point status should change to OPENED");

        point.status = Point.Status.BLOCK;
        assertEquals(Point.Status.BLOCK, point.status, "Point status should change to BLOCK");

        point.status = Point.Status.PATH;
        assertEquals(Point.Status.PATH, point.status, "Point status should change to PATH");
    }

    @Test
    void testCostsCalculation() {
        Point parent = new Point(0, 0);
        Point point = new Point(1, 2, parent, 10, 20);

        assertEquals(10, point.gCost, "gCost should be 10");
        assertEquals(20, point.hCost, "hCost should be 20");
        assertEquals(30, point.fCost, "fCost should be gCost + hCost");
    }

    @Test
    void testParentAssignment() {
        Point parent = new Point(0, 0);
        Point point = new Point(1, 2, parent, 10, 20);

        assertEquals(parent, point.parent, "Parent should be correctly assigned");
    }

    @Test
    void testIsMethod() {
        assertEquals(0, Point.is(Point.Status.CLOSED), "CLOSED status should have ordinal 0");
        assertEquals(1, Point.is(Point.Status.OPENED), "OPENED status should have ordinal 1");
        assertEquals(2, Point.is(Point.Status.BLOCK), "BLOCK status should have ordinal 2");
        assertEquals(3, Point.is(Point.Status.PATH), "PATH status should have ordinal 3");
    }
}
