package cube.models;

import java.awt.*;

/**
 * @author wenyu
 * @since 10/23/15
 */
public interface ICube {

    Integer getWidth();

    Integer getHeight();

    Position getPosition();

    void setPosition(Position position);

    void setPosition(Integer x, Integer y);

    void move(Integer[] d);

    void paint(Graphics g);
}
