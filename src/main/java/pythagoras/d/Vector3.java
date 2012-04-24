//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.io.Serializable;

/**
 * A three element vector.
 */
public class Vector3 extends AbstractVector3 implements Serializable
{
    /** A unit vector in the X+ direction. */
    public static final IVector3 UNIT_X = new Vector3(1f, 0f, 0f);

    /** A unit vector in the Y+ direction. */
    public static final IVector3 UNIT_Y = new Vector3(0f, 1f, 0f);

    /** A unit vector in the Z+ direction. */
    public static final IVector3 UNIT_Z = new Vector3(0f, 0f, 1f);

    /** A vector containing unity for all components. */
    public static final IVector3 UNIT_XYZ = new Vector3(1f, 1f, 1f);

    /** A normalized version of UNIT_XYZ. */
    public static final IVector3 NORMAL_XYZ = UNIT_XYZ.normalize();

    /** The zero vector. */
    public static final IVector3 ZERO = new Vector3(0f, 0f, 0f);

    /** A vector containing the minimum doubleing point value for all components
     * (note: the components are -{@link Float#MAX_VALUE}, not {@link Float#MIN_VALUE}). */
    public static final IVector3 MIN_VALUE =
        new Vector3(-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE);

    /** A vector containing the maximum doubleing point value for all components. */
    public static final IVector3 MAX_VALUE =
        new Vector3(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);

    /** The components of the vector. */
    public double x, y, z;

    /**
     * Creates a vector from three components.
     */
    public Vector3 (double x, double y, double z) {
        set(x, y, z);
    }

    /**
     * Creates a vector from an array of values.
     */
    public Vector3 (double[] values) {
        set(values);
    }

    /**
     * Copy constructor.
     */
    public Vector3 (IVector3 other) {
        set(other);
    }

    /**
     * Creates a zero vector.
     */
    public Vector3 () {
    }

    /**
     * Computes the cross product of this and the specified other vector, storing the result
     * in this vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 crossLocal (IVector3 other) {
        return cross(other, this);
    }

    /**
     * Negates this vector in-place.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 negateLocal () {
        return negate(this);
    }

    /**
     * Normalizes this vector in-place.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 normalizeLocal () {
        return normalize(this);
    }

    /**
     * Multiplies this vector in-place by a scalar.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 multLocal (double v) {
        return mult(v, this);
    }

    /**
     * Multiplies this vector in-place by another.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 multLocal (IVector3 other) {
        return mult(other, this);
    }

    /**
     * Adds a vector in-place to this one.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 addLocal (IVector3 other) {
        return add(other, this);
    }

    /**
     * Subtracts a vector in-place from this one.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 subtractLocal (IVector3 other) {
        return subtract(other, this);
    }

    /**
     * Adds a vector in-place to this one.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 addLocal (double x, double y, double z) {
        return add(x, y, z, this);
    }

    /**
     * Adds a scaled vector in-place to this one.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 addScaledLocal (IVector3 other, double v) {
        return addScaled(other, v, this);
    }

    /**
     * Linearly interpolates between this and the specified other vector in-place by the supplied
     * amount.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 lerpLocal (IVector3 other, double t) {
        return lerp(other, t, this);
    }
    /**
     * Copies the elements of another vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 set (IVector3 other) {
        return set(other.x(), other.y(), other.z());
    }

    /**
     * Copies the elements of an array.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 set (double[] values) {
        return set(values[0], values[1], values[2]);
    }

    /**
     * Sets all of the elements of the vector.
     *
     * @return a reference to this vector, for chaining.
     */
    public Vector3 set (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override // from AbstractVector3
    public double x () {
        return x;
    }

    @Override // from AbstractVector3
    public double y () {
        return y;
    }

    @Override // from AbstractVector3
    public double z () {
        return z;
    }
}
