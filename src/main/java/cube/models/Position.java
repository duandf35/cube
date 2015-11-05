package cube.models;

import org.codehaus.groovy.util.HashCodeHelper;

/**
 * @author wenyu
 * @since 11/2/15
 */
public class Position {
    private Integer x, y;

    public Position(Position position) {
        x = position.getX();
        y = position.getY();
    }

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

    public void moveX(Integer dx) {
        x = x + dx;
    }

    public void moveY(Integer dy) {
        y = y + dy;
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
