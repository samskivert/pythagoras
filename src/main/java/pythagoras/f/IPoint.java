//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Provides read-only access to a {@link Point}.
 */
public interface IPoint extends Cloneable
{
    /**
     * Returns this point's x-coordinate.
     */
    float getX ();

    /**
     * Returns this point's y-coordinate.
     */
    float getY ();

    /**
     * Returns the squared Euclidian distance between this point and the specified point.
     */
    float distanceSq (float px, float py);

    /**
     * Returns the squared Euclidian distance between this point and the supplied point.
     */
    float distanceSq (IPoint p);

    /**
     * Returns the Euclidian distance between this point and the specified point.
     */
    float distance (float px, float py);

    /**
     * Returns the Euclidian distance between this point and the supplied point.
     */
    float distance (IPoint p);

    /**
     * Returns a mutable copy of this point.
     */
    Point clone ();
}
