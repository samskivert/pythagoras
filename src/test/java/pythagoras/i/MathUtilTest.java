//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests parts of the {@link MathUtil} class.
 */
public class MathUtilTest
{
    @Test public void testFloorDiv () {
        int[] nums = { -15, -10, -8, -2, 0, 2, 8, 10, 15 };
        int[] vals = {  -2,  -1, -1, -1, 0, 0, 0,  1,  1 };
        for (int ii = 0; ii < nums.length; ii++) {
            assertEquals(vals[ii], MathUtil.floorDiv(nums[ii], 10));
        }
    }
}
