package cube.models;

import java.awt.*;
import java.util.List;

/**
 * @author wenyu
 * @since 11/3/15
 */
public interface ITetris {

    List<ICube> getCubes();

    List<Position> getPositions();

    void digest();

    boolean isDigested();

    void erase(Position position);

    void move(Integer[] d);

    void rotate();

    Position getNextMovePosition(Integer[] d, ICube cube);

    Position getNextRotatePosition(ICube cube);

    void paint(Graphics g);
}
