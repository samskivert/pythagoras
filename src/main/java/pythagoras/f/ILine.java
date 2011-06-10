//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Provides read-only access to a {@link Line}.
 */
public interface ILine extends IShape, Cloneable
{
    /** Returns the x-coordinate of the start of this line. */
    float getX1 ();

    /** Returns the y-coordinate of the start of this line. */
    float getY1 ();

    /** Returns the x-coordinate of the end of this line. */
    float getX2 ();

    /** Returns the y-coordinate of the end of this line. */
    float getY2 ();

    /** Returns a copy of the starting point of this line. */
    Point getP1 ();

    /** Initializes the supplied point with this line's starting point.
     * @return the supplied point. */
    Point getP1 (Point target);

    /** Returns a copy of the ending point of this line. */
    Point getP2 ();

    /** Initializes the supplied point with this line's ending point.
     * @return the supplied point.  */
    Point getP2 (Point target);

    /** Returns the square of the distance from the specified point to the line defined by this
     * line segment. */
    float pointLineDistSq (float px, float py);

    /** Returns the square of the distance from the supplied point to the line defined by this line
     * segment. */
    float pointLineDistSq (IPoint p);

    /** Returns the distance from the specified point to the line defined by this line segment. */
    float pointLineDist (float px, float py);

    /** Returns the distance from the supplied point to the line defined by this line segment. */
    float pointLineDist (IPoint p);

    /** Returns the square of the distance from the specified point this line segment. */
    float pointSegDistSq (float px, float py);

    /** Returns the square of the distance from the supplied point this line segment. */
    float pointSegDistSq (IPoint p);

    /** Returns the distance from the specified point this line segment. */
    float pointSegDist (float px, float py);

    /** Returns the distance from the supplied point this line segment. */
    float pointSegDist (IPoint p);

    int relativeCCW (float px, float py);

    int relativeCCW (IPoint p);

    /** Returns a mutable copy of this line. */
    Line clone ();
}
