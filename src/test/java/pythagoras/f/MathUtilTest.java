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
    public static final float PI = FloatMath.PI;
    public static final float PI2 = FloatMath.PI/2;
    public static final float PI4 = FloatMath.PI/4;
    public static final float PI8 = FloatMath.PI/8;

    @Test public void testLerpa() {
        assertEquals(MathUtil.lerpa(PI4, -PI4, 0.25f), PI8, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(PI4, -PI4, 0.75f), -PI8, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(-PI4, PI4, 0.25f), -PI8, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(-PI4, PI4, 0.75f), PI8, MathUtil.EPSILON);
        // make sure we lerp the shortest route around the circle
        assertEquals(MathUtil.lerpa(3*PI4, PI4, 0.5f), PI2, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(PI4, 3*PI4, 0.5f), PI2, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(-3*PI4, -PI4, 0.5f), -PI2, MathUtil.EPSILON);
        assertEquals(MathUtil.lerpa(-PI4, -3*PI4, 0.5f), -PI2, MathUtil.EPSILON);

        assertEquals(MathUtil.lerpa(3*PI4, -3*PI4, 0.5f), -PI, MathUtil.EPSILON);
    }

    @Test public void testMirrorAngleOrigin() {
        assertEquals(-PI2, MathUtil.mirrorAngleOrigin(PI2), MathUtil.EPSILON);
        assertEquals(PI2, MathUtil.mirrorAngleOrigin(-PI2), MathUtil.EPSILON);
    }
}
