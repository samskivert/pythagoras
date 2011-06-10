//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * An interface implemented by {@link Shape} classes whose geometry is defined by a rectangular
 * frame. The framing rectangle <em>defines</em> the geometry, but may in some cases differ from
 * the <em>bounding</em> rectangle of the shape.
 */
public interface IRectangularShape extends IShape
{
    /** Returns the x-coordinate of the upper-left corner of the framing rectangle. */
    double getX ();

    /** Returns the y-coordinate of the upper-left corner of the framing rectangle. */
    double getY ();

    /** Returns the width of the framing rectangle. */
    double getWidth ();

    /** Returns the height of the framing rectangle. */
    double getHeight ();

    /** Returns the minimum x-coordinate of the framing rectangle. */
    double getMinX ();

    /** Returns the minimum y-coordinate of the framing rectangle. */
    double getMinY ();

    /** Returns the maximum x-coordinate of the framing rectangle. */
    double getMaxX ();

    /** Returns the maximum y-coordinate of the framing rectangle. */
    double getMaxY ();

    /** Returns the x-coordinate of the center of the framing rectangle. */
    double getCenterX ();

    /** Returns the y-coordinate of the center of the framing rectangle. */
    double getCenterY ();

    /** Returns a copy of this shape's framing rectangle. */
    Rectangle getFrame ();

    /** Initializes the supplied rectangle with this shape's framing rectangle.
     * @return the supplied rectangle. */
    Rectangle getFrame (Rectangle target);
}
