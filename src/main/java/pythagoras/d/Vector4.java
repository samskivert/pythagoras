//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.io.Serializable;
import java.nio.DoubleBuffer;

import pythagoras.util.Platform;

/**
 * A four element vector.
 */
public class Vector4 implements IVector4, Serializable
{
    /** The components of the vector. */
    public double x, y, z, w;

    /**
     * Creates a vector from four components.
     */
    public Vector4 (double x, double y, double z, double w)
    {
        set(x, y, z, w);
    }

    /**
     * Creates a vector from four components.
     */
    public Vector4 (double[] values) {
        set(values);
    }

    /**
     * Creates a vector from a double buffer.
     */
    public Vector4 (DoubleBuffer buf) {
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
    public Vector4 set (double[] values) {
        return set(values[0], values[1], values[2], values[3]);
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (DoubleBuffer buf) {
        return set(buf.get(), buf.get(), buf.get(), buf.get());
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector4 set (double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override // from IVector4
    public double x () {
        return x;
    }

    @Override // from IVector4
    public double y () {
        return y;
    }

    @Override // from IVector4
    public double z () {
        return z;
    }

    @Override // from IVector4
    public double w () {
        return w;
    }

    @Override // from IVector4
    public DoubleBuffer get (DoubleBuffer buf) {
        return buf.put(x).put(y).put(z).put(w);
    }

    @Override // from IVector4
    public boolean epsilonEquals (IVector4 other, double epsilon) {
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
