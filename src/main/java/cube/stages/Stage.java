package cube.stages;

import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Score;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/27/15
 */
public abstract class Stage extends JPanel {

    /**
     * Get width of the stage.
     * @return the width
     */
    public Integer getXBoundary() {
        return 0;
    }

    /**
     * Get height of the stage.
     * @return the height
     */
    public Integer getYBoundary() {
        return 0;
    }

    /**
     * Get keyboard action.
     * @return the keyboard action
     */
    public Command getKeyboardAction() {
        return null;
    }

    /**
     * Get tetris in the stage.
     * @return the tetris
     */
    public ITetris getTetris() {
        return null;
    }

    /**
     * Set tetris to the stage.
     * @param tetris the tetris
     */
    public void setTetris(ITetris tetris) {

    }

    /**
     * Get position-cube map in the stage.
     * @return the map
     */
    public Map<Integer, Map<Integer, ICube>> getCubes() {
        return null;
    }

    /**
     * Digest current active tetris, convert it into cubes.
     */
    public void digestTetris() {

    }

    /**
     * Return score of current game.
     * @return the score.
     */
    public long getScore() {
        return 0;
    }

    /**
     * Get all historical score records.
     * @return the list of all score records.
     */
    public List<Score> getAllScores() {
        return null;
    }

    /**
     * Update score on the screen.
     */
    public void updateScore() {

    }
}
