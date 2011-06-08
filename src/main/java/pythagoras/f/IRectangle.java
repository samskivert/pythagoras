//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Provides read-only access to a {@link Rectangle}.
 */
public interface IRectangle extends Shape, Cloneable
{
    /** The bitmask that indicates that a point lies to the left of this rectangle.
     * See {@link #outcode}. */
    int OUT_LEFT = 1;

    /** The bitmask that indicates that a point lies above this rectangle. See {@link #outcode}. */
    int OUT_TOP = 2;

    /** The bitmask that indicates that a point lies to the right of this rectangle.
     * See {@link #outcode}. */
    int OUT_RIGHT = 4;

    /** The bitmask that indicates that a point lies below this rectangle. See {@link #outcode}. */
    int OUT_BOTTOM = 8;

    /**
     * Returns the x-coordinate of the rectangle's upper left corner.
     */
    float getX ();

    /**
     * Returns the y-coordinate of the rectangle's upper left corner.
     */
    float getY ();

    /**
     * Returns the width of the rectangle.
     */
    float getWidth ();

    /**
     * Returns the height of the rectangle.
     */
    float getHeight ();

    /**
     * Returns true if this rectangle does not have > 0 width and height.
     */
    boolean isEmpty ();

    /**
     * Returns this rectangle's upper-left corner. Note that this constructs a new instance,
     * because a {@link Rectangle} is not an {@link IPoint} and cannot efficiently masquerade as
     * one.
     */
    Point getLocation ();

    /**
     * Returns the size of this rectangle as a newly constructed dimension. Note that this
     * constructs a new instance, because a {@link Rectangle} is not an {@link IDimension} and
     * cannot efficiently masquerade as one.
     */
    Dimension getSize ();

    /**
     * Returns the intersection of the specified rectangle and this rectangle (i.e. the largest
     * rectangle contained in both this and the specified rectangle).
     */
    Rectangle intersection (float rx, float ry, float rw, float rh);

    /**
     * Returns the intersection of the supplied rectangle and this rectangle (i.e. the largest
     * rectangle contained in both this and the supplied rectangle).
     */
    Rectangle intersection (IRectangle r);

    /**
     * Returns the union of the supplied rectangle and this rectangle (i.e. the smallest rectangle
     * that contains both this and the supplied rectangle).
     */
    Rectangle union (IRectangle r);

    /**
     * Returns a set of flags indicating where the specified point lies in relation to the bounds
     * of this rectangle. See {@link #OUT_LEFT}, etc.
     */
    int outcode (double px, double py);

    /**
     * Returns a set of flags indicating where the supplied point lies in relation to the bounds of
     * this rectangle. See {@link #OUT_LEFT}, etc.
     */
    int outcode (IPoint point);

    /**
     * Clones this rectangle.
     */
    Rectangle clone ();
}
