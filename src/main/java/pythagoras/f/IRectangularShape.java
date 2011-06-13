//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * An interface implemented by {@link IShape} classes whose geometry is defined by a rectangular
 * frame. The framing rectangle <em>defines</em> the geometry, but may in some cases differ from
 * the <em>bounding</em> rectangle of the shape.
 */
public interface IRectangularShape extends IShape
{
    /** Returns the x-coordinate of the upper-left corner of the framing rectangle. */
    float getX ();

    /** Returns the y-coordinate of the upper-left corner of the framing rectangle. */
    float getY ();

    /** Returns the width of the framing rectangle. */
    float getWidth ();

    /** Returns the height of the framing rectangle. */
    float getHeight ();

    /** Returns the minimum x-coordinate of the framing rectangle. */
    float getMinX ();

    /** Returns the minimum y-coordinate of the framing rectangle. */
    float getMinY ();

    /** Returns the maximum x-coordinate of the framing rectangle. */
    float getMaxX ();

    /** Returns the maximum y-coordinate of the framing rectangle. */
    float getMaxY ();

    /** Returns the x-coordinate of the center of the framing rectangle. */
    float getCenterX ();

    /** Returns the y-coordinate of the center of the framing rectangle. */
    float getCenterY ();

    /** Returns a copy of this shape's framing rectangle. */
    Rectangle getFrame ();

    /** Initializes the supplied rectangle with this shape's framing rectangle.
     * @return the supplied rectangle. */
    Rectangle getFrame (Rectangle target);
}
