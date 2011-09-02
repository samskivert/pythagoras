//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests aspects of the {@link Vector} class.
 */
public class VectorTest
{
    @Test
    public void testFromPolar () {
        for (float length = 0.05f; length < 5; length += 0.05f) {
            // stay away from -PI and PI because the signs might be flipped
            for (float theta = -FloatMath.PI + 0.05f; theta < FloatMath.PI; theta += 0.05f) {
                Vector v = Vectors.fromPolar(length, theta);
                assertEquals(length, v.length(), MathUtil.EPSILON);
                assertEquals(theta, v.angle(), MathUtil.EPSILON);
            }
        }
    }

    @Test
    public void testSetLength () {
        for (float length = 0.05f; length < 5; length += 0.05f) {
            // stay away from -PI and PI because the signs might be flipped
            for (float theta = -FloatMath.PI + 0.05f; theta < FloatMath.PI; theta += 0.05f) {
                Vector v = Vectors.fromPolar(length, theta);
                v.setLength(10);
                // make sure setting length actually sets the length
                assertEquals(10, v.length(), MathUtil.EPSILON);
                // make sure setting length doesn't bork angle
                assertEquals(theta, v.angle(), MathUtil.EPSILON);
            }
        }
    }

    @Test
    public void testSetAngle () {
        for (float length = 0.05f; length < 5; length += 0.05f) {
            // stay away from -PI and PI because the signs might be flipped
            for (float theta = 0.05f; theta < FloatMath.PI; theta += 0.05f) {
                Vector v = Vectors.fromPolar(length, theta);
                v.setAngle(FloatMath.PI - theta);
                // make sure setting angle actually sets the angle
                assertEquals(FloatMath.PI - theta, v.angle(), MathUtil.EPSILON);
                // make sure setting length doesn't bork length
                assertEquals(length, v.length(), MathUtil.EPSILON);
            }
        }
    }
}
