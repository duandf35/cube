package cube.stages;

import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;

import javax.swing.*;
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
    public Integer[] getKeyboardAction() {
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
    public Map<Position, ICube> getCubes() {
        return null;
    }

    /**
     * Digest current active tetris, convert it into cubes.
     */
    public void digestTetris() {

    }

    /**
     * Refresh stage if tetris are erased.
     */
    public void refresh() {

    }
}
