//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides read-only access to a {@link CubicCurve}.
 */
public interface ICubicCurve extends IShape, Cloneable
{
    /** Returns the x-coordinate of the start of this curve. */
    double getX1 ();

    /** Returns the y-coordinate of the start of this curve. */
    double getY1 ();

    /** Returns the x-coordinate of the first control point. */
    double getCtrlX1 ();

    /** Returns the y-coordinate of the first control point. */
    double getCtrlY1 ();

    /** Returns the x-coordinate of the second control point. */
    double getCtrlX2 ();

    /** Returns the y-coordinate of the second control point. */
    double getCtrlY2 ();

    /** Returns the x-coordinate of the end of this curve. */
    double getX2 ();

    /** Returns the y-coordinate of the end of this curve. */
    double getY2 ();

    /** Returns a copy of the starting point of this curve. */
    Point getP1 ();

    /** Returns a copy of the first control point of this curve. */
    Point getCtrlP1 ();

    /** Returns a copy of the second control point of this curve. */
    Point getCtrlP2 ();

    /** Returns a copy of the ending point of this curve. */
    Point getP2 ();

    /** Returns the square of the flatness (maximum distance of a control point from the line
     * connecting the end points) of this curve. */
    double getFlatnessSq ();

    /** Returns the flatness (maximum distance of a control point from the line connecting the end
     * points) of this curve. */
    double getFlatness ();

    /** Subdivides this curve and stores the results into {@code left} and {@code right}. */
    void subdivide (CubicCurve left, CubicCurve right);

    /** Returns a mutable copy of this curve. */
    CubicCurve clone ();
}
