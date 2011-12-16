//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import org.junit.*;
import static org.junit.Assert.*;

public class RectangleTest
{
    @Test public void testPointRectDistance () {
        testPointRectDistance(0, new Rectangle(0, 0, 10, 10), new Point(0, 0)); // edge
        testPointRectDistance(0, new Rectangle(0, 0, 10, 10), new Point(5, 5)); // interior
        testPointRectDistance(5, new Rectangle(0, 0, 10, 10), new Point(5, 15)); // exterior
    }

    protected void testPointRectDistance (float expected, IRectangle r, Point p) {
        assertEquals(expected, Rectangles.pointRectDistance(r, p), MathUtil.EPSILON);
    }

}
