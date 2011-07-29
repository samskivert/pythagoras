//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests parts of the {@link FloatMath} class.
 */
public class FloatMathTest
{
    @Test public void testToString () {
        assertEquals("+1.0", FloatMath.toString(1f));
        assertEquals("-1.0", FloatMath.toString(-1f));
        assertEquals("+1.1", FloatMath.toString(1.1f));
        assertEquals("-1.1", FloatMath.toString(-1.1f));
        assertEquals("+3.141", FloatMath.toString(FloatMath.PI));
        assertEquals("-3.141", FloatMath.toString(-FloatMath.PI));

        FloatMath.setToStringDecimalPlaces(5);
        assertEquals("+1.0", FloatMath.toString(1f));
        assertEquals("-1.0", FloatMath.toString(-1f));
        assertEquals("+1.1", FloatMath.toString(1.1f));
        assertEquals("-1.1", FloatMath.toString(-1.1f));
        assertEquals("+3.14159", FloatMath.toString(FloatMath.PI));
        assertEquals("-3.14159", FloatMath.toString(-FloatMath.PI));
    }
}
