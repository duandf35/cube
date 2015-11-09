package cube.models;

import java.awt.*;
import java.util.List;

/**
 * @author wenyu
 * @since 11/3/15
 */
public interface ITetris {

    /**
     * Get UUID of tetris.
     * @return the UUID string
     */
    String getId();

    /**
     * Get composed cubes.
     * @return the cubes
     */
    List<ICube> getCubes();

    /**
     * Get cubes in the rim.
     * @return the rim cubes
     */
    List<ICube> getRim();

    /**
     * Get cubes in the center.
     * @return the center cubes.
     */
    List<ICube> getCenter();

    /**
     * Get positions of the composed cubes.
     * @return the positions
     */
    List<Position> getPositions();

    /**
     * Set digested flag.
     */
    void digest();

    /**
     * Check whether tetris is digested.
     * @return true if the tetris is digested
     */
    boolean isDigested();

    /**
     * Erase cubes based on the given position.
     * @param position the position
     */
    void erase(Position position);

    /**
     * Move tetris based on the given position change.
     * @param d the position change
     */
    void move(Integer[] d);

    /**
     * Rotate tetris.
     */
    void rotate();

    /**
     * Get possible position of the given cube if apply the given position change.
     * @param d    the position change
     * @param cube the cube
     * @return the position if the moving is applied
     */
    Position getNextMovePosition(Integer[] d, ICube cube);

    /**
     * Get possible position of the given cube if rotate.
     * @param cube the cube
     * @return the position if the rotating is applied
     */
    Position getNextRotatePosition(ICube cube);

    /**
     * Paint tetris.
     * @param g
     */
    void paint(Graphics g);
}
