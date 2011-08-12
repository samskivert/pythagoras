//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Represents a vector in a plane.
 */
public class Vector extends AbstractVector
{
    /** The x-component of the vector. */
    public double x;

    /** The y-component of the vector. */
    public double y;

    /** Creates a vector with the specified x and y components. */
    public Vector (double x, double y) {
        set(x, y);
    }

    /** Creates a vector equal to {@code other}. */
    public Vector (IVector other) {
        set(other);
    }

    /** Creates a vector with zero x and y components. */
    public Vector () {
    }

    /** Negates this vector in-place.
     * @return a reference to this vector, for chaining. */
    public Vector negateLocal () {
        return negate(this);
    }

    /** Normalizes this vector in-place.
     * @return a reference to this vector, for chaining. */
    public Vector normalizeLocal () {
        return normalize(this);
    }

    /** Multiplies this vector in-place by a scalar.
     * @return a reference to this vector, for chaining. */
    public Vector multLocal (double v) {
        return mult(v, this);
    }

    /** Multiplies this vector in-place by another.
     * @return a reference to this vector, for chaining. */
    public Vector multLocal (IVector other) {
        return mult(other, this);
    }

    /** Adds a vector in-place to this one.
     * @return a reference to this vector, for chaining. */
    public Vector addLocal (IVector other) {
        return add(other, this);
    }

    /** Subtracts a vector in-place from this one.
     * @return a reference to this vector, for chaining. */
    public Vector subtractLocal (IVector other) {
        return subtract(other, this);
    }

    /** Adds a vector in-place to this one.
     * @return a reference to this vector, for chaining. */
    public Vector addLocal (double x, double y) {
        return add(x, y, this);
    }

    /** Adds a scaled vector in-place to this one.
     * @return a reference to this vector, for chaining. */
    public Vector addScaledLocal (IVector other, double v) {
        return addScaled(other, v, this);
    }

    /** Rotates this vector in-place by the specified angle.
     * @return a reference to this vector, for chaining. */
    public Vector rotateLocal (double angle) {
        return rotate(angle, this);
    }

    /** Linearly interpolates between this and {@code other} in-place by the supplied amount.
     * @return a reference to this vector, for chaining. */
    public Vector lerpLocal (IVector other, double t) {
        return lerp(other, t, this);
    }

    /** Copies the elements of another vector.
     * @return a reference to this vector, for chaining. */
    public Vector set (IVector other) {
        return set(other.x(), other.y());
    }

    /** Copies the elements of an array.
     * @return a reference to this vector, for chaining. */
    public Vector set (double[] values) {
        return set(values[0], values[1]);
    }

    /** Sets all of the elements of the vector.
     * @return a reference to this vector, for chaining. */
    public Vector set (double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override // from AbstractVector
    public double x () {
        return x;
    }

    @Override // from AbstractVector
    public double y () {
        return y;
    }
}
