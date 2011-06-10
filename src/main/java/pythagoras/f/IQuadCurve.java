//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Provides read-only access to a {@link QuadCurve}.
 */
public interface IQuadCurve extends IShape, Cloneable
{
    /** Returns the x-coordinate of the start of this curve. */
    float getX1 ();

    /** Returns the y-coordinate of the start of this curve. */
    float getY1 ();

    /** Returns the x-coordinate of the control point. */
    float getCtrlX ();

    /** Returns the y-coordinate of the control point. */
    float getCtrlY ();

    /** Returns the x-coordinate of the end of this curve. */
    float getX2 ();

    /** Returns the y-coordinate of the end of this curve. */
    float getY2 ();

    /** Returns a copy of the starting point of this curve. */
    Point getP1 ();

    /** Returns a copy of the control point of this curve. */
    Point getCtrlP ();

    /** Returns a copy of the ending point of this curve. */
    Point getP2 ();

    /** Returns the square of the flatness (maximum distance of a control point from the line
     * connecting the end points) of this curve. */
    float getFlatnessSq ();

    /** Returns the flatness (maximum distance of a control point from the line connecting the end
     * points) of this curve. */
    float getFlatness ();

    /** Subdivides this curve and stores the results into {@code left} and {@code right}. */
    void subdivide (QuadCurve left, QuadCurve right);

    /** Returns a mutable copy of this curve. */
    QuadCurve clone ();
}
