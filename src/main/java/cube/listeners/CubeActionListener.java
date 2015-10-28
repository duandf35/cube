package cube.listeners;

import cube.models.Cube;
import cube.stages.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class CubeActionListener implements ActionListener {
    private Cube cube;
    private Stage stage;

    public CubeActionListener(Cube cube, Stage stage) {
        this.cube = cube;
        this.stage = stage;
    }

    /**
     * Listener to keyboard action and update the coordinate of cube.
     * @param e the keyboard action
     */
    public void actionPerformed(ActionEvent e) {
        Integer x = cube.getX();
        Integer y = cube.getY();

        Integer[] newCoords = stage.getKeyboardAction();
        adjustBoundary(x, y, stage.getXBoundary(), stage.getYBoundary(), newCoords);

        x += newCoords[0];
        y += newCoords[1];

        cube.setX(x);
        cube.setY(y);

        System.out.println("Update coordinate, x = " + x + ", y = " + y);
        stage.repaint();
    }

    /**
     * Adjust position updating, reset updating if reach boundary.
     * @param c the updated coordinate
     */
    private void adjustBoundary(Integer x, Integer y, Integer xb, Integer yb, Integer[] c) {

        if (x >= xb && c[0] >0) {
            x = xb;
            c[0] = 0;
        }

        if (x <= 0 && c[0] < 0) {
            c[0] = 0;
        }

        if (y >= yb && c[1] > 0) {
            y = yb;
            c[1] = 0;
        }

        if (y <= 0 && c[1] < 0) {
            c[1] = 0;
        }
    }
}
