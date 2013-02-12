//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests parts of the {@link MathUtil} class.
 */
public class MathUtilTest
{
    @Test public void testMirrorAngle() {
        assertEquals(-MathUtil.HALF_PI, MathUtil.mirrorAngle(MathUtil.HALF_PI), MathUtil.EPSILON);
        assertEquals(MathUtil.HALF_PI, MathUtil.mirrorAngle(-MathUtil.HALF_PI), MathUtil.EPSILON);
    }
}
