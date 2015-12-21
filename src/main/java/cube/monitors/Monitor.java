package cube.monitors;

import cube.models.ICube;
import cube.models.Position;
import cube.models.Score;

import java.awt.*;
import java.util.List;
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
     * Check each line and erase cubes if necessary.
     */
    void refresh(Graphics g);

    /**
     * Get score of current game.
     * @return the score.
     */
    Integer getScore();

    /**
     * Get all historical score records.
     * @return the list of scores.
     */
    List<Score> getAllScores();
}
