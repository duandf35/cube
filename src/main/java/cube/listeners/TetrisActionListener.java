package cube.listeners;

import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.services.Factory;
import cube.stages.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class TetrisActionListener implements ActionListener {
    private Stage stage;
    private Factory factory;

    public TetrisActionListener(Stage stage, Factory factory) {
        this.stage = stage;
        this.factory = factory;
    }

    /**
     * Listener to keyboard action and update the coordinate of tetris.
     * @param e the keyboard action
     */
    public void actionPerformed(ActionEvent e) {
        if (stage.getTetris() == null) {
            stage.setTetris((ITetris) factory.build());
        }

        Integer[] d = stage.getKeyboardAction();
        ITetris tetris = stage.getTetris();

        if (isMovable(d, tetris)) {
            adjustBoundary(d, tetris);
            moveTetris(d, tetris);
        }

        printTetrisPositon(tetris);
        stage.repaint();
    }

    private Position calculateNextPositions(Integer[] d, ICube cube) {
        Position p = new Position(cube.getPosition());

        p.moveX(d[0]);
        p.moveX(d[1]);

        return p;
    }

    private boolean isMovable(Integer[] d, ITetris tetris) {
        boolean canMove = true;

        if (d[0] == 0 && d[1] == 0) {
            canMove = false;
        } else {
            Map<Position, ICube> cubesInStage = stage.getCubes();

            for (ICube cube: tetris.getCubes()) {
                Position nextPosition = calculateNextPositions(d, cube);
                canMove &= isMovable(nextPosition, cubesInStage);
            }
        }

        return canMove;
    }

    private boolean isMovable(Position nextPosition, Map<Position, ICube> cubesInStage) {
        return (cubesInStage.get(nextPosition) == null);
    }

    private void moveTetris(Integer[] d, ITetris tetris) {
        tetris.move(d);
    }

    private boolean isRotatable(ITetris tetris) {
        return false;
    }

    private void rotateTetris() {

    }

    private void adjustBoundary(Integer[] d, ITetris tetris) {
        boolean reachEBoundary = false,
                reachWBoundary = false,
                reachNBoundary = false,
                reachSBoundary = false;

        for (ICube cube: tetris.getCubes()) {
            Position p = cube.getPosition();
            reachWBoundary |= (p.getX() <= 0);
            reachEBoundary |= (p.getX() >= stage.getXBoundary());
            reachNBoundary |= (p.getY() <= 0);
            reachSBoundary |= (p.getY() >= stage.getYBoundary());
        }

        if (reachWBoundary && d[0] < 0) {
            d[0] = 0;
        }

        if (reachEBoundary && d[0] > 0) {
            d[0] = 0;
        }

        if (reachNBoundary && d[1] < 0) {
            d[1] = 0;
        }

        if (reachSBoundary && d[1] > 0) {
            d[1] = 0;
        }
    }

    private void printTetrisPositon(ITetris tetris) {
        String tetrisPosition =
                tetris.getPositions()
                      .stream()
                      .map(p -> "x = " + p.getX() + ", y = " + p.getY())
                      .collect(Collectors.joining("\n"));

        System.out.println("\nTetris at:\n" + tetrisPosition + "\n");
    }
}
