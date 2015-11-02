package cube.stages;

import cube.models.Cube;

import javax.swing.*;

/**
 * @author wenyu
 * @since 10/27/15
 */
public abstract class Stage extends JPanel {

    public Integer getXBoundary() {
        return 0;
    }

    public Integer getYBoundary() {
        return 0;
    }

    public Integer[] getKeyboardAction() {
        return null;
    }

    public Cube getCube() {
        return null;
    }

    public void setCube(Cube cube) {

    }

    public void digestCube(Cube cube) {

    }
}
