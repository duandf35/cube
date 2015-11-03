package cube.stages;

import cube.models.Cube;

import javax.swing.*;

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
     * Get horizontal border of the stage.
     * @return the horizontal border
     */
    public Integer getXBorder() {
        return 0;
    }

    /**
     * Get vertical border of the stage.
     * @return the vertical border
     */
    public Integer getYBorder() {
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
     * Get cube in the stage.
     * @return the cube
     */
    public Cube getCube() {
        return null;
    }

    /**
     * Set cube to the stage.
     * @param cube the cube
     */
    public void setCube(Cube cube) {

    }

    /**
     * Unset cube in the stage.
     */
    public void digestCube() {

    }
}
