//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides most of the implementation of {@link IDimension}, obtaining only width and height from
 * the derived class.
 */
public abstract class AbstractDimension implements IDimension
{
    @Override // from interface IDimension
    public Dimension clone () {
        return new Dimension(this);
    }

    @Override
    public int hashCode () {
        long bits = Double.doubleToLongBits(getWidth());
        bits += Double.doubleToLongBits(getHeight()) * 37;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractDimension) {
            AbstractDimension d = (AbstractDimension)obj;
            return (d.getWidth() == getWidth() && d.getHeight() == getHeight());
        }
        return false;
    }

    @Override
    public String toString () {
        return Dimensions.dimenToString(getWidth(), getHeight());
    }
}
