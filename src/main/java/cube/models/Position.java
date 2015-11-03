package cube.models;

import org.codehaus.groovy.util.HashCodeHelper;

/**
 * @author wenyu
 * @since 11/2/15
 */
public class Position {
    private Integer x, y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return HashCodeHelper.updateHash(x.intValue(), y.intValue());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position p = (Position) o;
            return (x.equals(p.getX()) && y.equals(p.getY()));
        } else {
            return false;
        }
    }
}
