package cube.models;

import java.util.Map;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface Monitor {

    /**
     * Return cubes along with their position.
     * @return cubes with positions.
     */
    Map<Position, ICube> getCubes();

    /**
     * Add cube into monitor.
     * @param cube the cube
     */
    void add(ICube cube);

    /**
     * Remove cube in the position.
     * @param position the position
     */
    void remove(Position position);

    /**
     * Check if one line can be erased.
     * @param  line the line
     * @return true if the line can be erased
     */
    boolean isErasable(Integer line);

    /**
     * Erase cubes of the line.
     * @param line the line
     */
    void erase(Integer line);

    /**
     * Refresh monitor after erasing.
     */
    void refresh();
}
