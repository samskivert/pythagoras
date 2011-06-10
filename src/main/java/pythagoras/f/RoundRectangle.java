//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.io.Serializable;

/**
 * Represents a rectangle with rounded corners, defined by an arc width and height.
 */
public class RoundRectangle extends AbstractRoundRectangle implements Serializable
{
    public float x;
    public float y;
    public float width;
    public float height;
    public float arcwidth;
    public float archeight;

    public RoundRectangle () {
    }

    public RoundRectangle (float x, float y, float width, float height,
                           float arcwidth, float archeight) {
        setRoundRect(x, y, width, height, arcwidth, archeight);
    }

    public void setRoundRect (float x, float y, float width, float height,
                              float arcwidth, float archeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.arcwidth = arcwidth;
        this.archeight = archeight;
    }

    public void setRoundRect (IRoundRectangle rr) {
        setRoundRect(rr.getX(), rr.getY(), rr.getWidth(), rr.getHeight(),
                     rr.getArcWidth(), rr.getArcHeight());
    }

    @Override // from interface IRoundRectangle
    public float getArcWidth () {
        return arcwidth;
    }

    @Override // from interface IRoundRectangle
    public float getArcHeight () {
        return archeight;
    }

    @Override // from interface IRectangularShape
    public float getX () {
        return x;
    }

    @Override // from interface IRectangularShape
    public float getY () {
        return y;
    }

    @Override // from interface IRectangularShape
    public float getWidth () {
        return width;
    }

    @Override // from interface IRectangularShape
    public float getHeight () {
        return height;
    }

    @Override // from RoundRectangle
    public void setFrame (float x, float y, float width, float height) {
        setRoundRect(x, y, width, height, arcwidth, archeight);
    }
}
