//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.io.Serializable;
import java.nio.FloatBuffer;

import pythagoras.util.Platform;

/**
 * A four element vector.
 */
public class Vector4 implements IVector4, Serializable
{
    /** The components of the vector. */
    public float x, y, z, w;

    /**
     * Creates a vector from four components.
     */
    public Vector4 (float x, float y, float z, float w)
    {
        set(x, y, z, w);
    }

    /**
     * Creates a vector from four components.
     */
    public Vector4 (float[] values) {
        set(values);
    }

    /**
     * Creates a vector from a float buffer.
     */
    public Vector4 (FloatBuffer buf) {
        set(buf);
    }

    /**
     * Copy constructor.
     */
    public Vector4 (IVector4 other) {
        set(other);
    }

    /**
     * Creates a zero vector.
     */
    public Vector4 () {
    }

    /**
     * Copies the elements of another vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (IVector4 other) {
        return set(other.x(), other.y(), other.z(), other.w());
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (float[] values) {
        return set(values[0], values[1], values[2], values[3]);
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (FloatBuffer buf) {
        return set(buf.get(), buf.get(), buf.get(), buf.get());
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override // from IVector4
    public float x () {
        return x;
    }

    @Override // from IVector4
    public float y () {
        return y;
    }

    @Override // from IVector4
    public float z () {
        return z;
    }

    @Override // from IVector4
    public float w () {
        return w;
    }

    @Override // from IVector4
    public FloatBuffer get (FloatBuffer buf) {
        return buf.put(x).put(y).put(z).put(w);
    }

    @Override // from IVector4
    public boolean epsilonEquals (IVector4 other, float epsilon) {
        return (Math.abs(x - other.x()) < epsilon &&
                Math.abs(y - other.y()) < epsilon &&
                Math.abs(z - other.z()) < epsilon &&
                Math.abs(w - other.w()) < epsilon);
    }

    @Override
    public String toString () {
        return "[" + x + ", " + y + ", " + z + ", " + w + "]";
    }

    @Override
    public int hashCode () {
        return Platform.hashCode(x) ^ Platform.hashCode(y) ^ Platform.hashCode(z) ^
            Platform.hashCode(w);
    }

    @Override
    public boolean equals (Object other) {
        if (!(other instanceof Vector4)) {
            return false;
        }
        Vector4 ovec = (Vector4)other;
        return (x == ovec.x && y == ovec.y && z == ovec.z && w == ovec.w);
    }
}
