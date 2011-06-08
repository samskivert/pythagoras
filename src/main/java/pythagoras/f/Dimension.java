//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.io.Serializable;

/**
 * Represents a magnitude in two dimensions.
 */
public class Dimension implements IDimension, Serializable
{
    /** The magnitude in the x-dimension. */
    public float width;

    /** The magnitude in the y-dimension. */
    public float height;

    /**
     * Creates a dimension with magnitude (0, 0).
     */
    public Dimension () {
        this(0, 0);
    }

    /**
     * Creates a dimension with the specified width and height.
     */
    public Dimension (float width, float height) {
        setSize(width, height);
    }

    /**
     * Creates a dimension with width and height equal to the supplied dimension.
     */
    public Dimension (IDimension d) {
        this(d.getWidth(), d.getHeight());
    }

    /**
     * Sets the magnitudes of this dimension to the specified width and height.
     */
    public void setSize (float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the magnitudes of this dimension to be equal to the supplied dimension.
     */
    public void setSize (IDimension d) {
        setSize(d.getWidth(), d.getHeight());
    }

    @Override // from interface IDimension
    public float getWidth () {
        return width;
    }

    @Override // from interface IDimension
    public float getHeight () {
        return height;
    }

    @Override // from interface IDimension
    public Dimension clone () {
        try {
            return (Dimension)super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new AssertionError(cnse);
        }
    }

    @Override
    public int hashCode () {
        return Float.floatToIntBits(width) ^ Float.floatToIntBits(height);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Dimension) {
            Dimension d = (Dimension)obj;
            return (d.width == width && d.height == height);
        }
        return false;
    }

    @Override
    public String toString () {
        return Geometry.dimenToString(width, height);
    }
}
