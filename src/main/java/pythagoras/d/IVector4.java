//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.nio.DoubleBuffer;

/**
 * Provides read-only access to a {@link Vector4}.
 */
public interface IVector4
{
    /** Returns the x-component of this vector. */
    double x ();

    /** Returns the y-component of this vector. */
    double y ();

    /** Returns the z-component of this vector. */
    double z ();

    /** Returns the w-component of this vector. */
    double w ();

    /**
     * Populates the supplied buffer with the contents of this vector.
     *
     * @return a reference to the buffer, for chaining.
     */
    DoubleBuffer get (DoubleBuffer buf);

    /**
     * Compares this vector to another with the provided epsilon.
     */
    boolean epsilonEquals (IVector4 other, double epsilon);
}
