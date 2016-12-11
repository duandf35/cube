package cube.stages;

import javax.swing.*;

import java.util.Map;

import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.monitors.timers.TimerRegister;

/**
 * @author wenyu
 * @since 10/27/15
 */
public abstract class ContainerStage extends JPanel implements TimerRegister {

    /**
     * Get width of the stage.
     * @return the width
     */
    public abstract Integer getXBoundary();

    /**
     * Get height of the stage.
     * @return the height
     */
    public abstract Integer getYBoundary();

    /**
     * Get keyboard action.
     * @return the keyboard action
     */
    public abstract Command getKeyboardAction();

    /**
     * Get tetris in the stage.
     * @return the tetris
     */
    public abstract ITetris getTetris();

    /**
     * Set tetris to the stage.
     * @param tetris the tetris
     */
    public abstract void setTetris(ITetris tetris);

    /**
     * Get position-cube map in the stage.
     * @return the map
     */
    public abstract Map<Integer, Map<Integer, ICube>> getCubes();

    /**
     * Digest current active tetris, convert it into cubes.
     */
    public abstract void digestTetris();

    /**
     * Reset stage.
     */
    public abstract void reset();

    /**
     * Pops out final score dialog.
     */
    public abstract void showFinalScore();

    /**
     * Hide final score dialog.
     */
    public abstract void hideFinalScore();
}
