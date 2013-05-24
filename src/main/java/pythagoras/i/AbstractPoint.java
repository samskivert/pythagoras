//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Provides most of the implementation of {@link IPoint}, obtaining only the location from the
 * derived class.
 */
public abstract class AbstractPoint implements IPoint
{
    @Override // from IPoint
    public int distanceSq (int px, int py) {
        return Points.distanceSq(x(), y(), px, py);
    }

    @Override // from IPoint
    public int distanceSq (IPoint p) {
        return Points.distanceSq(x(), y(), p.x(), p.y());
    }

    @Override // from IPoint
    public int distance (int px, int py) {
        return Points.distance(x(), y(), px, py);
    }

    @Override // from IPoint
    public int distance (IPoint p) {
        return Points.distance(x(), y(), p.x(), p.y());
    }

    @Override // from IPoint
    public Point add (int x, int y) {
        return new Point(x() + x, y() + y);
    }

    @Override // from IPoint
    public Point add (int x, int y, Point result) {
        return result.set(x() + x, y() + y);
    }

    @Override // from IPoint
    public Point subtract (int x, int y) {
        return subtract(x, y, new Point());
    }

    @Override
    public Point subtract (int x, int y, Point result) {
        return result.set(x() - x, y() - y);
    }

    @Override
    public Point subtract (IPoint other, Point result) {
        return subtract(other.x(), other.y(), result);
    }

    @Override // from interface IPoint
    public Point clone () {
        return new Point(this);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractPoint) {
            AbstractPoint p = (AbstractPoint)obj;
            return x() == p.x() && y() == p.y();
        }
        return false;
    }

    @Override
    public int hashCode () {
        return x() ^ y();
    }

    @Override
    public String toString () {
        return Points.pointToString(x(), y());
    }
}
