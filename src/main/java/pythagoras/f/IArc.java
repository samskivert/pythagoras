//
// $Id$

package pythagoras.f;

/**
 * Provides read-only access to an {@link Arc}.
 */
public interface IArc extends IRectangularShape, Cloneable
{
    /** An arc type indicating a simple, unconnected curve. */
    int OPEN = 0;

    /** An arc type indicating a closed curve, connected by a straight line from the starting to
     * the ending point of the arc. */
    int CHORD = 1;

    /** An arc type indicating a closed curve, connected by a line from the starting point of the
     * arc to the center of the circle defining the arc, and another straight line from that center
     * to the ending point of the arc. */
    int PIE = 2;

    /** Returns the type of this arc: {@link #OPEN}, etc. */
    int getArcType ();

    /** Returns the starting angle of this arc. */
    float getAngleStart ();

    /** Returns the angular extent of this arc. */
    float getAngleExtent ();

    /** Returns the intersection of the ray from the center (defined by the starting angle) and the
     * elliptical boundary of the arc. */
    Point getStartPoint ();

    /** Writes the intersection of the ray from the center (defined by the starting angle) and the
     * elliptical boundary of the arc into {@code target}.
     * @return the supplied point. */
    Point getStartPoint (Point target);

    /** Returns the intersection of the ray from the center (defined by the starting angle plus the
     * angular extent of the arc) and the elliptical boundary of the arc. */
    Point getEndPoint ();

    /** Writes the intersection of the ray from the center (defined by the starting angle plus the
     * angular extent of the arc) and the elliptical boundary of the arc into {@code target}.
     * @return the supplied point. */
    Point getEndPoint (Point target);

    /** Returns whether the specified angle is within the angular extents of this arc. */
    boolean containsAngle (float angle);

    /** Returns a mutable copy of this arc. */
    Arc clone ();
}
