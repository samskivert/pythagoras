//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides read-only access to a {@link Point}.
 */
public interface IPoint extends Cloneable
{
    /** Returns this point's x-coordinate. */
    double getX ();

    /** Returns this point's y-coordinate. */
    double getY ();

    /** Returns the squared Euclidian distance between this point and the specified point. */
    double distanceSq (double px, double py);

    /** Returns the squared Euclidian distance between this point and the supplied point. */
    double distanceSq (IPoint p);

    /** Returns the Euclidian distance between this point and the specified point. */
    double distance (double px, double py);

    /** Returns the Euclidian distance between this point and the supplied point. */
    double distance (IPoint p);

    /** Returns a mutable copy of this point. */
    Point clone ();
}
