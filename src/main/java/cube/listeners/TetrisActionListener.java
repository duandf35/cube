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

        if (isRotatable(d, tetris)) {
            rotateTetris(tetris);
        }

        if (isMovable(d, tetris)) {
            adjustBoundary(d, tetris);
            moveTetris(d, tetris);
        }

        printTetrisPositon(tetris);
        stage.repaint();
    }

    private boolean isMovable(Integer[] d, ITetris tetris) {
        boolean canMove = true;

        if (d[0] == 0 && d[1] == 0) {
            canMove = false;
        } else {
            for (ICube cube: tetris.getCubes()) {
                Position nextPosition = tetris.getNextMovePosition(d, cube);
                canMove &= isMovable(nextPosition, stage.getCubes());
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

    private boolean isRotatable(Integer[] d, ITetris tetris) {
        boolean canRotate = true;

        if (d[2] == 1) {
            for (ICube cube: tetris.getCubes()) {
                Position nextPosition = tetris.getNextRotatePosition(cube);
                canRotate &= (isMovable(nextPosition, stage.getCubes()) && !isReachBoundary(nextPosition));
            }
        } else {
            canRotate = false;
        }

        return canRotate;
    }

    private boolean isReachBoundary(Position p) {
        return (0 >= p.getX() || stage.getXBoundary() <= p.getX() || 0 >= p.getY() || stage.getYBoundary() <= p.getY());
    }

    private void rotateTetris(ITetris tetris) {
        tetris.rotate();
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
