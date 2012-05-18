//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.nio.FloatBuffer;

/**
 * Provides read-only access to a {@link Vector4}.
 */
public interface IVector4
{
    /** Returns the x-component of this vector. */
    float x ();

    /** Returns the y-component of this vector. */
    float y ();

    /** Returns the z-component of this vector. */
    float z ();

    /** Returns the w-component of this vector. */
    float w ();

    /**
     * Populates the supplied buffer with the contents of this vector.
     *
     * @return a reference to the buffer, for chaining.
     */
    FloatBuffer get (FloatBuffer buf);

    /**
     * Compares this vector to another with the provided epsilon.
     */
    boolean epsilonEquals (IVector4 other, float epsilon);

    /**
     * Multiplies this vector by a matrix (V * M).
     *
     * @return a new vector containing the result.
     */
    IVector4 mult (IMatrix4 matrix);

    /**
     * Multiplies this vector by a matrix (V * M) and stores the result in the object provided.
     *
     * @return a reference to the result vector, for chaining.
     */
    IVector4 mult (IMatrix4 matrix, Vector4 result);
}
