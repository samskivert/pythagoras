//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Provides read-only access to a {@link Point}.
 */
public interface IPoint extends Cloneable
{
    /** Returns this point's x-coordinate. */
    int x ();

    /** Returns this point's y-coordinate. */
    int y ();

    /** Returns the squared Euclidian distance between this point and the specified point. */
    int distanceSq (int px, int py);

    /** Returns the squared Euclidian distance between this point and the supplied point. */
    int distanceSq (IPoint p);

    /** Returns the Euclidian distance between this point and the specified point. */
    int distance (int px, int py);

    /** Returns the Euclidian distance between this point and the supplied point. */
    int distance (IPoint p);

    /** Translates this point by the specified offset.
     * @return a new point containing the result. */
    Point add (int x, int y);

    /** Translates this point by the specified offset and stores the result in the object provided.
     * @return a reference to the result, for chaining. */
    Point add (int x, int y, Point result);

    /** Subtracts the supplied point from {@code this}.
     * @return a new point containing the result. */
    Point subtract (int x, int y);

    /** Subtracts the supplied point from {@code this} and stores the result in {@code result}.
     * @return a reference to the result, for chaining. */
    Point subtract (int x, int y, Point result);

    /** Subtracts the supplied point from {@code this} and stores the result in {@code result}.
     * @return a reference to the result, for chaining. */
    Point subtract (IPoint other, Point result);

    /** Returns a mutable copy of this point. */
    Point clone ();
}
