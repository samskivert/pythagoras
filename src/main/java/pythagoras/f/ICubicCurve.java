//
// $Id$

package pythagoras.f;

/**
 * Provides read-only access to a {@link CubicCurve}.
 */
public interface ICubicCurve extends IShape, Cloneable
{
    /** Returns the x-coordinate of the start of this curve. */
    float getX1 ();

    /** Returns the y-coordinate of the start of this curve. */
    float getY1 ();

    /** Returns the x-coordinate of the first control point. */
    float getCtrlX1 ();

    /** Returns the y-coordinate of the first control point. */
    float getCtrlY1 ();

    /** Returns the x-coordinate of the second control point. */
    float getCtrlX2 ();

    /** Returns the y-coordinate of the second control point. */
    float getCtrlY2 ();

    /** Returns the x-coordinate of the end of this curve. */
    float getX2 ();

    /** Returns the y-coordinate of the end of this curve. */
    float getY2 ();

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
