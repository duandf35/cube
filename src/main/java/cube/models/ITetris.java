package cube.models;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author wenyu
 * @since 11/3/15
 */
public interface ITetris {

    Map<Position, ICube> getTetris();

    List<Position> getPosition();

    void setPosition(List<Position> nextPositions);

    void rotate();

    void erase(Position position);

    void digest();

    boolean isDigested();

    void paint(Graphics g);
}
